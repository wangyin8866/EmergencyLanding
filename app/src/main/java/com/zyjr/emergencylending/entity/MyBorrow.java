package com.zyjr.emergencylending.entity;

import java.util.List;

/**
 * @author wangyin
 * @date 2017/11/9.
 * @description :
 */

public class MyBorrow {
    /**
     * flag : API0000
     * msg : 操作成功
     * ext : null
     * result : {"his_borrow_list":[{"product_name":null,"loan_amount":null,"loan_period":null,"loan_unit":null,"loan_time":"2017-11-08 15:16:03.0","loan_status":"2","loan_msg":"终审通过","is_out_push":"0","out_push_url":"www.baidu.com","out_push_image_url":"D:\\outLine","out_push_title":"欢迎去去哪儿贷借款","step_status":"5","isRepaymentFlag":"0"},{"product_name":null,"loan_amount":null,"loan_period":null,"loan_unit":null,"loan_time":"2017-11-08 15:16:03.0","loan_status":"2","loan_msg":"终审通过","is_out_push":"0","out_push_url":"www.baidu.com","out_push_image_url":"D:\\outLine","out_push_title":"欢迎去去哪儿贷借款","step_status":"5","isRepaymentFlag":"0"}],"pageNo":1,"current_borrow":{"product_name":null,"loan_amount":null,"loan_period":null,"loan_unit":null,"loan_time":"2017-11-08 15:16:03.0","loan_status":"2","loan_msg":"终审通过","is_out_push":"0","out_push_url":"www.baidu.com","out_push_image_url":"D:\\outLine","out_push_title":"欢迎去去哪儿贷借款","step_status":"5","isRepaymentFlag":"0"},"total_count":0}
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
         * his_borrow_list : [{"product_name":null,"loan_amount":null,"loan_period":null,"loan_unit":null,"loan_time":"2017-11-08 15:16:03.0","loan_status":"2","loan_msg":"终审通过","is_out_push":"0","out_push_url":"www.baidu.com","out_push_image_url":"D:\\outLine","out_push_title":"欢迎去去哪儿贷借款","step_status":"5","isRepaymentFlag":"0"},{"product_name":null,"loan_amount":null,"loan_period":null,"loan_unit":null,"loan_time":"2017-11-08 15:16:03.0","loan_status":"2","loan_msg":"终审通过","is_out_push":"0","out_push_url":"www.baidu.com","out_push_image_url":"D:\\outLine","out_push_title":"欢迎去去哪儿贷借款","step_status":"5","isRepaymentFlag":"0"}]
         * pageNo : 1
         * current_borrow : {"product_name":null,"loan_amount":null,"loan_period":null,"loan_unit":null,"loan_time":"2017-11-08 15:16:03.0","loan_status":"2","loan_msg":"终审通过","is_out_push":"0","out_push_url":"www.baidu.com","out_push_image_url":"D:\\outLine","out_push_title":"欢迎去去哪儿贷借款","step_status":"5","isRepaymentFlag":"0"}
         * total_count : 0
         */

        private int pageNo;
        private CurrentBorrowBean current_borrow;
        private int total_count;
        private List<HisBorrowListBean> his_borrow_list;

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public CurrentBorrowBean getCurrent_borrow() {
            return current_borrow;
        }

        public void setCurrent_borrow(CurrentBorrowBean current_borrow) {
            this.current_borrow = current_borrow;
        }

        public int getTotal_count() {
            return total_count;
        }

        public void setTotal_count(int total_count) {
            this.total_count = total_count;
        }

        public List<HisBorrowListBean> getHis_borrow_list() {
            return his_borrow_list;
        }

        public void setHis_borrow_list(List<HisBorrowListBean> his_borrow_list) {
            this.his_borrow_list = his_borrow_list;
        }

        public static class CurrentBorrowBean {
            /**
             * product_name : null
             * loan_amount : null
             * loan_period : null
             * loan_unit : null
             * loan_time : 2017-11-08 15:16:03.0
             * loan_status : 2
             * loan_msg : 终审通过
             * is_out_push : 0
             * out_push_url : www.baidu.com
             * out_push_image_url : D:\outLine
             * out_push_title : 欢迎去去哪儿贷借款
             * step_status : 5
             * isRepaymentFlag : 0
             */

            private String product_name;
            private String loan_amount;
            private String loan_period;
            private String loan_unit;
            private String loan_time;
            private String loan_status;
            private String loan_msg;
            private String is_out_push;
            private String out_push_url;
            private String out_push_image_url;
            private String out_push_title;
            private String step_status;
            private String isRepaymentFlag;

