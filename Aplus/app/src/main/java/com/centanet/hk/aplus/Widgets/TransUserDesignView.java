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
import com.centanet.hk.aplus.bean.save_option.TransRequestSaveParams;
import com.centanet.hk.aplus.bean.translist.TransListRequest;
import com.centanet.hk.aplus.bean.userdesign_option.PropertySearchHistory;
import com.centanet.hk.aplus.bean.userdesign_option.TransSearchHistory;

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

public class TransUserDesignView extends LinearLayout {

    private Context context;
    private OnItemClickLisenter onClickListener;

    public TransUserDesignView(Context context) {
        this(context, null);
    }

    public TransUserDesignView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TransUserDesignView(Context context, AttributeSet attrs, int defStyleAttr) {
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

    public void addItem(List<TransSearchHistory> propertySearchHistories) {
        for (int i = propertySearchHistories.size() - 1; i >= 0; i--) {
            addItem(propertySearchHistories.get(i), i);
            L.d("UserDesignView", i + "");
        }
    }

    public void addItem(TransSearchHistory propertySearchHistory, int position) {

//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, MyRadioGroup.LayoutParams.WRAP_CONTENT);
//        params.setLayoutDirection(VERTICAL);
//        this.setLayoutParams(params);

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

        TransRequestSaveParams paramsManager = propertySearchHistory.getManager();
//            PropertyRequestSaveParams paramsManager = new PropertyRequestSaveParams();
        TransListRequest description = propertySearchHistory.getHouseDescription();
//            HouseDescription description = new HouseDescription();

        List<String> list = new ArrayList<>();


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

//        List<PropertyParamHints> hints = paramsManager.getAddress();
//        L.d("ParamValue", "Hints: " + status.toString());
//        if (hints != null && !hints.isEmpty()) {
//            for (PropertyParamHints hint : hints) {
//                list.add(hint.getAddressName());
//            }
//        }
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
                list.add(item.getDistrictName());
            }
        }

        if (description.getSellPriceFrom() != null || description.getSellPriceTo() != null) {
            String str = "";
            if (description.getSellPriceFrom() == null || description.getSellPriceFrom().equals("")) {
                str = str + "不限";
            } else str = str + description.getSellPriceFrom() + "萬";
            str = str + "-";
            if (description.getSellPriceTo() == null || description.getSellPriceTo().equals("")) {
                str = str + "不限";
            } else str = str + description.getSellPriceTo() + "萬";
            list.add(str);
        }

        if (description.getRentPriceFrom() != null || description.getRentPriceTo() != null) {
            String str = "";
            if (description.getRentPriceFrom() == null || description.getRentPriceFrom().equals("")) {
                str = str + "不限";
            } else str = str + "$" + description.getRentPriceFrom() + "000";
            str = str + "-";
            if (description.getRentPriceTo() == null || description.getRentPriceTo().equals("")) {
                str = str + "不限";
            } else str = str + "$" + description.getRentPriceTo() + "000";
            list.add(str);
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


        if (!TextUtil.isEmply(description.getTransactionTypes())) {
            String[] type = description.getTransactionTypes().replace(" ", "").split(",");
            List<String> types = new ArrayList<>();
            for (int i = 0; i < type.length; i++) {
                switch (Integer.parseInt(type[i])) {
                    case 1:
                        types.add(context.getString(R.string.centa_sale));
                        break;
                    case 2:
                        types.add(context.getString(R.string.centa_rent));
                        break;
                    case 3:
                        types.add(context.getString(R.string.other_sale));
                        break;
                    case 4:
                        types.add(context.getString(R.string.other_rent));
                        break;
                    case 5:
                        types.add(context.getString(R.string.internal));
                        break;
                }
            }
            list.addAll(types);
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


        if (Boolean.parseBoolean(description.getIsTransferred())) {
            list.add(context.getString(R.string.transferred));
        }

        if (Boolean.parseBoolean(description.getIsConfirmed())) {
            list.add(context.getString(R.string.confirmed));
        }

        if (Boolean.parseBoolean(description.getIsOStatus())) {
            list.add(context.getString(R.string.o_property));
        }

        if (Boolean.parseBoolean(description.getIsCorporationTransferre())) {
            list.add(context.getString(R.string.corporationTransferre));
        }

        if (Boolean.parseBoolean(description.getIsDevelopmentEndCredits())) {
            list.add(context.getString(R.string.developmentEndCredits));
        }

        if (Boolean.parseBoolean(description.getIsSalePricePremiumUnpaid())) {
            list.add(context.getString(R.string.green_price));
        }


        if (description.getContactName() != null && !description.getContactName().equals("")) {
            list.add(context.getString(R.string.owner) + ":" + description.getContactName());
        }

        if (description.getContactValue() != null && !description.getContactValue().equals("")) {
            list.add(context.getString(R.string.phone) + ":" + description.getContactValue());
        }


        if (description.getPrelimDateFrom() != null || description.getPrelimDateTo() != null) {
            String itemSre = context.getString(R.string.record_transaction_appointment) + ":";
            itemSre = itemSre + isStrNull(description.getPrelimDateFrom()) + "-" + isStrNull(description.getPrelimDateTo());
            list.add(itemSre);
        }
        if (description.getFormalDateFrom() != null || description.getFormalDateTo() != null) {
            String itemSre =  context.getString(R.string.record_transaction_official) + ":";
            itemSre = itemSre + isStrNull(description.getFormalDateFrom()) + "-" + isStrNull(description.getFormalDateTo());
            list.add(itemSre);
        }
        if (description.getCompleteDateFrom() != null || description.getCompleteDateTo() != null) {
            String itemSre =  context.getString(R.string.record_transaction_finish) + ":";
            itemSre = itemSre + isStrNull(description.getCompleteDateFrom()) + "-" + isStrNull(description.getCompleteDateTo());
            list.add(itemSre);
        }
        if (description.getRentDateFrom() != null || description.getRentDateTo() != null) {
            String itemSre =  context.getString(R.string.record_transaction_rentdate) + ":";
            itemSre = itemSre + isStrNull(description.getRentDateFrom()) + "-" + isStrNull(description.getRentDateTo());
            list.add(itemSre);
        }

//        if ((description.getCompleteYearFrom() != null && !description.getCompleteYearFrom().equals("")) || (description.getCompleteYearTo() != null && !description.getCompleteYearTo().equals(""))) {
//            String str = context.getString(R.string.finish_year) + ":";
//            if (description.getCompleteYearFrom() == null || description.getCompleteYearFrom().equals("")) {
//                str = str + "不限";
//            } else str = str + description.getCompleteYearFrom();
//            str = str + "-";
//            if (description.getCompleteYearTo() == null || description.getCompleteYearTo().equals("")) {
//                str = str + "不限";
//            } else str = str + description.getCompleteYearTo();
//            list.add(str);
//        }

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

    private String isStrNull(String date) {

        if (date != null && !date.equals("")) {
            return date;
        } else return context.getString(R.string.dialog_price_unlimit);
    }


    public interface OnItemClickLisenter {
        void onClick(View v, int position);
    }
}
