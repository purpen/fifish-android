package com.qiyuan.fifish.bean;

/**
 * @author lilin
 * created at 2016/8/25 16:13
 */
public class LoginBean {

    public int code;
    public String message;
    public DataBean data;
    public ErrorBean meta;
    public static class DataBean {
        public String token;
    }
}
