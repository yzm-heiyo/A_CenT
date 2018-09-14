package com.centanet.hk.aplus.Views.MineView.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.DialogUtil;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.Views.Dialog.SimpleTipsDialog;
import com.centanet.hk.aplus.Widgets.TitleBar;
import com.githang.statusbar.StatusBarCompat;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by yangzm4 on 2018/3/20.
 */

public class WebExhibitionActivity extends AppCompatActivity{

    public static String WEB_TITLE = "WEB_TITLE";
    public static String WEB_URL = "WEB_URL";

    private TitleBar titleBar;
    private WebView webView;
    private String title,url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, Color.parseColor("#BB2E2D"),false);

        init();

//        setContentView(R.layout.activity_userclause);
//        titleBar = findViewById(R.id.clause_titlebar);
//        titleBar.setTitleContent(title);
//        webView = findViewById(R.id.userlause_webview_web);
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setLoadWithOverviewMode(true);
//        L.d("WebExhibitionActivity",url);
//        webView.loadUrl(url);
//
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                // TODO Auto-generated method stub
//                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
//                view.loadUrl(url);
//                return false;
//            }
//        });

        setContentView(R.layout.activity_userclause);
        titleBar = findViewById(R.id.clause_titlebar);
        titleBar.setTitleContent(title);
        webView = findViewById(R.id.userlause_webview_web);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        L.d("WebExhibitionActivity", url);
//        webView.loadUrl(url);

//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                // TODO Auto-generated method stub
//                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
//                view.loadUrl(url);
//                return false;
//            }
//        });

        HttpUtil.doGet(url, null, null, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                L.d("Reapon_Code",response.code()+"");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.d("Reapon_Code",response.code()+"");
                if (response.code() == 200) {
                    runOnUiThread(() -> {
                        webView.loadUrl(url);
                    });
                } else {
                    SimpleTipsDialog dialog = DialogUtil.getSimpleDialog(getString(R.string.try_later));
                    dialog.setOnItemclickListener((dialog1, type) -> {
                        WebExhibitionActivity.this.finish();
                    });
                    dialog.show(getSupportFragmentManager(), "");
                }
            }
        });

    }

    private void init() {

        Intent intent = getIntent();
        if(intent!=null) {
            title = intent.getStringExtra(WEB_TITLE);
            url = intent.getStringExtra(WEB_URL);
        }
    }
}
