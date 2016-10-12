package com.qiyuan.fifish.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * @author lilin
 * created at 2016/9/21 20:46
 */
public class TagsBean implements Parcelable {

    /**
     * message : Success.
     * status_code : 200
     * pagination : {"total":5,"count":5,"per_page":10,"current_page":1,"total_pages":1,"links":[]}
     */

    public MetaBean meta;
    /**
     * id : 5
     * name : 大哭
     * display_name : 大哭啊
     */

    public ArrayList<DataBean> data;

    public static class MetaBean implements Parcelable {
        public String message;
        public int status_code;

        public MetaBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.message);
            dest.writeInt(this.status_code);
        }

        protected MetaBean(Parcel in) {
            this.message = in.readString();
            this.status_code = in.readInt();
        }

        public static final Creator<MetaBean> CREATOR = new Creator<MetaBean>() {
            @Override
            public MetaBean createFromParcel(Parcel source) {
                return new MetaBean(source);
            }

            @Override
            public MetaBean[] newArray(int size) {
                return new MetaBean[size];
            }
        };
    }

    public static class DataBean implements Parcelable {
        public int id;
        public String name;
        public String display_name;

        public DataBean() {
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.name);
            dest.writeString(this.display_name);
        }

        protected DataBean(Parcel in) {
            this.id = in.readInt();
            this.name = in.readString();
            this.display_name = in.readString();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }

    public TagsBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.meta, flags);
        dest.writeTypedList(this.data);
    }

    protected TagsBean(Parcel in) {
        this.meta = in.readParcelable(MetaBean.class.getClassLoader());
        this.data = in.createTypedArrayList(DataBean.CREATOR);
    }

    public static final Creator<TagsBean> CREATOR = new Creator<TagsBean>() {
        @Override
        public TagsBean createFromParcel(Parcel source) {
            return new TagsBean(source);
        }

        @Override
        public TagsBean[] newArray(int size) {
            return new TagsBean[size];
        }
    };
}
