package com.qiyuan.fifish.bean;

import java.util.List;

/**
 * @author lilin
 * created at 2016/8/30 9:48
 */
public class ErrorBean {

    /**
     * meta : {"message":"Field Format Error","status_code":422,"errors":{"content":["内容长度不能小于2个字符"]}}
     */

    public MetaBean meta;

    public static class MetaBean {
        /**
         * message : Field Format Error
         * status_code : 422
         * errors : {"content":["内容长度不能小于2个字符"]}
         */

        public String message;
        public int status_code;
        public ErrorsBean errors;

        public static class ErrorsBean {
            public List<String> content;
        }
    }
}
