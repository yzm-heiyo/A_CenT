package com.centanet.hk.aplus.Views.basic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.centanet.hk.aplus.MyApplication;
import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.DialogUtil;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Views.Dialog.SimpleTipsDialog;
import com.centanet.hk.aplus.Views.LoginView.view.LoginActivity;
import com.centanet.hk.aplus.manager.ApplicationManager;

/**
 * Created by mzh1608258 on 2018/1/10.
 */

public abstract class BasicActivty extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ApplicationManager.getApplication().isOutTime()) {
            SimpleTipsDialog simpleTipsDialog = DialogUtil.getSimpleDialog(getString(R.string.dialog_tips_time_out));
            simpleTipsDialog.setDialogCancelOnTouchOutside(false);
            simpleTipsDialog.ableToKeyBack(false);
            simpleTipsDialog.setOnItemclickListener(new SimpleTipsDialog.OnItemClickListener() {
                @Override
                public void onClick(DialogFragment dialog, int type) {
                    dialog.dismiss();
                    MyApplication.removeAllActiies();
                    startActivity(new Intent(BasicActivty.this, LoginActivity.class));
                }
            });
            simpleTipsDialog.show(getSupportFragmentManager(), "");
            ApplicationManager.getApplication().setOutTime(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.removeActivity(this);
    }


}
