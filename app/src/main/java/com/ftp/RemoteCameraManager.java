package com.ftp;

import android.util.Log;
import com.example.testffmpeg.VideoPlayActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;
import org.xutils.http.annotation.HttpResponse;
import org.xutils.http.app.ParamsBuilder;
import org.xutils.http.app.ResponseParser;
import org.xutils.http.request.UriRequest;
import org.xutils.x;

import javax.net.ssl.SSLSocketFactory;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.testffmpeg.MyTimeZone.getCurTimeZoneNumber;

public class RemoteCameraManager {
    private static final String TAG = "RemoteCameraManager";
    private static RemoteCameraManager instance;

    private RemoteCameraManager() {
    }

    ;

    public static RemoteCameraManager getInstance() {
        if (instance == null)
            instance = new RemoteCameraManager();
        return instance;
    }

    boolean mIsLogin = false;
    boolean mDoLogin = false;

    public static class CWPCmd {
        public boolean success;
        public String version = "1.0";
        public String token = "11";
        public String type = "request";
        public String action = "CWJSONAuth";
        public String message_id = "";
        public int code = 0;
        public JSONObject body = null;
        public Object bodyObj = null;

        public String toJSONString() {
            try {
                JSONObject head = new JSONObject();
                head.put("version", version);
                if (token != null)
                    head.put("token", token);
                head.put("type", type);
                head.put("action", action);
                if (message_id != null)
                    head.put("message_id", message_id);

                JSONObject object = new JSONObject();
                object.put("head", head);
                if (body != null) {
                    object.put("body", body);
                }
                return object.toString();
            } catch (JSONException e) {
                Log.w(TAG, "toJSONString JSONException:" + e.getMessage());
            }
            return null;
        }

        public boolean parseJSONString(String cwp) {
            success = false;
            if (cwp.startsWith("CWP")) {
                int start = cwp.indexOf("{", 0);
                String json = cwp.substring(start);
                try {
                    JSONObject obj = new JSONObject(json);
                    JSONObject head = obj.getJSONObject("head");
                    action = head.getString("action");
                    code = head.getInt("code");
                    try {
                        body = obj.getJSONObject("body");
                    } catch (JSONException e) {
                        body = null;
                    }
                    success = true;
                } catch (JSONException e) {
                    Log.w(TAG, "parseJSONObject error:" + e.getMessage());
                }
            }
            return success;
        }

    }

    private void doCommonAction(String action, JSONObject body, Callback.CommonCallback cb) {
        //GET /cmd?CWPCmd={"head":{"version":"1.0","token":"11","type":"request","action":"CW_GET_AlarmMsg","message_id":""}}&_=1461727526954
        RequestParams params = new RequestParams("http://" + VideoPlayActivity.SERVER_IP + "/cmd");
        CWPCmd cmd = new CWPCmd();
        cmd.action = action;
        cmd.body = body;
        String CWPCmdString = cmd.toJSONString();
        params.addQueryStringParameter("CWPCmd", CWPCmdString);
        params.setConnectTimeout(5000);
        Log.e(TAG + ">>>", "request:" + params.getUri() + "?CWPCmd=" + CWPCmdString);
        if (cb == null) {
            cb = new Callback.CommonCallback<String>() {
                //CWP129@{"body":{"Msg":null},"head":{"action":"CW_GET_AlarmMsg","code":0,"message_id":"","token":"11","type":"response","version":"1.0"}}
                @Override
                public void onSuccess(String result) {
                    Log.e(TAG + ">>>", "onSuccess:" + result);
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Log.e(TAG + ">>>", "" + ex.toString());
                }

                @Override
                public void onCancelled(CancelledException cex) {
                }

                @Override
                public void onFinished() {
                }
            };
        }
        x.http().get(params, cb);
    }

