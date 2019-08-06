package com.uta.picartstudiosandep.waterfallcallerledscreen;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

public class About_Fountain extends Activity {

    Button proceed;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutapp);

        MobileAds.initialize(this, getResources().getString(R.string.appId));
        new NativeBannerIntegrations().nativeAdMobCalled(About_Fountain.this, NativeBannerIntegrations.ad_Banner_unit_1, R.id.adView, R.layout.native_medium_banner, true);


        proceed = findViewById(R.id.proceed);
        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.viewpush);
        findViewById(R.id.proceed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceed.startAnimation(animation);
                startActivity(new Intent(getApplicationContext(), HomePage_Color.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));


            }
        });



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 22);
            } else {
                // Proceed as we already have permission.
            }
        } else {
            // Proceed as we need not get the permission
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 22:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //Toast.makeText(context, "All Good", Toast.LENGTH_SHORT).show();
                } else {
                    finish();
                    //Toast.makeText(this, "Need call phone permission", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private boolean checkCallPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            String call = Manifest.permission.CALL_PHONE;
            String outgoing = Manifest.permission.PROCESS_OUTGOING_CALLS;
            String incoming = Manifest.permission.READ_PHONE_STATE;
            int hasCallPermission = checkSelfPermission(call);
            List<String> permissions = new ArrayList<String>();
            if (hasCallPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(call);
                permissions.add(outgoing);
                permissions.add(incoming);
            }
            if (!permissions.isEmpty()) {
                String[] params = permissions.toArray(new String[permissions.size()]);
                requestPermissions(params, 2121);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), HomePage_Color.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));

    }

}
