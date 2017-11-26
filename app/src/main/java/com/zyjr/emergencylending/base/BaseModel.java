package com.zyjr.emergencylending.base;

import android.content.Intent;
import android.os.Bundle;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zyjr.emergencylending.MainActivity;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.config.Constants;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.ui.account.LoginActivity;
import com.zyjr.emergencylending.ui.my.SettingActivity;
import com.zyjr.emergencylending.ui.salesman.activity.LineMainActivity;
import com.zyjr.emergencylending.utils.LogInterceptor;
import com.zyjr.emergencylending.utils.SPUtils;
import com.zyjr.emergencylending.utils.WYUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
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

    private static final int DEFAULT_TIMEOUT = 60;
    protected Retrofit retrofit;
    private OkHttpClient.Builder httpClientBuilder;

    /**
     * 有公共参数
     */
    protected BaseModel() {
        //手动创建一个OkHttpClient并设置超时时间
        httpClientBuilder = new OkHttpClient.Builder();
        if (WYUtils.isApkInDebug(BaseApplication.getContext())) {
            httpClientBuilder.retryOnConnectionFailure(true).addInterceptor(new AddHeaderInterceptor()).addInterceptor(new LogInterceptor());
        } else {
            httpClientBuilder.addInterceptor(new AddHeaderInterceptor()).addInterceptor(new ResponseInterceptor());
        }
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(NetConstantValues.HOST_URL)
                .build();

    }

    /**
     * 无公共参数
     *
     * @param url
     */
    public BaseModel(String url) {
        //手动创建一个OkHttpClient并设置超时时间
        httpClientBuilder = new OkHttpClient.Builder();
        if (WYUtils.isApkInDebug(BaseApplication.getContext())) {
            httpClientBuilder.retryOnConnectionFailure(true).addInterceptor(new LogInterceptor());
        } else {
            httpClientBuilder.addInterceptor(new AddHeaderInterceptor()).addInterceptor(new ResponseInterceptor());
        }
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(url)
                .build();

    }

    /**
     * 添加公共头信息
     */
    private class AddHeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request().newBuilder()
                    .header("juid", SPUtils.getString(BaseApplication.getContext(), Config.KEY_JUID, ""))
                    .header("register_platform", Constants.getPlatform(1))
                    .header("login_token", SPUtils.getString(BaseApplication.getContext(), Config.KEY_TOKEN, ""))
                    .header("version_no", Constants.getVersionName(BaseApplication.getContext()))
                    .build();
            return chain.proceed(request);
        }
    }

    /**
     * 响应拦截,当终端登录设备发生变更后,跳转到登录页面
     */
    private class ResponseInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            MediaType mediaType = response.body().contentType();
            ResponseBody originalBody = response.body();
            String content = "";
            if (null != originalBody) {
                content = originalBody.string();
            }
            try {
                JsonObject returnData = new JsonParser().parse(content).getAsJsonObject();
                if (returnData != null) {
                    String flag = returnData.get("flag").getAsString();
                    if ("API8888".equals(flag)) {
                        SPUtils.clear(BaseApplication.getContext());
                        Intent intent = new Intent(BaseApplication.context, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Bundle bundle = new Bundle();
                        bundle.putInt("index", 0);
                        intent.putExtras(bundle);
                        BaseApplication.context.startActivity(intent);
                        return response.newBuilder().body(ResponseBody.create(mediaType, content)).build();
                    }
                }
            } catch (Exception e) {
            }
            return chain.proceed(request);
        }
    }

    /**
     * 请求体定制：统一添加定制参数
     */
    private class AddBodyInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder();
            if (original.body() instanceof FormBody) {
                FormBody.Builder newFormBody = new FormBody.Builder();
                FormBody oidFormBody = (FormBody) original.body();
                for (int i = 0; i < oidFormBody.size(); i++) {
                    newFormBody.addEncoded(oidFormBody.encodedName(i), oidFormBody.encodedValue(i));
                }
                newFormBody.add("juid", SPUtils.getString(BaseApplication.getContext(), Config.KEY_JUID, ""));
                newFormBody.add("cust_juid", SPUtils.getString(BaseApplication.getContext(), Config.KEY_JUID, ""));
                newFormBody.add("login_token", SPUtils.getString(BaseApplication.getContext(), Config.KEY_TOKEN, ""));
                newFormBody.add("version_no", Constants.getVersionCode(BaseApplication.getContext()));
                newFormBody.add("register_platform", Constants.getPlatform(1));
                requestBuilder.method(original.method(), newFormBody.build());
            }
            Request request = requestBuilder.build();
            return chain.proceed(request);
        }
    }

    public static <T> void invoke(LifeSubscription lifeSubscription, Observable<T> observable, Subscriber<T> subscriber) {
        Subscription subscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        lifeSubscription.bindSubscription(subscription);
    }
}
