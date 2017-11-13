package com.zyjr.emergencylending.ui.my.presenter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.xfqz.xjd.mylibrary.ProgressSubscriber;
import com.xfqz.xjd.mylibrary.SubscriberOnNextListener;
import com.zyjr.emergencylending.R;
import com.zyjr.emergencylending.base.BasePresenter;
import com.zyjr.emergencylending.base.BaseView;
import com.zyjr.emergencylending.config.Config;
import com.zyjr.emergencylending.entity.UserStatus;
import com.zyjr.emergencylending.model.account.AccountModel;
import com.zyjr.emergencylending.utils.LogUtils;
import com.zyjr.emergencylending.utils.ToastAlone;

/**
 * @author wangyin
 * @date 2017/11/1.
 * @description :
 */

public class PersonalDataPresenter extends BasePresenter<BaseView<UserStatus>> {
    public PersonalDataPresenter(Context context) {
        super(context);
    }

    public void getUserInfoStatus(String router) {
        invoke(AccountModel.getInstance().getUserInfoStatus(router), new ProgressSubscriber<UserStatus>(new SubscriberOnNextListener<UserStatus>() {
            @Override
            public void onNext(UserStatus baseBean) {
                if (Config.CODE_SUCCESS.equals(baseBean.getFlag())) {
                    getView().requestSuccess();
                    getView().getCommonData(baseBean);
                } else {
                    ToastAlone.showShortToast(mContext, baseBean.getPromptMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                getView().requestError();
            }
        }, mContext));

    }

    public void initStatus(String personalStatus, String isPersonalEdit, String jobStatus, String isJobEdit,
                           String contactStatus, String isContactEdit, String cardStatus, String isCardEdit,
                           String isBankError, ImageView iconPicPersonal, ImageView iconPicJob,
                           ImageView iconPicContact, ImageView iconPicCard) {
        if (Config.TRUE.equals(personalStatus) && Config.TRUE.equals(isPersonalEdit)) {
            iconPicPersonal.setVisibility(View.VISIBLE);
            iconPicPersonal.setImageResource(R.mipmap.data_icon_edit_complete);
        } else if (Config.FALSE.equals(personalStatus) && Config.TRUE.equals(isPersonalEdit)) {
            iconPicPersonal.setVisibility(View.GONE);
        } else if (Config.FALSE.equals(isPersonalEdit)) {
            iconPicPersonal.setVisibility(View.VISIBLE);
            iconPicPersonal.setImageResource(R.mipmap.data_icon_complete);
        }
        //工作信息
        LogUtils.e(jobStatus + " ===" + isJobEdit);
        if (Config.TRUE.equals(jobStatus) && Config.TRUE.equals(isJobEdit)) {
            iconPicJob.setVisibility(View.VISIBLE);
            iconPicJob.setImageResource(R.mipmap.data_icon_edit_complete);
        } else if (Config.FALSE.equals(jobStatus) && Config.TRUE.equals(isJobEdit)) {
            iconPicJob.setVisibility(View.GONE);
        } else if (Config.FALSE.equals(isJobEdit)) {
            iconPicJob.setVisibility(View.VISIBLE);
            iconPicJob.setImageResource(R.mipmap.data_icon_complete);
        }
        //联系人
        if (Config.TRUE.equals(contactStatus) && Config.TRUE.equals(isContactEdit)) {
            iconPicContact.setVisibility(View.VISIBLE);
            iconPicContact.setImageResource(R.mipmap.data_icon_edit_complete);
        } else if (Config.FALSE.equals(contactStatus) && Config.TRUE.equals(isContactEdit)) {
            iconPicContact.setVisibility(View.GONE);
        } else if (Config.FALSE.equals(isContactEdit)) {
            iconPicContact.setVisibility(View.VISIBLE);
            iconPicContact.setImageResource(R.mipmap.data_icon_complete);
        }
        //银行卡
        if (Config.TRUE.equals(isBankError)) {
            iconPicCard.setVisibility(View.GONE);
        } else if (Config.TRUE.equals(cardStatus) && Config.TRUE.equals(isCardEdit)) {
            iconPicCard.setVisibility(View.VISIBLE);
            iconPicCard.setImageResource(R.mipmap.data_icon_edit_complete);
        } else if (Config.FALSE.equals(cardStatus) && Config.TRUE.equals(isCardEdit)) {
            iconPicCard.setImageResource(R.mipmap.data_icon_abnormal);
        } else if (Config.FALSE.equals(isCardEdit)) {
            iconPicCard.setVisibility(View.VISIBLE);
            iconPicCard.setImageResource(R.mipmap.data_icon_complete);
        }
    }
}
