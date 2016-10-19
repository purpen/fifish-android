package com.qiyuan.fifish.ui.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.qiyuan.fifish.R;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * @author lilin
 *         created at 2016/6/27 16:35
 */
public abstract class BaseFragment<T> extends Fragment {
    protected View view;
    protected final String TAG = getClass().getSimpleName();
    protected Activity activity;
    private int layoutId;
    protected DisplayImageOptions options;

    public BaseFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        options = new DisplayImageOptions.Builder()

                .showImageOnLoading(R.mipmap.default_background_750_1334)
                .showImageForEmptyUri(R.mipmap.default_background_750_1334)
                .showImageOnFail(R.mipmap.default_background_750_1334)
                .imageScaleType(ImageScaleType.EXACTLY)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .delayBeforeLoading(200)
                .displayer(new FadeInBitmapDisplayer(200))
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        initParams();
        requestNet();
    }

    protected void setFragmentLayout(int layoutId) {
        if (layoutId == 0) {
            throw new IllegalArgumentException("please invoke set setFragmentLayout");
        }
        this.layoutId = layoutId;
    }

    protected void initParams() {

    }

    protected void requestNet() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(layoutId, null);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    public void onResume() {
        super.onResume();
//        MobclickAgent.onPageStart(TAG);
    }

    public void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd(TAG);
    }

    protected abstract void initViews();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        installListener();
    }

    protected void installListener() {

    }

    protected void refreshUI() {

    }

    protected void refreshUI(ArrayList<T> list) {

    }

    protected void refreshUI(ArrayList<T> list, ArrayList<T> list1, ArrayList<T> list2, ArrayList<T> list3) {

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
