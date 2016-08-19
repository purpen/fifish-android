package com.qiyuan.fifish.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.adapter.UserCenterViewPagerAdapter;
import com.qiyuan.fifish.album.ImageLoaderEngine;
import com.qiyuan.fifish.album.Picker;
import com.qiyuan.fifish.album.PicturePickerUtils;
import com.qiyuan.fifish.bean.LoginUserInfo;
import com.qiyuan.fifish.ui.fragment.FansFragment;
import com.qiyuan.fifish.ui.fragment.FocusFragment;
import com.qiyuan.fifish.ui.fragment.MineFragment;
import com.qiyuan.fifish.ui.fragment.ProductsFragment;
import com.qiyuan.fifish.ui.view.MyNestScrollView;
import com.qiyuan.fifish.ui.view.UserCenterViewPager;
import com.qiyuan.fifish.ui.view.WaitingDialog;
import com.qiyuan.fifish.ui.view.roundImageView.RoundedImageView;
import com.qiyuan.fifish.util.PopupWindowUtil;
import com.qiyuan.fifish.util.ToastUtils;
import com.qiyuan.fifish.util.Util;

import org.xutils.common.util.LogUtil;

import java.io.File;
import java.util.List;

import butterknife.Bind;

/**
 * @author lilin
 *         created at 2016/4/26 17:43
 */
public class UserCenterActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.nested_scroll_view)
    MyNestScrollView nestedScrollView;
    @Bind(R.id.iv_bg)
    ImageView ivBg;
    @Bind(R.id.riv)
    RoundedImageView riv;
    @Bind(R.id.riv_auth)
    RoundedImageView rivAuth;
    @Bind(R.id.iv_label)
    TextView ivLabel;
    @Bind(R.id.tv_auth)
    TextView tvAuth;
    @Bind(R.id.tv_lv)
    TextView tvLv;
    @Bind(R.id.tv_label)
    TextView tvLabel;
    @Bind(R.id.tv_signature)
    TextView tvSignature;
    @Bind(R.id.viewPager)
    UserCenterViewPager viewPager;

    private int curPage = 1;
    private static final String PAGE_SIZE = "10";
    private LinearLayout ll_box;
    private LinearLayout ll_btn_box;
//    @Bind(R.id.tv_title)
//    TextView tv_title;
    //    @Bind(R.id.ll_tips)
//    LinearLayout ll_tips;
    private TextView tv_real;
    private TextView tv_qj;
    private TextView tv_cj;
    private TextView tv_focus;
    private TextView tv_fans;
    private Button bt_focus;
    private Button bt_msg;
    private ImageView iv_bg;
    private TextView tv_label;
    private TextView tv_lv;
    private TextView tv_auth;
    private TextView iv_label;
    private LoginUserInfo user;
    private List<Uri> mSelected;
    //    private int which = MineFragment.REQUEST_CJ;
    private long userId;//= LoginUserInfo.getLoginInfo()._id;
    private static final int REQUEST_CODE_PICK_IMAGE = 100;
    private static final int REQUEST_CODE_CAPTURE_CAMERA = 101;
    private WaitingDialog dialog;
    public static final Uri imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "temp.jpg"));
    TextView tv_tips;
    private boolean isFirstLoad = true;
    private String flag;
    private Fragment[] fragments={ProductsFragment.newInstance(), FocusFragment.newInstance(), FansFragment.newInstance()};
    public UserCenterActivity() {
        super(R.layout.activity_user_center);
    }

    @Override
    protected void getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra(MineFragment.class.getSimpleName())) {
//            which = intent.getIntExtra(MineFragment.class.getSimpleName(), MineFragment.REQUEST_CJ);
        }

