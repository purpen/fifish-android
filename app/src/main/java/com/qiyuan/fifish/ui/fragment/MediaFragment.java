package com.qiyuan.fifish.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qiyuan.fifish.R;
import com.qiyuan.fifish.adapter.MediaPagerAdapter;
import com.qiyuan.fifish.ui.activity.MainActivity;
import com.qiyuan.fifish.ui.view.CustomHeadView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class MediaFragment extends BaseFragment implements TabLayout.OnTabSelectedListener,MediaInnerFragment.OnPhotoCountListener {
    private TabLayout.Tab mLocalTab, mDeviceTab;
    @Bind(R.id.tab_media)
    TabLayout tabBar;
    @Bind(R.id.tv_choice_media)
    TextView mTvChoice;
    @Bind(R.id.image_import)
    ImageView mImageImport;
    @Bind(R.id.pager_media)
    ViewPager mPager;
    @Bind(R.id.layout_title)
    RelativeLayout mLayoutTitle;//白色背景标题栏
    @Bind(R.id.layout_blue)
    RelativeLayout mLayoutTop;//黑色背景标题栏
    @Bind(R.id.tv_show_pic_count_media_fragment)
    TextView mTvCount;
    @Bind(R.id.tv_cancel_media_fragment)
    TextView mTvCancel;

    @Override
    public void onResume() {
        super.onResume();
        mTvCount.setText(getActivity().getResources().getString(R.string.please_choose));
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.setFragmentLayout(R.layout.fragment_media);
        super.onCreateView(inflater, container, savedInstanceState);
        initView();
        return view;
    }

    private void initView() {
        //隐藏顶部动画降落的黑色背景标题栏、其栏高48dp，故此处取－48dp
        mLayoutTop.setTranslationY(-getResources().getDimension(R.dimen.dp48));
        mLocalTab = tabBar.newTab().setText(R.string.local);
        mDeviceTab = tabBar.newTab().setText(R.string.device);
        boolean mLocalBool = false, mDeviceBool = false;
        tabBar.addTab(mLocalTab, mLocalBool);
        tabBar.addTab(mDeviceTab, mDeviceBool);
        tabBar.setOnTabSelectedListener(this);
        mLocalTab.select();//初始化选中“本地”

        List<Fragment> mFragments = new ArrayList<>();
        for (int i = 0; i < tabBar.getTabCount(); i++) {
            mFragments.add(MediaInnerFragment.getInstance(i, MediaFragment.this));
        }
        MediaPagerAdapter mAdapter = new MediaPagerAdapter(getChildFragmentManager(), mFragments);
        mPager.setAdapter(mAdapter);
        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabBar));
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        //tabLayout滑动的时候，选中当前pager
        int position = tab.getPosition();
        mPager.setCurrentItem(position);
        //设备碎片时不显示左上角的导入图标,本地碎片时显示
        if (position == 1) {
            mImageImport.setVisibility(View.INVISIBLE);
        } else if (position == 0) {
            mImageImport.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    @OnClick({R.id.image_import, R.id.tv_choice_media, R.id.tv_cancel_media_fragment})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_import:
                //为了不重复显示dialog，在显示对话框之前移除正在显示的对话框。
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment fragment = getFragmentManager().findFragmentByTag(ImportDialogFragment.class.getSimpleName());
                if (null != fragment) {
                    ft.remove(fragment);
                }
                ImportDialogFragment newFragment = ImportDialogFragment.newInstance();
                newFragment.show(getFragmentManager(), ImportDialogFragment.class.getSimpleName());
                break;
            case R.id.tv_choice_media:
                Intent intent = new Intent();
                intent.setAction("com.qiyuan.fifish.ui.fragment.mediafragment");
                intent.putExtra("choosed", true);
                getActivity().sendBroadcast(intent);
                break;
            case R.id.tv_cancel_media_fragment:
                Intent in = new Intent();
                in.setAction("com.qiyuan.fifish.ui.fragment.mediafragment");
                in.putExtra("choosed", false);
                getActivity().sendBroadcast(in);
                break;
        }
    }
    //动画隐藏白色背景的标题栏、同时显示黑色背景的标题栏
    public void hideAnim() {
        // 动画显示出黑色背景的标题栏
        ValueAnimator mLayoutTopAnim = ObjectAnimator.ofFloat(mLayoutTop, "translationY", 0.0f)
                .setDuration(300);
        // 动画隐藏掉白色背景的标题栏
        ValueAnimator mLayoutTitleAnim = ObjectAnimator.ofFloat(mLayoutTitle, "translationY", -mLayoutTitle.getMeasuredHeight())
                .setDuration(300);
//        mLayoutTopAnim.start();
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(mLayoutTitleAnim).before(mLayoutTopAnim);
        animatorSet.start();
    }
    //动画显示白色背景的标题栏、同时隐藏黑色背景的标题栏
    public void showAnim() {
        //动画隐藏黑色背景的标题栏
        ValueAnimator mLayoutTopAnim = ObjectAnimator.ofFloat(mLayoutTop, "translationY", -mLayoutTop.getMeasuredHeight())
                .setDuration(300);
        //        mLayoutTopAnim.start();
        //动画显示白色背景的标题栏
        ValueAnimator mLayoutTitleAnim = ObjectAnimator.ofFloat(mLayoutTitle, "translationY", 0.0f)
                .setDuration(300);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(mLayoutTopAnim).before(mLayoutTitleAnim);
        animatorSet.start();
    }

    @Override
    public void setPhotoCount(int count) {
        if (count <= 0) {
            mTvCount.setText(getActivity().getResources().getString(R.string.please_choose));
            return;
        }
        mTvCount.setText("选中"+count+"张");
    }

    @Override
    protected void initViews() {

    }
}
