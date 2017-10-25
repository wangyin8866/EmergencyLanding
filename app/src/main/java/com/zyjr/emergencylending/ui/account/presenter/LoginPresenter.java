package com.zyjr.emergencylending.ui.account.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.entity.account.LoginBean;
import com.zyjr.emergencylending.model.account.AccountModel;
import com.zyjr.emergencylending.ui.account.view.LoginView;

/**
 * author wangyin
 * date 2017/10/24.
 * description :
 */

public class LoginPresenter extends BasePresenter<LoginView> {

    public LoginPresenter(Context context) {
        super(context);
    }

    @Override
    public void fetch(String... strings) {
        invoke(AccountModel.getInstance().login(strings[0], strings[1], strings[2], strings[3], strings[4], strings[5]), new ProgressSubscriber<LoginBean>(new SubscriberOnNextListener<LoginBean>() {
            @Override
            public void onNext(LoginBean loginBean) {
                getView().showData(loginBean);
            }

            @Override
            public void onError(Throwable e) {

            }
        }, mContext));
    }




}
