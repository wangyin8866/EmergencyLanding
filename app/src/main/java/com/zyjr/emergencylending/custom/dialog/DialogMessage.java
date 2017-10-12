package com.zyjr.emergencylending.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.zyjr.emergencylending.R;

import butterknife.BindView;


public class DialogMessage extends Dialog {
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.tv_right)
    TextView tvRight;

    private OnDoubleClickListener mdouble;
    private DialogMessage instance;

    public DialogMessage(Context context) {
        super(context, R.style.dialogToast);
        instance = this;
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.gravity = Gravity.CENTER | Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(attributes);
        super.setContentView(R.layout.dialog_message);
        tvLeft = (TextView) super.findViewById(R.id.tv_left);
        tvRight = (TextView) super.findViewById(R.id.tv_right);
        tvContent = (TextView) super.findViewById(R.id.tv_content);
        initListener();
    }

    public interface OnDoubleClickListener {
        void excuteLeft();

        void excuteRight();
    }

    public DialogMessage setOnDoubleClickListener(OnDoubleClickListener doubl) {
        this.mdouble = doubl;
        return instance;

    }



    private void initListener() {
        tvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                mdouble.excuteLeft();
            }
        });
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                mdouble.excuteRight();
            }
        });
    }

    public DialogMessage setContent(String btnText) {
        tvContent.setText(btnText);
        return instance;
    }


}
