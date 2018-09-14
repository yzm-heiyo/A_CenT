package com.centanet.hk.aplus.Views.Dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Views.basic.BaseDialog;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangzm4 on 2018/2/28.
 */

public class SortDialog extends BaseDialog implements RadioGroup.OnCheckedChangeListener,View.OnClickListener {

    public static final String PARAMS_ASCENDING = "Ascending";
    public static final String PARAMS_SORTFIELD = "SortField";
    public static final String PARAMS_SELECTID = "slectId";
    private Dialog dialog;
    private WindowManager.LayoutParams lp;
    private RadioGroup sortLeftRG, sortRightRG, txtRG;
    private View sortLayout;
    private int defaultId;
    private ImageView imageView;
    private boolean show;
//    private boolean isFirst = true;

    public SortDialog() {
    }


    @SuppressLint("ValidFragment")
    public SortDialog(int defaultId) {
        this.defaultId = defaultId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return dialog;
    }

    private void init() {
        dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_sort);
        initLayouts();
    }

    private void initLayouts() {

        sortLayout = dialog.findViewById(R.id.dialog_sort_layout);
        sortLeftRG = sortLayout.findViewById(R.id.sort_left_group);
        sortRightRG = sortLayout.findViewById(R.id.sort_right_group);
        imageView = dialog.findViewById(R.id.close);
        imageView.setOnClickListener(this);
        txtRG = sortLayout.findViewById(R.id.sort_txt_group);

        if(!show){
            dialog.findViewById(R.id.sort_txt_green).setVisibility(View.GONE);
            dialog.findViewById(R.id.sort_rb_green_up).setVisibility(View.GONE);
            dialog.findViewById(R.id.sort_rb_green_down).setVisibility(View.GONE);
        }else {
            dialog.findViewById(R.id.sort_txt_green).setVisibility(View.VISIBLE);
            dialog.findViewById(R.id.sort_rb_green_up).setVisibility(View.VISIBLE);
            dialog.findViewById(R.id.sort_rb_green_down).setVisibility(View.VISIBLE);
        }

        checkRadioButton(defaultId);
        checkTxt(defaultId);
        sortRightRG.check(defaultId == 0 ? R.id.sort_rb_default : defaultId);
        sortRightRG.setOnCheckedChangeListener(this);
        sortLeftRG.setOnCheckedChangeListener(this);

        final Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.AnimBottom);
        lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

