package com.lyc.exia.model;

import android.util.Log;

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
        void getHistory(HistoryBean bean);
        void getFailed(String error);
        /**
         * 验证开始
         */
        void requestStart();
        /**
         * 验证结束
         */
        void requestEnd();
    }

    @Override
    public void getServerData() {
        Observable<HistoryBean> request = RxHttp.getHistory();

        mOnReturnDataListener.requestStart();
//        Subscription sub = request.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<HistoryBean>() {
//                    @Override
//                    public void call(HistoryBean historyBean) {
//                        mOnReturnDataListener.getHistory(historyBean);
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        mOnReturnDataListener.getFailed(throwable);
//                    }
//                });

        MyCallBack.OnServerListener listener =  new MyCallBack.OnServerListener(){

            @Override
            public void onStart() {
                mOnReturnDataListener.requestStart();
            }

            @Override
            public void onSuccess(Object o) {
                HistoryBean bean = (HistoryBean) o;
                mOnReturnDataListener.getHistory(bean);
            }

            @Override
            public void onFailed(String e) {
                mOnReturnDataListener.getFailed(e);
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
}
