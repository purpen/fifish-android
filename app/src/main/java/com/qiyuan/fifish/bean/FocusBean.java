package com.qiyuan.fifish.bean;

import java.util.List;

/**
 * @author lilin
 * created at 2016/9/20 9:20
 */
public class FocusBean {


    /**
     * data : [{"id":219,"user_id":2,"user":{"id":2,"username":"1059232202@qq.com","summary":null,"first_login":1,"last_login":"2016-10-21 09:05:38","avatar":{"small":"http://s3.qysea.com/img/avatar!smx50.png","large":"http://s3.qysea.com/img/avatar!lgx180.png"}},"follow_id":8,"follower":{"id":8,"username":"cheer10","summary":"新的产品","first_login":1,"last_login":"2016-10-25 16:24:55","avatar":{"small":"http://oe5tkubcj.bkt.clouddn.com/avatar/161116/f92348fd14b76dbf1e5fd0a3ce43ecd9.jpg!smx50","large":"http://oe5tkubcj.bkt.clouddn.com/avatar/161116/f92348fd14b76dbf1e5fd0a3ce43ecd9.jpg!lgx180"}},"is_follow":false},{"id":140,"user_id":2,"user":{"id":2,"username":"1059232202@qq.com","summary":null,"first_login":1,"last_login":"2016-10-21 09:05:38","avatar":{"small":"http://s3.qysea.com/img/avatar!smx50.png","large":"http://s3.qysea.com/img/avatar!lgx180.png"}},"follow_id":5,"follower":{"id":5,"username":"特斯拉","summary":null,"first_login":1,"last_login":"2016-10-21 10:22:36","avatar":{"small":"http://oe5tkubcj.bkt.clouddn.com/avatar/161115/1ff23845c3110ac9861bd9f9dcb6a4cb.jpg!smx50","large":"http://oe5tkubcj.bkt.clouddn.com/avatar/161115/1ff23845c3110ac9861bd9f9dcb6a4cb.jpg!lgx180"}},"is_follow":false},{"id":139,"user_id":2,"user":{"id":2,"username":"1059232202@qq.com","summary":null,"first_login":1,"last_login":"2016-10-21 09:05:38","avatar":{"small":"http://s3.qysea.com/img/avatar!smx50.png","large":"http://s3.qysea.com/img/avatar!lgx180.png"}},"follow_id":117,"follower":null,"is_follow":false},{"id":137,"user_id":2,"user":{"id":2,"username":"1059232202@qq.com","summary":null,"first_login":1,"last_login":"2016-10-21 09:05:38","avatar":{"small":"http://s3.qysea.com/img/avatar!smx50.png","large":"http://s3.qysea.com/img/avatar!lgx180.png"}},"follow_id":119,"follower":null,"is_follow":false},{"id":136,"user_id":2,"user":{"id":2,"username":"1059232202@qq.com","summary":null,"first_login":1,"last_login":"2016-10-21 09:05:38","avatar":{"small":"http://s3.qysea.com/img/avatar!smx50.png","large":"http://s3.qysea.com/img/avatar!lgx180.png"}},"follow_id":99,"follower":null,"is_follow":false},{"id":133,"user_id":2,"user":{"id":2,"username":"1059232202@qq.com","summary":null,"first_login":1,"last_login":"2016-10-21 09:05:38","avatar":{"small":"http://s3.qysea.com/img/avatar!smx50.png","large":"http://s3.qysea.com/img/avatar!lgx180.png"}},"follow_id":98,"follower":null,"is_follow":false},{"id":125,"user_id":2,"user":{"id":2,"username":"1059232202@qq.com","summary":null,"first_login":1,"last_login":"2016-10-21 09:05:38","avatar":{"small":"http://s3.qysea.com/img/avatar!smx50.png","large":"http://s3.qysea.com/img/avatar!lgx180.png"}},"follow_id":104,"follower":null,"is_follow":false},{"id":124,"user_id":2,"user":{"id":2,"username":"1059232202@qq.com","summary":null,"first_login":1,"last_login":"2016-10-21 09:05:38","avatar":{"small":"http://s3.qysea.com/img/avatar!smx50.png","large":"http://s3.qysea.com/img/avatar!lgx180.png"}},"follow_id":107,"follower":null,"is_follow":false},{"id":123,"user_id":2,"user":{"id":2,"username":"1059232202@qq.com","summary":null,"first_login":1,"last_login":"2016-10-21 09:05:38","avatar":{"small":"http://s3.qysea.com/img/avatar!smx50.png","large":"http://s3.qysea.com/img/avatar!lgx180.png"}},"follow_id":108,"follower":null,"is_follow":false},{"id":122,"user_id":2,"user":{"id":2,"username":"1059232202@qq.com","summary":null,"first_login":1,"last_login":"2016-10-21 09:05:38","avatar":{"small":"http://s3.qysea.com/img/avatar!smx50.png","large":"http://s3.qysea.com/img/avatar!lgx180.png"}},"follow_id":109,"follower":null,"is_follow":false}]
     * meta : {"message":"Success.","status_code":200,"pagination":{"total":19,"count":10,"per_page":10,"current_page":1,"total_pages":2,"links":{"next":"https://api.qysea.com/user/2/followers?page=2"}}}
     */

