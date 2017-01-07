package com.lyc.exia.model;

import android.util.Log;

import com.lyc.exia.bean.DayBean;
import com.lyc.exia.bean.HistoryBean;
import com.lyc.exia.contract.DayContract;
import com.lyc.exia.http.MyCallBack;
import com.lyc.exia.http.RxHttp;
import com.lyc.exia.utils.LogU;
import com.lyc.exia.utils.RxHolder;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wayne on 2017/1/6.
 */

public class DayModelImpl implements DayContract.Model{

    private OnReturnDataListener mOnReturnDataListener;

    public DayModelImpl(OnReturnDataListener mOnReturnDataListener) {
        this.mOnReturnDataListener = mOnReturnDataListener;
    }

    public interface OnReturnDataListener {
        void getDayData(DayBean bean);
        void getDayDataFailed(String error);
        void requestStart();
        void requestEnd();
    }


    @Override
    public void requestDayData(String year, String month, String day) {
        Observable<DayBean> request = RxHttp.getDayData(year,month,day).cache();
        LogU.e("xxx",month+"-"+day);
        MyCallBack.OnServerListener listener =  new MyCallBack.OnServerListener(){

            @Override
            public void onStart() {
                mOnReturnDataListener.requestStart();
            }

            @Override
            public void onSuccess(Object o) {
                DayBean bean = (DayBean) o;
                mOnReturnDataListener.getDayData(bean);
            }

            @Override
            public void onFailed(String error) {
                mOnReturnDataListener.getDayDataFailed(error);
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
