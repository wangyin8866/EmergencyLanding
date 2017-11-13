package com.zyjr.emergencylending.entity;

/**
 * @author wangyin
 * @date 2017/11/9.
 * @description :
 */

public class RepaymentSuccess {

    /**
     * result : {"userId":"1077266","userState":"1","userStateDetail":null}
     * returncode : 0000
     * errormsg : 登陆成功
     * token : gyl29fUwKB3ED53UZNcCJKt+87Rz2/iPL92YytF0yF7bICpqiOllhw==
     */
    private String promptMsg;
    public String getPromptMsg() {
        return promptMsg;
    }

    public void setPromptMsg(String promptMsg) {
        this.promptMsg = promptMsg;
    }
    private ResultBean result;
    private String returncode;
    private String errormsg;
    private String token;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getReturncode() {
        return returncode;
    }

    public void setReturncode(String returncode) {
        this.returncode = returncode;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class ResultBean {
        /**
         * userId : 1077266
         * userState : 1
         * userStateDetail : null
         */

        private String userId;
        private String userState;
        private Object userStateDetail;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserState() {
            return userState;
        }

        public void setUserState(String userState) {
            this.userState = userState;
        }

        public Object getUserStateDetail() {
            return userStateDetail;
        }

        public void setUserStateDetail(Object userStateDetail) {
            this.userStateDetail = userStateDetail;
        }
    }
}
