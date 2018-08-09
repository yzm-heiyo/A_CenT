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

import static com.centanet.hk.aplus.common.CommandField.PriceUnitType.AVG_GREEN_REALLY_SALE;
import static com.centanet.hk.aplus.common.CommandField.PriceUnitType.AVG_GREEN_USE_SALE;
import static com.centanet.hk.aplus.common.CommandField.PriceUnitType.AVG_REALLY_RENT;
import static com.centanet.hk.aplus.common.CommandField.PriceUnitType.AVG_REALLY_SALE;
import static com.centanet.hk.aplus.common.CommandField.PriceUnitType.AVG_USE_RENT;
import static com.centanet.hk.aplus.common.CommandField.PriceUnitType.AVG_USE_SALE;

/**
 * Created by yangzm4 on 2018/7/24.
 */

public class SizeFragment extends BaseFragment implements View.OnClickListener {

    public static String ARGUMENT = "SysItemFragment";
    private View useAvgSale, useAvgSaleGreen, useAvgRent, realAvgSale, realAvgSaleGreen, realAvgRent;
    private LinearLayout content;
    private ProcessBarView processBarView;
    private EditText priceStartEdit, priceEndEdit;
    private View editContenView;
    private SizeParam sizeParam;
    private OnSizeChangeLisenter onSizeChangeLisenter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSizeChangeLisenter) {
            onSizeChangeLisenter = (OnSizeChangeLisenter) context;
        } else
            throw new IllegalArgumentException("Activity  Must Implements SizeFragment.OnSizeChangeLisenter");
    }


    public static SizeFragment newInstance(SizeParam argument) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARGUMENT, argument);
        SizeFragment contentFragment = new SizeFragment();
        contentFragment.setArguments(bundle);
        return contentFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_size, null, false);
        init();
        initView(view);
        initLisenter();
        return view;
    }

    private void init() {
        sizeParam = (SizeParam) getArguments().get(ARGUMENT);
        if (sizeParam == null) sizeParam = new SizeParam();
    }

    private void initLisenter() {

        useAvgSale.setOnClickListener(this);
        useAvgSaleGreen.setOnClickListener(this);
        useAvgRent.setOnClickListener(this);
        realAvgSale.setOnClickListener(this);
        realAvgSaleGreen.setOnClickListener(this);
        realAvgRent.setOnClickListener(this);

        processBarView.setOnProgressChangeListener(changeListener);
    }

    private void initView(View view) {
        useAvgSale = view.findViewById(R.id.size_view_use_ava_sale);
        useAvgSaleGreen = view.findViewById(R.id.size_view_use_ava_sale_green);
        useAvgRent = view.findViewById(R.id.size_view_usr_ava_rent);
        realAvgSale = view.findViewById(R.id.size_view_real_ava_sale);
        realAvgSaleGreen = view.findViewById(R.id.size_view_real_ava_sale_green);
        realAvgRent = view.findViewById(R.id.size_view_real_ava_rent);

        priceEndEdit = view.findViewById(R.id.size_edit_endstart);
        priceStartEdit = view.findViewById(R.id.size_edit_stastart);

        editContenView = view.findViewById(R.id.size_ll_edit);

        processBarView = view.findViewById(R.id.size_pb_price);
        processBarView.setMax(3000);

        content = view.findViewById(R.id.size_ll_content);

        reCoverView(sizeParam);
    }

    private void reCoverView(SizeParam sizeParam) {

        if (sizeParam.startPrice != null)
            processBarView.setLeftValue(Integer.parseInt(sizeParam.startPrice));
        if (sizeParam.endPrice != null)
            processBarView.setRightValue(Integer.parseInt(sizeParam.endPrice));
//1-建築面積，2-實用面積，3-花園面積
        if (sizeParam.avgType != 0) {
            editContenView.setVisibility(View.VISIBLE);
            switch (sizeParam.avgType) {
                case AVG_USE_SALE:
                    useAvgSale.setSelected(true);
                    break;
                case AVG_GREEN_USE_SALE:
                    useAvgSaleGreen.setSelected(true);
                    break;
                case AVG_USE_RENT:
                    useAvgRent.setSelected(true);
                    break;
                case AVG_REALLY_SALE:
                    realAvgSale.setSelected(true);
                    break;
                case AVG_GREEN_REALLY_SALE:
                    realAvgSaleGreen.setSelected(true);
                    break;
                case AVG_REALLY_RENT:
                    realAvgRent.setSelected(true);
                    break;
            }
        }
    }

    ProcessBarView.OnProgressChangeListener changeListener = new ProcessBarView.OnProgressChangeListener() {
        @Override
        public void onLeftProgressChange(float progress, int value) {
            priceStartEdit.setText(((int) (value * progress) + ""));
            sizeParam.startPrice = value + "";
            onSizeChangeLisenter.onSizeChange(sizeParam);
        }

        @Override
        public void onRightProgressChange(float progress, int value) {
            priceEndEdit.setText(((int) (value * (1 - progress)) + ""));
            sizeParam.endPrice = value + "";
            onSizeChangeLisenter.onSizeChange(sizeParam);
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
    public void onClick(View v) {//均價類型(1:實均售,2:實均租,3:建均售,4:建均租,5:綠表價實均售,6:綠表價建均售)
        resetView();
        editContenView.setVisibility(View.VISIBLE);
        v.setSelected(true);
        switch (v.getId()) {
            case R.id.size_view_use_ava_sale:
                sizeParam.avgType = AVG_USE_SALE;
                break;
            case R.id.size_view_use_ava_sale_green:
                sizeParam.avgType = AVG_GREEN_USE_SALE;
                break;
            case R.id.size_view_usr_ava_rent:
                sizeParam.avgType = AVG_USE_RENT;
                break;
            case R.id.size_view_real_ava_sale:
                sizeParam.avgType = AVG_REALLY_SALE;
                break;
            case R.id.size_view_real_ava_sale_green:
                sizeParam.avgType = AVG_GREEN_REALLY_SALE;
                break;
            case R.id.size_view_real_ava_rent:
                sizeParam.avgType = AVG_REALLY_RENT;
                break;
        }
        L.d("SizeParam_frag", sizeParam.avgType + "");
        if (onSizeChangeLisenter != null) onSizeChangeLisenter.onSizeChange(sizeParam);
    }

    public static class SizeParam implements Serializable {
        String startPrice;
        String endPrice;
        int avgType;
    }

    public interface OnSizeChangeLisenter {
        void onSizeChange(SizeParam sizeParam);
    }
}