    public void doLogin() {
        if (mDoLogin || mIsLogin)
            return;
        mDoLogin = true;
        JSONObject body = null;
        try {
            body = new JSONObject();
            body.put("UserName", "admin");
            body.put("UserPassword", "admin");
        } catch (JSONException e) {
            Log.w(TAG, "doLogin JSONException:" + e.getMessage());
        }
        doCommonAction("CW_JSON_Auth", body, new Callback.CommonCallback<String>() {
            //CWP129@{"body":{"Msg":null},"head":{"action":"CW_GET_AlarmMsg","code":0,"message_id":"","token":"11","type":"response","version":"1.0"}}
            @Override
            public void onSuccess(String result) {
                Log.i(TAG, "onSuccess:" + result);
                CWPCmd cmd = new CWPCmd();
                if (cmd.parseJSONString(result)) {
                    mIsLogin = true;
                } else {
                    mIsLogin = false;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
                mDoLogin = false;
            }
        });
    }

    //截图
    public void doSnapPic() {
        JSONObject body = null;
        try {
            body = new JSONObject();
            body.put("ChannelID", 0);
            body.put("SnapPicCount", 1);
            body.put("SnapPicFrequency", 1);
        } catch (JSONException e) {
            Log.w(TAG, "doLogin doCapture:" + e.getMessage());
        }
        doCommonAction("CW_JSON_SnapPic", body, null);
    }

    //录制
    public void doManualRecord(boolean stop) {
        JSONObject body = null;
        try {
            body = new JSONObject();
            body.put("ChannelID", 0);
            body.put("Type", stop ? 0 : 1);//0:停止 1:开始
        } catch (JSONException e) {
            Log.w(TAG, "doLogin doCapture:" + e.getMessage());
        }
        doCommonAction("CW_JSON_ManualRecord", body, null);
    }

    //图像设置界面的数据访问
    public void getImageSet(Callback.CommonCallback cb) {
        JSONObject body = null;
        try {
            body = new JSONObject();
            body.put("ChannelID", 0);
            body.put("BackLight", 1);
            body.put("DayToNightModel", 1);//"DayToNightModel": 2黑白，1彩色，0自动
            body.put("LowLumEnable", 1);
        } catch (JSONException e) {
            Log.w(TAG, "doLogin doCapture:" + e.getMessage());
        }
        doCommonAction("CW_JSON_GetVideo", body, cb);
    }

    //背光补偿 0---关闭  1---打开
    public void setBackBright(int backBright,  Callback.CommonCallback cb) {
        JSONObject body = null;
        try {
            body = new JSONObject();
            body.put("BackLight", backBright);
//            body.put("LowLumEnable", 0);
            body.put("ChannelID", 0);
//			body.put("AWBMode", 0);
//			body.put("VideoInputRate", 0);
//			body.put("EVCompensation", 6);
//			body.put("EVMode", 0);
//			body.put("AutoMinShutter", 14);
//			body.put("AutoMaxShutter", 2);
//			body.put("AutoMaxGain", 12);
//			body.put("DayToNightSwitch", 50);
//			body.put("NightToDaySwitch", 150);
//			body.put("D3FilteStrength", 5);
//			body.put("HdrStrength", 5);
//			body.put("HlcStrength", 1);
//			body.put("DefogStrength",0);
        } catch (JSONException e) {
            Log.w(TAG, "doLogin doCapture:" + e.getMessage());
        }
        doCommonAction("CW_JSON_SetVideo", body, cb);
    }

    //低照度 0---关闭  1---打开
    public void setLowLight(int light, Callback.CommonCallback cb) {
        JSONObject body = null;
        try {
            body = new JSONObject();
            body.put("LowLumEnable", light);
            body.put("ChannelID", 0);
//			body.put("BackLight", 0);
//			body.put("AWBMode", 0);
//			body.put("VideoInputRate", 0);
//			body.put("EVCompensation", 6);
//			body.put("EVMode", 0);
//			body.put("AutoMinShutter", 14);
//			body.put("AutoMaxShutter", 2);
//			body.put("AutoMaxGain", 12);
//			body.put("DayToNightSwitch", 50);
//			body.put("NightToDaySwitch", 150);
//			body.put("D3FilteStrength", 5);
//			body.put("HdrStrength", 5);
//			body.put("HlcStrength", 1);
//			body.put("DefogStrength",0);
        } catch (JSONException e) {
            Log.w(TAG, "doLogin doCapture:" + e.getMessage());
        }
        doCommonAction("CW_JSON_SetVideo", body, cb);
    }
    //自动（日/夜）
    public void setDayNight(int mode, Callback.CommonCallback cb) {
        JSONObject body = null;
        try {
            body = new JSONObject();
            body.put("DayToNightModel", mode);
            body.put("ChannelID", 0);
        } catch (JSONException e) {
            Log.w(TAG, "doLogin doCapture:" + e.getMessage());
        }
        doCommonAction("CW_JSON_SetVideo", body, cb);
    }
    //获取当前编码设置
    public void getEncode(Callback.CommonCallback cb) {
        JSONObject body = null;
        try {
            body = new JSONObject();
            body.put("StreamTypeId", 7);
            body.put("StreamId", 0);
            body.put("ChannelID", 0);
        } catch (JSONException e) {
            Log.w(TAG, "doLogin doCapture:" + e.getMessage());
        }
        doCommonAction("CW_JSON_GetVideoEncodeEx", body, cb);
    }
    // 视频流分辨率尺寸
    /*public void setEncodeResolution(int videoSize,int streamId,int channelID,int streamTypeId,
                                    int h264Profiles,int videoBitrateCtrlMode,int videoCBRBitrate,int encodeFormat,
                                    int framerate,int gop,int quality,int type,
                                    int vbrMaxbitrate,int vbrMinbitrate,Callback.CommonCallback cb) {*/
    public void setEncodeResolution(int channelTypeId,JSONArray jArray,Callback.CommonCallback cb) {
        JSONObject body = null;
        try {
            body = new JSONObject();
            body.put("ChannelTypeId", channelTypeId);
            body.put("StreamInfoListArray", jArray);

        } catch (JSONException e) {
            Log.w(TAG, "doLogin doCapture:" + e.getMessage());
        }

        doCommonAction("CW_JSON_SetVideoEncodeEx", body, cb);
    }

    //设置摄像机系统时间
    public void setCameraTime() {
        SimpleDateFormat formatter    =   new    SimpleDateFormat    ("yyyy年MM月dd日    HH:mm:ss     ");
        Date curDate    =   new    Date(System.currentTimeMillis());//获取当前时间
        String    str    =    formatter.format(curDate);

        JSONObject body = null;
        try {
            body = new JSONObject();
            body.put("TimeZoneId", getCurTimeZoneNumber());
            body.put("Seconds", Integer.parseInt(str.substring(21,23)));
            body.put("Minute", Integer.parseInt(str.substring(18,20)));
            body.put("Hours", Integer.parseInt(str.substring(15,17)));
            body.put("Day", Integer.parseInt(str.substring(8,10)));
            body.put("Month", Integer.parseInt(str.substring(5,7)));
            body.put("Year", Integer.parseInt(str.substring(0,4))-1900);
            body.put("NtpEnable", 0);
            body.put("NtpAddress","clock.isc.org");
        } catch (JSONException e) {
            Log.w(TAG, "doLogin doCapture:" + e.getMessage());
        }
        doCommonAction("CW_JSON_SetTime", body, null);
    }
    //CWP129@{"body":{"Msg":null},"head":{"action":"CW_GET_AlarmMsg","code":0,"message_id":"","token":"11","type":"response","version":"1.0"}}
    public static JSONObject parseJSONObject(String cwp) {
        if (cwp.startsWith("CWP")) {
            int start = cwp.indexOf("{", 0);
            String json = cwp.substring(start);
            try {
                JSONObject obj = new JSONObject(json);
                return obj;
            } catch (JSONException e) {
                Log.w(TAG, "parseJSONObject error:" + e.getMessage());
            }
        }
        return null;
    }

    @HttpResponse(parser = CameraResponseParser.class)
    public static class CameraResponse extends CWPCmd {
    }

    public static class CameraResponseParser implements ResponseParser {
        @Override
        public void checkResponse(UriRequest request) throws Throwable {

        }

        @Override
        public Object parse(Type resultType, Class<?> resultClass, String result) throws Throwable {
            if (resultClass == List.class) {
                List<CameraResponse> list = new ArrayList<CameraResponse>();
                CameraResponse cameraResponse = new CameraResponse();
                cameraResponse.parseJSONString(result);
                list.add(cameraResponse);
                return list;
            } else {
                CameraResponse cameraResponse = new CameraResponse();
                cameraResponse.parseJSONString(result);
                return cameraResponse;
            }
        }
    }

    @HttpRequest(
            host = VideoPlayActivity.SERVER_IP,
            path = "cmd",
            builder = CameraDemoParamsBuilder.class
    )
    public static class CameraParams extends RequestParams {
        public static Callback.Cancelable sendCWPCmd(CWPCmd cmd, Callback.CommonCallback<CameraResponse> callback) {
            CameraParams params = new CameraParams();
            params.addQueryStringParameter("CWPCmd", cmd.toJSONString());
            params.setConnectTimeout(5000);
            Log.i(TAG, "request:" + params.getUri() + "?CWPCmd=" + cmd.toJSONString());
            return x.http().get(params, callback);
        }

        public static CameraResponse sendCWPCmdSync(CWPCmd cmd) throws Throwable {
            CameraParams params = new CameraParams();
            params.addQueryStringParameter("CWPCmd", cmd.toJSONString());
            params.setConnectTimeout(5000);
            return x.http().getSync(params, CameraResponse.class);
        }
    }

    public static class CameraDemoParamsBuilder implements ParamsBuilder {
        @Override
        public String buildUri(RequestParams params, HttpRequest httpRequest) {
            String url = "http://" + VideoPlayActivity.SERVER_IP;
            url += "/" + httpRequest.path();
            return url;
        }

        @Override
        public String buildCacheKey(RequestParams params, String[] cacheKeys) {
            return null;
        }

        @Override
        public SSLSocketFactory getSSLSocketFactory() {
            return null;
        }

        @Override
        public void buildParams(RequestParams params) {
            params.setAsJsonContent(true);
        }

        @Override
        public void buildSign(RequestParams params, String[] signs) {
        }
    }

    public static class DeviceInfo {
        public String DeviceName;
        public String HardwareVersion;
        public String UbootVersion;
        public String LinuxVersion;
        public String SoftWareVersion;
        public String DeviceID;
        public String PtzVersion;

        void put(JSONObject obj) {
            try {
                obj.put("DeviceName", DeviceName);
                obj.put("HardwareVersion", HardwareVersion);
                obj.put("UbootVersion", UbootVersion);
                obj.put("LinuxVersion", LinuxVersion);
                obj.put("SoftWareVersion", SoftWareVersion);
                obj.put("DeviceID", DeviceID);
                obj.put("PtzVersion", PtzVersion);

            } catch (JSONException e) {
                Log.w(TAG, "DeviceInfo put JSONException:" + e.getMessage());
            }
        }

        void getFrom(JSONObject obj) {
            try {
                DeviceName = obj.getString("DeviceName");
                HardwareVersion = obj.getString("HardwareVersion");
                UbootVersion = obj.getString("UbootVersion");
                LinuxVersion = obj.getString("LinuxVersion");
                SoftWareVersion = obj.getString("SoftWareVersion");
                DeviceID = obj.getString("DeviceID");
                PtzVersion = obj.getString("PtzVersion");
            } catch (JSONException e) {
                Log.w(TAG, "DeviceInfo getFrom JSONException:" + e.getMessage());
            }
        }
    }

    public static DeviceInfo mDeviceInfo = null;

    public static DeviceInfo getDeviceInfo() {
        return mDeviceInfo;
    }

    public String getDeviceID() {
        if (mDeviceInfo != null) {
            return mDeviceInfo.DeviceID;
        }
        return null;
    }

    public DeviceInfo doGetDeviceInfoSync() {
        CWPCmd cmd = new CWPCmd();
        cmd.action = "CW_JSON_GetDeviceInfo";
        cmd.body = new JSONObject();
        try {
            cmd.body.put("DeviceName", 1);
            cmd.body.put("HardwareVersion", 1);
            cmd.body.put("UbootVersion", 1);
            cmd.body.put("LinuxVersion", 1);
            cmd.body.put("SoftWareVersion", 1);
            cmd.body.put("DeviceID", 1);
            cmd.body.put("PtzVersion", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            CameraResponse result = CameraParams.sendCWPCmdSync(cmd);
            if (result.body != null) {
                DeviceInfo info = new DeviceInfo();
                info.getFrom(result.body);
                mDeviceInfo = info;
                return info;
            }
        } catch (Throwable e) {
            Log.w(TAG, "doGetDeviceInfoSync Throwable:" + e.getMessage());
        }
        return null;
    }

    public Callback.Cancelable doSetDeviceInfo(String DeviceName, final Callback.CommonCallback cb) {
        CWPCmd cmd = new CWPCmd();
        cmd.action = "CW_JSON_SetDeviceInfo";
        cmd.body = new JSONObject();
        try {
            cmd.body.put("DeviceName", DeviceName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Callback.Cancelable cancelable = CameraParams.sendCWPCmd(cmd, cb);
        return cancelable;
    }

    public void doGetDeviceInfo(final Callback.CommonCallback cb) {
        CWPCmd cmd = new CWPCmd();
        cmd.action = "CW_JSON_GetDeviceInfo";
        cmd.body = new JSONObject();
        try {
            cmd.body.put("DeviceName", 1);
            cmd.body.put("HardwareVersion", 1);
            cmd.body.put("UbootVersion", 1);
            cmd.body.put("LinuxVersion", 1);
            cmd.body.put("SoftWareVersion", 1);
            cmd.body.put("DeviceID", 1);
            cmd.body.put("PtzVersion", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Callback.Cancelable cancelable = CameraParams.sendCWPCmd(cmd, new Callback.CommonCallback<CameraResponse>() {
            @Override
            public void onSuccess(CameraResponse result) {
                if (result.body != null) {
                    DeviceInfo info = new DeviceInfo();
                    info.getFrom(result.body);
                    mDeviceInfo = info;
                    result.bodyObj = info;
                }
                if (cb != null)
                    cb.onSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (cb != null)
                    cb.onError(ex, isOnCallback);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                if (cb != null)
                    cb.onCancelled(cex);
            }

            @Override
            public void onFinished() {
                if (cb != null)
                    cb.onFinished();
            }
        });
    }

    public static class CameraLens {
        public int AFControlModel = 1;
        public int AFSensitivity = 1;
        public int AFArea = 1;
        public int AFSearchModel = 1;
        public int DigitZoomEnable = 1;
        public int IrisControl = -1;
        public int ChannelID = 0;

        public void put(JSONObject obj) {
            try {
                obj.put("AFControlModel", AFControlModel);
                obj.put("AFSensitivity", AFSensitivity);
                obj.put("AFArea", AFArea);
                obj.put("AFSearchModel", AFSearchModel);
                obj.put("DigitZoomEnable", DigitZoomEnable);
                if (IrisControl > 0)
                    obj.put("IrisControl", DigitZoomEnable);
                obj.put("ChannelID", ChannelID);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public void getFrom(JSONObject obj) {
            try {
                AFControlModel = obj.getInt("AFControlModel");
                AFSensitivity = obj.getInt("AFSensitivity");
                AFArea = obj.getInt("AFArea");
                AFSearchModel = obj.getInt("AFSearchModel");
                DigitZoomEnable = obj.getInt("DigitZoomEnable");
                ChannelID = obj.getInt("ChannelID");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public Callback.Cancelable doGetCameraLens(final Callback.CommonCallback cb) {
        CWPCmd cmd = new CWPCmd();
        cmd.action = "CW_JSON_GetCameraLens";
        cmd.body = new JSONObject();
        CameraLens obj = new CameraLens();
        obj.put(cmd.body);
        Callback.Cancelable cancelable = CameraParams.sendCWPCmd(cmd, cb);
        return cancelable;
    }

    public Callback.Cancelable doSetCameraLens(CameraLens obj, final Callback.CommonCallback cb) {
        CWPCmd cmd = new CWPCmd();
        cmd.action = "CW_JSON_SetCameraLens";
        cmd.body = new JSONObject();
        obj.put(cmd.body);
        Callback.Cancelable cancelable = CameraParams.sendCWPCmd(cmd, cb);
        return cancelable;
    }

    public static class VideoEncodeEx {
        int ChannelTypeId;

        public static class StreamInfoList {
            public int ChannelID;

            public static class StreamInfo {
                public int H264Profiles;
                public int StreamId;
                public int VideoBitrateCtrlMode;
                public int VideoCBRBitrate;
                public int VideoEncodeFormat;
                public int VideoFramerate;
                public int VideoGop;
                public int VideoQuality;
                public int VideoSize;
                public int VideoType;
                public int VideoVBRMaxBitrate;
                public int VideoVBRMinBitrate;

                public void putTo(JSONObject obj) {
                    try {
                        obj.put("H264Profiles", H264Profiles);
                        obj.put("StreamId", StreamId);
                        obj.put("VideoBitrateCtrlMode", VideoBitrateCtrlMode);
                        obj.put("VideoCBRBitrate", VideoCBRBitrate);
                        obj.put("VideoEncodeFormat", VideoEncodeFormat);
                        obj.put("VideoFramerate", VideoFramerate);
                        obj.put("VideoGop", VideoGop);
                        obj.put("VideoQuality", VideoQuality);
                        obj.put("VideoSize", VideoSize);
                        obj.put("VideoType", VideoType);
                        obj.put("VideoVBRMaxBitrate", VideoVBRMaxBitrate);
                        obj.put("VideoVBRMinBitrate", VideoVBRMinBitrate);
                    } catch (JSONException e) {
                        Log.w(TAG, "VideoEncodeEx.StreamInfoList.StreamInfo putTo JSONException:" + e.getMessage());
                    }
                }

                public void getFrom(JSONObject obj) {
                    try {
                        H264Profiles = obj.getInt("H264Profiles");
                        StreamId = obj.getInt("StreamId");
                        VideoBitrateCtrlMode = obj.getInt("VideoBitrateCtrlMode");
                        VideoCBRBitrate = obj.getInt("VideoCBRBitrate");
                        VideoEncodeFormat = obj.getInt("VideoEncodeFormat");
                        VideoFramerate = obj.getInt("VideoFramerate");
                        VideoGop = obj.getInt("VideoGop");
                        VideoQuality = obj.getInt("VideoQuality");
                        VideoSize = obj.getInt("VideoSize");
                        VideoType = obj.getInt("VideoType");
                        VideoVBRMaxBitrate = obj.getInt("VideoVBRMaxBitrate");
                        VideoVBRMinBitrate = obj.getInt("VideoVBRMinBitrate");
                    } catch (JSONException e) {
                        Log.w(TAG, "VideoEncodeEx.StreamInfoList.StreamInfo getFrom JSONException:" + e.getMessage());
                    }
                }
            }

            public ArrayList<StreamInfo> StreamInfoArray = new ArrayList<StreamInfo>();
            public int StreamTypeId;

            void putTo(JSONObject obj) {
                try {
                    obj.put("ChannelID", ChannelID);
                    if (StreamInfoArray != null && StreamInfoArray.size() > 0) {
                        JSONArray StreamInfoArrayJ = new JSONArray();
                        for (int i = 0; i < StreamInfoArray.size(); i++) {
                            JSONObject StreamInfoArrayItemJ = new JSONObject();
                            StreamInfoArray.get(i).putTo(StreamInfoArrayItemJ);
                            StreamInfoArrayJ.put(i, StreamInfoArrayItemJ);
                        }
                        obj.put("StreamInfoArray", StreamInfoArrayJ);
                    }
                    obj.put("StreamTypeId", StreamTypeId);
                } catch (JSONException e) {
                    Log.w(TAG, "VideoEncodeEx.StreamInfoList putTo JSONException:" + e.getMessage());
                }
            }

            void getFrom(JSONObject obj) {
                try {
                    ChannelID = obj.getInt("ChannelID");
                    JSONArray StreamInfoArrayJ = obj.getJSONArray("StreamInfoArray");
                    StreamInfoArray.clear();
                    if (StreamInfoArrayJ != null && StreamInfoArrayJ.length() > 0) {
                        for (int i = 0; i < StreamInfoArrayJ.length(); i++) {
                            JSONObject StreamInfoArrayItemJ = StreamInfoArrayJ.getJSONObject(i);
                            StreamInfo item = new StreamInfo();
                            item.getFrom(StreamInfoArrayItemJ);
                            StreamInfoArray.add(item);
                        }
                        obj.put("StreamTypeId", StreamTypeId);
                    }
                } catch (JSONException e) {
                    Log.w(TAG, "VideoEncodeEx.StreamInfoList getFrom JSONException:" + e.getMessage());
                }
            }
        }

        public ArrayList<StreamInfoList> StreamInfoListArray = new ArrayList<StreamInfoList>();

        public void putTo(JSONObject obj) {
            try {
                obj.put("ChannelID", ChannelTypeId);
                if (StreamInfoListArray != null && StreamInfoListArray.size() > 0) {
                    JSONArray StreamInfoListArrayJ = new JSONArray();
                    for (int i = 0; i < StreamInfoListArray.size(); i++) {
                        JSONObject StreamInfoListArrayItemJ = new JSONObject();
                        StreamInfoListArray.get(i).putTo(StreamInfoListArrayItemJ);
                        StreamInfoListArrayJ.put(i, StreamInfoListArrayItemJ);
                    }
                    obj.put("StreamInfoListArray", StreamInfoListArrayJ);
                }

            } catch (JSONException e) {
                Log.w(TAG, "VideoEncodeEx putTo JSONException:" + e.getMessage());
            }
        }

        public void getFrom(JSONObject obj) {
            try {
                ChannelTypeId = obj.getInt("ChannelTypeId");
                StreamInfoListArray.clear();
                JSONArray StreamInfoListArrayJ = obj.getJSONArray("StreamInfoListArray");
                if (StreamInfoListArrayJ != null && StreamInfoListArrayJ.length() > 0) {
                    for (int i = 0; i < StreamInfoListArrayJ.length(); i++) {
                        JSONObject StreamInfoListArrayItemJ = StreamInfoListArrayJ.getJSONObject(i);
                        StreamInfoList item = new StreamInfoList();
                        item.getFrom(StreamInfoListArrayItemJ);
                        StreamInfoListArray.add(item);
                    }
                }

            } catch (JSONException e) {
                Log.w(TAG, "VideoEncodeEx getFrom JSONException:" + e.getMessage());
            }
        }
    }

    public Callback.Cancelable doGetVideoEncodeEx(final Callback.CommonCallback cb) {
        CWPCmd cmd = new CWPCmd();
        cmd.action = "CW_JSON_GetVideoEncodeEx";
        Callback.Cancelable cancelable = CameraParams.sendCWPCmd(cmd, cb);
        return cancelable;
    }

    public Callback.Cancelable doSetVideoEncodeEx(VideoEncodeEx obj, final Callback.CommonCallback cb) {
        CWPCmd cmd = new CWPCmd();
        cmd.action = "CW_JSON_SetVideoEncodeEx";
        cmd.body = new JSONObject();
        obj.putTo(cmd.body);
        Callback.Cancelable cancelable = CameraParams.sendCWPCmd(cmd, cb);
        return cancelable;
    }

    public static class SdFile {
        public int SdLoad = 1;
        public int SdFormat = 1;
        public int SdSize = 1;
        public int SdUseSize = 1;

        public void putTo(JSONObject obj) {
            try {
                obj.put("SdLoad", SdLoad);
                obj.put("SdFormat", SdFormat);
                obj.put("SdSize", SdSize);
                obj.put("SdUseSize", SdUseSize);

            } catch (JSONException e) {
                Log.w(TAG, "SdCardInfo put JSONException:" + e.getMessage());
            }
        }

        public void getFrom(JSONObject obj) {
            try {
                SdLoad = obj.getInt("SdLoad");
                SdFormat = obj.getInt("SdFormat");
                SdSize = obj.getInt("SdSize");
                SdUseSize = obj.getInt("SdUseSize");
            } catch (JSONException e) {
                Log.w(TAG, "DeviceInfo getFrom JSONException:" + e.getMessage());
            }
        }
    }

    public Callback.Cancelable doGetSdFile(final Callback.CommonCallback cb) {
        CWPCmd cmd = new CWPCmd();
        cmd.action = "CW_JSON_GetSdFile";
        cmd.body = new JSONObject();
        SdFile obj = new SdFile();
        obj.putTo(cmd.body);
        Callback.Cancelable cancelable = CameraParams.sendCWPCmd(cmd, cb);
        return cancelable;
    }

    public Callback.Cancelable doDelSdFile(final Callback.CommonCallback cb) {
        CWPCmd cmd = new CWPCmd();
        cmd.action = "CW_JSON_DelSdFile5";
        Callback.Cancelable cancelable = CameraParams.sendCWPCmd(cmd, cb);
        return cancelable;
    }
}
