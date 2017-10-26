package com.zyjr.emergencylending.ui.account.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.entity.BaseBean;
import com.zyjr.emergencylending.model.account.AccountModel;
import com.zyjr.emergencylending.ui.account.view.RegisterView;

/**
 * author wangyin
 * date 2017/10/24.
 * description :
 *
 * @author wangyin
 */

public class RegisterPresenter extends BasePresenter<RegisterView> {

    public RegisterPresenter(Context context) {
        super(context);
    }

    public void register(String router, String phone, String clientid,
                         String verify_code, String password, String recommend_code,
                         String register_platform, String register_ip, String register_device_no) {
        invoke(AccountModel.getInstance().register(router, phone, clientid, verify_code, password, recommend_code, register_platform, register_ip, register_device_no), new ProgressSubscriber<Object>(new SubscriberOnNextListener<Object>() {
            @Override
            public void onNext(Object registerBean) {
            }

            @Override
            public void onError(Throwable e) {

            }
        }, mContext));
    }

    @Override
    public void overwriteSendSMS(BaseBean baseBean) {
        super.overwriteSendSMS(baseBean);
        getView().getSendSMS(baseBean);
    }
}