//        sortLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                if (isFirst) {
//                    lp.height = getActivity().getWindowManager().getDefaultDisplay().getHeight() * 2 / 5;
//                    window.setAttributes(lp);
//                    isFirst = !isFirst;
//                }
//            }
//        });

        window.setBackgroundDrawableResource(android.R.color.transparent);
    }

    private void checkTxt(int defaultId) {
        if (defaultId == 0) {
            txtRG.check(R.id.sort_txt_default);
            return;
        }
        if (defaultId != 0) {
            switch (defaultId) {
                case R.id.sort_rb_default:
                    txtRG.check(R.id.sort_txt_default);
                    break;
                case R.id.sort_rb_price_up:
                case R.id.sort_rb_price_down:
                    txtRG.check(R.id.sort_txt_sale);
                    break;
                case R.id.sort_rb_real_up:
                case R.id.sort_rb_real_down:
                    txtRG.check(R.id.sort_txt_really);
                    break;
                case R.id.sort_rb_rent_up:
                case R.id.sort_rb_rent_down:
                    txtRG.check(R.id.sort_txt_rent);
                    break;
                case R.id.sort_rb_use_up:
                case R.id.sort_rb_use_down:
                    txtRG.check(R.id.sort_txt_user);
                    break;
                case R.id.sort_rb_green_up:
                case R.id.sort_rb_green_down:
                    txtRG.check(R.id.sort_txt_green);
                    break;
            }
        }

    }

    public void isShowGreenPrice(boolean show){
        this.show = show;
        //        if(!show){
//            dialog.findViewById(R.id.sort_txt_green).setVisibility(View.GONE);
//            dialog.findViewById(R.id.sort_rb_green_up).setVisibility(View.GONE);
//            dialog.findViewById(R.id.sort_rb_green_down).setVisibility(View.GONE);
//        }else {
//            dialog.findViewById(R.id.sort_txt_green).setVisibility(View.VISIBLE);
//            dialog.findViewById(R.id.sort_rb_green_up).setVisibility(View.VISIBLE);
//            dialog.findViewById(R.id.sort_rb_green_down).setVisibility(View.VISIBLE);
//        }
    }

    private void checkRadioButton(int defaultId) {
        if (defaultId != 0)
            switch (defaultId) {
                case R.id.sort_rb_price_up:
                case R.id.sort_rb_real_up:
                case R.id.sort_rb_rent_up:
                case R.id.sort_rb_use_up:
                case R.id.sort_rb_green_up:
                    L.d("SortDialog", "" + defaultId);
                    sortLeftRG.check(defaultId);
                    return;
            }
        sortRightRG.check(defaultId == 0 ? R.id.sort_rb_default : defaultId);
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //解決兩個RadioGroup的監聽時間的衝突
        if (checkedId == -1) return;
        RadioButton rb = group.findViewById(checkedId);
        if (rb == null) return;
        if (!rb.isChecked()) return;

        switch (group.getId()) {
            case R.id.sort_left_group:
                if (checkedId == -1) break;
                sortRightRG.clearCheck();
                if (onDialogClikeLisenter != null) {
                    onClike(checkedId);
                }
                break;
            case R.id.sort_right_group:
                if (checkedId == -1) break;
                sortLeftRG.clearCheck();
                if (onDialogClikeLisenter != null) {
                    onClike(checkedId);
                }
                break;
        }
    }

    private void onClike(int checkedId) {

        Map<String, Object> params = new HashMap();
        params.put(PARAMS_SELECTID, checkedId);

        switch (checkedId) {
            case R.id.sort_rb_default:
                params.put(PARAMS_ASCENDING, true);
                break;
            case R.id.sort_rb_price_up:
                params.put(PARAMS_ASCENDING, true);
                params.put("SortField", "SalePrice");
                break;
            case R.id.sort_rb_rent_up:
                params.put(PARAMS_ASCENDING, true);
                params.put(PARAMS_SORTFIELD, "RentPrice");
                break;
            case R.id.sort_rb_use_up:
                params.put(PARAMS_ASCENDING, true);
                params.put(PARAMS_SORTFIELD, "SquareUseFoot");
                break;
            case R.id.sort_rb_real_up:
                params.put(PARAMS_ASCENDING, true);
                params.put(PARAMS_SORTFIELD, "SquareFoot");
                break;
            case R.id.sort_rb_green_up:
                params.put(PARAMS_ASCENDING, true);
                params.put(PARAMS_SORTFIELD, "SalePricePremiumUnpaid");
                break;
            case R.id.sort_rb_price_down:
                params.put(PARAMS_ASCENDING, false);
                params.put(PARAMS_SORTFIELD, "SalePrice");
                break;
            case R.id.sort_rb_rent_down:
                params.put(PARAMS_ASCENDING, false);
                params.put(PARAMS_SORTFIELD, "RentPrice");
                break;
            case R.id.sort_rb_use_down:
                params.put(PARAMS_ASCENDING, false);
                params.put(PARAMS_SORTFIELD, "SquareUseFoot");
                break;
            case R.id.sort_rb_real_down:
                params.put(PARAMS_ASCENDING, false);
                params.put(PARAMS_SORTFIELD, "SquareFoot");
                break;
            case R.id.sort_rb_green_down:
                params.put(PARAMS_ASCENDING, false);
                params.put(PARAMS_SORTFIELD, "SalePricePremiumUnpaid");
                break;
        }
        L.d("SortDialog", "" + params.get("Ascending"));
        onDialogClikeLisenter.onClike(dialog, 0, params);
    }

    @Override
    public void onClick(View view) {
        dialog.dismiss();
    }

    public interface onDialogOnclikeLisenter extends BaseDialog.onDialogOnclikeLisenter<Map<String, Object>> {
    }


}
