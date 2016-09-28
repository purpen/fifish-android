package com.qiyuan.fifish.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import butterknife.BindView;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.adapter.TabLayoutViewPagerAdapter;
import com.qiyuan.fifish.ui.fragment.SupportPhotoFragment;
import com.qiyuan.fifish.ui.fragment.SupportVideoFragment;
import com.qiyuan.fifish.ui.view.CustomHeadView;

import java.lang.reflect.Field;

/**
 * 赞过
 * @author lilin
 *         created at 2016/9/20 17:27
 */
public class SupportProductsActivity extends BaseActivity {
    @BindView(R.id.custom_head)
    CustomHeadView custom_head;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private TabLayoutViewPagerAdapter adapter;

    public SupportProductsActivity() {
        super(R.layout.activity_support_products);
    }

    @Override
    protected void getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra(getClass().getSimpleName())) {

        }
    }

    @Override
    protected void initViews() {
        custom_head.setHeadCenterTxtShow(true,"赞过");
        String[] array = getResources().getStringArray(R.array.support_products);
        for (int i = 0; i < array.length; i++) {
            if (i == 0) {
                tabLayout.addTab(tabLayout.newTab().setText(array[0]), true);
            } else {
                tabLayout.addTab(tabLayout.newTab().setText(array[i]), false);
            }
        }

        Fragment[] fragments = {SupportPhotoFragment.newInstance(),SupportVideoFragment.newInstance()};
        adapter = new TabLayoutViewPagerAdapter(getSupportFragmentManager(), fragments, array);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(fragments.length);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setIndicatorWidth() throws NoSuchFieldException, IllegalAccessException {
        int margin = activity.getResources().getDimensionPixelSize(R.dimen.dp80);
        Class<?> tablayout = tabLayout.getClass();
        Field tabStrip = tablayout.getDeclaredField("mTabStrip");
        tabStrip.setAccessible(true);
        LinearLayout ll_tab = (LinearLayout) tabStrip.get(tabLayout);
        for (int i = 0; i < ll_tab.getChildCount(); i++) {
            View child = ll_tab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                params.setMarginStart(margin);
                params.setMarginEnd(margin);
            } else {
                params.setMargins(margin, 0, margin, 0);
            }
            child.setLayoutParams(params);
            child.invalidate();
        }
    }


    @Override
    protected void installListener() {
        tabLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                try {
                    setIndicatorWidth();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    tabLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    tabLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void requestNet() {

    }
}
