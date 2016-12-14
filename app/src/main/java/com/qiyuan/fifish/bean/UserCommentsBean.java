package com.qiyuan.fifish.bean;

import java.util.List;

/**
 * Created by lilin on 2016/12/14.
 */

public class UserCommentsBean {

    /**
     * data : [{"id":257,"evt":"评论了","content":"落叶归根","sender":{"id":45,"username":"大洋百货","summary":null,"first_login":1,"last_login":"2016-12-02 14:21:29","avatar":{"small":"https://s3.qysea.com/avatar/161208/7130a36c61e2732e0db6805a46f18a8d.jpg!smx50","large":"https://s3.qysea.com/avatar/161208/7130a36c61e2732e0db6805a46f18a8d.jpg!lgx180"}},"stuff":{"id":156,"kind":1,"cover":{"id":346,"filepath":"photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg","size":341560,"width":650,"height":488,"duration":0,"kind":1,"file":{"srcfile":"https://s3.qysea.com/photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg","small":"https://s3.qysea.com/photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg!cvxsm","large":"https://s3.qysea.com/photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg!hd","thumb":"https://s3.qysea.com/photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg!psq","adpic":"https://s3.qysea.com/photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg!plg"}}},"created_at":"2016-12-06"},{"id":256,"evt":"评论了","content":"一只","sender":{"id":45,"username":"大洋百货","summary":null,"first_login":1,"last_login":"2016-12-02 14:21:29","avatar":{"small":"https://s3.qysea.com/avatar/161208/7130a36c61e2732e0db6805a46f18a8d.jpg!smx50","large":"https://s3.qysea.com/avatar/161208/7130a36c61e2732e0db6805a46f18a8d.jpg!lgx180"}},"stuff":{"id":156,"kind":1,"cover":{"id":346,"filepath":"photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg","size":341560,"width":650,"height":488,"duration":0,"kind":1,"file":{"srcfile":"https://s3.qysea.com/photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg","small":"https://s3.qysea.com/photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg!cvxsm","large":"https://s3.qysea.com/photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg!hd","thumb":"https://s3.qysea.com/photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg!psq","adpic":"https://s3.qysea.com/photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg!plg"}}},"created_at":"2016-12-06"},{"id":255,"evt":"评论了","content":"一只","sender":{"id":45,"username":"大洋百货","summary":null,"first_login":1,"last_login":"2016-12-02 14:21:29","avatar":{"small":"https://s3.qysea.com/avatar/161208/7130a36c61e2732e0db6805a46f18a8d.jpg!smx50","large":"https://s3.qysea.com/avatar/161208/7130a36c61e2732e0db6805a46f18a8d.jpg!lgx180"}},"stuff":{"id":156,"kind":1,"cover":{"id":346,"filepath":"photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg","size":341560,"width":650,"height":488,"duration":0,"kind":1,"file":{"srcfile":"https://s3.qysea.com/photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg","small":"https://s3.qysea.com/photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg!cvxsm","large":"https://s3.qysea.com/photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg!hd","thumb":"https://s3.qysea.com/photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg!psq","adpic":"https://s3.qysea.com/photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg!plg"}}},"created_at":"2016-12-06"},{"id":252,"evt":"评论了","content":"世界","sender":{"id":45,"username":"大洋百货","summary":null,"first_login":1,"last_login":"2016-12-02 14:21:29","avatar":{"small":"https://s3.qysea.com/avatar/161208/7130a36c61e2732e0db6805a46f18a8d.jpg!smx50","large":"https://s3.qysea.com/avatar/161208/7130a36c61e2732e0db6805a46f18a8d.jpg!lgx180"}},"stuff":{"id":156,"kind":1,"cover":{"id":346,"filepath":"photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg","size":341560,"width":650,"height":488,"duration":0,"kind":1,"file":{"srcfile":"https://s3.qysea.com/photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg","small":"https://s3.qysea.com/photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg!cvxsm","large":"https://s3.qysea.com/photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg!hd","thumb":"https://s3.qysea.com/photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg!psq","adpic":"https://s3.qysea.com/photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg!plg"}}},"created_at":"2016-12-06"}]
     * meta : {"message":"Success.","status_code":200,"pagination":{"total":4,"count":4,"per_page":10,"current_page":1,"total_pages":1,"links":[]}}
     */

