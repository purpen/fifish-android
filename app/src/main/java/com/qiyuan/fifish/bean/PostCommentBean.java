package com.qiyuan.fifish.bean;

import java.util.List;

/**
 * Created by lilin on 2016/12/1.
 */

public class PostCommentBean {

    /**
     * data : {"id":25,"content":"额呃呃","user":{"id":2,"username":"1059232202@qq.com","summary":null,"first_login":1,"last_login":"2016-10-21 09:05:38","avatar":{"small":"https://s3.qysea.com/img/avatar!smx50.png","large":"https://s3.qysea.com/img/avatar!lgx180.png"}},"like_count":null,"reply_user":null,"created_at":"0秒前"}
     * meta : {"message":"Success.","status_code":200}
     */

    public DataBean data;
    public MetaBean meta;

    public static class DataBean {
        /**
         * id : 25
         * content : 额呃呃
         * user : {"id":2,"username":"1059232202@qq.com","summary":null,"first_login":1,"last_login":"2016-10-21 09:05:38","avatar":{"small":"https://s3.qysea.com/img/avatar!smx50.png","large":"https://s3.qysea.com/img/avatar!lgx180.png"}}
         * like_count : null
         * reply_user : null
         * created_at : 0秒前
         */

        public int id;
        public String content;
        public UserBean user;
        public Object like_count;
        public Object reply_user;
        public String created_at;

        public static class UserBean {
            /**
             * id : 2
             * username : 1059232202@qq.com
             * summary : null
             * first_login : 1
             * last_login : 2016-10-21 09:05:38
             * avatar : {"small":"https://s3.qysea.com/img/avatar!smx50.png","large":"https://s3.qysea.com/img/avatar!lgx180.png"}
             */

            public int id;
            public String username;
            public Object summary;
            public int first_login;
            public String last_login;
            public AvatarBean avatar;

            public static class AvatarBean {
                /**
                 * small : https://s3.qysea.com/img/avatar!smx50.png
                 * large : https://s3.qysea.com/img/avatar!lgx180.png
                 */

                public String small;
                public String large;
            }
        }
    }

    public static class MetaBean {
        /**
         * message : Success.
         * status_code : 200
         */

        public String message;
        public int status_code;
        public ErrorsBean errors;

        public static class ErrorsBean {
            public List<String> content;
        }
    }
}
