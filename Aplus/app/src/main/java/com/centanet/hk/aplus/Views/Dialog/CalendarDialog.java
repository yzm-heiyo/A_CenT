package com.centanet.hk.aplus.Views.Dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Widgets.CalendarListView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by yangzm4 on 2018/7/19.
 */

public class CalendarDialog extends DialogFragment implements View.OnClickListener {

    private Dialog dialog;
    private WindowManager.LayoutParams lp;

    private ImageView closeImg, laseImg, nextImg;
    private TextView yearTxt, yesTxt;
    private TextView startYearTxt, startDateTxt, endYearTxt, endDateTxt;
    private CalendarListView calendarListView;
    private Calendar calendar, startCl, endCl;

    private OnDialogClickLisenter onDialogOnclikeLisenter;
    private int currentYear;
    private int offset;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        init();
        initLisenter();
    }

    private void init() {

        calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
    }

    private void initLisenter() {

        calendarListView.setOnItemClickLisenter((start, end) -> {

            startCl = start;
            endCl = end;

            if (start != null) {
                startYearTxt.setText(start.get(Calendar.YEAR) + "");
                startDateTxt.setText(start.get(Calendar.MONTH) + 1 + "月" + start.get(Calendar.DAY_OF_MONTH) + "日");
            } else {
                startYearTxt.setText("");
                startDateTxt.setText("");
            }

            if (end != null) {
                endYearTxt.setText(end.get(Calendar.YEAR) + "");
                endDateTxt.setText(end.get(Calendar.MONTH) + 1 + "月" + end.get(Calendar.DAY_OF_MONTH) + "日");

                int days = (int) ((start.getTimeInMillis() - end.getTimeInMillis()) / (24L * 60 * 60 * 1000));
                yesTxt.setText(getString(R.string.dialog_confirm_confirm) + "(共" + Math.abs(days) + "日)");

            } else {
                endYearTxt.setText("");
                endDateTxt.setText("");
                yesTxt.setText(getString(R.string.dialog_confirm_confirm));
            }
        });

        nextImg.setOnClickListener(v -> {
            currentYear++;
            calendar.set(Calendar.YEAR, currentYear);
            calendarListView.setCalendar(calendar);
            yearTxt.setText(currentYear + "");
        });

        laseImg.setOnClickListener(v -> {
            currentYear--;
            calendar.set(Calendar.YEAR, currentYear);
            calendarListView.setCalendar(calendar);
            yearTxt.setText(currentYear + "");
        });

        yesTxt.setOnClickListener(v -> {
            if (onDialogOnclikeLisenter != null)
                onDialogOnclikeLisenter.onClick(dialog, v, startCl, endCl);
        });

        closeImg.setOnClickListener(v -> dismiss());

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
        dialog.setContentView(R.layout.dialog_calendar);

        closeImg = dialog.findViewById(R.id.calendar_img_close);
        laseImg = dialog.findViewById(R.id.calendar_img_last);
        nextImg = dialog.findViewById(R.id.calendar_img_next);

        yearTxt = dialog.findViewById(R.id.calendar_txt_year);
        yesTxt = dialog.findViewById(R.id.calendar_txt_yes);

        startYearTxt = dialog.findViewById(R.id.calendar_txt_start_year);
        startDateTxt = dialog.findViewById(R.id.calendar_txt_start_date);
        endYearTxt = dialog.findViewById(R.id.calendar_txt_end_year);
        endDateTxt = dialog.findViewById(R.id.calendar_txt_end_date);

        if (startCl != null) {

            startYearTxt.setText(startCl.get(Calendar.YEAR) + "");
            startDateTxt.setText(startCl.get(Calendar.MONTH) + 1 + "月" + startCl.get(Calendar.DAY_OF_MONTH) + "日");
        }

        if (endCl != null) {
            endYearTxt.setText(endCl.get(Calendar.YEAR) + "");
            endDateTxt.setText(endCl.get(Calendar.MONTH) + 1 + "月" + endCl.get(Calendar.DAY_OF_MONTH) + "日");
        }

        calendarListView = dialog.findViewById(R.id.calendar_calendarlist);
        calendarListView.setEndCalendar(endCl);
        calendarListView.setStartCalendar(startCl);

        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.AnimBottom);
        lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        // lp.height=getContext().getResources().getDisplayMetrics().heightPixels* 4 / 5;
        window.setAttributes(lp);
        window.setBackgroundDrawableResource(android.R.color.transparent);

    }

    public void setStartCl(Calendar startCl) {
        this.startCl = startCl;
    }

    public void setEndCl(Calendar endCl) {
        this.endCl = endCl;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


        }
    }

    public void setOnDialogOnclikeLisenter(OnDialogClickLisenter onDialogOnclikeLisenter) {
        this.onDialogOnclikeLisenter = onDialogOnclikeLisenter;
    }

    public interface OnDialogClickLisenter {
        void onClick(Dialog dialog, View view, Calendar start, Calendar end);
    }

    /**
     * 获取月份标题
     */
    private String getMonthStr(Date month) {
        SimpleDateFormat df = new SimpleDateFormat("MM月dd日");
        return df.format(month);
    }

}
