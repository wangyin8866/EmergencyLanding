package com.zyjr.emergencylending.ui.my;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.zyjr.emergencylending.base.BaseView;
import com.zyjr.emergencylending.custom.RoundImageViewByXfermode;
import com.zyjr.emergencylending.custom.dialog.CustomerDialog;
import com.zyjr.emergencylending.ui.my.presenter.MyPresenter;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.ToastAlone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 *
 * @author wangyin
 * @date 2017/8/9
 */

public class MyFragment extends BaseFragment<MyPresenter, BaseView> implements TakePhoto.TakeResultListener, InvokeListener {
    @BindView(R.id.QR_code)
    ImageView QRCode;
    @BindView(R.id.message_center)
    ImageView messageCenter;
    @BindView(R.id.user_pic)
    RoundImageViewByXfermode userPic;
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
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;

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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.QR_code, R.id.message_center, R.id.user_pic, R.id.user_info, R.id.my_borrow, R.id.my_repayment, R.id.help, R.id.setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.QR_code:
                break;
            case R.id.message_center:
                break;
            case R.id.user_pic:
                showUserPicDialog();
                break;
            case R.id.user_info:
                break;
            case R.id.my_borrow:
                startActivity(new Intent(mContext,MyBorrowActivity.class));
                break;
            case R.id.my_repayment:
                break;
            case R.id.help:
                break;
            case R.id.setting:
                startActivity(new Intent(mContext,SettingActivity.class));
                break;
        }
    }

    /**
     * 设置图像
     */
    private void showUserPicDialog() {
        final CustomerDialog dialog = new CustomerDialog(getActivity());
        dialog.userPicDialog(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.cancel:
                        dialog.dismiss();
                        break;
                    case R.id.tv_picture:
                        dialog.dismiss();

                        mPresenter.toPhone(new MyFragment(), takePhoto);


                        break;
                    case R.id.tv_album:
                        dialog.dismiss();
                        mPresenter.toGallery(takePhoto);
                        break;
                }
            }
        }).show();
    }


    @Override
    public void takeSuccess(TResult result) {
        LogUtils.e("takeSuccess", "takeSuccess：" + result.getImage().getCompressPath());
        String imgPath = result.getImage().getOriginalPath();
        Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
        Bitmap mBitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
        userPic.setImageBitmap(mBitmap);
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
}
