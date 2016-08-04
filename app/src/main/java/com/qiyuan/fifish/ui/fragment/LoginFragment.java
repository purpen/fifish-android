package com.qiyuan.fifish.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qiyuan.fifish.R;
import com.qiyuan.fifish.ui.view.CustomHeadView;

import butterknife.Bind;

public class LoginFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.setFragmentLayout(R.layout.fragment_login);
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    protected void initViews() {
    }
}
