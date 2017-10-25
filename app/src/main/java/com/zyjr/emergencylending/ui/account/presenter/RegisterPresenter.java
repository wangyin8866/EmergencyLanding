package com.zyjr.emergencylending.ui.account.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.entity.account.RegisterBean;
import com.zyjr.emergencylending.model.account.AccountModel;
import com.zyjr.emergencylending.ui.account.view.RegisterView;

/**
 * author wangyin
 * date 2017/10/24.
 * description :
 */

public class RegisterPresenter extends BasePresenter<RegisterView> {


    @Override
    public void fetch(String... strings) {
        invoke(AccountModel.getInstance().register(strings[0], strings[1], strings[2], strings[3], strings[4], strings[5], strings[6], strings[7]), new ProgressSubscriber<RegisterBean>(new SubscriberOnNextListener<RegisterBean>() {
            @Override
            public void onNext(RegisterBean registerBean) {
                getView().showData(registerBean);
            }

            @Override
            public void onError(Throwable e) {

            }
        }, mContext));
    }

    public RegisterPresenter(Context context) {
        super(context);
    }



}
