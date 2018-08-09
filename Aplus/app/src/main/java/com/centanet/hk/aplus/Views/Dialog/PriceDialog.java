package com.centanet.hk.aplus.Views.Dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.DensityUtil;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Views.basic.BaseDialog;
import com.centanet.hk.aplus.Widgets.ProcessBarView;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

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
    private static final int RENT_MAX = 100;
    private Dialog dialog;
    private ProcessBarView processBarView;
    private EditText priceLeftEdit, priceRightEdit;
    private View dialogView;
    private String[] price;
    private LinearLayout priceLayout;
    private RadioGroup priceGroup, typeGroup;
    private WindowManager.LayoutParams lp;
    private int selectRadId;
    private Button priceBtn;
    private RadioButton defaultBtn, _0_400_Btn, _400_600_btn, _600_800btn, _800_1000_btn, _1000_2000_btn, _2000_3000_btn, abovebtn;
    private String[] salePrice = {"0", "200", "400", "600", "800", "1000", "2000", "3000", ""};
    private String[] rentPrice = {"0", "10", "15", "20", "40", "60", "100", ""};
    private String[][] rentPriceList = {{"0", "10"}, {"10", "15"}, {"15", "20"}, {"20", "40"}, {"40", "60"}, {"60", "100"}, {"100", ""}, {"", ""}};
    private String[][] salePriceList = {{"0", "400"}, {"400", "600"}, {"600", "800"}, {"800", "1000"}, {"1000", "2000"}, {"2000", "3000"}, {"3000", ""}, {"", ""}};
    private String[] salePriceTxt = {"不限", "0 - 400萬", "400萬 - 600萬", "600萬 - 800萬", "800萬 - 1,000萬", "1,000萬 - 2,000萬", "2,000萬 - 3,000萬", "3,000萬以上", "萬"};
    private String[] rentPriceTxt = {"不限", "0 - 10,000", "10,000 - 15,000", "15,000 - 20,000", "20,000 - 40,000", "40,000 - 60,000", "60,000 - 100,000", "100,000以上", "千"};
    private boolean isFirstEnter = true;
    private int type = SALE;
    private int oldType;
    private int priceMax = SALE_MAX;
    private TextView editTxt;
    private int oldStartPrice = 0;
    private int oldEndPrice;
    private boolean isFirst = true;
    private boolean isRentOpen = false;
    public boolean isOpen = false;
    @android.support.annotation.IdRes
    int TAG1401 = 1000;
    private int lastheight = 0;


    public PriceDialog() {
    }

    @SuppressLint("ValidFragment")
    public PriceDialog(int type, int selectRadId, String[] price) {
        this.type = type;
        oldType = type;
        this.selectRadId = selectRadId;
        this.price = price;

        if (type == SALE) {
            oldEndPrice = SALE_MAX;
        } else {
            oldEndPrice = RENT_MAX;
        }

        if (type == RENT) {
            L.d("type", "rent");
            isRentOpen = true;
        }

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

        View decorView = dialog.getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        //int measuredHeight = decorView.getMeasuredHeight();//拿尺寸不好用
                        Rect rect = new Rect();
                        //拿这个控件在屏幕上的可见区域
                        decorView.getWindowVisibleDisplayFrame(rect);
                        int height = rect.height();
                        //第一次刚进来的时候,给上一次的可见高度赋一个初始值,
                        // 然后不需要再做什么比较了,直接return即可
                        if (lastheight == 0) {
                            lastheight = height;
                            return;
                        }

                        WindowManager manager = getActivity().getWindowManager();
                        DisplayMetrics outMetrics = new DisplayMetrics();
                        manager.getDefaultDisplay().getMetrics(outMetrics);
                        int screenHeight = outMetrics.heightPixels;

                        //当前这一次的可见高度比上一次的可见高度要小(有比较大的高度差,大于300了),
                        // 认为是软键盘弹出
                        if (lastheight - height > 200) {
                            //隐藏这个RoomFragment中的控件
                            L.d("open", "");
                            Timer timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    ScrollView scrollView = dialog.findViewById(R.id.dialog_price);
                                    scrollView.getY();
                                    L.d("scrollCurrentY", scrollView.getScrollY() + "");
                                    ((ScrollView) dialog.findViewById(R.id.dialog_price)).smoothScrollTo(0, scrollView.getScrollY() + DensityUtil.dip2px(null,20));
                                    L.d("scrollCurrentY", scrollView.getScrollY() + "");
                                }
                            }, 500);
                        }
                        //当前这一次的可见高度比上一次的可见高度要大,认为是软键盘收缩
                        if (height - lastheight > 200) {
                            L.d("close", "");
                        }
                        //记录下来
                        lastheight = height;
                    }
                }
        );

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

