package com.qiyuan.fifish.adapter;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bean.Folder;
import com.qiyuan.fifish.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FolderAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private boolean isVideo = false;
    private List<Folder> mFolders = new ArrayList<>();

    int mImageSize;

    int lastSelected = 0;

    public FolderAdapter(Context context, boolean isVideo) {
        this.isVideo = isVideo;
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mImageSize = mContext.getResources().getDimensionPixelOffset(R.dimen.dp72);
    }

    /**
     * 设置数据集
     *
     * @param folders
     */
    public void setData(List<Folder> folders) {
        if (folders != null && folders.size() > 0) {
            mFolders = folders;
        } else {
            mFolders.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mFolders.size() + 1;
    }

    @Override
    public Folder getItem(int i) {
        if (i == 0) return null;
        return mFolders.get(i - 1);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.item_folder, viewGroup, false);
            holder = new ViewHolder(view);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if (holder != null) {
            if (i == 0) {
                holder.name.setText(R.string.all);
                holder.path.setText("/sdcard");
                holder.size.setText(String.format("%d%s",
                        getTotalImageSize(), mContext.getResources().getString(R.string.shot)));
                if (mFolders.size() > 0) {
                    Folder f = mFolders.get(0);
                    if (f != null) {
                        holder.showImageWay(isVideo, f);//显示图片的方式，视频则用URI显示，图片则用PATH显示
                    } else {
                        holder.cover.setImageResource(R.mipmap.default_album_option);
                    }
                }
            } else {
                holder.bindData(getItem(i));
            }

            //显示被勾选Item
            if (lastSelected == i) {
                holder.indicator.setVisibility(View.VISIBLE);
            } else {
                holder.indicator.setVisibility(View.VISIBLE);
            }
        }
        return view;
    }

    private int getTotalImageSize() {
        int result = 0;
        if (mFolders != null && mFolders.size() > 0) {
            for (Folder f : mFolders) {
                result += f.images.size();
            }
        }
        return result;
    }

    //勾选Item
    public void setSelectIndex(int i) {
        if (lastSelected == i) return;
        lastSelected = i;
        notifyDataSetChanged();
    }

    public int getSelectIndex() {
        return lastSelected;
    }

    class ViewHolder {
        ImageView cover;
        TextView name;
        TextView path;
        TextView size;
        ImageView indicator, imageOverlay;

        ViewHolder(View view) {
            cover = (ImageView) view.findViewById(R.id.cover);
            name = (TextView) view.findViewById(R.id.name);
            path = (TextView) view.findViewById(R.id.path);
            size = (TextView) view.findViewById(R.id.size);
            indicator = (ImageView) view.findViewById(R.id.indicator);
            imageOverlay = (ImageView) view.findViewById(R.id.image_overlay);
            view.setTag(this);
        }

        void bindData(Folder data) {
            if (data == null) {
                return;
            }
            name.setText(data.name);
            path.setText(data.path);
            if (data.images != null) {
                size.setText(String.format("%d%s", data.images.size(), mContext.getResources().getString(R.string.shot)));
            } else {
                size.setText("*" + mContext.getResources().getString(R.string.shot));
            }
            if (data.cover != null) {
                showImageWay(isVideo,data);//显示图片的方式，视频则用URI显示，图片则用PATH显示

            } else {
                cover.setImageResource(R.mipmap.default_album_option);
            }
        }

        /**
         * 显示图片,当有大量图片时，URI显示图片比直接根据PATH显示更慢一些，所以能用PATH的就用PATH显示
         * @param isVideo
         * @param data
         */
        private void showImageWay(boolean isVideo, Folder data) {
            showVideoIcon(isVideo);//是视频，则在图上显示一个三角形的标志为视频的覆盖图标
//          Uri ss = Uri.withAppendedPath(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, data.cover.id + "");
            if (isVideo) {
                Picasso.with(mContext)
//                        .load(new File(data.cover.path))
                        .load(Uri.withAppendedPath(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, data.cover.id + ""))
                        .placeholder(R.mipmap.default_album_option)
                        .resizeDimen(R.dimen.dp72, R.dimen.dp72)
                        .centerCrop()
                        .into(cover);
            } else {
                Picasso.with(mContext)
                        .load(new File(data.cover.path))
                        .placeholder(R.mipmap.default_album_option)
                        .resizeDimen(R.dimen.dp72, R.dimen.dp72)
                        .centerCrop()
                        .into(cover);
            }
        }

        /**
         * 是视频，则在图上显示一个三角形的标志为视频的覆盖图标
         * @param isVideo
         */
        private void showVideoIcon(boolean isVideo) {
            if (isVideo) {
                imageOverlay.setVisibility(View.VISIBLE);
            } else {
                imageOverlay.setVisibility(View.GONE);
            }
        }
    }
}
