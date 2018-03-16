package com.centanet.hk.aplus.Views.HouseDetailView.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.TextUtil;
import com.centanet.hk.aplus.Widgets.LineBreakLayout;
import com.centanet.hk.aplus.Widgets.SmallItemView;
import com.centanet.hk.aplus.entity.detail.DetailHouse;
import com.centanet.hk.aplus.eventbus.MessageEventBus;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.DetailRefreshView.DETAIL_DETAILDATA;


/**
 * Created by mzh1608258 on 2018/1/3.
 */

public class BasicInfoFragmentAbst extends Fragment {

    private View view;
    private LineBreakLayout tagLayout;
    private String thiz = getClass().getSimpleName();
    private List<String> labelList;
    private DetailHouse detailHouseData;
    private SmallItemView useRvgPriceTxt, reallyRvgPriceTxt, useRvgRentTxt, reallyRvgRentTxt, entrust_3_txt, entrust_5_txt;
    private SmallItemView conjectureDateTxt, ssdTxt, rvdTxt, useAreaTxt, reallyAreaTxt, usePercentTxt, rvdDateTxt;
    private SmallItemView openDateTxt, houseTypeTxt, carPlaceTxt, intervalTxt, numberTxt, directionTxt, houseSumTxt, fromTxt;
    private SmallItemView searchTxt, attentionTxt, supplementTxt, customization_1_Txt, customization_2_Txt, customization_3_Txt;
    private SmallItemView remarkTxt, moveInDateTxt, managementPriceTxt;
    private TextView tipTxt, salePriceTxt, rentPriceTxt;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_basicinfo, null);
        setViews();
        initDATA();
        return view;
    }

    private void setViews() {
        tagLayout = view.findViewById(R.id.text_ll_taps_layout);
        useRvgPriceTxt = view.findViewById(R.id.detail_txt_use_rvg_price);
        tagLayout.setItemContentLayoutID(R.layout.item_label_taps);

        reallyRvgPriceTxt = view.findViewById(R.id.detail_txt_really_rvg_price);
        useRvgPriceTxt = view.findViewById(R.id.detail_txt_use_rvg_price);
        reallyRvgRentTxt = view.findViewById(R.id.detail_txt_really_rvg_rent);
        useRvgRentTxt = view.findViewById(R.id.detail_txt_use_rvg_rent);

        entrust_3_txt = view.findViewById(R.id.detail_txt_entrust_3);
        entrust_5_txt = view.findViewById(R.id.detail_txt_entrust_5);

        conjectureDateTxt = view.findViewById(R.id.detail_txt_conjecture_data);
        ssdTxt = view.findViewById(R.id.detail_txt_ssd);
        useAreaTxt = view.findViewById(R.id.detail_txt_use_area);
        reallyAreaTxt = view.findViewById(R.id.detail_txt_really_area);
        usePercentTxt = view.findViewById(R.id.detail_txt_usepercent);
        tipTxt = view.findViewById(R.id.detail_txt_tips);
        rvdDateTxt = view.findViewById(R.id.detail_txt_rvd_data);

        openDateTxt = view.findViewById(R.id.detail_open_date);
        houseTypeTxt = view.findViewById(R.id.detail_txt_house_type);
        carPlaceTxt = view.findViewById(R.id.detail_txt_catplace);
        intervalTxt = view.findViewById(R.id.detail_txt_interval);
        numberTxt = view.findViewById(R.id.detail_txt_number);
        directionTxt = view.findViewById(R.id.detail_txt_direction);
        houseSumTxt = view.findViewById(R.id.detail_txt_housesum);
        fromTxt = view.findViewById(R.id.detail_txt_from);
        searchTxt = view.findViewById(R.id.detail_txt_search);
        attentionTxt = view.findViewById(R.id.detail_txt_attention);

        supplementTxt = view.findViewById(R.id.detail_txt_supplement);
        customization_1_Txt = view.findViewById(R.id.detail_txt_customization_1);
        customization_2_Txt = view.findViewById(R.id.detail_txt_customization_2);
        customization_3_Txt = view.findViewById(R.id.detail_txt_customization_3);
        remarkTxt = view.findViewById(R.id.detail_txt_remark);
        moveInDateTxt = view.findViewById(R.id.detail_txt_movein_date);
        managementPriceTxt = view.findViewById(R.id.detail_txt_management_cost);

        salePriceTxt = view.findViewById(R.id.detail_txt_sale_price);
        rentPriceTxt = view.findViewById(R.id.detail_txt_rent_price);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEventBus messageEvent) {
        if (messageEvent.getMsg() == DETAIL_DETAILDATA) {
            detailHouseData = (DetailHouse) messageEvent.getObject();
            setViewsData(detailHouseData);
        }
    }

    private void setViewsData(DetailHouse detailHouseData) {

        useRvgPriceTxt.setContentName(detailHouseData.getActualSalePriceUnit());
        reallyRvgPriceTxt.setContentName(detailHouseData.getSalePriceUnit());
        useRvgRentTxt.setContentName(detailHouseData.getActualRentPriceUnit());
        reallyRvgRentTxt.setContentName(detailHouseData.getRentPriceUnit());
        entrust_3_txt.setContentName(detailHouseData.getPowerOfAttorneyThree());
        entrust_5_txt.setContentName(detailHouseData.getPowerOfAttorneyFive());
        conjectureDateTxt.setContentName(detailHouseData.getEstimatedDate());
        rvdDateTxt.setContentName(detailHouseData.getProvideDate());
        openDateTxt.setContentName(detailHouseData.getRegisterDate());
        usePercentTxt.setContentName(detailHouseData.getUtilityRatio());
        tipTxt.setText(detailHouseData.getPrompt());
        directionTxt.setContentName(detailHouseData.getHouseDirection());
        intervalTxt.setContentName(detailHouseData.getPropertyInterval());
        houseTypeTxt.setContentName(detailHouseData.getPropertyUsage());
        houseSumTxt.setContentName(detailHouseData.getAllFloor());
        carPlaceTxt.setContentName(detailHouseData.getParkingNumber());
        fromTxt.setContentName(detailHouseData.getPropertySource());
        supplementTxt.setContentName(detailHouseData.getSupply());
        customization_1_Txt.setContentName(detailHouseData.getCustomField1Name());
        customization_2_Txt.setContentName(detailHouseData.getCustomField2Name());
        customization_3_Txt.setContentName(detailHouseData.getCustomField3Name());
        remarkTxt.setContentName(detailHouseData.getRemark());
        numberTxt.setContentName(detailHouseData.getRemark());
        managementPriceTxt.setContentName(detailHouseData.getMgrFee());

        String squareUseFoot = detailHouseData.getSquareUseFoot();
        String squareSource = detailHouseData.getSquareSource();
        setUseSquare(squareUseFoot, squareSource);

        reallyAreaTxt.setContentName(detailHouseData.getSquareFoot());
        ssdTxt.setContentName(detailHouseData.getSSDInfo());
        moveInDateTxt.setContentName(detailHouseData.getCompleteYear());
        attentionTxt.setContentName(detailHouseData.getPropertyNote());
        searchTxt.setContentName(detailHouseData.getSearchDate());

        setSalePriceTxt(detailHouseData.getSalePrice(), detailHouseData.getSaleFloorPriceFormate());
        setRentPriceTxt(detailHouseData.getRentPrice(), detailHouseData.getRentFloorPriceFormate());

        if (detailHouseData.getPropertyTags() != null) {
            String[] tags = detailHouseData.getPropertyTags().split(",");
            for (String tag : tags)
                tagLayout.addItem(tag);
        }

    }

    private void setRentPriceTxt(String rentPrice, String rentFloorPrice) {
        String price = "租:" + rentPrice.split("\\.")[0];
        if (rentFloorPrice != "") {
            price = price + "(" + rentFloorPrice.split("\\.")[0] + ")";
        }
        rentPriceTxt.setText(price);
    }

    private void setSalePriceTxt(String salePrice, String saleFloorPrice) {
        String price = "售:" + salePrice + "萬";
        if (saleFloorPrice != "") {
            price = price + "(" + saleFloorPrice + ")";
        }
        salePriceTxt.setText(price);
    }

    private void setUseSquare(String squareUseFoot, String squareSource) {
        Spanned spanned = null;
        if (squareSource != null && squareSource != "") {
            if (squareSource == "RVD" || squareSource == "rvd")
                spanned = TextUtil.changeKeyWordColor(squareUseFoot + "{" + squareSource + "}", squareSource, Color.RED + "");
            else
                spanned = TextUtil.changeKeyWordColor(squareUseFoot + "{" + squareSource + "}", squareSource, Color.BLUE + "");
        } else {
            spanned = TextUtil.changeTextColor(squareUseFoot, Color.BLACK + "");
        }
        useAreaTxt.setContentName(spanned);

    }


    private void initDATA() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