//        if (intent.hasExtra(FocusActivity.USER_ID_EXTRA)) {
//            userId = intent.getLongExtra(FocusActivity.USER_ID_EXTRA, LoginUserInfo.getUserId());
//        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        resetData();
        requestNet();
    }

    private void resetData() {
        isFirstLoad = true;
        curPage = 1;
//        mQJList.clear();
//        mSceneList.clear();
//        ll_tips.setVisibility(View.GONE);
    }

    @Override
    protected void initViews() {
        nestedScrollView.setFillViewport (true);
//        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                LogUtil.e("scrollY==="+scrollY+";;;;oldScrollY==="+oldScrollY+"=="+nestedScrollView.computeVerticalScrollRange());
//                if (scrollY<=0){
//                    nestedScrollView.requestDisallowInterceptTouchEvent(true);
//                    viewPager.requestDisallowInterceptTouchEvent(true);
//                }else {
//                    nestedScrollView.requestDisallowInterceptTouchEvent(false);
//                }
//            }
//
//        });
        viewPager.setAdapter(new UserCenterViewPagerAdapter(getSupportFragmentManager(),fragments));
        dialog = new WaitingDialog(this);
    }

    @Override
    protected void requestNet() {

    }

    public UserCenterViewPager getViewPager(){
        if (viewPager==null) return null;
        return viewPager;
    }

    /**
     * 更新headview的UI
     */
    @Override
    protected void refreshUI() {
        if (user == null) {
            return;
        }
//        if (user.is_love == FansAdapter.NOT_LOVE) {
//            bt_focus.setText("关注");
//        } else {
//            bt_focus.setText("已关注");
//        }
        if (!TextUtils.isEmpty(user.nickname)) {
//            tv_title.setText(user.nickname);
        }
        if (!TextUtils.isEmpty(user.medium_avatar_url)) {
            ImageLoader.getInstance().displayImage(user.medium_avatar_url, riv);
        }
//        if (!TextUtils.isEmpty(user.head_pic_url)) {
        ImageLoader.getInstance().displayImage(user.head_pic_url, iv_bg);
//        }

//        if (user.identify.is_expert==1){
//            riv_auth.setVisibility(View.VISIBLE);
//        }else {
//            riv_auth.setVisibility(View.GONE);
//        }

        if (TextUtils.isEmpty(user.summary)) {
            if (LoginUserInfo.getLoginInfo()._id == userId) {
                tv_real.setText(String.format(" | %s", "说说你是什么人，来自哪片山川湖海！"));
            } else {
                tv_real.setText(String.format(" | %s", "这人好神秘，什么都不说"));
            }
        } else {
            tv_real.setText(String.format(" | %s", user.summary));
        }

//        if (!TextUtils.isEmpty(user.expert_label)){
//            iv_label.setText(String.format("%s |", user.expert_label));
//        }else {
//            iv_label.setVisibility(View.GONE);
//        }

//        if (!TextUtils.isEmpty(user.expert_info)){
//            tv_auth.setText(user.expert_info);
//        }else {
//            tv_auth.setVisibility(View.GONE);
//        }

        if (!TextUtils.isEmpty(user.label)) {
            tv_label.setText(String.format(" %s", user.label));
        } else {
            tv_label.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(user.expert_info)) {
            tv_auth.setText(user.expert_info);
        } else {
            tv_auth.setVisibility(View.GONE);
        }

//        tv_lv.setText(String.format("Lv%s", user.rank_id));
//        tv_qj.setText(String.valueOf(user.sight_count));
//        tv_cj.setText(String.valueOf(user.sight_count));
//        tv_focus.setText(String.valueOf(user.follow_count));
//        tv_fans.setText(String.valueOf(user.fans_count));
    }

    private View initPopView(int layout, String title) {
        View view = Util.inflateView(activity, layout, null);
        ((TextView) view.findViewById(R.id.tv_title)).setText(title);
        View iv_take_photo = view.findViewById(R.id.tv_take_photo);
        View iv_take_album = view.findViewById(R.id.tv_album);
        View iv_close = view.findViewById(R.id.tv_cancel);
        iv_take_photo.setOnClickListener(this);
        iv_take_album.setOnClickListener(this);
        iv_close.setOnClickListener(this);
        return view;
    }

    @Override
    protected void installListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
               switch (position){
                   case 0:
//                       viewPager.getParent().requestLayout();
                       break;
                   case 1:
//                       viewPager.getParent().requestLayout();
                       break;
                   case 2:
//                       viewPager.getParent().requestLayout();
                       break;
               }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        riv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LoginUserInfo.getLoginInfo()._id != userId) return;
                flag = EditUserInfoActivity.class.getSimpleName();
                PopupWindowUtil.show(activity, initPopView(R.layout.popup_upload_avatar, "更换头像"));
            }
        });

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_take_photo:
                PopupWindowUtil.dismiss();
                getImageFromCamera();
                break;
            case R.id.tv_album:
                PopupWindowUtil.dismiss();
                getImageFromAlbum();
                break;
            case R.id.tv_cancel:
                PopupWindowUtil.dismiss();
                break;
            case R.id.rl:
//                if (LoginUserInfo.getLoginInfo()._id != userId) return;
                flag = UserCenterActivity.class.getSimpleName();
                PopupWindowUtil.show(activity, initPopView(R.layout.popup_upload_avatar, "更换背景封面"));
                break;
