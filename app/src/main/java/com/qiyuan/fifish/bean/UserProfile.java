package com.qiyuan.fifish.bean;

import android.text.TextUtils;

import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.JsonUtil;
import com.qiyuan.fifish.util.SPUtil;

import java.io.Serializable;

/**
 * @author lilin
 * created at 2016/8/30 9:37
 */
public class UserProfile implements Serializable{
    public DataBean data;
    public MetaBean meta;
    public static class DataBean {
        public String id;
        public String account;
        public String username;
        public String job;
        public String zone;

        public AvatarBean avatar;

        public static class AvatarBean {
            public String small;
            public String large;
        }
    }

    public static class MetaBean {
        public Meta meta;
        public static class Meta {
            public String message;
            public int status_code;
        }
    }

    public static UserProfile getUserProfile(){
        if (isUserLogin()) {
            String login_info = SPUtil.read(Constants.LOGIN_INFO);
            UserProfile profile = JsonUtil.fromJson(login_info, UserProfile.class);
            return profile;
        }
        return null;
    }

    public static String getUserId() {
        UserProfile profile = getUserProfile();
        if (profile!=null){
            return profile.data.id;
        }
        return null;
    }

    public static boolean isUserLogin() {
        String login_info = SPUtil.read(Constants.LOGIN_INFO);
        return !TextUtils.isEmpty(login_info);
    }

}
