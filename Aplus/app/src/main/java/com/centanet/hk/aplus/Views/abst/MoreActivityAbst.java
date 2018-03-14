package com.centanet.hk.aplus.Views.abst;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Views.Dialog.DateDialog;
import com.centanet.hk.aplus.Views.Dialog.DialogFactory;
import com.centanet.hk.aplus.Views.basic.BasicActivty;
import com.centanet.hk.aplus.Widgets.MyRadioGroup;


import java.util.Calendar;

import static com.centanet.hk.aplus.common.CommandField.DialogType.OPENDATE;
import static com.centanet.hk.aplus.common.CommandField.DialogType.STATUS;

/**
 * Created by mzh1608258 on 2018/1/8.
 */

public abstract class MoreActivityAbst extends BasicActivty implements View.OnClickListener, MyRadioGroup.OnCheckedChangeListener {

    private TextView textDate, textDatecompletionBegin, textDatecompletionEnd, textDateOpenDateBegin, textDateOpenDateEnd, statusBegin, statusEnd;
    private Calendar completionBegin, completionEnd, openBegin, openEnd;
    private String[] statusBgeins, statusEnds;
    private View statusBeginLayout, statusEndLayout;
    private IMoreActivity more;

    private MyRadioGroup ssdRG, IntervalRG, directionRG, tagRG;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        setViews();
        setListeners();
        more = setIMoreActivity();
    }

    protected abstract IMoreActivity setIMoreActivity();

    //開盤日期
    public void openDate(View view) {
        DialogFragment dialog = DialogFactory.newInstance(OPENDATE, new DialogFactory.IGetClickItem() {
            @Override
            public void getClickItem(DialogFragment dialog, String... items) {
                textDate.setText(items[0]);
                dialog.dismiss();
            }

        });
        dialog.show(getSupportFragmentManager(), "");
    }

    public abstract void resetParams(View v);

    public abstract void search(View v);

    private void setViews() {
        textDate = this.findViewById(R.id.activity_more_open_date_text);
        textDatecompletionBegin = this.findViewById(R.id.activity_more_completion_begin);
        textDatecompletionEnd = this.findViewById(R.id.activity_more_completion_end);
        textDateOpenDateBegin = this.findViewById(R.id.activity_more_opendate_begin);
        textDateOpenDateEnd = this.findViewById(R.id.activity_more_opendate_end);
        statusBeginLayout = this.findViewById(R.id.activity_more_status_start_layout);
        statusEndLayout = this.findViewById(R.id.activity_more_status_end_layout);
        statusBegin = this.findViewById(R.id.activity_more_status_start);
        statusEnd = this.findViewById(R.id.activity_more_status_end);
        ssdRG = this.findViewById(R.id.activity_more_ssd_radiogroup);
        IntervalRG = this.findViewById(R.id.activity_more_interval_radiogroup);
        directionRG = this.findViewById(R.id.activity_more_direction_radiogroup);
        tagRG = this.findViewById(R.id.activity_more_tag_radiogroup);
    }

    private void setListeners() {
        textDatecompletionBegin.setOnClickListener(this);
        textDatecompletionEnd.setOnClickListener(this);
        textDateOpenDateBegin.setOnClickListener(this);
        textDateOpenDateEnd.setOnClickListener(this);
        statusBeginLayout.setOnClickListener(this);
        statusEndLayout.setOnClickListener(this);
        ssdRG.setOnCheckedChangeListener(this);
        IntervalRG.setOnCheckedChangeListener(this);
        directionRG.setOnCheckedChangeListener(this);
        tagRG.setOnCheckedChangeListener(this);

    }

    @Override
    public void onCheckedChanged(MyRadioGroup group, int checkedId) {
        RadioButton rb;
        switch (group.getId()) {
            case R.id.activity_more_ssd_radiogroup:

                rb = group.findViewById(checkedId);
                if (null != more) {
                    more.getSSD(rb.getText().toString());
                }

                break;

            case R.id.activity_more_interval_radiogroup:
                rb = group.findViewById(checkedId);
                if (null != more) {
                    more.getInterval(rb.getText().toString());
                }
                break;

            case R.id.activity_more_direction_radiogroup:
                rb = group.findViewById(checkedId);
                if (null != more) {
                    more.getDirection(rb.getText().toString());
                }
                break;

            case R.id.activity_more_tag_radiogroup:
                rb = group.findViewById(checkedId);
                if (null != more) {
                    more.getTag(rb.getText().toString());
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {

        Calendar c = Calendar.getInstance();

        switch (v.getId()) {
            case R.id.activity_more_completion_begin:
                DateDialog.showDateDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        completionBegin = Calendar.getInstance();
                        completionBegin.set(year, month, dayOfMonth);
                        textDatecompletionBegin.setText("" + year + "-" + (month + 1) + "-" + dayOfMonth);
                        if (null != more) {
                            more.getCompletionDate(completionBegin, completionEnd);
                        }
                    }
                }, c);
                break;

            case R.id.activity_more_completion_end:
                DateDialog.showDateDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        completionEnd = Calendar.getInstance();
                        completionEnd.set(year, month, dayOfMonth);
                        textDatecompletionEnd.setText("" + year + "-" + (month + 1) + "-" + dayOfMonth);
                        if (null != more) {
                            more.getCompletionDate(completionBegin, completionEnd);
                        }
                    }
                }, c);
                break;


            case R.id.activity_more_opendate_begin:
                DateDialog.showDateDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        openBegin = Calendar.getInstance();
                        openBegin.set(year, month, dayOfMonth);
                        textDateOpenDateBegin.setText("" + year + "-" + (month + 1) + "-" + dayOfMonth);

                        if (null != more) {
                            more.getOpenDate(openBegin, openEnd);
                        }
                    }
                }, c);

                break;

            case R.id.activity_more_opendate_end:
                DateDialog.showDateDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        openEnd = Calendar.getInstance();
                        openEnd.set(year, month, dayOfMonth);
                        textDateOpenDateEnd.setText("" + year + "-" + (month + 1) + "-" + dayOfMonth);

                        if (null != more) {
                            more.getOpenDate(openBegin, openEnd);
                        }
                    }
                }, c);
                break;

            case R.id.activity_more_status_start_layout:
                DialogFactory.newInstance(STATUS, new DialogFactory.IGetClickItem() {
                    @Override
                    public void getClickItem(DialogFragment dialog, String... items) {
                        statusBgeins = items;
                        if (null != more) {
                            more.getStatus(statusBgeins, statusEnds);
                        }
                        dialog.dismiss();
                    }
                }).show(getSupportFragmentManager(), "");
                break;

            case R.id.activity_more_status_end_layout:
                DialogFactory.newInstance(STATUS, new DialogFactory.IGetClickItem() {
                    @Override
                    public void getClickItem(DialogFragment dialog, String... items) {
                        statusEnds = items;
                        if (null != more) {
                            more.getStatus(statusBgeins, statusEnds);
                        }
                        dialog.dismiss();
                    }
                }).show(getSupportFragmentManager(), "");
                break;


            default:
                break;
        }

    }

    public interface IMoreActivity {
        void getCompletionDate(Calendar start, Calendar end);

        void getOpenDate(Calendar start, Calendar end);

        void getStatus(String[] start, String[] end);

        void getSSD(String... ssd);

        void getInterval(String... intervals);

        void getDirection(String... directions);

        void getTag(String... tags);
    }
}
