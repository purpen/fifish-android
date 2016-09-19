package com.example.testffmpeg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.ftp.RemoteCameraManager;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.application.AppApplication;
import com.tcp.ClientThread;
import com.tcp.RulerHorizontalView;
import com.tcp.RulerVerticalView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.text.DecimalFormat;

@ContentView(R.layout.activity_video_play)
public class VideoPlayActivity extends BaseStyleActivity implements MediaScannerConnectionClient {
    private static final String TAG = "VideoPlayActivity";
    private static final boolean DEBUG = true;
    private static final boolean DEMO = false;
    public static final String SERVER_IP = "192.168.2.158";
    private static final int LOCAL_UDP_PORT = 556;
    public static final int RTSP_PORT = 554;
    private static final int REMOTE_UDP_PORT = 556;
    private static final int REMOTE_TCP_PORT = 556;

    private static final int BUFF_LEN = 4096;
    private static final int THREAD_TIMEOUT = 10;
    private static final String THN_ROV_PATH = "/thn_rov";
    private static final String THN_ROV_RECORD_PATH = "/thn_rov/Record";
    private static final String THN_ROV_CAPTURE_PATH = "/thn_rov/Capture";
    //	private final AnimationManager mAnimationManager = new AnimationManager();;
    public static final String AUTO_START = "auto_start";

    private DecimalFormat df = null, df2 = null;
    // 定义与服务器通信的子线程
    private ClientThread clientThread;
    private int heading;
    @ViewInject(R.id.bt_photo)
    private ImageView photoButton;
    @ViewInject(R.id.bt_record)
    private TextView recordButton;
    @ViewInject(R.id.tv_degree)
    private TextView mDegree;//度数
    @ViewInject(R.id.ruler_view)
    private RulerHorizontalView rulerHorizontalView;
    @ViewInject(R.id.ruler_vertical_left)
    private RulerVerticalView rulerLeft;
    @ViewInject(R.id.ruler_vertical_right)
    private RulerVerticalView rulerRight;
    @ViewInject(R.id.tv_depth)
    private TextView mDepth;
    @ViewInject(R.id.progressbar_battery)
    private ProgressBar mBattery;
    @ViewInject(R.id.tv_phone_battery)
    private TextView mBatteryPhone;
    @ViewInject(R.id.image_battery_rov)
    private ImageView mBatteryRov;
    @ViewInject(R.id.tv_temperature)
    private TextView mTemperature;
    @ViewInject(R.id.btn_osd)
    private ToggleButton mButtonOSD;
    @ViewInject(R.id.layout_heading)
    private RelativeLayout mLayoutHeading;
    @ViewInject(R.id.layout_depth)
    private RelativeLayout mLayoutDepth;
    @ViewInject(R.id.image_set_point)
    private ImageView mSetPointImage;
    @ViewInject(R.id.tv_depth_unit)
    private TextView mDepthUnit;
    private boolean isMeter,isCentigrade;//判断深度、温度的显示单位
    private BroadcastReceiver batteryLevelRcvr;
    private IntentFilter batteryLevelFilter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // 如果消息来自子线程
            if (msg.what == 0x123) {
                String content = msg.obj.toString();
                String temp = df.format(Integer.valueOf(content.substring(0, 4), 16) / 10.0);
                int depth=Integer.valueOf(content.substring(4, 8), 16);
                heading = Integer.valueOf(content.substring(8, 12), 16) / 100;
                String pitchRoll = df2.format(Integer.valueOf(content.substring(12, 16), 16) / 100 - 90);
                int battery = Integer.valueOf(content.substring(16, 18), 16);
                if (AppApplication.isCentigrade) {
                    mTemperature.setText(temp+"°");
                }else {
                    mTemperature.setText(toFahrenheit(30.0)+"°");
                }
                if (AppApplication.isMeter) {
                    mDepthUnit.setText(R.string.depth_meter);
                    mDepth.setText("- "+df.format(depth/ 10.0));
                }else {
                    mDepthUnit.setText(R.string.depth_foot);
                    mDepth.setText("- "+df.format(meterToFoot(depth/ 10.0)));
                }

                rulerLeft.smoothScrollTo(depth);
                rulerRight.smoothScrollTo(depth);
                rulerHorizontalView.smoothScrollTo(heading);
                if (90 < heading && heading <= 180) {
                    heading = 180 - heading;
                } else if (180 < heading && heading <= 270) {
                    heading = heading - 180;
                } else if (270 < heading && heading <= 360) {
                    heading = 360 - heading;
                }
                mDegree.setText(heading + "");
                if (0 < battery && battery <= 20) {
                    mBatteryRov.setImageResource(R.mipmap.device_battery1);
                } else if (20 < battery && battery <= 40) {
                    mBatteryRov.setImageResource(R.mipmap.device_battrey2);
                } else if (40 < battery && battery <= 60) {
                    mBatteryRov.setImageResource(R.mipmap.device_battrey3);
                } else if (60 < battery && battery <= 80) {
                    mBatteryRov.setImageResource(R.mipmap.device_battrey4);
                } else if (80 < battery && battery <= 100) {
                    mBatteryRov.setImageResource(R.mipmap.device_battrey5);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        RemoteCameraManager.getInstance().setCameraTime();//设置摄像头时间和时区
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
        mButtonOSD.setChecked(true);
        mButtonOSD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mLayoutDepth.setVisibility(View.VISIBLE);
                    mLayoutHeading.setVisibility(View.VISIBLE);
                    mTemperature.setVisibility(View.VISIBLE);
                    mBatteryRov.setVisibility(View.VISIBLE);
                }else{
                    mLayoutDepth.setVisibility(View.INVISIBLE);
                    mLayoutHeading.setVisibility(View.INVISIBLE);
                    mTemperature.setVisibility(View.INVISIBLE);
                    mBatteryRov.setVisibility(View.INVISIBLE);
                }
            }
        });
        mSetPointImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(VideoPlayActivity.this, SetVideoActivity.class);
                VideoPlayActivity.this.startActivity(intent);
            }
        });
        setTranslucentStatus(true);
