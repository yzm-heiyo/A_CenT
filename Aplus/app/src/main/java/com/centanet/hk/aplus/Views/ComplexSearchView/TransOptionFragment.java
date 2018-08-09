package com.centanet.hk.aplus.Views.ComplexSearchView;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.centanet.hk.aplus.Views.basic.BaseFragment;

import java.io.Serializable;

/**
 * Created by yangzm4 on 2018/8/9.
 */

public class TransOptionFragment extends BaseFragment {


    public static String OPTION = "OPTION";
    private View transferred, confirmed, oStatus, corporationTransferre, dorporationTransferre, sorporationTransferre;
    private OnOptionChangeLisenter onOptionChangeLisenter;

    public static TransOptionFragment newInstance(Option option) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(OPTION, option);
        TransOptionFragment transOptionFragment = new TransOptionFragment();
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



    public static class Option implements Serializable {
        boolean isTransferred;
        boolean isConfirmed;
        boolean isOStatus;
        boolean isCorporationTransferre;
        boolean isDorporationTransferre;
        boolean isSorporationTransferre;
    }


    public interface OnOptionChangeLisenter {
        void onOptionChange(OptionFragment.Option option);
    }

}
