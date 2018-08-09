package com.centanet.hk.aplus.Views.ComplexSearchView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Views.Dialog.CalendarDialog;
import com.centanet.hk.aplus.Views.Dialog.OpenDataDialog;
import com.centanet.hk.aplus.Views.Dialog.StatusDialog;
import com.centanet.hk.aplus.Views.Dialog.WheelViewDialog;
import com.centanet.hk.aplus.Views.basic.BaseFragment;
import com.centanet.hk.aplus.Widgets.ClearEditText;
import com.centanet.hk.aplus.bean.http.HouseDescription;
import com.centanet.hk.aplus.bean.params.SystemParamItems;
import com.centanet.hk.aplus.common.APSystemParameterType;
import com.centanet.hk.aplus.manager.AppSystemParamsManager;
import com.centanet.hk.aplus.manager.ApplicationManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import static com.centanet.hk.aplus.common.CommandField.APPropertyDateType.changePriceDate;
import static com.centanet.hk.aplus.common.CommandField.APPropertyDateType.estimatedDate;
import static com.centanet.hk.aplus.common.CommandField.APPropertyDateType.lasetUpdate;
import static com.centanet.hk.aplus.common.CommandField.APPropertyDateType.lastFollowDate;
import static com.centanet.hk.aplus.common.CommandField.APPropertyDateType.onlyTrustEndDate;
import static com.centanet.hk.aplus.common.CommandField.APPropertyDateType.onlyTrustStartDate;
import static com.centanet.hk.aplus.common.CommandField.APPropertyDateType.registDate;
import static com.centanet.hk.aplus.common.CommandField.APPropertyDateType.statusChangedDate;
import static com.centanet.hk.aplus.manager.ApplicationManager.getSelectStatusText;
import static com.centanet.hk.aplus.manager.ApplicationManager.getStatusText;

/**
 * Created by yangzm4 on 2018/7/24.
 */

public class OtherFragment extends BaseFragment implements View.OnClickListener {

