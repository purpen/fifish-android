package com.qiyuan.fifish.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cnjabsco.android.jni.DecoderJni;
import com.example.testffmpeg.VideoPlayActivity;

import com.qiyuan.fifish.R;
import com.qiyuan.fifish.ui.view.CustomHeadView;
import com.tcp.RulerActivity;
import com.tcp.TcpSecActivity;

import butterknife.Bind;

public class DeviceFragment2 extends BaseFragment {
    DecoderJni mDecoderJni;
    @Bind(R.id.custom_head)
    CustomHeadView customHead;
    @Bind(R.id.bt_to_device)
    Button mToDevice;
    @Bind(R.id.bt_to_device2)
    Button mToDevice2;
    @Bind(R.id.bt_to_device_test_ruler)
    Button mToDeviceTestRuler;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.setFragmentLayout(R.layout.fragment_device2);
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    public static DeviceFragment2 newInstance() {
        DeviceFragment2 fragment = new DeviceFragment2();
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
        mToDevice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), TcpSecActivity.class);
                startActivity(intent);
                Log.e(">>", ">>>日志开始了2222");
            }
        });
        mToDeviceTestRuler.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), RulerActivity.class);
                startActivity(intent);
                Log.e(">>", ">>>日志开始了〈〉〈〉");
            }
        });
    }
}
