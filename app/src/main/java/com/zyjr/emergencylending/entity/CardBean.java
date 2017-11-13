package com.zyjr.emergencylending.entity;

import java.io.Serializable;

/**
 * @author wangyin
 * @date 2017/10/31.
 * @description : 业务员名片
 */

public class CardBean implements Serializable{

    /**
     * flag : API0000
     * msg : 操作成功
     * ext : null
     * {"result":{"head_url":"http://192.168.5.26/fxd//M00/09/A7/wKgFGln4SRuEc11kAAAAAEvdijI770.jpg","position":"客户经理","phone":"13050311105","rq_code":"http://192.168.5.26/fxd//M00/09/9E/wKgFGlnwWUqEGTSbAAAAANV9p8U329.png","product_img_url":"","company":"丹东分公司","name":"丁晓宇"},"lockerFlag":false}
     * result : {"head_url":"","position":"客户经理","phone":"13050311105","rq_code":"M00/09/9E/wKgFGlnwWUqEGTSbAAAAANV9p8U329.png","product_img_url":"","company":"丹东分公司","name":"丁晓宇"}
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

    public static class ResultBean implements Serializable{
        /**
         * head_url :
         * position : 客户经理
         * phone : 13050311105
         * rq_code : M00/09/9E/wKgFGlnwWUqEGTSbAAAAANV9p8U329.png
         * product_img_url :
         * company : 丹东分公司
         * name : 丁晓宇
         */

        private String head_url;
        private String position;
        private String phone;
        private String rq_code;
        private String product_img_url;
        private String company;
        private String name;

        public String getHead_url() {
            return head_url;
        }

        public void setHead_url(String head_url) {
            this.head_url = head_url;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getRq_code() {
            return rq_code;
        }

        public void setRq_code(String rq_code) {
            this.rq_code = rq_code;
        }

        public String getProduct_img_url() {
            return product_img_url;
        }

        public void setProduct_img_url(String product_img_url) {
            this.product_img_url = product_img_url;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "head_url='" + head_url + '\'' +
                    ", position='" + position + '\'' +
                    ", phone='" + phone + '\'' +
                    ", rq_code='" + rq_code + '\'' +
                    ", product_img_url='" + product_img_url + '\'' +
                    ", company='" + company + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
