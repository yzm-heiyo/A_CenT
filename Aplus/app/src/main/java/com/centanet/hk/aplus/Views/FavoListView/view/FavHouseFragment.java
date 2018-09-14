package com.centanet.hk.aplus.Views.FavoListView.view;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.centanet.hk.aplus.MyApplication;
import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.DensityUtil;
import com.centanet.hk.aplus.Utils.DialogUtil;
import com.centanet.hk.aplus.Utils.ItemCountUtil;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.TextUtil;
import com.centanet.hk.aplus.Utils.TimeLimitUtil;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.Views.ComplexSearchView.ComplexActivity;
import com.centanet.hk.aplus.Views.ComplexSearchView.FiltrateActivity;
import com.centanet.hk.aplus.Views.Dialog.AreaDialog;
import com.centanet.hk.aplus.Views.Dialog.FavoSearchOptionDialog;
import com.centanet.hk.aplus.Views.Dialog.FlootUnitDialog;
import com.centanet.hk.aplus.Views.Dialog.PriceDialog;
import com.centanet.hk.aplus.Views.Dialog.SearchOptionDialog;
import com.centanet.hk.aplus.Views.Dialog.SimpleTipsDialog;
import com.centanet.hk.aplus.Views.Dialog.SortDialog;
import com.centanet.hk.aplus.Views.Dialog.StatusDialog;
import com.centanet.hk.aplus.Views.FavoListView.present.HouseListPresenter;
import com.centanet.hk.aplus.Views.FavoListView.present.IHouseListPresenter;
import com.centanet.hk.aplus.Views.HouseDetailView.view.DetailActicity;
import com.centanet.hk.aplus.Views.HouseFragment.BaseHouseFragment;
import com.centanet.hk.aplus.Views.LoginView.view.LoginActivity;
import com.centanet.hk.aplus.Views.SearchView.view.SearchActivity;
import com.centanet.hk.aplus.Widgets.CircleTipsView;
import com.centanet.hk.aplus.bean.auto_estate.PropertyParamHints;
import com.centanet.hk.aplus.bean.district.DistrictItem;
import com.centanet.hk.aplus.bean.house.Properties;
import com.centanet.hk.aplus.bean.http.AHeaderDescription;
import com.centanet.hk.aplus.bean.http.FavoriteDescription;
import com.centanet.hk.aplus.bean.http.HouseDescription;
import com.centanet.hk.aplus.bean.http.UserBehaviorDescription;
import com.centanet.hk.aplus.eventbus.MessageEventBus;
import com.centanet.hk.aplus.manager.ApplicationManager;
import com.centanet.hk.aplus.manager.FavoRequestParamsManager;
import com.centanet.hk.aplus.manager.PropertyRequestParamsManager;
import com.centanet.hk.aplus.manager.ScreenShotListenManager;
import com.githang.statusbar.StatusBarCompat;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.centanet.hk.aplus.Utils.net.HttpUtil.URL_CANCELFAVO;
import static com.centanet.hk.aplus.Utils.net.HttpUtil.URL_FAVORITE;
import static com.centanet.hk.aplus.Views.SearchView.view.SearchActivity.VIEW_SEARCH_AREA;
import static com.centanet.hk.aplus.Views.SearchView.view.SearchActivity.VIEW_SEARCH_FLOOT;
import static com.centanet.hk.aplus.Views.SearchView.view.SearchActivity.VIEW_SEARCH_HISTORY_SAVE;
import static com.centanet.hk.aplus.Views.SearchView.view.SearchActivity.VIEW_SEARCH_LABEL;
import static com.centanet.hk.aplus.Views.SearchView.view.SearchActivity.VIEW_SEARCH_UNIT;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.FavoState.FAVO_FAVO_CANCEL;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.FavoState.HOUSE_FAVO;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.FavoState.HOUSE_FAVO_CANCEL;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.HouseListDataCount.FAVOLIST_COUNT;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.HouseListRemove.HOUSE_CANCE_FAVO;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.NetWorkState.NETWORK_STATE_FAIL;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.NetWorkState.NETWORK_STATE_SUCCESS;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.Permission.HOUSELIST;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.Permission.HOUSELIST_NO;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.ReFreshDataState.DATA_FAVO_END;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.RefreshView.VIEW_LOAD_START;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.RefreshView.VIEW_REFRESH_START;


