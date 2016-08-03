package com.qiyuan.fifish.bean;

import android.text.TextUtils;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.JsonUtil;
import com.qiyuan.fifish.util.SPUtil;

import java.io.Serializable;
import java.util.ArrayList;
public class LoginUserInfo implements Serializable {
    public long _id;
    public String message;
    public String account;
    public String nickname;
    public String true_nickname;
    public String realname;
    public int sex;
    public String medium_avatar_url;
    public String birthday;
    public String address;
    public String zip;
    public String im_qq;
    public String weixin;
    public String company;
    public String phone;
    public String summary;
    public String label;
    public String head_pic_url;
    public String expert_info;
    public int first_login;
    private static LoginUserInfo loginInfo;
    public Identify identify;
    public ArrayList<String> areas;
    public class Identify{
        public int is_scene_subscribe;
    }

    private LoginUserInfo() {}

    private static class UserInfoHolder{
       private static LoginUserInfo instance=new LoginUserInfo();
    }

    public static LoginUserInfo getInstance() {
        return UserInfoHolder.instance;
    }

    public static LoginUserInfo getLoginInfo(){
        if (isUserLogin()) {
            String login_info = SPUtil.read(Constants.LOGIN_INFO);
            loginInfo = JsonUtil.fromJson(login_info,LoginUserInfo.class);
            return loginInfo;
        }
        return null;
    }

    public static boolean isUserLogin() {
        String login_info = SPUtil.read(Constants.LOGIN_INFO);
        return !TextUtils.isEmpty(login_info);
    }

    public static long getUserId(){
        return null==getLoginInfo()?0:getLoginInfo()._id;
    }
}
