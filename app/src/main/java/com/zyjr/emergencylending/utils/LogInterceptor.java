package com.zyjr.emergencylending.utils;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * 采集okhttp3client的日志
 *
 * @author wangyin
 */

public class LogInterceptor implements Interceptor {
    String TAG = "LoggerInterceptor";
    private String content;

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        LogUtils.e(TAG, "request:" + request.url());
//        Headers headers = request.headers();
//        for (int i = 0; i < headers.size(); i++){
//            String headerName = headers.name(i);
//            String headerValue = headers.get(headerName);
//           LogUtils.e(TAG,"Header----------->Name:"+headerName+"------------>Value:"+headerValue+"\n");
//
//        }
        RequestBody requestBody = request.body();
        if (requestBody instanceof FormBody) {
            HashMap<String, Object> rootMap = new HashMap<>();
            for (int i = 0; i < ((FormBody) requestBody).size(); i++) {
                rootMap.put(((FormBody) requestBody).encodedName(i), getValueDecode(((FormBody) requestBody).encodedValue(i)));
            }
            LogUtils.e(TAG, "params : " + new Gson().toJson(rootMap));
        }
        long t1 = System.nanoTime();
        okhttp3.Response response = chain.proceed(chain.request());
        okhttp3.MediaType mediaType = response.body().contentType();
        ResponseBody originalBody = response.body();
        if (null != originalBody) {
            content = originalBody.string();
        }
        LogUtils.e(TAG, "response body:" + content);
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - t1);
        LogUtils.e(TAG, "time : " + " (" + tookMs + "ms" + ')');
        return response.newBuilder()
                .body(ResponseBody.create(mediaType, content))
                .build();
    }

    /**
     * 解决中文乱码结果集
     * @param value
     * @return
     */
    private static String getValueDecode(String value) {
        try {
            return URLDecoder.decode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return value;
    }
}