package com.lyc.exia.utils;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by wayne on 2017/1/5.
 */

public class RxHolder {
    private static CompositeSubscription mCompositeSubscription;

    //RXjava注册
    public static void addSubscription(Subscription subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscriber);
    }

    //RXjava取消注册，以避免内存泄露
    public static void unSubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }

}
