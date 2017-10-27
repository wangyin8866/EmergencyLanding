package com.zyjr.emergencylending.ui.account.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.base.BaseView;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.entity.account.LoginBean;
import com.zyjr.emergencylending.model.account.AccountModel;
import com.zyjr.emergencylending.utils.ToastAlone;

/**
 * author wangyin
 * date 2017/10/24.
 * description :
 *
 * @author wangyin
 */

public class LoginPresenter extends BasePresenter<BaseView<LoginBean>> {

    public void login(String router, String phone, String password,
                      String clientid, String login_ip, String login_platform, String login_device_no) {
        invoke(AccountModel.getInstance().login(router,phone,password,clientid,login_ip,login_device_no), new ProgressSubscriber<LoginBean>(new SubscriberOnNextListener<LoginBean>() {
            @Override
            public void onNext(LoginBean loginBean) {
                getView().callBack(loginBean);
            }

            @Override
            public void onError(Throwable e) {
                ToastAlone.showShortToast(mContext, Config.TIP_NET_ERROR);
            }
        }, mContext));
    }

    public LoginPresenter(Context context) {
        super(context);
    }

}
