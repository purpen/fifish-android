package com.qiyuan.fifish.ui.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.network.RequestService;
import com.qiyuan.fifish.ui.activity.ForgetPasswordActivity;
import com.qiyuan.fifish.util.ToastUtils;
import com.qiyuan.fifish.util.Util;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;

import butterknife.Bind;
import butterknife.OnClick;

public class LoginFragment extends BaseFragment {

    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.tv_forget_password)
    TextView tvForgetPassword;
    @Bind(R.id.btn_login)
    Button btnLogin;
    private String userName;
    private String userPsw;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.setFragmentLayout(R.layout.fragment_login);
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    protected void initViews() {

    }


    @OnClick({R.id.tv_forget_password, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_forget_password:
                startActivity(new Intent(activity, ForgetPasswordActivity.class));
                break;
            case R.id.btn_login:
                if (!checkUserInput()) return;
                loginUser();
                break;
        }
    }

    private void loginUser() {

        RequestService.loginUser(userName, userPsw, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private boolean checkUserInput() {
        userName = etPhone.getText().toString();
        userPsw = etPassword.getText().toString();
        if (TextUtils.isEmpty(userName)) {//已限制50个字符
            ToastUtils.showInfo(R.string.input_email);
            return false;
        }
        if (!Util.isEmailValid(userName)){
            ToastUtils.showInfo(R.string.input_right_email);
            return false;
        }

        if (TextUtils.isEmpty(userPsw)) {
            ToastUtils.showInfo("请输入密码");
            return false;
        }

        return true;
    }
}
