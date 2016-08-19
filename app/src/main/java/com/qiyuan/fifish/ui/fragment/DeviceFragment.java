package com.qiyuan.fifish.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cnjabsco.android.jni.DecoderJni;
import com.example.testffmpeg.VideoPlayActivity;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.ui.view.CustomHeadView;

import butterknife.Bind;

public class DeviceFragment extends BaseFragment {
    DecoderJni mDecoderJni;
    @Bind(R.id.custom_head)
    CustomHeadView customHead;
    @Bind(R.id.bt_to_device)
    Button mToDevice;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.setFragmentLayout(R.layout.fragment_device);
        super.onCreateView(inflater, container, savedInstanceState);
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
//        mDecoderJni=new DecoderJni();
        mToDevice.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), VideoPlayActivity.class);
                intent.putExtra(VideoPlayActivity.AUTO_START, true);
                startActivity(intent);
                Log.e(">>", ">>>日志开始了〈〉〈〉");
            }
        });
    }
}
