package com.qiyuan.fifish.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * @author lilin
 * created at 2016/4/27 13:29
 */
public class ProvinceCityData implements Parcelable {
    public int total_rows;
    public ArrayList<Province> rows;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.total_rows);
        dest.writeTypedList(this.rows);
    }

    public ProvinceCityData() {
    }

    protected ProvinceCityData(Parcel in) {
        this.total_rows = in.readInt();
        this.rows = in.createTypedArrayList(Province.CREATOR);
    }

    public static final Parcelable.Creator<ProvinceCityData> CREATOR = new Parcelable.Creator<ProvinceCityData>() {
        @Override
        public ProvinceCityData createFromParcel(Parcel source) {
            return new ProvinceCityData(source);
        }

        @Override
        public ProvinceCityData[] newArray(int size) {
            return new ProvinceCityData[size];
        }
    };
}
