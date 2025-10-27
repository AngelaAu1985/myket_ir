package com.example.android.trivialdrivesample.util;

class Base64 {
    public final static boolean ENCODE = true;

    public final static boolean DECODE = false;

    private final static byte EQUALS_SIGN = (byte) '=';

    private final static byte NEW_LINE = (byte) '\n';

    private final static byte[] ALPHABET =
        {(byte) 'A', (byte) 'B', (byte) 'C', (byte) 'D', (byte) 'E', (byte) 'F',
        (byte) 'G', (byte) 'H', (byte) 'I', (byte) 'J', (byte) 'K',
        (byte) 'L', (byte) 'M', (byte) 'N', (byte) 'O', (byte) 'P',
        (byte) 'Q', (byte) 'R', (byte) 'S', (byte) 'T', (byte) 'U',
        (byte) 'V', (byte) 'W', (byte) 'X', (byte) 'Y', (byte) 'Z',
        (byte) 'a', (byte) 'b', (byte) 'c', (byte) 'd', (byte) 'e',
        (byte) 'f', (byte) 'g', (byte) 'h', (byte) 'i', (byte) 'j',
        (byte) 'k', (byte) 'l', (byte) 'm', (byte) 'n', (byte) 'o',
        (byte) 'p', (byte) 'q', (byte) 'r', (byte) 's', (byte) 't',
        (byte) 'u', (byte) 'v', (byte) 'w', (byte) 'x', (byte) 'y',
        (byte) 'z', (byte) '0', (byte) '1', (byte) '2', (byte) '3',
        (byte) '4', (byte) '5', (byte) '6', (byte) '7', (byte) '8',
        (byte) '9', (byte) '+', (byte) '/'};

    private final static byte[] WEBSAFE_ALPHABET =
        {(byte) 'A', (byte) 'B', (byte) 'C', (byte) 'D', (byte) 'E', (byte) 'F',
        (byte) 'G', (byte) 'H', (byte) 'I', (byte) 'J', (byte) 'K',
        (byte) 'L', (byte) 'M', (byte) 'N', (byte) 'O', (byte) 'P',
        (byte) 'Q', (byte) 'R', (byte) 'S', (byte) 'T', (byte) 'U',
        (byte) 'V', (byte) 'W', (byte) 'X', (byte) 'Y', (byte) 'Z',
        (byte) 'a', (byte) 'b', (byte) 'c', (byte) 'd', (byte) 'e',
        (byte) 'f', (byte) 'g', (byte) 'h', (byte) 'i', (byte) 'j',
        (byte) 'k', (byte) 'l', (byte) 'm', (byte) 'n', (byte) 'o',
        (byte) 'p', (byte) 'q', (byte) 'r', (byte) 's', (byte) 't',
        (byte) 'u', (byte) 'v', (byte) 'w', (byte) 'x', (byte) 'y',
        (byte) 'z', (byte) '0', (byte) '1', (byte) '2', (byte) '3',
        (byte) '4', (byte) '5', (byte) '6', (byte) '7', (byte) '8',
        (byte) '9', (byte) '-', (byte) '_'};

    private final static byte[] DECODABET = {-9, -9, -9, -9, -9, -9, -9, -9, -9,
        -5, -5,
        -9, -9,
        -5,
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,
        -9, -9, -9, -9, -9,
        -5,
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,
        62,
        -9, -9, -9,
        63,
        52, 53, 54, 55, 56, 57, 58, 59, 60, 61,
        -9, -9, -9,
        -1,
        -9, -9, -9,
        0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,
        14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,
        -9, -9, -9, -9, -9, -9,
        26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38,
        39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51,
        -9, -9, -9, -9, -9 };

    private final static byte[] WEBSAFE_DECODABET =
        {-9, -9, -9, -9, -9, -9, -9, -9, -9,
        -5, -5,
        -9, -9,
        -5,
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,
        -9, -9, -9, -9, -9,
        -5,
        -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9,
        62,
        -9, -9,
        52, 53, 54, 55, 56, 57, 58, 59, 60, 61,
        -9, -9, -9,
        -1,
        -9, -9, -9,
        0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,
        14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,
        -9, -9, -9, -9,
        63,
        -9,
        26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38,
        39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51,
        -9, -9, -9, -9, -9 };

    private final static byte WHITE_SPACE_ENC = -5;
    private final static byte EQUALS_SIGN_ENC = -1;

    private Base64() {
    }

    private static byte[] encode3to4(byte[] source, int srcOffset,
            int numSigBytes, byte[] destination, int destOffset, byte[] alphabet) {
        int inBuff =
                (numSigBytes > 0 ? ((source[srcOffset] << 24) >>> 8) : 0)
                | (numSigBytes > 1 ? ((source[srcOffset + 1] << 24) >>> 16) : 0)
                | (numSigBytes > 2 ? ((source[srcOffset + 2] << 24) >>> 24) : 0);

        switch (numSigBytes) {
            case 3:
                destination[destOffset] = alphabet[(inBuff >>> 18)];
                destination[destOffset + 1] = alphabet[(inBuff >>> 12) & 0x3f];
                destination[destOffset + 2] = alphabet[(inBuff >>> 6) & 0x3f];
                destination[destOffset + 3] = alphabet[(inBuff) & 0x3f];
                return destination;
            case 2:
                destination[destOffset] = alphabet[(inBuff >>> 18)];
                destination[destOffset + 1] = alphabet[(inBuff >>> 12) & 0x3f];
                destination[destOffset + 2] = alphabet[(inBuff >>> 6) & 0x3f];
                destination[destOffset + 3] = EQUALS_SIGN;
                return destination;
            case 1:
                destination[destOffset] = alphabet[(inBuff >>> 18)];
                destination[destOffset + 1] = alphabet[(inBuff >>> 12) & 0x3f];
                destination[destOffset + 2] = EQUALS_SIGN;
                destination[destOffset + 3] = EQUALS_SIGN;
                return destination;
            default:
                return destination;
        }
    }

    public static String encode(byte[] source) {
        return encode(source, 0, source.length, ALPHABET, true);
    }

    public static String encodeWebSafe(byte[] source, boolean doPadding) {
        return encode(source, 0, source.length, WEBSAFE_ALPHABET, doPadding);
    }

