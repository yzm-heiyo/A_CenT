package com.centanet.hk.aplus.Views.Dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.centanet.hk.aplus.MyApplication;
import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.DensityUtil;
import com.centanet.hk.aplus.Utils.DialogUtil;
import com.centanet.hk.aplus.Utils.FileUtil;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.TextUtil;
import com.centanet.hk.aplus.Widgets.SmartLinBreakView;
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
import com.centanet.hk.aplus.manager.PropertyRequestParamsManager;
import com.centanet.hk.aplus.manager.TransRequestParamsManager;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
import static com.centanet.hk.aplus.common.CommandField.PriceUnitType.AVG_GREEN_REALLY_SALE;
import static com.centanet.hk.aplus.common.CommandField.PriceUnitType.AVG_GREEN_USE_SALE;
import static com.centanet.hk.aplus.common.CommandField.PriceUnitType.AVG_REALLY_RENT;
import static com.centanet.hk.aplus.common.CommandField.PriceUnitType.AVG_REALLY_SALE;
import static com.centanet.hk.aplus.common.CommandField.PriceUnitType.AVG_USE_RENT;
import static com.centanet.hk.aplus.common.CommandField.PriceUnitType.AVG_USE_SALE;


public class TransSearchOptionDialog extends DialogFragment implements View.OnClickListener {

    private Dialog dialog;
    private WindowManager.LayoutParams lp;

    private TextView yesTxt;

    private ImageView close;
    private TextView add;

    private OnItemClickLisenter onItemClickLisenter;
    private String thiz = getClass().getSimpleName();

