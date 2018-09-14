package com.centanet.hk.aplus.Views.MainActivity.view;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.centanet.hk.aplus.Adapter.PagerAdapter;
import com.centanet.hk.aplus.BackHandlerHelper.BackHandlerHelper;
import com.centanet.hk.aplus.MyApplication;
import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.AnSystemParamUtil;
import com.centanet.hk.aplus.Utils.DownloadUtil;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.MD5Util;
import com.centanet.hk.aplus.Utils.PreferenceUtils;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.Views.Dialog.LoadingDialog;
import com.centanet.hk.aplus.Views.Dialog.LogoutDialog;
import com.centanet.hk.aplus.Views.Dialog.SimpleTipsDialog;
import com.centanet.hk.aplus.Views.FavoListView.view.FavHouseFragment;
import com.centanet.hk.aplus.Views.HomePageView.HomeContentFragment;
import com.centanet.hk.aplus.Views.LoginView.view.LoginActivity;
import com.centanet.hk.aplus.Views.MainActivity.presenter.IMainPresenter;
import com.centanet.hk.aplus.Views.MainActivity.presenter.MainPresenter;
import com.centanet.hk.aplus.Views.MineView.view.MineFragment;
import com.centanet.hk.aplus.Views.MineView.view.WebExhibitionActivity;
import com.centanet.hk.aplus.Views.TransHomePagerView.TransHomeContentFragment;
import com.centanet.hk.aplus.Views.basic.BaseFragment;
import com.centanet.hk.aplus.Views.basic.BasicActivty;
import com.centanet.hk.aplus.Widgets.CustomViewPager;
import com.centanet.hk.aplus.eventbus.MessageEventBus;
import com.centanet.hk.aplus.manager.ApplicationManager;
import com.githang.statusbar.StatusBarCompat;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.centanet.hk.aplus.MyApplication.getContext;
import static com.centanet.hk.aplus.Views.Dialog.LogoutDialog.DIALOG_YES;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.HomePager.PAGER_HOME;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.HomePager.TRANSPAGER_HOME;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.HouseNavigation.HIDDEN;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.HouseNavigation.SHOW;

/**
 * Created by mzh1608258 on 2018/1/2.
 */

public class MainActivity extends BasicActivty implements View.OnClickListener, IMainView, BaseFragment.OnFragmentInteractionListener {


    private CustomViewPager pager;
    private RadioGroup rg;
    private List<Fragment> fragments;
    private FragmentPagerAdapter adapter;
    private List<RadioButton> rbs;
    private String thiz = getClass().getSimpleName();
    private static final int FILE_DOWN_PERMISSION = 1;
    private IMainPresenter presenter;
    private View naviContent;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        isFirstLogin();
        initViews();
        init();

