package com.lyc.exia.model;

import com.lyc.exia.bean.DayListBean;
import com.lyc.exia.contract.MainContract;
import com.lyc.exia.http.MyCallBack;
import com.lyc.exia.http.RxHttp;
import com.lyc.exia.utils.RxHolder;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wayne on 2017/1/5.
 */

public class MainModelImpl implements MainContract.Model {
    private static int REFRESH = 0;
    private static int LOAD_MORE = 1;
    private OnReturnDataListener mOnReturnDataListener;

    public MainModelImpl(OnReturnDataListener mOnReturnDataListener) {
        this.mOnReturnDataListener = mOnReturnDataListener;
    }

    public interface OnReturnDataListener {
        void getDayList(DayListBean bean);

        void getDayListError(String error);

        void getMoreDayList(DayListBean bean);

        void getMoreDayListError(String error);

        void requestStart();

        void requestEnd();
    }

    @Override
    public void getDayList(int size, int page) {
        getDayListModel(REFRESH, size, page);
    }

    @Override
    public void getMoreDayList(int size, int page) {
        getDayListModel(LOAD_MORE, size, page);
    }

    public void getDayListModel(final int type, int size, int page) {
        Observable<DayListBean> request = RxHttp.getDayList(size, page);

        MyCallBack.OnServerListener listener = new MyCallBack.OnServerListener() {

            @Override
            public void onStart() {
                mOnReturnDataListener.requestStart();
            }

            @Override
            public void onSuccess(Object o) {
                DayListBean bean = (DayListBean) o;
                if(type==REFRESH){
                    mOnReturnDataListener.getDayList(bean);
                }else{
                    mOnReturnDataListener.getMoreDayList(bean);
                }
            }

            @Override
            public void onFailed(String e) {
                if (type==REFRESH){
                    mOnReturnDataListener.getDayListError(e);
                }else {
                    mOnReturnDataListener.getMoreDayListError(e);
                }
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