            public String getProduct_name() {
                return product_name;
            }

            public void setProduct_name(String product_name) {
                this.product_name = product_name;
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

            public String getLoan_unit() {
                return loan_unit;
            }

            public void setLoan_unit(String loan_unit) {
                this.loan_unit = loan_unit;
            }

            public String getLoan_time() {
                return loan_time;
            }

            public void setLoan_time(String loan_time) {
                this.loan_time = loan_time;
            }

            public String getLoan_status() {
                return loan_status;
            }

            public void setLoan_status(String loan_status) {
                this.loan_status = loan_status;
            }

            public String getLoan_msg() {
                return loan_msg;
            }

            public void setLoan_msg(String loan_msg) {
                this.loan_msg = loan_msg;
            }

            public String getIs_out_push() {
                return is_out_push;
            }

            public void setIs_out_push(String is_out_push) {
                this.is_out_push = is_out_push;
            }

            public String getOut_push_url() {
                return out_push_url;
            }

            public void setOut_push_url(String out_push_url) {
                this.out_push_url = out_push_url;
            }

            public String getOut_push_image_url() {
                return out_push_image_url;
            }

            public void setOut_push_image_url(String out_push_image_url) {
                this.out_push_image_url = out_push_image_url;
            }

            public String getOut_push_title() {
                return out_push_title;
            }

            public void setOut_push_title(String out_push_title) {
                this.out_push_title = out_push_title;
            }

            public String getStep_status() {
                return step_status;
            }

            public void setStep_status(String step_status) {
                this.step_status = step_status;
            }

            public String getIsRepaymentFlag() {
                return isRepaymentFlag;
            }

            public void setIsRepaymentFlag(String isRepaymentFlag) {
                this.isRepaymentFlag = isRepaymentFlag;
            }
        }

        public static class HisBorrowListBean {
            /**
             * product_name : null
             * loan_amount : null
             * loan_period : null
             * loan_unit : null
             * loan_time : 2017-11-08 15:16:03.0
             * loan_status : 2
             * loan_msg : 终审通过
             * is_out_push : 0
             * out_push_url : www.baidu.com
             * out_push_image_url : D:\outLine
             * out_push_title : 欢迎去去哪儿贷借款
             * step_status : 5
             * isRepaymentFlag : 0
             */

            private String product_name;
            private String loan_amount;
            private String loan_period;
            private String loan_unit;
            private String loan_time;
            private String loan_status;
            private String loan_msg;
            private String is_out_push;
            private String out_push_url;
            private String out_push_image_url;
            private String out_push_title;
            private String step_status;
            private String isRepaymentFlag;

            public String getProduct_name() {
                return product_name;
            }

            public void setProduct_name(String product_name) {
                this.product_name = product_name;
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

            public String getLoan_unit() {
                return loan_unit;
            }

            public void setLoan_unit(String loan_unit) {
                this.loan_unit = loan_unit;
            }

            public String getLoan_time() {
                return loan_time;
            }

            public void setLoan_time(String loan_time) {
                this.loan_time = loan_time;
            }

            public String getLoan_status() {
                return loan_status;
            }

            public void setLoan_status(String loan_status) {
                this.loan_status = loan_status;
            }

            public String getLoan_msg() {
                return loan_msg;
            }

            public void setLoan_msg(String loan_msg) {
                this.loan_msg = loan_msg;
            }

            public String getIs_out_push() {
                return is_out_push;
            }

            public void setIs_out_push(String is_out_push) {
                this.is_out_push = is_out_push;
            }

            public String getOut_push_url() {
                return out_push_url;
            }

            public void setOut_push_url(String out_push_url) {
                this.out_push_url = out_push_url;
            }

            public String getOut_push_image_url() {
                return out_push_image_url;
            }

            public void setOut_push_image_url(String out_push_image_url) {
                this.out_push_image_url = out_push_image_url;
            }

            public String getOut_push_title() {
                return out_push_title;
            }

            public void setOut_push_title(String out_push_title) {
                this.out_push_title = out_push_title;
            }

            public String getStep_status() {
                return step_status;
            }

            public void setStep_status(String step_status) {
                this.step_status = step_status;
            }

            public String getIsRepaymentFlag() {
                return isRepaymentFlag;
            }

            public void setIsRepaymentFlag(String isRepaymentFlag) {
                this.isRepaymentFlag = isRepaymentFlag;
            }
        }
    }
}
