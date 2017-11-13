package com.zyjr.emergencylending.entity;

/**
 * @author wangyin
 * @date 2017/11/2.
 * @description :
 */

public class UserStatus {

    /**
     * flag : API0000
     * msg : 操作成功
     * ext : null
     * result : {"user_contact_status":0,"user_job_status":0,"user_contact_edit":"1","is_loan_fail":"0","user_data_edit":"1","user_bank_status":0,"user_job_edit":"1","user_data_status":0,"user_bank_edit":"1"}
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
         * user_contact_status : 0
         * user_job_status : 0
         * user_contact_edit : 1
         * is_loan_fail : 0
         * user_data_edit : 1
         * user_bank_status : 0
         * user_job_edit : 1
         * user_data_status : 0
         * user_bank_edit : 1
         */

        private String user_contact_status;
        private String user_job_status;
        private String user_contact_edit;
        private String is_loan_fail;
        private String user_data_edit;
        private String user_bank_status;
        private String user_job_edit;
        private String user_data_status;
        private String user_bank_edit;

        public String getUser_contact_status() {
            return user_contact_status;
        }

        public void setUser_contact_status(String user_contact_status) {
            this.user_contact_status = user_contact_status;
        }

        public String getUser_job_status() {
            return user_job_status;
        }

        public void setUser_job_status(String user_job_status) {
            this.user_job_status = user_job_status;
        }

        public String getUser_contact_edit() {
            return user_contact_edit;
        }

        public void setUser_contact_edit(String user_contact_edit) {
            this.user_contact_edit = user_contact_edit;
        }

        public String getIs_loan_fail() {
            return is_loan_fail;
        }

        public void setIs_loan_fail(String is_loan_fail) {
            this.is_loan_fail = is_loan_fail;
        }

        public String getUser_data_edit() {
            return user_data_edit;
        }

        public void setUser_data_edit(String user_data_edit) {
            this.user_data_edit = user_data_edit;
        }

        public String getUser_bank_status() {
            return user_bank_status;
        }

        public void setUser_bank_status(String user_bank_status) {
            this.user_bank_status = user_bank_status;
        }

        public String getUser_job_edit() {
            return user_job_edit;
        }

        public void setUser_job_edit(String user_job_edit) {
            this.user_job_edit = user_job_edit;
        }

        public String getUser_data_status() {
            return user_data_status;
        }

        public void setUser_data_status(String user_data_status) {
            this.user_data_status = user_data_status;
        }

        public String getUser_bank_edit() {
            return user_bank_edit;
        }

        public void setUser_bank_edit(String user_bank_edit) {
            this.user_bank_edit = user_bank_edit;
        }
    }
}
