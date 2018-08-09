package com.centanet.hk.aplus.Views.HouseDetailView.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.centanet.hk.aplus.MyApplication;
import com.centanet.hk.aplus.Utils.MD5Util;
import com.centanet.hk.aplus.Utils.net.GsonUtil;
import com.centanet.hk.aplus.Views.Dialog.LoadingDialog;
import com.centanet.hk.aplus.Views.Dialog.SimpleTipsDialog;
import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.Views.HouseDetailView.present.DetailPresent;
import com.centanet.hk.aplus.Views.HouseDetailView.present.IDetailPresent;
import com.centanet.hk.aplus.Views.basic.BasicActivty;
import com.centanet.hk.aplus.bean.detail.DetailAddressResponse;
import com.centanet.hk.aplus.bean.detail.DetailBriefInfo;
import com.centanet.hk.aplus.bean.detail.DetailHouse;
import com.centanet.hk.aplus.bean.favo.FavoResponse;
import com.centanet.hk.aplus.bean.http.DetaileNextKeyIdDescription;
import com.centanet.hk.aplus.bean.http.FavoriteDescription;
import com.centanet.hk.aplus.bean.http.PropertyAddDescription;
import com.centanet.hk.aplus.bean.http.AHeaderDescription;
import com.centanet.hk.aplus.bean.http.TrustorDescription;
import com.centanet.hk.aplus.bean.http.UserBehaviorDescription;
import com.centanet.hk.aplus.eventbus.MessageEventBus;
import com.centanet.hk.aplus.manager.ScreenShotListenManager;
import com.githang.statusbar.StatusBarCompat;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.centanet.hk.aplus.MyApplication.getContext;
import static com.centanet.hk.aplus.Utils.net.HttpUtil.URL_ADDRESS_DETAIL;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.DetailRefresh.DETAIL_REFRESH;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.DetailRefreshView.DETAIL_ADDRESS;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.NetWorkState.NETWORK_STATE_FAIL;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.Permission.CLIENTINFO_NO;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.Permission.FOLLOW_ADD_NO;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.Permission.SEARCH_ALL_NO;

/**
 * Created by mzh1608258 on 2018/1/3.
 */

public class DetailActicity extends BasicActivty implements IDetailView, BasicInfoFragment.IBasicInfo {

