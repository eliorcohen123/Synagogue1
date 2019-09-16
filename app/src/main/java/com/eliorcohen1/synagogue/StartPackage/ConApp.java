package com.eliorcohen1.synagogue.StartPackage;

import android.app.Application;
import android.content.Context;

// Summary of getContext
public class ConApp extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;
    }

    public static Context getmContext() {
        return mContext;
    }

}
