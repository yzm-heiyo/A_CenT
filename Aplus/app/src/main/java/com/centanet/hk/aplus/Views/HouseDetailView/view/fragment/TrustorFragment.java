package com.centanet.hk.aplus.Views.HouseDetailView.view.fragment;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Space;
import android.widget.TextView;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.DensityUtil;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.Views.Dialog.LoadingDialog;
import com.centanet.hk.aplus.Views.Dialog.SimpleTipsDialog;
import com.centanet.hk.aplus.Views.HouseDetailView.present.IDetailPresent;
import com.centanet.hk.aplus.Views.HouseDetailView.view.IDataManager;
import com.centanet.hk.aplus.Views.HouseDetailView.view.IPresenterManager;
import com.centanet.hk.aplus.bean.detail.DetailTrustor;
import com.centanet.hk.aplus.bean.detail.Trustor;
import com.centanet.hk.aplus.bean.detail.TrustorDetail;
import com.centanet.hk.aplus.bean.http.AHeaderDescription;
import com.centanet.hk.aplus.bean.http.VirtualPhoneDescription;
import com.centanet.hk.aplus.manager.ApplicationManager;
import com.centanet.hk.aplus.manager.PermissionManager;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.view.View.VISIBLE;
import static com.centanet.hk.aplus.Views.HouseDetailView.view.DetailActicity.isODish;

/**
 * Created by yangzm4 on 2018/7/13.
 */

public class TrustorFragment extends Fragment implements IDataManager<DetailTrustor>, IPresenterManager<IDetailPresent>, View.OnClickListener {

