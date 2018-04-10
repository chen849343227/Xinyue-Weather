package com.chen.xinyueweather.api;

import android.support.annotation.NonNull;

import com.chen.xinyueweather.dao.bean.BaseResponse;
import com.orhanobut.logger.Logger;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

import static com.chen.xinyueweather.api.Params.base;
import static com.chen.xinyueweather.api.Params.location;



/**
 * @author along
 * @date Created:17-11-10
 * @Description
 */
public class RetrofitService {

    private static IAppAction appAction;


    private static IAppAction appAction1;

    private RetrofitService() {
        throw new AssertionError();
    }

    /**
     * 初始化网络通信服务
     */
    public static void init() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .addInterceptor(sLoggingInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(base)
                .build();

        appAction = retrofit.create(IAppAction.class);

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(location)
                .build();

        appAction1 = retrofit.create(IAppAction.class);

    }


    /**
     * 打印返回的json数据拦截器
     */
    private static final Interceptor sLoggingInterceptor = chain -> {
        Request request = chain.request();
        // request.
        Buffer requestBuffer = new Buffer();
        if (request.body() != null) {
            request.body().writeTo(requestBuffer);
        } else {
            Logger.e("LogTAG" + "request.body() == null");
        }
        //打印url信息
        Logger.e(request.url() + (request.body() != null ? "?" + _parseParams(request.body(), requestBuffer) : ""));
        return chain.proceed(request);
    };

    @NonNull
    private static String _parseParams(RequestBody body, Buffer requestBuffer) throws UnsupportedEncodingException {
        if (body.contentType() != null && !body.contentType().toString().contains("multipart")) {
            return URLDecoder.decode(requestBuffer.readUtf8(), "UTF-8");
        }
        return "null";
    }

    /*--------------------------------- API ---------------------------------*/


    /**
     * 获取天气详情
     *
     * @param cityId 城市ID
     * @return
     */
    public static Observable<BaseResponse> getWeatherFromNet(String cityId) {
        return appAction.getWeatherFromNet(cityId);
    }


    public static Observable<ResponseBody> getLocationInfo(String latlng, String language) {
        return appAction1.getAreaFromNet(latlng, language);
    }

}
