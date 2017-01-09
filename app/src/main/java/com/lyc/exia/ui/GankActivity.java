package com.lyc.exia.ui;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lyc.exia.R;
import com.lyc.exia.bean.BaseBean;
import com.lyc.exia.bean.DayBean;
import com.lyc.exia.contract.GankContract;
import com.lyc.exia.presenter.GankPresenter;
import com.lyc.exia.ui.base.BaseActivity;
import com.lyc.exia.utils.LogU;
import com.lyc.exia.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wayne on 2017/1/10.
 */

public class GankActivity extends BaseActivity implements GankContract.View{

    private GankPresenter gankPresenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.coll_toolbar)
    CollapsingToolbarLayout coll_toolbar;
    @BindView(R.id.iv_title_img)
    ImageView iv_title_img;

    public static void actionStart(Context context, String date) {
        Intent intent = new Intent(context, GankActivity.class);
        //2017-01-06
        intent.putExtra("date", date);
        context.startActivity(intent);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_gank;
    }

    @Override
    protected void setView() {
        ButterKnife.bind(this);
        gankPresenter = new GankPresenter(this);
        Intent intent = getIntent();
        String date = intent.getStringExtra("date");
        String[] dates = date.split("-");
        gankPresenter.requestDayData(dates[0],dates[1],dates[2]);


        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void getDayData(DayBean dayBean) {
        ToastUtil.showSimpleToast(this,dayBean.toString());

        if(dayBean.getResults().benefitList!=null&&dayBean.getResults().benefitList.size()>0){
            Glide.with(this)
                    .load(dayBean.getResults().benefitList.get(0).getUrl())
                    .centerCrop()
                    .into(iv_title_img);
        }
    }

    @Override
    public void getDayDataFailed(String error) {
        ToastUtil.showSimpleToast(this, error);
    }

    @Override
    public void updateView(Object serverData) {

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
