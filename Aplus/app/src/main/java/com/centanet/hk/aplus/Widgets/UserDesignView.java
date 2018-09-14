package com.centanet.hk.aplus.Widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.DensityUtil;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.TextUtil;
import com.centanet.hk.aplus.bean.auto_estate.PropertyParamHints;
import com.centanet.hk.aplus.bean.build_tag.TagInfo;
import com.centanet.hk.aplus.bean.district.DistrictItem;
import com.centanet.hk.aplus.bean.http.HouseDescription;
import com.centanet.hk.aplus.bean.params.SystemParamItems;
import com.centanet.hk.aplus.bean.save_option.PropertyRequestSaveParams;
import com.centanet.hk.aplus.bean.userdesign_option.PropertySearchHistory;
import com.centanet.hk.aplus.manager.PropertyRequestParamsManager;

import java.util.ArrayList;
import java.util.List;

import static com.centanet.hk.aplus.common.CommandField.APPropertyDateType.changePriceDate;
import static com.centanet.hk.aplus.common.CommandField.APPropertyDateType.estimatedDate;
import static com.centanet.hk.aplus.common.CommandField.APPropertyDateType.lasetUpdate;
import static com.centanet.hk.aplus.common.CommandField.APPropertyDateType.lastFollowDate;
import static com.centanet.hk.aplus.common.CommandField.APPropertyDateType.onlyTrustEndDate;
import static com.centanet.hk.aplus.common.CommandField.APPropertyDateType.onlyTrustStartDate;
import static com.centanet.hk.aplus.common.CommandField.APPropertyDateType.registDate;
import static com.centanet.hk.aplus.common.CommandField.APPropertyDateType.statusChangedDate;
import static com.centanet.hk.aplus.common.CommandField.PriceUnitType.AVG_GREEN_REALLY_SALE;
import static com.centanet.hk.aplus.common.CommandField.PriceUnitType.AVG_GREEN_USE_SALE;
import static com.centanet.hk.aplus.common.CommandField.PriceUnitType.AVG_REALLY_RENT;
import static com.centanet.hk.aplus.common.CommandField.PriceUnitType.AVG_REALLY_SALE;
import static com.centanet.hk.aplus.common.CommandField.PriceUnitType.AVG_USE_RENT;
import static com.centanet.hk.aplus.common.CommandField.PriceUnitType.AVG_USE_SALE;

/**
 * Created by yangzm4 on 2018/8/8.
 */

public class UserDesignView extends LinearLayout {

    private Context context;
    private OnItemClickLisenter onClickListener;

    public UserDesignView(Context context) {
        this(context, null);
    }