    private String thiz = getClass().getSimpleName();
    private String keyId;
    private IDetailPresent present;
    private ImageView icoStatu;
    private TextView ssdTxt, houseEnNameTxt, houseChNameTxt;
    private View addDetailTxt;
    //    private TitleBar titleBar;
    private DetailHouse detailHouseData;
    private AHeaderDescription headerDescription;
    public static boolean isODish = false;
    private ScreenShotListenManager screenShotListenManager;
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 123;
    private BasicInfoFragment infoFragment;
    private ImageView backImg;
    private ImageView favoImg;
    private TextView titleTxt;
    private ImageView nextImg, lastImg;
    private TextView currentTxt;
    private int index;
    private String propertyCount;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        StatusBarCompat.setStatusBarColor(this, Color.parseColor("#BB2E2D"), false);
        initView();
        init();
        showLoadingTips();
    }

    private void initView() {
//        titleBar = findViewById(R.id.detail_titlebar_top);
//        ssdTxt = findViewById(R.id.item_icon_ssd);
        addDetailTxt = findViewById(R.id.detail_view_address);
        houseEnNameTxt = findViewById(R.id.detail_txt_en_housename);
        houseChNameTxt = findViewById(R.id.detail_txt_ch_housename);
        icoStatu = findViewById(R.id.detail_icon_statu);

        favoImg = findViewById(R.id.title_img_favo);
        backImg = findViewById(R.id.title_backicon);
        backImg.setOnClickListener(v -> finish());
        titleTxt = findViewById(R.id.title_txt_title);

        nextImg = findViewById(R.id.detail_txt_next);
        nextImg.setOnClickListener(v -> {
            index++;
            if (index >= Integer.parseInt(propertyCount))
                index = Integer.parseInt(propertyCount);
            infoFragment.resetFragment();
            present.getPropertyDetail(index);
            loadingDialog.show();
        });

        lastImg = findViewById(R.id.detail_txt_last);
        lastImg.setOnClickListener(v -> {
            index--;
            if (index <= 0)
                index = 0;
            infoFragment.resetFragment();
            present.getPropertyDetail(index);
            loadingDialog.show();
        });

        currentTxt = findViewById(R.id.detail_txt_currentitem);


        favoImg.setOnClickListener(v -> {
            FavoriteDescription description = new FavoriteDescription();
            description.setKeyId(keyId);
            if (favoImg.isSelected())
                getFavo(HttpUtil.URL_CANCELFAVO, description, headerDescription);
            else
                getFavo(HttpUtil.URL_FAVORITE, description, headerDescription);
        });
        addDetailTxt.setOnClickListener(v -> {
            PropertyAddDescription detailsDescription = new PropertyAddDescription();
            detailsDescription.setKeyId(present.getPropertyKey(index));
            present.doPost(URL_ADDRESS_DETAIL, headerDescription, detailsDescription);
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void init() {
        headerDescription = ((MyApplication) getApplicationContext()).getHeaderDescription();

        L.d("basicInfo", headerDescription.toString());

        keyId = getIntent().getStringExtra("keyId");
        present = new DetailPresent(this);
        PropertyAddDescription detailsDescription = new PropertyAddDescription();
        detailsDescription.setKeyId(keyId);

        present.doPost(HttpUtil.URL_DETAIL, headerDescription, detailsDescription);
        EventBus.getDefault().register(this);

        DetaileNextKeyIdDescription detaileNextKeyIdDescription = new DetaileNextKeyIdDescription();
        //todo 后面可以改成限定
        detaileNextKeyIdDescription.setPageSize(500);

        index = getIntent().getIntExtra("index", 1);
        detaileNextKeyIdDescription.setPageIndex(index / 100 + 1);
        detaileNextKeyIdDescription.setPropertyType(1);
        present.doGet(HttpUtil.URL_DETAILE_NEXT_KEYID, headerDescription, detaileNextKeyIdDescription);

        propertyCount = getIntent().getStringExtra("propertyCount");
        currentTxt.setText((index + 1) + "/" + propertyCount);

        startScreenListen();

//        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        infoFragment = BasicInfoFragment.newInstance(keyId);
        infoFragment.setaPresenter(present);
//        transaction.replace(R.id.detail_fl_content, infoFragment);
//        transaction.commit();
        trunToFragment(infoFragment);
    }

    private void trunToFragment(Fragment fragment) {

        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.detail_fl_content,fragment);
        transaction.commit();

//        android.support.v4.app.FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
//        transaction1.add(fragment,"");
//        transaction1.commit();

    }


    private void startScreenListen() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        }

        screenShotListenManager = ScreenShotListenManager.newInstance(this);
        screenShotListenManager.setListener((imagePath) -> onScreenShot());
        screenShotListenManager.startListen();
    }

    private void onScreenShot() {
        UserBehaviorDescription userBehaviorDescription = new UserBehaviorDescription();
        userBehaviorDescription.setAction(1);
        //todo 截屏监听还要完善
        userBehaviorDescription.setPage(1);
        userBehaviorDescription.setExtras("{" + "\"PropertyKeyId\"" + ":" + "[" + "\"" + keyId + "\"" + "]}");
        L.d(thiz, "\"HouseShot\"" + "{" + "PropertyKeyId" + ":" + keyId + "}");
        present.doPost(HttpUtil.URL_USER_BEHAVIOR, headerDescription, userBehaviorDescription);

        //todo other Api
//        DetailListsDescription description = new DetailListsDescription();
//        description.setKeyId(keyId);
//        present.doGet(HttpUtil.URL_DETAILS_LIST, ((MyApplication) getContext().getApplicationContext()).getHeaderDescription(), description);
    }

    @Override
    protected void onStop() {
        screenShotListenManager.stopListen();
        super.onStop();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEventBus messageEvent) {

        switch (messageEvent.getMsg()) {
            case DETAIL_ADDRESS:

                DetailAddressResponse address = (DetailAddressResponse) messageEvent.getObject();
                L.d(thiz, address.toString());
                if (address.getErrorMsg() != null && !address.getErrorMsg().equals("")) {
                    showPermissionTipDialog(address.getErrorMsg());
                    return;
                }

                detailHouseData.setUserIsShowDetailFloor(true);
                if (address != null) {
                    houseChNameTxt.setText(address.getDetailAddressChInfo());
                    houseEnNameTxt.setText(address.getDetailAddressEnInfo());
                    detailHouseData.setDetailAddressChInfo(address.getDetailAddressChInfo());
                    detailHouseData.setDetailAddressEnInfo(address.getDetailAddressEnInfo());
                    addDetailTxt.setVisibility(View.GONE);
                }

                break;
            case SEARCH_ALL_NO:
                //todo 提示彈框
                showPermissionTipDialog(getString(R.string.dialog_tips_permission_follow_no_look));
                break;
            case FOLLOW_ADD_NO:
                showPermissionTipDialog(getString(R.string.dialog_tips_permission_follow_no_add));
                break;
            case CLIENTINFO_NO:
                showPermissionTipDialog(getString(R.string.dialog_tips_permission_clientinfo_no_look));
                break;
            case NETWORK_STATE_FAIL:
                showPermissionTipDialog(getString(R.string.dialog_tips_network_fail));
                loadingDialog.dismiss();
                break;
            case DETAIL_REFRESH:
                TrustorDescription trustorDescription = new TrustorDescription();
                trustorDescription.setKeyId(keyId);
                String number = System.currentTimeMillis() / 1000 + "";
                L.d("time", number);
                headerDescription.setNumber(number);
                headerDescription.setSign(MD5Util.getMD5Str("CYDAP_com-group~Centa@" + number + headerDescription.getStaffno()));
                present.doPost(HttpUtil.URL_TRUSTOR, headerDescription, trustorDescription);
                break;
        }
    }

    private void showPermissionTipDialog(String per) {
        SimpleTipsDialog simpleTipsDialog = new SimpleTipsDialog();
        simpleTipsDialog.setContentString(per);
        simpleTipsDialog.setLeftBtnVisibility(false);
        simpleTipsDialog.show(getSupportFragmentManager(), "");
    }

    @Override
    public void refreshListData(final DetailHouse data) {
        runOnUiThread(() -> {
            loadingDialog.dismiss();
           currentTxt.setText((index + 1) + "/" + propertyCount);

//            infoFragment = BasicInfoFragment.newInstance(present.getPropertyKey(index));
            infoFragment.setaPresenter(present);
            infoFragment.setBasicInfo(data);
//            trunToFragment(infoFragment);


            if (data.isUserIsShowTrustor()) {
//                tabTitles.add("業主資料");
//                fragmentList.add(new ClientInfoFragment());
//                adapter.notifyDataSetChanged();
//                tab.setupWithViewPager(vp);
//                //todo 必须先绑定vp 再添加title才不会无法显示title 这是为什么呢？
//                initFragmentTitles(tabTitles);
            }


            if (!data.isUserIsShowDetailFloor()) {
                if (!data.getUserIsShowAddressDetail()) {
                    addDetailTxt.setVisibility(View.VISIBLE);
                    houseChNameTxt.setText(data.getDetailAddressChNoFoolrInfo());
                    houseEnNameTxt.setText(data.getDetailAddressEnNoFoolrInfo());
                } else {
                    houseChNameTxt.setText(data.getDetailAddressChInfo());
                    houseEnNameTxt.setText(data.getDetailAddressEnInfo());
                    addDetailTxt.setVisibility(View.GONE);
                }
            } else {
                addDetailTxt.setVisibility(View.GONE);
                houseChNameTxt.setText(data.getDetailAddressChInfo());
                houseEnNameTxt.setText(data.getDetailAddressEnInfo());
            }

            favoImg.setSelected(data.isFavorite());

            detailHouseData = data;
            isODish = data.isODish();

//            titleBar.setTitleContent(getString(R.string.house_umber) + ":" + data.getPropertyNo());
            titleTxt.setText(getString(R.string.house_umber) + ":" + data.getPropertyNo());
            setIconViewLevel(0, data.getPropertyStatus());
            showHouseTips(data.getPropertyNote(), R.layout.item_dialog_note);
            showHouseTips(data.isODish() ? data.getPropertyProprietors() : null, R.layout.item_dialog_detailtips);

        });
    }

    @Override
    public void refreshFragment(DetailBriefInfo briefInfo) {
        infoFragment.setData(briefInfo);
    }

    private void showHouseTips(String tips, int layoutId) {
        if (tips != null && tips.length() > 0) {
            View view = View.inflate(getContext(), layoutId, null);

            SimpleTipsDialog simpleTipsDialog = new SimpleTipsDialog(view, 0.72, 0.50, R.id.item_tips_owner);
            simpleTipsDialog.setLeftBtnVisibility(false);
            simpleTipsDialog.setContentString(tips);
            simpleTipsDialog.show(getSupportFragmentManager(), "");
        }
    }

    private void setIconViewLevel(int level, String properties) {
        if (properties == null) return;
        switch (properties.substring(0, 1)) {
            case "N":
                level = 1;
                break;
            case "P":
                level = 2;
                break;
            case "S":
                level = 6;
                break;
            case "T":
                level = 4;
                break;
            case "G":
                level = 5;
                break;
            default:
                level = 3;
                break;
        }
        icoStatu.setImageResource(R.drawable.level_list_propertystatus);
        icoStatu.setImageLevel(level);
    }

    public void getFavo(String url, FavoriteDescription detailsDescription, AHeaderDescription aHeaderDescription) {
        HttpUtil.doPost(url, detailsDescription, aHeaderDescription, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String databack = response.body().string().toString();
                if (response.isSuccessful())
                    switch (url) {

                        case HttpUtil.URL_CANCELFAVO:
                            if (parseFavo(databack))
                                setFavo(false);
                            break;
                        case HttpUtil.URL_FAVORITE:
                            if (parseFavo(databack))
                                setFavo(true);
                            break;
                    }
            }
        });
    }

    private void setFavo(boolean favo) {
        runOnUiThread(() -> {
            detailHouseData.setFavorite(favo);
            favoImg.setSelected(favo);
            infoFragment.setBasicInfo(detailHouseData);
        });
    }

    private boolean parseFavo(String dataBack) {
        try {
            FavoResponse response1 = GsonUtil.parseJson(dataBack, FavoResponse.class);
            return response1.isFlag();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void showLoadingTips() {
        loadingDialog = new LoadingDialog(this);
        loadingDialog.setCancelable(false);
        loadingDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (present != null) {
            present.onDestroy();
            present = null;
            System.gc();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 2:

                detailHouseData = (DetailHouse) data.getSerializableExtra("FollowBackData");
                if (detailHouseData.isUserIsShowDetailFloor()) {
                    addDetailTxt.setVisibility(View.GONE);
                    houseChNameTxt.setText(detailHouseData.getDetailAddressChInfo());
                    houseEnNameTxt.setText(detailHouseData.getDetailAddressEnInfo());
                }
                favoImg.setSelected(detailHouseData.isFavorite());
                infoFragment.setBasicInfo(detailHouseData);
                break;
        }
    }

    @Override
    public void turnToActivity(Intent intent) {
        intent.putExtra("keyId", present.getPropertyKey(index));
        intent.putExtra("DetailData", detailHouseData);
        startActivityForResult(intent, 0);
    }
}
