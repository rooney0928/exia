package com.lyc.exia.base;

import android.app.Application;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;
import org.litepal.tablemanager.Connector;

/**
 * Created by wayne on 2017/1/5.
 */

public class MyApplication extends LitePalApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        Connector.getDatabase();
    }
}
