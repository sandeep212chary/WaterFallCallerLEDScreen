package com.uta.picartstudiosandep.waterfallcallerledscreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class VideoAdapter_Color extends BaseAdapter {
    private int[] asset_path;
    private Integer[] imageIds = new Integer[]{Integer.valueOf(R.drawable.screen_1), Integer.valueOf(R.drawable.screen_2), Integer.valueOf(R.drawable.screen_3), Integer.valueOf(R.drawable.screen_4), Integer.valueOf(R.drawable.screen_5), Integer.valueOf(R.drawable.screen_6), Integer.valueOf(R.drawable.screen_7), Integer.valueOf(R.drawable.screen_8), Integer.valueOf(R.drawable.screen_9), Integer.valueOf(R.drawable.screen_10)};
    private Context mContext;

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return 0;
    }

    public VideoAdapter_Color(Context context, int[] arrayList) {
        this.mContext = context;
        this.asset_path = arrayList;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
    }

    public int getCount() {
        return this.asset_path.length;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        @SuppressLint("WrongConstant") LayoutInflater layoutInflater = (LayoutInflater) this.mContext.getSystemService("layout_inflater");
        if (view == null) {
            layoutInflater.inflate(R.layout.row_video_thumb, null);
        }
        view = layoutInflater.inflate(R.layout.row_video_thumb, null);
        ((ImageView) view.findViewById(R.id.gallery_image_preview_item)).setImageResource(this.imageIds[i].intValue());
        return view;
    }
}
