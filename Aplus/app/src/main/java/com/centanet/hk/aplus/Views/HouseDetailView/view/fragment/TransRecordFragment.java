package com.centanet.hk.aplus.Views.HouseDetailView.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Views.HouseDetailView.view.IDataManager;
import com.centanet.hk.aplus.Widgets.SmallItemView;
import com.centanet.hk.aplus.bean.detail.PropertyFollow;
import com.centanet.hk.aplus.bean.detail.PropertyTransaction;

import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by yangzm4 on 2018/7/13.
 */

public class TransRecordFragment extends Fragment implements IDataManager<List<PropertyTransaction>> {

    private TextView noDataTxt, transAllTxt;
    private LinearLayout contentView;
    private LayoutInflater inflater;
    private View progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.inflater = inflater;
        View view = inflater.inflate(R.layout.item_detail_record_transaction, null, false);
        contentView = view.findViewById(R.id.trans_ly_trans);
        progressBar = view.findViewById(R.id.progressBar);
        initView(view);

        return view;
    }

    private void initView(View view) {
        noDataTxt = view.findViewById(R.id.record_txt_nodata);
        transAllTxt = view.findViewById(R.id.trans_txt_all);
    }

    public void resetFragment(){
        progressBar.setVisibility(View.VISIBLE);
        transAllTxt.setVisibility(View.GONE);
        contentView.removeAllViews();
    }

    @Override
    public void setData(List<PropertyTransaction> data) {
        progressBar.setVisibility(View.GONE);
        contentView.removeAllViews();
        if (data == null || data.isEmpty()) {
            noDataTxt.setVisibility(VISIBLE);
            transAllTxt.setVisibility(GONE);
            return;
        }

        noDataTxt.setVisibility(GONE);
        transAllTxt.setVisibility(VISIBLE);
        addItem(data);
    }

    private void addItem(List<PropertyTransaction> data) {
        int size = data.size() > 3 ? 3 : data.size();
        for (int i = 0; i < size; i++) {
            PropertyTransaction trans = data.get(i);
            View view = inflater.inflate(R.layout.item_list_transtractrecord, null, false);

            ((TextView) view.findViewById(R.id.detail_transact_txt_proxy)).setText(trans.getAgency());
            ((TextView) view.findViewById(R.id.detail_transact_txt_transactprice)).setText("$ " + trans.getPrice() + getUnit(trans.getStatus()));
            ((TextView) view.findViewById(R.id.detail_transact_txt_manager)).setText(trans.getAgent());
            ((TextView) view.findViewById(R.id.detail_transact_txt_createtime)).setText("建立日期 " + trans.getCreateTime());

            ((TextView) view.findViewById(R.id.detail_transact_txt_appointmentdate)).setText(trans.getPrelimDate());

            ((TextView) view.findViewById(R.id.detail_transact_txt_officedate)).setText(trans.getFormalDate());
            ((TextView) view.findViewById(R.id.detail_transact_txt_finishdate)).setText(trans.getCompleteDate());
            ((TextView) view.findViewById(R.id.detail_transact_txt_realysize)).setText(trans.getBuildSquareFoot());
            ((TextView) view.findViewById(R.id.detail_transact_txt_reallyprice)).setText("$" + trans.getGrossAveragePrice());
            ((TextView) view.findViewById(R.id.detail_transact_txt_usesize)).setText(trans.getUseSquareFoot());

            ((TextView) view.findViewById(R.id.detail_transact_txt_useprice)).setText("$" + trans.getAveragePrice());
            ((SmallItemView) view.findViewById(R.id.detail_transact_stxt_bargain)).setContentName(trans.getTransactionDate());
            ((SmallItemView) view.findViewById(R.id.detail_transact_stxt_rentdateto)).setContentName(trans.getRentEndDate());
            ((ImageView) view.findViewById(R.id.detail_transact_img_state)).setImageLevel(trans.getStatus());

            ((TextView) view.findViewById(R.id.trans_txt_status)).setText(getStatuTxt(trans.getStatus()));
            ((TextView) view.findViewById(R.id.trans_txt_confirm)).setText(trans.isTranferToConfirm() ? "已確認" : "未確認");
            view.findViewById(R.id.trans_txt_confirm).setSelected(trans.isTranferToConfirm());


            contentView.addView(view);
        }
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

}
