package com.qiyuan.fifish.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.ajyw.R;
import com.ajyw.application.AppApplication;
import com.orhanobut.logger.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    public static String getImei(Context context) {
        TelephonyManager phoneMgr = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return phoneMgr.getDeviceId();
    }

    public static String getChannelID(Context context) {

        Bundle metaData = null;
        String metaValue = null;

        if (context == null) {
            return null;
        }
        try {

            ApplicationInfo ai = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);

            if (null != ai) {
                metaData = ai.metaData;
            }
            if (null != metaData) {
                metaValue = metaData.getString("UMENG_CHANNEL");
            }

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        return metaValue;

    }

    public static void makeToast(String content) {
        Toast result = new Toast(AppApplication.getInstance());
        result.setGravity(Gravity.CENTER, 0, 0);
        result.setDuration(Toast.LENGTH_SHORT);
        View v = inflateView(R.layout.transient_notification, null);
        TextView tv = (TextView) v.findViewById(R.id.message);
        tv.setText(content);
        result.setView(v);
        result.show();
    }

    public static void makeToast(int resId) {
        makeToast(AppApplication.getInstance().getResources().getString(resId));
    }
    public static float getScreenHeightDPI() {
        WindowManager wm = (WindowManager) AppApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.ydpi;
    }

    public static float getScreenWidthDPI() {
        WindowManager wm = (WindowManager) AppApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.xdpi;
    }

    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) AppApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }


    public static int getScreenHeight() {
        WindowManager wm = (WindowManager) AppApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static View inflateView(int resourceId, ViewGroup root) {
        LayoutInflater inflater = (LayoutInflater) AppApplication.getInstance().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(resourceId, root);
    }

    public static View inflateView(Activity activity, int resourceId, ViewGroup root) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(resourceId, root);
    }

    public static int getScaleHeight(Activity activity, int w, int h) {

        return getScreenWidth() * h / w;
    }

    public static void traverseFolder(String path) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                Logger.e("dir is null");
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        Logger.e(file2.getAbsolutePath());
                        traverseFolder(file2.getAbsolutePath());
                    } else {
                        Logger.e(file2.getAbsolutePath());
                    }
                }
            }
        } else {
            Logger.e("file not exist");
        }
    }

    public static void deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                Logger.e("dir is null");
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        traverseFolder(file2.getAbsolutePath());
                    } else {
                        file2.delete();
                    }
                }
            }
            file.delete();
            Logger.e("All file has been deleted");
        } else {
            Logger.e("file not exist");
        }
    }


    public static String replaceChinese2UTF8(String url) {
        Pattern pattern = Pattern.compile("[\u3400-\u9FFF]+");
        Matcher matcher = pattern.matcher(url);
        while (matcher.find()) {
            try {
                String str = matcher.group();
                url = url.replace(str, URLEncoder.encode(str, Constants.CHARSET));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return url;
    }

    public static File saveBitmapToFile(Bitmap bitmap) {
        File file = new File(Environment.getExternalStorageDirectory(),
                "tmp_avatar" + ".png");
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream fops = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fops);
            fops.flush();
            fops.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bitmap.recycle();
        }
        return file;
    }

    public static String saveBitmap2Base64Str(Bitmap bitmap) {
        if (bitmap == null) return null;
        String imgStr = null;
        try {
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bao);

            byte[] ba = bao.toByteArray();

            imgStr = Base64.encodeToString(ba, Base64.DEFAULT);
            bao.flush();
            bao.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imgStr;
    }

    public static String formatDouble(String price) throws NumberFormatException {
        DecimalFormat decimalFormat = new DecimalFormat("#.0");
        return decimalFormat.format(Double.parseDouble(price));
    }

    public static String formatFloat(float price) throws NumberFormatException {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        return decimalFormat.format(price);
    }

}
