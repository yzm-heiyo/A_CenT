package com.centanet.hk.aplus.Views.LoginView.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.centanet.hk.aplus.MyApplication;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.PreferenceUtils;
import com.centanet.hk.aplus.Views.Dialog.SimpleTipsDialog;
import com.centanet.hk.aplus.Views.LoginView.persent.ILoginPresent;
import com.centanet.hk.aplus.Views.LoginView.persent.LoginPresent;
import com.centanet.hk.aplus.Views.MainActivity.MainActivity;
import com.centanet.hk.aplus.Views.abst.LoginActivityAbst;
import com.centanet.hk.aplus.entity.http.AHeaderDescription;
import com.centanet.hk.aplus.entity.http.SSOHeaderDescription;
import com.centanet.hk.aplus.entity.http.SSOLoginDescription;
import com.centanet.hk.aplus.entity.login.Permisstions;

public class LoginActivity extends LoginActivityAbst implements LoginActivityAbst.ILoginManager, ILoginView {


    private ILoginPresent present;
    private String account;
    private String thiz =getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        present = new LoginPresent(this);
    }

    @Override
    protected ILoginManager setLoginHelper() {
        return this;
    }


    @Override
    public void login(View v, String account, String password) {
        this.account = account;
        SSOLoginDescription loginDescription = new SSOLoginDescription();
        loginDescription.setDomainAccount(account.trim());
        loginDescription.setDomainPass(password);
        SSOHeaderDescription ssoHeader = new SSOHeaderDescription();
        present.login(ssoHeader, loginDescription);
    }

    @Override
    public void reFreshApplication(AHeaderDescription headerDescription, Permisstions permission,SSOHeaderDescription ssoHeaderDescription) {
        MyApplication application = (MyApplication) getApplicationContext();
        application.setHeaderDescription(headerDescription);
        application.setUserPermission(permission);
        application.setSsoHeaderDescription(ssoHeaderDescription);
    }

    @Override
    public void showTipDialog(String tips) {
        SimpleTipsDialog tipsDialog = new SimpleTipsDialog();
        tipsDialog.setContentString(tips);
        tipsDialog.setLeftBtnVisibility(false);
        tipsDialog.show(getSupportFragmentManager(), "");
    }

    @Override
    public void toLogin() {
        finish();
        saveAccount();
        startActivity(new Intent(this, MainActivity.class));
    }

    private void saveAccount() {
        PreferenceUtils.addParams("account",account.trim());
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
