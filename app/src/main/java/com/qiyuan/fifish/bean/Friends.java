package com.qiyuan.fifish.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * @author lilin
 * created at 2016/8/3 17:30
 */
public class Friends implements Parcelable {
    public long _id;
    public String nickname;
    public String sex;
    public String medium_avatar_url;
    public int is_love;
    public ArrayList<Products> scene_sight;
    public String summary;
    public ArrayList<String> areas;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this._id);
        dest.writeString(this.nickname);
        dest.writeString(this.sex);
        dest.writeString(this.medium_avatar_url);
        dest.writeInt(this.is_love);
        dest.writeList(this.scene_sight);
        dest.writeString(this.summary);
        dest.writeStringList(this.areas);
    }

    public Friends() {
    }

    protected Friends(Parcel in) {
        this._id = in.readLong();
        this.nickname = in.readString();
        this.sex = in.readString();
        this.medium_avatar_url = in.readString();
        this.is_love = in.readInt();
        this.scene_sight = new ArrayList<Products>();
        in.readList(this.scene_sight, Products.class.getClassLoader());
        this.summary = in.readString();
        this.areas = in.createStringArrayList();
    }

    public static final Parcelable.Creator<Friends> CREATOR = new Parcelable.Creator<Friends>() {
        @Override
        public Friends createFromParcel(Parcel source) {
            return new Friends(source);
        }

        @Override
        public Friends[] newArray(int size) {
            return new Friends[size];
        }
    };
}
