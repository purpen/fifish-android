package com.qiyuan.fifish.util;

/**
 * @author lilin
 *         created at 2016/6/27 15:12
 */
public class Constants {
    public static final String BASE_URL = "http://api.dmore.com.cn/";
    public static final String APP_URI = BASE_URL + "mobile/api/client/interface.php";
    public static final String PIC_URI = BASE_URL;
    public static final String SHARE_URI = BASE_URL + "mobile/api/client/wap/?gid=";
    public static final String MAGZINE_URI = BASE_URL;
    public static final String WX_PAY_URI = BASE_URL + "mobile/api/client/weixin/unifiedorder.php";
    public static final int DEFAULT_INTERVAL = 4000;
    public static final int GUIDE_INTERVAL = 3000;
    public static final String CHARSET = "UTF-8";
    public final static int STATUS_OK = 1; //请求成功
    public final static int STATUS_FAIL = 0; //请求失败
    public final static int PAGE_SIZE = 10;
    public final static String APP_ID = "wxac92b0c6edcd78bf";
    public static final String PAGE_TAG = "PAGE_TAG";
    public static final String LOGIN_INFO = "login_info";
    public final static String GUIDE_TAG = "guide";
    public final static String LOGIN_TAG = "LOGIN_TAG";
    public static final String WX_PAY = "微信";
    public static final String ALI_PAY = "支付宝";
}
