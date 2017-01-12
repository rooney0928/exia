package com.lyc.exia.ui;

import android.content.Context;
import android.content.Intent;
import android.webkit.WebView;

import com.lyc.exia.R;
import com.lyc.exia.ui.base.ToolBarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wayne on 2017/1/12.
 */

public class WebActivity extends ToolBarActivity{

    public static void actionStart(Context context, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @BindView(R.id.wv_content)
    WebView wv_content;

    String url;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_web;
    }

    @Override
    protected void setView() {
        ButterKnife.bind(this);
        Intent intent = getIntent();

        url = intent.getStringExtra("url");
    }
}
