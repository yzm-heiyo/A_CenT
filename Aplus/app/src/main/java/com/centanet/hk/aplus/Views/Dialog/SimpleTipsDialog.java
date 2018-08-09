package com.centanet.hk.aplus.Views.Dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;

/**
 * Created by yangzm4 on 2018/1/25.
 * 提示dialog
 * shape的實現需要
 * getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
 * 設置dialogfragment需要設置
 * getDialog().getWindow().setLayout()
 */

public class SimpleTipsDialog extends DialogFragment implements View.OnClickListener {

    private String thiz = getClass().getSimpleName();
    public static final int DIALOG_YES = 0, DIALOG_CANCEL = 1;
    private OnItemClickListener mOnItemClickListener;
    private Button leftBtn, rightBtn;
    private View view;
    private WindowManager.LayoutParams lp;
    private double dialogWidthPercent = 0.72, dialogHeightPercent = 0.17;
    private String ContentString, tipString;
    private int Layout_ID = R.id.tips_dialog_tip_txt;
    private boolean isLeftVisibility = true;
    private View lineView;
    private boolean ableToCancelOutside = true;
    private boolean ableToKeyBack = true;
    private int contentHeightOld = 1;
    private int contentHeightTrue;
    private String leftString;
    private String rightString;

    public SimpleTipsDialog() {
    }

    @SuppressLint("ValidFragment")
    public SimpleTipsDialog(View view, double dialogWidthPercent, double dialogHeightPercent) {
        this.view = view;
        this.dialogWidthPercent = dialogWidthPercent;
        this.dialogHeightPercent = dialogHeightPercent;
    }

    @SuppressLint("ValidFragment")
    public SimpleTipsDialog(View view, double dialogWidthPercent, double dialogHeightPercent, int layout_ID) {
        this.view = view;
        this.dialogWidthPercent = dialogWidthPercent;
        this.dialogHeightPercent = dialogHeightPercent;
        this.Layout_ID = layout_ID;
    }

    public void setTipString(String tipString) {
        this.tipString = tipString;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().setCanceledOnTouchOutside(ableToCancelOutside);
        if (!ableToKeyBack) getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });

        View content = inflater.inflate(R.layout.fragment_dialog_tips, container, false);

        if (view != null) {
            LinearLayout topContentView = content.findViewById(R.id.tips_dialoa_contentview);
            topContentView.removeAllViews();
            topContentView.addView(view);
        }

        if (ContentString != null) {
            TextView conTentTxt = content.findViewById(Layout_ID);
            conTentTxt.setText(ContentString);
        }

        if (tipString != null) {
            TextView tipTxt = content.findViewById(R.id.dialog_title_txt);
            tipTxt.setText(tipString + "");
        }

        leftBtn = content.findViewById(R.id.tips_dialog_left);
        leftBtn.setOnClickListener(this);
        rightBtn = content.findViewById(R.id.tips_dialog_right);
        rightBtn.setOnClickListener(this);
        lineView = content.findViewById(R.id.dialog_tips_line);
        if (leftString != null && !leftString.equals("")) {
            leftBtn.setText(leftString);
        }

        if (rightString != null && !rightString.equals("")) {
            rightBtn.setText(rightString);
        }

        if (!isLeftVisibility) {
            leftBtn.setVisibility(View.GONE);
            lineView.setVisibility(View.GONE);
        }

        content.getViewTreeObserver().addOnGlobalLayoutListener(() -> {

            Window window = getDialog().getWindow();
            lp = window.getAttributes();
            lp.gravity = Gravity.CENTER; // 巨间

            WindowManager windowManager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
            int width = windowManager.getDefaultDisplay().getWidth();
            lp.width = (int) (width * dialogWidthPercent);
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//            content.measure(0,0);
            window.setAttributes(lp);
            window.setBackgroundDrawableResource(android.R.color.transparent);

        });

        return content;
    }

    public void setContentView(View v) {
        this.view = v;
    }

    public void setDialogWidthPercent(double dialogWidthPercent) {
        this.dialogWidthPercent = dialogWidthPercent;
    }

    public void setDialogHeightPercent(double dialogHeightPercent) {
        this.dialogHeightPercent = dialogHeightPercent;
    }

    public void setLeftBtnVisibility(boolean visibility) {
        isLeftVisibility = visibility;
    }

    public void setLeftButtonText(String tips) {
        leftString = tips;
    }

    public void setRightButtonText(String tips) {
        rightString = tips;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tips_dialog_left:
                dismiss();
                if (mOnItemClickListener != null) mOnItemClickListener.onClick(this, DIALOG_CANCEL);
                break;
            case R.id.tips_dialog_right:
                dismiss();
                if (mOnItemClickListener != null) mOnItemClickListener.onClick(this, DIALOG_YES);
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        WindowManager windowManager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();
        int height = windowManager.getDefaultDisplay().getHeight();

    }


    public void ableToKeyBack(boolean ableToKeyBack) {
        this.ableToKeyBack = ableToKeyBack;
    }

    public void setDialogCancelOnTouchOutside(boolean cancelOnTouchOutside) {
        ableToCancelOutside = cancelOnTouchOutside;
    }

    public void setContentString(String ContentString) {
        this.ContentString = ContentString;
    }

    public void setOnItemclickListener(OnItemClickListener onItemclickListener) {
        mOnItemClickListener = onItemclickListener;
    }

    public interface OnItemClickListener {
        void onClick(DialogFragment dialog, int type);
    }

}
