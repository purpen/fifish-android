package com.album;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bean.FilterMirror;
import com.bean.Image;
import com.customview.GlobalTitleLayout;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.adapter.FilterRecyclerAdapter;
import com.qiyuan.fifish.application.AppApplication;
import com.qiyuan.fifish.ui.activity.BaseActivity;
import com.qiyuan.fifish.ui.activity.MainActivity;
import com.qiyuan.fifish.ui.view.wheelview.StringWheelAdapter;
import com.qiyuan.fifish.util.GPUImageFilterTools;
import com.qiyuan.fifish.util.ToastUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageBrightnessFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageView;

public class FilterMirrorActivity extends BaseActivity implements SeekBar.OnSeekBarChangeListener, GPUImageFilterTools.OnGpuImageFilterChosenListener {
    private Image mImageBean;
    @BindView(R.id.gpuimage_filter_mirror_act)
    GPUImageView mGpuImage;
    @BindView(R.id.title_filter_mirror_act)
    GlobalTitleLayout mTitle;
    @BindView(R.id.seekBar_filter_mirror_act)
    SeekBar mSeekBar;
    @BindView(R.id.recycler_filter_mirror_act)
    RecyclerView filterRecycler;
    @BindView(R.id.tv_filter_filter_mirror_act)
    TextView mTvFilter;
    @BindView(R.id.tv_adjust_filter_mirror_act)
    TextView mTvAdjust;

    private GPUImageFilter currentFilter;//当前滤镜
    private int currentPosition;//滤镜位置
    private GPUImageFilterTools.FilterAdjuster filterAdjuster;
    private int mPicWidth = 0, mPicHeight = 0;//图片宽度、高度
    private boolean mSeekBarShow = false;
    private String currentFilterName="";
private Bitmap rawBitmap=null;

    public FilterMirrorActivity() {
        super(R.layout.activity_filter_mirror);
    }

    @Override
    protected void getIntentData() {
        mImageBean = (Image) getIntent().getSerializableExtra("ImageBean");
    }

    @Override
    protected void initViews() {
        //隐藏底部黑色背景进度条、因其高为48dp，故此处取48dp
//        mSeekBar.setTranslationY(getResources().getDimension(R.dimen.dp80));
        mSeekBar.setProgress(50);
        mSeekBar.setOnSeekBarChangeListener(this);
        mSeekBar.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
//                        mSeekBar.setTranslationY(mSeekBar.getMeasuredHeight());
                        mSeekBar.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                });

        mTitle.showTitle(false);
        mTitle.setRightButton(getString(R.string.go_on), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                compoundPicture();
//                Intent intent = new Intent();
//                intent.setClass(this,FilterMirrorActivity.class);
//                intent.putExtra("ImageBean",imageBean);
//                startActivity(intent);
            }


        });

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mGpuImage.getLayoutParams();
        lp.height = AppApplication.getInstance().getScreenWidth();
        mGpuImage.setLayoutParams(lp);
//        mGpuImage.setImage(new File(mImageBean.path));
        Uri urii = mImageBean.path.startsWith("file:") ? Uri.parse(mImageBean.path) : Uri.parse("file://" + mImageBean.path);
