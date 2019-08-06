package com.uta.picartstudiosandep.waterfallcallerledscreen;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.Nullable;

import com.uta.picartstudiosandep.waterfallcallerledscreen.utils_color.RoundCorner_color;


public class PreviewActivity_Color extends Activity implements OnCompletionListener, OnClickListener {
    ImageView accept;
    ImageView decline;
    TextView nameTV;
    TextView numberTV;
    RoundCorner_color photoIV;
    SharedPreferences sharedPreferences;
    VideoView videoView;

    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_preview);

        getWindow().setFlags(1024, 1024);
        this.videoView = (VideoView) findViewById(R.id.video);
        this.photoIV = (RoundCorner_color) findViewById(R.id.photoIV);
        this.nameTV = (TextView) findViewById(R.id.nameTV);
        this.numberTV = (TextView) findViewById(R.id.numberTV);
//        this.accept = (ImageView) findViewById(R.id.accept);
//        this.decline = (ImageView) findViewById(R.id.decline);


        Typeface typeface = Typeface.createFromAsset(getAssets(), "fontface/fontbold.otf");
        this.nameTV.setTypeface(typeface);
        this.numberTV.setTypeface(typeface);
        this.sharedPreferences = getSharedPreferences("MyPrefs", 0);
        String videoUrl = this.sharedPreferences.getString("videouri", null);
        if (videoUrl == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("android.resource://");
            stringBuilder.append(getPackageName());
            stringBuilder.append("/");
            stringBuilder.append(R.raw.screen_1);
            this.videoView.setVideoPath(stringBuilder.toString());
            this.videoView.start();
            this.videoView.setOnCompletionListener(this);
        } else {
            this.videoView.setVideoPath(videoUrl);
            this.videoView.start();
            this.videoView.setOnCompletionListener(this);
        }
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        this.accept.startAnimation(animation);
        this.decline.startAnimation(animation);
        setLayout();


        this.accept.setOnClickListener(this);
        this.decline.setOnClickListener(this);
    }


    void setLayout() {
        this.photoIV.setLayoutParams(new LayoutParams((getResources().getDisplayMetrics().widthPixels * 324) / 1080, (getResources().getDisplayMetrics().widthPixels * 324) / 1080));
        ViewGroup.LayoutParams layoutParams = new LayoutParams((getResources().getDisplayMetrics().widthPixels * 218) / 1080, (getResources().getDisplayMetrics().heightPixels * 218) / 1920);
        this.accept.setLayoutParams(layoutParams);
        this.decline.setLayoutParams(layoutParams);
    }

    public void onCompletion(MediaPlayer arg0) {
        this.videoView.start();
    }

    public void onClick(View arg0) {
        switch (arg0.getId()) {
//            case R.id.decline:
//                onBackPressed();
//                return;
//            case R.id.accept:
//                onBackPressed();
//                return;
            default:
                return;
        }
    }
}
