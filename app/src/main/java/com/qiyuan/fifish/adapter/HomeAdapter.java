package com.qiyuan.fifish.adapter;

import android.app.Activity;
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
import com.qiyuan.fifish.ui.view.roundImageView.RoundedImageView;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.JsonUtil;
import com.qiyuan.fifish.util.ToastUtils;
import com.qiyuan.fifish.util.Util;

import org.xutils.http.request.UriRequest;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * @author lilin
 *         created at 2016/4/22 19:00
 */
public class HomeAdapter extends BaseAdapter<ProductsBean.DataBean> implements View.OnClickListener {
    private ImageLoader imageLoader;
    private static final int TYPE_IMAGE = 1;
    private static final int TYPE_VIDEO = 2;

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
        Integer type = Integer.valueOf(item.kind);
//        ImageHolder imageholder;
        VideoHolder videoHolder;
        if (convertView == null) {
            convertView = Util.inflateView(R.layout.item_home_video, null);
            videoHolder = new VideoHolder(convertView);
            convertView.setTag(videoHolder);
        } else {
            videoHolder = (VideoHolder) convertView.getTag();
        }
        switch (type) {
            case TYPE_VIDEO:
                videoHolder.videoView.setVisibility(View.VISIBLE);
                videoHolder.ivCover.setVisibility(View.GONE);
                videoHolder.videoView.setUp(item.photo.file.large, JCVideoPlayerStandard.SCREEN_LAYOUT_LIST);
                break;
            case TYPE_IMAGE:
                videoHolder.videoView.setVisibility(View.GONE);
                videoHolder.ivCover.setVisibility(View.VISIBLE);
                imageLoader.displayImage(item.photo.file.large,videoHolder.ivCover, options);
                break;
            default:
                break;
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

    @Override
    public void onClick(final View view) {

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

                        break;
                    case R.id.ibtn_share:

                        break;
                    case R.id.ibtn_more:

                        break;

                    default:
                        break;
                }
            }
        });
    }

    private void cancelSupport(final View view, final ProductsBean.DataBean item) {
        RequestService.cancelSupport(item.id,new CustomCallBack(){
            @Override
            public void beforeRequest(UriRequest request) throws Throwable {
                view.setEnabled(false);
            }

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
        RequestService.doSupport(item.id,new CustomCallBack(){
            @Override
            public void beforeRequest(UriRequest request) throws Throwable {
                view.setEnabled(false);
            }

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
        @Bind(R.id.riv)
        RoundedImageView riv;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_desc)
        TextView tvDesc;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.tv_content)
        TextView tvContent;
        @Bind(R.id.videoView)
        JCVideoPlayerStandard videoView;
        @Bind(R.id.iv_cover)
        ImageView ivCover;
        @Bind(R.id.ibtn_favorite)
        ImageButton ibtnFavorite;
        @Bind(R.id.ibtn_comment)
        ImageButton ibtnComment;
        @Bind(R.id.ibtn_share)
        ImageButton ibtnShare;
        @Bind(R.id.ibtn_more)
        ImageButton ibtnMore;
        @Bind(R.id.view_line)
        View viewLine;
        public VideoHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