    public static String encode(byte[] source, int off, int len, byte[] alphabet,
            boolean doPadding) {
        byte[] outBuff = encode(source, off, len, alphabet, Integer.MAX_VALUE);
        int outLen = outBuff.length;

        while (doPadding == false && outLen > 0) {
            if (outBuff[outLen - 1] != '=') {
                break;
            }
            outLen -= 1;
        }

        return new String(outBuff, 0, outLen);
    }

    public static byte[] encode(byte[] source, int off, int len, byte[] alphabet,
            int maxLineLength) {
        int lenDiv3 = (len + 2) / 3;
        int len43 = lenDiv3 * 4;
        byte[] outBuff = new byte[len43
                                  + (len43 / maxLineLength)];

        int d = 0;
        int e = 0;
        int len2 = len - 2;
        int lineLength = 0;
        for (; d < len2; d += 3, e += 4) {

            int inBuff =
                    ((source[d + off] << 24) >>> 8)
                    | ((source[d + 1 + off] << 24) >>> 16)
                    | ((source[d + 2 + off] << 24) >>> 24);
            outBuff[e] = alphabet[(inBuff >>> 18)];
            outBuff[e + 1] = alphabet[(inBuff >>> 12) & 0x3f];
            outBuff[e + 2] = alphabet[(inBuff >>> 6) & 0x3f];
            outBuff[e + 3] = alphabet[(inBuff) & 0x3f];

            lineLength += 4;
            if (lineLength == maxLineLength) {
                outBuff[e + 4] = NEW_LINE;
                e++;
                lineLength = 0;
            }
        }

        if (d < len) {
            encode3to4(source, d + off, len - d, outBuff, e, alphabet);

            lineLength += 4;
            if (lineLength == maxLineLength) {
                outBuff[e + 4] = NEW_LINE;
                e++;
            }
            e += 4;
        }

        return outBuff;
    }

    private static int decode4to3(byte[] source, int srcOffset,
            byte[] destination, int destOffset, byte[] decodabet) {
        if (source[srcOffset + 2] == EQUALS_SIGN) {
            int outBuff =
                    ((decodabet[source[srcOffset]] << 24) >>> 6)
                    | ((decodabet[source[srcOffset + 1]] << 24) >>> 12);

            destination[destOffset] = (byte) (outBuff >>> 16);
            return 1;
        } else if (source[srcOffset + 3] == EQUALS_SIGN) {
            int outBuff =
                    ((decodabet[source[srcOffset]] << 24) >>> 6)
                    | ((decodabet[source[srcOffset + 1]] << 24) >>> 12)
                    | ((decodabet[source[srcOffset + 2]] << 24) >>> 18);

            destination[destOffset] = (byte) (outBuff >>> 16);
            destination[destOffset + 1] = (byte) (outBuff >>> 8);
            return 2;
        } else {
            int outBuff =
                    ((decodabet[source[srcOffset]] << 24) >>> 6)
                    | ((decodabet[source[srcOffset + 1]] << 24) >>> 12)
                    | ((decodabet[source[srcOffset + 2]] << 24) >>> 18)
                    | ((decodabet[source[srcOffset + 3]] << 24) >>> 24);

            destination[destOffset] = (byte) (outBuff >> 16);
            destination[destOffset + 1] = (byte) (outBuff >> 8);
            destination[destOffset + 2] = (byte) (outBuff);
            return 3;
        }
    }

    public static byte[] decode(String s) throws Base64DecoderException {
        byte[] bytes = s.getBytes();
        return decode(bytes, 0, bytes.length);
    }

    public static byte[] decodeWebSafe(String s) throws Base64DecoderException {
        byte[] bytes = s.getBytes();
        return decodeWebSafe(bytes, 0, bytes.length);
    }

    public static byte[] decode(byte[] source) throws Base64DecoderException {
        return decode(source, 0, source.length);
    }

    public static byte[] decodeWebSafe(byte[] source)
            throws Base64DecoderException {
        return decodeWebSafe(source, 0, source.length);
    }

    public static byte[] decode(byte[] source, int off, int len)
            throws Base64DecoderException {
        return decode(source, off, len, DECODABET);
    }

    public static byte[] decodeWebSafe(byte[] source, int off, int len)
            throws Base64DecoderException {
        return decode(source, off, len, WEBSAFE_DECODABET);
    }

    public static byte[] decode(byte[] source, int off, int len, byte[] decodabet)
            throws Base64DecoderException {
        int len34 = len * 3 / 4;
        byte[] outBuff = new byte[2 + len34];
        int outBuffPosn = 0;

        byte[] b4 = new byte[4];
        int b4Posn = 0;
        int i = 0;
        byte sbiCrop = 0;
        byte sbiDecode = 0;
        for (i = 0; i < len; i++) {
            sbiCrop = (byte) (source[i + off] & 0x7f);
            sbiDecode = decodabet[sbiCrop];

            if (sbiDecode >= WHITE_SPACE_ENC) {
                if (sbiDecode >= EQUALS_SIGN_ENC) {
                    if (sbiCrop == EQUALS_SIGN) {
                        int bytesLeft = len - i;
                        byte lastByte = (byte) (source[len - 1 + off] & 0x7f);
                        if (b4Posn == 0 || b4Posn == 1) {
                            throw new Base64DecoderException(
                                    "invalid padding byte '=' at byte offset " + i);
                        } else if ((b4Posn == 3 && bytesLeft > 2)
                                || (b4Posn == 4 && bytesLeft > 1)) {
                            throw new Base64DecoderException(
                                    "padding byte '=' falsely signals end of encoded value "
                                            + "at offset " + i);
                        } else if (lastByte != EQUALS_SIGN && lastByte != NEW_LINE) {
                            throw new Base64DecoderException(
                                    "encoded value has invalid trailing byte");
                        }
                        break;
                    }

                    b4[b4Posn++] = sbiCrop;
                    if (b4Posn == 4) {
                        outBuffPosn += decode4to3(b4, 0, outBuff, outBuffPosn, decodabet);
                        b4Posn = 0;
                    }
                }
            } else {
                throw new Base64DecoderException("Bad Base64 input character at " + i
                        + ": " + source[i + off] + "(decimal)");
            }
        }

        if (b4Posn != 0) {
            if (b4Posn == 1) {
                throw new Base64DecoderException("single trailing character at offset "
                        + (len - 1));
            }
            b4[b4Posn++] = EQUALS_SIGN;
            outBuffPosn += decode4to3(b4, 0, outBuff, outBuffPosn, decodabet);
        }

        byte[] out = new byte[outBuffPosn];
        System.arraycopy(outBuff, 0, out, 0, outBuffPosn);
        return out;
    }
}

