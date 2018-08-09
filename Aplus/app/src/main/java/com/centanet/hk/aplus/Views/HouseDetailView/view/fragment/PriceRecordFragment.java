package com.centanet.hk.aplus.Views.HouseDetailView.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.Utility;
import com.centanet.hk.aplus.Views.HouseDetailView.view.IDataManager;
import com.centanet.hk.aplus.bean.detail.PriceTrust;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangzm4 on 2018/7/13.
 */

public class PriceRecordFragment extends Fragment implements IDataManager<List<PriceTrust>> {

    public static final int PROPERTY_PRICE_SALE = 1;
    public static final int PROPERTY_PRICE_RENT = 2;
    private Context context;
    private RadioGroup typeRG;
    private ListView priceListView;
    private RadioButton saleRB, rentRB;
//    private List<PriceTrust> priceTrusts;
    private OnItemClickLisenter onItemClickLisenter;
    private String thiz = getClass().getSimpleName();
    private LinearLayout contentView;
    private LayoutInflater inflater;
    private TextView noDataTxt;
    private View title;
    private LinearLayout priceContent;
    private ProgressBar progressBar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        View view = inflater.inflate(R.layout.item_detail_record_price, null, false);
        contentView = view.findViewById(R.id.record_ly_price);
        progressBar = view.findViewById(R.id.progressBar);
        initView(view);
        init();
        initLisenter();
        return view;
    }

    void initView(View view) {
        typeRG = view.findViewById(R.id.record_price_rg_type);
        saleRB = view.findViewById(R.id.detail_price_rad_sale);
        rentRB = view.findViewById(R.id.detail_price_rad_rent);
        noDataTxt = view.findViewById(R.id.record_txt_nodata);
        title = view.findViewById(R.id.record_price_title);
        priceContent = view.findViewById(R.id.record_ly_price);
//        noDataTxt = view.findViewById(R.id)
//        priceListView = view.findViewById(R.id.record_price_price_list);
    }

    private void init() {
//        priceTrusts = new ArrayList<>();
        context = getContext();
//        Utility.setListViewHeightBasedOnChildren(priceListView);
    }

    public void setSaleRBClickable(boolean able) {
        typeRG.setOnCheckedChangeListener(null);
        saleRB.setClickable(able);
        saleRB.setChecked(able);
        saleRB.setEnabled(able);
        saleRB.setBackgroundResource(R.drawable.shape_square_circle_single_left_gray);
        saleRB.setTextColor(Color.WHITE);
        typeRG.setOnCheckedChangeListener(onCheckedChangeListener);
    }

