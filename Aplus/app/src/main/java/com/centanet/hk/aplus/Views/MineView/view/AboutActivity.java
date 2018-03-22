package com.centanet.hk.aplus.Views.MineView.view;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Widgets.TitleBar;

/**
 * Created by yangzm4 on 2018/3/19.
 */

public class AboutActivity extends AppCompatActivity {

    private TitleBar titleBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        titleBar = findViewById(R.id.about_titlebar);
        titleBar.setTitleContent(getString(R.string.about_app));
    }
}
