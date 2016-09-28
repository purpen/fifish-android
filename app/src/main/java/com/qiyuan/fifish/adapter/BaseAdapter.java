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
    protected int size;

    public BaseAdapter(List<T> list, Activity activity) {
        this.list = list;
        this.size = list.size();
        this.activity = activity;
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.default_background_750_1334)
                .showImageForEmptyUri(R.mipmap.default_background_750_1334)
                .showImageOnFail(R.mipmap.default_background_750_1334)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        }
        return size;
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
