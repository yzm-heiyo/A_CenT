package com.centanet.hk.aplus.Views.MineView.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.Widgets.TitleBar;

/**
 * Created by yangzm4 on 2018/3/20.
 */

public class UseClauseActivity extends AppCompatActivity{

    private TitleBar titleBar;
    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userclause);
        titleBar = findViewById(R.id.clause_titlebar);
        titleBar.setTitleContent(getString(R.string.app_use_clause));
        webView = findViewById(R.id.userlause_webview_web);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webView.loadUrl(HttpUtil.URL_USERLAUSE);

    }

}
