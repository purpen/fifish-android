package com.qiyuan.fifish.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.widget.RadioGroup;

import com.qiyuan.fifish.R;
import com.qiyuan.fifish.bean.UserProfile;
import com.qiyuan.fifish.ui.fragment.DeviceFragment;
import com.qiyuan.fifish.ui.fragment.DiscoverFragment;
import com.qiyuan.fifish.ui.fragment.HomeFragment;
import com.qiyuan.fifish.ui.fragment.MediaFragment;
import com.qiyuan.fifish.ui.fragment.MineFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * @author lilin
 *         created at 2016/7/26 13:12
 */
public class MainActivity extends BaseActivity {
    @Bind(R.id.main_nav)
    RadioGroup mainNav;
    private int checkedId = R.id.ll_nav0;
    private FragmentManager fm;
    private ArrayList<Fragment> fragments;
    private Fragment showFragment;
    private String which = HomeFragment.class.getSimpleName();

    public MainActivity() {
        super(R.layout.activity_main);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        which2Switch();
        super.onNewIntent(intent);
    }

    private void which2Switch() {
        if (TextUtils.equals(HomeFragment.class.getSimpleName(), which)) {
            switchFragmentandImg(HomeFragment.class);
        } else if (TextUtils.equals(MineFragment.class.getSimpleName(), which)) {
            switchFragmentandImg(MineFragment.class);
        } else if (TextUtils.equals(DiscoverFragment.class.getSimpleName(), which)) {
            switchFragmentandImg(DiscoverFragment.class);
        }
    }

    @Override
    protected void getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra(HomeFragment.class.getSimpleName())) {
            which = HomeFragment.class.getSimpleName();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (fragments == null) {
            fragments = new ArrayList<>();
        }
        fm = getSupportFragmentManager();
        if (savedInstanceState != null) {
            recoverAllState(savedInstanceState);
        } else {
            which2Switch();
        }
    }

    @Override
    protected void installListener() {
        mainNav.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.ll_nav0:
                        checkedId = i;
                        radioGroup.check(i);
                        switchFragmentandImg(HomeFragment.class);
//                        startActivity(new Intent(activity,UserCenterActivity.class));
                        break;
                    case R.id.ll_nav1:
                        checkedId = i;
                        radioGroup.check(i);
                        switchFragmentandImg(MediaFragment.class);
                        break;
                    case R.id.ll_nav2:
                        checkedId = i;
                        radioGroup.check(i);
                        switchFragmentandImg(DeviceFragment.class);
                        break;
                    case R.id.ll_nav3:
                        checkedId = i;
                        radioGroup.check(i);
                        switchFragmentandImg(DiscoverFragment.class);
                        break;
                    case R.id.ll_nav4:
                        if (UserProfile.isUserLogin()) {
                            checkedId = i;
                            radioGroup.check(i);
                            switchFragmentandImg(MineFragment.class);
                        } else {
                            radioGroup.check(checkedId);
                            which = MineFragment.class.getSimpleName();
                            startActivity(new Intent(activity, LoginActivity.class));
                        }
                        break;
                }
            }
        });
    }

    private void recoverAllState(Bundle savedInstanceState) {
        Fragment indexFragment = fm.getFragment(savedInstanceState, HomeFragment.class.getSimpleName());
        addFragment2List(indexFragment);
        Fragment findFragment = fm.getFragment(savedInstanceState, MediaFragment.class.getSimpleName());
        addFragment2List(findFragment);
        Fragment wellGoodsFragment = fm.getFragment(savedInstanceState, DiscoverFragment.class.getSimpleName());
        addFragment2List(wellGoodsFragment);
        Fragment mineFragment = fm.getFragment(savedInstanceState, MineFragment.class.getSimpleName());
        addFragment2List(mineFragment);
        Class clazz = (Class) savedInstanceState.getSerializable(MainActivity.class.getSimpleName());
        if (clazz == null) return;
        switchFragmentandImg(clazz);
    }

    private void addFragment2List(Fragment fragment) {
        if (fragment == null) {
            return;
        }

        if (fragments.contains(fragment)) {
            return;
        }

        fragments.add(fragment);
    }


    @Override
    protected void initViews() {

    }

    private void switchFragmentandImg(Class clazz) {
        resetUI();
        try {
            switchFragment(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void switchFragment(Class clazz) throws Exception {
        Fragment fragment = fm.findFragmentByTag(clazz.getSimpleName());
        if (fragment == null) {
            fragment = (Fragment) clazz.newInstance();
            fm.beginTransaction().add(R.id.main_content, fragment, clazz.getSimpleName()).commitAllowingStateLoss();
        } else {
            fm.beginTransaction().show(fragment).commitAllowingStateLoss();
        }
        addFragment2List(fragment);
        showFragment = fragment;
    }


    private void resetUI() {
        if (fragments == null) {
            return;
        }
        if (fragments.size() == 0) {
            return;
        }

        for (Fragment fragment : fragments) {
            fm.beginTransaction().hide(fragment).commitAllowingStateLoss();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (fragments != null) {
            int size = fragments.size();
            for (int i = 0; i < size; i++) {
                fm.putFragment(outState, fragments.get(i).getTag(), fragments.get(i));
            }
        }
        if (showFragment != null) {
            outState.putSerializable(MainActivity.class.getSimpleName(), showFragment.getClass());
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (fragments != null) {
            int size = fragments.size();
            for (int i = 0; i < size; i++) {
                fm.putFragment(savedInstanceState, fragments.get(i).getTag(), fragments.get(i));
            }
        }
        if (showFragment != null) {
            savedInstanceState.putSerializable(MainActivity.class.getSimpleName(), showFragment.getClass());
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    public Fragment getVisibleFragment() {
        List<Fragment> fragments = fm.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible())
                return fragment;
        }
        return null;
    }

    @Override
    protected void onDestroy() {
        fragments=null;
        super.onDestroy();
    }

}
