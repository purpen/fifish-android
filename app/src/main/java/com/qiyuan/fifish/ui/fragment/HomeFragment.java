package com.qiyuan.fifish.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.ui.view.CustomHeadView;

import butterknife.Bind;

public class HomeFragment extends BaseFragment{
    @Bind(R.id.custom_head)
    CustomHeadView customHead;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.setFragmentLayout(R.layout.fragment_home);
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    protected void initViews() {
        customHead.setHeadCenterTxtShow(true, R.string.home);
        customHead.setHeadGoBackShow(false);
    }

}
