package com.zyjr.emergencylending.ui.account.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.base.BaseView;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.entity.account.RegisterBean;
import com.zyjr.emergencylending.model.account.AccountModel;
import com.zyjr.emergencylending.utils.ToastAlone;

/**
 * author wangyin
 * date 2017/10/24.
 * description :
 *
 * @author wangyin
 */

public class RegisterPresenter extends BasePresenter<BaseView<RegisterBean>> {

    public RegisterPresenter(Context context) {
        super(context);
    }

    public void register(String router, String phone, String clientid,
                         String verify_code, String password, String recommend_code,
                         String register_platform, String register_ip, String register_device_no,String phone_equipment) {
        invoke(AccountModel.getInstance().register(router, phone, clientid, verify_code, password, recommend_code, register_ip, register_device_no,phone_equipment), new ProgressSubscriber<RegisterBean>(new SubscriberOnNextListener<RegisterBean>() {
            @Override
            public void onNext(RegisterBean registerBean) {
                if (Config.CODE_SUCCESS.equals(registerBean.getFlag())) {
                    getView().getCommonData(registerBean);
                } else {
                    ToastAlone.showShortToast(mContext, registerBean.getMsg());
                }

            }

            @Override
            public void onError(Throwable e) {
                ToastAlone.showShortToast(mContext, Config.TIP_NET_ERROR);
            }
        }, mContext));
    }

}
