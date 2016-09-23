package com.qiyuan.fifish.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
public class FileUtil {
    /**
     * bitmap转文件
     * @param bitmap
     * @param filePath 文件路径
     * @return 文件
     */
    public static File bitmapToFile(Bitmap bitmap, String filePath) {
        File file = new File(filePath.substring(0,
                filePath.lastIndexOf(File.separator)));
        if (!file.exists()) {
            file.mkdirs();
        }

        OutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(filePath),
                    8 * 1024);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeIO(out);
        }
        return file;
    }

    /**
     * 关闭流
     *
     * @param closeables
     */
    public static void closeIO(Closeable... closeables) {
        if (null == closeables || closeables.length <= 0) {
            return;
        }
        for (Closeable cb : closeables) {
            try {
                if (null == cb) {
                    continue;
                }
                cb.close();
            } catch (IOException e) {
                throw new RuntimeException(
                        FileUtil.class.getClass().getName(), e);
            }
        }
    }

    /**
     * 获取SD卡下指定文件夹的绝对路径
     *
     * @return 返回SD卡下的指定文件夹的绝对路径
     */
    public static String getSavePath(String folderName) {
        return getSaveFolder(folderName).getAbsolutePath();
    }

    /**
     * 获取文件夹对象
     *
     * @return 返回SD卡下的指定文件夹对象，若文件夹不存在则创建
     */
    public static File getSaveFolder(String folderName) {
        File file = new File(getSDCardPath() + File.separator + folderName
                + File.separator);
        file.mkdirs();
        return file;
    }

    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 得到本地图片文件
     *
     * @param context
     * @return
     */
    public static ArrayList<HashMap<String, String>> getAllPictures(Context context) {
        ArrayList<HashMap<String, String>> picturemaps = new ArrayList<>();
        HashMap<String, String> picturemap;
        ContentResolver cr = context.getContentResolver();
        //先得到缩略图的URL和对应的图片id
        Cursor cursor = cr.query(
                MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                new String[]{
                        MediaStore.Images.Thumbnails.IMAGE_ID,
                        MediaStore.Images.Thumbnails.DATA
                },
                null,
                null,
                null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                picturemap = new HashMap<>();
                picturemap.put("image_id_path", cursor.getInt(0) + "");
                picturemap.put("thumbnail_path", cursor.getString(1));
                picturemaps.add(picturemap);
            } while (cursor.moveToNext());
            cursor.close();
        }
        //再得到正常图片的path
        for (int i = 0; i < picturemaps.size(); i++) {
            picturemap = picturemaps.get(i);
            String media_id = picturemap.get("image_id_path");
            cursor = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    new String[]{
                            MediaStore.Images.Media.DATA
                    },
                    MediaStore.Images.Media._ID + "=" + media_id,
                    null,
                    null
            );
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    picturemap.put("image_id", cursor.getString(0));
                    picturemaps.set(i, picturemap);
                } while (cursor.moveToNext());
                cursor.close();
            }
        }
        return picturemaps;
    }
}
