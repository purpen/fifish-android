package com.qiyuan.fifish.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * @author lilin
 * created at 2016/8/4 16:40
 */
public class SearchLabelData implements Parcelable {

    public DataEntity data;

    public static class DataEntity implements Parcelable {
        public ArrayList<String> swords;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeStringList(this.swords);
        }

        public DataEntity() {
        }

        protected DataEntity(Parcel in) {
            this.swords = in.createStringArrayList();
        }

        public static final Creator<DataEntity> CREATOR = new Creator<DataEntity>() {
            @Override
            public DataEntity createFromParcel(Parcel source) {
                return new DataEntity(source);
            }

            @Override
            public DataEntity[] newArray(int size) {
                return new DataEntity[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.data, flags);
    }

    public SearchLabelData() {
    }

    protected SearchLabelData(Parcel in) {
        this.data = in.readParcelable(DataEntity.class.getClassLoader());
    }

    public static final Creator<SearchLabelData> CREATOR = new Creator<SearchLabelData>() {
        @Override
        public SearchLabelData createFromParcel(Parcel source) {
            return new SearchLabelData(source);
        }

        @Override
        public SearchLabelData[] newArray(int size) {
            return new SearchLabelData[size];
        }
    };
}
