package com.zyjr.emergencylending.entity;

import java.util.List;

/**
 *
 * @author wangyin
 * @date 2017/10/11
 */

public class MessageBean {

    /**
     * flag : API0000
     * msg : 操作成功
     * ext : null
     * result : {"pageNo":"1","resultList":[{"create_date":"2017-10-16 16:31:08","news_id":"20","news_title":"已读消息","news_detail":"已读消息newstype2,status:2","news_type":2,"status":2},{"create_date":"2017-10-16 16:31:00","news_id":"19","news_title":"已读消息","news_detail":"已读消息newstype1,status:2","news_type":1,"status":2},{"create_date":"2017-10-16 16:30:58","news_id":"18","news_title":"未读消息","news_detail":"未读消息newstype3,status:1","news_type":3,"status":1},{"create_date":"2017-10-16 16:30:51","news_id":"17","news_title":"未读消息","news_detail":"未读消息newstype2,status:1","news_type":2,"status":1},{"create_date":"2017-10-16 16:30:48","news_id":"16","news_title":"未读消息","news_detail":"未读消息newstype1,status:1","news_type":1,"status":1},{"create_date":"2017-10-16 16:30:47","news_id":"15","news_title":"未读消息","news_detail":"未读消息newstype3,status:1","news_type":3,"status":1},{"create_date":"2017-10-16 16:30:45","news_id":"14","news_title":"未读消息","news_detail":"未读消息newstype2,status:1","news_type":2,"status":1},{"create_date":"2017-10-16 16:30:44","news_id":"13","news_title":"未读消息","news_detail":"未读消息newstype1,status:1","news_type":1,"status":1},{"create_date":"2017-10-16 16:30:42","news_id":"12","news_title":"未读消息","news_detail":"未读消息newstype3,status:1","news_type":3,"status":1},{"create_date":"2017-10-16 16:30:41","news_id":"11","news_title":"未读消息","news_detail":"未读消息newstype2,status:1","news_type":2,"status":1},{"create_date":"2017-10-16 16:30:39","news_id":"10","news_title":"未读消息","news_detail":"未读消息newstype1,status:1","news_type":1,"status":2},{"create_date":"2017-10-16 16:30:37","news_id":"9","news_title":"已读消息","news_detail":"已读消息newstype3,status:2","news_type":3,"status":2},{"create_date":"2017-10-16 16:30:36","news_id":"8","news_title":"已读消息","news_detail":"已读消息newstype2,status:2","news_type":2,"status":2},{"create_date":"2017-10-16 16:30:34","news_id":"7","news_title":"已读消息","news_detail":"已读消息newstype1,status:2","news_type":1,"status":2},{"create_date":"2017-10-16 16:30:33","news_id":"6","news_title":"已读消息","news_detail":"已读消息newstype2,status:2","news_type":2,"status":2},{"create_date":"2017-10-16 16:30:31","news_id":"4","news_title":"已读消息","news_detail":"已读消息newstype1,status:2","news_type":1,"status":2},{"create_date":"2017-10-16 16:30:31","news_id":"5","news_title":"已读消息","news_detail":"已读消息newstype2,status:2","news_type":2,"status":2},{"create_date":"2017-10-16 16:29:56","news_id":"3","news_title":"未读消息","news_detail":"未读消息newstype3,status:1","news_type":3,"status":1},{"create_date":"2017-10-16 16:28:19","news_id":"2","news_title":"未读消息","news_detail":"未读消息newstype2,status:1","news_type":2,"status":1}],"total_count":19}
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
         * pageNo : 1
         * resultList : [{"create_date":"2017-10-16 16:31:08","news_id":"20","news_title":"已读消息","news_detail":"已读消息newstype2,status:2","news_type":2,"status":2},{"create_date":"2017-10-16 16:31:00","news_id":"19","news_title":"已读消息","news_detail":"已读消息newstype1,status:2","news_type":1,"status":2},{"create_date":"2017-10-16 16:30:58","news_id":"18","news_title":"未读消息","news_detail":"未读消息newstype3,status:1","news_type":3,"status":1},{"create_date":"2017-10-16 16:30:51","news_id":"17","news_title":"未读消息","news_detail":"未读消息newstype2,status:1","news_type":2,"status":1},{"create_date":"2017-10-16 16:30:48","news_id":"16","news_title":"未读消息","news_detail":"未读消息newstype1,status:1","news_type":1,"status":1},{"create_date":"2017-10-16 16:30:47","news_id":"15","news_title":"未读消息","news_detail":"未读消息newstype3,status:1","news_type":3,"status":1},{"create_date":"2017-10-16 16:30:45","news_id":"14","news_title":"未读消息","news_detail":"未读消息newstype2,status:1","news_type":2,"status":1},{"create_date":"2017-10-16 16:30:44","news_id":"13","news_title":"未读消息","news_detail":"未读消息newstype1,status:1","news_type":1,"status":1},{"create_date":"2017-10-16 16:30:42","news_id":"12","news_title":"未读消息","news_detail":"未读消息newstype3,status:1","news_type":3,"status":1},{"create_date":"2017-10-16 16:30:41","news_id":"11","news_title":"未读消息","news_detail":"未读消息newstype2,status:1","news_type":2,"status":1},{"create_date":"2017-10-16 16:30:39","news_id":"10","news_title":"未读消息","news_detail":"未读消息newstype1,status:1","news_type":1,"status":2},{"create_date":"2017-10-16 16:30:37","news_id":"9","news_title":"已读消息","news_detail":"已读消息newstype3,status:2","news_type":3,"status":2},{"create_date":"2017-10-16 16:30:36","news_id":"8","news_title":"已读消息","news_detail":"已读消息newstype2,status:2","news_type":2,"status":2},{"create_date":"2017-10-16 16:30:34","news_id":"7","news_title":"已读消息","news_detail":"已读消息newstype1,status:2","news_type":1,"status":2},{"create_date":"2017-10-16 16:30:33","news_id":"6","news_title":"已读消息","news_detail":"已读消息newstype2,status:2","news_type":2,"status":2},{"create_date":"2017-10-16 16:30:31","news_id":"4","news_title":"已读消息","news_detail":"已读消息newstype1,status:2","news_type":1,"status":2},{"create_date":"2017-10-16 16:30:31","news_id":"5","news_title":"已读消息","news_detail":"已读消息newstype2,status:2","news_type":2,"status":2},{"create_date":"2017-10-16 16:29:56","news_id":"3","news_title":"未读消息","news_detail":"未读消息newstype3,status:1","news_type":3,"status":1},{"create_date":"2017-10-16 16:28:19","news_id":"2","news_title":"未读消息","news_detail":"未读消息newstype2,status:1","news_type":2,"status":1}]
         * total_count : 19
         */

