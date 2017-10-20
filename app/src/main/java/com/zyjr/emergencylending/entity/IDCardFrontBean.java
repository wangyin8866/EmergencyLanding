package com.zyjr.emergencylending.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by neil on 2017/10/19
 * 备注:
 */

public class IDCardFrontBean implements Serializable{

    private static final long serialVersionUID = -2368447930686205050L;

    /**
     * race : 汉
     * name : 张三
     * time_used : 401
     * gender : 男
     * id_card_number : 42118******43332
     * request_id : 1508396808,4eff9815-73f4-46d1-a90d-36c732fe1c2c
     * birthday : {"year":"1994","day":"4","month":"14"}
     * legality : {"Edited":0,"Photocopy":0,"ID Photo":0.9,"Screen":0.1,"Temporary ID Photo":0}
     * address : 湖南江汉区西北湖路1号
     * head_rect : {"rt":{"y":0.18863636,"x":0.8832418},"lt":{"y":0.18863636,"x":0.60027474},"lb":{"y":0.71590906,"x":0.614011},"rb":{"y":0.71590906,"x":0.90796703}}
     * side : front
     */
    private String race;
    private String name;
    private int time_used;
    private String gender;
    private String id_card_number;
    private String request_id;
    private BirthdayBean birthday;
    private LegalityBean legality;
    private String address;
    private HeadRectBean head_rect;
    private String side;

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTime_used() {
        return time_used;
    }

    public void setTime_used(int time_used) {
        this.time_used = time_used;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId_card_number() {
        return id_card_number;
    }

    public void setId_card_number(String id_card_number) {
        this.id_card_number = id_card_number;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public BirthdayBean getBirthday() {
        return birthday;
    }

    public void setBirthday(BirthdayBean birthday) {
        this.birthday = birthday;
    }

    public LegalityBean getLegality() {
        return legality;
    }

    public void setLegality(LegalityBean legality) {
        this.legality = legality;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public HeadRectBean getHead_rect() {
        return head_rect;
    }

    public void setHead_rect(HeadRectBean head_rect) {
        this.head_rect = head_rect;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public static class BirthdayBean {
        /**
         * year : 1992
         * day : 4
         * month : 4
         */

        private String year;
        private String day;
        private String month;

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }
    }

    public static class LegalityBean {
        /**
         * Edited : 0.0
         * Photocopy : 0.0
         * ID Photo : 0.9
         * Screen : 0.1
         * Temporary ID Photo : 0.0
         */

        private double Edited;
        private double Photocopy;
        @SerializedName("ID Photo")
        private double _$IDPhoto261; // FIXME check this code
        private double Screen;
        @SerializedName("Temporary ID Photo")
        private double _$TemporaryIDPhoto257; // FIXME check this code

        public double getEdited() {
            return Edited;
        }

        public void setEdited(double Edited) {
            this.Edited = Edited;
        }

        public double getPhotocopy() {
            return Photocopy;
        }

        public void setPhotocopy(double Photocopy) {
            this.Photocopy = Photocopy;
        }

        public double get_$IDPhoto261() {
            return _$IDPhoto261;
        }

        public void set_$IDPhoto261(double _$IDPhoto261) {
            this._$IDPhoto261 = _$IDPhoto261;
        }

        public double getScreen() {
            return Screen;
        }

        public void setScreen(double Screen) {
            this.Screen = Screen;
        }

        public double get_$TemporaryIDPhoto257() {
            return _$TemporaryIDPhoto257;
        }

        public void set_$TemporaryIDPhoto257(double _$TemporaryIDPhoto257) {
            this._$TemporaryIDPhoto257 = _$TemporaryIDPhoto257;
        }
    }

    public static class HeadRectBean {
        /**
         * rt : {"y":0.18863636,"x":0.8832418}
         * lt : {"y":0.18863636,"x":0.60027474}
         * lb : {"y":0.71590906,"x":0.614011}
         * rb : {"y":0.71590906,"x":0.90796703}
         */

        private RtBean rt;
        private LtBean lt;
        private LbBean lb;
        private RbBean rb;

        public RtBean getRt() {
            return rt;
        }

        public void setRt(RtBean rt) {
            this.rt = rt;
        }

        public LtBean getLt() {
            return lt;
        }

        public void setLt(LtBean lt) {
            this.lt = lt;
        }

        public LbBean getLb() {
            return lb;
        }

        public void setLb(LbBean lb) {
            this.lb = lb;
        }

        public RbBean getRb() {
            return rb;
        }

        public void setRb(RbBean rb) {
            this.rb = rb;
        }

        public static class RtBean {
            /**
             * y : 0.18863636
             * x : 0.8832418
             */

            private double y;
            private double x;

            public double getY() {
                return y;
            }

            public void setY(double y) {
                this.y = y;
            }

            public double getX() {
                return x;
            }

            public void setX(double x) {
                this.x = x;
            }
        }

        public static class LtBean {
            /**
             * y : 0.18863636
             * x : 0.60027474
             */

            private double y;
            private double x;

            public double getY() {
                return y;
            }

            public void setY(double y) {
                this.y = y;
            }

            public double getX() {
                return x;
            }

            public void setX(double x) {
                this.x = x;
            }
        }

        public static class LbBean {
            /**
             * y : 0.71590906
             * x : 0.614011
             */

            private double y;
            private double x;

            public double getY() {
                return y;
            }

            public void setY(double y) {
                this.y = y;
            }

            public double getX() {
                return x;
            }

            public void setX(double x) {
                this.x = x;
            }
        }

        public static class RbBean {
            /**
             * y : 0.71590906
             * x : 0.90796703
             */

            private double y;
            private double x;

            public double getY() {
                return y;
            }

            public void setY(double y) {
                this.y = y;
            }

            public double getX() {
                return x;
            }

            public void setX(double x) {
                this.x = x;
            }
        }
    }
}
