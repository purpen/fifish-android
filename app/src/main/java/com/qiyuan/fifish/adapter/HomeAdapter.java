package com.qiyuan.fifish.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.qiyuan.fifish.ui.activity.PublishVideoActivity;
import com.qiyuan.fifish.ui.activity.SearchActivity;
import com.qiyuan.fifish.ui.view.labelview.AutoLabelUI;
import com.qiyuan.fifish.ui.view.labelview.Label;
import com.qiyuan.fifish.ui.view.roundImageView.RoundedImageView;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.JsonUtil;
import com.qiyuan.fifish.util.ToastUtils;
import com.qiyuan.fifish.util.Util;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * @author lilin
 *         created at 2016/4/22 19:00
 */
public class HomeAdapter extends BaseAdapter<ProductsBean.DataEntity> {
    private ImageLoader imageLoader;


    public HomeAdapter(List<ProductsBean.DataEntity> list, Activity activity) {
        super(list, activity);
        this.imageLoader = ImageLoader.getInstance();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ProductsBean.DataEntity item = list.get(position);
        VideoHolder videoHolder;
        if (convertView == null) {
            convertView = Util.inflateView(activity,R.layout.item_home_video, null);
            videoHolder = new VideoHolder(convertView);
            convertView.setTag(videoHolder);
        } else {
            videoHolder = (VideoHolder) convertView.getTag();
        }
        if (TextUtils.equals(Constants.TYPE_IMAGE, item.photo.kind)) {
            videoHolder.videoView.setVisibility(View.GONE);
            videoHolder.ivCover.setVisibility(View.VISIBLE);
            imageLoader.displayImage(item.photo.file.large, videoHolder.ivCover, options);
        } else if (TextUtils.equals(Constants.TYPE_VIDEO, item.photo.kind)) {
            videoHolder.videoView.setVisibility(View.VISIBLE);
            videoHolder.ivCover.setVisibility(View.GONE);
            videoHolder.videoView.setUp(item.photo.file.srcfile, JCVideoPlayerStandard.SCREEN_LAYOUT_LIST,"");
        }
        imageLoader.displayImage(item.user.avatar.large, videoHolder.riv);
        videoHolder.tvName.setText(item.user.username);
        if (item.user.summary != null) {
            videoHolder.tvDesc.setVisibility(View.VISIBLE);
            videoHolder.tvDesc.setText(item.user.summary.toString());
        } else {
            videoHolder.tvDesc.setVisibility(View.INVISIBLE);
        }
        videoHolder.labelView.clear();
        for (Object obj : item.tags) {
            if (obj instanceof String){
                String txt=obj.toString();
                if (!TextUtils.isEmpty(txt)){
                    videoHolder.labelView.addLabel("#" + obj.toString());
                }
            }
        }

//        ArrayList<String> strings = new ArrayList<>();
//        strings.add("北京");
//        strings.add("北京摄影");
//        strings.add("水下");
//        for (String obj : strings) {
//            videoHolder.labelView.addLabel("#" + obj.toString());
//        }
        videoHolder.tvContent.setText(item.content);
        if (item.is_love) {
            videoHolder.ibtnFavorite.setImageResource(R.mipmap.icon_support);
        } else {
            videoHolder.ibtnFavorite.setImageResource(R.mipmap.icon_unsupport);
        }

        if (position == size - 1) {
            videoHolder.viewLine.setVisibility(View.GONE);
        } else {
            videoHolder.viewLine.setVisibility(View.VISIBLE);
        }
//        videoHolder.tvTime.setText(item.created_at);
        setClickListener(videoHolder.ibtnFavorite, item);
        setClickListener(videoHolder.ibtnComment, item);
        setClickListener(videoHolder.ibtnShare, item);
        setClickListener(videoHolder.ibtnMore, item);
        videoHolder.labelView.setOnLabelClickListener(new AutoLabelUI.OnLabelClickListener() {
            @Override
            public void onClickLabel(Label labelClicked) {
                Intent intent=new Intent(activity, SearchActivity.class);
                String txt=labelClicked.getText().substring(1);
                if (!TextUtils.isEmpty(txt)){
                    intent.putExtra(SearchActivity.class.getSimpleName(),txt);
                    activity.startActivity(intent);
                }
            }
        });
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
                        //分享界面
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
                                switch (position){
                                    case 0: //举报
                                        final BottomSheetDialog dialogReport = new BottomSheetDialog(activity);
                                        View bottomView = Util.inflateView(R.layout.view_bottom_list, null);
                                        bottomView.findViewById(R.id.tv_title).setVisibility(View.GONE);
                                        RecyclerView recyclerView = (RecyclerView) bottomView.findViewById(R.id.bottom_sheet_recycler_view);
                                        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                                        String[] stringArray = activity.getResources().getStringArray(R.array.dialog_report);
                                        SimpleTextAdapter textAdapter = new SimpleTextAdapter(activity, Arrays.asList(stringArray));
                                        recyclerView.setAdapter(textAdapter);
                                        textAdapter.setOnItemClickListener(new SimpleTextAdapter.OnItemClickListener(){
                                            @Override
                                            public void onItemClick(View view, int position) {
                                                switch (position){
                                                    case 0:

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
                                    case 1: //分享
                                        Intent intent=new Intent(activity,PublishVideoActivity.class);
                                        intent.putExtra(PublishVideoActivity.class.getSimpleName(),item);
                                        activity.startActivity(intent);
                                        break;
                                    case 2:
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
                    default:
                        break;
                }
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
        @BindView(R.id.label_view)
        AutoLabelUI labelView;

        public VideoHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
