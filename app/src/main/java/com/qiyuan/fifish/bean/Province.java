package com.qiyuan.fifish.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * @author lilin
 * created at 2016/8/4 16:40
 */
public class Province implements Parcelable {
    public int _id;
    public String city;
    public String parent_id;
    public ArrayList<City> cities;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this._id);
        dest.writeString(this.city);
        dest.writeString(this.parent_id);
        dest.writeTypedList(this.cities);
    }

    public Province() {
    }

    protected Province(Parcel in) {
        this._id = in.readInt();
        this.city = in.readString();
        this.parent_id = in.readString();
        this.cities = in.createTypedArrayList(City.CREATOR);
    }

    public static final Parcelable.Creator<Province> CREATOR = new Parcelable.Creator<Province>() {
        @Override
        public Province createFromParcel(Parcel source) {
            return new Province(source);
        }

        @Override
        public Province[] newArray(int size) {
            return new Province[size];
        }
    };
}
