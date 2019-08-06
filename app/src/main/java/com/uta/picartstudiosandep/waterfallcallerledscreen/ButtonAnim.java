package com.uta.picartstudiosandep.waterfallcallerledscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class ButtonAnim extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_anim);

        ImageTouchSlider anscall = (ImageTouchSlider) findViewById(R.id.slider);
        ImageTouchSliderendca endcall = (ImageTouchSliderendca) findViewById(R.id.slidertoend);
        // Attach listener
        anscall.setOnUnlockListener(new ImageTouchSlider.OnUnlockListener() {
            @Override
            public void onUnlock() {
                Toast.makeText(ButtonAnim.this, "Call Answered", Toast.LENGTH_LONG).show();
            }
        });
        endcall.setOnUnlockListener(new ImageTouchSliderendca.OnUnlockListener() {
            @Override
            public void onUnlock() {
                Toast.makeText(ButtonAnim.this, "Call Ended", Toast.LENGTH_LONG).show();
            }
        });

    }
}
