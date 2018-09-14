package com.centanet.hk.aplus.Views.Dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.centanet.hk.aplus.R;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by yangzm4 on 2018/7/31.
 */

public class RadioWheelViewDialog extends DialogFragment implements View.OnClickListener {

    private Dialog dialog;
    private WindowManager.LayoutParams lp;
    private WheelView wheelViewFrom;
    private View yes, cancel;
    private String[] years;
    private OnDialogClickLisenter onDialogClickLisenter;
    private String starYear;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initView();
        initLisenter();
    }

    private void initLisenter() {
        yes.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    private void init() {
        years = new String[2];
    }

    private void initView() {
        dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.item_radiowheelview);

        wheelViewFrom = dialog.findViewById(R.id.wheelviewfrom);
        wheelViewFrom.setCyclic(false);


        yes = dialog.findViewById(R.id.yes);
        cancel = dialog.findViewById(R.id.close);


        final List<String> yearList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);


        for (int i = 0; i < 50; i++) {
            yearList.add((year - i) + "");
        }

        if (starYear != null && !starYear.equals("")) {
            years[0] = starYear;
            if(yearList.indexOf(starYear)!=-1)
                wheelViewFrom.setCurrentItem(yearList.indexOf(starYear));

        } else
            years[0] = year + "";



        wheelViewFrom.setAdapter(new ArrayWheelAdapter(yearList));
        wheelViewFrom.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                years[0] = yearList.get(index);
            }
        });


        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.AnimBottom);
        lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT; // 宽度持平
        // lp.height=getContext().getResources().getDisplayMetrics().heightPixels* 4 / 5;
        window.setAttributes(lp);
        window.setBackgroundDrawableResource(android.R.color.transparent);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return dialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yes:
                if (onDialogClickLisenter != null) onDialogClickLisenter.onClick(dialog, years);
                break;
            case R.id.close:
                dismiss();
                break;
        }
    }

    public void setStarYear(String star) {
        starYear = star;
    }

    public void setOnDialogClickLisenter(OnDialogClickLisenter onDialogClickLisenter) {
        this.onDialogClickLisenter = onDialogClickLisenter;
    }

    public interface OnDialogClickLisenter {
        void onClick(Dialog dialog, String... years);
    }
}
