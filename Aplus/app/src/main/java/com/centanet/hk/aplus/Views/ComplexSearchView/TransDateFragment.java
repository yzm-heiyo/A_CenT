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
import com.centanet.hk.aplus.Views.Dialog.SaleBuyDialog;
import com.centanet.hk.aplus.Views.Dialog.StatusDialog;
import com.centanet.hk.aplus.Views.Dialog.TranDateDialog;
import com.centanet.hk.aplus.bean.http.HouseDescription;
import com.centanet.hk.aplus.bean.translist.TransListRequest;
import com.centanet.hk.aplus.common.APSystemParameterType;
import com.centanet.hk.aplus.manager.AppSystemParamsManager;
import com.centanet.hk.aplus.manager.ApplicationManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import static com.centanet.hk.aplus.Views.Dialog.TranDateDialog.DATE_COMPLETE;
import static com.centanet.hk.aplus.Views.Dialog.TranDateDialog.DATE_FORMAL;
import static com.centanet.hk.aplus.Views.Dialog.TranDateDialog.DATE_PRELIM;
import static com.centanet.hk.aplus.Views.Dialog.TranDateDialog.DATE_RENT;
import static com.centanet.hk.aplus.Views.Dialog.TranDateDialog.DATE_TRANSACTION;
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

public class TransDateFragment extends Fragment implements View.OnClickListener {


