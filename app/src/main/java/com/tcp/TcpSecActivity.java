package com.tcp;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.qiyuan.fifish.R;

import java.text.DecimalFormat;

public class TcpSecActivity extends AppCompatActivity {
    private DecimalFormat df = null,df2=null;
    //heading指头的航向，pitch/roll倾斜度和翻滚姿态，USnoic声纳
    TextView showDepth,showTemp,showHeading,showPitchRoll,showBattery,showUSonic;
    // 定义界面上的一个按钮
    Button send;
    Handler handler;
    // 定义与服务器通信的子线程
    ClientThread clientThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcp_sec);
        showDepth = (TextView) findViewById(R.id.show_depth);
        showTemp = (TextView) findViewById(R.id.show_temp);
        showHeading = (TextView) findViewById(R.id.show_heading);
        showPitchRoll = (TextView) findViewById(R.id.show_pitch_roll);
        showBattery = (TextView) findViewById(R.id.show_battery);
        showUSonic = (TextView) findViewById(R.id.show_usonic);
        send = (Button) findViewById(R.id.send);
        df = new DecimalFormat("######0.0");
        df2 = new DecimalFormat("######0.00");
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // 如果消息来自子线程
                if (msg.what == 0x123) {
                    String content=msg.obj.toString();
                    String temp=df.format(Integer.valueOf(content.substring(0,4),16)/10);
                    String depth = df.format(Integer.valueOf(content.substring(4, 8), 16) / 10);
                    String heading = df2.format(Integer.valueOf(content.substring(8, 12), 16) / 100-180);
                    String pitchRoll = df2.format(Integer.valueOf(content.substring(12, 16), 16) / 100-90);
                    String battery=Integer.valueOf(content.substring(16, 18), 16).toString();
                    // 将读取的内容追加显示在文本框中
                    showTemp.setText(temp+"");
                    showDepth.setText( depth+"");
                    showHeading.setText(heading+"");
                    showPitchRoll.setText(pitchRoll+"");
                    showBattery.setText(battery+"%");
                }
            }
        };
        clientThread = new ClientThread(handler);
        // 客户端启动ClientThread线程创建网络连接、读取来自服务器的数据
        new Thread(clientThread).start();
        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    // 当用户按下按钮之后，将用户输入的数据封装成Message
                    // 然后发送给子线程Handler
                    Message msg = new Message();
                    msg.what = 0x345;
//                    msg.obj = input.getText().toString();
                    clientThread.revHandler.sendMessage(msg);
                } catch (Exception e) {

                }
            }
        });

}
}
