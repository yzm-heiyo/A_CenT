package com.centanet.hk.aplus.Views.HousetListView.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
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
import com.centanet.hk.aplus.Utils.TextUtil;
import com.centanet.hk.aplus.Utils.TimeLimitUtil;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.Views.ComplexSearchView.ComplexActivity;
import com.centanet.hk.aplus.Views.ComplexSearchView.FiltrateActivity;
import com.centanet.hk.aplus.Views.Dialog.AreaDialog;
import com.centanet.hk.aplus.Views.Dialog.FlootUnitDialog;
import com.centanet.hk.aplus.Views.Dialog.PriceDialog;
import com.centanet.hk.aplus.Views.Dialog.SearchOptionDialog;
import com.centanet.hk.aplus.Views.Dialog.SimpleTipsDialog;
import com.centanet.hk.aplus.Views.Dialog.SortDialog;
import com.centanet.hk.aplus.Views.Dialog.StatusDialog;
import com.centanet.hk.aplus.Views.Dialog.UserDesignNameDialog;
import com.centanet.hk.aplus.Views.HomePageView.view.HomePagerFragment;
import com.centanet.hk.aplus.Views.HouseDetailView.view.DetailActicity;
import com.centanet.hk.aplus.Views.HouseFragment.BaseHouseFragment;
import com.centanet.hk.aplus.Views.HousetListView.present.HouseListPresenter;
import com.centanet.hk.aplus.Views.HousetListView.present.IHouseListPresenter;
import com.centanet.hk.aplus.Views.LoginView.view.LoginActivity;
import com.centanet.hk.aplus.Views.MainActivity.view.MainActivity;
import com.centanet.hk.aplus.Views.SearchView.view.SearchActivity;
import com.centanet.hk.aplus.Views.SearchView.view.SearchFragment;
import com.centanet.hk.aplus.Widgets.CircleTipsView;
import com.centanet.hk.aplus.bean.auto_estate.PropertyParamHints;
import com.centanet.hk.aplus.bean.complexSearch.Operation;
import com.centanet.hk.aplus.bean.district.DistrictItem;
import com.centanet.hk.aplus.bean.house.Properties;
import com.centanet.hk.aplus.bean.http.AHeaderDescription;
import com.centanet.hk.aplus.bean.http.FavoriteDescription;
import com.centanet.hk.aplus.bean.http.HouseDescription;
import com.centanet.hk.aplus.bean.http.UserBehaviorDescription;
import com.centanet.hk.aplus.bean.params.SystemParamItems;
import com.centanet.hk.aplus.bean.save_option.PropertyRequestSaveParams;
import com.centanet.hk.aplus.bean.userdesign_option.PropertySearchHistory;
import com.centanet.hk.aplus.common.APSystemParameterType;
import com.centanet.hk.aplus.eventbus.MessageEventBus;
import com.centanet.hk.aplus.manager.AppSystemParamsManager;
import com.centanet.hk.aplus.manager.ApplicationManager;
import com.centanet.hk.aplus.manager.PropertyRequestParamsManager;
import com.centanet.hk.aplus.manager.ScreenShotListenManager;
import com.githang.statusbar.StatusBarCompat;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.centanet.hk.aplus.Utils.net.HttpUtil.URL_CANCELFAVO;
import static com.centanet.hk.aplus.Utils.net.HttpUtil.URL_FAVORITE;
import static com.centanet.hk.aplus.Views.SearchView.view.SearchActivity.VIEW_SEARCH_FLOOT;
import static com.centanet.hk.aplus.Views.SearchView.view.SearchActivity.VIEW_SEARCH_HISTORY_SAVE;
import static com.centanet.hk.aplus.Views.SearchView.view.SearchActivity.VIEW_SEARCH_LABEL;
import static com.centanet.hk.aplus.Views.SearchView.view.SearchActivity.VIEW_SEARCH_UNIT;
import static com.centanet.hk.aplus.common.CommandField.PriceType.SALE;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.FavoState.HOUSE_FAVO;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.FavoState.HOUSE_FAVO_CANCEL;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.HouseListDataCount.HOUSELIST_COUNT;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.HouseListRemove.HOUSE_CANCE_FAVO;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.HouseNavigation.HIDDEN;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.HouseNavigation.SHOW;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.NetWorkState.NETWORK_STATE_FAIL;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.NetWorkState.NETWORK_STATE_SUCCESS;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.Permission.HOUSELIST;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.Permission.HOUSELIST_NO;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.ReFreshDataState.DATA_END;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.RefreshView.VIEW_LOAD_START;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.RefreshView.VIEW_REFRESH_START;


