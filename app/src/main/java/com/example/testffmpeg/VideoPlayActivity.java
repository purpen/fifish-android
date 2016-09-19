package com.example.testffmpeg;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.qiyuan.fifish.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Set;

@ContentView(R.layout.activity_video_play)
public class VideoPlayActivity extends BaseStyleActivity implements MediaScannerConnectionClient {
	private static final String TAG = "VideoPlayActivity";
	private static final boolean DEBUG = true;
	private static final boolean DEMO = false;
	public 	static final String SERVER_IP = "192.168.2.158";
	private static final int LOCAL_UDP_PORT = 556;
	public 	static final int RTSP_PORT = 554;
	private static final int REMOTE_UDP_PORT = 556;
	private static final int REMOTE_TCP_PORT = 556;

	private static final int BUFF_LEN = 4096;
	private static final int THREAD_TIMEOUT = 10;
	private static final String THN_ROV_PATH = "/thn_rov";
	private static final String THN_ROV_RECORD_PATH = "/thn_rov/Record";
	private static final String THN_ROV_CAPTURE_PATH = "/thn_rov/Capture";
//	private final AnimationManager mAnimationManager = new AnimationManager();;
	public static final String AUTO_START = "auto_start";

	@ViewInject(R.id.bt_photo)
	private Button photoButton;
	@ViewInject(R.id.bt_record)
	private Button recordButton;

	@Override
    protected void onCreate(Bundle savedInstanceState) {    	
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        mVideo.setParams("1", "admin", "admin", "192.168.2.158", "554", "80");
		photoButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onCapture();
			}
		});
		recordButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onRecod();
			}
		});
        setTranslucentStatus(true);
//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
//        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath().build());
        if(getIntent().getBooleanExtra(AUTO_START,false)){
        	doLogin();
        }     
    }

	private void onRecod() {
		
		if(!mStarted)
			return;
		String filePath = mVideo.recordd();
		if(filePath != null){
			Log.e(">>>",""+filePath);
		}
	}

	private void onCapture(){
		if(!mStarted)
			return;
		String filePath = mVideo.doScreenShot();
		if(filePath != null){
			Toast.makeText(this,filePath,Toast.LENGTH_SHORT).show();
		}
	}
    private String[] allFiles;
    private MediaScannerConnection conn;

    @Override
    public void onMediaScannerConnected() {
//    	File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + THN_ROV_PATH);
//        conn.scanFile(folder.getAbsolutePath(), "image/*");
    }
    @Override
    public void onScanCompleted(String path, Uri uri) {
//        try{
//            if (uri != null)
//            {
//            	Intent intent = new Intent(Intent.ACTION_VIEW);
//            	intent.setData(uri);
//            	startActivity(intent);
//            }
//        }finally{
//            conn.disconnect();
//            conn = null;
//        }
    } 
//	Selector selector;

	private	boolean mStarted;
	@ViewInject(R.id.video)
	private VideoView mVideo;


	@Override
	protected void onDestroy() {
		super.onDestroy();
		mVideo.mStatus = VideoView.STATUS.STOPPED;
		mVideo.mThread = null;
		mStarted = false;
//		try{
//			selector.close();
//			selector = null;
//		}catch(IOException e){
//    		e.printStackTrace();
//    	}
	}

    void doLogin(){
    	if(mStarted)
    		return;
		mVideo.onPlay(new Runnable() {
			@Override
			public void run() {
			}
		});
		mStarted = true;
    }

    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS|WindowManager.LayoutParams.FLAG_FULLSCREEN;
        if (on) {
            winParams.flags |=  bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }    
    private void Log(String msg){
    	Log.d(TAG,msg);
    }
}
