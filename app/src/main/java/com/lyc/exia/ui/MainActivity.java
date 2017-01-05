package com.lyc.exia.ui;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.lyc.exia.R;
import com.lyc.exia.bean.HistoryBean;
import com.lyc.exia.contract.MainContract;
import com.lyc.exia.presenter.MainPresenter;
import com.lyc.exia.ui.base.ToolBarActivity;

public class MainActivity extends ToolBarActivity implements MainContract.View<HistoryBean,HistoryBean>{

    private MainPresenter mainPresenter;
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }


    @Override
    protected void setView() {
        mainPresenter = new MainPresenter(this);
        mainPresenter.getServerData();

        Throwable e= new Throwable();
        Log.e("test","eeee--"+e.getMessage());
    }


    @Override
    public void getHistory(HistoryBean bean) {
        Log.e("test",bean.isError()+"");
        Log.e("test",bean.getResults().size()+"");
    }

    @Override
    public void getHistoryFailed(String e) {
        Log.e("test",e);
    }


    @Override
    public void setPresenter(MainContract.Presenter presenter) {

    }

    @Override
    public void updateView(HistoryBean serverData) {

    }

    @Override
    public void updateError(String error) {

    }

    @Override
    public void requestStart() {

    }

    @Override
    public void requestEnd() {

    }
}
