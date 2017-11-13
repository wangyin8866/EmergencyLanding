package com.zyjr.emergencylending.entity;

import java.util.List;

/**
 * @author wangyin
 * @date 2017/11/2.
 * @description :
 */

public class RankBean {

    /**
     * flag : API0000
     * msg : 操作成功
     * ext : null
     * result : {"rankMap":{"nowMonth":[],"before2Month":[{"custom_id":"9","custom_name":"姓名9推荐人1","rank_num":1,"phone":"152****0389","head_url":"F:\\360Downloads\\9","lend_total_amount":31,"mon":"9","name":"姓名9推荐人1"},{"custom_id":"2","custom_name":null,"rank_num":2,"phone":null,"head_url":null,"lend_total_amount":203,"mon":"9","name":null},{"custom_id":"3","custom_name":"袁蕊","rank_num":3,"phone":"137****4663","head_url":"F:\\360Downloads","lend_total_amount":23,"mon":"9","name":"袁蕊"}],"before1Month":[{"custom_id":"9","custom_name":"姓名9推荐人1","rank_num":1,"phone":"152****0389","head_url":"F:\\360Downloads\\9","lend_total_amount":41,"mon":"10","name":"姓名9推荐人1"},{"custom_id":"3","custom_name":"袁蕊","rank_num":2,"phone":"137****4663","head_url":"F:\\360Downloads","lend_total_amount":25,"mon":"10","name":"袁蕊"},{"custom_id":null,"custom_name":null,"rank_num":3,"phone":null,"head_url":null,"lend_total_amount":281,"mon":"10","name":null},{"custom_id":"470830da3e8a45b0b42b45f8bf03d565","custom_name":null,"rank_num":4,"phone":"179****7922","head_url":null,"lend_total_amount":null,"mon":"10","name":null}]},"selfMap":{"nowMonth":{"custom_id":"9af78d0c73304808b6e8acbb8b6f0878","custom_name":"丁晓宇","rank_num":1,"phone":"130****1105","head_url":"M00/09/A7/wKgFGln4SRuEc11kAAAAAEvdijI770.jpg","lend_total_amount":0,"mon":"11","name":"丁晓宇"},"before2Month":{"custom_id":"9af78d0c73304808b6e8acbb8b6f0878","custom_name":"丁晓宇","rank_num":4,"phone":"130****1105","head_url":"M00/09/A7/wKgFGln4SRuEc11kAAAAAEvdijI770.jpg","lend_total_amount":0,"mon":"9","name":"丁晓宇"},"before1Month":{"custom_id":"9af78d0c73304808b6e8acbb8b6f0878","custom_name":"丁晓宇","rank_num":5,"phone":"130****1105","head_url":"M00/09/A7/wKgFGln4SRuEc11kAAAAAEvdijI770.jpg","lend_total_amount":0,"mon":"10","name":"丁晓宇"}}}
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
         * rankMap : {"nowMonth":[],"before2Month":[{"custom_id":"9","custom_name":"姓名9推荐人1","rank_num":1,"phone":"152****0389","head_url":"F:\\360Downloads\\9","lend_total_amount":31,"mon":"9","name":"姓名9推荐人1"},{"custom_id":"2","custom_name":null,"rank_num":2,"phone":null,"head_url":null,"lend_total_amount":203,"mon":"9","name":null},{"custom_id":"3","custom_name":"袁蕊","rank_num":3,"phone":"137****4663","head_url":"F:\\360Downloads","lend_total_amount":23,"mon":"9","name":"袁蕊"}],"before1Month":[{"custom_id":"9","custom_name":"姓名9推荐人1","rank_num":1,"phone":"152****0389","head_url":"F:\\360Downloads\\9","lend_total_amount":41,"mon":"10","name":"姓名9推荐人1"},{"custom_id":"3","custom_name":"袁蕊","rank_num":2,"phone":"137****4663","head_url":"F:\\360Downloads","lend_total_amount":25,"mon":"10","name":"袁蕊"},{"custom_id":null,"custom_name":null,"rank_num":3,"phone":null,"head_url":null,"lend_total_amount":281,"mon":"10","name":null},{"custom_id":"470830da3e8a45b0b42b45f8bf03d565","custom_name":null,"rank_num":4,"phone":"179****7922","head_url":null,"lend_total_amount":null,"mon":"10","name":null}]}
         * selfMap : {"nowMonth":{"custom_id":"9af78d0c73304808b6e8acbb8b6f0878","custom_name":"丁晓宇","rank_num":1,"phone":"130****1105","head_url":"M00/09/A7/wKgFGln4SRuEc11kAAAAAEvdijI770.jpg","lend_total_amount":0,"mon":"11","name":"丁晓宇"},"before2Month":{"custom_id":"9af78d0c73304808b6e8acbb8b6f0878","custom_name":"丁晓宇","rank_num":4,"phone":"130****1105","head_url":"M00/09/A7/wKgFGln4SRuEc11kAAAAAEvdijI770.jpg","lend_total_amount":0,"mon":"9","name":"丁晓宇"},"before1Month":{"custom_id":"9af78d0c73304808b6e8acbb8b6f0878","custom_name":"丁晓宇","rank_num":5,"phone":"130****1105","head_url":"M00/09/A7/wKgFGln4SRuEc11kAAAAAEvdijI770.jpg","lend_total_amount":0,"mon":"10","name":"丁晓宇"}}
         */

