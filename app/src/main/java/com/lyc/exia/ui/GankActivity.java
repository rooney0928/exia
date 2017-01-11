package com.lyc.exia.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lyc.exia.R;
import com.lyc.exia.adapter.GankAdapter;
import com.lyc.exia.bean.DayBean;
import com.lyc.exia.bean.Gank;
import com.lyc.exia.contract.GankContract;
import com.lyc.exia.presenter.GankPresenter;
import com.lyc.exia.ui.base.BaseActivity;
import com.lyc.exia.utils.ProgressBarUtil;
import com.lyc.exia.utils.ToastUtil;
import com.lyc.exia.view.ScrollLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by wayne on 2017/1/10.
 */

public class GankActivity extends BaseActivity implements GankContract.View {

    private GankPresenter gankPresenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.coll_toolbar)
    CollapsingToolbarLayout coll_toolbar;
    @BindView(R.id.iv_title_img)
    ImageView iv_title_img;
    @BindView(R.id.rv_ganks)
    RecyclerView rv_ganks;
    @BindView(R.id.fab_share)
    FloatingActionButton fab_share;


    private GankAdapter adapter;
    ScrollLinearLayoutManager linearLayoutManager;

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
        adapter = new GankAdapter(this);
        linearLayoutManager = new ScrollLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);

        rv_ganks.setLayoutManager(linearLayoutManager);
        rv_ganks.setHasFixedSize(true);
        rv_ganks.setNestedScrollingEnabled(false);
        rv_ganks.setAdapter(adapter);

        Intent intent = getIntent();
        String date = intent.getStringExtra("date");
        String[] dates = date.split("-");
        gankPresenter.requestDayData(dates[0], dates[1], dates[2]);

        toolbar.setTitle(date);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public void getDayData(DayBean dayBean) {
        //福利部分
        if (dayBean.getResults().benefitList != null && dayBean.getResults().benefitList.size() > 0) {
            Glide.with(this)
                    .load(dayBean.getResults().benefitList.get(0).getUrl())
                    .centerCrop()
                    .into(iv_title_img);
        }

        //代码分享
        List<List<Gank>> ganks = new ArrayList<>();
        ganks.add(dayBean.getResults().iosList);
        ganks.add(dayBean.getResults().androidList);
        ganks.add(dayBean.getResults().relaxList);
        adapter.setList(ganks);
        adapter.notifyDataSetChanged();
        fab_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(Intent.ACTION_SEND);
//                intent.setType("image/*");
//                intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
//                intent.putExtra(Intent.EXTRA_TEXT, "I have successfully share my message through my app");
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(Intent.createChooser(intent, getTitle()));
            }
        });
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
        ProgressBarUtil.showLoadDialog(this);
    }

    @Override
    public void requestEnd() {
        Observable.timer(400, TimeUnit.MILLISECONDS).subscribe(
                new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ProgressBarUtil.hideLoadDialog();
                            }
                        });
                    }
                }
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

}
