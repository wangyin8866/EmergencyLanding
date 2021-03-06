package com.zyjr.emergencylending.ui.my;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseFragment;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.custom.GlideCircleTransform;
import com.zyjr.emergencylending.entity.BaseBean;
import com.zyjr.emergencylending.entity.EffectiveOrderBean;
import com.zyjr.emergencylending.entity.H5Bean;
import com.zyjr.emergencylending.entity.MyBorrow;
import com.zyjr.emergencylending.entity.RepaymentSuccess;
import com.zyjr.emergencylending.entity.UserInfo;
import com.zyjr.emergencylending.ui.h5.H5WebView;
import com.zyjr.emergencylending.ui.home.MessageActivity;
import com.zyjr.emergencylending.ui.home.QrCodeActivity;
import com.zyjr.emergencylending.ui.my.View.MyView;
import com.zyjr.emergencylending.ui.my.presenter.MyPresenter;
import com.zyjr.emergencylending.utils.ImageUtils;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.PhotoUtils;
import com.zyjr.emergencylending.utils.SPUtils;
import com.zyjr.emergencylending.utils.ToastAlone;
import com.zyjr.emergencylending.utils.WYUtils;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * @author wangyin
 * @date 2017/8/9
 */

public class MyFragment extends BaseFragment<MyPresenter, MyView> implements MyView, TakePhoto.TakeResultListener, InvokeListener {
    @BindView(R.id.iv_QR_code)
    ImageView QRCode;
    @BindView(R.id.iv_message_center)
    ImageView messageCenter;
    @BindView(R.id.user_pic)
    ImageView userPic;
    @BindView(R.id.user_info)
    TextView userInfo;
    @BindView(R.id.my_borrow)
    TextView myBorrow;
    @BindView(R.id.my_repayment)
    TextView myRepayment;
    @BindView(R.id.help)
    TextView help;
    @BindView(R.id.setting)
    TextView setting;
    Unbinder unbinder;
    @BindView(R.id.user_name_phone)
    TextView userNamePhone;
    @BindView(R.id.ll_main)
    LinearLayout llMain;
    @BindView(R.id.ll_retry)
    LinearLayout llRetry;
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private UserInfo.ResultBean resultBean;
    private Bitmap mBitmap;
    private String phone;
    private String idCard;
    private String contractNo = ""; // 合同编号
    private String repayUrl = ""; // 还款url

