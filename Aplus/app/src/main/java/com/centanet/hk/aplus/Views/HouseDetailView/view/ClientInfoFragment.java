package com.centanet.hk.aplus.Views.HouseDetailView.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.entity.http.AHeaderDescription;
import com.centanet.hk.aplus.entity.http.TrustorDescription;

/**
 * Created by mzh1608258 on 2018/1/3.
 */

public class ClientInfoFragment extends Fragment {

    private View view;
    private ClientUpDateListener onUpdateListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ClientUpDateListener) {
            onUpdateListener = (ClientUpDateListener) context;
        } else {
            throw new IllegalArgumentException("activity must implements FragmentInteraction");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_ownerinfo, null);
        onUpdateListener.getClientInfo(HttpUtil.URL_TRUSTOR,new AHeaderDescription(),new TrustorDescription());
        return view;

    }

    public interface ClientUpDateListener{
        void getClientInfo(String address, AHeaderDescription AHeaderDescription, TrustorDescription trustorDescription);
    }
}
