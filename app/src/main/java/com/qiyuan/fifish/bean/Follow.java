package com.qiyuan.fifish.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author lilin
 * created at 2016/8/3 18:05
 */
public class Follow implements Parcelable {
    public long user_id;
    public String account;
    public String nickname;
    public String avatar_url;
    public String summary;
    public int is_love;
    public int is_expert;
    public String label;
    public String expert_label;
    public String expert_info;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.user_id);
        dest.writeString(this.account);
        dest.writeString(this.nickname);
        dest.writeString(this.avatar_url);
        dest.writeString(this.summary);
        dest.writeInt(this.is_love);
        dest.writeInt(this.is_expert);
        dest.writeString(this.label);
        dest.writeString(this.expert_label);
        dest.writeString(this.expert_info);
    }

    public Follow() {
    }

    protected Follow(Parcel in) {
        this.user_id = in.readLong();
        this.account = in.readString();
        this.nickname = in.readString();
        this.avatar_url = in.readString();
        this.summary = in.readString();
        this.is_love = in.readInt();
        this.is_expert = in.readInt();
        this.label = in.readString();
        this.expert_label = in.readString();
        this.expert_info = in.readString();
    }

    public static final Parcelable.Creator<Follow> CREATOR = new Parcelable.Creator<Follow>() {
        @Override
        public Follow createFromParcel(Parcel source) {
            return new Follow(source);
        }

        @Override
        public Follow[] newArray(int size) {
            return new Follow[size];
        }
    };
}
