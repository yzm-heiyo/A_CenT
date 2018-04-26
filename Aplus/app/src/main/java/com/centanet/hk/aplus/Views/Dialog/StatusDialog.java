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
import android.widget.Button;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Views.basic.BaseDialog;
import com.centanet.hk.aplus.Widgets.SmartCheckBoxLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangzm4 on 2018/2/28.
 */

public class StatusDialog extends BaseDialog implements View.OnClickListener {

    private String thiz = getClass().getSimpleName();
    private Dialog dialog;
    List<Integer> checkBoxSelecterList;
    private WindowManager.LayoutParams lp;
    private View status;
    private SmartCheckBoxLayout checkBoxLayout;
    private Button yes;
    private onDialogOnclikeLisenter onDialogOnclikeLisenter;
    private List<String> statusList;
    private boolean isFirst = true;
//    private boolean isSelectAll = false;


    public StatusDialog() {
    }


    @SuppressLint("ValidFragment")
    public StatusDialog(List<Integer> checkBoxSelecterList) {
        this.checkBoxSelecterList = checkBoxSelecterList;
    }


    @SuppressLint("ValidFragment")
    public StatusDialog(List<String> status, List<Integer> checkBoxSelecterList) {
        this.checkBoxSelecterList = checkBoxSelecterList;
        statusList = status;
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
        dialog.setContentView(R.layout.dialog_status);
        initLayouts();
    }

    private void initLayouts() {

        status = dialog.findViewById(R.id.dialog_status_layout);
        checkBoxLayout = status.findViewById(R.id.dialog_status_confirm_layout);
        checkBoxLayout.setOnItemClick(new SmartCheckBoxLayout.OnItemClick() {
            @Override
            public void onClick(View v, int viewId, List<Integer> selectList, int position) {
                L.d("checkLayout", selectList.toString());
                checkBoxSelecterList = selectList;
            }
        });

//        checkBoxLayout.setItemText(statusList);
        checkBoxLayout.setSeletList(checkBoxSelecterList);
//        if(checkBoxSelecterList==null||checkBoxSelecterList.isEmpty())checkBoxLayout.selectAllItem(true);

        yes = status.findViewById(R.id.dialog_status_confirm);
        yes.setOnClickListener(this);

        final Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.AnimBottom);
        lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        status.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (isFirst) {
                    lp.height = status.getHeight();
                    L.d("statusHeight", status.getHeight() + "");
                    window.setAttributes(lp);
                    window.setBackgroundDrawableResource(android.R.color.transparent);
                    isFirst = !isFirst;
                }
            }
        });

        if (checkBoxSelecterList == null)
            checkBoxSelecterList = new ArrayList<>();
    }

//    public void selectAllItem(boolean isSelectAll) {
//        this.isSelectAll=isSelectAll;
//    }

    @Override
    public void onClick(View v) {
        if (onDialogOnclikeLisenter != null)
            onDialogOnclikeLisenter.onClick(dialog, 0, checkBoxSelecterList, checkBoxLayout.getCheckBoxContent());
    }

    public void setOnDialogOnclikeLisenter(StatusDialog.onDialogOnclikeLisenter onDialogOnclikeLisenter) {
        this.onDialogOnclikeLisenter = onDialogOnclikeLisenter;
    }

    public interface onDialogOnclikeLisenter {
        void onClick(Dialog v, int viewID, List<Integer> viewList, String[] content);
    }
}
