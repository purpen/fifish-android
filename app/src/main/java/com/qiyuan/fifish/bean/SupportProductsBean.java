package com.qiyuan.fifish.bean;

/**
 * @author lilin
 * created at 2016/9/22 17:46
 */
public class SupportProductsBean {

    /**
     * id : 1
     * likeable : {"id":2,"content":"蓝色大山风景画","sticked":"1","featured":"1","view_count":"0","like_count":"1","comment_count":"0","created_at":"2016-09-19 19:27:07","kind":"1","address":null,"city":null}
     * user : {"id":1,"username":"董先生","avatar_url":"","summary":null}
     */

    public DataBean data;
    /**
     * message : Success.
     * status_code : 200
     */

    public MetaBean meta;

    public static class DataBean {
        public int id;
        /**
         * id : 2
         * content : 蓝色大山风景画
         * sticked : 1
         * featured : 1
         * view_count : 0
         * like_count : 1
         * comment_count : 0
         * created_at : 2016-09-19 19:27:07
         * kind : 1
         * address : null
         * city : null
         */

        public LikeableBean likeable;
        /**
         * id : 1
         * username : 董先生
         * avatar_url :
         * summary : null
         */

        public UserBean user;

        public static class LikeableBean {
            public int id;
            public String content;
            public String sticked;
            public String featured;
            public String view_count;
            public String like_count;
            public String comment_count;
            public String created_at;
            public String kind;
            public Object address;
            public Object city;
        }

        public static class UserBean {
            public int id;
            public String username;
            public String avatar_url;
            public Object summary;
        }
    }

    public static class MetaBean {
        public String message;
        public int status_code;
    }
}
