package com.qiyuan.fifish.network;

import android.text.TextUtils;

import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.SPUtil;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.app.RequestInterceptListener;
import org.xutils.http.request.UriRequest;

/**
 * @author lilin
 * created at 2016/9/5 16:42
 */
public abstract class CustomCallBack implements RequestInterceptListener,Callback.CommonCallback<String>,Callback.ProgressCallback<String>{
    @Override
    public void beforeRequest(UriRequest request) throws Throwable {

    }

    @Override
    public void afterRequest(UriRequest request) throws Throwable {
        LogUtil.e(request.toString());
        String token = request.getResponseHeader("Authorization");
        if (!TextUtils.isEmpty(token)){
            token=token.split("\\s")[1];
            LogUtil.e("afterRequest=="+token);
            SPUtil.write(Constants.TOKEN,token);
        }
    }

    @Override
    public void onSuccess(String result) {

    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {

    }

    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }

    @Override
    public void onLoading(long total, long current, boolean isDownloading) {
        LogUtil.e("total=="+total+";;current=="+current);
    }

    @Override
    public void onStarted() {

    }

    @Override
    public void onWaiting() {

    }
}
