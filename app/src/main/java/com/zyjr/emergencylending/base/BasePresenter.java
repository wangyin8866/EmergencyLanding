package com.zyjr.emergencylending.base;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.entity.BaseBean;
import com.zyjr.emergencylending.model.account.AccountModel;

import java.lang.ref.WeakReference;

import rx.Observable;
import rx.Subscriber;

/**
 *
 * @author wy
 * @date 2016/9/2
 */
public abstract class BasePresenter<T> {

    /**
     * 弱引用,有效防止view内存泄漏
     */
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


    /**
     * 关联
     * @param view
     */
    void attach(T view) {
        mViewRef = new WeakReference<T>(view);

    }


    /**
     * 解除关联
     */
    void detach() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    public T getView() {
        return mViewRef.get();
    }


    /**
     * 发送短信验证码
     */
    public void sendSMS( String router, String phone,
                        String registerPlatform, String versionNo) {

        invoke(AccountModel.getInstance().sendSMS(router, phone, registerPlatform, versionNo), new ProgressSubscriber<BaseBean>(new SubscriberOnNextListener<BaseBean>() {
            @Override
            public void onNext(BaseBean baseBean) {
                overwriteSendSMS(baseBean);
            }

            @Override
            public void onError(Throwable e) {
            }
        }, mContext));
    }
    public void overwriteSendSMS(BaseBean baseBean) {

    }
}
