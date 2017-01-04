package com.lyc.exia.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;

/**
 * Created by wayne on 2017/1/4.
 */

public abstract class ToolBarActivity extends BaseActivity {

    abstract protected int provideContentViewId();

    protected AppBarLayout mAppBar;
    protected Toolbar mToolbar;
    protected boolean mIsHidden = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(provideContentViewId());
    }
}
