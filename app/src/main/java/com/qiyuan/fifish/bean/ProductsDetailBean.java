package com.qiyuan.fifish.bean;

import java.util.List;

/**
 * Created by lilin on 2016/12/14.
 */

public class ProductsDetailBean {

    /**
     * data : {"id":161,"content":"说点","view_count":3,"kind":1,"city":null,"address":"","like_count":0,"comment_count":1,"user_id":45,"user":{"id":45,"username":"大洋百货","summary":null,"first_login":1,"last_login":"2016-12-02 14:21:29","avatar":{"small":"https://s3.qysea.com/avatar/161208/7130a36c61e2732e0db6805a46f18a8d.jpg!smx50","large":"https://s3.qysea.com/avatar/161208/7130a36c61e2732e0db6805a46f18a8d.jpg!lgx180"}},"tags":[],"cover":{"id":358,"filepath":"photo/161213/746e767012c6e98063f7b6444751f493.jpg","size":1292019,"width":1024,"height":768,"duration":0,"kind":1,"file":{"srcfile":"https://s3.qysea.com/photo/161213/746e767012c6e98063f7b6444751f493.jpg","small":"https://s3.qysea.com/photo/161213/746e767012c6e98063f7b6444751f493.jpg!cvxsm","large":"https://s3.qysea.com/photo/161213/746e767012c6e98063f7b6444751f493.jpg!hd","thumb":"https://s3.qysea.com/photo/161213/746e767012c6e98063f7b6444751f493.jpg!psq","adpic":"https://s3.qysea.com/photo/161213/746e767012c6e98063f7b6444751f493.jpg!plg"}},"created_at":"19小时前","is_love":false,"is_follow":false}
     * meta : {"message":"Success.","status_code":200}
     */

    public DataBean data;
    public MetaBean meta;

    public static class DataBean {
        /**
         * id : 161
         * content : 说点
         * view_count : 3
         * kind : 1
         * city : null
         * address :
         * like_count : 0
         * comment_count : 1
         * user_id : 45
         * user : {"id":45,"username":"大洋百货","summary":null,"first_login":1,"last_login":"2016-12-02 14:21:29","avatar":{"small":"https://s3.qysea.com/avatar/161208/7130a36c61e2732e0db6805a46f18a8d.jpg!smx50","large":"https://s3.qysea.com/avatar/161208/7130a36c61e2732e0db6805a46f18a8d.jpg!lgx180"}}
         * tags : []
         * cover : {"id":358,"filepath":"photo/161213/746e767012c6e98063f7b6444751f493.jpg","size":1292019,"width":1024,"height":768,"duration":0,"kind":1,"file":{"srcfile":"https://s3.qysea.com/photo/161213/746e767012c6e98063f7b6444751f493.jpg","small":"https://s3.qysea.com/photo/161213/746e767012c6e98063f7b6444751f493.jpg!cvxsm","large":"https://s3.qysea.com/photo/161213/746e767012c6e98063f7b6444751f493.jpg!hd","thumb":"https://s3.qysea.com/photo/161213/746e767012c6e98063f7b6444751f493.jpg!psq","adpic":"https://s3.qysea.com/photo/161213/746e767012c6e98063f7b6444751f493.jpg!plg"}}
         * created_at : 19小时前
         * is_love : false
         * is_follow : false
         */

        public int id;
        public String content;
        public int view_count;
        public String kind;
        public Object city;
        public String address;
        public int like_count;
        public int comment_count;
        public int user_id;
        public UserBean user;
        public CoverBean cover;
        public String created_at;
        public boolean is_love;
        public boolean is_follow;
        public List<Object> tags;

        public static class UserBean {
            /**
             * id : 45
             * username : 大洋百货
             * summary : null
             * first_login : 1
             * last_login : 2016-12-02 14:21:29
             * avatar : {"small":"https://s3.qysea.com/avatar/161208/7130a36c61e2732e0db6805a46f18a8d.jpg!smx50","large":"https://s3.qysea.com/avatar/161208/7130a36c61e2732e0db6805a46f18a8d.jpg!lgx180"}
             */

            public int id;
            public String username;
            public String summary;
            public int first_login;
            public String last_login;
            public AvatarBean avatar;

            public static class AvatarBean {
                /**
                 * small : https://s3.qysea.com/avatar/161208/7130a36c61e2732e0db6805a46f18a8d.jpg!smx50
                 * large : https://s3.qysea.com/avatar/161208/7130a36c61e2732e0db6805a46f18a8d.jpg!lgx180
                 */

                public String small;
                public String large;
            }
        }

        public static class CoverBean {
            /**
             * id : 358
             * filepath : photo/161213/746e767012c6e98063f7b6444751f493.jpg
             * size : 1292019
             * width : 1024
             * height : 768
             * duration : 0
             * kind : 1
             * file : {"srcfile":"https://s3.qysea.com/photo/161213/746e767012c6e98063f7b6444751f493.jpg","small":"https://s3.qysea.com/photo/161213/746e767012c6e98063f7b6444751f493.jpg!cvxsm","large":"https://s3.qysea.com/photo/161213/746e767012c6e98063f7b6444751f493.jpg!hd","thumb":"https://s3.qysea.com/photo/161213/746e767012c6e98063f7b6444751f493.jpg!psq","adpic":"https://s3.qysea.com/photo/161213/746e767012c6e98063f7b6444751f493.jpg!plg"}
             */

            public int id;
            public String filepath;
            public int size;
            public int width;
            public int height;
            public int duration;
            public int kind;
            public FileBean file;

            public static class FileBean {
                /**
                 * srcfile : https://s3.qysea.com/photo/161213/746e767012c6e98063f7b6444751f493.jpg
                 * small : https://s3.qysea.com/photo/161213/746e767012c6e98063f7b6444751f493.jpg!cvxsm
                 * large : https://s3.qysea.com/photo/161213/746e767012c6e98063f7b6444751f493.jpg!hd
                 * thumb : https://s3.qysea.com/photo/161213/746e767012c6e98063f7b6444751f493.jpg!psq
                 * adpic : https://s3.qysea.com/photo/161213/746e767012c6e98063f7b6444751f493.jpg!plg
                 */

                public String srcfile;
                public String small;
                public String large;
                public String thumb;
                public String adpic;
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
    }
}
