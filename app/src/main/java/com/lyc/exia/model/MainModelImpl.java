package com.lyc.exia.model;

import android.util.Log;

import com.lyc.exia.bean.DayBean;
import com.lyc.exia.bean.HistoryBean;
import com.lyc.exia.contract.MainContract;
import com.lyc.exia.http.MyCallBack;
import com.lyc.exia.http.RxHttp;
import com.lyc.exia.utils.RxHolder;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by wayne on 2017/1/5.
 */

public class MainModelImpl implements MainContract.Model {

    private OnReturnDataListener mOnReturnDataListener;

    public MainModelImpl(OnReturnDataListener mOnReturnDataListener) {
        this.mOnReturnDataListener = mOnReturnDataListener;
    }
    public interface OnReturnDataListener {
        void getDayList(DayBean bean);
        void getDayListError(String error);
        void requestStart();
        void requestEnd();
    }

    @Override
    public void getDayList(int size, int page) {
        Observable<DayBean> request = RxHttp.getDayList(size,page);

        MyCallBack.OnServerListener listener =  new MyCallBack.OnServerListener(){

            @Override
            public void onStart() {
                mOnReturnDataListener.requestStart();
            }

            @Override
            public void onSuccess(Object o) {
                DayBean bean = (DayBean) o;
                mOnReturnDataListener.getDayList(bean);
            }

            @Override
            public void onFailed(String e) {
                mOnReturnDataListener.getDayListError(e);
            }

            @Override
            public void onFinish() {
                mOnReturnDataListener.requestEnd();
            }
        };
        Subscription sub = request.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyCallBack(listener) {
                });
        RxHolder.addSubscription(sub);
    }



    @Override
    public void getServerData() {

    }
}
