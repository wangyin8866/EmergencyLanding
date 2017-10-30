package com.zyjr.emergencylending.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by neil on 2017/10/14
 * 备注: 产品介绍bean
 */
public class ProIntroduceBean implements Serializable {

    private static final long serialVersionUID = -2164450930686205050L;

    private List<String> product_info;

    public List<String> getProduct_info() {
        return product_info;
    }

    public void setProduct_info(List<String> product_info) {
        this.product_info = product_info;
    }
}
