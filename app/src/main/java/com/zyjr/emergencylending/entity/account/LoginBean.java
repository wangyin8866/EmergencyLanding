package com.zyjr.emergencylending.entity.account;

/**
 * Created by wangyin on 2017/10/24.
 */

public class LoginBean {

    /**
     * flag : 0000
     * msg : 请求成功
     * result : {"juid":"aaaaa","login_token":"a","user_type":"a","recommendCode":"a","Is_input_validcode":"a"}
     */

    private String flag;
    private String msg;
    private ResultBean result;

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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * juid : aaaaa
         * login_token : a
         * user_type : a
         * recommendCode : a
         * Is_input_validcode : a
         */

        private String juid;
        private String login_token;
        private String user_type;
        private String recommendCode;
        private String Is_input_validcode;

        public String getJuid() {
            return juid;
        }

        public void setJuid(String juid) {
            this.juid = juid;
        }

        public String getLogin_token() {
            return login_token;
        }

        public void setLogin_token(String login_token) {
            this.login_token = login_token;
        }

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public String getRecommendCode() {
            return recommendCode;
        }

        public void setRecommendCode(String recommendCode) {
            this.recommendCode = recommendCode;
        }

        public String getIs_input_validcode() {
            return Is_input_validcode;
        }

        public void setIs_input_validcode(String Is_input_validcode) {
            this.Is_input_validcode = Is_input_validcode;
        }
    }
}
