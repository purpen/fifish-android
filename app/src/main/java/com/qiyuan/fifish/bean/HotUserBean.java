package com.qiyuan.fifish.bean;

import java.util.ArrayList;

/**
 * @author lilin
 * created at 2016/9/22 9:47
 */
public class HotUserBean {

    /**
     * message : Success.
     * status_code : 200
     * pagination : {"total":14,"count":10,"per_page":10,"current_page":1,"total_pages":2,"links":{"next":"http://fish.taihuoniao.com/api/user/hot_users?page=2"}}
     */

    public MetaBean meta;
    /**
     * id : 1
     * account : 215141271@qq.com
     * username : 董先生
     * job :
     * zone : <UITextField: 0x1309eeaa0; frame = (55 138.5; 263 44); text = ''; clipsToBounds = YES; opaque = NO;
     * summary : null
     * follow_count : 2
     * fans_count : 0
     * stuff_count : 0
     * like_count : 0
     * avatar : {"small":"","large":""}
     */

    public ArrayList<DataBean> data;

    public static class MetaBean {
        public String message;
        public int status_code;
        /**
         * total : 14
         * count : 10
         * per_page : 10
         * current_page : 1
         * total_pages : 2
         * links : {"next":"http://fish.taihuoniao.com/api/user/hot_users?page=2"}
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
        public String account;
        public String username;
        public String job;
        public String zone;
        public Object summary;
        public String follow_count;
        public String fans_count;
        public String stuff_count;
        public String like_count;
        /**
         * small :
         * large :
         */

        public AvatarBean avatar;

        public static class AvatarBean {
            public String small;
            public String large;
        }
    }
}