    @Override
    protected MyPresenter createPresenter() {
        return new MyPresenter(mContext);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(getActivity(), type, invokeParam, this);


    }

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getBasicInfo(NetConstantValues.GET_BASIC_INFO);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.layout_QR_code, R.id.layout_message_center, R.id.user_pic, R.id.user_info, R.id.my_borrow, R.id.my_repayment, R.id.help, R.id.setting, R.id.btn_retry})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_QR_code:
                startActivity(new Intent(getActivity(), QrCodeActivity.class));
                break;
            case R.id.layout_message_center:
                startActivity(new Intent(getActivity(), MessageActivity.class));
                break;
            case R.id.user_pic:
                PhotoUtils.showUserPicDialog(mContext, takePhoto);
                break;
            case R.id.user_info:
                startActivity(new Intent(mContext, PersonalDataActivity.class));
                break;
            case R.id.my_borrow:
                startActivity(new Intent(mContext, MyBorrowActivity.class));
                break;
            case R.id.my_repayment:
                //是否有还款
                mPresenter.isEffectiveOrder(NetConstantValues.ROUTER_GET_CURRENT_EFFECTIVE_LOAN_ORDER);
                break;
            case R.id.help:
                mPresenter.getH5Url(Config.H5_URL_HELP, "帮助说明");
                break;
            case R.id.setting:
                startActivity(new Intent(mContext, SettingActivity.class));
                break;
            case R.id.btn_retry:
                mPresenter.getBasicInfo(NetConstantValues.GET_BASIC_INFO);
                break;
        }
    }


    @Override
    public void takeSuccess(TResult result) {
        LogUtils.e("takeSuccess", "takeSuccess：" + result.getImage().getCompressPath());
        String imgPath = result.getImage().getOriginalPath();
        Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
        mBitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
        // 文件名
        String fileName = imgPath.substring(imgPath.lastIndexOf("/") + 1, imgPath.lastIndexOf("."));
        // 后缀名(文件类型)
        String fileSuffixName = imgPath.substring(imgPath.lastIndexOf(".") + 1, imgPath.length());

        String data = ImageUtils.fileBase64(imgPath);

        mPresenter.uploadFile(NetConstantValues.ROUTER_UPLOAD_FILE, fileName, fileSuffixName, data, "1");
    }

    @Override
    public void takeFail(TResult result, String msg) {
        ToastAlone.showLongToast(getActivity(), "takeFail:" + msg);
    }

    @Override
    public void takeCancel() {
        ToastAlone.showShortToast(getActivity(), getResources().getString(com.jph.takephoto.R.string.msg_operation_canceled));
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    @Override
    public void getUserInfo(UserInfo userInfo) {

        resultBean = userInfo.getResult();
        //手机号
        phone = resultBean.getTel();
        //身份证
        idCard = resultBean.getIdcard();

        Glide.with(mContext).load(resultBean.getHead_url()).placeholder(R.mipmap.head_portrait).error(R.mipmap.head_portrait).transform(new GlideCircleTransform(mContext)).into(userPic);
        if (!TextUtils.isEmpty(resultBean.getUser_name())) {
            userNamePhone.setText(WYUtils.nameSecret(resultBean.getUser_name()) + " " + WYUtils.phoneSecret(resultBean.getTel()));
        } else {
            userNamePhone.setText("未实名" + " " + WYUtils.phoneSecret(resultBean.getTel()));
        }
        if (Config.TRUE.equals(resultBean.getNews_status())) {
            messageCenter.setImageResource(R.mipmap.icon_message_reddot);
        } else {
            messageCenter.setImageResource(R.mipmap.icon_message);
        }
    }

    @Override
    public void update(BaseBean baseBean) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();

        Glide.with(this).load(bytes).placeholder(R.mipmap.head_portrait).error(R.mipmap.head_portrait).transform(new GlideCircleTransform(mContext)).into(userPic);
    }

    @Override
    public void isEffectiveOrder(EffectiveOrderBean baseBean) {
        if (Config.TRUE.equals(baseBean.getResult().getOrder_status())) {
            contractNo = baseBean.getResult().getContract_no();
            repayUrl = baseBean.getResult().getH5_url();
            H5WebView.skipH5WebView(mContext, "还款", repayUrl + "?contractNo=" + contractNo + "&page=1&time=" + System.currentTimeMillis());
        } else {
            H5WebView.skipH5WebView(mContext, "还款", NetConstantValues.NO_REPAY);
        }
    }

    @Deprecated
    @Override
    public void getBorrowInfoByUserId(MyBorrow baseBean) {
        // 变更后
        if (baseBean.getResult().getCurrent_borrow() != null) {

            if (Config.TRUE.equals(baseBean.getResult().getCurrent_borrow().getIsRepaymentFlag())) {
                //获取订单状态
                mPresenter.isEffectiveOrder(NetConstantValues.ROUTER_GET_CURRENT_EFFECTIVE_LOAN_ORDER);
            } else {
                H5WebView.skipH5WebView(mContext, "还款", NetConstantValues.NO_REPAY);
            }
        } else {
            H5WebView.skipH5WebView(mContext, "还款", NetConstantValues.NO_REPAY);
        }

    }

    @Deprecated
    @Override
    public void getRepaymentLogin(RepaymentSuccess baseBean) {
        String token = baseBean.getToken();
        SPUtils.saveString(mContext, Config.KEY_REPAYMENT_TOKEN, token);
        // 调还款的接口
        mPresenter.getH5Url(Config.H5_URL_REPAYMENT, "还款");
    }

    @Deprecated
    @Override
    public void loadRepayH5(H5Bean h5Bean) {
        H5WebView.skipH5WebView(mContext, "还款", h5Bean.getResult().getH5_url() + "?contractNo=" + contractNo + "&page=1&time=" + System.currentTimeMillis());
    }

    @Override
    public void getCommonData(Object o) {

    }

    @Override
    public void requestError() {
        llMain.setVisibility(View.GONE);
        llRetry.setVisibility(View.VISIBLE);
    }

    @Override
    public void requestSuccess() {
        llMain.setVisibility(View.VISIBLE);
        llRetry.setVisibility(View.GONE);
    }
}