/**
 * Created by mzh1608258 on 2018/1/2.
 */

public class HouseFragment extends BaseHouseFragment implements IHouseListFragment, View.OnClickListener, FragmentBackHandler {

    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 321;
    public static final String FRAGMENT_TAG_HOUSELIST = "HOUSELIST";
    private final String thiz = getClass().getSimpleName();
    private View view;
    private ListView lv;
    private ItemAdapter adapter;
    private View more, sort, mic;
    private TextView search, currentPosition, optionTip;
    private List<Properties> listData;
    private List<String> searchHistory;
    private IHouseListPresenter presenter;
    private RefreshLayout refreshLayout;
    private AHeaderDescription aHeaderDescription;
    private HouseDescription bodyDescription;
    private String houseCount = "0";
    private List<Integer> staSelectList;
    public static String[] priceInterval;
    private int sortDialogSelectId;
    private int refreshType = 0;
    private String searchTip;
    private TextView flootTxt, unitTxt, optionTxt;
    private TextView areaTipTxt, saveOptionTxt;

    private View area, unitFloot;
    private View back;

    private View allOptionView;
    private CircleTipsView moreTip;

    private int position;
    private boolean isFirst = true;
    private boolean isFavo = false;
    private ScreenShotListenManager screenShotListenManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(getActivity(), Color.parseColor("#BB2E2D"), false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_property, null, false);
        if (view != null) {
            init();
            initViews();
            initListeners();
        }
        setOptionCount();
//        refreshLayout.autoRefresh();
        openFreshView();
//        hideKeyboard();
        MessageEventBus msg = new MessageEventBus();
        msg.setMsg(SHOW);
        EventBus.getDefault().post(msg);
        return view;
    }

    private void reCoverView(HouseDescription description) {
        List<String> districtList = description.getDistrictListIds();
        if (districtList != null && !districtList.isEmpty()) {
            areaTipTxt.setVisibility(View.VISIBLE);
            areaTipTxt.setText(districtList.size() + "");
        }
//        if(houseCount)
        if (description.getFloors() != null && !description.getFloors().equals(""))
            flootTxt.setText(description.getFloors());
        if (description.getUnits() != null && !description.getUnits().equals("")) {
            unitTxt.setText(description.getUnits());
        }
    }

    private void initViews() {
        optionTip = view.findViewById(R.id.house_txt_option_tip);
        optionTxt = view.findViewById(R.id.list_txt_proselter);
        saveOptionTxt = view.findViewById(R.id.list_txt_save);
        moreTip = view.findViewById(R.id.more_tip);
        optionContent = view.findViewById(R.id.property_rl_option_content);


        lv = view.findViewById(R.id.fragment_presmises_listview);
        lv.setAdapter(adapter);

        back = view.findViewById(R.id.back);
        if (isFavo) back.setVisibility(View.GONE);
        back.setOnClickListener(v -> {
            getActivity().onBackPressed();
        });

        if (isFavo) {
            LinearLayout layout = view.findViewById(R.id.house_ll_editcontent);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) layout.getLayoutParams();
            params.setMargins(DensityUtil.dip2px(getContext(), 15), DensityUtil.dip2px(getContext(), 6), 0, DensityUtil.dip2px(getContext(), 6));
            layout.setLayoutParams(params);
        }

        more = view.findViewById(R.id.list_img_moreoption);
        sort = view.findViewById(R.id.list_img_sort);
        search = view.findViewById(R.id.list_txt_search);
        if (searchTip != null && !searchTip.equals("")) search.setText(searchTip);

        area = view.findViewById(R.id.house_view_area);
        area.setOnClickListener(v -> {
            AreaDialog areaDialog = new AreaDialog();
            areaDialog.setItem(ApplicationManager.getDistrictItems(), bodyDescription.getDistrictListIds());
            areaDialog.setOnItemClickLisenter((dialog, v1, list) -> {
                if (list != null && !list.isEmpty()) {
                    areaTipTxt.setVisibility(View.VISIBLE);
                    areaTipTxt.setText(list.size() + "");
                } else areaTipTxt.setVisibility(View.GONE);
                bodyDescription.setDistrictListIds(list);
                PropertyRequestParamsManager.getParams().setArea(getSelectDistrict(ApplicationManager.getDistrictItems(), list));
                refreshLayout.autoRefresh();
                setOptionCount();

            });
            areaDialog.show(getFragmentManager(), "");
        });
        unitFloot = view.findViewById(R.id.list_ll_floot);
        unitFloot.setOnClickListener(v -> {
            String[] str = new String[2];
            str[0] = bodyDescription.getFloors();
            str[1] = bodyDescription.getUnits();
            FlootUnitDialog dialog = new FlootUnitDialog();
            dialog.setData(str);
            dialog.setOnItemClickLisenter((dialog1, v1, list) -> {
                if (list[0] == null || list[0].equals("")) {
                    flootTxt.setText(R.string.search_floot);
                } else flootTxt.setText(list[0]);

                if (list[1] == null || list[1].equals("")) {
                    unitTxt.setText(R.string.search_units);
                } else unitTxt.setText(list[1]);

                bodyDescription.setFloors(list[0]);
                bodyDescription.setUnits(list[1]);
                refreshLayout.autoRefresh();
                setOptionCount();

            });
            dialog.show(getFragmentManager(), "");
        });
        mic = view.findViewById(R.id.mic);

        flootTxt = view.findViewById(R.id.floot);
        unitTxt = view.findViewById(R.id.unit);

        currentPosition = view.findViewById(R.id.fragment_txt_currentpos);

        refreshLayout = view.findViewById(R.id.smartLayout);
        areaTipTxt = view.findViewById(R.id.house_txt_areatips);

        allOptionView = view.findViewById(R.id.house_rl_alloption);

        refreshLayout.setEnableLoadmore(false);

        reCoverView(bodyDescription);
    }

    public void setBodyDescription(HouseDescription description) {
        this.bodyDescription = description;
        reCoverView(bodyDescription);
    }

    private void init() {

        screenShotListenManager = ScreenShotListenManager.newInstance(getActivity());


        MyApplication application = (MyApplication) getActivity().getApplicationContext();
        aHeaderDescription = application.getHeaderDescription();

        if (getArguments() != null) {
            bodyDescription = (HouseDescription) getArguments().get("HOUSE_REQUEST");
            L.d("House_PropertyTypes", bodyDescription.toString());
            isFavo = getArguments().getBoolean("FAVO");
            searchTip = getArguments().getString(VIEW_SEARCH_LABEL);
//            search.setText(getArguments().getString(VIEW_SEARCH_LABEL));
            L.d("HouseLabel", searchTip);
        }

        if (bodyDescription == null)
            bodyDescription = new HouseDescription();

        if (isFavo)
            bodyDescription.setPropertyType(5);

        listData = new ArrayList<>();
        staSelectList = new ArrayList<>();
        searchHistory = new ArrayList<>();
        priceInterval = new String[2];
        priceInterval[0] = "";
        priceInterval[1] = "";
        adapter = new ItemAdapter(getActivity(), listData);
        adapter.setOnItemClickListener((View v, int position) -> {
            HouseFragment.this.position = position;
            showFavoriteDialog(v, position);
        });
        presenter = new HouseListPresenter(this);


        ClassicsFooter.REFRESH_FOOTER_LOADING = getString(R.string.loading_more);
        ClassicsHeader.REFRESH_HEADER_PULLDOWN = getString(R.string.pull_down_loading);
        ClassicsHeader.REFRESH_HEADER_RELEASE = getString(R.string.pull_down_release);
        ClassicsHeader.REFRESH_HEADER_REFRESHING = getString(R.string.updateing);
        ClassicsHeader.REFRESH_HEADER_FINISH = getString(R.string.update_finish);
        ClassicsFooter.REFRESH_FOOTER_PULLUP = getString(R.string.pull_up_loading);
        ClassicsFooter.REFRESH_FOOTER_RELEASE = getString(R.string.pull_down_release);
        ClassicsFooter.REFRESH_FOOTER_REFRESHING = getString(R.string.updateing);
        ClassicsFooter.REFRESH_FOOTER_LOADING = getString(R.string.updateing);
        ClassicsFooter.REFRESH_FOOTER_FINISH = getString(R.string.update_finish);

        startScreenListen();
    }

    @Override
    public void onResume() {
        super.onResume();
        L.d("View_State", "House_Onresume");
        EventBus.getDefault().register(this);
//        startScreenListen();
//        hideKeyboard();
    }


    @Override
    public void onStop() {
        super.onStop();
        screenShotListenManager.stopListen();
        L.d("View_State", "House_Onstop");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initListeners() {

        screenShotListenManager.setListener((imagePath) -> onScreenShot());


        sort.setOnClickListener(this);
        more.setOnClickListener(this);
        search.setOnClickListener(this);

        saveOptionTxt.setOnClickListener(this);

        mic.setOnClickListener(this);
        allOptionView.setOnClickListener(this);

        lv.getViewTreeObserver().addOnScrollChangedListener(() -> {
            if (houseCount.equals("0")) {
                currentPosition.setText("0/0");
            } else {

                int item = lv.getFirstVisiblePosition() + 1;
                if (lv.getChildAt(0) != null) {
                    int y = lv.getChildAt(0).getTop();
                    if (y < -144) item++;
                }
                currentPosition.setText(item + "/" + houseCount);
            }
        });

        refreshLayout.setOnRefreshListener((refreshlayout) -> {
            presenter.clearFlag();
            refreshType = 0;
            bodyDescription.setPageIndex(1);
            presenter.doPost(HttpUtil.URL_PATH, aHeaderDescription, bodyDescription);
            refreshlayout.setEnableLoadmore(true);
        });

        refreshLayout.setOnLoadmoreListener((refreshlayout) -> {
            if (listData.size() == Integer.parseInt(houseCount)) {
                refreshLayout.setEnableLoadmore(false);
                refreshlayout.finishLoadmore(2000);
                showEndTips();
                return;
            }
            refreshType = 1;
            bodyDescription.setPageIndex(listData.size() / 15 + 1);
            presenter.doPost(HttpUtil.URL_PATH, aHeaderDescription, bodyDescription);
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (refreshLayout.isRefreshing() || refreshLayout.isLoading()) return;
                Intent intent = new Intent(getActivity(), DetailActicity.class);
                intent.putExtra("keyId", listData.get(position).getKeyId());
                intent.putExtra("index", position);
                intent.putExtra("propertyCount", houseCount);
                intent.putExtra("current", 1);
                startActivity(intent);
            }
        });
    }

    private void showEndTips() {
        Toast.makeText(getActivity(), getString(R.string.no_more_data), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.list_txt_search:
                turnToSearch();
                break;
            case R.id.list_img_moreoption:

                Bundle bundle = new Bundle();
                bundle.putSerializable(FiltrateActivity.HOUSE_PARAMS, bodyDescription);
                Intent in = new Intent(getContext(), FiltrateActivity.class);
                in.putExtras(bundle);
                startActivityForResult(in, 0);
                break;

            case R.id.list_img_sort:
                showSortDialog();
                break;

            case R.id.mic:

                FragmentTransaction ft1 = getParentFragment().getChildFragmentManager().beginTransaction();
                ft1.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                Fragment fragment1 = getParentFragment().getChildFragmentManager().findFragmentByTag(SearchFragment.FRAGMENT_TAG_SEARCH);
                if (fragment1 == null) {
                    fragment1 = new SearchFragment();
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable("HOUSE_REQUEST", bodyDescription);
                    bundle1.putBoolean("mic", true);
                    fragment1.setArguments(bundle1);
                    ft1.hide(this);
                    ft1.add(R.id.fragment_content, fragment1, SearchFragment.FRAGMENT_TAG_SEARCH).commit();
                } else {
                    Bundle bundle1 = new Bundle();
                    bundle1.putBoolean("mic", true);
                    bundle1.putSerializable("HOUSE_REQUEST", bodyDescription);
                    fragment1.setArguments(bundle1);
                    ft1.show(fragment1);
                    ft1.hide(this).commit();
                }

                break;
            case R.id.house_rl_alloption:

                SearchOptionDialog optionDialog = new SearchOptionDialog();
                optionDialog.setData(bodyDescription);
                optionDialog.setOnItemClickLisenter((dialog, v1, description) -> {
                    dialog.dismiss();
                    bodyDescription = description;
                    if (TextUtil.isEmply(description.getSortField())) {
                        sortDialogSelectId = R.id.sort_rb_default;
                    }
                    setOptionCount();
                    openFreshView();
                });
                optionDialog.show(getFragmentManager(), "");
                break;
            case R.id.list_txt_save:
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

    private void turnToSearch() {

        FragmentTransaction ft = getParentFragment().getChildFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        Fragment fragment = getParentFragment().getChildFragmentManager().findFragmentByTag(SearchFragment.FRAGMENT_TAG_SEARCH);
        Bundle bundle2 = new Bundle();
        bundle2.putSerializable("HOUSE_REQUEST", bodyDescription);
        if (fragment == null) {
            fragment = new SearchFragment();
            fragment.setArguments(bundle2);
            ft.hide(this);
            ft.add(R.id.fragment_content, fragment, SearchFragment.FRAGMENT_TAG_SEARCH).commit();
        } else {
            fragment.setArguments(bundle2);
            ft.show(fragment);
            ft.hide(this).commit();
        }

    }

    private void saveOption(String name) {
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

                PropertySearchHistory history = new PropertySearchHistory(bodyDescription, PropertyRequestParamsManager.getParams());
                history.setOptionMame(name);
                searchHistories.add(history);
                gson = new Gson().toJson(searchHistories);
                L.d("SearchHis_Exists", "HisSize: " + searchHistories.size() + " HisStr" + searchHistories.toString());
            } else {
                List<PropertySearchHistory> searchHistories = new ArrayList<>();
                PropertySearchHistory history = new PropertySearchHistory(bodyDescription, PropertyRequestParamsManager.getParams());
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
            DialogUtil.getSimpleDialog(getString(R.string.success_to_save)).show(getFragmentManager(), "");
        });
    }

    private void startScreenListen() {
        screenShotListenManager.startListen();
    }

    private void onScreenShot() {

        L.d("house_fragment_screen","house_fragment_screen");
//        if (TimeLimitUtil.isAchieveLimitTime(1000)) return;
        if(!presenter.isAbleToScreen())return;

        if (getActivity() == null) return;
        if (((MainActivity) getActivity()).getCurrentItem() != 0) return;

        L.d("onScreenShot_house", "Hidden:" + isHidden() + " Resume:" + isResumed() + " isVisible:" + isVisible);
        if (isVisible || isHidden() || !isResumed()) return;
        L.d(thiz, "HouseShot: " + " FirstVisiblePosition: " + lv.getFirstVisiblePosition() + " LastVisiblePosition: " + lv.getLastVisiblePosition());
        presenter.setAbleToScreen(false);
        UserBehaviorDescription userBehaviorDescription = new UserBehaviorDescription();
        userBehaviorDescription.setAction(1);
        userBehaviorDescription.setPage(4);
        userBehaviorDescription.setExtras(getExtras());
        presenter.doPost(HttpUtil.URL_USER_BEHAVIOR, aHeaderDescription, userBehaviorDescription);
    }

    private String getExtras() {
        if (lv.getCount() == 0) return null;
        int firstVisiblePosition = lv.getFirstVisiblePosition();
        int lastVisiblePosition = lv.getLastVisiblePosition();

        String extras = "{" + "\"PropertyKeyId\"" + ":" + "[";
        for (int i = firstVisiblePosition; i <= lastVisiblePosition; i++) {
            extras = extras + "\"" + listData.get(i).getKeyId() + "\"" + ",";
        }
        extras = extras.substring(0, extras.length() - 1);
        extras = extras + "]}";
        L.d(thiz, "onShot: " + extras);
        return extras;
    }

    private void showSortDialog() {
        SortDialog sortDialog = new SortDialog(sortDialogSelectId);
        sortDialog.setOnDialogClikeLisenter(new SortDialog.onDialogOnclikeLisenter() {
            @Override
            public void onClike(Dialog dialog, int viewID, Map<String, Object> params) {
                dialog.dismiss();
                sortDialogSelectId = (int) params.get(SortDialog.PARAMS_SELECTID);
                bodyDescription.setAscending((Boolean) params.get(SortDialog.PARAMS_ASCENDING));
                String sort = params.get(SortDialog.PARAMS_SORTFIELD) != null ? (String) params.get(SortDialog.PARAMS_SORTFIELD) : null;
                bodyDescription.setSortField(sort);
                openFreshView();
            }
        });
        if (bodyDescription.getHasSalePricePremiumUnpaid() || bodyDescription.isShowSalePricePremiumUnpaid())
            sortDialog.isShowGreenPrice(true);
        else sortDialog.isShowGreenPrice(false);
        sortDialog.show(getFragmentManager(), "");
    }

    private void showFavoriteDialog(View v, final int position) {

        View view = View.inflate(getContext(), R.layout.item_dialog_double, null);

        SimpleTipsDialog dialog = new SimpleTipsDialog(view, 0.72, 0.21, R.id.dialog_content_txt);
        final Properties data = listData.get(position);
        final FavoriteDescription favoriteDescription = new FavoriteDescription();
        final String address = data.isFavoriteFlag() == true ? URL_CANCELFAVO : URL_FAVORITE;
        if (!data.isFavoriteFlag()) {
            dialog.setTipString("加入收藏");
            dialog.setRightButtonText(getString(R.string.add));
        } else
            dialog.setRightButtonText(getString(R.string.remove));
        favoriteDescription.setKeyId(data.getKeyId());
        dialog.setOnItemclickListener((dialog1, type) -> {
            if (type == SimpleTipsDialog.DIALOG_YES) {
                presenter.doPost(address, aHeaderDescription, favoriteDescription);
            }
        });

        Properties properties = listData.get(position);
        String floor = properties.getFloor() == null || properties.getFloor().equals("") ? "" : properties.getFloor();
        dialog.setContentString(properties.getEstateName() + " " + properties.getBuildingName() + " " + floor + " " + (properties.getHouseNo() == null ? "" : properties.getHouseNo() + "室"));
        dialog.show(getFragmentManager(), "");
    }

    @Override
    public void refreshListView() {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadmore();
        adapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEventBus messageEvent) {
        switch (messageEvent.getMsg()) {
            case NETWORK_STATE_FAIL:
                closeRefresh();
                onFailure();
                break;
            case NETWORK_STATE_SUCCESS:
                refreshListView();
                break;
            case VIEW_REFRESH_START:
                openFreshView();
                break;
            case VIEW_LOAD_START:
                openLoadView();
                break;
            case HOUSELIST_COUNT:
                if (messageEvent != null)
                    setCountTxt(messageEvent);
                break;
            case HOUSELIST:
                lv.setVisibility(View.VISIBLE);
                break;
            case HOUSELIST_NO:
                showNoPermissionDialog();
                break;
            case DATA_END:
                refreshLayout.setEnableLoadmore(false);
                showEndTips();
                break;
            case HOUSE_CANCE_FAVO:
                String key = (String) messageEvent.getObject();
                int index = isExise(key);
                if (index != -1)
                    adapter.updateView(index, lv, false);
                break;
        }
        closeRefresh();
    }

    private int isExise(String s) {
        if (listData != null && !listData.isEmpty()) {
            for (int i = 0; i < listData.size(); i++) {
                if (listData.get(i).getKeyId().equals(s))
                    return i;
            }
        }
        return -1;
    }

    private void setCountTxt(MessageEventBus messageEvent) {
        houseCount = (String) messageEvent.getObject();
        if (houseCount.equals("0")) {
            DialogUtil.getSimpleDialog(getString(R.string.house_no_find)).show(getFragmentManager(), "");
            currentPosition.setVisibility(View.GONE);
            return;
        }
        currentPosition.setVisibility(View.VISIBLE);
    }

    private void closeRefresh() {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadmore();
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (listData != null) listData.clear();
        listData = null;
    }

    @Override
    public void refreshListData(List<Properties> properties) {

        if (refreshType == 0) {
            if (listData != null) {
                listData.clear();
                listData.addAll(properties);
            }
        } else if (refreshType == 1) {
            listData.addAll(properties);
        }
    }

    @Override
    public void openFreshView() {
        if (refreshLayout.isRefreshing()) return;
        if (refreshLayout.isLoading()) return;
        if (!listData.isEmpty()) lv.post(() -> lv.setSelection(0));
        //todo 修改listView定位
        L.d("getHasSalePricePremiumUnpaid", bodyDescription.getHasSalePricePremiumUnpaid() + "  " + bodyDescription.isShowSalePricePremiumUnpaid());
        if (bodyDescription.getHasSalePricePremiumUnpaid() || bodyDescription.isShowSalePricePremiumUnpaid())
            adapter.setShowGreenTabView(true);
        else adapter.setShowGreenTabView(false);
        refreshLayout.autoRefresh();
    }

    @Override
    public void openLoadView() {
        if (refreshLayout.isLoading()) return;
        refreshLayout.autoLoadmore();
    }

    @Override
    public void onFailure() {
        if (!isVisible) return;
        SimpleTipsDialog tipsDialog = new SimpleTipsDialog();
        tipsDialog.setContentString(getString(R.string.network_unenable));
        tipsDialog.setLeftBtnVisibility(false);
        tipsDialog.show(getFragmentManager(), "");
    }

    @Override
    public void setCancelFavo() {
        changeFavoStatu(false);
        DialogUtil.getSimpleDialog("已取消收藏").show(getFragmentManager(), "");
        Properties house = listData.get(position);
        EventBus.getDefault().post(new MessageEventBus(HOUSE_FAVO_CANCEL, house.getKeyId()));
        listData.get(position).setFavoriteFlag(false);
    }

    @Override
    public void setFavo() {
        changeFavoStatu(true);
        DialogUtil.getSimpleDialog(" 成功加入收藏").show(getFragmentManager(), "");
        EventBus.getDefault().post(new MessageEventBus(HOUSE_FAVO, listData.get(position)));
        listData.get(position).setFavoriteFlag(true);
    }

    private void changeFavoStatu(final boolean b) {
        getActivity().runOnUiThread(() -> adapter.updateView(position, lv, b));
    }

    @Override
    public void toLogin() {
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    @Override
    public void showNoPermissionDialog() {
        SimpleTipsDialog simpleTipsDialog = new SimpleTipsDialog();
        simpleTipsDialog.setContentString(getString(R.string.dialog_tips_permission_no_houselist));
        simpleTipsDialog.setLeftBtnVisibility(false);
        closeRefresh();
        if (!isVisible) return;
        simpleTipsDialog.show(getFragmentManager(), "");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        L.d("result", "" + resultCode);
        switch (resultCode) {
            case 0:
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    String condition = bundle.getString(SearchActivity.VIEW_SEARCH_KEY_TYPE);
                    if (condition != null) {
                        searchHistory.clear();
                        for (String address : condition.split("/")) {
                            searchHistory.add(address.split(":")[1]);
                        }
                    }
                }
                break;
            case 1:
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    List<String> addressList = new ArrayList<>();
                    String condition = bundle.getString(SearchActivity.VIEW_SEARCH_KEY_TYPE);
                    L.d("SearchAdress", condition);
                    if (condition != null) {
                        searchHistory.clear();
                        for (String address : condition.split("/")) {
                            searchHistory.add(address.split(":")[1]);
                            addressList.add(address);
                            L.d("SearchAdress", addressList.toString());
                        }
                        bodyDescription.setSearcherAddress(addressList);
                    }
                    String label = bundle.getString(VIEW_SEARCH_LABEL);
                    if (label != null) search.setText(label);
                    bodyDescription.setFloors(bundle.getString(VIEW_SEARCH_FLOOT));
                    bodyDescription.setUnits(bundle.getString(VIEW_SEARCH_UNIT));
                    openFreshView();
                } else {
                    search.setText(null);
                    searchHistory.clear();
                    bodyDescription.setSearcherAddress(null);
                    bodyDescription.setFloors(null);
                    bodyDescription.setUnits(null);
                    openFreshView();
                }
                setOptionCount();
                break;

            case FiltrateActivity.CODE_FILTRATE:
                Bundle bundle = data.getExtras();
                bodyDescription = (HouseDescription) bundle.get(FiltrateActivity.HOUSE_PARAMS);
                try {
//                    Toast.makeText(getContext(), ItemCountUtil.count(bodyDescription) + "", Toast.LENGTH_SHORT).show();
                    optionTip.setVisibility(View.VISIBLE);
                    optionTip.setText(ItemCountUtil.count(bodyDescription) + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                setOptionCount();
                L.d("OnActivityBack1", bodyDescription.toString());
//                refreshLayout.autoRefresh();
                openFreshView();
                break;
        }
    }

    private void setOptionCount() {
        try {

            int count = ItemCountUtil.count(bodyDescription);

            if (bodyDescription.getSquareFrom() != null && bodyDescription.getSquareTo() != null) {
                count--;
            }

            if (bodyDescription.getCompleteYearFrom() != null && bodyDescription.getCompleteYearTo() != null) {
                count--;
            }

            if (bodyDescription.getPriceUnitFrom() != null && bodyDescription.getPriceUnitTo() != null) {
                count--;
            }

            if (bodyDescription.getSalePriceFrom() != null && bodyDescription.getSalePriceTo() != null) {
                count--;
            }

            if (bodyDescription.getRentPriceFrom() != null && bodyDescription.getRentPriceTo() != null) {
                count--;
            }

            if (bodyDescription.getPropertyDateFrom() != null && bodyDescription.getPropertyDateTo() != null) {
                count--;
            }

            if (bodyDescription.getSquareUseFrom() != null && bodyDescription.getSquareUseTo() != null) {
                count--;
            }

            if (!TextUtil.isEmply(bodyDescription.getSortField())) {
                count--;
            }

            if (bodyDescription.getDistrictListIds() != null)
                count = count - bodyDescription.getDistrictListIds().size();
            if (bodyDescription.getSearcherAddress() != null)
                count = count - bodyDescription.getSearcherAddress().size();
            if (bodyDescription.getFloors() != null && !bodyDescription.getFloors().equals(""))
                count--;
            if (bodyDescription.getUnits() != null && !bodyDescription.getUnits().equals(""))
                count--;

            if (bodyDescription.getBuildingUseTypes() != null)
                count = count - bodyDescription.getBuildingUseTypes().size();

            moreTip.setText(count + "");
            if (count <= 0) {
                moreTip.setVisibility(View.GONE);
            } else {
                moreTip.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        L.d("getSearcherAddress",bodyDescription.getSearcherAddress().toString());
        if (TextUtil.isEmply(bodyDescription.getSearcherAddress())) {
            optionTxt.setText(R.string.dialog_price_unlimit);
            search.setText(null);
            optionTip.setVisibility(View.GONE);
//            L.d("",);
        } else {
//            optionTxt.setText(parseData(PropertyRequestParamsManager.getParams().getAddress().get(0)));
            optionTip.setVisibility(View.VISIBLE);
            optionTip.setText(PropertyRequestParamsManager.getParams().getAddress().size() + "");
            String addStr = "";
            for (PropertyParamHints h : PropertyRequestParamsManager.getParams().getAddress()) {
                addStr = addStr + getDeatilAddress(h) + ",";
            }
            optionTxt.setText(addStr);

            optionContent.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
            optionContent.measure(0, 0);
//            int width = optionContent.getWidth();
//            L.d("optionContent_Width",width+"");
//            if (optionTxt.getWidth() + DensityUtil.dip2px(getContext(), 10) > width) {
////            search.setText(addStr.substring(0, addStr.length() - 1));
//                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width - DensityUtil.dip2px(getContext(), 50), ViewGroup.LayoutParams.WRAP_CONTENT);
//                layoutParams.setMargins(DensityUtil.dip2px(getContext(), 12), 0, 0, 0);
//                layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
//                optionTxt.setSingleLine();
//                optionTxt.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
//                optionTxt.setLayoutParams(layoutParams);
//            }
        }
    }

    private View optionContent;
    private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {

        @Override
        public void onGlobalLayout() {
            int width = optionContent.getWidth();
            L.d("optionContent_Width", width + "");
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
        } else if (data.getDistrictName().length() > 0 && data.getAreaName().length() > 0) {
            labelString = data.getDistrictName() + "\\\\\\" + data.getAreaName();
        } else if (data.getDistrictName().length() > 0) {
            labelString = data.getDistrictName();
        } else if (data.getEnAddressName().length() > 0) {
//            labelString = data.getAreaName();
            labelString = data.getEnAddressName();
        }
        return labelString;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
//        hideKeyboard();
    }

//    private void hideKeyboard() {
//        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//        if (imm.isActive() && getActivity().getCurrentFocus() != null) {
//            if (getActivity().getCurrentFocus().getWindowToken() != null) {
//                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//            }
//        }
//    }

    @Override
    public boolean onBackPressed() {
        FragmentTransaction ft = getParentFragment().getChildFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        Fragment fragment = getParentFragment().getChildFragmentManager().findFragmentByTag(SearchFragment.FRAGMENT_TAG_SEARCH);
        if (fragment != null) {
            Bundle bundle1 = new Bundle();
            HouseDescription description = new HouseDescription();
            description.setDistrictListIds(bodyDescription.getDistrictListIds());
            description.setFloors(bodyDescription.getFloors());
            description.setUnits(bodyDescription.getUnits());
            bundle1.putSerializable("HOUSE_REQUEST", description);
            fragment.setArguments(bundle1);
            ft.show(fragment);
        } else {
            PropertyRequestParamsManager.setParams(new PropertyRequestSaveParams());
            ft.show(getParentFragment().getChildFragmentManager().findFragmentByTag(HomePagerFragment.FRAGMENT_TAG_HOMEPAGER));
        }
        ft.remove(this).commit();
        return true;
    }
}
