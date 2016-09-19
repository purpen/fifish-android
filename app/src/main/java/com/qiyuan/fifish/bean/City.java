package com.qiyuan.fifish.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author lilin
 * created at 2016/8/4 16:40
 */
public class City implements Parcelable {
    public int _id;
    public String city;
    public int parent_id;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this._id);
        dest.writeString(this.city);
        dest.writeInt(this.parent_id);
    }

    public City() {
    }

    protected City(Parcel in) {
        this._id = in.readInt();
        this.city = in.readString();
        this.parent_id = in.readInt();
    }

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel source) {
            return new City(source);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };
}
