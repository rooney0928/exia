package com.lyc.exia.base;

import android.app.Application;
import android.content.Context;


/**
 * Created by wayne on 2017/1/5.
 */

public class MyApplication extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
