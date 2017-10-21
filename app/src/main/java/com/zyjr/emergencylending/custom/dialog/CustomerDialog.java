package com.zyjr.emergencylending.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zyjr.emergencylending.R;

import static com.zyjr.emergencylending.R.id.tv_album;
import static com.zyjr.emergencylending.R.id.tv_picture;


public class CustomerDialog extends Dialog {
    private Window window;
    private CustomerDialog instance;

    public CustomerDialog(Context context) {
        super(context, R.style.transparentFrameWindowStyle);
        instance = this;
        window = getWindow();

    }

    /**
     * 上传图片
     *
     * @param listener
     * @return
     */
    public CustomerDialog userPicDialog(View.OnClickListener listener) {
        setContentView(R.layout.dialog_adavater_layout);
        TextView tvPicture = findViewById(tv_picture);
        TextView tvAlbum = findViewById(tv_album);
        TextView cancel = findViewById(R.id.cancel);
        window.setGravity(Gravity.BOTTOM);
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置点击外围解散
        CustomerDialog.this.setCanceledOnTouchOutside(false);
        cancel.setOnClickListener(listener);
        tvPicture.setOnClickListener(listener);
        tvAlbum.setOnClickListener(listener);
        return instance;
    }

    /**
     * 删除消息
     */
    public CustomerDialog deleteMessage(View.OnClickListener listener) {
        setContentView(R.layout.dialog_message);
        TextView tvLeft = super.findViewById(R.id.message_left);
        TextView tvRight = super.findViewById(R.id.message_right);
        window.setGravity(Gravity.CENTER);
        tvLeft.setOnClickListener(listener);
        tvRight.setOnClickListener(listener);
        return instance;
    }

    /**
     * 手机认证信息提示
     */
    public CustomerDialog mobileAuthNotice(View.OnClickListener listener) {
        setContentView(R.layout.dialog_mobile_auth_notice);
        ImageView ivClose = findViewById(R.id.iv_close_pop);
        window.setGravity(Gravity.CENTER);
        ivClose.setOnClickListener(listener);
        return instance;
    }

    /**
     * 借款 信息确认弹出框
     *
     * @param listener 事件回调
     * @param userFlag 用户标识
     * @param second   是否续贷
     * @param money    借款金额
     * @param week     借款周期
     * @return
     */
    public CustomerDialog loanProductMatchInfo(View.OnClickListener listener, String userFlag, boolean second, String money, String week) {
        setContentView(R.layout.dialog_product_match);
        TextView tvDesc = findViewById(R.id.tv_title1);
        if (second) {
            tvDesc.setVisibility(View.INVISIBLE);
        }
        Button btnSumbit = findViewById(R.id.btn_comfirm_submit);
        TextView tvLoadMoney = findViewById(R.id.tv_loan_money);
        tvLoadMoney.setText(money);
        TextView tvLoadWeek = findViewById(R.id.tv_loan_week);
        tvLoadWeek.setText(week);
        ImageView ivClose = findViewById(R.id.iv_close);
        window.setGravity(Gravity.CENTER);
        ivClose.setOnClickListener(listener);
        btnSumbit.setOnClickListener(listener);
        return instance;
    }


