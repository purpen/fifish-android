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
     * pagination : {"total":22,"count":8,"per_page":8,"current_page":1,"total_pages":3,"links":{"next":"http://api.qysea.com/stuffs?page=2"}}
     */

    public MetaEntity meta;
    /**
     * id : 22
     * content : 1111111
     * view_count : 0
     * kind : 2
     * city : null
     * address : Label
     * like_count : 0
     * comment_count : 0
     * user_id : 1
     * user : {"id":1,"username":"Dev","summary":null,"avatar":{"small":"http://s3.qysea.com/img/avatar!smx50.png","large":"http://s3.qysea.com/img/avatar!lgx180.png"}}
     * tags : []
     * photo : {"id":55,"filepath":"video/161014/d2fb7af62512b682e5c3a758de74f668.MOV","size":"977KB","width":0,"height":0,"duration":9.58,"kind":2,"file":{"srcfile":"http://oe5tkubcj.bkt.clouddn.com/video/161014/d2fb7af62512b682e5c3a758de74f668.MOV","small":"http://oe5tkubcj.bkt.clouddn.com/video/161014/d2fb7af62512b682e5c3a758de74f668.MOV!vfrsm","large":"http://oe5tkubcj.bkt.clouddn.com/video/161014/d2fb7af62512b682e5c3a758de74f668.MOV!vfr"}}
     * created_at : 2016-10-14
     * is_love : false
     * is_follow : false
     */

    public ArrayList<DataEntity> data;

    public static class MetaEntity {
        public String message;
        public int status_code;
        /**
         * total : 22
         * count : 8
         * per_page : 8
         * current_page : 1
         * total_pages : 3
         * links : {"next":"http://api.qysea.com/stuffs?page=2"}
         */
    }

    public static class DataEntity implements Serializable{
        public String id;
        public String content;
        public String view_count;
        public String kind;
        public Object city;
        public String address;
        public String like_count;
        public String comment_count;
        public String user_id;
        /**
         * id : 1
         * username : Dev
         * summary : null
         * avatar : {"small":"http://s3.qysea.com/img/avatar!smx50.png","large":"http://s3.qysea.com/img/avatar!lgx180.png"}
         */

        public UserEntity user;
        /**
         * id : 55
         * filepath : video/161014/d2fb7af62512b682e5c3a758de74f668.MOV
         * size : 977KB
         * width : 0
         * height : 0
         * duration : 9.58
         * kind : 2
         * file : {"srcfile":"http://oe5tkubcj.bkt.clouddn.com/video/161014/d2fb7af62512b682e5c3a758de74f668.MOV","small":"http://oe5tkubcj.bkt.clouddn.com/video/161014/d2fb7af62512b682e5c3a758de74f668.MOV!vfrsm","large":"http://oe5tkubcj.bkt.clouddn.com/video/161014/d2fb7af62512b682e5c3a758de74f668.MOV!vfr"}
         */

        public PhotoEntity photo;
        public String created_at;
        public boolean is_love;
        public boolean is_follow;
        public List<Object> tags;

        public static class UserEntity implements Serializable{
            public String id;
            public String username;
            public Object summary;
            /**
             * small : http://s3.qysea.com/img/avatar!smx50.png
             * large : http://s3.qysea.com/img/avatar!lgx180.png
             */

            public AvatarEntity avatar;

            public static class AvatarEntity {
                public String small;
                public String large;
            }
        }

        public static class PhotoEntity {
            public String id;
            public String filepath;
            public String size;
            public int width;
            public int height;
            public double duration;
            public String kind;
            /**
             * srcfile : http://oe5tkubcj.bkt.clouddn.com/video/161014/d2fb7af62512b682e5c3a758de74f668.MOV
             * small : http://oe5tkubcj.bkt.clouddn.com/video/161014/d2fb7af62512b682e5c3a758de74f668.MOV!vfrsm
             * large : http://oe5tkubcj.bkt.clouddn.com/video/161014/d2fb7af62512b682e5c3a758de74f668.MOV!vfr
             */

            public FileEntity file;

            public static class FileEntity{
                public String srcfile;
                public String small;
                public String large;
            }
        }
    }
}
