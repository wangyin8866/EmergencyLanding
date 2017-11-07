package com.zyjr.emergencylending.ui.salesman.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
import com.zyjr.emergencylending.GlideApp;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BaseActivity;
import com.zyjr.emergencylending.base.BaseView;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.custom.GlideCircleTransform;
import com.zyjr.emergencylending.custom.TopBar;
import com.zyjr.emergencylending.entity.BaseBean;
import com.zyjr.emergencylending.entity.CardBean;
import com.zyjr.emergencylending.ui.salesman.presenter.EditInformationPresenter;
import com.zyjr.emergencylending.utils.ImageUtils;
import com.zyjr.emergencylending.utils.PhotoUtils;
import com.zyjr.emergencylending.utils.ToastAlone;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @author wangyin
 * @date 2017/10/23
 */

public class EditInformation extends BaseActivity<EditInformationPresenter, BaseView<BaseBean>> implements BaseView<BaseBean>, TakePhoto.TakeResultListener, InvokeListener {
    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_position)
    TextView userPosition;
    @BindView(R.id.user_phone)
    TextView userPhone;
    @BindView(R.id.user_pic)
    ImageView userPic;
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private Bitmap mBitmap;

    @Override
    protected EditInformationPresenter createPresenter() {
        return new EditInformationPresenter(mContext);
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
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.line_activity_edit_information);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        CardBean.ResultBean resultBean = (CardBean.ResultBean) getIntent().getSerializableExtra("information");
        userName.setText(resultBean.getName());
        userPosition.setText(resultBean.getPosition());
        userPhone.setText(resultBean.getPhone());

        GlideApp.with(this).load(resultBean.getHead_url()).placeholder(R.mipmap.billboard_head).error(R.mipmap.billboard_head).transform(new GlideCircleTransform(mContext)).into(userPic);
        topBar.setOnItemClickListener(new TopBar.OnItemClickListener() {
            @Override
            public void OnLeftButtonClicked() {
                finish();
            }

            @Override
            public void OnRightButtonClicked() {

            }
        });
    }

    @OnClick(R.id.user_pic)
    public void onViewClicked() {
        PhotoUtils.showUserPicDialog(mContext, takePhoto);
    }

    @Override
    public void takeSuccess(TResult result) {
//        LogUtils.e("takeSuccess", "takeSuccess：" + result.getImage().getCompressPath());
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
        ToastAlone.showLongToast(this, "takeFail:" + msg);
    }

    @Override
    public void takeCancel() {
        ToastAlone.showShortToast(this, getResources().getString(com.jph.takephoto.R.string.msg_operation_canceled));
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
    public void callBack(BaseBean baseBean) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();

        GlideApp.with(this).load(mBitmap).placeholder(R.mipmap.billboard_head).error(R.mipmap.billboard_head).transform(new GlideCircleTransform(mContext)).into(userPic);
    }
}
