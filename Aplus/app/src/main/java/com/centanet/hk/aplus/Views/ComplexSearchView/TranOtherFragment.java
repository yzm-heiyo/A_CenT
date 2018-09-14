package com.centanet.hk.aplus.Views.ComplexSearchView;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Views.Dialog.CalendarDialog;
import com.centanet.hk.aplus.Views.Dialog.OpenDataDialog;
import com.centanet.hk.aplus.Views.Dialog.SaleBuyDialog;
import com.centanet.hk.aplus.Views.Dialog.StatusDialog;
import com.centanet.hk.aplus.Views.Dialog.TranDateDialog;
import com.centanet.hk.aplus.Views.Dialog.WheelViewDialog;
import com.centanet.hk.aplus.Views.basic.BaseFragment;
import com.centanet.hk.aplus.Widgets.ClearEditText;
import com.centanet.hk.aplus.bean.http.HouseDescription;
import com.centanet.hk.aplus.bean.translist.TransListRequest;
import com.centanet.hk.aplus.common.APSystemParameterType;
import com.centanet.hk.aplus.manager.AppSystemParamsManager;
import com.centanet.hk.aplus.manager.ApplicationManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import static com.centanet.hk.aplus.Views.Dialog.SaleBuyDialog.CONTACT_TYPE_BUY;
import static com.centanet.hk.aplus.Views.Dialog.SaleBuyDialog.CONTACT_TYPE_SALE;
import static com.centanet.hk.aplus.Views.Dialog.SaleBuyDialog.CONTACT_TYPE_SALE_BUY;
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

/**
 * Created by yangzm4 on 2018/7/24.
 */

public class TranOtherFragment extends BaseFragment implements View.OnClickListener {

