package com.uta.picartstudiosandep.waterfallcallerledscreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;

public class previewImages extends Activity {
    private static int[] imageIDs = {R.drawable.preview_screen_2};

    ImageSwitcher Switch;
    ImageView images;
    float initialX;
    private int position = 0;
    private int totalimg = imageIDs.length - 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview);
        Switch = findViewById(R.id.imageSwitcher);
        images = findViewById(R.id.imageView1);
        images.setBackgroundResource(imageIDs[position]);

    }

    public boolean onTouchEvent(MotionEvent event) {
// TODO Auto-generated method stub
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initialX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                float finalX = event.getX();
                if (initialX > finalX) {

                    if (position < totalimg) {
                        Switch.showNext();
                        position = position + 1;
                        images.setBackgroundResource(imageIDs[position]);

                    } else {
                        Toast.makeText(getApplicationContext(), "No More Images To Swipe", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    if (position > 0) {

                        Switch.showPrevious();
                        position = position - 1;
                        images.setBackgroundResource(imageIDs[position]);
                    } else {
                        Toast.makeText(getApplicationContext(), "No More Images To Swipe", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
     startActivity(new Intent(getApplicationContext(), HomePage_Color.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));


    }
}
