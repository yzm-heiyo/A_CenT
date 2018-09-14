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

public class SaleFragment extends BaseFragment implements View.OnClickListener {

    public static String ARGUMENT = "SysItemFragment";
    private View price400, price_400_600, price_600_800, price_800_1000, price_1000_2000, price_2000_3000, priceUp, unlimit;
    private LinearLayout content;
    private ProcessBarView processBarView;
    private EditText priceStartEdit, priceEndEdit;
    private View conGreen, showGreen;
    private SalePrice salePrice;
    private OnSaleChangeLisenter changeLisenter;
    private boolean show = true;
    private boolean isChangeByEdit;


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

        if (salePrice.startPrice != null && salePrice.startPrice.equals("3000")) {
//            priceStartEdit.setText(null);
//            priceEndEdit.setText(null);
            setPrice("3000", null);
        } else setPrice(salePrice.startPrice, salePrice.endPrice);
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
//                priceStartEdit.addTextChangedListener(n);
                salePrice.startPrice = null;
            } else {
                priceStartEdit.setText((value + ""));
                salePrice.startPrice = value + "";
            }
            if (changeLisenter != null) changeLisenter.onSaleChange(salePrice);
        }

        @Override
        public void onRightProgressChange(float progress, int value) {

            if (isChangeByEdit) {
                isChangeByEdit = false;
                return;
            }

            isProcessChange = true;

            if (value == 3000) {
                priceEndEdit.setText(null);
                salePrice.endPrice = null;
            } else {
                priceEndEdit.setText((value + ""));
                salePrice.endPrice = value + "";
            }
//            priceEndEdit.setText((value + ""));
//            salePrice.endPrice = value + "";
            if (changeLisenter != null) changeLisenter.onSaleChange(salePrice);
        }
    };

    private void initLisenter() {

        processBarView.setOnProgressChangeListener(changeListener);

        priceStartEdit.addTextChangedListener(leftTextWatcher);
        priceEndEdit.addTextChangedListener(rightTextWatcher);

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
                            if (priccs > 3000) priceEndEdit.setText(null);
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
                        if (priccs > 3000) priceEndEdit.setText(null);
                    } catch (Exception e) {

                    }
                }
            }
            return false;
        });
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
        unlimit.setOnClickListener(this);

    }

    private void initView(View view) {

        price400 = view.findViewById(R.id.sale_view_400);
        price_400_600 = view.findViewById(R.id.sale_view_400_600);
        price_600_800 = view.findViewById(R.id.sale_view_600_800);
        price_800_1000 = view.findViewById(R.id.sale_view_800_1000);
        price_1000_2000 = view.findViewById(R.id.sale_view_1000_2000);
        price_2000_3000 = view.findViewById(R.id.sale_view_2000_3000);
        priceUp = view.findViewById(R.id.sale_view_3000up);
        unlimit = view.findViewById(R.id.sale_view_unlimit);


        conGreen = view.findViewById(R.id.sale_view_con_greenprice);
        showGreen = view.findViewById(R.id.sale_view_greenprice);

        L.d("showGreenPrice", show + "");
        conGreen.setVisibility(show ? View.VISIBLE : View.GONE);

        processBarView = view.findViewById(R.id.sale_pb_price);
        processBarView.setMax(3000);


        priceStartEdit = view.findViewById(R.id.sale_edit_pricestart);
        priceEndEdit = view.findViewById(R.id.sale_edit_priceend);

        content = view.findViewById(R.id.sale_ll_content);

        if (salePrice.endPrice == null && salePrice.startPrice == null)
            unlimit.setSelected(true);
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

    public void setShowGreenTxt(boolean show) {
        L.d("showGreenPrice", show + "");
        this.show = show;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sale_view_400:
                resetView();
                setPrice("0", "400");
                v.setSelected(true);
                salePrice.startPrice = "0";
                salePrice.endPrice = "400";
                break;
            case R.id.sale_view_400_600:
                resetView();
                setPrice("400", "600");
                v.setSelected(true);
                salePrice.startPrice = "400";
                salePrice.endPrice = "600";
                break;
            case R.id.sale_view_600_800:
                resetView();
                setPrice("600", "800");
                v.setSelected(true);
                salePrice.startPrice = "600";
                salePrice.endPrice = "800";
                break;
            case R.id.sale_view_800_1000:
                resetView();
                setPrice("800", "1000");
                v.setSelected(true);
                salePrice.startPrice = "800";
                salePrice.endPrice = "1000";
                break;
            case R.id.sale_view_1000_2000:
                resetView();
                setPrice("1000", "2000");
                v.setSelected(true);
                salePrice.startPrice = "1000";
                salePrice.endPrice = "2000";
                break;
            case R.id.sale_view_2000_3000:
                resetView();
                setPrice("2000", "3000");
                v.setSelected(true);
                salePrice.startPrice = "2000";
                salePrice.endPrice = "3000";
                break;
            case R.id.sale_view_3000up:
                resetView();
                resetPriceProcess();
                setPrice("3000", null);
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
            case R.id.sale_view_unlimit:
                resetView();
                v.setSelected(!v.isSelected());
//                priceStartEdit.setText(null);
//                priceEndEdit.setText(null);.
                setPrice(null, null);
                resetPriceProcess();

                salePrice.startPrice = null;
                salePrice.endPrice = null;
                break;
        }

        if (changeLisenter != null) changeLisenter.onSaleChange(salePrice);

    }

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
            if (isProcessChange) {
                isProcessChange = false;
                if (TextUtil.isEmply(salePrice.startPrice) && TextUtil.isEmply(salePrice.endPrice))
                    unlimit.setSelected(true);
                return;
            }

            isChangeByEdit = true;
            salePrice.startPrice = priceStartEdit.getText().toString();
            if (!TextUtil.isEmply(salePrice.startPrice)) {
                processBarView.setLeftValue(Integer.parseInt(salePrice.startPrice));
            } else {
                processBarView.setLeftValue(0);
                if (TextUtil.isEmply(salePrice.startPrice) && TextUtil.isEmply(salePrice.endPrice))
                    unlimit.setSelected(true);
            }
            if (changeLisenter != null) changeLisenter.onSaleChange(salePrice);
        }
    };

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
            if (isProcessChange) {
                isProcessChange = false;
                if (TextUtil.isEmply(salePrice.startPrice) && TextUtil.isEmply(salePrice.endPrice))
                    unlimit.setSelected(true);
                return;
            }

            isChangeByEdit = true;
            salePrice.endPrice = priceEndEdit.getText().toString();

            if (!TextUtil.isEmply(salePrice.endPrice)) {
                if (Integer.parseInt(salePrice.endPrice) > 3000) {
                    salePrice.endPrice = null;
//                    priceEndEdit.setText(null);
                    processBarView.setRightValue(3000);
                } else processBarView.setRightValue(Integer.parseInt(salePrice.endPrice));
                resetView();
            } else {
                processBarView.setRightValue(3000);
                if (TextUtil.isEmply(salePrice.startPrice) && TextUtil.isEmply(salePrice.endPrice))
                    unlimit.setSelected(true);
            }

            if (changeLisenter != null) changeLisenter.onSaleChange(salePrice);
        }
    };

    private void resetPriceProcess() {
        processBarView.setOnProgressChangeListener(null);
        processBarView.setLeftProcess(0);
        processBarView.setRightProcess(1);
        processBarView.setOnProgressChangeListener(changeListener);
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

    public static class SalePrice implements Serializable {
        String startPrice;
        String endPrice;
        boolean isConGreen;
        boolean isShowGreen;
    }

    public interface OnSaleChangeLisenter {
        void onSaleChange(SalePrice salePrice);
    }

}
