package com.qiyuan.fifish.bean;

import java.util.ArrayList;

/**
 * @author lilin
 * created at 2016/9/20 9:20
 */
public class FocusBean {

    /**
     * message : Success.
     * status_code : 200
     * pagination : {"total":2,"count":2,"per_page":10,"current_page":1,"total_pages":1,"links":[]}
     */

    public MetaBean meta;
    /**
     * id : 3
     * user_id : 1
     * user : {"id":1,"username":"董先生","summary":null}
     * follow_id : 10
     * follower : {"id":10,"username":"609042238@qq.com","summary":null}
     */

    public ArrayList<DataBean> data;

    public static class MetaBean {
        public String message;
        public int status_code;
        /**
         * total : 2
         * count : 2
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
        }
    }

    public static class DataBean {
        public int id;
        public String user_id;
        /**
         * id : 1
         * username : 董先生
         * summary : null
         */

        public UserBean user;
        public String follow_id;
        /**
         * id : 10
         * username : 609042238@qq.com
         * summary : null
         */

        public FollowerBean follower;

        public static class UserBean {
            public int id;
            public String username;
            public Object summary;
        }

        public static class FollowerBean {
            public int id;
            public String username;
            public Object summary;
        }
    }
}
