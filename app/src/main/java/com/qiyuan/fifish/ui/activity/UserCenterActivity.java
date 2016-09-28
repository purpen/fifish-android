package com.qiyuan.fifish.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.adapter.UserCenterViewPagerAdapter;
import com.qiyuan.fifish.album.ImageLoaderEngine;
import com.qiyuan.fifish.album.Picker;
import com.qiyuan.fifish.album.PicturePickerUtils;
import com.qiyuan.fifish.bean.LoginUserInfo;
import com.qiyuan.fifish.bean.UserProfile;
import com.qiyuan.fifish.interfaces.ScrollTabHolder;
import com.qiyuan.fifish.network.CustomCallBack;
import com.qiyuan.fifish.network.RequestService;
import com.qiyuan.fifish.ui.fragment.FansFragment;
import com.qiyuan.fifish.ui.fragment.FocusFragment;
import com.qiyuan.fifish.ui.fragment.MineFragment;
import com.qiyuan.fifish.ui.fragment.ProductsFragment;
import com.qiyuan.fifish.ui.view.CustomViewPager;
import com.qiyuan.fifish.ui.view.WaitingDialog;
import com.qiyuan.fifish.ui.view.roundImageView.RoundedImageView;
import com.qiyuan.fifish.util.*;

import java.io.File;
import java.util.List;

/**
 * @author lilin
 *         created at 2016/4/26 17:43
 */
