package com.centanet.hk.aplus.Views.HousetListView.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Views.basic.BaseFragment;

/**
 * Created by yangzm4 on 2018/7/24.
 */

public class PropertyFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_property,container,false);
        return view;
    }
}
