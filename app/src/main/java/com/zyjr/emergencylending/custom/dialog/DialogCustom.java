package com.zyjr.emergencylending.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zyjr.emergencylending.R;

import static com.zyjr.emergencylending.R.id.tv_album;
import static com.zyjr.emergencylending.R.id.tv_picture;


public class DialogCustom extends Dialog {
    private Window window;
    private DialogCustom instance;

    public DialogCustom(Context context) {
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
    public DialogCustom userPicDialog(View.OnClickListener listener) {
        setContentView(R.layout.dialog_adavater_layout);
        TextView tvPicture = findViewById(tv_picture);
        TextView tvAlbum = findViewById(tv_album);
        TextView cancel = findViewById(R.id.cancel);
        window.setGravity(Gravity.BOTTOM);
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置点击外围解散
        DialogCustom.this.setCanceledOnTouchOutside(false);
        cancel.setOnClickListener(listener);
        tvPicture.setOnClickListener(listener);
        tvAlbum.setOnClickListener(listener);
        return instance;
    }

    /**
     * 删除消息
     */
    public DialogCustom deleteMessage(View.OnClickListener listener) {
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
    public DialogCustom mobileAuthNotice(View.OnClickListener listener) {
        setContentView(R.layout.dialog_mobile_auth_notice);
        ImageView ivClose = findViewById(R.id.iv_close_pop);
        window.setGravity(Gravity.CENTER);
        ivClose.setOnClickListener(listener);
        return instance;
    }

    /**
     * 借款 信息确认弹出框
     * @param listener 事件回调
     * @param money 借款金额
     * @param week  借款周期
     * @return
     */
    public DialogCustom loanProductMatchInfo(View.OnClickListener listener, String money, String week) {
        setContentView(R.layout.dialog_product_match);
        Button btnSumbit = findViewById(R.id.btn_comfirm_submit);
        TextView tvLoadMoney = findViewById(R.id.tv_loan_money);
        tvLoadMoney.setText(money);
        TextView tvLoadWeek = findViewById(R.id.tv_loan_week);
        tvLoadWeek.setText(week);
        window.setGravity(Gravity.CENTER);
        btnSumbit.setOnClickListener(listener);
        return instance;
    }


    /**
     * 删除银行卡信息
     * @param listener
     * @return
     */
    public DialogCustom deleteBankcard(View.OnClickListener listener) {
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

}
