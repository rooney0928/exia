package com.lyc.exia.http;

import android.util.Log;

import com.lyc.exia.bean.BaseBean;

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
        void onFailed(String error);
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
        onReturnListener.onFailed(e.getMessage());
    }

    @Override
    public void onNext(Object o) {
        BaseBean baseBean = (BaseBean) o;
        if(!baseBean.isError()){
            onReturnListener.onSuccess(o);
        }
    }
}
