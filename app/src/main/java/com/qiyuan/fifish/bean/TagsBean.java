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
     */

    public MetaEntity meta;
    /**
     * id : 1
     * name : 科技
     * display_name : 科技
     * cover : {"id":145,"filepath":"photo/161018/c9dc008d403eae0e33b4a97eb8412c0f.jpg","size":112537,"width":1248,"height":896,"duration":0,"kind":1,"file":{"srcfile":"http://oe5tkubcj.bkt.clouddn.com/photo/161018/c9dc008d403eae0e33b4a97eb8412c0f.jpg","small":"http://oe5tkubcj.bkt.clouddn.com/photo/161018/c9dc008d403eae0e33b4a97eb8412c0f.jpg!cvxsm","large":"http://oe5tkubcj.bkt.clouddn.com/photo/161018/c9dc008d403eae0e33b4a97eb8412c0f.jpg!cvxlg","thumb":"http://oe5tkubcj.bkt.clouddn.com/photo/161018/c9dc008d403eae0e33b4a97eb8412c0f.jpg!psq"}}
     */

    public ArrayList<DataEntity> data;

    public static class MetaEntity implements Parcelable {
        public String message;
        public int status_code;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.message);
            dest.writeInt(this.status_code);
        }

        public MetaEntity() {
        }

        protected MetaEntity(Parcel in) {
            this.message = in.readString();
            this.status_code = in.readInt();
        }

        public static final Creator<MetaEntity> CREATOR = new Creator<MetaEntity>() {
            @Override
            public MetaEntity createFromParcel(Parcel source) {
                return new MetaEntity(source);
            }

            @Override
            public MetaEntity[] newArray(int size) {
                return new MetaEntity[size];
            }
        };
    }

    public static class DataEntity implements Parcelable {
        public int id;
        public String name;
        public String display_name;
        /**
         * id : 145
         * filepath : photo/161018/c9dc008d403eae0e33b4a97eb8412c0f.jpg
         * size : 112537
         * width : 1248
         * height : 896
         * duration : 0
         * kind : 1
         * file : {"srcfile":"http://oe5tkubcj.bkt.clouddn.com/photo/161018/c9dc008d403eae0e33b4a97eb8412c0f.jpg","small":"http://oe5tkubcj.bkt.clouddn.com/photo/161018/c9dc008d403eae0e33b4a97eb8412c0f.jpg!cvxsm","large":"http://oe5tkubcj.bkt.clouddn.com/photo/161018/c9dc008d403eae0e33b4a97eb8412c0f.jpg!cvxlg","thumb":"http://oe5tkubcj.bkt.clouddn.com/photo/161018/c9dc008d403eae0e33b4a97eb8412c0f.jpg!psq"}
         */

        public CoverEntity cover;

        public static class CoverEntity implements Parcelable {
            public int id;
            public String filepath;
            public int size;
            public int width;
            public int height;
            public int duration;
            public int kind;
            /**
             * srcfile : http://oe5tkubcj.bkt.clouddn.com/photo/161018/c9dc008d403eae0e33b4a97eb8412c0f.jpg
             * small : http://oe5tkubcj.bkt.clouddn.com/photo/161018/c9dc008d403eae0e33b4a97eb8412c0f.jpg!cvxsm
             * large : http://oe5tkubcj.bkt.clouddn.com/photo/161018/c9dc008d403eae0e33b4a97eb8412c0f.jpg!cvxlg
             * thumb : http://oe5tkubcj.bkt.clouddn.com/photo/161018/c9dc008d403eae0e33b4a97eb8412c0f.jpg!psq
             */

            public FileEntity file;

            public static class FileEntity implements Parcelable {
                public String srcfile;
                public String small;
                public String large;
                public String thumb;

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.srcfile);
                    dest.writeString(this.small);
                    dest.writeString(this.large);
                    dest.writeString(this.thumb);
                }

                public FileEntity() {
                }

                protected FileEntity(Parcel in) {
                    this.srcfile = in.readString();
                    this.small = in.readString();
                    this.large = in.readString();
                    this.thumb = in.readString();
                }

                public static final Creator<FileEntity> CREATOR = new Creator<FileEntity>() {
                    @Override
                    public FileEntity createFromParcel(Parcel source) {
                        return new FileEntity(source);
                    }

                    @Override
                    public FileEntity[] newArray(int size) {
                        return new FileEntity[size];
                    }
                };
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.id);
                dest.writeString(this.filepath);
                dest.writeInt(this.size);
                dest.writeInt(this.width);
                dest.writeInt(this.height);
                dest.writeInt(this.duration);
                dest.writeInt(this.kind);
                dest.writeParcelable(this.file, flags);
            }

            public CoverEntity() {
            }

            protected CoverEntity(Parcel in) {
                this.id = in.readInt();
                this.filepath = in.readString();
                this.size = in.readInt();
                this.width = in.readInt();
                this.height = in.readInt();
                this.duration = in.readInt();
                this.kind = in.readInt();
                this.file = in.readParcelable(FileEntity.class.getClassLoader());
            }

            public static final Creator<CoverEntity> CREATOR = new Creator<CoverEntity>() {
                @Override
                public CoverEntity createFromParcel(Parcel source) {
                    return new CoverEntity(source);
                }

                @Override
                public CoverEntity[] newArray(int size) {
                    return new CoverEntity[size];
                }
            };
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
            dest.writeParcelable(this.cover, flags);
        }

        public DataEntity() {
        }

        protected DataEntity(Parcel in) {
            this.id = in.readInt();
            this.name = in.readString();
            this.display_name = in.readString();
            this.cover = in.readParcelable(CoverEntity.class.getClassLoader());
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
        dest.writeParcelable(this.meta, flags);
        dest.writeList(this.data);
    }

    public TagsBean() {
    }

    protected TagsBean(Parcel in) {
        this.meta = in.readParcelable(MetaEntity.class.getClassLoader());
        this.data = new ArrayList<DataEntity>();
        in.readList(this.data, DataEntity.class.getClassLoader());
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
