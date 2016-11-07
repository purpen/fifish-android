package com.qiyuan.fifish.bean;

/**
 * @author lilin
 * created at 2016/8/25 16:13
 */
public class LoginBean {

    public MetaBean meta;

    public DataBean data;

    public static class MetaBean {
        public String message;
        public int status_code;
    }

    public static class DataBean {
        public String token;
        public int first_login;
    }
}
