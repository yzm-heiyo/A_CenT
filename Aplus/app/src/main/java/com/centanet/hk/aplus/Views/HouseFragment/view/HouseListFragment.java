package com.centanet.hk.aplus.Views.HouseFragment.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.centanet.hk.aplus.MyApplication;
import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.ItemCountUtil;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.Views.ComplexSearchView.ComplexActivity;
import com.centanet.hk.aplus.Views.Dialog.PriceDialog;
import com.centanet.hk.aplus.Views.Dialog.SimpleTipsDialog;
import com.centanet.hk.aplus.Views.Dialog.SortDialog;
import com.centanet.hk.aplus.Views.Dialog.StatusDialog;
import com.centanet.hk.aplus.Views.GooglevoiView;
import com.centanet.hk.aplus.Views.HouseDetailView.view.DetailActicity;
import com.centanet.hk.aplus.Views.HouseFragment.BaseHouseFragment;
import com.centanet.hk.aplus.Views.HousetListView.present.HouseListPresenter;
import com.centanet.hk.aplus.Views.HousetListView.present.IHouseListPresenter;
import com.centanet.hk.aplus.Views.LoginView.view.LoginActivity;
import com.centanet.hk.aplus.Views.SearchView.view.SearchActivity;
import com.centanet.hk.aplus.Widgets.CircleTipsView;
import com.centanet.hk.aplus.bean.complexSearch.Operation;
import com.centanet.hk.aplus.bean.house.Properties;
import com.centanet.hk.aplus.bean.http.AHeaderDescription;
import com.centanet.hk.aplus.bean.http.FavoriteDescription;
import com.centanet.hk.aplus.bean.http.HouseDescription;
import com.centanet.hk.aplus.eventbus.MessageEventBus;
import com.centanet.hk.aplus.manager.ApplicationManager;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
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
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.FavoState.HOUSE_FAVO;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.FavoState.HOUSE_FAVO_CANCEL;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.HouseListDataCount.HOUSELIST_COUNT;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.HouseListRemove.HOUSE_CANCE_FAVO;
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

public class HouseListFragment extends BaseHouseFragment implements IHouseListFragment, View.OnClickListener {

    protected final String thiz = getClass().getSimpleName();
    protected View view;
    protected ListView lv;
    protected ItemAdapter adapter;
    protected View status, price, more, sort,mic;
    protected TextView search, currentPosition, reset;
    protected List<Properties> listData;
    protected List<String> searchHistory;
    protected IHouseListPresenter presenter;
    protected RefreshLayout refreshLayout;
    protected AHeaderDescription aHeaderDescription;
    protected HouseDescription bodyDescription;
    protected String houseCount = "0";
    protected boolean isFavorite = false;
    protected List<Integer> staSelectList;
    protected static String[] priceInterval;
    protected int priceType = SALE;
    protected int priceDialogSeletedId;
    protected int sortDialogSelectId;
    protected int refreshType = 0;
    protected CircleTipsView statusCircleTipsView;
    protected CircleTipsView complexTipsView;
    protected View statusDown, complexDown;
    protected int position;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        }
        return view;
    }

    private void initViews() {
        lv = view.findViewById(R.id.fragment_presmises_listview);
        status = view.findViewById(R.id.fragment_presmises_status);
        price = view.findViewById(R.id.fragment_presmises_price);
        more = view.findViewById(R.id.fragment_presmises_more);
        sort = view.findViewById(R.id.fragment_presmises_sort);
        search = view.findViewById(R.id.fragment_presmises_search);
        currentPosition = view.findViewById(R.id.fragment_presmises_current_position);
        statusCircleTipsView = view.findViewById(R.id.list_status_tip);
        statusDown = view.findViewById(R.id.fragment_img_status_down);
        complexTipsView = view.findViewById(R.id.list_complex_tip);
        complexDown = view.findViewById(R.id.fragment_img_complex_down);
        refreshLayout = view.findViewById(R.id.smartLayout);
        reset = view.findViewById(R.id.fragment_list_txt_reset);
        mic = view.findViewById(R.id.houselist_img_mic);
        refreshLayout.setEnableLoadmore(false);
    }

    private void init() {
        MyApplication application = (MyApplication) getActivity().getApplicationContext();
        aHeaderDescription = application.getHeaderDescription();
        bodyDescription = new HouseDescription();
        listData = new ArrayList<>();
        staSelectList = new ArrayList<>();
        searchHistory = new ArrayList<>();
        priceInterval = new String[2];
        adapter = new ItemAdapter(getActivity(), listData);
        adapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                HouseListFragment.this.position = position;
                showFavoriteDialog(v, position);
            }
        });
        lv.setAdapter(adapter);
        presenter = new HouseListPresenter((com.centanet.hk.aplus.Views.HousetListView.view.IHouseListFragment) this);
        ClassicsFooter.REFRESH_FOOTER_LOADING = getString(R.string.loading_more);
