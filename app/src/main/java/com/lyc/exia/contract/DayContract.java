package com.lyc.exia.contract;

import com.lyc.exia.base.BaseModel;
import com.lyc.exia.base.BasePresenter;
import com.lyc.exia.base.BaseView;
import com.lyc.exia.bean.DayBean;

/**
 * Created by wayne on 2017/1/6.
 */

public interface DayContract {

    interface View extends BaseView {
        void getDayData(DayBean dayBean);
        void getDayDataFailed(String error);
        void requestStart();
        void requestEnd();
    }

    interface Presenter extends BasePresenter {
        void requestDayData(String year,String month,String day);
    }

    interface Model extends BaseModel {
        void requestDayData(String year,String month,String day);
    }
}
