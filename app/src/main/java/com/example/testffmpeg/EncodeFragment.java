package com.example.testffmpeg;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ftp.RemoteCameraManager;
import com.qiyuan.fifish.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by android on 2016/9/9.
 */

public class EncodeFragment extends BaseStyleFragment implements View.OnClickListener {
    private View view;
    private TextView mTv720, mTv1080;
    private GetVideoEncode encodeBean = null;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constants.ENCODE_SET:
                    if (msg.obj != null) {
                        if (msg.obj instanceof GetVideoEncode) {
                            encodeBean = null;
                            encodeBean = (GetVideoEncode) msg.obj;
                            //108是720p 112是1080p
                            if ("112".equals(encodeBean.getVideoSize().get(0).getVideoSizeInner().get(0).getVideoSize())) {
                                mTv1080.setTextColor(getResources().getColor(R.color.black_theme));
                                mTv720.setTextColor(getResources().getColor(R.color.grey_text_color));
                            } else if ("108".equals(encodeBean.getVideoSize().get(0).getVideoSizeInner().get(0).getVideoSize())) {
                                mTv720.setTextColor(getResources().getColor(R.color.black_theme));
                                mTv1080.setTextColor(getResources().getColor(R.color.grey_text_color));
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
        view = inflater.inflate(R.layout.fragment_encode_set, container, false);
        initData();
        initView();
        return view;
    }

    private void initView() {
        mTv720 = (TextView) view.findViewById(R.id.tv_720);
        mTv1080 = (TextView) view.findViewById(R.id.tv_1080);
        mTv1080.setOnClickListener(this);
        mTv720.setOnClickListener(this);
    }

    private void initData() {
        DataParser.getEncodeParser(mHandler);
    }

    /*   (int videoSize,int streamId,int channelID,int streamTypeId,
       int h264Profiles,int videoBitrateCtrlMode,int videoCBRBitrate,int encodeFormat,
       int framerate,int gop,int quality,int type,int vbrMaxbitrate,int vbrMinbitrate)*/
    //108是720p 112是1080p
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_720:
           /*     StringBuilder builder = new StringBuilder();
                builder.append("[");
                //拼接须注意，字段名要加双引号，字段内容如为字符串也得加双引号，整型则不加
                for (int i = 0; i < encodeBean.getVideoSize().size(); i++) {
                    builder.append("{\"ChannelID\":" + encodeBean.getVideoSize().get(i).getChannelID()+",\"StreamInfoArray\":[");
                    for (int j = 0; j < encodeBean.getVideoSize().get(i).getVideoSizeInner().size(); j++) {
                        if (j == 0) {
                            builder.append("{\"H264Profiles\":" + encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getH264Profiles() +
                                    ",\"StreamId\":" + encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getStreamId()
                                    + ",\"VideoBitrateCtrlMode\":" +encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoBitrateCtrlMode()
                                    + ",\"VideoCBRBitrate\":" +encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoCBRBitrate()
                                    + ",\"VideoEncodeFormat\":" +encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoEncodeFormat()
                                    + ",\"VideoFramerate\":" +encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoFramerate()
                                    + ",\"VideoGop\":" +encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoGop()
                                    + ",\"VideoQuality\":" +encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoQuality()
                                    + ",\"VideoSize\":" +108
                                    + ",\"VideoType\":" +encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoType()
                                    + ",\"VideoVBRMaxBitrate\":" +encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoVBRMaxBitrate()
                                    + ",\"VideoVBRMinBitrate\":" +encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoVBRMinBitrate()
                                    + "},");
                        }else {
                            builder.append("{\"H264Profiles\":" + encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getH264Profiles() +
                                    ",\"StreamId\":" + encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getStreamId()
                                    + ",\"VideoBitrateCtrlMode\":" +encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoBitrateCtrlMode()
                                    + ",\"VideoCBRBitrate\":" +encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoCBRBitrate()
                                    + ",\"VideoEncodeFormat\":" +encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoEncodeFormat()
                                    + ",\"VideoFramerate\":" +encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoFramerate()
                                    + ",\"VideoGop\":" +encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoGop()
                                    + ",\"VideoQuality\":" +encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoQuality()
                                    + ",\"VideoSize\":" +encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoSize()
                                    + ",\"VideoType\":" +encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoType()
                                    + ",\"VideoVBRMaxBitrate\":" +encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoVBRMaxBitrate()
                                    + ",\"VideoVBRMinBitrate\":" +encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoVBRMinBitrate()
                                    + "},");
                        }

                    }
                    builder.append("]");
                    builder.replace(builder.length() - 2, builder.length() - 1, "");
                    builder.append(",\"StreamTypeId\":"+encodeBean.getVideoSize().get(i).getStreamTypeId()+"},");
                }
                builder.append("]");
                builder.replace(builder.length() - 2, builder.length() - 1, "");
                String array = builder.toString();
                Log.e(">>", ">A>" + array);*/

                JSONArray jArray = null;
                try {
                    jArray = new JSONArray();
                    if (encodeBean==null) {
                        return;
                    }
                    for (int i = 0; i < encodeBean.getVideoSize().size(); i++) {
                        JSONObject jobj = new JSONObject();
                        jobj.put("ChannelID", Integer.parseInt(encodeBean.getVideoSize().get(i).getChannelID()));
                        JSONArray ar = new JSONArray();
                        for (int j = 0; j < encodeBean.getVideoSize().get(i).getVideoSizeInner().size(); j++) {
                            JSONObject ob = new JSONObject();
                            if (j == 0) {
                                ob.put("H264Profiles", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getH264Profiles()));
                                ob.put("StreamId", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getStreamId()));
                                ob.put("VideoBitrateCtrlMode", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoBitrateCtrlMode()));
                                ob.put("VideoCBRBitrate", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoCBRBitrate()));
                                ob.put("VideoEncodeFormat", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoEncodeFormat()));
                                ob.put("VideoFramerate", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoFramerate()));
                                ob.put("VideoGop", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoGop()));
                                ob.put("VideoQuality", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoQuality()));
                                ob.put("VideoSize", 108);
                                ob.put("VideoType", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoType()));
                                ob.put("VideoVBRMaxBitrate", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoVBRMaxBitrate()));
                                ob.put("VideoVBRMinBitrate", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoVBRMinBitrate()));
                            } else {
                                ob.put("H264Profiles", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getH264Profiles()));
                                ob.put("StreamId", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getStreamId()));
                                ob.put("VideoBitrateCtrlMode", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoBitrateCtrlMode()));
                                ob.put("VideoCBRBitrate", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoCBRBitrate()));
                                ob.put("VideoEncodeFormat", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoEncodeFormat()));
                                ob.put("VideoFramerate", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoFramerate()));
                                ob.put("VideoGop", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoGop()));
                                ob.put("VideoQuality", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoQuality()));
                                ob.put("VideoSize", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoSize()));
                                ob.put("VideoType", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoType()));
                                ob.put("VideoVBRMaxBitrate", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoVBRMaxBitrate()));
                                ob.put("VideoVBRMinBitrate", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoVBRMinBitrate()));
                            }
                            ar.put(ob);
                        }
                        jobj.put("StreamInfoArray", ar);
                        jobj.put("StreamTypeId", Integer.parseInt(encodeBean.getVideoSize().get(i).getStreamTypeId()));
                        jArray.put(jobj);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                RemoteCameraManager.getInstance().setEncodeResolution(Integer.parseInt(encodeBean.getChannelTypeId()), jArray,
                        new Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                mTv1080.setTextColor(getResources().getColor(R.color.grey_text_color));
                                mTv720.setTextColor(getResources().getColor(R.color.black_theme));
                            }

                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {
                                Log.e(">>>", "" + ex.toString());
                            }

                            @Override
                            public void onCancelled(CancelledException cex) {
                            }

                            @Override
                            public void onFinished() {
                            }
                        }
                );
            /*    RemoteCameraManager.getInstance().setEncodeResolution(108,
                        Integer.parseInt(list.get(0).getVideoSizeInner().getStreamId()),
                        Integer.parseInt(list.get(0).getChannelID()),
                        Integer.parseInt(list.get(0).getStreamTypeId()),
                        Integer.parseInt(list.get(0).getVideoSizeInner().getH264Profiles()),
                        Integer.parseInt(list.get(0).getVideoSizeInner().getVideoBitrateCtrlMode()),
                        Integer.parseInt(list.get(0).getVideoSizeInner().getVideoCBRBitrate()),
                        Integer.parseInt(list.get(0).getVideoSizeInner().getVideoEncodeFormat()),
                        Integer.parseInt(list.get(0).getVideoSizeInner().getVideoFramerate()),
                        Integer.parseInt(list.get(0).getVideoSizeInner().getVideoGop()),
                        Integer.parseInt(list.get(0).getVideoSizeInner().getVideoQuality()),
                        Integer.parseInt(list.get(0).getVideoSizeInner().getVideoType()),
                        Integer.parseInt(list.get(0).getVideoSizeInner().getVideoVBRMaxBitrate()),
                        Integer.parseInt(list.get(0).getVideoSizeInner().getVideoVBRMinBitrate()),
                        new Callback.CommonCallback() {
                            @Override
                            public void onSuccess(Object result) {
                                mTv720.setTextColor(getResources().getColor(R.color.black_theme));
                                mTv1080.setTextColor(getResources().getColor(R.color.grey_text_color));
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
                        }
                );*/
                break;
            case R.id.tv_1080:
               /* StringBuilder builder2 = new StringBuilder();
                builder2.append("[");
                //拼接须注意，字段名要加双引号，字段内容如为字符串也得加双引号，整型则不加
                for (int i = 0; i < encodeBean.getVideoSize().size(); i++) {
                    builder2.append("{\"ChannelID\":" + encodeBean.getVideoSize().get(i).getChannelID()+",\"StreamInfoArray\":[");
                    for (int j = 0; j < encodeBean.getVideoSize().get(i).getVideoSizeInner().size(); j++) {
                        if (j == 0) {
                            builder2.append("{\"H264Profiles\":" + encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getH264Profiles() +
                                    ",\"StreamId\":" + encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getStreamId()
                                    + ",\"VideoBitrateCtrlMode\":" +encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoBitrateCtrlMode()
                                    + ",\"VideoCBRBitrate\":" +encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoCBRBitrate()
                                    + ",\"VideoEncodeFormat\":" +encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoEncodeFormat()
                                    + ",\"VideoFramerate\":" +encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoFramerate()
                                    + ",\"VideoGop\":" +encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoGop()
                                    + ",\"VideoQuality\":" +encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoQuality()
                                    + ",\"VideoSize\":" +112
                                    + ",\"VideoType\":" +encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoType()
                                    + ",\"VideoVBRMaxBitrate\":" +encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoVBRMaxBitrate()
                                    + ",\"VideoVBRMinBitrate\":" +encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoVBRMinBitrate()
                                    + "},");
                        }else {
                            builder2.append("{\"H264Profiles\":" + encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getH264Profiles() +
                                    ",\"StreamId\":" + encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getStreamId()
                                    + ",\"VideoBitrateCtrlMode\":" +encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoBitrateCtrlMode()
                                    + ",\"VideoCBRBitrate\":" +encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoCBRBitrate()
                                    + ",\"VideoEncodeFormat\":" +encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoEncodeFormat()
                                    + ",\"VideoFramerate\":" +encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoFramerate()
                                    + ",\"VideoGop\":" +encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoGop()
                                    + ",\"VideoQuality\":" +encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoQuality()
                                    + ",\"VideoSize\":" +encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoSize()
                                    + ",\"VideoType\":" +encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoType()
                                    + ",\"VideoVBRMaxBitrate\":" +encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoVBRMaxBitrate()
                                    + ",\"VideoVBRMinBitrate\":" +encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoVBRMinBitrate()
                                    + "},");
                        }

                    }
                    builder2.append("]");
                    builder2.replace(builder2.length() - 2, builder2.length() - 1, "");
                    builder2.append(",\"StreamTypeId\":"+encodeBean.getVideoSize().get(i).getStreamTypeId()+"},");
                }
                builder2.append("]");
                builder2.replace(builder2.length() - 2, builder2.length() - 1, "");
                String array2 = builder2.toString();
                Log.e(">>", ">A>" + array2);*/

                JSONArray jArray2 = null;
                try {
                    jArray2 = new JSONArray();
                    if (encodeBean==null) {
                        return;
                    }
                    for (int i = 0; i < encodeBean.getVideoSize().size(); i++) {
                        JSONObject jobj2 = new JSONObject();
                        jobj2.put("ChannelID", Integer.parseInt(encodeBean.getVideoSize().get(i).getChannelID()));
                        JSONArray ar = new JSONArray();
                        for (int j = 0; j < encodeBean.getVideoSize().get(i).getVideoSizeInner().size(); j++) {
                            JSONObject ob = new JSONObject();
                            if (j == 0) {
                                ob.put("H264Profiles", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getH264Profiles()));
                                ob.put("StreamId", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getStreamId()));
                                ob.put("VideoBitrateCtrlMode", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoBitrateCtrlMode()));
                                ob.put("VideoCBRBitrate", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoCBRBitrate()));
                                ob.put("VideoEncodeFormat", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoEncodeFormat()));
                                ob.put("VideoFramerate", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoFramerate()));
                                ob.put("VideoGop", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoGop()));
                                ob.put("VideoQuality", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoQuality()));
                                ob.put("VideoSize", 112);
                                ob.put("VideoType", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoType()));
                                ob.put("VideoVBRMaxBitrate", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoVBRMaxBitrate()));
                                ob.put("VideoVBRMinBitrate", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoVBRMinBitrate()));
                            } else {
                                ob.put("H264Profiles", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getH264Profiles()));
                                ob.put("StreamId", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getStreamId()));
                                ob.put("VideoBitrateCtrlMode", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoBitrateCtrlMode()));
                                ob.put("VideoCBRBitrate", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoCBRBitrate()));
                                ob.put("VideoEncodeFormat", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoEncodeFormat()));
                                ob.put("VideoFramerate", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoFramerate()));
                                ob.put("VideoGop", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoGop()));
                                ob.put("VideoQuality", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoQuality()));
                                ob.put("VideoSize", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoSize()));
                                ob.put("VideoType", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoType()));
                                ob.put("VideoVBRMaxBitrate", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoVBRMaxBitrate()));
                                ob.put("VideoVBRMinBitrate", Integer.parseInt(encodeBean.getVideoSize().get(i).getVideoSizeInner().get(j).getVideoVBRMinBitrate()));
                            }
                            ar.put(ob);
                        }
                        jobj2.put("StreamInfoArray", ar);
                        jobj2.put("StreamTypeId", Integer.parseInt(encodeBean.getVideoSize().get(i).getStreamTypeId()));
                        jArray2.put(jobj2);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                RemoteCameraManager.getInstance().setEncodeResolution(Integer.parseInt(encodeBean.getChannelTypeId()), jArray2, new Callback.CommonCallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                mTv1080.setTextColor(getResources().getColor(R.color.black_theme));
                                mTv720.setTextColor(getResources().getColor(R.color.grey_text_color));
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
                        }
                );
              /*  RemoteCameraManager.getInstance().setEncodeResolution(112,
                        Integer.parseInt(list.get(0).getVideoSizeInner().getStreamId()),
                        Integer.parseInt(list.get(0).getChannelID()),
                        Integer.parseInt(list.get(0).getStreamTypeId()),
                        Integer.parseInt(list.get(0).getVideoSizeInner().getH264Profiles()),
                        Integer.parseInt(list.get(0).getVideoSizeInner().getVideoBitrateCtrlMode()),
                        Integer.parseInt(list.get(0).getVideoSizeInner().getVideoCBRBitrate()),
                        Integer.parseInt(list.get(0).getVideoSizeInner().getVideoEncodeFormat()),
                        Integer.parseInt(list.get(0).getVideoSizeInner().getVideoFramerate()),
                        Integer.parseInt(list.get(0).getVideoSizeInner().getVideoGop()),
                        Integer.parseInt(list.get(0).getVideoSizeInner().getVideoQuality()),
                        Integer.parseInt(list.get(0).getVideoSizeInner().getVideoType()),
                        Integer.parseInt(list.get(0).getVideoSizeInner().getVideoVBRMaxBitrate()),
                        Integer.parseInt(list.get(0).getVideoSizeInner().getVideoVBRMinBitrate()),
                        new Callback.CommonCallback() {
                            @Override
                            public void onSuccess(Object result) {
                                mTv720.setTextColor(getResources().getColor(R.color.grey_text_color));
                                mTv1080.setTextColor(getResources().getColor(R.color.black_theme));
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
                        }
                );*/
                break;
        }
    }
}