        private RankMapBean rankMap;
        private SelfMapBean selfMap;

        public RankMapBean getRankMap() {
            return rankMap;
        }

        public void setRankMap(RankMapBean rankMap) {
            this.rankMap = rankMap;
        }

        public SelfMapBean getSelfMap() {
            return selfMap;
        }

        public void setSelfMap(SelfMapBean selfMap) {
            this.selfMap = selfMap;
        }

        public static class RankMapBean {
            private List<?> nowMonth;
            private List<Before2MonthBean> before2Month;
            private List<Before1MonthBean> before1Month;

            public List<?> getNowMonth() {
                return nowMonth;
            }

            public void setNowMonth(List<?> nowMonth) {
                this.nowMonth = nowMonth;
            }

            public List<Before2MonthBean> getBefore2Month() {
                return before2Month;
            }

            public void setBefore2Month(List<Before2MonthBean> before2Month) {
                this.before2Month = before2Month;
            }

            public List<Before1MonthBean> getBefore1Month() {
                return before1Month;
            }

            public void setBefore1Month(List<Before1MonthBean> before1Month) {
                this.before1Month = before1Month;
            }

            public static class Before2MonthBean {
                /**
                 * custom_id : 9
                 * custom_name : 姓名9推荐人1
                 * rank_num : 1
                 * phone : 152****0389
                 * head_url : F:\360Downloads\9
                 * lend_total_amount : 31.0
                 * mon : 9
                 * name : 姓名9推荐人1
                 */

                private String custom_id;
                private String custom_name;
                private int rank_num;
                private String phone;
                private String head_url;
                private double lend_total_amount;
                private String mon;
                private String name;

                public String getCustom_id() {
                    return custom_id;
                }

                public void setCustom_id(String custom_id) {
                    this.custom_id = custom_id;
                }

                public String getCustom_name() {
                    return custom_name;
                }

                public void setCustom_name(String custom_name) {
                    this.custom_name = custom_name;
                }

                public int getRank_num() {
                    return rank_num;
                }

                public void setRank_num(int rank_num) {
                    this.rank_num = rank_num;
                }

                public String getPhone() {
                    return phone;
                }

                public void setPhone(String phone) {
                    this.phone = phone;
                }

                public String getHead_url() {
                    return head_url;
                }

                public void setHead_url(String head_url) {
                    this.head_url = head_url;
                }

                public double getLend_total_amount() {
                    return lend_total_amount;
                }

                public void setLend_total_amount(double lend_total_amount) {
                    this.lend_total_amount = lend_total_amount;
                }

                public String getMon() {
                    return mon;
                }

