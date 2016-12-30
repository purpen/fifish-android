package com.qiyuan.fifish.ui.activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.bean.QNBean;
import com.qiyuan.fifish.bean.UploadImgVideoBean;
import com.qiyuan.fifish.network.CustomCallBack;
import com.qiyuan.fifish.network.RequestManager;
import com.qiyuan.fifish.network.RequestService;
import com.qiyuan.fifish.ui.view.WaitingDialog;
import com.qiyuan.fifish.ui.view.imageCrop.ClipSquareImageView;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.FileUtil;
import com.qiyuan.fifish.util.JsonUtil;
import com.qiyuan.fifish.util.ToastUtils;
import com.qiyuan.fifish.util.Util;
import org.xutils.common.util.LogUtil;
import java.io.File;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author lilin
 *         created at 2016/5/18 18:01
 */
public class ImageCropActivity extends BaseActivity {
    interface OnClipCompleteListener {
        void onClipComplete(Bitmap bitmap);
    }

    private static OnClipCompleteListener listener;
    @BindView(R.id.csiv)
    ClipSquareImageView csiv;
    private Uri uri;
    @BindView(R.id.bt_cancel)
    Button bt_cancel;
    @BindView(R.id.bt_clip)
    Button bt_clip;
    private String page;
    private WaitingDialog dialog;
    private String token;
    private String upload_url;
    public ImageCropActivity() {
        super(R.layout.activity_image_crop);
    }

    public static void setOnClipCompleteListener(OnClipCompleteListener listener) {
        ImageCropActivity.listener = listener;
    }

    @Override
    protected void getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra(ImageCropActivity.class.getSimpleName())) {
            uri = intent.getParcelableExtra(ImageCropActivity.class.getSimpleName());
        }

        if (intent.hasExtra(ImageCropActivity.class.getName())) {//区分界面
            page = intent.getStringExtra(ImageCropActivity.class.getName());
        }
    }

    @Override
    protected void initViews() {
        if (uri == null) return;
        dialog = new WaitingDialog(this);
        String path = FileUtil.getRealFilePath(getApplicationContext(), uri);
        ImageLoader.getInstance().displayImage("file:///" + path, csiv);
    }


    @OnClick({R.id.bt_cancel, R.id.bt_clip})
    void performClick(View v) {
        switch (v.getId()) {
            case R.id.bt_cancel://取消上传
                RequestManager.getInstance().cancel(Constants.UPLOAD_AVATAR_URL);
                finish();
                break;
            case R.id.bt_clip:
                Bitmap bitmap = csiv.clip();
                if (TextUtils.isEmpty(page)) {//认证图片
                    if (listener != null) {
                        listener.onClipComplete(bitmap);
                        finish();
                    }
                } else if (TextUtils.equals(EditUserInfoActivity.class.getSimpleName(), page) || TextUtils.equals(CompleteUserInfoActivity.class.getSimpleName(), page)) {//上传头像
                    uploadUserAvatar(bitmap);
                } else {//上传背景封面
                    uploadFile(bitmap);
                }
                break;
        }
    }

    private void uploadFile(Bitmap bitmap) { //换个人中心背景图
        if (bitmap == null) return;
//        File avatar = Util.saveBitmapToFile(bitmap);
//        bitmap.recycle();
//        try {
//            RequestService.upLoadAvatar(avatar, new CustomCallBack() {
//                @Override
//                public void beforeRequest(UriRequest request) throws Throwable {
//                    if (dialog!=null && !activity.isFinishing()) dialog.dismiss();
//                    setViewEnable(false);
//                }
//
//                @Override
//                public void onSuccess(String result) {
//                    setViewEnable(true);
//                }
//
//                @Override
//                public void onError(Throwable ex, boolean isOnCallback) {
//                    setViewEnable(true);
//                    ex.printStackTrace();
//                    ToastUtils.showError(R.string.request_error);
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            setViewEnable(true);
//            if (dialog!=null && !activity.isFinishing()) dialog.dismiss();
//        }

    }

    private void setViewEnable(boolean enable) {
        bt_clip.setEnabled(enable);
        csiv.setEnabled(enable);
    }

    /**
     * @param bitmap
     */
    private void uploadUserAvatar(final Bitmap bitmap) {
        if (bitmap == null) return;
//        File avatar = Util.saveBitmapToFile(bitmap);
        getQNUrlToken("User",bitmap);
    }

    /**
      * @param type
     * @param bitmap
     */
    private void getQNUrlToken(String type, final Bitmap bitmap) {
        final File avatar = Util.saveBitmapToFile(bitmap);
        if (dialog != null && !activity.isFinishing()) dialog.show();
        setViewEnable(false);
        RequestService.getAvatarToken(new CustomCallBack(){
            @Override
            public void onSuccess(String result) {
                QNBean response = JsonUtil.fromJson(result, QNBean.class);
                if (response.meta.status_code==Constants.HTTP_OK){
                    RequestService.upLoadFile(avatar,response.data.token,response.data.upload_url, new CustomCallBack() {
                        @Override
                        public void onSuccess(String result) {
                            LogUtil.e(result);
                            setViewEnable(true);
                            if (dialog != null && !activity.isFinishing()) dialog.dismiss();
                            UploadImgVideoBean response = JsonUtil.fromJson(result, UploadImgVideoBean.class);
                            if (TextUtils.equals(response.ret,"success")) {
                                ToastUtils.showSuccess(R.string.update_success);
                                if (listener != null) {
                                    listener.onClipComplete(bitmap);
                                }
                                finish();
                                return;
                            }
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            if (dialog != null && !activity.isFinishing()) dialog.dismiss();
                            setViewEnable(true);
                            ex.printStackTrace();
                            ToastUtils.showError(R.string.request_error);
                        }
                    });
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (dialog != null && !activity.isFinishing()) dialog.dismiss();
                ex.printStackTrace();
                ToastUtils.showError(R.string.request_error);
            }
        });
    }

}

