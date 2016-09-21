package com.qiyuan.fifish.bean;

import java.util.List;

/**
 * @author lilin
 * created at 2016/9/21 20:46
 */
public class TagsBean {

    /**
     * status : success
     * code : 200
     * message : Success.
     * pagination : {"total":2,"count":2,"per_page":20,"current_page":1,"total_pages":1,"links":[]}
     */

    public MetaBean meta;
    /**
     * id : 1
     * name : 科技2
     * display_name : 科技范
     */

    public List<DataBean> data;

    public static class MetaBean {
        public String status;
        public int code;
        public String message;
        /**
         * total : 2
         * count : 2
         * per_page : 20
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
