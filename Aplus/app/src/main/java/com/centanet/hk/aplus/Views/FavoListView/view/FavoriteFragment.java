package com.centanet.hk.aplus.Views.FavoListView.view;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.centanet.hk.aplus.MyApplication;
import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.DialogUtil;
import com.centanet.hk.aplus.Utils.ItemCountUtil;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.Views.ComplexSearchView.ComplexActivity;
import com.centanet.hk.aplus.Views.Dialog.PriceDialog;
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
import com.centanet.hk.aplus.bean.complexSearch.Operation;
import com.centanet.hk.aplus.bean.house.Properties;
import com.centanet.hk.aplus.bean.http.AHeaderDescription;
import com.centanet.hk.aplus.bean.http.FavoriteDescription;
import com.centanet.hk.aplus.bean.http.HouseDescription;
import com.centanet.hk.aplus.bean.http.UserBehaviorDescription;
import com.centanet.hk.aplus.eventbus.MessageEventBus;
import com.centanet.hk.aplus.manager.ApplicationManager;
import com.centanet.hk.aplus.manager.ScreenShotListenManager;
import com.githang.statusbar.StatusBarCompat;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

public class FavoriteFragment extends BaseHouseFragment implements IFavorieFragment, View.OnClickListener {

    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 321;
    private final String thiz = getClass().getSimpleName();
    private View view;
    private ListView lv;
    private ItemAdapter adapter;
    private View status, price, more, sort, reset, mic;
    private TextView search, currentPosition;
    private List<Properties> listFavo;
    private List<String> searchHistory;
    private IHouseListPresenter presenter;
    private RefreshLayout refreshLayout;
    private AHeaderDescription aHeaderDescription;
    private HouseDescription bodyDescription;
    private String houseCount = "0";
    private boolean isFavorite = false;
    private List<String> staSelectList;
    public static String[] priceInterval;
    private int priceType = SALE;
    private int priceDialogSeletedId;
    private int sortDialogSelectId;
    private int position;
    private int refreshType = 0;
    private CircleTipsView statusCircleTipsView;
    private CircleTipsView complexTipsView;
    private View complexDown;
    private View statusDown;
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
        view = inflater.inflate(R.layout.fragment_houselist, null, false);
        if (view != null) {
            initViews();
            init();
            initListeners();
            bodyDescription.setPropertyType(5);
        }
//        openFreshView();
        return view;
    }

    private void initViews() {
        lv = view.findViewById(R.id.fragment_presmises_listview);
        status = view.findViewById(R.id.fragment_presmises_status);
        price = view.findViewById(R.id.fragment_presmises_price);
        more = view.findViewById(R.id.fragment_presmises_more);
        sort = view.findViewById(R.id.fragment_presmises_sort);
        search = view.findViewById(R.id.fragment_presmises_search);
        statusDown = view.findViewById(R.id.fragment_img_status_down);
        currentPosition = view.findViewById(R.id.fragment_presmises_current_position);
        statusCircleTipsView = view.findViewById(R.id.list_status_tip);
        complexTipsView = view.findViewById(R.id.list_complex_tip);
        complexDown = view.findViewById(R.id.fragment_img_complex_down);
        refreshLayout = view.findViewById(R.id.smartLayout);
        reset = view.findViewById(R.id.fragment_list_txt_reset);
        mic = view.findViewById(R.id.mic);
        mic.setOnClickListener(this);
        refreshLayout.setEnableLoadmore(false);
    }

    private void init() {
        MyApplication application = (MyApplication) getActivity().getApplicationContext();
        aHeaderDescription = application.getHeaderDescription();
        bodyDescription = new HouseDescription();
        listFavo = new ArrayList<>();
        staSelectList = new ArrayList<>();
        searchHistory = new ArrayList<>();
        priceInterval = new String[2];
        priceInterval[0] = "";
        priceInterval[1] = "";
        adapter = new ItemAdapter(getActivity(), listFavo);
        adapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                showFavoriteDialog(v, position);
                FavoriteFragment.this.position = position;
            }
        });
        lv.setAdapter(adapter);
        presenter = new HouseListPresenter(this);
        EventBus.getDefault().register(this);

        startScreenListen();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initListeners() {

        status.setOnClickListener(this);
        price.setOnClickListener(this);
        sort.setOnClickListener(this);
        more.setOnClickListener(this);
        search.setOnClickListener(this);
        reset.setOnClickListener(this);

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
                intent.putExtra("keyId", listFavo.get(position).getKeyId());
                startActivity(intent);
            }
        });
    }

    private void startScreenListen() {
//        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            //申请WRITE_EXTERNAL_STORAGE权限
//            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                    WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
//        }

        screenShotListenManager = ScreenShotListenManager.newInstance(getActivity());
        screenShotListenManager.setListener((imagePath) -> onScreenShot());
        screenShotListenManager.startListen();
    }


    private void onScreenShot() {
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
            case R.id.fragment_presmises_search:
                Intent intent = new Intent(getContext(), SearchActivity.class);
                if (!searchHistory.isEmpty()) {
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList(VIEW_SEARCH_HISTORY_SAVE, (ArrayList<String>) searchHistory);
                    intent.putExtras(bundle);
                }
                startActivityForResult(intent, 0);
                break;
            case R.id.fragment_presmises_more:
                Intent in = new Intent(getContext(), ComplexActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("operation", ApplicationManager.getFavoOperation());
                in.putExtras(bundle);
                startActivityForResult(in, 0);
                break;
            case R.id.fragment_presmises_sort:
                showSortDialog();
                break;
            case R.id.fragment_presmises_price:
                showPriceDialog();
                break;
            case R.id.fragment_presmises_status:
                showStatusDialog();
                break;
            case R.id.fragment_list_txt_reset:
                reset();
                break;
            case R.id.mic:
                Intent micIntent = new Intent(getContext(), SearchActivity.class);
                micIntent.putExtra("mic", true);
                if (!searchHistory.isEmpty()) {
                    Bundle micBundle = new Bundle();
                    micBundle.putStringArrayList(VIEW_SEARCH_HISTORY_SAVE, (ArrayList<String>) searchHistory);
                    micIntent.putExtras(micBundle);
                }
                startActivityForResult(micIntent, 0);
                break;
        }
    }

    private void reset() {
        complexTipsView.setVisibility(View.GONE);
        complexDown.setVisibility(View.VISIBLE);
        sortDialogSelectId = R.id.sort_rb_default;
        priceDialogSeletedId = R.id.dialog_radiobtn_default;
        staSelectList.clear();
        statusDown.setVisibility(View.VISIBLE);
        statusCircleTipsView.setVisibility(View.GONE);
        ApplicationManager.setFavoOperation(new Operation());
        search.setText(null);
//        priceInterval = new String[2];
        priceInterval[0] = "";
        priceInterval[1] = "";
        searchHistory.clear();
        bodyDescription = new HouseDescription();
        bodyDescription.setPropertyType(5);
//        openFreshView();
    }


    private void showStatusDialog() {

        StatusDialog statusEndDialog = new StatusDialog(ApplicationManager.getStatusText(), staSelectList);
        statusEndDialog.setOnDialogOnclikeLisenter(new StatusDialog.onDialogOnclikeLisenter() {
            @Override
            public void onClick(Dialog v, int viewID, List<String> viewList, String[] content) {

                v.dismiss();
                staSelectList = viewList;
                statusCircleTipsView.setText(viewList.size());
                if (content == null) {
                    statusDown.setVisibility(View.VISIBLE);
                    statusCircleTipsView.setVisibility(View.GONE);
                } else {
                    statusCircleTipsView.setVisibility(View.VISIBLE);
                    statusDown.setVisibility(View.GONE);
                }
                bodyDescription.setPropertyStatus(ApplicationManager.getStatusValue(content));
                openFreshView();
            }
        });

        statusEndDialog.show(getFragmentManager(), "");
    }

    private void showPriceDialog() {
        PriceDialog priceDialog = new PriceDialog(priceType, priceDialogSeletedId, priceInterval);
        priceDialog.setOnDialogClikeLisenter(new PriceDialog.onDialogOnclikeLisenter() {

            @Override
            public void onClike(Dialog dialog, int viewID, Map<String, Object> params) {
                dialog.dismiss();
                int type = (int) params.get(PriceDialog.PARAMS_TYPE);
                priceType = type;
                priceDialogSeletedId = (int) params.get(PriceDialog.PARAMS_SELECTID);
                String[] items = (String[]) params.get(PriceDialog.PARAMS_PRICE);
                if (items != null) {
                    if (type == SALE) {
                        bodyDescription.setSalePriceFrom(items[0] == "" ? null : items[0]);
                        bodyDescription.setSalePriceTo(items[1] == "" ? null : items[1]);
                        bodyDescription.setRentPriceFrom(null);
                        bodyDescription.setRentPriceTo(null);
                    } else {
                        bodyDescription.setRentPriceFrom(items[0] == "" ? null : items[0]);
                        bodyDescription.setRentPriceTo(items[1] == "" ? null : items[1]);
                        bodyDescription.setSalePriceFrom(null);
                        bodyDescription.setSalePriceTo(null);
                    }

                    priceInterval[0] = items[0];
                    priceInterval[1] = items[1];
                }
                openFreshView();
            }
        });
        priceDialog.show(getFragmentManager(), "");
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
        dialog.setOnItemclickListener(new SimpleTipsDialog.OnItemClickListener() {
            @Override
            public void onClick(DialogFragment dialog, int type) {
                if (type == SimpleTipsDialog.DIALOG_YES) {
                    presenter.doPost(address, aHeaderDescription, favoriteDescription);
                }
            }
        });
        dialog.setContentString(listFavo.get(position).getBuildingName());
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
                    openFreshView();
                } else {
                    search.setText(null);
                    searchHistory.clear();
                    bodyDescription.setSearcherAddress(null);
                    bodyDescription.setFloors(null);
                    bodyDescription.setUnits(null);
                    openFreshView();
                }
                break;
            case 2:
                Bundle bundle = data.getExtras();
                HouseDescription description = (HouseDescription) bundle.get("body");
                int count = 0;
                if (description != null) {
                    try {
                        count = ItemCountUtil.count(description);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    L.d(thiz, "count: " + count);
                    if (count == 0 || count == -1) {
                        complexTipsView.setVisibility(View.GONE);
                        complexDown.setVisibility(View.VISIBLE);
                    } else {
                        complexTipsView.setVisibility(View.VISIBLE);
                        complexTipsView.setText(count);
                        complexDown.setVisibility(View.GONE);
                    }
                    description.setPropertyStatus(bodyDescription.getPropertyStatus());
                    description.setRentPriceFrom(bodyDescription.getRentPriceFrom());
                    description.setRentPriceTo(bodyDescription.getRentPriceTo());
                    description.setSalePriceFrom(bodyDescription.getSalePriceFrom());
                    description.setSalePriceTo(bodyDescription.getSalePriceTo());
                    description.setPropertyType(5);
                    bodyDescription = description;
                    openFreshView();
                }
                Operation operation = (Operation) bundle.get("operation");
                ApplicationManager.setFavoOperation(operation);
                break;
            case 3:
                complexTipsView.setVisibility(View.GONE);
                complexDown.setVisibility(View.VISIBLE);
                ApplicationManager.setHouseOperation(new Operation());
                break;
        }

    }
}
