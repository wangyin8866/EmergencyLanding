package com.zyjr.emergencylending.entity;

import java.io.Serializable;

/**
 * Created by neil on 2017/10/14
 * 备注: 产品介绍bean
 */
public class ProIntroduceBean implements Serializable {

    private static final long serialVersionUID = -2164450930686205050L;

    private String title;
    private String content;

    public ProIntroduceBean(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
