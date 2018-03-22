package com.centanet.hk.aplus.Views.Dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Widgets.ProcessBarView;

import java.util.HashMap;
import java.util.Map;

import static com.centanet.hk.aplus.common.CommandField.DialogItemStatus.UNSELECT;
import static com.centanet.hk.aplus.common.CommandField.PriceType.RENT;
import static com.centanet.hk.aplus.common.CommandField.PriceType.SALE;

/**
 * Created by yangzm4 on 2018/2/13.
 */

public class PriceDialog extends BaseDialog implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private String thiz = getClass().getSimpleName();
    public static final String PARAMS_TYPE = "type";
    public static final String PARAMS_SELECTID = "selectId";
    public static final String PARAMS_PRICE = "price";
    private static final int SALE_MAX = 3000;
    private static final int RENT_MAX = 100000;
    private Dialog dialog;
    private ProcessBarView processBarView;
    private EditText priceLeftEdit, priceRightEdit;
    private View dialogView;
    private String[] price;
    private View priceLayout;
    private RadioGroup priceGroup, typeGroup;
    private WindowManager.LayoutParams lp;
    private int selectRadId;
    private Button priceBtn;
    private RadioButton defaultBtn, _0_400_Btn, _400_600_btn, _600_800btn, _800_1000_btn, _1000_2000_btn, _2000_3000_btn, abovebtn;
    private String[] salePrice = {"0", "200", "400", "600", "800", "1000", "2000", "3000"};
    private String[] rentPrice = {"0", "10000", "15000", "20000", "40000", "60000", "100000"};
    private String[][] rentPriceList = {{"0", "10000"}, {"10000", "15000"}, {"15000", "20000"}, {"20000", "40000"}, {"40000", "60000"}, {"60000", "100000"}, {"100000", "100000"}, {"", ""}};
    private String[][] salePriceList = {{"0", "400"}, {"400", "600"}, {"600", "800"}, {"800", "1000"}, {"1000", "2000"}, {"2000", "3000"}, {"3000", "3000"}, {"", ""}};
    private String[] salePriceTxt = {"不限", "0 - 400萬", "400萬 - 600萬", "600萬 - 800萬", "800萬 - 1,000萬", "1,000萬 - 2,000萬", "2,000萬 - 3,000萬", "3,000萬以上", "萬"};
    private String[] rentPriceTxt = {"不限", "0 - 10,000", "10,000 - 15,000", "15,000 - 20,000", "20,000 - 40,000", "40,000 - 60,000", "60,000 - 100,000", "100,000以上", "千"};
    private boolean isFirstEnter = true;
    private int type = SALE;
    private int oldType;
    private int priceMax = SALE_MAX;
    private TextView editTxt;
    private int oldStartPrice = 0;
    private int oldEndPrice = SALE_MAX;

    public PriceDialog() {
    }

    @SuppressLint("ValidFragment")
    public PriceDialog(int type, int selectRadId, String[] price) {
        this.type = type;
        oldType = type;
        this.selectRadId = selectRadId;
        this.price = price;

        if (price[0] != null && !price[0].equals(""))
            oldStartPrice = Integer.parseInt(price[0]);
        if (price[1] != null && !price[1].equals("")) {
            oldEndPrice = Integer.parseInt(price[1]);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_price);
        initLayouts();
    }

    private void initLayouts() {

        priceLayout = dialog.findViewById(R.id.dialog_price_layout);

        priceLeftEdit = priceLayout.findViewById(R.id.dialog_price_left_edit);
        priceLeftEdit.setFocusableInTouchMode(true);
        priceLeftEdit.setFocusable(true);
        priceLeftEdit.requestFocus();
        priceLeftEdit.clearFocus();

        priceRightEdit = priceLayout.findViewById(R.id.dialog_price_right_edit);
        priceRightEdit.setFocusableInTouchMode(true);
        priceRightEdit.setFocusable(true);
        priceRightEdit.requestFocus();
        priceRightEdit.clearFocus();
        editTxt = priceLayout.findViewById(R.id.dialog_price_edit_txt);

        priceGroup = priceLayout.findViewById(R.id.dialog_price_group);
        priceGroup.check(selectRadId == 0 ? R.id.dialog_radiobtn_default : selectRadId);
        typeGroup = priceLayout.findViewById(R.id.dialog_price_chioce_radiogroup);
        typeGroup.setOnCheckedChangeListener(this);

        priceBtn = priceLayout.findViewById(R.id.dialog_price_confirm);
        priceBtn.setOnClickListener(this);
        defaultBtn = priceLayout.findViewById(R.id.dialog_radiobtn_default);
        defaultBtn.setOnClickListener(this);
        _0_400_Btn = priceLayout.findViewById(R.id.dialog_radiobtn_0_400);
        _0_400_Btn.setOnClickListener(this);
        _400_600_btn = priceLayout.findViewById(R.id.dialog_radiobtn_400_600);
        _400_600_btn.setOnClickListener(this);
        _600_800btn = priceLayout.findViewById(R.id.dialog_radiobtn_600_800);
        _600_800btn.setOnClickListener(this);
        _800_1000_btn = priceLayout.findViewById(R.id.dialog_radiobtn_800_1000);
        _800_1000_btn.setOnClickListener(this);
        _1000_2000_btn = priceLayout.findViewById(R.id.dialog_radiobtn_1000_2000);
        _1000_2000_btn.setOnClickListener(this);
        _2000_3000_btn = priceLayout.findViewById(R.id.dialog_radiobtn_2000_3000);
        _2000_3000_btn.setOnClickListener(this);
        abovebtn = priceLayout.findViewById(R.id.dialog_radiobtn_above3000);
        abovebtn.setOnClickListener(this);
        setItemValue(salePriceList);

        processBarView = priceLayout.findViewById(R.id.dialog_price_ratting);
        processBarView.setMax(3000);

        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.AnimBottom);
        lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        lp.height = getActivity().getWindowManager().getDefaultDisplay().getHeight() * 4 / 5;
        window.setAttributes(lp);
        window.setBackgroundDrawableResource(android.R.color.transparent);

        if (type == SALE) {
            priceMax = SALE_MAX;
            typeGroup.check(R.id.dialog_price_rad_sale);
            setItemText(salePriceTxt);
        } else {
            priceMax = RENT_MAX;
            typeGroup.check(R.id.dialog_price_rad_rent);
            setItemText(rentPriceTxt);
        }
        initLisenter();
    }

    private void initLisenter() {
        priceLeftEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                price[0] = priceLeftEdit.getText().toString();
            }
        });
        priceRightEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                price[1] = priceRightEdit.getText().toString();
            }
        });
        processBarView.setOnProgressChangeListener(onProgressChangeListener);
    }

    private ProcessBarView.OnProgressChangeListener onProgressChangeListener = new ProcessBarView.OnProgressChangeListener() {
        @Override
        public void onLeftProgressChange(float progress, int value) {

            priceLeftEdit.setText("" + value);
            if (value != 0) {
                if (type == RENT) {
                    value = value >= 10000 ? (value / 1000) * 1000 : (value / 100) * 100;
                    priceLeftEdit.setText("" + (value >= 10000 ? value / 1000 : value / 100));
                }
            }
            price[0] = value + "";
        }

        @Override
        public void onRightProgressChange(float progress, int value) {
            priceRightEdit.setText("" + value);
            if (value != 0) {
                if (type == RENT) {
                    value = value >= 10000 ? (value / 1000) * 1000 : (value / 100) * 100;
                    priceRightEdit.setText("" + (value >= 10000 ? value / 1000 : value / 100));
                }
            }
            price[1] = value + "";
        }
    };


    private void setItemText(String[] priceTxt) {
        defaultBtn.setText(priceTxt[0]);
        _0_400_Btn.setText(priceTxt[1]);
        _400_600_btn.setText(priceTxt[2]);
        _600_800btn.setText(priceTxt[3]);
        _800_1000_btn.setText(priceTxt[4]);
        _1000_2000_btn.setText(priceTxt[5]);
        _2000_3000_btn.setText(priceTxt[6]);
        abovebtn.setText(priceTxt[7]);
        editTxt.setText(priceTxt[8]);
    }

    private void setItemValue(String[][] priceList) {
        defaultBtn.setTag(priceList[7]);
        _0_400_Btn.setTag(priceList[0]);
        _400_600_btn.setTag(priceList[1]);
        _600_800btn.setTag(priceList[2]);
        _800_1000_btn.setTag(priceList[3]);
        _1000_2000_btn.setTag(priceList[4]);
        _2000_3000_btn.setTag(priceList[5]);
        abovebtn.setTag(priceList[6]);
    }


    private void isClearGroup(String[] price) {

        if (!isSection(price)) {
            priceGroup.clearCheck();
            selectRadId = UNSELECT;
        }
    }


    private boolean isSection(String[] price) {

        String[] priceString = (type == SALE ? salePrice : rentPrice);
        for (String priceStart : priceString) {
            if (price[0].equals(priceStart)) {
                for (String priceEnd : priceString) {
                    if (price[1].equals(priceEnd)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        processBarView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                showSlideBar();
            }
        });
        return dialog;
    }

    @Override
    public void onClick(View v) {

        selectRadId = v.getId();
        if (onDialogClikeLisenter != null) {
            if (selectRadId != R.id.dialog_price_confirm) {
                selectRadId = v.getId();
                getInterval(v.getId());
            }
            if (selectRadId != R.id.dialog_radiobtn_default)
                isClearGroup(price);
            if (selectRadId == R.id.dialog_price_confirm)
                selectRadId = 0;
            Map<String,Object> params = new HashMap<>();

            params.put(PARAMS_TYPE,type);
            params.put(PARAMS_SELECTID,selectRadId);
            params.put(PARAMS_PRICE,price);

            onDialogClikeLisenter.onClike(getDialog(),0,params);
        }
    }

    private void showSlideBar() {

        if (isFirstEnter) {
            isFirstEnter = false;
            if (selectRadId != 0 && selectRadId != UNSELECT) {
                getInterval(selectRadId);
            } else {
                if (price[0] == null && price[1] == null) return;
            }
            setEditText();
        }
    }

    private void setEditText() {

        if (price[0] != null && !price[0].equals("")) {
            int value = Integer.parseInt(price[0]);
            if (price[0].equals("3000") || price[0].equals("100000")) value = 0;
            processBarView.setLeftValue(value);
        }

        if (price[1] != null && !price[1].equals("")) {
            int value = Integer.parseInt(price[1]);
            processBarView.setRightValue(value);
        }
    }


    private String[] getInterval(int viewId) {
        price = (String[]) priceLayout.findViewById(viewId).getTag();
        return price;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId) {
            case R.id.dialog_price_rad_sale:
                type = SALE;
                priceMax = SALE_MAX;
                processBarView.setMax(SALE_MAX);
                recoverRadioStatus(type);
                setItemValue(salePriceList);
                setItemText(salePriceTxt);
                break;
            case R.id.dialog_price_rad_rent:
                type = RENT;
                priceMax = RENT_MAX;
                processBarView.setMax(RENT_MAX);
                recoverRadioStatus(type);
                setItemValue(rentPriceList);
                setItemText(rentPriceTxt);
                break;
        }
    }

    private void recoverRadioStatus(int type) {
        if (type == oldType) {
            priceGroup.check(selectRadId == 0 ? R.id.dialog_radiobtn_default : selectRadId);
            //todo 設置成正確狀態
            processBarView.setLeftValue(oldStartPrice);
            processBarView.setRightValue(oldEndPrice);
        } else {
            priceGroup.check(R.id.dialog_radiobtn_default);
            processBarView.setLeftProcess(0);
            processBarView.setRightProcess(1);
        }
    }

    public interface onDialogOnclikeLisenter extends BaseDialog.onDialogOnclikeLisenter<Map<String,Object>>{};
}
