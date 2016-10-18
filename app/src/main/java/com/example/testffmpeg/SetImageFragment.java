package com.example.testffmpeg;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ftp.RemoteCameraManager;
import com.qiyuan.fifish.R;

import org.xutils.common.Callback;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by android on 2016/9/9.
 */

public class SetImageFragment extends BaseStyleFragment implements View.OnClickListener {
    private View view;
    private ImageView mBackLight, mLowLight,mImageClose;
    private TextView mDaytime,mNight,mAuto;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constants.IMAGE_SET:
                    if (msg.obj != null) {
                        if (msg.obj instanceof GetImageSet) {
                            GetImageSet getImageSet = null;
                            getImageSet = (GetImageSet) msg.obj;
                            Log.e(">>>", "OO>>" + getImageSet.getBackLight()
                                    + ">>" + getImageSet.getDayToNightModel() + ">>" + getImageSet.getLowLumEnable());
                            if ("1".equals(getImageSet.getBackLight())) {
                                mBackLight.setImageResource(R.mipmap.switch_on);
                                isOpenBackLight = true;
                            } else {
                                mBackLight.setImageResource(R.mipmap.switch_off);
                                isOpenBackLight = false;
                            }
                            if ("1".equals(getImageSet.getLowLumEnable())) {
                                mLowLight.setImageResource(R.mipmap.switch_on);
                                isOpenLowLight = true;
                            } else {
                                mLowLight.setImageResource(R.mipmap.switch_off);
                                isOpenLowLight = false;
                            }
                            if ("0".equals(getImageSet.getDayToNightModel())) {
                                mAuto.setTextColor(getResources().getColor(R.color.black_theme));
                                mDaytime.setTextColor(getResources().getColor(R.color.grey_text_color));
                                mNight.setTextColor(getResources().getColor(R.color.grey_text_color));
                            } else if ("1".equals(getImageSet.getDayToNightModel())) {
                                mAuto.setTextColor(getResources().getColor(R.color.grey_text_color));
                                mDaytime.setTextColor(getResources().getColor(R.color.black_theme));
                                mNight.setTextColor(getResources().getColor(R.color.grey_text_color));
                            } else if ("2".equals(getImageSet.getDayToNightModel())) {
                                mAuto.setTextColor(getResources().getColor(R.color.grey_text_color));
                                mDaytime.setTextColor(getResources().getColor(R.color.grey_text_color));
                                mNight.setTextColor(getResources().getColor(R.color.black_theme));
                            }
                        }
                    }
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_image_set, container, false);
        initData();
        initView(view);
        return view;
    }

    private void initData() {
        DataParser.getImageSetParser(mHandler);
    }

    private void initView(View view) {
        mImageClose = (ImageView) view.findViewById(R.id.image_close);
        mAuto = (TextView) view.findViewById(R.id.tv_auto);
        mNight = (TextView) view.findViewById(R.id.tv_night);
        mDaytime = (TextView) view.findViewById(R.id.tv_daytime);
        mBackLight = (ImageView) view.findViewById(R.id.image_backlight_compensation);
        mLowLight = (ImageView) view.findViewById(R.id.image_low_light);
        mBackLight.setOnClickListener(this);
        mLowLight.setOnClickListener(this);
        mAuto.setOnClickListener(this);
        mDaytime.setOnClickListener(this);
        mNight.setOnClickListener(this);
        mImageClose.setOnClickListener(this);
    }

    private boolean isOpenBackLight = false;
    private boolean isOpenLowLight = false;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_close:
                getActivity().finish();
                break;
            // 0---关闭  1---打开
            case R.id.image_backlight_compensation:
                if (!isOpenBackLight) {
                    RemoteCameraManager.getInstance().setBackBright(1, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            mBackLight.setImageResource(R.mipmap.switch_on);
                            isOpenBackLight = true;
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {

                        }

                        @Override
                        public void onCancelled(CancelledException cex) {

                        }

                        @Override
                        public void onFinished() {

                        }
                    });

                } else {
                    RemoteCameraManager.getInstance().setBackBright(0, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            mBackLight.setImageResource(R.mipmap.switch_off);
                            isOpenBackLight = false;
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {

                        }

                        @Override
                        public void onCancelled(CancelledException cex) {

                        }

                        @Override
                        public void onFinished() {

                        }
                    });

                }
                break;
            case R.id.image_low_light:
                if (!isOpenLowLight) {
                    RemoteCameraManager.getInstance().setLowLight(1, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            mLowLight.setImageResource(R.mipmap.switch_on);
                            isOpenLowLight = true;
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            Toast.makeText(getActivity(), R.string.connet_failed, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(CancelledException cex) {
                            Toast.makeText(getActivity(), R.string.connet_canceled, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFinished() {

                        }
                    });

                } else {
                    RemoteCameraManager.getInstance().setLowLight(0, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            mLowLight.setImageResource(R.mipmap.switch_off);
                            isOpenLowLight = false;
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            Toast.makeText(getActivity(), R.string.connet_failed, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(CancelledException cex) {
                            Toast.makeText(getActivity(), R.string.connet_canceled, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFinished() {

                        }
                    });

                }
                break;
            //"DayToNightModel": 2黑白，1彩色，0自动
            case R.id.tv_auto:
                RemoteCameraManager.getInstance().setDayNight(0, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        mAuto.setTextColor(getResources().getColor(R.color.black_theme));
                        mDaytime.setTextColor(getResources().getColor(R.color.grey_text_color));
                        mNight.setTextColor(getResources().getColor(R.color.grey_text_color));
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
                break;
            case R.id.tv_daytime:
                RemoteCameraManager.getInstance().setDayNight(1, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        mAuto.setTextColor(getResources().getColor(R.color.grey_text_color));
                        mDaytime.setTextColor(getResources().getColor(R.color.black_theme));
                        mNight.setTextColor(getResources().getColor(R.color.grey_text_color));
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
                break;
            case R.id.tv_night:
                RemoteCameraManager.getInstance().setDayNight(2, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        mAuto.setTextColor(getResources().getColor(R.color.grey_text_color));
                        mDaytime.setTextColor(getResources().getColor(R.color.grey_text_color));
                        mNight.setTextColor(getResources().getColor(R.color.black_theme));
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
                break;
        }
    }


}
