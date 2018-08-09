package com.centanet.hk.aplus.Views.Dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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

public class TextDialog extends DialogFragment implements View.OnClickListener {

    private String thiz = getClass().getSimpleName();
    public static final int DIALOG_YES = 0, DIALOG_CANCEL = 1;
    private OnItemClickListener mOnItemClickListener;
    private Button leftBtn, rightBtn;
    private View view;
    private double dialogWidthPercent = 0.72, dialogHeightPercent = 0.17;
    private String ContentString, tipString;
    private int Layout_ID = R.id.tips_dialog_tip_txt;
    private boolean isLeftVisibility = true;
    private View lineView;
    private Dialog dialog;
    private boolean ableToCancelOutside = true;

    public TextDialog() {
    }

    @SuppressLint("ValidFragment")
    public TextDialog(View view, double dialogWidthPercent, double dialogHeightPercent) {
        this.view = view;
        this.dialogWidthPercent = dialogWidthPercent;
        this.dialogHeightPercent = dialogHeightPercent;
    }

    @SuppressLint("ValidFragment")
    public TextDialog(View view, double dialogWidthPercent, double dialogHeightPercent, int layout_ID) {
        this.view = view;
        this.dialogWidthPercent = dialogWidthPercent;
        this.dialogHeightPercent = dialogHeightPercent;
        this.Layout_ID = layout_ID;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.d("onCreate","dialog");
        init();
    }

    private void init() {
        dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(ableToCancelOutside);
        dialog.setContentView(R.layout.fragment_dialog_tips);
        initLayouts();
    }

    private void initLayouts() {
        View view = dialog.findViewById(R.id.tips_dialoa_contentview);
        leftBtn=view.findViewById(R.id.tips_dialog_left);
        leftBtn.setOnClickListener(this);
        rightBtn=view.findViewById(R.id.tips_dialog_right);
        rightBtn.setOnClickListener(this);
        lineView = view.findViewById(R.id.dialog_tips_line);
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return dialog;
    }

    public void setTipString(String tipString) {
        this.tipString = tipString;
    }

    public void setCanceledOnTouchOutside(boolean cancelable){
        ableToCancelOutside = cancelable;
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
        getDialog().getWindow().setLayout((int) (width * dialogWidthPercent), (int) (height * dialogHeightPercent));
    }

    public void setDialogCancelOnTouchOutside(boolean cancelOnTouchOutside) {
        getDialog().setCanceledOnTouchOutside(cancelOnTouchOutside);
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
