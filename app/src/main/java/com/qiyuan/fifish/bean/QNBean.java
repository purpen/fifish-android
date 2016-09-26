package com.qiyuan.fifish.bean;

/**
 * @author lilin
 * created at 2016/9/26 15:59
 */
public class QNBean {

    /**
     * message : request ok
     * status_code : 200
     */

    public MetaBean meta;
    /**
     * token : lg_vCeWWdlVmlld1wvMVwvd1wvMTY.......wXC9oXC8xMjBcL3DkyMn0=
     * upload_url : http://up.qiniu.com
     */

    public DataBean data;

    public static class MetaBean {
        public String message;
        public int status_code;
    }

    public static class DataBean {
        public String token;
        public String upload_url;
    }
}
