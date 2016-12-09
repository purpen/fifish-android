package com.qiyuan.fifish.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.adapter.SearchProductsAdapter;
import com.qiyuan.fifish.bean.SearchProductsBean;
import com.qiyuan.fifish.network.CustomCallBack;
import com.qiyuan.fifish.network.RequestService;
import com.qiyuan.fifish.ui.view.CustomHeadView;
import com.qiyuan.fifish.ui.view.WaitingDialog;
import com.qiyuan.fifish.ui.view.roundImageView.RoundedImageView;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.JsonUtil;
import com.qiyuan.fifish.util.ToastUtils;
import com.qiyuan.fifish.util.Util;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;


public class TagActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.custom_head)
    CustomHeadView customHead;
    @BindView(R.id.listView1)
    ListView listView1;
    @BindView(R.id.listView2)
    ListView listView2;
    @BindView(R.id.listView3)
    ListView listView3;
    private RelativeLayout rl1;
    private RelativeLayout rl2;
    private RelativeLayout rl3;
    private View line1;
    private View line2;
    private View line3;
    private String tag;
    private int videoPage = 1;
    private int picturePage = 1;
    private int userPage = 1;
    private SearchProductsAdapter videoAdapter;
    private SearchProductsAdapter pictureAdapter;
    private List<SearchProductsBean.DataBean> mVideoList;
    private List<SearchProductsBean.DataBean> mPictureList;
    private TextView tvTitle;
    private TextView tvDesc;
    private RoundedImageView riv;
    private WaitingDialog dialog;
    public TagActivity() {
        super(R.layout.activity_tag);
    }


    @Override
    protected void getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra(TAG)) {
            tag = intent.getStringExtra(TAG);
        }
    }

    @Override
    protected void initViews() {
        customHead.setHeadCenterTxtShow(true, R.string.title_tag);
        dialog=new WaitingDialog(this);
        View view = Util.inflateView(activity, R.layout.view_tag_head, null);
        mVideoList = new ArrayList<>();
        mPictureList = new ArrayList<>();
//        imageView = ButterKnife.findById(view, R.id.imageView);
//        tvTitle = ButterKnife.findById(view, R.id.tv_title);
//        tvDesc = ButterKnife.findById(view, R.id.tv_desc);
//        viewLine = ButterKnife.findById(view, R.id.view_line);
//        tvDuring = ButterKnife.findById(view, R.id.tv_during);
        riv = ButterKnife.findById(view, R.id.riv);
        tvTitle = ButterKnife.findById(view, R.id.tv_title);
        tvDesc = ButterKnife.findById(view, R.id.tv_desc);
        rl1 = ButterKnife.findById(view, R.id.rl1);
        rl2 = ButterKnife.findById(view, R.id.rl2);
        rl3 = ButterKnife.findById(view, R.id.rl3);
        line1 = ButterKnife.findById(view, R.id.line1);
        line2 = ButterKnife.findById(view, R.id.line2);
        line3 = ButterKnife.findById(view, R.id.line3);

        listView1.addHeaderView(view);
        listView2.addHeaderView(view);
        listView3.addHeaderView(view);
        listView1.setVisibility(View.VISIBLE);
        listView1.setAdapter(null);
        listView2.setAdapter(null);
        listView3.setAdapter(null);
    }

    @Override
    protected void installListener() {
        rl1.setOnClickListener(this);
        rl2.setOnClickListener(this);
        rl3.setOnClickListener(this);
        listView1.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if (AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL == i || AbsListView.OnScrollListener.SCROLL_STATE_FLING == i) {
                    if (absListView.getLastVisiblePosition() == (absListView.getCount() - 1)) {
                        searchPicture();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });

        listView2.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if (AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL == i || AbsListView.OnScrollListener.SCROLL_STATE_FLING == i) {
                    if (absListView.getLastVisiblePosition() == (absListView.getCount() - 1)) {
                        searchVideo();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });

        listView3.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if (AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL == i || AbsListView.OnScrollListener.SCROLL_STATE_FLING == i) {
                    if (absListView.getLastVisiblePosition() == (absListView.getCount() - 1)) {
                        searchUser();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl1:
                resetUI();
                listView1.setVisibility(View.VISIBLE);
                line1.setVisibility(View.VISIBLE);
                break;
            case R.id.rl2:
                resetUI();
                listView2.setVisibility(View.VISIBLE);
                line2.setVisibility(View.VISIBLE);
                break;
            case R.id.rl3:
                resetUI();
                listView3.setVisibility(View.VISIBLE);
                line3.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void resetUI() {
        listView1.setVisibility(View.GONE);
        listView2.setVisibility(View.GONE);
        listView3.setVisibility(View.GONE);
        line1.setVisibility(View.INVISIBLE);
        line2.setVisibility(View.INVISIBLE);
        line3.setVisibility(View.INVISIBLE);
    }

    private void searchVideo() {
        RequestService.searchInSite(String.valueOf(videoPage), tag, "1", "2", "2", "1", new CustomCallBack() {
            @Override
            public void onSuccess(String result) {
                SearchProductsBean searchProductsBean = JsonUtil.fromJson(result, SearchProductsBean.class);
                if (searchProductsBean.meta.status_code == Constants.HTTP_OK) {
                    List<SearchProductsBean.DataBean> list = searchProductsBean.data;
                    if (list == null || list.size() == 0) return;
                    videoPage++;
                    mVideoList.addAll(list);
                    if (videoAdapter == null) {
                        videoAdapter = new SearchProductsAdapter(mVideoList, activity);
                        listView2.setAdapter(videoAdapter);
                    } else {
                        videoAdapter.notifyDataSetChanged();
                    }
                    return;
                }
                ToastUtils.showError(R.string.request_error);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                ToastUtils.showError(R.string.request_error);
            }

            @Override
            public void onFinished() {
                searchUser();
            }
        });
    }

    private void searchPicture() {

        RequestService.searchInSite(String.valueOf(picturePage), tag, "1", "1", "2", "1", new CustomCallBack() {
            @Override
            public void onSuccess(String result) {
                SearchProductsBean searchProductsBean = JsonUtil.fromJson(result, SearchProductsBean.class);
                if (searchProductsBean.meta.status_code == Constants.HTTP_OK) {
                    List<SearchProductsBean.DataBean> list = searchProductsBean.data;
                    if (list == null || list.size() == 0) return;
                    picturePage++;
                    mPictureList.addAll(list);
                    if (mPictureList.size()>0){
                        List<SearchProductsBean.DataBean.StuffBean.TagBean> tags = mPictureList.get(0).stuff.tags;
                        if (tags!=null&&tags.size()>0) {
                            tvTitle.setText(String.format("# %s",tags.get(0).name));
                            tvDesc.setText(getResources().getString(R.string.used_times)+tags.get(0).total_count);
                            ImageLoader.getInstance().displayImage(tags.get(0).cover.file.thumb,riv,options);
                        }
                    }else {
                        //TODO
                    }
                    if (pictureAdapter == null) {
                        pictureAdapter = new SearchProductsAdapter(mPictureList, activity);
                        listView1.setAdapter(pictureAdapter);
                    } else {
                        pictureAdapter.notifyDataSetChanged();
                    }
                    return;
                }
                ToastUtils.showError(R.string.request_error);
                if (dialog!=null&&!activity.isFinishing()) dialog.dismiss();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                if (dialog!=null&&!activity.isFinishing()) dialog.dismiss();
                ToastUtils.showError(R.string.request_error);
            }

            @Override
            public void onFinished() {
                searchVideo();
            }
        });
    }

    private void searchUser() {
        //搜索用户
        RequestService.searchInSite(String.valueOf(picturePage), tag, "1", null, "2", "1", new CustomCallBack() {
            @Override
            public void onSuccess(String result) {
                if (dialog!=null&&!activity.isFinishing()) dialog.dismiss();
                SearchProductsBean searchProductsBean = JsonUtil.fromJson(result, SearchProductsBean.class);
                if (searchProductsBean.meta.status_code == Constants.HTTP_OK) {
                    List<SearchProductsBean.DataBean> list = searchProductsBean.data;
                    if (list == null || list.size() == 0) return;
                    picturePage++;
                    mPictureList.addAll(list);
                    if (pictureAdapter == null) {
                        pictureAdapter = new SearchProductsAdapter(mPictureList, activity);
                        listView2.setAdapter(pictureAdapter);
                    } else {
                        pictureAdapter.notifyDataSetChanged();
                    }
                    return;
                }
                ToastUtils.showError(R.string.request_error);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (dialog!=null&&!activity.isFinishing()) dialog.dismiss();
                ex.printStackTrace();
                ToastUtils.showError(R.string.request_error);
            }
        });
    }

    @Override
    protected void requestNet() {
        if (dialog!=null&&!activity.isFinishing()) dialog.show();
        if (TextUtils.isEmpty(tag)) return;
        searchPicture();
//        searchVideo();
//        searchUser();
    }

    @Override
    protected void refreshUI() {

    }
}
