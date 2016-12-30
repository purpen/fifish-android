package com.qiyuan.fifish.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.qiyuan.fifish.R;
import com.qiyuan.fifish.bean.SuccessBean;
import com.qiyuan.fifish.bean.UserProfile;
import com.qiyuan.fifish.network.CustomCallBack;
import com.qiyuan.fifish.network.RequestService;
import com.qiyuan.fifish.ui.view.CustomHeadView;
import com.qiyuan.fifish.ui.view.WaitingDialog;
import com.qiyuan.fifish.ui.view.labelview.AutoLabelUI;
import com.qiyuan.fifish.ui.view.labelview.Label;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.JsonUtil;
import com.qiyuan.fifish.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author lilin
 * created at 2016/4/28 16:36
 */
public class EditUserSignatureActivity extends BaseActivity{
    @BindView(R.id.head_view)
    CustomHeadView head_view;
    @BindView(R.id.et_nickname)
    EditText et_nickname;
    private UserProfile user;
    @BindView(R.id.label_view)
    AutoLabelUI label_view;
    @BindView(R.id.tv_tag)
    TextView tv_tag;
    @BindView(R.id.tv_hint)
    TextView tv_hint;
    @BindView(R.id.iv_clear)
    ImageButton iv_clear;
    private WaitingDialog dialog;
    public EditUserSignatureActivity() {
        super(R.layout.activity_edit_signatrue);
    }

    @Override
    protected void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            if (bundle.containsKey(UserProfile.class.getSimpleName())) {
                user = (UserProfile) bundle.getSerializable(UserProfile.class.getSimpleName());
            }
        }
    }

    @Override
    protected void initViews() {
        dialog = new WaitingDialog(activity);
        head_view.setHeadCenterTxtShow(true,R.string.user_signature);
        head_view.setHeadRightTxtShow(true, R.string.complete);
//        String[] stringArray = getResources().getStringArray(R.array.user_tags);
//        for (String aStringArray : stringArray) {
//            label_view.addLabel(aStringArray);
//        }
        if (user!=null){
            if (!TextUtils.isEmpty(user.data.summary)&& !TextUtils.equals("null",user.data.summary)){
                et_nickname.setText(user.data.summary);
                iv_clear.setVisibility(View.VISIBLE);
            }
//            if (!TextUtils.isEmpty(user.label)){
//                tv_tag.setText(user.label);
//                tv_tag.setVisibility(View.VISIBLE);
//                tv_hint.setVisibility(View.GONE);
//                iv_clear.setVisibility(View.VISIBLE);
//            }else {
//                tv_hint.setVisibility(View.VISIBLE);
//            }
        }
    }

    @Override
    protected void installListener() {
//        label_view.setOnLabelClickListener(new AutoLabelUI.OnLabelClickListener() {
//            @Override
//            public void onClickLabel(Label labelClicked) {
//                tv_tag.setVisibility(View.VISIBLE);
//                tv_tag.setText(labelClicked.getText());
//                tv_hint.setVisibility(View.GONE);
//                iv_clear.setVisibility(View.VISIBLE);
//            }
//        });
        et_nickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String keyWord = charSequence.toString().trim();
                if (!TextUtils.isEmpty(keyWord)) {
                    iv_clear.setVisibility(View.VISIBLE);
                } else {
                    iv_clear.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @OnClick({R.id.tv_head_right,R.id.iv_clear})
    void performClick(View view){
        switch (view.getId()){
            case R.id.tv_head_right:
                submitData();
                break;
            case R.id.iv_clear:
//                tv_tag.setVisibility(View.GONE);
                et_nickname.setText("");
                iv_clear.setVisibility(View.GONE);
                break;
        }
    }


    protected void submitData() {
//        final String label=tv_tag.getText().toString().trim();
        final String signature=et_nickname.getText().toString().trim();
        if (TextUtils.isEmpty(signature)){
            ToastUtils.showInfo(R.string.please_input);
            return;
        }
        RequestService.updateSignature(signature,new CustomCallBack(){
            @Override
            public void onStarted() {
                if (dialog!=null&&!activity.isFinishing()) dialog.show();
            }

            @Override
            public void onSuccess(String result) {
                SuccessBean successBean = JsonUtil.fromJson(result, SuccessBean.class);
                if (successBean.meta.status_code== Constants.HTTP_OK){
                    ToastUtils.showSuccess(R.string.update_success);
                    user.data.summary= signature;
                    Intent intent = new Intent();
                    intent.putExtra(UserProfile.class.getSimpleName(),user);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtils.showError(R.string.request_error);
                ex.printStackTrace();
            }

            @Override
            public void onFinished() {
                if (dialog!=null&&!activity.isFinishing()) dialog.dismiss();
            }
        });
    }
}
