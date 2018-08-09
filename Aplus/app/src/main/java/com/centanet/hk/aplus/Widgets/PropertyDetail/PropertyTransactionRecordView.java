package com.centanet.hk.aplus.Widgets.PropertyDetail;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Views.HouseDetailView.view.IDataManager;
import com.centanet.hk.aplus.Widgets.SmallItemView;
import com.centanet.hk.aplus.bean.detail.PropertyFollow;
import com.centanet.hk.aplus.bean.detail.PropertyTransaction;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangzm4 on 2018/7/4.
 */
//todo 无效View
public class PropertyTransactionRecordView extends LinearLayout implements IDataManager<List<PropertyTransaction>> {

    private Context context;
    private ListView listView;
    private List<PropertyTransaction> transactions;
    private TranstractAdapter transtractAdapter;
    private TextView noDataTxt;
    private TextView transAllTxt;


    public PropertyTransactionRecordView(Context context) {
        this(context, null);
    }

    public PropertyTransactionRecordView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PropertyTransactionRecordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
        init();
    }

    private void init() {
        transactions = new ArrayList<>();
        transtractAdapter = new TranstractAdapter(context, transactions);
        listView.setAdapter(transtractAdapter);
    }

    void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.item_detail_record_transaction, null, false);
//        listView = view.findViewById(R.id.detail_list_transtract);
        noDataTxt = view.findViewById(R.id.record_txt_nodata);
        transAllTxt = view.findViewById(R.id.trans_txt_all);
        this.addView(view);
    }

    @Override
    public void setData(List<PropertyTransaction> data) {

        if (data == null || data.isEmpty()) {
            listView.setVisibility(GONE);
            noDataTxt.setVisibility(VISIBLE);
            transAllTxt.setVisibility(GONE);
            return;
        }

        if (transactions != null) transactions.clear();
        transactions.addAll(data);
        post(() -> transtractAdapter.notifyDataSetChanged());
    }


    class TranstractAdapter extends BaseAdapter {

        private List<PropertyTransaction> transactions;
        private Context context;

        public TranstractAdapter(Context context, List<PropertyTransaction> transactions) {
            this.transactions = transactions;
            this.context = context;
        }

        @Override
        public int getCount() {
            return transactions.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder viewHolder = null;
            if (convertView == null) {
                view = LayoutInflater.from(context).inflate(R.layout.item_list_transtractrecord, parent, false);
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
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }

            if (transactions != null && !transactions.isEmpty()) {
                PropertyTransaction data = transactions.get(position);

                viewHolder.createTimeTxt.setText("建立日期 " + data.getCreateTime());
                viewHolder.proxyTxt.setText(data.getAgency());
                viewHolder.transactPriceTxt.setText("$ " + data.getPrice()+getUnit(data.getStatus()));
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
            private TextView proxyTxt, transactPriceTxt, managerTxt;
            private TextView createTimeTxt, appointmentDateTxt, officeDateTxt, finishDateTxt;
            private TextView realSizeTxt, useSizeTxt, realPriceTxt, usePriceTxt;
            private SmallItemView bargainStxt, rentDateToStxt;
            private ImageView stateImg;
            public TextView confirmTxt, statuTxt;
        }
    }
}
