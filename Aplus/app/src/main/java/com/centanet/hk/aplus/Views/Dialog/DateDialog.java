package com.centanet.hk.aplus.Views.Dialog;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by mzh1608258 on 2018/1/8.
 */

public class DateDialog {


    public static void showDateDialog(Context context, DatePickerDialog.OnDateSetListener listener, Calendar calendar) {
        new DatePickerDialog(context, listener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
        ).show();
    }


}
