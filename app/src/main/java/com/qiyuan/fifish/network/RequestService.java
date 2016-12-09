package com.qiyuan.fifish.network;

import android.text.TextUtils;

import com.qiyuan.fifish.network.params.AddNewProductsParams;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.SPUtil;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.common.util.MD5;
import org.xutils.http.HttpMethod;
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
        params.setConnectTimeout(30000);
        LogUtil.e("添加token==" + SPUtil.read(Constants.TOKEN));
    }

    public static void logout(CustomCallBack callBack) {
        RequestParams params = new RequestParams(Constants.USER_LOGOUT_URL);
        addToken(params);
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
        addToken(params);
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
        addToken(params);
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
        addToken(params);
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
        addToken(params);
        Callback.Cancelable cancelable = x.http().get(params, callBack);
        RequestManager.getInstance().add(MD5.md5(Constants.USER_PRODUCTS_URL), cancelable);
    }

    /**
     * 热门标签
     * @param callBack
     */
    public static void getHotTags(CustomCallBack callBack) {
        RequestParams params = new RequestParams(Constants.HOT_TAGS_URL);
        addToken(params);
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
        addToken(params);
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
        addToken(params);
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
        addToken(params);
        Callback.Cancelable cancelable = x.http().post(params, callBack);
        RequestManager.getInstance().add(MD5.md5(url), cancelable);
    }

    /**
     * 关注
     * @param id
     * @param callBack
     */
    public static void doFocus(String id, CustomCallBack callBack) {
        String url = Constants.BASE_URL+"user/"+id+"/follow";
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("token", SPUtil.read(Constants.TOKEN));
        Callback.Cancelable cancelable = x.http().post(params, callBack);
        RequestManager.getInstance().add(MD5.md5(url), cancelable);
    }
    /**
     * 取消关注
     * @param id
     * @param callBack
     */
    public static void cancelFocus(String id, CustomCallBack callBack) {
        String url = Constants.BASE_URL+"user/"+id+"/cancelFollow";
        RequestParams params = new RequestParams(url);
        addToken(params);
        Callback.Cancelable cancelable = x.http().request(HttpMethod.DELETE,params, callBack);
        RequestManager.getInstance().add(MD5.md5(url), cancelable);
    }

    /**
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
     * 上传头像Token
     * @param customCallBack
     */
    public static void getAvatarToken(CustomCallBack customCallBack) {
        RequestParams params = new RequestParams(Constants.AVATAR_TOKEN_URL);
        addToken(params);
        Callback.Cancelable cancelable = x.http().get(params, customCallBack);
        RequestManager.getInstance().add(MD5.md5(Constants.AVATAR_TOKEN_URL), cancelable);
    }
    /**
     * 上传图片Token
     * @param customCallBack
     */
    public static void getPhotoToken(CustomCallBack customCallBack) {
        RequestParams params = new RequestParams(Constants.PHOTO_TOKEN_URL);
        addToken(params);
        Callback.Cancelable cancelable = x.http().get(params, customCallBack);
        RequestManager.getInstance().add(MD5.md5(Constants.PHOTO_TOKEN_URL), cancelable);
    }
    /**
     * 上传视频Token
     * @param customCallBack
     */
    public static void getVideoToken(CustomCallBack customCallBack) {
        RequestParams params = new RequestParams(Constants.VIDEO_TOKEN_URL);
        addToken(params);
        Callback.Cancelable cancelable = x.http().get(params, customCallBack);
        RequestManager.getInstance().add(MD5.md5(Constants.VIDEO_TOKEN_URL), cancelable);
    }



    /**
     * 获取作品的评论
     * @param id
     * @param customCallBack
     */
    public static void getProductsComments(String id, CustomCallBack customCallBack) {
        String url=Constants.BASE_URL+"stuffs/"+id+"/comments";
        RequestParams params = new RequestParams(url);
        addToken(params);
        Callback.Cancelable cancelable = x.http().get(params, customCallBack);
        RequestManager.getInstance().add(MD5.md5(url), cancelable);
    }

    /**
     * 搜索建议
     * @param q
     * @param size
     * @param customCallBack
     */
    public static void searchSuggestion(String q, String size, CustomCallBack customCallBack) {
        String url=Constants.BASE_URL+"search/expanded";
        RequestParams params = new RequestParams(url);
        params.addQueryStringParameter("q",q);
        params.addQueryStringParameter("size",size);
        addToken(params);
        Callback.Cancelable cancelable = x.http().get(params, customCallBack);
        RequestManager.getInstance().add(MD5.md5(url), cancelable);
    }

    /**
     * 站内搜索作品和用户
     * @param page
     * @param str   关键字
     * @param type 类型：1.作品；2.用户；3.－－；
     * @param tid 子类型(根据父类型判断条件)：作品：0.所有；1.图片；2.视频；
     * @param evt 搜索方式：1.内容；2.标签；
     * @param sort 排序：0.关联度；1.最新创建；2.最近更新；
     * @param customCallBack
     */
    public static void searchInSite(String page, String str, String type, String tid, String evt, String sort, CustomCallBack customCallBack) {
        String url=Constants.BASE_URL+"search/list";
        RequestParams params = new RequestParams(url);
        params.addQueryStringParameter("page",page);
        params.addQueryStringParameter("per_page",Constants.PAGE_SIZE);
        params.addQueryStringParameter("str",str);
        params.addQueryStringParameter("type",type);
        params.addQueryStringParameter("tid",tid);
        params.addQueryStringParameter("evt",evt);
        params.addQueryStringParameter("sort",sort);
        addToken(params);
        Callback.Cancelable cancelable = x.http().get(params, customCallBack);
        RequestManager.getInstance().add(MD5.md5(url), cancelable);
    }

    /**
     * 上传新作品信息
     * @param content
     * @param city
     * @param address
     * @param kind
     * @param tags
     * @param customCallBack
     */
    public static void addNewProducts(String content,String asset_id,String city,String address,String lat,String lng,String kind,String[] tags,CustomCallBack customCallBack) {
//        RequestParams params = new RequestParams(Constants.STUFFS_STORE_URL);
//        params.addQueryStringParameter("content",content);
//        params.addQueryStringParameter("city",city);
//        params.addQueryStringParameter("asset_id",asset_id);
//        params.addQueryStringParameter("address",address);
//        params.addQueryStringParameter("kind",kind);
//        params.addQueryStringParameter("lat",lat);
//        params.addQueryStringParameter("lng",lng);
//        params.addQueryStringParameter("tags",tags);
        AddNewProductsParams params = new AddNewProductsParams();
        params.content=content;
        params.city=city;
        params.asset_id=asset_id;
        params.address=address;
        params.kind=kind;
        params.lat=lat;
        params.lng=lng;
        params.tags=tags;
        LogUtil.e(""+tags);
        addToken(params);
        Callback.Cancelable cancelable = x.http().post(params,customCallBack);
        RequestManager.getInstance().add(MD5.md5(Constants.STUFFS_STORE_URL), cancelable);
    }

    /**
     * 获取轮换图
     * @param page
     * @param per_page
     * @param name
     * @param customCallBack
     */
    public static void getBanners(String page,String per_page,String name,CustomCallBack customCallBack){
        String url=Constants.BASE_URL+"gateway/columns";
        RequestParams params = new RequestParams(url);
        params.addQueryStringParameter("page",page);
        params.addQueryStringParameter("per_page",per_page);
        params.addQueryStringParameter("name",name);
        addToken(params);
        Callback.Cancelable cancelable = x.http().get(params,customCallBack);
        RequestManager.getInstance().add(MD5.md5(url), cancelable);
    }

    /**
     * 获取我的评论列表
     * @param customCallBack
     */
    public static void getMyComments(CustomCallBack customCallBack) {
        String url=Constants.BASE_URL+"me/gotComment";
        RequestParams params = new RequestParams(url);
        addToken(params);
        Callback.Cancelable cancelable = x.http().get(params,customCallBack);
        RequestManager.getInstance().add(MD5.md5(url), cancelable);
    }

    /**
     * 更新密码
     * @param originPsd
     * @param newPsd
     * @param confirmNewPsd
     * @param customCallBack
     */
    public static void updatePassword(String originPsd, String newPsd, String confirmNewPsd, CustomCallBack customCallBack) {
        String url=Constants.BASE_URL+"me/updatePassword";
        RequestParams params = new RequestParams(url);
        params.addQueryStringParameter("old_password",originPsd);
        params.addQueryStringParameter("new_password",newPsd);
        params.addQueryStringParameter("confrim_password",confirmNewPsd);
        addToken(params);
        Callback.Cancelable cancelable = x.http().post(params,customCallBack);
        RequestManager.getInstance().add(MD5.md5(url), cancelable);
    }

    /**
     * 获得赞过的作品
     * @param kind
     * @param customCallBack
     */
    public static void getSupportProducts(String kind, CustomCallBack customCallBack) {
        String url=Constants.BASE_URL+"me/likeStuffs";
        RequestParams params = new RequestParams(url);
        params.addQueryStringParameter("kind",kind);
        addToken(params);
        Callback.Cancelable cancelable = x.http().get(params,customCallBack);
        RequestManager.getInstance().add(MD5.md5(url), cancelable);
    }

    /**
     * 评论列表
     * @param content
     * @param reply_user_id
     * @param parent_id
     * @param customCallBack
     */
    public static void postComment(String content,String id,String reply_user_id, String parent_id, CustomCallBack customCallBack){
        String url=Constants.BASE_URL+"stuffs/"+id+"/postComment";
        RequestParams params = new RequestParams(url);
        params.addQueryStringParameter("content",content);
        params.addQueryStringParameter("reply_user_id",reply_user_id);
        params.addQueryStringParameter("parent_id",parent_id);
        addToken(params);
        Callback.Cancelable cancelable = x.http().post(params,customCallBack);
        RequestManager.getInstance().add(MD5.md5(url), cancelable);
    }

    /**
     * 根据标签搜索内容
     * @param page
     * @param tag
     * @param customCallBack
     */
    public static void getResultByTagName(String page, String tag, CustomCallBack customCallBack) {
        String url=Constants.BASE_URL+"tags/"+tag;
        RequestParams params = new RequestParams(url);
        params.addQueryStringParameter("name",tag);
        addToken(params);
        Callback.Cancelable cancelable = x.http().get(params,customCallBack);
        RequestManager.getInstance().add(MD5.md5(url), cancelable);
    }
}
