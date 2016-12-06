package com.qiyuan.fifish.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.qiyuan.fifish.R;

import java.util.List;

public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {
    protected final String TAG = getClass().getSimpleName();
    protected List<T> list;
    protected Activity activity;
    protected DisplayImageOptions options;

    public BaseAdapter(List<T> list, Activity activity) {
        this.list = list;
        this.activity = activity;
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.default_cover)
                .showImageForEmptyUri(R.mipmap.default_cover)
                .showImageOnFail(R.mipmap.default_cover)
                .imageScaleType(ImageScaleType.EXACTLY)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .delayBeforeLoading(50)
                .build();
    }

    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);
}
