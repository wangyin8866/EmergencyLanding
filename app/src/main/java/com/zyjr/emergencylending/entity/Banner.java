package com.zyjr.emergencylending.entity;

import java.util.List;

/**
 * @author wangyin
 * @date 2017/10/27.
 * @description :
 */

public class Banner {

    /**
     * flag : API0000
     * msg : 操作成功
     * ext : null
     * result : {"ad_list":[{"title":"急借通续贷推出优惠","ad_desc":"对优质客户执行优质正常","ad_url":"www.baidu.com","ad_pic":"F:\\360Downloads"},{"title":"盈信通首贷客户优惠惊喜","ad_desc":"针对优质首贷客户","ad_url":"www.taobao.com","ad_pic":"F:\\360Downloads\\Software"}]}
     * lockerFlag : false
     */
    private String promptMsg;
    public String getPromptMsg() {
        return promptMsg;
    }

    public void setPromptMsg(String promptMsg) {
        this.promptMsg = promptMsg;
    }
    private String flag;
    private String msg;
    private Object ext;
    private ResultBean result;
    private boolean lockerFlag;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getExt() {
        return ext;
    }

    public void setExt(Object ext) {
        this.ext = ext;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public boolean isLockerFlag() {
        return lockerFlag;
    }

    public void setLockerFlag(boolean lockerFlag) {
        this.lockerFlag = lockerFlag;
    }

    public static class ResultBean {
        private List<AdListBean> ad_list;

        public List<AdListBean> getAd_list() {
            return ad_list;
        }

        public void setAd_list(List<AdListBean> ad_list) {
            this.ad_list = ad_list;
        }

        public static class AdListBean {
            /**
             * title : 急借通续贷推出优惠
             * ad_desc : 对优质客户执行优质正常
             * ad_url : www.baidu.com
             * ad_pic : F:\360Downloads
             */

            private String title;
            private String ad_desc;
            private String ad_url;
            private String ad_pic;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getAd_desc() {
                return ad_desc;
            }

            public void setAd_desc(String ad_desc) {
                this.ad_desc = ad_desc;
            }

            public String getAd_url() {
                return ad_url;
            }

            public void setAd_url(String ad_url) {
                this.ad_url = ad_url;
            }

            public String getAd_pic() {
                return ad_pic;
            }

            public void setAd_pic(String ad_pic) {
                this.ad_pic = ad_pic;
            }
        }
    }
}
