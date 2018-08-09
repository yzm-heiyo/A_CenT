package com.centanet.hk.aplus.Views.HouseDetailView.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.widget.NestedScrollView;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.centanet.hk.aplus.MyApplication;
import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.TextUtil;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.Views.Dialog.LoadingDialog;
import com.centanet.hk.aplus.Views.FollowAddView.view.FollowAddActivity;
import com.centanet.hk.aplus.Views.FollowView.view.FollowActivity;
import com.centanet.hk.aplus.Views.HouseDetailView.present.IDetailPresent;
import com.centanet.hk.aplus.Views.HouseDetailView.view.fragment.DataInfoFragment;
import com.centanet.hk.aplus.Views.HouseDetailView.view.fragment.FollowRecordFragment;
import com.centanet.hk.aplus.Views.HouseDetailView.view.fragment.PriceRecordFragment;
import com.centanet.hk.aplus.Views.HouseDetailView.view.fragment.TransRecordFragment;
import com.centanet.hk.aplus.Views.HouseDetailView.view.fragment.TrustorFragment;
import com.centanet.hk.aplus.Views.TransactView.TransactActivity;
import com.centanet.hk.aplus.Widgets.HorizontalView;
import com.centanet.hk.aplus.Widgets.LineBreakLayout;
import com.centanet.hk.aplus.Widgets.PropertyDetail.PropertyDataInformationView;
import com.centanet.hk.aplus.Widgets.PropertyDetail.PropertyFollowView;
import com.centanet.hk.aplus.Widgets.PropertyDetail.PropertyPriceRecordView;
import com.centanet.hk.aplus.Widgets.PropertyDetail.PropertyTransactionRecordView;
import com.centanet.hk.aplus.Widgets.PropertyDetail.PropertyTrustorInfoView;
import com.centanet.hk.aplus.Widgets.SmallItemView;
import com.centanet.hk.aplus.bean.detail.DetailBriefInfo;
import com.centanet.hk.aplus.bean.detail.DetailHouse;
import com.centanet.hk.aplus.bean.http.DetailListsDescription;
import com.centanet.hk.aplus.eventbus.MessageEventBus;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.centanet.hk.aplus.Widgets.PropertyDetail.PropertyPriceRecordView.PROPERTY_PRICE_RENT;
import static com.centanet.hk.aplus.Widgets.PropertyDetail.PropertyPriceRecordView.PROPERTY_PRICE_SALE;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.DetailRefreshView.DETAIL_DETAILDATA;


/**
 * Created by mzh1608258 on 2018/1/3.
 */

public class BasicInfoFragment extends Fragment implements IPresenterManager<IDetailPresent>, IDataManager<DetailBriefInfo> {

    private static final String BASIC_INFO = "basicInfo";
    private static String FRAGMENT_VIEW_DATA = "FRAGMENT_VIEW_DATA";
    private View view, divisionView;
    private LineBreakLayout tagLayout;
    private String thiz = getClass().getSimpleName();
    private List<String> labelList;
    private DetailHouse detailHouseData;
    private SmallItemView useRvgPriceTxt, reallyRvgPriceTxt, useRvgRentTxt, reallyRvgRentTxt, entrust_3_txt, entrust_5_txt;
    private SmallItemView conjectureDateTxt, ssdTxt, rvdTxt, useAreaTxt, reallyAreaTxt, usePercentTxt, rvdDateTxt;
    private SmallItemView openDateTxt, houseTypeTxt, carPlaceTxt, intervalTxt, numberTxt, directionTxt, houseSumTxt, fromTxt;
    private SmallItemView searchTxt, attentionTxt, supplementTxt, customization_1_Txt, customization_2_Txt, customization_3_Txt;
    private SmallItemView remarkTxt, moveInDateTxt, managementPriceTxt;
    private TextView tipTxt, salePriceTxt, rentPriceTxt, keyNumberTxt, greenTabPriceTxt;
    private HorizontalView navigationView;
    private NestedScrollView scrollContentView;
    private List<Integer> itemPositionList;
    private boolean isDoAnimation;
    private ImageView iconHot, iconKey, iconO, iconL, iconD, iconSingle, iconFavo;
    private TextView iconSSD;
    private int oldScrollY = -1;
    private ValueAnimator animator;
    private TextView followAddTxt, followAllTxt, clineNameTxt;
    private ListView followListView;
    private IDetailPresent present;
    private DataInfoFragment dataInformationView;
    private PriceRecordFragment priceRecordView;
    private DetailBriefInfo briefInfo;
    private FollowRecordFragment propertyFollowView;
    private TrustorFragment trustorInfoView;
    private TransRecordFragment transactionRecordView;
    private IBasicInfo iBasicInfo;
    private LineBreakLayout facilityLb;
    private LoadingDialog loadingDialog;
    private TextView transAllTxt;
    private boolean isLoadOther = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IBasicInfo) {
            iBasicInfo = (IBasicInfo) context;
        } else throw new IllegalArgumentException("Must Imp IBasicInfo");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EventBus.getDefault().register(this);
    }

    public static BasicInfoFragment newInstance(String keyId) {
        BasicInfoFragment oneFragment = new BasicInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(FRAGMENT_VIEW_DATA, keyId);
        oneFragment.setArguments(bundle);
        return oneFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_basicinfo, null);
        initData();
        initViews();
        initLisenter();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = getArguments();