    private TextView choiceDateTxt, dateStaTxt, dateEndTxt, dateTypeTxt;
    private TextView staStartTxt, staEndTxt;
    private View statuView, dateContent;
    private int openDataType;
    private TransListRequest request;
    private List<String> staBeginSelectList;
    private List<String> staEndSelectList;
    private OnDateChangeLisenter onDateChangeLisenter;
    public static String ARGUMENT = "SysItemFragment";
    private int dateType = DATE_TRANSACTION;

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
        reCoverView(request);
        return view;
    }

    private void init() {
        request = (TransListRequest) getArguments().get(ARGUMENT);
        L.d("HouseDescription", request.toString());
        if (request == null)
            request = new TransListRequest();
    }

    public static TransDateFragment newInstance(TransListRequest argument) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARGUMENT, argument);
        TransDateFragment contentFragment = new TransDateFragment();
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
                String[] date = getCurrentSelectDate();
                L.d("getCurrentSelectDate", date[0] + " : " + date[1]);
                showDateDialog(date[0], date[1]);
                break;
        }
    }

    private String[] getCurrentSelectDate() {
        String[] date = new String[2];

        if (request.getTransactionDateFrom() != null || request.getTransactionDateTo() != null) {
            date[0] = request.getTransactionDateFrom();
            date[1] = request.getTransactionDateTo();
        }
        if (request.getPrelimDateFrom() != null || request.getPrelimDateTo() != null) {
            date[0] = request.getPrelimDateFrom();
            date[1] = request.getPrelimDateTo();
        }
        if (request.getFormalDateFrom() != null || request.getFormalDateTo() != null) {
            date[0] = request.getFormalDateFrom();
            date[1] = request.getFormalDateTo();
        }
        if (request.getCompleteDateFrom() != null || request.getCompleteDateTo() != null) {
            date[0] = request.getCompleteDateFrom();
            date[1] = request.getCompleteDateTo();
        }
        if (request.getRentDateFrom() != null || request.getRentDateTo() != null) {
            date[0] = request.getRentDateFrom();
            date[1] = request.getRentDateTo();
        }

        return date;
    }


    private void reCoverView(TransListRequest request) {
        String[] s = getCurrentSelectDate();
        if (!TextUtil.isEmply(s[0]) || !TextUtil.isEmply(s[1])) {
            choiceDateTxt.setVisibility(View.GONE);
            if (!TextUtil.isEmply(s[0])) {
                String[] time = s[0].split("-");
                dateStaTxt.setText(time[0] + "年" + time[1] + "月" + time[2] + "日");
            }
            if (!TextUtil.isEmply(s[0])) {
                String[] time = s[1].split("-");
                dateEndTxt.setText(time[0] + "年" + time[1] + "月" + time[2] + "日");
//                dateEndTxt.setText(s[1]);
            }
        }

        switch (getCurrentType()) {
            case DATE_TRANSACTION:
                dateTypeTxt.setText(R.string.record_transaction_bargain);
                break;
            case DATE_PRELIM:
                dateTypeTxt.setText(R.string.record_transaction_appointment);
                break;
            case DATE_FORMAL:
                dateTypeTxt.setText(R.string.record_transaction_official);
                break;
            case DATE_COMPLETE:
                dateTypeTxt.setText(R.string.record_transaction_finish);
                break;
            case DATE_RENT:
                dateTypeTxt.setText(R.string.record_transaction_rentdate);
                break;
        }
    }

    private void setDateTypeText(int type) {

        String start = null;
        String end = null;
        if (dateStaTxt.getText().equals("") || dateEndTxt.getText().equals("")) {
            choiceDateTxt.setVisibility(View.GONE);
            Calendar calendar = Calendar.getInstance();
            end = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
            dateEndTxt.setText(calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日");
            calendar.add(Calendar.MONTH, -1);
            start = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
            dateStaTxt.setText(calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日");
        } else {
            start = changeToDate(dateStaTxt.getText().toString());
            end = changeToDate(dateEndTxt.getText().toString());
        }

        L.d("setDateTypeText", start + " : " + end);

        switch (type) {
            case DATE_TRANSACTION:
                dateTypeTxt.setText(R.string.record_transaction_bargain);
                request.setTransactionDateFrom(start);
                request.setTransactionDateTo(end);
                break;
            case DATE_PRELIM:
                dateTypeTxt.setText(R.string.record_transaction_appointment);
                request.setPrelimDateFrom(start);
                request.setPrelimDateTo(end);
                break;
            case DATE_FORMAL:
                dateTypeTxt.setText(R.string.record_transaction_official);
                request.setFormalDateFrom(start);
                request.setFormalDateTo(end);
                break;
            case DATE_COMPLETE:
                dateTypeTxt.setText(R.string.record_transaction_finish);
                request.setCompleteDateFrom(start);
                request.setCompleteDateTo(end);
                break;
            case DATE_RENT:
                dateTypeTxt.setText(R.string.record_transaction_rentdate);
                request.setRentDateFrom(start);
                request.setRentDateTo(end);
                break;
        }
    }


    //開盤日期
    public void openDate() {
        TranDateDialog tranDateDialog = new TranDateDialog();
//        tranDateDialog.selectView();
        L.d("openDate", getCurrentType() + "");
        tranDateDialog.setItemSelectAt(getCurrentType());
        tranDateDialog.setOnItemClickLisenter((dialog, type) -> {
            dialog.dismiss();
            clearDate();

            if (dateType != type) {
                dateType = type;
                setDateTypeText(type);
            } else {
                choiceDateTxt.setVisibility(View.VISIBLE);
                dateTypeTxt.setText(getString(R.string.undate));
                dateType = 0;
                dateStaTxt.setText(null);
                dateEndTxt.setText(null);
            }

            L.d("transType", "" + type);
            if (onDateChangeLisenter != null) onDateChangeLisenter.onDateChange(request);
        });
        tranDateDialog.show(getActivity().getFragmentManager(), "");
    }


    private void showDateDialog(String start, String end) {

        Calendar startCalen = changeToCalendar(start);
        Calendar endCalen = changeToCalendar(end);

        if (startCalen == null && endCalen == null) {
            endCalen = Calendar.getInstance();
            Calendar c = Calendar.getInstance();
            c.add(Calendar.MONTH, -1);
            startCalen = c;
        }

        CalendarDialog dialog = new CalendarDialog();
        dialog.setEndCl(endCalen);
        dialog.setStartCl(startCalen);
        dialog.setOnDialogOnclikeLisenter((dialog1, view, start1, end1) -> {
            dialog1.dismiss();
            if (start1 == null && end1 == null) {
                choiceDateTxt.setVisibility(View.VISIBLE);
            } else choiceDateTxt.setVisibility(View.GONE);
            setDateStaToEnd(dateType, start1, end1);
            String dateStar = null;
            String dateEnd = null;
            if (start1 != null)
                dateStar = start1.get(Calendar.YEAR) + "年" + (start1.get(Calendar.MONTH) + 1) + "月" + start1.get(Calendar.DAY_OF_MONTH) + "日";
            if (end1 != null)
                dateEnd = end1.get(Calendar.YEAR) + "年" + (end1.get(Calendar.MONTH) + 1) + "月" + end1.get(Calendar.DAY_OF_MONTH) + "日";

            L.d("SDSSDSikeLisenter", dateStar + " : " + dateEnd);
            dateStaTxt.setText(dateStar);
            dateEndTxt.setText(dateEnd);
            onDateChangeLisenter.onDateChange(request);
        });
        dialog.show(getActivity().getFragmentManager(), "");

    }

    private void setDateStaToEnd(int type, Calendar start1, Calendar end1) {

        String start = start1.get(Calendar.YEAR) + "-" + (start1.get(Calendar.MONTH) + 1) + "-" + start1.get(Calendar.DAY_OF_MONTH);
        String end = end1.get(Calendar.YEAR) + "-" + (end1.get(Calendar.MONTH) + 1) + "-" + end1.get(Calendar.DAY_OF_MONTH);

        switch (type) {
            case DATE_TRANSACTION:
                dateTypeTxt.setText(R.string.record_transaction_bargain);
                request.setTransactionDateFrom(start);
                request.setTransactionDateTo(end);
                L.d("setDateStaToEnd", request.getTransactionDateFrom() + " : " + request.getTransactionDateTo());

                break;
            case DATE_PRELIM:
                dateTypeTxt.setText(R.string.record_transaction_appointment);
                request.setPrelimDateFrom(start);
                request.setPrelimDateTo(end);
                L.d("setDateStaToEnd", request.getPrelimDateFrom() + " : " + request.getPrelimDateTo());


                break;
            case DATE_FORMAL:
                dateTypeTxt.setText(R.string.record_transaction_official);
                request.setFormalDateFrom(start);
                request.setFormalDateTo(end);
                L.d("setDateStaToEnd", request.getFormalDateFrom() + " : " + request.getFormalDateTo());

                break;
            case DATE_COMPLETE:
                dateTypeTxt.setText(R.string.record_transaction_finish);
                request.setCompleteDateFrom(start);
                request.setCompleteDateTo(end);
                L.d("setDateStaToEnd", request.getCompleteDateFrom() + " : " + request.getCompleteDateTo());

                break;
            case DATE_RENT:
                dateTypeTxt.setText(R.string.record_transaction_rentdate);
                request.setRentDateFrom(start);
                request.setRentDateTo(end);
                L.d("setDateStaToEnd", request.getCompleteDateTo() + " : " + request.getRentDateTo());

                break;
        }


    }

    private void clearDate() {
        request.setCompleteDateTo(null);
        request.setCompleteDateFrom(null);

        request.setTransactionDateFrom(null);
        request.setTransactionDateTo(null);

        request.setPrelimDateFrom(null);
        request.setPrelimDateTo(null);

        request.setFormalDateFrom(null);
        request.setFormalDateTo(null);

        request.setRentDateFrom(null);
        request.setRentDateTo(null);
    }

    public int getCurrentType() {
        int i = -1;
        if (request.getTransactionDateFrom() != null || request.getTransactionDateTo() != null) {
            i = DATE_TRANSACTION;
            L.d("DATE_TRANSACTION", request.getTransactionDateFrom() + " : " + request.getTransactionDateTo());
        }
        if (request.getPrelimDateFrom() != null || request.getPrelimDateTo() != null) {
            i = DATE_PRELIM;
            L.d("DATE_PRELIM", request.getPrelimDateFrom() + " : " + request.getPrelimDateTo());
        }
        if (request.getFormalDateFrom() != null || request.getFormalDateTo() != null) {
            i = DATE_FORMAL;
            L.d("DATE_FORMAL", request.getFormalDateFrom() + " : " + request.getFormalDateTo());
        }
        if (request.getCompleteDateFrom() != null || request.getCompleteDateTo() != null) {
            i = DATE_COMPLETE;
            L.d("DATE_COMPLETE", request.getCompleteDateFrom() + " : " + request.getCompleteDateTo());
        }
        if (request.getRentDateFrom() != null || request.getRentDateTo() != null) {
            i = DATE_RENT;
            L.d("DATE_RENT", request.getRentDateFrom() + " : " + request.getRentDateTo());
        }
        return i;
    }

    private String changeToDate(String date) {
        if (date == null) return null;
        if (date.equals("")) return null;

        L.d("changeToCalendar", date);

        int year = 0;
        int month = 0;
        int day = 0;
        int index = 0;

        if (date.indexOf("年") != -1) {
            year = Integer.parseInt(date.substring(index, date.indexOf("年")));
            index = date.indexOf("年") + 1;
        }

        if (date.indexOf("月") != -1) {
            month = Integer.parseInt(date.substring(index, date.indexOf("月"))) - 1;
            index = date.indexOf("月") + 1;
        }

        if (date.indexOf("日") != -1) {
            day = Integer.parseInt(date.substring(index, date.indexOf("日")));
            index = date.indexOf("日");
        }

        return year + "-" + (month + 1) + "-" + day;

    }

    private Calendar changeToCalendar(String date) {
        if (date == null) return null;
        if (date.equals("")) return null;

        L.d("changeToCalendar", date);

        int year = 0;
        int month = 0;
        int day = 0;
        int index = 0;

        if (!TextUtil.isEmply(date)) {
            String[] time = date.split("-");
            year = Integer.parseInt(time[0]);
            month = Integer.parseInt(time[1]) - 1;
            day = Integer.parseInt(time[2]);
        }
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

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar;

    }


    public interface OnDateChangeLisenter {
        void onDateChange(TransListRequest request);
    }

}
