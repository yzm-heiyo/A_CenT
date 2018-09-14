package com.centanet.hk.aplus.Views.ComplexSearchView;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Views.basic.BaseFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangzm4 on 2018/8/9.
 */

public class TranTypeFragment extends BaseFragment implements View.OnClickListener {

    public static String TRANSTYPE = "TRANSTYPE";
    private OnTranTypeChangeLisenter onTranTypeChangeLisenter;
    private String tranTyp = "";
    private List<String> trans;

    public static TranTypeFragment newInstance(String option) {
        Bundle bundle = new Bundle();
        bundle.putString(TRANSTYPE, option);
        TranTypeFragment transOptionFragment = new TranTypeFragment();
        transOptionFragment.setArguments(bundle);
        return transOptionFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTranTypeChangeLisenter) {
            onTranTypeChangeLisenter = (OnTranTypeChangeLisenter) context;
        } else
            throw new IllegalArgumentException("Activity  Must Implements OptionFragment.OnOptionChangeLisenter");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transtype, null, false);
        init();
        initView(view);
        initLisenter();
        reCoverView(tranTyp);

        return view;
    }

    private void reCoverView(String tranType) {

        centaSale.setSelected(tranType.indexOf("1") != -1 ? true : false);
        centaRent.setSelected(tranType.indexOf("2") != -1 ? true : false);
        otherSale.setSelected(tranType.indexOf("3") != -1 ? true : false);

        otherRent.setSelected(tranType.indexOf("4") != -1 ? true : false);
        internal.setSelected(tranType.indexOf("5") != -1 ? true : false);

    }

    private void initLisenter() {

        centaSale.setOnClickListener(this);
        centaRent.setOnClickListener(this);
        otherSale.setOnClickListener(this);

        otherRent.setOnClickListener(this);
        internal.setOnClickListener(this);
    }


    private void initView(View view) {

        centaSale = view.findViewById(R.id.type_view_centasale);
        centaRent = view.findViewById(R.id.type_view_centarent);
        otherSale = view.findViewById(R.id.type_view_othersale);

        otherRent = view.findViewById(R.id.type_view_otherrent);
        internal = view.findViewById(R.id.type_view_internal);

    }

    private void init() {
        tranTyp = getArguments().getString(TRANSTYPE);
        if (tranTyp == null) tranTyp = new String();

        trans = new ArrayList<>();
        if (tranTyp != null && !tranTyp.equals(""))
            changeToList(tranTyp);
    }

    private void changeToList(String tranTyp) {
        tranTyp = tranTyp.replace(" ", "");
        String[] tranStr = tranTyp.split(",");
        for (int i = 0; i < tranStr.length; i++) {
            trans.add(tranStr[i]);
        }
    }

    private View centaSale, centaRent, otherSale, otherRent, internal;

    @Override
    public void onClick(View v) {

        L.d("TransDate", trans.toString());
        v.setSelected(!v.isSelected());
        switch (v.getId()) {
            case R.id.type_view_centasale:
                if (v.isSelected()) {
                    trans.add("1");
                } else trans.remove("1");
                break;
            case R.id.type_view_centarent:
                if (v.isSelected()) {
                    trans.add("2");
                } else trans.remove("2");
                break;
            case R.id.type_view_othersale:
                if (v.isSelected()) {
                    trans.add("3");
                } else trans.remove("3");
                break;
            case R.id.type_view_otherrent:
                if (v.isSelected()) {
                    trans.add("4");
                } else trans.remove("4");
                break;
            case R.id.type_view_internal:
                if (v.isSelected()) {
                    trans.add("5");
                } else trans.remove("5");
                break;
        }
        if (onTranTypeChangeLisenter != null)
            onTranTypeChangeLisenter.onTranTypeChange(trans.toString().replace("[", "").replace("]", ""));
    }


    public static class TranType implements Serializable {
        boolean isCentaSale;
        boolean isCentaRent;
        boolean isOtherSale;
        boolean isOtherRent;
        boolean isInternal;
    }


    public interface OnTranTypeChangeLisenter {
        void onTranTypeChange(String option);
    }

}
