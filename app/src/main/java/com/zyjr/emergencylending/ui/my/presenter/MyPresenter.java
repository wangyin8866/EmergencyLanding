package com.zyjr.emergencylending.ui.my.presenter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.Fragment;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TakePhotoOptions;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.base.BaseView;

import java.io.File;

/**
 * Created by wangyin on 2017/8/9.
 */

public class MyPresenter extends BasePresenter<BaseView> {
    public MyPresenter(Context context) {
        super(context);
    }

    /**
     * 拍照
     */
    public void toPhone(Fragment fragment, TakePhoto takePhoto) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasCamera = mContext.checkSelfPermission(Manifest.permission.CAMERA);
            int hasWsd = mContext.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (hasCamera == PackageManager.PERMISSION_GRANTED && hasWsd == PackageManager.PERMISSION_GRANTED) {
                takePhoto.setTakePhotoOptions(new TakePhotoOptions.Builder().setWithOwnGallery(true).create());
                CompressConfig compressConfig = new CompressConfig.Builder().setMaxSize(50 * 1024).setMaxPixel(800).create();
                takePhoto.onEnableCompress(compressConfig, true);
                takePhoto.onPickFromCaptureWithCrop(handlerFile(), getCropOptions());
            } else {
                fragment.requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
            }
        } else {
            takePhoto.setTakePhotoOptions(new TakePhotoOptions.Builder().setWithOwnGallery(true).create());
            CompressConfig compressConfig = new CompressConfig.Builder().setMaxSize(50 * 1024).setMaxPixel(800).create();
            takePhoto.onEnableCompress(compressConfig, true);
            takePhoto.onPickFromCaptureWithCrop(handlerFile(), getCropOptions());
        }
    }

    private Uri handlerFile() {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        return Uri.fromFile(file);
    }

    private CropOptions getCropOptions() {
        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setOutputX(400).setOutputY(300);
        builder.setWithOwnCrop(true);
        return builder.create();
    }

    /**
     * 相册
     *
     * @param takePhoto
     */
    public void toGallery(TakePhoto takePhoto) {
        takePhoto.setTakePhotoOptions(new TakePhotoOptions.Builder().setWithOwnGallery(true).create());
        CompressConfig compressConfig = new CompressConfig.Builder().setMaxSize(50 * 1024).setMaxPixel(800).create();
        takePhoto.onEnableCompress(compressConfig, true);
        takePhoto.onPickFromGalleryWithCrop(handlerFile(), getCropOptions());
    }
}
