package com.lyc.exia.presenter;

import com.lyc.exia.bean.DayBean;
import com.lyc.exia.bean.HistoryBean;
import com.lyc.exia.contract.MainContract;
import com.lyc.exia.model.MainModelImpl;

/**
 * Created by wayne on 2017/1/5.
 */

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View view;
    private MainContract.Model model;

    public MainPresenter(MainContract.View iview) {
        this.view = iview;
        model = new MainModelImpl(new MainModelImpl.OnReturnDataListener() {


            @Override
            public void getDayList(DayBean bean) {
                view.getDayList(bean);
            }

            @Override
            public void getDayListError(String error) {
                view.getDayListError(error);
            }

            @Override
            public void requestStart() {
                view.requestStart();
            }

            @Override
            public void requestEnd() {
                view.requestEnd();
            }
        });
    }

    @Override
    public void getServerData() {
        model.getServerData();
    }

    @Override
    public void getDayList(int size, int page) {
        model.getDayList(size, page);
    }
}
