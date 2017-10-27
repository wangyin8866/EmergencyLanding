package com.zyjr.emergencylending.entity;

/**
 * @author wangyin
 * @date 2017/10/27.
 * @description :
 */

public class QrBean {

    /**
     * flag : API0000
     * msg : 操作成功
     * ext : null
     * result : {"recommendcode":"A000018","url":"M00/09/9F/wKgFGlnyp6uEME8tAAAAAMwHHEc982.png"}
     * lockerFlag : false
     */

    private String flag;
    private String msg;
    private String ext;
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

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
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
         * recommendcode : A000018
         * url : M00/09/9F/wKgFGlnyp6uEME8tAAAAAMwHHEc982.png
         */

        private String recommendcode;
        private String url;

        public String getRecommendcode() {
            return recommendcode;
        }

        public void setRecommendcode(String recommendcode) {
            this.recommendcode = recommendcode;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "recommendcode='" + recommendcode + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "QrBean{" +
                "flag='" + flag + '\'' +
                ", msg='" + msg + '\'' +
                ", ext='" + ext + '\'' +
                ", result=" + result +
                ", lockerFlag=" + lockerFlag +
                '}';
    }
}
