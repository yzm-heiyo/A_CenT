package com.centanet.hk.aplus.Views.Dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.centanet.hk.aplus.R;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.http.PUT;

/**
 * Created by yangzm4 on 2018/5/10.
 */

public class LogoutDialog extends DialogFragment implements View.OnClickListener {

    private Dialog dialog;
    private WindowManager.LayoutParams lp;
    private TextView logout, cancel;
    private OnDialogOnclikeLisenter onDialogOnclikeLisenter;
    public static final int DIALOG_YES = 1;
    public static final int DIALOG_CANCEL = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
        dialog.setContentView(R.layout.dialog_logout);

        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.AnimBottom);
        lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        // lp.height=getContext().getResources().getDisplayMetrics().heightPixels* 4 / 5;
        window.setAttributes(lp);
        window.setBackgroundDrawableResource(android.R.color.transparent);

        logout = dialog.findViewById(R.id.dialog_logout_logout);
        cancel = dialog.findViewById(R.id.dialog_logout_cancl);
        logout.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_logout_cancl:
                if(onDialogOnclikeLisenter!=null)onDialogOnclikeLisenter.onClick(dialog,DIALOG_CANCEL);
                break;
            case R.id.dialog_logout_logout:
                if(onDialogOnclikeLisenter!=null)onDialogOnclikeLisenter.onClick(dialog,DIALOG_YES);
                break;
        }
    }

    public void setOnDialogOnclikeLisenter(OnDialogOnclikeLisenter onDialogOnclikeLisenter) {
        this.onDialogOnclikeLisenter = onDialogOnclikeLisenter;
    }

    public interface OnDialogOnclikeLisenter {
        void onClick(Dialog v, int clickID);
    }

}