//        presenter.doPost(HttpUtil.URL_PARAMETER, aHeaderDescription, new ParameterDescription());
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initListeners() {

        status.setOnClickListener(this);
        price.setOnClickListener(this);
        sort.setOnClickListener(this);
        more.setOnClickListener(this);
        search.setOnClickListener(this);
        reset.setOnClickListener(this);
        mic.setOnClickListener(this);
        lv.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (houseCount.equals("0")) {
                    currentPosition.setText("0/0");
                } else {
                    currentPosition.setText(lv.getFirstVisiblePosition() + 1 + "/" + houseCount);
                }
            }
        });

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                presenter.clearFlag();
                refreshType = 0;
                bodyDescription.setPageIndex(1);
                presenter.doPost(HttpUtil.URL_PATH, aHeaderDescription, bodyDescription);
                refreshlayout.setEnableLoadmore(true);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (listData.size() == Integer.parseInt(houseCount)) {
                    refreshLayout.setEnableLoadmore(false);
                    refreshlayout.finishLoadmore(2000);
                    showEndTips();
                    return;
                }
                refreshType = 1;
                bodyDescription.setPageIndex(listData.size() / 15 + 1);
                presenter.doPost(HttpUtil.URL_PATH, aHeaderDescription, bodyDescription);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailActicity.class);
                intent.putExtra("keyId", listData.get(position).getKeyId());
                startActivity(intent);
            }
        });
    }

    private void showEndTips() {
        Toast.makeText(getActivity(), getString(R.string.no_more_data), Toast.LENGTH_SHORT).show();
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
                bundle.putSerializable("operation", ApplicationManager.getHouseOperation());
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
            case R.id.houselist_img_mic:
                startActivity(new Intent(getActivity(), GooglevoiView.class));
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
        ApplicationManager.setHouseOperation(new Operation());
        search.setText(null);
        priceInterval = new String[2];
        searchHistory.clear();
        bodyDescription = new HouseDescription();
        openFreshView();

    }

    private void showStatusDialog() {

        StatusDialog statusEndDialog = new StatusDialog(ApplicationManager.getStatusText(), staSelectList);
        statusEndDialog.setOnDialogOnclikeLisenter(new StatusDialog.onDialogOnclikeLisenter() {
            @Override
            public void onClick(Dialog v, int viewID, List<Integer> viewList, String[] content) {
                v.dismiss();
                staSelectList = viewList;
                statusCircleTipsView.setText(viewList.size() == 7 ? 6 : viewList.size());
                if (viewList.isEmpty()) {
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
//                bodyDescription.setPageIndex(1);
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
//                bodyDescription.setPageIndex(1);
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
        final Properties data = listData.get(position);
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
        dialog.setContentString(listData.get(position).getBuildingName());
        dialog.show(getFragmentManager(), "");
    }

    @Override
    public void refreshListView() {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadmore();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEventBus messageEvent) {
        L.d(thiz, messageEvent.getMsg() + "");
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
        if (houseCount.equals("0")) return;
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
        if (refreshType == 0) listData.clear();
        listData.addAll(properties);
    }

    @Override
    public void openFreshView() {
        if (refreshLayout.isRefreshing()) return;
        if (refreshLayout.isLoading()) return;
//        if(!listData.isEmpty())lv.setSelection(0);
        if(!listData.isEmpty())lv.post(new Runnable() {
            @Override
            public void run() {
                lv.smoothScrollToPositionFromTop(0,0,2);
            }
        });
        refreshLayout.autoRefresh();
    }

    @Override
    public void openLoadView() {
        if (refreshLayout.isLoading()) return;
        refreshLayout.autoLoadmore();
    }

    @Override
    public void onFailure() {
        SimpleTipsDialog tipsDialog = new SimpleTipsDialog();
        tipsDialog.setContentString(getString(R.string.network_unenable));
        tipsDialog.setLeftBtnVisibility(false);
        tipsDialog.show(getFragmentManager(), "");
    }

    @Override
    public void setCancelFavo() {
        changeFavoStatu(false);
        Properties house = listData.get(position);
        EventBus.getDefault().post(new MessageEventBus(HOUSE_FAVO_CANCEL, house.getKeyId()));
        listData.get(position).setFavoriteFlag(false);
    }

    @Override
    public void setFavo() {
        changeFavoStatu(true);
        EventBus.getDefault().post(new MessageEventBus(HOUSE_FAVO, listData.get(position)));
        listData.get(position).setFavoriteFlag(true);
    }

    private void changeFavoStatu(final boolean b) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.updateView(position, lv, b);
            }
        });
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
        simpleTipsDialog.show(getFragmentManager(), "");
        closeRefresh();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
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
                    if (count == 0) {
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
                    bodyDescription = description;
                    openFreshView();
                }
                Operation operation = (Operation) bundle.get("operation");
                ApplicationManager.setHouseOperation(operation);
                break;

            case 3:
                complexTipsView.setVisibility(View.GONE);
                complexDown.setVisibility(View.VISIBLE);
                ApplicationManager.setHouseOperation(new Operation());
                break;
        }
    }
}
