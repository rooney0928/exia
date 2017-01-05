package com.lyc.exia.http;

import com.lyc.exia.bean.HistoryBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wayne on 2017/1/5.
 */

public interface GankApi {

    @GET("day/history")
    Observable<HistoryBean> getWeather();
}
