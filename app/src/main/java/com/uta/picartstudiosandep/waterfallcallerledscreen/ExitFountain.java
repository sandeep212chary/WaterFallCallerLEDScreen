package com.uta.picartstudiosandep.waterfallcallerledscreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class ExitFountain extends Activity {

    AdClass adsManager = new AdClass();
    LinearLayout layout_ad, strip_ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exit);

        if (adsManager.sharedPrefepenceReturning(ExitFountain.this, "onetime") == 1) {
            adsManager.sharedPrefepenceAcepting(ExitFountain.this, "onetime", 2);
            startActivity(new Intent(getApplicationContext(), About_Fountain.class));

        }


        layout_ad = (LinearLayout) findViewById(R.id.recyclerView_layout);
        if (AppStatus.getInstance(ExitFountain.this).isOnline()) {


            if (AppStatus.getInstance(this).isOnline()) {
                try {
                    if (AdClass.exit != null)
                        layout_ad.addView(AdClass.exit);

                } catch (Exception e) {

                }
            }


        } else {
            LinearLayout layout_ad;
            layout_ad = findViewById(R.id.recyclerView_layout);
            layout_ad.setVisibility(View.GONE);
        }

        findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), About_Fountain.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (AdClass.exit != null)
                layout_ad.removeAllViews();

        } catch (Exception e) {

        }

    }


    @Override
    public void onBackPressed() {

    }
}