class Base64DecoderException extends Exception {
    public Base64DecoderException() {
        super();
    }

    public Base64DecoderException(String s) {
        super(s);
    }

    private static final long serialVersionUID = 1L;
}

class IabException extends Exception {
    IabResult mResult;

    public IabException(IabResult r) {
        this(r, null);
    }
    public IabException(int response, String message) {
        this(new IabResult(response, message));
    }
    public IabException(IabResult r, Exception cause) {
        super(r.getMessage(), cause);
        mResult = r;
    }
    public IabException(int response, String message, Exception cause) {
        this(new IabResult(response, message), cause);
    }

    public IabResult getResult() { return mResult; }
}

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.IntentSender.SendIntentException;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import com.android.vending.billing.IInAppBillingService;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class IabHelper {
    boolean mDebugLog = false;
    String mDebugTag = "IabHelper";

    boolean mSetupDone = false;

    boolean mDisposed = false;

    boolean mSubscriptionsSupported = false;

    boolean mAsyncInProgress = false;

    String mAsyncOperation = "";

    Context mContext;

    IInAppBillingService mService;
    ServiceConnection mServiceConn;

    int mRequestCode;

    String mPurchasingItemType;

    String mSignatureBase64 = null;

    public static final int BILLING_RESPONSE_RESULT_OK = 0;
    public static final int BILLING_RESPONSE_RESULT_USER_CANCELED = 1;
    public static final int BILLING_RESPONSE_RESULT_BILLING_UNAVAILABLE = 3;
    public static final int BILLING_RESPONSE_RESULT_ITEM_UNAVAILABLE = 4;
    public static final int BILLING_RESPONSE_RESULT_DEVELOPER_ERROR = 5;
    public static final int BILLING_RESPONSE_RESULT_ERROR = 6;
    public static final int BILLING_RESPONSE_RESULT_ITEM_ALREADY_OWNED = 7;
    public static final int BILLING_RESPONSE_RESULT_ITEM_NOT_OWNED = 8;

    public static final int IABHELPER_ERROR_BASE = -1000;
    public static final int IABHELPER_REMOTE_EXCEPTION = -1001;
    public static final int IABHELPER_BAD_RESPONSE = -1002;
    public static final int IABHELPER_VERIFICATION_FAILED = -1003;
    public static final int IABHELPER_SEND_INTENT_FAILED = -1004;
    public static final int IABHELPER_USER_CANCELLED = -1005;
    public static final int IABHELPER_UNKNOWN_PURCHASE_RESPONSE = -1006;
    public static final int IABHELPER_MISSING_TOKEN = -1007;
    public static final int IABHELPER_UNKNOWN_ERROR = -1008;
    public static final int IABHELPER_SUBSCRIPTIONS_NOT_AVAILABLE = -1009;
    public static final int IABHELPER_INVALID_CONSUMPTION = -1010;

    public static final String RESPONSE_CODE = "RESPONSE_CODE";
    public static final String RESPONSE_GET_SKU_DETAILS_LIST = "DETAILS_LIST";
    public static final String RESPONSE_BUY_INTENT = "BUY_INTENT";
    public static final String RESPONSE_INAPP_PURCHASE_DATA = "INAPP_PURCHASE_DATA";
    public static final String RESPONSE_INAPP_SIGNATURE = "INAPP_DATA_SIGNATURE";
    public static final String RESPONSE_INAPP_ITEM_LIST = "INAPP_PURCHASE_ITEM_LIST";
    public static final String RESPONSE_INAPP_PURCHASE_DATA_LIST = "INAPP_PURCHASE_DATA_LIST";
    public static final String RESPONSE_INAPP_SIGNATURE_LIST = "INAPP_DATA_SIGNATURE_LIST";
    public static final String INAPP_CONTINUATION_TOKEN = "INAPP_CONTINUATION_TOKEN";

    public static final String ITEM_TYPE_INAPP = "inapp";
    public static final String ITEM_TYPE_SUBS = "subs";

    public static final String GET_SKU_DETAILS_ITEM_LIST = "ITEM_ID_LIST";
    public static final String GET_SKU_DETAILS_ITEM_TYPE_LIST = "ITEM_TYPE_LIST";

    public IabHelper(Context ctx, String base64PublicKey) {
        mContext = ctx.getApplicationContext();
        mSignatureBase64 = base64PublicKey;
        logDebug("IAB helper created.");
    }

    public void enableDebugLogging(boolean enable, String tag) {
        checkNotDisposed();
        mDebugLog = enable;
        mDebugTag = tag;
    }

    public void enableDebugLogging(boolean enable) {
        checkNotDisposed();
        mDebugLog = enable;
    }

    public interface OnIabSetupFinishedListener {
        public void onIabSetupFinished(IabResult result);
    }

    public void startSetup(final OnIabSetupFinishedListener listener) {
        checkNotDisposed();
        if (mSetupDone) throw new IllegalStateException("IAB helper is already set up.");

        logDebug("Starting in-app billing setup.");
        mServiceConn = new ServiceConnection() {
            @Override
            public void onServiceDisconnected(ComponentName name) {
                logDebug("Billing service disconnected.");
                mService = null;
            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                if (mDisposed) return;
                logDebug("Billing service connected.");
                mService = IInAppBillingService.Stub.asInterface(service);
                String packageName = mContext.getPackageName();
                try {
                    logDebug("Checking for in-app billing 3 support.");

                    int response = mService.isBillingSupported(3, packageName, ITEM_TYPE_INAPP);
                    if (response != BILLING_RESPONSE_RESULT_OK) {
                        if (listener != null) listener.onIabSetupFinished(new IabResult(response,
                                "Error checking for billing v3 support."));

                        mSubscriptionsSupported = false;
                        return;
                    }
                    logDebug("In-app billing version 3 supported for " + packageName);

                    response = mService.isBillingSupported(3, packageName, ITEM_TYPE_SUBS);
                    if (response == BILLING_RESPONSE_RESULT_OK) {
                        logDebug("Subscriptions AVAILABLE.");
                        mSubscriptionsSupported = true;
                    }
                    else {
                        logDebug("Subscriptions NOT AVAILABLE. Response: " + response);
                    }

                    mSetupDone = true;
                }
                catch (RemoteException e) {
                    if (listener != null) {
                        listener.onIabSetupFinished(new IabResult(IABHELPER_REMOTE_EXCEPTION,
                                                    "RemoteException while setting up in-app billing."));
                    }
                    e.printStackTrace();
                    return;
                }

                if (listener != null) {
                    listener.onIabSetupFinished(new IabResult(BILLING_RESPONSE_RESULT_OK, "Setup successful."));
                }
            }
        };

        Intent serviceIntent = new Intent("ir.mservices.market.InAppBillingService.BIND");
        serviceIntent.setPackage("ir.mservices.market");

        PackageManager pm = mContext.getPackageManager();
        List<ResolveInfo> intentServices = pm.queryIntentServices(serviceIntent, 0);
        if (intentServices != null && !intentServices.isEmpty()) {
            mContext.bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);
        }
        else {
            if (listener != null) {
                listener.onIabSetupFinished(
                        new IabResult(BILLING_RESPONSE_RESULT_BILLING_UNAVAILABLE,
                        "Billing service unavailable on device."));
            }
        }
    }

    public void dispose() {
        logDebug("Disposing.");
        mSetupDone = false;
        if (mServiceConn != null) {
            logDebug("Unbinding from service.");
            if (mContext != null && mService != null) {
                mContext.unbindService(mServiceConn);
            }
        }
        mDisposed = true;
        mContext = null;
        mServiceConn = null;
        mService = null;
        mPurchaseListener = null;
    }

    private void checkNotDisposed() {
        if (mDisposed) throw new IllegalStateException("IabHelper was disposed of, so it cannot be used.");
    }

    public boolean subscriptionsSupported() {
        checkNotDisposed();
        return mSubscriptionsSupported;
    }

    public interface OnIabPurchaseFinishedListener {
        public void onIabPurchaseFinished(IabResult result, Purchase info);
    }

    OnIabPurchaseFinishedListener mPurchaseListener;

    public void launchPurchaseFlow(Activity act, String sku, int requestCode, OnIabPurchaseFinishedListener listener) {
        launchPurchaseFlow(act, sku, requestCode, listener, "");
    }

    public void launchPurchaseFlow(Activity act, String sku, int requestCode,
            OnIabPurchaseFinishedListener listener, String extraData) {
        launchPurchaseFlow(act, sku, ITEM_TYPE_INAPP, requestCode, listener, extraData);
    }

    public void launchSubscriptionPurchaseFlow(Activity act, String sku, int requestCode,
            OnIabPurchaseFinishedListener listener) {
        launchSubscriptionPurchaseFlow(act, sku, requestCode, listener, "");
    }

    public void launchSubscriptionPurchaseFlow(Activity act, String sku, int requestCode,
            OnIabPurchaseFinishedListener listener, String extraData) {
        launchPurchaseFlow(act, sku, ITEM_TYPE_SUBS, requestCode, listener, extraData);
    }

    public void launchPurchaseFlow(Activity act, String sku, String itemType, int requestCode,
                        OnIabPurchaseFinishedListener listener, String extraData) {
        checkNotDisposed();
        checkSetupDone("launchPurchaseFlow");
        flagStartAsync("launchPurchaseFlow");
        IabResult result;

        if (itemType.equals(ITEM_TYPE_SUBS) && !mSubscriptionsSupported) {
            IabResult r = new IabResult(IABHELPER_SUBSCRIPTIONS_NOT_AVAILABLE,
                    "Subscriptions are not available.");
            flagEndAsync();
            if (listener != null) listener.onIabPurchaseFinished(r, null);
            return;
        }

        try {
            logDebug("Constructing buy intent for " + sku + ", item type: " + itemType);
            int apiVersion = 3;

            logDebug("launchBuyIntent for " + sku + ", item type: " + itemType);
            launchBuyIntent(act, sku, itemType, requestCode, listener, extraData);
        } catch (IntentSender.SendIntentException e) {
            logError("SendIntentException while launching purchase flow for sku " + sku);
            e.printStackTrace();
            flagEndAsync();

            result = new IabResult(IABHELPER_SEND_INTENT_FAILED, "Failed to send intent.");
            if (listener != null) listener.onIabPurchaseFinished(result, null);
        } catch (RemoteException e) {
            logError("RemoteException while launching purchase flow for sku " + sku);
            e.printStackTrace();
            flagEndAsync();

            result = new IabResult(IABHELPER_REMOTE_EXCEPTION, "Remote exception while starting purchase flow");
            if (listener != null) listener.onIabPurchaseFinished(result, null);
        }
    }

    private void launchBuyIntent(Activity act, String sku, String itemType, int requestCode,
            OnIabPurchaseFinishedListener listener, String extraData)
            throws RemoteException, IntentSender.SendIntentException {

        int apiVersion = 3;
        String packageName = mContext.getPackageName();

        Bundle buyIntentBundle = mService.getBuyIntent(apiVersion, packageName, sku, itemType, extraData);
        int response = getResponseCodeFromBundle(buyIntentBundle);

        if (response != BILLING_RESPONSE_RESULT_OK) {
            logError("Unable to buy item, Error response: " + getResponseDesc(response));
            flagEndAsync();
            IabResult result = new IabResult(response, "Unable to buy item");
            if (listener != null) listener.onIabPurchaseFinished(result, null);
            return;
        }

        PendingIntent pendingIntent = buyIntentBundle.getParcelable(RESPONSE_BUY_INTENT);
        logDebug("Launching buy intent v1 for " + sku + ". Request code: " + requestCode);
        mRequestCode = requestCode;
        mPurchaseListener = listener;
        mPurchasingItemType = itemType;
        act.startIntentSenderForResult(pendingIntent.getIntentSender(),
                requestCode, new Intent(),
                Integer.valueOf(0), Integer.valueOf(0),
                Integer.valueOf(0));
    }

    public boolean handleActivityResult(int requestCode, int resultCode, Intent data) {
        IabResult result;
        if (requestCode != mRequestCode) return false;

        checkNotDisposed();
        checkSetupDone("handleActivityResult");

        flagEndAsync();

        if (data == null) {
            logError("Null data in IAB activity result.");
            result = new IabResult(IABHELPER_BAD_RESPONSE, "Null data in IAB result");
            if (mPurchaseListener != null) mPurchaseListener.onIabPurchaseFinished(result, null);
            return true;
        }

        int responseCode = getResponseCodeFromIntent(data);
        String purchaseData = data.getStringExtra(RESPONSE_INAPP_PURCHASE_DATA);
        String dataSignature = data.getStringExtra(RESPONSE_INAPP_SIGNATURE);

        if (resultCode == Activity.RESULT_OK && responseCode == BILLING_RESPONSE_RESULT_OK) {
            logDebug("Successful resultcode from purchase activity.");
            logDebug("Purchase data: " + purchaseData);
            logDebug("Data signature: " + dataSignature);
            logDebug("Extras: " + data.getExtras());
            logDebug("Expected item type: " + mPurchasingItemType);

            if (purchaseData == null || dataSignature == null) {
                logError("BUG: either purchaseData or dataSignature is null.");
                logDebug("Extras: " + data.getExtras().toString());
                result = new IabResult(IABHELPER_UNKNOWN_ERROR, "IAB returned null purchaseData or dataSignature");
                if (mPurchaseListener != null) mPurchaseListener.onIabPurchaseFinished(result, null);
                return true;
            }

            Purchase purchase = null;
            try {
                purchase = new Purchase(mPurchasingItemType, purchaseData, dataSignature);
                String sku = purchase.getSku();

                if (!Security.verifyPurchase(mSignatureBase64, purchaseData, dataSignature)) {
                    logError("Purchase signature verification FAILED for sku " + sku);
                    result = new IabResult(IABHELPER_VERIFICATION_FAILED, "Signature verification failed for sku " + sku);
                    if (mPurchaseListener != null) mPurchaseListener.onIabPurchaseFinished(result, purchase);
                    return true;
                }
                logDebug("Purchase signature successfully verified.");
            }
            catch (JSONException e) {
                logError("Failed to parse purchase data.");
                e.printStackTrace();
                result = new IabResult(IABHELPER_BAD_RESPONSE, "Failed to parse purchase data.");
                if (mPurchaseListener != null) mPurchaseListener.onIabPurchaseFinished(result, null);
                return true;
            }

            if (mPurchaseListener != null) {
                mPurchaseListener.onIabPurchaseFinished(new IabResult(BILLING_RESPONSE_RESULT_OK, "Success"), purchase);
            }
        }
        else if (resultCode == Activity.RESULT_OK) {
            logDebug("Result code was OK but in-app billing response was not OK: " + getResponseDesc(responseCode));
            if (mPurchaseListener != null) {
                result = new IabResult(responseCode, "Problem purchashing item.");
                mPurchaseListener.onIabPurchaseFinished(result, null);
            }
        }
        else if (resultCode == Activity.RESULT_CANCELED) {
            logDebug("Purchase canceled - Response: " + getResponseDesc(responseCode));
            result = new IabResult(IABHELPER_USER_CANCELLED, "User canceled.");
            if (mPurchaseListener != null) mPurchaseListener.onIabPurchaseFinished(result, null);
        }
        else {
            logError("Purchase failed. Result code: " + Integer.toString(resultCode)
                    + ". Response: " + getResponseDesc(responseCode));
            result = new IabResult(IABHELPER_UNKNOWN_PURCHASE_RESPONSE, "Unknown purchase response.");
            if (mPurchaseListener != null) mPurchaseListener.onIabPurchaseFinished(result, null);
        }
        return true;
    }

    public Inventory queryInventory(boolean querySkuDetails, List<String> moreSkus) throws IabException {
        return queryInventory(querySkuDetails, moreSkus, null);
    }

    public Inventory queryInventory(boolean querySkuDetails, List<String> moreItemSkus,
                                        List<String> moreSubsSkus) throws IabException {
        checkNotDisposed();
        checkSetupDone("queryInventory");
        try {
            Inventory inv = new Inventory();
            int r = queryPurchases(inv, ITEM_TYPE_INAPP);
            if (r != BILLING_RESPONSE_RESULT_OK) {
                throw new IabException(r, "Error refreshing inventory (querying owned items).");
            }

            if (querySkuDetails) {
                r = querySkuDetails(ITEM_TYPE_INAPP, inv, moreItemSkus);
                if (r != BILLING_RESPONSE_RESULT_OK) {
                    throw new IabException(r, "Error refreshing inventory (querying prices of items).");
                }
            }

            if (mSubscriptionsSupported) {
                r = queryPurchases(inv, ITEM_TYPE_SUBS);
                if (r != BILLING_RESPONSE_RESULT_OK) {
                    throw new IabException(r, "Error refreshing inventory (querying owned subscriptions).");
                }

                if (querySkuDetails) {
                    r = querySkuDetails(ITEM_TYPE_SUBS, inv, moreSubsSkus);
                    if (r != BILLING_RESPONSE_RESULT_OK) {
                        throw new IabException(r, "Error refreshing inventory (querying prices of subscriptions).");
                    }
                }
            }

            return inv;
        }
        catch (RemoteException e) {
            throw new IabException(IABHELPER_REMOTE_EXCEPTION, "Remote exception while refreshing inventory.", e);
        }
        catch (JSONException e) {
            throw new IabException(IABHELPER_BAD_RESPONSE, "Error parsing JSON response while refreshing inventory.", e);
        }
    }

    public interface QueryInventoryFinishedListener {
        public void onQueryInventoryFinished(IabResult result, Inventory inv);
    }

    public void queryInventoryAsync(final boolean querySkuDetails,
                               final List<String> moreSkus,
                               final QueryInventoryFinishedListener listener) {
        final Handler handler = new Handler();
        checkNotDisposed();
        checkSetupDone("queryInventory");
        flagStartAsync("refresh inventory");
        (new Thread(new Runnable() {
            public void run() {
                IabResult result = new IabResult(BILLING_RESPONSE_RESULT_OK, "Inventory refresh successful.");
                Inventory inv = null;
                try {
                    inv = queryInventory(querySkuDetails, moreSkus);
                }
                catch (IabException ex) {
                    result = ex.getResult();
                }

                flagEndAsync();

                final IabResult result_f = result;
                final Inventory inv_f = inv;
                if (!mDisposed && listener != null) {
                    handler.post(new Runnable() {
                        public void run() {
                            listener.onQueryInventoryFinished(result_f, inv_f);
                        }
                    });
                }
            }
        })).start();
    }

    public void queryInventoryAsync(QueryInventoryFinishedListener listener) {
        queryInventoryAsync(true, null, listener);
    }

    public void queryInventoryAsync(boolean querySkuDetails, QueryInventoryFinishedListener listener) {
        queryInventoryAsync(querySkuDetails, null, listener);
    }

    void consume(Purchase itemInfo) throws IabException {
        checkNotDisposed();
        checkSetupDone("consume");

        if (!itemInfo.mItemType.equals(ITEM_TYPE_INAPP)) {
            throw new IabException(IABHELPER_INVALID_CONSUMPTION,
                    "Items of type '" + itemInfo.mItemType + "' can't be consumed.");
        }

        try {
            String token = itemInfo.getToken();
            String sku = itemInfo.getSku();
            if (token == null || token.equals("")) {
               logError("Can't consume "+ sku + ". No token.");
               throw new IabException(IABHELPER_MISSING_TOKEN, "PurchaseInfo is missing token for sku: "
                   + sku + " " + itemInfo);
            }

            logDebug("Consuming sku: " + sku + ", token: " + token);
            int response = mService.consumePurchase(3, mContext.getPackageName(), token);
            if (response == BILLING_RESPONSE_RESULT_OK) {
               logDebug("Successfully consumed sku: " + sku);
            }
            else {
               logDebug("Error consuming consuming sku " + sku + ". " + getResponseDesc(response));
               throw new IabException(response, "Error consuming sku " + sku);
            }
        }
        catch (RemoteException e) {
            throw new IabException(IABHELPER_REMOTE_EXCEPTION, "Remote exception while consuming. PurchaseInfo: " + itemInfo, e);
        }
    }

    public interface OnConsumeFinishedListener {
        public void onConsumeFinished(Purchase purchase, IabResult result);
    }

    public interface OnConsumeMultiFinishedListener {
        public void onConsumeMultiFinished(List<Purchase> purchases, List<IabResult> results);
    }

    public void consumeAsync(Purchase purchase, OnConsumeFinishedListener listener) {
        checkNotDisposed();
        checkSetupDone("consume");
        List<Purchase> purchases = new ArrayList<Purchase>();
        purchases.add(purchase);
        consumeAsyncInternal(purchases, listener, null);
    }

    public void consumeAsync(List<Purchase> purchases, OnConsumeMultiFinishedListener listener) {
        checkNotDisposed();
        checkSetupDone("consume");
        consumeAsyncInternal(purchases, null, listener);
    }

    public static String getResponseDesc(int code) {
        String[] iab_msgs = ("0:OK/1:User Canceled/2:Unknown/" +
                "3:Billing Unavailable/4:Item unavailable/" +
                "5:Developer Error/6:Error/7:Item Already Owned/" +
                "8:Item not owned").split("/");
        String[] iabhelper_msgs = ("0:OK/-1001:Remote exception during initialization/" +
                                   "-1002:Bad response received/" +
                                   "-1003:Purchase signature verification failed/" +
                                   "-1004:Send intent failed/" +
                                   "-1005:User cancelled/" +
                                   "-1006:Unknown purchase response/" +
                                   "-1007:Missing token/" +
                                   "-1008:Unknown error/" +
                                   "-1009:Subscriptions not available/" +
                                   "-1010:Invalid consumption attempt").split("/");

        if (code <= IABHELPER_ERROR_BASE) {
            int index = IABHELPER_ERROR_BASE - code;
            if (index >= 0 && index < iabhelper_msgs.length) return iabhelper_msgs[index];
            else return String.valueOf(code) + ":Unknown IAB Helper Error";
        }
        else if (code < 0 || code >= iab_msgs.length)
            return String.valueOf(code) + ":Unknown";
        else
            return iab_msgs[code];
    }

    void checkSetupDone(String operation) {
        if (!mSetupDone) {
            logError("Illegal state for operation (" + operation + "): IAB helper is not set up.");
            throw new IllegalStateException("IAB helper is not set up. Can't perform operation: " + operation);
        }
    }

    int getResponseCodeFromBundle(Bundle b) {
        Object o = b.get(RESPONSE_CODE);
        if (o == null) {
            logDebug("Bundle with null response code, assuming OK (known issue)");
            return BILLING_RESPONSE_RESULT_OK;
        }
        else if (o instanceof Integer) return ((Integer)o).intValue();
        else if (o instanceof Long) return (int)((Long)o).longValue();
        else {
            logError("Unexpected type for bundle response code.");
            logError(o.getClass().getName());
            throw new RuntimeException("Unexpected type for bundle response code: " + o.getClass().getName());
        }
    }

    int getResponseCodeFromIntent(Intent i) {
        Object o = i.getExtras().get(RESPONSE_CODE);
        if (o == null) {
            logError("Intent with no response code, assuming OK (known issue)");
            return BILLING_RESPONSE_RESULT_OK;
        }
        else if (o instanceof Integer) return ((Integer)o).intValue();
        else if (o instanceof Long) return (int)((Long)o).longValue();
        else {
            logError("Unexpected type for intent response code.");
            logError(o.getClass().getName());
            throw new RuntimeException("Unexpected type for intent response code: " + o.getClass().getName());
        }
    }

    void flagStartAsync(String operation) {
        if (mAsyncInProgress) throw new IllegalStateException("Can't start async operation (" +
                operation + ") because another async operation(" + mAsyncOperation + ") is in progress.");
        mAsyncOperation = operation;
        mAsyncInProgress = true;
        logDebug("Starting async operation: " + operation);
    }

    void flagEndAsync() {
        logDebug("Ending async operation: " + mAsyncOperation);
        mAsyncOperation = "";
        mAsyncInProgress = false;
    }

    int queryPurchases(Inventory inv, String itemType) throws JSONException, RemoteException {
        logDebug("Querying owned items, item type: " + itemType);
        logDebug("Package name: " + mContext.getPackageName());
        boolean verificationFailed = false;
        String continueToken = null;

        do {
            logDebug("Calling getPurchases with continuation token: " + continueToken);
            Bundle ownedItems = mService.getPurchases(3, mContext.getPackageName(),
                    itemType, continueToken);

            int response = getResponseCodeFromBundle(ownedItems);
            logDebug("Owned items response: " + String.valueOf(response));
            if (response != BILLING_RESPONSE_RESULT_OK) {
                logDebug("getPurchases() failed: " + getResponseDesc(response));
                return response;
            }
            if (!ownedItems.containsKey(RESPONSE_INAPP_ITEM_LIST)
                    || !ownedItems.containsKey(RESPONSE_INAPP_PURCHASE_DATA_LIST)
                    || !ownedItems.containsKey(RESPONSE_INAPP_SIGNATURE_LIST)) {
                logError("Bundle returned from getPurchases() doesn't contain required fields.");
                return IABHELPER_BAD_RESPONSE;
            }

            ArrayList<String> ownedSkus = ownedItems.getStringArrayList(
                        RESPONSE_INAPP_ITEM_LIST);
            ArrayList<String> purchaseDataList = ownedItems.getStringArrayList(
                        RESPONSE_INAPP_PURCHASE_DATA_LIST);
            ArrayList<String> signatureList = ownedItems.getStringArrayList(
                        RESPONSE_INAPP_SIGNATURE_LIST);

            for (int i = 0; i < purchaseDataList.size(); ++i) {
                String purchaseData = purchaseDataList.get(i);
                String signature = signatureList.get(i);
                String sku = ownedSkus.get(i);
                if (Security.verifyPurchase(mSignatureBase64, purchaseData, signature)) {
                    logDebug("Sku is owned: " + sku);
                    Purchase purchase = new Purchase(itemType, purchaseData, signature);

                    if (TextUtils.isEmpty(purchase.getToken())) {
                        logWarn("BUG: empty/null token!");
                        logDebug("Purchase data: " + purchaseData);
                    }

                    inv.addPurchase(purchase);
                }
                else {
                    logWarn("Purchase signature verification **FAILED**. Not adding item.");
                    logDebug("   Purchase data: " + purchaseData);
                    logDebug("   Signature: " + signature);
                    verificationFailed = true;
                }
            }

            continueToken = ownedItems.getString(INAPP_CONTINUATION_TOKEN);
            logDebug("Continuation token: " + continueToken);
        } while (!TextUtils.isEmpty(continueToken));

        return verificationFailed ? IABHELPER_VERIFICATION_FAILED : BILLING_RESPONSE_RESULT_OK;
    }

    int querySkuDetails(String itemType, Inventory inv, List<String> moreSkus)
                                throws RemoteException, JSONException {
        logDebug("Querying SKU details.");
        ArrayList<String> skuList = new ArrayList<String>();
        skuList.addAll(inv.getAllOwnedSkus(itemType));
        if (moreSkus != null) {
            for (String sku : moreSkus) {
                if (!skuList.contains(sku)) {
                    skuList.add(sku);
                }
            }
        }

        if (skuList.size() == 0) {
            logDebug("queryPrices: nothing to do because there are no SKUs.");
            return BILLING_RESPONSE_RESULT_OK;
        }

        Bundle querySkus = new Bundle();
        querySkus.putStringArrayList(GET_SKU_DETAILS_ITEM_LIST, skuList);
        Bundle skuDetails = mService.getSkuDetails(3, mContext.getPackageName(),
                itemType, querySkus);

        if (!skuDetails.containsKey(RESPONSE_GET_SKU_DETAILS_LIST)) {
            int response = getResponseCodeFromBundle(skuDetails);
            if (response != BILLING_RESPONSE_RESULT_OK) {
                logDebug("getSkuDetails() failed: " + getResponseDesc(response));
                return response;
            }
            else {
                logError("getSkuDetails() returned a bundle with neither an error nor a detail list.");
                return IABHELPER_BAD_RESPONSE;
            }
        }

        ArrayList<String> responseList = skuDetails.getStringArrayList(
                RESPONSE_GET_SKU_DETAILS_LIST);

        for (String thisResponse : responseList) {
            SkuDetails d = new SkuDetails(itemType, thisResponse);
            logDebug("Got sku details: " + d);
            inv.addSkuDetails(d);
        }
        return BILLING_RESPONSE_RESULT_OK;
    }

    void consumeAsyncInternal(final List<Purchase> purchases,
                              final OnConsumeFinishedListener singleListener,
                              final OnConsumeMultiFinishedListener multiListener) {
        final Handler handler = new Handler();
        flagStartAsync("consume");
        (new Thread(new Runnable() {
            public void run() {
                final List<IabResult> results = new ArrayList<IabResult>();
                for (Purchase purchase : purchases) {
                    try {
                        consume(purchase);
                        results.add(new IabResult(BILLING_RESPONSE_RESULT_OK, "Successful consume of sku " + purchase.getSku()));
                    }
                    catch (IabException ex) {
                        results.add(ex.getResult());
                    }
                }

                flagEndAsync();
                if (!mDisposed && singleListener != null) {
                    handler.post(new Runnable() {
                        public void run() {
                            singleListener.onConsumeFinished(purchases.get(0), results.get(0));
                        }
                    });
                }
                if (!mDisposed && multiListener != null) {
                    handler.post(new Runnable() {
                        public void run() {
                            multiListener.onConsumeMultiFinished(purchases, results);
                        }
                    });
                }
            }
        })).start();
    }

    void logDebug(String msg) {
        if (mDebugLog) Log.d(mDebugTag, msg);
    }

    void logError(String msg) {
        Log.e(mDebugTag, "In-app billing error: " + msg);
    }

    void logWarn(String msg) {
        Log.w(mDebugTag, "In-app billing warning: " + msg);
    }
}

