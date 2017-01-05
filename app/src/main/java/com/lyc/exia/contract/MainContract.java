package com.lyc.exia.contract;

import com.lyc.exia.base.BaseModel;
import com.lyc.exia.base.BasePresenter;
import com.lyc.exia.base.BaseView;
import com.lyc.exia.bean.HistoryBean;

/**
 * Created by wayne on 2017/1/5.
 */

public interface MainContract {

    interface View<A,B> extends BaseView<Presenter,HistoryBean> {
        void getHistory(A bean);
        void getHistoryFailed(String error);
        void requestStart();
        void requestEnd();
    }

    interface Presenter extends BasePresenter {
    }

    interface Model extends BaseModel {

    }
}
