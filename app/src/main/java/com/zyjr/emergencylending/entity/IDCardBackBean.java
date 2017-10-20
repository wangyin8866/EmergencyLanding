package com.zyjr.emergencylending.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by neil on 2017/10/19
 * 备注:
 */

public class IDCardBackBean implements Serializable {

    private static final long serialVersionUID = -2368447930686125050L;


    /**
     * legality : {"Edited":0,"Photocopy":0,"Screen":0.146,"ID Photo":0.854,"Temporary ID Photo":0}
     * request_id : 1508406861,73097b75-934e-48cb-bdce-afa10ad49835
     * side : back
     * time_used : 374
     */

    private LegalityBean legality;
    private String request_id;
    private String side;
    private int time_used;

    public LegalityBean getLegality() {
        return legality;
    }

    public void setLegality(LegalityBean legality) {
        this.legality = legality;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public int getTime_used() {
        return time_used;
    }

    public void setTime_used(int time_used) {
        this.time_used = time_used;
    }

    public static class LegalityBean {
        /**
         * Edited : 0.0
         * Photocopy : 0.0
         * Screen : 0.146
         * ID Photo : 0.854
         * Temporary ID Photo : 0.0
         */

        private double Edited;
        private double Photocopy;
        private double Screen;
        @SerializedName("ID Photo")
        private double _$IDPhoto142; // FIXME check this code
        @SerializedName("Temporary ID Photo")
        private double _$TemporaryIDPhoto158; // FIXME check this code

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

        public double getScreen() {
            return Screen;
        }

        public void setScreen(double Screen) {
            this.Screen = Screen;
        }

        public double get_$IDPhoto142() {
            return _$IDPhoto142;
        }

        public void set_$IDPhoto142(double _$IDPhoto142) {
            this._$IDPhoto142 = _$IDPhoto142;
        }

        public double get_$TemporaryIDPhoto158() {
            return _$TemporaryIDPhoto158;
        }

        public void set_$TemporaryIDPhoto158(double _$TemporaryIDPhoto158) {
            this._$TemporaryIDPhoto158 = _$TemporaryIDPhoto158;
        }
    }
}