class IabResult {
    int mResponse;
    String mMessage;

    public IabResult(int response, String message) {
        mResponse = response;
        if (message == null || message.trim().length() == 0) {
            mMessage = IabHelper.getResponseDesc(response);
        }
        else {
            mMessage = message + " (response: " + IabHelper.getResponseDesc(response) + ")";
        }
    }
    public int getResponse() { return mResponse; }
    public String getMessage() { return mMessage; }
    public boolean isSuccess() { return mResponse == IabHelper.BILLING_RESPONSE_RESULT_OK; }
    public boolean isFailure() { return !isSuccess(); }
    public String toString() { return "IabResult: " + getMessage(); }
}

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Inventory {
    Map<String, SkuDetails> mSkuMap = new HashMap<String, SkuDetails>();
    Map<String, Purchase> mPurchaseMap = new HashMap<String, Purchase>();

    Inventory() {
    }

    public SkuDetails getSkuDetails(String sku) {
        return mSkuMap.get(sku);
    }

    public Purchase getPurchase(String sku) {
        return mPurchaseMap.get(sku);
    }

    public boolean hasPurchase(String sku) {
        return mPurchaseMap.containsKey(sku);
    }

    public boolean hasDetails(String sku) {
        return mSkuMap.containsKey(sku);
    }

    public void erasePurchase(String sku) {
        if (mPurchaseMap.containsKey(sku)) mPurchaseMap.remove(sku);
    }

    List<String> getAllOwnedSkus() {
        return new ArrayList<String>(mPurchaseMap.keySet());
    }

    List<String> getAllOwnedSkus(String itemType) {
        List<String> result = new ArrayList<String>();
        for (Purchase p : mPurchaseMap.values()) {
            if (p.getItemType().equals(itemType)) result.add(p.getSku());
        }
        return result;
    }

    public List<Purchase> getAllPurchases() {
        return new ArrayList<Purchase>(mPurchaseMap.values());
    }

    public List<SkuDetails> getAllProducts() {
        return new ArrayList<SkuDetails>(mSkuMap.values());
    }

    void addSkuDetails(SkuDetails d) {
        mSkuMap.put(d.getSku(), d);
    }

    public void addPurchase(Purchase p) {
        mPurchaseMap.put(p.getSku(), p);
    }
}

