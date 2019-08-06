package com.uta.picartstudiosandep.waterfallcallerledscreen;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.ads.MobileAds;

public class SettingActivity_Color extends AppCompatActivity implements OnClickListener, OnSeekBarChangeListener {
    ImageView callerIdIV;
    private Camera camera;
    Editor editor;
    TextView enableTV;
    TextView flaTV;
    boolean flash = false;
    ImageView flashIV;
    SeekBar flashSeek;
    boolean in = false;
    Parameters params;
    SharedPreferences sharedpreferences;
    TextView speedTV;
    int valueSeek = 0;
    TextView vibTV;
    boolean vibr = false;
    ImageView vibrateIV;


    public View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        return super.onCreateView(view, str, context, attributeSet);
    }

    public View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return super.onCreateView(str, context, attributeSet);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_setting);

        MobileAds.initialize(this, getResources().getString(R.string.appId));
        new NativeBannerIntegrations().nativeAdMobCalled(SettingActivity_Color.this, NativeBannerIntegrations.ad_Banner_unit_1, R.id.adView, R.layout.native_medium_banner, true);


        this.callerIdIV = (ImageView) findViewById(R.id.callerIdIV);
        this.enableTV = (TextView) findViewById(R.id.enableTV);
        this.vibTV = (TextView) findViewById(R.id.vibTV);
        this.flaTV = (TextView) findViewById(R.id.flaTV);
        this.speedTV = (TextView) findViewById(R.id.speedTV);
        this.vibrateIV = (ImageView) findViewById(R.id.vibrateIV);
        this.flashIV = (ImageView) findViewById(R.id.flashIV);
        this.flashSeek = (SeekBar) findViewById(R.id.flashSeek);

        this.sharedpreferences = getSharedPreferences("MyPrefs", 0);
        this.editor = this.sharedpreferences.edit();
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fontface/font.otf");
        this.enableTV.setTypeface(typeface);
        this.vibTV.setTypeface(typeface);
        this.flaTV.setTypeface(typeface);
        this.speedTV.setTypeface(typeface);
        this.in = this.sharedpreferences.getBoolean("inCall", false);
        if (this.in) {
            this.editor.putBoolean("inCall", true);
            this.callerIdIV.setImageResource(R.drawable.on_switch);
            this.editor.apply();
        }
        this.vibr = this.sharedpreferences.getBoolean("vibrate", false);
        if (this.vibr) {
            this.vibrateIV.setImageResource(R.drawable.on_switch);
        }
        this.flash = this.sharedpreferences.getBoolean("flash", false);
        if (this.flash) {
            this.flashIV.setImageResource(R.drawable.on_switch);
        }
        int j = this.sharedpreferences.getInt("seekvlaue", 0);
        int i = j;
        if (j >= 200) {
            i = 200;
        }
        flashSeek.setProgress(i);
        flashSeek.setOnSeekBarChangeListener(this);


        callerIdIV.setOnClickListener(this);
        vibrateIV.setOnClickListener(this);
        flashIV.setOnClickListener(this);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.callerIdIV:
                this.in = this.sharedpreferences.getBoolean("inCall", true);
                if (this.in) {
                    this.editor.putBoolean("inCall", false);
                    this.callerIdIV.setImageResource(R.drawable.off_switch);
                    // Toast.makeText(this, " Disabled Color Screen!", Toast.LENGTH_SHORT).show();
                } else {
                    this.editor.putBoolean("inCall", true);
                    this.callerIdIV.setImageResource(R.drawable.on_switch);
                    // Toast.makeText(this, " Enabled Color Screen!", Toast.LENGTH_SHORT).show();
                }
                this.editor.commit();
                return;
            case R.id.vibrateIV:
                this.vibr = this.sharedpreferences.getBoolean("vibrate", false);
                if (this.vibr) {
                    this.editor.putBoolean("vibrate", false);
                    this.vibrateIV.setImageResource(R.drawable.off_switch);
                    //  Toast.makeText(this, "Vibration Off", Toast.LENGTH_SHORT).show();
                } else {
                    this.editor.putBoolean("vibrate", true);
                    this.vibrateIV.setImageResource(R.drawable.on_switch);
                    // Toast.makeText(this, "Vibration On", Toast.LENGTH_SHORT).show();
                }
                this.editor.commit();
                return;
            case R.id.flashIV:
                if (checkCameraPermission()) {
                    this.flash = this.sharedpreferences.getBoolean("flash", false);
                    if (this.flash) {
                        this.editor.putBoolean("flash", false);
                        this.flashIV.setImageResource(R.drawable.off_switch);
                        // Toast.makeText(this, " Flash light OFF!", Toast.LENGTH_SHORT).show();
                    } else {
                        this.editor.putBoolean("flash", true);
                        getCamera();
                        this.flashIV.setImageResource(R.drawable.on_switch);
                        //Toast.makeText(this, " Flash light ON!", Toast.LENGTH_SHORT).show();
                    }
                    this.editor.commit();
                    return;
                } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.CAMERA")) {
                    ActivityCompat.requestPermissions(this, new String[]{"android.permission.CAMERA"}, 102);
                    return;
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{"android.permission.CAMERA"}, 102);
                    return;
                }
            default:
                return;
        }
    }

    private boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") == 0;
    }

    public void getCamera() {
        releaseCamera();
        if (this.camera == null) {
            try {
                this.camera = Camera.open();
                this.params = this.camera.getParameters();
            } catch (RuntimeException e) {
            }
        }
    }

    public void releaseCamera() {
        if (this.camera != null) {
            this.camera.release();
            this.camera = null;
        }
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        if (i != 101) {
            return;
        }
        if (iArr.length <= 0 || iArr[0] != 0) {
            Toast.makeText(this, "please accept the permission in order to use the app.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onProgressChanged(SeekBar seekBar, int i, boolean arg2) {
        this.valueSeek = i;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(this.valueSeek);
    }

    public void onStartTrackingTouch(SeekBar arg0) {
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        this.editor.putInt("seekvlaue", this.valueSeek);
        this.editor.putInt("flashspeed", 1200 - this.valueSeek);
        this.editor.commit();
    }
}
