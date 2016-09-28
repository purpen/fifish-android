package com.tcp;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Created by android on 2016/9/1.
 */

public class ClientThread implements Runnable {
    private Socket s;
    // 定义向UI线程发送消息的Handler对象
    Handler handler;
    // 定义接收UI线程的Handler对象
    public Handler revHandler;
    // 该线程处理Socket所对用的输入输出流
    BufferedReader br = null;
    OutputStream os = null;

    public static String toHexString(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }

    private static final String HEX_CODE = "0123456789ABCDEF";

    public static String byteArrayToHexString(byte[] bs) {
        int _byteLen = bs.length;
        StringBuilder _result = new StringBuilder(_byteLen * 2);
        for (int i = 0; i < _byteLen; i++) {
            int n = bs[i] & 0xFF;
            _result.append(HEX_CODE.charAt(n >> 4));
            _result.append(HEX_CODE.charAt(n & 0x0F));
        }
        return String.valueOf(_result);
    }


    public ClientThread(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        s = new Socket();
        try {
            s.connect(new InetSocketAddress("192.168.2.222", 4321), 2000);
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            os = s.getOutputStream();
            final BufferedInputStream bis = new BufferedInputStream(s.getInputStream());

            // 启动一条子线程来读取服务器相应的数据
            new Thread() {
                @Override
                public void run() {
                    String content = null, buffer = "";

                    // 不断的读取Socket输入流的内容
                    try {

                        byte data[] = new byte[18];
                        String str = null,ss=null;
                        while ((bis.read(data, 0, data.length)) != -1) {
                            str = byteArrayToHexString(data);
                            if (str.startsWith("AA55") && str.endsWith("A5")) {
                                Log.e(">>>", ">>>charchar>>>" + str);
                                //substring(4, 34)包含第四位，不包含第34位
                                str = str.substring(4, 34);
//                                for (int i = 0; i < str.length(); i++) {
//                                    ss = str.substring(i, i + 2);
//                                    Log.e(">>>", ">>>flsjfd>>>" + ss);
//                                    ss = Integer.valueOf(ss,16)+"";
                                    Message msg = new Message();
                                    msg.what = 0x123;
                                    msg.obj = str;
                                    handler.sendMessage(msg);
//                                    i++;
//                                }
                            }
                        }
                        Log.e(">>>", ">>>flsjfd>>>" + str);
                        /*while ((content = br.readLine()) != null) {
                            buffer = toHexString(content);
                            Log.e(">>>", ">>>之之之之>>>" + buffer);
                            // 每当读取到来自服务器的数据之后，发送的消息通知程序
                            // 界面显示该数据
                            Message msg = new Message();
                            msg.what = 0x123;
                            msg.obj = content;
                            handler.sendMessage(msg);
                        }*/
                    } catch (IOException io) {
                        io.printStackTrace();
                    }
                }

            }.start();
            // 为当前线程初始化Looper
            Looper.prepare();
            // 创建revHandler对象
            revHandler = new Handler() {

                @Override
                public void handleMessage(Message msg) {
                    // 接收到UI线程的中用户输入的数据
                    if (msg.what == 0x345) {
                        // 将用户在文本框输入的内容写入网络
                        try {
//                            os.write((msg.obj.toString() + "")
//                                    .getBytes("gbk"));
                            os.write("app_connect".getBytes());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            };
            // 启动Looper
            Looper.loop();

        } catch (SocketTimeoutException e) {
            Message msg = new Message();
            msg.what = 0x123;
            msg.obj = "网络连接超时！";
            handler.sendMessage(msg);
        } catch (IOException io) {
            io.printStackTrace();
        }

    }
}