/**
 * Created by mzh1608258 on 2018/1/2.
 */

public class FavHouseFragment extends BaseHouseFragment implements IFavorieFragment, View.OnClickListener {

    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 321;
    private final String thiz = getClass().getSimpleName();
    private View view;
    private ListView lv;
    private ItemAdapter adapter;
    private View sort, mic;
    private TextView search, currentPosition;
    private List<Properties> listFavo;
    private List<String> searchHistory;
    private IHouseListPresenter presenter;
    private RefreshLayout refreshLayout;
    private AHeaderDescription aHeaderDescription;
    private HouseDescription bodyDescription;
    private String houseCount = "0";
    private ImageView more;
    private boolean isFavorite = false;

    private int sortDialogSelectId;
    private TextView areaTxt, flootTxt, unitTxt, areaTipTxt, optionTip, optionTxt;
    private View flootUnitView;
    private int position;
    private int refreshType = 0;

    private ScreenShotListenManager screenShotListenManager;
    private CircleTipsView moreTip;
    private View allOptionView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(getActivity(), Color.parseColor("#BB2E2D"), false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favo, null, false);
        if (view != null) {
            initViews();
            init();
            initListeners();
            bodyDescription.setPropertyType(5);
            refreshLayout.autoRefresh();
        }
        return view;
    }

    private void initViews() {
        lv = view.findViewById(R.id.fragment_presmises_listview);

//        optionContent = view.findViewById(R.id.property_rl_option_content);
        sort = view.findViewById(R.id.list_img_sort);
        search = view.findViewById(R.id.list_txt_search);
        currentPosition = view.findViewById(R.id.fragment_txt_currentpos);
        refreshLayout = view.findViewById(R.id.smartLayout);
        more = view.findViewById(R.id.list_img_moreoption);
        flootTxt = view.findViewById(R.id.floot);
        unitTxt = view.findViewById(R.id.unit);
        areaTipTxt = view.findViewById(R.id.house_txt_areatips);
        areaTxt = view.findViewById(R.id.list_txt_area);
        flootUnitView = view.findViewById(R.id.list_ll_floot);
        allOptionView = view.findViewById(R.id.house_rl_alloption);
        moreTip = view.findViewById(R.id.more_tip);

        optionTip = view.findViewById(R.id.house_txt_option_tip);
        optionTxt = view.findViewById(R.id.list_txt_proselter);

        mic = view.findViewById(R.id.mic);
        mic.setOnClickListener(this);
        refreshLayout.setEnableLoadmore(false);
    }

    private void init() {
        MyApplication application = (MyApplication) getActivity().getApplicationContext();
        aHeaderDescription = application.getHeaderDescription();
        bodyDescription = new HouseDescription();
        listFavo = new ArrayList<>();

        searchHistory = new ArrayList<>();

        adapter = new ItemAdapter(getActivity(), listFavo);
        adapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                showFavoriteDialog(v, position);
                FavHouseFragment.this.position = position;
            }
        });
        lv.setAdapter(adapter);
        presenter = new HouseListPresenter(this);
        EventBus.getDefault().register(this);

        startScreenListen();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initListeners() {

        sort.setOnClickListener(this);
        search.setOnClickListener(this);
        more.setOnClickListener(this);
        areaTxt.setOnClickListener(this);
        flootUnitView.setOnClickListener(this);
        allOptionView.setOnClickListener(this);

        lv.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
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
            }
        });

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshType = 0;
                presenter.clearFlag();
                bodyDescription.setPageIndex(1);
                refreshLayout.setEnableLoadmore(true);
                presenter.doPost(HttpUtil.URL_PATH, aHeaderDescription, bodyDescription);
            }
        });

        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshType = 1;
                if (listFavo.size() == Integer.parseInt(houseCount)) {
                    refreshLayout.setEnableLoadmore(false);
                    refreshlayout.finishLoadmore(2000);
                    showEndTips();
                    return;
                }
                bodyDescription.setPageIndex(listFavo.size() / 15 + 1);
                presenter.doPost(HttpUtil.URL_PATH, aHeaderDescription, bodyDescription);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (refreshLayout.isRefreshing() || refreshLayout.isLoading()) return;
                Intent intent = new Intent(getActivity(), DetailActicity.class);
