package com.qiyuan.fifish.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qiyuan.fifish.R;

/**
 * @author lilin
 * created at 2016/8/8 11:42
 */
public class FansFragment extends BaseFragment{
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.setFragmentLayout(R.layout.fragment_fans);
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    public static FocusFragment newInstance(){
        return new FocusFragment();
    }

    @Override
    protected void initViews() {

    }
}
