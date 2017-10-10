package com.zyjr.emergencylending.model;


import com.wyman.wangyin.mylibrary.LogInterceptor;
import com.zyjr.emergencylending.base.BaseApplication;
import com.zyjr.emergencylending.base.LifeSubscription;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.WYUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 网络请求基类2.0版本
 * Created by wy on 2016/11/17.
 */

public class BaseModel {

    public static final int DEFAULT_TIMEOUT = 15;
    Retrofit retrofit;
    static Map<String, String> map = new HashMap<>();
    OkHttpClient.Builder httpClientBuilder;

    public BaseModel() {
        //手动创建一个OkHttpClient并设置超时时间
        httpClientBuilder = new OkHttpClient.Builder();
        if (WYUtils.isApkInDebug(BaseApplication.getContext())) {
            httpClientBuilder.retryOnConnectionFailure(true).addInterceptor(new LogInterceptor()).connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS).readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        }else {
            httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        }
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(NetConstantValues.HOST_URL)
                .build();

    }

    //添加线程订阅
    public static <T> void invoke(LifeSubscription lifeSubscription, Observable<T> observable, Subscriber<T> subscriber) {
        LogUtils.e("wyman", map.toString());
        Subscription subscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        lifeSubscription.bindSubscription(subscription);
    }

}
