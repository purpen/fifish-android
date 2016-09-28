package com.qiyuan.fifish.adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.bean.ProductsBean;
import com.qiyuan.fifish.bean.SupportProductsBean;
import com.qiyuan.fifish.network.CustomCallBack;
import com.qiyuan.fifish.network.RequestService;
import com.qiyuan.fifish.ui.activity.CommentsDetailActivity;
import com.qiyuan.fifish.ui.view.BottomSheetView;
import com.qiyuan.fifish.ui.view.roundImageView.RoundedImageView;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.JsonUtil;
import com.qiyuan.fifish.util.ToastUtils;
import com.qiyuan.fifish.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * @author lilin
 *         created at 2016/4/22 19:00
 */
public class HomeAdapter extends BaseAdapter<ProductsBean.DataBean>{
    private ImageLoader imageLoader;


    public HomeAdapter(List<ProductsBean.DataBean> list, Activity activity) {
        super(list, activity);
        this.imageLoader = ImageLoader.getInstance();
    }

//    @Override
//    public int getItemViewType(int position) {
//        return Integer.valueOf(list.get(position).kind);
//    }

//    @Override
//    public int getViewTypeCount() {
//        return super.getViewTypeCount() + 1;
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        int type = getItemViewType(position);
        final ProductsBean.DataBean item = list.get(position);
//        Integer type = Integer.valueOf(item.kind);
//        ImageHolder imageholder;
        VideoHolder videoHolder;
        if (convertView == null) {
            convertView = Util.inflateView(R.layout.item_home_video, null);
            videoHolder = new VideoHolder(convertView);
            convertView.setTag(videoHolder);
        } else {
            videoHolder = (VideoHolder) convertView.getTag();
        }
        if (TextUtils.equals(Constants.TYPE_IMAGE,item.kind)){
            videoHolder.videoView.setVisibility(View.GONE);
            videoHolder.ivCover.setVisibility(View.VISIBLE);
            imageLoader.displayImage(item.photo.file.large,videoHolder.ivCover, options);
        }else if(TextUtils.equals(Constants.TYPE_VIDEO,item.kind)){
            videoHolder.videoView.setVisibility(View.VISIBLE);
            videoHolder.ivCover.setVisibility(View.GONE);
            videoHolder.videoView.setUp(item.photo.file.large, JCVideoPlayerStandard.SCREEN_LAYOUT_LIST);
        }
        imageLoader.displayImage(item.user.avatar.large,videoHolder.riv);
        videoHolder.tvName.setText(item.user.username);
        if (item.user.summary!=null){
            videoHolder.tvDesc.setVisibility(View.VISIBLE);
            videoHolder.tvDesc.setText(item.user.summary.toString());
        }else {
            videoHolder.tvDesc.setVisibility(View.INVISIBLE);
        }
        videoHolder.tvContent.setText(item.content);
        if (item.is_love){
            videoHolder.ibtnFavorite.setImageResource(R.mipmap.icon_support);
        }else {
            videoHolder.ibtnFavorite.setImageResource(R.mipmap.icon_unsupport);
        }

        if (position==size-1){
            videoHolder.viewLine.setVisibility(View.GONE);
        }else {
            videoHolder.viewLine.setVisibility(View.VISIBLE);
        }
        videoHolder.tvTime.setText(item.created_at);
        setClickListener(videoHolder.ibtnFavorite,item);
        setClickListener(videoHolder.ibtnComment,item);
        setClickListener(videoHolder.ibtnShare,item);
        setClickListener(videoHolder.ibtnMore,item);
        return convertView;
    }

    private void setClickListener(View v,final ProductsBean.DataBean item){
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.ibtn_favorite:
                        if (item.is_love){
                            cancelSupport(view,item);
                        }else {
                            doSupport(view,item);
                        }
                        break;
                    case R.id.ibtn_comment:
                        Intent intent=new Intent(activity, CommentsDetailActivity.class);
                        intent.putExtra(CommentsDetailActivity.class.getSimpleName(),item);
                        activity.startActivity(intent);
                        break;
                    case R.id.ibtn_share:
                        //分享界面
                        break;
                    case R.id.ibtn_more:
                        ArrayList<String> strings = new ArrayList<>();
                        strings.add("google");
                        strings.add("google");
                        strings.add("google");
                        strings.add("google");
                        BottomSheetView.show(activity,new SimpleTextAdapter(activity,strings),BottomSheetView.LINEAR_LAYOUT);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void cancelSupport(final View view, final ProductsBean.DataBean item) {
        view.setEnabled(false);
        RequestService.cancelSupport(item.id,new CustomCallBack(){
            @Override
            public void onSuccess(String result) {
                view.setEnabled(true);
                SupportProductsBean response = JsonUtil.fromJson(result, SupportProductsBean.class);
                if (response.meta.status_code== Constants.HTTP_OK){
                    item.is_love=false;
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                view.setEnabled(true);
                ex.printStackTrace();
                ToastUtils.showError(R.string.request_error);
            }
        });
    }

    private void doSupport(final View view,final ProductsBean.DataBean item) {
        view.setEnabled(false);
        RequestService.doSupport(item.id,new CustomCallBack(){
            @Override
            public void onSuccess(String result) {
                view.setEnabled(true);
                SupportProductsBean response = JsonUtil.fromJson(result, SupportProductsBean.class);
                if (response.meta.status_code== Constants.HTTP_OK){
                    item.is_love=true;
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                view.setEnabled(true);
                ex.printStackTrace();
                ToastUtils.showError(R.string.request_error);
            }
        });
    }

//    static class ImageHolder {
//        ImageView iv;
//
//        public ImageHolder(View view) {
//            ButterKnife.bind(this, view);
//        }
//    }

    static class VideoHolder {
        @BindView(R.id.riv)
        RoundedImageView riv;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.videoView)
        JCVideoPlayerStandard videoView;
        @BindView(R.id.iv_cover)
        ImageView ivCover;
        @BindView(R.id.ibtn_favorite)
        ImageButton ibtnFavorite;
        @BindView(R.id.ibtn_comment)
        ImageButton ibtnComment;
        @BindView(R.id.ibtn_share)
        ImageButton ibtnShare;
        @BindView(R.id.ibtn_more)
        ImageButton ibtnMore;
        @BindView(R.id.view_line)
        View viewLine;
        public VideoHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
