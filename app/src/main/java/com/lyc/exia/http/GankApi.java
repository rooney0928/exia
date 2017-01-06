package com.lyc.exia.http;

import com.lyc.exia.bean.DayBean;
import com.lyc.exia.bean.HistoryBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by wayne on 2017/1/5.
 */

public interface GankApi {

    @GET("day/history")
    Observable<HistoryBean> getWeather();

    @GET("day/{year}/{month}/{day}")
    Observable<DayBean> getDayData(@Path("year") String year,
                                   @Path("month") String month,
                                   @Path("day") String day);

}
