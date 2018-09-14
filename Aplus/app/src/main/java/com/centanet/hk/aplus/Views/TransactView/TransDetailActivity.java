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
import android.widget.Toast;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.DialogUtil;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.MD5Util;
import com.centanet.hk.aplus.Utils.TextUtil;
import com.centanet.hk.aplus.Utils.TimeLimitUtil;
import com.centanet.hk.aplus.Utils.net.GsonUtil;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.Views.Dialog.LoadingDialog;
import com.centanet.hk.aplus.Views.basic.BasicActivty;
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
import com.centanet.hk.aplus.bean.trans_detail.TransDetailRequest;
import com.centanet.hk.aplus.bean.trans_detail.TransDetailResponse;
import com.centanet.hk.aplus.bean.translist.Transaction;
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

public class TransDetailActivity extends BasicActivty {

    private ListView listView;
    private List<Transaction> transactions;
    private TranstractAdapter adapter;
    private AHeaderDescription header;
    private String keyId;
    private SmartRefreshLayout refreshLayout;
    private View addTxt;
    private TextView addChTxt, addEnTxt;
    //    private DetailHouse houseData;
    private TextView title;
    private ImageView favoImg, backImg, tranStatuImg;
    private String thiz = getClass().getSimpleName();

    private TransDetailRequest request;
    private boolean isDataEnd;
    private LoadingDialog loadingDialog;
    private String selectId;
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
        showLoadingTips();
        startScreenListen();
    }

    @Override
    protected void onStop() {
        super.onStop();
        screenShotListenManager.stopListen();
    }

    private void init() {
        request = new TransDetailRequest();
        Intent intent = getIntent();
        keyId = intent.getStringExtra("keyId");
        selectId = intent.getStringExtra("tans_keyId");
        request.setKeyId(keyId);

        L.d("TransDetailActivity", keyId);
        transactions = new ArrayList<>();
        adapter = new TranstractAdapter(this, transactions, selectId);
        listView.setAdapter(adapter);
        header = ApplicationManager.getApplication().getHeaderDescription();

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

    private void initLisenter() {

        refreshLayout.setOnRefreshListener(refreshlayout -> {
            request.setPageIndex(1);
            refreshLayout.setEnableLoadmore(true);
            getTransDetail();
        });

        refreshLayout.setOnLoadmoreListener(refreshlayout -> {
            request.setPageIndex(request.getPageIndex() + 1);
            getTransDetail();
        });

        addTxt.setOnClickListener(v -> {
            PropertyAddDescription detailsDescription = new PropertyAddDescription();
            detailsDescription.setKeyId(keyId);
            getAdd(detailsDescription, header);
        });

        backImg.setOnClickListener(v -> {
            finish();
        });
    }


    private void reFreshTrans(List<Transaction> transactions) {
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
//            houseData.setUserIsShowDetailFloor(true);
//            houseData.setDetailAddressEnInfo(address.getDetailAddressEnInfo());
//            houseData.setDetailAddressChInfo(address.getDetailAddressChInfo());
        });
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
        userBehaviorDescription.setExtras("{" + "\"" + "PropertyKeyId" + "\"" + ":" + "\"" + keyId + "\"" + "," + getExtras() + "}");
        L.d("userBehaviorDescription.setExtras", "{" + "\"" + "PropertyKeyId" + "\"" + ":" + "\"" + keyId + "\"" + "," + getExtras() + "}");

        String number = System.currentTimeMillis() / 1000 + "";
        L.d("time", number);
        header.setNumber(number);
        header.setSign(MD5Util.getMD5Str("CYDAP_com-group~Centa@" + number + header.getStaffno()));

        HttpUtil.doPost(HttpUtil.URL_USER_BEHAVIOR, userBehaviorDescription, header, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.d("TransDetailActivity_OnScreen", response.body().string().toString());
            }
        });
    }

    private String getExtras() {
        if (listView.getCount() == 0) return null;
        int firstVisiblePosition = listView.getFirstVisiblePosition();
        int lastVisiblePosition = listView.getLastVisiblePosition();

        String extras = "\"TransactionKeyId\"" + ":" + "[";
        for (int i = firstVisiblePosition; i <= lastVisiblePosition; i++) {
            extras = extras + "\"" + transactions.get(i).getKeyId() + "\"" + ",";
        }
        extras = extras.substring(0, extras.length() - 1);
        extras = extras + "]";
        L.d(thiz, "onShot: " + extras);
        return extras;
    }


    public void setViewData(TransDetailResponse viewData) {

        if (!TextUtil.isEmply(viewData.getPropertyStatusCode()))
            setIconViewLevel(viewData.getPropertyStatusCode());

        if (!viewData.isUserIsShowDetailFloor()) {
            if (!viewData.isUserIsShowAddressDetail()) {
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

    private void showLoadingTips() {
        loadingDialog = new LoadingDialog(this);
        loadingDialog.setCancelable(false);
        loadingDialog.show();
    }

    private void setIconViewLevel(String code) {
        int level = 0;

        switch (code) {
            case "100":
                level = 1;
                break;
            case "200":
                level = 2;
                break;
            case "700":
                level = 6;
                break;
            case "600":
                level = 4;
                break;
            case "400":
                level = 5;
                break;
            default:
                level = 3;
                break;
        }
        tranStatuImg.setImageResource(R.drawable.level_list_propertystatus);
        tranStatuImg.setImageLevel(level);
    }

    private void getTransDetail() {
        HttpUtil.doPost(HttpUtil.URL_TAG_TRAN_DETAIL, request, header, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                DialogUtil.getSimpleDialog(getString(R.string.dialog_tips_network_fail)).show(getSupportFragmentManager(), "");
                loadingDialog.dismiss();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String dataBack = response.body().string().toString();
                L.d("getTransDetail", dataBack);
                if (response.isSuccessful()) {
                    try {
                        TransDetailResponse detailResponse = GsonUtil.parseJson(dataBack, TransDetailResponse.class);
                        if (detailResponse != null)
                            parseResponse(detailResponse);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void parseResponse(TransDetailResponse detailResponse) {
        runOnUiThread(() -> {
            if (detailResponse.getTransactions().size() < 15)
                Toast.makeText(this, getString(R.string.no_more_data), Toast.LENGTH_SHORT).show();
            reFreshTrans(detailResponse.getTransactions());
            setViewData(detailResponse);
            loadingDialog.dismiss();
        });
    }

    class TranstractAdapter extends BaseAdapter {

        private List<Transaction> transactions;
        private Context context;
        private String selectId;

        public TranstractAdapter(Context context, List<Transaction> transactions, String selectId) {
            this.transactions = transactions;
            this.context = context;
            this.selectId = selectId;
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
//                view.setSelected(false);
//                view.findViewById(R.id.content).setBackgroundColor(getResources().getColor(R.color.colortheme));

                viewHolder = new ViewHolder();
                viewHolder.proxyTxt = view.findViewById(R.id.detail_transact_txt_proxy);
                viewHolder.transactPriceTxt = view.findViewById(R.id.detail_transact_txt_transactprice);
                viewHolder.managerTxt = view.findViewById(R.id.detail_transact_txt_manager);
                viewHolder.content = view.findViewById(R.id.content);
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
                Transaction data = transactions.get(position);

                viewHolder.content.setBackgroundColor(getResources().getColor(R.color.color_white));
                if (data.getKeyId().equals(selectId))
                    viewHolder.content.setBackgroundColor(getResources().getColor(R.color.color_pink));

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

                viewHolder.realSizeTxt.setText(TextUtil.getInteger(data.getBuildSquareFoot()) + " " + getString(R.string.ruler));
                viewHolder.realPriceTxt.setText("$" + TextUtil.getInteger(data.getGrossAveragePrice()));
                viewHolder.useSizeTxt.setText(TextUtil.getInteger(data.getUseSquareFoot()) + " " + getString(R.string.ruler));
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
            private TextView realSizeTxt, useSizeTxt, realPriceTxt, usePriceTxt, bargainStxt;
            private TextView rentDateToStxt;
            private ImageView stateImg;
            private View content;
        }
    }
}
