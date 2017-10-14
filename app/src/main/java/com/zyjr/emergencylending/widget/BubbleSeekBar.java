package com.zyjr.emergencylending.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.AttributeSet;
import android.widget.SeekBar;

import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.utils.Arithmetic;

public class BubbleSeekBar extends AppCompatSeekBar {
    private Drawable mThumbDrawable;
    private int oldPaddingTop = 0;
    private int oldPaddingBottom = 0;
    private int oldPaddingLeft = 50;
    private int oldPaddingRight = 50;
    private int textpaddingleft;
    private int textpaddingtop;
    private int imagepaddingleft;
    private int imagepaddingtop;
    private float mTextWidth;
    private float mImgWidth;
    private float mImgHei;
    private String mText;
    private int textsize = 35;
    //    private int textColor = getResources().getColor(R.color.front_black);
    private boolean isMysetPadding = true;
    private Paint mPaint;
    private Bitmap bm;
    private Resources res;
    public static final int MONEY = 0;
    public static final int WEEK = 1;
    public int type = -1;
    // 设置区间范围
    public int MONEY_MIN = 0;
    public int MONEY_MAX = 0;
    // 标识14天
    public int ONLINE = 0;
    // 设置区间范围
    public int WEEK_MIN = 0;
    public int WEEK_MAX = 0;


    public BubbleSeekBar(Context context) {
        this(context, null);
        init();
    }

    public BubbleSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public BubbleSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnSeekBarChangeListener(mOnSeekBarChangeListener);
        init();
    }

    // 初始化
    private void init() {
        res = getResources();
        initBitmap();
        initDraw();
        setPadding();
    }

    private void initBitmap() {
        bm = BitmapFactory.decodeResource(res, R.mipmap.icon_slid);
        if (bm != null) {
            mImgWidth = bm.getWidth();
            mImgHei = bm.getHeight();
        } else {
            mImgWidth = 0;
            mImgHei = 0;
        }
    }

    private void initDraw() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTypeface(Typeface.DEFAULT);
        mPaint.setTextSize(textsize);
        mPaint.setColor(res.getColor(R.color.white));
    }

    @Override
    public void setThumb(Drawable thumb) {
        super.setThumb(thumb);
        mThumbDrawable = thumb;
    }


    private OnSeekBarChangeListener mOnSeekBarChangeListener = new OnSeekBarChangeListener() {

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        }
    };

    protected synchronized void onDraw(Canvas canvas) {
        try {
            super.onDraw(canvas);
            if (type == MONEY) {
                mText = Arithmetic.progressToMoney(getProgress(), MONEY_MIN, MONEY_MAX) + "元";
            } else if (type == WEEK) {
                if (ONLINE == 1 && getProgress() <= 1) {
                    mText = WEEK_MIN * 7 + "天";
                } else {
                    mText = Arithmetic.progressToWeek(getProgress(), WEEK_MIN, WEEK_MAX) + "周";
                }
            } else {
                mText = "";
            }
            mTextWidth = mPaint.measureText(mText);
            Rect bounds = this.getProgressDrawable().getBounds();
            float xImg = bounds.width() * getProgress() / getMax() - oldPaddingLeft + 10;
            float yImg = imagepaddingtop + oldPaddingTop;
            float xText = bounds.width() * getProgress() / getMax() + mImgWidth / 2
                    - mTextWidth / 2 + textpaddingleft - oldPaddingLeft + 10;
            float yText = yImg + textpaddingtop + mImgHei / 2 + getTextHei() / 4 - 5;
            canvas.drawBitmap(bm, xImg, yImg, mPaint);
            canvas.drawText(mText, xText, yText, mPaint);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getBitmapWidth() {
        return (int) Math.ceil(mImgWidth);
    }

    private int getBitmapHeigh() {
        return (int) Math.ceil(mImgHei);
    }

    private float getTextHei() {
        Paint.FontMetrics fm = mPaint.getFontMetrics();
        return (float) Math.ceil(fm.descent - fm.top) + 2;
    }

    // 初始化padding 使其左右上 留下位置用于展示进度图片
    private void setPadding() {
        int top = getBitmapHeigh() + oldPaddingTop;
        int left = oldPaddingLeft;
        int right = oldPaddingRight;
        int bottom = oldPaddingBottom;
        isMysetPadding = true;
        setPadding(left, top, right, bottom);
        isMysetPadding = false;
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        if (isMysetPadding) {
            super.setPadding(left, top, right, bottom);
        }
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public int getONLINE() {
        return ONLINE;
    }

    public void setONLINE(int ONLINE) {
        this.ONLINE = ONLINE;
    }

    public int getMONEY_MIN() {
        return MONEY_MIN;
    }

    public void setMONEY_MIN(int MONEY_MIN) {
        this.MONEY_MIN = MONEY_MIN;
    }

    public int getMONEY_MAX() {
        return MONEY_MAX;
    }

    public void setMONEY_MAX(int MONEY_MAX) {
        this.MONEY_MAX = MONEY_MAX;
    }

    public int getWEEK_MIN() {
        return WEEK_MIN;
    }

    public void setWEEK_MIN(int WEEK_MIN) {
        this.WEEK_MIN = WEEK_MIN;
    }

    public int getWEEK_MAX() {
        return WEEK_MAX;
    }

    public void setWEEK_MAX(int WEEK_MAX) {
        this.WEEK_MAX = WEEK_MAX;
    }
}
