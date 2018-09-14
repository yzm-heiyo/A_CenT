package com.centanet.hk.aplus.Views.ComplexSearchView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
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
import com.centanet.hk.aplus.bean.http.HouseDescription;
import com.centanet.hk.aplus.bean.params.SystemParamItems;
import com.centanet.hk.aplus.bean.save_option.PropertyRequestSaveParams;
import com.centanet.hk.aplus.bean.save_option.TransRequestSaveParams;
import com.centanet.hk.aplus.bean.translist.TransListRequest;
import com.centanet.hk.aplus.common.APSystemParameterType;
import com.centanet.hk.aplus.manager.AppSystemParamsManager;
import com.centanet.hk.aplus.manager.PropertyRequestParamsManager;
import com.centanet.hk.aplus.manager.TransRequestParamsManager;
import com.githang.statusbar.StatusBarCompat;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.centanet.hk.aplus.common.CommandField.PropertySquareType.AREA_REALLY;
import static com.centanet.hk.aplus.common.CommandField.PropertySquareType.AREA_USE;

/**
 * Created by yangzm4 on 2018/8/10.
 */

public class TransFiltrateActivity extends BasicActivty implements View.OnClickListener, TranTypeFragment.OnTranTypeChangeLisenter, BaseFragment.OnFragmentInteractionListener, SaleFragment.OnSaleChangeLisenter,
        RentFragment.OnRentChangeLisenter, AreaFragment.OnAreaChangeLisenter, TranOtherFragment.OnOtherChangeLisenter, TranOptionFragment.OnOptionChangeLisenter, SizeFragment.OnSizeChangeLisenter, IntervalFragment.OnIntervalChangeLisenter,
        TagFragment.OnTagChangeLisenter, TransDateFragment.OnDateChangeLisenter {

    private View type, salePrice, rentPrice, sizePrice, area, interval, label, other, option, date;
    private View yes, back;
    private LinearLayout navigation;
    private TextView typeCount, salePriceCount, rentPriceCount, sizePriceCount, areaCount, intervalCount, labelCount, otherCount, optionCount, dateCount;
    private TranOptionFragment.Option sysOptions;
    private SaleFragment.SalePrice salePrices;
    private List<SystemParamItems> tagList;
    private List<SystemParamItems> intervalList;
    private RentFragment.RentPrice rentPrices;
    private SizeFragment.SizeParam sizeParam;
    private AreaFragment.AreaParam areaParam;
    private TranTypeFragment.TranType tranType;
    private TransListRequest request;
    public static final String TRANS_PARAMS = "TRANS_PARAMS";
    public static final int CODE_TRAN_FILTRATE = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_more);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.colortheme), false);
        init();
        initView();
        initLisenter();
        turnToFragment(TranTypeFragment.newInstance(request.getTransactionTypes()));
        recoverView(request);
    }

    private void recoverView(TransListRequest request) {
        if (request.getTransactionTypes() != null)
            onTranTypeChange(request.getTransactionTypes());

        onTransOptionChange(sysOptions);
        onIntervalChange(request.getRoomCounts());
        onTagChange(request.getBuildingUsages());
        onSizeChange(sizeParam);
        onSaleChange(salePrices);
        onRentChange(rentPrices);
        onAreaChange(areaParam);
        onDateChange(request);
        onOtherChange(request);
    }

    private void init() {

        if (getIntent().getExtras() != null)
            request = (TransListRequest) getIntent().getExtras().get(TRANS_PARAMS);

        if (request == null)
            request = new TransListRequest();

        sysOptions = new TranOptionFragment.Option();
        sysOptions.isConfirmed = Boolean.parseBoolean(request.getIsConfirmed());
        sysOptions.isCorporationTransferre = Boolean.parseBoolean(request.getIsCorporationTransferre());
        sysOptions.isDorporationTransferre = Boolean.parseBoolean(request.getIsDevelopmentEndCredits());
        sysOptions.isOStatus = Boolean.parseBoolean(request.getIsOStatus());
        sysOptions.isTransferred = Boolean.parseBoolean(request.getIsTransferred());
        sysOptions.isSorporationTransferre = Boolean.parseBoolean(request.getIsSalePricePremiumUnpaid());
        salePrices = new SaleFragment.SalePrice();
        salePrices.startPrice = request.getSellPriceFrom();
        salePrices.endPrice = request.getSellPriceTo();
        salePrices.isShowGreen = Boolean.parseBoolean(request.getIsSalePricePremiumUnpaid());

        rentPrices = new RentFragment.RentPrice();
        rentPrices.startPrice = request.getRentPriceFrom();
        rentPrices.endPrice = request.getRentPriceTo();

        sizeParam = new SizeFragment.SizeParam();

        if (request.getPriceUnitType() != null) {
            sizeParam.avgType = Integer.parseInt(request.getPriceUnitType());
            sizeParam.startPrice = request.getPriceUnitFrom();
            sizeParam.endPrice = request.getPriceUnitTo();
        }

        areaParam = new AreaFragment.AreaParam();

        if (isStrNull(request.getSquareUseFrom()) || isStrNull(request.getSquareUseTo())) {
            areaParam.areaType = AREA_USE;
            areaParam.areaTo = request.getSquareUseTo();
            areaParam.areaFrom = request.getSquareUseFrom();
        }

        if (isStrNull(request.getSquareFrom()) || isStrNull(request.getSquareTo())) {
            areaParam.areaType = AREA_REALLY;
            areaParam.areaTo = request.getSquareTo();
            areaParam.areaFrom = request.getSquareFrom();
        }

        tranType = new TranTypeFragment.TranType();
        tagList = new ArrayList<>();
        intervalList = new ArrayList<>();
        tagList.addAll(AppSystemParamsManager.getSystemParams(APSystemParameterType.propertyTag).getSystemParamItems());
        intervalList.addAll(AppSystemParamsManager.getSystemParams(APSystemParameterType.house).getSystemParamItems());
    }

    private boolean isStrNull(String str) {
        if (str != null && !str.equals(""))
            return true;
        else return false;
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
        type.setOnClickListener(this);
        yes.setOnClickListener(this);
        date.setOnClickListener(this);

        back.setOnClickListener(this);

    }

    private void initView() {

        navigation = findViewById(R.id.navigation);
        type = findViewById(R.id.more_view_transtype);
        salePrice = findViewById(R.id.more_view_sale);
        rentPrice = findViewById(R.id.more_view_rent);
        sizePrice = findViewById(R.id.more_view_size);
        area = findViewById(R.id.more_view_area);
        interval = findViewById(R.id.more_view_interval);
        label = findViewById(R.id.more_view_label);
        other = findViewById(R.id.more_view_other);
        date = findViewById(R.id.more_view_date);
        option = findViewById(R.id.more_view_option);
        back = findViewById(R.id.back);

        salePriceCount = findViewById(R.id.more_txt_salecount);
        rentPriceCount = findViewById(R.id.more_txt_rentcount);
        sizePriceCount = findViewById(R.id.more_txt_sizecount);
        typeCount = findViewById(R.id.more_txt_typecount);
        areaCount = findViewById(R.id.more_txt_areacpunt);
        intervalCount = findViewById(R.id.more_txt_interval);
        labelCount = findViewById(R.id.more_txt_tagcount);
        otherCount = findViewById(R.id.more_txt_othercount);
        dateCount = findViewById(R.id.more_txt_date);
        optionCount = findViewById(R.id.more_txt_optioncount);

        yes = findViewById(R.id.yes);
        type.setSelected(true);
        type.setSelected(true);

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
    public void onClick(View v) {
        resetNavigation();
        v.setSelected(true);
        switch (v.getId()) {
            case R.id.more_view_transtype:
                turnToFragment(TranTypeFragment.newInstance(request.getTransactionTypes()));
                break;
            case R.id.more_view_area:
                turnToFragment(AreaFragment.newInstance(areaParam));
                break;
            case R.id.more_view_sale:
                SaleFragment saleFragment = SaleFragment.newInstance(salePrices);
                saleFragment.setShowGreenTxt(false);
                turnToFragment(saleFragment);
                break;
            case R.id.more_view_size:
                turnToFragment(SizeFragment.newInstance(sizeParam));
                break;
            case R.id.more_view_rent:
                turnToFragment(RentFragment.newInstance(rentPrices));
                break;
            case R.id.more_view_option:
                L.d("Filtrate", "" + sysOptions.toString());
                turnToFragment(TranOptionFragment.newInstance(sysOptions));
                break;
            case R.id.more_view_other:
                turnToFragment(TranOtherFragment.newInstance(request));
                break;
            case R.id.more_view_date:
                turnToFragment(TransDateFragment.newInstance(request));
                break;
            case R.id.more_view_label:
                turnToFragment(TagFragment.newInstance(tagList, request.getBuildingUsages()));
                break;
            case R.id.more_view_interval:
                turnToFragment(IntervalFragment.newInstance(intervalList, request.getRoomCounts()));
                break;
            case R.id.yes:
                TransRequestSaveParams params = TransRequestParamsManager.getParams();
                if (request.getBuildingUsages() != null)
                    params.setTagList(getSearchOption(tagList, request.getBuildingUsages()));
                if (request.getRoomCounts() != null)
                    params.setIntervalList(getSearchOption(intervalList, request.getRoomCounts()));
                TransRequestParamsManager.setParams(params);
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable(TRANS_PARAMS, request);
                L.d("OnActivityBack", request.toString());
                intent.putExtras(bundle);
                this.setResult(CODE_TRAN_FILTRATE, intent);
                finish();
                break;
            case R.id.back:
                finish();
                break;
        }
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

    private void turnToFragment(android.support.v4.app.Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_content, fragment);
        transaction.commit();
    }

    @Override
    public void onRentChange(RentFragment.RentPrice rentPrice) {

        L.d("onRentChange", rentPrice.startPrice + " : " + rentPrice.endPrice);
//        if ((rentPrice.startPrice != null && !rentPrice.startPrice.equals("") || (rentPrice.endPrice != null && !rentPrice.endPrice.equals("")))) {
//            rentPriceCount.setVisibility(View.VISIBLE);
//            rentPriceCount.setText("1");
//            rentPrices = rentPrice;
//        }

        if ((rentPrice.startPrice != null && !rentPrice.startPrice.equals("") || (rentPrice.endPrice != null && !rentPrice.endPrice.equals("")))) {
            rentPriceCount.setVisibility(View.VISIBLE);
            rentPriceCount.setText("1");
            rentPrices = rentPrice;
        } else {
            rentPriceCount.setVisibility(View.GONE);
            rentPriceCount.setText(null);
        }

        request.setRentPriceFrom(rentPrice.startPrice);
        request.setRentPriceTo(rentPrice.endPrice);
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

        L.d("onSaleChange", salePrice.startPrice + " : " + salePrice.endPrice);
        if ((salePrice.startPrice != null && !salePrice.startPrice.equals("") || (salePrice.endPrice != null && !salePrice.endPrice.equals("")))) {
            salePriceCount.setVisibility(View.VISIBLE);
            salePriceCount.setText("1");
        } else {
            salePriceCount.setVisibility(View.GONE);
            salePriceCount.setText(null);
        }

        L.d("Option", "- - - - - - - - - - - - - - - - - - - - - - - - -");

//        L.d("onSaleChange", salePrice.startPrice + " : " + salePrice.endPrice);
//        if ((salePrice.startPrice != null && !salePrice.startPrice.equals("") || (salePrice.endPrice != null && !salePrice.endPrice.equals("")))) {
//            salePriceCount.setVisibility(View.VISIBLE);
//            salePriceCount.setText("1");
//        }

        salePrices = salePrice;
        request.setSellPriceFrom(salePrice.startPrice);
        request.setSellPriceTo(salePrice.endPrice);
        sysOptions.isSorporationTransferre = salePrice.isShowGreen;
        onTransOptionChange(sysOptions);
    }

    @Override
    public void onInteraction(String fragmentType, Object viewData) {

    }

    @Override
    public void onAreaChange(AreaFragment.AreaParam areaParam) {
        this.areaParam = areaParam;

        if ((areaParam.areaFrom != null && !areaParam.areaFrom.equals("") || (areaParam.areaTo != null && !areaParam.areaTo.equals("")))) {
            areaCount.setVisibility(View.VISIBLE);
            areaCount.setText("1");
        } else {
            areaCount.setVisibility(View.GONE);
            areaCount.setText(null);
        }

//        if (areaParam.areaType != 0) {
//
//            areaCount.setVisibility(View.VISIBLE);
//            areaCount.setText("1");
//        }
        if (areaParam.areaType == AREA_USE) {
            request.setSquareUseFrom(areaParam.areaFrom);
            request.setSquareUseTo(areaParam.areaTo);
            request.setSquareFrom(null);
            request.setSquareTo(null);
        } else {
            request.setSquareFrom(areaParam.areaFrom);
            request.setSquareTo(areaParam.areaTo);
            request.setSquareUseFrom(null);
            request.setSquareUseTo(null);
        }
    }

    @Override
    public void onTransOptionChange(TranOptionFragment.Option option) {

        L.d("onTransOptionChange", option.toString());
        sysOptions = option;
        int count = 0;

        for (Field field : option.getClass().getDeclaredFields()) {
            String type = field.getGenericType().toString();
            field.setAccessible(true);
            L.d("onTransOptionChange", "type: " + type);
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

        if (option.isSorporationTransferre)
            request.setSalePricePremiumUnpaid(true + "");
        else request.setSalePricePremiumUnpaid(null);

        if (option.isTransferred)
            request.setIsTransferred(true + "");
        else request.setIsTransferred(null);

        if (option.isOStatus)
            request.setIsOStatus(true + "");
        else request.setIsOStatus(null);

        if (option.isCorporationTransferre)
            request.setIsCorporationTransferre(true + "");
        else request.setIsCorporationTransferre(null);

        if (option.isConfirmed)
            request.setIsConfirmed(true + "");
        else request.setIsConfirmed(null);

        if (option.isDorporationTransferre)
            request.setIsDevelopmentEndCredits(true + "");
        else request.setIsDevelopmentEndCredits(null);

        if (count == 0) optionCount.setVisibility(View.GONE);
        else {
            optionCount.setVisibility(View.VISIBLE);
            optionCount.setText(count + "");
        }
    }


    @Override
    public void onSizeChange(SizeFragment.SizeParam sizeParam) {
        this.sizeParam = sizeParam;
//        if (sizeParam.avgType != 0) {
//            sizePriceCount.setVisibility(View.VISIBLE);
//            sizePriceCount.setText("1");
//        }

        if ((sizeParam.startPrice != null && !sizeParam.startPrice.equals("") || (sizeParam.endPrice != null && !sizeParam.endPrice.equals("")))) {
            sizePriceCount.setVisibility(View.VISIBLE);
            sizePriceCount.setText("1");
        } else {
            sizePriceCount.setVisibility(View.GONE);
            sizePriceCount.setText(null);
        }

        request.setPriceUnitFrom(sizeParam.startPrice);
        request.setPriceUnitTo(sizeParam.endPrice);
        request.setPriceUnitType(sizeParam.avgType + "");
    }

    @Override
    public void onTagChange(List<String> tags) {
        request.setBuildingUsages(tags);
        if (tags != null && !tags.isEmpty()) {
            labelCount.setText(tags.size() + "");
            labelCount.setVisibility(View.VISIBLE);
        } else labelCount.setVisibility(View.GONE);
    }

    @Override
    public void onIntervalChange(List<String> intervals) {
        request.setRoomCounts(intervals);
        if (intervals != null && !intervals.isEmpty()) {
            intervalCount.setText(intervals.size() + "");
            intervalCount.setVisibility(View.VISIBLE);
        } else intervalCount.setVisibility(View.GONE);
    }

    @Override
    public void onTranTypeChange(String type) {

        request.setTransactionTypes(type);

        L.d("onTranTypeChange", type);
        int count = 0;
        int index = 0;
        String key = ",";
        if (type != null && !type.equals("")) count = 1;

        while ((index = type.indexOf(key, index)) != -1) {
            //每循环一次，就要明确下一次查找的起始位置。
            index = index + key.length();
            //每查找一次，count自增。
            count++;
        }

        if (count == 0) typeCount.setVisibility(View.GONE);
        else {
            typeCount.setVisibility(View.VISIBLE);
            typeCount.setText(count + "");
        }
    }

    @Override
    public void onOtherChange(TransListRequest description) {
        request = description;
        int i = 0;
        if (!TextUtil.isEmply(description.getContactValue())) {
            i++;
        }
        if (!TextUtil.isEmply(description.getContactName())) {
            i++;
        }

        if (i == 0) {
            otherCount.setVisibility(View.GONE);
            request.setContactSearchType(null);
        } else {
            otherCount.setVisibility(View.VISIBLE);
            otherCount.setText(i + "");
        }
    }


    @Override
    public void onDateChange(TransListRequest request) {
        this.request = request;

        L.d("onDateChange",request.toString());

        int count = 0;

        if (!TextUtil.isEmply(request.getCompleteDateTo()) || !TextUtil.isEmply(request.getCompleteDateFrom())) {
            count++;
        }
        if (!TextUtil.isEmply(request.getTransactionDateFrom()) && !TextUtil.isEmply(request.getTransactionDateTo())) {
            count++;
        }
        if (!TextUtil.isEmply(request.getPrelimDateFrom()) && !TextUtil.isEmply(request.getPrelimDateTo())) {
            count++;
        }
        if (!TextUtil.isEmply(request.getFormalDateFrom()) && !TextUtil.isEmply(request.getFormalDateTo())) {
            count++;
        }
        if (!TextUtil.isEmply(request.getRentDateFrom()) && !TextUtil.isEmply(request.getRentDateTo())) {
            count++;
        }

        this.request.setTrusactionDate(null);

        if (count == 0) dateCount.setVisibility(View.GONE);
        if (count != 0) {
            dateCount.setVisibility(View.VISIBLE);
            dateCount.setText(count + "");
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

}
