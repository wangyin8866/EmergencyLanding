package com.zyjr.emergencylending.ui.home.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.config.Constants;
import com.zyjr.emergencylending.entity.ContactInfoBean;
import com.zyjr.emergencylending.entity.PersonalInfoBean;
import com.zyjr.emergencylending.model.home.loan.ContactInfoModel;
import com.zyjr.emergencylending.model.home.loan.PersonalInfoModel;
import com.zyjr.emergencylending.ui.home.View.ContactInfoView;
import com.zyjr.emergencylending.utils.LogUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by neil on 2017/10/24
 * 备注: 联系人相关
 */
public class ContactInfoPresenter extends BasePresenter<ContactInfoView> {

    public ContactInfoPresenter(Context context) {
        super(context);
    }

    public void getContantInfo(Map<String, String> params) {
        invoke(ContactInfoModel.getInstance().getContactInfo(params), new ProgressSubscriber<ApiResult<List<ContactInfoBean>>>(new SubscriberOnNextListener<ApiResult<List<ContactInfoBean>>>() {
            @Override
            public void onNext(ApiResult<List<ContactInfoBean>> result) {
                if (Config.CODE_SUCCESS.equals(result.getFlag())) {
                    LogUtils.d("获取联系人信息(成功)---->" + result.getMsg());
                    if (result.getResult() != null) {
                        getView().onSuccessGet(Constants.GET_CONTACT_INFO, result.getResult());
                    }
                } else {
                    LogUtils.d("获取联系人信息(失败)---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.GET_CONTACT_INFO, result.getPromptMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("获取联系人信息(异常)---->" + e.getMessage());
                getView().onError(Constants.GET_CONTACT_INFO, Config.TIP_NET_ERROR);
            }
        }, mContext));
    }

    public void addContactInfo(Map<String, String> params) {
        invoke(ContactInfoModel.getInstance().addContactInfo(params), new ProgressSubscriber<ApiResult<List<ContactInfoBean>>>(new SubscriberOnNextListener<ApiResult<List<ContactInfoBean>>>() {
            @Override
            public void onNext(ApiResult<List<ContactInfoBean>> result) {
                if (Config.CODE_SUCCESS.equals(result.getFlag())) {
                    LogUtils.d("添加联系人信息(成功)---->" + result.getMsg());
                    getView().onSuccessAdd(Constants.ADD_CONTACT_INFO, result.getPromptMsg());
                } else {
                    LogUtils.d("添加联系人信息(失败)---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.ADD_CONTACT_INFO, result.getPromptMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("添加联系人信息(异常)---->" + e.getMessage());
                getView().onError(Constants.ADD_CONTACT_INFO, Config.TIP_NET_ERROR);
            }
        }, mContext));
    }

    public void editContactInfo(Map<String, String> params) {
        invoke(ContactInfoModel.getInstance().editContactInfo(params), new ProgressSubscriber<ApiResult<List<ContactInfoBean>>>(new SubscriberOnNextListener<ApiResult<List<ContactInfoBean>>>() {
            @Override
            public void onNext(ApiResult<List<ContactInfoBean>> result) {
                if (Config.CODE_SUCCESS.equals(result.getFlag())) {
                    LogUtils.d("修改联系人信息成功---->" + result.getMsg());
                    getView().onSuccessAdd(Constants.EDIT_CONTACT_INFO, result.getPromptMsg());
                } else {
                    LogUtils.d("修改联系人信息失败---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.EDIT_CONTACT_INFO, result.getPromptMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("修改联系人信息异常---->" + e.getMessage());
                getView().onError(Constants.EDIT_CONTACT_INFO, Config.TIP_NET_ERROR);
            }
        }, mContext));
    }

    public void getPersonalInfo(Map<String, String> params) {
        invoke(PersonalInfoModel.getInstance().getPersonalInfo(params), new ProgressSubscriber<ApiResult<PersonalInfoBean>>(new SubscriberOnNextListener<ApiResult<PersonalInfoBean>>() {
            @Override
            public void onNext(ApiResult<PersonalInfoBean> result) {
                if (Config.CODE_SUCCESS.equals(result.getFlag())) {
                    if (result.getResult() != null) {
                        LogUtils.d("获取个人信息(成功)---->" + result.getMsg());
                        getView().onSuccessGetPersonInfo(Constants.GET_PERSONAL_INFO, result.getResult());
                    }
                } else {
                    LogUtils.d("获取个人信息(失败)---->" + result.getFlag() + "," + result.getMsg());
                    getView().onFail(Constants.GET_PERSONAL_INFO, result.getPromptMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d("获取个人信息(异常)---->" + e.getMessage());
                getView().onError(Constants.GET_PERSONAL_INFO, Config.TIP_NET_ERROR);
            }
        }, mContext));
    }

}
