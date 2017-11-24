package com.xfqz.xjd.mylibrary;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import rx.Subscriber;

public class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {
    private final String TAG = getClass().getSimpleName();
    private SubscriberOnNextListener<T> mSubscriberOnNextListener;
    private ProgressDialogHandler mProgressDialogHandler;

    private Context context;

    public ProgressSubscriber(SubscriberOnNextListener<T> mSubscriberOnNextListener, Context context) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.context = context;
        mProgressDialogHandler = new ProgressDialogHandler(context, this, true);
    }

    private void showProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    @Override
    public void onStart() {
        if (!NetworkUtils.isNetAvailable(context)) {
            Toast.makeText(context, "当前网络不可用，请检查网络情况！", Toast.LENGTH_SHORT).show();
            // 一定好主动调用下面这一句
            onCompleted();
            return;
        }
        showProgressDialog();
    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }

    @Override
    public void onError(Throwable e) {
        Log.e("onError", "ProgressSubscriber.throwable =" + e.toString());
        Log.e("onError", e.getMessage() + "");
        dismissProgressDialog();
        mSubscriberOnNextListener.onError(e);

    }

    @Override
    public void onNext(T t) {
        mSubscriberOnNextListener.onNext(t);
    }

    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
}