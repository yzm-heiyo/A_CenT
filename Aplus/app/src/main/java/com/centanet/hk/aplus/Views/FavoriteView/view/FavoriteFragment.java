package com.centanet.hk.aplus.Views.FavoriteView.view;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.centanet.hk.aplus.MyApplication;
import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.TextUtil;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.Views.Dialog.DialogFactory;
import com.centanet.hk.aplus.Views.Dialog.PriceDialog;
import com.centanet.hk.aplus.Views.Dialog.SimpleTipsDialog;
import com.centanet.hk.aplus.Views.Dialog.SortDialog;
import com.centanet.hk.aplus.Views.FavoriteView.present.HouseListPresenter;
import com.centanet.hk.aplus.Views.FavoriteView.present.IHouseListPresenter;
import com.centanet.hk.aplus.Views.HouseDetailView.view.DetailActicity;
import com.centanet.hk.aplus.Views.LoginView.view.LoginActivity;
import com.centanet.hk.aplus.Views.SearchView.view.SearchActivity;
import com.centanet.hk.aplus.Views.ComplexSearchView.ComplexSearchActivity;
import com.centanet.hk.aplus.common.DataManager;
import com.centanet.hk.aplus.entity.house.Properties;
import com.centanet.hk.aplus.entity.http.AHeaderDescription;
import com.centanet.hk.aplus.entity.http.FavoriteDescription;
import com.centanet.hk.aplus.entity.http.HouseDescription;
import com.centanet.hk.aplus.entity.http.ParameterDescription;
import com.centanet.hk.aplus.eventbus.MessageEventBus;
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
import static com.centanet.hk.aplus.common.CommandField.DialogType.STATUS;
import static com.centanet.hk.aplus.common.CommandField.PriceType.SALE;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.HouseListDataCount.FAVOLIST_COUNT;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.NetWorkState.NETWORK_STATE_FAIL;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.NetWorkState.NETWORK_STATE_SUCCESS;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.Permission.HOUSELIST;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.Permission.HOUSELIST_NO;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.RefreshView.VIEW_LOAD_START;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.RefreshView.VIEW_REFRESH_START;


/**
 * Created by mzh1608258 on 2018/1/2.
 */

public class FavoriteFragment extends Fragment implements IFavorieFragment, View.OnClickListener {

