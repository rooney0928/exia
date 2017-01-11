package com.lyc.exia.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lyc.exia.LycFactory;
import com.lyc.exia.bean.DayBean;
import com.lyc.exia.bean.DayListBean;
import com.lyc.exia.bean.HistoryBean;
import com.lyc.exia.bean.Response;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.functions.Func1;
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

    private static class SingletonHolder {
        private static RxHttp rxHttp = new RxHttp();
    }

    private RxHttp() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(12, TimeUnit.SECONDS)
                .readTimeout(12, TimeUnit.SECONDS)
//                .retryOnConnectionFailure(true)// 失败重发
                ;
        if (LycFactory.isDebug) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
        }


        OkHttpClient client = httpClient.build();

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(ROOT)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit gankRest = builder.build();
        gankApi = gankRest.create(GankApi.class);
    }

    public static RxHttp getInstance() {
        return SingletonHolder.rxHttp;
    }

    /**
     * 获取历史发布列表
     *
     * @return
     */
    public static Observable<HistoryBean> getHistory() {
        return gankApi.getHistory();
    }

    /**
     * 获取历史发布列表
     *
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


    private class ServerResponseFunc<T> implements Func1<Response<T>, T> {

        @Override
        public T call(Response<T> reponse) {
            //对返回码进行判断，如果不是0，则证明服务器端返回错误信息了，便根据跟服务器约定好的错误码去解析异常
            if (reponse.error) {
                //如果服务器端有错误信息返回，那么抛出异常，让下面的方法去捕获异常做统一处理
                throw new ServerException(201,"服务器的锅");
            }
            //服务器请求数据成功，返回里面的数据实体
            return reponse.data;
        }
    }
}
