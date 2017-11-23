package com.zyjr.emergencylending.model.home.loan;

import com.zyjr.emergencylending.base.ApiResult;
import com.zyjr.emergencylending.base.BaseModel;
import com.zyjr.emergencylending.config.NetConstantValues;
import com.zyjr.emergencylending.service.home.loan.QuestValidateService;

import java.util.Map;

import rx.Observable;

/**
 * 问题验证
 * @author neil
 * @date 2017/11/6
 */
public class QuestValidateModel extends BaseModel{

    private QuestValidateService questValidateService;

    private QuestValidateModel() {
        super();
        this.questValidateService = retrofit.create(QuestValidateService.class);
    }

    private static class SingletonHolder {
        private static final QuestValidateModel questValidateModel = new QuestValidateModel();
    }

    public static QuestValidateModel getInstance() {
        return SingletonHolder.questValidateModel;
    }

    public Observable<ApiResult<String>> saveContactsList(Map<String, String> params) {
        params.put("router", NetConstantValues.ROUTER_SAVE_CONTACTS_LIST);
        return questValidateService.saveContactsList(params);
    }

}
