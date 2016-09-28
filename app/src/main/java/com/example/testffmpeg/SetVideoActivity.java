package com.example.testffmpeg;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.util.ActivityUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_set_video)
public class SetVideoActivity extends BaseStyleActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    @ViewInject(R.id.tv_image_set)
    private TextView mImageSet;
    @ViewInject(R.id.tv_common_set)
    private TextView mCommonSet;
    @ViewInject(R.id.tv_encode_set)
    private TextView mEncodeSet;
    @ViewInject(R.id.viewpaer_video_set)
    private ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_set_video);
        initView();
    }

    private void initView() {
        ActivityUtil.getInstance().addActivity(this);
        mCommonSet.setOnClickListener(this);
        mImageSet.setOnClickListener(this);
        mEncodeSet.setOnClickListener(this);
        List<Fragment> mFragments = new ArrayList<>();
        mFragments.add(new CommonFragment());
        mFragments.add(new SetImageFragment());
        mFragments.add(new EncodeFragment());
        VideoViewpagerAdapter mAdapter = new VideoViewpagerAdapter(getSupportFragmentManager(), mFragments, this);
        mPager.setAdapter(mAdapter);
        mPager.addOnPageChangeListener(this);
        mPager.setCurrentItem(0);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_common_set:
                mPager.setCurrentItem(0);
                break;
            case R.id.tv_image_set:
                mPager.setCurrentItem(1);
                break;
            case R.id.tv_encode_set:
                mPager.setCurrentItem(2);
                break;

        }
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}