package com.zyjr.emergencylending.entity;

import java.util.List;

/**
 * @author wangyin
 * @date 2017/11/2.
 * @description :
 */

public class WaitApplyBean {
    /**
     * flag : API0000
     * msg : 操作成功
     * ext : null
     * result : {"clerkRecordList":[{"cust_name":null,"cust_phone":"17917917913","loan_amount":"5000.00","loan_period":"5","order_status":"10","order_time":"2017-10-30 14:51:16"},{"cust_name":null,"cust_phone":"17917917913","loan_amount":"5000.00","loan_period":"5","order_status":"10","order_time":"2017-10-30 15:07:00"},{"cust_name":null,"cust_phone":"17917917913","loan_amount":"5000.00","loan_period":"5","order_status":"10","order_time":"2017-10-30 15:13:32"},{"cust_name":null,"cust_phone":"17917917913","loan_amount":"5000.00","loan_period":"5","order_status":"10","order_time":"2017-10-30 15:08:04"}]}
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
        private List<ClerkRecordListBean> clerkRecordList;

        public List<ClerkRecordListBean> getClerkRecordList() {
            return clerkRecordList;
        }

        public void setClerkRecordList(List<ClerkRecordListBean> clerkRecordList) {
            this.clerkRecordList = clerkRecordList;
        }

        public static class ClerkRecordListBean {
            /**
             * cust_name : null
             * cust_phone : 17917917913
             * loan_amount : 5000.00
             * loan_period : 5
             * order_status : 10
             * order_time : 2017-10-30 14:51:16
             */

            private String cust_name;
            private String cust_phone;
            private String loan_amount;
            private String loan_period;
            private String order_status;
            private String order_time;
            private String step_status;
            private String loan_periods_unit;

            public String getLoan_periods_unit() {
                return loan_periods_unit;
            }

            public void setLoan_periods_unit(String loan_periods_unit) {
                this.loan_periods_unit = loan_periods_unit;
            }

            public String getStep_status() {
                return step_status;
            }

            public void setStep_status(String step_status) {
                this.step_status = step_status;
            }

            public String getCust_name() {
                return cust_name;
            }

            public void setCust_name(String cust_name) {
                this.cust_name = cust_name;
            }

            public String getCust_phone() {
                return cust_phone;
            }

            public void setCust_phone(String cust_phone) {
                this.cust_phone = cust_phone;
            }

            public String getLoan_amount() {
                return loan_amount;
            }

            public void setLoan_amount(String loan_amount) {
                this.loan_amount = loan_amount;
            }

            public String getLoan_period() {
                return loan_period;
            }

            public void setLoan_period(String loan_period) {
                this.loan_period = loan_period;
            }

            public String getOrder_status() {
                return order_status;
            }

            public void setOrder_status(String order_status) {
                this.order_status = order_status;
            }

            public String getOrder_time() {
                return order_time;
            }

            public void setOrder_time(String order_time) {
                this.order_time = order_time;
            }
        }
    }
}
