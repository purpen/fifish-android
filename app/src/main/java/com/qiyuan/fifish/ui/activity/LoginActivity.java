package com.qiyuan.fifish.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qiyuan.fifish.R;
import com.qiyuan.fifish.util.Util;

import butterknife.Bind;

/**
 * @author lilin
 *         created at 2016/7/26 13:10
 */
public class LoginActivity extends BaseActivity {

    @Bind(R.id.ll_login)
    LinearLayout llLogin;
    @Bind(R.id.horizontalScrollView)
    HorizontalScrollView horizontalScrollView;
    @Bind(R.id.et_code)
    EditText etCode;
    @Bind(R.id.btn_code)
    Button btnCode;
    @Bind(R.id.btn_register)
    Button btnRegister;
    @Bind(R.id.ll_reg)
    LinearLayout llReg;
    @Bind(R.id.tv_register)
    TextView tvRegister;

    public LoginActivity() {
        super(R.layout.activity_login_register);
    }

    @Override
    protected void initViews() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.width=Util.getScreenWidth();
        llReg.setLayoutParams(params);
        llLogin.setLayoutParams(params);
    }

    @Override
    protected void installListener() {
//        horizontalScrollView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                return true;
//            }
//        });
    }
}
