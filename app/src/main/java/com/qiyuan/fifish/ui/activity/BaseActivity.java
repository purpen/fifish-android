package com.qiyuan.fifish.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.application.AppApplication;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * @author lilin
 *         created at 2016/6/27 14:56
 */
public abstract class BaseActivity<T> extends AppCompatActivity {
    protected final String TAG = getClass().getSimpleName();
    private long startTime;
    protected Activity activity;
    protected AppApplication application;
    private int layoutResID;
    protected DisplayImageOptions options;

    public BaseActivity(int layoutResID) {
        this.layoutResID = layoutResID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.default_cover)
                .showImageForEmptyUri(R.mipmap.default_cover)
                .showImageOnFail(R.mipmap.default_cover)
                .imageScaleType(ImageScaleType.EXACTLY)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .delayBeforeLoading(50)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
//        ShareSDK.initSDK(this);
        application = AppApplication.getInstance();
        this.activity = this;
//		MobclickAgent.setDebugMode(true);
//        MobclickAgent.openActivityDurationTrack(false);
//        AnalyticsConfig.enableEncrypt(true);
        checkUserLogin();
        setContentView(layoutResID);
        ButterKnife.bind(this);
        getIntentData();
        initViews();
        installListener();
        initParams();
        requestNet();
//        LogUtil.e(tag,getDeviceInfo(activity));
    }

    protected void checkUserLogin() {
    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }


    /**
     * 获得界面跳转数据
     */
    protected void getIntentData() {
    }

    /**
     * 初始化view控件
     */
    protected abstract void initViews();

    /**
     * 注册监听器
     */
    protected void installListener() {
    }


    /**
     * 初始化请求参数
     */
    protected void initParams() {

    }

    /**
     * 请求网络数据
     */
    protected void requestNet() {

    }


    /**
     * 提交数据方法
     */
    protected void submitData() {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        MobclickAgent.onPageStart(tag);
//        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd(tag);
//        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
//        ShareSDK.stopSDK(this);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    protected void refreshUI() {

    }

    protected void refreshUI(ArrayList<T> list) {

    }

    protected void refreshUI(ArrayList<T> list, ArrayList<T> list1) {

    }
}
