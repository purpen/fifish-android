package com.qiyuan.fifish.bean;

/**
 * @author lilin
 * created at 2016/9/23 14:04
 */
public class UploadImgVideoBean {

    /**
     * message : request ok
     * status_code : 200
     */

    public MetaBean meta;
    /**
     * imageUrl : http://xxxx.com/uploads/images/ada22917f864829d4ef2a7be17a2fcdb.jpg
     */

    public DataBean data;

    public static class MetaBean {
        public String message;
        public int status_code;
    }

    public static class DataBean {
        public String imageUrl;
    }
}
