package com.lyc.exia.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lyc.exia.LycFactory;
import com.lyc.exia.bean.DayBean;
import com.lyc.exia.bean.DayListBean;
import com.lyc.exia.bean.HistoryBean;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by wayne on 2017/1/5.
 */

public class RxHttp {

    private static final String ROOT = "http://gank.io/api/";

    static GankApi gankApi;
    final static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .serializeNulls()
            .create();


    public RxHttp(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        if (LycFactory.isDebug) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
        }

        httpClient.connectTimeout(12, TimeUnit.SECONDS);
        OkHttpClient client = httpClient.build();

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(ROOT)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit gankRest = builder.build();
        gankApi = gankRest.create(GankApi.class);
    }

    /**
     * 获取历史发布列表
     * @return
     */
    public static Observable<HistoryBean> getHistory() {
        return gankApi.getHistory();
    }

    /**
     * 获取历史发布列表
     * @param size
     * @param page
     * @return
     */
    public static Observable<DayListBean> getDayList(int size, int page) {
        return gankApi.getDayList(size, page);
    }

    /**
     * 获取单日数据
     */
    public static Observable<DayBean> getDayData(String year, String month, String day) {
        return gankApi.getDayData(year, month, day);
    }
}
