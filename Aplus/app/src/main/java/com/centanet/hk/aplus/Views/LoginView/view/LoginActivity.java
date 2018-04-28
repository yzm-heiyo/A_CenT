package com.centanet.hk.aplus.Views.LoginView.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.centanet.hk.aplus.MyApplication;
import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.DialogUtil;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.PreferenceUtils;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.Views.Dialog.LoadingDialog;
import com.centanet.hk.aplus.Views.Dialog.SimpleTipsDialog;
import com.centanet.hk.aplus.Views.LoginView.persent.ILoginPresent;
import com.centanet.hk.aplus.Views.LoginView.persent.LoginPresent;
import com.centanet.hk.aplus.Views.MainActivity.MainActivity;
import com.centanet.hk.aplus.Views.abst.LoginActivityAbst;
import com.centanet.hk.aplus.bean.http.AHeaderDescription;
import com.centanet.hk.aplus.bean.http.SSOHeaderDescription;
import com.centanet.hk.aplus.bean.http.SSOLoginDescription;
import com.centanet.hk.aplus.bean.login.Permisstions;
import com.githang.statusbar.StatusBarCompat;

public class LoginActivity extends LoginActivityAbst implements LoginActivityAbst.ILoginManager, ILoginView {


    private ILoginPresent present;
    private String account;
    private String thiz = getClass().getSimpleName();
    private LoadingDialog loadingDialog;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        present = new LoginPresent(this);
        loadingDialog = new LoadingDialog(LoginActivity.this);
        intent = new Intent(this, MainActivity.class);
        present.doGet(HttpUtil.URL_UPDATE, new SSOHeaderDescription());
        if (Build.VERSION.SDK_INT < 23)
            StatusBarCompat.setStatusBarColor(this, Color.parseColor("#BB2E2D"), false);
        else StatusBarCompat.setStatusBarColor(this, Color.WHITE, true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        present.isRoot();
    }

    @Override
    protected ILoginManager setLoginHelper() {
        return this;
    }


    @Override
    public void login(View v, String account, String password) {
        this.account = account;
        if (password == null || password.equals("") || account.equals("") || account == null) {
            DialogUtil.getSimpleDialog(getString(R.string.dialog_tips_input_acc_pass)).show(getSupportFragmentManager(), "");
            return;
        }
        SSOLoginDescription loginDescription = new SSOLoginDescription();
        loginDescription.setDomainAccount(account.trim());
        loginDescription.setDomainPass(password.toLowerCase());
        SSOHeaderDescription ssoHeader = new SSOHeaderDescription();
        present.login(ssoHeader, loginDescription);
        loadingDialog.show();
    }

    @Override
    public void reFreshApplication(AHeaderDescription headerDescription, Permisstions permission, SSOHeaderDescription ssoHeaderDescription) {
        MyApplication application = (MyApplication) getApplicationContext();
        application.setHeaderDescription(headerDescription);
        application.setUserPermission(permission);
        application.setSsoHeaderDescription(ssoHeaderDescription);
    }

    @Override
    public void showTipDialog(String tips) {
        loadingDialog.cancel();
        SimpleTipsDialog tipsDialog = new SimpleTipsDialog();
        tipsDialog.setContentString(tips);
        tipsDialog.setLeftBtnVisibility(false);
        tipsDialog.show(getSupportFragmentManager(), "");
    }

    @Override
    public void toLogin() {
        finish();
        saveAccount();
        loadingDialog.cancel();
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void onFailure() {
        loadingDialog.cancel();
        DialogUtil.getSimpleDialog(getString(R.string.network_unenable)).show(getSupportFragmentManager(), "");
    }

    @Override
    public void setUpdateUrl(String url, boolean isForceUpdate) {
    }

    @Override
    public void isRoot(boolean isRoot) {
        if (isRoot) {
//            finish();
//            Toast.makeText(this, "手機已Root", Toast.LENGTH_SHORT).show();
            SimpleTipsDialog dialog =DialogUtil.getSimpleDialog(getString(R.string.dialog_tips_root));
            dialog.setDialogCancelOnTouchOutside(false);
            dialog.ableToKeyBack(false);
            dialog.setOnItemclickListener(new SimpleTipsDialog.OnItemClickListener() {
                @Override
                public void onClick(DialogFragment dialog, int type) {
                    finish();
                }
            });
            dialog.show(getSupportFragmentManager(), "");
        } else Toast.makeText(this, "手機沒有Root", Toast.LENGTH_SHORT).show();
    }

    private void saveAccount() {
        PreferenceUtils.addParams("account", account.trim());
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 給所有組建分發事件
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}
