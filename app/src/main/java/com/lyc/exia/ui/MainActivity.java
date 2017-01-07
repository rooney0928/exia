package com.lyc.exia.ui;

import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.lyc.exia.R;
import com.lyc.exia.adapter.DayAdapter;
import com.lyc.exia.bean.DayBean;
import com.lyc.exia.contract.MainContract;
import com.lyc.exia.presenter.MainPresenter;
import com.lyc.exia.ui.base.ToolBarActivity;
import com.lyc.exia.utils.AnimatorUtil;
import com.lyc.exia.utils.CommUtil;
import com.lyc.exia.utils.LogU;
import com.lyc.exia.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.functions.Action1;

public class MainActivity extends ToolBarActivity implements MainContract.View {
    private static int PAGE_SIZE = 15;


    private MainPresenter mainPresenter;

    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab_up)
    FloatingActionButton fab_up;

    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipe_layout;
    @BindView(R.id.rv_list)
    RecyclerView rv_list;

    int page = 1;
    /**
     * 最后可见条目
     */
    protected int lastVisibleItem;

    List<DayBean.ResultsBean> dateList;
    DayAdapter adapter;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    LinearLayoutManager linearLayoutManager;

    private boolean isRefresh;


    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }


    @Override
    protected void setView() {
        mainPresenter = new MainPresenter(this);
        adapter = new DayAdapter(this);
        dateList = new ArrayList<>();

        toolbar.setTitle(R.string.app_name);
        toolbar.setOnClickListener(new View.OnClickListener() {
            long[] mHits = new long[2];
            private final static int N_CLAP_TIME = 2*1000;
            @Override
            public void onClick(View v) {
                System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
                // 将最后一个位置更新为距离开机的时间，如果最后一个时间和最开始时间小于2000，即n击
                mHits[mHits.length - 1] = SystemClock.uptimeMillis();
                if ( N_CLAP_TIME >= (mHits[mHits.length - 1] - mHits[0])  ) {
                    staggeredGridLayoutManager.scrollToPosition(0);
                }
            }
        });
        setSupportActionBar(toolbar);

        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        GridLayoutManager layoutManager =
//                new GridLayoutManager(this,2);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
//        rv_list.setHasFixedSize(true);
        rv_list.setLayoutManager(staggeredGridLayoutManager);
        rv_list.setPadding(0, 0, 0, 0);
        rv_list.setItemAnimator(new DefaultItemAnimator());
        rv_list.setAdapter(adapter);

        swipe_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isRefresh) {
                    return;
                }
                mainPresenter.getDayList(PAGE_SIZE, 0);
            }
        });
        int pink_dark = ContextCompat.getColor(this, R.color.pink_dark);
        swipe_layout.setColorSchemeColors(pink_dark);

        rv_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            //用来标记是否正在向最后一个滑动，既是否向下滑动
            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                staggeredGridLayoutManager.invalidateSpanAssignments();

                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的ItemPosition
                    int[] lastVisiblePositions = staggeredGridLayoutManager
                            .findLastVisibleItemPositions(
                                    new int[staggeredGridLayoutManager.getSpanCount()]);
                    int lastVisiblePos = CommUtil.getMaxElem(lastVisiblePositions);
                    int totalItemCount = staggeredGridLayoutManager.getItemCount();

                    // 判断是否滚动到底部
                    if (lastVisiblePos == (totalItemCount - 1) && isSlidingToLast) {
                        //加载更多功能的代码
                        swipe_layout.setRefreshing(true);
                        mainPresenter.getMoreDayList(PAGE_SIZE, ++page);
                    }
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
                if (dy > 0) {
                    //大于0表示，正在向下滚动
                    isSlidingToLast = true;
                } else {
                    //小于等于0 表示停止或向上滚动
                    isSlidingToLast = false;
                }
            }

        });
        fab_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                staggeredGridLayoutManager.scrollToPosition(0);

            }
        });
        mainPresenter.getDayList(PAGE_SIZE, 0);
    }


    @Override
    public void getDayList(DayBean bean) {
        //刷新
        List<DayBean.ResultsBean> dailyBean = bean.getResults();
        dateList.clear();
        dateList = dailyBean;
        adapter.setList(dateList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getDayListError(String error) {
        ToastUtil.showSimpleToast(this, error);
    }

    @Override
    public void getMoreDayList(DayBean bean) {
        //加载更多
        List<DayBean.ResultsBean> dailyBean = bean.getResults();
        if (dailyBean.size() > 0) {
            dateList.addAll(dailyBean);
            adapter.setList(dateList);
            adapter.notifyDataSetChanged();
        } else {
            ToastUtil.showSimpleToast(this, "没有更多数据啦");
        }
    }

    @Override
    public void getMoreDayListError(String error) {
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
        isRefresh = true;
    }

    @Override
    public void requestEnd() {
        if (swipe_layout != null && swipe_layout.isRefreshing()) {
            //延迟500毫秒关闭swipe
            Observable.timer(500, TimeUnit.MILLISECONDS).subscribe(
                    new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    swipe_layout.setRefreshing(false);
                                }
                            });
                        }
                    }
            );

        }
        isRefresh = false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                ToastUtil.showSimpleToast(this, "setting");
                break;
        }
        return true;
    }

//    private boolean isInitializeFAB = false;

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (!isInitializeFAB) {
//            isInitializeFAB = true;
//            hideFAB();
//        }
//    }


    private void hideFAB() {
        Observable.timer(500, TimeUnit.MILLISECONDS).subscribe(
                new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AnimatorUtil.scaleHide(fab_up, new ViewPropertyAnimatorListener() {
                                    @Override
                                    public void onAnimationStart(View view) {
                                    }

                                    @Override
                                    public void onAnimationEnd(View view) {
                                        fab_up.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onAnimationCancel(View view) {
                                    }
                                });
                            }
                        });
                    }
                }
        );
    }
}