        private String pageNo;
        private int total_count;
        private List<ResultListBean> resultList;

        public String getPageNo() {
            return pageNo;
        }

        public void setPageNo(String pageNo) {
            this.pageNo = pageNo;
        }

        public int getTotal_count() {
            return total_count;
        }

        public void setTotal_count(int total_count) {
            this.total_count = total_count;
        }

        public List<ResultListBean> getResultList() {
            return resultList;
        }

        public void setResultList(List<ResultListBean> resultList) {
            this.resultList = resultList;
        }

        public static class ResultListBean {
            /**
             * create_date : 2017-10-16 16:31:08
             * news_id : 20
             * news_title : 已读消息
             * news_detail : 已读消息newstype2,status:2
             * news_type : 2
             * status : 2
             */

            private String create_date;
            private String news_id;
            private String news_title;
            private String news_detail;
            private int news_type;
            private int status;

            public String getCreate_date() {
                return create_date;
            }

            public void setCreate_date(String create_date) {
                this.create_date = create_date;
            }

            public String getNews_id() {
                return news_id;
            }

            public void setNews_id(String news_id) {
                this.news_id = news_id;
            }

            public String getNews_title() {
                return news_title;
            }

            public void setNews_title(String news_title) {
                this.news_title = news_title;
            }

            public String getNews_detail() {
                return news_detail;
            }

            public void setNews_detail(String news_detail) {
                this.news_detail = news_detail;
            }

            public int getNews_type() {
                return news_type;
            }

            public void setNews_type(int news_type) {
                this.news_type = news_type;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}
