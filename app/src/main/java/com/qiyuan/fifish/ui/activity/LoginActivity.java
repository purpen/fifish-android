package com.qiyuan.fifish.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.qiyuan.fifish.R;
import com.qiyuan.fifish.adapter.LoginRegsiterViewPagerAdapter;
import com.qiyuan.fifish.ui.fragment.LoginFragment;
import com.qiyuan.fifish.ui.fragment.MineFragment;
import com.qiyuan.fifish.ui.fragment.RegisterFragment;
import com.qiyuan.fifish.ui.view.WrapContentHeightViewPager;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author lilin
 *         created at 2016/7/26 13:10
 */
public class LoginActivity extends BaseActivity {
    @BindView(R.id.viewPager)
    WrapContentHeightViewPager viewPager;
    @BindView(R.id.btn_switch)
    Button btnSwitch;
    private String page;
    public LoginActivity() {
        super(R.layout.activity_login_register);
    }

    @Override
    protected void getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra(MineFragment.class.getSimpleName())){
            page = MineFragment.class.getSimpleName();
        }
    }

    @Override
    protected void initViews() {
        btnSwitch.setText(R.string.no_account);
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

    public WrapContentHeightViewPager getViewPager(){
        return viewPager==null?null:viewPager;
    }

    @OnClick({R.id.btn_switch,R.id.ibtn_close})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_switch:
                switch (viewPager.getCurrentItem()){
                    case 0:
                        viewPager.setCurrentItem(1);
                        btnSwitch.setText(R.string.has_account);
                        break;
                    case 1:
                        viewPager.setCurrentItem(0);
                        btnSwitch.setText(R.string.no_account);
                        break;
                }
                break;
            case R.id.ibtn_close:
                finish();
                break;
        }
    }


}
