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
        x.Ext.setDebug(true);
        initImageLoader(getApplicationContext());
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad");
        UMShareAPI.get(this);
        Config.REDIRECT_URL = "您新浪后台的回调地址";
        Config.DEBUG=true;
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
