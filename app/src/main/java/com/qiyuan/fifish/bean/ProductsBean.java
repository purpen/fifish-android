package com.qiyuan.fifish.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author lilin
 * created at 2016/9/20 21:03
 */
public class ProductsBean implements Serializable{

    /**
     * message : Success.
     * status_code : 200
     * pagination : {"total":104,"count":8,"per_page":8,"current_page":1,"total_pages":13,"links":{"next":"https://api.qysea.com/stuffs?page=2"}}
     */

    public MetaEntity meta;
    /**
     * id : 104
     * content : 急景凋年打扮打扮的
     * view_count : 0
     * kind : 1
     * city : null
     * address : 艺术
     * like_count : 0
     * comment_count : 0
     * user_id : 5
     * user : {"id":5,"username":"特斯拉","summary":null,"first_login":1,"last_login":"2016-10-21 10:22:36","avatar":{"small":"http://oe5tkubcj.bkt.clouddn.com/avatar/161101/f625910543eebd140aee5604ddca0c74.jpg!smx50","large":"http://oe5tkubcj.bkt.clouddn.com/avatar/161101/f625910543eebd140aee5604ddca0c74.jpg!lgx180"}}
     * tags : [{"id":2,"name":"蓝色大海","display_name":"蓝色大海","same_words":null,"total_count":5,"cover":{"id":144,"filepath":"photo/161018/4e543c867db939e3b287cc14d4fee502.jpg","size":48588,"width":634,"height":448,"duration":0,"kind":1,"file":{"srcfile":"http://oe5tkubcj.bkt.clouddn.com/photo/161018/4e543c867db939e3b287cc14d4fee502.jpg","small":"http://oe5tkubcj.bkt.clouddn.com/photo/161018/4e543c867db939e3b287cc14d4fee502.jpg!cvxsm","large":"http://oe5tkubcj.bkt.clouddn.com/photo/161018/4e543c867db939e3b287cc14d4fee502.jpg!cvxlg","thumb":"http://oe5tkubcj.bkt.clouddn.com/photo/161018/4e543c867db939e3b287cc14d4fee502.jpg!psq","adpic":"http://oe5tkubcj.bkt.clouddn.com/photo/161018/4e543c867db939e3b287cc14d4fee502.jpg!plg"}}}]
     * cover : {"id":201,"filepath":"photo/161103/62fd7fd9a233adadebd1328742a42043.jpg","size":704923,"width":750,"height":750,"duration":0,"kind":1,"file":{"srcfile":"http://oe5tkubcj.bkt.clouddn.com/photo/161103/62fd7fd9a233adadebd1328742a42043.jpg","small":"http://oe5tkubcj.bkt.clouddn.com/photo/161103/62fd7fd9a233adadebd1328742a42043.jpg!cvxsm","large":"http://oe5tkubcj.bkt.clouddn.com/photo/161103/62fd7fd9a233adadebd1328742a42043.jpg!cvxlg","thumb":"http://oe5tkubcj.bkt.clouddn.com/photo/161103/62fd7fd9a233adadebd1328742a42043.jpg!psq","adpic":"http://oe5tkubcj.bkt.clouddn.com/photo/161103/62fd7fd9a233adadebd1328742a42043.jpg!plg"}}
     * created_at : 2016-11-03
     * is_love : false
     * is_follow : false
     */

    public List<DataEntity> data;

    public static class MetaEntity {
        public String message;
        public int status_code;
    }

    public static class DataEntity implements Serializable{
        public String id;
        public String content;
        public String view_count;
        public String kind;
        public String city;
        public String address;
        public int like_count;
        public String comment_count;
        public String user_id;
        /**
         * id : 5
         * username : 特斯拉
         * summary : null
         * first_login : 1
         * last_login : 2016-10-21 10:22:36
         * avatar : {"small":"http://oe5tkubcj.bkt.clouddn.com/avatar/161101/f625910543eebd140aee5604ddca0c74.jpg!smx50","large":"http://oe5tkubcj.bkt.clouddn.com/avatar/161101/f625910543eebd140aee5604ddca0c74.jpg!lgx180"}
         */

        public UserEntity user;
        /**
         * id : 201
         * filepath : photo/161103/62fd7fd9a233adadebd1328742a42043.jpg
         * size : 704923
         * width : 750
         * height : 750
         * duration : 0
         * kind : 1
         * file : {"srcfile":"http://oe5tkubcj.bkt.clouddn.com/photo/161103/62fd7fd9a233adadebd1328742a42043.jpg","small":"http://oe5tkubcj.bkt.clouddn.com/photo/161103/62fd7fd9a233adadebd1328742a42043.jpg!cvxsm","large":"http://oe5tkubcj.bkt.clouddn.com/photo/161103/62fd7fd9a233adadebd1328742a42043.jpg!cvxlg","thumb":"http://oe5tkubcj.bkt.clouddn.com/photo/161103/62fd7fd9a233adadebd1328742a42043.jpg!psq","adpic":"http://oe5tkubcj.bkt.clouddn.com/photo/161103/62fd7fd9a233adadebd1328742a42043.jpg!plg"}
         */

