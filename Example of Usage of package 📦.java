Replace YOUR_BASE64_PUBLIC_KEY with your app's public key.

Material Design for UI (add dependencies in build.gradle):

implementation 'com.google.android.material:material:1.12.0'
implementation 'androidx.constraintlayout:constraintlayout:2.1.4'


MainActivity.java

package com.example.yourapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yourapp.util.IabHelper;
import com.example.yourapp.util.IabResult;
import com.example.yourapp.util.Inventory;
import com.example.yourapp.util.Purchase;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends Activity {
    private static final String TAG = "InAppBillingExample";
    private static final String SKU_PREMIUM = "premium";
    private static final String SKU_CREDITS = "credits";
    private static final String PAYLOAD_PREFIX = "secure_payload_";

    private static final int RC_REQUEST = 10001;

    private IabHelper mHelper;
    private boolean mIsPremium = false;
    private int mCredits = 0;
    private MaterialButton premiumButton;
    private MaterialButton buyCreditsButton;
    private TextView statusText;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getPreferences(MODE_PRIVATE);
        mCredits = prefs.getInt("credits", 0);

        premiumButton = findViewById(R.id.premium_button);
        buyCreditsButton = findViewById(R.id.buy_credits_button);
        statusText = findViewById(R.id.status_text);

        premiumButton.setOnClickListener(v -> launchPurchase(SKU_PREMIUM, false));
        buyCreditsButton.setOnClickListener(v -> launchPurchase(SKU_CREDITS, true));

        String publicKey = "YOUR_BASE64_PUBLIC_KEY";  // Replace with your key
        mHelper = new IabHelper(this, publicKey);
        mHelper.enableDebugLogging(BuildConfig.DEBUG);

        mHelper.startSetup(result -> {
            if (!result.isSuccess()) {
                Log.e(TAG, "Billing setup failed: " + result);
                Toast.makeText(this, "Billing unavailable", Toast.LENGTH_SHORT).show();
                return;
            }
            mHelper.queryInventoryAsync((queryResult, inventory) -> {
                if (!queryResult.isSuccess()) {
                    Log.e(TAG, "Inventory query failed: " + queryResult);
                    return;
                }
                handleInventory(inventory);
            });
        });

        updateUI();
    }

    private void launchPurchase(String sku, boolean isConsumable) {
        String payload = PAYLOAD_PREFIX + System.currentTimeMillis() + "_" + sku;  // Secure payload
        mHelper.launchPurchaseFlow(this, sku, isConsumable ? IabHelper.ITEM_TYPE_INAPP : IabHelper.ITEM_TYPE_INAPP,
                RC_REQUEST, result -> {
                    if (result.isSuccess() && result.getPurchase() != null) {
                        Purchase purchase = result.getPurchase();
                        if (sku.equals(SKU_CREDITS)) {
                            mHelper.consumeAsync(purchase, consumeResult -> {
                                if (consumeResult.isSuccess()) {
                                    mCredits += 100;  // Award credits
                                    saveData();
                                    Toast.makeText(this, "100 credits added!", Toast.LENGTH_SHORT).show();
                                }
                                updateUI();
                            });
                        } else {
                            mIsPremium = true;
                            saveData();
                            Toast.makeText(this, "Premium unlocked!", Toast.LENGTH_SHORT).show();
                        }
                        updateUI();
                    } else {
                        Log.w(TAG, "Purchase failed: " + result);
                        Toast.makeText(this, "Purchase failed", Toast.LENGTH_SHORT).show();
                    }
                }, payload);
    }

    private void handleInventory(Inventory inventory) {
        Purchase premium = inventory.getPurchase(SKU_PREMIUM);
        mIsPremium = premium != null && verifyPayload(premium);

        Purchase credits = inventory.getPurchase(SKU_CREDITS);
        if (credits != null && verifyPayload(credits)) {
            mHelper.consumeAsync(credits, result -> {
                if (result.isSuccess()) {
                    mCredits += 100;  // Restore unconsumed credits
                    saveData();
                }
            });
        }
        updateUI();
    }

    private boolean verifyPayload(Purchase p) {
        String expected = PAYLOAD_PREFIX + p.getOrderId();  // Server-side verification recommended
        return p.getDeveloperPayload().startsWith(expected);
    }

    private void updateUI() {
        premiumButton.setVisibility(mIsPremium ? View.GONE : View.VISIBLE);
        buyCreditsButton.setEnabled(mIsPremium);  // Premium users only
        statusText.setText(String.format("Credits: %d | Premium: %s", mCredits, mIsPremium ? "Yes" : "No"));
    }

    private void saveData() {
        prefs.edit()
                .putBoolean("is_premium", mIsPremium)
                .putInt("credits", mCredits)
                .apply();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mHelper != null) mHelper.handleActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHelper != null) {
            mHelper.dispose();
            mHelper = null;
        }
    }
}

