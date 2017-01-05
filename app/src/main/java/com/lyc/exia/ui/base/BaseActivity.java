package com.lyc.exia.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.lyc.exia.utils.RxHolder;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by wayne on 2017/1/4.
 */

public abstract class BaseActivity extends AppCompatActivity {

    abstract protected int provideContentViewId();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(provideContentViewId());
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**
         * 防止context泄露
         */
        RxHolder.unSubscribe();
    }

}
