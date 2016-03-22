package com.fitta.lightsoo.fitta;

import android.app.Application;
import android.content.Context;

/**
 * Created by LG on 2016-02-20.
 */
public class MyApplication extends Application{
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
    public static Context getContext(){
        return mContext;
    }
}
