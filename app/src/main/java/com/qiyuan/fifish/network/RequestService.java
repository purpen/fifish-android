package com.qiyuan.fifish.network;

import com.qiyuan.fifish.util.Constants;

import org.xutils.common.Callback;
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
        params.addBodyParameter("account",account);
        params.addBodyParameter("password",password);
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
        params.addBodyParameter("account",account);
        params.addBodyParameter("password",password);
        Callback.Cancelable cancelable = x.http().post(params, callBack);
        RequestManager.getInstance().add(MD5.md5(Constants.REGISTER_USER_URL),cancelable);
    }
}
