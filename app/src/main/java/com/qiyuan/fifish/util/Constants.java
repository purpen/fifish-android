package com.qiyuan.fifish.util;

/**
 * @author lilin
 *         created at 2016/6/27 15:12
 */
public class Constants {
    public static final boolean DEVELOPER_MODE = true;
    public static final String BASE_URL = "http://fish.taihuoniao.com/api/";
    public static final String LOGIN_USER_URL= Constants.BASE_URL+"auth/login";
    public static final String REGISTER_USER_URL= Constants.BASE_URL+"auth/register";

    public static final String PIC_URI = BASE_URL;
    public static final String SHARE_URI = BASE_URL;
    public static final String MAGZINE_URI = BASE_URL;
    public static final String WX_PAY_URI = BASE_URL;
    public static final int BANNER_INTERVAL = 3000;
    public static final int GUIDE_INTERVAL = 2000;
    public static final String CHARSET = "UTF-8";

    public final static int PAGE_SIZE = 10;
    public static final String PAGE_TAG = "PAGE_TAG";
    public static final String LOGIN_INFO = "login_info";
    public final static String GUIDE_TAG = "guide";
    public static final String WX_PAY = "";
    public static final String ALI_PAY = "";
}
