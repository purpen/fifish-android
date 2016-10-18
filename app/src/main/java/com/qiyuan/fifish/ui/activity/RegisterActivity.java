package com.qiyuan.fifish.ui.activity;

import android.os.Build;
import android.view.WindowManager;

import com.qiyuan.fifish.R;

/**
 * @author lilin
 * created at 2016/7/26 13:44
 */
public class RegisterActivity extends BaseActivity{
    public RegisterActivity(){
        super(R.layout.activity_login_register);
    }

    @Override
    protected void initViews() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

}
