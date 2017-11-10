package com.zyjr.emergencylending.ui.salesman.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.base.BaseView;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.entity.BaseBean;
import com.zyjr.emergencylending.ui.salesman.model.SalesmanModel;
import com.zyjr.emergencylending.utils.ToastAlone;

/**
 * @author wangyin
 * @date 2017/10/31.
 * @description :
 */

public class EditInformationPresenter extends BasePresenter<BaseView<BaseBean>> {
    public EditInformationPresenter(Context context) {
        super(context);
    }

    public void uploadFile(String router, String fileName, String fileExtName, String fileContext, String fileType) {
        invoke(SalesmanModel.getInstance().updatePic(router, fileName, fileExtName, fileContext, fileType), new ProgressSubscriber<BaseBean>(new SubscriberOnNextListener<BaseBean>() {
            @Override
            public void onNext(BaseBean result) {
                if (result.getFlag().equals(Config.CODE_SUCCESS)) {
                    getView().getCommonData(result);
                } else {
                    ToastAlone.showShortToast(mContext, result.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }, mContext));
    }
}
