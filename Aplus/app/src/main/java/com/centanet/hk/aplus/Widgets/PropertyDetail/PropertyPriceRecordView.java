package com.centanet.hk.aplus.Widgets.PropertyDetail;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.Utility;
import com.centanet.hk.aplus.Views.HouseDetailView.view.IDataManager;
import com.centanet.hk.aplus.bean.detail.PriceTrust;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangzm4 on 2018/7/4.
 */

public class PropertyPriceRecordView extends LinearLayout implements IDataManager<List<PriceTrust>> {

    public static final int PROPERTY_PRICE_SALE = 1;
    public static final int PROPERTY_PRICE_RENT = 2;
    private Context context;
    private RadioGroup typeRG;
    private ListView priceListView;
    private RadioButton saleRB, rentRB;
    private List<PriceTrust> priceTrusts;
    private PriceRecordAdapter recordAdapter;
    private OnItemClickLisenter onItemClickLisenter;
    private String thiz = getClass().getSimpleName();


    public PropertyPriceRecordView(Context context) {
        this(context, null);
    }

    public PropertyPriceRecordView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PropertyPriceRecordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
        init();
        initLisenter();
    }

    private void init() {
        priceTrusts = new ArrayList<>();
        recordAdapter = new PriceRecordAdapter(context, priceTrusts);
        priceListView.setAdapter(recordAdapter);
        Utility.setListViewHeightBasedOnChildren(priceListView);
    }


    void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.item_detail_record_price, null, false);
        typeRG = view.findViewById(R.id.record_price_rg_type);
        saleRB = view.findViewById(R.id.detail_price_rad_sale);
        rentRB = view.findViewById(R.id.detail_price_rad_rent);
//        priceListView = view.findViewById(R.id.record_price_price_list);
        this.addView(view);
    }

    public void setSaleRBClickable(boolean able) {
        post(() -> {
            saleRB.setClickable(able);
            saleRB.setChecked(able);
            saleRB.setEnabled(able);
            saleRB.setBackgroundResource(R.drawable.shape_square_circle_single_left_gray);
            saleRB.setTextColor(Color.WHITE);
        });
    }

    public void setRentRBClickable(boolean able) {
        post(() -> {
            rentRB.setClickable(able);
            rentRB.setChecked(able);
            rentRB.setEnabled(able);
            rentRB.setBackgroundResource(R.drawable.shape_square_circle_single_right_gray);
            rentRB.setTextColor(Color.WHITE);
        });
    }

    public void checkType(int type) {
        switch (type) {
            case PROPERTY_PRICE_SALE:
                saleRB.setChecked(true);
                break;
            case PROPERTY_PRICE_RENT:
                rentRB.setChecked(true);
                break;
        }
    }

    private void initLisenter() {

        typeRG.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.detail_price_rad_sale:
                    if (onItemClickLisenter != null)
                        onItemClickLisenter.onClick(group.findViewById(checkedId), PROPERTY_PRICE_SALE);
                    break;
                case R.id.detail_price_rad_rent:
                    if (onItemClickLisenter != null)
                        onItemClickLisenter.onClick(group.findViewById(checkedId), PROPERTY_PRICE_RENT);
                    break;
            }
        });
    }

    @Override
    public void setData(List<PriceTrust> data) {
        L.d(thiz, data.toString() + " DataSize: " + data.size());

        post(() -> {
            if (priceTrusts != null) priceTrusts.clear();
            priceTrusts.addAll(data);
            recordAdapter.notifyDataSetChanged();
        });
    }

    public void setOnItemClickLisenter(OnItemClickLisenter onItemClickLisenter) {
        this.onItemClickLisenter = onItemClickLisenter;
    }

    class PriceRecordAdapter extends BaseAdapter {

        private List<PriceTrust> priceTrusts;
        private Context context;

        public PriceRecordAdapter(Context context, List<PriceTrust> priceTrusts) {
            this.priceTrusts = priceTrusts;
            this.context = context;
        }

        @Override
        public int getCount() {
            return priceTrusts.size() > 3 ? 3 : priceTrusts.size();
        }

        @Override
        public Object getItem(int position) {
            return priceTrusts.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public void notifyDataSetChanged() {
            L.d(thiz,"NotifyDataChange: "+priceTrusts.size()+"");
            super.notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder viewHolder = null;
            if (convertView == null) {
                view = LayoutInflater.from(context).inflate(R.layout.item_list_pricerecord, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.dateTxt = view.findViewById(R.id.item_pricelist_txt_date);
                viewHolder.priceTxt = view.findViewById(R.id.item_pricelist_txt_sale);
                viewHolder.addReduceTxt = view.findViewById(R.id.item_pricelist_txt_add_reduce);
                viewHolder.personTxt = view.findViewById(R.id.item_pricelist_txt_person);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }

            if (priceTrusts != null && !priceTrusts.isEmpty()) {
                viewHolder.dateTxt.setText(priceTrusts.get(position).getCreateTime());
                viewHolder.priceTxt.setText(priceTrusts.get(position).getChangeRate());
                viewHolder.addReduceTxt.setText(priceTrusts.get(position).getChangeRate());
                viewHolder.personTxt.setText(priceTrusts.get(position).getCreateUserName());
            }
            return view;
        }

        private class ViewHolder {
            TextView dateTxt, priceTxt, addReduceTxt, personTxt;
        }
    }

    public interface OnItemClickLisenter {
        void onClick(View v, int type);
    }
}
