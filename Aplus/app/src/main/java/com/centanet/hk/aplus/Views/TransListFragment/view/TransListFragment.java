package com.centanet.hk.aplus.Views.TransListFragment.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.centanet.hk.aplus.BackHandlerHelper.FragmentBackHandler;
import com.centanet.hk.aplus.MyApplication;
import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.DensityUtil;
import com.centanet.hk.aplus.Utils.DialogUtil;
import com.centanet.hk.aplus.Utils.FileUtil;
import com.centanet.hk.aplus.Utils.ItemCountUtil;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.MD5Util;
import com.centanet.hk.aplus.Utils.TextUtil;
import com.centanet.hk.aplus.Utils.TimeLimitUtil;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.Views.ComplexSearchView.TransFiltrateActivity;
import com.centanet.hk.aplus.Views.Dialog.AreaDialog;
import com.centanet.hk.aplus.Views.Dialog.FlootUnitDialog;
import com.centanet.hk.aplus.Views.Dialog.SearchOptionDialog;
import com.centanet.hk.aplus.Views.Dialog.SortDialog;
import com.centanet.hk.aplus.Views.Dialog.TransSearchOptionDialog;
import com.centanet.hk.aplus.Views.Dialog.TransSortDialog;
import com.centanet.hk.aplus.Views.Dialog.UserDesignNameDialog;
import com.centanet.hk.aplus.Views.HomePageView.view.HomePagerFragment;
import com.centanet.hk.aplus.Views.MainActivity.view.MainActivity;
import com.centanet.hk.aplus.Views.SearchView.view.SearchFragment;
import com.centanet.hk.aplus.Views.TransHomePagerView.TransPagerView.view.TransHomePagerFragment;
import com.centanet.hk.aplus.Views.TransHomePagerView.TransSearchView.view.TransSearchFragment;
import com.centanet.hk.aplus.Views.TransListFragment.present.ITransPresenter;
import com.centanet.hk.aplus.Views.TransListFragment.present.TransPresenter;
import com.centanet.hk.aplus.Views.TransactView.TransDetailActivity;
import com.centanet.hk.aplus.Views.basic.BaseFragment;
import com.centanet.hk.aplus.Views.basic.BaseListAdapter;
import com.centanet.hk.aplus.Widgets.CircleTipsView;
import com.centanet.hk.aplus.Widgets.PropertyDetail.PropertyTransactionRecordView;
import com.centanet.hk.aplus.Widgets.SmallItemView;
import com.centanet.hk.aplus.bean.auto_estate.PropertyParamHints;
import com.centanet.hk.aplus.bean.detail.PropertyTransaction;
import com.centanet.hk.aplus.bean.district.DistrictItem;
import com.centanet.hk.aplus.bean.http.AHeaderDescription;
import com.centanet.hk.aplus.bean.http.HouseDescription;
import com.centanet.hk.aplus.bean.http.UserBehaviorDescription;
import com.centanet.hk.aplus.bean.translist.TransListRequest;
import com.centanet.hk.aplus.bean.translist.Transaction;
import com.centanet.hk.aplus.bean.userdesign_option.PropertySearchHistory;
import com.centanet.hk.aplus.bean.userdesign_option.TransSearchHistory;
import com.centanet.hk.aplus.manager.ApplicationManager;
import com.centanet.hk.aplus.manager.PropertyRequestParamsManager;
import com.centanet.hk.aplus.manager.ScreenShotListenManager;
import com.centanet.hk.aplus.manager.TransRequestParamsManager;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.centanet.hk.aplus.Views.ComplexSearchView.TransFiltrateActivity.CODE_TRAN_FILTRATE;
import static com.centanet.hk.aplus.Views.ComplexSearchView.TransFiltrateActivity.TRANS_PARAMS;
import static com.centanet.hk.aplus.Views.SearchView.view.SearchActivity.VIEW_SEARCH_LABEL;

/**
 * Created by yangzm4 on 2018/8/9.
 */

public class TransListFragment extends BaseFragment implements ITransListView, View.OnClickListener, FragmentBackHandler {

