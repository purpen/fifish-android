package com.qiyuan.fifish.application;

import android.app.Activity;
import android.app.Application;

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
        super.onCreate();
        application = this;
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

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
