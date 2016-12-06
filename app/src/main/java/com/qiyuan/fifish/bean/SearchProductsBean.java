package com.qiyuan.fifish.bean;

import java.util.List;

/**
 * Created by lilin on 2016/11/29.
 */

public class SearchProductsBean {


    /**
     * meta : {"message":"success","status_code":200,"pagination":{"total":1,"count":"8","per_page":"8","current_page":"1","total_pages":1}}
     * data : [{"pid":"stuff_60","oid":"60","tid":"2","cid":null,"kind":"Stuff","title":"这是测试内容60","cover_id":null,"content":"","user_id":"1","tags":["测试","无效","无限","参与","test","city","baby","beijing"],"created_on":"1476846207","updated_on":"1476846207","high_title":"这是<em>测试<\/em>内容60","high_content":"","stuff":{"id":60,"content":"催呀催呀我的骄傲放纵","view_count":0,"kind":2,"city":null,"address":"Label","like_count":1,"comment_count":0,"user_id":1,"user":{"id":1,"username":"M鱼","summary":null,"first_login":1,"last_login":"2016-11-02 09:18:06","avatar":{"small":"http://oe5tkubcj.bkt.clouddn.com/avatar/161118/05a2762751b655d14b8983ef7f8fead8.jpg!smx50","large":"http://oe5tkubcj.bkt.clouddn.com/avatar/161118/05a2762751b655d14b8983ef7f8fead8.jpg!lgx180"}},"tags":[],"cover":{"id":146,"filepath":"video/161018/8e0fa96a6e03541fa5be847582f0c8b1.MOV","size":2359265,"width":0,"height":0,"duration":3.87,"kind":2,"file":{"srcfile":"http://oe5tkubcj.bkt.clouddn.com/video/161018/8e0fa96a6e03541fa5be847582f0c8b1.MOV!wm","small":"http://oe5tkubcj.bkt.clouddn.com/video/161018/8e0fa96a6e03541fa5be847582f0c8b1.MOV!vfrsm","large":"http://oe5tkubcj.bkt.clouddn.com/video/161018/8e0fa96a6e03541fa5be847582f0c8b1.MOV!vfr"}},"created_at":"2016-10-18","is_love":false,"is_follow":false},"user":null}]
     */

    public MetaBean meta;
    public List<DataBean> data;

    public static class MetaBean {
        public String message;
        public int status_code;
    }

    public static class DataBean {
        /**
         * pid : stuff_60
         * oid : 60
         * tid : 2
         * cid : null
         * kind : Stuff
         * title : 这是测试内容60
         * cover_id : null
         * content :
         * user_id : 1
         * tags : ["测试","无效","无限","参与","test","city","baby","beijing"]
         * created_on : 1476846207
         * updated_on : 1476846207
         * high_title : 这是<em>测试</em>内容60
         * high_content :
         * stuff : {"id":60,"content":"催呀催呀我的骄傲放纵","view_count":0,"kind":2,"city":null,"address":"Label","like_count":1,"comment_count":0,"user_id":1,"user":{"id":1,"username":"M鱼","summary":null,"first_login":1,"last_login":"2016-11-02 09:18:06","avatar":{"small":"http://oe5tkubcj.bkt.clouddn.com/avatar/161118/05a2762751b655d14b8983ef7f8fead8.jpg!smx50","large":"http://oe5tkubcj.bkt.clouddn.com/avatar/161118/05a2762751b655d14b8983ef7f8fead8.jpg!lgx180"}},"tags":[],"cover":{"id":146,"filepath":"video/161018/8e0fa96a6e03541fa5be847582f0c8b1.MOV","size":2359265,"width":0,"height":0,"duration":3.87,"kind":2,"file":{"srcfile":"http://oe5tkubcj.bkt.clouddn.com/video/161018/8e0fa96a6e03541fa5be847582f0c8b1.MOV!wm","small":"http://oe5tkubcj.bkt.clouddn.com/video/161018/8e0fa96a6e03541fa5be847582f0c8b1.MOV!vfrsm","large":"http://oe5tkubcj.bkt.clouddn.com/video/161018/8e0fa96a6e03541fa5be847582f0c8b1.MOV!vfr"}},"created_at":"2016-10-18","is_love":false,"is_follow":false}
         * user : null
         */

        public String pid;
        public String oid;
        public String tid;
        public String cid;
        public String kind;
        public String title;
        public String cover_id;
        public String content;
        public String user_id;
        public String created_on;
        public String updated_on;
        public String high_title;
        public String high_content;
        public StuffBean stuff;
        public List<String> tags;

