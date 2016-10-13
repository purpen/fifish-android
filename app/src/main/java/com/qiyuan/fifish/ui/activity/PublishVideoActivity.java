package com.qiyuan.fifish.ui.activity;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.qiyuan.fifish.R;
import com.qiyuan.fifish.adapter.ShareAdapter;
import com.qiyuan.fifish.adapter.SuggestionAdapter;
import com.qiyuan.fifish.bean.ProductsBean;
import com.qiyuan.fifish.bean.PublishProductsBean;
import com.qiyuan.fifish.bean.QNBean;
import com.qiyuan.fifish.bean.ShareItem;
import com.qiyuan.fifish.bean.TagsBean;
import com.qiyuan.fifish.bean.UploadImgVideoBean;
import com.qiyuan.fifish.network.CustomCallBack;
import com.qiyuan.fifish.network.RequestService;
import com.qiyuan.fifish.ui.view.CustomHeadView;
import com.qiyuan.fifish.ui.view.GridSpacingItemDecoration;
import com.qiyuan.fifish.ui.view.labelview.AutoLabelUI;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.FileUtil;
import com.qiyuan.fifish.util.JsonUtil;
import com.qiyuan.fifish.util.ToastUtils;

import org.xutils.common.util.LogUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * 发布视频
 *
 * @author lilin
 *         created at 2016/10/9 9:06
 */
public class PublishVideoActivity extends BaseActivity implements ShareAdapter.OnItemClickListener, View.OnClickListener {
    @BindView(R.id.custom_head)
    CustomHeadView custom_head;
    @BindView(R.id.videoView)
    JCVideoPlayerStandard videoView;
    @BindView(R.id.et_share_txt)
    EditText et_share_txt;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_add_tag)
    TextView tvAddTag;
    @BindView(R.id.label_view)
    AutoLabelUI labelView;
    private String token;
    private String uploadUrl;
    private ProductsBean.DataBean item;
    private int[] images = {R.mipmap.share_wechat, R.mipmap.share_sina, R.mipmap.share_qq, R.mipmap.share_facebook, R.mipmap.share_tumblr, R.mipmap.share_whatapp};
    private String content;
    private String address;
    private List<String> tags;
    public PublishVideoActivity() {
        super(R.layout.activity_share_video);
    }

    @Override
    protected void getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra(TAG)) {
            item = (ProductsBean.DataBean) intent.getSerializableExtra(TAG);
        }
    }

    @Override
    protected void initViews() {
        custom_head.setHeadCenterTxtShow(true, R.string.title_share);
        custom_head.setHeadRightTxtShow(true, R.string.publish_products);
        custom_head.getHeadRightTV().setTextColor(getResources().getColor(R.color.color_2187ff));
//            videoView.setUp(item.photo.file.large, JCVideoPlayerStandard.SCREEN_LAYOUT_LIST);
        videoView.setUp("http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4", JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, "");
//        }
        String[] strings = getResources().getStringArray(R.array.share_way);
        ArrayList<ShareItem> shareList = new ArrayList<>();
        for (int i = 0; i < strings.length; i++) {
            ShareItem shareItem = new ShareItem();
            shareItem.pic = images[i];
            shareItem.txt = strings[i];
            shareList.add(shareItem);
        }
        ShareAdapter shareAdapter = new ShareAdapter(activity, shareList);
        recyclerView.setLayoutManager(new GridLayoutManager(activity, 3));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration());
        recyclerView.setAdapter(shareAdapter);
        shareAdapter.setmOnItemClickListener(this);
    }

    @Override
    protected void installListener() {
        custom_head.getHeadRightTV().setOnClickListener(this);
    }

    @Override
    protected void requestNet() {
        RequestService.getVideoToken(new CustomCallBack() {
            @Override
            public void onSuccess(String result) {
                QNBean response = JsonUtil.fromJson(result, QNBean.class);
                if (response.meta.status_code == Constants.HTTP_OK) {
                    token = response.data.token;
                    uploadUrl = response.data.upload_url;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                ToastUtils.showError(R.string.request_error);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_head_right:
                upLoadVideo(view);
                break;
            default:
                break;
        }
    }

    private void upLoadVideo(final View view) {//上传的本地视频
        LogUtil.e("token=="+token+";;;;uploadUrl==="+uploadUrl);
        if (TextUtils.isEmpty(token)||TextUtils.isEmpty(uploadUrl)) return;
        final File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/app_tmp.mp4");
        try {
            FileUtil.inputStreamToFile(getResources().openRawResource(R.raw.video),file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        view.setEnabled(false);
        RequestService.upLoadFile(file, token, uploadUrl, new CustomCallBack() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("=========================="+result);
                view.setEnabled(true);
                UploadImgVideoBean response = JsonUtil.fromJson(result, UploadImgVideoBean.class);
                if (TextUtils.equals(response.ret, "success")) {
                    LogUtil.e("视频上传成功");
                    addNewProducts(response.id);
                }
            }

            //上传进度
            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                LogUtil.e("total=="+total+";;current=="+current);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                view.setEnabled(true);
                ex.printStackTrace();
                ToastUtils.showError(R.string.request_error);
            }
        });
    }

    /**
     * 上传作品信息
     * @param asset_id
     */
    private void addNewProducts(String asset_id) {
        LogUtil.e("asset_id==="+asset_id);
        RequestService.addNewProducts(content,asset_id,"","","2",JsonUtil.list2Json(tags),new CustomCallBack(){
            @Override
            public void onSuccess(String result) {
                PublishProductsBean response = JsonUtil.fromJson(result, PublishProductsBean.class);
                if (response.meta.status_code==Constants.HTTP_OK){
                    LogUtil.e("图片发布成功");
                    ToastUtils.showSuccess(R.string.publish_success);
                    finish();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                ToastUtils.showError(R.string.request_error);
            }
        });
    }


    @OnClick({R.id.tv_add_tag, R.id.label_view})
    void performClick(View v) {
        startActivityForResult(new Intent(activity, AddLabelActivity.class), Constants.REQUEST_LABEL);
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case 0://wechat

                break;
            case 1: //sina
                break;
            case 2: //qq
                break;
            case 3: //facebook
                break;
            case 4: //tumblr
                break;
            case 5://whatapp
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case Constants.REQUEST_LABEL:
                String searchTag = data.getStringExtra(SuggestionAdapter.class.getSimpleName());
                if (!TextUtils.isEmpty(searchTag)) {
                    labelView.addLabel("#" + searchTag);
                }

                String enterTag = data.getStringExtra(AddLabelActivity.class.getSimpleName());
                if (!TextUtils.isEmpty(enterTag)) {
                    labelView.addLabel("#" + enterTag);
                }

                TagsBean.DataBean item = data.getParcelableExtra(AddLabelActivity.class.getSimpleName());
                if (item != null) {
                    labelView.addLabel("#" + item.display_name);
                }
                if (labelView.getLabels().size() > 0) {
                    tvAddTag.setVisibility(View.GONE);
                } else {
                    tvAddTag.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
