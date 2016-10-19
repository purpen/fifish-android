package com.example.testffmpeg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.cnjabsco.android.jni.DecoderJni;
import com.cnjabsco.android.jni.RecordVideoJni;
import com.ftp.RemoteCameraManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VideoView extends ImageView implements Runnable {
    protected String mName;
    protected String mUser;
    protected String mPass;
    protected String mIp;
    protected String mRtsp;
    protected String mWeb;

    protected enum STATUS {
        STOPPED,
        CONNECTING,
        PLAYING,
        PAUSE,
    };
    protected STATUS mStatus = STATUS.STOPPED;
    private static final int SHOW_VIDEO_IMG = 0;
    private static final int CONNECT_SUCCESS = 1;

    public VideoView(Context context) {
        super(context);
    }

    protected Thread mThread;

    public VideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setParams(String name, String user, String pass, String ip, String rtsp, String web) {
        mName = name;
        mUser = user;
        mPass = pass;
        mIp = ip;
        mRtsp = rtsp;
        mWeb = web;
    }

    private Runnable mConnected;

    public void onPlay(Runnable finish) {
        if (mIp == null)
            return;
        if (mStatus == STATUS.STOPPED && mThread == null) {
            mThread = new Thread(this);
            mStatus = STATUS.CONNECTING;
            mThread.start();
        }
        mConnected = finish;
//        RemoteCameraManager.getInstance().doLogin();
    }

    public void onStartRecord() {
        RemoteCameraManager.getInstance().doManualRecord(false);
//        RemoteCameraManager.getInstance().setEncodeResolution(6);
    }

    public void onStopRecord() {
        RemoteCameraManager.getInstance().doManualRecord(true);
//        RemoteCameraManager.getInstance().setEncodeResolution(4);
    }

    public void onCapture() {
        RemoteCameraManager.getInstance().doSnapPic();
    }

    private DecoderJni mDecoder;

    private Bitmap mBpFrame;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SHOW_VIDEO_IMG:
                    VideoView.this.setImageBitmap(mBpFrame);
                    if (mConnected != null) {
                        mConnected.run();
                        mConnected = null;
                    }
                    break;
            }
        }
    };
    public static String getNakeDownloadRootDir(){
        try {
            File sdCardDir = null;
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                sdCardDir = Environment.getExternalStorageDirectory();
            }else{
                sdCardDir = Environment.getDataDirectory();
            }
            File dir = new File(sdCardDir.getCanonicalPath(),"aaaaa");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            return dir.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            Log.w(">>>", "getDownloadRootDir error:" + e.getMessage());
        }
        return null;
    }
    public static String getDeviceDownloadRootDir(){
        String DeviceID = RemoteCameraManager.getInstance().getDeviceID();
        if(DeviceID != null){
            File dir = new File(getNakeDownloadRootDir(),DeviceID);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            return dir.getAbsolutePath();
        }
        return getNakeDownloadRootDir();
    }
    
    //screenshot:屏幕截图工具
    @SuppressLint("NewApi")
	public String doScreenShot(){
        String screenShotDir = getDeviceDownloadRootDir() + "/"+ "screenshot";
        FileOutputStream fos = null;
        String filePath = null;
        try {
            File dir = new File(screenShotDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String fileName = sdf.format(new Date()) + ".jpg";
            filePath = screenShotDir + "/" + fileName;
            fos = new FileOutputStream(filePath);
            if (fos != null) {
                // 将当前帧图像写入到SD卡，保存成图片
                Bitmap bm = mBpFrame.copy(mBpFrame.getConfig(), false);
                bm.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
                return filePath;
            }
        } catch (Exception e) {
            if(filePath!=null && !filePath.isEmpty()){ // 如果抓文件（损坏的图片文件）已生成，则删除
                File file = new File(filePath);
                if(file.exists()){
                    file.delete();
                }
            }
        }
        return null;
    }
    
    public String recordd(){
    	Thread thread=new Thread(new Runnable()  
        {  
            @Override  
            public void run()  
            {  
            	String viPath = "rtsp://" + mUser + ":" + mPass + "@" + mIp + ":" + mRtsp + "/channel1/" + "2";
            	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        	    String fileName = sdf.format(new Date()) + ".mp4";
        		String videoPath =getDeviceDownloadRootDir() + "/"+ "video";
        		File dirVideo = new File(videoPath);
                if (!dirVideo.exists()) {
                	dirVideo.mkdirs();
                }
                String filePath = videoPath + "/" + fileName;
//        		+ "/" + fileName;

        	    RecordVideoJni recordVideoJni=new RecordVideoJni();
        		recordVideoJni.recordVedioJ(filePath,viPath, 1); 
//        		mDecoder.recordVedioJ(filePath,1);
            }  
        });  
        thread.start();  
        return null;
    }
    
    public void run() {    	
        mDecoder = new DecoderJni();
      
        String videoPath = "rtsp://" + mUser + ":" + mPass + "@" + mIp + ":" + mRtsp + "/channel1/" + "2";
//        String videoPath = "rtsp://218.204.223.237:554/live/1/66251FC11353191F/e7ooqwcfbqjoo80j.sdp";
        Log.e(">>", ">videoPath>>" + Uri.parse(videoPath));
        if (mDecoder.openSourceJ(videoPath) != 0) {
            mThread = null;
            mStatus = STATUS.STOPPED;
            return;
        }
        Log.e(">>", ">>openSourceJ>>" + mDecoder.openSourceJ(videoPath));
        initFrameSize();
        mStatus = STATUS.PLAYING;
        while (mStatus != STATUS.STOPPED) {
            if (mStatus == STATUS.PLAYING) {
                if (mDecoder.decodeOneFrameJ(mBpFrame) != 0) {
                    continue;
                }
                mHandler.obtainMessage(SHOW_VIDEO_IMG).sendToTarget();
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        mThread = null;
        mStatus = STATUS.STOPPED;
    }

    private void initFrameSize() {
        int frameWidth = mDecoder.getFrameWidthJ();
        int frameHeight = mDecoder.getFrameHeightJ();
        mBpFrame = Bitmap.createBitmap(frameWidth, frameHeight, Bitmap.Config.ARGB_8888);
        if (Build.VERSION.SDK_INT >= 14) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            mBpFrame.compress(Bitmap.CompressFormat.JPEG, 100, os);
            byte[] buffer = os.toByteArray();
            mBpFrame = BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
        }
    }
}
