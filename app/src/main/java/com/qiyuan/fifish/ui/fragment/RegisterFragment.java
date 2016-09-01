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

import com.google.gson.JsonSyntaxException;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.bean.ErrorBean;
import com.qiyuan.fifish.bean.RegisterInfo;
import com.qiyuan.fifish.bean.UserProfile;
import com.qiyuan.fifish.network.RequestService;
import com.qiyuan.fifish.ui.activity.MainActivity;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.JsonUtil;
import com.qiyuan.fifish.util.SPUtil;
import com.qiyuan.fifish.util.ToastUtils;
import com.qiyuan.fifish.util.Util;

import org.xutils.common.Callback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterFragment extends BaseFragment {
    @Bind(R.id.et_phone_reg)
    EditText etPhoneReg;
    @Bind(R.id.et_password_reg)
    EditText etPasswordReg;
    @Bind(R.id.btn_register)
    Button btnRegister;
    private String account;
    private String userPsw;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.setFragmentLayout(R.layout.fragment_register);
        super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, view);
        return view;
    }

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }

    @Override
    protected void initViews() {

    }


    @OnClick(R.id.btn_register)
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_register:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        if (!checkUserInput()) return;
        RequestService.registerUser(account, userPsw, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (TextUtils.isEmpty(result)) return;
                RegisterInfo registerInfo = JsonUtil.fromJson(result, RegisterInfo.class);
                if (registerInfo.meta.status_code== Constants.HTTP_OK){
                    getUserProfile();
                    return;
                }
                ToastUtils.showError(registerInfo.meta.errors.account.get(0));
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                ToastUtils.showError(R.string.request_error);
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }

    private void getUserProfile() {
        RequestService.getUserProfile(new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (TextUtils.isEmpty(result)) return;
                try {
                    UserProfile userInfo = JsonUtil.fromJson(result, UserProfile.class);
                    if (userInfo.meta.meta.status_code== Constants.HTTP_OK){
                        SPUtil.write(Constants.LOGIN_INFO,result);
                        startActivity(new Intent(activity, MainActivity.class));
                        return;
                    }
                }catch (JsonSyntaxException e){
                    e.printStackTrace();
                }finally {
                    ErrorBean errorBean = JsonUtil.fromJson(result, ErrorBean.class);
                    ToastUtils.showError(errorBean.meta.message);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                ToastUtils.showError(R.string.request_error);
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
        account = etPhoneReg.getText().toString();
        userPsw = etPasswordReg.getText().toString();
        if (TextUtils.isEmpty(account)) {//已限制50个字符
            ToastUtils.showInfo(R.string.input_email);
            return false;
        }
        if (!Util.isEmailValid(account)){
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
