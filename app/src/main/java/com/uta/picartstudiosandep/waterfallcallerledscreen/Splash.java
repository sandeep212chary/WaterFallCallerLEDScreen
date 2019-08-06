package com.uta.picartstudiosandep.waterfallcallerledscreen;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;

public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);


        MobileAds.initialize(this, getResources().getString(R.string.appId));

        new AdClass().interstitialAd_1(Splash.this);

        new AdClass().interstitialAd_2(Splash.this);

        runOnUiThread(new Thread(new Runnable() {
            @Override
            public void run() {
                if (AppStatus.getInstance(Splash.this).isOnline()) {


                    AdClass.ExitAppWall(Splash.this);
                }
            }
        }));
        new LongOperation().execute();

        AdClass.interstitialAd_1.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                new AdClass().sharedPrefepenceAcepting(Splash.this, "onetime", 1);
                AdClass.interstitialAd_1.loadAd(new AdRequest.Builder().build());
                startActivity(new Intent(getApplicationContext(), ExitFountain.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }

        });


    }

    private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(850);
                } catch (InterruptedException e) {
                    Thread.interrupted();
                }
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            if (AdClass.interstitialAd_1.isLoaded() && AdClass.interstitialAd_1 != null) {
                AdClass.interstitialAd_1.show();

            } else {
                new AdClass().sharedPrefepenceAcepting(Splash.this, "onetime", 1);
                startActivity(new Intent(getApplicationContext(), ExitFountain.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                finish();
            }


        }
    }


}
