package com.album;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.bean.Image;
import com.customview.GlobalTitleLayout;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.application.AppApplication;
import com.qiyuan.fifish.ui.activity.BaseActivity;

import java.io.File;

import butterknife.BindView;
import jp.co.cyberagent.android.gpuimage.GPUImageView;

public class FilterMirrorActivity extends BaseActivity {
    private Image mImageBean;
    @BindView(R.id.gpuimage_filter_mirror_act)
    GPUImageView mGpuImage;
    @BindView(R.id.title_filter_mirror_act)
    GlobalTitleLayout mTitle;
    @BindView(R.id.recycler_filter_mirror_act)
    RecyclerView mRecycler;
    @BindView(R.id.seekBar_filter_mirror_act)
    SeekBar mSeekBar;


    public FilterMirrorActivity() {
        super(R.layout.activity_filter_mirror);
    }

    @Override
    protected void getIntentData() {
        mImageBean = (Image) getIntent().getSerializableExtra("ImageBean");
    }

    @Override
    protected void initViews() {
        mTitle.showTitle(false);
        mTitle.setRightButton(getString(R.string.go_on), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setClass(this,FilterMirrorActivity.class);
//                intent.putExtra("ImageBean",imageBean);
//                startActivity(intent);
            }
        });
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mGpuImage.getLayoutParams();
        lp.height = AppApplication.getInstance().getScreenWidth();
        mGpuImage.setLayoutParams(lp);
        mGpuImage.setImage(new File(mImageBean.path));
        mSeekBar.setProgress(50);

    }
}
