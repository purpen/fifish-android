package com.qiyuan.fifish.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author lilin
 * created at 2016/9/27 14:47
 */
public class ProductsCommentBean implements Serializable {

    /**
     * message : Success.
     * status_code : 200
     * pagination : {"total":16,"count":10,"per_page":10,"current_page":1,"total_pages":2,"links":{"next":"http://fish.taihuoniao.com/api/stuffs/11/comments?page=2"}}
     */

    public MetaBean meta;
    /**
     * id : 10
     * content : 你好
     * user : {"id":1,"username":"我","summary":null,"avatar":{"small":"http://obbrr76ua.bkt.clouddn.com/avatar/160923/03ee0bb62f6d6fb57231c004c1cd4fe1!smx50","large":"http://obbrr76ua.bkt.clouddn.com/avatar/160923/03ee0bb62f6d6fb57231c004c1cd4fe1!lgx180"}}
     * like_count : 0
     * reply_user : {"id":1,"username":"我","summary":null,"avatar":{"small":"http://obbrr76ua.bkt.clouddn.com/avatar/160923/03ee0bb62f6d6fb57231c004c1cd4fe1!smx50","large":"http://obbrr76ua.bkt.clouddn.com/avatar/160923/03ee0bb62f6d6fb57231c004c1cd4fe1!lgx180"}}
     * created_at : 昨天
     */

    public ArrayList<DataBean> data;

    public static class MetaBean {
        public String message;
        public int status_code;
        /**
         * total : 16
         * count : 10
         * per_page : 10
         * current_page : 1
         * total_pages : 2
         * links : {"next":"http://fish.taihuoniao.com/api/stuffs/11/comments?page=2"}
         */

        public PaginationBean pagination;

        public static class PaginationBean {
            public int total;
            public int count;
            public int per_page;
            public int current_page;
            public int total_pages;
            /**
             * next : http://fish.taihuoniao.com/api/stuffs/11/comments?page=2
             */

//            public LinksBean links;

//            public static class LinksBean {
//                public String next;
//            }
        }
    }

    public static class DataBean {
        public int id;
        public String content;
        /**
         * id : 1
         * username : 我
         * summary : null
         * avatar : {"small":"http://obbrr76ua.bkt.clouddn.com/avatar/160923/03ee0bb62f6d6fb57231c004c1cd4fe1!smx50","large":"http://obbrr76ua.bkt.clouddn.com/avatar/160923/03ee0bb62f6d6fb57231c004c1cd4fe1!lgx180"}
         */

        public UserBean user;
        public String like_count;
        /**
         * id : 1
         * username : 我
         * summary : null
         * avatar : {"small":"http://obbrr76ua.bkt.clouddn.com/avatar/160923/03ee0bb62f6d6fb57231c004c1cd4fe1!smx50","large":"http://obbrr76ua.bkt.clouddn.com/avatar/160923/03ee0bb62f6d6fb57231c004c1cd4fe1!lgx180"}
         */

        public ReplyUserBean reply_user;
        public String created_at;

        public static class UserBean {
            public String id;
            public String username;
            public String summary;
            /**
             * small : http://obbrr76ua.bkt.clouddn.com/avatar/160923/03ee0bb62f6d6fb57231c004c1cd4fe1!smx50
             * large : http://obbrr76ua.bkt.clouddn.com/avatar/160923/03ee0bb62f6d6fb57231c004c1cd4fe1!lgx180
             */

            public AvatarBean avatar;

            public static class AvatarBean {
                public String small;
                public String large;
            }
        }

        public static class ReplyUserBean {
            public int id;
            public String username;
            public Object summary;
            /**
             * small : http://obbrr76ua.bkt.clouddn.com/avatar/160923/03ee0bb62f6d6fb57231c004c1cd4fe1!smx50
             * large : http://obbrr76ua.bkt.clouddn.com/avatar/160923/03ee0bb62f6d6fb57231c004c1cd4fe1!lgx180
             */

            public AvatarBean avatar;

            public static class AvatarBean {
                public String small;
                public String large;
            }
        }
    }
}