//            case R.id.iv_right:
//                startActivity(new Intent(activity, EditUserInfoActivity.class));
//                break;
//            case R.id.iv_detail:
//                finish();
//                break;
//            case R.id.ll_focus:
//                intent = new Intent(activity, FocusActivity.class);
//                intent.putExtra(FocusActivity.USER_ID_EXTRA, userId);
//                startActivity(intent);
//                break;
//            case R.id.ll_fans:
//                intent = new Intent(activity, FansActivity.class);
//                intent.putExtra(FocusActivity.USER_ID_EXTRA, userId);
//                startActivity(intent);
//                break;
//            case R.id.bt_msg:
//                intent = new Intent(activity, PrivateMessageActivity.class);
//                intent.putExtra(UserCenterActivity.class.getSimpleName(), user);
//                startActivity(intent);
//                break;
//            case R.id.bt_focus:
//                bt_focus.setEnabled(false);
//                if (user.is_love == FansAdapter.NOT_LOVE) {
//                    ClientDiscoverAPI.focusOperate(userId + "", new RequestCallBack<String>() {
//                        @Override
//                        public void onSuccess(ResponseInfo<String> responseInfo) {
//                            bt_focus.setEnabled(true);
//                            if (responseInfo == null) return;
//                            if (TextUtils.isEmpty(responseInfo.result)) return;
//                            LogUtil.e("focusOperate", responseInfo.result);
//                            HttpResponse response = JsonUtil.fromJson(responseInfo.result, HttpResponse.class);
//                            if (response.isSuccess()) {
//                                user.is_love = FansAdapter.LOVE;
//                                bt_focus.setText("已关注");
//                                return;
//                            }
//                            ToastUtils.showError(response.getMessage());
//                        }
//
//                        @Override
//                        public void onFailure(HttpException e, String s) {
//                            bt_focus.setEnabled(true);
//                            ToastUtils.showError("网络异常，请确认网络畅通");
//                        }
//                    });
//                } else {
//                    ClientDiscoverAPI.cancelFocusOperate(userId + "", new RequestCallBack<String>() {
//                        @Override
//                        public void onSuccess(ResponseInfo<String> responseInfo) {
//                            bt_focus.setEnabled(true);
//                            PopupWindowUtil.dismiss();
//                            if (responseInfo == null) return;
//                            if (TextUtils.isEmpty(responseInfo.result)) return;
//                            LogUtil.e("cancelFocusOperate", responseInfo.result);
//                            HttpResponse response = JsonUtil.fromJson(responseInfo.result, HttpResponse.class);
//                            if (response.isSuccess()) {
//                                user.is_love = FansAdapter.NOT_LOVE;
//                                bt_focus.setText("关注");
//                                return;
//                            }
//                            ToastUtils.showError(response.getMessage());
//                        }
//
//                        @Override
//                        public void onFailure(HttpException e, String s) {
//                            bt_focus.setEnabled(true);
//                            PopupWindowUtil.dismiss();
//                            ToastUtils.showError("网络异常，请确认网络畅通");
//                        }
//                    });
//                }
//                break;
//            case R.id.ibtn:
//                Util.makeToast("认证");
//                break;
//            case R.id.ll_cj:
//                if (which == MineFragment.REQUEST_CJ) return;
//                showCj();
//                break;
//            case R.id.ll_qj:
//                if (which == MineFragment.REQUEST_CJ) return;
//                showCj();
//                break;
        }
    }

    protected void getImageFromAlbum() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Picker.from(this)
                    .count(1)
                    .enableCamera(false)
                    .singleChoice()
                    .setEngine(new ImageLoaderEngine())
                    .forResult(REQUEST_CODE_PICK_IMAGE);
        } else {
            ToastUtils.showError("未检测到SD卡");
//            dialog.showErrorWithStatus("未检测到SD卡");
        }
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");//相片类型
//        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    protected void getImageFromCamera() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQUEST_CODE_CAPTURE_CAMERA);
        } else {
            ToastUtils.showError("未检测到SD卡");
//            dialog.showErrorWithStatus("未检测到SD卡");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_PICK_IMAGE:
//                    Uri uri = data.getData();
                    mSelected = PicturePickerUtils.obtainResult(data);
                    if (mSelected == null) return;
                    if (mSelected.size() == 0) return;
//                    if (uri != null) {
//                        Bitmap bitmap = ImageUtils.decodeUriAsBitmap(uri);
//                        mClipImageLayout.setImageBitmap(bitmap);
                    toCropActivity(mSelected.get(0));
//                    } else {
//                        Util.makeToast("抱歉，从相册获取图片失败");
//                    }
                    break;
                case REQUEST_CODE_CAPTURE_CAMERA:
//                    Bitmap bitmap =ImageUtils.decodeUriAsBitmap(imageUri);
                    if (imageUri != null) {
//                        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,MediaStore.Images.Media.EXTERNAL_CONTENT_URI));
                        toCropActivity(imageUri);
                    }
                    break;
            }
        }
    }

    private void toCropActivity(Uri uri) {
        Intent intent = new Intent(activity, ImageCropActivity.class);
        intent.putExtra(ImageCropActivity.class.getSimpleName(), uri);
        intent.putExtra(ImageCropActivity.class.getName(), flag);
        startActivity(intent);
    }

}
