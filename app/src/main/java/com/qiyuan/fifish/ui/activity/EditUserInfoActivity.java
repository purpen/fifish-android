package com.qiyuan.fifish.ui.activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.album.ImageLoaderEngine;
import com.qiyuan.fifish.album.Picker;
import com.qiyuan.fifish.album.PicturePickerUtils;
import com.qiyuan.fifish.bean.SuccessBean;
import com.qiyuan.fifish.bean.UserProfile;
import com.qiyuan.fifish.network.CustomCallBack;
import com.qiyuan.fifish.network.RequestService;
import com.qiyuan.fifish.ui.view.*;
import com.qiyuan.fifish.ui.view.wheelview.StringWheelAdapter;
import com.qiyuan.fifish.ui.view.wheelview.WheelView;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.JsonUtil;
import com.qiyuan.fifish.util.PopupWindowUtil;
import com.qiyuan.fifish.util.ProvinceUtil;
import com.qiyuan.fifish.util.ToastUtils;
import com.qiyuan.fifish.util.Util;
import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @author lilin
 *         created at 2016/4/26 18:50
 */
public class EditUserInfoActivity extends BaseActivity {
    @BindView(R.id.head_view)
    CustomHeadView head_view;
    @BindView(R.id.custom_user_avatar)
    CustomItemLayout custom_user_avatar;
    @BindView(R.id.custom_nick_name)
    CustomItemLayout custom_nick_name;
    @BindView(R.id.custom_signature)
    CustomItemLayout custom_signature;

    @BindView(R.id.custom_area)
    CustomItemLayout custom_area;

    @BindView(R.id.custom_user_sex)
    CustomItemLayout custom_user_sex;
    @BindView(R.id.custom_user_birthday)
    CustomItemLayout custom_user_birthday;

    @BindView(R.id.custom_code)
    CustomItemLayout custom_code;

    @BindView(R.id.custom_auth)
    CustomItemLayout custom_auth;
    private UserProfile user;

    private Bitmap bitmap;
    private static final int REQUEST_CODE_PICK_IMAGE = 100;
    private static final int REQUEST_CODE_CAPTURE_CAMERA = 101;
    public static final Uri imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "temp.jpg"));
    private static final int REQUEST_NICK_NAME = 3;
    private static final int REQUEST_SIGNATURE = 4;
    private static final int SECRET = 0;
    private static final int MAN = 1;
    private static final int WOMAN = 2;
    private String key;
    private String value;
    public static boolean isSubmitAddress = false;
    private WaitingDialog dialog;
    private String[] genderArr;
    public EditUserInfoActivity() {
        super(R.layout.activity_edit_user_info);
    }

    @Override
    protected void initViews() {
        head_view.setHeadCenterTxtShow(true, R.string.personal_data);
        genderArr = getResources().getStringArray(R.array.user_gender);
        dialog = new WaitingDialog(this);
        custom_user_avatar.setUserAvatar(null);
        custom_user_avatar.setTVStyle(0, R.string.user_avatar, R.color.color_333);
        custom_nick_name.setTVStyle(0, R.string.nick_name, R.color.color_333);
        custom_signature.setTVStyle(0, R.string.user_signature, R.color.color_333);
//        custom_signature.setTvArrowLeftStyle(true, R.string.input_signature);
        custom_user_sex.setTVStyle(0, R.string.user_sex, R.color.color_333);
        custom_user_sex.setTvArrowLeftStyle(true, R.string.select_gender);
//        custom_user_birthday.setTVStyle(0, R.string.user_birthday, R.color.color_333);
//        custom_user_birthday.setTvArrowLeftStyle(true, R.string.select_birth);
        custom_area.setTVStyle(0, R.string.user_area, R.color.color_333);
        custom_area.setTvArrowLeftStyle(true, R.string.select_city);
//        custom_code.setTVStyle(0, R.string.user_code, R.color.color_333);
//        custom_code.setIvArrowLeftShow(true);
//        custom_auth.setTVStyle(0, R.string.user_auth, R.color.color_333);
    }

    @OnClick({R.id.custom_nick_name, R.id.custom_user_avatar, R.id.custom_area, R.id.custom_signature, R.id.custom_auth, R.id.custom_user_sex, R.id.custom_user_birthday, R.id.custom_code})
    void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.custom_user_avatar:
                PopupWindowUtil.show(activity, initPopView(R.layout.popup_upload_avatar,getString(R.string.update_avatar)));
                break;
            case R.id.custom_nick_name:
                intent = new Intent(activity, EditUserNameActivity.class);
                intent.putExtra(UserProfile.class.getSimpleName(), user);
                startActivityForResult(intent, REQUEST_NICK_NAME);
                break;
            case R.id.custom_signature:
                intent = new Intent(activity, EditUserSignatureActivity.class);
                intent.putExtra(UserProfile.class.getSimpleName(), user);
                startActivityForResult(intent, REQUEST_SIGNATURE);
                break;
            case R.id.custom_area:
                PopupWindowUtil.show(activity, initAddressPopView(R.layout.popup_address_layout, R.string.select_address, ProvinceUtil.getProvinces()));
                //TODO 选择区域
                break;
            case R.id.custom_user_birthday:
                PopupWindowUtil.show(activity, initPopView(R.layout.popup_birth_layout, R.string.select_birth));
                break;
            case R.id.custom_user_sex:
                PopupWindowUtil.show(activity, initPopView(R.layout.popup_gender_layout, R.string.select_gender, Arrays.asList(getResources().getStringArray(R.array.user_gender))));
                break;
            case R.id.custom_code:
