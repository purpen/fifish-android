package com.example.testffmpeg;

import android.os.Handler;
import android.os.Message;
import com.ftp.RemoteCameraManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 2016/9/10.
 */

public class DataParser {
    //　获取编码设置信息
    public static void getEncodeParser(final Handler handler) {
        RemoteCameraManager.getInstance().getEncode(new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                GetVideoEncode encode = null;
                Message msg = new Message();
                msg.what = Constants.ENCODE_SET;
                try {
                    JSONObject obj = new JSONObject(result.substring(7, result.length()));
                    JSONObject nicegoodsObj = obj.getJSONObject("body");
                    encode = new GetVideoEncode();
                    encode.setChannelTypeId(nicegoodsObj.optString("ChannelTypeId"));
                    List<VideoSize> videoList = new ArrayList();
                    JSONArray jsonArray = nicegoodsObj.getJSONArray("StreamInfoListArray");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        VideoSize videoSize = new VideoSize();
                        videoSize.setChannelID(jsonObject.optString("ChannelID"));
                        videoSize.setStreamTypeId(jsonObject.optString("StreamTypeId"));
                        JSONArray array = jsonObject.getJSONArray("StreamInfoArray");
                        List<VideoSizeInner> inners = new ArrayList<VideoSizeInner>();
                        for (int j = 0; j < array.length(); j++) {
                            JSONObject jsonObj = array.getJSONObject(j);
                            VideoSizeInner bean = new VideoSizeInner();
                            bean.setVideoSize(jsonObj.optString("VideoSize"));
                            bean.setH264Profiles(jsonObj.optString("H264Profiles"));
                            bean.setStreamId(jsonObj.optString("StreamId"));
                            bean.setVideoBitrateCtrlMode(jsonObj.optString("VideoBitrateCtrlMode"));
                            bean.setVideoCBRBitrate(jsonObj.optString("VideoCBRBitrate"));
                            bean.setVideoEncodeFormat(jsonObj.optString("VideoEncodeFormat"));
                            bean.setVideoFramerate(jsonObj.optString("VideoFramerate"));
                            bean.setVideoGop(jsonObj.optString("VideoGop"));
                            bean.setVideoQuality(jsonObj.optString("VideoQuality"));
                            bean.setVideoType(jsonObj.optString("VideoType"));
                            bean.setVideoVBRMaxBitrate(jsonObj.optString("VideoVBRMaxBitrate"));
                            bean.setVideoVBRMinBitrate(jsonObj.optString("VideoVBRMinBitrate"));
                            inners.add(bean);
                        }
                        videoSize.setVideoSizeInner(inners);
                        videoList.add(videoSize);

                    }
                    encode.setVideoSize(videoList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                msg.obj = encode;
                handler.sendMessage(msg);
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

    //　获取图像设置信息
    public static void getImageSetParser(final Handler handler) {
        RemoteCameraManager.getInstance().getImageSet(new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                GetImageSet bean = null;
                Message msg = new Message();
                msg.what = Constants.IMAGE_SET;
                try {
                    JSONObject obj = new JSONObject(result.substring(7, result.length()));
                    bean = new GetImageSet();
                    JSONObject nicegoodsObj = obj.getJSONObject("body");
                    bean.setBackLight(nicegoodsObj.optString("BackLight"));
                    bean.setDayToNightModel(nicegoodsObj.optString("DayToNightModel"));
                    bean.setLowLumEnable(nicegoodsObj.optString("LowLumEnable"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                msg.obj = bean;
                handler.sendMessage(msg);
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
}
