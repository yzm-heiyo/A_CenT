package com.centanet.hk.aplus.Views.ComplexSearchView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.centanet.hk.aplus.MyApplication;
import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Views.Dialog.OpenDataDialog;
import com.centanet.hk.aplus.Views.Dialog.StatusDialog;
import com.centanet.hk.aplus.Views.basic.BasicActivty;
import com.centanet.hk.aplus.Widgets.CheckBoxLayout;
import com.centanet.hk.aplus.Widgets.MyRadioGroup;
import com.centanet.hk.aplus.bean.complexSearch.Operation;
import com.centanet.hk.aplus.bean.http.HouseDescription;
import com.centanet.hk.aplus.bean.params.SystemParam;
import com.centanet.hk.aplus.bean.params.SystemParamItems;
import com.centanet.hk.aplus.manager.ApplicationManager;
import com.githang.statusbar.StatusBarCompat;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.centanet.hk.aplus.MyApplication.getContext;
import static com.centanet.hk.aplus.common.CommandField.APPropertyDateType.changePriceDate;
import static com.centanet.hk.aplus.common.CommandField.APPropertyDateType.estimatedDate;
import static com.centanet.hk.aplus.common.CommandField.APPropertyDateType.lasetUpdate;
import static com.centanet.hk.aplus.common.CommandField.APPropertyDateType.lastFollowDate;
import static com.centanet.hk.aplus.common.CommandField.APPropertyDateType.onlyTrustEndDate;
import static com.centanet.hk.aplus.common.CommandField.APPropertyDateType.onlyTrustStartDate;
import static com.centanet.hk.aplus.common.CommandField.APPropertyDateType.registDate;
import static com.centanet.hk.aplus.common.CommandField.APPropertyDateType.statusChangedDate;

/**
 * Created by mzh1608258 on 2018/1/8.
 */

public class ComplexActivity extends BasicActivty implements RadioGroup.OnCheckedChangeListener, View.OnClickListener, MyRadioGroup.OnCheckedChangeListener, CheckBoxLayout.OnItemViewCheckChangerListener {

    private static final int TYPE_AREA_REALLY = 1;
    private static final int TYPE_AREA_USE = 2;
    private static final int TYPE_PRICE_REALLY_SALE = 3;
    private static final int TYPE_PRICE_REALLY_RENT = 4;
    private static final int TYPE_PRICE_USE_SALE = 1;
    private static final int TYPE_PRICE_USE_RENT = 2;
    private TextView textDate, textDatecompletionBegin, textDatecompletionEnd, textDateOpenDateBegin, textDateOpenDateEnd, statusBegin, statusEnd;
    private View statusBeginLayout, statusEndLayout;
    private IMoreActivity more;
    private int areaType = TYPE_AREA_REALLY;
    private int priceType = TYPE_PRICE_USE_SALE;
    private int openDataType;
    private int ssdSelectID;
    private View statusView;
    private String ssd = null;
    private TextView dateTipTxt, search, reset;
    private List<Integer> staBeginSelectList, staEndSelectList;
    private EditText areaLft, areaRight, priceLeft, priceRight, owner, phone;
    private CheckBoxLayout direction, interval, houseTag;
    private Map<String, String> houseTagValue, intervalValue, directionValue;

    private MyRadioGroup ssdRG;
    private RadioGroup priceRG, areaRG;
    private CheckBox keyCb, greenTabCb;

    private Date completeDateStart, completeDateEnd, changeDateStart, changeDateEnd;

    private List<String> directionList, intervalList, houseTagList;
    private List<String> staValueBeginList, staValueEndList;

