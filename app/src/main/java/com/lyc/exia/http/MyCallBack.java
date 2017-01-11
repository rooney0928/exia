package com.lyc.exia.http;

import com.lyc.exia.bean.Response;
import com.lyc.exia.utils.LogU;

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
//        onReturnListener.onFinish();
    }

    @Override
    public void onError(Throwable e) {
        LogU.t("error?--"+e);
        onReturnListener.onFailed(e.getMessage());
        onReturnListener.onFinish();
    }

    @Override
    public void onNext(Object o) {
        Response response = (Response) o;
        LogU.t("ok--"+ response.error);
        if(!response.error){
            onReturnListener.onSuccess(o);
        }
        onReturnListener.onFinish();
    }
}
