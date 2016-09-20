package com.qiyuan.fifish.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * @author lilin
 *         created at 2016/5/5 23:51
 */
public class SystemNoticeData implements Parcelable {
    public int total_rows;
    public ArrayList<SystemNoticeItem> rows;
    public long current_user_id;

    public static class SystemNoticeItem implements Parcelable {
        public String title;
        public String content;
        public String created_at;
        public String url;
        public String cover_url;
        public int state;
        public int evt;
        public int send_count;
        public boolean is_unread;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.title);
            dest.writeString(this.content);
            dest.writeString(this.created_at);
            dest.writeString(this.url);
            dest.writeString(this.cover_url);
            dest.writeInt(this.state);
            dest.writeInt(this.evt);
            dest.writeInt(this.send_count);
            dest.writeByte(this.is_unread ? (byte) 1 : (byte) 0);
        }

        public SystemNoticeItem() {
        }

        protected SystemNoticeItem(Parcel in) {
            this.title = in.readString();
            this.content = in.readString();
            this.created_at = in.readString();
            this.url = in.readString();
            this.cover_url = in.readString();
            this.state = in.readInt();
            this.evt = in.readInt();
            this.send_count = in.readInt();
            this.is_unread = in.readByte() != 0;
        }

        public static final Creator<SystemNoticeItem> CREATOR = new Creator<SystemNoticeItem>() {
            @Override
            public SystemNoticeItem createFromParcel(Parcel source) {
                return new SystemNoticeItem(source);
            }

            @Override
            public SystemNoticeItem[] newArray(int size) {
                return new SystemNoticeItem[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.total_rows);
        dest.writeList(this.rows);
        dest.writeLong(this.current_user_id);
    }

    public SystemNoticeData() {
    }

    protected SystemNoticeData(Parcel in) {
        this.total_rows = in.readInt();
        this.rows = new ArrayList<SystemNoticeItem>();
        in.readList(this.rows, SystemNoticeItem.class.getClassLoader());
        this.current_user_id = in.readLong();
    }

    public static final Creator<SystemNoticeData> CREATOR = new Creator<SystemNoticeData>() {
        @Override
        public SystemNoticeData createFromParcel(Parcel source) {
            return new SystemNoticeData(source);
        }

        @Override
        public SystemNoticeData[] newArray(int size) {
            return new SystemNoticeData[size];
        }
    };
}
