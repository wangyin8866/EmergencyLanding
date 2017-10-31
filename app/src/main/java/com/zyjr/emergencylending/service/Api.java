package com.zyjr.emergencylending.service;

import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.entity.Banner;
import com.zyjr.emergencylending.entity.BaseBean;
import com.zyjr.emergencylending.entity.CardBean;
import com.zyjr.emergencylending.entity.H5Bean;
import com.zyjr.emergencylending.entity.MessageBean;
import com.zyjr.emergencylending.entity.NoticeBean;
import com.zyjr.emergencylending.entity.QrBean;
import com.zyjr.emergencylending.entity.account.LoginBean;
import com.zyjr.emergencylending.entity.account.RegisterBean;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by wangyin on 2017/10/24.
 */

public interface Api {
    /**
     * 发送验证码
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<H5Bean> getH5Url(@FieldMap Map<String, String> params);
    /**
     * 注册
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<RegisterBean> register(@FieldMap Map<String, String> params);

    /**
     * 登录
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<LoginBean> login(@FieldMap Map<String, String> params);

    /**
     * 版本更新
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<String> versionUpdate(@FieldMap Map<String, String> params);

    /**
     * 发送验证码
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<BaseBean> sendSMS(@FieldMap Map<String, String> params);

    /**
     * 忘记密码
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<BaseBean> forgetPassword(@FieldMap Map<String, String> params);

    /**
     * 首页广告
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<Banner> getHomeAds(@FieldMap Map<String, String> params);

    /**
     * 二维码
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<QrBean> getQr(@FieldMap Map<String, String> params);

    /**
     * 消息接口
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<MessageBean> getUserNews(@FieldMap Map<String, String> params);

    /**
     * 消息操作
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<BaseBean> updateUserNews(@FieldMap Map<String, String> params);

    /**
     * 公告清单(标题)及详情
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<NoticeBean> getNoticeList(@FieldMap Map<String, String> params);

    /**
     * 我的名片
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<BaseBean> myCard(@FieldMap Map<String, String> params);

    /**
     * 活动清单
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<BaseBean> getActivity(@FieldMap Map<String, String> params);

    /**
     * 我的收入
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<CardBean> myIncome(@FieldMap Map<String, String> params);

    /**
     * 帮助页面
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<H5Bean> helpPage(@FieldMap Map<String, String> params);

    /**
     * 关于我们
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<H5Bean> aboutUs(@FieldMap Map<String, String> params);

    /**
     * 我的业绩
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<BaseBean> myPerformance(@FieldMap Map<String, String> params);

    /**
     * 当月榜单
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<BaseBean> rankList(@FieldMap Map<String, String> params);

    /**
     * 等待申请
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<BaseBean> waitApply(@FieldMap Map<String, String> params);
    /**
     * 上传照片
     */
    @FormUrlEncoded
    @POST(NetConstantValues.LOAN)
    Observable<BaseBean> uploadFile(@FieldMap Map<String, String> params);
}
