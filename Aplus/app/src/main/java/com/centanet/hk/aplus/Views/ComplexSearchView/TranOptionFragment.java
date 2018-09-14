package com.centanet.hk.aplus.Views.ComplexSearchView;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Views.basic.BaseFragment;

import java.io.Serializable;

/**
 * Created by yangzm4 on 2018/8/9.
 */

public class TranOptionFragment extends BaseFragment implements View.OnClickListener {


    public static String OPTION = "OPTION";
    private OnOptionChangeLisenter onOptionChangeLisenter;
    private Option option;

    public static TranOptionFragment newInstance(Option option) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(OPTION, option);
        TranOptionFragment transOptionFragment = new TranOptionFragment();
        transOptionFragment.setArguments(bundle);
        return transOptionFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnOptionChangeLisenter) {
            onOptionChangeLisenter = (OnOptionChangeLisenter) context;
        } else
            throw new IllegalArgumentException("Activity  Must Implements OptionFragment.OnOptionChangeLisenter");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trans_option, null, false);
        init();
        initView(view);
        initLisenter();
        reCoverView(option);

        return view;
    }

    private void reCoverView(Option option) {

        transferred.setSelected(option.isTransferred);
        confirmed.setSelected(option.isConfirmed);
        oStatus.setSelected(option.isOStatus);

        corporationTransferre.setSelected(option.isCorporationTransferre);
        dorporationTransferre.setSelected(option.isDorporationTransferre);
        sorporationTransferre.setSelected(option.isSorporationTransferre);

    }

    private void initLisenter() {

        transferred.setOnClickListener(this);
        confirmed.setOnClickListener(this);
        oStatus.setOnClickListener(this);

        corporationTransferre.setOnClickListener(this);
        dorporationTransferre.setOnClickListener(this);
        sorporationTransferre.setOnClickListener(this);

    }


    private void initView(View view) {

        transferred = view.findViewById(R.id.option_view_transferred);
        confirmed = view.findViewById(R.id.option_view_confirmed);
        oStatus = view.findViewById(R.id.option_view_oproperty);

        corporationTransferre = view.findViewById(R.id.option_view_corporationTransferre);
        dorporationTransferre = view.findViewById(R.id.option_view_developmentEndCredits);
        sorporationTransferre = view.findViewById(R.id.option_view_green_price);

    }

    private void init() {
        option = (Option) getArguments().get(OPTION);
        if (option == null) option = new Option();
    }

    private View transferred, confirmed, oStatus, corporationTransferre, dorporationTransferre, sorporationTransferre;

    @Override
    public void onClick(View v) {

        v.setSelected(!v.isSelected());

        switch (v.getId()) {
            case R.id.option_view_transferred:
                option.isTransferred = v.isSelected();
                break;
            case R.id.option_view_confirmed:
                option.isConfirmed = v.isSelected();
                break;
            case R.id.option_view_oproperty:
                option.isOStatus = v.isSelected();
                break;
            case R.id.option_view_corporationTransferre:
                option.isCorporationTransferre = v.isSelected();
                break;
            case R.id.option_view_developmentEndCredits:
                option.isDorporationTransferre = v.isSelected();
                break;
            case R.id.option_view_green_price:
                option.isSorporationTransferre = v.isSelected();
                break;
        }
        if (onOptionChangeLisenter != null) onOptionChangeLisenter.onTransOptionChange(option);
    }


    public static class Option implements Serializable {
        boolean isTransferred;
        boolean isConfirmed;
        boolean isOStatus;
        boolean isCorporationTransferre;
        boolean isDorporationTransferre;
        boolean isSorporationTransferre;

        @Override
        public String toString() {
            return "Option{" +
                    "isTransferred=" + isTransferred +
                    ", isConfirmed=" + isConfirmed +
                    ", isOStatus=" + isOStatus +
                    ", isCorporationTransferre=" + isCorporationTransferre +
                    ", isDorporationTransferre=" + isDorporationTransferre +
                    ", isSorporationTransferre=" + isSorporationTransferre +
                    '}';
        }
    }


    public interface OnOptionChangeLisenter {
        void onTransOptionChange(Option option);
    }

}