    private TransListRequest request = new TransListRequest();
    private TransRequestSaveParams paramsManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        init();
        initLisenter();
        initData(request);
    }

    private void initLisenter() {
        yesTxt.setOnClickListener(this);
        close.setOnClickListener(this);
        add.setOnClickListener(this);

        paramsManager = TransRequestParamsManager.getParams();

        date.setOnItemChangeLisenter((view, contentView, position) -> {

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

        });

//        area.setOnItemChangeLisenter((view, contentView, position) -> {
//
//        });

        sort.setOnItemChangeLisenter((view, contentView, position) -> {
            request.setSortField("DisplayTransDate");
            request.setAscending(false);
        });

        transDate.setOnItemChangeLisenter((view, contentView, position) -> {
            request.setTrusactionDate(null);
        });

        address.setOnItemChangeLisenter((view, contentView, position) -> {
            PropertyParamHints s = paramsManager.getAddress().get(position);
            request.getSearcherAddress().remove(s.getKeyIdType() + ":" + s.getKeyId());
            L.d("Request_Params", request.toString());
            paramsManager.getAddress().remove(position);
        });

        tag.setOnItemChangeLisenter((view, contentView, position) -> {
            request.getBuildingUsages().remove(paramsManager.getTagList().get(position).getItemValue());
            paramsManager.getTagList().remove(position);
        });

        interval.setOnItemChangeLisenter((view, contentView, position) -> {
            request.getRoomCounts().remove(paramsManager.getIntervalList().get(position).getItemValue());
            paramsManager.getIntervalList().remove(position);
        });

        floot.setOnItemChangeLisenter((v, c, p) -> {
            request.setFloors(null);
        });
        unit.setOnItemChangeLisenter((v, c, p) -> {
            request.setUnits(null);
        });

        area.setOnItemChangeLisenter((v, c, p) -> {
//            request.setSquareUseTo(null);
//            request.setSquareUseFrom(null);
//            request.setSquareTo(null);
//            request.setSquareFrom(null);

            request.getDistrictListIds().remove(p);
            paramsManager.getArea().remove(p);
            L.d("getDistrictListIds", paramsManager.getArea().toString());
        });

        acereage.setOnItemChangeLisenter((view, contentView, position) -> {
            request.setSquareUseTo(null);
            request.setSquareUseFrom(null);
            request.setSquareTo(null);
            request.setSquareFrom(null);
        });

        sale.setOnItemChangeLisenter((v, c, p) -> {
            request.setSellPriceFrom(null);
            request.setSellPriceTo(null);
        });


        size.setOnItemChangeLisenter((v, c, p) -> {
            request.setPriceUnitType(null);
            request.setPriceUnitFrom(null);
            request.setPriceUnitTo(null);
        });

        other.setOnItemChangeLisenter((view, contentView, position) -> {
            String s = ((TextView) view).getText().toString();
            L.d("other", s);
            if (s.indexOf(getString(R.string.sale_buy_people)) != -1) {
                request.setContactName(null);
            }
            if (s.indexOf(getString(R.string.sale_people)) != -1) {
                request.setContactName(null);
            }
            if (s.indexOf(getString(R.string.buy_people)) != -1) {
                request.setContactName(null);
            }

            if (s.indexOf(getString(R.string.record_transaction_appointment)) != -1) {
                request.setPrelimDateFrom(null);
                request.setPrelimDateTo(null);
            }
            if (s.indexOf(getString(R.string.record_transaction_finish)) != -1) {
                request.setCompleteDateFrom(null);
                request.setCompleteDateTo(null);
            }
            if (s.indexOf(getString(R.string.record_transaction_official)) != -1) {
                request.setFormalDateFrom(null);
                request.setFormalDateTo(null);
            }
            if (s.indexOf(getString(R.string.record_transaction_rentdate)) != -1) {
                request.setRentDateFrom(null);
                request.setRentDateTo(null);
            }
            if (s.indexOf(getString(R.string.record_transaction_bargain)) != -1) {
                request.setTransactionDateFrom(null);
                request.setTransactionDateTo(null);
            }

            if (s.indexOf(getString(R.string.phone)) != -1) {
                request.setContactValue(null);
            }

//            if (s.indexOf("日期") != -1) {
//                request.setPropertyDateType(null);
//                request.setPropertyDateFrom(null);
//                request.setPropertyDateTo(null);
//                request.setIncludedPropertyStatusTo(null);
//                request.setIncludedPropertyStatusFrom(null);
//                paramsManager.getStatuTo().clear();
//                paramsManager.getStatuFrom().clear();
////                paramsManager.getStatuTo() = null;
//            }
        });

        rent.setOnItemChangeLisenter((view, contentView, position) -> {
            request.setRentPriceFrom(null);
            request.setRentPriceTo(null);
        });

        trantype.setOnItemChangeLisenter((view, contentView, position) -> {

            String[] type = request.getTransactionTypes().replace(" ", "").split(",");
            List<String> types = new ArrayList<>();
            for (int i = 0; i < type.length; i++) {
                types.add(type[i]);
            }

            String s = ((TextView) view).getText().toString();
            if (s.equals(getString(R.string.centa_sale)))
                types.remove("1");
            if (s.equals(getString(R.string.centa_rent)))
                types.remove("2");
            if (s.equals(getString(R.string.other_sale)))
                types.remove("3");
            if (s.equals(getString(R.string.other_rent)))
                types.remove("4");
            if (s.equals(getString(R.string.internal)))
                types.remove("5");

            if (types.isEmpty()) {
                trantype.setVisibility(View.GONE);
                request.setTransactionTypes(null);
            } else {
                request.setTransactionTypes(types.toString().replace("[", "").replace("]", ""));
            }
            L.d("TransactionTypes_", types.toString());
        });

        option.setOnItemChangeLisenter((view, contentView, position) -> {
            String s = ((TextView) view).getText().toString();
            if (s.equals(getString(R.string.confirmed)))
                request.setIsConfirmed(null);
            if (s.equals(getString(R.string.corporationTransferre)))
                request.setIsCorporationTransferre(null);
            if (s.equals(getString(R.string.green_price)))
                request.setSalePricePremiumUnpaid(null);
            if (s.equals(getString(R.string.developmentEndCredits)))
                request.setIsDevelopmentEndCredits(null);
            if (s.equals(getString(R.string.transferred)))
                request.setIsTransferred(null);
            if (s.equals(getString(R.string.o_property)))
                request.setIsOStatus(null);
        });


    }

    private void init() {
//        paramsManager = new PropertyRequestParamsManager();
//        paramsManager.getTagInfos().addAll(PropertyRequestParamsManager.newInstance().getTagInfos());
//        paramsManager.get
    }

    public void setData(TransListRequest description) {
        this.request = description;
    }

    public void initData(TransListRequest request) {

        TransRequestSaveParams paramsManager = TransRequestParamsManager.getParams();


        List<SystemParamItems> intervals = paramsManager.getIntervalList();
        if (intervals != null && !intervals.isEmpty()) {
            for (SystemParamItems item : intervals) {
                interval.setVisibility(View.VISIBLE);
                interval.addItem(item.getItemText());
            }
        }

        List<SystemParamItems> tags = paramsManager.getTagList();
        if (tags != null && !tags.isEmpty()) {
            for (SystemParamItems item : tags) {
                tag.setVisibility(View.VISIBLE);
                tag.addItem(item.getItemText());
            }
        }

        List<PropertyParamHints> hints = paramsManager.getAddress();

        if (hints != null && !hints.isEmpty()) {
            for (PropertyParamHints hint : hints) {
                address.setVisibility(View.VISIBLE);

                View view = LayoutInflater.from(getContext()).inflate(R.layout.item_label_detail, null, false);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                view.setLayoutParams(layoutParams);
                TextView dis = view.findViewById(R.id.item_label_area);
                TextView street = view.findViewById(R.id.item_label_street);
                View content = view.findViewById(R.id.label);

                MyGlobalLayoutListener listener = new MyGlobalLayoutListener(content, view);

                view.getViewTreeObserver().addOnGlobalLayoutListener(listener);

                if (hint.getDistrictName() != null)
                    dis.setText(hint.getDistrictName() + "/" + hint.getAreaName());
                else {
                    dis.setText(hint.getAreaName());
                }

                street.setText(hint.getEnAddressName());

                if (!TextUtil.isEmply(hint.getEnAddressName())) {
                    String enAdd = hint.getEnAddressName();
//                    L.d("EnAdd_Trans",enAdd);
                    street.setText(enAdd);
                    LinearLayout.LayoutParams txtParams = new LinearLayout.LayoutParams(DensityUtil.dip2px(getContext(),12)*enAdd.length(), ViewGroup.LayoutParams.WRAP_CONTENT);
                    street.setLayoutParams(txtParams);
                    L.d("StreetTxt_Width",DensityUtil.dip2px(getContext(),12)*enAdd.length()+"");
                    street.setEllipsize(TextUtils.TruncateAt.END);
                    address.addItem(view);

                }else {
//                    street.setVisibility(View.GONE);
//                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, DensityUtil.dip2px(getContext(), 40));
//                    dis.setLayoutParams(params);
                    address.addItem(dis.getText().toString());
                }
            }
        }

        List<DistrictItem> districtItems = paramsManager.getArea();
        if (districtItems != null && !districtItems.isEmpty()) {
            for (DistrictItem item : districtItems) {
                area.setVisibility(View.VISIBLE);
                area.addItem(item.getDistrictName());
            }
        }

        if (request.getSellPriceFrom() != null || request.getSellPriceTo() != null) {
            String str = "";
            if (request.getSellPriceFrom() == null || request.getSellPriceFrom().equals("") || request.getSellPriceFrom().equals("0")) {
                str = str + "不限";
            } else str = str + request.getSellPriceFrom() + "萬";
            str = str + " - ";
            if (request.getSellPriceTo() == null || request.getSellPriceTo().equals("")) {
                str = str + "不限";
            } else str = str + request.getSellPriceTo() + "萬";
            sale.setVisibility(View.VISIBLE);
            sale.addItem(str);
        }

        if (request.getRentPriceFrom() != null || request.getRentPriceTo() != null) {
            String str = "";
            if (request.getRentPriceFrom() == null || request.getRentPriceFrom().equals("") || request.getRentPriceFrom().equals("0")) {
                str = str + "不限";
            } else str = str + "$" + request.getRentPriceFrom() + ",000";
            str = str + " - ";
            if (request.getRentPriceTo() == null || request.getRentPriceTo().equals("")) {
                str = str + "不限";
            } else str = str + "$" + request.getRentPriceTo() + ",000";
            rent.setVisibility(View.VISIBLE);
            rent.addItem(str);
        }


//        if (description.getSquareFrom() != null || description.getSquareTo() != null) {
        if ((request.getSquareFrom() != null && !request.getSquareFrom().equals("")) || (request.getSquareTo() != null && !request.getSquareTo().equals(""))) {
            String str = "建築面積: ";
            if (request.getSquareFrom() == null) {
                str = str + "不限";
            } else str = str + request.getSquareFrom() + " 呎";
            str = str + " - ";
            if (request.getSquareTo() == null) {
                str = str + "不限";
            } else str = str + request.getSquareTo() + " 呎";
            acereage.setVisibility(View.VISIBLE);
            acereage.addItem(str);
        }

        //todo 清除的时候清楚五个参数
        if ((request.getSquareUseFrom() != null && !request.getSquareUseFrom().equals("")) || (request.getSquareUseTo() != null && !request.getSquareUseTo().equals(""))) {
            String str = "實用面積: ";
            if (request.getSquareUseFrom() == null) {
                str = str + "不限";
            } else str = str + request.getSquareUseFrom() + " 呎";
            str = str + " - ";
            if (request.getSquareUseTo() == null) {
                str = str + "不限";
            } else str = str + request.getSquareUseTo() + " 呎";
            acereage.setVisibility(View.VISIBLE);
            acereage.addItem(str);
        }

        if (request.getFloors() != null && !request.getFloors().equals("")) {
            floot.setVisibility(View.VISIBLE);
            floot.addItem(request.getFloors());
        }

        if (request.getUnits() != null && !request.getUnits().equals("")) {
            unit.setVisibility(View.VISIBLE);
            unit.addItem(request.getUnits());
        }

        if (Boolean.parseBoolean(request.getIsTransferred())) {
            option.setVisibility(View.VISIBLE);
            option.addItem(getString(R.string.transferred));
        }

        //------------------------------------------------
        if (Boolean.parseBoolean(request.getIsTransferred())) {
            option.setVisibility(View.VISIBLE);
            option.addItem(getString(R.string.transferred));
        }

        if (Boolean.parseBoolean(request.getIsConfirmed())) {
            option.setVisibility(View.VISIBLE);
            option.addItem(getString(R.string.confirmed));
        }

        if (Boolean.parseBoolean(request.getIsOStatus())) {
            option.setVisibility(View.VISIBLE);
            option.addItem(getString(R.string.o_property));
        }

        if (Boolean.parseBoolean(request.getIsCorporationTransferre())) {
            option.setVisibility(View.VISIBLE);
            option.addItem(getString(R.string.corporationTransferre));
        }

        if (Boolean.parseBoolean(request.getIsDevelopmentEndCredits())) {
            option.setVisibility(View.VISIBLE);
            option.addItem(getString(R.string.developmentEndCredits));
        }

        if (Boolean.parseBoolean(request.getIsSalePricePremiumUnpaid())) {
            option.setVisibility(View.VISIBLE);
            option.addItem(getString(R.string.green_price));
        }
//
//        if (description.isOnlyTrust()) {
//            option.setVisibility(View.VISIBLE);
//            option.addItem(getString(R.string.exclusive));
//        }
//
//        if (description.isHasOptout()) {
//            option.setVisibility(View.VISIBLE);
//            option.addItem(getString(R.string.o_property));
//        }
//
//        if (description.isHasDevelopmentEndCredits()) {
//            option.setVisibility(View.VISIBLE);
//            option.addItem(getString(R.string.pending));
//        }
//
//        if (description.isProxy()) {
//            option.setVisibility(View.VISIBLE);
//            option.addItem(getString(R.string.power_of_attorney));
//        }

        if (request.getContactName() != null && !request.getContactName().equals("")) {
            other.setVisibility(View.VISIBLE);

            String name = "";
            switch (Integer.parseInt(request.getContactSearchType())) {
                case 1:
                    name = getString(R.string.sale_buy_people);
                    break;
                case 2:
                    name = getString(R.string.buy_people);
                    break;
                case 3:
                    name = getString(R.string.sale_people);
                    break;
            }

            other.addItem(name + ":" + request.getContactName());
        }

        if (request.getContactValue() != null && !request.getContactValue().equals("")) {
            other.setVisibility(View.VISIBLE);
            other.addItem(getString(R.string.phone) + ":" + request.getContactValue());
        }

        if (!TextUtil.isEmply(request.getSortField())) {
            L.d("chengjiaopaixu：", request.getSortField());
            String tip = "";
            switch (request.getSortField()) {
                case "DisplayTransDate":
                    tip = "成交日期: ";
                    break;
                case "Price":
                    tip = "成交價: ";
                    break;
                case "UseSquareFoot":
                    tip = "實用面積: ";
                    break;
                case "BuildSquareFoot":
                    tip = "建築面積: ";
                    break;
            }

            if(tip.equals("成交日期: ")) {
                if(request.isAscending()){
                    tip = tip + "由遠至近";
                }else
                    tip = tip + "由近至遠";
            }else if (request.isAscending()) {
                tip = tip + "由高至低";
            } else tip = tip + "由低至高";
            sort.addItem(tip);

        } else sort.addItem("預設排序:由近至遠");


        if (request.getTransactionDateFrom() != null || request.getTransactionDateTo() != null) {
            String itemStr = getString(R.string.record_transaction_bargain) + ":";
            String dateStr = request.getTransactionDateFrom();
            if (dateStr != null && !dateStr.equals("")) {
                itemStr = itemStr + dateStr;
            } else itemStr = getString(R.string.dialog_price_unlimit);

            itemStr = itemStr + " 至 ";
            dateStr = request.getTransactionDateTo();
            if (dateStr != null && !dateStr.equals("")) {
                itemStr = itemStr + dateStr;
            } else itemStr = itemStr + getString(R.string.dialog_price_unlimit);
            date.addItem(itemStr);
            date.setVisibility(View.VISIBLE);
        }

        if (request.getPrelimDateFrom() != null || request.getPrelimDateTo() != null) {
            String itemSre = getString(R.string.record_transaction_appointment) + ":";
            itemSre = itemSre + isStrNull(request.getPrelimDateFrom()) + " 至 " + isStrNull(request.getPrelimDateTo());
            date.addItem(itemSre);
            date.setVisibility(View.VISIBLE);
        }
        if (request.getFormalDateFrom() != null || request.getFormalDateTo() != null) {
            String itemSre = getString(R.string.record_transaction_official) + ":";
            itemSre = itemSre + isStrNull(request.getFormalDateFrom()) + " 至 " + isStrNull(request.getFormalDateTo());
            date.addItem(itemSre);
            date.setVisibility(View.VISIBLE);
        }
        if (request.getCompleteDateFrom() != null || request.getCompleteDateTo() != null) {
            String itemSre = getString(R.string.record_transaction_finish) + ":";
            itemSre = itemSre + isStrNull(request.getCompleteDateFrom()) + " 至 " + isStrNull(request.getCompleteDateTo());
            date.addItem(itemSre);
            date.setVisibility(View.VISIBLE);
        }
        if (request.getRentDateFrom() != null || request.getRentDateTo() != null) {
            String itemSre = getString(R.string.record_transaction_rentdate) + ":";
            itemSre = itemSre + isStrNull(request.getRentDateFrom()) + " 至 " + isStrNull(request.getRentDateTo());
            date.addItem(itemSre);
            date.setVisibility(View.VISIBLE);
        }

        if (!TextUtil.isEmply(request.getTrusactionDate())) {
            transDate.setVisibility(View.VISIBLE);
            transDate.addItem("近" + Integer.parseInt(request.getTrusactionDate()) + "日");
        }

        if (!TextUtil.isEmply(request.getTransactionTypes())) {
            String[] type = request.getTransactionTypes().replace(" ", "").split(",");
            List<String> types = new ArrayList<>();
            for (int i = 0; i < type.length; i++) {
                switch (Integer.parseInt(type[i])) {
                    case 1:
                        types.add(getString(R.string.centa_sale));
                        break;
                    case 2:
                        types.add(getString(R.string.centa_rent));
                        break;
                    case 3:
                        types.add(getString(R.string.other_sale));
                        break;
                    case 4:
                        types.add(getString(R.string.other_rent));
                        break;
                    case 5:
                        types.add(getString(R.string.internal));
                        break;
                }
            }
            trantype.addItem(types);
            trantype.setVisibility(View.VISIBLE);
        }

//        L.d("onOtherChange", description.getPropertyDateFrom() + "   " + description.getPropertyDateFrom());
//        if ((description.getPropertyDateFrom() != null && !description.getPropertyDateFrom().equals("")) || (description.getPropertyDateTo() != null && !description.getPropertyDateTo().equals(""))) {
//
//            String str = "";
//            switch (Integer.parseInt(description.getPropertyDateType())) {
//                case statusChangedDate:
//                    str = "改盤日期:";
//                    break;
//                case lasetUpdate:
//                    str = "最后修改日期:";
//                    break;
//                case lastFollowDate:
//                    str = "最后修改日期:";
//                    break;
//                case registDate:
//                    str = "開盤日期:";
//                    break;
//                case estimatedDate:
//                    str = "估計日期:";
//                    break;
//                case changePriceDate:
//                    str = "最后修改日期:";
//                    break;
//                case onlyTrustStartDate:
//                    str = "委託書開始日:";
//                    break;
//                case onlyTrustEndDate:
//                    str = "委託書到期日:";
//                    break;
//            }
//
//            if (request.getPropertyDateFrom() == null && !request.getPropertyDateFrom().equals("")) {
//                str = str + "不限";
//            } else str = str + request.getPropertyDateFrom();
//
//            str = str + "至";
//
//            if (request.getPropertyDateTo() == null && request.getPropertyDateTo().equals("")) {
//                str = str + "不限";
//            } else str = str + description.getPropertyDateTo();
//            L.d("onOtherChange", str);
//            other.setVisibility(View.VISIBLE);
//            other.addItem(str);
//
//            if (Integer.parseInt(description.getPropertyDateType()) == statusChangedDate && (description.getIncludedPropertyStatusFrom() != null || description.getIncludedPropertyStatusTo() != null)) {
//                String s = "";
//                if (description.getIncludedPropertyStatusFrom() != null) {
//                    for (SystemParamItems items : paramsManager.getStatuFrom()) {
//                        s = s + items.getItemText().substring(0, items.getItemText().indexOf("(")) + ",";
//                    }
//                    s = s.substring(0, s.length() - 1);
//                } else s = "不限";
//                s = s + "->";
//                if (description.getIncludedPropertyStatusTo() != null) {
//                    for (SystemParamItems items : paramsManager.getStatuTo()) {
//                        s = s + items.getItemText().substring(0, items.getItemText().indexOf("(")) + ",";
//                    }
//                    s = s.substring(0, s.length() - 1);
//                } else s = s + "不限";
//                other.addItem(s);
//            }
//        }

//        if (description.getCompleteYearFrom() != null)
//        if ((description.getCompleteYearFrom() != null && !description.getCompleteYearFrom().equals("")) || (description.getCompleteYearTo() != null && !description.getCompleteYearTo().equals(""))) {
//            String str = getString(R.string.finish_year) + ":";
//            if (description.getCompleteYearFrom() == null || description.getCompleteYearFrom().equals("")) {
//                str = str + "不限";
//            } else str = str + description.getCompleteYearFrom();
//            str = str + "-";
//            if (description.getCompleteYearTo() == null || description.getCompleteYearTo().equals("")) {
//                str = str + "不限";
//            } else str = str + description.getCompleteYearTo();
//            other.setVisibility(View.VISIBLE);
//            other.addItem(str);
//        }

//        if ((description.getPriceUnitFrom() != null && !description.getPriceUnitFrom().equals("")) || (description.getPriceUnitTo() != null && !description.getPriceUnitTo().equals(""))) {
//            String str = "";
//            L.d("SizeParam_dialog", description.getPriceUnitType());
//            switch (Integer.parseInt(description.getPriceUnitType())) {
//                case AVG_USE_SALE:
//                    str = getString(R.string.use_aver_sale) + ":";
//                    break;
//                case AVG_USE_RENT:
//                    str = getString(R.string.use_aver_rent) + ":";
//                    break;
//                case AVG_REALLY_SALE:
//                    str = getString(R.string.really_aver_sale) + ":";
//                    break;
//                case AVG_REALLY_RENT:
//                    str = getString(R.string.really_aver_rent) + ":";
//                    break;
//                case AVG_GREEN_USE_SALE:
//                    str = getString(R.string.really_aver_sale_green) + ":";
//                    break;
//                case AVG_GREEN_REALLY_SALE:
//                    str = getString(R.string.use_aver_sale_green) + ":";
//                    break;
//            }
//            if (request.getPriceUnitFrom() == null || request.getPriceUnitFrom().equals("")) {
//                str = str + "不限";
//            } else str = str + request.getPriceUnitFrom();
//            str = str + "-";
//            if (request.getPriceUnitTo() == null || request.getPriceUnitTo().equals("")) {
//                str = str + "不限";
//            } else str = str + request.getPriceUnitTo();
//            size.setVisibility(View.VISIBLE);
//            size.addItem(str);
//        }
    }

    private String isStrNull(String date) {

        if (date != null && !date.equals("")) {
            return date;
        } else return getString(R.string.dialog_price_unlimit);
    }

    private SmartLinBreakView area, address, floot, unit, trantype, sale, rent, interval, tag, option, acereage, size, other, date, transDate, sort;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return dialog;
    }

    private void initView() {
        dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_trans_userdesign);

        area = dialog.findViewById(R.id.userdesign_lb_area);
        sort = dialog.findViewById(R.id.userdesign_lb_sort);
        sort.enablieToClick(false);

        address = dialog.findViewById(R.id.userdesign_lb_address);
        address.setLayoutId(R.layout.item_dialog_option_two);

        floot = dialog.findViewById(R.id.userdesign_lb_floot);
        unit = dialog.findViewById(R.id.userdesign_lb_unit);
        trantype = dialog.findViewById(R.id.userdesign_lb_transtype);
        sale = dialog.findViewById(R.id.userdesign_lb_sale);
        rent = dialog.findViewById(R.id.userdesign_lb_rent);
        interval = dialog.findViewById(R.id.userdesign_lb_interval);
        tag = dialog.findViewById(R.id.userdesign_lb_tag);
        option = dialog.findViewById(R.id.userdesign_lb_typesearch);
        acereage = dialog.findViewById(R.id.userdesign_lb_acreage);
        size = dialog.findViewById(R.id.userdesign_lb_size_price);
        transDate = dialog.findViewById(R.id.userdesign_lb_transdate);
        other = dialog.findViewById(R.id.userdesign_lb_other);
        date = dialog.findViewById(R.id.userdesign_lb_date);
        add = dialog.findViewById(R.id.add);

