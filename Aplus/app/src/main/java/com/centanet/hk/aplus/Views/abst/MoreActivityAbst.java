package com.centanet.hk.aplus.Views.abst;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Views.Dialog.DateDialog;
import com.centanet.hk.aplus.Views.Dialog.DialogFactory;
import com.centanet.hk.aplus.Views.Dialog.OpenDataDialog;
import com.centanet.hk.aplus.Views.Dialog.StatusDialog;
import com.centanet.hk.aplus.Views.basic.BasicActivty;
import com.centanet.hk.aplus.Widgets.MyRadioGroup;
import com.centanet.hk.aplus.manager.ApplicationManager;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.centanet.hk.aplus.MyApplication.getContext;
import static com.centanet.hk.aplus.common.CommandField.DialogType.STATUS;

/**
 * Created by mzh1608258 on 2018/1/8.
 */

public abstract class MoreActivityAbst extends BasicActivty implements RadioGroup.OnCheckedChangeListener, View.OnClickListener, MyRadioGroup.OnCheckedChangeListener {

    private TextView textDate, textDatecompletionBegin, textDatecompletionEnd, textDateOpenDateBegin, textDateOpenDateEnd, statusBegin, statusEnd;
    private Calendar completionBegin, completionEnd, openBegin, openEnd;
    private String[] statusBgeins, statusEnds;
    private View statusBeginLayout, statusEndLayout;
    private IMoreActivity more;
    private int areaType = 0;
    private int priceType = 0;
    private int openDataTtpe = 0;
    private TextView dateTipTxt;
    private List<Integer> staBeginSelectList, staEndSelectList;
    private EditText areaLft, areaRight, priceLeft, priceRight;

    private MyRadioGroup ssdRG, IntervalRG, directionRG, tagRG;
    private RadioGroup priceRG, areaRG;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_complex);
        setViews();
        setListeners();
        init();
        more = setIMoreActivity();
    }

    private void init() {
        openDataTtpe = ApplicationManager.getOpenDataType();
        staBeginSelectList = new ArrayList<>();
        staEndSelectList = new ArrayList<>();
    }

    protected abstract IMoreActivity setIMoreActivity();

    //開盤日期
    public void openDate(View view) {
        OpenDataDialog openDataDialog = new OpenDataDialog(openDataTtpe);
        openDataDialog.setOnDialogClikeLisenter(new OpenDataDialog.onDialogOnclikeLisenter() {
            @Override
            public void onClike(Dialog dialog, int viewID, String result) {
                dialog.dismiss();
                openDataTtpe = viewID;
                dateTipTxt.setText(result);
                ApplicationManager.setOpenDataType(openDataTtpe);
            }
        });
        openDataDialog.show(getSupportFragmentManager(), "");
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
        tagRG = this.findViewById(R.id.activity_more_tag_radiogroup);
        priceRG = findViewById(R.id.complex_price_group);
        areaRG = findViewById(R.id.complex_area_group);
        priceRG.setOnCheckedChangeListener(this);
        areaRG.setOnCheckedChangeListener(this);
        dateTipTxt = findViewById(R.id.opendate_txt_date);

        IntervalRG = this.findViewById(R.id.activity_more_interval_radiogroup);
        directionRG = this.findViewById(R.id.activity_more_direction_radiogroup);

        priceLeft = findViewById(R.id.complex_edit_price_left);
        priceLeft.setInputType(InputType.TYPE_CLASS_NUMBER);
        priceRight = findViewById(R.id.complex_edit_price_right);
        priceRight.setInputType(InputType.TYPE_CLASS_NUMBER);
        areaLft = findViewById(R.id.complex_edit_area_left);
        areaLft.setInputType(InputType.TYPE_CLASS_NUMBER);
        areaRight = findViewById(R.id.complex_edit_price_right);
        areaRight.setInputType(InputType.TYPE_CLASS_NUMBER);
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
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.complex_rb_reallly_area:
                areaType = 1;
                cleanAreaText();
                break;
            case R.id.complex_rb_use_area:
                areaType = 0;
                cleanAreaText();
                break;
            case R.id.complex_rb_really_rent:
                priceType = 3;
                cleanPriceText();
                break;
            case R.id.complex_rb_really_sale:
                priceType = 2;
                cleanPriceText();
                break;
            case R.id.complex_rb_use_rent:
                priceType = 1;
                cleanPriceText();
                break;
            case R.id.complex_rb_use_sale:
                priceType = 0;
                cleanPriceText();
                break;
        }
        L.d("complex", "priceType: " + priceType + " areaType: " + areaType);
    }

    private void cleanAreaText() {
        areaLft.setText("");
        areaRight.setText("");
    }

    private void cleanPriceText() {
        priceLeft.setText("");
        priceRight.setText("");
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

        switch (v.getId()) {
            case R.id.activity_more_completion_begin:
                getDate(v.getId());
                break;

            case R.id.activity_more_completion_end:
                getDate(v.getId());
                break;

            case R.id.activity_more_opendate_begin:
                getDate(v.getId());
                break;

            case R.id.activity_more_opendate_end:
                getDate(v.getId());
                break;

            case R.id.activity_more_status_start_layout:
                showStatusBeginDialog();
                break;

            case R.id.activity_more_status_end_layout:
                showStatusEndDialog();

                break;


            default:
                break;
        }

    }

    private void showStatusEndDialog() {
        StatusDialog statusEndDialog = new StatusDialog(staEndSelectList);
        statusEndDialog.setOnDialogOnclikeLisenter(new StatusDialog.onDialogOnclikeLisenter() {
            @Override
            public void onClick(Dialog v, int viewID, List<Integer> viewList, String[] content) {
                v.dismiss();
                staEndSelectList = viewList;
            }
        });
        statusEndDialog.show(getSupportFragmentManager(), "");
    }

    private void showStatusBeginDialog() {

        StatusDialog statusBeginDialog = new StatusDialog(staBeginSelectList);
        statusBeginDialog.setOnDialogOnclikeLisenter(new StatusDialog.onDialogOnclikeLisenter() {
            @Override
            public void onClick(Dialog v, int viewID, List<Integer> viewList, String[] content) {
                v.dismiss();
                staBeginSelectList = viewList;
            }
        });
        statusBeginDialog.show(getSupportFragmentManager(), "");
    }


    //返回时间
    private void getDate(int viewID) {

        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(selectedDate.get(Calendar.YEAR) - 20, 0, 1);
        boolean[] showType = new boolean[]{true, false, false, false, false, false};

        if (viewID == R.id.activity_more_opendate_begin || viewID == R.id.activity_more_opendate_end)
            showType = new boolean[]{true, true, true, false, false, false};

        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调

            }

            private String getTime(Date date) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
            }
        })
                .setCancelText("取消")
                .setSubmitText("確定")
                .setTitleText("請選擇日期")
                .setLabel("", "", "", "时", "分", "秒")
                .setType(showType)
                .setRangDate(startDate, selectedDate)
                .isCenterLabel(false)
                .setContentSize(22)
                .setTitleBgColor(Color.WHITE)
                .setSubmitColor(getResources().getColor(R.color.colortheme))//确定按钮文字颜色
                .setCancelColor(Color.BLACK)//取消按钮文字颜色
                .build();
        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 給所有組建分發事件
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
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
