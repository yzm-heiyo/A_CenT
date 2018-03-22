package com.centanet.hk.aplus.Views.HouseDetailView.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.centanet.hk.aplus.MyApplication;
import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.DensityUtil;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.entity.detail.DetailTrustor;
import com.centanet.hk.aplus.entity.detail.Trustor;
import com.centanet.hk.aplus.entity.detail.TrustorDetail;
import com.centanet.hk.aplus.entity.http.AHeaderDescription;
import com.centanet.hk.aplus.entity.http.TrustorDescription;
import com.centanet.hk.aplus.eventbus.MessageEventBus;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.DetailRefreshView.DETAIL_CLIENT_INFO;

/**
 * Created by mzh1608258 on 2018/1/3.
 */

public class ClientInfoFragment extends Fragment {

    private View view;
    private ClientUpDateListener onUpdateListener;
    private AHeaderDescription headerDescription;
    private Context context;
    private LinearLayout ll_owners;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof ClientUpDateListener) {
            onUpdateListener = (ClientUpDateListener) context;
        } else {
            throw new IllegalArgumentException("activity must implements FragmentInteraction");
        }
        L.d("FRAGMENT", "onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        L.d("FRAGMENT", "onCreate");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEventBus messageEvent) {

        if (messageEvent.getMsg() == DETAIL_CLIENT_INFO) {
            DetailTrustor detailTrustor = (DetailTrustor) messageEvent.getObject();
            L.d("ClientInfo", detailTrustor.toString());
            if (detailTrustor.getTrustors() == null) return;

            addClientInfo(detailTrustor.getTrustors());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_clientsinfo, null);
        ll_owners = view.findViewById(R.id.info_ll_owners);
        headerDescription = ((MyApplication) getActivity().getApplication()).getHeaderDescription();

        return view;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (onUpdateListener != null)
                onUpdateListener.getClientInfo(HttpUtil.URL_TRUSTOR, headerDescription, new TrustorDescription());
        } else {
            //相当于Fragment的onPause
        }
    }


    private void addClientInfo(List<Trustor> clients) {
        ll_owners.removeAllViews();
        L.d("ClientInfo", clients.size() + "");
        boolean isFirstLine = true;
        for (Trustor trustor : clients) {

            if (!isFirstLine) {
                View line = new View(getActivity());
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 10);
                line.setLayoutParams(layoutParams);
                line.setBackgroundColor(Color.TRANSPARENT);
                ll_owners.addView(line);
            }
            isFirstLine = false;

            View infoView = LayoutInflater.from(getActivity()).inflate(R.layout.item_clientinfo, null);
            LinearLayout owner = infoView.findViewById(R.id.info_ll_owner);
            LinearLayout telephone = infoView.findViewById(R.id.info_ll_phone);

            View clientInfo = LayoutInflater.from(getActivity()).inflate(R.layout.item_info_text, null);
            TextView title = clientInfo.findViewById(R.id.info_title);
            TextView content = clientInfo.findViewById(R.id.info_content);
            title.setText(trustor.getTrustorName());
            content.setText(trustor.getTrustortRemark());
            owner.addView(clientInfo);

            boolean isFirstContactLine = true;
            for (TrustorDetail contact : trustor.getTrustorDetails()) {
                telephone.setVisibility(View.VISIBLE);
                if (!isFirstContactLine) {
                    View line = new View(getActivity());
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);
                    line.setLayoutParams(layoutParams);
                    line.setBackgroundColor(Color.parseColor("#EFEFEF"));
                    telephone.addView(line);
                }
                isFirstContactLine = false;
                View phoneInfo = null;
                L.d("ClientInfo", "phoneName: " + contact.getContactTypeName());
                if (contact.getContactTypeName().equals("電話號碼")) {
                    phoneInfo = LayoutInflater.from(getActivity()).inflate(R.layout.item_info_phone, null);
                } else {
                    phoneInfo = LayoutInflater.from(getActivity()).inflate(R.layout.item_info_text, null);
                }
                TextView phoneNumber = phoneInfo.findViewById(R.id.info_title);
                TextView phoneRemark = phoneInfo.findViewById(R.id.info_content);
                phoneNumber.setText(contact.getContactValue());
                phoneRemark.setText(contact.getMobileRemark());
                telephone.addView(phoneInfo);
            }

            ll_owners.addView(infoView);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public interface ClientUpDateListener {
        void getClientInfo(String address, AHeaderDescription AHeaderDescription, TrustorDescription trustorDescription);
    }
}
