package com.zyjr.emergencylending.utils;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.ResponseBody;

/**
 * 采集okhttp3client的日志
 * Created by NIUDEYANG on 2016/11/18.
 */

public class LogInterceptor implements Interceptor {
    String TAG = "LoggerInterceptor";
    private String content;

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        LogUtils.d(TAG, "request:" + request.toString());
        Headers headers = request.headers();
        if (headers != null && headers.size() > 0) {
            LogUtils.d(TAG, "headers : " + headers.toString());
        }
        long t1 = System.nanoTime();
        okhttp3.Response response = chain.proceed(chain.request());
//        long t2 = System.nanoTime();
//        LogUtils.v(TAG, String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        okhttp3.MediaType mediaType = response.body().contentType();
        ResponseBody originalBody = response.body();
        if (null != originalBody) {
            content = originalBody.string();
        }
        LogUtils.d(TAG, "response body:" + content);
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - t1);
        LogUtils.d(TAG, "time : " + " (" + tookMs + "ms" + ')');
        return response.newBuilder()
                .body(ResponseBody.create(mediaType, content))
                .build();
    }
}