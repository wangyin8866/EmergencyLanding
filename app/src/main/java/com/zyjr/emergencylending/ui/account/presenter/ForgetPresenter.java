package com.zyjr.emergencylending.ui.account.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.base.BaseView;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.entity.BaseBean;
import com.zyjr.emergencylending.model.account.AccountModel;
import com.zyjr.emergencylending.utils.ToastAlone;

/**
 * author wangyin
 * date 2017/10/24.
 * description :
 *
 * @author wangyin
 */

public class ForgetPresenter extends BasePresenter<BaseView<BaseBean>> {

    public ForgetPresenter(Context context) {
        super(context);
    }

    public void forgetPassword(String router, String phone,
                               String verify_code, String password) {
        invoke(AccountModel.getInstance().forgetPassword(router, phone, verify_code, password), new ProgressSubscriber<BaseBean>(new SubscriberOnNextListener<BaseBean>() {
            @Override
            public void onNext(BaseBean baseBean) {
                if (Config.CODE_SUCCESS.equals(baseBean.getFlag())) {
                    getView().getCommonData(baseBean);
                } else {
                    ToastAlone.showShortToast(mContext, baseBean.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastAlone.showShortToast(mContext, Config.TIP_NET_ERROR);
            }
        }, mContext));
    }

}
