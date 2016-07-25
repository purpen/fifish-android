package com.qiyuan.fifish.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.bean.LoginInfo;
import com.qiyuan.fifish.bean.NavTabItem;
import com.qiyuan.fifish.ui.fragment.MediaFragment;
import com.qiyuan.fifish.ui.fragment.HomeFragment;
import com.qiyuan.fifish.ui.fragment.MineFragment;
import com.qiyuan.fifish.ui.fragment.FindFragment;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.activity_main_container)
    RelativeLayout container;
    @Bind(R.id.ll_nav0)
    RadioButton ll_nav0;
    @Bind(R.id.ll_nav1)
    RadioButton ll_nav1;
    @Bind(R.id.ll_nav2)
    RadioButton ll_nav2;
    @Bind(R.id.ll_nav3)
    RadioButton ll_nav3;
    @Bind(R.id.ll_nav4)
    RadioButton ll_nav4;
    @Bind(R.id.main_content)
    FrameLayout mainContent;
    private FragmentManager fm;
    private ArrayList<NavTabItem> tabList;
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
        } else if (TextUtils.equals(FindFragment.class.getSimpleName(), which)) {
            switchFragmentandImg(FindFragment.class);
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

    private void recoverAllState(Bundle savedInstanceState) {
        Fragment indexFragment = fm.getFragment(savedInstanceState, HomeFragment.class.getSimpleName());
        addFragment2List(indexFragment);
        Fragment findFragment = fm.getFragment(savedInstanceState, MediaFragment.class.getSimpleName());
        addFragment2List(findFragment);
        Fragment wellGoodsFragment = fm.getFragment(savedInstanceState, FindFragment.class.getSimpleName());
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
    protected void installListener() {
        ll_nav0.setOnClickListener(this);
        ll_nav1.setOnClickListener(this);
        ll_nav2.setOnClickListener(this);
        ll_nav3.setOnClickListener(this);
        ll_nav4.setOnClickListener(this);
    }

    @Override
    protected void initViews() {
        if (tabList != null) {
            tabList.clear();
        } else {
            tabList = new ArrayList<>();
        }

        initTabItem(homepageImg, tv_nav0, HomeFragment.class, R.mipmap.home, R.mipmap.home_unsel);

        initTabItem(findImg, tv_nav1, MediaFragment.class, R.mipmap.media, R.mipmap.media_unsel);

        initTabItem(shopImg, tv_nav3, FindFragment.class, R.mipmap.discover, R.mipmap.discover_unsel);

        initTabItem(mineImg, tv_nav4, MineFragment.class, R.mipmap.mine, R.mipmap.mine_unsel);
    }


    private void initTabItem(ImageView imageView, TextView tv, Class clazz, int selId, int unselId) {
        NavTabItem tabItem = new NavTabItem();
        tabItem.imageView = imageView;
        tabItem.tv = tv;
        tabItem.clazz = clazz;
        tabItem.selId = selId;
        tabItem.unselId = unselId;
        tabList.add(tabItem);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_nav2:
                if (LoginInfo.isUserLogin()) {
//                    MainApplication.tag = 1;
//                    startActivity(new Intent(MainActivity.this, SelectPhotoOrCameraActivity.class));
                } else {
//                    MainApplication.which_activity = 0;
//                    startActivity(new Intent(activity, OptRegisterLoginActivity.class));
                }
                break;
            case R.id.ll_nav0://情
                switchFragmentandImg(HomeFragment.class);
                break;
            case R.id.ll_nav1: //景
                switchFragmentandImg(MediaFragment.class);
                break;
            case R.id.ll_nav3:  //品
                switchFragmentandImg(FindFragment.class);
                break;
            case R.id.ll_nav4: //个人中心
                if (getVisibleFragment() instanceof MineFragment) return;
                if (LoginInfo.isUserLogin()) {
                    switchFragmentandImg(MineFragment.class);
                } else {
                    which = MineFragment.class.getSimpleName();
//                    startActivity(new Intent(activity, OptRegisterLoginActivity.class));
                }
                break;
        }
    }

    private void switchFragmentandImg(Class clazz) {
        resetUI();
        switchImgAndTxtStyle(clazz);
        try {
            switchFragment(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 切换fragment
     *
     * @param clazz
     * @throws Exception
     */
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

    private void switchImgAndTxtStyle(Class clazz) {
        for (NavTabItem tabItem : tabList) {
            if (tabItem.clazz.equals(clazz)) {
                tabItem.imageView.setImageResource(tabItem.selId);
                tabItem.tv.setTextColor(getResources().getColor(R.color.color_0c151c));
            } else {
                tabItem.imageView.setImageResource(tabItem.unselId);
                tabItem.tv.setTextColor(getResources().getColor(R.color.color_7f8fa2));
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        int size = fragments.size();
        if (fragments != null && size > 0) {
            for (int i = 0; i < size; i++) {
                fm.putFragment(outState, fragments.get(i).getTag(), fragments.get(i));
            }
        }
        if (showFragment != null) {
            outState.putSerializable(MainActivity.class.getSimpleName(), showFragment.getClass());
        }
        super.onSaveInstanceState(outState);
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
        super.onDestroy();
    }

}
