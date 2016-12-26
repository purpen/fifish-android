package com.qiyuan.fifish.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UmengTool;

import org.xutils.BuildConfig;
import org.xutils.x;

import cn.jpush.android.api.JPushInterface;

/**
 * @author lilin
 *         created at 2016/6/27 15:46
 */
public class AppApplication extends Application {
    public static boolean isMeter = true, isCentigrade = true;
    private static AppApplication application;
    public static Bitmap originalBitmap = null;//原图
    public static Bitmap editBitmap = null;//编辑好的图片
    public static AppApplication getInstance() {
        return application;

    }

    @Override
    public void onCreate() {
//        if (Constants.DEVELOPER_MODE && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
//            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyDialog().build());
//            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyDeath().build());
//        }
        super.onCreate();
        application = this;
        x.Ext.init(this);
        initImageLoader(getApplicationContext());
        PlatformConfig.setWeixin("wx5d74f772a28a33a4", "f3fdb3d58250d0444924076b168ba492");
        PlatformConfig.setSinaWeibo("3236371468", "79aa38f5e760446d9fe28c92976322ed");
        PlatformConfig.setQQZone("1105852250","DN1d2vLr9mmedHIc");
        UMShareAPI.get(this);
        Config.REDIRECT_URL = "http://sns.whalecloud.com/sina2/callback";
        Config.DEBUG=true;
        Config.isJumptoAppStore = true;
        Config.isUmengQQ=true;
        Config.isUmengSina=true;
        Config.isUmengWx=true;
        Config.dialogSwitch = false;
        x.Ext.setDebug(true);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024);
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.memoryCache(new WeakMemoryCache());
//        config.writeDebugLogs();
        ImageLoader.getInstance().init(config.build());
    }

    
}
