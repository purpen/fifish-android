package com.qiyuan.fifish.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * @author lilin
 * created at 2016/5/8 19:04
 */
public class FindFriendData implements Parcelable {
    public ArrayList<Friends> friends;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.friends);
    }

    public FindFriendData() {
    }

    protected FindFriendData(Parcel in) {
        this.friends = in.createTypedArrayList(Friends.CREATOR);
    }

    public static final Parcelable.Creator<FindFriendData> CREATOR = new Parcelable.Creator<FindFriendData>() {
        @Override
        public FindFriendData createFromParcel(Parcel source) {
            return new FindFriendData(source);
        }

        @Override
        public FindFriendData[] newArray(int size) {
            return new FindFriendData[size];
        }
    };
}
