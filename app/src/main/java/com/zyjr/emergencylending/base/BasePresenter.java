package com.zyjr.emergencylending.base;

import android.content.Context;
import android.widget.Button;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.entity.BaseBean;
import com.zyjr.emergencylending.model.account.AccountModel;
import com.zyjr.emergencylending.utils.DateUtil;
import com.zyjr.emergencylending.utils.ToastAlone;

import java.lang.ref.WeakReference;

import rx.Observable;
import rx.Subscriber;

/**
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
     *
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
    public void sendSMS(final Button button, String router, String phone
                       ) {

        invoke(AccountModel.getInstance().sendSMS(router, phone), new ProgressSubscriber<BaseBean>(new SubscriberOnNextListener<BaseBean>() {
            @Override
            public void onNext(BaseBean baseBean) {
                if (baseBean.getFlag().equals(Config.CODE_SUCCESS)) {
                    ToastAlone.showShortToast(mContext, "短信发送成功");
                    button.setEnabled(false);
                    DateUtil.countDown(button, "重新发送");
                } else {
                    ToastAlone.showShortToast(mContext, baseBean.getMsg());
                }
            }
            @Override
            public void onError(Throwable e) {
                ToastAlone.showShortToast(mContext, "短信发送失败，请重试");
            }
        }, mContext));
    }

}
