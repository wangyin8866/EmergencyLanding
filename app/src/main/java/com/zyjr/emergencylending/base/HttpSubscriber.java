package com.zyjr.emergencylending.base;

import android.widget.Toast;

import com.xfqz.xjd.mylibrary.NetworkUtils;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.StringUtil;
import com.zyjr.emergencylending.utils.ToastAlone;

import java.net.SocketTimeoutException;

import retrofit2.HttpException;
import rx.Subscriber;

/**
 * Created by neil on 2017/10/20
 * 备注:
 */
public abstract class HttpSubscriber<T> extends Subscriber<T> {

    @Override
    public void onStart() {
        if (!NetworkUtils.isNetAvailable(BaseApplication.getContext())) {
            ToastAlone.showLongToast(BaseApplication.getContext(), "当前网络不可用，请检查网络情况！");
            // 一定好主动调用下面这一句
            onCompleted();
            return;
        }
    }

    @Override
    public void onCompleted() {
        LogUtils.e("HttpSubscriber----onCompleted");
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }


    @Override
    public void onError(Throwable e) {
        onRequestError(e);
//        ToastAlone.showShortToast(BaseApplication.getContext(), e.getMessage());
//        if (e instanceof SocketTimeoutException) {
//            ToastAlone.showShortToast(BaseApplication.getContext(), "请求超时，稍后再试");
//            return;
//        }
//        if (e instanceof HttpException) {
//            String message = ((HttpException) e).message();
//            if (StringUtil.isEmpty(message)) {
//                ToastAlone.showShortToast(BaseApplication.getContext(), "请求超时，稍后再试");
//            } else {
//                LogUtils.e("请求返回信息处理失败:---->" + e.getMessage());
//                ToastAlone.showShortToast(BaseApplication.getContext(), e.getMessage());
//            }
//        }
    }


    public abstract void onSuccess(T t);

    public abstract void onRequestError(Throwable e);
}