    public static final String FRAGMENT_TAG_TRANSLIST = "FRAGMENT_TAG_TRANSLIST";
    private View back, area, flootUnit, more, sort, save, allOption, mic;
    private TextView search;
    private ListView lv;
    private SmartRefreshLayout refreshLayout;
    private TextView flootTxt, unitTxt, areaTipTxt, optionTipTxt, optionTxt;
    private TransListAdapter adapter;
    private List<Transaction> transactionList;
    private TextView currenPositionTxt;
    private TransListRequest request;
    private ITransPresenter presenter;
    private AHeaderDescription header;
    private int count;
    private int sortDialogSelectId;
    private CircleTipsView moreTip;
    private String searchTip;
    private ScreenShotListenManager screenShotListenManager;
    private String thiz = getClass().getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_property, null, false);
        init();
        initView(view);
        initLisenter();
        setOptionCount();
        refreshLayout.autoRefresh();
        hideKeyboard();
        startScreenListen();
        return view;
    }

    private void initLisenter() {

        sort.setOnClickListener(this);
        area.setOnClickListener(this);
        flootUnit.setOnClickListener(this);
        save.setOnClickListener(this);
        more.setOnClickListener(this);
        flootTxt.setOnClickListener(this);
        unitTxt.setOnClickListener(this);
        allOption.setOnClickListener(this);
        mic.setOnClickListener(this);
        back.setOnClickListener(this);
        search.setOnClickListener(this);

        refreshLayout.setOnRefreshListener((refreshlayout) -> {
//            presenter.clearFlag();
            request.setPageIndex(1);
            presenter.getTransList(HttpUtil.URL_TAG_TRANLIST, header, request);
            refreshlayout.setEnableLoadmore(true);
        });

        refreshLayout.setOnLoadmoreListener((refreshlayout) -> {
            if (transactionList.size() == count) {
                refreshLayout.setEnableLoadmore(false);
                refreshlayout.finishLoadmore(2000);
                showEndTips();
                return;
            }
            request.setPageIndex(request.getPageIndex() + 1);
            presenter.getTransList(HttpUtil.URL_TAG_TRANLIST, header, request);
        });

        lv.getViewTreeObserver().addOnScrollChangedListener(() -> {
            int item = lv.getFirstVisiblePosition() + 1;
            if (lv.getChildAt(0) != null) {
                int y = lv.getChildAt(0).getTop();
                if (y < -220) item++;
            }
            currenPositionTxt.setText(item + "/" + count);
        });

        lv.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(getActivity(), TransDetailActivity.class);
            String keyId = transactionList.get(i).getPropertyKeyId();
            String transKeyId = transactionList.get(i).getKeyId();
            intent.putExtra("keyId", keyId);
            intent.putExtra("tans_keyId", transKeyId);
            startActivity(intent);
        });
    }

    private void init() {
        transactionList = new ArrayList<>();

        if (getArguments() != null) {
            request = (TransListRequest) getArguments().get("HOUSE_REQUEST");
            searchTip = getArguments().getString(VIEW_SEARCH_LABEL);
            L.d("TransListFrag", "HOUSE_REQUEST");
        }

//        Calendar calendar = Calendar.getInstance();
//        String end = calendar.get(Calendar.YEAR)+"-"+calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
//        String start = "";

        if (!TextUtil.isEmply(request.getTrusactionDate())) {
            String start = null;
            Calendar calendar = Calendar.getInstance();
            String end = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
            calendar.add(Calendar.DAY_OF_MONTH, -Integer.parseInt(request.getTrusactionDate()));
            start = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
            request.setTransactionDateFrom(start);
            request.setTransactionDateTo(end);
        } else if (TextUtil.isEmply(request.getCompleteDateTo()) && TextUtil.isEmply(request.getCompleteDateFrom())
                && TextUtil.isEmply(request.getTransactionDateFrom()) && TextUtil.isEmply(request.getTransactionDateTo())
                && TextUtil.isEmply(request.getPrelimDateFrom()) && TextUtil.isEmply(request.getPrelimDateTo())
                && TextUtil.isEmply(request.getFormalDateFrom()) && TextUtil.isEmply(request.getFormalDateTo())
                && TextUtil.isEmply(request.getRentDateFrom()) && TextUtil.isEmply(request.getRentDateTo())) {
            Calendar calendar = Calendar.getInstance();
            String start = null;
            String end = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
            calendar.add(Calendar.MONTH, -1);
//            calendar.add(Calendar.DAY_OF_MONTH, -30);
            start = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
            request.setTransactionDateFrom(start);
            request.setTransactionDateTo(end);
        }

        L.d("getTransactionDateFrom", request.getTransactionDateFrom() + " : " + request.getTransactionDateTo());
//        request.setCompleteDateTo(null);
//        request.setCompleteDateFrom(null);
//
//        request.setTransactionDateFrom(null);
//        request.setTransactionDateTo(null);
//
//        request.setPrelimDateFrom(null);
//        request.setPrelimDateTo(null);
//
//        request.setFormalDateFrom(null);
//        request.setFormalDateTo(null);
//
//        request.setRentDateFrom(null);
//        request.setRentDateTo(null);

        //todo  判断日期是否为空 其他的日期 是的话 就不进入判断

//        if (!TextUtil.isEmply(request.getTrusactionDate())) {
//            calendar.add(Calendar.DAY_OF_MONTH, -Integer.parseInt(request.getTrusactionDate()));
//            start = calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
//
//        } else {
//            calendar.add(Calendar.MONTH, -1);
//            start = calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
//        }
//
//        request.setTransactionDateFrom(start);
//        request.setTransactionDateTo(end);

        if (request == null)
            request = new TransListRequest();

        presenter = new TransPresenter(this);
        header = ApplicationManager.getApplication().getHeaderDescription();
        adapter = new TransListAdapter(getContext(), transactionList, R.layout.item_translist);
    }

    private void initView(View view) {

        back = view.findViewById(R.id.back);
        area = view.findViewById(R.id.house_view_area);
        flootUnit = view.findViewById(R.id.list_ll_floot);
        more = view.findViewById(R.id.list_img_moreoption);
        sort = view.findViewById(R.id.list_img_sort);
        optionContent = view.findViewById(R.id.property_rl_option_content);
        save = view.findViewById(R.id.list_txt_save);
        allOption = view.findViewById(R.id.house_rl_alloption);
        search = view.findViewById(R.id.list_txt_search);
        optionTxt = view.findViewById(R.id.list_txt_proselter);
        moreTip = view.findViewById(R.id.more_tip);

        if (searchTip != null && !searchTip.equals(""))
            search.setText(searchTip);

        mic = view.findViewById(R.id.mic);
        areaTipTxt = view.findViewById(R.id.house_txt_areatips);
        optionTipTxt = view.findViewById(R.id.house_txt_option_tip);

        currenPositionTxt = view.findViewById(R.id.fragment_txt_currentpos);

        flootTxt = view.findViewById(R.id.floot);
        unitTxt = view.findViewById(R.id.unit);

        lv = view.findViewById(R.id.fragment_presmises_listview);
        lv.setAdapter(adapter);

        refreshLayout = view.findViewById(R.id.smartLayout);
    }

    private void startScreenListen() {
        screenShotListenManager = ScreenShotListenManager.newInstance(getActivity());
        screenShotListenManager.setListener((imagePath) -> onScreenShot());
        screenShotListenManager.startListen();
    }

    private void onScreenShot() {
        L.d("onScreenShot", isHidden() + "");
//        if (!TimeLimitUtil.isAchieveLimitTime(1000)) return;
        if (isHidden() || !isResumed()) return;

        if (getActivity() == null) return;
        if (((MainActivity) getActivity()).getCurrentItem() != 2) return;

        L.d(thiz, "HouseShot: " + " FirstVisiblePosition: " + lv.getFirstVisiblePosition() + " LastVisiblePosition: " + lv.getLastVisiblePosition());
        UserBehaviorDescription userBehaviorDescription = new UserBehaviorDescription();
        userBehaviorDescription.setAction(1);
        userBehaviorDescription.setPage(6);
        userBehaviorDescription.setExtras(getExtras());

        String number = System.currentTimeMillis() / 1000 + "";
        L.d("time", number);
        header.setNumber(number);
        header.setSign(MD5Util.getMD5Str("CYDAP_com-group~Centa@" + number + header.getStaffno()));

        HttpUtil.doPost(HttpUtil.URL_USER_BEHAVIOR, userBehaviorDescription, header, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.d("onScreenShot", response.body().string().toString());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        L.d("View_State", "House_Onresume");
//        startScreenListen();
    }

    @Override
    public void onStop() {
        super.onStop();
        screenShotListenManager.stopListen();
        L.d("View_State", "House_Onstop");
    }

    private String getExtras() {
        if (lv.getCount() == 0) return null;
        int firstVisiblePosition = lv.getFirstVisiblePosition();
        int lastVisiblePosition = lv.getLastVisiblePosition();

        String extras = "{" + "\"TransactionKeyId\"" + ":" + "[";
        for (int i = firstVisiblePosition; i <= lastVisiblePosition; i++) {
            extras = extras + "\"" + transactionList.get(i).getKeyId() + "\"" + ",";
        }
        extras = extras.substring(0, extras.length() - 1);
        extras = extras + "]}";
        L.d(thiz, "onShot: " + extras);
        return extras;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                getActivity().onBackPressed();
                break;
            case R.id.house_view_area:
                showAreaDialog();
                break;
            case R.id.list_ll_floot:
                showFlootUnitDialog();
                break;
            case R.id.floot:
                showFlootUnitDialog();
                break;
            case R.id.unit:
                showFlootUnitDialog();
                break;
            case R.id.list_img_moreoption:
                Intent intent = new Intent(getActivity(), TransFiltrateActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(TRANS_PARAMS, request);
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
                break;
            case R.id.list_img_sort:
                showSortDialog();
                break;
            case R.id.list_txt_save:
                saveUserDesign();
                break;
            case R.id.house_rl_alloption:
                showCurrenOptionDialog();
                break;
            case R.id.list_txt_search:
                turnToSearchFragment();
                break;
            case R.id.mic:

                FragmentTransaction ft1 = getParentFragment().getChildFragmentManager().beginTransaction();
                ft1.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                Fragment fragment1 = getParentFragment().getChildFragmentManager().findFragmentByTag(TransSearchFragment.FRAGMENT_TAG_SEARCH);
                if (fragment1 == null) {
                    fragment1 = new TransSearchFragment();
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable("HOUSE_REQUEST", request);
                    bundle1.putBoolean("mic", true);
                    fragment1.setArguments(bundle1);
                    ft1.hide(this);
                    ft1.add(R.id.fragment_content, fragment1, TransSearchFragment.FRAGMENT_TAG_SEARCH).commit();
                } else {
                    Bundle bundle1 = new Bundle();
                    bundle1.putBoolean("mic", true);
                    bundle1.putSerializable("HOUSE_REQUEST", request);
                    fragment1.setArguments(bundle1);

                    ft1.show(fragment1);
                    ft1.hide(this).commit();
                }

                break;
        }
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getActivity().getCurrentFocus() != null) {
            if (getActivity().getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        hideKeyboard();
    }

    private void turnToSearchFragment() {
        FragmentTransaction ft = getParentFragment().getChildFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        Fragment fragment = getParentFragment().getChildFragmentManager().findFragmentByTag(TransSearchFragment.FRAGMENT_TAG_SEARCH);

        Bundle bundle = new Bundle();
        bundle.putSerializable("HOUSE_REQUEST", request);

        L.d("putSerializable", request.toString());

        if (fragment == null) {
            fragment = new TransSearchFragment();
            fragment.setArguments(bundle);
            ft.add(R.id.fragment_content, fragment, TransSearchFragment.FRAGMENT_TAG_SEARCH);
        } else {
            fragment.setArguments(bundle);
            ft.show(fragment);
        }
        ft.hide(this).commit();
    }

    private void showCurrenOptionDialog() {
        TransSearchOptionDialog optionDialog = new TransSearchOptionDialog();
        optionDialog.setData(request);
        optionDialog.setOnItemClickLisenter((dialog, v1, description) -> {
            dialog.dismiss();
            request = description;
            if (description.getSortField().equals("DisplayTransDate") && !description.isAscending()) {
                sortDialogSelectId = R.id.sort_rb_price_down;
            }
            openFreshView();
            setOptionCount();
        });
        optionDialog.show(getFragmentManager(), "");
    }

    private void saveUserDesign() {
        UserDesignNameDialog userDesignNameDialog = new UserDesignNameDialog();
        userDesignNameDialog.setOnItemclickListener((dialog1, s, type) -> {
            dialog1.dismiss();
            if (type == UserDesignNameDialog.DIALOG_YES) {
                saveOption(s);
            }
        });
        userDesignNameDialog.show(getFragmentManager(), "");
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

                TransSearchHistory history = new TransSearchHistory(request, TransRequestParamsManager.getParams());
                history.setOptionMame(name);
                searchHistories.add(history);
                gson = new Gson().toJson(searchHistories);
                L.d("TransSaveOption", "Save: " + gson);
            } else {
                List<TransSearchHistory> searchHistories = new ArrayList<>();
                TransSearchHistory history = new TransSearchHistory(request, TransRequestParamsManager.getParams());
                searchHistories.add(history);
                history.setOptionMame(name);
                gson = new Gson().toJson(searchHistories);
//                L.d("SearchHis_Single", new Gson().toJson(history));
            }
            FileUtil.saveFile(gson, file);
            if (getActivity() != null) showSaveFinishDialog();
        }).start();
    }

    private void showEndTips() {
        Toast.makeText(getActivity(), getString(R.string.no_more_data), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void refreshListData(List<Transaction> properties) {
        L.d("TransResp", "refreshListData");
        if (refreshLayout.isLoading()) transactionList.addAll(properties);
        if (refreshLayout.isRefreshing()) {
            transactionList.clear();
            transactionList.addAll(properties);
        }
        if (getActivity() != null) {
            getActivity().runOnUiThread(() -> adapter.notifyDataSetChanged());
        }
        closeRefresh();
    }

    public void closeRefresh() {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadmore();
    }

    private void showFlootUnitDialog() {
        String[] str = new String[2];
        str[0] = request.getFloors();
        str[1] = request.getUnits();
        FlootUnitDialog dialog = new FlootUnitDialog();
        dialog.setData(str);
        dialog.setOnItemClickLisenter((dialog1, v1, list) -> {
            if (list[0] == null || list[0].equals("")) {
                flootTxt.setText(R.string.search_floot);
            } else flootTxt.setText(list[0]);

            if (list[1] == null || list[1].equals("")) {
                unitTxt.setText(R.string.search_units);
            } else unitTxt.setText(list[1]);

            request.setFloors(list[0]);
            request.setUnits(list[1]);
            refreshLayout.autoRefresh();
            setOptionCount();
        });
        dialog.show(getFragmentManager(), "");
    }

    private void showAreaDialog() {
        AreaDialog areaDialog = new AreaDialog();
        areaDialog.setItem(ApplicationManager.getDistrictItems(), request.getDistrictListIds());
        areaDialog.setOnItemClickLisenter((dialog, v1, list) -> {
            if (list != null && !list.isEmpty()) {
                areaTipTxt.setVisibility(View.VISIBLE);
                areaTipTxt.setText(list.size() + "");
            } else areaTipTxt.setVisibility(View.GONE);
            request.setDistrictListIds(list);
            TransRequestParamsManager.getParams().setArea(getSelectDistrict(ApplicationManager.getDistrictItems(), list));
            refreshLayout.autoRefresh();
            setOptionCount();
        });
        areaDialog.show(getFragmentManager(), "");
    }

    private void setOptionCount() {
        try {
//            moreTip.setVisibility(View.VISIBLE);
//            moreTip.setText(ItemCountUtil.count(bodyDescription) + "");
//            L.d("setOptionCount",request.toString());
            int count = ItemCountUtil.paramsCount(request) - 6;
            L.d("setOptionCount", count + "");

            if (request.getPriceUnitType() != null)
                count--;

            if (request.getContactSearchType() != null) {
                count--;
            }

//            if (request.getTrusactionDate() != null) {
//                count--;
//            }
            if (request.getTrusactionDate() != null) {
                count--;
            }

            if (request.getTransactionTypes() != null) {
                count = count + request.getTransactionTypes().split(",").length - 1;
            }

            if (request.getDistrictListIds() != null)
                count = count - request.getDistrictListIds().size();
            if (request.getSearcherAddress() != null)
                count = count - request.getSearcherAddress().size();
            if (request.getFloors() != null && !request.getFloors().equals(""))
                count--;
            if (request.getUnits() != null && !request.getUnits().equals(""))
                count--;

            if (!TextUtil.isEmply(request.getCompleteDateTo()) && !TextUtil.isEmply(request.getCompleteDateFrom())) {
                count--;
            }
            if (!TextUtil.isEmply(request.getTransactionDateFrom()) && !TextUtil.isEmply(request.getTransactionDateTo())) {
                count--;
            }
            if (!TextUtil.isEmply(request.getPrelimDateFrom()) && !TextUtil.isEmply(request.getPrelimDateTo())) {
                count--;
            }
            if (!TextUtil.isEmply(request.getFormalDateFrom()) && !TextUtil.isEmply(request.getFormalDateTo())) {
                count--;
            }
            if (!TextUtil.isEmply(request.getRentDateFrom()) && !TextUtil.isEmply(request.getRentDateTo())) {
                count--;
            }

            if (!TextUtil.isEmply(request.getSellPriceFrom()) && !TextUtil.isEmply(request.getSellPriceTo())) {
                count--;
            }

            if (!TextUtil.isEmply(request.getRentPriceFrom()) && !TextUtil.isEmply(request.getRentPriceFrom())) {
                count--;
            }

            L.d("getPriceUnitType", request.getPriceUnitType());
            if (!TextUtil.isEmply(request.getPriceUnitType()) && Integer.parseInt(request.getPriceUnitType()) != 0) {
                count--;
            }

            if (!TextUtil.isEmply(request.getSortField())) {
                count--;
            }

            if (!TextUtil.isEmply(request.getSquareUseFrom()) && !TextUtil.isEmply(request.getSquareUseTo())) {
                count--;
            }

            if (!TextUtil.isEmply(request.getSquareFrom()) && !TextUtil.isEmply(request.getSquareTo())) {
                count--;
            }

//            if (request.getBuildingUseTypes() != null)
//                count = count - request.getBuildingUseTypes().size();

            if (request.getDistrictListIds() == null || request.getDistrictListIds().isEmpty())
                areaTipTxt.setVisibility(View.GONE);
            else {
                areaTipTxt.setText(request.getDistrictListIds().size() + "");
                areaTipTxt.setVisibility(View.VISIBLE);
            }

            if (!TextUtil.isEmply(request.getFloors())) {
                flootTxt.setText(request.getFloors());
            }

            if (!TextUtil.isEmply(request.getUnits())) {
                unitTxt.setText(request.getUnits());
            }

            moreTip.setText(count + "");
            if (count <= 0) {
                moreTip.setVisibility(View.GONE);
            } else {
                moreTip.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        L.d("TransListFragment", request.getSearcherAddress() + "");

//        if (request.getSearcherAddress() == null || request.getSearcherAddress().isEmpty()) {
//            optionTxt.setText(R.string.dialog_price_unlimit);
//            optionTipTxt.setVisibility(View.GONE);
//        } else {
//            optionTxt.setText(parseData(TransRequestParamsManager.getParams().getAddress().get(0)));
//            optionTipTxt.setVisibility(View.VISIBLE);
//            optionTipTxt.setText(TransRequestParamsManager.getParams().getAddress().size() + "");
//            String addStr = "";
//            for (PropertyParamHints h : TransRequestParamsManager.getParams().getAddress()) {
////                addStr = parseData(h);
//                addStr = addStr + parseData(h) + ",";
//            }
////            search.setText(addStr.substring(0, addStr.length() - 1));
//            L.d("TransListFragment", addStr);
//        }

        if (TextUtil.isEmply(request.getSearcherAddress())) {
            optionTxt.setText(R.string.dialog_price_unlimit);
            search.setText(null);
            optionTipTxt.setVisibility(View.GONE);
//            L.d("",);
        } else {
//            optionTxt.setText(parseData(PropertyRequestParamsManager.getParams().getAddress().get(0)));
            optionTipTxt.setVisibility(View.VISIBLE);
            optionTipTxt.setText(TransRequestParamsManager.getParams().getAddress().size() + "");
            String addStr = "";
            for (PropertyParamHints h : TransRequestParamsManager.getParams().getAddress()) {
                addStr = addStr + getDeatilAddress(h) + ",";
            }
            optionTxt.setText(addStr);
        }

        optionContent.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
        optionContent.measure(0,0);

    }

    private View optionContent;
    private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener(){

        @Override
        public void onGlobalLayout() {
            int width = optionContent.getWidth();
            L.d("optionContent_Width",width+"");
            if (optionTxt.getWidth() + DensityUtil.dip2px(getContext(), 35) > width) {
//            search.setText(addStr.substring(0, addStr.length() - 1));
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width - DensityUtil.dip2px(getContext(), 35), ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(DensityUtil.dip2px(getContext(), 12), 0, 0, 0);
                layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
                optionTxt.setSingleLine();
                optionTxt.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
                optionTxt.setLayoutParams(layoutParams);
            }
            optionContent.getViewTreeObserver().removeGlobalOnLayoutListener(onGlobalLayoutListener);
        }
    };


    private String getDeatilAddress(PropertyParamHints data) {

        String detailAddress = "";
        if (!TextUtil.isEmply(data.getDistrictName()))
            detailAddress = data.getDistrictName() + "/";

        if (!TextUtil.isEmply(data.getAreaName()))
            detailAddress = detailAddress + data.getAreaName() + "/";

        if (!TextUtil.isEmply(data.getEnAddressName()))
            detailAddress = detailAddress + data.getEnAddressName();

        return detailAddress;
    }


    private String parseData(PropertyParamHints data) {
        String labelString = null;
        if (data.getAreaName().length() > 0) {
            labelString = data.getAreaName();
        }
//        else if (data.getDistrictName().length() > 0 && data.getAreaName().length() > 0) {
//            labelString = data.getDistrictName() + "\\\\\\" + data.getAreaName();
//        }
        else if (data.getDistrictName().length() > 0) {
            labelString = data.getDistrictName();
        } else if (data.getEnAddressName().length() > 0) {
//            labelString = data.getAreaName();
            labelString = data.getEnAddressName();
        }
        return labelString;
    }


    private void showSaveFinishDialog() {
        getActivity().runOnUiThread(() -> {
            DialogUtil.getSimpleDialog(getString(R.string.success_to_save)).show(getFragmentManager(), "");
        });
    }

    private List<DistrictItem> getSelectDistrict(List<DistrictItem> items, List<String> keys) {
        List<DistrictItem> districtItems = new ArrayList<>();
        if (keys == null) return null;
        for (DistrictItem item : items) {
            if (keys.indexOf(item.getDistrictKeyId()) != -1) {
                districtItems.add(item);
            }
        }
        return districtItems;
    }

    private void showSortDialog() {
        TransSortDialog sortDialog = new TransSortDialog(sortDialogSelectId);
        sortDialog.setOnDialogClikeLisenter(new SortDialog.onDialogOnclikeLisenter() {
            @Override
            public void onClike(Dialog dialog, int viewID, Map<String, Object> params) {
                dialog.dismiss();
                sortDialogSelectId = (int) params.get(SortDialog.PARAMS_SELECTID);
                request.setAscending((Boolean) params.get(SortDialog.PARAMS_ASCENDING));
                String sort = params.get(SortDialog.PARAMS_SORTFIELD) != null ? (String) params.get(SortDialog.PARAMS_SORTFIELD) : null;
                request.setSortField(sort);
                openFreshView();
            }
        });
        sortDialog.show(getFragmentManager(), "");
    }

    public void openFreshView() {
        if (refreshLayout.isRefreshing()) return;
        if (refreshLayout.isLoading()) return;
        if (!transactionList.isEmpty()) lv.post(() -> lv.setSelection(0));
        //todo 修改listView定位
        refreshLayout.autoRefresh();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == CODE_TRAN_FILTRATE) {
            request = (TransListRequest) data.getExtras().get(TRANS_PARAMS);
//            refreshLayout.autoRefresh();
            openFreshView();
            setOptionCount();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public boolean onBackPressed() {
        FragmentTransaction ft = getParentFragment().getChildFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        Fragment fragment = getParentFragment().getChildFragmentManager().findFragmentByTag(TransSearchFragment.FRAGMENT_TAG_SEARCH);
        if (fragment != null) {
            Bundle bundle1 = new Bundle();
            TransListRequest description = new TransListRequest();
            description.setDistrictListIds(request.getDistrictListIds());
            description.setFloors(request.getFloors());
            description.setUnits(request.getUnits());
            bundle1.putSerializable("HOUSE_REQUEST", description);
            fragment.setArguments(bundle1);
            ft.show(fragment);
        } else
            ft.show(getParentFragment().getChildFragmentManager().findFragmentByTag(TransHomePagerFragment.FRAGMENT_TAG_HOMEPAGER));
        ft.remove(this).commit();
        return true;
    }

    @Override
    public void refreshTransCount(int count) {
        this.count = count;

        if (getActivity() == null) return;
        getActivity().runOnUiThread(() -> {
            if (count == 0) {
                currenPositionTxt.setVisibility(View.GONE);
                DialogUtil.getSimpleDialog(getString(R.string.house_no_find)).show(getFragmentManager(), "");
                return;
            }
            currenPositionTxt.setVisibility(View.VISIBLE);
            currenPositionTxt.setText("1/" + count);
        });
    }

    @Override
    public void onFailure(String tip) {
        DialogUtil.getSimpleDialog(tip).show(getFragmentManager(), "");
        closeRefresh();
    }

    class TransListAdapter extends BaseListAdapter<Transaction> {

        public TransListAdapter(Context context, List<Transaction> datas, int layoutId) {
            super(context, datas, layoutId);
        }

        @Override
        protected View getItemView(View view, View convertView, int position, List<Transaction> datas) {

            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                viewHolder.proxyTxt = view.findViewById(R.id.detail_transact_txt_proxy);
                viewHolder.transactPriceTxt = view.findViewById(R.id.detail_transact_txt_transactprice);
                viewHolder.managerTxt = view.findViewById(R.id.detail_transact_txt_manager);
                viewHolder.createTimeTxt = view.findViewById(R.id.detail_transact_txt_createtime);
                viewHolder.appointmentDateTxt = view.findViewById(R.id.detail_transact_txt_appointmentdate);
                viewHolder.officeDateTxt = view.findViewById(R.id.detail_transact_txt_officedate);
                viewHolder.finishDateTxt = view.findViewById(R.id.detail_transact_txt_finishdate);

                viewHolder.realSizeTxt = view.findViewById(R.id.detail_transact_txt_realysize);
                viewHolder.realPriceTxt = view.findViewById(R.id.detail_transact_txt_reallyprice);
                viewHolder.useSizeTxt = view.findViewById(R.id.detail_transact_txt_usesize);
                viewHolder.usePriceTxt = view.findViewById(R.id.detail_transact_txt_useprice);

                viewHolder.bargainStxt = view.findViewById(R.id.detail_transact_stxt_bargain);
                viewHolder.rentDateToStxt = view.findViewById(R.id.detail_transact_stxt_rentdateto);
                viewHolder.stateImg = view.findViewById(R.id.detail_transact_img_state);
                viewHolder.confirmTxt = view.findViewById(R.id.trans_txt_status);
                viewHolder.statuTxt = view.findViewById(R.id.trans_txt_confirm);
                viewHolder.buildingNameTxt = view.findViewById(R.id.trans_txt_bulidingname);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }

            if (datas != null && !datas.isEmpty()) {
                Transaction data = datas.get(position);

                viewHolder.createTimeTxt.setText("建立日期 " + data.getCreateTime());
                viewHolder.proxyTxt.setText(data.getAgency());
                viewHolder.transactPriceTxt.setSelected(!data.isGreen());
                viewHolder.transactPriceTxt.setText("$ " + TextUtil.getInteger(data.getPrice()) + " " + getUnit(data.getStatus()));
                viewHolder.managerTxt.setText(data.getAgent());

                viewHolder.bargainStxt.setText(data.getTransactionDate());
                viewHolder.rentDateToStxt.setText(data.getRentEndDate());
                viewHolder.appointmentDateTxt.setText(data.getPrelimDate());
                viewHolder.officeDateTxt.setText(data.getFormalDate());

                viewHolder.finishDateTxt.setText(data.getCompleteDate());
//                viewHolder.realSizeTxt.setText(data.getBuildSquareFoot());
//                viewHolder.realPriceTxt.setText("$" + data.getGrossAveragePrice());
//                viewHolder.useSizeTxt.setText(data.getUseSquareFoot());

                viewHolder.realSizeTxt.setText(TextUtil.getInteger(data.getBuildSquareFoot()) + " " + getString(R.string.ruler));
                viewHolder.realPriceTxt.setText("$" + TextUtil.getInteger(data.getGrossAveragePrice()));
                viewHolder.useSizeTxt.setText(TextUtil.getInteger(data.getUseSquareFoot()) + " " + getString(R.string.ruler));
                viewHolder.usePriceTxt.setText("$" + TextUtil.getInteger(data.getAveragePrice()));

//                viewHolder.usePriceTxt.setText("$" + data.getAveragePrice());
                viewHolder.stateImg.setImageLevel(data.getStatus());
                viewHolder.statuTxt.setText(data.isNotConfirmed() ? "已確認" : "未確認");
                viewHolder.statuTxt.setSelected(data.isNotConfirmed());
                viewHolder.confirmTxt.setText(getStatuTxt(data.getStatus()));
                viewHolder.buildingNameTxt.setText(data.getDetailAddressChInfo());
            }

            return view;
        }

        private String getUnit(int status) {
            String unit = "";
            switch (status) {
                case 1:
                case 3:
                case 5:
                    unit = "萬";
                    break;
                case 2:
                case 4:
                    unit = "元";
                    break;
            }
            return unit;
        }

        private String getStatuTxt(int statu) {
            String statuStr = null;
            switch (statu) {
                case 1:
                    statuStr = "中原售";
                    break;
                case 2:
                    statuStr = "中原租";
                    break;
                case 3:
                    statuStr = "行家售";
                    break;
                case 4:
                    statuStr = "行家租";
                    break;
                case 5:
                    statuStr = "內部轉讓";
                    break;
            }
            return statuStr;
        }

        private class ViewHolder {
            private TextView proxyTxt, transactPriceTxt, managerTxt, buildingNameTxt;
            private TextView createTimeTxt, appointmentDateTxt, officeDateTxt, finishDateTxt;
            private TextView realSizeTxt, useSizeTxt, realPriceTxt, usePriceTxt, bargainStxt;
            private TextView rentDateToStxt;
            private ImageView stateImg;
            public TextView confirmTxt, statuTxt;
        }
    }
}
