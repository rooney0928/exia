package com.lyc.exia.presenter;

import android.util.Log;

import com.lyc.exia.bean.DayBean;
import com.lyc.exia.contract.DayContract;
import com.lyc.exia.model.DayModelImpl;

/**
 * Created by wayne on 2017/1/6.
 */

public class DayPresenter implements DayContract.Presenter {
    private DayContract.View view;
    private DayContract.Model model;

    public DayPresenter(DayContract.View iview) {
        this.view = iview;

        model = new DayModelImpl(new DayModelImpl.OnReturnDataListener() {
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
