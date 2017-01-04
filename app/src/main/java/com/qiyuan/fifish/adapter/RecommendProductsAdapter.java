package com.qiyuan.fifish.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.bean.ProductsBean;
import com.qiyuan.fifish.bean.SearchProductsBean;
import com.qiyuan.fifish.bean.SupportProductsBean;
import com.qiyuan.fifish.bean.UserProfile;
import com.qiyuan.fifish.network.CustomCallBack;
import com.qiyuan.fifish.network.RequestService;
import com.qiyuan.fifish.ui.activity.CommentsDetailActivity;
import com.qiyuan.fifish.ui.activity.ShareDialogActivity;
import com.qiyuan.fifish.ui.activity.TagActivity;
import com.qiyuan.fifish.ui.fragment.ShowImageFragment;
import com.qiyuan.fifish.ui.view.BottomSheetView;
import com.qiyuan.fifish.ui.view.labelview.AutoLabelUI;
import com.qiyuan.fifish.ui.view.labelview.Label;
import com.qiyuan.fifish.ui.view.roundImageView.RoundedImageView;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.JsonUtil;
import com.qiyuan.fifish.util.ToastUtils;
import com.qiyuan.fifish.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * @author lilin
 *         created at 2016/9/22 15:58
 */
public class RecommendProductsAdapter extends BaseAdapter<ProductsBean.DataEntity> {
    private ImageLoader imageLoader;
    private static final int TYPE_IMAGE = 1;
    private static final int TYPE_VIDEO = 2;

    public RecommendProductsAdapter(List<ProductsBean.DataEntity> list, Activity activity) {
        super(list, activity);
        this.imageLoader = ImageLoader.getInstance();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ProductsBean.DataEntity item = list.get(position);
        Integer type = Integer.valueOf(item.kind);
        VideoHolder videoHolder;
        if (convertView == null) {
            convertView = Util.inflateView(activity, R.layout.item_dicover_products, null);
            videoHolder = new VideoHolder(convertView);
            convertView.setTag(videoHolder);
        } else {
            videoHolder = (VideoHolder) convertView.getTag();
        }
        switch (type) {
            case TYPE_VIDEO:
                videoHolder.videoView.setVisibility(View.VISIBLE);
                videoHolder.ivCover.setVisibility(View.GONE);
                videoHolder.videoView.thumbImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                if (item.cover != null) {
                    videoHolder.videoView.setUp(item.cover.file.srcfile, JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, "");
                    ImageLoader.getInstance().displayImage(item.cover.file.large, videoHolder.videoView.thumbImageView, options);
                }
                break;
            case TYPE_IMAGE:
                videoHolder.videoView.setVisibility(View.GONE);
                videoHolder.ivCover.setVisibility(View.VISIBLE);
                if (item.cover != null)
                    imageLoader.displayImage(item.cover.file.large, videoHolder.ivCover, options);
                break;
            default:
                break;
        }
        imageLoader.displayImage(item.user.avatar.large, videoHolder.riv, options);
        videoHolder.tvName.setText(item.user.username);
        if (item.like_count > 0) {
            videoHolder.tvZanNum.setVisibility(View.VISIBLE);
            videoHolder.tvZanNum.setText(String.valueOf(item.like_count));
        } else {
            videoHolder.tvZanNum.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(item.address) && !TextUtils.equals("null", item.address)) {
            videoHolder.tvDesc.setVisibility(View.VISIBLE);
            videoHolder.tvDesc.setText(item.address);
        } else {
            videoHolder.tvDesc.setVisibility(View.INVISIBLE);
        }

        videoHolder.labelView.clear();
        for (ProductsBean.DataEntity.TagsEntity tag : item.tags) {
            videoHolder.labelView.addLabel("#" + tag.name);
        }
        videoHolder.tvTxt.setText(item.content);
        if (item.is_love) {
            videoHolder.ibtnFavorite.setImageResource(R.mipmap.icon_support);
        } else {
            videoHolder.ibtnFavorite.setImageResource(R.mipmap.icon_unsupport);
        }
        if (position == list.size() - 1) {
            videoHolder.viewLine.setVisibility(View.GONE);
        } else {
            videoHolder.viewLine.setVisibility(View.VISIBLE);
        }
        videoHolder.tvCommentNum.setText(String.format("所有%s条评论", item.comment_count));
        videoHolder.tvTime.setText(item.created_at);
        if (TextUtils.equals(item.user.id, UserProfile.getUserId())) {
            videoHolder.btnFocus.setVisibility(View.GONE);
        } else {
            videoHolder.btnFocus.setVisibility(View.VISIBLE);
        }
        if (item.is_follow) {
            setFocusBtnStyle(videoHolder.btnFocus, R.dimen.dp8, R.string.focused, R.mipmap.focused, R.color.color_2288ff, R.drawable.shape_focus);
        } else {
            setFocusBtnStyle(videoHolder.btnFocus, R.dimen.dp15, R.string.focus, R.mipmap.unfocus, R.color.color_7f8fa2, R.drawable.shape_unfocus);
        }
        videoHolder.ivCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putString(ShowImageFragment.class.getSimpleName(),item.cover.file.srcfile);
                ShowImageFragment.newInstance(bundle).show(activity.getFragmentManager(),ShowImageFragment.class.getSimpleName());
            }
        });
        setClickListener(videoHolder.ibtnFavorite, item);
        setClickListener(videoHolder.ibtnComment, item);
        setClickListener(videoHolder.ibtnShare, item);
        setClickListener(videoHolder.ibtnMore, item);
        setClickListener(videoHolder.btnFocus, item);
        videoHolder.labelView.setOnLabelClickListener(new AutoLabelUI.OnLabelClickListener() {
            @Override
            public void onClickLabel(Label labelClicked) {
                Intent intent = new Intent(activity, TagActivity.class);
                String txt = labelClicked.getText().substring(1);
                if (!TextUtils.isEmpty(txt)) {
                    intent.putExtra(TagActivity.class.getSimpleName(), txt);
                    activity.startActivity(intent);
                }
            }
        });
