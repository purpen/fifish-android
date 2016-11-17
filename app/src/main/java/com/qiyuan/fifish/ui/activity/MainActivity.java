package com.qiyuan.fifish.ui.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.qiyuan.fifish.R;
import com.qiyuan.fifish.bean.UserProfile;
import com.qiyuan.fifish.ui.fragment.DeviceFragment;
import com.qiyuan.fifish.ui.fragment.DiscoverFragment;
import com.qiyuan.fifish.ui.fragment.HomeFragment;
import com.qiyuan.fifish.ui.fragment.MediaFragment;
import com.qiyuan.fifish.ui.fragment.MineFragment;

import org.xutils.common.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * @author lilin
 *         created at 2016/7/26 13:12
 */
public class MainActivity extends BaseActivity {
    @BindView(R.id.main_content)
    FrameLayout mContentLayout;
    @BindView(R.id.main_nav)
    RadioGroup mainNav;
//    private int checkedId = R.id.ll_nav0;
    private FragmentManager fm;
    private ArrayList<Fragment> fragments;
    private Fragment showFragment;
    private String which = HomeFragment.class.getSimpleName();

    public MainActivity() {
        super(R.layout.activity_main);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (intent.hasExtra(HomeFragment.class.getSimpleName())) {
            which = HomeFragment.class.getSimpleName();
        }
        which2Switch();
        super.onNewIntent(intent);
    }

    private void which2Switch() {
        if (TextUtils.equals(HomeFragment.class.getSimpleName(), which)) {
            switchFragmentandImg(HomeFragment.class);
            mainNav.check(R.id.ll_nav0);
        } else if(TextUtils.equals(MediaFragment.class.getSimpleName(), which)){
            switchFragmentandImg(MediaFragment.class);
            mainNav.check(R.id.ll_nav1);
        }else if (TextUtils.equals(DeviceFragment.class.getSimpleName(), which)) {
            switchFragmentandImg(DeviceFragment.class);
            mainNav.check(R.id.ll_nav2);
        } else if (TextUtils.equals(DiscoverFragment.class.getSimpleName(), which)) {
            switchFragmentandImg(DiscoverFragment.class);
            mainNav.check(R.id.ll_nav3);
        }else {
            switchFragmentandImg(MineFragment.class);
            mainNav.check(R.id.ll_nav4);
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
                        LogUtil.e((R.id.ll_nav0==i)+"");
                        which = HomeFragment.class.getSimpleName();
//                        radioGroup.check(i);
                        switchFragmentandImg(HomeFragment.class);
//                        startActivity(new Intent(activity,UserCenterActivity.class));
                        break;
                    case R.id.ll_nav1:
//                        radioGroup.check(i);
                        which = MediaFragment.class.getSimpleName();
                        switchFragmentandImg(MediaFragment.class);
//                        switchFragmentandImg(DeviceFragment2.class);
                        break;
                    case R.id.ll_nav2:
                        which = DeviceFragment.class.getSimpleName();
//                        radioGroup.check(i);
                        switchFragmentandImg(DeviceFragment.class);
                        break;
                    case R.id.ll_nav3:
                        which = DiscoverFragment.class.getSimpleName();
//                        radioGroup.check(i);
                        switchFragmentandImg(DiscoverFragment.class);
                        break;
                    case R.id.ll_nav4:
                        if (UserProfile.isUserLogin()) {
                            which = MineFragment.class.getSimpleName();
//                            radioGroup.check(i);
                            switchFragmentandImg(MineFragment.class);
                        } else {
//                            radioGroup.check(checkedId);
//                            which = MineFragment.class.getSimpleName();
                            Intent intent = new Intent(activity, LoginActivity.class);
                            intent.putExtra(MineFragment.class.getSimpleName(),MineFragment.class.getSimpleName());
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
        fragments = null;
        super.onDestroy();
    }

    //隐藏底部的radioGroup
    public void hideAnim() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            WindowUtils.hide(this);
//        }
        ObjectAnimator.ofFloat(mainNav, "translationY", mainNav.getMeasuredHeight())
                .setDuration(300)
                .start();
    }

    //显示底部的radioGroup
    public void showAnim() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            WindowUtils.show(this);
//        }
        ObjectAnimator.ofFloat(mainNav, "translationY", 0)
                .setDuration(300)
                .start();
    }
}
