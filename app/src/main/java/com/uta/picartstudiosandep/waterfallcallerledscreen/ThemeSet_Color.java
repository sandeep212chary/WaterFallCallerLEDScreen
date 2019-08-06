package com.uta.picartstudiosandep.waterfallcallerledscreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.MobileAds;

public class ThemeSet_Color extends AppCompatActivity {
    public static int pos;
    public static int[] videoFiles = new int[]{R.raw.screen_1, R.raw.screen_2, R.raw.screen_3, R.raw.screen_4, R.raw.screen_5, R.raw.screen_6, R.raw.screen_7, R.raw.screen_8};
    GridView bgGrid;
    Editor editor;

    SharedPreferences sharedPreferences;


    public View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        return super.onCreateView(view, str, context, attributeSet);
    }

    public View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return super.onCreateView(str, context, attributeSet);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_theme);

        MobileAds.initialize(this, getResources().getString(R.string.appId));
        new NativeBannerIntegrations().nativeAdMobCalled(ThemeSet_Color.this, NativeBannerIntegrations.ad_Banner_unit_3, R.id.adView, R.layout.native_medium_banner, true);


        bgGrid = (GridView) findViewById(R.id.bgGrid);

        this.sharedPreferences = getSharedPreferences("MyPrefs", 0);
        this.editor = this.sharedPreferences.edit();
        this.bgGrid.setAdapter(new VideoAdapter_Color(this, videoFiles));
        this.bgGrid.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                ThemeSet_Color.pos = i;
                ThemeSet_Color.this.startActivity(new Intent(ThemeSet_Color.this, ThemeSettings_Color.class));
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), HomePage_Color.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));

    }


}
