package com.lyc.exia.http;

import com.lyc.exia.bean.Gank;
import com.lyc.exia.bean.HistoryBean;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by wayne on 2017/1/5.
 */

public class RxHttp {

    private static final String ROOT = "http://gank.io/api/";

    private static final Retrofit sRetrofit = new Retrofit .Builder()
            .baseUrl(ROOT)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 使用RxJava作为回调适配器
            .build();
    private static final GankApi gankApi = sRetrofit.create(GankApi.class);
    /**
     * 获取历史发布列表
     * @return
     */
    public static Observable<HistoryBean> getHistory() {
        return gankApi.getWeather();
    }
}