                public void setMon(String mon) {
                    this.mon = mon;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class Before1MonthBean {
                /**
                 * custom_id : 9
                 * custom_name : 姓名9推荐人1
                 * rank_num : 1
                 * phone : 152****0389
                 * head_url : F:\360Downloads\9
                 * lend_total_amount : 41.0
                 * mon : 10
                 * name : 姓名9推荐人1
                 */

                private String custom_id;
                private String custom_name;
                private int rank_num;
                private String phone;
                private String head_url;
                private double lend_total_amount;
                private String mon;
                private String name;

                public String getCustom_id() {
                    return custom_id;
                }

                public void setCustom_id(String custom_id) {
                    this.custom_id = custom_id;
                }

                public String getCustom_name() {
                    return custom_name;
                }

                public void setCustom_name(String custom_name) {
                    this.custom_name = custom_name;
                }

                public int getRank_num() {
                    return rank_num;
                }

                public void setRank_num(int rank_num) {
                    this.rank_num = rank_num;
                }

                public String getPhone() {
                    return phone;
                }

                public void setPhone(String phone) {
                    this.phone = phone;
                }

                public String getHead_url() {
                    return head_url;
                }

                public void setHead_url(String head_url) {
                    this.head_url = head_url;
                }

                public double getLend_total_amount() {
                    return lend_total_amount;
                }

                public void setLend_total_amount(double lend_total_amount) {
                    this.lend_total_amount = lend_total_amount;
                }

                public String getMon() {
                    return mon;
                }

                public void setMon(String mon) {
                    this.mon = mon;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }
        }

        public static class SelfMapBean {
            /**
             * nowMonth : {"custom_id":"9af78d0c73304808b6e8acbb8b6f0878","custom_name":"丁晓宇","rank_num":1,"phone":"130****1105","head_url":"M00/09/A7/wKgFGln4SRuEc11kAAAAAEvdijI770.jpg","lend_total_amount":0,"mon":"11","name":"丁晓宇"}
             * before2Month : {"custom_id":"9af78d0c73304808b6e8acbb8b6f0878","custom_name":"丁晓宇","rank_num":4,"phone":"130****1105","head_url":"M00/09/A7/wKgFGln4SRuEc11kAAAAAEvdijI770.jpg","lend_total_amount":0,"mon":"9","name":"丁晓宇"}
             * before1Month : {"custom_id":"9af78d0c73304808b6e8acbb8b6f0878","custom_name":"丁晓宇","rank_num":5,"phone":"130****1105","head_url":"M00/09/A7/wKgFGln4SRuEc11kAAAAAEvdijI770.jpg","lend_total_amount":0,"mon":"10","name":"丁晓宇"}
             */

            private NowMonthBean nowMonth;
            private Before2MonthBeanX before2Month;
            private Before1MonthBeanX before1Month;

            public NowMonthBean getNowMonth() {
                return nowMonth;
            }

            public void setNowMonth(NowMonthBean nowMonth) {
                this.nowMonth = nowMonth;
            }

            public Before2MonthBeanX getBefore2Month() {
                return before2Month;
            }

            public void setBefore2Month(Before2MonthBeanX before2Month) {
                this.before2Month = before2Month;
            }

            public Before1MonthBeanX getBefore1Month() {
                return before1Month;
            }

            public void setBefore1Month(Before1MonthBeanX before1Month) {
                this.before1Month = before1Month;
            }

            public static class NowMonthBean {
                /**
                 * custom_id : 9af78d0c73304808b6e8acbb8b6f0878
                 * custom_name : 丁晓宇
                 * rank_num : 1
                 * phone : 130****1105
                 * head_url : M00/09/A7/wKgFGln4SRuEc11kAAAAAEvdijI770.jpg
                 * lend_total_amount : 0
                 * mon : 11
                 * name : 丁晓宇
                 */

                private String custom_id;
                private String custom_name;
                private String rank_num;
                private String phone;
                private String head_url;
                private String lend_total_amount;
                private String mon;
                private String name;

                public String getCustom_id() {
                    return custom_id;
                }

                public void setCustom_id(String custom_id) {
                    this.custom_id = custom_id;
                }

                public String getCustom_name() {
                    return custom_name;
                }

                public void setCustom_name(String custom_name) {
                    this.custom_name = custom_name;
                }

                public String getRank_num() {
                    return rank_num;
                }

                public void setRank_num(String rank_num) {
                    this.rank_num = rank_num;
                }

                public String getPhone() {
                    return phone;
                }

                public void setPhone(String phone) {
                    this.phone = phone;
                }

                public String getHead_url() {
                    return head_url;
                }

                public void setHead_url(String head_url) {
                    this.head_url = head_url;
                }

                public String getLend_total_amount() {
                    return lend_total_amount;
                }

                public void setLend_total_amount(String lend_total_amount) {
                    this.lend_total_amount = lend_total_amount;
                }

                public String getMon() {
                    return mon;
                }

                public void setMon(String mon) {
                    this.mon = mon;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class Before2MonthBeanX {
                /**
                 * custom_id : 9af78d0c73304808b6e8acbb8b6f0878
                 * custom_name : 丁晓宇
                 * rank_num : 4
                 * phone : 130****1105
                 * head_url : M00/09/A7/wKgFGln4SRuEc11kAAAAAEvdijI770.jpg
                 * lend_total_amount : 0
                 * mon : 9
                 * name : 丁晓宇
                 */

                private String custom_id;
                private String custom_name;
                private int rank_num;
                private String phone;
                private String head_url;
                private int lend_total_amount;
                private String mon;
                private String name;

                public String getCustom_id() {
                    return custom_id;
                }

                public void setCustom_id(String custom_id) {
                    this.custom_id = custom_id;
                }

                public String getCustom_name() {
                    return custom_name;
                }

                public void setCustom_name(String custom_name) {
                    this.custom_name = custom_name;
                }

                public int getRank_num() {
                    return rank_num;
                }

                public void setRank_num(int rank_num) {
                    this.rank_num = rank_num;
                }

                public String getPhone() {
                    return phone;
                }

                public void setPhone(String phone) {
                    this.phone = phone;
                }

                public String getHead_url() {
                    return head_url;
                }

                public void setHead_url(String head_url) {
                    this.head_url = head_url;
                }

                public int getLend_total_amount() {
                    return lend_total_amount;
                }

                public void setLend_total_amount(int lend_total_amount) {
                    this.lend_total_amount = lend_total_amount;
                }

                public String getMon() {
                    return mon;
                }

                public void setMon(String mon) {
                    this.mon = mon;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class Before1MonthBeanX {
                /**
                 * custom_id : 9af78d0c73304808b6e8acbb8b6f0878
                 * custom_name : 丁晓宇
                 * rank_num : 5
                 * phone : 130****1105
                 * head_url : M00/09/A7/wKgFGln4SRuEc11kAAAAAEvdijI770.jpg
                 * lend_total_amount : 0
                 * mon : 10
                 * name : 丁晓宇
                 */

                private String custom_id;
                private String custom_name;
                private int rank_num;
                private String phone;
                private String head_url;
                private int lend_total_amount;
                private String mon;
                private String name;

                public String getCustom_id() {
                    return custom_id;
                }

                public void setCustom_id(String custom_id) {
                    this.custom_id = custom_id;
                }

                public String getCustom_name() {
                    return custom_name;
                }

                public void setCustom_name(String custom_name) {
                    this.custom_name = custom_name;
                }

                public int getRank_num() {
                    return rank_num;
                }

                public void setRank_num(int rank_num) {
                    this.rank_num = rank_num;
                }

                public String getPhone() {
                    return phone;
                }

                public void setPhone(String phone) {
                    this.phone = phone;
                }

                public String getHead_url() {
                    return head_url;
                }

                public void setHead_url(String head_url) {
                    this.head_url = head_url;
                }

                public int getLend_total_amount() {
                    return lend_total_amount;
                }

                public void setLend_total_amount(int lend_total_amount) {
                    this.lend_total_amount = lend_total_amount;
                }

                public String getMon() {
                    return mon;
                }

                public void setMon(String mon) {
                    this.mon = mon;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }
        }
    }
}
