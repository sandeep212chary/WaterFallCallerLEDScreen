package com.uta.picartstudiosandep.waterfallcallerledscreen.service;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.uta.picartstudiosandep.waterfallcallerledscreen.ITelephony;

import java.lang.reflect.Method;

public class BroadcastReceiverService extends BroadcastReceiver {
    public static String incomingNumber;
    SharedPreferences preferences;
    private final String TAG = this.getClass().getSimpleName();


    public void onReceive(Context context, Intent intent) {
        this.preferences = context.getSharedPreferences("MyPrefs", 0);
        if (this.preferences.getBoolean("inCall", false) && intent.getExtras() != null) {
            String stringExtra = intent.getStringExtra("state");
            if (stringExtra == null) {
                return;
            }


            if (stringExtra.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
                incomingNumber = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                Log.d(TAG, "Incoming number : " + incomingNumber + stateStr);
                context.startService(new Intent(context, CallService_Theme.class));
            } else if (stringExtra.equals(TelephonyManager.EXTRA_STATE_IDLE) && stringExtra.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                context.stopService(new Intent(context, CallService_Theme.class));
            } else if (stringExtra.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                context.stopService(new Intent(context, CallService_Theme.class));
            }
        }
//        if (intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER) != null) {

//        }

    }

    private void disconnectPhoneItelephony(Context context) {
        ITelephony telephonyService;
        TelephonyManager telephony = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            Class c = Class.forName(telephony.getClass().getName());
            Method m = c.getDeclaredMethod("getITelephony");
            m.setAccessible(true);
//            SessionManager.getInstance(context).setBlockStatusAllow("BLOCKED");
            telephonyService = (ITelephony) m.invoke(telephony);
            telephonyService.silenceRinger();
            telephonyService.endCall();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
