package com.qiyuan.fifish.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lilin
 * created at 2016/9/20 21:03
 */
public class ProductsBean implements Serializable{
    /**
     * message : Success.
     * status_code : 200
     * pagination : {"total":5,"count":5,"per_page":10,"current_page":1,"total_pages":1,"links":[]}
     */

    public MetaBean meta;
    /**
     * id : 11
     * content : 飞行鱼重新发现海洋
     * view_count : 0
     * kind : 1
     * city : null
     * address : null
     * like_count : 0
     * comment_count : 0
     * user_id : 1
     * user : {"id":1,"username":"董先生","summary":null,"avatar":{"small":"http://obbrr76ua.bkt.clouddn.com/avatar/160923/03ee0bb62f6d6fb57231c004c1cd4fe1!smx50","large":"http://obbrr76ua.bkt.clouddn.com/avatar/160923/03ee0bb62f6d6fb57231c004c1cd4fe1!lgx180"}}
     * tags : []
     * photo : {"id":26,"filepath":"photo/160923/d25ddc0b2ec76ee8d47c74f7a96c64e2","size":"226KB","width":"750","height":"460","file":{"small":"http://obbrr76ua.bkt.clouddn.com/photo/160923/d25ddc0b2ec76ee8d47c74f7a96c64e2!cvxsm","large":"http://obbrr76ua.bkt.clouddn.com/photo/160923/d25ddc0b2ec76ee8d47c74f7a96c64e2!cvxlg"}}
     * created_at : 2016-09-23
     * is_love : false
     */

    public ArrayList<DataBean> data;

    public static class MetaBean {
        public String message;
        public int status_code;
        /**
         * total : 5
         * count : 5
         * per_page : 10
         * current_page : 1
         * total_pages : 1
         * links : []
         */

        public PaginationBean pagination;

        public static class PaginationBean {
            public int total;
            public int count;
            public int per_page;
            public int current_page;
            public int total_pages;
            public List<?> links;
        }
    }

    public static class DataBean implements Serializable{
        public String id;
        public String content;
        public String view_count;
        public String kind;
        public Object city;
        public Object address;
        public String like_count;
        public String comment_count;
        public String user_id;
        /**
         * id : 1
         * username : 董先生
         * summary : null
         * avatar : {"small":"http://obbrr76ua.bkt.clouddn.com/avatar/160923/03ee0bb62f6d6fb57231c004c1cd4fe1!smx50","large":"http://obbrr76ua.bkt.clouddn.com/avatar/160923/03ee0bb62f6d6fb57231c004c1cd4fe1!lgx180"}
         */

        public UserBean user;
        /**
         * id : 26
         * filepath : photo/160923/d25ddc0b2ec76ee8d47c74f7a96c64e2
         * size : 226KB
         * width : 750
         * height : 460
         * file : {"small":"http://obbrr76ua.bkt.clouddn.com/photo/160923/d25ddc0b2ec76ee8d47c74f7a96c64e2!cvxsm","large":"http://obbrr76ua.bkt.clouddn.com/photo/160923/d25ddc0b2ec76ee8d47c74f7a96c64e2!cvxlg"}
         */

        public PhotoBean photo;
        public String created_at;
        public boolean is_love;
        public List<?> tags;

        public static class UserBean implements Serializable{
            public int id;
            public String username;
            public Object summary;
            /**
             * small : http://obbrr76ua.bkt.clouddn.com/avatar/160923/03ee0bb62f6d6fb57231c004c1cd4fe1!smx50
             * large : http://obbrr76ua.bkt.clouddn.com/avatar/160923/03ee0bb62f6d6fb57231c004c1cd4fe1!lgx180
             */

            public AvatarBean avatar;

            public static class AvatarBean implements Serializable{
                public String small;
                public String large;
            }
        }

        public static class PhotoBean implements Serializable{
            public String id;
            public String filepath;
            public String size;
            public String width;
            public String height;
            /**
             * small : http://obbrr76ua.bkt.clouddn.com/photo/160923/d25ddc0b2ec76ee8d47c74f7a96c64e2!cvxsm
             * large : http://obbrr76ua.bkt.clouddn.com/photo/160923/d25ddc0b2ec76ee8d47c74f7a96c64e2!cvxlg
             */

            public FileBean file;

            public static class FileBean implements Serializable{
                public String small;
                public String large;
            }
        }
    }
}