import org.json.JSONException;
import org.json.JSONObject;

class Purchase {
    String mItemType;
    String mOrderId;
    String mPackageName;
    String mSku;
    long mPurchaseTime;
    int mPurchaseState;
    String mDeveloperPayload;
    String mToken;
    String mOriginalJson;
    String mSignature;

    public Purchase(String itemType, String jsonPurchaseInfo, String signature) throws JSONException {
        mItemType = itemType;
        mOriginalJson = jsonPurchaseInfo;
        JSONObject o = new JSONObject(mOriginalJson);
        mOrderId = o.optString("orderId");
        mPackageName = o.optString("packageName");
        mSku = o.optString("productId");
        mPurchaseTime = o.optLong("purchaseTime");
        mPurchaseState = o.optInt("purchaseState");
        mDeveloperPayload = o.optString("developerPayload");
        mToken = o.optString("token", o.optString("purchaseToken"));
        mSignature = signature;
    }

    public String getItemType() { return mItemType; }
    public String getOrderId() { return mOrderId; }
    public String getPackageName() { return mPackageName; }
    public String getSku() { return mSku; }
    public long getPurchaseTime() { return mPurchaseTime; }
    public int getPurchaseState() { return mPurchaseState; }
    public String getDeveloperPayload() { return mDeveloperPayload; }
    public String getToken() { return mToken; }
    public String getOriginalJson() { return mOriginalJson; }
    public String getSignature() { return mSignature; }

