package com.qiyuan.fifish.ui.activity;

import android.animation.ObjectAnimator;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.qiyuan.fifish.R;
import com.qiyuan.fifish.adapter.LoginRegsiterViewPagerAdapter;
import com.qiyuan.fifish.ui.fragment.LoginFragment;
import com.qiyuan.fifish.ui.fragment.RegisterFragment;
import com.qiyuan.fifish.ui.view.WrapContentHeightViewPager;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author lilin
 *         created at 2016/7/26 13:10
 */
public class LoginActivity extends BaseActivity {
    @Bind(R.id.viewPager)
    WrapContentHeightViewPager viewPager;
    @Bind(R.id.tv_register)
    TextView tvRegister;

    public LoginActivity() {
        super(R.layout.activity_login_register);
    }

    @Override
    protected void initViews() {
        tvRegister.setText(R.string.no_account);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        Fragment[] fragments={LoginFragment.newInstance(),RegisterFragment.newInstance()};
        LoginRegsiterViewPagerAdapter adapter = new LoginRegsiterViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.setPagingEnabled(false);
    }

    @Override
    protected void installListener() {

    }

    @OnClick({R.id.tv_register,R.id.ibtn_close})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_register:
                switch (viewPager.getCurrentItem()){
                    case 0:
                        viewPager.setCurrentItem(1);
                        tvRegister.setText(R.string.has_account);
                        break;
                    case 1:
                        viewPager.setCurrentItem(0);
                        tvRegister.setText(R.string.no_account);
                        break;
                }
                break;
            case R.id.ibtn_close:
                finish();
                break;

        }
    }


}
