package com.qiyuan.fifish.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.*;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.application.AppApplication;
import org.xutils.common.util.LogUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
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
                LogUtil.e("dir is null");
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        LogUtil.e(file2.getAbsolutePath());
                        traverseFolder(file2.getAbsolutePath());
                    } else {
                        LogUtil.e(file2.getAbsolutePath());
                    }
                }
            }
        } else {
            LogUtil.e("file not exist");
        }
    }

    public static void deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                LogUtil.e("dir is null");
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
            LogUtil.e("All file has been deleted");
        } else {
            LogUtil.e("file not exist");
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

    //获取手机状态栏高度
    public static int getStatusBarHeight() {
        int statusBarHeight = 0;
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(c.newInstance()).toString());
            statusBarHeight = AppApplication.getInstance().getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    public static boolean isEmailValid(String email) {
        if (TextUtils.isEmpty(email)) return false;
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isPhoneValid(String phone) {
        if (TextUtils.isEmpty(phone)) return false;
        return PhoneNumberUtils.isGlobalPhoneNumber(phone);
    }


    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * 获取Listview的高度，然后设置ViewPager的高度
     * @param listView
     * @return
     */
    public static int setListViewHeightBasedOnChildren1(ListView listView) {
        //获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return 0;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { //listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); //计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); //统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        //listView.getDividerHeight()获取子项间分隔符占用的高度
        //params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
        return params.height;
    }


    public static String second2Hour(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    /**
     * dp转px
     *
     * @param context 上下文
     * @param dpValue dp值
     * @return px值
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转dp
     *
     * @param context 上下文
     * @param pxValue px值
     * @return dp值
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
