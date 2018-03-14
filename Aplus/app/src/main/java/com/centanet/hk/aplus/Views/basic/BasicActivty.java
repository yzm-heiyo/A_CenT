package com.centanet.hk.aplus.Views.basic;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.centanet.hk.aplus.MyApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mzh1608258 on 2018/1/10.
 */

public abstract class BasicActivty extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.addActivity(this);
        Log.e("DATA",MyApplication.getActiviesSize()+"");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.removeActivity(this);
    }


}
