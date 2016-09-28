package com.qiyuan.fifish.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import butterknife.BindView;
import com.qiyuan.fifish.R;
import com.qiyuan.fifish.ui.view.CustomHeadView;
import com.qiyuan.fifish.ui.view.WaitingDialog;

public class AboutUsActivity extends BaseActivity {
    @BindView(R.id.custom_head)
    CustomHeadView custom_head;
    private WaitingDialog dialog;
    private String url;
    private String title;
    private WebView webView;
    public AboutUsActivity(){
        super(R.layout.activity_about_us);
    }

    @Override
    protected void getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra(AboutUsActivity.class.getSimpleName())){
            url=intent.getStringExtra(AboutUsActivity.class.getSimpleName());
        }

        if (intent.hasExtra(AboutUsActivity.class.getName())){
            title=intent.getStringExtra(AboutUsActivity.class.getName());
        }
    }

    @Override
    protected void initViews() {
        custom_head.setHeadCenterTxtShow(true,title);
        dialog = new WaitingDialog(this);
        webView = (WebView) findViewById(R.id.webView_about);
        webView.setWebViewClient(webViewClient);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setAppCacheEnabled(true);
        webView.loadUrl(url);
    }

    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (dialog != null) dialog.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (dialog != null) dialog.dismiss();
        }

    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        webViewClient = null;
        webView.removeAllViews();
        webView.destroy();
    }
}
