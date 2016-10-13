package com.qiyuan.fifish.bean;

/**
 * @author lilin
 * created at 2016/10/13 18:41
 */
public class PublishProductsBean {

    /**
     * id : 10
     * content : 这是重大的设计走势
     * user_id : 10
     * tags : null
     * asset_id : 23
     */

    public DataEntity data;
    /**
     * message : Success.
     * status_code : 200
     */

    public MetaEntity meta;

    public static class DataEntity {
        public int id;
        public String content;
        public int user_id;
        public Object tags;
        public String asset_id;
    }

    public static class MetaEntity {
        public String message;
        public int status_code;
    }
}
