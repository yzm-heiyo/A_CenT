package com.centanet.hk.aplus.Views.Unensure;

import android.view.View;
import android.widget.Toast;

import com.centanet.hk.aplus.Views.abst.MoreActivityAbst;

import java.util.Calendar;

/**
 * Created by mzh1608258 on 2018/1/8.
 */

public class MoreActivity extends MoreActivityAbst implements MoreActivityAbst.IMoreActivity {


    @Override
    protected IMoreActivity setIMoreActivity() {
        return this;
    }

    @Override
    public void resetParams(View v) {
        Toast.makeText(getApplicationContext(),"resetParams",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void search(View v) {
        Toast.makeText(getApplicationContext(),"search",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getCompletionDate(Calendar start, Calendar end) {

    }

    @Override
    public void getOpenDate(Calendar start, Calendar end) {

    }

    @Override
    public void getStatus(String[] start, String[] end) {

        StringBuilder builder = new StringBuilder();

        if (start != null) {
            for (int i = 0; i < start.length; i++) {
                builder.append(start[i] + ",");
            }
        }

        if (end != null) {
            for (int i = 0; i < end.length; i++) {
                builder.append(end[i] + ",");
            }
        }


        Toast.makeText(getApplicationContext(), builder.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getSSD(String... ssd) {
        Toast.makeText(getApplicationContext(), ssd[0], Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getInterval(String... intervals) {
        Toast.makeText(getApplicationContext(), intervals[0], Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getDirection(String... directions) {
        Toast.makeText(getApplicationContext(), directions[0], Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getTag(String... tags) {
        Toast.makeText(getApplicationContext(), tags[0], Toast.LENGTH_SHORT).show();
    }
}
