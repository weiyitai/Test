package com.qianbajin.test;

import android.app.Application;

import com.facebook.stetho.Stetho;
/*
 * @des
 * @Created  Administrator  at 2017/8/12 0012  23:30 
 *
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
