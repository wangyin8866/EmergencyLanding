package com.zyjr.emergencylending.entity;

/**
 * @author wangyin
 * @date 2017/11/2.
 * @description :
 */

public class UserInfo {

    /**
     * flag : API0000
     * msg : 操作成功
     * ext : null
     * result : {"head_url":"","user_name":null,"tel":"17621573868","news_status":"0"}
     * lockerFlag : false
     */

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
        /**
         * head_url :
         * user_name : null
         * tel : 17621573868
         * news_status : 0
         */

        private String head_url;
        private String user_name;
        private String tel;
        private String news_status;

        public String getHead_url() {
            return head_url;
        }

        public void setHead_url(String head_url) {
            this.head_url = head_url;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getNews_status() {
            return news_status;
        }

        public void setNews_status(String news_status) {
            this.news_status = news_status;
        }
    }
}
