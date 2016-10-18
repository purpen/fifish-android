package com.qiyuan.fifish.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author lilin
 * created at 2016/10/18 15:44
 */
public class BannersBean implements Serializable {

    /**
     * message : Success.
     * status_code : 200
     * pagination : {"total":2,"count":1,"per_page":1,"current_page":1,"total_pages":2,"links":{"next":"http://api.qysea.com/gateway/columns?page=2"}}
     */

    public MetaEntity meta;
    /**
     * id : 2
     * title : 非常时期肥皂
     * sub_title : null
     * content : null
     * summary :
     * column_space_id : 1
     * type : 1
     * evt : 1
     * url : 52
     * cover_id : 134
     * cover : {"id":134,"filepath":"photo/161018/dbea7050e0724b2ab4acff47f88b1993.jpg","size":267305,"width":1200,"height":1200,"duration":0,"kind":1,"file":{"srcfile":"http://oe5tkubcj.bkt.clouddn.com/photo/161018/dbea7050e0724b2ab4acff47f88b1993.jpg","small":"http://oe5tkubcj.bkt.clouddn.com/photo/161018/dbea7050e0724b2ab4acff47f88b1993.jpg!cvxsm","large":"http://oe5tkubcj.bkt.clouddn.com/photo/161018/dbea7050e0724b2ab4acff47f88b1993.jpg!cvxlg"}}
     * status : 1
     * order : 0
     * view_count : 0
     * created_at : 2016-10-18
     */

    public List<DataEntity> data;

    public static class MetaEntity {
        public String message;
        public int status_code;
    }

    public static class DataEntity {
        public int id;
        public String title;
        public Object sub_title;
        public Object content;
        public String summary;
        public int column_space_id;
        public int type;
        public int evt;
        public String url;
        public int cover_id;
        /**
         * id : 134
         * filepath : photo/161018/dbea7050e0724b2ab4acff47f88b1993.jpg
         * size : 267305
         * width : 1200
         * height : 1200
         * duration : 0
         * kind : 1
         * file : {"srcfile":"http://oe5tkubcj.bkt.clouddn.com/photo/161018/dbea7050e0724b2ab4acff47f88b1993.jpg","small":"http://oe5tkubcj.bkt.clouddn.com/photo/161018/dbea7050e0724b2ab4acff47f88b1993.jpg!cvxsm","large":"http://oe5tkubcj.bkt.clouddn.com/photo/161018/dbea7050e0724b2ab4acff47f88b1993.jpg!cvxlg"}
         */

        public CoverEntity cover;
        public int status;
        public int order;
        public int view_count;
        public String created_at;

        public static class CoverEntity {
            public int id;
            public String filepath;
            public int size;
            public int width;
            public int height;
            public int duration;
            public int kind;
            /**
             * srcfile : http://oe5tkubcj.bkt.clouddn.com/photo/161018/dbea7050e0724b2ab4acff47f88b1993.jpg
             * small : http://oe5tkubcj.bkt.clouddn.com/photo/161018/dbea7050e0724b2ab4acff47f88b1993.jpg!cvxsm
             * large : http://oe5tkubcj.bkt.clouddn.com/photo/161018/dbea7050e0724b2ab4acff47f88b1993.jpg!cvxlg
             */

            public FileEntity file;

            public static class FileEntity {
                public String srcfile;
                public String small;
                public String large;
            }
        }
    }
}