//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(activity, CommentsDetailActivity.class);
//                intent.putExtra(CommentsDetailActivity.class.getSimpleName(),item);
//                activity.startActivity(intent);
//            }
//        });
        return convertView;
    }

    private void setClickListener(View v, final ProductsBean.DataEntity item) {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.ibtn_favorite:
                        if (item.is_love) {
                            cancelSupport(view, item);
                        } else {
                            doSupport(view, item);
                        }
                        break;
                    case R.id.ibtn_comment:
                        final Intent intent = new Intent(activity, CommentsDetailActivity.class);
                        intent.putExtra(CommentsDetailActivity.class.getSimpleName(), item);
                        activity.startActivity(intent);
                        break;
                    case R.id.ibtn_share:
                        Intent intent1 = new Intent(activity, ShareDialogActivity.class);
                        intent1.putExtra(ShareDialogActivity.class.getSimpleName(),item);
                        activity.startActivity(intent1);
                        break;
                    case R.id.ibtn_more:
                        final BottomSheetDialog dialog = new BottomSheetDialog(activity);
                        View bottomView = Util.inflateView(R.layout.view_bottom_list, null);
                        bottomView.findViewById(R.id.tv_title).setVisibility(View.GONE);
                        RecyclerView recyclerView = (RecyclerView) bottomView.findViewById(R.id.bottom_sheet_recycler_view);
                        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                        String[] stringArray = activity.getResources().getStringArray(R.array.dialog_bottom);
                        SimpleTextAdapter adapter = new SimpleTextAdapter(activity, Arrays.asList(stringArray));
                        recyclerView.setAdapter(adapter);
                        adapter.setOnItemClickListener(new SimpleTextAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                switch (position) {
                                    case 0: //举报
                                        final BottomSheetDialog dialogReport = new BottomSheetDialog(activity);
                                        View bottomView = Util.inflateView(R.layout.view_bottom_list, null);
                                        bottomView.findViewById(R.id.tv_title).setVisibility(View.GONE);
                                        RecyclerView recyclerView = (RecyclerView) bottomView.findViewById(R.id.bottom_sheet_recycler_view);
                                        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                                        String[] stringArray = activity.getResources().getStringArray(R.array.dialog_report);
                                        SimpleTextAdapter textAdapter = new SimpleTextAdapter(activity, Arrays.asList(stringArray));
                                        recyclerView.setAdapter(textAdapter);
                                        textAdapter.setOnItemClickListener(new SimpleTextAdapter.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(View view, int position) {
                                                switch (position) {
                                                    case 0:
                                                        //TODO
                                                        break;
                                                    case 1:

                                                        break;
                                                    case 2:
                                                        dialogReport.dismiss();
                                                        break;
                                                    default:
                                                        break;
                                                }
                                            }

                                            @Override
                                            public void onItemLongClick(View view, int position) {

                                            }
                                        });
                                        dialogReport.setContentView(bottomView);
                                        dialogReport.show();
                                        break;
                                    case 1: //取消
                                        dialog.dismiss();
                                        break;
                                    default:
                                        break;
                                }
                            }

                            @Override
                            public void onItemLongClick(View view, int position) {

                            }
                        });
                        dialog.setContentView(bottomView);
                        dialog.show();
                        break;
                    case R.id.btn_focus:
                        if (item.is_follow) { //做取消关注
                            setFocusBtnStyle((Button) view, R.dimen.dp15, R.string.focus, R.mipmap.unfocus, R.color.color_7f8fa2, R.drawable.shape_unfocus);
                            cancelFocus(view, item);
                        } else {
                            setFocusBtnStyle((Button) view, R.dimen.dp8, R.string.focused, R.mipmap.focused, R.color.color_2288ff, R.drawable.shape_focus);
                            doFocus(view, item);
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void doFocus(final View view, final ProductsBean.DataEntity item) {
        view.setEnabled(false);
        RequestService.doFocus(item.user_id, new CustomCallBack() {
            @Override
            public void onSuccess(String result) {
                view.setEnabled(true);
                item.is_follow = true;
                for (ProductsBean.DataEntity dataEntity : list) {
                    if (TextUtils.equals(item.user_id, dataEntity.user_id)) {
                        dataEntity.is_follow = true;
                    }
                }
                notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                view.setEnabled(true);
                ex.printStackTrace();
                ToastUtils.showError(R.string.request_error);
            }
        });
    }

    private void cancelFocus(final View view, final ProductsBean.DataEntity item) {
        view.setEnabled(false);
        RequestService.cancelFocus(item.user_id, new CustomCallBack() {
            @Override
            public void onSuccess(String result) {
                view.setEnabled(true);
                item.is_follow = false;
                for (ProductsBean.DataEntity dataEntity : list) {
                    if (TextUtils.equals(item.user_id, dataEntity.user_id)) {
                        dataEntity.is_follow = false;
                    }
                }
                notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                view.setEnabled(true);
                ex.printStackTrace();
                ToastUtils.showError(R.string.request_error);
            }
        });
    }


    private void cancelSupport(final View view, final ProductsBean.DataEntity item) {
        view.setEnabled(false);
        RequestService.cancelSupport(item.id, new CustomCallBack() {
            @Override
            public void onSuccess(String result) {
                view.setEnabled(true);
                SupportProductsBean response = JsonUtil.fromJson(result, SupportProductsBean.class);
                if (response.meta.status_code == Constants.HTTP_OK) {
                    item.is_love = false;
                    item.like_count -= 1;
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

    private void doSupport(final View view, final ProductsBean.DataEntity item) {
        view.setEnabled(false);
        RequestService.doSupport(item.id, new CustomCallBack() {
            @Override
            public void onSuccess(String result) {
                view.setEnabled(true);
                SupportProductsBean response = JsonUtil.fromJson(result, SupportProductsBean.class);
                if (response.meta.status_code == Constants.HTTP_OK) {
                    item.is_love = true;
                    item.like_count += 1;
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

    private void setFocusBtnStyle(Button bt_focus, int dimensionPixelSize, int focus, int unfocus_pic, int color, int drawable) {
        dimensionPixelSize = activity.getResources().getDimensionPixelSize(dimensionPixelSize);
        bt_focus.setPadding(dimensionPixelSize, 0, dimensionPixelSize, 0);
        bt_focus.setText(focus);
        bt_focus.setTextColor(activity.getResources().getColor(color));
        bt_focus.setBackgroundResource(drawable);
        bt_focus.setCompoundDrawablesWithIntrinsicBounds(unfocus_pic, 0, 0, 0);
    }

    static class VideoHolder {
        @BindView(R.id.riv)
        RoundedImageView riv;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.btn_focus)
        Button btnFocus;
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
        @BindView(R.id.tv_zan_num)
        TextView tvZanNum;
        @BindView(R.id.tv_txt)
        TextView tvTxt;
        @BindView(R.id.btn_more)
        TextView btnMore;
        @BindView(R.id.tv_comment_num)
        TextView tvCommentNum;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.label_view)
        AutoLabelUI labelView;
        @BindView(R.id.view_line)
        View viewLine;

        public VideoHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
