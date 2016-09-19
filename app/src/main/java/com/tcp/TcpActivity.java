package com.tcp;

import android.app.Activity;
import android.os.Handler;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.qiyuan.fifish.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class TcpActivity extends Activity {
    Socket socket = null;
    String buffer = "";
    TextView txt1;
    Button send;
    EditText ed1;

    public Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x11) {
                Bundle bundle = msg.getData();
                txt1.append("server:" + bundle.getString("msg") + "\n");
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcp);
        txt1 = (TextView) findViewById(R.id.tv_ss);
        send = (Button) findViewById(R.id.btn);

      /*  new Thread(){
            public void run() {
                try {
                    //cmd ipconfig获取当前本机ip地址
                    Socket client = new Socket("192.168.2.222", 4321);
                    Log.e(">>>", ">>connect success>>>"+client);
                    //一定要要以换行符结尾，不然readline会一直读取不会停止，所以reaeline没有值返回
                    String str = "app_connect";
                    OutputStream os=client.getOutputStream();
                    os.write(str.getBytes("utf-8"));

                    client.close();
                    BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    Log.e(">>>", "<<<<<");
                    String line=br.readLine();
                    Log.e(">>>", ">>conss>>>"+line);
                    br.close();
                    client.close();
                    os.close();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();*/

        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                geted1 = ed1.getText().toString();
//                txt1.append("client:"+geted1+"\n");
                //启动线程 向服务器发送和接收信息
                new MyThread("app_connect").start();
            }
        });
    }

    class MyThread extends Thread {

        public String txt1;

        public MyThread(String str) {
            txt1 = str;
        }

        @Override
        public void run() {
            //定义消息
            Message msg = new Message();
            msg.what = 0x11;
            Bundle bundle = new Bundle();
            bundle.clear();
            try {
                //连接服务器 并设置连接超时为5秒
                socket = new Socket();
                socket.connect(new InetSocketAddress("192.168.2.222", 4321), 2000);
                Log.e(">>>", ">>>988connect success>>>");
                //获取输入输出流
                OutputStream ou = socket.getOutputStream();
                //向服务器发送信息
                ou.write("app_connect".getBytes());
                ou.flush();
                BufferedReader bff = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
                //读取发来服务器信息
                String line = null;
                buffer = "";
                int by;
                int te;
//                char cha[]=null;
//                 while ((by = bff.read(cha,0,100)) != -1) {
//                    buffer =  bff.read(cha,0,100)+"" ;
//                    Log.e(">>>", ">>>88888>>>"+buffer);
////                    Log.e(">>>", ">>>9999>>>"+Integer.parseInt(buffer,16));
//                }
//                Log.e(">>>", ">>>之之>>>"+bff.read());

                StringBuffer sb = new StringBuffer();
                int ch;
                while ((ch = bff.read()) != -1) {
                    if (ch == '\n'||ch=='\t'||ch=='\r') {
                        Log.e(">>>", ">>>之之之之>>>" + sb);
//                        break;
//                        if((char)bff.read() == '\n'){
//
//                            break;
//                        }
                    }else {
                        sb.append(ch);
//                        Log.e(">>>", ">>>++++>>>" + sb);
                    }

                }


//                while ((line = bff.readLine()) != null) {
//                    buffer = line ;
//                    Log.e(">>>", ">>>888>>>"+buffer);
////                    Log.e(">>>", ">>>9999>>>"+Integer.parseInt(buffer,16));
//                }
                Log.e(">>>", ">>>4444>>>");
                bundle.putString("msg", buffer.toString());
                msg.setData(bundle);
                //发送消息 修改UI线程中的组件
                myHandler.sendMessage(msg);
                //关闭各种输入输出流
                bff.close();
                ou.close();
                socket.close();
            } catch (SocketTimeoutException aa) {
                //连接超时 在UI界面显示消息
                bundle.putString("msg", "服务器连接失败！请检查网络是否打开");
                msg.setData(bundle);
                //发送消息 修改UI线程中的组件
                myHandler.sendMessage(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
