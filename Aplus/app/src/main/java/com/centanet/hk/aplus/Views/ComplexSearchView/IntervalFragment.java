package com.centanet.hk.aplus.Views.ComplexSearchView;

import android.content.Context;
import android.os.Bundle;

import com.centanet.hk.aplus.bean.params.SystemParamItems;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangzm4 on 2018/7/26.
 */

public class IntervalFragment extends SysItemFragment {

    private OnIntervalChangeLisenter onStatuChangeLisenter;

    public static IntervalFragment newInstance(List<SystemParamItems> argument, List<String> selectList) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARGUMENT, (Serializable) argument);
        bundle.putStringArrayList(SELECTLIST, (ArrayList<String>) selectList);
        IntervalFragment contentFragment = new IntervalFragment();
        contentFragment.setArguments(bundle);
        return contentFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnIntervalChangeLisenter) {
            onStatuChangeLisenter = (OnIntervalChangeLisenter) context;
        } else
            throw new IllegalArgumentException("Activity  Must Implements StatuFragment.OnStatuChangeLisenter");
    }

    @Override
    protected void oniTtemClick(boolean checked, int position, List<SystemParamItems> datas, List<String> selects) {
        super.oniTtemClick(checked, position, datas, selects);
        if (checked) selects.add(datas.get(position).getItemValue());
        else selects.remove(datas.get(position).getItemValue());

        if (onStatuChangeLisenter != null) onStatuChangeLisenter.onIntervalChange(selects);
    }

    public interface OnIntervalChangeLisenter {
        void onIntervalChange(List<String> intervals);
    }


}
