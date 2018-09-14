package com.centanet.hk.aplus.Views.ComplexSearchView;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.TextUtil;
import com.centanet.hk.aplus.Views.Dialog.CalendarDialog;
import com.centanet.hk.aplus.Views.Dialog.OpenDataDialog;
import com.centanet.hk.aplus.Views.Dialog.StatusDialog;
import com.centanet.hk.aplus.bean.http.HouseDescription;
import com.centanet.hk.aplus.common.APSystemParameterType;
import com.centanet.hk.aplus.manager.AppSystemParamsManager;
import com.centanet.hk.aplus.manager.ApplicationManager;

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

public class DateFragment extends Fragment implements View.OnClickListener {


    private TextView choiceDateTxt, dateStaTxt, dateEndTxt, dateTypeTxt;
    private TextView staStartTxt, staEndTxt;
    private View statuView, dateContent;
    private int openDataType;
    private HouseDescription houseDescription;
    private List<String> staBeginSelectList;
    private List<String> staEndSelectList;
    private OnDateChangeLisenter onDateChangeLisenter;
    public static String ARGUMENT = "SysItemFragment";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDateChangeLisenter) {
            onDateChangeLisenter = (OnDateChangeLisenter) context;
        } else
            throw new IllegalArgumentException("Activity  Must Implements RentFragment.OnRentChangeLisenter");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_date, null, false);
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

//        if (!TextUtil.isEmply(houseDescription.getPropertyDateFrom())) {
//            houseDescription.setPropertyDateFrom(changeToDate(houseDescription.getPropertyDateFrom()));
//        }
//
//        if (!TextUtil.isEmply(houseDescription.getPropertyDateTo())) {
//            houseDescription.setPropertyDateTo(changeToDate(houseDescription.getPropertyDateTo()));
//        }
    }

