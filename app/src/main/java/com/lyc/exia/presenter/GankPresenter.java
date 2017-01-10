package com.lyc.exia.presenter;

import com.lyc.exia.bean.DayBean;
import com.lyc.exia.contract.GankContract;
import com.lyc.exia.model.GankModelImpl;
import com.lyc.exia.utils.LogU;

/**
 * Created by wayne on 2017/1/6.
 */

public class GankPresenter implements GankContract.Presenter {
    private GankContract.View view;
    private GankContract.Model model;

    public GankPresenter(GankContract.View iview) {
        this.view = iview;

        model = new GankModelImpl(new GankModelImpl.OnReturnDataListener() {
            @Override
            public void getDayData(DayBean bean) {
                view.getDayData(bean);
            }

            @Override
            public void getDayDataFailed(String error) {
                view.getDayDataFailed(error);
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
    public void requestDayData(String year, String month, String day) {
        model.requestDayData(year,month,day);
    }

    @Override
    public void getServerData() {

    }
}
