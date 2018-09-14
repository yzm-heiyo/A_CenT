package com.centanet.hk.aplus.Views.HouseDetailView.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.TextUtil;
import com.centanet.hk.aplus.Views.HouseDetailView.view.IDataManager;
import com.centanet.hk.aplus.bean.detail.APDayBook;
import com.centanet.hk.aplus.bean.detail.APTransactionAnalysis;
import com.centanet.hk.aplus.bean.detail.DetailDataInformation;

import java.math.BigDecimal;
import java.text.NumberFormat;

import static com.centanet.hk.aplus.Utils.TextUtil.getInteger;

/**
 * Created by yangzm4 on 2018/7/13.
 */

public class DataInfoFragment extends Fragment implements IDataManager<DetailDataInformation> {

    private Context context;
    private TextView markTxt, centaPercentTxt, phonePercentTxt;
    private TextView bargainTxt, priceTxt, ownerTxt;
    private TextView noDataTxt;
    private View contentView;
    private View progressBar;
    private View dataInfoView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContext();
        View view = inflater.inflate(R.layout.item_detail_record_date_information, null, false);
        progressBar = view.findViewById(R.id.progressBar);
        initView(view);
        return view;
    }

    void initView(View view) {

        contentView = view.findViewById(R.id.datainfo_ll_content);
        ownerTxt = view.findViewById(R.id.datainfo_txt_owner);
        priceTxt = view.findViewById(R.id.datainfo_txt_transprice);
        bargainTxt = view.findViewById(R.id.datainfo_txt_bargain);
        phonePercentTxt = view.findViewById(R.id.datainfo_txt_phonepercent);
        centaPercentTxt = view.findViewById(R.id.datainfo_txt_centapercent);
        markTxt = view.findViewById(R.id.datainfo_txt_marktrans);
        noDataTxt = view.findViewById(R.id.record_txt_nodata);
        dataInfoView = view.findViewById(R.id.datainfo_ll_content);

    }

    public void resetFragment() {
        progressBar.setVisibility(View.VISIBLE);
        dataInfoView.setVisibility(View.GONE);
        noDataTxt.setVisibility(View.GONE);
    }

    @Override
    public void setData(DetailDataInformation data) {
        progressBar.setVisibility(View.GONE);
        if (data == null || (data.getDaybook() == null && data.getTransactionAnalysis() == null)) {
            contentView.setVisibility(View.GONE);
            noDataTxt.setVisibility(View.VISIBLE);
            return;
        }

        dataInfoView.setVisibility(View.VISIBLE);

        APTransactionAnalysis estate = data.getTransactionAnalysis();
//        if (estate != null && estate.length() > 0) {
//            markTxt.setText(estate.substring(estate.indexOf("共") + 1, estate.indexOf("共") + 2));
//            centaPercentTxt.setText(estate.substring(estate.indexOf("佔") + 1, estate.indexOf("佔") + 2));
//            centaPercentTxt.setText(estate.substring(estate.indexOf("話") + 1, estate.indexOf("話") + 2));
//        }

        if (estate != null) {
            markTxt.setText(estate.getCountIn30Days() + "");
            centaPercentTxt.setText(estate.getCountIn30DaysCentaline() + "");
            phonePercentTxt.setText(estate.getCountIn30DaysTelephone() + "");
        }

        APDayBook dayb = data.getDaybook();
//        L.d("Datainfo", dayb);

//        if (dayb != null && dayb.length() > 0) {
//            bargainTxt.setText(dayb.substring(0, dayb.indexOf(" ")));
//            priceTxt.setText(dayb.substring(dayb.indexOf(" ") + 1, dayb.lastIndexOf(";")));
//            ownerTxt.setText(dayb.substring(dayb.lastIndexOf(";") + 1));
//        }
        if (dayb != null) {
            if (dayb.getDate() != null) {
                if (dayb.getDate().indexOf("T") != -1)
                    bargainTxt.setText(dayb.getDate().split("T")[0]);
            } else bargainTxt.setText(dayb.getDate());

            try {
                if (!TextUtil.isEmply(dayb.getPrice())) {
//                    priceTxt.setText(TextUtil.getInteger("$:" + dayb.getPrice() + ""));
                    NumberFormat currency = NumberFormat.getCurrencyInstance();
                    String price = currency.format(new BigDecimal(dayb.getPrice()));
                    priceTxt.setText(TextUtil.getInteger(price + ""));
                }
            } catch (Exception e) {

            }


            ownerTxt.setText(dayb.getOwner());
        }
    }
}
