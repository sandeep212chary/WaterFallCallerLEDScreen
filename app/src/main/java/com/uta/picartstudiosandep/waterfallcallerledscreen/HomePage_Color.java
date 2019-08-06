package com.uta.picartstudiosandep.waterfallcallerledscreen;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ShareCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.ads.MobileAds;
import com.uta.picartstudiosandep.waterfallcallerledscreen.utils_color.SharedPreferences_color;

import java.util.ArrayList;
import java.util.List;

public class HomePage_Color extends Activity {
    WebView webView;
    AdClass adClass = new AdClass();
    SharedPreferences_color ePref;
    SharedPreferences.Editor editor;
    SharedPreferences sharedpreferences;
    Button theme, preview, privacy, share, more, settings;
    Animation animation;
    ImageView callerIdIV;
    boolean in = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page__color);

        MobileAds.initialize(this, getResources().getString(R.string.appId));

        new NativeBannerIntegrations().nativeAdMobCalled(HomePage_Color.this, NativeBannerIntegrations.ad_Banner_unit_2, R.id.adView, R.layout.native_medium_banner, true);


        this.callerIdIV = (ImageView) findViewById(R.id.callerIdIV);
        webView = findViewById(R.id.webview);
        theme = findViewById(R.id.theme);
        preview = findViewById(R.id.preview);
        privacy = findViewById(R.id.privacy);
        share = findViewById(R.id.share);
        more = findViewById(R.id.more);

        settings = findViewById(R.id.settings);

        sharedpreferences = getSharedPreferences("MyPrefs", 0);
        this.editor = this.sharedpreferences.edit();

        in = this.sharedpreferences.getBoolean("inCall", false);
        if (in) {
            editor.putBoolean("inCall", true);
            callerIdIV.setImageResource(R.drawable.on_switch);
            editor.apply();
        }


        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.READ_PHONE_STATE}, 3);
        }

        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.viewpush);
        if (!(Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners") == null || Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners").contains(getApplicationContext().getPackageName()))) {
            startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
        }
        if (Build.VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(this)) {
            //  Toast.makeText(getApplicationContext(), "Enable Permission and Click Back Button", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION");
            intent.setData(Uri.fromParts("package", getPackageName(), null));
            startActivity(intent);
        }
        this.sharedpreferences = getSharedPreferences("MyPrefs", 0);
        this.editor = this.sharedpreferences.edit();
        if (isFirstTime()) {
            this.editor.putBoolean("inCall", true);
            this.editor.apply();
        }
        if (Build.VERSION.SDK_INT >= 23 && !checkPermission()) {
            requestStoragePermission();
            this.ePref = SharedPreferences_color.getInstance(this);

        }


        callerIdIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                in = sharedpreferences.getBoolean("inCall", true);
                if (in) {
                    editor.putBoolean("inCall", false);
                    callerIdIV.setImageResource(R.drawable.off_switch);
                    // Toast.makeText(this, " Disabled Color Screen!", Toast.LENGTH_SHORT).show();
                } else {
                    editor.putBoolean("inCall", true);
                    callerIdIV.setImageResource(R.drawable.on_switch);
                    // Toast.makeText(this, " Enabled Color Screen!", Toast.LENGTH_SHORT).show();
                }
                editor.commit();
            }
        });
        findViewById(R.id.theme).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theme.startAnimation(animation);
                startActivity(new Intent(getApplicationContext(), ThemeSet_Color.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

            }
        });
        findViewById(R.id.settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings.startAnimation(animation);
                startActivity(new Intent(getApplicationContext(), SettingActivity_Color.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

            }
        });
        findViewById(R.id.privacy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                privacy.startAnimation(animation);
                webView.setVisibility(View.VISIBLE);
                webView.loadUrl("https://sites.google.com/view/utapps");
            }
        });
        findViewById(R.id.preview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preview.startAnimation(animation);
                startActivity(new Intent(getApplicationContext(), previewImages.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

            }
        });


        findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share.startAnimation(animation);
                share();
            }
        });

        findViewById(R.id.more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                more.startAnimation(animation);
                adClass.moreApp(HomePage_Color.this);

            }
        });

    }

    public void share() {

        ShareCompat.IntentBuilder
// getActivity() or activity field if within Fragment
                .from(HomePage_Color.this)
// The text that will be shared
                .setText(" Enjoy & Share this Application \n" + " \n https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())
// most general text sharing MIME type
                .setType("text/plain")
                .setChooserTitle("Via")
                .startChooser();

    }

    private boolean isFirstTime() {
        Boolean firstTime = null;
        if (null == null) {
            SharedPreferences mPreferences = getSharedPreferences("first_time", 0);
            firstTime = Boolean.valueOf(mPreferences.getBoolean("firstTime", true));
            if (firstTime.booleanValue()) {
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.putBoolean("firstTime", false);
                editor.commit();
            }
        }
        return firstTime.booleanValue();
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.READ_PHONE_STATE") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.READ_CONTACTS") == 0) {
            return true;
        }
        return false;
    }

    private boolean requestStoragePermission() {
        int checkSelfPermission = ContextCompat.checkSelfPermission(this, "android.permission.READ_PHONE_STATE");
        int checkSelfPermission2 = ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE");
        int checkSelfPermission3 = ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE");
        int checkSelfPermission4 = ContextCompat.checkSelfPermission(this, "android.permission.READ_CONTACTS");
        List<String> arrayList = new ArrayList();
        if (checkSelfPermission != 0) {
            arrayList.add("android.permission.READ_PHONE_STATE");
        }
        if (checkSelfPermission2 != 0) {
            arrayList.add("android.permission.READ_EXTERNAL_STORAGE");
        }
        if (checkSelfPermission3 != 0) {
            arrayList.add("android.permission.WRITE_EXTERNAL_STORAGE");
        }
        if (checkSelfPermission4 != 0) {
            arrayList.add("android.permission.READ_CONTACTS");
        }
        if (arrayList.isEmpty()) {
            return true;
        }
        ActivityCompat.requestPermissions(this, (String[]) arrayList.toArray(new String[arrayList.size()]), 101);
        return false;
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        if (i != 101) {
            return;
        }
        if (iArr.length <= 0 || iArr[0] != 0) {
            Toast.makeText(this, "please accept the permission in order to use the app.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.getVisibility() == View.VISIBLE) {
            webView.setVisibility(View.INVISIBLE);
            return;
        }
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), ExitFountain.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

    }
}