        public static class StuffBean {
            /**
             * id : 60
             * content : 催呀催呀我的骄傲放纵
             * view_count : 0
             * kind : 2
             * city : null
             * address : Label
             * like_count : 1
             * comment_count : 0
             * user_id : 1
             * user : {"id":1,"username":"M鱼","summary":null,"first_login":1,"last_login":"2016-11-02 09:18:06","avatar":{"small":"http://oe5tkubcj.bkt.clouddn.com/avatar/161118/05a2762751b655d14b8983ef7f8fead8.jpg!smx50","large":"http://oe5tkubcj.bkt.clouddn.com/avatar/161118/05a2762751b655d14b8983ef7f8fead8.jpg!lgx180"}}
             * tags : []
             * cover : {"id":146,"filepath":"video/161018/8e0fa96a6e03541fa5be847582f0c8b1.MOV","size":2359265,"width":0,"height":0,"duration":3.87,"kind":2,"file":{"srcfile":"http://oe5tkubcj.bkt.clouddn.com/video/161018/8e0fa96a6e03541fa5be847582f0c8b1.MOV!wm","small":"http://oe5tkubcj.bkt.clouddn.com/video/161018/8e0fa96a6e03541fa5be847582f0c8b1.MOV!vfrsm","large":"http://oe5tkubcj.bkt.clouddn.com/video/161018/8e0fa96a6e03541fa5be847582f0c8b1.MOV!vfr"}}
             * created_at : 2016-10-18
             * is_love : false
             * is_follow : false
             */

            public String id;
            public String content;
            public int view_count;
            public String kind;
            public String city;
            public String address;
            public int like_count;
            public int comment_count;
            public int user_id;
            public UserBean user;
            public CoverBean cover;
            public String created_at;
            public boolean is_love;
            public boolean is_follow;
            public List<TagBean> tags;
            public static class TagBean{
                public String id;
                public String name;
                public String display_name;
            }
            public static class UserBean {
                /**
                 * id : 1
                 * username : M鱼
                 * summary : null
                 * first_login : 1
                 * last_login : 2016-11-02 09:18:06
                 * avatar : {"small":"http://oe5tkubcj.bkt.clouddn.com/avatar/161118/05a2762751b655d14b8983ef7f8fead8.jpg!smx50","large":"http://oe5tkubcj.bkt.clouddn.com/avatar/161118/05a2762751b655d14b8983ef7f8fead8.jpg!lgx180"}
                 */

                public int id;
                public String username;
                public String summary;
                public int first_login;
                public String last_login;
                public AvatarBean avatar;

                public static class AvatarBean {
                    /**
                     * small : http://oe5tkubcj.bkt.clouddn.com/avatar/161118/05a2762751b655d14b8983ef7f8fead8.jpg!smx50
                     * large : http://oe5tkubcj.bkt.clouddn.com/avatar/161118/05a2762751b655d14b8983ef7f8fead8.jpg!lgx180
                     */

                    public String small;
                    public String large;
                }
            }

            public static class CoverBean {
                /**
                 * id : 146
                 * filepath : video/161018/8e0fa96a6e03541fa5be847582f0c8b1.MOV
                 * size : 2359265
                 * width : 0
                 * height : 0
                 * duration : 3.87
                 * kind : 2
                 * file : {"srcfile":"http://oe5tkubcj.bkt.clouddn.com/video/161018/8e0fa96a6e03541fa5be847582f0c8b1.MOV!wm","small":"http://oe5tkubcj.bkt.clouddn.com/video/161018/8e0fa96a6e03541fa5be847582f0c8b1.MOV!vfrsm","large":"http://oe5tkubcj.bkt.clouddn.com/video/161018/8e0fa96a6e03541fa5be847582f0c8b1.MOV!vfr"}
                 */

                public int id;
                public String filepath;
                public int size;
                public int width;
                public int height;
                public double duration;
                public int kind;
                public FileBean file;

                public static class FileBean {
                    /**
                     * srcfile : http://oe5tkubcj.bkt.clouddn.com/video/161018/8e0fa96a6e03541fa5be847582f0c8b1.MOV!wm
                     * small : http://oe5tkubcj.bkt.clouddn.com/video/161018/8e0fa96a6e03541fa5be847582f0c8b1.MOV!vfrsm
                     * large : http://oe5tkubcj.bkt.clouddn.com/video/161018/8e0fa96a6e03541fa5be847582f0c8b1.MOV!vfr
                     */

                    public String srcfile;
                    public String small;
                    public String large;
                }
            }
        }
    }
}
