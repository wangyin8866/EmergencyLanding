package com.zyjr.emergencylending.base;

import android.content.Context;

import com.zyjr.emergencylending.model.BaseModel;

import java.lang.ref.WeakReference;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by wy on 2016/9/2.
 */
public abstract class BasePresenter<T> {

    //弱引用,有效防止view内存泄漏
    private WeakReference<T> mViewRef;
    protected Context mContext;
    public String tag = this.getClass().getSimpleName();
    public LifeSubscription lifeSubscription;

    public void setLifeSubscription(LifeSubscription lifeSubscription) {
        this.lifeSubscription = lifeSubscription;
    }

    protected <T> void invoke(Observable<T> observable, Subscriber<T> subscriber) {
        BaseModel.invoke(lifeSubscription, observable, subscriber);
    }

    public BasePresenter() {
        super();
    }

    public BasePresenter(Context context) {
        this.mContext = context;
    }

    //关联
    void attach(T view) {
        mViewRef = new WeakReference<T>(view);

    }

    //解除关联
    void detach() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    public T getView() {
        return mViewRef.get();
    }

}
