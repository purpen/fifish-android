package com.qiyuan.fifish.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author lilin
 * created at 2016/8/3 18:04
 */
public class Products implements Parcelable {
    public String _id;
    public String title;
    public String address;
    public String cover_url;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this._id);
        dest.writeString(this.title);
        dest.writeString(this.address);
        dest.writeString(this.cover_url);
    }

    public Products() {
    }

    protected Products(Parcel in) {
        this._id = in.readString();
        this.title = in.readString();
        this.address = in.readString();
        this.cover_url = in.readString();
    }

    public static final Creator<Products> CREATOR = new Creator<Products>() {
        @Override
        public Products createFromParcel(Parcel source) {
            return new Products(source);
        }

        @Override
        public Products[] newArray(int size) {
            return new Products[size];
        }
    };
}