public class UserCenterActivity extends BaseActivity implements ScrollTabHolder, View.OnClickListener, ViewPager.OnPageChangeListener {
    @BindView(R.id.viewPager)
    CustomViewPager viewPager;
    @BindView(R.id.header)
    View mHeader;
    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.riv)
    RoundedImageView riv;
    @BindView(R.id.riv_auth)
    RoundedImageView rivAuth;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_summary)
    TextView tvSummary;
    @BindView(R.id.tv_products_num)
    TextView tvProductsNum;
    @BindView(R.id.tv_focus_num)
    TextView tvFocusNum;
    @BindView(R.id.tv_fans_num)
    TextView tvFansNum;
    @BindView(R.id.ll_products)
    LinearLayout llProducts;
    @BindView(R.id.ll_focus)
    LinearLayout llFocus;
    @BindView(R.id.ll_fans)
    LinearLayout llFans;
    @BindView(R.id.tv_products)
    TextView tvProducts;
    @BindView(R.id.tv_focus)
    TextView tvFocus;
    @BindView(R.id.tv_fans)
    TextView tvFans;
    private LoginUserInfo user;
    private List<Uri> mSelected;
    private String userId;
    private static final int REQUEST_CODE_PICK_IMAGE = 100;
    private static final int REQUEST_CODE_CAPTURE_CAMERA = 101;
    private WaitingDialog dialog;
    public static final Uri imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "temp.jpg"));
    private String flag;
    private int mMinHeaderHeight;
    private int mHeaderHeight;
    private int mMinHeaderTranslation;
    private UserCenterViewPagerAdapter mPagerAdapter;
    private Fragment[] fragments = new Fragment[3];
    private UserProfile userInfo;
    private int color_2187ff;
    public UserCenterActivity() {
        super(R.layout.activity_user_center);
    }

    @Override
    protected void getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra(MineFragment.class.getSimpleName())) {
//            which = intent.getIntExtra(MineFragment.class.getSimpleName(), MineFragment.REQUEST_CJ);
        }

        if (intent.hasExtra(Constants.USER_ID)) {
            userId = intent.getStringExtra(Constants.USER_ID);
        } else {
            userId = UserProfile.getUserId();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        resetData();
        requestNet();
    }

    private void resetData() {

    }

    @Override
    protected void initViews() {
        color_2187ff=getResources().getColor(R.color.color_2187ff);
        tvProductsNum.setTextColor(color_2187ff);
        tvProducts.setTextColor(color_2187ff);
        mMinHeaderHeight = getResources().getDimensionPixelSize(R.dimen.dp225);
        mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.dp280);
        mMinHeaderTranslation = -mMinHeaderHeight;
        fragments[0] = ProductsFragment.newInstance(0, userId);
        fragments[1] = FocusFragment.newInstance(1, userId);
        fragments[2] = FansFragment.newInstance(2, userId);
        mPagerAdapter = new UserCenterViewPagerAdapter(getSupportFragmentManager(), fragments);
        mPagerAdapter.setTabHolderScrollingContent(this);
        viewPager.setAdapter(mPagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(this);
        dialog = new WaitingDialog(this);
    }

    @Override
    protected void requestNet() {
        RequestService.getUserProfile(new CustomCallBack() {
            @Override
            public void onSuccess(String result) {
                if (TextUtils.isEmpty(result)) return;
                userInfo = JsonUtil.fromJson(result, UserProfile.class);
                if (userInfo.meta.status_code == Constants.HTTP_OK) {
                    refreshUI();
                    return;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                ToastUtils.showError(R.string.request_error);
            }
        });
    }

    public CustomViewPager getViewPager() {
        if (viewPager == null) return null;
        return viewPager;
    }

    @Override
    public void adjustScroll(int scrollHeight) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition) {
        if (viewPager.getCurrentItem() == pagePosition) {
            int scrollY = getScrollY(view);
//            Log.e("-scrollY", -scrollY + "");
//            Log.e("mMinHeaderTranslation", "" + mMinHeaderTranslation);
//            Log.e("Math.max()", Math.max(-scrollY, mMinHeaderTranslation) + "");
//            mHeader.setTranslationY(Math.max(-scrollY, mMinHeaderTranslation));
            mHeader.setTranslationY(-scrollY);
        }
    }

    public int getScrollY(AbsListView view) {
        View c = view.getChildAt(0);
        if (c == null) {
            return 0;
        }
        int firstVisiblePosition = view.getFirstVisiblePosition();
        int top = c.getTop();

        int headerHeight = 0;
        if (firstVisiblePosition >= 1) {
            headerHeight = mHeaderHeight;
        }

        return -top + firstVisiblePosition * c.getHeight() + headerHeight;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffsetPixels > 0) {
            int currentItem = viewPager.getCurrentItem();

            SparseArrayCompat<ScrollTabHolder> scrollTabHolders = mPagerAdapter.getScrollTabHolders();
            ScrollTabHolder currentHolder;

            if (position < currentItem) {
                currentHolder = scrollTabHolders.valueAt(position);
            } else {
                currentHolder = scrollTabHolders.valueAt(position + 1);
            }
            currentHolder.adjustScroll((int) (mHeader.getHeight() + mHeader.getTranslationY()));
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                resetUI();
                tvProductsNum.setTextColor(color_2187ff);
                tvProducts.setTextColor(color_2187ff);
                viewPager.setCurrentItem(0, true);
                break;
            case 1:
                resetUI();
                tvFocusNum.setTextColor(color_2187ff);
                tvFocus.setTextColor(color_2187ff);
                break;
            case 2:
                resetUI();
                tvFansNum.setTextColor(color_2187ff);
                tvFans.setTextColor(color_2187ff);
                break;
        }
        SparseArrayCompat<ScrollTabHolder> scrollTabHolders = mPagerAdapter.getScrollTabHolders();
        ScrollTabHolder currentHolder = scrollTabHolders.valueAt(position);
        currentHolder.adjustScroll((int) (mHeader.getHeight() + mHeader.getTranslationY()));
    }

    @Override
    protected void refreshUI() {
        if (userInfo == null) return;
        ImageLoader.getInstance().displayImage(userInfo.data.avatar.large,riv);
        ImageLoader.getInstance().displayImage("bg", ivBg);
        tvName.setText(userInfo.data.username);
//        tvAddress.setText(userInfo.data.zone);
        tvAddress.setText("北京朝阳");
        tvFocusNum.setText(userInfo.data.follow_count);
        tvFansNum.setText(userInfo.data.fans_count);
        tvProductsNum.setText(userInfo.data.stuff_count);
        if (userInfo.data.summary != null) {
            if (!TextUtils.isEmpty(userInfo.data.summary.toString())) {
                tvSummary.setText(userInfo.data.summary.toString());
            }
        } else {
            tvSummary.setText("人生是场大设计!");
        }
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
        llProducts.setOnClickListener(this);
        llFocus.setOnClickListener(this);
        llFans.setOnClickListener(this);
        riv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.equals(UserProfile.getUserId(),userId)) return;
                flag = EditUserInfoActivity.class.getSimpleName();
                PopupWindowUtil.show(activity, initPopView(R.layout.popup_upload_avatar, "更换头像"));
            }
        });

    }

    private void resetUI() {
        int color = getResources().getColor(android.R.color.white);
        tvProductsNum.setTextColor(color);
        tvProducts.setTextColor(color);
        tvFocusNum.setTextColor(color);
        tvFocus.setTextColor(color);
        tvFansNum.setTextColor(color);
        tvFans.setTextColor(color);
    }

    @Override
    public void onClick(View view) {
        int color = getResources().getColor(R.color.color_2187ff);
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_products:
                resetUI();
                tvProductsNum.setTextColor(color);
                tvProducts.setTextColor(color);
                viewPager.setCurrentItem(0, true);
                break;
            case R.id.ll_focus:
                resetUI();
                tvFocusNum.setTextColor(color);
                tvFocus.setTextColor(color);
                viewPager.setCurrentItem(1, true);
                break;
            case R.id.ll_fans:
                resetUI();
                tvFansNum.setTextColor(color);
                tvFans.setTextColor(color);
                viewPager.setCurrentItem(2, true);
                break;
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
                if (!TextUtils.equals(UserProfile.getUserId(),userId)) return;
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
        }
    }

    protected void getImageFromCamera() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQUEST_CODE_CAPTURE_CAMERA);
        } else {
            ToastUtils.showError("未检测到SD卡");
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
                        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,MediaStore.Images.Media.EXTERNAL_CONTENT_URI));
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
