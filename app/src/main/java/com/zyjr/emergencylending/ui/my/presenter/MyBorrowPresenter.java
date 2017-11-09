package com.zyjr.emergencylending.ui.my.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.base.BaseView;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.entity.MyBorrow;
import com.zyjr.emergencylending.model.account.AccountModel;
import com.zyjr.emergencylending.utils.ToastAlone;

/**
 * @author wangyin
 * @date 2017/11/6.
 * @description :
 */

public class MyBorrowPresenter extends BasePresenter<BaseView<MyBorrow>> {
    public MyBorrowPresenter(Context context) {
        super(context);
    }

    public void getData(String router, String pageNo, String pageSize) {
        invoke(AccountModel.getInstance().myBorrow(router, pageNo, pageSize), new ProgressSubscriber<MyBorrow>(new SubscriberOnNextListener<MyBorrow>() {
            @Override
            public void onNext(MyBorrow baseBean) {
                if (baseBean.getFlag().equals(Config.CODE_SUCCESS)) {
                            getView().callBack(baseBean);
                } else {
                    ToastAlone.showShortToast(mContext, baseBean.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }, mContext));
    }
}
