package com.zyjr.emergencylending.entity;

/**
 * @author wangyin
 * @date 2017/11/10.
 * @description :
 */

public class VersionBean {

    /**
     * flag : API0000
     * msg : 操作成功
     * ext : null
     * result : {"url":"static/app/jijietong.apk","flag":"0001","content":"下载app","appVersion":"1.10"}
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
        /**
         * url : static/app/jijietong.apk
         * flag : 0001
         * content : 下载app
         * appVersion : 1.10
         */

        private String url;
        private String flag;
        private String content;
        private String appVersion;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAppVersion() {
            return appVersion;
        }

        public void setAppVersion(String appVersion) {
            this.appVersion = appVersion;
        }
    }
}
