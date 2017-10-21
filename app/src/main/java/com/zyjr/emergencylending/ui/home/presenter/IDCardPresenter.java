package com.zyjr.emergencylending.ui.home.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.base.HttpSubscriber;
import com.zyjr.emergencylending.entity.IDCardBackBean;
import com.zyjr.emergencylending.entity.IDCardFrontBean;
import com.zyjr.emergencylending.model.home.loan.IDCardModel;
import com.zyjr.emergencylending.ui.home.View.IDCardView;
import com.zyjr.emergencylending.utils.LogUtils;

import java.io.File;

/**
 * Created by neil on 2017/10/19
 * 备注: 身份证信息
 */
public class IDCardPresenter extends BasePresenter<IDCardView> {

    public IDCardPresenter(Context context) {
        super(context);
    }

    public void uploadFileGetIDCardFrontInfo(File file) {
        invoke(IDCardModel.getInstance().getIDCardFrontInfo(file), new HttpSubscriber<IDCardFrontBean>() {
            @Override
            public void onSuccess(IDCardFrontBean result) {
                LogUtils.d("正面照返回数据结果集:---->" + new Gson().toJson(result));
                if (result != null) {
                    getView().onSuccessFrontBean(result.getSide(), result);
                }
            }
        });
    }

    public void uploadFileGetIDCardBackInfo(File file) {
        invoke(IDCardModel.getInstance().getIDCardBackInfo(file), new HttpSubscriber<IDCardBackBean>() {
            @Override
            public void onSuccess(IDCardBackBean result) {
                LogUtils.d("反面照返回数据结果集:---->" + new Gson().toJson(result));
                if (result != null) {
                    getView().onSuccessBackBean(result.getSide(), result);
                }
            }
        });
    }



}
