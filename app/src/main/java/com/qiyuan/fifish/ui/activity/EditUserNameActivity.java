package com.qiyuan.fifish.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.OnClick;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.bean.LoginUserInfo;
import com.qiyuan.fifish.ui.view.CustomHeadView;
import com.qiyuan.fifish.util.ToastUtils;

import java.util.HashMap;

/**
 * @author lilin
 *         created at 2016/4/27 13:11
 */
public class EditUserNameActivity extends BaseActivity {
    @BindView(R.id.head_view)
    CustomHeadView head_view;
    private HashMap hashMap;
    @BindView(R.id.et_nickname)
    EditText et_nickname;
    private LoginUserInfo user;

    public EditUserNameActivity() {
        super(R.layout.activity_modify_nickname);
    }

    @Override
    protected void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey(LoginUserInfo.class.getSimpleName())) {
                user = (LoginUserInfo) bundle.getSerializable(LoginUserInfo.class.getSimpleName());
            }
        }
    }

    @Override
    protected void initViews() {
        head_view.setHeadCenterTxtShow(true, R.string.title_modify_nickname);
        head_view.setHeadRightTxtShow(true, R.string.save);
        if (user != null) {
            et_nickname.setText(user.nickname);
        }
    }

    @OnClick({R.id.tv_head_right, R.id.ibtn})
    protected void submit(View v) {
        switch (v.getId()) {
            case R.id.tv_head_right:
                if (!TextUtils.isEmpty(et_nickname.getText().toString().trim())) {
                    submitData();
                } else {
                    ToastUtils.showInfo("请先填写昵称");
                }
                break;
            case R.id.ibtn:
                et_nickname.getText().clear();
                break;
        }
    }


    protected void submitData() {
        final String nickName = et_nickname.getText().toString();
        EditUserInfoActivity.isSubmitAddress = false;
//        ClientDiscoverAPI.updateUserInfo("nickname", nickName, new RequestCallBack<String>() {
//            @Override
//            public void onSuccess(ResponseInfo<String> responseInfo) {
//                if (responseInfo == null) {
//                    return;
//                }
//
//                if (TextUtils.isEmpty(responseInfo.result)) {
//                    return;
//                }
//
//                HttpResponse response = JsonUtil.fromJson(responseInfo.result, HttpResponse.class);
//
//                if (response.isSuccess()) {
//                    Util.makeToast(response.getMessage());
//                    Intent intent = new Intent();
//                    user.nickname = nickName;
//                    intent.putExtra(Friends.class.getSimpleName(), user);
//                    setResult(RESULT_OK, intent);
//                    finish();
//                    return;
//                }
//
//                Util.makeToast(response.getMessage());
//            }
//
//            @Override
//            public void onFailure(HttpException e, String s) {
//                Util.makeToast(s);
//            }
//        });
    }
}