    public UserDesignView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UserDesignView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    private void initView() {
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, MyRadioGroup.LayoutParams.WRAP_CONTENT);
//        params.setLayoutDirection(VERTICAL);
//        this.setLayoutParams(params);
    }

    public void setOnClickListener(OnItemClickLisenter onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void addItem(List<PropertySearchHistory> propertySearchHistories) {
        for (int i = propertySearchHistories.size() - 1; i >= 0; i--) {
            addItem(propertySearchHistories.get(i), i);
            L.d("UserDesignView", i + "");
        }
    }

    public void addItem(PropertySearchHistory propertySearchHistory, int position) {


        View view = LayoutInflater.from(context).inflate(R.layout.item_list_userdesign, null, false);
        view.setTag(position);
        view.setOnClickListener(v -> {
            if (onClickListener != null) onClickListener.onClick(v, position);
        });
        LineBreakLayout lineBreakLayout = view.findViewById(R.id.linebreaklayout);
        LineBreakLayout addresslb = view.findViewById(R.id.addresslb);
        lineBreakLayout.setItemContentLayoutID(R.layout.item_userdesign);

        addresslb.setItemContentLayoutID(R.layout.item_dialog_usedesign_double);
        lineBreakLayout.setRowSpace(DensityUtil.dip2px(getContext(),8));
        addresslb.setRowSpace(DensityUtil.dip2px(getContext(),8));

        addresslb.setItemOnclickListener((view1, contentView, position1) -> {
            if (onClickListener != null) onClickListener.onClick(view1, position);
        });
        lineBreakLayout.setItemOnclickListener((view1, contentView, position1) -> {
            if (onClickListener != null) onClickListener.onClick(view1, position);
        });

        ((TextView) (view.findViewById(R.id.title))).setText(propertySearchHistory.getOptionMame());

        PropertyRequestSaveParams paramsManager = propertySearchHistory.getManager();
//            PropertyRequestSaveParams paramsManager = new PropertyRequestSaveParams();
        HouseDescription description = propertySearchHistory.getHouseDescription();
//            HouseDescription description = new HouseDescription();

        if (description == null) return;

        List<String> list = new ArrayList<>();

        if (description.getSSDType() != null && !description.getSSDType().equals("")) {
            list.add(description.getSSDType() + "%");
        }

        List<SystemParamItems> status = paramsManager.getStatulist();
        L.d("ParamValue", "Statu: " + status.toString());
        if (status != null && !status.isEmpty()) {
            for (SystemParamItems item : status) {
                list.add(item.getItemText());
            }
        }

        List<SystemParamItems> directions = paramsManager.getDirectionList();
        L.d("ParamValue", "Directions: " + status.toString());
        if (directions != null && !directions.isEmpty()) {
            for (SystemParamItems item : directions) {
                list.add(item.getItemText());
            }
        }

        List<SystemParamItems> intervals = paramsManager.getIntervalList();
        L.d("ParamValue", "Intervals: " + status.toString());
        if (intervals != null && !intervals.isEmpty()) {
            for (SystemParamItems item : intervals) {
                list.add(item.getItemText());
            }
        }

        List<SystemParamItems> tags = paramsManager.getTagList();
        L.d("ParamValue", "Tags: " + status.toString());
        if (tags != null && !tags.isEmpty()) {
            for (SystemParamItems item : tags) {
                list.add(item.getItemText());
            }
        }

        List<PropertyParamHints> hints = paramsManager.getAddress();
        L.d("ParamValue", "Hints: " + status.toString());
        if (hints != null && !hints.isEmpty()) {
            for (PropertyParamHints hint : hints) {
//                list.add(hint.getAddressName());
                addresslb.addItem(parseData(hint));
            }
        }

        List<DistrictItem> districtItems = paramsManager.getArea();
        L.d("ParamValue", "DistrictItems: " + status.toString());
        if (districtItems != null && !districtItems.isEmpty()) {
            for (DistrictItem item : districtItems) {
//                addresslb.addItem(parseData(item));
                list.add(item.getDistrictName());
            }
        }

        if (description.getSalePriceFrom() != null || description.getSalePriceTo() != null) {
            String str = "";
            if (description.getSalePriceFrom() == null || description.getSalePriceFrom().equals("")) {
                str = str + "不限";
            } else str = str + description.getSalePriceFrom() + "萬";
            str = str + "-";
            if (description.getSalePriceTo() == null || description.getSalePriceTo().equals("")) {
                str = str + "不限";
            } else str = str + description.getSalePriceTo() + "萬";
            list.add(str);
        }

        if (description.getRentPriceFrom() != null || description.getRentPriceTo() != null) {
            String str = "";
            if (description.getRentPriceFrom() == null || description.getRentPriceFrom().equals("")) {
                str = str + "不限";
            } else str = str + "$" + description.getRentPriceFrom() + ",000";
            str = str + "-";
            if (description.getRentPriceTo() == null || description.getRentPriceTo().equals("")) {
                str = str + "不限";
            } else str = str + "$" + description.getRentPriceTo() + ",000";
            list.add(str);
        }

        if (description.getKeywords() != null && !description.getKeywords().equals("")) {
            list.add(description.getKeywords());
        }

        List<TagInfo> tagInfos = paramsManager.getTagInfos();
        if (tagInfos != null && !tagInfos.isEmpty()) {
            for (TagInfo tagInfo : tagInfos)
                list.add(tagInfo.getTagChineseName());
        }

        if ((description.getSquareFrom() != null && !description.getSquareFrom().equals("")) || (description.getSquareTo() != null && !description.getSquareTo().equals(""))) {
            String str = "建築面積:";
            if (description.getSquareFrom() == null) {
                str = str + "不限";
            } else str = str + description.getSquareFrom() + "呎";
            str = str + "-";
            if (description.getSquareTo() == null) {
                str = str + "不限";
            } else str = str + description.getSquareTo() + "呎";
            list.add(str);
        }

        //todo 清除的时候清楚五个参数
        if ((description.getSquareUseFrom() != null && !description.getSquareUseFrom().equals("")) || (description.getSquareUseTo() != null && !description.getSquareUseTo().equals(""))) {
            String str = "實用面積:";
            if (description.getSquareUseFrom() == null && !description.getSquareUseFrom().equals("")) {
                str = str + "不限";
            } else str = str + description.getSquareUseFrom() + "呎";
            str = str + "-";
            if (description.getSquareUseTo() == null && !description.getSquareUseTo().equals("")) {
                str = str + "不限";
            } else str = str + description.getSquareUseTo() + "呎";
            list.add(str);
        }

        if (description.getFloors() != null && !description.getFloors().equals("")) {
            list.add(description.getFloors());
        }

        if (description.getUnits() != null && !description.getUnits().equals("")) {
            list.add(description.getUnits());
        }

        if (description.isHasParkingNumber()) {
            list.add(context.getString(R.string.car_place));
        }

        if (description.isShowSalePricePremiumUnpaid()) {
            list.add(context.getString(R.string.green_price));
        }

        if (description.getHasSalePricePremiumUnpaid()) {
            list.add(context.getString(R.string.connect_green_price));
        }

        if (description.getHasPropertyKey()) {
            list.add(context.getString(R.string.key));
        }

        if (description.isHasConfirmTransaction()) {
            list.add(context.getString(R.string.unconfirm_tran));
        }

        if (description.isHotlist()) {
            list.add(context.getString(R.string.hotlist));
        }

        if (description.isNoneSSD()) {
            list.add(context.getString(R.string.no_ssd));
        }

        if (description.isOnlyTrust()) {
            list.add(context.getString(R.string.exclusive));
        }

        if (description.isHasOptout()) {
            list.add(context.getString(R.string.o_property));
        }

        if (description.isHasDevelopmentEndCredits()) {
            list.add(context.getString(R.string.pending));
        }

        if (description.isProxy()) {
            list.add(context.getString(R.string.power_of_attorney));
        }

        if (description.getTrustorName() != null && !description.getTrustorName().equals("")) {
            list.add(context.getString(R.string.owner) + ":" + description.getTrustorName());
        }

        if (description.getMobile() != null && !description.getMobile().equals("")) {
            list.add(context.getString(R.string.phone) + ":" + description.getMobile());
        }

        L.d("onOtherChange", description.getPropertyDateFrom() + "   " + description.getPropertyDateFrom());
        if ((description.getPropertyDateFrom() != null && !description.getPropertyDateFrom().equals("")) || (description.getPropertyDateTo() != null && !description.getPropertyDateTo().equals(""))) {

            String str = "";
            switch (Integer.parseInt(description.getPropertyDateType())) {
                case statusChangedDate:
                    str = "改盤日期:";
                    break;
                case lasetUpdate:
                    str = "最后修改日期:";
                    break;
                case lastFollowDate:
                    str = "最后修改日期:";
                    break;
                case registDate:
                    str = "開盤日期:";
                    break;
                case estimatedDate:
                    str = "估計日期:";
                    break;
                case changePriceDate:
                    str = "最后修改日期:";
                    break;
                case onlyTrustStartDate:
                    str = "委託書開始日:";
                    break;
                case onlyTrustEndDate:
                    str = "委託書到期日:";
                    break;
            }

            if (description.getPropertyDateFrom() == null && !description.getPropertyDateFrom().equals("")) {
                str = str + "不限";
            } else str = str + description.getPropertyDateFrom();

            str = str + "至";

            if (description.getPropertyDateTo() == null && description.getPropertyDateTo().equals("")) {
                str = str + "不限";
            } else str = str + description.getPropertyDateTo();
            L.d("onOtherChange", str);
            list.add(str);

            if (Integer.parseInt(description.getPropertyDateType()) == statusChangedDate && (description.getIncludedPropertyStatusFrom() != null || description.getIncludedPropertyStatusTo() != null)) {
                String s = "";
                if (description.getIncludedPropertyStatusFrom() != null) {
                    for (SystemParamItems items : paramsManager.getStatuFrom()) {
                        s = s + items.getItemText().substring(0, items.getItemText().indexOf("(")) + ",";
                    }
                    s = s.substring(0, s.length() - 1);
                } else s = "不限";
                s = s + "->";
                if (description.getIncludedPropertyStatusTo() != null) {
                    for (SystemParamItems items : paramsManager.getStatuTo()) {
                        s = s + items.getItemText().substring(0, items.getItemText().indexOf("(")) + ",";
                    }
                    s = s.substring(0, s.length() - 1);
                } else s = s + "不限";
                list.add(s);
            }
        }

        if ((description.getCompleteYearFrom() != null && !description.getCompleteYearFrom().equals("")) || (description.getCompleteYearTo() != null && !description.getCompleteYearTo().equals(""))) {
            String str = context.getString(R.string.finish_year) + ":";
            if (description.getCompleteYearFrom() == null || description.getCompleteYearFrom().equals("")) {
                str = str + "不限";
            } else str = str + description.getCompleteYearFrom();
            str = str + "-";
            if (description.getCompleteYearTo() == null || description.getCompleteYearTo().equals("")) {
                str = str + "不限";
            } else str = str + description.getCompleteYearTo();
            list.add(str);
        }

        if ((description.getPriceUnitFrom() != null && !description.getPriceUnitFrom().equals("")) || (description.getPriceUnitTo() != null && !description.getPriceUnitTo().equals(""))) {
            String str = "";
            L.d("SizeParam_dialog", description.getPriceUnitType());
            switch (Integer.parseInt(description.getPriceUnitType())) {
                case AVG_USE_SALE:
                    str = context.getString(R.string.use_aver_sale) + ":";
                    break;
                case AVG_USE_RENT:
                    str = context.getString(R.string.use_aver_rent) + ":";
                    break;
                case AVG_REALLY_SALE:
                    str = context.getString(R.string.really_aver_sale) + ":";
                    break;
                case AVG_REALLY_RENT:
                    str = context.getString(R.string.really_aver_rent) + ":";
                    break;
                case AVG_GREEN_USE_SALE:
                    str = context.getString(R.string.really_aver_sale_green) + ":";
                    break;
                case AVG_GREEN_REALLY_SALE:
                    str = context.getString(R.string.use_aver_sale_green) + ":";
                    break;
            }
            if (description.getPriceUnitFrom() == null || description.getPriceUnitFrom().equals("")) {
                str = str + "不限";
            } else str = str + description.getPriceUnitFrom();
            str = str + "-";
            if (description.getPriceUnitTo() == null || description.getPriceUnitTo().equals("")) {
                str = str + "不限";
            } else str = str + description.getPriceUnitTo();
            list.add(str);
        }

        lineBreakLayout.addItem(list);
        addView(view);

        Space space = new Space(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(context, 10));
        space.setLayoutParams(params);
        addView(space);

    }

    private String parseData(PropertyParamHints s) {
        String labelString = null;

        String address = "";
        String street = "";
        if (!TextUtil.isEmply(s.getDistrictName()) && !TextUtil.isEmply(s.getAreaName())) {
            address = address + s.getDistrictName() + "/" + s.getAreaName();
        } else if (!TextUtil.isEmply(s.getDistrictName())) {
            address = address + s.getDistrictName();
        } else if (!TextUtil.isEmply(s.getAreaName())) {
            address = address + s.getAreaName();
        }

        if (!TextUtil.isEmply(s.getEnAddressName())) {
            street = s.getEnAddressName();
        }

        if (!TextUtil.isEmply(address) && !TextUtil.isEmply(street)) {
            labelString = address + "\n" + street;
        } else if (!TextUtil.isEmply(address)) {
            labelString = address;
        } else if (!TextUtil.isEmply(street)) {
            labelString = street;
        }

        return labelString;
    }

    public interface OnItemClickLisenter {
        void onClick(View v, int position);
    }
}