//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
//        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath().build());
        if (getIntent().getBooleanExtra(AUTO_START, false)) {
            doLogin();
        }
        monitorBatteryState();
        rulerLeft.setWithText(false);
        rulerRight.setWithText(false);
        df = new DecimalFormat("######0.0");
        df2 = new DecimalFormat("######0.00");
        clientThread = new ClientThread(handler);
        // 客户端启动ClientThread线程创建网络连接、读取来自服务器的数据
//        new Thread(clientThread).start();

    }

    @Override
    protected void onResume() {
        super.onResume();
        rulerHorizontalView.post(new Runnable() {
            @Override
            public void run() {
                rulerHorizontalView.smoothScrollTo(heading);
            }
        });

    }
    boolean mRecording = false;
    private void onRecod() {
        if (!mStarted)
            return;
        if(mRecording){

            mVideo.onStopRecord();
            Toast.makeText(this, 222+"", Toast.LENGTH_SHORT).show();
//            mClock.stopTick();
//            mClock.setVisibility(View.INVISIBLE);
            mRecording = false;
        }else{

            mVideo.onStartRecord();
            mRecording = true;
            Toast.makeText(this, 111+"", Toast.LENGTH_SHORT).show();
//            mClock.startTick();
//            mClock.setVisibility(View.VISIBLE);
        }

//        String filePath = mVideo.recordd();
//        if (filePath != null) {
//            Log.e(">>>", "" + filePath);
//        }
    }

    private void onCapture() {
        if (!mStarted)
            return;
        mVideo.onCapture();//发送指令以使机器端自己截图存在机器端的存储卡上
        String filePath = mVideo.doScreenShot();
        if (filePath != null) {
            Toast.makeText(this, filePath, Toast.LENGTH_SHORT).show();
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

    private boolean mStarted;
    @ViewInject(R.id.video)
    private VideoView mVideo;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(batteryLevelRcvr);
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

    void doLogin() {
        if (mStarted)
            return;
        mVideo.onPlay(new Runnable() {
            @Override
            public void run() {

               /* try {
                    Message msg = new Message();
                    msg.what = 0x345;
                    clientThread.revHandler.sendMessage(msg);
                } catch (Exception e) {
                    Log.e(">>", ">>>VVV>>7777" + e.toString());
                }*/

            }
        });
        mStarted = true;
    }

    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_FULLSCREEN;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    private void Log(String msg) {
        Log.d(TAG, msg);
    }

    private void monitorBatteryState() {
        batteryLevelRcvr = new BroadcastReceiver() {

            public void onReceive(Context context, Intent intent) {
                StringBuilder sb = new StringBuilder();
                int rawlevel = intent.getIntExtra("level", -1);
                int scale = intent.getIntExtra("scale", -1);
                int status = intent.getIntExtra("status", -1);
                int health = intent.getIntExtra("health", -1);
                int level = -1; // percentage, or -1 for unknown
                if (rawlevel >= 0 && scale > 0) {
                    level = (rawlevel * 100) / scale;
                }
                sb.append("The phone");
                if (BatteryManager.BATTERY_HEALTH_OVERHEAT == health) {
                    sb.append("'s battery feels very hot!");
                } else {
                    switch (status) {
                        case BatteryManager.BATTERY_STATUS_UNKNOWN:
                            sb.append("no battery.");
                            break;
                        case BatteryManager.BATTERY_STATUS_CHARGING:
                            sb.append("'s battery");
                            if (level <= 33)
                                sb.append(" is charging, battery level is low"
                                        + "[" + level + "]");
                            else if (level <= 84)
                                sb.append(" is charging." + "[" + level + "]");
                            else
                                sb.append(" will be fully charged.");
                            break;
                        case BatteryManager.BATTERY_STATUS_DISCHARGING:
                        case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                            if (level == 0)
                                sb.append(" needs charging right away.");
                            else if (level > 0 && level <= 33)
                                sb.append(" is about ready to be recharged, battery level is low"
                                        + "[" + level + "]");
                            else
                                sb.append("'s battery level is" + "[" + level + "]");
                            break;
                        case BatteryManager.BATTERY_STATUS_FULL:
                            sb.append(" is fully charged.");
                            break;
                        default:
                            sb.append("'s battery is indescribable!");
                            break;
                    }
                }
                sb.append(' ');
                mBattery.setProgress(level);
                mBatteryPhone.setText(level+"%");
            }
        };
        batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryLevelRcvr, batteryLevelFilter);
    }
    //米转英尺
    public static double meterToFoot(double meter)
    {
        double n=meter/0.305;
        n=Math.round(n*1000)/1000.0;
        return n;
    }
    //摄氏度转华氏度
    public double toFahrenheit(double centigrade){
        double fahrenheit = 1.8 * centigrade + 32;
        return fahrenheit;
    }
}
