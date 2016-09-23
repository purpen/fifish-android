package com.qiyuan.fifish.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.math.BigDecimal;

/**
 * Created by android on 2016/2/27.
 */
public class DataCleanUtil {
    private static final int RATE = 1024;

    public static String getTotalCacheSize(Context context) throws Exception {
        long cacheSize = getFolderSize(context.getCacheDir());
        cacheSize += getFolderSize(context.getFilesDir());
        cacheSize += getFolderSize(FileUtil.getSaveFolder(context.getPackageName()));
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(context.getExternalCacheDir());
        }
        return getFormatSize(cacheSize);
    }

    public static void cleanAppData(Context context, String... filepath) {
        cleanInternalCache(context);
        cleanExternalCache(context);
        cleanFiles(context);
        if (filepath == null) {
            return;
        }
        for (String filePath : filepath) {
            cleanCustomCache(filePath);
        }
    }

    public static void cleanInternalCache(Context context) {
        deleteFolderFile(context.getCacheDir(), true);
    }

    public static void cleanFiles(Context context) {
        deleteFolderFile(context.getFilesDir(), true);
    }

    public static void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            deleteFolderFile(context.getExternalCacheDir(), true);
        }
    }

    public static void deleteFolderFile(File file, boolean deleteThisPath) {
        if (file == null) return;
        try {
            if (file.isDirectory()) {// 如果下面还有文件
                File files[] = file.listFiles();
                for (File file1 : files) {
                    deleteFolderFile(file1, true);
                }
            }
            if (deleteThisPath) {
                if (!file.isDirectory()) {// 如果是文件，删除
                    file.delete();
                } else {// 目录
                    if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                        file.delete();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void cleanCustomCache(String filePath) {
        deleteFolderFile(new File(filePath), true);
    }


    public static long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size += getFolderSize(aFileList);
                } else {
                    size += aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    public static String getFormatSize(double size) {
        BigDecimal result;
        size = size / (RATE * RATE);
        if (size < RATE) {
            result = new BigDecimal(Double.toString(size));
            return result.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        size = size / RATE;
        if (size < RATE) {
            result = new BigDecimal(Double.toString(size));
            return result.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        size = size / RATE;
        if (size < RATE) {
            result = new BigDecimal(Double.toString(size));
            return result.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        result = new BigDecimal(size);
        return result.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }
}
