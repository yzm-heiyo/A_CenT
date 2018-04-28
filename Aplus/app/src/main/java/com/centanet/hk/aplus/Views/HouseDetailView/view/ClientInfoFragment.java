package com.centanet.hk.aplus.Views.HouseDetailView.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.centanet.hk.aplus.MyApplication;
import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.DialogUtil;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.Views.Dialog.SimpleTipsDialog;
import com.centanet.hk.aplus.bean.detail.DetailTrustor;
import com.centanet.hk.aplus.bean.detail.Trustor;
import com.centanet.hk.aplus.bean.detail.TrustorDetail;
import com.centanet.hk.aplus.bean.detail.VirtualPhone;
import com.centanet.hk.aplus.bean.http.AHeaderDescription;
import com.centanet.hk.aplus.bean.http.VirtualPhoneDescription;
import com.centanet.hk.aplus.eventbus.MessageEventBus;
import com.centanet.hk.aplus.manager.ApplicationManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.CALLHIDDEN_YES;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.DetailRefresh.DETAIL_REFRESH;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.DetailRefreshView.DETAIL_CLIENT_INFO;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.VIRTUALPHONE;

/**
 * Created by mzh1608258 on 2018/1/3.
 */

public class ClientInfoFragment extends Fragment implements View.OnClickListener {

