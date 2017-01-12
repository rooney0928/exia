package com.lyc.exia.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.lyc.exia.R;
import com.lyc.exia.ui.base.ToolBarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wayne on 2017/1/12.
 */

public class WebActivity extends ToolBarActivity {

    public static void actionStart(Context context, String url, String title) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    @BindView(R.id.pb_bar)
    ProgressBar pb_bar;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipe_layout;
    @BindView(R.id.wv_content)
    WebView wv_content;

    String url;
    String title;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_web;
    }

    @Override
    protected void setView() {
        ButterKnife.bind(this);
        Intent intent = getIntent();

        url = intent.getStringExtra("url");
        title = intent.getStringExtra("title");

        toolbar.setTitle(title);
        toolbar.setTitleTextAppearance(this, R.style.ToolbarTitleText);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        pb_bar.setMax(100);
        wv_content.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if(swipe_layout!=null&&swipe_layout.isRefreshing()){
                    swipe_layout.setRefreshing(false);
                }
            }
        });

        wv_content.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    pb_bar.setVisibility(View.INVISIBLE);
                } else {
                    if (View.INVISIBLE == pb_bar.getVisibility()) {
                        pb_bar.setVisibility(View.VISIBLE);
                    }
                    pb_bar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        swipe_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (wv_content != null) {
                    wv_content.loadUrl(url);
                }
            }
        });

        wv_content.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && wv_content.canGoBack()) {
            wv_content.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
