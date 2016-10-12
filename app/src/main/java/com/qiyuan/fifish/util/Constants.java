package com.qiyuan.fifish.util;

/**
 * @author lilin
 *         created at 2016/6/27 15:12
 */
public class Constants {
    public static final String BASE_URL = "http://fish.taihuoniao.com/api/";
    public static final String LOGIN_USER_URL= Constants.BASE_URL+"auth/login";
    public static final String REGISTER_USER_URL= Constants.BASE_URL+"auth/register";
    public static final String USER_PROFILE_URL= Constants.BASE_URL+"user/profile";
    public static final String USER_LOGOUT_URL= Constants.BASE_URL+"auth/logout";
    public static final String FEEDBACK_URL= Constants.BASE_URL+"feedback/submit";
    public static final String USER_PRODUCTS_URL= Constants.BASE_URL+"stuffs";
    public static final String HOT_TAGS_URL= Constants.BASE_URL+"tags/sticks";
    public static final String HOT_USERS_URL= Constants.BASE_URL+"user/hot_users";
    public static final String UPLOAD_AVATAR_URL= Constants.BASE_URL+"upload/avatar";
    public static final String QN_PARAM_URL= Constants.BASE_URL+"upload/qiniuToken";

    public static final int REQUEST_LABEL = 10;
    public static final int REQUEST_ADDRESS = 10;

    public static final int HTTP_OK = 200;
    public static final int HTTP_ACCOUNT_ALREADY_EXIST = 422;
    public static final int HTTP_NOT_FOUND = 404;

    public static final int BANNER_INTERVAL = 3000;
    public static final int GUIDE_INTERVAL = 2000;
    public static final String CHARSET = "UTF-8";
    public static  final String PAGE_SIZE = "8";
    public static final String PAGE_TAG = "PAGE_TAG";
    public static final String LOGIN_INFO = "login_info";
    public static final  String GUIDE_TAG = "guide";
    public static final  String TOKEN = "TOKEN";
    public static final  String USER_ID = "USER_ID";
    public static final String TYPE_IMAGE = "1";
    public static final String TYPE_VIDEO = "2";
}
