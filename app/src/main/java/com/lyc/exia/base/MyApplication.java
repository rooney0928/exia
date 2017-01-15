package com.lyc.exia.base;

import android.app.Application;
import android.content.Context;
import android.widget.TextView;

import com.lyc.exia.LycFactory;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;


/**
 * Created by wayne on 2017/1/5.
 */

public class MyApplication extends Application {
    public static Context context;

    //umeng
    private static final String UMENG_KEY = "587b1c8475ca35392c00169b";
    private static final String UMENG_CHANNEL = "formal";
    private static final boolean debugByUmeng = false;

    //bugly
    private static final String BUGLY_ID = "d67916183f";
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        MobclickAgent.UMAnalyticsConfig umConfig = new MobclickAgent.UMAnalyticsConfig(this, UMENG_KEY, UMENG_CHANNEL
                , MobclickAgent.EScenarioType.E_UM_NORMAL, debugByUmeng);
        MobclickAgent.startWithConfigure(umConfig);
        MobclickAgent.openActivityDurationTrack(false);

        CrashReport.initCrashReport(getApplicationContext(), BUGLY_ID, false);
    }
}
