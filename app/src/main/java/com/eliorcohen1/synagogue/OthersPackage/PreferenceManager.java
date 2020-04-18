package com.eliorcohen1.synagogue.OthersPackage;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;
    private int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "intro_slider-welcome";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public PreferenceManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch() {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, false).commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

}
