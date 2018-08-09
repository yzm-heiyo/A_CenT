package com.centanet.hk.aplus.Widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.DensityUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;

/**
 * Created by yangzm4 on 2018/6/21.
 */

public class MyCalendarView extends View {

    private int currentDay;
    private int currentYear;
    private int currentMonth;
    private int todayWeekIndex;
    private int lineNum;


    private int sumOfMonth;
    private int firstIndex;
    private int firstLineSum;
    private int lastLineNum;
    private Date month;
    private int itemWidth;
    private int lineHeight = DensityUtil.dip2px(getContext(), 20);

    private int textSize;

    private Paint dayPaint;
    private Paint monthPaint;
    private Paint selectPaint;
    private int leftAndRightSpace;
    private int lineSpace;

    private int startDay;
    private int endDay;
    private int selectDay;

    private int selectYear;
    private int selectMonth;

    private Calendar selectStartDay;
    private Calendar selectEndDay;

    private int clickSum = 0;

    private int[] selectStarEnd;

//    private Calendar calendar;

    private OnItemClickLisenter onItemClickLisenter;

    public MyCalendarView(Context context) {
        this(context, null);
    }

    public MyCalendarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date());
//        initCompute(calendar);
    }

    private void init() {

        dayPaint = new Paint();
        dayPaint.setColor(Color.GRAY);
        dayPaint.setAntiAlias(true);

        monthPaint = new Paint();
        monthPaint.setColor(getResources().getColor(R.color.calendar_week_gray));
        monthPaint.setAntiAlias(true);

        selectPaint = new Paint();
        selectPaint.setColor(Color.WHITE);
        selectPaint.setAntiAlias(true);

        textSize = DensityUtil.dip2px(getContext(), 16);

//        leftAndRightSpace = DensityUtil.dip2px(getContext(), 12);
        lineSpace = DensityUtil.dip2px(getContext(), 12);
    }

    public void setCalendarData(Calendar calendar) {
        initCompute(calendar);
    }

    private void initCompute(Calendar calendar) {

        //当前几号
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        currentMonth = calendar.get(Calendar.MONTH);
        currentYear = calendar.get(Calendar.YEAR);
        Log.d(TAG, "initCompute: currentMonth: " + currentMonth + " currentYear: " + currentYear);

        //获得当前星期几
        todayWeekIndex = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        //获得这个月的天数
        sumOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        Log.d(TAG, "initCompute: sumOfMonth: " + sumOfMonth);
        //获得月份1号是星期几
        firstIndex = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        Log.d(TAG, "initCompute: firstIndex: " + firstIndex);
        lineNum = 1;

        //第一行能展示的天数
        firstLineSum = 7 - firstIndex;

        int shengyu = sumOfMonth - firstLineSum;
        while (shengyu > 7) {
            lineNum++;
            shengyu -= 7;
        }

        if (shengyu > 0) {
            lineNum++;
            lastLineNum = shengyu;
        }

        Log.i(TAG, "" + "一共有" + sumOfMonth + "天,第一天的索引是：" + firstIndex + "   有" + lineNum +
                "行，第一行" + firstLineSum + "个，最后一行" + lastLineNum + "个");

        requestLayout();
        invalidate();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //宽度 = 填充父窗体
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);   //获取宽的尺寸
        itemWidth = widthSize / 7;
        lineHeight = widthSize / 7 - textSize - leftAndRightSpace;
        //高度 = 标题高度 + 星期高度 + 日期行数*每行高度
        float height = (lineNum * (lineHeight + textSize + lineSpace));

        Log.v(TAG, "标题高度：" + 10 + " 星期高度：" + 10 + " 每行高度：" + lineHeight +
                " 行数：" + lineNum + "  \n控件高度：" + height);

        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                (int) height + DensityUtil.dip2px(getContext(), 20));

    }


    @Override
    protected void onDraw(Canvas canvas) {
        drawMonth(canvas);
        drawDayAndPre(canvas);
    }

    private void drawMonth(Canvas canvas) {

    }

    private void drawDayAndPre(Canvas canvas) {

        int top = 0;

        for (int line = 0; line < lineNum; line++) {
            if (line == 0) {
                //第一行
                top = top + lineSpace;
                drawDayAndPre(canvas, top, firstLineSum, 0, firstIndex);
                //todo 20 就是行间距
                top = top + lineSpace + textSize;
            } else if (line == lineNum - 1) {
                //最后一行
                top += lineHeight;
                drawDayAndPre(canvas, top, lastLineNum, firstLineSum + (line - 1) * 7, 0);
            } else {
                //满行
                top += lineHeight;
                drawDayAndPre(canvas, top, 7, firstLineSum + (line - 1) * 7, 0);
                top = top + lineSpace + textSize;
            }
        }

    }


    @SuppressLint("ResourceAsColor")
    private void drawDayAndPre(Canvas canvas, float top, int firstLineSum, int i, int firstIndex) {
        //Rect可以用来控制日历的背景色
//        Rect rect = new Rect(0, (int) top, (int) getWidth(), (int) top + lineHeight);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(getResources().getColor(R.color.colortheme));

        Paint bgpaint = new Paint();
        bgpaint.setAntiAlias(true);
        bgpaint.setColor(Color.argb(20, 187, 46, 45));

        Paint textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(textSize);

        textPaint.setTextAlign(Paint.Align.CENTER);

        int firstLineX = firstIndex * itemWidth;


        for (int sum = 0; sum < firstLineSum; sum++) {
            int left = firstLineX + itemWidth * sum + +leftAndRightSpace / 2;
            int right = firstLineX + itemWidth * (sum + 1) - leftAndRightSpace / 2;
            int num = i + 1 + sum;
            int x = (left + right) / 2;


            if (selectEndDay == null && selectStartDay != null) {
                if (currentYear == selectStartDay.get(Calendar.YEAR) && currentMonth == selectStartDay.get(Calendar.MONTH) && num == selectStartDay.get(Calendar.DAY_OF_MONTH)) {
                    textPaint.setColor(Color.WHITE);
                    canvas.drawCircle(x, top + (lineHeight + textSize) / 2, (lineHeight + textSize) / 2, paint);
                }
            }

            Rect rect = new Rect(left, (int) top, right, (int) top + lineHeight + textSize);


            if (selectStartDay != null && selectEndDay != null) {

                int startYear = selectStartDay.get(Calendar.YEAR);
                int startMonth = selectStartDay.get(Calendar.MONTH);
                int startDay = selectStartDay.get(Calendar.DAY_OF_MONTH);

                int endYear = selectEndDay.get(Calendar.YEAR);
                int endMonth = selectEndDay.get(Calendar.MONTH);
                int endDay = selectEndDay.get(Calendar.DAY_OF_MONTH);

                if (currentYear == startYear && currentMonth == startMonth && num == startDay) {
                    textPaint.setColor(Color.WHITE);
                    canvas.drawRect(new Rect(left + itemWidth / 2, (int) top, right, (int) top + lineHeight + textSize), bgpaint);
                    canvas.drawCircle(x, top + (lineHeight + textSize) / 2, (lineHeight + textSize) / 2, paint);
                }

                if (currentYear == endYear && currentMonth == endMonth && num == endDay) {
                    textPaint.setColor(Color.WHITE);
                    canvas.drawRect(new Rect(left, (int) top, right - itemWidth / 2, (int) top + lineHeight + textSize), bgpaint);
                    canvas.drawCircle(x, top + (lineHeight + textSize) / 2, (lineHeight + textSize) / 2, paint);
                }

                if (currentYear > startYear && currentYear < endYear) {
                    canvas.drawRect(rect, bgpaint);
                }

                if (currentYear == startYear && currentYear == endYear) {

                    if (currentMonth > startMonth && currentMonth < endMonth) {
                        canvas.drawRect(rect, bgpaint);
                    }
                    if (currentMonth == startMonth && currentMonth == endMonth) {
                        if (num > startDay && num < endDay)
                            canvas.drawRect(rect, bgpaint);
                    } else if (currentMonth == startMonth) {
                        if (num > startDay)
                            canvas.drawRect(rect, bgpaint);
                    } else if (currentMonth == endMonth) {
                        if (num < endDay)
                            canvas.drawRect(rect, bgpaint);
                    }
                } else if (currentYear == startYear) {
                    if (currentMonth > startMonth)
                        canvas.drawRect(rect, bgpaint);
                    if (currentMonth == startMonth) {
                        if (num > startDay)
                            canvas.drawRect(rect, bgpaint);
                    }

                } else if (currentYear == endYear) {
                    if (currentMonth < endMonth)
                        canvas.drawRect(rect, bgpaint);
                    if (currentMonth == endMonth) {
                        if (num < endDay)
                            canvas.drawRect(rect, bgpaint);
                    }
                }
            }

            float basicLine = rect.centerY() + (textPaint.descent() - textPaint.ascent()) / 2 - textPaint.descent();

            canvas.drawText(num + "", rect.centerX(), basicLine, textPaint);

            textPaint.setColor(Color.BLACK);

        }
    }

    private PointF point = new PointF();

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                point.set(event.getX(), event.getY());
                setSelectDay(point);
                break;
            case MotionEvent.ACTION_MOVE:
