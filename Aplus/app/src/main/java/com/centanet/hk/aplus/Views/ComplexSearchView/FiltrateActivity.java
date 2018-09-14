package com.centanet.hk.aplus.Views.ComplexSearchView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.TextUtil;
import com.centanet.hk.aplus.Views.basic.BaseFragment;
import com.centanet.hk.aplus.Views.basic.BasicActivty;
import com.centanet.hk.aplus.Widgets.ClearEditText;
import com.centanet.hk.aplus.bean.build_tag.TagCategory;
import com.centanet.hk.aplus.bean.build_tag.TagInfo;
import com.centanet.hk.aplus.bean.http.HouseDescription;
import com.centanet.hk.aplus.bean.params.SystemParamItems;
import com.centanet.hk.aplus.bean.save_option.PropertyRequestSaveParams;
import com.centanet.hk.aplus.common.APSystemParameterType;
import com.centanet.hk.aplus.common.CommandField;
import com.centanet.hk.aplus.manager.AppSystemParamsManager;
import com.centanet.hk.aplus.manager.ApplicationManager;
import com.centanet.hk.aplus.manager.PropertyRequestParamsManager;
import com.githang.statusbar.StatusBarCompat;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.centanet.hk.aplus.common.CommandField.PropertySquareType.AREA_USE;

/**
 * Created by yangzm4 on 2018/7/24.
 */

