package com.lyc.exia.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.lyc.exia.R;
import com.lyc.exia.ui.base.ToolBarActivity;
import com.lyc.exia.utils.CommUtil;
import com.lyc.exia.utils.FileUtil;
import com.lyc.exia.utils.LogU;
import com.lyc.exia.utils.ToastUtil;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import rx.functions.Action1;
import uk.co.senab.photoview.PhotoViewAttacher;

import static android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS;

/**
 * Created by wayne on 2017/1/12.
 */

public class ImageActivity extends ToolBarActivity {

    String url;
    PhotoViewAttacher mAttacher;
    @BindView(R.id.iv_image)
    ImageView iv_image;
    @BindView(R.id.fab_download)
    FloatingActionButton fab_download;

    public static void actionStart(Context context, String url) {
        Intent intent = new Intent(context, ImageActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_image;
    }

    @Override
    protected void setView() {
        ButterKnife.bind(this);
        toolbar.setTitle("福利");
        AppBarLayout.LayoutParams mParams = (AppBarLayout.LayoutParams) appbar.getChildAt(0).getLayoutParams();
        mParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);


        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        final View.OnClickListener downListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PermUtil.requestPermission(ImageActivity.this, PermUtil.CODE_WRITE_EXTERNAL_STORAGE, mPermissionGrant);
//                downloadImage();
                requestPerm();

            }
        };
        Glide.with(this).load(url).fitCenter().into(new GlideDrawableImageViewTarget(iv_image) {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                super.onResourceReady(resource, animation);
                iv_image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                mAttacher = new PhotoViewAttacher(iv_image);
                fab_download.setOnClickListener(downListener);
            }
        });


    }

    private void requestPerm() {
        new RxPermissions(ImageActivity.this)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if(aBoolean){
                            downloadImage();
                        }else {
                            showSettingDialog();
                        }
                    }
                });
    }

    private void showSettingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(CommUtil.getResourcesString(R.string.perm_title));
        builder.setMessage(CommUtil.getResourcesString(R.string.perm_setting));
        builder.setCancelable(false);
        builder.setPositiveButton("去设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Uri packageURI = Uri.parse("package:" + "com.lyc.exia");
                Intent intent = new Intent(ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("我知道了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void downloadImage() {
        LogU.t("download");
        if (!TextUtils.isEmpty(url)) {
            OkHttpUtils.get().url(url).build().execute(new FileCallBack(FileUtil.getExiaRoot(), FileUtil.getTime() + ".jpg") {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Snackbar.make(fab_download,"下载出错:"+e.getMessage(),Snackbar.LENGTH_LONG).show();
                }

                @Override
                public void onResponse(File response, int id) {
                    Snackbar.make(fab_download,"已保存在:"+response.getAbsolutePath(),Snackbar.LENGTH_LONG).show();
                }
            });
        } else {
            ToastUtil.showSimpleToast(this, "下载地址出错");
        }
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
