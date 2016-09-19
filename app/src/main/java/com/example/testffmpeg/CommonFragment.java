package com.example.testffmpeg;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.qiyuan.fifish.R;
import com.qiyuan.fifish.application.AppApplication;
import com.qiyuan.fifish.ui.fragment.BaseFragment;
import com.qiyuan.fifish.ui.view.CustomHeadView;

import org.xutils.view.annotation.ContentView;

/**
 * Created by android on 2016/9/9.
 */

public class CommonFragment extends BaseStyleFragment implements View.OnClickListener {
    private View view;
    private ImageView mImageClose;
    private RelativeLayout mLayoutFoot, mLayoutMeter, mLayoutCentigrade, mLayoutFahrenheit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_common, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mImageClose = (ImageView) view.findViewById(R.id.image_close);
        mLayoutFoot = (RelativeLayout) view.findViewById(R.id.layout_foot);
        mLayoutMeter = (RelativeLayout) view.findViewById(R.id.layout_meter);
        mLayoutCentigrade = (RelativeLayout) view.findViewById(R.id.layout_degree_centigrade);
        mLayoutFahrenheit = (RelativeLayout) view.findViewById(R.id.layout_degree_fahrenheit);
        mLayoutFahrenheit.setOnClickListener(this);
        mLayoutCentigrade.setOnClickListener(this);
        mLayoutMeter.setOnClickListener(this);
        mLayoutFoot.setOnClickListener(this);
        mImageClose.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_close:
                getActivity().finish();
                break;
            case R.id.layout_foot:
                AppApplication.isMeter = false;
                break;
            case R.id.layout_meter:
                AppApplication.isMeter = true;
                break;
            case R.id.layout_degree_centigrade:
                AppApplication.isCentigrade = true;
                break;
            case R.id.layout_degree_fahrenheit:
                AppApplication.isCentigrade = false;
                break;
        }

    }
}
