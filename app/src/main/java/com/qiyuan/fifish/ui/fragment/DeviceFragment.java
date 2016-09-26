package com.qiyuan.fifish.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qiyuan.fifish.R;
import com.qiyuan.fifish.adapter.ViewPagerAdapter;
import com.qiyuan.fifish.ui.view.CustomHeadView;
import com.qiyuan.fifish.ui.view.ScrollableView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeviceFragment extends BaseFragment {
    @Bind(R.id.custom_head)
    CustomHeadView customHead;
    @Bind(R.id.tv_wifi)
    TextView tvWifi;
    @Bind(R.id.view_line)
    View viewLine;
    @Bind(R.id.tv_device_name)
    TextView tvDeviceName;
    @Bind(R.id.scrollableView)
    ScrollableView scrollableView;
    @Bind(R.id.btn_link)
    Button btnLink;
    @Bind(R.id.btn_help)
    Button btnHelp;
    @Bind(R.id.ll_dots)
    LinearLayout ll_dots;
    private ArrayList<ImageView> imageViews = new ArrayList<>();
    private ArrayList<Integer> list;
    private int currentItem;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.setFragmentLayout(R.layout.fragment_device);
        super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, view);
        return view;
    }

    public static DeviceFragment newInstance() {
        DeviceFragment fragment = new DeviceFragment();
        return fragment;
    }

    @Override
    protected void initViews() {
        customHead.setHeadCenterTxtShow(true, R.string.device);
        customHead.setHeadGoBackShow(false);
        list = new ArrayList<>();
        list.add(R.mipmap.robot);
        list.add(R.mipmap.guide1);
        list.add(R.mipmap.guide2);
        list.add(R.mipmap.guide3);
        scrollableView.setAdapter(new ViewPagerAdapter<>(activity, list));
        showIndicators();
    }

    public void showIndicators() {
        imageViews = new ArrayList<>();
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        llp.setMargins(activity.getResources().getDimensionPixelSize(R.dimen.dp5), 0, 0, 0);
        ViewGroup.LayoutParams vlp = new ViewGroup.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ImageView imageView;
        for (int i = 0; i < list.size(); i++) {
            imageView = new ImageView(activity);
            imageView.setLayoutParams(vlp);
            if (i == currentItem) {
                imageView.setImageResource(R.drawable.shape_oval_sel);
            } else {
                imageView.setImageResource(R.drawable.shape_oval_unsel);
            }
            imageViews.add(imageView);
            ll_dots.addView(imageView, llp);
        }
    }

    @Override
    protected void installListener() {
        scrollableView.addOnPageChangeListener(new CustomOnPageChangeListener());
    }

    @OnClick(R.id.btn_help)
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_help:
                LinkHelpFragment newFragment = LinkHelpFragment.newInstance();
                newFragment.show(getFragmentManager(), LinkHelpFragment.class.getSimpleName());
                break;
        }
    }

    private class CustomOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            currentItem = position;
            setCurFocus(position);
        }

        private void setCurFocus(int position) {
            for (int i = 0; i < list.size(); i++) {
                if (i == position) {
                    imageViews.get(i).setImageResource(R.drawable.shape_oval_sel);
                } else {
                    imageViews.get(i).setImageResource(R.drawable.shape_oval_unsel);
                }
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }
}
