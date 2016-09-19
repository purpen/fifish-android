package com.qiyuan.fifish.bean;

/**
 * @author lilin
 * created at 2016/8/30 9:48
 */
public class ErrorBean {
    /**
     * message : Not Found！
     * status_code : 404
     */

    public MetaBean meta;

    public static class MetaBean {
        public String message;
        public int status_code;
    }
}