    public MetaBean meta;
    public List<DataBean> data;

    public static class MetaBean {
        /**
         * message : Success.
         * status_code : 200
         * pagination : {"total":19,"count":10,"per_page":10,"current_page":1,"total_pages":2,"links":{"next":"https://api.qysea.com/user/2/followers?page=2"}}
         */

        public String message;
        public int status_code;

    }

    public static class DataBean {
        /**
         * id : 219
         * user_id : 2
         * user : {"id":2,"username":"1059232202@qq.com","summary":null,"first_login":1,"last_login":"2016-10-21 09:05:38","avatar":{"small":"http://s3.qysea.com/img/avatar!smx50.png","large":"http://s3.qysea.com/img/avatar!lgx180.png"}}
         * follow_id : 8
         * follower : {"id":8,"username":"cheer10","summary":"新的产品","first_login":1,"last_login":"2016-10-25 16:24:55","avatar":{"small":"http://oe5tkubcj.bkt.clouddn.com/avatar/161116/f92348fd14b76dbf1e5fd0a3ce43ecd9.jpg!smx50","large":"http://oe5tkubcj.bkt.clouddn.com/avatar/161116/f92348fd14b76dbf1e5fd0a3ce43ecd9.jpg!lgx180"}}
         * is_follow : false
         */

        public int id;
        public int user_id;
        public UserBean user;
        public int follow_id;
        public FollowerBean follower;
        public boolean is_follow;
        public boolean is_focus=true;
        public static class UserBean {
            /**
             * id : 2
             * username : 1059232202@qq.com
             * summary : null
             * first_login : 1
             * last_login : 2016-10-21 09:05:38
             * avatar : {"small":"http://s3.qysea.com/img/avatar!smx50.png","large":"http://s3.qysea.com/img/avatar!lgx180.png"}
             */

            public String id;
            public String username;
            public String summary;
            public int first_login;
            public String last_login;
            public AvatarBean avatar;

            public static class AvatarBean {
                /**
                 * small : http://s3.qysea.com/img/avatar!smx50.png
                 * large : http://s3.qysea.com/img/avatar!lgx180.png
                 */

                public String small;
                public String large;
            }
        }

        public static class FollowerBean {
            /**
             * id : 8
             * username : cheer10
             * summary : 新的产品
             * first_login : 1
             * last_login : 2016-10-25 16:24:55
             * avatar : {"small":"http://oe5tkubcj.bkt.clouddn.com/avatar/161116/f92348fd14b76dbf1e5fd0a3ce43ecd9.jpg!smx50","large":"http://oe5tkubcj.bkt.clouddn.com/avatar/161116/f92348fd14b76dbf1e5fd0a3ce43ecd9.jpg!lgx180"}
             */

            public String id;
            public String username;
            public String summary;
            public int first_login;
            public String last_login;
            public AvatarBeanX avatar;

            public static class AvatarBeanX {
                /**
                 * small : http://oe5tkubcj.bkt.clouddn.com/avatar/161116/f92348fd14b76dbf1e5fd0a3ce43ecd9.jpg!smx50
                 * large : http://oe5tkubcj.bkt.clouddn.com/avatar/161116/f92348fd14b76dbf1e5fd0a3ce43ecd9.jpg!lgx180
                 */

                public String small;
                public String large;
            }
        }
    }
}
