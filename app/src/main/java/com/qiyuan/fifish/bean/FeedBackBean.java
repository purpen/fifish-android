package com.qiyuan.fifish.bean;

/**
 * @author lilin
 * created at 2016/9/20 17:14
 */
public class FeedBackBean {

    /**
     * contact : 44334512
     * content : 提交一个测试反馈信息
     */

    public DataBean data;
    /**
     * status : success
     * status_code : 200
     * message : 提交反馈信息成功
     */

    public MetaBean meta;

    public static class DataBean {
        public String contact;
        public String content;
    }

    public static class MetaBean {
        public String status;
        public int status_code;
        public String message;
    }
}
