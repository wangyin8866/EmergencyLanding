package com.zyjr.emergencylending.model.home.loan;

import com.zyjr.emergencylending.config.Constants;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.entity.IDCardBackBean;
import com.zyjr.emergencylending.entity.IDCardFrontBean;
import com.zyjr.emergencylending.model.BaseModel;
import com.zyjr.emergencylending.service.home.loan.IDCardService;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by neil on 2017/10/19
 * 备注: 获取身份证信息
 */
public class IDCardModel extends BaseModel {

    private IDCardService idcardService;

    private IDCardModel(String baseUrl) {
        super(baseUrl);
        idcardService = retrofit.create(IDCardService.class);
    }

    private static class SingletonHolder {
        private static final IDCardModel idCardModel = new IDCardModel(NetConstantValues.IDCARD_URL);
    }

    public static IDCardModel getInstance() {
        return SingletonHolder.idCardModel;
    }


    /**
     * 上传图片获取信息(正面)
     */
    public Observable<IDCardFrontBean> getIDCardFrontInfo(File file) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM); //ParamKey.TOKEN 自定义参数key常量类，即参数名
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        builder.addFormDataPart("image", file.getName(), imageBody); //imgfile 后台接收图片流的参数名
        builder.addFormDataPart("api_key", Constants.FACE_APPKEY);
        builder.addFormDataPart("api_secret", Constants.FACE_APPSECRET);
        builder.addFormDataPart("legality", "1");  //传入1可以判断身份证是否  被编辑/是真实身份证/是复印件/是屏幕翻拍/是临时身份证
        List<MultipartBody.Part> parts = builder.build().parts();
        return idcardService.getIDCardFrontInfo(parts);
    }

    /**
     * 上传图片获取信息(背面)
     */
    public Observable<IDCardBackBean> getIDCardBackInfo(File file) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        builder.addFormDataPart("image", file.getName(), imageBody);
        builder.addFormDataPart("api_key", Constants.FACE_APPKEY);
        builder.addFormDataPart("api_secret", Constants.FACE_APPSECRET);
        builder.addFormDataPart("legality", "1");
        List<MultipartBody.Part> parts = builder.build().parts();
        return idcardService.getIDCardBackInfo(parts);
    }

}
