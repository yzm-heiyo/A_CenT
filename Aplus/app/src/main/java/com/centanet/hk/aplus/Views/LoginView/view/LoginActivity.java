package com.centanet.hk.aplus.Views.LoginView.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.centanet.hk.aplus.MyApplication;
import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.AnSystemParamUtil;
import com.centanet.hk.aplus.Utils.DialogUtil;
import com.centanet.hk.aplus.Utils.PreferenceUtils;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.Views.Dialog.LoadingDialog;
import com.centanet.hk.aplus.Views.Dialog.SimpleTipsDialog;
import com.centanet.hk.aplus.Views.LoginView.persent.ILoginPresent;
import com.centanet.hk.aplus.Views.LoginView.persent.LoginPresent;
import com.centanet.hk.aplus.Views.MainActivity.view.MainActivity;
import com.centanet.hk.aplus.bean.http.AHeaderDescription;
import com.centanet.hk.aplus.bean.http.SSOHeaderDescription;
import com.centanet.hk.aplus.bean.http.SSOLoginDescription;
import com.centanet.hk.aplus.bean.login.Permisstions;
import com.githang.statusbar.StatusBarCompat;

import static com.centanet.hk.aplus.MyApplication.getContext;

public class LoginActivity extends AppCompatActivity implements ILoginView {

    private EditText accountEdit;
    private EditText passwordEdit;

    private ILoginPresent present;
    private String account;
    private String thiz = getClass().getSimpleName();
    private LoadingDialog loadingDialog;
    private Intent intent;
    private TextView appVersion;
    private static final int FILE_WRITE_PERMISSION = 1001;
    private boolean allowToLogin = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        init();

    }

    private void init() {
        present = new LoginPresent(this);
        loadingDialog = new LoadingDialog(LoginActivity.this);
        intent = new Intent(this, MainActivity.class);
        present.doGet(HttpUtil.URL_UPDATE, new SSOHeaderDescription());
        if (Build.VERSION.SDK_INT < 23)
            StatusBarCompat.setStatusBarColor(this, Color.parseColor("#BB2E2D"), false);
        else StatusBarCompat.setStatusBarColor(this, Color.WHITE, true);

        requestPermission();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doRequest(requestCode, grantResults);
    }

    private void doRequest(int requestCode, int[] grantResults) {
        if (requestCode == FILE_WRITE_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
            } else {
                // Permission Denied
                allowToLogin = false;
            }
        }
    }

    private void initViews() {

        accountEdit = findViewById(R.id.login_account);
        passwordEdit = this.findViewById(R.id.login_password);
        appVersion = findViewById(R.id.login_txt_app_version);
        appVersion.setText("v " + AnSystemParamUtil.getLocalVersionName(this));

        String clientAccount = PreferenceUtils.getValue("account");
        if (!clientAccount.equals("")) accountEdit.setText(clientAccount);
    }

    @Override
    protected void onResume() {
        super.onResume();
        present.isRoot();
        if (checkPermission()) allowToLogin = true;
        if (!allowToLogin) {
            showPermissionDialog();
        }
    }

    private void showPermissionDialog() {
        View view = View.inflate(getContext(), R.layout.item_dialog_note, null);
        SimpleTipsDialog simpleTipsDialog = new SimpleTipsDialog(view, 0.8, 0.50, R.id.item_tips_owner);
        simpleTipsDialog.setContentString(getString(R.string.dialog_tips_user_no_permission));
        simpleTipsDialog.setLeftBtnVisibility(false);
        simpleTipsDialog.setDialogCancelOnTouchOutside(false);
        simpleTipsDialog.ableToKeyBack(false);
        simpleTipsDialog.setOnItemclickListener((dialog, type) -> {

            Intent localIntent = new Intent();
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= 9) {
                localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                localIntent.setData(Uri.fromParts("package", getPackageName(), null));
            } else if (Build.VERSION.SDK_INT <= 8) {
                localIntent.setAction(Intent.ACTION_VIEW);
                localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
            }
            startActivity(localIntent);

        });
        simpleTipsDialog.show(getSupportFragmentManager(), "permissionDialog");
    }

    private void requestPermission() {
        if (!checkPermission()) {
            //申请访问Data数据
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                this.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        FILE_WRITE_PERMISSION);
            }
            return;
        } else {
            allowToLogin = true;
        }
    }

    private boolean checkPermission() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }


    public void login(View v) {
//        CalendarDialog calendarDialog = new CalendarDialog();
//        calendarDialog.show(getFragmentManager(),"");
        String account = accountEdit.getText().toString();
        String password = passwordEdit.getText().toString();
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
        if(this == null)return;
        DialogUtil.getSimpleDialog(getString(R.string.network_unenable)).show(getSupportFragmentManager(), "");
    }

    @Override
    public void setUpdateUrl(String url, boolean isForceUpdate) {
    }

    @Override
    public void isRoot(boolean isRoot) {
        if (isRoot) {
            View view = View.inflate(getContext(), R.layout.item_dialog_note, null);
            SimpleTipsDialog simpleTipsDialog = new SimpleTipsDialog(view, 0.72, 0.50, R.id.item_tips_owner);
            simpleTipsDialog.setLeftBtnVisibility(false);
            simpleTipsDialog.setDialogCancelOnTouchOutside(false);
            simpleTipsDialog.ableToKeyBack(false);
            simpleTipsDialog.setContentString(getString(R.string.dialog_tips_root));
            simpleTipsDialog.setOnItemclickListener(new SimpleTipsDialog.OnItemClickListener() {
                @Override
                public void onClick(DialogFragment dialog, int type) {
                    finish();
                }
            });
            simpleTipsDialog.show(getSupportFragmentManager(), "");
        }
    }

    private void saveAccount() {
        PreferenceUtils.addParams("account", account.trim());
    }


    //记录用户首次点击返回键的时间
    private long firstTime = 0;

    /**
     * 第一种解决办法 通过监听keyUp
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                Toast.makeText(LoginActivity.this, getString(R.string.dialog_tips_close), Toast.LENGTH_SHORT).show();
                firstTime = secondTime;
                return true;
            } else {
                System.exit(0);
            }
        }

        return super.onKeyUp(keyCode, event);
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
