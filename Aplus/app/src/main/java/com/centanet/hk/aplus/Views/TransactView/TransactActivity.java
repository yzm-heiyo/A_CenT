package com.centanet.hk.aplus.Views.TransactView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.MD5Util;
import com.centanet.hk.aplus.Utils.TextUtil;
import com.centanet.hk.aplus.Utils.TimeLimitUtil;
import com.centanet.hk.aplus.Utils.net.GsonUtil;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.Widgets.SmallItemView;
import com.centanet.hk.aplus.bean.detail.DetailAddressResponse;
import com.centanet.hk.aplus.bean.detail.DetailHouse;
import com.centanet.hk.aplus.bean.detail.PropertyTransaction;
import com.centanet.hk.aplus.bean.favo.FavoResponse;
import com.centanet.hk.aplus.bean.http.AHeaderDescription;
import com.centanet.hk.aplus.bean.http.FavoriteDescription;
import com.centanet.hk.aplus.bean.http.PropertyAddDescription;
import com.centanet.hk.aplus.bean.http.TransListDescription;
import com.centanet.hk.aplus.bean.http.UserBehaviorDescription;
import com.centanet.hk.aplus.bean.transrecord_list.TranListResponse;
import com.centanet.hk.aplus.manager.ApplicationManager;
import com.centanet.hk.aplus.manager.ScreenShotListenManager;
import com.githang.statusbar.StatusBarCompat;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by yangzm4 on 2018/7/12.
 */

public class TransactActivity extends AppCompatActivity {

