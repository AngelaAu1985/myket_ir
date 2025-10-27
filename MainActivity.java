package com.example.android.trivialdrivesample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.trivialdrivesample.util.IabHelper;
import com.example.android.trivialdrivesample.util.IabResult;
import com.example.android.trivialdrivesample.util.Inventory;
import com.example.android.trivialdrivesample.util.Purchase;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;

public class MainActivity extends AppCompatActivity {
    static final String TAG = "TrivialDrive";

    boolean mIsPremium = false;

    boolean mSubscribedToInfiniteGas = false;

    static final String SKU_PREMIUM = "premium";
    static final String SKU_GAS = "gas";

    static final String SKU_INFINITE_GAS = "infinite_gas";

    static final int RC_REQUEST = 10001;

    static int[] TANK_RES_IDS = { R.drawable.gas0, R.drawable.gas1, R.drawable.gas2,
                                   R.drawable.gas3, R.drawable.gas4 };

    static final int TANK_MAX = 4;

    int mTank;

    IabHelper mHelper;

    private MaterialButton buyGasButton;
    private MaterialButton driveButton;
    private MaterialButton premiumButton;
    private MaterialButton infiniteGasButton;
    private TextView gasLevelText;
    private TextView milesDrivenText;
    private TextView premiumStatusText;
    private ImageView carImage;
    private ImageView gasGaugeImage;
    private ProgressBar progressBar;
    private MaterialCardView statusCard;

    int mMilesDriven = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData();

        buyGasButton = findViewById(R.id.buy_gas_button);
        driveButton = findViewById(R.id.drive_button);
        premiumButton = findViewById(R.id.premium_button);
        infiniteGasButton = findViewById(R.id.infinite_gas_button);
        gasLevelText = findViewById(R.id.gas_level_text);
        milesDrivenText = findViewById(R.id.miles_driven_text);
        premiumStatusText = findViewById(R.id.premium_status_text);
        carImage = findViewById(R.id.car_image);
        gasGaugeImage = findViewById(R.id.gas_gauge_image);
        progressBar = findViewById(R.id.progress_bar);
        statusCard = findViewById(R.id.status_card);

        buyGasButton.setOnClickListener(v -> onBuyGasButtonClicked(v));
        driveButton.setOnClickListener(v -> onDriveButtonClicked(v));
        premiumButton.setOnClickListener(v -> onUpgradeAppButtonClicked(v));
        infiniteGasButton.setOnClickListener(v -> onInfiniteGasButtonClicked(v));

        String base64EncodedPublicKey = "MIGeMA0GCSqGSIb3DQEBAQUAA4GMADCBiAKBgOW5KR56WBWCb5K+yyVDnh/7op0FY4zmM93CWz3xFhgUJe2WXM/8MgpTHiDxrj2Mkgt9bg30qZDtT8gzDHiTgNv6G7pZBDWuyKEariGbbQgoCoeaq3GBcNsQf418jsvOfPjzZ7Rpcl/+9ZPsp1kbJVOmZxnwAZx/wnkUduwfuf8hAgMBAAE=";

        if (base64EncodedPublicKey.contains("CONSTRUCT_YOUR")) {
            throw new RuntimeException("Please put your app's public key in MainActivity.java. See README.");
        }

        Log.d(TAG, "Creating IAB helper.");
        mHelper = new IabHelper(this, base64EncodedPublicKey);

        mHelper.enableDebugLogging(true);

        Log.d(TAG, "Starting setup.");
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                Log.d(TAG, "Setup finished.");

                if (!result.isSuccess()) {
                    complain("Problem setting up in-app billing: " + result);
                    return;
                }

                if (mHelper == null) return;

