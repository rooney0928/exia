package com.lyc.exia.ui;

import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.lyc.exia.R;
import com.lyc.exia.adapter.DayAdapter;
import com.lyc.exia.bean.DayBean;
import com.lyc.exia.bean.HistoryBean;
import com.lyc.exia.contract.MainContract;
import com.lyc.exia.http.MyCallBack;
import com.lyc.exia.http.RxHttp;
import com.lyc.exia.presenter.MainPresenter;
import com.lyc.exia.ui.base.ToolBarActivity;
import com.lyc.exia.utils.ArrayUtil;
import com.lyc.exia.utils.LogU;
import com.lyc.exia.utils.RxHolder;
import com.lyc.exia.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends ToolBarActivity implements MainContract.View<HistoryBean, HistoryBean> {

    private MainPresenter mainPresenter;

    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipe_layout;
    @BindView(R.id.rv_list)
    RecyclerView rv_list;

    int page1 = 1;
    /**
     * 最后可见条目
     */
    protected int lastVisibleItem;

    List<String> dateList;
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
        setSupportActionBar(toolbar);

        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);

        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
//        GridLayoutManager layoutManager =
//                new GridLayoutManager(this,2);
        rv_list.setLayoutManager(linearLayoutManager);
        rv_list.setAdapter(adapter);

        swipe_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mainPresenter.getServerData();


            }
        });

        rv_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                staggeredGridLayoutManager.invalidateSpanAssignments(); //防止第一行到顶部有空白区域

                if (newState == RecyclerView.SCROLL_STATE_IDLE &&
                        lastVisibleItem == adapter.getItemCount() - 1 && !isRefresh) {
                    LogU.t("加载贡多");

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        mainPresenter.getServerData();
    }

    @Override
    public void getHistory(HistoryBean bean) {
        //刷新列表或初始化
        dateList.clear();
        dateList = bean.getResults();
        adapter.setList(ArrayUtil.getData(dateList, page1, 30));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getHistoryFailed(String e) {
        ToastUtil.showSimpleToast(this, e);
    }


    @Override
    public void updateView(HistoryBean serverData) {

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
            swipe_layout.setRefreshing(false);
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
}
