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
import com.centanet.hk.aplus.Views.basic.BaseFragment;
import com.centanet.hk.aplus.Widgets.ClearEditText;
import com.centanet.hk.aplus.bean.build_tag.TagCategory;
import com.centanet.hk.aplus.bean.build_tag.TagInfo;
import com.centanet.hk.aplus.bean.http.HouseDescription;
import com.centanet.hk.aplus.bean.params.SystemParamItems;
import com.centanet.hk.aplus.bean.save_option.PropertyRequestSaveParams;
import com.centanet.hk.aplus.bean.translist.TransListRequest;
import com.centanet.hk.aplus.common.APSystemParameterType;
import com.centanet.hk.aplus.common.CommandField;
import com.centanet.hk.aplus.manager.AppSystemParamsManager;
import com.centanet.hk.aplus.manager.PropertyRequestParamsManager;
import com.githang.statusbar.StatusBarCompat;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.centanet.hk.aplus.common.CommandField.PropertySquareType.AREA_USE;

/**
 * Created by yangzm4 on 2018/7/24.
 */

public class TransFiltrateActivity extends AppCompatActivity implements BaseFragment.OnFragmentInteractionListener, View.OnClickListener, StatuFragment.OnStatuChangeLisenter, OptionFragment.OnOptionChangeLisenter, IntervalFragment.OnIntervalChangeLisenter, TagFragment.OnTagChangeLisenter, SaleFragment.OnSaleChangeLisenter, RentFragment.OnRentChangeLisenter,
        OtherFragment.OnOtherChangeLisenter, SizeFragment.OnSizeChangeLisenter, AreaFragment.OnAreaChangeLisenter {

    public static final String HOUSE_PARAMS = "HOUSE_PARAMS";
    public static final int CODE_FILTRATE = 100;

    private View salePrice, rentPrice, sizePrice, area, interval, label, other, option;
    private TextView statusCount, salePriceCount, rentPriceCount, sizePriceCount, areaCount, intervalCount, labelCount, otherCount, optionCount;
    private LinearLayout navigation;
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
    private View transtype;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_more);
        init();
        initView();
        initLisenter();
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.colortheme), false);

        L.d(thiz, statulist.toString());

    }

    private void init() {

        sysOptions = OptionFragment.newInstance(null).new Option();

        houseDescription = (HouseDescription) getIntent().getExtras().get(HOUSE_PARAMS);
        L.d("OnActivityBack2", houseDescription.toString());
        if (houseDescription == null) {
            houseDescription = new HouseDescription();
        }

        L.d("Option_filtrate", "getHasPropertyKey: " + houseDescription.getHasPropertyKey() + "");
        L.d("Option_filtrate", "isOnlyTrust: " + houseDescription.isOnlyTrust() + "");
        L.d("Option_filtrate", "isHasSalePricePremiumUnpaid: " + houseDescription.isHasSalePricePremiumUnpaid() + "");
        L.d("Option_filtrate", "isShowSalePricePremiumUnpaid: " + houseDescription.isShowSalePricePremiumUnpaid() + "");
        L.d("Option_filtrate", "isProxy: " + houseDescription.isProxy() + "");
        L.d("Option_filtrate", "isNoneSSD: " + houseDescription.isNoneSSD() + "");
        L.d("Option_filtrate", "isHotlist: " + houseDescription.isHotlist() + "");
        L.d("Option_filtrate", "isHasParkingNumber: " + houseDescription.isHasParkingNumber() + "");
        L.d("Option_filtrate", "isHasConfirmTransaction: " + houseDescription.isHasConfirmTransaction() + "");
        L.d("Option_filtrate", "isHasDevelopmentEndCredits: " + houseDescription.isHasDevelopmentEndCredits() + "");
        L.d("Option_filtrate", "isHasOptout: " + houseDescription.isHasOptout() + "");

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

        SaleFragment saleFragment = SaleFragment.newInstance(null);
        salePrices = saleFragment.new SalePrice();

        salePrices.endPrice = houseDescription.getSalePriceTo();
        salePrices.startPrice = houseDescription.getSalePriceFrom();
        salePrices.isConGreen = houseDescription.isHasSalePricePremiumUnpaid();
        salePrices.isShowGreen = houseDescription.isShowSalePricePremiumUnpaid();

        areaParam = new AreaFragment.AreaParam();
        if (houseDescription.getPropertySquareType() != null && !houseDescription.getPropertySquareType().equals("")) {
            if (Integer.parseInt(houseDescription.getPropertySquareType()) == 1) {
                areaParam.areaFrom = houseDescription.getSquareFrom();
                areaParam.areaTo = houseDescription.getSquareTo();
                areaParam.areaType = CommandField.PropertySquareType.AREA_REALLY;
            } else {
                areaParam.areaFrom = houseDescription.getSquareUseFrom();
                areaParam.areaTo = houseDescription.getSquareUseTo();
                areaParam.areaType = CommandField.PropertySquareType.AREA_USE;
            }
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

        if (houseDescription.getPropertyStatus() != null && !houseDescription.getPropertyStatus().isEmpty()) {
            statusCount.setText(houseDescription.getPropertyStatus().size() + "");
            statusCount.setVisibility(View.VISIBLE);
        }



        if (houseDescription.getPropertyTypes() != null && !houseDescription.getPropertyTypes().isEmpty()) {
            intervalCount.setText(houseDescription.getPropertyTypes().size() + "");
            intervalCount.setVisibility(View.VISIBLE);
        }

        if (houseDescription.getPropertyboolTag() != null && !houseDescription.getPropertyboolTag().isEmpty()) {
            labelCount.setText(houseDescription.getPropertyboolTag().size() + "");
            labelCount.setVisibility(View.VISIBLE);
        }


//
//        if (houseDescription.getPropertySquareType() != null && !houseDescription.getPropertySquareType().equals("")) {
//            areaCount.setText("1");
//            L.d("getPropertySquareType", houseDescription.getPropertySquareType());
//            areaCount.setVisibility(View.VISIBLE);
//        }


//        onOptionChange(sysOptions);
        onSizeChange(sizeParam);
        onAreaChange(areaParam);
        onSaleChange(salePrices);
        onRentChange(rentPrices);

    }

    private void initLisenter() {

        salePrice.setOnClickListener(this);
        rentPrice.setOnClickListener(this);
        sizePrice.setOnClickListener(this);
        area.setOnClickListener(this);
        interval.setOnClickListener(this);
        label.setOnClickListener(this);
        other.setOnClickListener(this);
        option.setOnClickListener(this);
        yes.setOnClickListener(this);


        back.setOnClickListener(v -> {
            finish();
        });
    }

    private void initView() {

        navigation = findViewById(R.id.navigation);

        salePrice = findViewById(R.id.more_view_sale);
        rentPrice = findViewById(R.id.more_view_rent);
        sizePrice = findViewById(R.id.more_view_size);
        area = findViewById(R.id.more_view_area);
        transtype = findViewById(R.id.more_view_transtype);


        interval = findViewById(R.id.more_view_interval);
        label = findViewById(R.id.more_view_label);

        other = findViewById(R.id.more_view_other);
        option = findViewById(R.id.more_view_option);
        back = findViewById(R.id.back);

        statusCount = findViewById(R.id.more_txt_stacount);
        salePriceCount = findViewById(R.id.more_txt_salecount);
        rentPriceCount = findViewById(R.id.more_txt_rentcount);
        sizePriceCount = findViewById(R.id.more_txt_sizecount);
        areaCount = findViewById(R.id.more_txt_areacpunt);

        intervalCount = findViewById(R.id.more_txt_interval);
        labelCount = findViewById(R.id.more_txt_tagcount);

        otherCount = findViewById(R.id.more_txt_othercount);
        optionCount = findViewById(R.id.more_txt_optioncount);

        yes = findViewById(R.id.yes);

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
            case R.id.more_view_sale:

                turnToFragment(SaleFragment.newInstance(salePrices));
                break;
            case R.id.more_view_size:
                turnToFragment(SizeFragment.newInstance(sizeParam));
                break;
            case R.id.more_view_rent:
                turnToFragment(RentFragment.newInstance(rentPrices));
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
            case R.id.more_view_interval:
                turnToFragment(IntervalFragment.newInstance(intervalList, houseDescription.getPropertyTypes()));
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
                    params.setStatuFrom(getStatuCodeOption(statulist,houseDescription.getIncludedPropertyStatusFrom()));
                }
                if (houseDescription.getIncludedPropertyStatusTo() != null) {
                    params.setStatuTo(getStatuCodeOption(statulist,houseDescription.getIncludedPropertyStatusTo()));
                }
                PropertyRequestParamsManager.setParams(params);
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable(HOUSE_PARAMS, houseDescription);
                L.d("OnActivityBack", houseDescription.toString());
                intent.putExtras(bundle);
                TransFiltrateActivity.this.setResult(CODE_FILTRATE, intent);
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
        L.d("showStatus",paramItems.toString());
        return paramItems;
    }

    private List<SystemParamItems> getSearchOption(List<SystemParamItems> items, List<String> key) {
        List<SystemParamItems> paramItems = new ArrayList<>();
        for (SystemParamItems item : items) {
            if (key.indexOf(item.getItemValue()) != -1) {
                paramItems.add(item);
            }
        }
        L.d("showStatus",paramItems.toString());
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

        if (count == 0) optionCount.setVisibility(View.GONE);
        else {
            optionCount.setVisibility(View.VISIBLE);
            optionCount.setText(count + "");
        }
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
        L.d("onOtherChange", houseDescription.getPropertyDateFrom() + "   " + houseDescription.getPropertyDateFrom());
    }



    @Override
    public void onSizeChange(SizeFragment.SizeParam sizeParam) {
        this.sizeParam = sizeParam;
        if (sizeParam.avgType != 0) {
            sizePriceCount.setVisibility(View.VISIBLE);
            sizePriceCount.setText("1");
        }
        houseDescription.setPriceUnitFrom(sizeParam.startPrice);
        houseDescription.setPriceUnitTo(sizeParam.endPrice);
        houseDescription.setPriceUnitType(sizeParam.avgType + "");
    }

    @Override
    public void onAreaChange(AreaFragment.AreaParam areaParam) {

        this.areaParam = areaParam;

        if (areaParam.areaType != 0) {
            houseDescription.setPropertySquareType(areaParam.areaType + "");
            areaCount.setVisibility(View.VISIBLE);
            areaCount.setText("1");
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
}