    private ListView listView;
    private List<PropertyTransaction> transactions;
    private TranstractAdapter adapter;
    private AHeaderDescription aHeaderDescription;
    private TransListDescription transListDescription;
    private String keyId;
    private SmartRefreshLayout refreshLayout;
    private View addTxt;
    private TextView addChTxt, addEnTxt;
    private DetailHouse houseData;
    private TextView title;
    private ImageView favoImg, backImg, tranStatuImg;
    private String thiz = getClass().getSimpleName();
    private int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 123;
    private ScreenShotListenManager screenShotListenManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transact);
        StatusBarCompat.setStatusBarColor(this, Color.parseColor("#BB2E2D"), false);
        initView();
        init();
        initLisenter();
        startScreenListen();
    }

    @Override
    protected void onStop() {
        super.onStop();
        screenShotListenManager.stopListen();
    }

    private void init() {
        Intent intent = getIntent();
        keyId = intent.getStringExtra("keyId");
        houseData = (DetailHouse) intent.getSerializableExtra("DetailData");
        setViewData(houseData);
        transactions = new ArrayList<>();
        adapter = new TranstractAdapter(this, transactions);
        listView.setAdapter(adapter);
        aHeaderDescription = ApplicationManager.getApplication().getHeaderDescription();
        transListDescription = new TransListDescription();
        transListDescription.setKeyId(keyId);
//        getTransList(transListDescription, aHeaderDescription);
        refreshLayout.autoRefresh();
    }

    private void initView() {

        listView = findViewById(R.id.transact_list);
        refreshLayout = findViewById(R.id.transact_smartLayout);
        addTxt = findViewById(R.id.transact_view_address);
        addChTxt = findViewById(R.id.trans_txt_ch_housename);
        addEnTxt = findViewById(R.id.trans_txt_en_housename);
        title = findViewById(R.id.title_txt_title);
        favoImg = findViewById(R.id.title_img_favo);
        backImg = findViewById(R.id.title_backicon);

        tranStatuImg = findViewById(R.id.trans_img_icon_statu);

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

//        if (!TimeLimitUtil.isAchieveLimitTime(1000)) return;

        UserBehaviorDescription userBehaviorDescription = new UserBehaviorDescription();
        userBehaviorDescription.setAction(1);
        userBehaviorDescription.setPage(9);
        userBehaviorDescription.setExtras("{" + "\"PropertyKeyId\"" + ":" + "[" + "\"" + keyId + "\"" + "]}");
        L.d(thiz, "\"HouseShot\"" + "{" + "PropertyKeyId" + ":" + keyId + "}");

        String number = System.currentTimeMillis() / 1000 + "";
        L.d("time", number);
        aHeaderDescription.setNumber(number);
        aHeaderDescription.setSign(MD5Util.getMD5Str("CYDAP_com-group~Centa@" + number + aHeaderDescription.getStaffno()));

//        presenter.doAddRequest(HttpUtil.URL_USER_BEHAVIOR, headerDescription, userBehaviorDescription);
        HttpUtil.doPost(HttpUtil.URL_USER_BEHAVIOR, userBehaviorDescription, aHeaderDescription, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.d("TransActivity_OnScreen",response.body().string().toString());
            }
        });

    }


    private void initLisenter() {

        refreshLayout.setOnRefreshListener(refreshlayout -> {
            transListDescription.setPageIndex(1);
            refreshLayout.setEnableLoadmore(true);
            getTransList(transListDescription, aHeaderDescription);
        });

        refreshLayout.setOnLoadmoreListener(refreshlayout -> {
            transListDescription.setPageIndex(transListDescription.getPageIndex() + 1);
            getTransList(transListDescription, aHeaderDescription);
        });

        addTxt.setOnClickListener(v -> {
            PropertyAddDescription detailsDescription = new PropertyAddDescription();
            detailsDescription.setKeyId(keyId);
            getAdd(detailsDescription, aHeaderDescription);
        });

        backImg.setOnClickListener(v -> {
            Intent dbIntent = new Intent();
            dbIntent.putExtra("FollowBackData", houseData);
            this.setResult(2, dbIntent);
            finish();
        });
    }

    public void getTransList(TransListDescription transListDescription, AHeaderDescription aHeaderDescription) {
        L.d(thiz, "getTransList");
        HttpUtil.doPost(HttpUtil.URL_TRAN_LIST, transListDescription, aHeaderDescription, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        TranListResponse tranListResponse = GsonUtil.parseJson(response.body().byteStream(), TranListResponse.class);
                        reFreshTrans(tranListResponse.getTransactions());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        favoImg.setOnClickListener(v -> {
            FavoriteDescription description = new FavoriteDescription();
            description.setKeyId(keyId);
            if (favoImg.isSelected())
                getFavo(HttpUtil.URL_CANCELFAVO, description, aHeaderDescription);
            else
                getFavo(HttpUtil.URL_FAVORITE, description, aHeaderDescription);
        });
    }

    private void reFreshTrans(List<PropertyTransaction> transactions) {
        L.d(thiz, "reFreshTrans");

        if (this.transactions != null && refreshLayout.isRefreshing()) this.transactions.clear();
        this.transactions.addAll(transactions);
        runOnUiThread(() -> adapter.notifyDataSetChanged());
        closeRefresh();
    }

    private void closeRefresh() {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadmore();
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

    private void setFavo(boolean favo) {
        runOnUiThread(() -> {
            houseData.setFavorite(favo);
            favoImg.setSelected(favo);
        });
    }

    public void getAdd(PropertyAddDescription detailsDescription, AHeaderDescription aHeaderDescription) {
        HttpUtil.doPost(HttpUtil.URL_ADDRESS_DETAIL, detailsDescription, aHeaderDescription, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                DetailAddressResponse address = null;
                try {
                    address = GsonUtil.parseJson(response.body().byteStream(), DetailAddressResponse.class);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
                reFreshAdd(address);
            }
        });
    }

    private void reFreshAdd(DetailAddressResponse address) {
        runOnUiThread(() -> {
            addTxt.setVisibility(View.GONE);
            addEnTxt.setText(address.getDetailAddressEnInfo());
            addChTxt.setText(address.getDetailAddressChInfo());
            houseData.setUserIsShowDetailFloor(true);
            houseData.setDetailAddressEnInfo(address.getDetailAddressEnInfo());
            houseData.setDetailAddressChInfo(address.getDetailAddressChInfo());
        });
    }

    public void setViewData(DetailHouse viewData) {

        setIconViewLevel(viewData.getPropertyStatus());

        favoImg.setSelected(viewData.isFavorite());
        if (!viewData.isUserIsShowDetailFloor()) {
            if (!viewData.getUserIsShowAddressDetail()) {
                addTxt.setVisibility(View.VISIBLE);
                addChTxt.setText(viewData.getDetailAddressChNoFoolrInfo());
                addEnTxt.setText(viewData.getDetailAddressEnNoFoolrInfo());
            } else {
                addChTxt.setText(viewData.getDetailAddressChInfo());
                addEnTxt.setText(viewData.getDetailAddressEnInfo());
                addTxt.setVisibility(View.GONE);
            }
        } else {
            addTxt.setVisibility(View.GONE);
            addChTxt.setText(viewData.getDetailAddressChInfo());
            addEnTxt.setText(viewData.getDetailAddressEnInfo());
        }
    }

    private void setIconViewLevel(String properties) {
        int level = 0;
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
        tranStatuImg.setImageResource(R.drawable.level_list_propertystatus);
        tranStatuImg.setImageLevel(level);
    }

    @Override
    public void onBackPressed() {
        Intent dbIntent = new Intent();
        dbIntent.putExtra("FollowBackData", houseData);
        this.setResult(2, dbIntent);
        super.onBackPressed();
    }

    class TranstractAdapter extends BaseAdapter {

        private List<PropertyTransaction> transactions;
        private Context context;

        public TranstractAdapter(Context context, List<PropertyTransaction> transactions) {
            this.transactions = transactions;
            this.context = context;
        }

        @Override
        public int getCount() {
            return transactions.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            TranstractAdapter.ViewHolder viewHolder = null;
            if (convertView == null) {
                view = LayoutInflater.from(context).inflate(R.layout.item_list_transtractrecord, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.proxyTxt = view.findViewById(R.id.detail_transact_txt_proxy);
                viewHolder.transactPriceTxt = view.findViewById(R.id.detail_transact_txt_transactprice);
                viewHolder.managerTxt = view.findViewById(R.id.detail_transact_txt_manager);
                viewHolder.createTimeTxt = view.findViewById(R.id.detail_transact_txt_createtime);
                viewHolder.appointmentDateTxt = view.findViewById(R.id.detail_transact_txt_appointmentdate);
                viewHolder.officeDateTxt = view.findViewById(R.id.detail_transact_txt_officedate);
                viewHolder.finishDateTxt = view.findViewById(R.id.detail_transact_txt_finishdate);

                viewHolder.realSizeTxt = view.findViewById(R.id.detail_transact_txt_realysize);
                viewHolder.realPriceTxt = view.findViewById(R.id.detail_transact_txt_reallyprice);
                viewHolder.useSizeTxt = view.findViewById(R.id.detail_transact_txt_usesize);
                viewHolder.usePriceTxt = view.findViewById(R.id.detail_transact_txt_useprice);

                viewHolder.bargainStxt = view.findViewById(R.id.detail_transact_stxt_bargain);
                viewHolder.rentDateToStxt = view.findViewById(R.id.detail_transact_stxt_rentdateto);
                viewHolder.stateImg = view.findViewById(R.id.detail_transact_img_state);
                viewHolder.confirmTxt = view.findViewById(R.id.trans_txt_status);
                viewHolder.statuTxt = view.findViewById(R.id.trans_txt_confirm);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }

            if (transactions != null && !transactions.isEmpty()) {
                PropertyTransaction data = transactions.get(position);
                L.d("TransDetail", data.toString());
                viewHolder.createTimeTxt.setText("建立日期 " + data.getCreateTime());
                viewHolder.proxyTxt.setText(data.getAgency());
                viewHolder.transactPriceTxt.setSelected(!data.isGreen());
                viewHolder.transactPriceTxt.setText("$ " + TextUtil.getInteger(data.getPrice()) + " " + getUnit(data.getStatus()));
                viewHolder.managerTxt.setText(data.getAgent());
                viewHolder.bargainStxt.setText(data.getTransactionDate());
                viewHolder.rentDateToStxt.setText(data.getRentEndDate());
                viewHolder.appointmentDateTxt.setText(data.getPrelimDate());
                viewHolder.officeDateTxt.setText(data.getFormalDate());
                viewHolder.finishDateTxt.setText(data.getCompleteDate());

                viewHolder.realSizeTxt.setText(TextUtil.getInteger(data.getBuildSquareFoot()));
                viewHolder.realPriceTxt.setText("$" + TextUtil.getInteger(data.getGrossAveragePrice()));
                viewHolder.useSizeTxt.setText(TextUtil.getInteger(data.getUseSquareFoot()));
                viewHolder.usePriceTxt.setText("$" + TextUtil.getInteger(data.getAveragePrice()));

                viewHolder.stateImg.setImageLevel(data.getStatus());
                viewHolder.statuTxt.setText(data.isNotConfirmed() ? "已確認" : "未確認");
                viewHolder.statuTxt.setSelected(data.isNotConfirmed());
                L.d(thiz, "TransConfirm: " + data.isTranferToConfirm());
                viewHolder.confirmTxt.setText(getStatuTxt(data.getStatus()));
            }
            return view;
        }

        private String getUnit(int status) {
            String unit = "";
            switch (status) {
                case 1:
                case 3:
                case 5:
                    unit = "萬";
                    break;
                case 2:
                case 4:
                    unit = "元";
                    break;
            }
            return unit;
        }

        private String getStatuTxt(int statu) {
            String statuStr = null;
            switch (statu) {
                case 1:
                    statuStr = "中原售";
                    break;
                case 2:
                    statuStr = "中原租";
                    break;
                case 3:
                    statuStr = "行家售";
                    break;
                case 4:
                    statuStr = "行家租";
                    break;
                case 5:
                    statuStr = "內部轉讓";
                    break;
            }
            return statuStr;
        }

        private class ViewHolder {
            private TextView proxyTxt, transactPriceTxt, managerTxt, confirmTxt, statuTxt;
            private TextView createTimeTxt, appointmentDateTxt, officeDateTxt, finishDateTxt;
            private TextView realSizeTxt, useSizeTxt, realPriceTxt, usePriceTxt;
            private TextView bargainStxt, rentDateToStxt;
            private ImageView stateImg;
        }
    }
}
