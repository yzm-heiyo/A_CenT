package com.centanet.hk.aplus.Widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;

/**
 * Created by yangzm4 on 2018/7/18.
 */

public class CalendarListView extends ScrollView {

    private Context context;
    private LinearLayout content;
    //    private Calendar calendar;
//    private int currentYear;
    private Calendar startCalendar;
    private Calendar endCalendar;

    private OnItemClickLisenter onItemClickLisenter;

    public CalendarListView(Context context) {
        this(context, null);
    }

    public CalendarListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    private void initView() {
        content = new LinearLayout(context);
        content.setOrientation(LinearLayout.VERTICAL);
        this.addView(content);

        addCanlendar(Calendar.getInstance());
    }

    public void addCanlendar(Calendar instance) {
        int currentYear = instance.get(Calendar.YEAR);
        for (int i = 0; i < 12; i++) {
            Calendar time = Calendar.getInstance();
            time.set(Calendar.YEAR, currentYear);
            time.set(Calendar.MONTH, 0);//todo 直接set Month会有错误 原因暂时未知 有待研究
            time.add(Calendar.MONTH, i);
            L.d("addCanlendar", "TrueMonth: " + i + " CurrentMonth: " + time.get(Calendar.MONTH));
            View view = LayoutInflater.from(context).inflate(R.layout.calendar, null, false);
            TextView month = view.findViewById(R.id.calendar_txt_month);
            month.setText(getMonthStr(time.getTime()));
            L.d("addCanlendar", getMonthStr(time.getTime()));
            MyCalendarView calendarView = view.findViewById(R.id.calendar);
            calendarView.setEndDay(startCalendar);
            calendarView.setEndDay(endCalendar);
            calendarView.setCalendarData(time);
            calendarView.setOnItemClickLisenter(new MyCalendarView.OnItemClickLisenter() {
                @Override
                public void onClick(Calendar calendar, int year, int month, int day) {
                    Log.d(TAG, "onClick: " + getMonthStr(calendar.getTime()));
                    if (startCalendar != null)
                        Log.d(TAG, "onClick: " + getMonthStr(startCalendar.getTime()));

                    if (startCalendar == null) startCalendar = calendar;
                    else if (startCalendar.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) && (startCalendar.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)) && (startCalendar.get(Calendar
                            .DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH))) { //等于表示取消选中，秒数会变，所以使用equare无效
                        startCalendar = endCalendar;
                        endCalendar = null;
                    } else if (endCalendar != null && endCalendar.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) && (endCalendar.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)) && (endCalendar.get(Calendar
                            .DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH))) {//等于表示取消选中，所以使用equare无效
                        endCalendar = null;
                    } else if (startCalendar.after(calendar)) {
                        endCalendar = startCalendar;
                        startCalendar = calendar;
                    } else endCalendar = calendar;

                    if (onItemClickLisenter != null)
                        onItemClickLisenter.onClick(startCalendar, endCalendar);

                    resetSelectDay(startCalendar, endCalendar);
                }
            });
            content.addView(view);
        }
        resetSelectDay(startCalendar, endCalendar);
    }

    //刷新Calendar的开始和结束日期
    private void resetSelectDay(Calendar start, Calendar end) {
        int count = content.getChildCount();
        Log.d(TAG, "ContentSize: " + count);
        for (int i = 0; i < count; i++) {
            View view = content.getChildAt(i);
            if (view instanceof LinearLayout) {
                Log.d(TAG, "resetSelectDay: ");
                int viewCount = ((LinearLayout) view).getChildCount();
                for (int a = 0; a < viewCount; a++) {
                    View childView = ((LinearLayout) view).getChildAt(a);
                    if (childView instanceof MyCalendarView) {
                        ((MyCalendarView) childView).setStartDay(start);
                        ((MyCalendarView) childView).setEndDay(end);
                    }
                }
            }
        }
    }

    public void setOnItemClickLisenter(OnItemClickLisenter onItemClickLisenter) {
        this.onItemClickLisenter = onItemClickLisenter;
    }

    public void setCalendar(Calendar calendar) {
        content.removeAllViews();
        addCanlendar(calendar);
    }

    public void setStartCalendar(Calendar startCalendar) {
        this.startCalendar = startCalendar;
        resetSelectDay(startCalendar, endCalendar);
    }

    public void setEndCalendar(Calendar endCalendar) {
        this.endCalendar = endCalendar;
        resetSelectDay(startCalendar, endCalendar);
    }


    /**
     * 获取月份标题
     */
    private String getMonthStr(Date month) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月");
        return df.format(month);
    }

    public interface OnItemClickLisenter {
        void onClick(Calendar start, Calendar end);
    }

}
