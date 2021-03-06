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
import android.widget.RadioGroup;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Views.basic.BaseDialog;

/**
 * Created by yangzm4 on 2018/3/21.
 */

public class OpenDataDialog extends BaseDialog implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    public static final int DIALOG_TYPE_DEFAULT = R.id.dialog_opendate_rb_default;
    public static final int DIALOG_TYPE_MODIFICATION = R.id.dialog_opendate_rb_modification_last;
    public static final int DIALOG_TYPE_FOLLOW = R.id.dialog_opendate_rb_follow;
    public static final int DIALOG_TYPE_PRICE = R.id.dialog_opendate_rb_price;
    public static final int DIALOG_TYPE_ENTRUST_START = R.id.dialog_opendate_rb_entrust_start;
    public static final int DIALOG_TYPE_ENTRUSE_END = R.id.dialog_opendate_rb_entrust_end;
    public static final int DIALOG_TYPE_ANTICIPATE = R.id.dialog_opendate_rb_anticipate;
    public static final int DIALOG_TYPE_CHANGEHOUSE = R.id.dialog_opendate_rb_changehouse;
    private Dialog dialog;
    private WindowManager.LayoutParams lp;
    private View openData;
    private int defaultId;
    private RadioGroup dataRG;
    private View close;
    private boolean isFirst = true;

    public OpenDataDialog() {
    }


    @SuppressLint("ValidFragment")
    public OpenDataDialog(int defaultId) {
        this.defaultId = defaultId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_opendata);
        initLayouts();
    }

    private void initLayouts() {
        openData = dialog.findViewById(R.id.dialog_opendate_layout);
        close = dialog.findViewById(R.id.close);
        dataRG = dialog.findViewById(R.id.dialog_opendate_radiogroup);
        dialog.findViewById(R.id.close).setOnClickListener(this);

        dialog.findViewById(R.id.dialog_opendate_rb_default).setOnClickListener(this);
        dialog.findViewById(R.id.dialog_opendate_rb_modification_last).setOnClickListener(this);
        dialog.findViewById(R.id.dialog_opendate_rb_follow).setOnClickListener(this);
        dialog.findViewById(R.id.dialog_opendate_rb_price).setOnClickListener(this);

        dialog.findViewById(R.id.dialog_opendate_rb_entrust_start).setOnClickListener(this);
        dialog.findViewById(R.id.dialog_opendate_rb_entrust_end).setOnClickListener(this);
        dialog.findViewById(R.id.dialog_opendate_rb_anticipate).setOnClickListener(this);
        dialog.findViewById(R.id.dialog_opendate_rb_changehouse).setOnClickListener(this);


        if (defaultId != 0)
            dataRG.check(defaultId == 0 ? R.id.dialog_opendate_rb_default : defaultId);

//        dataRG.setOnCheckedChangeListener(this);

        final Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.AnimBottom);
        lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.height = getActivity().getWindowManager().getDefaultDisplay().getHeight() * 8 / 10;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平

//        openData.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
//            if (isFirst) {
//                lp.height = openData.getHeight();
//                window.setAttributes(lp);
//                isFirst = !isFirst;
//            }
//        });

        window.setBackgroundDrawableResource(android.R.color.transparent);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return dialog;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
//        switch (checkedId) {
//            case R.id.dialog_opendate_rb_default:
//                onDialogClikeLisenter.onClike(dialog, DIALOG_TYPE_DEFAULT, getString(R.string.dialog_opendate_opendate));
//                break;
//            case R.id.dialog_opendate_rb_price:
//                onDialogClikeLisenter.onClike(dialog, DIALOG_TYPE_PRICE, getString(R.string.dialog_opendate_last_changeprice));
//                break;
//            case R.id.dialog_opendate_rb_modification_last:
//                onDialogClikeLisenter.onClike(dialog, DIALOG_TYPE_MODIFICATION, getString(R.string.dialog_opendate_last_modify));
//                break;
//            case R.id.dialog_opendate_rb_follow:
//                onDialogClikeLisenter.onClike(dialog, DIALOG_TYPE_FOLLOW, getString(R.string.dialog_opendate_last_follow));
//                break;
//            case R.id.dialog_opendate_rb_entrust_start:
//                onDialogClikeLisenter.onClike(dialog, DIALOG_TYPE_ENTRUST_START, getString(R.string.dialog_opendate_entrust_begin));
//                break;
//            case R.id.dialog_opendate_rb_entrust_end:
//                onDialogClikeLisenter.onClike(dialog, DIALOG_TYPE_ENTRUSE_END, getString(R.string.dialog_opendate_entrust_end));
//                break;
//            case R.id.dialog_opendate_rb_anticipate:
//                onDialogClikeLisenter.onClike(dialog, DIALOG_TYPE_ANTICIPATE, getString(R.string.dialog_opendate_estimate_date));
//                break;
//            case R.id.dialog_opendate_rb_changehouse:
//                onDialogClikeLisenter.onClike(dialog, DIALOG_TYPE_CHANGEHOUSE, getString(R.string.dialog_opendate_change_date));
//                break;
//        }
    }

    @Override
    public void onClick(View view) {
        L.d("OpenDataDialog", view.getId()+"");
        switch (view.getId()) {
            case R.id.close:
                dismiss();
                break;
            case R.id.dialog_opendate_rb_default:
                onDialogClikeLisenter.onClike(dialog, DIALOG_TYPE_DEFAULT, getString(R.string.dialog_opendate_opendate));
                break;
            case R.id.dialog_opendate_rb_price:
                onDialogClikeLisenter.onClike(dialog, DIALOG_TYPE_PRICE, getString(R.string.dialog_opendate_last_changeprice));
                break;
            case R.id.dialog_opendate_rb_modification_last:
                onDialogClikeLisenter.onClike(dialog, DIALOG_TYPE_MODIFICATION, getString(R.string.dialog_opendate_last_modify));
                break;
            case R.id.dialog_opendate_rb_follow:
                onDialogClikeLisenter.onClike(dialog, DIALOG_TYPE_FOLLOW, getString(R.string.dialog_opendate_last_follow));
                break;
            case R.id.dialog_opendate_rb_entrust_start:
                onDialogClikeLisenter.onClike(dialog, DIALOG_TYPE_ENTRUST_START, getString(R.string.dialog_opendate_entrust_begin));
                break;
            case R.id.dialog_opendate_rb_entrust_end:
                onDialogClikeLisenter.onClike(dialog, DIALOG_TYPE_ENTRUSE_END, getString(R.string.dialog_opendate_entrust_end));
                break;
            case R.id.dialog_opendate_rb_anticipate:
                onDialogClikeLisenter.onClike(dialog, DIALOG_TYPE_ANTICIPATE, getString(R.string.dialog_opendate_estimate_date));
                break;
            case R.id.dialog_opendate_rb_changehouse:
                onDialogClikeLisenter.onClike(dialog, DIALOG_TYPE_CHANGEHOUSE, getString(R.string.dialog_opendate_change_date));
                break;
        }
    }

    public interface onDialogOnclikeLisenter extends BaseDialog.onDialogOnclikeLisenter<String> {
    }


}