    public MetaBean meta;
    public List<DataBean> data;

    public static class MetaBean {
        /**
         * message : Success.
         * status_code : 200
         * pagination : {"total":4,"count":4,"per_page":10,"current_page":1,"total_pages":1,"links":[]}
         */

        public String message;
        public int status_code;
    }

    public static class DataBean {
        /**
         * id : 257
         * evt : 评论了
         * content : 落叶归根
         * sender : {"id":45,"username":"大洋百货","summary":null,"first_login":1,"last_login":"2016-12-02 14:21:29","avatar":{"small":"https://s3.qysea.com/avatar/161208/7130a36c61e2732e0db6805a46f18a8d.jpg!smx50","large":"https://s3.qysea.com/avatar/161208/7130a36c61e2732e0db6805a46f18a8d.jpg!lgx180"}}
         * stuff : {"id":156,"kind":1,"cover":{"id":346,"filepath":"photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg","size":341560,"width":650,"height":488,"duration":0,"kind":1,"file":{"srcfile":"https://s3.qysea.com/photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg","small":"https://s3.qysea.com/photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg!cvxsm","large":"https://s3.qysea.com/photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg!hd","thumb":"https://s3.qysea.com/photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg!psq","adpic":"https://s3.qysea.com/photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg!plg"}}}
         * created_at : 2016-12-06
         */

        public int id;
        public String evt;
        public String content;
        public SenderBean sender;
        public StuffBean stuff;
        public String created_at;

        public static class SenderBean {
            /**
             * id : 45
             * username : 大洋百货
             * summary : null
             * first_login : 1
             * last_login : 2016-12-02 14:21:29
             * avatar : {"small":"https://s3.qysea.com/avatar/161208/7130a36c61e2732e0db6805a46f18a8d.jpg!smx50","large":"https://s3.qysea.com/avatar/161208/7130a36c61e2732e0db6805a46f18a8d.jpg!lgx180"}
             */

            public int id;
            public String username;
            public String summary;
            public int first_login;
            public String last_login;
            public AvatarBean avatar;

            public static class AvatarBean {
                /**
                 * small : https://s3.qysea.com/avatar/161208/7130a36c61e2732e0db6805a46f18a8d.jpg!smx50
                 * large : https://s3.qysea.com/avatar/161208/7130a36c61e2732e0db6805a46f18a8d.jpg!lgx180
                 */

                public String small;
                public String large;
            }
        }

        public static class StuffBean {
            /**
             * id : 156
             * kind : 1
             * cover : {"id":346,"filepath":"photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg","size":341560,"width":650,"height":488,"duration":0,"kind":1,"file":{"srcfile":"https://s3.qysea.com/photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg","small":"https://s3.qysea.com/photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg!cvxsm","large":"https://s3.qysea.com/photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg!hd","thumb":"https://s3.qysea.com/photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg!psq","adpic":"https://s3.qysea.com/photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg!plg"}}
             */

            public String id;
            public int kind;
            public CoverBean cover;

            public static class CoverBean {
                /**
                 * id : 346
                 * filepath : photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg
                 * size : 341560
                 * width : 650
                 * height : 488
                 * duration : 0
                 * kind : 1
                 * file : {"srcfile":"https://s3.qysea.com/photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg","small":"https://s3.qysea.com/photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg!cvxsm","large":"https://s3.qysea.com/photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg!hd","thumb":"https://s3.qysea.com/photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg!psq","adpic":"https://s3.qysea.com/photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg!plg"}
                 */

                public int id;
                public String filepath;
                public int size;
                public int width;
                public int height;
                public int duration;
                public int kind;
                public FileBean file;

                public static class FileBean {
                    /**
                     * srcfile : https://s3.qysea.com/photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg
                     * small : https://s3.qysea.com/photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg!cvxsm
                     * large : https://s3.qysea.com/photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg!hd
                     * thumb : https://s3.qysea.com/photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg!psq
                     * adpic : https://s3.qysea.com/photo/161206/da6c50a0616a0edea8bccdd3a951f6a8.jpg!plg
                     */

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
