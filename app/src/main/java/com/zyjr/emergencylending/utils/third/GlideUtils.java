package com.zyjr.emergencylending.utils.third;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.zyjr.emergencylending.utils.ImageUtils;


/**
 * Glide 工具类
 * Created by neil on 2017/10/26
 */
public class GlideUtils {

    public static void displayImage(Context context, String uri, ImageView imageView) {
        displayImage(context, uri, -1, imageView, -1);
    }

    public static void displayImage(Context context, String uri, int preResourceId, ImageView imageView, int resId) {
        displayImage(context, uri, preResourceId, imageView, resId, false);
    }

    public static void displayImageWithPre(Context context, String uri, int preResourceId, ImageView imageView) {
        displayImage(context, uri, preResourceId, imageView, -1);
    }


    public static void displayImage(Context context, String uri, int preResourceId, ImageView imageView, int resId, boolean needCircle) {
        if (TextUtils.isEmpty(uri)) {
            if (resId != -1) {
                if (needCircle) {
//                    Glide.with(context).load(resId).transform(new GlideCircleBitmap(context)).into(imageView);
                } else {
                    Glide.with(context).load(resId).into(imageView);
                }
            }
        } else {
            // 需要圆形切角图
            if (needCircle) {
                if (resId == -1) {
//                    Glide.with(context).load(uri).transform(new GlideCircleBitmap(context)).into(imageView);
                } else {
//                    Glide.with(context).load(uri).placeholder(resId).transform(new GlideCircleBitmap(context)).into(imageView);
                }
            } else {
                if (resId == -1) {
                    if (preResourceId != -1)
                        Glide.with(context).load(uri).placeholder(preResourceId).into(imageView);
                    else {
                        Glide.with(context).load(uri).into(imageView);
                    }
                } else {
                    Glide.with(context).load(uri).placeholder(resId).into(imageView);
                }
            }
        }
    }


    public static void clearMemoryCache(final Context context) {
        Glide.get(context).clearMemory();

    }

    public static void clearDiskCache(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();
            }
        }).start();
    }


    public static void clear(final Context context) {
        clearMemoryCache(context);
        clearDiskCache(context);
    }

    /**
     * 根据
     */
    public static void displayImageWithFixedSize(Context context, String url, int preResourceId, final ImageView imageView, final int fixedWidth, final int fixedHeight) {
        Glide.with(context).load(url).placeholder(preResourceId)
                .override(fixedWidth, fixedHeight).into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                Bitmap bmp = ImageUtils.drawableToBitmap(resource);
                Bitmap mBitmapIDcardBack = Bitmap.createScaledBitmap(bmp, fixedWidth, fixedHeight, true);
                bmp.recycle();
                System.gc();
                imageView.setImageBitmap(mBitmapIDcardBack);
            }
        });

    }

}