        public CoverEntity cover;
        public String created_at;
        public boolean is_love;
        public boolean is_follow;
        /**
         * id : 2
         * name : 蓝色大海
         * display_name : 蓝色大海
         * same_words : null
         * total_count : 5
         * cover : {"id":144,"filepath":"photo/161018/4e543c867db939e3b287cc14d4fee502.jpg","size":48588,"width":634,"height":448,"duration":0,"kind":1,"file":{"srcfile":"http://oe5tkubcj.bkt.clouddn.com/photo/161018/4e543c867db939e3b287cc14d4fee502.jpg","small":"http://oe5tkubcj.bkt.clouddn.com/photo/161018/4e543c867db939e3b287cc14d4fee502.jpg!cvxsm","large":"http://oe5tkubcj.bkt.clouddn.com/photo/161018/4e543c867db939e3b287cc14d4fee502.jpg!cvxlg","thumb":"http://oe5tkubcj.bkt.clouddn.com/photo/161018/4e543c867db939e3b287cc14d4fee502.jpg!psq","adpic":"http://oe5tkubcj.bkt.clouddn.com/photo/161018/4e543c867db939e3b287cc14d4fee502.jpg!plg"}}
         */

        public List<TagsEntity> tags;

        public static class UserEntity implements Serializable{
            public String id;
            public String username;
            public String summary;
            public int first_login;
            public String last_login;
            /**
             * small : http://oe5tkubcj.bkt.clouddn.com/avatar/161101/f625910543eebd140aee5604ddca0c74.jpg!smx50
             * large : http://oe5tkubcj.bkt.clouddn.com/avatar/161101/f625910543eebd140aee5604ddca0c74.jpg!lgx180
             */

            public AvatarEntity avatar;

            public static class AvatarEntity implements Serializable{
                public String small;
                public String large;
            }
        }

        public static class CoverEntity implements Serializable{
            public String id;
            public String filepath;
            public float size;
            public int width;
            public int height;
            public float duration;
            public String kind;
            /**
             * srcfile : http://oe5tkubcj.bkt.clouddn.com/photo/161103/62fd7fd9a233adadebd1328742a42043.jpg
             * small : http://oe5tkubcj.bkt.clouddn.com/photo/161103/62fd7fd9a233adadebd1328742a42043.jpg!cvxsm
             * large : http://oe5tkubcj.bkt.clouddn.com/photo/161103/62fd7fd9a233adadebd1328742a42043.jpg!cvxlg
             * thumb : http://oe5tkubcj.bkt.clouddn.com/photo/161103/62fd7fd9a233adadebd1328742a42043.jpg!psq
             * adpic : http://oe5tkubcj.bkt.clouddn.com/photo/161103/62fd7fd9a233adadebd1328742a42043.jpg!plg
             */

            public FileEntity file;

            public static class FileEntity implements Serializable{
                public String srcfile;
                public String small;
                public String large;
                public String thumb;
                public String adpic;
            }
        }

        public static class TagsEntity implements Serializable{
            public String id;
            public String name;
            public String display_name;
            public String same_words;
            public String total_count;
            /**
             * id : 144
             * filepath : photo/161018/4e543c867db939e3b287cc14d4fee502.jpg
             * size : 48588
             * width : 634
             * height : 448
             * duration : 0
             * kind : 1
             * file : {"srcfile":"http://oe5tkubcj.bkt.clouddn.com/photo/161018/4e543c867db939e3b287cc14d4fee502.jpg","small":"http://oe5tkubcj.bkt.clouddn.com/photo/161018/4e543c867db939e3b287cc14d4fee502.jpg!cvxsm","large":"http://oe5tkubcj.bkt.clouddn.com/photo/161018/4e543c867db939e3b287cc14d4fee502.jpg!cvxlg","thumb":"http://oe5tkubcj.bkt.clouddn.com/photo/161018/4e543c867db939e3b287cc14d4fee502.jpg!psq","adpic":"http://oe5tkubcj.bkt.clouddn.com/photo/161018/4e543c867db939e3b287cc14d4fee502.jpg!plg"}
             */

            public CoverEntity cover;

            public static class CoverEntity implements Serializable{
                public int id;
                public String filepath;
                public int size;
                public int width;
                public int height;
                public int duration;
                public int kind;
                /**
                 * srcfile : http://oe5tkubcj.bkt.clouddn.com/photo/161018/4e543c867db939e3b287cc14d4fee502.jpg
                 * small : http://oe5tkubcj.bkt.clouddn.com/photo/161018/4e543c867db939e3b287cc14d4fee502.jpg!cvxsm
                 * large : http://oe5tkubcj.bkt.clouddn.com/photo/161018/4e543c867db939e3b287cc14d4fee502.jpg!cvxlg
                 * thumb : http://oe5tkubcj.bkt.clouddn.com/photo/161018/4e543c867db939e3b287cc14d4fee502.jpg!psq
                 * adpic : http://oe5tkubcj.bkt.clouddn.com/photo/161018/4e543c867db939e3b287cc14d4fee502.jpg!plg
                 */

                public FileEntity file;

                public static class FileEntity implements Serializable{
                    public String srcfile;
                    public String small;
                    public String large;
                    public String thumb;
                    public String adpic;
                }
            }
        }
    }
}
