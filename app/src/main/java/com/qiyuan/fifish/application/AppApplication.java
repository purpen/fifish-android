package com.qiyuan.fifish.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.LogUtil;

/**
 * @author lilin
 *         created at 2016/6/27 15:46
 */
public class AppApplication extends Application {
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
        initImageLoader(getApplicationContext());
    }

    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024);
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs();
        ImageLoader.getInstance().init(config.build());
    }

    public void setCurrentActivity(Activity activity) {
        this.activity = activity;
    }

    public Activity getCurrentActivity() {
        if (activity != null) {
            return activity;
        } else {
            throw new IllegalStateException("please setCurrentActivity()");
        }
    }

}
