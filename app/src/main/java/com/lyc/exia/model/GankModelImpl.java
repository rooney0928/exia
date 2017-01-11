package com.lyc.exia.model;

import com.lyc.exia.bean.DayBean;
import com.lyc.exia.contract.GankContract;
import com.lyc.exia.http.ApiException;
import com.lyc.exia.http.MyCallBack;
import com.lyc.exia.http.RxHttp;
import com.lyc.exia.http.RxSubscriber;
import com.lyc.exia.utils.LogU;
import com.lyc.exia.utils.RxHolder;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wayne on 2017/1/6.
 */

public class GankModelImpl implements GankContract.Model {

    private OnReturnDataListener mOnReturnDataListener;

    public GankModelImpl(OnReturnDataListener mOnReturnDataListener) {
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
        Observable<DayBean> request = RxHttp.getInstance().getDayData(year, month, day).cache();
        Subscription sub = request.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxSubscriber<DayBean>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        mOnReturnDataListener.requestStart();
                    }

                    @Override
                    protected void onError(ApiException ex) {
                        mOnReturnDataListener.getDayDataFailed(ex.getMessage());
                    }

                    @Override
                    protected void onOk(DayBean dayBean) {
                        mOnReturnDataListener.getDayData(dayBean);
                    }

                    @Override
                    protected void requestEnd() {
                        mOnReturnDataListener.requestEnd();
                    }
                });
        RxHolder.addSubscription(sub);
    }

    @Override
    public void getServerData() {

    }
}
