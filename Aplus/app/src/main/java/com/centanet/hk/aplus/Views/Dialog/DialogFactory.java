package com.centanet.hk.aplus.Views.Dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Widgets.MyCheckBoxLayout;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.centanet.hk.aplus.common.CommandField.DialogType.CONFIRM;
import static com.centanet.hk.aplus.common.CommandField.DialogType.HINT;
import static com.centanet.hk.aplus.common.CommandField.DialogType.LOGOUT;
import static com.centanet.hk.aplus.common.CommandField.DialogType.OPENDATE;
import static com.centanet.hk.aplus.common.CommandField.DialogType.PRICE;
import static com.centanet.hk.aplus.common.CommandField.DialogType.REMARK;
import static com.centanet.hk.aplus.common.CommandField.DialogType.SORT;
import static com.centanet.hk.aplus.common.CommandField.DialogType.STATUS;

/**
 * Created by mzh1608258 on 2018/1/5.
 */

public class DialogFactory extends DialogFragment implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private String thiz = getClass().getSimpleName();

    private Dialog dialog;

    private int type;

    private static DialogFactory fragment;

    private static String TAG = "MyDialog_DialogType";
    private static String TAG2 = "MyDialog_GetClickItem";

    public static String DIALOG_YES = "YES", DIALOG_CANCEL = "NO";

    private View sortLayout, statusLayout, priceLayout, hintLayout, openDateLayout, confirmLayout, logoutLayout, remarkLayout;

    private WindowManager.LayoutParams lp;

    private IGetClickItem clickItem;

    private RadioGroup openDateRG, sortLeftRG, sortRightRG;

    private MyCheckBoxLayout checkBoxLayout;

    private Button statusBtn, searchBtn, priceBtn;

    private TextView confirmConfirm, confirmCancl, logoutCancl, logoutConfirm;

    private EditText remarkRearchEdit,priceLeftEdit,priceRightEdit;

    String[] price = new String[2];

    /**
     * 需要回调的dialog
     */
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({SORT, STATUS, PRICE, HINT, OPENDATE, CONFIRM, LOGOUT, REMARK})
    @interface FileTypeDef {
    }

    public static DialogFactory newInstance(@FileTypeDef int type, IGetClickItem clickItem) {
        fragment = new DialogFactory();
        Bundle b = new Bundle();
        b.putSerializable(TAG, type);
        b.putSerializable(TAG2, clickItem);
        fragment.setArguments(b);

        return fragment;

    }

    /**
     * 不需要回调的dialog
     *
     * @param type
     * @return
     */
    public static DialogFactory newInstance(@FileTypeDef int type) {
        DialogFactory Fragment = new DialogFactory();

        Bundle b = new Bundle();
        b.putSerializable(TAG, type);
        b.putSerializable(TAG2, null);
        Fragment.setArguments(b);

        return Fragment;
    }


    public DialogFactory() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            this.type = (int) args.getSerializable(TAG);
            this.clickItem = (IGetClickItem) args.getSerializable(TAG2);
            init();
            Visibility(this.type);
        }
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
        dialog.setContentView(R.layout.dialog_pattern);

        initLayouts();
    }

    private void initLayouts() {
        sortLayout = dialog.findViewById(R.id.dialog_sort_layout);
        statusLayout = dialog.findViewById(R.id.dialog_status_layout);
        priceLayout = dialog.findViewById(R.id.dialog_price_layout);
        hintLayout = dialog.findViewById(R.id.dialog_hint_layout);
        openDateLayout = dialog.findViewById(R.id.dialog_opendate_layout);
        confirmLayout = dialog.findViewById(R.id.dialog_confirm_layout);
        logoutLayout = dialog.findViewById(R.id.dialog_logout_layout);
        remarkLayout = dialog.findViewById(R.id.dialog_remark_search);


        openDateRG = openDateLayout.findViewById(R.id.dialog_opendate_radiogroup);
        openDateRG.setOnCheckedChangeListener(this);
        sortLeftRG = sortLayout.findViewById(R.id.sort_left_group);
        sortLeftRG.setOnCheckedChangeListener(this);
        sortRightRG = sortLayout.findViewById(R.id.sort_right_group);
        sortRightRG.check(R.id.sort_rb_default);
        sortRightRG.setOnCheckedChangeListener(this);
        checkBoxLayout = statusLayout.findViewById(R.id.dialog_status_confirm_layout);
        statusBtn = statusLayout.findViewById(R.id.dialog_status_confirm);
        statusBtn.setOnClickListener(this);
        searchBtn = remarkLayout.findViewById(R.id.dialog_btn_remark_search);
        searchBtn.setOnClickListener(this);
        priceBtn = priceLayout.findViewById(R.id.dialog_price_confirm);
        priceBtn.setOnClickListener(this);

        priceLeftEdit = priceLayout.findViewById(R.id.dialog_price_left_edit);
        priceRightEdit = priceLayout.findViewById(R.id.dialog_price_right_edit);

        confirmCancl = confirmLayout.findViewById(R.id.dialog_confirm_cancl);
        confirmCancl.setOnClickListener(this);
        confirmConfirm = confirmLayout.findViewById(R.id.dialog_confirm_confirm);
        confirmConfirm.setOnClickListener(this);

        logoutCancl = logoutLayout.findViewById(R.id.dialog_logout_cancl);
        logoutCancl.setOnClickListener(this);
        logoutConfirm = logoutLayout.findViewById(R.id.dialog_logout_logout);
        logoutConfirm.setOnClickListener(this);

        remarkRearchEdit = remarkLayout.findViewById(R.id.dialog_edit_remark_search);

        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.AnimBottom);
        lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        // lp.height=getContext().getResources().getDisplayMetrics().heightPixels* 4 / 5;
        window.setAttributes(lp);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        Visibility(this.type);

    }

    /**
     * 设置要显示的dialog
     */
    private View Visibility(@FileTypeDef int type) {
        switch (type) {
            case SORT:
                sortLayout.setVisibility(View.VISIBLE);
                lp.height = getActivity().getWindowManager().getDefaultDisplay().getHeight() * 2 / 5;
                return sortLayout;

            case STATUS:
                statusLayout.setVisibility(View.VISIBLE);
                lp.height = getActivity().getWindowManager().getDefaultDisplay().getHeight() * 65 / 100;
                L.d(thiz, "StatusDialogShow");
                return statusLayout;

            case PRICE:
                priceLayout.setVisibility(View.VISIBLE);
                lp.height = getActivity().getWindowManager().getDefaultDisplay().getHeight() * 4 / 5;
                return priceLayout;

            case HINT:
                hintLayout.setVisibility(View.VISIBLE);
                lp.gravity = Gravity.CENTER;
                lp.height = getContext().getResources().getDisplayMetrics().heightPixels * 4 / 7;
                lp.width = getContext().getResources().getDisplayMetrics().widthPixels * 5 / 7;

                return priceLayout;

            case OPENDATE:
                openDateLayout.setVisibility(View.VISIBLE);
                lp.height = getActivity().getWindowManager().getDefaultDisplay().getHeight() * 3 / 5;
                return openDateLayout;

            case CONFIRM:
                lp.gravity = Gravity.CENTER;
                confirmLayout.setVisibility(View.VISIBLE);
                lp.height = getContext().getResources().getDisplayMetrics().heightPixels * 1 / 5;
                lp.width = getContext().getResources().getDisplayMetrics().widthPixels * 5 / 7;
                return confirmLayout;

            case LOGOUT:
                logoutLayout.setVisibility(View.VISIBLE);
//                lp.width = getContext().getResources().getDisplayMetrics().widthPixels * 6 / 7;

                return logoutLayout;

            case REMARK:
                lp.gravity = Gravity.CENTER;
                remarkLayout.setVisibility(View.VISIBLE);
                lp.height = getContext().getResources().getDisplayMetrics().heightPixels * 22 / 100;
                lp.width = getContext().getResources().getDisplayMetrics().widthPixels * 72 / 100;
                return remarkLayout;

            default:

                return null;

        }
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //解決兩個RadioGroup的監聽時間的衝突
        if (checkedId == -1) return;
        RadioButton rb = group.findViewById(checkedId);
        if (!rb.isChecked()) return;

        switch (group.getId()) {
            case R.id.sort_left_group:
                if (checkedId == -1) break;
                sortRightRG.clearCheck();
                clickItem.getClickItem(this,"");
                break;
            case R.id.sort_right_group:
                if (checkedId == -1) break;
                sortLeftRG.clearCheck();
                clickItem.getClickItem(this,"");
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_status_confirm:
                if (clickItem != null) {
                    clickItem.getClickItem(this, checkBoxLayout.getCheckBoxContent());
                }
                break;

            case R.id.dialog_confirm_cancl:
                this.dismiss();
                break;

            case R.id.dialog_confirm_confirm:

                if (clickItem != null) {
                    clickItem.getClickItem(this, DIALOG_YES);
                }

                break;

            case R.id.dialog_logout_cancl:
                this.dismiss();
                break;

            case R.id.dialog_logout_logout:
                if (clickItem != null) {
                    clickItem.getClickItem(this, "");
                }
                break;

            case R.id.dialog_btn_remark_search:
                if (clickItem != null) {
                    String[] rebackString = new String[1];
                    rebackString[0] = "";
                    if (remarkRearchEdit.getText().toString().trim() != "") {
                        rebackString[0] = remarkRearchEdit.getText().toString();
                    }
                    clickItem.getClickItem(this, rebackString);
                }
                break;

            case R.id.dialog_price_confirm:
                if (clickItem != null) {
                    clickItem.getClickItem(this, price);
                }
                break;
            default:
                break;
        }
    }


    public interface IGetClickItem extends Serializable {
        void getClickItem(DialogFragment dialog, String... items);
    }
}