    private Context context;
    private LinearLayout contentView;
    private boolean isOdish;
    private IDetailPresent present;
    private Intent numberIntent;
    private LoadingDialog loadingDialog;
    private boolean isCallHiddenPhone;
    private boolean isAbleToSeeObuliding;
    private List<String> contactType;
    private boolean isAbleToSeeClientInfo; //如果没有查看业资料权限就不显示
    private TextView tipsTxt;
    private ProgressBar progressBar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContext();
        View view = inflater.inflate(R.layout.item_detail_clientinfo, null, false);
        contentView = view.findViewById(R.id.trustor_ly_phone);
        tipsTxt = view.findViewById(R.id.record_txt_nodata);
        progressBar = view.findViewById(R.id.progressBar);
        init();
        return view;
    }

    private void init() {
        loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.setCancelable(false);
        isCallHiddenPhone = PermissionManager.isCallHiddenPhonePermission();
        isAbleToSeeObuliding = PermissionManager.isSeeOBuildPermission();
        isAbleToSeeClientInfo = PermissionManager.isSeeClientInfoPermission();
        contactType = ApplicationManager.getContactType();
//        EventBus.getDefault().register(this);
    }

    public void resetFragment() {
        progressBar.setVisibility(View.VISIBLE);
        contentView.removeAllViews();
        tipsTxt.setVisibility(View.GONE);
        tipsTxt.setText(getString(R.string.no_data));
    }

    @Override
    public void setData(DetailTrustor data) {
        progressBar.setVisibility(View.GONE);
        tipsTxt.setVisibility(View.GONE);

        contentView.removeAllViews();

        isCallHiddenPhone = PermissionManager.isCallHiddenPhonePermission();
        isAbleToSeeObuliding = PermissionManager.isSeeOBuildPermission();
        isAbleToSeeClientInfo = PermissionManager.isSeeClientInfoPermission();

        if (!isAbleToSeeClientInfo) {
            tipsTxt.setText("没有查看權限");
            tipsTxt.setVisibility(VISIBLE);
            return;
        }

        if (data == null || data.getTrustors().isEmpty()) {
            tipsTxt.setVisibility(VISIBLE);
            return;
        }

        addClientInfo(data);
    }

    private void addClientInfo(DetailTrustor data) {

        L.d("","isAbleToSeeClientInfo: "+ isAbleToSeeClientInfo+" isAbleToSeeObuliding:" +isAbleToSeeObuliding + " isAbleToSeeClientInfo: "+isAbleToSeeClientInfo);

        if (data.getTrustors() != null && !data.getTrustors().isEmpty()) {
            List<Trustor> trustors = data.getTrustors();
            for (int i = 0; i < trustors.size(); i++) {

                Space space = new Space(getContext());
                space.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(context, 12)));
                contentView.addView(space);

                LinearLayout infoView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.item_detail_owner, null, false);

                View infoTitleView = LayoutInflater.from(context).inflate(R.layout.item_info_title, null, false);
                ((TextView) infoTitleView.findViewById(R.id.phone_txt_trustertype)).setText(trustors.get(i).getTrustorTypeName());
                infoView.addView(infoTitleView);

                View ownerView = LayoutInflater.from(context).inflate(R.layout.item_info_owner, null, false);
                ((TextView) ownerView.findViewById(R.id.phone_txt_owner)).setText(trustors.get(i).getTrustorName());
                ((TextView) ownerView.findViewById(R.id.phone_txt_ownerremark)).setText(trustors.get(i).getTrustortRemark());
                infoView.addView(ownerView);


                if (trustors.get(i).getTrustorDetails() != null) {
                    infoView.addView(getLineView());
                    addPhoneView(infoView, trustors.get(i).getTrustorDetails());
                }

                contentView.addView(infoView);
            }
        }
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onMoonEvent(MessageEventBus messageEvent) {
//
//        switch (messageEvent.getMsg()) {
//
//            case VIRTUALPHONE:
//                loadingDialog.dismiss();
//                VirtualPhone number = (VirtualPhone) messageEvent.getObject();
//                if (number != null) {
//                    if (!number.isFlag() && number.getErrorMsg() != null && !number.getErrorMsg().equals(""))
//                        DialogUtil.getSimpleDialog(number.getErrorMsg()).show(getFragmentManager(), "");
//                    else callPhone(number.getMsisdn());
//                }
//                break;
//
//            case NETWORK_STATE_FAIL:
//                loadingDialog.dismiss();
//                break;
//        }
//    }

    public void setOdish(boolean odish) {
        isOdish = odish;
    }

    private void addPhoneView(LinearLayout infoView, List<TrustorDetail> trustorDetails) {
        int size = trustorDetails.size();

        for (int i = 0; i < size; i++) {

            Space space = new Space(context);
            space.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(context, 10)));
            infoView.addView(space);

            TrustorDetail trustorDetail = trustorDetails.get(i);
//            for (int i = 0; i < 3; i++) {

            //电话标题
            View phoneTitleView = LayoutInflater.from(context).inflate(R.layout.item_info_title, null, false);
            ((TextView) phoneTitleView.findViewById(R.id.phone_txt_trustertype)).setText(trustorDetail.getContactTypeName());
            infoView.addView(phoneTitleView);

//            //如果直限类型为不允许，显示不可以
//            if (trustorDetail.getDirectSellTyp() == CommandField.APPropertyTrustorDirectSell.NOTALLOW)
            TextView directTxt = phoneTitleView.findViewById(R.id.phone_ly_tips);
            directTxt.setVisibility(VISIBLE);
            L.d("getDirectSellTyp",trustorDetail.getDirectSellTyp()+"");
            switch (trustorDetail.getDirectSellTyp()) {
                case 0:
                    directTxt.setText("直銷:不反對");
                    directTxt.setTextColor(getResources().getColor(R.color.color_light_green));
                    break;
                case 1:
                    directTxt.setText("直銷:不可以");
                    directTxt.setTextColor(getResources().getColor(R.color.colortheme));
                    break;
                case 2:
                    directTxt.setText("直銷:未知");
                    directTxt.setTextColor(getResources().getColor(R.color.color_orange));
                    break;
            }

            //电话视图
            View tleView = LayoutInflater.from(context).inflate(R.layout.item_info_validphone, null, false);

            //todo

            if (contactType.contains(trustorDetail.getContactTypeKeyId())) {

                if (!isODish || (isODish && isAbleToSeeObuliding)) {

                    if (trustorDetail.isHidden()) {
                        if (isCallHiddenPhone) {//如果是隐藏号码 并且有查看隐藏号码得权限 直接显示
//                            phoneInfo = LayoutInflater.from(getActivity()).inflate(R.layout.item_info_phone, null);
                            tleView.setTag(trustorDetail.getKeyId());
                            tleView.setSelected(true);
                            tleView.setOnClickListener(this);
                        } else {
//                            phoneInfo = LayoutInflater.from(getActivity()).inflate(R.layout.item_info_other, null);
                        }
                    } else {
                        //说明不是隐藏号码 又有查看得权限显示
//                        phoneInfo = LayoutInflater.from(getActivity()).inflate(R.layout.item_info_phone, null);
                        tleView.setTag(trustorDetail.getKeyId());
                        tleView.setSelected(true);
                        tleView.setOnClickListener(this);
                    }
                } else {
                    //是O盘但没有查看O盘得权限
//                    phoneInfo = LayoutInflater.from(getActivity()).inflate(R.layout.item_info_other, null);
                }

            } else {
                //不是电话号码类型，邮件类型
//                phoneInfo = LayoutInflater.from(getActivity()).inflate(R.layout.item_info_other, null);
            }
            //todo


//            //如果直限类型为不允许，显示不可以
//            if (trustorDetail.getDirectSellTyp() == CommandField.APPropertyTrustorDirectSell.NOTALLOW)
//                tleView.findViewById(R.id.phone_ly_tips).setVisibility(VISIBLE);

            ((TextView) tleView.findViewById(R.id.phone_txt_remark)).setText(trustorDetail.getMobileRemark());
            TextView tleTxt = tleView.findViewById(R.id.phone_txt_number);
            tleTxt.setText(trustorDetail.getContactValue());
            infoView.addView(tleView);

            if (i != size - 1) infoView.addView(getLineView());
        }
    }

    private View getLineView() {
        View view = new View(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
        view.setLayoutParams(params);
        view.setBackgroundColor(Color.parseColor("#E7E7E7"));
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doRequest(requestCode, grantResults);
    }

    public void callPhone(String phoneNum) {
        L.d("callPhone", "callPhone");

        numberIntent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        numberIntent.setData(data);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            //申请電話权限
            TrustorFragment.this.requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                    123);
            return;
        }
        startActivity(numberIntent);
    }

    private void doRequest(int requestCode, int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Permission Granted
            if (numberIntent != null)
                startActivity(numberIntent);
        } else {
            // Permission Denied
        }
    }

    @Override
    public void setaPresenter(IDetailPresent presenter) {
        present = presenter;
    }

    @Override
    public void onClick(View v) {
        VirtualPhoneDescription virtualPhoneDescription = new VirtualPhoneDescription();
        virtualPhoneDescription.setTargetMobileNumber((String) v.getTag());
        AHeaderDescription headerDescription1 = ApplicationManager.getApplication().getHeaderDescription();
        String staffNo = headerDescription1.getStaffno();
        virtualPhoneDescription.setStaffNo(staffNo);
        virtualPhoneDescription.setItemType("AP");
        virtualPhoneDescription.setSystem("AA");

        SimpleTipsDialog simpleTipsDialog = new SimpleTipsDialog();
        simpleTipsDialog.setOnItemclickListener(new SimpleTipsDialog.OnItemClickListener() {
            @Override
            public void onClick(DialogFragment dialog, int type) {
                if (type == SimpleTipsDialog.DIALOG_YES) {

                    //todo
//                    getPhoneNumber(headerDescription1,virtualPhoneDescription);
                    present.doPost(HttpUtil.URL_CALL_VIRTUAL_PHONE, headerDescription1, virtualPhoneDescription);

                    loadingDialog.show();
                    v.setOnClickListener(null);
                    TextView phoneNumber = v.findViewById(R.id.phone_txt_number);
                    ImageView phoneLogo = v.findViewById(R.id.info_logo);
//                    View bgView = v.findViewById(R.id.smallitem_tele_layout);
//                    bgView.setBackground(getContext().getResources().getDrawable(R.drawable.shape_detail_no_tele_background));

                    v.setSelected(false);

                    int left = ((LinearLayout.LayoutParams) phoneLogo.getLayoutParams()).leftMargin;

                    String number = phoneNumber.getText().toString();
                    Timer timer = new Timer();
                    final int[] i = {0};
                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            if (getActivity() == null) return;
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    L.d("time", i[0] + "");
                                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) phoneLogo.getLayoutParams();
                                    lp.leftMargin = left * 8;
                                    phoneLogo.setLayoutParams(lp);
                                    phoneNumber.setText(" (" + (15 - (++i[0])) + "s)     ");
                                    if (i[0] == 15) {
                                        i[0] = 0;
                                        timer.cancel();
                                        phoneNumber.setText(number);
                                        v.setOnClickListener(TrustorFragment.this);
//                                        bgView.setBackground(getContext().getResources().getDrawable(R.drawable.shape_detail_tele_background));
                                        v.setSelected(true);
                                        LinearLayout.LayoutParams oldp = (LinearLayout.LayoutParams) phoneLogo.getLayoutParams();
                                        lp.leftMargin = left;
                                        phoneLogo.setLayoutParams(oldp);
                                    }
                                }
                            });
                        }
                    };
                    timer.schedule(timerTask, 0, 1000);
                }
            }
        });
        simpleTipsDialog.setContentString(getString(R.string.dialog_tips_detail_call));
        simpleTipsDialog.show(getFragmentManager(), "");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }
}
