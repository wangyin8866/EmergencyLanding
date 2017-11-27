package com.zyjr.emergencylending.entity;

/**
 * @author wangyin
 * @date 2017/11/9.
 * @description :
 */

public class RepaymentLogin {
    /**
     * record : {"idcard":"android","phone":"18217117889","platform":"411425199105276618","type":"jjtapp"}
     */
    private String promptMsg;
    public String getPromptMsg() {
        return promptMsg;
    }

    public void setPromptMsg(String promptMsg) {
        this.promptMsg = promptMsg;
    }
    private String record;

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public static class RecordBean {
        public RecordBean(String idcard, String phone, String platform, String type,long time) {
            this.idcard = idcard;
            this.phone = phone;
            this.platform = platform;
            this.type = type;
            this.time = time;
        }

        /**
         * idcard : android
         * phone : 18217117889
         * platform : 411425199105276618
         * type : jjtapp
         */

        private String idcard;
        private String phone;
        private String platform;
        private String type;
        private long time;

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "RecordBean{" +
                    "idcard='" + idcard + '\'' +
                    ", phone='" + phone + '\'' +
                    ", platform='" + platform + '\'' +
                    ", type='" + type + '\'' +
                    ", time=" + time +
                    '}';
        }
    }
}
