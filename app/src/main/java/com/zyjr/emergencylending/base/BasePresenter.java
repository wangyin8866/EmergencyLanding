package com.zyjr.emergencylending.base;

import android.content.Context;
import android.widget.Button;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.entity.BaseBean;
import com.zyjr.emergencylending.entity.H5Bean;
import com.zyjr.emergencylending.model.account.AccountModel;
import com.zyjr.emergencylending.ui.h5.H5WebView;
import com.zyjr.emergencylending.ui.salesman.activity.ApplyActivity;
import com.zyjr.emergencylending.ui.salesman.activity.MyCardActivity;
import com.zyjr.emergencylending.utils.DateUtil;
import com.zyjr.emergencylending.utils.SPUtils;
import com.zyjr.emergencylending.utils.ToastAlone;

import java.lang.ref.WeakReference;

import rx.Observable;
import rx.Subscriber;

/**
 * @author wy
 * @date 2016/9/2
 */
public class BasePresenter<T> {

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

    protected<T>  void invokeMerge(Subscriber<T> subscriber, Observable... observables) {
        BaseModel.invokeMerge(lifeSubscription, subscriber, observables);
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
                    ToastAlone.showShortToast(mContext, baseBean.getPromptMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastAlone.showShortToast(mContext, "短信发送失败，请重试");
            }
        }, mContext));
    }

    /**
     * h5
     */
    public void getH5Url(final String url_type, final String title
    ) {
        invoke(AccountModel.getInstance().getH5Url(url_type), new ProgressSubscriber<H5Bean>(new SubscriberOnNextListener<H5Bean>() {
            @Override
            public void onNext(H5Bean baseBean) {
                if (baseBean.getFlag().equals(Config.CODE_SUCCESS)) {
                    if (url_type.equals(Config.H5_URL_REPAYMENT)) {
                        H5WebView.skipH5WebView(mContext, title, baseBean.getResult().getH5_url() + "?login_token=" + SPUtils.getString(mContext, Config.KEY_REPAYMENT_TOKEN, "") + "&page=1");
                    } else if (url_type.equals(Config.H5_URL_MYRESULTS_CUSTOMER)) {
                        H5WebView.skipH5WebView(mContext, title, baseBean.getResult().getH5_url() + "?login_token=" + SPUtils.getString(mContext, Config.KEY_TOKEN, "")
                                + "&juid=" + SPUtils.getString(mContext, Config.KEY_JUID, "") + "&type=" + SPUtils.getInt(mContext, Config.KEY_TYPE, 1));
                    } else if (url_type.equals(Config.H5_URL_MYRESULTS_APPLY)) {
                        ApplyActivity.skipH5WebView(mContext, baseBean.getResult().getH5_url() + "?login_token=" + SPUtils.getString(mContext, Config.KEY_TOKEN, "")
                                + "&juid=" + SPUtils.getString(mContext, Config.KEY_JUID, "") + "&type=" + SPUtils.getInt(mContext, Config.KEY_TYPE, 1));
                    } else if (url_type.equals(Config.H5_URL_MYRESULTS_SUCCESS)) {
                        H5WebView.skipH5WebView(mContext, title, baseBean.getResult().getH5_url() + "?login_token=" + SPUtils.getString(mContext, Config.KEY_TOKEN, "")
                                + "&juid=" + SPUtils.getString(mContext, Config.KEY_JUID, "") + "&type=" + SPUtils.getInt(mContext, Config.KEY_TYPE, 1));
                    } else if (url_type.equals(Config.H5_URL_MYCARD)) {
                        MyCardActivity.skipH5WebView(mContext, baseBean.getResult().getH5_url() + "?login_token=" + SPUtils.getString(mContext, Config.KEY_TOKEN, "")
                                + "&juid=" + SPUtils.getString(mContext, Config.KEY_JUID, ""));
                    } else {
                        H5WebView.skipH5WebView(mContext, title, baseBean.getResult().getH5_url() + "?login_token=" + SPUtils.getString(mContext, Config.KEY_TOKEN, "")
                                + "&juid=" + SPUtils.getString(mContext, Config.KEY_JUID, ""));
                    }
                } else {
                    ToastAlone.showShortToast(mContext, baseBean.getPromptMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastAlone.showShortToast(mContext, Config.TIP_NET_ERROR);
            }
        }, mContext));
    }
}
