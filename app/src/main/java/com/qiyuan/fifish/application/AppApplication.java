package com.qiyuan.fifish.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import org.xutils.x;

/**
 * @author lilin
 *         created at 2016/6/27 15:46
 */
public class AppApplication extends Application {
    public static boolean isMeter = true, isCentigrade = true;
    private Activity activity;
    private static AppApplication application;

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
    }

    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024);
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
//        config.writeDebugLogs();
        ImageLoader.getInstance().init(config.build());
    }

    
}
