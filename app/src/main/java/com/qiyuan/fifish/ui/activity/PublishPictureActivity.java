package com.qiyuan.fifish.ui.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.adapter.ShareAdapter;
import com.qiyuan.fifish.bean.ProductsBean;
import com.qiyuan.fifish.bean.ShareItem;
import com.qiyuan.fifish.bean.TagsBean;
import com.qiyuan.fifish.ui.view.CustomHeadView;
import com.qiyuan.fifish.ui.view.labelview.AutoLabelUI;
import com.qiyuan.fifish.util.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * 发布图片
 * @author lilin
 *         created at 2016/10/9 9:06
 */
public class PublishPictureActivity extends BaseActivity implements ShareAdapter.OnItemClickListener {
    @BindView(R.id.custom_head)
    CustomHeadView custom_head;
    @BindView(R.id.videoView)
    JCVideoPlayerStandard videoView;
    @BindView(R.id.iv_cover)
    ImageView ivCover;
    @BindView(R.id.et_share_txt)
    EditText et_share_txt;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_add_tag)
    TextView tvAddTag;
    @BindView(R.id.tv_add_address)
    TextView tvAddAddress;
    @BindView(R.id.label_view)
    AutoLabelUI labelView;

    private ProductsBean.DataBean item;
    private int[] images = {R.mipmap.share_wechat, R.mipmap.share_sina, R.mipmap.share_qq, R.mipmap.share_facebook, R.mipmap.share_tumblr, R.mipmap.share_whatapp};

    public PublishPictureActivity() {
        super(R.layout.activity_share_picture);
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
        if (TextUtils.equals(Constants.TYPE_IMAGE, item.kind)) {
            videoView.setVisibility(View.GONE);
            ivCover.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(item.photo.file.large, ivCover, options);
        } else if (TextUtils.equals(Constants.TYPE_VIDEO, item.kind)) {
            videoView.setVisibility(View.VISIBLE);
            ivCover.setVisibility(View.GONE);
            videoView.setUp(item.photo.file.large, JCVideoPlayerStandard.SCREEN_LAYOUT_LIST);
        }
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
        recyclerView.setAdapter(shareAdapter);
        shareAdapter.setmOnItemClickListener(this);
    }

    @OnClick({R.id.tv_add_tag,R.id.tv_add_address})
    void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_add_tag:
                startActivityForResult(new Intent(activity, AddLabelActivity.class),Constants.REQUEST_LABEL);
                break;
            case R.id.tv_add_address:
                startActivityForResult(new Intent(activity,MapSearchAddressActivity.class),Constants.REQUEST_ADDRESS);
                break;
            default:
                break;
        }
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
        if (requestCode!=RESULT_OK) return;
        switch (requestCode){
            case Constants.REQUEST_LABEL:
                TagsBean.DataBean item= data.getParcelableExtra(AddLabelActivity.class.getSimpleName());
                labelView.addLabel(item.display_name);
                break;
            default:
                break;
        }
    }
}
