package com.qiyuan.fifish.bean;

/**
 * Created by lilin on 2016/12/14.
 */

public class SuccessBean {

    /**
     * meta : {"message":"Success.","status_code":200}
     */

    public MetaBean meta;

    public static class MetaBean {
        /**
         * message : Success.
         * status_code : 200
         */

        public String message;
        public int status_code;
    }
}