//        other.setVisibility(View.VISIBLE);
//        other.addItem("ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");
//        other.addItem("sssssssssssssssssssssssssssssssss");
//        other.addItem("ssssssssssssssssssssssss");
//        other.addItem("sssssssssssssssss");
//        other.addItem("sssssssssssssssssssssssssssssssssssssssssssss");
//        other.addItem("ssssssssssssssssssssssssssssssssssssssssssssssssssssssssss");

        yesTxt = dialog.findViewById(R.id.yes);
        close = dialog.findViewById(R.id.close);

        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.AnimBottom);
        lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
//        lp.height = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        lp.height = getActivity().getWindowManager().getDefaultDisplay().getHeight() * 8 / 10;
        window.setAttributes(lp);
        window.setBackgroundDrawableResource(android.R.color.transparent);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yes:
                if (onItemClickLisenter != null)
                    onItemClickLisenter.onClick(dialog, v, request);
                dismiss();
                break;
            case R.id.close:
                dismiss();
                break;
            case R.id.add:

                UserDesignNameDialog userDesignNameDialog = new UserDesignNameDialog();
                userDesignNameDialog.setOnItemclickListener((dialog1, s, type) -> {
                    dialog1.dismiss();
                    if (type == UserDesignNameDialog.DIALOG_YES) {
                        saveOption(s);
                    }
                });
                userDesignNameDialog.show(getFragmentManager(), "");
                break;
        }
    }

    private String parseData(PropertyParamHints s) {
        String labelString = null;

        String address = "";
        String street = "";
        String area = s.getAreaName();

        if (area.length() > 18) {
            area = area.substring(0, 17) + "...";
        }

        if (!TextUtil.isEmply(s.getDistrictName()) && !TextUtil.isEmply(s.getAreaName())) {
            address = address + s.getDistrictName() + "/" + area;
            L.d("parseData", address);
            if (address.length() > 24) {
                address = address.substring(0, 20) + "...";
            }
        } else if (!TextUtil.isEmply(s.getDistrictName())) {
            address = address + s.getDistrictName();
        } else if (!TextUtil.isEmply(s.getAreaName())) {
            address = address + area;
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

    class MyGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {

        private View view;
        private View content;

        public MyGlobalLayoutListener(View view, View content) {
            this.view = view;
            this.content = content;
        }

        @Override
        public void onGlobalLayout() {

            L.d("Item_TransOption_Width",""+content.getWidth());

            if (view.getWidth() + DensityUtil.dip2px(getContext(), 30) > content.getWidth()) {

                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(content.getWidth() - DensityUtil.dip2px(getContext(), 30), ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
                view.setLayoutParams(layoutParams);
            }
            view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }
    }


    private void saveOption(String name) {
        new Thread(() -> {
            String path = MyApplication.getContext().getFilesDir().getAbsolutePath() + "tranUserDesign.txt";
            String gson = null;
            File file = new File(path);
            if (file.exists()) {
                List<TransSearchHistory> searchHistories = new ArrayList<>();
                searchHistories = FileUtil.getData(searchHistories.getClass(), file);

                if (searchHistories.size() >= 5) {
                    searchHistories.remove(0);
                }

                TransSearchHistory history = new TransSearchHistory(request, paramsManager);
                history.setOptionMame(name);
                searchHistories.add(history);
                gson = new Gson().toJson(searchHistories);
                L.d("SearchHis_Exists", "HisSize: " + searchHistories.size() + " HisStr" + searchHistories.toString());
            } else {
                List<TransSearchHistory> searchHistories = new ArrayList<>();
                TransSearchHistory history = new TransSearchHistory(request, paramsManager);
                searchHistories.add(history);
                history.setOptionMame(name);
                gson = new Gson().toJson(searchHistories);
                L.d("SearchHis_Single", new Gson().toJson(history));
            }
            FileUtil.saveFile(gson, file);
            if (getActivity() != null) showSaveFinishDialog();
        }).start();
    }


    private void showSaveFinishDialog() {
        getActivity().runOnUiThread(() -> {
            dialog.dismiss();
            DialogUtil.getSimpleDialog(getString(R.string.success_to_save)).show(getFragmentManager(), "");
        });
    }

    public void setOnItemClickLisenter(OnItemClickLisenter onItemClickLisenter) {
        this.onItemClickLisenter = onItemClickLisenter;
    }

    public OnItemClickLisenter getOnItemClickLisenter() {
        return onItemClickLisenter;
    }

    public interface OnItemClickLisenter {
        void onClick(Dialog dialog, View v, TransListRequest request);
    }
}
