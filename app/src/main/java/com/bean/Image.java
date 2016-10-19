package com.bean;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * 图片实体
 * Created by Nereo on 2015/4/7.
 */
public class Image implements Serializable{
    public int id;
    public String path;
    public String name;
    public long time;
    public boolean isVideo;
    public long videoDuration;

    public Image(int id,String path, String name, long time,boolean isVideo,long videoDuration){
        this.id=id;
        this.isVideo=isVideo;
        this.path = path;
        this.name = name;
        this.time = time;
        this.videoDuration=videoDuration;
    }

    @Override
    public boolean equals(Object o) {
        try {
            Image other = (Image) o;
            return TextUtils.equals(this.path, other.path);
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        return super.equals(o);
    }
}
