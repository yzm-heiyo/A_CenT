package com.centanet.hk.aplus.Views.Dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.centanet.hk.aplus.MyApplication;
import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.FileUtil;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.net.GsonUtil;
import com.centanet.hk.aplus.Widgets.LineBreakLayout;
import com.centanet.hk.aplus.Widgets.SmartLinBreakView;
import com.centanet.hk.aplus.bean.auto_estate.PropertyParamHints;
import com.centanet.hk.aplus.bean.build_tag.TagInfo;
import com.centanet.hk.aplus.bean.district.DistrictItem;
import com.centanet.hk.aplus.bean.http.HouseDescription;
import com.centanet.hk.aplus.bean.params.SystemParamItems;
import com.centanet.hk.aplus.bean.save_option.PropertyRequestSaveParams;
import com.centanet.hk.aplus.bean.userdesign_option.PropertySearchHistory;
import com.centanet.hk.aplus.common.APSystemParameterType;
import com.centanet.hk.aplus.manager.AppSystemParamsManager;
import com.centanet.hk.aplus.manager.PropertyRequestParamsManager;
import com.google.gson.Gson;

import org.litepal.tablemanager.Connector;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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


public class SearchOptionDialog extends DialogFragment implements View.OnClickListener {

    private Dialog dialog;
    private WindowManager.LayoutParams lp;

    private TextView yesTxt;

    private ImageView close;
    private TextView add;

    private OnItemClickLisenter onItemClickLisenter;
    private String thiz = getClass().getSimpleName();

