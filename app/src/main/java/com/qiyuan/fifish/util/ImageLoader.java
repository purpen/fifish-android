package com.qiyuan.fifish.util;
import android.net.Uri;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.application.AppApplication;

import java.io.File;

/**
 * @author lilin
 * created at 2016/7/25 18:24
 */
public class ImageLoader {
    public static final int placeHolderId= R.mipmap.ic_launcher;
    public static void loadImage(String url, ImageView imageView, int placeHolderId) {
        Glide.with(AppApplication.getInstance()).load(url).placeholder(placeHolderId).diskCacheStrategy(DiskCacheStrategy.ALL).error(placeHolderId).into(imageView);
    }

    public static void loadImage(String url, ImageView imageView) {
        loadImage(url,imageView,placeHolderId);
    }
    public static void loadImage(Uri uri, ImageView imageView) {
        Glide.with(AppApplication.getInstance()).load(uri).placeholder(placeHolderId).diskCacheStrategy(DiskCacheStrategy.ALL).error(placeHolderId).into(imageView);
    }

    public static void loadImage(int imgId, ImageView imageView) {
        Glide.with(AppApplication.getInstance()).load(imgId).placeholder(placeHolderId).diskCacheStrategy(DiskCacheStrategy.ALL).error(placeHolderId).into(imageView);
    }
    public static void loadImage(File file, ImageView imageView) {
        Glide.with(AppApplication.getInstance()).load(file).placeholder(placeHolderId).diskCacheStrategy(DiskCacheStrategy.ALL).error(placeHolderId).into(imageView);
    }
}