        StatusBarCompat.setStatusBarColor(this, Color.parseColor("#BB2E2D"), false);
    }

    private void initViews() {
        rg = this.findViewById(R.id.main_navigationbar);
        pager = this.findViewById(R.id.main_viewpager);
        pager.setScanScroll(false);
        naviContent = findViewById(R.id.main_bottom);
        rbs = new ArrayList<>();

        RadioButton manager = rg.findViewById(R.id.activity_main_manager);
        manager.setOnClickListener(this);
        RadioButton collect = rg.findViewById(R.id.activity_main_collect);
        collect.setOnClickListener(this);
        RadioButton tran = rg.findViewById(R.id.activity_main_tran);
        tran.setOnClickListener(this);
        RadioButton user = rg.findViewById(R.id.activity_main_user);
        user.setOnClickListener(this);

        rbs.add(manager);
        rbs.add(collect);
        rbs.add(tran);
        rbs.add(user);

    }

    private void init() {

        presenter = new MainPresenter(this);
        presenter.getDistrict();
        fragments = this.setFragments();

        try {
            //不可為空,判定個數是否一致
            if (fragments == null) {
                throw new Exception("fragments are null!pls check");
            } else if (rg.getChildCount() != fragments.size()) {
                throw new Exception("fragments and RadioGroup size are not match!pls check");
            }
            setAdapter();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//
//                for (int i = 0; i < rbs.size(); i++) {
//                    if (checkedId == rbs.get(i).getId()) {
//                        pager.setCurrentItem(i);
//                        return;
//                    }
//                }
//            }
//        });

//        rg.setOnClickListener(view -> {
//            L.d("getIdOnClickListener", view.getId()+"");
//        });


        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position <= rbs.size()) {
                    rbs.get(position).setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 主页面
     *
     * @return
     */
    protected List<Fragment> setFragments() {

        List<Fragment> fragments = new ArrayList<>();
//        fragments.add(new HouseListFragment());

        fragments.add(new HomeContentFragment());
//        fragments.add(new HouseFragment());
//        fragments.add(new HomePagerFragment());
//        fragments.add(new SearchFragment());
        FavHouseFragment favoriteFragment = new FavHouseFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("FAVO", true);
        favoriteFragment.setArguments(bundle);
        fragments.add(favoriteFragment);

        fragments.add(new TransHomeContentFragment());
        fragments.add(new MineFragment());

        return fragments;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEventBus messageEvent) {
        L.d(thiz, "onMoonEvent" + messageEvent.getMsg() + "");

        switch (messageEvent.getMsg()) {
            case SHOW:
                naviContent.setVisibility(View.VISIBLE);
                break;
            case HIDDEN:
                naviContent.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUpdateApk();
    }

    private void getUpdateApk() {

        int currentVersion = AnSystemParamUtil.getLocalVersion(this);

        int updateType = ApplicationManager.getApplication().getUpdateType();
        String updateUrl = ApplicationManager.getApplication().getUpdateUrl();
        String updateContent = ApplicationManager.getApplication().getUpdateContent();
        if (updateContent == null || updateContent.trim().equals("") || updateContent.equals(""))//当更新内容为空时不显示
            return;
        if (updateType != 0) {
            View view = View.inflate(getContext(), R.layout.item_dialog_note, null);
            SimpleTipsDialog simpleTipsDialog = new SimpleTipsDialog(view, 0.8, 0.50, R.id.item_tips_owner);
            simpleTipsDialog.ableToKeyBack(false);
            simpleTipsDialog.setLeftButtonText(getString(R.string.dialog_tips_btn_close));
            simpleTipsDialog.setRightButtonText(getString(R.string.dialog_tips_btn_update));
            simpleTipsDialog.setTipString(getString(R.string.dialog_tips_title_update));
            simpleTipsDialog.setDialogCancelOnTouchOutside(false);

            if (ApplicationManager.getApplication().getClientVer() <= currentVersion) return;
            if (updateType == 2) {
                simpleTipsDialog.setContentString(updateContent);
                simpleTipsDialog.setLeftBtnVisibility(false);

            } else if (updateType == 1) {
                if (ApplicationManager.getApplication().isCancelUpdate()) return;
                simpleTipsDialog.setContentString(updateContent);
            }

            simpleTipsDialog.setOnItemclickListener(new SimpleTipsDialog.OnItemClickListener() {
                @Override
                public void onClick(DialogFragment dialog, int type) {
                    dialog.dismiss();
                    if (type == SimpleTipsDialog.DIALOG_YES) {

                        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            //申请访问Data数据
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                MainActivity.this.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        FILE_DOWN_PERMISSION);
                            }
                            return;
                        }
                        downLoadApk(updateUrl);
                    } else if (type == SimpleTipsDialog.DIALOG_CANCEL) {
                        ApplicationManager.getApplication().setCancelUpdate(true);
                    }
                }
            });
            FragmentTransaction s = getSupportFragmentManager().beginTransaction();
            Fragment fragment = getSupportFragmentManager().findFragmentByTag("UpdateDialog");
            if (fragment == null)
                simpleTipsDialog.show(getSupportFragmentManager(), "UpdateDialog");
            else {
                if(fragment.isHidden())
                    s.show(fragment).commit();
            }
//            simpleTipsDialog.show(getSupportFragmentManager(), "");
        }
    }

    private void downLoadApk(String updateUrl) {
        LoadingDialog.show(MainActivity.this).setCancelable(false);
        Toast.makeText(MainActivity.this, Environment.getExternalStorageDirectory() + "", Toast.LENGTH_SHORT).show();
        DownloadUtil.getInstance().download(updateUrl, Environment.getExternalStorageDirectory() + "", new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(String path) {
                L.d("onDownloadSuccess", "");
                File apkFile = new File(Environment.getExternalStorageDirectory() + "/" + updateUrl.substring(updateUrl.lastIndexOf("/") + 1));
                updateApk(apkFile);
            }

            @Override
            public void onDownloading(int progress) {
                L.d("onDownloading", "");
            }

            @Override
            public void onDownloadFailed() {
                downLoadApk(updateUrl);
                Toast.makeText(MainActivity.this, " 網絡連結出現問題，再次嘗試更新...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public int getCurrentItem() {
        return pager.getCurrentItem();
    }

    @Override
    public void onBackPressed() {

        if (!BackHandlerHelper.handleBackPress(pager)) {

            LogoutDialog logoutDialog = new LogoutDialog();
            logoutDialog.setOnDialogOnclikeLisenter(new LogoutDialog.OnDialogOnclikeLisenter() {
                @Override
                public void onClick(Dialog v, int clickID) {
                    v.dismiss();
                    if (clickID == DIALOG_YES) {
                        MyApplication.removeAllActiies();
                        startActivity(new Intent(getContext(), LoginActivity.class));
                    }
                }
            });
            logoutDialog.show(getFragmentManager(), "");
        }
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


    private void updateApk(File apkFile) {

        if (Build.VERSION.SDK_INT >= 24) {//判读版本是否在7.0以上
            Uri apkUri = FileProvider.getUriForFile(MainActivity.this, "com.centanet.hk.aplus.fileprovider", apkFile);//在AndroidManifest中的android:authorities值
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
            install.setDataAndType(apkUri, "application/vnd.android.package-archive");
            startActivity(install);
        } else {
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(install);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doRequest(requestCode, grantResults);
    }

    private void doRequest(int requestCode, int[] grantResults) {
        if (requestCode == FILE_DOWN_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                downLoadApk(ApplicationManager.getApplication().getUpdateUrl());
            } else {
                // Permission Denied
            }
        }
    }

    private void setAdapter() {
        adapter = new PagerAdapter(getSupportFragmentManager(), fragments);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(fragments.size());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onInteraction(String fragmentType, Object viewData) {

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.activity_main_manager:
                if (pager.getCurrentItem() == 0) {
                    L.d("", "OnButtonClick");
                    MessageEventBus msg = new MessageEventBus();
                    msg.setMsg(PAGER_HOME);
                    EventBus.getDefault().post(msg);
                }
                pager.setCurrentItem(0);
                break;
            case R.id.activity_main_collect:
                pager.setCurrentItem(1);
                break;
            case R.id.activity_main_tran:
                if (pager.getCurrentItem() == 2) {
                    L.d("", "OnButtonClick");
                    MessageEventBus msg = new MessageEventBus();
                    msg.setMsg(TRANSPAGER_HOME);
                    EventBus.getDefault().post(msg);
                    return;
                }
                pager.setCurrentItem(2);
                break;
            case R.id.activity_main_user:
                pager.setCurrentItem(3);
                break;
        }
    }



    public void isFirstLogin() {

        Calendar calendar = Calendar.getInstance();
        String cueerntDay = calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
        String time = PreferenceUtils.getValue("loginTime");

        if (!cueerntDay.equals(time)) {
            String url = HttpUtil.URL_SSO_CALLCOUNT + "?StaffNo=";

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
            turnToWebView(getString(R.string.call_count), url);
        }
    }

    private void turnToWebView(String title, String url) {
        Intent intent = new Intent(MainActivity.this, WebExhibitionActivity.class);
        intent.putExtra(WebExhibitionActivity.WEB_TITLE, title);
        intent.putExtra(WebExhibitionActivity.WEB_URL, url);
        startActivity(intent);
    }
}
