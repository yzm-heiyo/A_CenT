package com.centanet.hk.aplus.Views.HouseDetailView.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.centanet.hk.aplus.Adapter.PagerAdapter;
import com.centanet.hk.aplus.MyApplication;
import com.centanet.hk.aplus.Views.Dialog.LoadingDialog;
import com.centanet.hk.aplus.Views.Dialog.SimpleTipsDialog;
import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.Views.HouseDetailView.present.DetailPresent;
import com.centanet.hk.aplus.Views.HouseDetailView.present.IDetailPresent;
import com.centanet.hk.aplus.Widgets.TitleBar;
import com.centanet.hk.aplus.bean.detail.DetailAddress;
import com.centanet.hk.aplus.bean.detail.DetailHouse;
import com.centanet.hk.aplus.bean.http.DetailsDescription;
import com.centanet.hk.aplus.bean.http.FollowDescription;
import com.centanet.hk.aplus.bean.http.AHeaderDescription;
import com.centanet.hk.aplus.bean.http.TrustorDescription;
import com.centanet.hk.aplus.bean.http.VirtualPhoneDescription;
import com.centanet.hk.aplus.eventbus.MessageEventBus;
import com.githang.statusbar.StatusBarCompat;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static com.centanet.hk.aplus.MyApplication.getContext;
import static com.centanet.hk.aplus.Utils.net.HttpUtil.URL_ADDRESS_DETAIL;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.DetailRefresh.DETAIL_REFRESH;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.DetailRefreshView.DETAIL_ADDRESS;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.Permission.CLIENTINFO_NO;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.Permission.FOLLOW_ADD_NO;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.Permission.SEARCH_ALL_NO;

/**
 * Created by mzh1608258 on 2018/1/3.
 */

public class DetailActicity extends AppCompatActivity implements IDetailView, FollowFragment.OnUpdateListener, ClientInfoFragment.ClientUpDateListener {

