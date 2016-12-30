package com.qiyuan.fifish.bean;

import android.text.TextUtils;

import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.JsonUtil;
import com.qiyuan.fifish.util.SPUtil;

import java.io.Serializable;

/**
 * @author lilin
 *         created at 2016/8/30 9:37
 */
public class UserProfile implements Serializable {

    /**
     * id : 2
     * account : 1059232202@qq.com
     * username : 1059232202@qq.com
     * job :
     * sex : 0
     * zone :
     * summary : null
     * follow_count : 0
     * fans_count : 1
     * stuff_count : 0
     * like_count : 0
     * alert_total_count : 2
     * avatar : {"small":"http://s3.qysea.com/img/avatar!smx50.png","large":"http://s3.qysea.com/img/avatar!lgx180.png"}
     * first_login : true
     * following : false
     */

    public DataEntity data;
    /**
     * message : Success.
     * status_code : 200
     */

    public MetaEntity meta;

    public static UserProfile getUserProfile() {
        if (isUserLogin()) {
            String login_info = SPUtil.read(Constants.LOGIN_INFO);
            UserProfile profile = JsonUtil.fromJson(login_info, UserProfile.class);
            return profile;
        }
        return null;
    }

    public static String getUserId() {
        UserProfile profile = getUserProfile();
        if (profile != null) {
            return profile.data.id;
        }
        return "";
    }

    public static boolean isUserLogin() {
        String login_info = SPUtil.read(Constants.LOGIN_INFO);
        return !TextUtils.isEmpty(login_info);
    }

    private static class UserProfileHolder{
        private static UserProfile instance=new UserProfile();
    }

    public static UserProfile getInstance() {
        return UserProfileHolder.instance;
    }


    public static class DataEntity implements Serializable{
        public String id;
        public String account;
        public String username;
        public String job;
        public int sex;
        public String zone;
        public String summary;
        public String follow_count;
        public String fans_count;
        public String stuff_count;
        public String like_count;
        public int alert_total_count;
        /**
         * small : http://s3.qysea.com/img/avatar!smx50.png
         * large : http://s3.qysea.com/img/avatar!lgx180.png
         */

        public AvatarEntity avatar;
        public boolean first_login;
        public boolean following;

        public static class AvatarEntity implements Serializable{
            public String small;
            public String large;
        }
    }

    public static class MetaEntity implements Serializable{
        public String message;
        public int status_code;
    }
}
