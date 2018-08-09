package com.centanet.hk.aplus.Views.ComplexSearchView;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Views.basic.BaseFragment;
import com.centanet.hk.aplus.bean.params.SystemParamItems;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yangzm4 on 2018/7/25.
 */

public class OptionFragment extends BaseFragment implements View.OnClickListener {

    public static String OPTION = "OPTION";
    private View carplace, congreen, greenPrice, exclusive, key, hotlist, nossd, attorney, pending, oproperty, unconfirm;
    private LinearLayout content;
    private Option option;
    private OnOptionChangeLisenter changeLisenter;

    public static OptionFragment newInstance(Option option) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(OPTION, option);
        OptionFragment contentFragment = new OptionFragment();
        contentFragment.setArguments(bundle);
        return contentFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnOptionChangeLisenter) {
            changeLisenter = (OnOptionChangeLisenter) context;
        } else
            throw new IllegalArgumentException("Activity  Must Implements OptionFragment.OnOptionChangeLisenter");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_option, null, false);
        init();
        initView(view);
        initLisenter();
        reCoverView(option);

        return view;
    }

    private void init() {
        option = (Option) getArguments().get(OPTION);
        if (option == null) option = new Option();
    }

    private void initLisenter() {

        carplace.setOnClickListener(this);
        congreen.setOnClickListener(this);
        greenPrice.setOnClickListener(this);
        exclusive.setOnClickListener(this);
        key.setOnClickListener(this);
        hotlist.setOnClickListener(this);
        nossd.setOnClickListener(this);
        attorney.setOnClickListener(this);
        pending.setOnClickListener(this);
        oproperty.setOnClickListener(this);
        unconfirm.setOnClickListener(this);

    }

    private void initView(View view) {

        carplace = view.findViewById(R.id.option_view_carplace);
        congreen = view.findViewById(R.id.option_view_congreenprice);
        greenPrice = view.findViewById(R.id.option_view_greenprice);
        exclusive = view.findViewById(R.id.option_view_exclusive);

        key = view.findViewById(R.id.option_view_key);
        hotlist = view.findViewById(R.id.option_view_hotlist);
        nossd = view.findViewById(R.id.option_view_nossd);

        attorney = view.findViewById(R.id.option_view_powerofattorney);
        pending = view.findViewById(R.id.option_view_pending);
        oproperty = view.findViewById(R.id.option_view_operperty);
        unconfirm = view.findViewById(R.id.option_view_unconfirmtran);

        content = view.findViewById(R.id.option_ll_content);

    }

    private void reCoverView(Option option) {
        carplace.setSelected(option.hasParkingNumber);
        congreen.setSelected(option.hasSalePricePremiumUnpaid);
        greenPrice.setSelected(option.isShowSalePricePremiumUnpaid);
        hotlist.setSelected(option.isHotlist);
        key.setSelected(option.hasPropertyKey);
        exclusive.setSelected(option.isOnlyTrust);
        nossd.setSelected(option.isNoneSSD);
        oproperty.setSelected(option.hasOptout);

        unconfirm.setSelected(option.hasConfirmTransaction);
        attorney.setSelected(option.isProxy);
        pending.setSelected(option.hasDevelopmentEndCredits);
    }


    @Override
    public void onClick(View v) {

        v.setSelected(!v.isSelected());
        L.d("OnOptionClick", v.isSelected() + "");
        switch (v.getId()) {
            case R.id.option_view_carplace:
                option.hasParkingNumber = v.isSelected();
                L.d("option_view_carplace", option.hasParkingNumber + "");

                break;
            case R.id.option_view_congreenprice:
                option.hasSalePricePremiumUnpaid = v.isSelected();
                L.d("option_view_congreenprice", option.hasSalePricePremiumUnpaid + "");

                break;
            case R.id.option_view_greenprice:
                option.isShowSalePricePremiumUnpaid = v.isSelected();
                L.d("option_view_greenprice", v.isSelected() + "");

                break;
            case R.id.option_view_exclusive:
                option.isOnlyTrust = v.isSelected();
                L.d("option_view_exclusive", v.isSelected() + "");

                break;
            case R.id.option_view_key:
                option.hasPropertyKey = v.isSelected();
                L.d("option_view_key", v.isSelected() + "");

                break;
            case R.id.option_view_hotlist:
                option.isHotlist = v.isSelected();
                L.d("option_view_hotlist", v.isSelected() + "");

                break;
            case R.id.option_view_nossd:
                option.isNoneSSD = v.isSelected();
                L.d("option_view_nossd", v.isSelected() + "");

                break;
            case R.id.option_view_powerofattorney:
                option.isProxy = v.isSelected();
                L.d("option_view_powerofattorney", v.isSelected() + "");

                break;
            case R.id.option_view_pending:
                option.hasDevelopmentEndCredits = v.isSelected();
                L.d("option_view_pending", v.isSelected() + "");

                break;
            case R.id.option_view_operperty:
                option.hasOptout = v.isSelected();
                L.d("option_view_operperty", v.isSelected() + "");

                break;
            case R.id.option_view_unconfirmtran:
                option.hasConfirmTransaction = v.isSelected();
                L.d("option_view_unconfirmtran", v.isSelected() + "");

                break;
            case R.id.option_ll_content:
//                option.HasParkingNumber = v.isSelected();
                break;
        }
        L.d("Option", option.toString());
        if (changeLisenter != null)
            changeLisenter.onOptionChange(option);
    }

    public interface OnOptionChangeLisenter {
        void onOptionChange(Option option);
    }

    public class Option implements Serializable {
        boolean hasParkingNumber;
        boolean hasSalePricePremiumUnpaid;
        boolean isShowSalePricePremiumUnpaid;
        boolean isOnlyTrust;
        boolean hasPropertyKey;
        boolean isHotlist;
        boolean isNoneSSD;
        boolean hasConfirmTransaction;
        boolean isProxy;
        boolean hasDevelopmentEndCredits;
        boolean hasOptout;

        @Override
        public String toString() {
            return "Option{" +
                    "hasParkingNumber=" + hasParkingNumber +
                    ", hasSalePricePremiumUnpaid=" + hasSalePricePremiumUnpaid +
                    ", isShowSalePricePremiumUnpaid=" + isShowSalePricePremiumUnpaid +
                    ", isOnlyTrust=" + isOnlyTrust +
                    ", hasPropertyKey=" + hasPropertyKey +
                    ", isHotlist=" + isHotlist +
                    ", isNoneSSD=" + isNoneSSD +
                    ", hasConfirmTransaction=" + hasConfirmTransaction +
                    ", isProxy=" + isProxy +
                    ", hasDevelopmentEndCredits=" + hasDevelopmentEndCredits +
                    ", hasOptout=" + hasOptout +
                    '}';
        }
    }

}
