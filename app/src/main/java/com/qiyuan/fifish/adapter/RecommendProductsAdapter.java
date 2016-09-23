package com.qiyuan.fifish.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.bean.ProductsBean;
import com.qiyuan.fifish.ui.view.roundImageView.RoundedImageView;
import com.qiyuan.fifish.util.Util;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * @author lilin
 *         created at 2016/9/22 15:58
 */
public class RecommendProductsAdapter extends BaseAdapter<ProductsBean.DataBean> implements View.OnClickListener {
    private ImageLoader imageLoader;
    private static final int TYPE_IMAGE = 1;
    private static final int TYPE_VIDEO = 2;
    public RecommendProductsAdapter(List<ProductsBean.DataBean> list, Activity activity) {
        super(list, activity);
        this.imageLoader = ImageLoader.getInstance();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ProductsBean.DataBean item = list.get(position);
        Integer type = Integer.valueOf(item.kind);
        VideoHolder videoHolder;
        if (convertView == null) {
            convertView = Util.inflateView(R.layout.item_dicover_products, null);
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
                imageLoader.displayImage(item.photo.file.large, videoHolder.ivCover, options);
                break;
            default:
                break;
        }
        imageLoader.displayImage(item.user.avatar.large, videoHolder.riv);
        videoHolder.tvName.setText(item.user.username);
        videoHolder.tvZanNum.setText(item.like_count+"次赞");
        if (item.user.summary != null) {
            videoHolder.tvDesc.setVisibility(View.VISIBLE);
            videoHolder.tvDesc.setText(item.user.summary.toString());
        } else {
            videoHolder.tvDesc.setVisibility(View.INVISIBLE);
        }
        videoHolder.tvTxt.setText(item.content);
        if (position==size-1){
            videoHolder.viewLine.setVisibility(View.GONE);
        }else {
            videoHolder.viewLine.setVisibility(View.VISIBLE);
        }
        videoHolder.tvCommentNum.setText(String.format("所有%s条评论",item.comment_count));
        videoHolder.tvTime.setText(item.created_at);
        setClickListener(videoHolder.ibtnFavorite, item);
        setClickListener(videoHolder.ibtnComment, item);
        setClickListener(videoHolder.ibtnShare, item);
        setClickListener(videoHolder.ibtnMore, item);
        return convertView;
    }

    @Override
    public void onClick(final View view) {

    }

    private void setClickListener(View v, final ProductsBean.DataBean item) {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.ibtn_favorite:

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
    static class VideoHolder {
        @Bind(R.id.riv)
        RoundedImageView riv;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_desc)
        TextView tvDesc;
        @Bind(R.id.btn_focus)
        Button btnFocus;
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
        @Bind(R.id.tv_zan_num)
        TextView tvZanNum;
        @Bind(R.id.tv_txt)
        TextView tvTxt;
        @Bind(R.id.btn_more)
        TextView btnMore;
        @Bind(R.id.tv_comment_num)
        TextView tvCommentNum;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.view_line)
        View viewLine;
        public VideoHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
