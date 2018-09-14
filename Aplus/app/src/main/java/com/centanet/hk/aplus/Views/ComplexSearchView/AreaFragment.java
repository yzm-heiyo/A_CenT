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
import com.centanet.hk.aplus.Utils.TextUtil;
import com.centanet.hk.aplus.Views.basic.BaseFragment;
import com.centanet.hk.aplus.Widgets.ProcessBarView;
import com.centanet.hk.aplus.bean.params.SystemParamItems;

import java.io.Serializable;
import java.util.List;

import static com.centanet.hk.aplus.common.CommandField.PropertySquareType.AREA_REALLY;
import static com.centanet.hk.aplus.common.CommandField.PropertySquareType.AREA_USE;

/**
 * Created by yangzm4 on 2018/7/24.
 */

public class AreaFragment extends BaseFragment implements View.OnClickListener {

    public static String ARGUMENT = "SysItemFragment";
    private View use, really;
    private LinearLayout content;
    private ProcessBarView processBarView;
    private EditText priceStartEdit, priceEndEdit;
    private View editContenView;
    private AreaParam areaParam;
    private OnAreaChangeLisenter onAreaChangeLisenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAreaChangeLisenter) {
            onAreaChangeLisenter = (OnAreaChangeLisenter) context;
        } else
            throw new IllegalArgumentException("Activity  Must Implements StatuFragment.OnStatuChangeLisenter");
    }

    public static AreaFragment newInstance(AreaParam argument) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARGUMENT, (Serializable) argument);
        AreaFragment contentFragment = new AreaFragment();
        contentFragment.setArguments(bundle);
        return contentFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_area, null, false);
        init();
        initView(view);
        initLisenter();
        return view;
    }

    private void init() {
        areaParam = (AreaParam) getArguments().get(ARGUMENT);
        if (areaParam == null) areaParam = new AreaParam();
    }

    ProcessBarView.OnProgressChangeListener changeListener = new ProcessBarView.OnProgressChangeListener() {
        @Override
        public void onLeftProgressChange(float progress, int value) {
//            priceStartEdit.setText(value + "");
//            areaParam.areaFrom = value + "";
            if (isChangeByEdit) {
                isChangeByEdit = false;
                return;
            }

            isProcessChange = true;

            if (value == 0) {
                priceStartEdit.setText(null);
                areaParam.areaFrom = null;
            } else {
                priceStartEdit.setText((value + ""));
                areaParam.areaFrom = value + "";
            }
            onAreaChangeLisenter.onAreaChange(areaParam);
        }

        @Override
        public void onRightProgressChange(float progress, int value) {
//            priceEndEdit.setText(value + "");
//            areaParam.areaTo = value + "";
            if (isChangeByEdit) {
                isChangeByEdit = false;
                return;
            }

            isProcessChange = true;

            if (value == 3000) {
                priceEndEdit.setText(null);
                areaParam.areaTo = null;
            } else {
                priceEndEdit.setText((value + ""));
                areaParam.areaTo = value + "";
            }

            onAreaChangeLisenter.onAreaChange(areaParam);
        }
    };

    private void initLisenter() {

        priceStartEdit.addTextChangedListener(leftTextWatcher);
        priceEndEdit.addTextChangedListener(rightTextWatcher);

        really.setOnClickListener(this);
        use.setOnClickListener(this);

        processBarView.setOnProgressChangeListener(changeListener);

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

    }



    private void initView(View view) {

        really = view.findViewById(R.id.area_view_really);
        use = view.findViewById(R.id.area_view_use);

        priceEndEdit = view.findViewById(R.id.area_edit_end);
        priceStartEdit = view.findViewById(R.id.area_edit_start);

        editContenView = view.findViewById(R.id.area_ll_editcontent);

        processBarView = view.findViewById(R.id.area_pb_price);
        processBarView.setMax(3000);

        content = view.findViewById(R.id.ssd_ll_content);

        reCoverView(areaParam);
    }

    private void reCoverView(AreaParam areaParam) {

//        if(areaParam.areaType== AREA_REALLY)
        switch (areaParam.areaType) {
            case AREA_REALLY:
                really.setSelected(true);
                editContenView.setVisibility(View.VISIBLE);
                break;
            case AREA_USE:
                use.setSelected(true);
                editContenView.setVisibility(View.VISIBLE);
                break;
        }
        priceStartEdit.setText(areaParam.areaFrom);
        if (areaParam.areaTo != null) {
            if (areaParam.areaTo.equals("3000"))
                priceEndEdit.setText("3000 +");
            else
                priceEndEdit.setText(areaParam.areaTo);
        }

        if (areaParam.areaFrom != null && !areaParam.areaFrom.equals("")) {
            processBarView.setLeftValue(Integer.parseInt(areaParam.areaFrom));
        }

        if (areaParam.areaTo != null && !areaParam.areaTo.equals("")) {
            processBarView.setRightValue(Integer.parseInt(areaParam.areaTo));
        }
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

            if(isProcessChange){
                isProcessChange = false;
                return;
            }

            isChangeByEdit = true;
            areaParam.areaFrom = priceStartEdit.getText().toString();
            if (!TextUtil.isEmply(areaParam.areaFrom)) {
                processBarView.setLeftValue(Integer.parseInt(areaParam.areaFrom));
//                resetView();
            } else {
                processBarView.setLeftValue(0);
            }
            if (onAreaChangeLisenter != null) onAreaChangeLisenter.onAreaChange(areaParam);
        }
    };

    private boolean isChangeByEdit;
    private boolean isProcessChange;
    private TextWatcher rightTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

            if(isProcessChange){
                isProcessChange = false;
                return;
            }

            isChangeByEdit = true;
            areaParam.areaTo = priceEndEdit.getText().toString();

            if (!TextUtil.isEmply(areaParam.areaTo)) {

                if (Integer.parseInt(areaParam.areaTo) > 3000) {
                    areaParam.areaTo = null;
//                    priceEndEdit.setText(null);
                    processBarView.setRightValue(3000);
                } else processBarView.setRightValue(Integer.parseInt(areaParam.areaTo));

//                processBarView.setRightValue(Integer.parseInt(areaParam.areaTo));
//                resetView();
            } else {
                processBarView.setRightValue(3000);
            }

            if (onAreaChangeLisenter != null) onAreaChangeLisenter.onAreaChange(areaParam);
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

        if(v.isSelected()){
            editContenView.setVisibility(View.GONE);
            areaParam.areaFrom = null;
            areaParam.areaTo = null;
            if (onAreaChangeLisenter != null) onAreaChangeLisenter.onAreaChange(areaParam);
            resetView();
            processBarView.setRightProcess(1);
            processBarView.setLeftProcess(0);
            return;
        }
        else editContenView.setVisibility(View.VISIBLE);

        resetView();
//        editContenView.setVisibility(View.VISIBLE);
        v.setSelected(true);
        switch (v.getId()) {
            case R.id.area_view_use:
                areaParam.areaType = AREA_USE;
                break;
            case R.id.area_view_really:
                areaParam.areaType = AREA_REALLY;
                break;
        }
        if (onAreaChangeLisenter != null) onAreaChangeLisenter.onAreaChange(areaParam);
    }

    public static class AreaParam implements Serializable {
        String areaFrom;
        String areaTo;
        int areaType;
    }

    public interface OnAreaChangeLisenter {
        void onAreaChange(AreaParam areaParam);
    }
}