    /**
     * 删除银行卡信息
     *
     * @param listener
     * @return
     */
    public CustomerDialog deleteBankcard(View.OnClickListener listener) {
        setContentView(R.layout.dialog_delete_bankcard);
        TextView tvDelete = findViewById(R.id.tv_delete_bankcard);
        TextView tvCancel = findViewById(R.id.tv_cancel);
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.main_menu_animstyle);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvDelete.setOnClickListener(listener);
        tvCancel.setOnClickListener(listener);
        return instance;
    }

    /**
     * 身份证 信息
     *
     * @param idName  名字
     * @param idNum   身份证号
     * @param address 地址
     * @return
     */
    public CustomerDialog scanIdcardInfo(View.OnClickListener listener, String idName, String idNum, String address) {
        setContentView(R.layout.dialog_idcard_info);
        TextView tvIdcardName = findViewById(R.id.tv_idcard_name);
        tvIdcardName.setText(idName);
        TextView tvIdcardNumber = findViewById(R.id.tv_idcard_number);
        tvIdcardNumber.setText(idNum);
        TextView etDetailAddress = findViewById(R.id.et_detail_address);
        etDetailAddress.setText(address);
        TextView tvConfirm = findViewById(R.id.tv_confirm);
        tvConfirm.setOnClickListener(listener);
        TextView tvScanAgain = findViewById(R.id.tv_scan_again);
        tvScanAgain.setOnClickListener(listener);
        window.setWindowAnimations(R.style.main_menu_animstyle);
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        CustomerDialog.this.setCanceledOnTouchOutside(false);  // 设置点击外围解散
        return instance;
    }

    /**
     * 借款失败流转
     *
     * @param listener
     * @return
     */
    public CustomerDialog borrowSkip(View.OnClickListener listener) {
        setContentView(R.layout.dialog_borrow_error);
        ImageView close = findViewById(R.id.iv_close);
        Button submit = findViewById(R.id.btn_confirm_submit);
        window.setGravity(Gravity.CENTER);
        window.setWindowAnimations(R.style.main_menu_animstyle);
        close.setOnClickListener(listener);
        submit.setOnClickListener(listener);
        return instance;
    }

    /**
     * 手持证件照 拍照提示
     */
    public CustomerDialog holdIdcardNotice(View.OnClickListener listener) {
        setContentView(R.layout.dialog_hold_idcard_model);
        LinearLayout rootView = findViewById(R.id.root_takephoto_model);
        window.setGravity(Gravity.FILL);
        window.setWindowAnimations(R.style.dialog_transparent);
        rootView.setOnClickListener(listener);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setCanceledOnTouchOutside(false);
        return instance;
    }


    /**
     * 通用弹出确认
     */
    /**
     * @param content
     * @param contentColor
     * @param leftMsg
     * @param leftFrontColor
     * @param rightMsg
     * @param rightFrontColor
     * @return
     */
    public CustomerDialog operateComfirm(View.OnClickListener listener,
                                         String content, int contentColor, String leftMsg, int leftFrontColor, String rightMsg, int rightFrontColor) {
        setContentView(R.layout.dialog_operate_confrim);
        TextView tvContent = findViewById(R.id.tv_content);
        tvContent.setText(content);
        if (contentColor != 0) {
            tvContent.setTextColor(leftFrontColor);
        }
        TextView tvLeftMsg = findViewById(R.id.message_left);
        tvLeftMsg.setText(leftMsg);
        if (leftFrontColor != 0) {
            tvLeftMsg.setTextColor(leftFrontColor);
        }
        TextView tvRightMsg = findViewById(R.id.message_right);
        tvRightMsg.setText(rightMsg);
        if (rightFrontColor != 0) {
            tvRightMsg.setTextColor(leftFrontColor);
        }
        window.setGravity(Gravity.CENTER);
        setCanceledOnTouchOutside(false);
        tvLeftMsg.setOnClickListener(listener);
        tvRightMsg.setOnClickListener(listener);
        return instance;
    }

    /**
     * 拨打客户电话
     *
     * @param onClickListener
     */
    public void showHotLineDialog(View.OnClickListener onClickListener) {
        TextView cancel, tv_title;
        setContentView(R.layout.dialog_hot_line);
        cancel = (TextView) findViewById(R.id.cancel);
        tv_title = (TextView) findViewById(R.id.tv_title);
        window.setGravity(Gravity.BOTTOM);
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置点击外围解散
        setCanceledOnTouchOutside(false);
        cancel.setOnClickListener(onClickListener);
        tv_title.setOnClickListener(onClickListener);
    }
}