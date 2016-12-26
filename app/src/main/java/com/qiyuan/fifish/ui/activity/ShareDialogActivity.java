package com.qiyuan.fifish.ui.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.qiyuan.fifish.R;
import com.qiyuan.fifish.adapter.ShareDialogAdapter;
import com.qiyuan.fifish.bean.ProductsBean;
import com.qiyuan.fifish.bean.ShareItem;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.ToastUtils;
import com.umeng.qq.tencent.AuthActivity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static com.qiyuan.fifish.util.Constants.REQUEST_READ_EXTERNAL_STORAGE;

/**
 * Created by lilin on 2016/12/22.
 */

public class ShareDialogActivity extends BaseActivity {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private ProductsBean.DataEntity item;
    private SHARE_MEDIA platform;

    public ShareDialogActivity() {
        super(R.layout.activity_share_dialog);
    }

    @Override
    protected void getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra(TAG)) {
            item = (ProductsBean.DataEntity) intent.getSerializableExtra(TAG);
        }
    }

    @Override
    protected void initViews() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(lp);
        initData();
    }

    private boolean canRedExternalStorage() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        requestPermissions(new String[]{READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);
        return false;
    }

    private void initData() {
        int[] image = {R.mipmap.share_wechat, R.mipmap.share_qq, R.mipmap.share_sina, R.mipmap.share_facebook, R.mipmap.share_instgram};
        String[] name = {"微信", "QQ", "微博", "facebook", "instagram"};
        List<ShareItem> shareList = new ArrayList<>();
        ShareItem shareItem;
        for (int i = 0; i < image.length; i++) {
            shareItem = new ShareItem();
            shareItem.txt = name[i];
            shareItem.pic = image[i];
            shareList.add(shareItem);
        }
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        ShareDialogAdapter adapter = new ShareDialogAdapter(activity, shareList);
        recyclerView.setAdapter(adapter);
        adapter.setmOnItemClickListener(new ShareDialogAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        platform = SHARE_MEDIA.WEIXIN;
                        break;
                    case 1:
                        platform = SHARE_MEDIA.QQ;
                        break;
                    case 2:
                        platform = SHARE_MEDIA.SINA;
                        break;
                    case 3:
                        platform = SHARE_MEDIA.FACEBOOK;
                        break;
                    case 4:
                        platform = SHARE_MEDIA.INSTAGRAM;
                        break;
                }
                if(canRedExternalStorage()){
                    share();
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    private void share() {
        if (platform == null || item == null) return;
        if (TextUtils.equals(Constants.TYPE_IMAGE, item.kind)) {
            UMImage image = new UMImage(activity, item.cover.file.large);//网络图片
            new ShareAction(activity).setPlatform(platform).withMedia(image)
                    .withText(item.content)
                    .setCallback(umShareListener)
                    .share();
        }

        if (TextUtils.equals(Constants.TYPE_VIDEO, item.kind)) {
            UMVideo video = new UMVideo(item.cover.file.srcfile);
            video.setTitle(getString(R.string.app_name));//视频的标题
            video.setThumb(new UMImage(activity, item.cover.file.large)); //视频封面
            video.setDescription(item.content);//视频的描述
            new ShareAction(activity).setPlatform(platform).withMedia(video)
                    .setCallback(umShareListener)
                    .share();
        }
    }


    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.e("plat", "platform" + platform);
            ToastUtils.showInfo(platform + " 分享成功啦");
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtils.showInfo(platform + " 分享失败啦");
            if (t != null) {
                Log.e("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtils.showInfo(platform + " 分享取消了");
        }
    };

    @OnClick(R.id.btn)
    public void onClick() {
        finish();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(activity).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted , Access contacts here or do whatever you need.
                share();
            }
        }
    }
}