    private final String thiz = getClass().getSimpleName();
    private View view;
    private ListView lv;
    private ItemAdapter adapter;
    private View status, price, more, sort;
    private TextView search, currentPosition;
    private static List<Properties> listFavo;
    private List<String> searchHistory;
    private IHouseListPresenter presenter;
    private RefreshLayout refreshLayout;
    private AHeaderDescription aHeaderDescription;
    private HouseDescription bodyDescription;
    private String houseCount = "0";
    private boolean isFavorite = false;

//    public static HouseListFragment newInstance(int pageType, String parentCategory) {
//        HouseListFragment f = new HouseListFragment();
//        Bundle b = new Bundle();
//        b.putInt("pageType", pageType);
//        b.putSerializable("parentCategory", parentCategory);
//        f.setArguments(b);
//        return f;
//    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            isFavorite = args.getBoolean("isFavorite");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_productlist, null, false);
        if (view != null) {
            initViews();
            init();
            initListeners();
            if (isFavorite) {
                bodyDescription.setPropertyType(5);
                openFreshView();
            }
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
        refreshLayout = view.findViewById(R.id.smartLayout);
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
            }
        });
        lv.setAdapter(adapter);
        presenter = new HouseListPresenter(this);
        EventBus.getDefault().register(this);
        ClassicsFooter.REFRESH_FOOTER_LOADING = getString(R.string.loading_more);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            presenter.doPost(HttpUtil.URL_PARAMETER, aHeaderDescription, new ParameterDescription());
        } else {
            //相当于Fragment的onPause
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initListeners() {

        status.setOnClickListener(this);
        price.setOnClickListener(this);
        sort.setOnClickListener(this);
        more.setOnClickListener(this);
        search.setOnClickListener(this);

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
                listFavo.clear();
                presenter.clearFlag();
                presenter.doPost(HttpUtil.URL_PATH, aHeaderDescription, bodyDescription);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                bodyDescription.setPageIndex(listFavo.size() / 15 + 1);
                presenter.doPost(HttpUtil.URL_PATH, aHeaderDescription, bodyDescription);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailActicity.class);
                intent.putExtra("keyId", listFavo.get(position).getKeyId());
                startActivity(intent);
            }
        });
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
                startActivity(new Intent(getContext(), ComplexSearchActivity.class));
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
        }
    }

    private void showStatusDialog() {
        DialogFragment statusDialog = DialogFactory.newInstance(STATUS, new DialogFactory.IGetClickItem() {
            @Override
            public void getClickItem(DialogFragment dialog, String... items) {
                dialog.dismiss();
                List<String> statuList = new ArrayList<>();
                for (int i = 0; i < DataManager.checkBoxSelecterList.size(); i++) {
                    int index = DataManager.checkBoxSelecterList.get(i) - 1;//數據矯正
                    index = index == -1 ? 0 : index;
                    for (Map.Entry<String, String> entry : DataManager.parameter.get(index).entrySet()) {
                        statuList.add(entry.getValue());
                    }
                }
                bodyDescription.setPropertyStatus(statuList);
                listFavo.clear();
                openFreshView();
            }
        });
        statusDialog.show(getFragmentManager(), "");
    }

    private void showPriceDialog() {
        PriceDialog priceDialog = new PriceDialog(DataManager.priceType, DataManager.priceDialogSeletedId, DataManager.priceInterval);
        priceDialog.setOnDialogClikeLisenter(new PriceDialog.onDialogOnclikeLisenter() {

            @Override
            public void onClike(Dialog dialog, int viewID, Map<String, Object> params) {
                dialog.dismiss();
                int type = (int) params.get(PriceDialog.PARAMS_TYPE);
                DataManager.priceType = type;
                DataManager.priceDialogSeletedId = (int) params.get(PriceDialog.PARAMS_SELECTID);
                String[] items = (String[]) params.get(PriceDialog.PARAMS_PRICE);
                bodyDescription.setPageIndex(1);
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

                    DataManager.priceInterval[0] = items[0];
                    DataManager.priceInterval[1] = items[1];
                }
                openFreshView();
            }
        });
        priceDialog.show(getFragmentManager(), "");
    }

    private void showSortDialog() {
        SortDialog sortDialog = new SortDialog(DataManager.sortDialogSelectId);
        sortDialog.setOnDialogClikeLisenter(new SortDialog.onDialogOnclikeLisenter() {
            @Override
            public void onClike(Dialog dialog, int viewID, Map<String, Object> params) {
                dialog.dismiss();
                bodyDescription.setPageIndex(1);
                DataManager.sortDialogSelectId = (int) params.get(SortDialog.PARAMS_SELECTID);
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
                    listFavo.get(position).setFavoriteFlag(!data.isFavoriteFlag());
                    adapter.updateView(position, lv);
//                    PermissionManager.set();
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
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEventBus messageEvent) {
        L.d(thiz, messageEvent.getMsg() + "");
        switch (messageEvent.getMsg()) {
            case NETWORK_STATE_FAIL:
                //todo 失敗彈出提示框
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
        }
        closeRefresh();
    }

    private void setCountTxt(MessageEventBus messageEvent) {
        houseCount = (String) messageEvent.getObject();
        if (houseCount.equals("0")) return;
//        currentPosition.setText("1" + "/" + houseCount);
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
        listFavo.addAll(properties);
//        adapter.notifyDataSetChanged();
    }

    @Override
    public void openFreshView() {
        if (refreshLayout.isRefreshing()) return;
        if (refreshLayout.isLoading()) return;
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
        simpleTipsDialog.show(getFragmentManager(), "");
        closeRefresh();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
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
        }
    }

    private static class ItemAdapter extends BaseAdapter implements View.OnClickListener {

        private Context context;
        private OnItemClickListener mOnItemClickListener;
        private int mPosition;
//        private List<Properties> dataList;

        ItemAdapter(Context context, List<Properties> data) {
            //todo 存在一個問題 爲何使用EventBus會導致listData指向不一致
            this.context = context;
//            dataList = data;
        }

        @Override
        public int getCount() {
            if (listFavo.size() != 0) return listFavo.size();
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return initView(position, convertView, parent);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) mOnItemClickListener.onClick(v, (int) v.getTag());
        }

        public View initView(int position, View convertView, ViewGroup parent) {
            View view;
            final ViewHolder viewHolder;
            if (convertView == null) {
                view = LayoutInflater.from(context).inflate(R.layout.item_resultitem, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.iconView = view.findViewById(R.id.item_resultitem_heart_img);
                viewHolder.iconView.setOnClickListener(this);
                viewHolder.iconView.setTag(position);
                viewHolder.resultView = view.findViewById(R.id.item_result_ico);
                viewHolder.buildTxt = view.findViewById(R.id.item_result_name);
                viewHolder.tipsTxt = view.findViewById(R.id.item_tips_txt);
                viewHolder.rentTxt = view.findViewById(R.id.item_result_rent);
                viewHolder.dateTxt = view.findViewById(R.id.item_sell_date_txt);
                viewHolder.ssdTxt = view.findViewById(R.id.item_icon_ssd);

                viewHolder.priceTxt = view.findViewById(R.id.item_result_price);
                viewHolder.codeTxt = view.findViewById(R.id.item_result_code);
                viewHolder.placeTxt = view.findViewById(R.id.item_peace_txt);
                viewHolder.useTxt = view.findViewById(R.id.item_user_txt);
                viewHolder.useRveSaleTxt = view.findViewById(R.id.item_user_rev_sale_txt);
                viewHolder.useRveRentTxt = view.findViewById(R.id.item_user_rev_rent_txt);
                viewHolder.directionTxt = view.findViewById(R.id.item_HouseDirection_txt);
                viewHolder.reallyTxt = view.findViewById(R.id.item_really_txt);
                viewHolder.reallyRveRentTxt = view.findViewById(R.id.item_really_rev_rent_txt);
                viewHolder.reallyRveSaleTxt = view.findViewById(R.id.item_really_rev_sale_txt);

                viewHolder.iconHot = view.findViewById(R.id.item_icon_hot);
                viewHolder.iconKey = view.findViewById(R.id.item_icon_key);
                viewHolder.iconO = view.findViewById(R.id.item_icon_o);
                viewHolder.iconL = view.findViewById(R.id.item_icon_l);
                viewHolder.iconD = view.findViewById(R.id.item_icon_d);
                viewHolder.iconSingle = view.findViewById(R.id.item_icon_medal);
                viewHolder.iconFavo = view.findViewById(R.id.item_icon_favo);

                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
                viewHolder.iconView.setTag(position);
            }
            if (!listFavo.isEmpty()) {
                Properties properties = listFavo.get(position);
                viewHolder.codeTxt.setText(properties.getPropertyNo());
                viewHolder.buildTxt.setText(properties.getBuildingName());
                viewHolder.tipsTxt.setText(properties.getPrompt());
                viewHolder.rentTxt.setText(properties.getRentPrice());
                viewHolder.priceTxt.setText(properties.getSalePrice());
                viewHolder.dateTxt.setText(properties.getRegisterDate());
                viewHolder.placeTxt.setText(properties.getPropertyInterval());
                viewHolder.reallyTxt.setText(properties.getSquareFoot());
                viewHolder.useRveSaleTxt.setText(properties.getActualSalePriceUnit());
                viewHolder.useRveRentTxt.setText(properties.getActualRentPriceUnit());
                viewHolder.directionTxt.setText(properties.getHouseDirection());
                viewHolder.reallyRveSaleTxt.setText(properties.getSalePriceUnit());
                viewHolder.reallyRveRentTxt.setText(properties.getRentPriceUnit());
                viewHolder.useRveRentTxt.setText(properties.getRentPriceUnit());
                String useSquare = properties.getSquareUseFoot();
                String useSquareNum = properties.getSquareUseSourceNum();
                if (useSquareNum != null && useSquareNum.equals("10"))
                    viewHolder.useTxt.setText(TextUtil.changeTextColor(useSquare, Color.BLUE + ""));
                else if (useSquareNum == null || !useSquareNum.equals("10"))
                    viewHolder.useTxt.setText(useSquare);

                viewHolder.iconSingle.setSelected(properties.isOnlyTrust());
                viewHolder.iconFavo.setSelected(properties.isFavoriteFlag());
                viewHolder.iconView.setSelected(properties.isFavoriteFlag());
                viewHolder.iconO.setSelected(properties.isODish());
                viewHolder.iconKey.setImageLevel(properties.getPropertyKeyEnum());

                viewHolder.iconHot.setSelected(properties.getHotList() == null ? false : true);
                viewHolder.iconL.setSelected(properties.getIsConfirmed() == null ? false : true);
                viewHolder.iconD.setSelected(properties.getDevelopmentEndCredits() == null ? false : true);
                if (properties.getSSDType() != 0) {
                    viewHolder.ssdTxt.setVisibility(View.VISIBLE);
                    int per = 5 * properties.getSSDType();
                    if (properties.getSSDType() == 1) per = 0;
                    viewHolder.ssdTxt.setText(per + "%");
                }
                int level = 1;
                setIconViewLevel(level, viewHolder, properties.getPropertyStatus());
            }
            return view;
        }

        private void setIconViewLevel(int level, ViewHolder viewHolder, String properties) {
            switch (properties.substring(0, 1)) {
                case "N":
                    level = 1;
                    break;
                case "P":
                    level = 2;
                    break;
                case "S":
                    level = 6;
                    break;
                case "T":
                    level = 4;
                    break;
                case "G":
                    level = 5;
                    break;
                default:
                    level = 3;
                    break;
            }
            viewHolder.resultView.setImageLevel(level);
        }


        public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
            this.mOnItemClickListener = mOnItemClickListener;
        }

        private class ViewHolder {
            ImageView iconView, resultView;
            TextView buildTxt, placeTxt, codeTxt, priceTxt, rentTxt, tipsTxt, dateTxt, ssdTxt;
            TextView directionTxt, useTxt, useRveSaleTxt, useRveRentTxt, reallyTxt, reallyRveSaleTxt, reallyRveRentTxt;
            ImageView iconHot, iconKey, iconO, iconL, iconD, iconSingle, iconFavo;
        }

        public void updateView(int position, ListView listView) {
            int visibleFirstPosi = listView.getFirstVisiblePosition();
            int visibleLastPosi = listView.getLastVisiblePosition();
            if (position >= visibleFirstPosi && position <= visibleLastPosi) {
                View view = listView.getChildAt(position - visibleFirstPosi);
                ViewHolder holder = (ViewHolder) view.getTag();
                holder.iconView.setSelected(!holder.iconView.isSelected());
            }
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }

        public interface OnItemClickListener {
            void onClick(View v, int position);
        }
    }
}
