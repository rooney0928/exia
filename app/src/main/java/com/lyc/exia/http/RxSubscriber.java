package com.lyc.exia.http;

import rx.Observer;
import rx.Subscriber;

/**
 * Created by wayne on 2017/1/11.
 */

public abstract class RxSubscriber<T> extends Subscriber<T>{

    @Override
    public void onNext(T t) {
        onOk(t);
        requestEnd();
    }

    @Override
    public void onError(Throwable e) {
        if(e instanceof ApiException){
            onError((ApiException)e);
        }else{
            onError(new ApiException(e,123));
        }
        requestEnd();
    }

    @Override
    public void onCompleted() {

    }

    /**
     * 错误回调
     */
    protected abstract void onError(ApiException ex);
    /**
     * 正确回调
     */
    protected abstract void onOk(T t);

    /**
     * 回调完成
     */
    protected abstract void requestEnd();
}

