package com.qiyuan.fifish.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.google.gson.JsonSyntaxException;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.adapter.LoginRegsiterViewPagerAdapter;
import com.qiyuan.fifish.application.AppApplication;
import com.qiyuan.fifish.bean.LoginBean;
import com.qiyuan.fifish.bean.UserProfile;
import com.qiyuan.fifish.network.CustomCallBack;
import com.qiyuan.fifish.network.RequestService;
import com.qiyuan.fifish.ui.fragment.HomeFragment;
import com.qiyuan.fifish.ui.fragment.LoginFragment;
import com.qiyuan.fifish.ui.fragment.MineFragment;
import com.qiyuan.fifish.ui.fragment.RegisterFragment;
import com.qiyuan.fifish.ui.view.WaitingDialog;
import com.qiyuan.fifish.ui.view.WrapContentHeightViewPager;
import com.qiyuan.fifish.util.Constants;
import com.qiyuan.fifish.util.JsonUtil;
import com.qiyuan.fifish.util.SPUtil;
import com.qiyuan.fifish.util.ToastUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.xutils.common.util.LogUtil;

import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * @author lilin
 *         created at 2016/7/26 13:10
 */
public class LoginActivity extends BaseActivity {
    @BindView(R.id.viewPager)
    WrapContentHeightViewPager viewPager;
    @BindView(R.id.btn_switch)
    Button btnSwitch;
    private String page;
    private String uid="";
    private String accessToken="";
    private String name="";
    private String icon="";
    private String gender="";
    private WaitingDialog dialog;
    private String userId;
    public LoginActivity() {
        super(R.layout.activity_login_register);
    }

    @Override
    protected void getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra(MineFragment.class.getSimpleName())){
            page = MineFragment.class.getSimpleName();
        }
    }

    @Override
    protected void initViews() {
        dialog =new WaitingDialog(activity);
        btnSwitch.setText(R.string.no_account);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        Fragment[] fragments={LoginFragment.newInstance(),RegisterFragment.newInstance()};
        LoginRegsiterViewPagerAdapter adapter = new LoginRegsiterViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.setPagingEnabled(false);
    }

    @Override
    protected void installListener() {

    }

    public WrapContentHeightViewPager getViewPager(){
        return viewPager==null?null:viewPager;
    }

    @OnClick({R.id.btn_switch,R.id.ibtn_close,R.id.iv_login0,R.id.iv_login1,R.id.iv_login2})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_switch:
                switch (viewPager.getCurrentItem()){
                    case 0:
                        viewPager.setCurrentItem(1);
                        btnSwitch.setText(R.string.has_account);
                        break;
                    case 1:
                        viewPager.setCurrentItem(0);
                        btnSwitch.setText(R.string.no_account);
                        break;
                }
                break;
            case R.id.ibtn_close:
                finish();
                break;
            case R.id.iv_login0: //facebook
                UMShareAPI.get(getApplicationContext()).getPlatformInfo(activity,SHARE_MEDIA.FACEBOOK,authListener);
                break;
            case R.id.iv_login1: //instagram
                UMShareAPI.get(getApplicationContext()).getPlatformInfo(activity,SHARE_MEDIA.INSTAGRAM,authListener);
                break;
            case R.id.iv_login2:// wechat
                UMShareAPI.get(getApplicationContext()).getPlatformInfo(activity,SHARE_MEDIA.WEIXIN,authListener);
                break;
            default:
                break;
        }
    }
    UMAuthListener authListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            String temp = "";
            for (String key : data.keySet()) {
                temp= temp+key +" : "+data.get(key)+"\n";
            }
            LogUtil.e("=================="+temp);
            //获取用户信息
            if (data==null) return;
            switch (platform){
                case WEIXIN:
                    if (data.containsKey("openid")){
                        uid= data.get("openid");
                    }
                    if (data.containsKey("access_token")){
                        accessToken= data.get("access_token");
                    }

                    if (data.containsKey("screen_name")){
                        name=data.get("screen_name");
                    }
                    if (data.containsKey("profile_image_url")){
                        icon =data.get("profile_image_url");
                    }
                    if (data.containsKey("gender")){
                        gender =data.get("gender");
                    }
                    break;
                case FACEBOOK:
                    break;
                case INSTAGRAM:
                    break;
                default:
                    break;
            }
            doThirdLogin();
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            ToastUtils.showError(R.string.third_login_error);
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            ToastUtils.showInfo(R.string.cancel_login);
        }
    };

    //三方登录
    private void doThirdLogin() {
        RequestService.doThirdLogin(uid,accessToken,name,icon,gender,new CustomCallBack(){
            @Override
            public void onStarted() {
                if (dialog!=null&&!activity.isFinishing()) dialog.show();
            }

            @Override
            public void onSuccess(String result) {
                LoginBean loginBean = JsonUtil.fromJson(result, LoginBean.class);
                if (loginBean.meta.status_code== Constants.HTTP_OK){
                    SPUtil.write(Constants.TOKEN,loginBean.data.token);
                    getUserProfile();
                    return;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("login error");
                ex.printStackTrace();
            }

            @Override
            public void onFinished() {
                if (dialog!=null&&!activity.isFinishing()) dialog.dismiss();
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
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(Constants.MSG_SET_ALIAS,userId), 1000 * 60);
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
                case Constants.MSG_SET_ALIAS:
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode,resultCode,data);
    }
}
