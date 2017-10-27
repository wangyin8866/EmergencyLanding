package com.zyjr.emergencylending.base;

import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.config.Constants;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.utils.BasicParamsInterceptor;
import com.zyjr.emergencylending.utils.LogInterceptor;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.SPUtils;
import com.zyjr.emergencylending.utils.WYUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
 *
 * @author wangyin
 * @date 2016/11/17
 */

public class BaseModel {

    private static final int DEFAULT_TIMEOUT = 15;
    protected Retrofit retrofit;
    protected static Map<String, String> map = new HashMap<>();
    private OkHttpClient.Builder httpClientBuilder;

    protected BaseModel() {
        //手动创建一个OkHttpClient并设置超时时间
        httpClientBuilder = new OkHttpClient.Builder();
        if (WYUtils.isApkInDebug(BaseApplication.getContext())) {
            httpClientBuilder.retryOnConnectionFailure(true).addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder();
                    //请求体定制：统一添加定制参数
                    if(original.body() instanceof FormBody){
                        FormBody.Builder newFormBody = new FormBody.Builder();
                        FormBody oidFormBody = (FormBody) original.body();
                        for (int i = 0;i<oidFormBody.size();i++){
                            newFormBody.addEncoded(oidFormBody.encodedName(i),oidFormBody.encodedValue(i));
                        }
                        newFormBody.add("juid",SPUtils.getString(BaseApplication.getContext(), Config.KEY_JUID, "e517fafd0d4a4034b4a88a6a1e041540"));
                        newFormBody.add("login_token",SPUtils.getString(BaseApplication.getContext(), Config.KEY_TOKEN, "login_token"));
                        newFormBody.add("version_no", Constants.getVersionCode(BaseApplication.getContext()));
                        newFormBody.add("register_platform",Constants.getPlatform(1));
                        requestBuilder.method(original.method(),newFormBody.build());
                    }
                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            }).addInterceptor(new LogInterceptor()).connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        } else {
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


    public BaseModel(String url) {

        //手动创建一个OkHttpClient并设置超时时间
        httpClientBuilder = new OkHttpClient.Builder();
        if (WYUtils.isApkInDebug(BaseApplication.getContext())) {
            httpClientBuilder.retryOnConnectionFailure(true).addInterceptor(new LogInterceptor()).connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        } else {
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
