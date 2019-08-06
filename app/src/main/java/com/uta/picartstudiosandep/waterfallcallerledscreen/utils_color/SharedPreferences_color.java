package com.uta.picartstudiosandep.waterfallcallerledscreen.utils_color;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferences_color {
    private static final int MODE_PRIVATE = 0;
    private static final String PREF_NAME = "myPref";
    private SharedPreferences m_csPref;

    public static SharedPreferences_color getInstance(Context context) {
        return new SharedPreferences_color(context, PREF_NAME, 0);
    }

    private SharedPreferences_color(Context context, String pref_name, int mode) {
        this.m_csPref = context.getSharedPreferences(pref_name, mode);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return this.m_csPref.getBoolean(key, defaultValue);
    }

    public int putBoolean(String key, boolean defaultValue) {
        Editor edit = this.m_csPref.edit();
        edit.putBoolean(key, defaultValue);
        edit.commit();
        return 0;
    }
}