    @Override
    public String toString() { return "PurchaseInfo(type:" + mItemType + "):" + mOriginalJson; }
}

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

class Security {
    private static final String TAG = "IABUtil/Security";

    private static final String KEY_FACTORY_ALGORITHM = "RSA";
    private static final String SIGNATURE_ALGORITHM = "SHA1withRSA";

    public static boolean verifyPurchase(String base64PublicKey, String signedData, String signature) {
        if (TextUtils.isEmpty(signedData) || TextUtils.isEmpty(base64PublicKey) ||
                TextUtils.isEmpty(signature)) {
            Log.e(TAG, "Purchase verification failed: missing data.");
            return false;
        }

        PublicKey key = generatePublicKey(base64PublicKey);
        return verify(key, signedData, signature);
    }

    public static PublicKey generatePublicKey(String encodedPublicKey) {
        try {
            byte[] decodedKey = Base64.decode(encodedPublicKey);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_FACTORY_ALGORITHM);
            return keyFactory.generatePublic(new X509EncodedKeySpec(decodedKey));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            Log.e(TAG, "Invalid key specification.");
            throw new IllegalArgumentException(e);
        } catch (Base64DecoderException e) {
            Log.e(TAG, "Base64 decoding failed.");
            throw new IllegalArgumentException(e);
        }
    }

    public static boolean verify(PublicKey publicKey, String signedData, String signature) {
        Signature sig;
        try {
            sig = Signature.getInstance(SIGNATURE_ALGORITHM);
            sig.initVerify(publicKey);
            sig.update(signedData.getBytes());
            if (!sig.verify(Base64.decode(signature))) {
                Log.e(TAG, "Signature verification failed.");
                return false;
            }
            return true;
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "NoSuchAlgorithmException.");
        } catch (InvalidKeyException e) {
            Log.e(TAG, "Invalid key specification.");
        } catch (SignatureException e) {
            Log.e(TAG, "Signature exception.");
        } catch (Base64DecoderException e) {
            Log.e(TAG, "Base64 decoding failed.");
        }
        return false;
    }
}

import org.json.JSONException;
import org.json.JSONObject;

class SkuDetails {
    String mItemType;
    String mSku;
    String mType;
    String mPrice;
    String mTitle;
    String mDescription;
    String mJson;

    public SkuDetails(String jsonSkuDetails) throws JSONException {
        this(IabHelper.ITEM_TYPE_INAPP, jsonSkuDetails);
    }

    public SkuDetails(String itemType, String jsonSkuDetails) throws JSONException {
        mItemType = itemType;
        mJson = jsonSkuDetails;
        JSONObject o = new JSONObject(mJson);
        mSku = o.optString("productId");
        mType = o.optString("type");
        mPrice = o.optString("price");
        mTitle = o.optString("title");
        mDescription = o.optString("description");
    }

    public String getSku() {
        return mSku;
    }

    public String getType() {
        return mType;
    }

    public String getPrice() {
        return mPrice;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    @Override
    public String toString() {
        return "SkuDetails:" + mJson;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jsonObj = new JSONObject(mJson);
        return jsonObj;
    }
}