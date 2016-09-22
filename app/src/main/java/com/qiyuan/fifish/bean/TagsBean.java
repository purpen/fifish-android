package com.qiyuan.fifish.bean;

import java.util.ArrayList;

/**
 * @author lilin
 * created at 2016/9/21 20:46
 */
public class TagsBean {

    /**
     * message : Success.
     * status_code : 200
     * pagination : {"total":5,"count":5,"per_page":10,"current_page":1,"total_pages":1,"links":[]}
     */

    public MetaBean meta;
    /**
     * id : 5
     * name : 大哭
     * display_name : 大哭啊
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
        }
    }

    public static class DataBean {
        public int id;
        public String name;
        public String display_name;
    }
}
