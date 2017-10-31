package com.zyjr.emergencylending.entity;

import java.util.List;

/**
 * @author wangyin
 * @date 2017/10/31.
 * @description :
 */

public class NoticeBean {

    /**
     * flag : API0000
     * msg : 操作成功
     * ext : null
     * result : {"resultList":[{"title":"未来公告11","infor":"title:未来公告11   ,begin_date:2017-10-18 14:07:53   ,end_date2017-10-20 14:07:58"},{"title":"展示公告01","infor":"title:展示公告01   ,begin_date:2017-10-01 14:06:49   ,end_date2017-10-18 14:06:54"},{"title":"展示公告10","infor":"title:展示公告10   ,begin_date:2017-10-04 14:06:49   ,end_date2017-10-21 14:06:54"}]}
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
        private List<ResultListBean> resultList;

        public List<ResultListBean> getResultList() {
            return resultList;
        }

        public void setResultList(List<ResultListBean> resultList) {
            this.resultList = resultList;
        }

        public static class ResultListBean {
            /**
             * title : 未来公告11
             * infor : title:未来公告11   ,begin_date:2017-10-18 14:07:53   ,end_date2017-10-20 14:07:58
             */

            private String title;
            private String infor;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getInfor() {
                return infor;
            }

            public void setInfor(String infor) {
                this.infor = infor;
            }
        }
    }
}
