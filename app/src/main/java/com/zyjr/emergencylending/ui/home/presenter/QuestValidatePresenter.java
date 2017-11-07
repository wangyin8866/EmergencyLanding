package com.zyjr.emergencylending.ui.home.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.Constants;
import com.zyjr.emergencylending.entity.ContactInfoBean;
import com.zyjr.emergencylending.model.home.loan.ContactInfoModel;
import com.zyjr.emergencylending.model.home.loan.QuestValidateModel;
import com.zyjr.emergencylending.ui.home.View.QuestValidateView;
import com.zyjr.emergencylending.utils.LogUtils;

import java.util.List;
import java.util.Map;

/**
 * @author neil
 * @date 2017/11/6
 */
public class QuestValidatePresenter extends BasePresenter<QuestValidateView> {

    public QuestValidatePresenter(Context context) {
        super(context);
    }

    public void getContactInfo(Map<String, String> params) {
        invoke(ContactInfoModel.getInstance().getContactInfo(params), new ProgressSubscriber<ApiResult<List<ContactInfoBean>>>(new SubscriberOnNextListener<ApiResult<List<ContactInfoBean>>>() {
            @Override
            public void onNext(ApiResult<List<ContactInfoBean>> result) {
                if (result.getFlag().equals("API0000")) {
                    LogUtils.d("获取联系人信息成功---->" + result.getResult());
                    if (result.getResult() != null) {
                        getView().onSuccessGetContactInfo(Constants.GET_CONTACT_INFO, result.getResult());
                    }
                } else {
                    LogUtils.d("获取联系人信息失败---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.GET_CONTACT_INFO, result.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("获取联系人信息异常---->" + e.getMessage());
                getView().onError(Constants.GET_CONTACT_INFO, e.getMessage());
            }
        }, mContext));
    }

    public void saveContactsList(Map<String, String> params) {
        invoke(QuestValidateModel.getInstance().saveContactsList(params), new ProgressSubscriber<ApiResult<String>>(new SubscriberOnNextListener<ApiResult<String>>() {
            @Override
            public void onNext(ApiResult<String> result) {
                if (result.getFlag().equals("API0000")) {
                    LogUtils.d("问题验证,保存通讯录信息成功---->" + result.getMsg());
                    getView().onSuccessSubmit(Constants.SAVE_CONTACTS_LIST, result.getResult());
                } else {
                    LogUtils.d("问题验证,保存通讯录信息失败---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.SAVE_CONTACTS_LIST, result.getMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("问题验证,保存通讯录信息异常---->" + e.getMessage());
                getView().onError(Constants.SAVE_CONTACTS_LIST, e.getMessage());
            }
        }, mContext));
    }

}