//    private String changeToDate(String propertyDateTo) {
//        L.d("propertyDateTo______",propertyDateTo);
//        String[] date = propertyDateTo.split("-");
//        L.d("propertyDateTo______",date[0] + "年" + date[1] + "月" + date[2] + "日");
//
//        return date[0] + "年" + date[1] + "月" + date[2] + "日";
//    }

    public static DateFragment newInstance(HouseDescription argument) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARGUMENT, argument);
        DateFragment contentFragment = new DateFragment();
        contentFragment.setArguments(bundle);
        return contentFragment;
    }

    private void initLisenter() {
        dateTypeTxt.setOnClickListener(this);
        dateContent.setOnClickListener(this);

        staEndTxt.setOnClickListener(this);
        staStartTxt.setOnClickListener(this);
    }

    private void initView(View view) {

        choiceDateTxt = view.findViewById(R.id.other_txt_choicedate);
        dateStaTxt = view.findViewById(R.id.other_txt_datesta);
        dateEndTxt = view.findViewById(R.id.other_txt_dateend);

        dateTypeTxt = view.findViewById(R.id.other_txt_date);
        statuView = view.findViewById(R.id.other_ll_statuchange);
        dateContent = view.findViewById(R.id.other_ll_choicedate);

        staStartTxt = view.findViewById(R.id.other_txt_stastart);
        staEndTxt = view.findViewById(R.id.other_txt_staend);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.other_txt_date:
                openDate();
                break;
            case R.id.other_ll_choicedate:
                showDateDialog(houseDescription.getPropertyDateFrom(), houseDescription.getPropertyDateTo());
                break;
            case R.id.other_txt_stastart:
                showStatusBeginDialog();
                break;
            case R.id.other_txt_staend:
                showStatusEndDialog();
        }
    }

    private void reCoverView(HouseDescription houseDescription) {

        L.d("reCoverView", houseDescription.getPropertyDateFrom() + " " + houseDescription.getPropertyDateTo());

        if (houseDescription.getPropertyDateType() != null && !houseDescription.getPropertyDateType().equals(""))
            switch (Integer.parseInt(houseDescription.getPropertyDateType())) {
                case registDate:
                    openDataType = R.id.dialog_opendate_rb_default;
                    dateTypeTxt.setText(getContext().getString(R.string.dialog_opendate_opendate));
                    break;
                case lasetUpdate:
                    openDataType = R.id.dialog_opendate_rb_modification_last;
                    dateTypeTxt.setText(getContext().getString(R.string.dialog_opendate_last_modify));
                    break;
                case lastFollowDate:
                    openDataType = R.id.dialog_opendate_rb_follow;
                    dateTypeTxt.setText(getContext().getString(R.string.dialog_opendate_last_follow));
                    break;
                case changePriceDate:
                    openDataType = R.id.dialog_opendate_rb_price;
                    dateTypeTxt.setText(getContext().getString(R.string.dialog_opendate_last_changeprice));
                    break;
                case onlyTrustStartDate:
                    openDataType = R.id.dialog_opendate_rb_entrust_start;
                    dateTypeTxt.setText(getContext().getString(R.string.dialog_opendate_entrust_begin));
                    break;
                case onlyTrustEndDate:
                    openDataType = R.id.dialog_opendate_rb_entrust_end;
                    dateTypeTxt.setText(getContext().getString(R.string.dialog_opendate_entrust_end));
                    break;
                case estimatedDate:
                    openDataType = R.id.dialog_opendate_rb_anticipate;
                    dateTypeTxt.setText(getContext().getString(R.string.dialog_opendate_estimate_date));
                    break;
                case statusChangedDate:
                    openDataType = R.id.dialog_opendate_rb_changehouse;
                    dateTypeTxt.setText(getContext().getString(R.string.dialog_opendate_change_date));
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
            dateEndTxt.setText(changeToDate(houseDescription.getPropertyDateTo()));
            dateStaTxt.setText(changeToDate(houseDescription.getPropertyDateFrom()));

        }
    }

    private String changeToDate(String date) {
        if (date == null) return null;
        String[] dates = date.split("-");
        return dates[0] + "年" + dates[1] + "月" + dates[2] + "日";
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
            onDateChangeLisenter.onDateChange(houseDescription);
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
            onDateChangeLisenter.onDateChange(houseDescription);
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


    //開盤日期
    public void openDate() {
        OpenDataDialog openDataDialog = new OpenDataDialog(openDataType);
        openDataDialog.setOnDialogClikeLisenter((dialog, viewId, result) -> {
            dialog.dismiss();
            if (openDataType != viewId) {
                openDataType = viewId;
            } else {
                openDataType = 0;
                houseDescription.setIncludedPropertyStatusFrom(null);
                houseDescription.setIncludedPropertyStatusTo(null);
                choiceDateTxt.setVisibility(View.VISIBLE);
                dateTypeTxt.setText(getString(R.string.undate));
                dateStaTxt.setText(null);
                dateEndTxt.setText(null);
                houseDescription.setPropertyDateType(null);
                houseDescription.setPropertyDateFrom(null);
                houseDescription.setPropertyDateTo(null);
                onDateChangeLisenter.onDateChange(houseDescription);
                return;
            }

            dateTypeTxt.setText((String) result);
            if (viewId == R.id.dialog_opendate_rb_changehouse) {
                statuView.setVisibility(View.VISIBLE);
            } else statuView.setVisibility(View.GONE);

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

                    break;
            }

            String dateFrom = houseDescription.getPropertyDateFrom();
            String dateTo = houseDescription.getPropertyDateTo();
            if ((dateFrom == null || dateFrom.equals("") && (dateTo == null || dateTo.equals("")))) {
                Calendar calendar = Calendar.getInstance();
                String dateEnd = calendar.get(Calendar.YEAR) + "年" + calendar.get(Calendar.MONTH) + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日";
                houseDescription.setPropertyDateTo(calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH));
                L.d("OpenDataDialog", houseDescription.getPropertyDateTo());

                calendar.add(Calendar.MONTH, -1);
                String dateStar = calendar.get(Calendar.YEAR) + "年" + calendar.get(Calendar.MONTH) + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日";
                houseDescription.setPropertyDateFrom(calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH));
                L.d("OpenDataDialog", houseDescription.getPropertyDateFrom());
                choiceDateTxt.setVisibility(View.GONE);

                ///
//                houseDescription.setPropertyDateTo(dateEnd);
//                houseDescription.setPropertyDateFrom(dateStar);
                ///

                dateStaTxt.setText(dateStar);
                dateEndTxt.setText(dateEnd);
            }

            onDateChangeLisenter.onDateChange(houseDescription);
        });
        openDataDialog.show(getActivity().getSupportFragmentManager(), "");
    }

    private void showDateDialog(String start, String end) {

        Calendar startCalen = changeToCalendar(start);
        Calendar endCalen = changeToCalendar(end);

        CalendarDialog dialog = new CalendarDialog();
        dialog.setEndCl(endCalen);
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.MONTH, -1);
        dialog.setStartCl(startCalen);
        dialog.setOnDialogOnclikeLisenter((dialog1, view, start1, end1) -> {
            dialog1.dismiss();
            if (start1 == null && end1 == null) {
                choiceDateTxt.setVisibility(View.VISIBLE);
            } else choiceDateTxt.setVisibility(View.GONE);

            String dateStar = null;
            String dateEnd = null;
            if (start1 != null) {
                dateStar = start1.get(Calendar.YEAR) + "年" + (start1.get(Calendar.MONTH) + 1) + "月" + start1.get(Calendar.DAY_OF_MONTH) + "日";
                houseDescription.setPropertyDateFrom(start1.get(Calendar.YEAR) + "-" + (start1.get(Calendar.MONTH) + 1) + "-" + start1.get(Calendar.DAY_OF_MONTH));
                L.d("changeToCalendar_dialog", houseDescription.getPropertyDateFrom());

            }
            if (end1 != null) {
                dateEnd = end1.get(Calendar.YEAR) + "年" + (end1.get(Calendar.MONTH) + 1) + "月" + end1.get(Calendar.DAY_OF_MONTH) + "日";
                houseDescription.setPropertyDateTo(end1.get(Calendar.YEAR) + "-" + (end1.get(Calendar.MONTH) + 1) + "-" + end1.get(Calendar.DAY_OF_MONTH));
                L.d("changeToCalendar_dialog", houseDescription.getPropertyDateTo());

            }

            dateStaTxt.setText(dateStar);
            dateEndTxt.setText(dateEnd);
            onDateChangeLisenter.onDateChange(houseDescription);
        });
        dialog.show(getActivity().getFragmentManager(), "");
    }

    private Calendar changeToCalendar(String date) {

        L.d("changeToCalendar", date);

        if (date == null) return null;
        if (date.equals("")) return null;

        int year = 0;
        int month = 0;
        int day = 0;
        int index = 0;

//        if (date.indexOf("年") != -1) {
//            year = Integer.parseInt(date.substring(index, date.indexOf("年")));
//            index = date.indexOf("年") + 1;
//        }
//
//        if (date.indexOf("月") != -1) {
//            month = Integer.parseInt(date.substring(index, date.indexOf("月"))) - 1;
//            index = date.indexOf("月") + 1;
//        }
//
//        if (date.indexOf("日") != -1) {
//            day = Integer.parseInt(date.substring(index, date.indexOf("日")));
//            index = date.indexOf("日");
//        }
//
        String[] dates = date.split("-");
        Calendar calendar = Calendar.getInstance();
        L.d("changeToCalendar", dates[0] + " " + dates[1] + " " + dates[2]);
        calendar.set(Calendar.YEAR, Integer.parseInt(dates[0]));
        calendar.set(Calendar.MONTH, Integer.parseInt(dates[1]) - 1);
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dates[2]));

//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.YEAR, year);
//        calendar.set(Calendar.MONTH, month);
//        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar;

    }


    public interface OnDateChangeLisenter {
        void onDateChange(HouseDescription description);
    }

}