    private HouseDescription description = new HouseDescription();
    private PropertyRequestSaveParams paramsManager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        init();
        initLisenter();
        initData(description);
    }

    private void initLisenter() {
        yesTxt.setOnClickListener(this);
        close.setOnClickListener(this);
        add.setOnClickListener(this);

        paramsManager = PropertyRequestParamsManager.getParams();

        statu.setOnItemChangeLisenter((view, contentView, position) -> {
            description.getPropertyStatus().remove(paramsManager.getStatulist().get(position).getItemValue());
            paramsManager.getStatulist().remove(position);
        });

        direction.setOnItemChangeLisenter((view, contentView, position) -> {
            description.getHouseDirection().remove(paramsManager.getDirectionList().get(position).getItemValue());
            paramsManager.getDirectionList().remove(position);
        });

        tag.setOnItemChangeLisenter((view, contentView, position) -> {
            description.getPropertyboolTag().remove(paramsManager.getTagList().get(position).getItemValue());
            paramsManager.getTagList().remove(position);
        });

        facility.setOnItemChangeLisenter((view, contentView, position) -> {
            description.getPropertyboolTag().remove(paramsManager.getTagInfos().get(position).getTagKeyId());
            paramsManager.getTagInfos().remove(position);
        });

        interval.setOnItemChangeLisenter((view, contentView, position) -> {
            description.getPropertyTypes().remove(paramsManager.getIntervalList().get(position).getItemValue());
            paramsManager.getIntervalList().remove(position);
        });

        floot.setOnItemChangeLisenter((v, c, p) -> {
            description.setFloors(null);
        });
        unit.setOnItemChangeLisenter((v, c, p) -> {
            description.setUnits(null);
        });
        area.setOnItemChangeLisenter((v, c, p) -> {
            description.setSquareUseTo(null);
            description.setSquareUseFrom(null);
            description.setSquareTo(null);
            description.setSquareFrom(null);
            description.setPropertySquareType(null);
        });

        sale.setOnItemChangeLisenter((v, c, p) -> {
            description.setSalePriceFrom(null);
            description.setSalePriceTo(null);
        });

        ssd.setOnItemChangeLisenter((v, c, p) -> {
            description.setSSDType(null);
        });

        size.setOnItemChangeLisenter((v, c, p) -> {
            description.setPriceUnitType(null);
            description.setPriceUnitFrom(null);
            description.setPriceUnitTo(null);
        });

        other.setOnItemChangeLisenter((view, contentView, position) -> {
            String s = ((TextView) view).getText().toString();
            L.d("other", s);
            if (s.indexOf(getString(R.string.owner)) != -1) {
                description.setTrustorName(null);
            }

            if (s.indexOf(getString(R.string.finish_year)) != -1) {
                description.setCompleteYearTo(null);
                description.setCompleteYearFrom(null);
            }

            if (s.indexOf(getString(R.string.phone)) != -1) {
                description.setMobile(null);
            }

            if (s.indexOf("日期") != -1) {
                description.setPropertyDateType(null);
                description.setPropertyDateFrom(null);
                description.setPropertyDateTo(null);
                description.setIncludedPropertyStatusTo(null);
                description.setIncludedPropertyStatusFrom(null);
                paramsManager.getStatuTo().clear();
                paramsManager.getStatuFrom().clear();
//                paramsManager.getStatuTo() = null;
            }

            if (s.indexOf("->") != -1) {
                description.setIncludedPropertyStatusTo(null);
                description.setIncludedPropertyStatusFrom(null);
                paramsManager.getStatuTo().clear();
                paramsManager.getStatuFrom().clear();
            }
        });

        rent.setOnItemChangeLisenter((view, contentView, position) -> {
            description.setRentPriceFrom(null);
            description.setRentPriceTo(null);
        });


        option.setOnItemChangeLisenter((view, contentView, position) -> {
            String s = ((TextView) view).getText().toString();
            if (s.equals(getString(R.string.car_place)))
                description.setHasParkingNumber(null);
            if (s.equals(getString(R.string.connect_green_price)))
                description.setHasSalePricePremiumUnpaid(null);
            if (s.equals(getString(R.string.green_price)))
                description.setShowSalePricePremiumUnpaid(null);
            if (s.equals(getString(R.string.exclusive)))
                description.setOnlyTrust(null);
            if (s.equals(getString(R.string.hotlist)))
                description.setHotlist(null);
            if (s.equals(getString(R.string.key)))
                description.setHasPropertyKey(null);
            if (s.equals(getString(R.string.no_ssd)))
                description.setNoneSSD(null);
            if (s.equals(getString(R.string.o_property)))
                description.setHasOptout(null);
            if (s.equals(getString(R.string.pending)))
                description.setHasDevelopmentEndCredits(null);
            if (s.equals(getString(R.string.unconfirm_tran)))
                description.setHasConfirmTransaction(null);
            if (s.equals(getString(R.string.power_of_attorney)))
                description.setProxy(null);
        });
    }

    private void init() {
//        paramsManager = new PropertyRequestParamsManager();
//        paramsManager.getTagInfos().addAll(PropertyRequestParamsManager.newInstance().getTagInfos());
//        paramsManager.get
    }

    public void setData(HouseDescription description) {
        this.description = description;
    }

    public void initData(HouseDescription description) {

        PropertyRequestSaveParams paramsManager = PropertyRequestParamsManager.getParams();

        if (description.getSSDType() != null && !description.getSSDType().equals("")) {
            ssd.setVisibility(View.VISIBLE);
            ssd.addItem(description.getSSDType() + "%");
        }

        List<SystemParamItems> status = paramsManager.getStatulist();
        L.d("ParamValue", "Statu: " + status.toString());
        if (status != null && !status.isEmpty()) {
            for (SystemParamItems item : status) {
                statu.setVisibility(View.VISIBLE);
                statu.addItem(item.getItemText());
            }
        }
        List<SystemParamItems> directions = paramsManager.getDirectionList();
        L.d("ParamValue", "Directions: " + status.toString());
        if (directions != null && !directions.isEmpty()) {
            for (SystemParamItems item : directions) {
                direction.setVisibility(View.VISIBLE);
                direction.addItem(item.getItemText());
            }
        }
        List<SystemParamItems> intervals = paramsManager.getIntervalList();
        L.d("ParamValue", "Intervals: " + status.toString());
        if (intervals != null && !intervals.isEmpty()) {
            for (SystemParamItems item : intervals) {
                interval.setVisibility(View.VISIBLE);
                interval.addItem(item.getItemText());
            }
        }

        List<SystemParamItems> tags = paramsManager.getTagList();
        L.d("ParamValue", "Tags: " + status.toString());
        if (tags != null && !tags.isEmpty()) {
            for (SystemParamItems item : tags) {
                tag.setVisibility(View.VISIBLE);
                tag.addItem(item.getItemText());
            }
        }

        List<PropertyParamHints> hints = paramsManager.getAddress();
        L.d("ParamValue", "Hints: " + status.toString());
        if (hints != null && !hints.isEmpty()) {
            for (PropertyParamHints hint : hints) {
                address.setVisibility(View.VISIBLE);
                address.addItem(hint.getAddressName());
            }
        }

        List<DistrictItem> districtItems = paramsManager.getArea();
        L.d("ParamValue", "DistrictItems: " + status.toString());
        if (districtItems != null && !districtItems.isEmpty()) {
            for (DistrictItem item : districtItems) {
                area.setVisibility(View.VISIBLE);
                area.addItem(item.getDistrictName());
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
            sale.setVisibility(View.VISIBLE);
            sale.addItem(str);
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
            rent.setVisibility(View.VISIBLE);
            rent.addItem(str);
        }

        if (description.getKeywords() != null && !description.getKeywords().equals("")) {
            keyword.addItem(description.getKeywords());
            keyword.setVisibility(View.VISIBLE);
        }

        List<TagInfo> tagInfos = PropertyRequestParamsManager.getParams().getTagInfos();
        if (tagInfos != null && !tagInfos.isEmpty()) {
            facility.setVisibility(View.VISIBLE);
            for (TagInfo tagInfo : tagInfos)
                facility.addItem(tagInfo.getTagChineseName());
        }

//        if (description.getSquareFrom() != null || description.getSquareTo() != null) {
        if ((description.getSquareFrom() != null && !description.getSquareFrom().equals("")) || (description.getSquareTo() != null && !description.getSquareTo().equals(""))) {
            String str = "建築面積:";
            if (description.getSquareFrom() == null) {
                str = str + "不限";
            } else str = str + description.getSquareFrom() + "呎";
            str = str + "-";
            if (description.getSquareTo() == null) {
                str = str + "不限";
            } else str = str + description.getSquareTo() + "呎";
            acereage.setVisibility(View.VISIBLE);
            acereage.addItem(str);
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
            acereage.setVisibility(View.VISIBLE);
            acereage.addItem(str);
        }

        if (description.getFloors() != null && !description.getFloors().equals("")) {
            floot.setVisibility(View.VISIBLE);
            floot.addItem(description.getFloors());
        }

        if (description.getUnits() != null && !description.getUnits().equals("")) {
            unit.setVisibility(View.VISIBLE);
            unit.addItem(description.getUnits());
        }

        if (description.isHasParkingNumber()) {
            option.setVisibility(View.VISIBLE);
            option.addItem(getString(R.string.car_place));
        }

        if (description.isShowSalePricePremiumUnpaid()) {
            option.setVisibility(View.VISIBLE);
            option.addItem(getString(R.string.green_price));
        }

        if (description.getHasSalePricePremiumUnpaid()) {
            option.setVisibility(View.VISIBLE);
            option.addItem(getString(R.string.connect_green_price));
        }

        if (description.getHasPropertyKey()) {
            option.setVisibility(View.VISIBLE);
            option.addItem(getString(R.string.key));
        }

        if (description.isHasConfirmTransaction()) {
            option.setVisibility(View.VISIBLE);
            option.addItem(getString(R.string.unconfirm_tran));
        }

        if (description.isHotlist()) {
            option.setVisibility(View.VISIBLE);
            option.addItem(getString(R.string.hotlist));
        }

        if (description.isNoneSSD()) {
            option.setVisibility(View.VISIBLE);
            option.addItem(getString(R.string.no_ssd));
        }

        if (description.isOnlyTrust()) {
            option.setVisibility(View.VISIBLE);
            option.addItem(getString(R.string.exclusive));
        }

        if (description.isHasOptout()) {
            option.setVisibility(View.VISIBLE);
            option.addItem(getString(R.string.o_property));
        }

        if (description.isHasDevelopmentEndCredits()) {
            option.setVisibility(View.VISIBLE);
            option.addItem(getString(R.string.pending));
        }

        if (description.isProxy()) {
            option.setVisibility(View.VISIBLE);
            option.addItem(getString(R.string.power_of_attorney));
        }

        if (description.getTrustorName() != null && !description.getTrustorName().equals("")) {
            other.setVisibility(View.VISIBLE);
            other.addItem(getString(R.string.owner) + ":" + description.getTrustorName());
        }

        if (description.getMobile() != null && !description.getMobile().equals("")) {
            other.setVisibility(View.VISIBLE);
            other.addItem(getString(R.string.phone) + ":" + description.getMobile());
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
            other.setVisibility(View.VISIBLE);
            other.addItem(str);

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
                other.addItem(s);
            }
        }

//        if (description.getCompleteYearFrom() != null)
        if ((description.getCompleteYearFrom() != null && !description.getCompleteYearFrom().equals("")) || (description.getCompleteYearTo() != null && !description.getCompleteYearTo().equals(""))) {
            String str = getString(R.string.finish_year) + ":";
            if (description.getCompleteYearFrom() == null || description.getCompleteYearFrom().equals("")) {
                str = str + "不限";
            } else str = str + description.getCompleteYearFrom();
            str = str + "-";
            if (description.getCompleteYearTo() == null || description.getCompleteYearTo().equals("")) {
                str = str + "不限";
            } else str = str + description.getCompleteYearTo();
            other.setVisibility(View.VISIBLE);
            other.addItem(str);
        }

        if ((description.getPriceUnitFrom() != null && !description.getPriceUnitFrom().equals("")) || (description.getPriceUnitTo() != null && !description.getPriceUnitTo().equals(""))) {
            String str = "";
            L.d("SizeParam_dialog", description.getPriceUnitType());
            switch (Integer.parseInt(description.getPriceUnitType())) {
                case AVG_USE_SALE:
                    str = getString(R.string.use_aver_sale) + ":";
                    break;
                case AVG_USE_RENT:
                    str = getString(R.string.use_aver_rent) + ":";
                    break;
                case AVG_REALLY_SALE:
                    str = getString(R.string.really_aver_sale) + ":";
                    break;
                case AVG_REALLY_RENT:
                    str = getString(R.string.really_aver_rent) + ":";
                    break;
                case AVG_GREEN_USE_SALE:
                    str = getString(R.string.really_aver_sale_green) + ":";
                    break;
                case AVG_GREEN_REALLY_SALE:
                    str = getString(R.string.use_aver_sale_green) + ":";
                    break;
            }
            if (description.getPriceUnitFrom() == null || description.getPriceUnitFrom().equals("")) {
                str = str + "不限";
            } else str = str + description.getPriceUnitFrom();
            str = str + "-";
            if (description.getPriceUnitTo() == null || description.getPriceUnitTo().equals("")) {
                str = str + "不限";
            } else str = str + description.getPriceUnitTo();
            size.setVisibility(View.VISIBLE);
            size.addItem(str);
        }
    }

    private SmartLinBreakView area, address, floot, unit, keyword, statu, sale, rent, ssd, direction, interval, tag, option, acereage, size, facility, other;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return dialog;
    }

    private void initView() {
        dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_userdesign);

        area = dialog.findViewById(R.id.userdesign_lb_area);
        address = dialog.findViewById(R.id.userdesign_lb_address);
        floot = dialog.findViewById(R.id.userdesign_lb_floot);
        unit = dialog.findViewById(R.id.userdesign_lb_unit);
        keyword = dialog.findViewById(R.id.userdesign_lb_keywork);
        statu = dialog.findViewById(R.id.userdesign_lb_statu);
        sale = dialog.findViewById(R.id.userdesign_lb_sale);
        rent = dialog.findViewById(R.id.userdesign_lb_rent);
        ssd = dialog.findViewById(R.id.userdesign_lb_ssd);
        direction = dialog.findViewById(R.id.userdesign_lb_direction);
        interval = dialog.findViewById(R.id.userdesign_lb_interval);
        tag = dialog.findViewById(R.id.userdesign_lb_tag);
        option = dialog.findViewById(R.id.userdesign_lb_typesearch);
        acereage = dialog.findViewById(R.id.userdesign_lb_acreage);
        size = dialog.findViewById(R.id.userdesign_lb_size_price);
        facility = dialog.findViewById(R.id.userdesign_lb_facility);
        other = dialog.findViewById(R.id.userdesign_lb_other);
        add = dialog.findViewById(R.id.add);

        yesTxt = dialog.findViewById(R.id.yes);
        close = dialog.findViewById(R.id.close);

        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.AnimBottom);
        lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        lp.height = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        window.setAttributes(lp);
        window.setBackgroundDrawableResource(android.R.color.transparent);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yes:
                if (onItemClickLisenter != null)
                    onItemClickLisenter.onClick(dialog, v, description);
                dismiss();
                break;
            case R.id.close:
                dismiss();
                break;
            case R.id.add:

                UserDesignNameDialog userDesignNameDialog = new UserDesignNameDialog();
                userDesignNameDialog.setOnItemclickListener((dialog1, s, type) -> {
                    dialog1.dismiss();
                    if(type == UserDesignNameDialog.DIALOG_YES){
                        saveOption(s);
                    }
                });
                userDesignNameDialog.show(getFragmentManager(),"");
                break;
        }
    }

    private void saveOption(String name){
        new Thread(() -> {
            String path = MyApplication.getContext().getFilesDir().getAbsolutePath() + "userDesign.txt";
            String gson = null;
            File file = new File(path);
            if (file.exists()) {
                List<PropertySearchHistory> searchHistories = new ArrayList<>();
                searchHistories = FileUtil.getData(searchHistories.getClass(), file);

                if (searchHistories.size() >= 5) {
                    searchHistories.remove(0);
                }

                PropertySearchHistory history = new PropertySearchHistory(description, paramsManager);
                history.setOptionMame(name);
                searchHistories.add(history);
                gson = new Gson().toJson(searchHistories);
                L.d("SearchHis_Exists", "HisSize: " + searchHistories.size() + " HisStr" + searchHistories.toString());
            } else {
                List<PropertySearchHistory> searchHistories = new ArrayList<>();
                PropertySearchHistory history = new PropertySearchHistory(description, paramsManager);
                searchHistories.add(history);
                history.setOptionMame(name);
                gson = new Gson().toJson(searchHistories);
                L.d("SearchHis_Single", new Gson().toJson(history));
            }
            FileUtil.saveFile(gson, file);
        }).start();
    }

    public void setOnItemClickLisenter(OnItemClickLisenter onItemClickLisenter) {
        this.onItemClickLisenter = onItemClickLisenter;
    }

    public OnItemClickLisenter getOnItemClickLisenter() {
        return onItemClickLisenter;
    }

    public interface OnItemClickLisenter {
        void onClick(Dialog dialog, View v, HouseDescription description);
    }
}