public class FiltrateActivity extends BasicActivty implements BaseFragment.OnFragmentInteractionListener, View.OnClickListener, StatuFragment.OnStatuChangeLisenter, OptionFragment.OnOptionChangeLisenter,
        SSDFragment.OnSSDChangeLisenter, DirectionFragment.OnDirectionChangeLisenter, IntervalFragment.OnIntervalChangeLisenter, TagFragment.OnTagChangeLisenter, SaleFragment.OnSaleChangeLisenter, RentFragment.OnRentChangeLisenter,
        OtherFragment.OnOtherChangeLisenter, FacilityFragment.OnFacilityChangeLisenter, SizeFragment.OnSizeChangeLisenter, AreaFragment.OnAreaChangeLisenter, DateFragment.OnDateChangeLisenter {

    public static final String HOUSE_PARAMS = "HOUSE_PARAMS";
    public static final int CODE_FILTRATE = 100;

    private View status, salePrice, rentPrice, sizePrice, area, ssd, direction, interval, label, factility, other, option, date;
    private TextView statusCount, salePriceCount, rentPriceCount, sizePriceCount, areaCount, ssdCount, directionCount, intervalCount, labelCount, factilityCount, otherCount, optionCount, dateCount;
    private LinearLayout navigation;
    private ClearEditText keyEditTxt;
    private List<SystemParamItems> statulist;
    private List<SystemParamItems> tagList;
    private List<SystemParamItems> directionList;
    private List<SystemParamItems> intervalList;
    private View back;
    private HouseDescription houseDescription;
    private String thiz = getClass().getSimpleName();
    private OptionFragment.Option sysOptions;
    private View yes;
    private SaleFragment.SalePrice salePrices;
    private RentFragment.RentPrice rentPrices;
    private SizeFragment.SizeParam sizeParam;
    private AreaFragment.AreaParam areaParam;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        init();
        initView();
        initLisenter();
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.colortheme), false);

        L.d(thiz, statulist.toString());
        turnToFragment(StatuFragment.newInstance(statulist, houseDescription.getPropertyStatus()));
        status.setSelected(true);
    }

    private void init() {

        sysOptions = OptionFragment.newInstance(null).new Option();

        houseDescription = (HouseDescription) getIntent().getExtras().get(HOUSE_PARAMS);
        L.d("OnActivityBack2", houseDescription.toString());
        if (houseDescription == null) {
            houseDescription = new HouseDescription();
        }

        sysOptions.hasParkingNumber = houseDescription.isHasParkingNumber();
        sysOptions.hasOptout = houseDescription.isHasOptout();
        sysOptions.isHotlist = houseDescription.isHotlist();
        sysOptions.isOnlyTrust = houseDescription.isOnlyTrust();
        L.d("Option", "option init: " + houseDescription.isOnlyTrust() + "");
        sysOptions.hasPropertyKey = houseDescription.getHasPropertyKey();
        sysOptions.isHotlist = houseDescription.isHotlist();
        sysOptions.hasDevelopmentEndCredits = houseDescription.isHasDevelopmentEndCredits();
        sysOptions.isNoneSSD = houseDescription.isNoneSSD();
        sysOptions.isProxy = houseDescription.isProxy();
        sysOptions.isShowSalePricePremiumUnpaid = houseDescription.isShowSalePricePremiumUnpaid();
        sysOptions.hasSalePricePremiumUnpaid = houseDescription.isHasSalePricePremiumUnpaid();
        sysOptions.hasConfirmTransaction = houseDescription.isHasConfirmTransaction();

        rentPrices = new RentFragment.RentPrice();
        rentPrices.startPrice = houseDescription.getRentPriceFrom();
        rentPrices.endPrice = houseDescription.getRentPriceTo();

        salePrices = new SaleFragment.SalePrice();

        salePrices.endPrice = houseDescription.getSalePriceTo();
        salePrices.startPrice = houseDescription.getSalePriceFrom();
        salePrices.isConGreen = houseDescription.isHasSalePricePremiumUnpaid();
        salePrices.isShowGreen = houseDescription.isShowSalePricePremiumUnpaid();

        areaParam = new AreaFragment.AreaParam();
//        if (houseDescription.getPropertySquareType() != null && !houseDescription.getPropertySquareType().equals("")) {
//            if (Integer.parseInt(houseDescription.getPropertySquareType()) == 1) {
//                areaParam.areaFrom = houseDescription.getSquareFrom();
//                areaParam.areaTo = houseDescription.getSquareTo();
//                areaParam.areaType = CommandField.PropertySquareType.AREA_REALLY;
//            } else {
//                areaParam.areaFrom = houseDescription.getSquareUseFrom();
//                areaParam.areaTo = houseDescription.getSquareUseTo();
//                areaParam.areaType = CommandField.PropertySquareType.AREA_USE;
//            }
//        }

        if (houseDescription.getSquareFrom() != null && houseDescription.getSquareTo() != null) {
            areaParam.areaFrom = houseDescription.getSquareFrom();
            areaParam.areaTo = houseDescription.getSquareTo();
            areaParam.areaType = CommandField.PropertySquareType.AREA_REALLY;
        } else if (houseDescription.getSquareUseFrom() != null || houseDescription.getSquareUseTo() != null) {
            areaParam.areaFrom = houseDescription.getSquareUseFrom();
            areaParam.areaTo = houseDescription.getSquareUseTo();
            areaParam.areaType = CommandField.PropertySquareType.AREA_USE;
        }

        sizeParam = new SizeFragment.SizeParam();
        if (houseDescription.getPriceUnitType() != null && !houseDescription.getPriceUnitType().equals("")) {
            sizeParam.avgType = Integer.parseInt(houseDescription.getPriceUnitType());
            sizeParam.startPrice = houseDescription.getPriceUnitFrom();
            sizeParam.endPrice = houseDescription.getPriceUnitTo();
        }

//        houseDescription.setPropertyStatus(new ArrayList<>());
        statulist = new ArrayList<>();
        tagList = new ArrayList<>();
        directionList = new ArrayList<>();
        intervalList = new ArrayList<>();

        statulist.addAll(AppSystemParamsManager.getSystemParams(APSystemParameterType.propertyStatusCategory).getSystemParamItems());
        tagList.addAll(AppSystemParamsManager.getSystemParams(APSystemParameterType.propertyTag).getSystemParamItems());
        directionList.addAll(AppSystemParamsManager.getSystemParams(APSystemParameterType.houseDirection).getSystemParamItems());
        intervalList.addAll(AppSystemParamsManager.getSystemParams(APSystemParameterType.house).getSystemParamItems());

    }

    private void reCoverView(HouseDescription houseDescription) {

        L.d("DateChange_Filter_recover",houseDescription.getPropertyDateFrom()+" "+houseDescription.getPropertyDateTo());

        if (houseDescription.getPropertyStatus() != null && !houseDescription.getPropertyStatus().isEmpty()) {
            statusCount.setText(houseDescription.getPropertyStatus().size() + "");
            statusCount.setVisibility(View.VISIBLE);
        }

        if (houseDescription.getHouseDirection() != null && !houseDescription.getHouseDirection().isEmpty()) {
            directionCount.setText(houseDescription.getHouseDirection().size() + "");
            directionCount.setVisibility(View.VISIBLE);
        }

        if (houseDescription.getPropertyTypes() != null && !houseDescription.getPropertyTypes().isEmpty()) {
            intervalCount.setText(houseDescription.getPropertyTypes().size() + "");
            intervalCount.setVisibility(View.VISIBLE);
        }

        if (houseDescription.getPropertyboolTag() != null && !houseDescription.getPropertyboolTag().isEmpty()) {
            labelCount.setText(houseDescription.getPropertyboolTag().size() + "");
            labelCount.setVisibility(View.VISIBLE);
        }

        if (houseDescription.getBuildingTags() != null && !houseDescription.getBuildingTags().isEmpty()) {
            factilityCount.setText(houseDescription.getBuildingTags().size() + "");
            factilityCount.setVisibility(View.VISIBLE);
        }

        if (houseDescription.getSSDType() != null && !houseDescription.getSSDType().equals("")) {
            ssdCount.setText("1");
            ssdCount.setVisibility(View.VISIBLE);
        }
//
//        if (houseDescription.getPropertySquareType() != null && !houseDescription.getPropertySquareType().equals("")) {
//            areaCount.setText("1");
//            L.d("getPropertySquareType", houseDescription.getPropertySquareType());
//            areaCount.setVisibility(View.VISIBLE);
//        }

        if (houseDescription.getKeywords() != null && !houseDescription.getKeywords().equals("")) {
            keyEditTxt.setText(houseDescription.getKeywords());
        }

//        onOptionChange(sysOptions);
        onSizeChange(sizeParam);
        onAreaChange(areaParam);
        onSaleChange(salePrices);
        onRentChange(rentPrices);
        onDateChange(houseDescription);
        onOtherChange(houseDescription);

    }

    private void initLisenter() {

        status.setOnClickListener(this);
        salePrice.setOnClickListener(this);
        rentPrice.setOnClickListener(this);
        sizePrice.setOnClickListener(this);
        area.setOnClickListener(this);
        ssd.setOnClickListener(this);
        direction.setOnClickListener(this);
        interval.setOnClickListener(this);
        label.setOnClickListener(this);
        factility.setOnClickListener(this);
        other.setOnClickListener(this);
        option.setOnClickListener(this);
        yes.setOnClickListener(this);
        date.setOnClickListener(this);

        keyEditTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                houseDescription.setKeywords(keyEditTxt.getText().toString());
            }
        });

        back.setOnClickListener(v -> {
            finish();
        });
    }

    private void initView() {

        navigation = findViewById(R.id.navigation);
        status = findViewById(R.id.more_view_statu);
        salePrice = findViewById(R.id.more_view_sale);
        rentPrice = findViewById(R.id.more_view_rent);
        sizePrice = findViewById(R.id.more_view_size);
        area = findViewById(R.id.more_view_area);
        ssd = findViewById(R.id.more_view_ssd);
        direction = findViewById(R.id.more_view_direction);
        interval = findViewById(R.id.more_view_interval);
        label = findViewById(R.id.more_view_label);
        factility = findViewById(R.id.more_view_facility);
        other = findViewById(R.id.more_view_other);
        option = findViewById(R.id.more_view_option);
        back = findViewById(R.id.back);
        date = findViewById(R.id.more_view_date);

        statusCount = findViewById(R.id.more_txt_stacount);
        salePriceCount = findViewById(R.id.more_txt_salecount);
        rentPriceCount = findViewById(R.id.more_txt_rentcount);
        sizePriceCount = findViewById(R.id.more_txt_sizecount);
        areaCount = findViewById(R.id.more_txt_areacpunt);
        ssdCount = findViewById(R.id.more_txt_ssdcount);
        directionCount = findViewById(R.id.more_txt_directioncount);
        intervalCount = findViewById(R.id.more_txt_interval);
        labelCount = findViewById(R.id.more_txt_tagcount);
        factilityCount = findViewById(R.id.more_txt_faccount);
        otherCount = findViewById(R.id.more_txt_othercount);
        optionCount = findViewById(R.id.more_txt_optioncount);
        dateCount = findViewById(R.id.more_txt_date);

        yes = findViewById(R.id.yes);

        keyEditTxt = findViewById(R.id.search_edit);
        reCoverView(houseDescription);

    }


    private void turnToFragment(android.support.v4.app.Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_content, fragment);
        transaction.commit();
    }

    @Override
    public void onInteraction(String fragmentType, Object viewData) {

    }

    @Override
    public void onClick(View v) {
        L.d("onClick", "");
        resetNavigation();
        v.setSelected(true);
        switch (v.getId()) {
            case R.id.more_view_area:
                turnToFragment(AreaFragment.newInstance(areaParam));
                break;
            case R.id.more_view_statu:
                turnToFragment(StatuFragment.newInstance(statulist, houseDescription.getPropertyStatus()));
                break;
            case R.id.more_view_sale:

                turnToFragment(SaleFragment.newInstance(salePrices));
                break;
            case R.id.more_view_size:
                turnToFragment(SizeFragment.newInstance(sizeParam));
                break;
            case R.id.more_view_rent:
                turnToFragment(RentFragment.newInstance(rentPrices));
                break;
            case R.id.more_view_ssd:
                if (houseDescription.getSSDType() != null)
                    turnToFragment(SSDFragment.newInstance(houseDescription.getSSDType()));
                else turnToFragment(SSDFragment.newInstance(null));
                break;
            case R.id.more_view_direction:
                turnToFragment(DirectionFragment.newInstance(directionList, houseDescription.getHouseDirection()));
                break;
            case R.id.more_view_option:
                L.d("Filtrate", "" + sysOptions.toString());
                turnToFragment(OptionFragment.newInstance(sysOptions));
                break;
            case R.id.more_view_other:
                turnToFragment(OtherFragment.newInstance(houseDescription));
                break;
            case R.id.more_view_label:
                turnToFragment(TagFragment.newInstance(tagList, houseDescription.getPropertyboolTag()));
                break;
            case R.id.more_view_facility:
                turnToFragment(FacilityFragment.newInstance(houseDescription.getBuildingTags()));
                break;
            case R.id.more_view_interval:
                turnToFragment(IntervalFragment.newInstance(intervalList, houseDescription.getPropertyTypes()));
                break;
            case R.id.more_view_date:
                turnToFragment(DateFragment.newInstance(houseDescription));
//                turnToFragment(DateDemoFragment.newInstance(houseDescription));
                break;
            case R.id.yes:
                PropertyRequestSaveParams params = PropertyRequestParamsManager.getParams();
                if (houseDescription.getPropertyStatus() != null)
                    params.setStatulist(getSearchOption(statulist, houseDescription.getPropertyStatus()));
                if (houseDescription.getHouseDirection() != null)
                    params.setDirectionList(getSearchOption(directionList, houseDescription.getHouseDirection()));
                if (houseDescription.getPropertyboolTag() != null)
                    params.setTagList(getSearchOption(tagList, houseDescription.getPropertyboolTag()));
                if (houseDescription.getPropertyTypes() != null)
                    params.setIntervalList(getSearchOption(intervalList, houseDescription.getPropertyTypes()));
                if (houseDescription.getBuildingTags() != null)
                    params.setTagInfos(getBuildginTag(houseDescription.getBuildingTags()));
                if (houseDescription.getIncludedPropertyStatusFrom() != null) {
                    params.setStatuFrom(getStatuCodeOption(statulist, houseDescription.getIncludedPropertyStatusFrom()));
                }
                if (houseDescription.getIncludedPropertyStatusTo() != null) {
                    params.setStatuTo(getStatuCodeOption(statulist, houseDescription.getIncludedPropertyStatusTo()));
                }


//                if (!TextUtil.isEmply(houseDescription.getPropertyDateFrom())) {
//                    houseDescription.setPropertyDateFrom(changeToDate(houseDescription.getPropertyDateFrom()));
//                }
//
//                if (!TextUtil.isEmply(houseDescription.getPropertyDateTo())) {
//                    houseDescription.setPropertyDateTo(changeToDate(houseDescription.getPropertyDateTo()));
//                }


                PropertyRequestParamsManager.setParams(params);
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable(HOUSE_PARAMS, houseDescription);
                L.d("OnActivityBack", houseDescription.toString());
                intent.putExtras(bundle);
                FiltrateActivity.this.setResult(CODE_FILTRATE, intent);
                finish();
                break;
        }
    }

    private List<TagInfo> getBuildginTag(List<String> strings) {
        List<TagInfo> tagInfos = new ArrayList<>();
        if (strings == null) return null;
        for (TagCategory info : AppSystemParamsManager.getTagCategorys()) {
            for (TagInfo info1 : info.getTagInfos()) {
                if (strings.contains(info1.getTagKeyId()))
                    tagInfos.add(info1);
            }
        }
        return tagInfos;
    }

    private List<SystemParamItems> getStatuCodeOption(List<SystemParamItems> items, List<String> key) {
        List<SystemParamItems> paramItems = new ArrayList<>();
        for (SystemParamItems item : items) {
            if (key.indexOf(item.getItemCode()) != -1) {
                paramItems.add(item);
            }
        }
        L.d("showStatus", paramItems.toString());
        return paramItems;
    }

    private List<SystemParamItems> getSearchOption(List<SystemParamItems> items, List<String> key) {
        List<SystemParamItems> paramItems = new ArrayList<>();
        for (SystemParamItems item : items) {
            if (key.indexOf(item.getItemValue()) != -1) {
                paramItems.add(item);
            }
        }
        L.d("showStatus", paramItems.toString());
        return paramItems;
    }

    private void resetNavigation() {
        int count = navigation.getChildCount();
        L.d("ContenSize", count + "");
        for (int i = 0; i < count; i++) {
            View view = navigation.getChildAt(i);
            if (view instanceof RelativeLayout)
                view.setSelected(false);
        }
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
                if (v instanceof EditText)
                    v.clearFocus();
                return true;
            }
        }
        return false;
    }


    @Override
    public void onStatuChange(List<String> status) {
        houseDescription.setPropertyStatus(status);
        if (status != null && !status.isEmpty()) {
            statusCount.setText(status.size() + "");
            statusCount.setVisibility(View.VISIBLE);
        } else statusCount.setVisibility(View.GONE);
        L.d("onStatuChange", status.toString());
    }

    @Override
    public void onOptionChange(OptionFragment.Option option) {
        sysOptions = option;

        salePrices.isShowGreen = option.isShowSalePricePremiumUnpaid;
        salePrices.isConGreen = option.hasSalePricePremiumUnpaid;

        if (option.hasParkingNumber != false) {
            houseDescription.setHasParkingNumber(option.hasParkingNumber);
        } else houseDescription.setHasParkingNumber(null);

        if (option.hasOptout != false)
            houseDescription.setHasOptout(option.hasOptout);
        else houseDescription.setHasOptout(null);

        if (option.isHotlist != false)
            houseDescription.setHotlist(option.isHotlist);
        else houseDescription.setHotlist(null);

        if (option.isOnlyTrust != false)
            houseDescription.setOnlyTrust(option.isOnlyTrust);
        else houseDescription.setOnlyTrust(null);

        if (option.hasPropertyKey != false)
            houseDescription.setHasPropertyKey(option.hasPropertyKey);
        else houseDescription.setHasPropertyKey(null);

        if (option.hasDevelopmentEndCredits != false)
            houseDescription.setHasDevelopmentEndCredits(option.hasDevelopmentEndCredits);
        else houseDescription.setHasDevelopmentEndCredits(null);

        if (option.isNoneSSD != false)
            houseDescription.setNoneSSD(option.isNoneSSD);
        else houseDescription.setNoneSSD(null);

        if (option.isProxy != false)
            houseDescription.setProxy(option.isProxy);
        else houseDescription.setProxy(null);

        if (option.isShowSalePricePremiumUnpaid != false)
            houseDescription.setShowSalePricePremiumUnpaid(option.isShowSalePricePremiumUnpaid);
        else houseDescription.setShowSalePricePremiumUnpaid(null);

        if (option.hasSalePricePremiumUnpaid != false)
            houseDescription.setHasSalePricePremiumUnpaid(option.hasSalePricePremiumUnpaid);
        else houseDescription.setHasSalePricePremiumUnpaid(null);

        if (option.hasConfirmTransaction != false)
            houseDescription.setHasConfirmTransaction(option.hasConfirmTransaction);
        else houseDescription.setHasConfirmTransaction(null);

        int count = 0;
        for (Field field : option.getClass().getDeclaredFields()) {
            String type = field.getGenericType().toString();
            field.setAccessible(true);
            try {
                if (field.get(option) != null) {
                    if (type.equals("boolean")) {
                        L.d("OptionChange", field.get(option) + "");
                        if ((boolean) field.get(option))
                            count++;
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        if (count == 0) optionCount.setVisibility(View.GONE);
        else {
            optionCount.setVisibility(View.VISIBLE);
            optionCount.setText(count + "");
        }
    }

    @Override
    public void onSSDChange(String ssd) {
        ssdCount.setText(1 + "");
        ssdCount.setVisibility(View.VISIBLE);
        houseDescription.setSSDType(ssd);
    }

    @Override
    public void onDrectionChange(List<String> directions) {
        houseDescription.setHouseDirection(directions);
        if (directions != null && !directions.isEmpty()) {
            directionCount.setText(directions.size() + "");
            directionCount.setVisibility(View.VISIBLE);
        } else directionCount.setVisibility(View.GONE);
    }

    @Override
    public void onIntervalChange(List<String> intervals) {
        houseDescription.setPropertyTypes(intervals);
        if (intervals != null && !intervals.isEmpty()) {
            intervalCount.setText(intervals.size() + "");
            intervalCount.setVisibility(View.VISIBLE);
        } else intervalCount.setVisibility(View.GONE);
    }

    @Override
    public void onTagChange(List<String> tags) {
        houseDescription.setPropertyboolTag(tags);
        if (tags != null && !tags.isEmpty()) {
            labelCount.setText(tags.size() + "");
            labelCount.setVisibility(View.VISIBLE);
        } else labelCount.setVisibility(View.GONE);
    }

    @Override
    public void onRentChange(RentFragment.RentPrice rentPrice) {
        L.d("onRentChange", rentPrice.startPrice + " : " + rentPrice.endPrice);
        if ((rentPrice.startPrice != null && !rentPrice.startPrice.equals("") || (rentPrice.endPrice != null && !rentPrice.endPrice.equals("")))) {
            rentPriceCount.setVisibility(View.VISIBLE);
            rentPriceCount.setText("1");
            rentPrices = rentPrice;
        } else {
            rentPriceCount.setVisibility(View.GONE);
            rentPriceCount.setText(null);
        }

        houseDescription.setRentPriceFrom(rentPrice.startPrice);
        houseDescription.setRentPriceTo(rentPrice.endPrice);

    }

    @Override
    public void onSaleChange(SaleFragment.SalePrice salePrice) {

        int count = 0;
        for (Field field : option.getClass().getDeclaredFields()) {
            String type = field.getGenericType().toString();
            field.setAccessible(true);
            try {
                if (field.get(option) != null) {
                    if (type == "boolean") {
                        L.d("OptionChange", field.get(option) + "");
                        if ((boolean) field.get(option))
                            count++;
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        L.d("Option", "- - - - - - - - - - - - - - - - - - - - - - - - -");

        L.d("onSaleChange", salePrice.startPrice + " : " + salePrice.endPrice);
        if ((salePrice.startPrice != null && !salePrice.startPrice.equals("") || (salePrice.endPrice != null && !salePrice.endPrice.equals("")))) {
            salePriceCount.setVisibility(View.VISIBLE);
            salePriceCount.setText("1");
        } else {
            salePriceCount.setVisibility(View.GONE);
            salePriceCount.setText(null);
        }

        salePrices = salePrice;
        houseDescription.setSalePriceFrom(salePrice.startPrice);
        houseDescription.setSalePriceTo(salePrice.endPrice);

//        houseDescription.setShowSalePricePremiumUnpaid();

        sysOptions.hasSalePricePremiumUnpaid = salePrice.isConGreen;
        sysOptions.isShowSalePricePremiumUnpaid = salePrice.isShowGreen;
        onOptionChange(sysOptions);
    }

    @Override
    public void onOtherChange(HouseDescription description) {
        houseDescription = description;

        int count = 0;
        if (!TextUtil.isEmply(houseDescription.getTrustorName())) count++;
        if (!TextUtil.isEmply(houseDescription.getMobile())) count++;
        if (!TextUtil.isEmply(houseDescription.getCompleteYearFrom())) count++;
        if (!TextUtil.isEmply(houseDescription.getCompleteYearTo())) count++;

        if (!TextUtil.isEmply(houseDescription.getCompleteYearTo()) && !TextUtil.isEmply(houseDescription.getCompleteYearFrom())) {
            count--;
        }
//        if (!TextUtil.isEmply(houseDescription.getIncludedPropertyStatusFrom()))
//            count = count + houseDescription.getIncludedPropertyStatusFrom().size();
//        if (!TextUtil.isEmply(houseDescription.getIncludedPropertyStatusTo()))
//            count = count + houseDescription.getIncludedPropertyStatusTo().size();

        if (count == 0) otherCount.setVisibility(View.GONE);
        if (count != 0) {
            otherCount.setVisibility(View.VISIBLE);
            otherCount.setText(count + "");
        }
        L.d("onOtherChange", houseDescription.getPropertyDateFrom() + "   " + houseDescription.getPropertyDateFrom());
    }

    @Override
    public void onFacilityChange(List<String> tags) {
        houseDescription.setBuildingTags(tags);
        if (tags == null || tags.isEmpty())
            factilityCount.setVisibility(View.GONE);
        else {
            factilityCount.setVisibility(View.VISIBLE);
            factilityCount.setText(tags.size() + "");
        }
    }

    @Override
    public void onSizeChange(SizeFragment.SizeParam sizeParam) {
        this.sizeParam = sizeParam;
//        if (sizeParam.avgType != 0) {
//            sizePriceCount.setVisibility(View.VISIBLE);
//            sizePriceCount.setText("1");
//        }else {
//
//        }

        if ((sizeParam.startPrice != null && !sizeParam.startPrice.equals("") || (sizeParam.endPrice != null && !sizeParam.endPrice.equals("")))) {
            sizePriceCount.setVisibility(View.VISIBLE);
            sizePriceCount.setText("1");
        } else {
            sizePriceCount.setVisibility(View.GONE);
            sizePriceCount.setText(null);
        }

        houseDescription.setPriceUnitFrom(sizeParam.startPrice);
        houseDescription.setPriceUnitTo(sizeParam.endPrice);
        houseDescription.setPriceUnitType(sizeParam.avgType + "");
    }

    @Override
    public void onAreaChange(AreaFragment.AreaParam areaParam) {

        this.areaParam = areaParam;
//
//        if (areaParam.areaType != 0) {
//            houseDescription.setPropertySquareType(areaParam.areaType + "");
//            areaCount.setVisibility(View.VISIBLE);
//            areaCount.setText("1");
//        }

        L.d("onAreaChange", areaParam.areaType + "");

        if ((areaParam.areaFrom != null && !areaParam.areaFrom.equals("") || (areaParam.areaTo != null && !areaParam.areaTo.equals("")))) {
            areaCount.setVisibility(View.VISIBLE);
            areaCount.setText("1");
        } else {
            areaCount.setVisibility(View.GONE);
            areaCount.setText(null);
        }

        if (areaParam.areaType == AREA_USE) {
            houseDescription.setSquareUseFrom(areaParam.areaFrom);
            houseDescription.setSquareUseTo(areaParam.areaTo);
            houseDescription.setSquareFrom(null);
            houseDescription.setSquareTo(null);
        } else {
            houseDescription.setSquareFrom(areaParam.areaFrom);
            houseDescription.setSquareTo(areaParam.areaTo);
            houseDescription.setSquareUseFrom(null);
            houseDescription.setSquareUseTo(null);
        }
    }

    private String changeToDate(String date) {
        if (date == null) return null;
        if (date.equals("")) return null;

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

        L.d("wahahah", year + "-" + month + "-" + day);

        return year + "-" + month + "-" + day;

    }

    @Override
    public void onDateChange(HouseDescription description) {

//        L.d("Filter_onDateChange", description.getPropertyDateFrom() + "  " + description.getPropertyDateTo());

        if (TextUtil.isEmply(description.getPropertyDateType())) return;

//        if (!TextUtil.isEmply(description.getPropertyDateFrom())) {
//            description.setPropertyDateFrom(changeToDate(description.getPropertyDateFrom()));
//        }
//
//        if (!TextUtil.isEmply(description.getPropertyDateTo())) {
//            description.setPropertyDateTo(changeToDate(description.getPropertyDateTo()));
//        }


        houseDescription = description;
        L.d("DateChange_Filter",description.getPropertyDateFrom()+" "+description.getPropertyDateTo());
//        dateCount

        int count = 0;

        if (!TextUtil.isEmply(houseDescription.getPropertyDateFrom())) {
            count++;
        }
        if (!TextUtil.isEmply(houseDescription.getPropertyDateTo())) {
            count++;
        }

        if (!TextUtil.isEmply(houseDescription.getPropertyDateFrom()) && !TextUtil.isEmply(houseDescription.getPropertyDateTo())) {
            count--;
        }

        if (!TextUtil.isEmply(houseDescription.getIncludedPropertyStatusFrom()))
            count = count + houseDescription.getIncludedPropertyStatusFrom().size();
        if (!TextUtil.isEmply(houseDescription.getIncludedPropertyStatusTo()))
            count = count + houseDescription.getIncludedPropertyStatusTo().size();

        if (count == 0) dateCount.setVisibility(View.GONE);
        if (count != 0) {
            dateCount.setVisibility(View.VISIBLE);
            dateCount.setText(count + "");
        }
    }
}
