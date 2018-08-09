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

public class TagFragment extends SysItemFragment {

    private OnTagChangeLisenter onStatuChangeLisenter;

    public static TagFragment newInstance(List<SystemParamItems> argument, List<String> selectList) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARGUMENT, (Serializable) argument);
        bundle.putStringArrayList(SELECTLIST, (ArrayList<String>) selectList);
        TagFragment contentFragment = new TagFragment();
        contentFragment.setArguments(bundle);
        return contentFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTagChangeLisenter) {
            onStatuChangeLisenter = (OnTagChangeLisenter) context;
        } else
            throw new IllegalArgumentException("Activity  Must Implements StatuFragment.OnStatuChangeLisenter");
    }

    @Override
    protected void oniTtemClick(boolean checked, int position, List<SystemParamItems> datas, List<String> selects) {
        super.oniTtemClick(checked, position, datas, selects);
        if (checked) selects.add(datas.get(position).getItemValue());
        else selects.remove(datas.get(position).getItemValue());

        if (onStatuChangeLisenter != null) onStatuChangeLisenter.onTagChange(selects);
    }

    public interface OnTagChangeLisenter {
        void onTagChange(List<String> tags);
    }


}
