package com.zyjr.emergencylending.service.home.loan;

import com.zyjr.emergencylending.entity.IDCardBackBean;
import com.zyjr.emergencylending.entity.IDCardFrontBean;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by neil on 2017/10/19
 * 备注: 身份证扫描(外接第三方)
 */
public interface IDCardService {

    // https://api.faceid.com/faceid/
    @Multipart
    @POST("v1/ocridcard")
    Observable<IDCardFrontBean> getIDCardFrontInfo(@Part List<MultipartBody.Part> partList);


    @Multipart
    @POST("v1/ocridcard")
    Observable<IDCardBackBean> getIDCardBackInfo(@Part List<MultipartBody.Part> partList);

}
