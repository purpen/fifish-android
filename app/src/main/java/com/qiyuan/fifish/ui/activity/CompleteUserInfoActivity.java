package com.qiyuan.fifish.ui.activity;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.*;
import butterknife.BindView;
import butterknife.OnClick;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.ui.view.roundImageView.RoundedImageView;
import com.qiyuan.fifish.util.Util;

/**
 * @author lilin
 *         created at 2016/7/26 13:10
 */
public class CompleteUserInfoActivity extends BaseActivity {

    @BindView(R.id.ibtn_close)
    ImageButton ibtnClose;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.riv)
    RoundedImageView riv;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.et_job)
    EditText etJob;
    @BindView(R.id.et_area)
    EditText etArea;
    @BindView(R.id.btn_update)
    Button btnUpdate;

    public CompleteUserInfoActivity() {
        super(R.layout.activity_complete_user_info);
    }

    @Override
    protected void initViews() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.dp48));
            params.topMargin = Util.getStatusBarHeight();
            rlHead.setLayoutParams(params);
        }

    }


    @OnClick({R.id.ibtn_close, R.id.riv, R.id.btn_update})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtn_close:
                break;
            case R.id.riv:
                break;
            case R.id.btn_update:
                break;
        }
    }
}
