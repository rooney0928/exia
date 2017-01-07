package com.lyc.exia.contract;

import com.lyc.exia.base.BaseModel;
import com.lyc.exia.base.BasePresenter;
import com.lyc.exia.base.BaseView;
import com.lyc.exia.bean.DayBean;
import com.lyc.exia.bean.HistoryBean;

/**
 * Created by wayne on 2017/1/5.
 */

public interface MainContract {

    interface View extends BaseView{
        void getDayList(DayBean bean);
        void getDayListError(String error);
        void getMoreDayList(DayBean bean);
        void getMoreDayListError(String error);
        void requestStart();
        void requestEnd();
    }

    interface Presenter extends BasePresenter {
        void getDayList(int size,int page);
        void getMoreDayList(int size,int page);
    }

    interface Model extends BaseModel {
        void getDayList(int size,int page);
        void getMoreDayList(int size,int page);
    }
}