    public static String ARGUMENT = "SysItemFragment";
    private ClearEditText ownerEdit, phoneEdit;
    private TextView staStartTxt, staEndTxt;
    private OnOtherChangeLisenter onOtherChangeLisenter;
    private List<String> staBeginSelectList;
    private List<String> staEndSelectList;
    private HouseDescription houseDescription;
    private TextView dateTypeTxt, yearStaTxt, yearEndTxt, choiceDateTxt, dateStaTxt, dateEndTxt;
    private int openDataType;
    private View statuView;
    private View finishYearContent;
    private View dateContent;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnOtherChangeLisenter) {
            onOtherChangeLisenter = (OnOtherChangeLisenter) context;
        } else
            throw new IllegalArgumentException("Activity  Must Implements RentFragment.OnRentChangeLisenter");
    }

    public static OtherFragment newInstance(HouseDescription argument) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARGUMENT, argument);
        OtherFragment contentFragment = new OtherFragment();
        contentFragment.setArguments(bundle);
        return contentFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other, null, false);
        init();
        initView(view);
        initLisenter();
        reCoverView(houseDescription);
        return view;
    }

    private void init() {
        houseDescription = (HouseDescription) getArguments().get(ARGUMENT);
        L.d("HouseDescription", houseDescription.toString());
        if (houseDescription == null)
            houseDescription = new HouseDescription();
        staBeginSelectList = new ArrayList<>();
        staEndSelectList = new ArrayList<>();
    }

    private void reCoverView(HouseDescription description) {
        ownerEdit.setText(description.getTrustorName());
        phoneEdit.setText(description.getMobile());
//        finishYearEdit.setText(houseDescription.getCom);

        if (houseDescription.getPropertyDateType() != null && !houseDescription.getPropertyDateType().equals(""))
            switch (Integer.parseInt(houseDescription.getPropertyDateType())) {
                case registDate:
                    openDataType = R.id.dialog_opendate_rb_default;
                    dateTypeTxt.setText("開盤日期");
                    break;
                case lasetUpdate:
                    openDataType = R.id.dialog_opendate_rb_modification_last;
                    dateTypeTxt.setText("最后修改日期");
                    break;
                case lastFollowDate:
                    openDataType = R.id.dialog_opendate_rb_follow;
                    dateTypeTxt.setText("最后修改日期");
                    break;
                case changePriceDate:
                    openDataType = R.id.dialog_opendate_rb_price;
                    dateTypeTxt.setText("最后修改日期");
                    break;
                case onlyTrustStartDate:
                    openDataType = R.id.dialog_opendate_rb_entrust_start;
                    dateTypeTxt.setText("委託書開始日");
                    break;
                case onlyTrustEndDate:
                    openDataType = R.id.dialog_opendate_rb_entrust_end;
                    dateTypeTxt.setText("委託書到期日");
                    break;
                case estimatedDate:
                    openDataType = R.id.dialog_opendate_rb_anticipate;
                    dateTypeTxt.setText("估計日期");
                    break;
                case statusChangedDate:
                    openDataType = R.id.dialog_opendate_rb_changehouse;
                    dateTypeTxt.setText("改盤日期");
                    statuView.setVisibility(View.VISIBLE);
                    if (houseDescription.getIncludedPropertyStatusFrom() != null) {

                        L.d("getValue", houseDescription.getIncludedPropertyStatusFrom().toString());

                        for (String s : AppSystemParamsManager.getValue(APSystemParameterType.propertyStatusCategory, houseDescription.getIncludedPropertyStatusFrom())) {
                            L.d("getValue", s);
                        }
                        staStartTxt.setText(getSelectStatusText(AppSystemParamsManager.getValue(APSystemParameterType.propertyStatusCategory, houseDescription.getIncludedPropertyStatusFrom())));
                    }
                    if (houseDescription.getIncludedPropertyStatusTo() != null)
                        staEndTxt.setText(getSelectStatusText(AppSystemParamsManager.getValue(APSystemParameterType.propertyStatusCategory, houseDescription.getIncludedPropertyStatusTo())));
                    break;
            }

        if ((houseDescription.getPropertyDateFrom() != null && !houseDescription.getPropertyDateFrom().equals("")) ||
                (houseDescription.getPropertyDateTo() != null && !houseDescription.getPropertyDateTo().equals(""))) {
            choiceDateTxt.setVisibility(View.GONE);
            dateEndTxt.setText(houseDescription.getPropertyDateTo());
            dateStaTxt.setText(houseDescription.getPropertyDateFrom());
        }

        if (houseDescription.getCompleteYearFrom() != null && !houseDescription.getCompleteYearFrom().equals(""))
            yearStaTxt.setText(houseDescription.getCompleteYearFrom());
        if (houseDescription.getCompleteYearTo() != null && !houseDescription.getCompleteYearTo().equals(""))
            yearEndTxt.setText(houseDescription.getCompleteYearTo());
    }

    private void initLisenter() {

        staEndTxt.setOnClickListener(this);
        staStartTxt.setOnClickListener(this);
        dateTypeTxt.setOnClickListener(this);
//        choiceDateTxt.setOnClickListener(this);
        dateContent.setOnClickListener(this);
//        yearStaTxt.setOnClickListener(this);
//        yearEndTxt.setOnClickListener(this);

//        yearEndTxt.setOnClickListener(this);
//        yearStaTxt.setOnClickListener(this);
        finishYearContent.setOnClickListener(this);

        ownerEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                houseDescription.setTrustorName(ownerEdit.getText().toString());
                onOtherChangeLisenter.onOtherChange(houseDescription);
            }
        });

        phoneEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                houseDescription.setMobile(phoneEdit.getText().toString());
                onOtherChangeLisenter.onOtherChange(houseDescription);
            }
        });

    }

    private void initView(View view) {
        ownerEdit = view.findViewById(R.id.other_edit_owner);
        phoneEdit = view.findViewById(R.id.other_edit_phone);

        yearStaTxt = view.findViewById(R.id.other_txt_yearsta);
        yearEndTxt = view.findViewById(R.id.other_txt_yearend);

        choiceDateTxt = view.findViewById(R.id.other_txt_choicedate);
        dateStaTxt = view.findViewById(R.id.other_txt_datesta);
        dateEndTxt = view.findViewById(R.id.other_txt_dateend);

        finishYearContent = view.findViewById(R.id.other_ll_finishyear);

        staStartTxt = view.findViewById(R.id.other_txt_stastart);
        staEndTxt = view.findViewById(R.id.other_txt_staend);

        dateTypeTxt = view.findViewById(R.id.other_txt_date);
        statuView = view.findViewById(R.id.other_ll_statuchange);

        dateContent = view.findViewById(R.id.other_ll_choicedate);
    }

    //開盤日期
    public void openDate() {
        OpenDataDialog openDataDialog = new OpenDataDialog(openDataType);
        openDataDialog.setOnDialogClikeLisenter((dialog, viewId, result) -> {
            dialog.dismiss();
            openDataType = viewId;
            dateTypeTxt.setText((String) result);
            if (viewId == R.id.dialog_opendate_rb_changehouse) {
                statuView.setVisibility(View.VISIBLE);
            } else statuView.setVisibility(View.GONE);
//                    Calendar calendar = Calendar.getInstance();
//                    textDateOpenDateBegin.setText(calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH)) + "-" + calendar.get(Calendar.DAY_OF_MONTH));
//                    textDateOpenDateEnd.setText(calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH));
//                    changeDateEnd = calendar.getTime();
//                    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) - 1, calendar.get(Calendar.DAY_OF_MONTH));
//                    changeDateStart = calendar.getTime();
//                } else statusView.setVisibility(View.GONE);
            switch (openDataType) {
                case R.id.dialog_opendate_rb_default:
                    houseDescription.setPropertyDateType(registDate);
                    houseDescription.setIncludedPropertyStatusFrom(null);
                    houseDescription.setIncludedPropertyStatusTo(null);
                    break;
                case R.id.dialog_opendate_rb_modification_last:
                    houseDescription.setPropertyDateType(lasetUpdate);
                    houseDescription.setIncludedPropertyStatusFrom(null);
                    houseDescription.setIncludedPropertyStatusTo(null);
                    break;
                case R.id.dialog_opendate_rb_follow:
                    houseDescription.setPropertyDateType(lastFollowDate);
                    houseDescription.setIncludedPropertyStatusFrom(null);
                    houseDescription.setIncludedPropertyStatusTo(null);
                    break;
                case R.id.dialog_opendate_rb_price:
                    houseDescription.setPropertyDateType(changePriceDate);
                    houseDescription.setIncludedPropertyStatusFrom(null);
                    houseDescription.setIncludedPropertyStatusTo(null);
                    break;
                case R.id.dialog_opendate_rb_entrust_start:
                    houseDescription.setPropertyDateType(onlyTrustStartDate);
                    houseDescription.setIncludedPropertyStatusFrom(null);
                    houseDescription.setIncludedPropertyStatusTo(null);
                    break;
                case R.id.dialog_opendate_rb_entrust_end:
                    houseDescription.setPropertyDateType(onlyTrustEndDate);
                    houseDescription.setIncludedPropertyStatusFrom(null);
                    houseDescription.setIncludedPropertyStatusTo(null);
                    break;
                case R.id.dialog_opendate_rb_anticipate:
                    houseDescription.setPropertyDateType(estimatedDate);
                    houseDescription.setIncludedPropertyStatusFrom(null);
                    houseDescription.setIncludedPropertyStatusTo(null);
                    break;
                case R.id.dialog_opendate_rb_changehouse:
                    houseDescription.setPropertyDateType(statusChangedDate);
//                    houseDescription.setIncludedPropertyStatusFrom((staValueBeginList != null && staValueBeginList.isEmpty()) ? null : staValueBeginList);
//                    houseDescription.setIncludedPropertyStatusTo((staValueEndList != null && staValueEndList.isEmpty()) ? null : staValueEndList);
                    break;
            }
            onOtherChangeLisenter.onOtherChange(houseDescription);
        });
        openDataDialog.show(getActivity().getSupportFragmentManager(), "");
    }


    private void showStatusEndDialog() {
        StatusDialog statusEndDialog = new StatusDialog(getStatusText(), houseDescription.getIncludedPropertyStatusTo());
        statusEndDialog.setOnDialogOnclikeLisenter((v, viewID, viewList, content) -> {
            v.dismiss();
            staEndSelectList = viewList;
            staEndTxt.setText(getSelectStatusText(content));
            houseDescription.setIncludedPropertyStatusTo(getStatusCodes(content));
//            for (String s : content) L.d("showStatusEndDialog", s);
            L.d("showStatusEndDialog", viewList.toString());
            onOtherChangeLisenter.onOtherChange(houseDescription);
        });
        statusEndDialog.show(getActivity().getSupportFragmentManager(), "");
    }

    private void showStatusBeginDialog() {

        StatusDialog statusBeginDialog = new StatusDialog(getStatusText(), houseDescription.getIncludedPropertyStatusFrom());
        statusBeginDialog.setOnDialogOnclikeLisenter((v, viewID, viewList, content) -> {
            v.dismiss();
            staBeginSelectList = viewList;
            L.d("showStatusBeginDialog", viewList.toString());
            staStartTxt.setText(getSelectStatusText(content));
            houseDescription.setIncludedPropertyStatusFrom(getStatusCodes(content));
            onOtherChangeLisenter.onOtherChange(houseDescription);
        });
        statusBeginDialog.show(getActivity().getSupportFragmentManager(), "");
    }

    private List<String> getStatusCodes(String[] status) {
        if (status == null) return null;
        Map<String, String> statusParams = ApplicationManager.getStatusCode();
        List<String> statuList = new ArrayList<>();
        for (String sta : status) {
            for (Map.Entry<String, String> entry : statusParams.entrySet()) {
                L.d("showStatusEndDialog", "Key: " + entry.getKey() + " " + " Value: " + entry.getValue());
                if (entry.getKey().equals(sta.substring(0, 1))) {
                    L.d("showStatusEndDialog", "");
                    statuList.add(entry.getValue());
                }
            }
        }
        return statuList;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.other_txt_stastart:
                showStatusBeginDialog();
                break;
            case R.id.other_txt_staend:
                showStatusEndDialog();
                break;
            case R.id.other_txt_date:
                openDate();
                break;
            case R.id.other_ll_finishyear:
                showYearDialog(houseDescription.getCompleteYearFrom(), houseDescription.getCompleteYearTo());
                break;
            case R.id.other_ll_choicedate:
                showDateDialog("", "");
                break;
        }
    }

    private void showDateDialog(String start, String end) {
        CalendarDialog dialog = new CalendarDialog();
        dialog.setEndCl(Calendar.getInstance());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        dialog.setStartCl(calendar);
        dialog.setOnDialogOnclikeLisenter((dialog1, view, start1, end1) -> {
            dialog1.dismiss();
            choiceDateTxt.setVisibility(View.GONE);
            String dateStar = start1.get(Calendar.YEAR) + "-" + start1.get(Calendar.MONTH) + "-" + start1.get(Calendar.DAY_OF_MONTH);
            String dateEnd = end1.get(Calendar.YEAR) + "-" + end1.get(Calendar.MONTH) + "-" + end1.get(Calendar.DAY_OF_MONTH);
            dateStaTxt.setText(dateStar);
            dateEndTxt.setText(dateEnd);
            houseDescription.setPropertyDateFrom(dateStar);
            houseDescription.setPropertyDateTo(dateEnd);

            onOtherChangeLisenter.onOtherChange(houseDescription);
        });
        dialog.show(getActivity().getFragmentManager(), "");
    }

    private void showYearDialog(String start, String end) {
        WheelViewDialog dialog = new WheelViewDialog();
        dialog.setEndYear(end);
        dialog.setStarYear(start);
        dialog.setOnDialogClickLisenter((dialog1, years) -> {
            dialog1.dismiss();
            yearStaTxt.setText(years[0]);
            yearEndTxt.setText(years[1]);
            houseDescription.setCompleteYearFrom(years[0]);
            houseDescription.setCompleteYearTo(years[1]);
            onOtherChangeLisenter.onOtherChange(houseDescription);
        });
        dialog.show(getActivity().getFragmentManager(), "");
    }

    public interface OnOtherChangeLisenter {
        void onOtherChange(HouseDescription description);
    }
}
