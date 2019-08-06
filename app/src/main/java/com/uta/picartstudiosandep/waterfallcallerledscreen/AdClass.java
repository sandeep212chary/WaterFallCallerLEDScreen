package com.uta.picartstudiosandep.waterfallcallerledscreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.mobmatrix.mobmatrixappwall.AppWall.MobMatrixAppWall;

public class AdClass {

    public static String folder_name = "mirror";

    public void moreApp(Activity activity) {
        activity.startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/developer?id=Creative+Infotech+India")));
    }

    public void firstshare(Activity activity, String s, Boolean aBoolean) {
        SharedPreferences yousharp;
        SharedPreferences.Editor youeditor;

        yousharp = activity.getSharedPreferences("weipref", activity.MODE_PRIVATE);
        youeditor = yousharp.edit();

        youeditor.putBoolean(s, aBoolean);
        youeditor.commit();
    }

    public Boolean lastshare(Activity activity, String s) {
        SharedPreferences youyousharf;

        youyousharf = activity.getSharedPreferences("weipref", activity.MODE_PRIVATE);
        return youyousharf.getBoolean(s, true);
    }

    public void sharedPrefepenceAcepting(Activity activity, String key, int value) {

        SharedPreferences pref;
        SharedPreferences.Editor editor;
        pref = activity.getSharedPreferences("MyPref", activity.MODE_PRIVATE);
        editor = pref.edit();

        editor.putInt(key, value);
        editor.commit();

    }


    public int sharedPrefepenceReturning(Activity activity, String key) {

        SharedPreferences pref;
        pref = activity.getSharedPreferences("MyPref", activity.MODE_PRIVATE);

        return pref.getInt(key, 0);
    }


    public void sharedPreferenceAcepting(Activity activity, String key, int value) {

        SharedPreferences pref;
        SharedPreferences.Editor editor;
        pref = activity.getSharedPreferences("MyPref", activity.MODE_PRIVATE);
        editor = pref.edit();

        editor.putInt(key, value);
        editor.commit();

    }

    public int sharedPreferenceReturning(Activity activity, String key) {

        SharedPreferences pref;
        pref = activity.getSharedPreferences("MyPref", activity.MODE_PRIVATE);

        return pref.getInt(key, 0);
    }

    public static String AppId = "ca-app-pub-3940256099942544~3347511713";
    private static String interstitialAd_unit_1 = "ca-app-pub-3940256099942544/1033173712";
    private static String interstitialAd_unit_2 = "ca-app-pub-3940256099942544/1033173712";

    public static InterstitialAd interstitialAd_1,interstitialAd_2;


    public void interstitialAd_1(Context context) {

        MobileAds.initialize(context, AppId);

        interstitialAd_1 = new InterstitialAd(context);
        interstitialAd_1.setAdUnitId(interstitialAd_unit_1);

        if (!interstitialAd_1.isLoaded() && !interstitialAd_1.isLoading()) {
            interstitialAd_1.loadAd(new AdRequest.Builder().build());
        }

    }
    public void interstitialAd_2(Context context) {

        MobileAds.initialize(context, AppId);

        interstitialAd_2 = new InterstitialAd(context);
        interstitialAd_2.setAdUnitId(interstitialAd_unit_2);

        if (!interstitialAd_2.isLoaded() && !interstitialAd_2.isLoading()) {
            interstitialAd_2.loadAd(new AdRequest.Builder().build());
        }

    }

    public static MobMatrixAppWall mobMatrixAppWall1 = new MobMatrixAppWall();
    public static LinearLayout exit = null;


    public static void ExitAppWall(Context activity) {
        mobMatrixAppWall1.MobMatrixExitAppWall(activity, MyPackageName(activity));
        exit = AdClass.mobMatrixAppWall1.layout_recycle(activity);
    }

    static String MyPackageName(Context activity) {

        return "https://play.google.com/store/apps/details?id=" + activity.getApplicationContext().getPackageName();

    }


}