//                point.set(event.getX(), event.getY());
//                setSelectDay(point);
                break;
            case MotionEvent.ACTION_UP:
//                point.set(event.getX(), event.getY());
//                setSelectDay(point);
                break;
        }
        return true;
    }

    public void setOnItemClickLisenter(OnItemClickLisenter onItemClickLisenter) {
        this.onItemClickLisenter = onItemClickLisenter;
    }

    private void setSelectDay(PointF point) {
        int line = (int) (point.y / (lineSpace + lineHeight + textSize)) + 1;
        int row = (int) (point.x / itemWidth) + 1;
        Log.d(TAG, "setSelectDay " + "line: " + line + " row: " + row);
        if (line == 1 && row < firstIndex) {
            Log.d(TAG, "setSelectDay: " + "无效");
            return;
        }
        if (line == lineNum && row > lastLineNum) {
            Log.d(TAG, "setSelectDay: " + "无效");
            return;
        }

        if (line > lineNum) {
            Log.d(TAG, "setSelectDay: " + "无效");
            return;
        }

        Log.d(TAG, "lastLineNum: " + lastLineNum + " lineNum: " + lineNum);

        selectDay = (line - 1) * 7 + row - firstIndex;


        clickSum++;

        if (onItemClickLisenter != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(currentYear, currentMonth, selectDay);
            onItemClickLisenter.onClick(calendar, currentYear, currentMonth, selectDay);
        }

        Log.d(TAG, "setSelectDay: " + selectDay);
        invalidate();
    }

    public void setStartDay(int startDay) {
        this.startDay = startDay;
        invalidate();
    }

    public void setEndDay(int endDay) {
        this.endDay = endDay;
        invalidate();
    }

    public void setStartDay(Calendar startDay) {
        selectStartDay = startDay;
        invalidate();
    }

    public void setEndDay(Calendar endDay) {
        selectEndDay = endDay;
        invalidate();
    }


    /**
     * 获取月份标题
     */
    private String getMonthStr(Date month) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月");
        return df.format(month);
    }

    private Date str2Date(String str) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月");
            return df.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public interface OnItemClickLisenter {
        void onClick(Calendar calendar, int year, int month, int day);
    }

}
