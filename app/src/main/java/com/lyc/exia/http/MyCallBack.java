package com.lyc.exia.http;

import rx.Observer;
import rx.Subscriber;

/**
 * Created by wayne on 2017/1/5.
 */

public abstract class MyCallBack extends Subscriber {

    private OnServerListener onReturnListener;

    public MyCallBack(OnServerListener onReturnListener) {
        this.onReturnListener = onReturnListener;
    }

    public interface OnServerListener{
        void onStart();
        void onSuccess(Object o);
        void onFailed(Throwable e);
        void onFinish();
    }


    @Override
    public void onStart() {
        super.onStart();
        onReturnListener.onStart();
    }

    @Override
    public void onCompleted() {
        onReturnListener.onFinish();
    }

    @Override
    public void onError(Throwable e) {
        onReturnListener.onFailed(e);
    }

    @Override
    public void onNext(Object o) {
        onReturnListener.onSuccess(o);
    }
}
