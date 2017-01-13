package com.lyc.exia.ui;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
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


    private String date;
    private String shareUrl;
    private String shareTitle;

    public static void actionStart(Context context, String date, String title) {
        Intent intent = new Intent(context, GankActivity.class);
        //2017-01-06
        intent.putExtra("date", date);
        intent.putExtra("title", title);
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
        date = intent.getStringExtra("date");
        shareTitle = intent.getStringExtra("title");
        String[] dates = date.split("-");

        shareUrl = "http://gank.io/" + dates[0] + "/" + dates[1] + "/" + dates[2];
        gankPresenter.requestDayData(dates[0], dates[1], dates[2]);

        toolbar.setTitle(date);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public void getDayData(final DayBean dayBean) {
        //福利部分
        if (dayBean.getResults().benefitList != null && dayBean.getResults().benefitList.size() > 0) {
            Glide.with(this)
                    .load(dayBean.getResults().benefitList.get(0).getUrl())
                    .centerCrop()
                    .into(iv_title_img);
            iv_title_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageActivity.actionStart(GankActivity.this, dayBean.getResults().benefitList.get(0).getUrl());
                }
            });
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
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, shareUrl);
                intent.putExtra(Intent.EXTRA_SUBJECT, shareTitle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(intent, "分享到"));
//                openShareDialog();
            }
        });
    }

    private void openShareDialog() {
        final BottomSheetDialog bsd = new BottomSheetDialog(this);
        View view = View.inflate(this, R.layout.share_layout, null);
        View.OnClickListener clickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.ll_share_wechat:
                        break;
                    case R.id.ll_share_friend:
                        break;
                    case R.id.ll_share_qq:
                        break;
                    case R.id.ll_share_browser:
                        break;
                    case R.id.ll_share_copy:
                        break;
                }
                bsd.dismiss();
            }
        };
        view.findViewById(R.id.ll_share_wechat).setOnClickListener(clickListener);
        view.findViewById(R.id.ll_share_friend).setOnClickListener(clickListener);
        view.findViewById(R.id.ll_share_qq).setOnClickListener(clickListener);
        view.findViewById(R.id.ll_share_browser).setOnClickListener(clickListener);
        view.findViewById(R.id.ll_share_copy).setOnClickListener(clickListener);

        bsd.setContentView(view);
        View parent = (View) view.getParent();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
        view.measure(0, 0);
        behavior.setPeekHeight(view.getMeasuredHeight());
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) parent.getLayoutParams();
        params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        parent.setLayoutParams(params);
        bsd.show();
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
