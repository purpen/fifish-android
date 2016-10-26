package com.qiyuan.fifish.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author lilin
 *         created at 2016/4/22 1746
 */
public class FocusFans implements Parcelable {
    public int _id;
    public int user_id;
    public int follow_id;
    public int group_id;
    public int type;
    public boolean focus_flag;
    public int is_read;
    public Follow follows;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this._id);
        dest.writeInt(this.user_id);
        dest.writeInt(this.follow_id);
        dest.writeInt(this.group_id);
        dest.writeInt(this.type);
        dest.writeByte(this.focus_flag ? (byte) 1 : (byte) 0);
        dest.writeInt(this.is_read);
        dest.writeParcelable(this.follows, flags);
    }

    public FocusFans() {
    }

    protected FocusFans(Parcel in) {
        this._id = in.readInt();
        this.user_id = in.readInt();
        this.follow_id = in.readInt();
        this.group_id = in.readInt();
        this.type = in.readInt();
        this.focus_flag = in.readByte() != 0;
        this.is_read = in.readInt();
        this.follows = in.readParcelable(Follow.class.getClassLoader());
    }

    public static final Creator<FocusFans> CREATOR = new Creator<FocusFans>() {
        @Override
        public FocusFans createFromParcel(Parcel source) {
            return new FocusFans(source);
        }

        @Override
        public FocusFans[] newArray(int size) {
            return new FocusFans[size];
        }
    };
}
