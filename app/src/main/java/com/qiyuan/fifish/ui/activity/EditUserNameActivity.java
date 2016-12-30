package com.qiyuan.fifish.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.OnClick;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.bean.SuccessBean;
import com.qiyuan.fifish.bean.UserProfile;
import com.qiyuan.fifish.network.CustomCallBack;
import com.qiyuan.fifish.network.RequestService;
import com.qiyuan.fifish.ui.view.CustomHeadView;
import com.qiyuan.fifish.ui.view.WaitingDialog;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.JsonUtil;
import com.qiyuan.fifish.util.ToastUtils;


/**
 * @author lilin
 *         created at 2016/4/27 13:11
 */
public class EditUserNameActivity extends BaseActivity {
    @BindView(R.id.head_view)
    CustomHeadView head_view;
    @BindView(R.id.et_nickname)
    EditText et_nickname;
    private UserProfile user;
    private WaitingDialog dialog;
    @BindView(R.id.iv_clear)
    ImageButton iv_clear;
    public EditUserNameActivity() {
        super(R.layout.activity_modify_nickname);
    }

    @Override
    protected void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey(UserProfile.class.getSimpleName())) {
                user = (UserProfile) bundle.getSerializable(UserProfile.class.getSimpleName());
            }
        }
    }

    @Override
    protected void initViews() {
        dialog=new WaitingDialog(activity);
        head_view.setHeadCenterTxtShow(true, R.string.title_modify_nickname);
        head_view.setHeadRightTxtShow(true, R.string.complete);
        if (user != null) {
            if (!TextUtils.isEmpty(user.data.username)&&!TextUtils.equals("null",user.data.username))
            et_nickname.setText(user.data.username);
        }
    }

    @Override
    protected void installListener() {
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

    @OnClick({R.id.tv_head_right, R.id.iv_clear})
    protected void submit(View v) {
        switch (v.getId()) {
            case R.id.tv_head_right:
                if (!TextUtils.isEmpty(et_nickname.getText().toString().trim())) {
                    submitData();
                } else {
                    ToastUtils.showInfo(R.string.please_input);
                }
                break;
            case R.id.iv_clear:
                et_nickname.getText().clear();
                iv_clear.setVisibility(View.GONE);
                break;
        }
    }


    protected void submitData() {
        final String nickName = et_nickname.getText().toString();
        EditUserInfoActivity.isSubmitAddress = false;
        if (TextUtils.isEmpty(nickName)){
            ToastUtils.showInfo(R.string.please_input);
            return;
        }
        String key="summary";
        RequestService.updateUserInfo(key,nickName,new CustomCallBack(){
            @Override
            public void onStarted() {
                if (dialog!=null&&!activity.isFinishing()) dialog.show();
            }

            @Override
            public void onSuccess(String result) {
                SuccessBean successBean = JsonUtil.fromJson(result, SuccessBean.class);
                if (successBean.meta.status_code== Constants.HTTP_OK){
                    ToastUtils.showSuccess(R.string.update_success);
                    user.data.username= nickName;
                    Intent intent = new Intent();
                    intent.putExtra(UserProfile.class.getSimpleName(),user);
                    setResult(RESULT_OK,intent);
                    finish();
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
}
