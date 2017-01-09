package com.lyc.exia.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.lyc.exia.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wayne on 2017/1/4.
 */

public abstract class ToolBarActivity extends BaseActivity {

    abstract protected int provideContentViewId();

    protected abstract void setView();

    @BindView(R.id.appbar)
    protected AppBarLayout appbar;
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(provideContentViewId());
        setView();
    }
}
