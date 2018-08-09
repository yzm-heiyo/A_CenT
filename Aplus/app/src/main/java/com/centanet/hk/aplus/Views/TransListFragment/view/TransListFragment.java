package com.centanet.hk.aplus.Views.TransListFragment.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.ItemCountUtil;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.Views.Dialog.AreaDialog;
import com.centanet.hk.aplus.Views.Dialog.FlootUnitDialog;
import com.centanet.hk.aplus.Views.Dialog.SortDialog;
import com.centanet.hk.aplus.Views.TransListFragment.present.ITransPresenter;
import com.centanet.hk.aplus.Views.TransListFragment.present.TransPresenter;
import com.centanet.hk.aplus.Views.basic.BaseFragment;
import com.centanet.hk.aplus.Views.basic.BaseListAdapter;
import com.centanet.hk.aplus.Widgets.PropertyDetail.PropertyTransactionRecordView;
import com.centanet.hk.aplus.Widgets.SmallItemView;
import com.centanet.hk.aplus.bean.detail.PropertyTransaction;
import com.centanet.hk.aplus.bean.district.DistrictItem;
import com.centanet.hk.aplus.bean.http.AHeaderDescription;
import com.centanet.hk.aplus.bean.translist.TransListRequest;
import com.centanet.hk.aplus.bean.translist.Transaction;
import com.centanet.hk.aplus.manager.ApplicationManager;
import com.centanet.hk.aplus.manager.PropertyRequestParamsManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yangzm4 on 2018/8/9.
 */

public class TransListFragment extends BaseFragment implements ITransListView, View.OnClickListener {

    private View back, area, flootUnit, more, sort, save, allOption, search, mic;
    private ListView lv;
    private SmartRefreshLayout refreshLayout;
    private TextView flootTxt, unitTxt, areaTipTxt, optionTipTxt;
    private TransListAdapter adapter;
    private List<Transaction> transactionList;
    private TextView currenPositionTxt;
    private TransListRequest request;
    private ITransPresenter presenter;
    private AHeaderDescription header;
    private int count;
    private int sortDialogSelectId;

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
        return view;
    }

    private void initLisenter() {

        sort.setOnClickListener(this);
        area.setOnClickListener(this);
        flootUnit.setOnClickListener(this);
        save.setOnClickListener(this);
        flootTxt.setOnClickListener(this);
        unitTxt.setOnClickListener(this);

        refreshLayout.setOnRefreshListener((refreshlayout) -> {
//            presenter.clearFlag();
            request.setPageIndex(1);
            presenter.getTransList(HttpUtil.URL_TRAN_LIST, header, request);
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
            presenter.getTransList(HttpUtil.URL_TRAN_LIST, header, request);
        });
    }

    private void init() {
        transactionList = new ArrayList<>();
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
        save = view.findViewById(R.id.list_txt_save);
        allOption = view.findViewById(R.id.house_rl_alloption);
        search = view.findViewById(R.id.list_txt_search);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
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
                break;
            case R.id.list_img_sort:
                showSortDialog();
                break;
            case R.id.list_txt_save:
                break;
            case R.id.house_rl_alloption:
                break;
            case R.id.list_txt_search:
                break;
            case R.id.mic:
                break;
        }
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
        dialog.show(getFragmentManager(),"");
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
            PropertyRequestParamsManager.getParams().setArea(getSelectDistrict(ApplicationManager.getDistrictItems(), list));
            refreshLayout.autoRefresh();
            setOptionCount();

        });
        areaDialog.show(getFragmentManager(), "");
    }

    private void setOptionCount() {
        try {
//            optionTip.setVisibility(View.VISIBLE);
            optionTipTxt.setText(ItemCountUtil.count(request) + "");
            if (ItemCountUtil.count(request) <= 0) {
                optionTipTxt.setVisibility(View.GONE);
                optionTipTxt.setText(R.string.dialog_price_unlimit);
            } else {
                optionTipTxt.setVisibility(View.VISIBLE);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        SortDialog sortDialog = new SortDialog(sortDialogSelectId);
        sortDialog.setOnDialogClikeLisenter(new SortDialog.onDialogOnclikeLisenter() {
            @Override
            public void onClike(Dialog dialog, int viewID, Map<String, Object> params) {
                dialog.dismiss();
//                bodyDescription.setPageIndex(1);
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
    public void refreshTransCount(int count) {
        this.count = count;
    }

    @Override
    public void onFailure() {
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
                viewHolder.transactPriceTxt.setText("$ " + data.getPrice() + getUnit(data.getStatus()));
                viewHolder.managerTxt.setText(data.getAgent());

                viewHolder.bargainStxt.setContentName(data.getTransactionDate());
                viewHolder.rentDateToStxt.setContentName(data.getRentEndDate());
                viewHolder.appointmentDateTxt.setText(data.getPrelimDate());
                viewHolder.officeDateTxt.setText(data.getFormalDate());

                viewHolder.finishDateTxt.setText(data.getCompleteDate());
                viewHolder.realSizeTxt.setText(data.getBuildSquareFoot());
                viewHolder.realPriceTxt.setText("$" + data.getGrossAveragePrice());
                viewHolder.useSizeTxt.setText(data.getUseSquareFoot());

                viewHolder.usePriceTxt.setText("$" + data.getAveragePrice());
                viewHolder.stateImg.setImageLevel(data.getStatus());
                viewHolder.statuTxt.setText(data.isTranferToConfirm() ? "已確認" : "未確認");
                viewHolder.statuTxt.setSelected(data.isTranferToConfirm());
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
            private TextView realSizeTxt, useSizeTxt, realPriceTxt, usePriceTxt;
            private SmallItemView bargainStxt, rentDateToStxt;
            private ImageView stateImg;
            public TextView confirmTxt, statuTxt;
        }
    }
}
