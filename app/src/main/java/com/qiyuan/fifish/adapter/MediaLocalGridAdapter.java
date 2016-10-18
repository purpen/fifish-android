package com.qiyuan.fifish.adapter;

import android.content.Context;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.album.OptionAlbumActivity;
import com.bean.Image;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.util.MySQLiteOpenHelper;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 2016/9/30.
 */
public class MediaLocalGridAdapter extends android.widget.BaseAdapter {

    private static final int TYPE_CAMERA = 0;
    private static final int TYPE_NORMAL = 1;

    private Context mContext;

    private LayoutInflater mInflater;
    private boolean showCamera = true;
    private boolean showSelectIndicator = true;
    private boolean isVideo=false;
    private MySQLiteOpenHelper dbHelper;

    private List<Image> mImages = new ArrayList<>();
    private List<Image> mSelectedImages = new ArrayList<>();

    final int mGridWidth;

    public MediaLocalGridAdapter(Context context, boolean showCamera, int column){
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.showCamera = showCamera;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            Point size = new Point();
            wm.getDefaultDisplay().getSize(size);
            width = size.x;
        }else{
            width = wm.getDefaultDisplay().getWidth();
        }
        mGridWidth = width / column;
    }
    /**
     * 显示选择指示器
     * @param b
     */
    public void showSelectIndicator(boolean b) {
        showSelectIndicator = b;
        notifyDataSetChanged();
    }

    public void setShowCamera(boolean b){
        if(showCamera == b) return;

        showCamera = b;
        notifyDataSetChanged();
    }

    public boolean isShowCamera(){
        return showCamera;
    }

    /**
     * 选择某个图片，改变选择状态
     * @param image
     */
    public void select(Image image) {
        if(mSelectedImages.contains(image)){
            mSelectedImages.remove(image);
        }else{
            mSelectedImages.add(image);
        }
        notifyDataSetChanged();
    }

    /**
     * 通过图片路径设置默认选择
     * @param resultList
     */
    public void setDefaultSelected(ArrayList<String> resultList) {
        for(String path : resultList){
            Image image = getImageByPath(path);
            if(image != null){
                mSelectedImages.add(image);
            }
        }
        if(mSelectedImages.size() > 0){
            notifyDataSetChanged();
        }
    }

    private Image getImageByPath(String path){
        if(mImages != null && mImages.size()>0){
            /**
             * foreach的语句格式：
             for(元素类型t 元素变量x : 遍历对象obj){
             引用了x的java语句;
             }
             */
            for(Image image : mImages){
                if(image.path.equalsIgnoreCase(path)){
                    return image;
                }
            }
        }
        return null;
    }

    /**
     * 设置数据集
     * @param images
     */
    public void setData(List<Image> images, MySQLiteOpenHelper dbHelper) {
       this.dbHelper=dbHelper;
        mSelectedImages.clear();

        if(images != null && images.size()>0){
            mImages = images;
        }else{
            mImages.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if(showCamera){
            return position==0 ? TYPE_CAMERA : TYPE_NORMAL;
        }
        return TYPE_NORMAL;
    }

    @Override
    public int getCount() {
        return showCamera ? mImages.size()+1 : mImages.size();
    }

    @Override
    public Image getItem(int i) {
        if(showCamera){
            if(i == 0){
                return null;
            }
            return mImages.get(i-1);
        }else{
            return mImages.get(i);
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

//        if(isShowCamera()){
//            if(i == 0){
//                view = mInflater.inflate(R.layout.mis_list_item_camera, viewGroup, false);
//                return view;
//            }
//        }

        ViewHolder holder;
        if(view == null){
            view = mInflater.inflate(R.layout.item_imagegrid_layout, viewGroup, false);
            holder = new ViewHolder(view);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        if(holder != null) {
            holder.bindData(getItem(i));
        }

        return view;
    }

    class ViewHolder {
        ImageView image;
        ImageView indicator;
        View mask;
        ImageView imageOverlay;
        TextView videoDuration;

        ViewHolder(View view){
            image = (ImageView) view.findViewById(R.id.image);
            indicator = (ImageView) view.findViewById(R.id.checkmark);
            mask = view.findViewById(R.id.mask);
            imageOverlay = (ImageView) view.findViewById(R.id.image_overlay);
            videoDuration = (TextView) view.findViewById(R.id.tv_video_duration);
            view.setTag(this);
        }

        void bindData(final Image data){
            if(data == null) return;
            // 处理单选和多选状态
            if(showSelectIndicator){
                indicator.setVisibility(View.VISIBLE);
                if(mSelectedImages.contains(data)){
                    // 设置选中状态
                    indicator.setImageResource(R.mipmap.checked);
                    mask.setVisibility(View.VISIBLE);
                }else{
                    // 未选择
                    indicator.setImageResource(R.mipmap.unchecked);
                    mask.setVisibility(View.GONE);
                }
            }else{
                indicator.setVisibility(View.GONE);
                mask.setVisibility(View.GONE);
            }
            File imageFile = new File(data.path);
            Uri uri = Uri.withAppendedPath(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, data.id + "");
            if (imageFile.exists()) {
                isVideo=dbHelper.queryIsVideo(data);//查询每一张图片是属于视频还是图片
                showImageWay(isVideo,data);
            }else{
                image.setImageResource(R.mipmap.default_album_option);
            }
        }
        /**
         * 显示图片,当有大量图片时，URI显示图片比直接根据PATH显示更慢一些，所以能用PATH的就用PATH显示
         * @param isVideo
         * @param data
         */
        private void showImageWay(boolean isVideo, Image data) {
            showVideoIcon(isVideo);//是视频，则显示视频时长,以及在图上显示一个三角形的标志为视频的覆盖图标
            int hour = Integer.parseInt((data.videoDuration / 3600) + "");
            int min = Integer.parseInt((data.videoDuration  - hour * 3600) / 60 + "");
            int sec = Integer.parseInt((data.videoDuration  - hour * 3600 - min * 60) + "");
            videoDuration.setText(String.format("%02d", hour) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec));
//          Uri ss = Uri.withAppendedPath(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, data.cover.id + "");
            if (isVideo) {
                Picasso.with(mContext)
//                        .load(new File(data.cover.path))
                        .load(Uri.withAppendedPath(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, data.id + ""))
                        .placeholder(R.mipmap.default_album_option)
                        .resizeDimen(R.dimen.dp72, R.dimen.dp72)
                        .centerCrop()
                        .into(image);
            } else {
                Picasso.with(mContext)
                        .load(new File(data.path))
                        .placeholder(R.mipmap.default_album_option)
                        .resizeDimen(R.dimen.dp72, R.dimen.dp72)
                        .centerCrop()
                        .into(image);
            }
        }
        /**
         * 是视频，则显示视频时长,以及在图上显示一个三角形的标志为视频的覆盖图标
         * @param isVideo
         */
        private void showVideoIcon(boolean isVideo) {
            if (isVideo) {
                videoDuration.setVisibility(View.VISIBLE);
                imageOverlay.setVisibility(View.VISIBLE);
            } else {
                videoDuration.setVisibility(View.GONE);
                imageOverlay.setVisibility(View.GONE);
            }
        }
    }

}