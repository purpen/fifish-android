package com.qiyuan.fifish.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.qiyuan.fifish.R;
import com.qiyuan.fifish.adapter.LinkHelpAdapter;
import com.qiyuan.fifish.ui.view.CustomViewPager;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author lilin
 *         created at 2016/8/18 17:26
 */
public class LinkHelpFragment extends DialogFragment {
    @Bind(R.id.viewPager)
    CustomViewPager viewPager;
    @Bind(R.id.ll_dots)
    LinearLayout ll_dots;
    private ArrayList<ImageView> imageViews;
    private static final int SIZE = 4;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme);
    }

    public static LinkHelpFragment newInstance() {
        LinkHelpFragment f = new LinkHelpFragment();
        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_link_help, container, false);
        ButterKnife.bind(this, v);
        String[] help_step_arr = getResources().getStringArray(R.array.help_step_arr);
        String[] link_help_arr = getResources().getStringArray(R.array.link_help_arr);
        viewPager.setAdapter(new LinkHelpAdapter(help_step_arr, link_help_arr,LinkHelpFragment.this,viewPager));
        showIndicators();
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        installListeners();
    }

    private void installListeners() {
        viewPager.addOnPageChangeListener(new CustomOnPageChangeListener());
    }

    public void showIndicators() {
        imageViews = new ArrayList<>();
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        llp.setMargins(getActivity().getResources().getDimensionPixelSize(R.dimen.dp5), 0, 0, getActivity().getResources().getDimensionPixelSize(R.dimen.dp15));
        ViewGroup.LayoutParams vlp = new ViewGroup.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ImageView imageView;
        for (int i = 0; i < SIZE; i++) {
            imageView = new ImageView(getActivity());
            imageView.setLayoutParams(vlp);
            if (i == 0) {
                imageView.setImageResource(R.drawable.shape_oval_sel);
            } else {
                imageView.setImageResource(R.drawable.shape_oval_unsel);
            }
            imageViews.add(imageView);
            ll_dots.addView(imageView, llp);
        }
    }

    @OnClick(R.id.ibtn_close)
    public void onClick() {
        dismiss();
    }

    private class CustomOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            setCurFocus(position);
        }

        private void setCurFocus(int position) {
            for (int i = 0; i < SIZE; i++) {
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