                Log.d(TAG, "Setup successful. Querying inventory.");
                mHelper.queryInventoryAsync(mGotInventoryListener);
            }
        });
    }

    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            Log.d(TAG, "Query inventory finished.");

            if (mHelper == null) return;

            if (result.isFailure()) {
                complain("Failed to query inventory: " + result);
                return;
            }

            Log.d(TAG, "Query inventory was successful.");

            Purchase premiumPurchase = inventory.getPurchase(SKU_PREMIUM);
            mIsPremium = (premiumPurchase != null && verifyDeveloperPayload(premiumPurchase));
            Log.d(TAG, "User is " + (mIsPremium ? "PREMIUM" : "NOT PREMIUM"));

            Purchase infiniteGasPurchase = inventory.getPurchase(SKU_INFINITE_GAS);
            mSubscribedToInfiniteGas = (infiniteGasPurchase != null &&
                    verifyDeveloperPayload(infiniteGasPurchase));
            Log.d(TAG, "User " + (mSubscribedToInfiniteGas ? "HAS" : "DOES NOT HAVE")
                        + " infinite gas subscription.");
            if (mSubscribedToInfiniteGas) mTank = TANK_MAX;

            Purchase gasPurchase = inventory.getPurchase(SKU_GAS);
            if (gasPurchase != null && verifyDeveloperPayload(gasPurchase)) {
                Log.d(TAG, "We have gas. Consuming it.");
                mHelper.consumeAsync(inventory.getPurchase(SKU_GAS), mConsumeFinishedListener);
                return;
            }

            updateUi();
            setWaitScreen(false);
            Log.d(TAG, "Initial inventory query finished; enabling main UI.");
        }
    };

    public void onBuyGasButtonClicked(View arg0) {
        Log.d(TAG, "Buy gas button clicked.");

        if (mSubscribedToInfiniteGas) {
            complain("No need! You're subscribed to infinite gas. Isn't that awesome?");
            return;
        }

        if (mTank >= TANK_MAX) {
            complain("Your tank is full. Drive around a bit!");
            return;
        }

        setWaitScreen(true);
        Log.d(TAG, "Launching purchase flow for gas.");

        String payload = "";

        mHelper.launchPurchaseFlow(this, SKU_GAS, RC_REQUEST,
                mPurchaseFinishedListener, payload);
    }

    public void onUpgradeAppButtonClicked(View arg0) {
        Log.d(TAG, "Upgrade button clicked; launching purchase flow for upgrade.");
        setWaitScreen(true);

        String payload = "";

        mHelper.launchPurchaseFlow(this, SKU_PREMIUM, RC_REQUEST,
                mPurchaseFinishedListener, payload);
    }

    public void onInfiniteGasButtonClicked(View arg0) {
        if (!mHelper.subscriptionsSupported()) {
            complain("Subscriptions not supported on your device yet. Sorry!");
            return;
        }

        String payload = "";

        setWaitScreen(true);
        Log.d(TAG, "Launching purchase flow for infinite gas subscription.");
        mHelper.launchPurchaseFlow(this,
                SKU_INFINITE_GAS, IabHelper.ITEM_TYPE_SUBS,
                RC_REQUEST, mPurchaseFinishedListener, payload);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);
        if (mHelper == null) return;

        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
        else {
            Log.d(TAG, "onActivityResult handled by IABUtil.");
        }
    }

    boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();

        return true;
    }

    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            Log.d(TAG, "Purchase finished: " + result + ", purchase: " + purchase);

            if (mHelper == null) return;

            if (result.isFailure()) {
                complain("Error purchasing: " + result);
                setWaitScreen(false);
                return;
            }
            if (!verifyDeveloperPayload(purchase)) {
                complain("Error purchasing. Authenticity verification failed.");
                setWaitScreen(false);
                return;
            }

            Log.d(TAG, "Purchase successful.");

            if (purchase.getSku().equals(SKU_GAS)) {
                Log.d(TAG, "Purchase is gas. Starting gas consumption.");
                mHelper.consumeAsync(purchase, mConsumeFinishedListener);
            }
            else if (purchase.getSku().equals(SKU_PREMIUM)) {
                Log.d(TAG, "Purchase is premium upgrade. Congratulating user.");
                alert("Thank you for upgrading to premium!");
                mIsPremium = true;
                updateUi();
                setWaitScreen(false);
            }
            else if (purchase.getSku().equals(SKU_INFINITE_GAS)) {
                Log.d(TAG, "Infinite gas subscription purchased.");
                alert("Thank you for subscribing to infinite gas!");
                mSubscribedToInfiniteGas = true;
                mTank = TANK_MAX;
                updateUi();
                setWaitScreen(false);
            }
        }
    };

    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        public void onConsumeFinished(Purchase purchase, IabResult result) {
            Log.d(TAG, "Consumption finished. Purchase: " + purchase + ", result: " + result);

            if (mHelper == null) return;

            if (result.isSuccess()) {
                Log.d(TAG, "Consumption successful. Provisioning.");
                mTank = mTank == TANK_MAX ? TANK_MAX : mTank + 1;
                saveData();
                alert("You filled 1/4 tank. Your tank is now " + String.valueOf(mTank) + "/4 full!");
            }
            else {
                complain("Error while consuming: " + result);
            }
            updateUi();
            setWaitScreen(false);
            Log.d(TAG, "End consumption flow.");
        }
    };

    public void onDriveButtonClicked(View arg0) {
        Log.d(TAG, "Drive button clicked.");
        if (!mSubscribedToInfiniteGas && mTank <= 0) alert("Oh, no! You are out of gas! Try buying some!");
        else {
            if (!mSubscribedToInfiniteGas) --mTank;
            mMilesDriven++;
            saveData();
            alert("Vroooom, you drove a few miles.");
            updateUi();
            Log.d(TAG, "Vrooom. Tank is now " + mTank);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "Destroying helper.");
        if (mHelper != null) {
            mHelper.dispose();
            mHelper = null;
        }
    }

    public void updateUi() {
        carImage.setImageResource(mIsPremium ? R.drawable.premium_car : R.drawable.free_car);

        premiumButton.setVisibility(mIsPremium ? View.GONE : View.VISIBLE);

        infiniteGasButton.setVisibility(mSubscribedToInfiniteGas ?
                View.GONE : View.VISIBLE);

        driveButton.setEnabled(mSubscribedToInfiniteGas || mTank > 0);

        gasLevelText.setText("Gas: " + mTank + "/" + TANK_MAX + " units");
        milesDrivenText.setText("Miles Driven: " + mMilesDriven);
        premiumStatusText.setText(mIsPremium ? "Premium User" : "Standard User");

        if (mSubscribedToInfiniteGas) {
            gasGaugeImage.setImageResource(R.drawable.gas_inf);
        }
        else {
            int index = mTank >= TANK_RES_IDS.length ? TANK_RES_IDS.length - 1 : mTank;
            gasGaugeImage.setImageResource(TANK_RES_IDS[index]);
        }
    }

    void setWaitScreen(boolean set) {
        if (set) {
            progressBar.setVisibility(View.VISIBLE);
            statusCard.setAlpha(0.5f);
            buyGasButton.setEnabled(false);
            driveButton.setEnabled(false);
            premiumButton.setEnabled(false);
            infiniteGasButton.setEnabled(false);
        } else {
            progressBar.setVisibility(View.GONE);
            statusCard.setAlpha(1.0f);
            buyGasButton.setEnabled(true);
            premiumButton.setEnabled(!mIsPremium);
            infiniteGasButton.setEnabled(!mSubscribedToInfiniteGas);
            updateUi();
        }
    }

    void complain(String message) {
        Log.e(TAG, "**** TrivialDrive Error: " + message);
        alert("Error: " + message);
    }

    void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        Log.d(TAG, "Showing alert dialog: " + message);
        bld.create().show();
    }

    void saveData() {
        SharedPreferences.Editor spe = getPreferences(MODE_PRIVATE).edit();
        spe.putInt("tank", mTank);
        spe.putInt("miles_driven", mMilesDriven);
        spe.apply();
        Log.d(TAG, "Saved data: tank = " + String.valueOf(mTank));
    }

    void loadData() {
        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        mTank = sp.getInt("tank", 2);
        mMilesDriven = sp.getInt("miles_driven", 0);
        Log.d(TAG, "Loaded data: tank = " + String.valueOf(mTank));
    }
}