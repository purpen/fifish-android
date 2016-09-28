package com.qiyuan.fifish.bean;

/**
 * @author lilin
 * created at 2016/9/22 17:46
 */
public class SupportProductsBean {

    /**
     * id : 62
     * likeable : {"id":10,"content":"飞行鱼发现波涛","sticked":"1","featured":"1","view_count":"0","like_count":"1","comment_count":"0","created_at":"2016-09-23 15:37:59","kind":"1","address":null,"city":null,"cover":{"id":25,"filepath":"photo/160923/cb3c7c272247d43f04c3d6c3212529c2","size":"22KB","width":"449","height":"300","file":{"small":"http://obbrr76ua.bkt.clouddn.com/photo/160923/cb3c7c272247d43f04c3d6c3212529c2!cvxsm","large":"http://obbrr76ua.bkt.clouddn.com/photo/160923/cb3c7c272247d43f04c3d6c3212529c2!cvxlg"}}}
     * user : {"id":1,"username":"我","summary":null,"avatar":{"small":"http://obbrr76ua.bkt.clouddn.com/avatar/160923/03ee0bb62f6d6fb57231c004c1cd4fe1!smx50","large":"http://obbrr76ua.bkt.clouddn.com/avatar/160923/03ee0bb62f6d6fb57231c004c1cd4fe1!lgx180"}}
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
         * id : 10
         * content : 飞行鱼发现波涛
         * sticked : 1
         * featured : 1
         * view_count : 0
         * like_count : 1
         * comment_count : 0
         * created_at : 2016-09-23 15:37:59
         * kind : 1
         * address : null
         * city : null
         * cover : {"id":25,"filepath":"photo/160923/cb3c7c272247d43f04c3d6c3212529c2","size":"22KB","width":"449","height":"300","file":{"small":"http://obbrr76ua.bkt.clouddn.com/photo/160923/cb3c7c272247d43f04c3d6c3212529c2!cvxsm","large":"http://obbrr76ua.bkt.clouddn.com/photo/160923/cb3c7c272247d43f04c3d6c3212529c2!cvxlg"}}
         */

        public LikeableBean likeable;
        /**
         * id : 1
         * username : 我
         * summary : null
         * avatar : {"small":"http://obbrr76ua.bkt.clouddn.com/avatar/160923/03ee0bb62f6d6fb57231c004c1cd4fe1!smx50","large":"http://obbrr76ua.bkt.clouddn.com/avatar/160923/03ee0bb62f6d6fb57231c004c1cd4fe1!lgx180"}
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
            /**
             * id : 25
             * filepath : photo/160923/cb3c7c272247d43f04c3d6c3212529c2
             * size : 22KB
             * width : 449
             * height : 300
             * file : {"small":"http://obbrr76ua.bkt.clouddn.com/photo/160923/cb3c7c272247d43f04c3d6c3212529c2!cvxsm","large":"http://obbrr76ua.bkt.clouddn.com/photo/160923/cb3c7c272247d43f04c3d6c3212529c2!cvxlg"}
             */

            public CoverBean cover;

            public static class CoverBean {
                public int id;
                public String filepath;
                public String size;
                public String width;
                public String height;
                /**
                 * small : http://obbrr76ua.bkt.clouddn.com/photo/160923/cb3c7c272247d43f04c3d6c3212529c2!cvxsm
                 * large : http://obbrr76ua.bkt.clouddn.com/photo/160923/cb3c7c272247d43f04c3d6c3212529c2!cvxlg
                 */

                public FileBean file;

                public static class FileBean {
                    public String small;
                    public String large;
                }
            }
        }

        public static class UserBean {
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

    public static class MetaBean {
        public String message;
        public int status_code;
    }
}
