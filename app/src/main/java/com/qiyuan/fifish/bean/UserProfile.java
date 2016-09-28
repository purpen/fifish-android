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
     * id : 1
     * account : 215141271@qq.com
     * username : 董先生
     * job :
     * zone : <UITextField: 0x1309eeaa0; frame = (55 138.5; 263 44); text = ''; clipsToBounds = YES; opaque = NO;
     * summary : null
     * follow_count : 0
     * fans_count : 0
     * stuff_count : 0
     * like_count : 0
     * avatar : {"small":"","large":""}
     */

    public DataBean data;
    /**
     * message : Success.
     * status_code : 200
     */

    public MetaBean meta;

    public static class DataBean {
        public String id;
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

    public static class MetaBean {
        public String message;
        public int status_code;
    }

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
}