    private static final String CLIENT_DETAIL = "STATE";
    private View view;
    private ClientUpDateListener onUpdateListener;
    private AHeaderDescription headerDescription;
    private Context context;
    private LinearLayout ll_owners;
    private Intent numberIntent;
    private List<String> contactType;
    private boolean isVisible = false;
    private DetailTrustor detailTrustor;
    private boolean isCallHiddenPhone = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof ClientUpDateListener) {
            onUpdateListener = (ClientUpDateListener) context;
        } else {
            throw new IllegalArgumentException("activity must implements FragmentInteraction");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEventBus messageEvent) {

        switch (messageEvent.getMsg()) {
            case DETAIL_CLIENT_INFO:
                detailTrustor = (DetailTrustor) messageEvent.getObject();
                if (detailTrustor != null)
                    addTrustorView(detailTrustor);
                break;
            case VIRTUALPHONE:
                VirtualPhone number = (VirtualPhone) messageEvent.getObject();
                if (number != null) {
                    if (!number.isFlag() && number.getErrorMsg() != null && !number.getErrorMsg().equals(""))
                        DialogUtil.getSimpleDialog(number.getErrorMsg()).show(getFragmentManager(), "");
                    else callPhone(number.getMsisdn());
                }
                break;
            case CALLHIDDEN_YES:
                isCallHiddenPhone = true;
                break;
        }
    }

    private void addTrustorView(DetailTrustor detailTrustor) {
        if (isAbleToShowTrustor(detailTrustor))
            addClientInfo(detailTrustor.getTrustors());
    }

    private boolean isAbleToShowTrustor(DetailTrustor detailTrustor) {

        if (detailTrustor == null) return false;

        if (detailTrustor.getTrustors() == null || detailTrustor.getTrustors().isEmpty()) {
            SimpleTipsDialog simpleTipsDialog = new SimpleTipsDialog();
            if (!isVisible) return false;
            if (detailTrustor.getErrorMsg() != null && !detailTrustor.getErrorMsg().equals("")) {
                simpleTipsDialog.setContentString(detailTrustor.getErrorMsg());
            } else {
                simpleTipsDialog.setContentString(getString(R.string.dialog_tips_permission_clientinfo_no));
            }
            simpleTipsDialog.setLeftBtnVisibility(false);
            simpleTipsDialog.show(getFragmentManager(), "");
            return false;
        }

        return true;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_clientsinfo, null);
        ll_owners = view.findViewById(R.id.info_ll_owners);
        headerDescription = ((MyApplication) getActivity().getApplication()).getHeaderDescription();
        contactType = ApplicationManager.getContactType();
        if (getArguments() != null) {
            detailTrustor = (DetailTrustor) getArguments().get(CLIENT_DETAIL);
            if (detailTrustor == null) return view;
            addClientInfo(detailTrustor.getTrustors());
        }
        return view;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisible = true;
            Bundle stateBundle = getArguments();
            if (stateBundle == null) {
                MessageEventBus eventBus = new MessageEventBus();
                eventBus.setMsg(DETAIL_REFRESH);
                EventBus.getDefault().post(eventBus);
            } else {
                isAbleToShowTrustor((DetailTrustor) stateBundle.get(CLIENT_DETAIL));
            }
        } else {
            //相当于Fragment的onPause
            isVisible = false;
        }
    }

    private void addClientInfo(List<Trustor> clients) {
        ll_owners.removeAllViews();
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
            title.setText(trustor.getTrustorGender() + trustor.getTrustorName());
            content.setText(trustor.getTrustortRemark());
            owner.addView(clientInfo);

            boolean isFirstContactLine = true;

            if (trustor.getTrustorDetails() != null)
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
//                    if (contactType.contains(contact.getContactTypeKeyId())) {
//                        phoneInfo = LayoutInflater.from(getActivity()).inflate(R.layout.item_info_phone, null);
//                        phoneInfo.setTag(contact.getKeyId());
//                        phoneInfo.setOnClickListener(this);
//                    } else {
//                        phoneInfo = LayoutInflater.from(getActivity()).inflate(R.layout.item_info_other, null);
//                    }

                    if (contactType.contains(contact.getContactTypeKeyId())) {
                        if (contact.isHidden()) {
                            if (isCallHiddenPhone) {
                                phoneInfo = LayoutInflater.from(getActivity()).inflate(R.layout.item_info_phone, null);
                                phoneInfo.setTag(contact.getKeyId());
                                phoneInfo.setOnClickListener(this);
                            } else {
                                phoneInfo = LayoutInflater.from(getActivity()).inflate(R.layout.item_info_other, null);
                            }
                        } else {
                            phoneInfo = LayoutInflater.from(getActivity()).inflate(R.layout.item_info_phone, null);
                            phoneInfo.setTag(contact.getKeyId());
                            phoneInfo.setOnClickListener(this);
                        }
                    } else {
                        phoneInfo = LayoutInflater.from(getActivity()).inflate(R.layout.item_info_other, null);
                    }

                    TextView phoneNumber = phoneInfo.findViewById(R.id.info_title);
                    TextView phoneRemark = phoneInfo.findViewById(R.id.info_content);
                    phoneNumber.setText(contact.getContactValue());
                    phoneRemark.setText(contact.getMobileRemark());
                    telephone.addView(phoneInfo);


                }

            ll_owners.addView(infoView);
        }

        View line = new View(getActivity());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 10);
        line.setLayoutParams(layoutParams);
        line.setBackgroundColor(Color.TRANSPARENT);
        ll_owners.addView(line);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {

        VirtualPhoneDescription virtualPhoneDescription = new VirtualPhoneDescription();
        virtualPhoneDescription.setTargetMobileNumber((String) v.getTag());
        AHeaderDescription headerDescription1 = ApplicationManager.getApplication().getHeaderDescription();
        String staffNo = headerDescription1.getStaffno();
        virtualPhoneDescription.setStaffNo(staffNo);
        virtualPhoneDescription.setItemType("AP");

        SimpleTipsDialog simpleTipsDialog = new SimpleTipsDialog();
        simpleTipsDialog.setOnItemclickListener(new SimpleTipsDialog.OnItemClickListener() {
            @Override
            public void onClick(DialogFragment dialog, int type) {
                if (type == SimpleTipsDialog.DIALOG_YES)
                    onUpdateListener.getClientInfo(HttpUtil.URL_CALL_VIRTUAL_PHONE, headerDescription1, virtualPhoneDescription);
            }
        });
        simpleTipsDialog.setContentString(getString(R.string.dialog_tips_detail_call));
        simpleTipsDialog.show(getFragmentManager(), "");

//        callPhone("10086");
    }

    public void callPhone(String phoneNum) {

        L.d("callPhone", "callPhone");

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            //申请電話权限
            ClientInfoFragment.this.requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                    123);
            return;
        }
        numberIntent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        numberIntent.setData(data);
        startActivity(numberIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doRequest(requestCode, grantResults);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (detailTrustor == null) return;
        Bundle stateBundle = new Bundle();
        stateBundle.putSerializable(CLIENT_DETAIL, detailTrustor);
        this.setArguments(stateBundle);
    }

    private void doRequest(int requestCode, int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Permission Granted
            startActivity(numberIntent);
        } else {
            // Permission Denied
        }
    }

    public interface ClientUpDateListener {
        void getClientInfo(String address, AHeaderDescription AHeaderDescription, VirtualPhoneDescription trustorDescription);
    }
}
