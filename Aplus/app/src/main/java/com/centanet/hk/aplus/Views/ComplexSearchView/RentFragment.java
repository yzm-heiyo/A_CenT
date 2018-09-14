package com.centanet.hk.aplus.Views.ComplexSearchView;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.TextUtil;
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
    private View price400, price_400_600, price_600_800, price_800_1000, price_1000_2000, price_2000_3000, priceUp, unlimit;
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

        priceStartEdit.addTextChangedListener(leftTextWatcher);
        priceEndEdit.addTextChangedListener(rightTextWatcher);

        price400.setOnClickListener(this);
        price_400_600.setOnClickListener(this);
        price_600_800.setOnClickListener(this);
        price_800_1000.setOnClickListener(this);
        price_1000_2000.setOnClickListener(this);
        price_2000_3000.setOnClickListener(this);
        priceUp.setOnClickListener(this);
        conGreen.setOnClickListener(this);
        showGreen.setOnClickListener(this);
        unlimit.setOnClickListener(this);

        priceEndEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {

                } else {
                    isProcessChange = true;
//                    asdsadasd
                    String price = priceEndEdit.getText().toString();
                    if (price != null) {
                        try {
                            int priccs = Integer.parseInt(price);
                            if (priccs > 100) priceEndEdit.setText(null);
                        } catch (Exception e) {

                        }
                    }
                }
            }
        });

        priceEndEdit.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
                isProcessChange = true;
                String price = priceEndEdit.getText().toString();
                if (price != null) {
                    try {
                        int priccs = Integer.parseInt(price);
                        if (priccs > 100) priceEndEdit.setText(null);
                    } catch (Exception e) {

                    }
                }
            }
            return false;
        });

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

        unlimit = view.findViewById(R.id.rent_view_unlimit);

        conGreen = view.findViewById(R.id.sale_view_con_greenprice);
        showGreen = view.findViewById(R.id.sale_view_greenprice);

        processBarView = view.findViewById(R.id.rent_pb_price);
        processBarView.setMax(100);

        priceStartEdit = view.findViewById(R.id.rent_edit_stastart);
        priceEndEdit = view.findViewById(R.id.rent_edit_endstart);

        content = view.findViewById(R.id.rent_ll_content);

//        setPrice(rentPrice.startPrice, rentPrice.endPrice);
        if (rentPrice.endPrice == null && rentPrice.startPrice == null)
            unlimit.setSelected(true);
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
            setPrice("100", null);
        } else setPrice(rentPrice.startPrice, rentPrice.endPrice);

    }

    private void resetPriceProcess() {
        processBarView.setOnProgressChangeListener(null);
        processBarView.setLeftProcess(0);
        processBarView.setRightProcess(1);
        processBarView.setOnProgressChangeListener(changeListener);
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

    private boolean isProcessChange;
    ProcessBarView.OnProgressChangeListener changeListener = new ProcessBarView.OnProgressChangeListener() {
        @Override
        public void onLeftProgressChange(float progress, int value) {


            if (isChangeByEdit) {
                isChangeByEdit = false;
                return;
            }

            isProcessChange = true;

            if (value == 0) {
                priceStartEdit.setText(null);
                rentPrice.startPrice = null;
            } else {
                priceStartEdit.setText((value + ""));
                rentPrice.startPrice = value + "";
            }

//            priceStartEdit.setText(value + "");
//            rentPrice.startPrice = value + "";
            if (onRentChangeLisenter != null) onRentChangeLisenter.onRentChange(rentPrice);
        }

        @Override
        public void onRightProgressChange(float progress, int value) {


            if (isChangeByEdit) {
                isChangeByEdit = false;
                return;
            }

            isProcessChange = true;

            if (value == 100) {
                priceEndEdit.setText(null);
                rentPrice.endPrice = null;
            } else {
                priceEndEdit.setText((value + ""));
                rentPrice.endPrice = value + "";
            }
//            priceEndEdit.setText(value + "");
//            rentPrice.endPrice = value + "";
            if (onRentChangeLisenter != null) onRentChangeLisenter.onRentChange(rentPrice);
        }
    };

    private TextWatcher leftTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

            resetView();
            if(isProcessChange){
                isProcessChange = false;
                if(TextUtil.isEmply(rentPrice.startPrice)&&TextUtil.isEmply(rentPrice.endPrice))unlimit.setSelected(true);
                return;
            }

            isChangeByEdit = true;
            rentPrice.startPrice = priceStartEdit.getText().toString();
            if (!TextUtil.isEmply(rentPrice.startPrice)) {
                processBarView.setLeftValue(Integer.parseInt(rentPrice.startPrice));
                resetView();
            } else {
                processBarView.setLeftValue(0);
                if(TextUtil.isEmply(rentPrice.startPrice)&&TextUtil.isEmply(rentPrice.endPrice))unlimit.setSelected(true);
            }
            if (onRentChangeLisenter != null) onRentChangeLisenter.onRentChange(rentPrice);
        }
    };

    private boolean isChangeByEdit;
    private TextWatcher rightTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

            resetView();
            if(isProcessChange){
                isProcessChange = false;
                if(TextUtil.isEmply(rentPrice.startPrice)&&TextUtil.isEmply(rentPrice.endPrice))unlimit.setSelected(true);
                return;
            }

            isChangeByEdit = true;
            rentPrice.endPrice = priceEndEdit.getText().toString();

            if (!TextUtil.isEmply(rentPrice.endPrice)) {

                if (Integer.parseInt(rentPrice.endPrice) > 100) {
                    rentPrice.endPrice = null;
//                    priceEndEdit.setText(null);
                    processBarView.setRightValue(100);
                } else processBarView.setRightValue(Integer.parseInt(rentPrice.endPrice));
//                processBarView.setRightValue(Integer.parseInt(rentPrice.endPrice));
                resetView();
            } else {
                processBarView.setRightValue(100);
                if(TextUtil.isEmply(rentPrice.startPrice)&&TextUtil.isEmply(rentPrice.endPrice))unlimit.setSelected(true);
            }

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
                resetPriceProcess();
                setPrice("100", null);
                v.setSelected(true);
                rentPrice.startPrice = "100";
                rentPrice.endPrice = "";
                break;
            case R.id.rent_view_unlimit:
                setPrice(null, null);
                resetPriceProcess();
                v.setSelected(true);
                rentPrice.startPrice = null;
                rentPrice.endPrice = null;
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
