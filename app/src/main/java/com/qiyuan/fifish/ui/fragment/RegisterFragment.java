package com.qiyuan.fifish.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.google.gson.JsonSyntaxException;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.application.AppApplication;
import com.qiyuan.fifish.bean.RegisterInfo;
import com.qiyuan.fifish.bean.UserProfile;
import com.qiyuan.fifish.network.CustomCallBack;
import com.qiyuan.fifish.network.RequestService;
import com.qiyuan.fifish.ui.activity.LoginActivity;
import com.qiyuan.fifish.ui.view.WrapContentHeightViewPager;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.JsonUtil;
import com.qiyuan.fifish.util.ToastUtils;
import com.qiyuan.fifish.util.Util;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;

import java.util.Set;

public class RegisterFragment extends BaseFragment {
    @BindView(R.id.et_phone_reg)
    EditText etPhoneReg;
    @BindView(R.id.et_password_reg)
    EditText etPasswordReg;
    @BindView(R.id.btn_register)
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
        switch (view.getId()) {
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
                if (registerInfo.meta.status_code == Constants.HTTP_OK) { //注册成功,让用户登录
                    ToastUtils.showSuccess(registerInfo.meta.message);
                    WrapContentHeightViewPager viewPager = ((LoginActivity) getActivity()).getViewPager();
                    if (viewPager != null) {
                        viewPager.setCurrentItem(0);
                    }
                    return;
                }

                if (registerInfo.meta.status_code == Constants.HTTP_ACCOUNT_ALREADY_EXIST) {
                    ToastUtils.showError(registerInfo.meta.message);
                    return;
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
        if (!Util.isEmailValid(account)) {
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