    public static String ARGUMENT = "SysItemFragment";
    private ClearEditText ownerEdit, phoneEdit;
    private OnOtherChangeLisenter onOtherChangeLisenter;
    private List<String> staBeginSelectList;
    private List<String> staEndSelectList;
    private TransListRequest request;
    private TextView dateTypeTxt, choiceDateTxt, dateStaTxt, dateEndTxt, contactTxt;
    private int openDataType;
    private int dateType;
    private View dateContent;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnOtherChangeLisenter) {
            onOtherChangeLisenter = (OnOtherChangeLisenter) context;
        } else
            throw new IllegalArgumentException("Activity  Must Implements RentFragment.OnRentChangeLisenter");
    }

    public static TranOtherFragment newInstance(TransListRequest argument) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARGUMENT, argument);
        TranOtherFragment contentFragment = new TranOtherFragment();
        contentFragment.setArguments(bundle);
        return contentFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tranother, null, false);
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
        staBeginSelectList = new ArrayList<>();
        staEndSelectList = new ArrayList<>();
    }

    private void reCoverView(TransListRequest request) {
        ownerEdit.setText(request.getContactName());
        phoneEdit.setText(request.getContactValue());
    }

    private void initLisenter() {

        dateTypeTxt.setOnClickListener(this);
//        choiceDateTxt.setOnClickListener(this);
        dateContent.setOnClickListener(this);
        contactTxt.setOnClickListener(this);


        ownerEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                request.setContactName(ownerEdit.getText().toString());
                if(request.getContactSearchType()==null||request.getContactSearchType().equals(""))request.setContactSearchType("1");
                onOtherChangeLisenter.onOtherChange(request);
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
                request.setContactValue(phoneEdit.getText().toString());
                onOtherChangeLisenter.onOtherChange(request);
            }
        });

    }

    private void initView(View view) {
        ownerEdit = view.findViewById(R.id.option_edit_ownername);
        phoneEdit = view.findViewById(R.id.other_edit_phone);

        choiceDateTxt = view.findViewById(R.id.other_txt_choicedate);
        dateStaTxt = view.findViewById(R.id.other_txt_datesta);
        dateEndTxt = view.findViewById(R.id.other_txt_dateend);

        dateTypeTxt = view.findViewById(R.id.other_txt_date);
        dateContent = view.findViewById(R.id.other_ll_choicedate);

        contactTxt = view.findViewById(R.id.other_txt_ownertype);
    }

    //開盤日期
    public void openDate() {

        TranDateDialog tranDateDialog = new TranDateDialog();
//        tranDateDialog.selectView();
        tranDateDialog.setItemSelectAt(getCurrentType());
        tranDateDialog.setOnItemClickLisenter((dialog, type) -> {
            dialog.dismiss();
            clearDate();
            dateType = type;
            L.d("transType", "" + type);
            setDateTypeText(type);
        });
        tranDateDialog.show(getActivity().getFragmentManager(), "");

    }

    public void openContactTypeDialog() {
        SaleBuyDialog saleBuyDialog = new SaleBuyDialog();
        L.d("getContactSearchType", request.getContactSearchType());
        if (request.getContactSearchType() != null)
            saleBuyDialog.setItemSelectAt(Integer.parseInt(request.getContactSearchType()));

        saleBuyDialog.setOnItemClickLisenter((dialog, type) -> {
            dialog.dismiss();
            setContactTypeText(type);
            request.setContactSearchType(type + "");
        });
        saleBuyDialog.show(getActivity().getFragmentManager(), "");
    }

    private void setDateTypeText(int type) {

        if (dateStaTxt.getText().equals("") && dateEndTxt.getText().equals("")) {
            choiceDateTxt.setVisibility(View.GONE);
            Calendar calendar = Calendar.getInstance();
            dateEndTxt.setText(calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日");
            calendar.add(Calendar.MONTH, -1);
            dateStaTxt.setText(calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日");
        }

        switch (type) {
            case DATE_TRANSACTION:
                dateTypeTxt.setText(R.string.record_transaction_bargain);
                request.setTransactionDateFrom(dateStaTxt.getText().toString());
                request.setTransactionDateTo(dateEndTxt.getText().toString());
                break;
            case DATE_PRELIM:
                dateTypeTxt.setText(R.string.record_transaction_appointment);
                request.setPrelimDateFrom(dateStaTxt.getText().toString());
                request.setPrelimDateTo(dateEndTxt.getText().toString());
                break;
            case DATE_FORMAL:
                dateTypeTxt.setText(R.string.record_transaction_official);
                request.setFormalDateFrom(dateStaTxt.getText().toString());
                request.setFormalDateTo(dateEndTxt.getText().toString());
                break;
            case DATE_COMPLETE:
                dateTypeTxt.setText(R.string.record_transaction_finish);
                request.setCompleteDateFrom(dateStaTxt.getText().toString());
                request.setCompleteDateTo(dateEndTxt.getText().toString());
                break;
            case DATE_RENT:
                dateTypeTxt.setText(R.string.record_transaction_rentdate);
                request.setRentDateFrom(dateStaTxt.getText().toString());
                request.setRentDateTo(dateEndTxt.getText().toString());
                break;
        }
    }

    private void clearDate() {
        request.setCompleteDateTo(null);
        request.setCompleteDateTo(null);

        request.setTransactionDateFrom(null);
        request.setTransactionDateTo(null);

        request.setPrelimDateFrom(null);
        request.setPrelimDateTo(null);

        request.setFormalDateFrom(null);
        request.setFormalDateTo(null);

        request.setRentDateFrom(null);
        request.setRentDateTo(null);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.other_txt_date:
                openDate();
                break;
            case R.id.other_ll_choicedate:
                showDateDialog("", "");
                break;
            case R.id.other_txt_ownertype:
                openContactTypeDialog();
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
            String dateStar = start1.get(Calendar.YEAR) + "-" + (start1.get(Calendar.MONTH) + 1) + "-" + start1.get(Calendar.DAY_OF_MONTH);
            String dateEnd = end1.get(Calendar.YEAR) + "-" + (end1.get(Calendar.MONTH) + 1) + "-" + end1.get(Calendar.DAY_OF_MONTH);
            dateStaTxt.setText(dateStar);
            dateEndTxt.setText(dateEnd);
            setDateStaToEnd(dateType);
            onOtherChangeLisenter.onOtherChange(request);
        });
        dialog.show(getActivity().getFragmentManager(), "");
    }

    private void setDateStaToEnd(int type) {

        switch (type) {
            case DATE_TRANSACTION:
                dateTypeTxt.setText(R.string.record_transaction_bargain);
                request.setTransactionDateFrom(dateStaTxt.getText().toString());
                request.setTransactionDateTo(dateEndTxt.getText().toString());
                break;
            case DATE_PRELIM:
                dateTypeTxt.setText(R.string.record_transaction_appointment);
                request.setPrelimDateFrom(dateStaTxt.getText().toString());
                request.setPrelimDateTo(dateEndTxt.getText().toString());
                break;
            case DATE_FORMAL:
                dateTypeTxt.setText(R.string.record_transaction_official);
                request.setFormalDateFrom(dateStaTxt.getText().toString());
                request.setFormalDateTo(dateEndTxt.getText().toString());
                break;
            case DATE_COMPLETE:
                dateTypeTxt.setText(R.string.record_transaction_finish);
                request.setCompleteDateFrom(dateStaTxt.getText().toString());
                request.setCompleteDateTo(dateEndTxt.getText().toString());
                break;
            case DATE_RENT:
                dateTypeTxt.setText(R.string.record_transaction_rentdate);
                request.setRentDateFrom(dateStaTxt.getText().toString());
                request.setRentDateTo(dateEndTxt.getText().toString());
                break;
        }

    }


    public void setContactTypeText(int contactTypeText) {
        switch (contactTypeText) {
            case CONTACT_TYPE_SALE_BUY:
                contactTxt.setText(R.string.sale_buy_people);
                break;
            case CONTACT_TYPE_BUY:
                contactTxt.setText(R.string.buy_people);
                break;
            case CONTACT_TYPE_SALE:
                contactTxt.setText(R.string.sale_people);
                break;
        }
    }

    public int getCurrentType() {
        int i = -1;
        if (request.getTransactionDateFrom() != null || request.getTransactionDateTo() != null) {
            i = DATE_TRANSACTION;
        }
        if (request.getPrelimDateFrom() != null || request.getPrelimDateTo() != null) {
            i = DATE_PRELIM;
        }
        if (request.getFormalDateFrom() != null || request.getFormalDateTo() != null) {
            i = DATE_FORMAL;
        }
        if (request.getCompleteDateFrom() != null || request.getCompleteDateTo() != null) {
            i = DATE_COMPLETE;
        }
        if (request.getRentDateFrom() != null || request.getRentDateTo() != null) {
            i = DATE_RENT;
        }
        return i;
    }


    public interface OnOtherChangeLisenter {
        void onOtherChange(TransListRequest description);
    }
}
