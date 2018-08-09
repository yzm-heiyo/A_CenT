package com.centanet.hk.aplus.Views.ComplexSearchView;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Views.basic.BaseFragment;
import com.centanet.hk.aplus.Widgets.ProcessBarView;
import com.centanet.hk.aplus.bean.params.SystemParamItems;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yangzm4 on 2018/7/24.
 */

public class RentFragment extends BaseFragment implements View.OnClickListener {

    public static String ARGUMENT = "SysItemFragment";
    private View ssd, ssd10;
    private LinearLayout content;
    private ProcessBarView processBarView;
    private EditText priceStartEdit, priceEndEdit;
    private View price400, price_400_600, price_600_800, price_800_1000, price_1000_2000, price_2000_3000, priceUp;
    private View conGreen, showGreen;
    private OnRentChangeLisenter onRentChangeLisenter;
    private RentPrice rentPrice;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRentChangeLisenter) {
            onRentChangeLisenter = (OnRentChangeLisenter) context;
        } else
            throw new IllegalArgumentException("Activity  Must Implements RentFragment.OnRentChangeLisenter");
    }

    public static RentFragment newInstance(RentPrice argument) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARGUMENT, argument);
        RentFragment contentFragment = new RentFragment();
        contentFragment.setArguments(bundle);
        return contentFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rent, null, false);
        init();
        initView(view);
        initLisenter();
        return view;
    }

    private void init() {
        rentPrice = (RentPrice) getArguments().get(ARGUMENT);
        if (rentPrice == null) rentPrice = new RentPrice();
    }

    private void initLisenter() {

        price400.setOnClickListener(this);
        price_400_600.setOnClickListener(this);
        price_600_800.setOnClickListener(this);
        price_800_1000.setOnClickListener(this);
        price_1000_2000.setOnClickListener(this);
        price_2000_3000.setOnClickListener(this);
        priceUp.setOnClickListener(this);
        conGreen.setOnClickListener(this);
        showGreen.setOnClickListener(this);

        processBarView.setOnProgressChangeListener(changeListener);

    }

    private void initView(View view) {
        price400 = view.findViewById(R.id.rent_view_10000);
        price_400_600 = view.findViewById(R.id.rent_view_10000_15000);
        price_600_800 = view.findViewById(R.id.rent_view_15000_20000);
        price_800_1000 = view.findViewById(R.id.rent_view_20000_40000);
        price_1000_2000 = view.findViewById(R.id.rent_view_40000_60000);
        price_2000_3000 = view.findViewById(R.id.rent_view_60000_100000);
        priceUp = view.findViewById(R.id.rent_view_100000up);

        conGreen = view.findViewById(R.id.sale_view_con_greenprice);
        showGreen = view.findViewById(R.id.sale_view_greenprice);

        processBarView = view.findViewById(R.id.rent_pb_price);
        processBarView.setMax(100);

        priceStartEdit = view.findViewById(R.id.rent_edit_stastart);
        priceEndEdit = view.findViewById(R.id.rent_edit_endstart);

        content = view.findViewById(R.id.rent_ll_content);

//        setPrice(rentPrice.startPrice, rentPrice.endPrice);
        reCoverView(rentPrice);
    }


    private void reCoverView(RentPrice rentPrice) {

        if (rentPrice.startPrice != null)
            switch (rentPrice.startPrice) {
                case "0":
                    if (rentPrice.endPrice.equals("10")) price400.setSelected(true);
                    break;
                case "10":
                    if (rentPrice.endPrice.equals("15")) price_400_600.setSelected(true);
                    break;
                case "15":
                    if (rentPrice.endPrice.equals("20")) price_600_800.setSelected(true);
                    break;
                case "20":
                    if (rentPrice.endPrice.equals("40")) price_800_1000.setSelected(true);
                    break;
                case "40":
                    if (rentPrice.endPrice.equals("60")) price_1000_2000.setSelected(true);
                    break;
                case "60":
                    if (rentPrice.endPrice.equals("100")) price_2000_3000.setSelected(true);
                    break;
                case "100":
                    if (rentPrice.endPrice.equals("")) priceUp.setSelected(true);
                    break;
            }


        if (rentPrice.startPrice != null && rentPrice.startPrice.equals("100")) {
            priceStartEdit.setText("0");
            priceEndEdit.setText("100 +");
        } else setPrice(rentPrice.startPrice, rentPrice.endPrice);

    }

    private void setPrice(String start, String end) {
        L.d("setPrice", start + " : " + end);
        processBarView.setOnProgressChangeListener(null);

        priceStartEdit.setText(start);
        priceEndEdit.setText(end);
        if (start != null && !start.equals(""))
            processBarView.setLeftValue(Integer.parseInt(start));
        if (end != null && !end.equals("") && !end.equals("100 +"))
            processBarView.setRightValue(Integer.parseInt(end));
        else {
            if (end != null && !end.equals(""))
                processBarView.setRightValue(100);
        }
        processBarView.setOnProgressChangeListener(changeListener);
    }

    ProcessBarView.OnProgressChangeListener changeListener = new ProcessBarView.OnProgressChangeListener() {
        @Override
        public void onLeftProgressChange(float progress, int value) {
            priceStartEdit.setText(value + "");
            rentPrice.startPrice = value + "";
            if (onRentChangeLisenter != null) onRentChangeLisenter.onRentChange(rentPrice);
        }

        @Override
        public void onRightProgressChange(float progress, int value) {
            priceEndEdit.setText(value + "");
            rentPrice.endPrice = value + "";
            if (onRentChangeLisenter != null) onRentChangeLisenter.onRentChange(rentPrice);
        }
    };

    private void resetView() {
        int count = content.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = content.getChildAt(i);
            if (view instanceof LinearLayout)
                view.setSelected(false);
        }
    }

    @Override
    public void onClick(View v) {
        resetView();

        switch (v.getId()) {
            case R.id.rent_view_10000:
                setPrice("0", "10");
                v.setSelected(true);
                rentPrice.startPrice = "0";
                rentPrice.endPrice = "10";
                break;
            case R.id.rent_view_10000_15000:
                setPrice("10", "15");
                v.setSelected(true);
                rentPrice.startPrice = "10";
                rentPrice.endPrice = "15";
                break;
            case R.id.rent_view_15000_20000:
                setPrice("15", "20");
                v.setSelected(true);
                rentPrice.startPrice = "15";
                rentPrice.endPrice = "20";
                break;
            case R.id.rent_view_20000_40000:
                setPrice("20", "40");
                v.setSelected(true);
                rentPrice.startPrice = "20";
                rentPrice.endPrice = "40";
                break;
            case R.id.rent_view_40000_60000:
                setPrice("40", "60");
                v.setSelected(true);
                rentPrice.startPrice = "40";
                rentPrice.endPrice = "60";
                break;
            case R.id.rent_view_60000_100000:
                setPrice("60", "100");
                v.setSelected(true);
                rentPrice.startPrice = "60";
                rentPrice.endPrice = "100";
                break;
            case R.id.rent_view_100000up:
                setPrice("0", "100 +");
                v.setSelected(true);
                rentPrice.startPrice = "100";
                rentPrice.endPrice = "";
                break;
        }
        if (onRentChangeLisenter != null) onRentChangeLisenter.onRentChange(rentPrice);
    }


    public static class RentPrice implements Serializable {
        String startPrice;
        String endPrice;
    }

    public interface OnRentChangeLisenter {
        void onRentChange(RentPrice salePrice);
    }
}
