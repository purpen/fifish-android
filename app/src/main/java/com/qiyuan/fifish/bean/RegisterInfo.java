package com.qiyuan.fifish.bean;

import java.util.List;

/**
 * @author lilin
 * created at 2016/8/30 10:10
 */
public class RegisterInfo {

    public MetaBean meta;

    public static class MetaBean {
        public String message;
        public int status_code;
        public ErrorsBean errors;

        public static class ErrorsBean {
            public List<String> account;
        }
    }
}