//        if (bundle != null) {
//            if (bundle.get(BASIC_INFO) != null)
//                setViewsData((DetailHouse) bundle.get(BASIC_INFO));
//        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void initViews() {
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
        keyNumberTxt = view.findViewById(R.id.detail_key_number);

        divisionView = view.findViewById(R.id.detail_txt_division);

        greenTabPriceTxt = view.findViewById(R.id.detail_txt_green_tab_price);

        navigationView = view.findViewById(R.id.detail_scroll_navigation_view);
        scrollContentView = view.findViewById(R.id.detail_scroll_view);
        navigationView.addItems(getTitle());
        navigationView.selectItem(0);


        followAddTxt = view.findViewById(R.id.detail_follow_txt_add);
        followAllTxt = view.findViewById(R.id.detail_follow_txt_all);

//        dataInformationView = view.findViewById(R.id.detail_data_info);

//        priceRecordView = getActivity().getSupportLoaderManager();
        dataInformationView = (DataInfoFragment) getChildFragmentManager().findFragmentById(R.id.recprd_fra_datainfo);
        priceRecordView = (PriceRecordFragment) getChildFragmentManager().findFragmentById(R.id.record_fra_pricerecord);
        propertyFollowView = (FollowRecordFragment) getChildFragmentManager().findFragmentById(R.id.record_fra_followrecord);
        transactionRecordView = (TransRecordFragment) getChildFragmentManager().findFragmentById(R.id.record_fra_trans);
        trustorInfoView = (TrustorFragment) getChildFragmentManager().findFragmentById(R.id.record_fra_trustor);
        trustorInfoView.setaPresenter(present);

//        trustorInfoView = view.findViewById(R.id.detail_trustor);
//        transactionRecordView = view.findViewById(R.id.detail_treansact);

        iconHot = view.findViewById(R.id.item_icon_hot);
        iconKey = view.findViewById(R.id.item_icon_key);
        iconO = view.findViewById(R.id.item_icon_o);
        iconL = view.findViewById(R.id.item_icon_l);
        iconD = view.findViewById(R.id.item_icon_d);
        iconSingle = view.findViewById(R.id.item_icon_medal);
        iconFavo = view.findViewById(R.id.item_icon_favo);
        iconSSD = view.findViewById(R.id.item_icon_ssd);
        clineNameTxt = view.findViewById(R.id.item_txt_client);

        facilityLb = view.findViewById(R.id.text_ll_facility_layout);

        transAllTxt = view.findViewById(R.id.trans_txt_all);
//        showLoadingTips();
//        if (briefInfo != null) refreshOther(briefInfo);
//        if (detailHouseData != null) setViewsData(detailHouseData);
    }

