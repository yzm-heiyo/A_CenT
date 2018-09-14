package com.centanet.hk.aplus.Views.Dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;

/**
 * Created by yangzm4 on 2018/8/10.
 */

public class SaleBuyDialog extends DialogFragment implements View.OnClickListener {

    private Dialog dialog;
    private WindowManager.LayoutParams lp;
    private View transactionDate, prelimDate, formalDate, close;
    public static final int CONTACT_TYPE_SALE_BUY = 1;
    public static final int CONTACT_TYPE_BUY = 2;
    public static final int CONTACT_TYPE_SALE = 3;

    private int position;

    private OnItemClickLisenter onItemClickLisenter;
    private LinearLayout content;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        init();
        initLisenter();
    }

    private void initLisenter() {
        transactionDate.setOnClickListener(this);
        prelimDate.setOnClickListener(this);
        formalDate.setOnClickListener(this);

        close.setOnClickListener(v -> dismiss());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return dialog;
    }

    private void init() {

    }

    private void initView() {
        dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_salebug);

        transactionDate = dialog.findViewById(R.id.date_view_bargain);
        prelimDate = dialog.findViewById(R.id.date_view_appointment);
        formalDate = dialog.findViewById(R.id.date_view_official);

        close = dialog.findViewById(R.id.close);

        content = dialog.findViewById(R.id.date_ll_content);

        if (position != 0) {
            selectView(position);
        }

        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.AnimBottom);
        lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        lp.height = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平

        window.setAttributes(lp);
        window.setBackgroundDrawableResource(android.R.color.transparent);
    }

    public void setOnItemClickLisenter(OnItemClickLisenter onItemClickLisenter) {
        this.onItemClickLisenter = onItemClickLisenter;
    }

    @Override
    public void onClick(View v) {
        int type = 1;
        resetView();
        v.setSelected(true);

        switch (v.getId()) {
            case R.id.date_view_bargain:
                type = CONTACT_TYPE_SALE_BUY;
                break;
            case R.id.date_view_appointment:
                type = CONTACT_TYPE_BUY;
                break;
            case R.id.date_view_official:
                type = CONTACT_TYPE_SALE;
                break;
        }
        if (onItemClickLisenter != null) onItemClickLisenter.onClick(dialog, type);
    }

    public void setItemSelectAt(int position) {
        this.position = position;
    }

    private void selectView(int position) {
        L.d("getContactSearchType_select", position + "");
        int count = content.getChildCount();
        int currentPosition = 1;
        for (int i = 0; i < count; i++) {
            View view = content.getChildAt(i);
            if (view instanceof LinearLayout) {
                if (currentPosition == position)
                    view.setSelected(true);
                else view.setSelected(false);
                currentPosition++;
            }
        }
    }

    private void resetView() {
        int count = content.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = content.getChildAt(i);
            if (view instanceof LinearLayout)
                view.setSelected(false);
        }
    }

    public interface OnItemClickLisenter {
        void onClick(Dialog dialog, int type);
    }
}
