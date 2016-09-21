package com.qiyuan.fifish.bean;

import java.util.ArrayList;

/**
 * @author lilin
 * created at 2016/9/20 21:03
 */
public class ProductsBean {

    /**
     * message : Success.
     * status_code : 200
     * pagination : {"total":4,"count":4,"per_page":8,"current_page":1,"total_pages":1,"links":[]}
     */

    public MetaBean meta;
    /**
     * id : 5
     * content : 大海,是雄伟的,一朵朵浪花拍打沙滩,发出轰轰的浪花声
     * view_count : 0
     * kind : 1
     * like_count : 0
     * comment_count : 0
     * user_id : 1
     * user : {"id":1,"username":"董先生","summary":null}
     * tags : []
     * photo : {"id":5,"filepath":"uploads/images/a2081c1fb866ac79e3b8329139e51e8d.jpg","size":"916KB","width":"960","height":"600","fileurl":"http://fish.taihuoniao.com/uploads/images/a2081c1fb866ac79e3b8329139e51e8d.jpg"}
     */

    public ArrayList<DataBean> data;

    public static class MetaBean {
        public String message;
        public int status_code;
        /**
         * total : 4
         * count : 4
         * per_page : 8
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
        public String content;
        public String view_count;
        public String kind;
        public String like_count;
        public String comment_count;
        public String user_id;
        /**
         * id : 1
         * username : 董先生
         * summary : null
         */

        public UserBean user;
        /**
         * id : 5
         * filepath : uploads/images/a2081c1fb866ac79e3b8329139e51e8d.jpg
         * size : 916KB
         * width : 960
         * height : 600
         * fileurl : http://fish.taihuoniao.com/uploads/images/a2081c1fb866ac79e3b8329139e51e8d.jpg
         */

        public PhotoBean photo;

        public static class UserBean {
            public int id;
            public String username;
            public Object summary;
        }

        public static class PhotoBean {
            public int id;
            public String filepath;
            public String size;
            public String width;
            public String height;
            public String fileurl;
        }
    }
}
