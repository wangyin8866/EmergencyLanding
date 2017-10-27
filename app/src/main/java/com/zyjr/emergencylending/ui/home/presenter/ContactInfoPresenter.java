package com.zyjr.emergencylending.ui.home.presenter;

import android.content.Context;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.config.Constants;
import com.zyjr.emergencylending.entity.ContactInfoBean;
import com.zyjr.emergencylending.model.home.loan.ContactInfoModel;
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
                LogUtils.d("获取联系人信息成功---->" + result.getResult().toString());
                getView().onSuccessGet(Constants.GET_CONTACT_INFO, result.getResult());
            }

            @Override
            public void onError(Throwable e) {

            }
        }, mContext));
    }

    public void addContactInfo(Map<String, String> params) {
        invoke(ContactInfoModel.getInstance().addContactInfo(params), new ProgressSubscriber<ApiResult<List<ContactInfoBean>>>(new SubscriberOnNextListener<ApiResult<List<ContactInfoBean>>>() {
            @Override
            public void onNext(ApiResult<List<ContactInfoBean>> result) {
                LogUtils.d("添加联系人信息成功---->" + result.getResult());
                getView().onSuccessAdd(Constants.ADD_CONTACT_INFO, result.getResult());
            }

            @Override
            public void onError(Throwable e) {

            }
        }, mContext));
    }

    public void editContactInfo(Map<String, String> params) {
        invoke(ContactInfoModel.getInstance().editContactInfo(params), new ProgressSubscriber<ApiResult<List<ContactInfoBean>>>(new SubscriberOnNextListener<ApiResult<List<ContactInfoBean>>>() {
            @Override
            public void onNext(ApiResult<List<ContactInfoBean>> result) {
                LogUtils.d("保存联系人信息成功---->" + result.getResult());
            }

            @Override
            public void onError(Throwable e) {

            }
        }, mContext));
    }

    public void submitContacts(Map<String, String> params) {
        invoke(ContactInfoModel.getInstance().submitContacts(params), new ProgressSubscriber<ApiResult<String>>(new SubscriberOnNextListener<ApiResult<String>>() {
            @Override
            public void onNext(ApiResult<String> result) {
                LogUtils.d("上传通讯录信息成功---->" + result.getResult());

            }

            @Override
            public void onError(Throwable e) {

            }
        }, mContext));
    }


}
