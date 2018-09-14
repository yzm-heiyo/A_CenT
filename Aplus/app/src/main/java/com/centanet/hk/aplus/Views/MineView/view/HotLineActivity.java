package com.centanet.hk.aplus.Views.MineView.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.MD5Util;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.manager.ApplicationManager;
import com.githang.statusbar.StatusBarCompat;

public class HotLineActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String HOTLINE_NUMBER = "HOTLINE_NUMBER";
    private TextView hotlineNumberTxt, useInfoTxt, callCountTxt;
    private String number;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotline);
        StatusBarCompat.setStatusBarColor(this, Color.parseColor("#BB2E2D"), false);
        init();
        initView();
        initLisenter();
    }

    private void init() {
        if (getIntent() != null)
            number = getIntent().getStringExtra(HOTLINE_NUMBER);
    }

    private void initLisenter() {
        hotlineNumberTxt.setOnClickListener(this);
        useInfoTxt.setOnClickListener(this);
        callCountTxt.setOnClickListener(this);
    }

    private void initView() {

        hotlineNumberTxt = findViewById(R.id.mine_txt_hotlinenumber);
        hotlineNumberTxt.setText(number);
        useInfoTxt = findViewById(R.id.mine_txt_useinfo);
        callCountTxt = findViewById(R.id.mine_txt_callcount);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.mine_txt_useinfo:
                turnToWebView(getString(R.string.use_info), HttpUtil.URL_HOTLINE_INFO);
                break;
//            case R.id.mine_txt_callcount:
//                String url = "http://10.29.204.2?StaffNo=";
//
//                String staff = ApplicationManager.getApplication().getHeaderDescription().getStaffno();
//                L.d("staff",staff);
//                url = url + staff + "&Timestamp=";
//                String number = System.currentTimeMillis() + "";
//                L.d("mine_txt_callcount", number);
//                url = url + number + "&Token=";
//                String token = MD5Util.getMD5Str(staff + "qwolxcb45" + number);
//                token = token.toLowerCase();
//                url = url + token;
////                turnToWebView("","http://10.29.204.2?StaffNo=00272&Timestamp=1534744891.06127&Token=358b0fd20567f0eebda19211e4b6efb5");
//                turnToWebView(getString(R.string.call_count), url);
//                break;
            case R.id.mine_txt_callcount:
                String url = HttpUtil.URL_SSO_CALLCOUNT + "?StaffNo=";
//                String url = "http://hkqasso.centanet.com/report/#/?StaffNo=";
                //http://hkqasso.centanet.com/report/#/?
                String staff = ApplicationManager.getApplication().getHeaderDescription().getStaffno();
                L.d("staff", staff);
                url = url + staff + "&Timestamp=";
                String number = System.currentTimeMillis() + "";
                L.d("mine_txt_callcount", number);
                url = url + number + "&Token=";
                String token = MD5Util.getMD5Str(staff + "qwolxcb45" + number);
                token = token.toLowerCase();
                url = url + token;
//                turnToWebView("","http://10.29.204.2?StaffNo=00272&Timestamp=1534744891.06127&Token=358b0fd20567f0eebda19211e4b6efb5");
                L.d("CallCount",url);
                turnToWebView(getString(R.string.call_count), url);
//                turnToWebView("","http://hkqasso.centanet.com/report/#/?StaffNo=00066&Timestamp=1534905422572&Token=e2721b7860951fcab5ed400d8d675503");
                break;
        }
    }

    private void turnToWebView(String title, String url) {
        Intent intent = new Intent(HotLineActivity.this, WebExhibitionActivity.class);
        intent.putExtra(WebExhibitionActivity.WEB_TITLE, title);
        intent.putExtra(WebExhibitionActivity.WEB_URL, url);
        startActivity(intent);
    }
}