    private String thiz = getClass().getSimpleName();
    private String keyId;
    private IDetailPresent present;
    private ImageView iconHot, iconKey, iconO, iconL, iconD, iconSingle, iconFavo, icoStatu;
    private TextView ssdTxt, clineNameTxt, houseEnNameTxt, houseChNameTxt;
    private View addDetailTxt;
    private TitleBar titleBar;
    private DetailHouse detailHouseData;
    private AHeaderDescription headerDescription;
    private ViewPager vp;
    private TabLayout tab;
    private FragmentPagerAdapter adapter;
    private List<Fragment> fragmentList;
    private List<String> tabTitles;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        StatusBarCompat.setStatusBarColor(this, Color.parseColor("#BB2E2D"), false);
        initView();
        init();
    }

    private void initView() {
        vp = this.findViewById(R.id.detail_activity_viewpager);
        tab = this.findViewById(R.id.detail_activity_tablayout);
        adapter = new PagerAdapter(getSupportFragmentManager(), setFragments());
        vp.setAdapter(adapter);
        tab.setupWithViewPager(vp);
        initFragmentTitles(getFragemtsTitle());
        titleBar = findViewById(R.id.detail_titlebar_top);
        ssdTxt = findViewById(R.id.item_icon_ssd);
        addDetailTxt = findViewById(R.id.detail_view_address);
        clineNameTxt = findViewById(R.id.item_txt_client);
        houseEnNameTxt = findViewById(R.id.detail_txt_en_housename);
        houseChNameTxt = findViewById(R.id.detail_txt_ch_housename);
        icoStatu = findViewById(R.id.detail_icon_statu);
        iconHot = findViewById(R.id.item_icon_hot);
        iconKey = findViewById(R.id.item_icon_key);
        iconO = findViewById(R.id.item_icon_o);
        iconL = findViewById(R.id.item_icon_l);
        iconD = findViewById(R.id.item_icon_d);
        iconSingle = findViewById(R.id.item_icon_medal);
        iconFavo = findViewById(R.id.item_icon_favo);

        addDetailTxt.setOnClickListener(v -> {
            DetailsDescription detailsDescription = new DetailsDescription();
            detailsDescription.setKeyId(keyId);
            present.doPost(URL_ADDRESS_DETAIL, headerDescription, detailsDescription);
        });
    }

    private void initFragmentTitles(List<String> titles) {
        tab.removeAllTabs();
        for (int i = 0; i < titles.size(); i++) {
            tab.addTab(tab.newTab().setText(titles.get(i)));
        }
    }

    private void init() {
        headerDescription = ((MyApplication) getApplicationContext()).getHeaderDescription();
        keyId = getIntent().getStringExtra("keyId");
        present = new DetailPresent(this);
        DetailsDescription detailsDescription = new DetailsDescription();
        detailsDescription.setKeyId(keyId);
        present.doPost(HttpUtil.URL_DETAIL, headerDescription, detailsDescription);
        EventBus.getDefault().register(this);

    }

    private List<Fragment> setFragments() {

        fragmentList = new ArrayList<>();
        fragmentList.add(new BasicInfoFragment());
        fragmentList.add(new FollowFragment());
//        fragmentList.add(new ClientInfoFragment());

        return fragmentList;
    }

    private List<String> getFragemtsTitle() {
        tabTitles = new ArrayList<>();
        tabTitles.add("基本資料");
        tabTitles.add("跟進");
//        tabTitle.add("業主資料");
        return tabTitles;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEventBus messageEvent) {

        switch (messageEvent.getMsg()) {
            case DETAIL_ADDRESS:
                detailHouseData.setUserIsShowDetailFloor(true);
                DetailAddress address = (DetailAddress) messageEvent.getObject();
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
            case DETAIL_REFRESH:
                TrustorDescription trustorDescription = new TrustorDescription();
                trustorDescription.setKeyId(keyId);
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

            if (data.isUserIsShowTrustor()) {
                tabTitles.add("業主資料");
                fragmentList.add(new ClientInfoFragment());
                adapter.notifyDataSetChanged();
                tab.setupWithViewPager(vp);
                //todo 必须先绑定vp 再添加title才不会无法显示title 这是为什么呢？
                initFragmentTitles(tabTitles);
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

            detailHouseData = data;
            iconSingle.setSelected(data.isHasOnlyTrust());
            iconFavo.setSelected(data.isFavorite());
            iconO.setSelected(data.isODish());
            iconKey.setImageLevel(data.getPropertyKeyType());
            iconHot.setSelected(data.getHotList() == null || data.getHotList().equals("") ? false : true);
            iconL.setSelected(!data.isConfirmed());
            iconD.setSelected(data.getDevelopmentEndCredits());
            clineNameTxt.setText(data.getPropertyBuildingOwner() == "" ? " " + getString(R.string.detail_no_owner) : " " + data.getPropertyBuildingOwner());
//            houseChNameTxt.setText(!data.isUserIsShowDetailFloor() ? data.getDetailAddressChInfo() : data.getDetailAddressChNoFoolrInfo());
//            houseEnNameTxt.setText(!data.isUserIsShowDetailFloor() ? data.getDetailAddressEnInfo() : data.getDetailAddressEnNoFoolrInfo());

            titleBar.setTitleContent(getString(R.string.house_umber) + ":" + data.getPropertyNo());
            setIconViewLevel(0, data.getPropertyStatus());
            showHouseTips(data.getPropertyNote(), R.layout.item_dialog_note);
            showHouseTips(data.isODish() ? data.getPropertyProprietors() : null, R.layout.item_dialog_detailtips);
            if (data.getSSDType() != 0) {
                ssdTxt.setVisibility(View.VISIBLE);
                int per = 5 * data.getSSDType();
                if (data.getSSDType() == 1) per = 0;
                ssdTxt.setText(per + "%");
            }
        });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onRefresh(String address, AHeaderDescription aHeaderDescription, FollowDescription body) {
        body.setPropertyKeyId(keyId);
        present.doPost(address, headerDescription, body);
    }

    @Override
    public void trunToActivity(Intent intent) {
        intent.putExtra("keyId", keyId);
        intent.putExtra("DetailData", detailHouseData);
        startActivityForResult(intent, 0);
    }

    @Override
    public void clearFlag() {
        present.clearNetFlag();
    }

    @Override
    public void getClientInfo(String address, AHeaderDescription AHeaderDescription, VirtualPhoneDescription body) {
        body.setItemKeyId(keyId);
        present.doPost(address, headerDescription, body);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 2:
                addDetailTxt.setVisibility(View.GONE);
                detailHouseData.setUserIsShowDetailFloor(true);
                String detailAddressChInfo = data.getStringExtra("DetailAddressChInfo");
                String detailAddressEnInfo = data.getStringExtra("DetailAddressEnInfo");
                detailHouseData.setDetailAddressChInfo(detailAddressChInfo);
                detailHouseData.setDetailAddressEnInfo(detailAddressEnInfo);
                houseChNameTxt.setText(detailAddressChInfo);
                houseEnNameTxt.setText(detailAddressEnInfo);
                break;
        }
    }
}
