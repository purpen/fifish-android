package com.qiyuan.fifish.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.ui.view.CustomHeadView;

public class MediaFragment extends BaseFragment{
    @BindView(R.id.custom_head)
    CustomHeadView customHead;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.setFragmentLayout(R.layout.fragment_media);
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    protected void initViews() {
        customHead.setHeadCenterTxtShow(true, R.string.media);
        customHead.setHeadGoBackShow(false);
    }
}
