package com.centanet.hk.aplus.Views.HouseDetailView.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.centanet.hk.aplus.Views.Dialog.SimpleTipsDialog;
import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.Views.HouseDetailView.present.DetailPresent;
import com.centanet.hk.aplus.Views.HouseDetailView.present.IDetailPresent;
import com.centanet.hk.aplus.Views.Unensure.BasicInfoFragment;
import com.centanet.hk.aplus.Views.Unensure.FollowFragment;
import com.centanet.hk.aplus.Views.abst.DetailActivityAbst;
import com.centanet.hk.aplus.Widgets.TitleBar;
import com.centanet.hk.aplus.entity.detail.DetailHouse;
import com.centanet.hk.aplus.entity.http.DetailsDescription;
import com.centanet.hk.aplus.entity.http.FollowDescription;
import com.centanet.hk.aplus.entity.http.AHeaderDescription;
import com.centanet.hk.aplus.entity.http.TrustorDescription;
import com.centanet.hk.aplus.eventbus.MessageEventBus;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.centanet.hk.aplus.MyApplication.getContext;
import static com.centanet.hk.aplus.Utils.net.HttpUtil.URL_ADDRESS_DETAIL;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.DetailRefreshView.DETAIL_ADDRESS;

/**
 * Created by mzh1608258 on 2018/1/3.
 */

public class DetailActicity extends DetailActivityAbst implements IDetailView, FollowFragmentAbst.OnUpdateListener, ClientInfoFragment.ClientUpDateListener {

    private String thiz = getClass().getSimpleName();
    private String keyId;
    private IDetailPresent present;
    private ImageView iconHot, iconKey, iconO, iconL, iconD, iconSingle, iconFavo, icoStatu;
    private TextView ssdTxt, clineNameTxt, houseEnNameTxt, houseChNameTxt, addDetailTxt;
    private TitleBar titleBar;
    private DetailHouse detailHouseData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        init();
    }

    private void initView() {
        titleBar = findViewById(R.id.detail_titlebar_top);
        ssdTxt = findViewById(R.id.item_icon_ssd);
        addDetailTxt = findViewById(R.id.detail_txt_adddetail);
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
        addDetailTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailsDescription detailsDescription = new DetailsDescription();
                detailsDescription.setKeyId(keyId);
                present.doPost(URL_ADDRESS_DETAIL, new AHeaderDescription(), detailsDescription);
            }
        });
    }

    private void init() {
        keyId = getIntent().getStringExtra("keyId");
        present = new DetailPresent(this);
        DetailsDescription detailsDescription = new DetailsDescription();
        detailsDescription.setKeyId(keyId);
        present.doPost(HttpUtil.URL_DETAIL, new AHeaderDescription(), detailsDescription);
        EventBus.getDefault().register(this);
    }

    @Override
    protected Fragment[] setFragments() {

        Fragment[] fragments = new Fragment[3];
        fragments[0] = new BasicInfoFragment();
        fragments[1] = new FollowFragment();
        fragments[2] = new ClientInfoFragment();

        return fragments;
    }

    @Override
    protected String[] setFragemtsTitle() {
        String[] titles = new String[3];
        titles[0] = "基本資料";
        titles[1] = "跟進";
        titles[2] = "業主資料";
        return titles;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEventBus messageEvent) {

        if (messageEvent.getMsg() == DETAIL_ADDRESS) {
            String address = (String) messageEvent.getObject();
            houseChNameTxt.setText(address);
        }
    }

    @Override
    public void refreshListData(final DetailHouse data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                detailHouseData = data;
                iconSingle.setSelected(data.isHasOnlyTrust());
                iconFavo.setSelected(data.isFavorite());
                iconO.setSelected(data.isODish());
                iconKey.setImageLevel(data.getPropertyKeyType());
                iconHot.setSelected(data.getHotList() == null ? false : true);
                iconL.setSelected(data.isConfirmed());
                iconD.setSelected(data.getDevelopmentEndCredits() == null ? false : true);
                clineNameTxt.setText(data.getPropertyBuildingOwner() == "" ? getString(R.string.detail_no_owner) : data.getPropertyBuildingOwner());
                houseChNameTxt.setText(data.getDetailAddressChNoFoolrInfo());
                houseEnNameTxt.setText(data.getDetailAddressEnNoFoolrInfo());
                titleBar.setTitleContent(getString(R.string.house_umber) + ":" + data.getPropertyNo());
                setIconViewLevel(0, data.getPropertyStatus());
                showODishTips(data.getPropertyProprietors());
                showNotoTips(data.getPropertyNote());
                if (data.getSSDType() != 0) {
                    ssdTxt.setVisibility(View.VISIBLE);
                    int per = 5 * data.getSSDType();
                    if (data.getSSDType() == 1) per = 0;
                    ssdTxt.setText(per + "%");
                }
            }
        });

    }

    private void showODishTips(String developmentEndCredits) {
        if (developmentEndCredits != null && developmentEndCredits.length() > 0) {
            View view = View.inflate(getContext(), R.layout.item_dialog_detailtips, null);

            SimpleTipsDialog simpleTipsDialog = new SimpleTipsDialog(view, 0.72, 0.50, R.id.item_tips_owner);
            L.d(thiz, developmentEndCredits);
            simpleTipsDialog.setLeftBtnVisibility(false);
            simpleTipsDialog.setContentString(developmentEndCredits);
            simpleTipsDialog.show(getSupportFragmentManager(), "");
        }
    }

    private void showNotoTips(String note) {
        if (note != null && note.length() > 0) {
            View view = View.inflate(getContext(), R.layout.item_dialog_note, null);
            SimpleTipsDialog simpleTipsDialog = new SimpleTipsDialog(view, 0.72, 0.50, R.id.dialog_note_content_txt);
            L.d(thiz, note);
            simpleTipsDialog.setLeftBtnVisibility(false);
            simpleTipsDialog.setContentString(note);
            simpleTipsDialog.setTipString(getString(R.string.detail_attention));
            simpleTipsDialog.show(getSupportFragmentManager(), "");
        }
    }

    private void setIconViewLevel(int level, String properties) {
        L.d(thiz, properties);
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
        icoStatu.setImageLevel(level);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onRefresh(String address, AHeaderDescription AHeaderDescription, FollowDescription body) {
        body.setPropertyKeyId(keyId);
        present.doPost(address, AHeaderDescription, body);
    }

    @Override
    public void trunToActivity(Intent intent) {
        intent.putExtra("keyId", keyId);
        intent.putExtra("DetailData", detailHouseData);
        startActivity(intent);
    }

    @Override
    public void clearFlag() {
        present.clearNetFlag();
    }

    @Override
    public void getClientInfo(String address, AHeaderDescription AHeaderDescription, TrustorDescription body) {
        body.setKeyId(keyId);
        present.doPost(address, AHeaderDescription, body);
    }
}
