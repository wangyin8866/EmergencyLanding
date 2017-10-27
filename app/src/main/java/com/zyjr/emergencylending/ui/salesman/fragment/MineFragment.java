package com.zyjr.emergencylending.ui.salesman.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.custom.RoundImageViewByXfermode;
import com.zyjr.emergencylending.ui.h5.H5WebView;
import com.zyjr.emergencylending.ui.home.MessageActivity;
import com.zyjr.emergencylending.ui.my.SettingActivity;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.PhotoUtils;
import com.zyjr.emergencylending.utils.ToastAlone;
import com.zyjr.emergencylending.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by wangyin on 2017/8/9.
 */

public class MineFragment extends BaseFragment implements TakePhoto.TakeResultListener, InvokeListener {
    @BindView(R.id.edit_information)
    ImageView editInformation;
    @BindView(R.id.user_pic)
    RoundImageViewByXfermode userPic;
    @BindView(R.id.ll_starts)
    LinearLayout llStarts;
    @BindView(R.id.income)
    TextView income;
    @BindView(R.id.message_center)
    TextView messageCenter;
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
        View view = inflater.inflate(R.layout.line_fragment_my_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        //创建星级
        addGroupImage(3);
    }

    private void addGroupImage(int size) {
        llStarts.removeAllViews();  //clear linearlayout
        for (int i = 0; i < size; i++) {
            ImageView imageView = new ImageView(mContext);
            //设置图片宽高
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(UIUtils.dip2px(5), 0, 0, 0);
            imageView.setLayoutParams(layoutParams);
            //图片资源
            imageView.setImageResource(R.mipmap.icon_star);
            //动态添加图片
            llStarts.addView(imageView);
        }
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.user_pic, R.id.message_center, R.id.my_repayment, R.id.help, R.id.setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_pic:
                PhotoUtils.showUserPicDialog(mContext,takePhoto);
                break;
            case R.id.message_center:
                startActivity(new Intent(mContext,MessageActivity.class));
                break;
            case R.id.my_repayment:
                break;
            case R.id.help:
                H5WebView.skipH5WebView(mContext,"帮助说明");
                break;
            case R.id.setting:
                startActivity(new Intent(mContext,SettingActivity.class));
                break;
        }
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
