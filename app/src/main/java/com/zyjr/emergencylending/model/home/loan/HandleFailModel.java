package com.zyjr.emergencylending.model.home.loan;

import com.zyjr.emergencylending.base.BaseModel;
import com.zyjr.emergencylending.service.home.loan.HandleFailService;

/**
 * 审核失败
 *
 * @author neil
 * @date 2017/11/13
 */
public class HandleFailModel extends BaseModel {

    private HandleFailService handleFailService;

    private HandleFailModel() {
        super();
        this.handleFailService = retrofit.create(HandleFailService.class);
    }

    private static class SingletonHolder {
        private static final HandleFailModel handleFailModel = new HandleFailModel();
    }

    public static HandleFailModel getInstance() {
        return SingletonHolder.handleFailModel;
    }

}
