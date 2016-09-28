package com.qiyuan.fifish.network;

import android.text.TextUtils;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.SPUtil;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.common.util.MD5;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

/**
 * @author lilin
 *         created at 2016/8/5 15:49
 */
public class RequestService {
    /**
     * 登录用户
     *
     * @param account  账号
     * @param password 密码
     * @param callBack 请求回调
     */
    public static void loginUser(String account, String password, Callback.CommonCallback<String> callBack) {
        RequestParams params = new RequestParams(Constants.LOGIN_USER_URL);
        params.addQueryStringParameter("account", account);
        params.addQueryStringParameter("password", password);
        Callback.Cancelable cancelable = x.http().post(params, callBack);
        RequestManager.getInstance().add(MD5.md5(Constants.LOGIN_USER_URL), cancelable);
    }

    /**
     * 注册用户
     *
     * @param account  账号
     * @param password 密码
     * @param callBack 请求回调
     */
    public static void registerUser(String account, String password, Callback.CommonCallback<String> callBack) {
        RequestParams params = new RequestParams(Constants.REGISTER_USER_URL);
        params.addQueryStringParameter("account", account);
        params.addQueryStringParameter("password", password);
        Callback.Cancelable cancelable = x.http().post(params, callBack);
        RequestManager.getInstance().add(MD5.md5(Constants.REGISTER_USER_URL), cancelable);
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
    public static void getUserProfile(CustomCallBack callBack) {
        RequestParams params = new RequestParams(Constants.USER_PROFILE_URL);
        addToken(params);
        Callback.Cancelable cancelable = x.http().get(params, callBack);
        RequestManager.getInstance().add(MD5.md5(Constants.USER_PROFILE_URL), cancelable);
    }

    /**
     * 添加token
     *
     * @param params
     */
    private static void addToken(RequestParams params) {
        if (params == null) return;
        params.addQueryStringParameter("token", SPUtil.read(Constants.TOKEN));
        LogUtil.e("添加token==" + SPUtil.read(Constants.TOKEN));
    }

    public static void logout(CustomCallBack callBack) {
        RequestParams params = new RequestParams(Constants.USER_LOGOUT_URL);
        Callback.Cancelable cancelable = x.http().post(params, callBack);
        RequestManager.getInstance().add(MD5.md5(Constants.USER_LOGOUT_URL), cancelable);
    }

    /**
     * 获取关注的人
     *
     * @param id
     * @param callBack
     */
    public static void getFocus(String id, CustomCallBack callBack) {
        String url = Constants.BASE_URL + "user/" + id + "/followers";
        RequestParams params = new RequestParams(url);
//        addToken(params);
        Callback.Cancelable cancelable = x.http().get(params, callBack);
        RequestManager.getInstance().add(MD5.md5(url), cancelable);
    }

    /**
     * 获取粉丝
     *
     * @param id
     * @param callBack
     */
    public static void getFans(String id, CustomCallBack callBack) {
        String url = Constants.BASE_URL + "user/" + id + "/fans";
        RequestParams params = new RequestParams(url);
//        addToken(params);
        Callback.Cancelable cancelable = x.http().get(params, callBack);
        RequestManager.getInstance().add(MD5.md5(url), cancelable);
    }

    /**
     * 意见反馈
     *
     * @param contact
     * @param content
     * @param callBack
     */
    public static void submitFeedBack(String contact, String content, CustomCallBack callBack) {
        RequestParams params = new RequestParams(Constants.FEEDBACK_URL);
        params.addQueryStringParameter("contact", contact);
        params.addQueryStringParameter("content", content);
        Callback.Cancelable cancelable = x.http().post(params, callBack);
        RequestManager.getInstance().add(MD5.md5(Constants.FEEDBACK_URL), cancelable);
    }

    /**
     * 获取用户作品(图片和视频)
     *
     * @param callBack
     */
    public static void getProducts(String page, String per_page, String kind, String user_id, String sort, CustomCallBack callBack) {
        RequestParams params = new RequestParams(Constants.USER_PRODUCTS_URL);
        params.addQueryStringParameter("page", page);
        params.addQueryStringParameter("per_page", per_page);
        params.addQueryStringParameter("kind", kind);
        params.addQueryStringParameter("user_id", user_id);
        params.addQueryStringParameter("sort", sort);
//        addToken(params);
        Callback.Cancelable cancelable = x.http().get(params, callBack);
        RequestManager.getInstance().add(MD5.md5(Constants.USER_PRODUCTS_URL), cancelable);
    }

    /**
     * 热门标签
     *
     * @param callBack
     */
    public static void getHotTags(CustomCallBack callBack) {
        RequestParams params = new RequestParams(Constants.HOT_TAGS_URL);
        Callback.Cancelable cancelable = x.http().get(params, callBack);
        RequestManager.getInstance().add(MD5.md5(Constants.HOT_TAGS_URL), cancelable);
    }

    /**
     * 热门用户
     *
     * @param callBack
     */
    public static void getHotUsers(CustomCallBack callBack) {
        RequestParams params = new RequestParams(Constants.HOT_USERS_URL);
        Callback.Cancelable cancelable = x.http().get(params, callBack);
        RequestManager.getInstance().add(MD5.md5(Constants.HOT_USERS_URL), cancelable);
    }

    /**
     * 点赞
     * @param id
     * @param callBack
     */
    public static void doSupport(String id, CustomCallBack callBack) {
        String url = Constants.BASE_URL+"stuffs/"+id+"/dolike";
        RequestParams params = new RequestParams(url);
        Callback.Cancelable cancelable = x.http().post(params, callBack);
        RequestManager.getInstance().add(MD5.md5(url), cancelable);
    }
    /**
     * 取消点赞
     * @param id
     * @param callBack
     */
    public static void cancelSupport(String id, CustomCallBack callBack) {
        String url = Constants.BASE_URL+"stuffs/"+id+"/cancelike";
        RequestParams params = new RequestParams(url);
        Callback.Cancelable cancelable = x.http().post(params, callBack);
        RequestManager.getInstance().add(MD5.md5(url), cancelable);
    }

    /**
     * 上传头像
     * @param file
     * @param callBack
     */
    public static void upLoadFile(File file,String token,String upload_url, CustomCallBack callBack) {
        if (file==null || TextUtils.isEmpty(token)|| TextUtils.isEmpty(upload_url)) return;
        RequestParams params = new RequestParams(upload_url);
        params.setMultipart(true);
        params.addBodyParameter("token",token);
        params.addBodyParameter("file",file);
        Callback.Cancelable cancelable = x.http().post(params, callBack);
        RequestManager.getInstance().add(MD5.md5(upload_url), cancelable);
    }

    /**
     * @param assetable_type 上传类型
     * @param customCallBack
     */
    public static void getQNUrlToken(String assetable_type, CustomCallBack customCallBack) {
        RequestParams params = new RequestParams(Constants.QN_PARAM_URL);
        params.addBodyParameter("assetable_type",assetable_type);
//        addToken(params);
        Callback.Cancelable cancelable = x.http().get(params, customCallBack);
        RequestManager.getInstance().add(MD5.md5(Constants.QN_PARAM_URL), cancelable);
    }

    /**
     * 获取作品的评论
     * @param id
     * @param customCallBack
     */
    public static void getProductsComments(String id, CustomCallBack customCallBack) {
        String url=Constants.BASE_URL+"stuffs/"+id+"/comments";
        RequestParams params = new RequestParams(url);
        Callback.Cancelable cancelable = x.http().get(params, customCallBack);
        RequestManager.getInstance().add(MD5.md5(url), cancelable);
    }
}