//    public void resetSaleRBBg(){
//        saleRB.setBackgroundResource(R.drawable.selecter_square_circle_single_left);
//        saleRB.setClickable(false);
//        saleRB.setChecked(false);
//        saleRB.setEnabled(false);
//        saleRB.setTextColor(R.drawable.selector_dialog_choice_textcolor);
//
//        rentRB.setBackgroundResource(R.drawable.selecter_square_circle_single_right);
//        rentRB.setClickable(false);
//        rentRB.setChecked(false);
//        rentRB.setEnabled(false);
//        rentRB.setTextColor(R.drawable.selector_dialog_choice_textcolor);
//    }
//
//    public void resetRentRBBg(){
//        rentRB.setBackgroundResource(R.drawable.selecter_square_circle_single_right);
//
//    }


    public void setRentRBClickable(boolean able) {
        typeRG.setOnCheckedChangeListener(null);
        rentRB.setClickable(able);
        rentRB.setChecked(able);
        rentRB.setEnabled(able);
        rentRB.setBackgroundResource(R.drawable.shape_square_circle_single_right_gray);
        rentRB.setTextColor(Color.WHITE);
        typeRG.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    public void resetFragment(){
        progressBar.setVisibility(View.VISIBLE);
        contentView.removeAllViews();
        typeRG.setVisibility(View.GONE);
        typeRG.setOnCheckedChangeListener(null);
        noDataTxt.setVisibility(View.GONE);
        saleRB.setTextColor(getResources().getColorStateList(R.color.textcolor_red_white));
        rentRB.setTextColor(getResources().getColorStateList(R.color.textcolor_red_white));
        saleRB.setBackgroundResource(R.drawable.selecter_square_circle_single_left);
        rentRB.setBackgroundResource(R.drawable.selecter_square_circle_single_right);
        rentRB.setClickable(true);
        rentRB.setChecked(false);
        rentRB.setEnabled(true);
        saleRB.setClickable(true);
        saleRB.setChecked(true);
        saleRB.setEnabled(true);

        title.setVisibility(View.GONE);
        priceContent.setVisibility(View.GONE);
        typeRG.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    @Override
    public void setData(List<PriceTrust> data) {
        contentView.removeAllViews();
        progressBar.setVisibility(View.GONE);
        if (data == null || data.isEmpty()) {
            title.setVisibility(View.GONE);
            noDataTxt.setVisibility(View.VISIBLE);
            return;
        }
        L.d(thiz, data.toString() + " DataSize: " + data.size());
        priceContent.setVisibility(View.VISIBLE);
        typeRG.setVisibility(View.VISIBLE);
        noDataTxt.setVisibility(View.GONE);
        title.setVisibility(View.VISIBLE);
//        if (priceTrusts != null) priceTrusts.clear();
//        priceTrusts.addAll(data);
        addItem(data);

    }

    private void addItem(List<PriceTrust> data) {
//        if (data != null) data.clear();
//        contentView.removeAllViews();
        int size = data.size() > 3 ? 3 : data.size();
        for (int i = 0; i < size; i++) {
            PriceTrust trust = data.get(i);
            View view = inflater.inflate(R.layout.item_list_pricerecord, null, false);
            ((TextView) view.findViewById(R.id.item_pricelist_txt_date)).setText(trust.getCreateTime());
            TextView saleTxt = view.findViewById(R.id.item_pricelist_txt_sale);
            saleTxt.setText(trust.getPrice());
            String price = trust.getPrice();
//            L.d(thiz, "Start: " + price.substring(0, price.indexOf(" ")) + " End: " + price.substring(price.lastIndexOf(" ")));
//            int startPrice = Integer.parseInt(price.substring(0, price.indexOf(" ")).replace(",", "").trim());
//            int endPrice = Integer.parseInt(price.substring(price.lastIndexOf(" ")).replace(",", "").trim());
            int rate = Integer.parseInt(trust.getRate().replace(",", ""));
            TextView rateTxt = view.findViewById(R.id.item_pricelist_txt_add_reduce);
            if (rate < 0) {
                rateTxt.setSelected(true);
                saleTxt.setSelected(true);
            }
            if (rate == 0) {
                rateTxt.setTextColor(getResources().getColor(R.color.color_black));
                saleTxt.setTextColor(getResources().getColor(R.color.color_black));
            }
            String unit = saleRB.isChecked() ? "萬" : "元";
            rateTxt.setText(rate + unit + "\n" + "(" + trust.getChangeRate() + "%" + ")");
            ((TextView) view.findViewById(R.id.item_pricelist_txt_person)).setText(trust.getCreateUserName());
            contentView.addView(view);
        }
    }

    public void checkType(int type) {
        switch (type) {
            case PROPERTY_PRICE_SALE:
                saleRB.setChecked(true);
                break;
            case PROPERTY_PRICE_RENT:
                rentRB.setChecked(true);
                break;
        }
    }

    private void initLisenter() {
        typeRG.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.detail_price_rad_sale:
                    if (onItemClickLisenter != null)
                        onItemClickLisenter.onClick(group.findViewById(checkedId), PROPERTY_PRICE_SALE);
                    break;
                case R.id.detail_price_rad_rent:
                    if (onItemClickLisenter != null)
                        onItemClickLisenter.onClick(group.findViewById(checkedId), PROPERTY_PRICE_RENT);
                    break;
            }
        }
    };

    public void setOnItemClickLisenter(OnItemClickLisenter onItemClickLisenter) {
        this.onItemClickLisenter = onItemClickLisenter;
    }

    public interface OnItemClickLisenter {
        void onClick(View v, int type);
    }

}
