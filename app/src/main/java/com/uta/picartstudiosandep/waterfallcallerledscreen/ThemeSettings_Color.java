package com.uta.picartstudiosandep.waterfallcallerledscreen;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.uta.picartstudiosandep.waterfallcallerledscreen.utils_color.RoundCorner_color;


public class ThemeSettings_Color extends Activity implements OnCompletionListener, OnClickListener {
    ImageTouchSlider accept;
    ImageTouchSliderendca decline;
    Editor editor;
    TextView nameTV;
    TextView numberTV;
    RoundCorner_color photoIV;
    Button setTheme;
    SharedPreferences sharedPreferences;
    VideoView videoView;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_set_theme);

        //getWindow().setFlags(1024, 1024);
        this.videoView = (VideoView) findViewById(R.id.video);
        this.photoIV = (RoundCorner_color) findViewById(R.id.photoIV);
        this.nameTV = (TextView) findViewById(R.id.nameTV);
        this.numberTV = (TextView) findViewById(R.id.numberTV);
        this.accept = findViewById(R.id.slider);
        this.decline = findViewById(R.id.slidertoend);
        this.setTheme = (Button) findViewById(R.id.setTheme);


        Typeface typeface = Typeface.createFromAsset(getAssets(), "fontface/fontbold.otf");
        this.nameTV.setTypeface(typeface);
        this.numberTV.setTypeface(typeface);
        this.sharedPreferences = getSharedPreferences("MyPrefs", 0);
        this.editor = this.sharedPreferences.edit();
        this.videoView.setVideoPath("android.resource://" + getPackageName() + "/" + ThemeSet_Color.videoFiles[ThemeSet_Color.pos]);
        this.videoView.start();
        this.videoView.setOnCompletionListener(this);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
//        this.accept.startAnimation(animation);
//        this.decline.startAnimation(animation);
//        setLayout();

        this.accept.setOnClickListener(this);
        this.decline.setOnClickListener(this);
        this.setTheme.setOnClickListener(this);
    }



//    void setLayout() {
//        this.photoIV.setLayoutParams(new LayoutParams((getResources().getDisplayMetrics().widthPixels * 324) / 1080, (getResources().getDisplayMetrics().widthPixels * 324) / 1080));
//        ViewGroup.LayoutParams layoutParams = new LayoutParams((getResources().getDisplayMetrics().widthPixels * 218) / 1080, (getResources().getDisplayMetrics().heightPixels * 218) / 1920);
//        this.accept.setLayoutParams(layoutParams);
//        this.decline.setLayoutParams(layoutParams);
//    }

    public void onCompletion(MediaPlayer arg0) {
        this.videoView.start();
    }

    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.decline:
//                onBackPressed();
//                return;
//            case R.id.accept:
//                onBackPressed();
//                return;
            case R.id.setTheme:
                this.editor.putString("videouri", "android.resource://" + getPackageName() + "/" + ThemeSet_Color.videoFiles[ThemeSet_Color.pos]);
                this.editor.commit();
                Toast.makeText(this, "Applied", Toast.LENGTH_SHORT).show();
                finish();
                return;
            default:
                return;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), ThemeSet_Color.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));

    }
}