//                intent.putExtra("keyId", listFavo.get(position).getKeyId());
                intent.putExtra("keyId", listFavo.get(position).getKeyId());
                intent.putExtra("index", position);
                intent.putExtra("current", 5);
                intent.putExtra("propertyCount", houseCount);
                startActivity(intent);
            }
        });
    }

    private void startScreenListen() {
        screenShotListenManager = ScreenShotListenManager.newInstance(getActivity());
        screenShotListenManager.setListener((imagePath) -> onScreenShot());
        screenShotListenManager.startListen();
    }


    private void onScreenShot() {
//        if (!TimeLimitUtil.isAchieveLimitTime(1000)) return;
        if (!isVisible || !isResume) return;
        L.d(thiz, "HouseShot: " + " FirstVisiblePosition: " + lv.getFirstVisiblePosition() + " LastVisiblePosition: " + lv.getLastVisiblePosition());
        UserBehaviorDescription userBehaviorDescription = new UserBehaviorDescription();
        userBehaviorDescription.setAction(1);
        userBehaviorDescription.setPage(5);
        userBehaviorDescription.setExtras(getExtras());
        presenter.doPost(HttpUtil.URL_USER_BEHAVIOR, aHeaderDescription, userBehaviorDescription);
    }

    private String getExtras() {
        if (lv.getCount() == 0) return null;
        int firstVisiblePosition = lv.getFirstVisiblePosition();
        int lastVisiblePosition = lv.getLastVisiblePosition();

        String extras = "{" + "\"PropertyKeyId\"" + ":" + "[";
        for (int i = firstVisiblePosition; i <= lastVisiblePosition; i++) {
            extras = extras + "\"" + listFavo.get(i).getKeyId() + "\"" + ",";
        }
        extras = extras.substring(0, extras.length() - 1);
        extras = extras + "]}";
        L.d(thiz, "onShot: " + extras);
        return extras;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.list_txt_search:
                Intent intent = new Intent(getContext(), SearchActivity.class);
                Bundle bundle = new Bundle();
                if (!searchHistory.isEmpty()) {
                    bundle.putStringArrayList(VIEW_SEARCH_HISTORY_SAVE, (ArrayList<String>) searchHistory);
                }
                bundle.putStringArrayList(VIEW_SEARCH_AREA, (ArrayList<String>) bodyDescription.getDistrictListIds());
                bundle.putString(VIEW_SEARCH_FLOOT, bodyDescription.getFloors());
                bundle.putString(VIEW_SEARCH_UNIT, bodyDescription.getUnits());
//                L.d("VIEW_SEARCH_AREA",bodyDescription.getDistrictListIds().toString());
                intent.putExtras(bundle);
                startActivityForResult(intent, 0);
                break;

            case R.id.list_img_sort:
                showSortDialog();
                break;
            case R.id.mic:
                Intent micIntent = new Intent(getContext(), SearchActivity.class);
                micIntent.putExtra("mic", true);
                Bundle micBundle = new Bundle();
                if (!searchHistory.isEmpty()) {
                    micBundle.putStringArrayList(VIEW_SEARCH_HISTORY_SAVE, (ArrayList<String>) searchHistory);
                    micIntent.putExtras(micBundle);
                }
                micBundle.putString(VIEW_SEARCH_FLOOT, bodyDescription.getFloors());
                micBundle.putString(VIEW_SEARCH_UNIT, bodyDescription.getUnits());
                micBundle.putStringArrayList(VIEW_SEARCH_AREA, (ArrayList<String>) bodyDescription.getDistrictListIds());
                L.d("VIEW_SEARCH_AREA", bodyDescription.getDistrictListIds().toString());
                micIntent.putExtras(micBundle);
                startActivityForResult(micIntent, 0);
                break;
            case R.id.list_img_moreoption:
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable(FiltrateActivity.HOUSE_PARAMS, bodyDescription);
                Intent in1 = new Intent(getContext(), FiltrateActivity.class);
                in1.putExtras(bundle1);
                startActivityForResult(in1, 0);
                break;
            case R.id.list_ll_floot:
                showFlootUnitDialog();
                break;
            case R.id.house_rl_alloption:

                FavoSearchOptionDialog optionDialog = new FavoSearchOptionDialog();
                optionDialog.setData(bodyDescription);
                optionDialog.setOnItemClickLisenter((dialog, v1, description) -> {
                    dialog.dismiss();
                    bodyDescription = description;
                    setOptionCount();
                    openFreshView();
                });
                optionDialog.show(getFragmentManager(), "");
                break;
            case R.id.list_txt_area:
                showAreaDialog();
                break;
        }
    }

    private void showAreaDialog() {
        AreaDialog areaDialog = new AreaDialog();
        areaDialog.setItem(ApplicationManager.getDistrictItems(), bodyDescription.getDistrictListIds());
        areaDialog.setOnItemClickLisenter((dialog, v1, list) -> {
            if (list != null && !list.isEmpty()) {
                areaTipTxt.setVisibility(View.VISIBLE);
                areaTipTxt.setText(list.size() + "");
            } else areaTipTxt.setVisibility(View.GONE);
            bodyDescription.setDistrictListIds(list);
            FavoRequestParamsManager.getParams().setArea(getSelectDistrict(ApplicationManager.getDistrictItems(), list));
            refreshLayout.autoRefresh();
            setOptionCount();
        });
        areaDialog.show(getFragmentManager(), "");
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

    private void showFlootUnitDialog() {
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
        sortDialog.show(getFragmentManager(), "");
    }

    private void showFavoriteDialog(View v, final int position) {

        View view = View.inflate(getContext(), R.layout.item_dialog_double, null);

        SimpleTipsDialog dialog = new SimpleTipsDialog(view, 0.72, 0.21, R.id.dialog_content_txt);
        final Properties data = listFavo.get(position);
        final FavoriteDescription favoriteDescription = new FavoriteDescription();
        final String address = data.isFavoriteFlag() == true ? URL_CANCELFAVO : URL_FAVORITE;
        if (!data.isFavoriteFlag()) dialog.setTipString("收藏");
        favoriteDescription.setKeyId(data.getKeyId());
        dialog.setOnItemclickListener((dialog1, type) -> {
            if (type == SimpleTipsDialog.DIALOG_YES) {
                presenter.doPost(address, aHeaderDescription, favoriteDescription);
            }
        });
        Properties properties = listFavo.get(position);
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
        L.d(thiz, messageEvent.getMsg() + "");
        switch (messageEvent.getMsg()) {
            case NETWORK_STATE_FAIL:
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
            case FAVOLIST_COUNT:
                setCountTxt(messageEvent);
                break;
            case HOUSELIST:
                lv.setVisibility(View.VISIBLE);
                break;
            case HOUSELIST_NO:
                showNoPermissionDialog();
                break;
            case FAVO_FAVO_CANCEL:
                removeFavo(position);
                DialogUtil.getSimpleDialog("已取消收藏").show(getFragmentManager(), "");
                break;
            case DATA_FAVO_END:
                refreshLayout.setEnableLoadmore(false);
                showEndTips();
                break;
            case HOUSE_FAVO:
                Properties properties = (Properties) messageEvent.getObject();
                listFavo.add(properties);
                adapter.notifyDataSetChanged();
                houseCount = Integer.parseInt(houseCount) + 1 + "";
                break;
            case HOUSE_FAVO_CANCEL:
                String key = (String) messageEvent.getObject();
                int index = isExise(key);
                if (index != -1)
                    removeFavo(index);
                break;
        }
        closeRefresh();
    }

    private int isExise(String s) {
        if (listFavo != null && !listFavo.isEmpty()) {
            for (int i = 0; i < listFavo.size(); i++) {
                if (listFavo.get(i).getKeyId().equals(s))
                    return i;
            }
        }
        return -1;
    }

    public void onFailure() {
        if (!isVisible) return;
        SimpleTipsDialog tipsDialog = new SimpleTipsDialog();
        tipsDialog.setContentString(getString(R.string.network_unenable));
        tipsDialog.setLeftBtnVisibility(false);
        tipsDialog.show(getFragmentManager(), "");
    }

    private void showEndTips() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), getString(R.string.no_more_data), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removeFavo(int position) {

        MessageEventBus messageEventBus = new MessageEventBus();
        messageEventBus.setMsg(HOUSE_CANCE_FAVO);
        messageEventBus.setObject(listFavo.get(position).getKeyId());
        EventBus.getDefault().post(messageEventBus);

//        deletePattern(adapter.getView(position,lv),position);
        listFavo.remove(position);
        adapter.notifyDataSetChanged();
        houseCount = Integer.parseInt(houseCount) - 1 + "";
    }

    private void setCountTxt(MessageEventBus messageEvent) {
        houseCount = (String) messageEvent.getObject();
        if (houseCount.equals("0")) {
            if (isVisible)
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
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (listFavo != null) listFavo.clear();
        listFavo = null;
    }

    @Override
    public void refreshListData(List<Properties> properties) {
        if (refreshType == 0) listFavo.clear();
        listFavo.addAll(properties);
    }

    @Override
    public void openFreshView() {
        if (refreshLayout.isRefreshing()) return;
        if (refreshLayout.isLoading()) return;
        if (!listFavo.isEmpty()) lv.post(new Runnable() {
            @Override
            public void run() {
                lv.setSelection(0);
            }
        });
        adapter.setShowGreenTabView(bodyDescription.getHasSalePricePremiumUnpaid());
        refreshLayout.autoRefresh();
    }

    @Override
    public void openLoadView() {
        if (refreshLayout.isLoading()) return;
        refreshLayout.autoLoadmore();
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
                setOptionCount();
                break;

            case 1:
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    List<String> addressList = new ArrayList<>();
                    String condition = bundle.getString(SearchActivity.VIEW_SEARCH_KEY_TYPE);
                    if (condition != null) {
                        searchHistory.clear();
                        for (String address : condition.split("/")) {
                            searchHistory.add(address.split(":")[1]);
                            addressList.add(address);
                        }
                        bodyDescription.setSearcherAddress(addressList);
                    }
                    String label = bundle.getString(VIEW_SEARCH_LABEL);
                    if (label != null) search.setText(label);
                    bodyDescription.setFloors(bundle.getString(VIEW_SEARCH_FLOOT));
                    bodyDescription.setUnits(bundle.getString(VIEW_SEARCH_UNIT));
                    bodyDescription.setDistrictListIds((bundle.getStringArrayList(VIEW_SEARCH_AREA)));
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
//                bodyDescription =
                Bundle bundle = data.getExtras();
                bodyDescription = (HouseDescription) bundle.get(FiltrateActivity.HOUSE_PARAMS);
                try {
                    Toast.makeText(getContext(), ItemCountUtil.count(bodyDescription) + "", Toast.LENGTH_SHORT).show();
//                    optionTip.setVisibility(View.VISIBLE);
//                    optionTip.setText(ItemCountUtil.count(bodyDescription) + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                setOptionCount();
//                setOptionCount();
                L.d("OnActivityBack1", bodyDescription.toString());
                refreshLayout.autoRefresh();
                break;

        }
    }

    private void setOptionCount() {
        try {
//            moreTip.setVisibility(View.VISIBLE);
//            moreTip.setText(ItemCountUtil.count(bodyDescription) + "");
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

        if (bodyDescription.getDistrictListIds() != null) {
//            areaTipTxt.setVisibility();
            if (bodyDescription.getDistrictListIds() != null && !bodyDescription.getDistrictListIds().isEmpty()) {
                areaTipTxt.setVisibility(View.VISIBLE);
                areaTipTxt.setText(bodyDescription.getDistrictListIds().size() + "");
            } else areaTipTxt.setVisibility(View.GONE);
        }

//        L.d("getSearcherAddress",bodyDescription.getSearcherAddress().toString());
//        if (TextUtil.isEmply(bodyDescription.getSearcherAddress())) {
//            optionTxt.setText(R.string.dialog_price_unlimit);
//            search.setText(null);
//            optionTip.setVisibility(View.GONE);
//        } else {
//            optionTxt.setText(parseData(FavoRequestParamsManager.getParams().getAddress().get(0)));
//            optionTip.setVisibility(View.VISIBLE);
//            optionTip.setText(FavoRequestParamsManager.getParams().getAddress().size() + "");
//            String addStr = "";
//            for (PropertyParamHints h : PropertyRequestParamsManager.getParams().getAddress()) {
//                addStr = addStr + parseData(h) + ",";
//            }
////            search.setText(addStr.substring(0, addStr.length() - 1));
//        }

        //        L.d("getSearcherAddress",bodyDescription.getSearcherAddress().toString());
        if (TextUtil.isEmply(bodyDescription.getSearcherAddress())) {
            optionTxt.setText(R.string.dialog_price_unlimit);
            search.setText(null);
            optionTip.setVisibility(View.GONE);
//            L.d("",);
        } else {
//            optionTxt.setText(parseData(PropertyRequestParamsManager.getParams().getAddress().get(0)));
            optionTip.setVisibility(View.VISIBLE);
            optionTip.setText(FavoRequestParamsManager.getParams().getAddress().size() + "");
            String addStr = "";
            for (PropertyParamHints h : FavoRequestParamsManager.getParams().getAddress()) {
                addStr = addStr + getDeatilAddress(h) + ",";
            }
            optionTxt.setText(addStr);
//            search.setText(addStr.substring(0, addStr.length() - 1));
        }

        allOptionView.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
        allOptionView.measure(0, 0);

    }

    //    private View optionContent;
    private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {

        @Override
        public void onGlobalLayout() {
            int width = allOptionView.getWidth();
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
            allOptionView.getViewTreeObserver().removeGlobalOnLayoutListener(onGlobalLayoutListener);
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
        L.d("parseData", labelString);
        return labelString;
    }

//    private void setOptionCount() {
//        try {
//            optionTip.setText(ItemCountUtil.count(bodyDescription) + "");
//            if (ItemCountUtil.count(bodyDescription) <= 0) {
//                optionTip.setVisibility(View.GONE);
//            } else {
//                optionTip.setVisibility(View.VISIBLE);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        if (bodyDescription.getDistrictListIds() == null || bodyDescription.getDistrictListIds().isEmpty())
//            optionTxt.setText(R.string.dialog_price_unlimit);
//        else {
//            optionTxt.setText(FavoRequestParamsManager.getParams().getArea().get(0).getDistrictName());
//        }
//    }
}
