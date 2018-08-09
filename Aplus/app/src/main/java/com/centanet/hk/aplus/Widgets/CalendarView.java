package com.centanet.hk.aplus.Widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Created by yangzm4 on 2018/7/18.
 */

public class CalendarView extends ScrollView {

    private Context context;
    private LinearLayout content;

    public CalendarView(Context context) {
        this(context, null);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    private void initView() {
        content = new LinearLayout(context);
        this.addView(content);
    }





}
