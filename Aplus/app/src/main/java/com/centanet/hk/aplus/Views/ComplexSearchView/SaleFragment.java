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

public class SaleFragment extends BaseFragment implements View.OnClickListener {

    public static String ARGUMENT = "SysItemFragment";
    private View price400, price_400_600, price_600_800, price_800_1000, price_1000_2000, price_2000_3000, priceUp;
    private LinearLayout content;
    private ProcessBarView processBarView;
    private EditText priceStartEdit, priceEndEdit;
    private View conGreen, showGreen;
    private SalePrice salePrice;
    private OnSaleChangeLisenter changeLisenter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSaleChangeLisenter) {
            changeLisenter = (OnSaleChangeLisenter) context;
        } else
            throw new IllegalArgumentException("Activity  Must Implements SaleFragment.OnSaleChangeLisenter");
    }

    public static SaleFragment newInstance(SalePrice salePrice) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARGUMENT, salePrice);
        SaleFragment contentFragment = new SaleFragment();
        contentFragment.setArguments(bundle);
        return contentFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sale, null, false);
        init();
        initView(view);
        initLisenter();
        return view;
    }

    private void init() {
        salePrice = (SalePrice) getArguments().get(ARGUMENT);
        if (salePrice == null) salePrice = new SalePrice();
    }

    private void reCoverView(SalePrice salePrice) {

        if (salePrice.startPrice != null)
            switch (salePrice.startPrice) {
                case "0":
                    if (salePrice.endPrice.equals("400")) price400.setSelected(true);
                    break;
                case "400":
                    if (salePrice.endPrice.equals("600")) price_400_600.setSelected(true);
                    break;
                case "600":
                    if (salePrice.endPrice.equals("800")) price_600_800.setSelected(true);
                    break;
                case "800":
                    if (salePrice.endPrice.equals("1000")) price_800_1000.setSelected(true);
                    break;
                case "1000":
                    if (salePrice.endPrice.equals("2000")) price_1000_2000.setSelected(true);
                    break;
                case "2000":
                    if (salePrice.endPrice.equals("3000")) price_2000_3000.setSelected(true);
                    break;
                case "3000":
                    if (salePrice.endPrice.equals("")) priceUp.setSelected(true);
                    break;
            }

        conGreen.setSelected(salePrice.isConGreen);
        showGreen.setSelected(salePrice.isShowGreen);

        if (salePrice.startPrice != null && salePrice.startPrice.equals("3000")){
            priceStartEdit.setText("0");
            priceEndEdit.setText("3000 +");
        }
        else setPrice(salePrice.startPrice, salePrice.endPrice);
    }

    ProcessBarView.OnProgressChangeListener changeListener = new ProcessBarView.OnProgressChangeListener() {
        @Override
        public void onLeftProgressChange(float progress, int value) {
            priceStartEdit.setText((value + ""));
            salePrice.startPrice = value + "";
            if (changeLisenter != null) changeLisenter.onSaleChange(salePrice);
        }

        @Override
        public void onRightProgressChange(float progress, int value) {
            priceEndEdit.setText((value + ""));
            salePrice.endPrice = value + "";
            if (changeLisenter != null) changeLisenter.onSaleChange(salePrice);
        }
    };

    private void initLisenter() {

        processBarView.setOnProgressChangeListener(changeListener);

//        ssd.setOnClickListener(this);
//        ssd10.setOnClickListener(this);
        price400.setOnClickListener(this);
        price_400_600.setOnClickListener(this);
        price_600_800.setOnClickListener(this);
        price_800_1000.setOnClickListener(this);
        price_1000_2000.setOnClickListener(this);
        price_2000_3000.setOnClickListener(this);
        priceUp.setOnClickListener(this);
        conGreen.setOnClickListener(this);
        showGreen.setOnClickListener(this);

    }

    private void initView(View view) {

        price400 = view.findViewById(R.id.sale_view_400);
        price_400_600 = view.findViewById(R.id.sale_view_400_600);
        price_600_800 = view.findViewById(R.id.sale_view_600_800);
        price_800_1000 = view.findViewById(R.id.sale_view_800_1000);
        price_1000_2000 = view.findViewById(R.id.sale_view_1000_2000);
        price_2000_3000 = view.findViewById(R.id.sale_view_2000_3000);
        priceUp = view.findViewById(R.id.sale_view_3000up);

        conGreen = view.findViewById(R.id.sale_view_con_greenprice);
        showGreen = view.findViewById(R.id.sale_view_greenprice);

        processBarView = view.findViewById(R.id.sale_pb_price);
        processBarView.setMax(3000);


        priceStartEdit = view.findViewById(R.id.sale_edit_pricestart);
        priceEndEdit = view.findViewById(R.id.sale_edit_priceend);

        content = view.findViewById(R.id.sale_ll_content);

        reCoverView(salePrice);
    }

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
            case R.id.sale_view_400:
                setPrice("0", "400");
                v.setSelected(true);
                salePrice.startPrice = "0";
                salePrice.endPrice = "400";
                break;
            case R.id.sale_view_400_600:
                setPrice("400", "600");
                v.setSelected(true);
                salePrice.startPrice = "400";
                salePrice.endPrice = "600";
                break;
            case R.id.sale_view_600_800:
                setPrice("600", "800");
                v.setSelected(true);
                salePrice.startPrice = "600";
                salePrice.endPrice = "800";
                break;
            case R.id.sale_view_800_1000:
                setPrice("800", "1000");
                v.setSelected(true);
                salePrice.startPrice = "800";
                salePrice.endPrice = "1000";
                break;
            case R.id.sale_view_1000_2000:
                setPrice("1000", "2000");
                v.setSelected(true);
                salePrice.startPrice = "1000";
                salePrice.endPrice = "2000";
                break;
            case R.id.sale_view_2000_3000:
                setPrice("2000", "3000");
                v.setSelected(true);
                salePrice.startPrice = "2000";
                salePrice.endPrice = "3000";
                break;
            case R.id.sale_view_3000up:
                setPrice("0", "3000 +");
                v.setSelected(true);
                salePrice.startPrice = "3000";
                salePrice.endPrice = "";
                break;
            case R.id.sale_view_greenprice:
                v.setSelected(!v.isSelected());
                salePrice.isShowGreen = v.isSelected();
                break;
            case R.id.sale_view_con_greenprice:
                v.setSelected(!v.isSelected());
                salePrice.isConGreen = v.isSelected();
                break;
        }

        if (changeLisenter != null) changeLisenter.onSaleChange(salePrice);

    }

    private void setPrice(String start, String end) {
        L.d("setPrice", "start: " + start + " end: " + end);
        processBarView.setOnProgressChangeListener(null);

        priceStartEdit.setText(start);
        priceEndEdit.setText(end);
        if (start != null && !start.equals("")) {
            int left = Integer.parseInt(start);
            L.d("setPrice", "left: " + left);
            processBarView.setLeftValue(left);
        }

        if (end != null && !end.equals("") && !end.equals("3000 +"))
            processBarView.setRightValue(Integer.parseInt(end));
        else {
            if (end != null && !end.equals(""))
                processBarView.setRightValue(3000);
        }
        processBarView.setOnProgressChangeListener(changeListener);

    }

    public class SalePrice implements Serializable {
        String startPrice;
        String endPrice;
        boolean isConGreen;
        boolean isShowGreen;
    }

    public interface OnSaleChangeLisenter {
        void onSaleChange(SalePrice salePrice);
    }

}