    private MyApplication application;
    private Operation operation;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_complex);
        setViews();
        init();
        setListeners();
        StatusBarCompat.setStatusBarColor(this, Color.parseColor("#BB2E2D"), false);
    }

    private void init() {

        application = (MyApplication) getApplication();
//        Operation operation = application.getComplexOperation();
        Bundle bundle = getIntent().getExtras();
        operation = (Operation) bundle.get("operation");

        staBeginSelectList = new ArrayList<>();
        staEndSelectList = new ArrayList<>();
        if (operation != null)
            recoverView(operation);
        else operation = new Operation();

        houseTagValue = new HashMap<>();
        intervalValue = new HashMap<>();
        houseTagValue = new HashMap<>();

        SystemParam directionSys = ApplicationManager.getDirectionSystemParam();
        List<String> directionList = getParams(directionSys);
        direction.addItem(directionList, this.directionList);
        directionValue = getKeyValue(directionSys);

        SystemParam intervalSys = ApplicationManager.getIntervalSystemParam();
        List<String> intervalList = getParams(intervalSys);
        interval.addItem(intervalList, this.intervalList);
        intervalValue = getKeyValue(intervalSys);

        SystemParam tagSys = ApplicationManager.getLabelSystenParam();
        List<String> tagList = getParams(tagSys);
        houseTag.addItem(tagList, houseTagList);
        houseTagValue = getKeyValue(tagSys);
    }

    private void recoverView(Operation operation) {

        directionList = operation.getDirectionList() != null ? operation.getDirectionList() : new ArrayList<String>();
        intervalList = operation.getIntervalList() != null ? operation.getIntervalList() : new ArrayList<String>();
        houseTagList = operation.getHouseTagList() != null ? operation.getHouseTagList() : new ArrayList<String>();

        areaType = operation.getAreaType();
        int checkId = areaType == TYPE_AREA_USE ? R.id.complex_rb_use_area : R.id.complex_rb_reallly_area;
        areaRG.check(checkId);

        if (operation.getDateTypeText() != null && operation.getDateTypeText() != "")
            dateTipTxt.setText(operation.getDateTypeText());

        priceType = operation.getPriceType();
        switch (priceType) {
            case TYPE_PRICE_USE_RENT:
                priceRG.check(R.id.complex_rb_use_rent);
                break;
            case TYPE_PRICE_USE_SALE:
                priceRG.check(R.id.complex_rb_use_sale);
                break;
            case TYPE_PRICE_REALLY_RENT:
                priceRG.check(R.id.complex_rb_really_rent);
                break;
            case TYPE_PRICE_REALLY_SALE:
                priceRG.check(R.id.complex_rb_really_sale);
                break;
        }

        ssdSelectID = operation.getSsdSelectId();
        ssdRG.setOnCheckedChangeListener(this);
        ssdRG.check(ssdSelectID);
        ssd = operation.getSsdType();

        openDataType = operation.getDateType() == 0 ? R.id.dialog_opendate_rb_default : operation.getDateType();
        if (openDataType == R.id.dialog_opendate_rb_changehouse) {
            statusView.setVisibility(View.VISIBLE);
            staBeginSelectList = operation.getStaBeginSelectList();
            staEndSelectList = operation.getStaEndSelectList();
            statusBegin.setText(operation.getStaBeginText() == "" ? null : operation.getStaBeginText());
            statusEnd.setText(operation.getStaEndText() == "" ? null : operation.getStaEndText());
        }

        staValueBeginList = operation.getStaValueStart();
        staValueEndList = operation.getStaValueEnd();

        changeDateStart = operation.getChangeDateStart();
        changeDateEnd = operation.getChangeDateEnd();
        completeDateStart = operation.getCompleteDateStart();
        completeDateEnd = operation.getCompleteDateEnd();

        areaLft.setText(operation.getAreaFrom());
        areaRight.setText(operation.getAreaTo());
        priceLeft.setText(operation.getPriceFrom());
        priceRight.setText(operation.getPriceTo());

        textDatecompletionBegin.setText(operation.getCompleteStartText());
        textDatecompletionEnd.setText(operation.getCompleteEndText());
        textDateOpenDateBegin.setText(operation.getDateStartText());
        textDateOpenDateEnd.setText(operation.getDateEndText());

        owner.setText(operation.getOwner());
        phone.setText(operation.getPhone());

        greenTabCb.setChecked(operation.isGreenTabCheck());
        keyCb.setChecked(operation.isKeyCheck());
    }

    //獲得類型參數選項内容
    private List<String> getParams(SystemParam systemParam) {
        List<String> params = new ArrayList<>();
        List<SystemParamItems> directionList = systemParam.getSystemParamItems();
        for (SystemParamItems d : directionList) {
            params.add(d.getItemText());
        }
        return params;
    }


    //開盤日期
    public void openDate(View view) {
        OpenDataDialog openDataDialog = new OpenDataDialog(openDataType);
        openDataDialog.setOnDialogClikeLisenter(new OpenDataDialog.onDialogOnclikeLisenter() {
            @Override
            public void onClike(Dialog dialog, int viewID, String result) {
                dialog.dismiss();
                openDataType = viewID;
                dateTipTxt.setText(result);
                if (viewID == R.id.dialog_opendate_rb_changehouse) {
                    statusView.setVisibility(View.VISIBLE);
                    Calendar calendar = Calendar.getInstance();
                    textDateOpenDateBegin.setText(calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH)) + "-" + calendar.get(Calendar.DAY_OF_MONTH));
                    textDateOpenDateEnd.setText(calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH));
                    changeDateEnd = calendar.getTime();
                    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) - 1, calendar.get(Calendar.DAY_OF_MONTH));
                    changeDateStart = calendar.getTime();
                } else statusView.setVisibility(View.GONE);
            }
        });
        openDataDialog.show(getSupportFragmentManager(), "");
    }

    //保存鍵值對
    private Map<String, String> getKeyValue(SystemParam systemParam) {
        Map<String, String> value = new HashMap<>();
        List<SystemParamItems> directionList = systemParam.getSystemParamItems();
        for (SystemParamItems d : directionList) {
            value.put(d.getItemText(), d.getItemValue());
        }
        return value;
    }

    //獲得對應的value
    private List<String> getValue(Map<String, String> keyValue, List<String> keys) {
        List<String> values = new ArrayList<>();
        for (Map.Entry<String, String> entry : keyValue.entrySet()) {
            for (String key : keys) {
                if (key.equals(entry.getKey())) values.add(entry.getValue());
            }
        }
        return values;
    }


    private void setViews() {
        textDate = this.findViewById(R.id.activity_more_open_date_text);
        textDatecompletionBegin = this.findViewById(R.id.activity_more_completion_begin);
        textDatecompletionEnd = this.findViewById(R.id.activity_more_completion_end);
        textDateOpenDateBegin = this.findViewById(R.id.activity_more_opendate_begin);
        textDateOpenDateEnd = this.findViewById(R.id.activity_more_opendate_end);
        statusBeginLayout = this.findViewById(R.id.activity_more_status_start_layout);
        statusEndLayout = this.findViewById(R.id.activity_more_status_end_layout);
        statusBegin = this.findViewById(R.id.activity_more_status_start);
        statusEnd = this.findViewById(R.id.activity_more_status_end);
        ssdRG = this.findViewById(R.id.activity_more_ssd_radiogroup);

        priceRG = findViewById(R.id.complex_price_group);
        areaRG = findViewById(R.id.complex_area_group);


        dateTipTxt = findViewById(R.id.opendate_txt_date);

        search = findViewById(R.id.complex_txt_search);
        reset = findViewById(R.id.complex_txt_reset);

        statusView = findViewById(R.id.complex_ll_status);

        priceLeft = findViewById(R.id.complex_edit_price_left);
        priceLeft.setInputType(InputType.TYPE_CLASS_NUMBER);
        priceRight = findViewById(R.id.complex_edit_price_right);
        priceRight.setInputType(InputType.TYPE_CLASS_NUMBER);
        areaLft = findViewById(R.id.complex_edit_area_left);
        areaLft.setInputType(InputType.TYPE_CLASS_NUMBER);
        areaRight = findViewById(R.id.complex_edit_area_right);
        areaRight.setInputType(InputType.TYPE_CLASS_NUMBER);

        owner = findViewById(R.id.complex_edit_owner);
        phone = findViewById(R.id.complex_edit_phone);

        direction = findViewById(R.id.complex_cb_direction);
        direction.setItemContentLayoutID(R.layout.item_complex_btn);
        direction.setLeftRightSpace(25);
        direction.setRowSpace(18);

        interval = findViewById(R.id.complex_cb_interval);
        interval.setItemContentLayoutID(R.layout.item_complex_btn);
        interval.setLeftRightSpace(25);
        interval.setRowSpace(18);

        houseTag = findViewById(R.id.complex_cb_tag);
        houseTag.setItemContentLayoutID(R.layout.item_complex_btn);
        houseTag.setLeftRightSpace(25);
        houseTag.setRowSpace(18);

        greenTabCb = findViewById(R.id.complex_cb_green_tab_price);
        keyCb = findViewById(R.id.complex_cb_key);
    }


    private void setListeners() {
        textDatecompletionBegin.setOnClickListener(this);
        textDatecompletionEnd.setOnClickListener(this);
        textDateOpenDateBegin.setOnClickListener(this);
        textDateOpenDateEnd.setOnClickListener(this);
        statusBeginLayout.setOnClickListener(this);
        statusEndLayout.setOnClickListener(this);

        priceRG.setOnCheckedChangeListener(this);
        areaRG.setOnCheckedChangeListener(this);

        search.setOnClickListener(this);
        reset.setOnClickListener(this);

        direction.setOnItemViewCheckChangerListener(this);
        houseTag.setOnItemViewCheckChangerListener(this);
        interval.setOnItemViewCheckChangerListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.complex_rb_reallly_area:
                areaType = TYPE_AREA_REALLY;
                cleanAreaText();
                break;
            case R.id.complex_rb_use_area:
                areaType = TYPE_AREA_USE;
                cleanAreaText();
                break;
            case R.id.complex_rb_really_rent:
                priceType = TYPE_PRICE_REALLY_RENT;
                cleanPriceText();
                break;
            case R.id.complex_rb_really_sale:
                priceType = TYPE_PRICE_REALLY_SALE;
                cleanPriceText();
                break;
            case R.id.complex_rb_use_rent:
                priceType = TYPE_PRICE_USE_RENT;
                cleanPriceText();
                break;
            case R.id.complex_rb_use_sale:
                priceType = TYPE_PRICE_USE_SALE;
                cleanPriceText();
                break;
        }
    }

    private void cleanAreaText() {
        areaLft.setText("");
        areaRight.setText("");
    }

    private void cleanPriceText() {
        priceLeft.setText("");
        priceRight.setText("");
    }

    @Override
    public void onCheckedChanged(MyRadioGroup group, int checkedId) {
        RadioButton rb;

        switch (group.getId()) {
            case R.id.activity_more_ssd_radiogroup:

                rb = group.findViewById(checkedId);
                if (rb != null) {
                    String rbStr = rb.getText().toString();
                    if (!rbStr.equals("未知")) {
                        ssd = rbStr.substring(0, rbStr.length() - 1);
                    } else ssd = null;

                    ssdSelectID = checkedId;
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.activity_more_completion_begin:
                setDate(v.getId());
                break;

            case R.id.activity_more_completion_end:
                setDate(v.getId());
                break;

            case R.id.activity_more_opendate_begin:
                setDate(v.getId());
                break;

            case R.id.activity_more_opendate_end:
                setDate(v.getId());
                break;

            case R.id.activity_more_status_start_layout:
                showStatusBeginDialog();
                break;

            case R.id.activity_more_status_end_layout:
                showStatusEndDialog();
                break;

            case R.id.complex_txt_reset:
                reset();
                break;

            case R.id.complex_txt_search:
                search();
                break;

            default:
                break;
        }
    }

    private void reset() {
//        application.setComplexOperation(new Operation());
        this.setResult(3);
        owner.setText("");
        phone.setText("");
        cleanPriceText();
        cleanAreaText();
        areaRG.check(R.id.complex_rb_use_area);
        priceRG.check(R.id.complex_rb_use_sale);
        textDateOpenDateBegin.setText(null);
        textDateOpenDateEnd.setText(null);
        textDatecompletionBegin.setText(null);
        textDatecompletionEnd.setText(null);
        dateTipTxt.setText(getString(R.string.dialog_opendate_opendate));
        interval.resetView();
        houseTag.resetView();
        direction.resetView();
        ssdRG.clearCheck();
        ssdSelectID = -1;
        ssd = null;
        areaType = TYPE_AREA_USE;
        priceType = TYPE_PRICE_USE_SALE;
        textDatecompletionBegin.setText(null);
        textDatecompletionEnd.setText(null);
        textDateOpenDateBegin.setText(null);
        textDateOpenDateEnd.setText(null);
        completeDateStart = Calendar.getInstance().getTime();
        completeDateEnd = Calendar.getInstance().getTime();
        changeDateStart = Calendar.getInstance().getTime();
        changeDateEnd = Calendar.getInstance().getTime();
        statusBegin.setText("全部");
        statusEnd.setText("全部");
        staBeginSelectList = new ArrayList<>();
        staEndSelectList = new ArrayList<>();
        statusView.setVisibility(View.GONE);
        openDataType = R.id.dialog_opendate_rb_default;
    }

    private void search() {
        HouseDescription description = new HouseDescription();

        description.setSSDType(ssd == null || ssd.equals("") ? null : ssd);

        String priceFrom = priceLeft.getText().toString();
        description.setPriceUnitFrom(priceFrom.equals("") ? null : priceFrom);
        String priceTo = priceRight.getText().toString();
        description.setPriceUnitTo(priceTo.equals("") ? null : priceTo);
        if (!priceFrom.equals("") || !priceTo.equals("")) {
            description.setPriceUnitType(priceType + "");
        }

        String completeBegin = textDatecompletionBegin.getText().toString();
        description.setCompleteYearFrom(completeBegin.equals("") ? null : completeBegin);
        String completeEnd = textDatecompletionEnd.getText().toString();
        description.setCompleteYearTo(completeEnd.equals("") ? null : completeEnd);


        String ownerStr = owner.getText().toString();
        description.setTrustorName(ownerStr.equals("") ? null : ownerStr);
        String phoneStr = phone.getText().toString();
        description.setMobile(phoneStr.equals("") ? null : phoneStr);

        //房源類型
        String areaFrom = areaLft.getText().toString();
        String areaTo = areaRight.getText().toString();


        if (areaType == TYPE_AREA_USE) {
            description.setSquareUseFrom(areaFrom.equals("") ? null : areaFrom);
            description.setSquareUseTo(areaTo.equals("") ? null : areaTo);
        } else {
            description.setSquareFrom(areaFrom.equals("") ? null : areaFrom);
            description.setSquareTo(areaTo.equals("") ? null : areaTo);
        }

        if (!areaFrom.equals("") || !areaTo.equals("")) {
            description.setPropertySquareType(areaType + "");
        }

        //房源日期
        String opendateBegin = textDateOpenDateBegin.getText().toString();
        description.setPropertyDateFrom(opendateBegin.equals("") ? null : opendateBegin);
        String opendateEnd = textDateOpenDateEnd.getText().toString();
        description.setPropertyDateTo(opendateEnd.equals("") ? null : opendateEnd);

        if (!opendateBegin.equals("") || !opendateEnd.equals("")) {
            switch (openDataType) {
                case R.id.dialog_opendate_rb_default:
                    description.setPropertyDateType(registDate);
                    break;
                case R.id.dialog_opendate_rb_modification_last:
                    description.setPropertyDateType(lasetUpdate);
                    break;
                case R.id.dialog_opendate_rb_follow:
                    description.setPropertyDateType(lastFollowDate);
                    break;
                case R.id.dialog_opendate_rb_price:
                    description.setPropertyDateType(changePriceDate);
                    break;
                case R.id.dialog_opendate_rb_entrust_start:
                    description.setPropertyDateType(onlyTrustStartDate);
                    break;
                case R.id.dialog_opendate_rb_entrust_end:
                    description.setPropertyDateType(onlyTrustEndDate);
                    break;
                case R.id.dialog_opendate_rb_anticipate:
                    description.setPropertyDateType(estimatedDate);
                    break;
                case R.id.dialog_opendate_rb_changehouse:
                    description.setPropertyDateType(statusChangedDate);
                    description.setIncludedPropertyStatusFrom((staValueBeginList != null && staValueBeginList.isEmpty()) ? null : staValueBeginList);
                    description.setIncludedPropertyStatusTo((staValueEndList != null && staValueEndList.isEmpty()) ? null : staValueEndList);
                    break;
            }
        }

        if (!houseTagList.isEmpty())
            description.setPropertyboolTag(getValue(houseTagValue, houseTagList));

        if (!directionList.isEmpty())
            description.setHouseDirection(getValue(directionValue, directionList));

        if (!intervalList.isEmpty())
            description.setPropertyTypes(getValue(intervalValue, intervalList));

        if (greenTabCb.isChecked())
            description.setHasSalePricePremiumUnpaid(greenTabCb.isChecked());

        if (keyCb.isChecked())
            description.setHasPropertyKey(true);

        saveOperation();

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("body", description);
        bundle.putSerializable("operation", operation);
        intent.putExtras(bundle);
        ComplexActivity.this.setResult(2, intent);
        finish();
    }

    private void saveOperation() {
        operation = new Operation();
        operation.setGreenTabCheck(greenTabCb.isChecked());
        operation.setKeyCheck(keyCb.isChecked());
        operation.setDirectionList(directionList);
        operation.setChangeDateStart(changeDateStart);
        operation.setChangeDateEnd(changeDateEnd);
        operation.setCompleteDateStart(completeDateStart);
        operation.setCompleteDateEnd(completeDateEnd);
        operation.setIntervalList(intervalList);
        operation.setHouseTagList(houseTagList);
        operation.setAreaType(areaType);
        operation.setPriceType(priceType);
        operation.setDateTypeText(dateTipTxt.getText().toString());
        operation.setDateType(openDataType);
        operation.setStaBeginSelectList(staBeginSelectList);
        operation.setStaEndSelectList(staEndSelectList);
        operation.setStaBeginText(statusBegin.getText().toString());
        operation.setStaEndText(statusEnd.getText().toString());
        operation.setAreaFrom(areaLft.getText().toString());
        operation.setAreaTo(areaRight.getText().toString());
        operation.setPriceFrom(priceLeft.getText().toString());
        operation.setPriceTo(priceRight.getText().toString());
        operation.setPhone(phone.getText().toString());
        operation.setSsdSelectId(ssdSelectID);
        operation.setSsdType(ssd);
        operation.setStaValueEnd(staValueEndList);
        operation.setStaValueStart(staValueBeginList);
        operation.setOwner(owner.getText().toString());
        operation.setCompleteStartText(textDatecompletionBegin.getText().toString());
        operation.setCompleteEndText(textDatecompletionEnd.getText().toString());
        operation.setDateStartText(textDateOpenDateBegin.getText().toString());
        operation.setDateEndText(textDateOpenDateEnd.getText().toString());
//        application.setComplexOperation(operation);
    }


    private void showStatusEndDialog() {
        StatusDialog statusEndDialog = new StatusDialog(getStatusText(), staEndSelectList);
        statusEndDialog.setOnDialogOnclikeLisenter(new StatusDialog.onDialogOnclikeLisenter() {
            @Override
            public void onClick(Dialog v, int viewID, List<Integer> viewList, String[] content) {
                v.dismiss();
                staEndSelectList = viewList;
                statusEnd.setText(getSelectStatusText(content));
                staValueEndList = getStatusCodes(content);
            }
        });
        statusEndDialog.show(getSupportFragmentManager(), "");
    }

    private void showStatusBeginDialog() {

        StatusDialog statusBeginDialog = new StatusDialog(getStatusText(), staBeginSelectList);
        statusBeginDialog.setOnDialogOnclikeLisenter(new StatusDialog.onDialogOnclikeLisenter() {
            @Override
            public void onClick(Dialog v, int viewID, List<Integer> viewList, String[] content) {
                v.dismiss();
                staBeginSelectList = viewList;
                L.d("statu",staBeginSelectList.toString());
                statusBegin.setText(getSelectStatusText(content));
                staValueBeginList = getStatusCodes(content);
            }
        });
        statusBeginDialog.show(getSupportFragmentManager(), "");
    }

    private List<String> getStatusCodes(String[] status) {
        if (status == null) return null;
        Map<String, String> statusParams = ApplicationManager.getStatusCode();
        List<String> statuList = new ArrayList<>();
        for (String sta : status) {
            for (Map.Entry<String, String> entry : statusParams.entrySet()) {
                if (entry.getKey().equals(sta)) {
                    statuList.add(entry.getValue());
                }
            }
        }
        return statuList;
    }

    private List<String> getStatusText() {
        Map<String, String> statusParams = ApplicationManager.getStatusParams();
        List<String> statuList = new ArrayList<>();
        for (Map.Entry<String, String> entry : statusParams.entrySet()) {
            statuList.add(entry.getKey());
        }
        return statuList;
    }


    private String getSelectStatusText(String[] status) {
        String statusStr = "";
        if (status != null && status.length != 0) {
            for (String str : status) {
                if (str.equals("不限")) return "不限";
                statusStr = statusStr + str;
            }
            return statusStr;
        }
        return "不限";
    }

    //設置日期
    private void setDate(final int viewID) {

        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(selectedDate.get(Calendar.YEAR) - 20, 0, 1);
        boolean[] showType = new boolean[]{true, false, false, false, false, false};

        if (viewID == R.id.activity_more_opendate_begin || viewID == R.id.activity_more_opendate_end)
            showType = new boolean[]{true, true, true, false, false, false};

        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                switch (viewID) {

                    case R.id.activity_more_opendate_begin:
                        textDateOpenDateBegin.setText(getTime(date));
                        changeDateStart = date;
                        break;
                    case R.id.activity_more_opendate_end:
                        textDateOpenDateEnd.setText(getTime(date));
                        changeDateEnd = date;
                        break;
                    case R.id.activity_more_completion_begin:
                        textDatecompletionBegin.setText(getTime(date));
                        completeDateStart = date;
                        break;
                    case R.id.activity_more_completion_end:
                        textDatecompletionEnd.setText(getTime(date));
                        completeDateEnd = date;
                        break;
                }
            }

            private String getTime(Date date) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                if (viewID == R.id.activity_more_opendate_begin || viewID == R.id.activity_more_opendate_end)
                    return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
                else return calendar.get(Calendar.YEAR) + "";
            }
        })
                .setCancelText("取消")
                .setSubmitText("確定")
                .setTitleText("請選擇日期")
                .setLabel("", "", "", "时", "分", "秒")
                .setType(showType)
                .setRangDate(startDate, selectedDate)
                .isCenterLabel(false)
                .setContentSize(22)
                .setTitleBgColor(Color.WHITE)
                .setSubmitColor(getResources().getColor(R.color.colortheme))//确定按钮文字颜色
                .setCancelColor(Color.BLACK)//取消按钮文字颜色
                .build();

        Calendar defaulut = Calendar.getInstance();
        switch (viewID) {

            case R.id.activity_more_opendate_begin:
                if (changeDateStart != null) defaulut.setTime(changeDateStart);
                break;
            case R.id.activity_more_opendate_end:
                if (changeDateEnd != null) defaulut.setTime(changeDateEnd);
                break;
            case R.id.activity_more_completion_begin:
                if (completeDateStart != null) defaulut.setTime(completeDateStart);
                break;
            case R.id.activity_more_completion_end:
                if (completeDateEnd != null) defaulut.setTime(completeDateEnd);
                break;
        }

        pvTime.setDate(defaulut);//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 給所有組建分發事件
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onItemClick(ViewGroup content, View view, boolean isCheck) {

        switch (content.getId()) {
            case R.id.complex_cb_direction:
                if (isCheck) directionList.add((String) view.getTag());
                else directionList.remove((String) view.getTag());
                break;
            case R.id.complex_cb_interval:
                if (isCheck) intervalList.add((String) view.getTag());
                else intervalList.remove((String) view.getTag());
                break;
            case R.id.complex_cb_tag:
                if (isCheck) houseTagList.add((String) view.getTag());
                else houseTagList.remove((String) view.getTag());
                break;
        }

    }


    public interface IMoreActivity {

        void getSSD(String... ssd);

        void getInterval(String... intervals);

        void getDirection(String... directions);

        void getTag(String... tags);
    }
}
