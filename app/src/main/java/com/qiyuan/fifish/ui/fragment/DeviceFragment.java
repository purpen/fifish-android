package com.qiyuan.fifish.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.testffmpeg.VideoPlayActivity;
import com.ftp.RemoteCameraManager;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.adapter.ViewPagerAdapter;
import com.qiyuan.fifish.ui.view.CustomHeadView;
import com.qiyuan.fifish.ui.view.ScrollableView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeviceFragment extends BaseFragment {
    @BindView(R.id.custom_head)
    CustomHeadView customHead;
    @BindView(R.id.tv_wifi)
    TextView tvWifi;
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.tv_device_name)
    TextView tvDeviceName;
    @BindView(R.id.scrollableView)
    ScrollableView scrollableView;
    @BindView(R.id.btn_link_wifi)
    Button btnLinkWifi;
    @BindView(R.id.btn_help)
    Button btnHelp;
    @BindView(R.id.ll_dots)
    LinearLayout ll_dots;
    private ArrayList<ImageView> imageViews = new ArrayList<>();
    private ArrayList<Integer> list;
    private int currentItem;
    TestNetworkStatus task;
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
        list.add(R.mipmap.guide0);
        list.add(R.mipmap.guide1);
        list.add(R.mipmap.guide2);
        list.add(R.mipmap.guide3);
        scrollableView.setAdapter(new ViewPagerAdapter<>(activity, list));
        showIndicators();

        task = new TestNetworkStatus();
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,(Void) null);
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

    @OnClick({R.id.btn_help,R.id.btn_link_wifi})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_link_wifi:
                Intent intent = new Intent();
                intent.setClass(getActivity(), VideoPlayActivity.class);
                intent.putExtra(VideoPlayActivity.AUTO_START, true);
                startActivity(intent);
                task.mStop = true;
                Log.e(">>", ">>>日志开始了〈〉〈〉");
                break;
            case R.id.btn_help:
                /**
                 * 为了不重复显示dialog，在显示对话框之前移除正在显示的对话框。
                 */
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment fragment = getFragmentManager().findFragmentByTag( LinkHelpFragment.class.getSimpleName());
                if (null != fragment) {
                    ft.remove(fragment);
                }
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

    public static enum STATUS {
        NO_WIFI, UNCONNECTED, CONNECTED;
        public String msg;
    };

    private class TestNetworkStatus extends AsyncTask<Void, STATUS, Void> {
        public boolean mStop = false;
        RemoteCameraManager.DeviceInfo info;
        @Override
        protected Void doInBackground(Void... params) {
            while (!mStop) {
                if(DeviceFragment.this.isResumed())
                {
                    ConnectivityManager connectivityManager = (ConnectivityManager) getActivity()
                            .getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connectivityManager
                            .getActiveNetworkInfo();
                    if (networkInfo != null
                            && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                        WifiManager wifiManager = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
                        final WifiInfo info = wifiManager.getConnectionInfo();
                    } else {
                        this.publishProgress(STATUS.NO_WIFI);
                        try {
                            Thread.sleep(3000);
                        } catch (Exception e) {

                        }
                        continue;
                    }
                    try {
                        info = RemoteCameraManager.getInstance().doGetDeviceInfoSync();
                        if(info != null){
                            this.publishProgress(STATUS.CONNECTED);
                            Thread.sleep(10000);
                        }else{
                            this.publishProgress(STATUS.UNCONNECTED);
                        }
                    } catch (Exception e) {
                        this.publishProgress(STATUS.UNCONNECTED);
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {

                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(STATUS... values) {
            super.onProgressUpdate(values);
            for (STATUS status : values) {
                switch (status) {
                    case NO_WIFI:
                        btnLinkWifi.setEnabled(false);
                        btnHelp.setVisibility(View.VISIBLE);
                        tvWifi.setText("WiFi未连接");
                        break;
                    case UNCONNECTED:
                        btnLinkWifi.setEnabled(false);
                        btnHelp.setVisibility(View.VISIBLE);
                        tvWifi.setText("设备未连接");
                        break;
                    case CONNECTED:
                        btnLinkWifi.setEnabled(true);
                        //btnHelp.setVisibility(View.GONE);
                        String name  = info.DeviceName;
                        if(DeviceFragment.this.isResumed())
                        tvWifi.setText("设备已连接");
                        break;
                }
            }
        }
    }
}
