package com.qiyuan.fifish.util;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.qiyuan.fifish.application.AppApplication;

/**
 * @author lilin
 * created at 2016/7/25 18:24
 */
public class ImageLoader {
    public static void loadImage(String url, ImageView imageView, int placeHolderId) {
        Glide.with(AppApplication.getInstance()).load(url).placeholder(placeHolderId).diskCacheStrategy(DiskCacheStrategy.ALL).error(placeHolderId).into(imageView);
    }
}
