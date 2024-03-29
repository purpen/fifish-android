package com.qiyuan.fifish.network;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.SPUtil;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.common.util.MD5;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * @author lilin
 * created at 2016/8/5 15:49
 */
public class RequestService {
    /**
     * 登录用户
     * @param account 账号
     * @param password 密码
     * @param callBack 请求回调
     */
    public static void loginUser(String account, String password, Callback.CommonCallback<String> callBack) {
        RequestParams params = new RequestParams(Constants.LOGIN_USER_URL);
        params.addQueryStringParameter("account",account);
        params.addQueryStringParameter("password",password);
        Callback.Cancelable cancelable = x.http().post(params, callBack);
        RequestManager.getInstance().add(MD5.md5(Constants.LOGIN_USER_URL),cancelable);
    }

    /**
     * 注册用户
     * @param account 账号
     * @param password 密码
     * @param callBack 请求回调
     */
    public static void registerUser(String account, String password, Callback.CommonCallback<String> callBack) {
        RequestParams params = new RequestParams(Constants.REGISTER_USER_URL);
        params.addQueryStringParameter("account",account);
        params.addQueryStringParameter("password",password);
        Callback.Cancelable cancelable = x.http().post(params, callBack);
        RequestManager.getInstance().add(MD5.md5(Constants.REGISTER_USER_URL),cancelable);
    }

    /**
     * 获取用户资料
     * @param callBack
     */
//    public static void getUserProfile(Callback.CommonCallback<String> callBack) {
//        RequestParams params = new RequestParams(Constants.USER_PROFILE_URL);
//        addToken(params);
//        Callback.Cancelable cancelable = x.http().get(params,callBack);
//        RequestManager.getInstance().add(MD5.md5(Constants.USER_PROFILE_URL),cancelable);
//    }
    /**
     * 获取用户资料
     */
    public static void getUserProfile(CustomCallBack callBack){
        RequestParams params = new RequestParams(Constants.USER_PROFILE_URL);
        addToken(params);
        Callback.Cancelable cancelable = x.http().get(params,callBack);
        RequestManager.getInstance().add(MD5.md5(Constants.USER_PROFILE_URL),cancelable);
    }

    /**
     * 添加token
     * @param params
     */
    private static void addToken(RequestParams params){
        if (params==null) return;
        params.addQueryStringParameter("token",SPUtil.read(Constants.TOKEN));
        LogUtil.e("添加token=="+SPUtil.read(Constants.TOKEN));
    }

    public static void logout(CustomCallBack callBack) {
        RequestParams params = new RequestParams(Constants.USER_LOGOUT_URL);
        Callback.Cancelable cancelable = x.http().post(params, callBack);
        RequestManager.getInstance().add(MD5.md5(Constants.USER_LOGOUT_URL),cancelable);
    }
}
