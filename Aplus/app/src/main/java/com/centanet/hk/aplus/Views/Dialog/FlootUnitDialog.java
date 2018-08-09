package com.centanet.hk.aplus.Views.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Views.basic.BaseListAdapter;
import com.centanet.hk.aplus.bean.district.DistrictItem;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class FlootUnitDialog extends DialogFragment implements View.OnClickListener {

    private Dialog dialog;
    private WindowManager.LayoutParams lp;

    private EditText unitEdit, flootEdit;
    private TextView yesTxt;

    private ImageView close;

    private String[] params;

    private OnItemClickLisenter onItemClickLisenter;
    private String thiz = getClass().getSimpleName();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        init();
        initLisenter();
    }

    private void initLisenter() {

    }

    private void init() {

    }

    public void setData(String... data) {
        params = data;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return dialog;
    }

    private void initView() {
        dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_floot_unit);

        unitEdit = dialog.findViewById(R.id.floot_unit_edit_unit);
        flootEdit = dialog.findViewById(R.id.floot_unit_edit_floot);

        if(params!=null){
            if(params[0]!=null && !params[0].equals("")){
                flootEdit.setText(params[0]);
            }
            if(params[1]!=null && !params[1].equals("")){
                unitEdit.setText(params[1]);
            }
        }

        yesTxt = dialog.findViewById(R.id.txt_yes);
        yesTxt.setOnClickListener(this);

        close = dialog.findViewById(R.id.close);
        close.setOnClickListener(this);

        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.AnimBottom);
        lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        lp.height = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        // lp.height=getContext().getResources().getDisplayMetrics().heightPixels* 4 / 5;
        window.setAttributes(lp);
        window.setBackgroundDrawableResource(android.R.color.transparent);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_yes:
                String[] str = new String[2];
                str[0] = flootEdit.getText().toString();
                str[1] = unitEdit.getText().toString();
                if (onItemClickLisenter != null) onItemClickLisenter.onClick(dialog, v, str);
                dismiss();
                break;
            case R.id.close:
                dismiss();
                break;
        }
    }

    public interface OnAdapterItemClickLisenter {
        void onClick(View v, int postion);
    }

    public void setOnItemClickLisenter(OnItemClickLisenter onItemClickLisenter) {
        this.onItemClickLisenter = onItemClickLisenter;
    }

    public OnItemClickLisenter getOnItemClickLisenter() {
        return onItemClickLisenter;
    }

    public interface OnItemClickLisenter {
        void onClick(Dialog dialog, View v, String... list);
    }
}