//                intent= new Intent(activity, MyBarCodeActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("url",user.medium_avatar_url);
//                bundle.putString("nickName",user.nickname);
//                bundle.putString("sex",user.sex+"");
//                bundle.putSerializable("areas",user.areas);
//                intent.putExtra(MyBarCodeActivity.class.getSimpleName(),bundle);
//                startActivity(intent);
                break;
            case R.id.custom_auth:
//                startActivity(new Intent(activity,CertificateStatusActivity.class));
                break;
        }
    }

    private View initPopView(int layout, int resId, List<String> list) {
        View view = Util.inflateView(activity, layout, null);
        View tv_cancel_select = view.findViewById(R.id.tv_cancel_select);
        View tv_confirm_select = view.findViewById(R.id.tv_confirm_select);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        WheelView wv_province = (WheelView) view.findViewById(R.id.custom_wv);
        wv_province.setAdapter(new StringWheelAdapter(list));
        wv_province.setVisibleItems(5);
        tv_title.setText(resId);
        setClickListener(tv_cancel_select, resId, wv_province, list);
        setClickListener(tv_confirm_select, resId, wv_province, list);
        return view;
    }

    private View initAddressPopView(int layout, int resId, List<String> list) {
        View view = Util.inflateView(activity, layout, null);
        View tv_cancel_select = view.findViewById(R.id.tv_cancel_select);
        View tv_confirm_select = view.findViewById(R.id.tv_confirm_select);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        CustomAddressSelectView addressView = (CustomAddressSelectView) view.findViewById(R.id.custom_address_select);
//        wv_province.setAdapter(new StringWheelAdapter(list));
//        wv_province.setVisibleItems(5);
        tv_title.setText(resId);
        setClickListener(tv_cancel_select, addressView);
        setClickListener(tv_confirm_select, addressView);
        return view;
    }

    private View initPopView(int layout, String title) {
        View view = Util.inflateView(activity, layout, null);
        ((TextView) view.findViewById(R.id.tv_title)).setText(title);
        View iv_take_photo = view.findViewById(R.id.tv_take_photo);
        View iv_take_album = view.findViewById(R.id.tv_album);
        View iv_close = view.findViewById(R.id.tv_cancel);
        iv_take_photo.setOnClickListener(onClickListener);
        iv_take_album.setOnClickListener(onClickListener);
        iv_close.setOnClickListener(onClickListener);
        return view;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_take_photo:
                    PopupWindowUtil.dismiss();
                    getImageFromCamera();
                    break;
                case R.id.tv_album:
                    PopupWindowUtil.dismiss();
                    getImageFromAlbum();
                    break;
                case R.id.tv_cancel:
                default:
                    PopupWindowUtil.dismiss();
                    break;
            }
        }
    };

    @Override
    protected void requestNet() {
        ProvinceUtil.init();
        RequestService.getUserProfile(new CustomCallBack() {
            @Override
            public void onStarted() {
                if (dialog != null && !activity.isFinishing()) dialog.show();
            }

            @Override
            public void onSuccess(String result) {
                user= JsonUtil.fromJson(result, UserProfile.class);
                if (user.meta.status_code == Constants.HTTP_OK) {
                    refreshUI();
                    return;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
            }

            @Override
            public void onFinished() {
                if (dialog != null && !activity.isFinishing()) dialog.dismiss();
            }
        });
    }

    private View initPopView(int layout, int resId) {
        View view = Util.inflateView(activity, layout, null);
        View tv_cancel_select = view.findViewById(R.id.tv_cancel_select);
        View tv_confirm_select = view.findViewById(R.id.tv_confirm_select);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        CustomBirthdaySelectView custom_birth_select = (CustomBirthdaySelectView) view.findViewById(R.id.custom_birth_select);
        tv_title.setText(resId);
        setClickListener(tv_confirm_select, custom_birth_select);
        setClickListener(tv_cancel_select, custom_birth_select);
        return view;
    }

    /**
     * 确认选择地址
     *
     * @param view
     * @param casv
     */
    private void setClickListener(View view, final CustomAddressSelectView casv) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_confirm_select:
                        isSubmitAddress = true;
                        String addr = casv.getAddress();
//                        LogUtil.e(TAG,addr.split("\\s")[0]);
//                        LogUtil.e(TAG,addr.split("\\s")[1]);
                        if (TextUtils.isEmpty(addr)) {
                            return;
                        }
                        key = "zone";
                        value = ProvinceUtil.getCityIdByName(addr.split("\\s")[1]) + "";
                        submitData();
                        custom_area.setTvArrowLeftStyle(true, addr, R.color.color_333);
                    case R.id.tv_cancel_select:
                    default:
                        PopupWindowUtil.dismiss();
                        break;
                }
            }
        });
    }


    private void setClickListener(View view, final CustomBirthdaySelectView cbsv) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_confirm_select:
                        isSubmitAddress = false;
                        key = "birthday";
                        value = cbsv.getBithday();
                        submitData();
                        custom_user_birthday.setTvArrowLeftStyle(true, cbsv.getBithday(), R.color.color_333);
                    case R.id.tv_cancel_select:
                    default:
                        PopupWindowUtil.dismiss();
                        break;
                }
            }
        });
    }

    private void setClickListener(View view, final int id, final WheelView wheelView, final List<String> list) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_cancel_select:
                        PopupWindowUtil.dismiss();
                        break;
                    case R.id.tv_confirm_select:
                        switch (id) {
                            case R.string.select_gender:
                                isSubmitAddress = false;
                                String sex = list.get(wheelView.getCurrentItem());
                                key = "sex";
                                if (TextUtils.equals(genderArr[0], sex)) {
                                    value = String.valueOf(SECRET);
                                } else if (TextUtils.equals(genderArr[1], sex)) {
                                    value = String.valueOf(MAN);
                                } else {
                                    value = String.valueOf(WOMAN);
                                }
                                submitData();
                                custom_user_sex.setTvArrowLeftStyle(true, sex, R.color.color_333);
                                break;
                        }
                        PopupWindowUtil.dismiss();
                        break;
                    default:
                        PopupWindowUtil.dismiss();
                        break;
                }
            }
        });
    }

    protected void submitData() {
        RequestService.updateUserInfo(key,value,new CustomCallBack(){
            @Override
            public void onStarted() {
                if (dialog!=null&&!activity.isFinishing()) dialog.show();
            }

            @Override
            public void onSuccess(String result) {
                SuccessBean successBean = JsonUtil.fromJson(result, SuccessBean.class);
                if (successBean.meta.status_code== Constants.HTTP_OK){
                    ToastUtils.showSuccess(R.string.update_success);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                ToastUtils.showError(R.string.request_error);
            }

            @Override
            public void onFinished() {
                if (dialog!=null&&!activity.isFinishing()) dialog.dismiss();
            }
        });
    }

    @Override
    protected void installListener() {
    }

    @Override
    protected void refreshUI() {
        if (user != null) {
            if (!TextUtils.isEmpty(user.data.avatar.small) &&!TextUtils.equals("null",user.data.avatar.small)) {
                ImageLoader.getInstance().displayImage(user.data.avatar.small,custom_user_avatar.getAvatarIV());
            }
//            custom_user_name.setTvArrowLeftStyle(true, userLogin.user_name, R.color.color_333);
//            custom_user_name.sertTVRightTxt(userLogin.user_name);
//            custom_user_name.setRightMoreImgStyle(false);
            if (!TextUtils.isEmpty(user.data.username)) {
                custom_nick_name.setTvArrowLeftStyle(true, user.data.username, R.color.color_333);
            }

//            if (TextUtils.isEmpty(user.expert_info)){
//                custom_auth.setTvArrowLeftStyle(true,"",R.color.color_333);
//            }else {
//                custom_auth.setTvArrowLeftStyle(true,user.expert_info,R.color.color_333);
//            }

            setLabelSignatrue();

            if (!TextUtils.isEmpty(user.data.zone) && !TextUtils.equals("null",user.data.zone)) {
                custom_area.setTvArrowLeftStyle(true,user.data.zone, R.color.color_333);
            }

            switch (user.data.sex) {
                case SECRET:
                    custom_user_sex.setTvArrowLeftStyle(true, "保密", R.color.color_333);
                    break;
                case MAN:
                    custom_user_sex.setTvArrowLeftStyle(true, "男", R.color.color_333);
                    break;
                case WOMAN:
                    custom_user_sex.setTvArrowLeftStyle(true, "女", R.color.color_333);
                    break;
            }

//            if (!TextUtils.isEmpty(user.birthday)) {
//                custom_user_birthday.setTvArrowLeftStyle(true, user.birthday, R.color.color_333);
//            }

        }
    }

    private void setLabelSignatrue() {
        if (!TextUtils.isEmpty(user.data.summary) && !TextUtils.equals("null",user.data.summary)){
            custom_signature.setTvArrowLeftStyle(true, user.data.summary, R.color.color_333);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_NICK_NAME:
                    user = (UserProfile) data.getSerializableExtra(UserProfile.class.getSimpleName());
                    custom_nick_name.setTvArrowLeftStyle(true, user.data.username, R.color.color_333);
                    break;
                case REQUEST_SIGNATURE:
                    user = (UserProfile) data.getSerializableExtra(UserProfile.class.getSimpleName());
//                    custom_signature.setTvArrowLeftStyle(true,user.summary,R.color.color_333);
                    setLabelSignatrue();
                    break;
                case REQUEST_CODE_PICK_IMAGE:
                    List<Uri> mSelected = PicturePickerUtils.obtainResult(data);
                    if (mSelected == null) return;
                    if (mSelected.size() == 0) return;
                    toCropActivity(mSelected.get(0));
//                    Uri uri = data.getData();
//                    if (uri != null) {
//                        toCropActivity(uri);
//                    } else {
//                        Util.makeToast("抱歉，从相册获取图片失败");
//                    }
                    break;
                case REQUEST_CODE_CAPTURE_CAMERA:
                    if (imageUri != null) {
                        toCropActivity(imageUri);
                    }
                    break;
            }
        }
    }


    protected void getImageFromAlbum() {
        Picker.from(this)
                .count(1)
                .enableCamera(false)
                .singleChoice()
                .setEngine(new ImageLoaderEngine())
                .forResult(REQUEST_CODE_PICK_IMAGE);
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
            Util.makeToast("请确认已经插入SD卡");
        }
    }

    private void toCropActivity(Uri uri) {
        ImageCropActivity.setOnClipCompleteListener(new ImageCropActivity.OnClipCompleteListener() {
            @Override
            public void onClipComplete(Bitmap bitmap) {
                EditUserInfoActivity.this.bitmap = bitmap;
                custom_user_avatar.getAvatarIV().setImageBitmap(bitmap);
            }
        });
        Intent intent = new Intent(activity, ImageCropActivity.class);
        intent.putExtra(ImageCropActivity.class.getSimpleName(), uri);
        intent.putExtra(ImageCropActivity.class.getName(), TAG);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        if (bitmap != null) bitmap.recycle();
        super.onDestroy();
    }
}