//        mGpuImage.setImage(Uri.parse("file:///storage/emulated/0/MIUI/wallpaper/%E6%80%A7%E6%84%9F%E7%BE%8E%E5%A5%B3%20(7)_%26_eebc8557-3eca-45be-8bd3-c6efb90c2b91.jpg"));
//      mGpuImage.setImage(AppApplication.cropBitmap);
        mGpuImage.setScaleType(GPUImage.ScaleType.CENTER_INSIDE);
        mGpuImage.setBackgroundResource(R.color.white_theme);
        ImageLoader.getInstance().loadImage(urii.toString(), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                mPicWidth = loadedImage.getWidth();
                mPicHeight = loadedImage.getHeight();
                AppApplication.originalBitmap = loadedImage;
                AppApplication.editBitmap=loadedImage;
                rawBitmap=loadedImage;
//                        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mGpuImage.getLayoutParams();
//                        lp.height=lp.width=mPicHeight>mPicWidth?mPicWidth:mPicHeight;
//                        Log.e(">>>", mPicWidth+">>"+mPicHeight+">>>ww>>" + lp.height + ">>" + lp.width);
//                        mGpuImage.setLayoutParams(lp);
                mGpuImage.setImage(loadedImage);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });

        filterRecycler.setHasFixedSize(true);
        filterRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        final FilterRecyclerAdapter recyclerAdapter = new FilterRecyclerAdapter(this, this, new FilterRecyclerAdapter.ItemClick() {
            @Override
            public void click(int position, String typeName) {
                //当点击同一滤镜时，做滤镜调节时使用
                mSeekBarShow = true;
                currentFilterName = typeName;
                showSeekBar(currentFilterName, getString(R.string.cancel), getString(R.string.complete));
            }
        });
        filterRecycler.setAdapter(recyclerAdapter);
        currentFilter = new GPUImageFilter();
        currentPosition = 0;
    }

    @Override
    public void onGpuImageFilterChosenListener(GPUImageFilter filter, int position) {
        currentFilter = filter;
        currentPosition = position;
        mGpuImage.setFilter(filter);

        filterAdjuster = new GPUImageFilterTools.FilterAdjuster(filter);
        if (mSeekBarShow) mSeekBar.setVisibility(filterAdjuster.canAdjust() ? View.VISIBLE : View.GONE);

        if (mGpuImage.getGPUImage() != null) {
            mGpuImage.getGPUImage().deleteImage();
            if (AppApplication.editBitmap==null) mGpuImage.setImage(AppApplication.originalBitmap);
            else mGpuImage.setImage(AppApplication.editBitmap);
        }
        int constant = 0;
        switch (position) {
            // 亮度. 原图、都市、摩登、日光、摩卡、佳人、 候鸟、夏日、午茶、戏剧、流年、暮光
            case 0:
//                if (mGpuImage.getGPUImage() != null) {
//                    mGpuImage.getGPUImage().deleteImage();
//                    mGpuImage.setImage(AppApplication.editBitmap);
//                }
                filterAdjuster.adjust(50);
                mSeekBar.setProgress(50);
                break;
            case 4:
                constant = 97;
                filterAdjuster.adjust(constant);//摩卡
                mSeekBar.setProgress(constant);
                break;
            case 11:
                filterAdjuster.adjust(45);//暮光
                mSeekBar.setProgress(45);
                break;
            case 6:
                filterAdjuster.adjust(0);//候鸟
                mSeekBar.setProgress(0);
                break;
            case 7:
                filterAdjuster.adjust(40);//夏日
                mSeekBar.setProgress(40);
                break;
            case 1:
                constant = 100;
                filterAdjuster.adjust(constant);//都市
                mSeekBar.setProgress(constant);
                break;
            case 5:
                filterAdjuster.adjust(25);//佳人
                mSeekBar.setProgress(25);
                break;
            case 10:
                filterAdjuster.adjust(55);//流年
                mSeekBar.setProgress(55);
                break;
            case 3:
                constant = 53;
                filterAdjuster.adjust(constant);//日光
                mSeekBar.setProgress(constant);
                break;
            case 8:
                filterAdjuster.adjust(60);//午茶
                mSeekBar.setProgress(60);
                break;
        }
        mGpuImage.requestRender();
    }

    @OnClick({R.id.tv_adjust_filter_mirror_act, R.id.tv_filter_filter_mirror_act})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_adjust_filter_mirror_act:
                if (mTvAdjust.getText().equals(getString(R.string.adjust))) {
//                try {
//                    saveToFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera", true, bitmap);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                    setNewBitmap();
                    mSeekBarShow = true;
                    onGpuImageFilterChosenListener(new GPUImageBrightnessFilter(1.5f), 0);
                    showSeekBar(getString(R.string.brightness), getString(R.string.cancel), getString(R.string.complete));
                } else if (mTvAdjust.getText().equals(getString(R.string.complete))) {
                    setNewBitmap();
                    mSeekBarShow = false;
                    mSeekBar.setVisibility(View.GONE);
                    filterRecycler.setVisibility(View.VISIBLE);
                    showSeekBar(currentFilterName, getString(R.string.filter_mirror), getString(R.string.adjust));
                }
                break;
            case R.id.tv_filter_filter_mirror_act:
                if (mTvFilter.getText().equals(getString(R.string.cancel))) {
                    mSeekBarShow = false;
                    mSeekBar.setVisibility(View.GONE);
                    filterRecycler.setVisibility(View.VISIBLE);
                    onGpuImageFilterChosenListener(new GPUImageFilter(), 2);//不是恢复原图，而是加正常状态滤镜
                    if (mGpuImage.getGPUImage() != null) {
                        mGpuImage.getGPUImage().deleteImage();
                        mGpuImage.setImage(rawBitmap);
                    }
                }
                showSeekBar("", getString(R.string.filter_mirror), getString(R.string.adjust));
                break;
        }
    }

    private void setNewBitmap() {
        if (mGpuImage.getWidth() <= 0 || mGpuImage.getHeight() <= 0) {
            ToastUtils.showError("图片信息错误，请重试");
            return;
        }
        //加滤镜
        final Bitmap newBitmap = Bitmap.createBitmap(mPicWidth, mPicHeight,
                Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(newBitmap);
        RectF dst = new RectF(0, 0, mPicWidth, mPicHeight);
        cv.drawBitmap(AppApplication.originalBitmap, null, dst, null);
        if (mGpuImage.getGPUImage() != null) mGpuImage.getGPUImage().deleteImage();
        mGpuImage.setImage(newBitmap);
//                onGpuImageFilterChosenListener(currentFilter, currentPosition);
        Bitmap bitmap = null;
        try {
            bitmap = mGpuImage.capture();
        } catch (InterruptedException e) {
            mGpuImage.setImage(AppApplication.originalBitmap);
            ToastUtils.showError("图片信息错误，请重试");
            return;
        }
        if (bitmap == null) {
            mGpuImage.setImage(AppApplication.originalBitmap);
            ToastUtils.showError("图片信息错误，请重试");
            return;
        }
        AppApplication.editBitmap = bitmap;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        if (filterAdjuster != null) {
            filterAdjuster.adjust(progress);
        }
        mGpuImage.requestRender();
    }

    @Override //刚开始拖动时触发
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override //停止拖动时触发
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private void showSeekBar(String title, String filter, String adjust) {
        mTitle.showTitle(true);
        mTitle.setTitle(title);
        mTvAdjust.setText(adjust);
        mTvFilter.setText(filter);
    }

    private ObjectAnimator showAnimator, hideAnimator;

    //显示顶部黑色背景标题栏和底部黑背景分享条
    private void showAnim() {
        ValueAnimator mRecyclerAnim = ObjectAnimator.ofFloat(filterRecycler, "translationY", 0)
                .setDuration(300);
        ValueAnimator mSeekBarAnim = ObjectAnimator.ofFloat(mSeekBar, "translationY", mSeekBar.getMeasuredHeight())
                .setDuration(300);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(mSeekBarAnim).before(mRecyclerAnim);
        animatorSet.start();
//        if (showAnimator == null) {
//            showAnimator = ObjectAnimator.ofFloat(mSeekBar, "translationY", 0).setDuration(300);
//            showAnimator.addListener(new Animator.AnimatorListener() {
//                @Override
//                public void onAnimationStart(Animator animation) {
////                    MainActivity activity = (MainActivity) getActivity();
////                    activity.hideAnim();
////                    mediaFragment.hideAnim();
//                }
//
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                }
//
//                @Override
//                public void onAnimationCancel(Animator animation) {
//                }
//
//                @Override
//                public void onAnimationRepeat(Animator animation) {
//                }
//            });
//        }
//        showAnimator.start();
    }

    //隐藏顶部黑色背景标题栏和底部黑背景分享条
    private void hideAnim() {
        ValueAnimator mRecyclerAnim = ObjectAnimator.ofFloat(filterRecycler, "translationY", filterRecycler.getMeasuredHeight())
                .setDuration(300);
        ValueAnimator mSeekBarAnim = ObjectAnimator.ofFloat(mSeekBar, "translationY", 0)
                .setDuration(300);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(mRecyclerAnim).before(mSeekBarAnim);
        animatorSet.start();
//        if (hideAnimator == null) {
//            hideAnimator = ObjectAnimator.ofFloat(mSeekBar, "translationY", mSeekBar.getMeasuredHeight()).setDuration(300);
//            hideAnimator.addListener(new Animator.AnimatorListener() {
//                @Override
//                public void onAnimationStart(Animator animation) {
////                    MainActivity activity = (MainActivity) getActivity();
////                    activity.showAnim();
////                    mediaFragment.showAnim();
//                }
//
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                }
//
//                @Override
//                public void onAnimationCancel(Animator animation) {
//                }
//
//                @Override
//                public void onAnimationRepeat(Animator animation) {
//                }
//            });
//        }
//        hideAnimator.start();
    }

    private void compoundPicture() {

    }

    //保存图片文件
    public static String saveToFile(String fileFolderStr, boolean isDir, Bitmap croppedImage) throws IOException {
        File jpgFile;
        if (isDir) {
            File fileFolder = new File(fileFolderStr);
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA); // 格式化时间
            String filename = format.format(date) + ".jpg";
            if (!fileFolder.exists()) { // 如果目录不存在，则创建一个名为"finger"的目录
                mkdir(fileFolder);
            }
            jpgFile = new File(fileFolder, filename);
        } else {
            jpgFile = new File(fileFolderStr);
//            Log.e("<<<", "jpgfile.path = " + jpgFile.getAbsolutePath());
            if (!jpgFile.getParentFile().exists()) { // 如果目录不存在，则创建一个名为"finger"的目录
                mkdir(jpgFile.getParentFile());
            }
        }
        FileOutputStream outputStream = new FileOutputStream(jpgFile); // 文件输出流
        croppedImage.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        outputStream.close();
//        Log.e("<<<", "path = " + jpgFile.getPath());
        return jpgFile.getPath();
    }

    //创建文件夹
    private static boolean mkdir(File file) {
        while (!file.getParentFile().exists()) {
            mkdir(file.getParentFile());
        }
        return file.mkdir();
    }
}
