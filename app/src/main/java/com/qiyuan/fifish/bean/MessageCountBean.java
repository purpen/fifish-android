package com.qiyuan.fifish.bean;

/**
 * Created by lilin on 2016/12/15.
 */

public class MessageCountBean {

    /**
     * meta : {"message":"Success.","status_code":200}
     * data : {"alert_fans_count":41,"alert_like_count":2,"alert_comment_count":5}
     */

    public MetaBean meta;
    public DataBean data;

    public static class MetaBean {
        /**
         * message : Success.
         * status_code : 200
         */

        public String message;
        public int status_code;
    }

    public static class DataBean {
        /**
         * alert_fans_count : 41
         * alert_like_count : 2
         * alert_comment_count : 5
         */

        public int alert_fans_count;
        public int alert_like_count;
        public int alert_comment_count;
    }
}
