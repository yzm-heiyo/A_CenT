package com.centanet.hk.aplus.Views.HouseDetailView.view;

import android.widget.Toast;

import com.centanet.hk.aplus.Views.HouseDetailView.view.FollowFragmentAbst;

import java.util.Calendar;

/**
 * Created by mzh1608258 on 2018/1/4.
 */

public class FollowFragment extends FollowFragmentAbst {


    @Override
    protected IFollowFragment setFollow() {
        return new IFollowFragment() {
            @Override
            public void selectDate(Calendar start, Calendar end) {
                Toast.makeText(getContext(), date(start, end), Toast.LENGTH_SHORT).show();
            }
        };
    }

    private String date(Calendar start, Calendar end) {
        return "" + start.get(Calendar.YEAR) + "-" + (start.get(Calendar.MONTH) + 1) + "-" + start.get(Calendar.DAY_OF_MONTH)
                + "------"
                + end.get(Calendar.YEAR) + "-" + (end.get(Calendar.MONTH) + 1) + "-" + end.get(Calendar.DAY_OF_MONTH);

    }
}