//        priceLeftEdit.setOnClickListener(this);
//        priceRightEdit.setOnClickListener(this);

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

        final Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.AnimBottom);
        lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);


        window.setBackgroundDrawableResource(android.R.color.transparent);

        if (type == SALE)

        {
            priceMax = SALE_MAX;
            typeGroup.check(R.id.dialog_price_rad_sale);
            setItemText(salePriceTxt);
        } else

        {
            priceMax = RENT_MAX;
            typeGroup.check(R.id.dialog_price_rad_rent);
            setItemText(rentPriceTxt);
        }

        initLisenter();
    }

    private TextWatcher left = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!abovebtn.isChecked())
                price[0] = priceLeftEdit.getText().toString();
        }
    };

    private TextWatcher right = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!abovebtn.isChecked()) {
                price[1] = priceRightEdit.getText().toString();
                if (price[1].indexOf("+") != -1)
                    price[1] = "";
            }
        }
    };

    private void initLisenter() {
        priceLeftEdit.addTextChangedListener(left);

        priceRightEdit.addTextChangedListener(right);
        processBarView.setOnProgressChangeListener(onProgressChangeListener);
    }

    private ProcessBarView.OnProgressChangeListener onProgressChangeListener = new ProcessBarView.OnProgressChangeListener() {
        @Override
        public void onLeftProgressChange(float progress, int value) {
            priceLeftEdit.setText("" + value);
            price[0] = value + "";
        }

        @Override
        public void onRightProgressChange(float progress, int value) {
            L.d("", "progress: " + progress + " value: " + value);
            priceRightEdit.setText("" + value);
            price[1] = value + "";
            if (abovebtn.isChecked() || progress == 0) {
                priceRightEdit.setText(priceRightEdit.getText().toString() + " +");
            }

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

//        if (v.getId() == R.id.dialog_price_left_edit || v.getId() == R.id.dialog_price_right_edit) {
//            ((ScrollView) dialog.findViewById(R.id.dialog_price)).scrollTo(0, DensityUtil.dip2px(null, 35));
//            return;
//        }

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
            Map<String, Object> params = new HashMap<>();

            if (abovebtn.isChecked()) price[1] = "";

            params.put(PARAMS_TYPE, type);
            params.put(PARAMS_SELECTID, selectRadId);
            params.put(PARAMS_PRICE, price);

            onDialogClikeLisenter.onClike(getDialog(), 0, params);
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
            L.d("showSlideBar", price[0] + " " + price[1]);
            setEditText();
        }
    }

    private void setEditText() {

        L.d("setEditText", price[0] + " : " + price[1]);

        if (price[0] != null && !price[0].equals("")) {
            int value = Integer.parseInt(price[0]);
            if (price[0].equals("3000") || price[0].equals("100")) {
                value = 0;
                processBarView.setOnProgressChangeListener(null);
            }
            processBarView.setLeftValue(value);
            processBarView.setOnProgressChangeListener(onProgressChangeListener);
        }

        if (price[1] != null && !price[1].equals("")) {
            int value = Integer.parseInt(price[1]);
            processBarView.setRightValue(value);
        }

        if (price[0].equals("3000") || price[0].equals("100")) {
            priceGroup.check(R.id.dialog_radiobtn_above3000);
            selectRadId = R.id.dialog_radiobtn_above3000;
            priceLeftEdit.setText("0");
            priceRightEdit.setText(price[0] + "  +");
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
                setItemValue(salePriceList);
                setItemText(salePriceTxt);
                if (isRentOpen) {
                    isRentOpen = false;
                    break;
                }
                recoverRadioStatus(type);
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
            if (defaultBtn.isChecked()) {
                priceLeftEdit.setText(null);
                priceRightEdit.setText(null);
                return;
            }
            processBarView.setLeftValue(oldStartPrice);
            processBarView.setRightValue(oldEndPrice);

            if (abovebtn.isChecked()) setEditText();

        } else {
            priceGroup.check(R.id.dialog_radiobtn_default);
            processBarView.setLeftProcess(0);
            processBarView.setRightProcess(1);
            priceLeftEdit.setText(null);
            priceRightEdit.setText(null);
        }
    }


    public interface onDialogOnclikeLisenter extends BaseDialog.onDialogOnclikeLisenter<Map<String, Object>> {
    }
}
