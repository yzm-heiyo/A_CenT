package com.centanet.hk.aplus.Views.MineView.view;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Widgets.TitleBar;
import com.githang.statusbar.StatusBarCompat;

/**
 * Created by yangzm4 on 2018/3/19.
 */

public class AboutActivity extends AppCompatActivity {

    private TitleBar titleBar;
    private TextView machine, sysType, sysVersion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        StatusBarCompat.setStatusBarColor(this, Color.parseColor("#BB2E2D"),false);
        titleBar = findViewById(R.id.about_titlebar);
        titleBar.setTitleContent(getString(R.string.about_app));

        machine = findViewById(R.id.about_machine_txt);
        sysType = findViewById(R.id.about_sys_type_txt);
        sysVersion = findViewById(R.id.about_sys_version_txt);

        machine.setText(android.os.Build.BRAND +" "+ android.os.Build.MODEL);
        sysVersion.setText(android.os.Build.VERSION.RELEASE);

    }
}
