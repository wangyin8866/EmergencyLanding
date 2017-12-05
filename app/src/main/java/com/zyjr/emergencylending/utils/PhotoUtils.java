package com.zyjr.emergencylending.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.View;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TakePhotoOptions;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.config.Constants;
import com.zyjr.emergencylending.custom.dialog.CustomerDialog;

import java.io.File;

import static android.support.v4.app.ActivityCompat.requestPermissions;

/**
 * Created by wangyin on 2017/10/23.
 */

public class PhotoUtils {
    /**
     * 设置图像
     */
    public static void showUserPicDialog(final Context mContext, final TakePhoto takePhoto) {

        final CustomerDialog dialog = new CustomerDialog(mContext);
        dialog.userPicDialog(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.cancel:
                        dialog.dismiss();
                        break;
                    case R.id.tv_picture:
                        dialog.dismiss();
                        PhotoUtils.toPhone(mContext, takePhoto);
                        break;
                    case R.id.tv_album:
                        dialog.dismiss();
                        PhotoUtils.toGallery(takePhoto);
                        break;
                }
            }
        }).show();
    }

    /**
     * 拍照
     */
    public static void toPhone(Context mContext, TakePhoto takePhoto) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasCamera = mContext.checkSelfPermission(Manifest.permission.CAMERA);
            int hasWsd = mContext.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (hasCamera == PackageManager.PERMISSION_GRANTED && hasWsd == PackageManager.PERMISSION_GRANTED) {
                takePhoto.setTakePhotoOptions(new TakePhotoOptions.Builder().setWithOwnGallery(true).create());
                CompressConfig compressConfig = new CompressConfig.Builder().setMaxSize(50 * 1024).setMaxPixel(800).create();
                takePhoto.onEnableCompress(compressConfig, true);
                takePhoto.onPickFromCaptureWithCrop(handlerFile(), getCropOptions());
            } else {
                requestPermissions((Activity) mContext, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
            }
        } else {
            takePhoto.setTakePhotoOptions(new TakePhotoOptions.Builder().setWithOwnGallery(true).create());
            CompressConfig compressConfig = new CompressConfig.Builder().setMaxSize(50 * 1024).setMaxPixel(800).create();
            takePhoto.onEnableCompress(compressConfig, true);
            takePhoto.onPickFromCaptureWithCrop(handlerFile(), getCropOptions());
        }
    }

    private static Uri handlerFile() {
        File file = new File(Environment.getExternalStorageDirectory(), Constants.HEAD_PIC +  + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        return Uri.fromFile(file);
    }

    private static CropOptions getCropOptions() {
        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setOutputX(400).setOutputY(400);
        builder.setWithOwnCrop(true);
        return builder.create();
    }

    /**
     * 相册
     *
     * @param takePhoto
     */
    public static void toGallery(TakePhoto takePhoto) {
        takePhoto.setTakePhotoOptions(new TakePhotoOptions.Builder().setWithOwnGallery(true).create());
        CompressConfig compressConfig = new CompressConfig.Builder().setMaxSize(50 * 1024).setMaxPixel(800).create();
        takePhoto.onEnableCompress(compressConfig, true);
        takePhoto.onPickFromGalleryWithCrop(handlerFile(), getCropOptions());
    }
}