//    private void showLoadingTips() {
//        loadingDialog = new LoadingDialog(getActivity());
//        loadingDialog.setCancelable(false);
//        loadingDialog.show();
//    }

    private List<String> getTitle() {
        List<String> taps = new ArrayList<>();
        taps.add("基本资料");
        taps.add("跟進");
        taps.add("業主資料");
        taps.add("叫價記錄");
        taps.add("成交記錄");
        taps.add("数据资料");
        return taps;
    }

    public void setBasicInfo(DetailHouse detailHouseData) {
        this.detailHouseData = detailHouseData;
        scrollContentView.getViewTreeObserver().addOnScrollChangedListener(scrollChangedListener);
        setViewsData(detailHouseData);
        isLoadOther = true;
    }


    private void initLisenter() {

        navigationView.setOnItemClickLisenter((view, position) -> {
            int y = (int) scrollContentView.findViewWithTag(position + "").getY();
            isDoAnimation = true;
            doAnimation(500, scrollContentView.getScrollY(), y);
        });

        followAddTxt.setOnClickListener(v -> {
            iBasicInfo.turnToActivity(new Intent(getContext(), FollowAddActivity.class));
        });

        followAllTxt.setOnClickListener(v ->
                iBasicInfo.turnToActivity(new Intent(getContext(), FollowActivity.class)));

        priceRecordView.setOnItemClickLisenter((v, type) -> {
            switch (type) {
                case PROPERTY_PRICE_SALE:
                    priceRecordView.setData(briefInfo.getSaleTrusts());
                    break;
                case PROPERTY_PRICE_RENT:
                    priceRecordView.setData(briefInfo.getRentTrusts());
                    break;
            }
        });

        scrollContentView.getViewTreeObserver().addOnScrollChangedListener(scrollChangedListener);

        transAllTxt.setOnClickListener(v -> {
            iBasicInfo.turnToActivity(new Intent(getContext(), TransactActivity.class));
        });
    }

    ViewTreeObserver.OnScrollChangedListener scrollChangedListener = new ViewTreeObserver.OnScrollChangedListener() {
        @Override
        public void onScrollChanged() {
            View childView = scrollContentView.getChildAt(0);
            if (childView != null && childView.getMeasuredHeight() <= scrollContentView.getScrollY() + scrollContentView.getHeight()) {
                navigationView.selectItem(navigationView.getItemCount() - 1);
                return;
            }

            if (!isDoAnimation && oldScrollY != scrollContentView.getScrollY()) {
                Log.d("Scroll", "onScrollChanged: " + scrollContentView.getScrollY());
                getItemPosition();
                oldScrollY = scrollContentView.getScrollY();
                if (getCurrentItem(scrollContentView.getScrollY() + scrollContentView.getHeight()) >= 2 && isLoadOther) {
                    DetailListsDescription description = new DetailListsDescription();
                    isLoadOther = false;
                    description.setKeyId(getArguments().getString(FRAGMENT_VIEW_DATA));
                    present.doGet(HttpUtil.URL_DETAILS_LIST, ((MyApplication) getContext().getApplicationContext()).getHeaderDescription(), description);
                }
                navigationView.selectItem(getCurrentItem(scrollContentView.getScrollY()));
            }
        }
    };

    private void doAnimation(int duration, int start, int end) {
        if (animator != null) animator.cancel();

        animator = ValueAnimator.ofInt(start, end);
        animator.setDuration(duration);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                scrollContentView.scrollTo(0, (Integer) animation.getAnimatedValue());
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isDoAnimation = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isDoAnimation = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private int getCurrentItem(int postion) {
        int index = 0;
        for (int i = 0; i < itemPositionList.size(); i++) {
            if (postion >= itemPositionList.get(itemPositionList.size() - 1 - i)) {
                Log.d("Scroll", "getCurrentItem: ");
                index = itemPositionList.size() - 1 - i;
                break;
            }
        }
        Log.d("Scroll", "index: " + index);
        return index;
    }

    private void getItemPosition() {
        itemPositionList.clear();
        for (int i = 0; i < 8; i++) {
            View view = scrollContentView.findViewWithTag(i + "");
            if (view != null)
                itemPositionList.add((int) view.getY());
        }
    }

    private void initData() {
        itemPositionList = new ArrayList<>();


//        present.doGet(HttpUtil.URL_DETAILS_LIST, ((MyApplication) getContext().getApplicationContext()).getHeaderDescription(), description);
    }


    private String formatData(String data) {
        if (data == null) return null;
        data = data.replaceAll("[A-Z]+", " ").trim();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formaData = null;
        try {
            formaData = sdf.format(sdf.parse(data));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formaData;
    }

    private void setViewsData(DetailHouse detailHouseData) {

        navigationView.setIndexItemTip(1, detailHouseData.getLastUpdateTime());

        iconSingle.setSelected(detailHouseData.isHasOnlyTrust());
        iconFavo.setSelected(detailHouseData.isFavorite());
        L.d(thiz, "DetaileActivity: " + detailHouseData.isFavorite());
        iconO.setSelected(detailHouseData.isODish());
        iconKey.setImageLevel(detailHouseData.getPropertyKeyType());
        iconHot.setSelected(detailHouseData.getHotList() == null || detailHouseData.getHotList().equals("") ? false : true);
        iconL.setSelected(!detailHouseData.isConfirmed());
        iconD.setSelected(detailHouseData.getDevelopmentEndCredits());
        clineNameTxt.setText(detailHouseData.getPropertyBuildingOwner() == null || detailHouseData.getPropertyBuildingOwner() == "" ? " " + getString(R.string.detail_no_owner) : " " + detailHouseData.getPropertyBuildingOwner());

        if (detailHouseData.getSSDType() != 0) {
            iconSSD.setVisibility(View.VISIBLE);
            int per = 5 * detailHouseData.getSSDType();
            if (detailHouseData.getSSDType() == 1) per = 0;
            iconSSD.setText(per + "%");
        }

        trustorInfoView.setOdish(detailHouseData.isHasOnlyTrust());

        useRvgPriceTxt.setContentName(detailHouseData.getActualSalePriceUnit());
        reallyRvgPriceTxt.setContentName(detailHouseData.getSalePriceUnit());
        useRvgRentTxt.setContentName(detailHouseData.getActualRentPriceUnit());
        reallyRvgRentTxt.setContentName(detailHouseData.getRentPriceUnit());
        entrust_3_txt.setContentName(detailHouseData.getPowerOfAttorneyThree());
        entrust_5_txt.setContentName(detailHouseData.getPowerOfAttorneyFive());

        conjectureDateTxt.setContentName(formatData(detailHouseData.getEstimatedDate()));
        rvdDateTxt.setContentName(formatData(detailHouseData.getProvideDate()));
        openDateTxt.setContentName(formatData(detailHouseData.getRegisterDate()));

        usePercentTxt.setContentName(detailHouseData.getUtilityRatio());
        tipTxt.setText(detailHouseData.getPrompt());
        directionTxt.setContentName(detailHouseData.getHouseDirection());
        intervalTxt.setContentName(detailHouseData.getPropertyInterval());
        houseTypeTxt.setContentName(detailHouseData.getPropertyUsage());
        houseSumTxt.setContentName(detailHouseData.getAllFloor());
        carPlaceTxt.setContentName(detailHouseData.getParkingNumber());
        fromTxt.setContentName(detailHouseData.getPropertySource());
        supplementTxt.setContentName(detailHouseData.getSupply());
        customization_1_Txt.setContentName(detailHouseData.getCustomField1());
        customization_2_Txt.setContentName(detailHouseData.getCustomField2());
        customization_3_Txt.setContentName(detailHouseData.getCustomField3());
        remarkTxt.setContentName(detailHouseData.getRemark());
        numberTxt.setContentName(detailHouseData.getAccessementNo());
        Log.e(TAG, "setViewsData: " + detailHouseData.getMgrFee());
        managementPriceTxt.setContentName(detailHouseData.getMgrFee() == null ? null : ((int) Float.parseFloat(detailHouseData.getMgrFee())) + "");
        keyNumberTxt.setText(getString(R.string.keyhouse) + " " + detailHouseData.getPropertyKeyNo());
        String squareUseFoot = detailHouseData.getSquareUseFoot();
        String squareSource = detailHouseData.getSquareUseSource();
        setUseSquare(squareUseFoot, squareSource);

        int propertType = isShouldToShowType(detailHouseData.getBulidingPropertyUsage()) ? View.VISIBLE : View.GONE;
        greenTabPriceTxt.setVisibility(propertType);
        divisionView.setVisibility(propertType);

        String reallyAreaSource = detailHouseData.getSquareSource() == null || detailHouseData.getSquareSource().equals("") ? null : "(" + detailHouseData.getSquareSource() + ")";
        reallyAreaTxt.setContentName(detailHouseData.getSquareFoot() + (reallyAreaSource == null ? "" : reallyAreaSource));

        ssdTxt.setContentName(detailHouseData.getSSDInfo() == null || detailHouseData.getSSDInfo().equals("") ? "未知" : detailHouseData.getSSDInfo());
        moveInDateTxt.setContentName(detailHouseData.getCompleteYear());
        attentionTxt.setContentName(detailHouseData.getPropertyNote());
        searchTxt.setContentName(formatData(detailHouseData.getSearchDate()));

        greenTabPriceTxt.setText("綠: $" + (detailHouseData.getSalePricePremiumUnpaid() == null || detailHouseData.getSalePricePremiumUnpaid().equals("") ? 0 : detailHouseData.getSalePricePremiumUnpaid()) + "萬");

        setSalePriceTxt(detailHouseData.getSalePrice(), detailHouseData.getSaleFloorPriceFormate());
        setRentPriceTxt(detailHouseData.getRentPrice(), detailHouseData.getRentFloorPriceFormate());

        facilityLb.addItem(detailHouseData.getBuildingTagInfos());

        if (detailHouseData.getPropertyTags() != null) {
            String[] tags = detailHouseData.getPropertyTags().split(",");
            for (String tag : tags)
                tagLayout.addItem(tag);
        }
    }

    private boolean isShouldToShowType(String propertType) {
        boolean show = false;
        if (propertType == null) return false;
        switch (propertType) {
            case "資助房屋":
            case "居屋":
            case "公屋":
                show = true;
                break;
        }
        return show;
    }

    private void setRentPriceTxt(String rentPrice, String rentFloorPrice) {

        NumberFormat currency = NumberFormat.getCurrencyInstance();
        Log.e(TAG, "setRentPriceTxt: " + rentPrice);

        if (rentPrice != null)
            rentPrice = ((int) Float.parseFloat(rentPrice)) + "";

        Log.e(TAG, "setRentPrice: " + rentPrice);
        String price = null;

        if (rentPrice == null) price = "租: $0";

        if (rentPrice != null && currency.format(new BigDecimal(rentPrice)).indexOf("$") != -1)
            price = "租: $" + currency.format(new BigDecimal(rentPrice)).split("\\.")[0].split("\\$")[1];
        else if (rentPrice != null)
            price = "租: " + currency.format(new BigDecimal(rentPrice)).split("\\.")[0];

        if (rentFloorPrice != null && rentFloorPrice != "") {
            price = price + "(" + rentFloorPrice.split("\\.")[0] + ")";
        }
        rentPriceTxt.setText(price);
    }

    private void setSalePriceTxt(String salePrice, String saleFloorPrice) {
        String price = "售: $" + salePrice + "萬";
        if (saleFloorPrice != null && saleFloorPrice != "") {
            price = price + "(" + saleFloorPrice + ")";
        }
        salePriceTxt.setText(price);
    }

    private void setUseSquare(String squareUseFoot, String squareSource) {
        Spanned spanned = null;
        if (squareSource != null && squareSource != "") {
            if (squareSource.equals("RVD") || squareSource.equals("rvd"))
                spanned = TextUtil.changeKeyWordColor(squareUseFoot + " " + squareSource, squareSource, Color.RED + "");
            else
                spanned = TextUtil.changeKeyWordColor(squareUseFoot + " " + squareSource, squareSource, Color.BLUE + "");
        } else {
            spanned = TextUtil.changeTextColor(squareUseFoot, Color.BLACK + "");
        }
        useAreaTxt.setContentName(spanned);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setaPresenter(IDetailPresent presenter) {
        present = presenter;
    }

    @Override
    public void setData(DetailBriefInfo data) {

        getActivity().runOnUiThread(() -> {
            briefInfo = data;
            refreshOther(data);
        });
    }

    public void refreshOther(DetailBriefInfo data) {

//        resetFragment();

        dataInformationView.setData(data.getCentaData());

//            priceRecordView.resetSaleRBBg();

        if (data.getSaleTrusts() != null && !data.getSaleTrusts().isEmpty()) {
            priceRecordView.setData(data.getSaleTrusts());
        } else if (data.getRentTrusts() != null && !data.getRentTrusts().isEmpty()) {
            priceRecordView.setData(data.getRentTrusts());
            priceRecordView.checkType(PROPERTY_PRICE_RENT);
        }

        if (data.getRentTrusts() == null || data.getRentTrusts().isEmpty()) {
            priceRecordView.setRentRBClickable(false);
        }


        if (data.getSaleTrusts() == null || data.getSaleTrusts().isEmpty()) {
            priceRecordView.setSaleRBClickable(false);
        }
        if ((data.getSaleTrusts() == null || data.getSaleTrusts().isEmpty()) && (data.getRentTrusts() == null || data.getRentTrusts().isEmpty())) {
            priceRecordView.setData(null);
        }


        propertyFollowView.setData(data.getPropertyFollows());

        trustorInfoView.setData(data.getPropertyTrustors());
        transactionRecordView.setData(data.getPropertyTransactions());

    }


    public void resetFragment() {
//        scrollContentView.g
        scrollContentView.getViewTreeObserver().removeOnScrollChangedListener(scrollChangedListener);
        scrollContentView.scrollTo(0, 0);

        transactionRecordView.resetFragment();
        propertyFollowView.resetFragment();
        priceRecordView.resetFragment();
        dataInformationView.resetFragment();
        trustorInfoView.resetFragment();

    }


    public interface IBasicInfo {
        void turnToActivity(Intent intent);
    }
}
