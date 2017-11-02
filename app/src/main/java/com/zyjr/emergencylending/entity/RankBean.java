package com.zyjr.emergencylending.entity;

import com.google.gson.annotations.SerializedName;

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
     * result : {"rankMap":{"10":[{"custom_id":"9","custom_name":"姓名9推荐人1","rank_num":1,"phone":"152****0389","head_url":"F:\\360Downloads\\9","lend_total_amount":41,"mon":"10","name":"姓名9推荐人1"},{"custom_id":"3","custom_name":"1","rank_num":2,"phone":"180****7692","head_url":"F:\\360Downloads","lend_total_amount":25,"mon":"10","name":"1"},{"custom_id":"1","custom_name":null,"rank_num":3,"phone":null,"head_url":null,"lend_total_amount":268,"mon":"10","name":null},{"custom_id":"2","custom_name":"姓名2推荐人1","rank_num":4,"phone":"152****0382","head_url":"F:\\360Downloads","lend_total_amount":13,"mon":"10","name":"姓名2推荐人1"},{"custom_id":"470830da3e8a45b0b42b45f8bf03d565","custom_name":null,"rank_num":5,"phone":"179****7922","head_url":null,"lend_total_amount":null,"mon":"10","name":null}],"9":[{"custom_id":"9","custom_name":"姓名9推荐人1","rank_num":1,"phone":"152****0389","head_url":"F:\\360Downloads\\9","lend_total_amount":31,"mon":"9","name":"姓名9推荐人1"},{"custom_id":"4","custom_name":null,"rank_num":2,"phone":null,"head_url":null,"lend_total_amount":162,"mon":"9","name":null},{"custom_id":"2","custom_name":"姓名2推荐人1","rank_num":3,"phone":"152****0382","head_url":"F:\\360Downloads","lend_total_amount":41,"mon":"9","name":"姓名2推荐人1"},{"custom_id":"3","custom_name":"1","rank_num":4,"phone":"180****7692","head_url":"F:\\360Downloads","lend_total_amount":23,"mon":"9","name":"1"}],"11":[]},"selfMap":{"10":{"custom_id":"9af78d0c73304808b6e8acbb8b6f0878","custom_name":"丁晓宇","rank_num":5,"phone":"130****1105","head_url":"M00/09/A7/wKgFGln4SRuEc11kAAAAAEvdijI770.jpg","lend_total_amount":0,"mon":"9","name":"丁晓宇"},"9":{"custom_id":"9af78d0c73304808b6e8acbb8b6f0878","custom_name":"丁晓宇","rank_num":5,"phone":"130****1105","head_url":"M00/09/A7/wKgFGln4SRuEc11kAAAAAEvdijI770.jpg","lend_total_amount":0,"mon":"9","name":"丁晓宇"},"11":{"custom_id":"9af78d0c73304808b6e8acbb8b6f0878","custom_name":"丁晓宇","rank_num":5,"phone":"130****1105","head_url":"M00/09/A7/wKgFGln4SRuEc11kAAAAAEvdijI770.jpg","lend_total_amount":0,"mon":"9","name":"丁晓宇"}}}
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
         * rankMap : {"10":[{"custom_id":"9","custom_name":"姓名9推荐人1","rank_num":1,"phone":"152****0389","head_url":"F:\\360Downloads\\9","lend_total_amount":41,"mon":"10","name":"姓名9推荐人1"},{"custom_id":"3","custom_name":"1","rank_num":2,"phone":"180****7692","head_url":"F:\\360Downloads","lend_total_amount":25,"mon":"10","name":"1"},{"custom_id":"1","custom_name":null,"rank_num":3,"phone":null,"head_url":null,"lend_total_amount":268,"mon":"10","name":null},{"custom_id":"2","custom_name":"姓名2推荐人1","rank_num":4,"phone":"152****0382","head_url":"F:\\360Downloads","lend_total_amount":13,"mon":"10","name":"姓名2推荐人1"},{"custom_id":"470830da3e8a45b0b42b45f8bf03d565","custom_name":null,"rank_num":5,"phone":"179****7922","head_url":null,"lend_total_amount":null,"mon":"10","name":null}],"9":[{"custom_id":"9","custom_name":"姓名9推荐人1","rank_num":1,"phone":"152****0389","head_url":"F:\\360Downloads\\9","lend_total_amount":31,"mon":"9","name":"姓名9推荐人1"},{"custom_id":"4","custom_name":null,"rank_num":2,"phone":null,"head_url":null,"lend_total_amount":162,"mon":"9","name":null},{"custom_id":"2","custom_name":"姓名2推荐人1","rank_num":3,"phone":"152****0382","head_url":"F:\\360Downloads","lend_total_amount":41,"mon":"9","name":"姓名2推荐人1"},{"custom_id":"3","custom_name":"1","rank_num":4,"phone":"180****7692","head_url":"F:\\360Downloads","lend_total_amount":23,"mon":"9","name":"1"}],"11":[]}
         * selfMap : {"10":{"custom_id":"9af78d0c73304808b6e8acbb8b6f0878","custom_name":"丁晓宇","rank_num":5,"phone":"130****1105","head_url":"M00/09/A7/wKgFGln4SRuEc11kAAAAAEvdijI770.jpg","lend_total_amount":0,"mon":"9","name":"丁晓宇"},"9":{"custom_id":"9af78d0c73304808b6e8acbb8b6f0878","custom_name":"丁晓宇","rank_num":5,"phone":"130****1105","head_url":"M00/09/A7/wKgFGln4SRuEc11kAAAAAEvdijI770.jpg","lend_total_amount":0,"mon":"9","name":"丁晓宇"},"11":{"custom_id":"9af78d0c73304808b6e8acbb8b6f0878","custom_name":"丁晓宇","rank_num":5,"phone":"130****1105","head_url":"M00/09/A7/wKgFGln4SRuEc11kAAAAAEvdijI770.jpg","lend_total_amount":0,"mon":"9","name":"丁晓宇"}}
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
            @SerializedName("10")
            private List<_$10Bean> _$10;
            @SerializedName("9")
            private List<_$9Bean> _$9;
            @SerializedName("11")
            private List<?> _$11;

            public List<_$10Bean> get_$10() {
                return _$10;
            }

            public void set_$10(List<_$10Bean> _$10) {
                this._$10 = _$10;
            }

            public List<_$9Bean> get_$9() {
                return _$9;
            }

            public void set_$9(List<_$9Bean> _$9) {
                this._$9 = _$9;
            }

            public List<?> get_$11() {
                return _$11;
            }

            public void set_$11(List<?> _$11) {
                this._$11 = _$11;
            }

            public static class _$10Bean {
                /**
                 * custom_id : 9
                 * custom_name : 姓名9推荐人1
                 * rank_num : 1
                 * phone : 152****0389
                 * head_url : F:\360Downloads\9
                 * lend_total_amount : 41
                 * mon : 10
                 * name : 姓名9推荐人1
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

            public static class _$9Bean {
                /**
                 * custom_id : 9
                 * custom_name : 姓名9推荐人1
                 * rank_num : 1
                 * phone : 152****0389
                 * head_url : F:\360Downloads\9
                 * lend_total_amount : 31
                 * mon : 9
                 * name : 姓名9推荐人1
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

        public static class SelfMapBean {
            /**
             * 10 : {"custom_id":"9af78d0c73304808b6e8acbb8b6f0878","custom_name":"丁晓宇","rank_num":5,"phone":"130****1105","head_url":"M00/09/A7/wKgFGln4SRuEc11kAAAAAEvdijI770.jpg","lend_total_amount":0,"mon":"9","name":"丁晓宇"}
             * 9 : {"custom_id":"9af78d0c73304808b6e8acbb8b6f0878","custom_name":"丁晓宇","rank_num":5,"phone":"130****1105","head_url":"M00/09/A7/wKgFGln4SRuEc11kAAAAAEvdijI770.jpg","lend_total_amount":0,"mon":"9","name":"丁晓宇"}
             * 11 : {"custom_id":"9af78d0c73304808b6e8acbb8b6f0878","custom_name":"丁晓宇","rank_num":5,"phone":"130****1105","head_url":"M00/09/A7/wKgFGln4SRuEc11kAAAAAEvdijI770.jpg","lend_total_amount":0,"mon":"9","name":"丁晓宇"}
             */

            @SerializedName("10")
            private _$10BeanX _$10;
            @SerializedName("9")
            private _$9BeanX _$9;
            @SerializedName("11")
            private _$11Bean _$11;

            public _$10BeanX get_$10() {
                return _$10;
            }

            public void set_$10(_$10BeanX _$10) {
                this._$10 = _$10;
            }

            public _$9BeanX get_$9() {
                return _$9;
            }

            public void set_$9(_$9BeanX _$9) {
                this._$9 = _$9;
            }

            public _$11Bean get_$11() {
                return _$11;
            }

            public void set_$11(_$11Bean _$11) {
                this._$11 = _$11;
            }

            public static class _$10BeanX {
                /**
                 * custom_id : 9af78d0c73304808b6e8acbb8b6f0878
                 * custom_name : 丁晓宇
                 * rank_num : 5
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

            public static class _$9BeanX {
                /**
                 * custom_id : 9af78d0c73304808b6e8acbb8b6f0878
                 * custom_name : 丁晓宇
                 * rank_num : 5
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

            public static class _$11Bean {
                /**
                 * custom_id : 9af78d0c73304808b6e8acbb8b6f0878
                 * custom_name : 丁晓宇
                 * rank_num : 5
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
        }
    }
}
