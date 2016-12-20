package com.qiyuan.fifish.ui.fragment;

import android.content.Intent;
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
import android.widget.TextView;

import com.google.gson.JsonSyntaxException;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.application.AppApplication;
import com.qiyuan.fifish.bean.LoginBean;
import com.qiyuan.fifish.bean.UserProfile;
import com.qiyuan.fifish.network.CustomCallBack;
import com.qiyuan.fifish.network.RequestService;
import com.qiyuan.fifish.ui.activity.ForgetPasswordActivity;
import com.qiyuan.fifish.ui.activity.MainActivity;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.JsonUtil;
import com.qiyuan.fifish.util.SPUtil;
import com.qiyuan.fifish.util.ToastUtils;
import com.qiyuan.fifish.util.Util;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;

import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class LoginFragment extends BaseFragment {
    private static final int MSG_SET_ALIAS = 10;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_forget_password)
    TextView tvForgetPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    private String userName;
    private String userPsw;
    private String userId;
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
                LogUtil.e("登录"+result);
                if (TextUtils.isEmpty(result)) return;
                LoginBean loginBean = JsonUtil.fromJson(result, LoginBean.class);
                if (loginBean.meta.status_code== Constants.HTTP_OK){
                    SPUtil.write(Constants.TOKEN,loginBean.data.token);
                    getUserProfile();
                    return;
                }
                ToastUtils.showError(loginBean.meta.message);
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
        RequestService.getUserProfile(new CustomCallBack() {
            @Override
            public void onSuccess(String result) {
                if (TextUtils.isEmpty(result)) return;
                try {
                    UserProfile userInfo = JsonUtil.fromJson(result, UserProfile.class);

                    if (userInfo.meta.status_code== Constants.HTTP_OK){
                        if (JPushInterface.isPushStopped(AppApplication.getInstance())) JPushInterface.resumePush(AppApplication.getInstance());
                        SPUtil.write(Constants.LOGIN_INFO,result);
                        userId = userInfo.data.id;
                        JPushInterface.setAlias(activity.getApplicationContext(),userId,mTagAliasCallback);
                        Intent intent = new Intent(activity, MainActivity.class);
                        intent.putExtra(HomeFragment.class.getSimpleName(),HomeFragment.class.getSimpleName());
                        startActivity(intent);
                        return;
                    }
                }catch (JsonSyntaxException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                ToastUtils.showError(R.string.request_error);
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
            ToastUtils.showInfo(R.string.password_null);
            return false;
        }

        return true;
    }

    private final TagAliasCallback mTagAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int i, String s, Set<String> set) {
            String logs;
            switch (i) {
                case 0:
                    logs = "Set tag and alias success";
                    LogUtil.e(logs);
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    LogUtil.e(logs);
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS,userId), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + i;
                    LogUtil.e(logs);
            }
        }
    };


    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(AppApplication.getInstance(),
                            (String) msg.obj,
                            null,
                            mTagAliasCallback);
                    break;
                default:
                    LogUtil.i("Unhandled msg - " + msg.what);
            }
        }
    };

}
