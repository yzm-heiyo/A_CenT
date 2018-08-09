package com.centanet.hk.aplus.Views.ComplexSearchView;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Views.basic.BaseFragment;
import com.centanet.hk.aplus.Views.basic.BaseRecycleAdapter;
import com.centanet.hk.aplus.Views.basic.BaseViewHolder;
import com.centanet.hk.aplus.bean.params.SystemParamItems;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yangzm4 on 2018/7/24.
 */

public class SSDFragment extends BaseFragment implements View.OnClickListener {

    public static String ARGUMENT = "SysItemFragment";
    private View ssd, ssd10, ssd15, ssd20, unknow;
    private LinearLayout content;
    private String ssdStr;
    private OnSSDChangeLisenter onSSDChangeLisenter;


    public static SSDFragment newInstance(String ssd) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT, ssd);
        SSDFragment contentFragment = new SSDFragment();
        contentFragment.setArguments(bundle);
        return contentFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSSDChangeLisenter) {
            onSSDChangeLisenter = (OnSSDChangeLisenter) context;
        } else
            throw new IllegalArgumentException("Activity  Must Implements SSDFragment.OnSSDChangeLisenter");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more_ssd, null, false);
        initView(view);
        init();
        initLisenter();
        return view;
    }

    private void init() {
        ssdStr = getArguments().getString(ARGUMENT);
        if (ssdStr != null) {
            switch (ssdStr) {
                case "0":
                    ssd.setSelected(true);
                    break;
                case "10":
                    ssd10.setSelected(true);
                    break;
                case "15":
                    ssd15.setSelected(true);
                    break;
                case "20":
                    ssd20.setSelected(true);
                    break;
                case "":
                    unknow.setSelected(true);
                    break;
            }
        }
    }

    private void initLisenter() {

        ssd.setOnClickListener(this);
        ssd10.setOnClickListener(this);
        ssd15.setOnClickListener(this);
        ssd20.setOnClickListener(this);
        unknow.setOnClickListener(this);
    }

    private void initView(View view) {
        ssd = view.findViewById(R.id.ssd_view_0);
        ssd10 = view.findViewById(R.id.ssd_view_10);
        ssd15 = view.findViewById(R.id.ssd_view_15);
        ssd20 = view.findViewById(R.id.ssd_view_20);
        unknow = view.findViewById(R.id.ssd_view_unknow);
        content = view.findViewById(R.id.ssd_ll_content);
    }

    private void resetView() {
        int count = content.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = content.getChildAt(i);
            if (view instanceof LinearLayout)
                view.setSelected(false);
        }
    }

    @Override
    public void onClick(View v) {
        resetView();
        v.setSelected(true);
        String ssd = null;
        switch (v.getId()) {
            case R.id.ssd_view_0:
                ssd = "0";
                break;
            case R.id.ssd_view_10:
                ssd = "10";
                break;
            case R.id.ssd_view_15:
                ssd = "15";
                break;
            case R.id.ssd_view_20:
                ssd = "20";
                break;
            case R.id.ssd_view_unknow:
                ssd = "";
                break;
        }
        if (onSSDChangeLisenter != null) onSSDChangeLisenter.onSSDChange(ssd);
    }

    public interface OnSSDChangeLisenter {
        void onSSDChange(String ssd);
    }

}
