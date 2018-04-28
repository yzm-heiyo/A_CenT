package com.centanet.hk.aplus.Views.HouseFragment;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.TextUtil;
import com.centanet.hk.aplus.bean.house.Properties;

import java.util.List;


/**
 * 房源列表視圖的base類
 */
public class BaseHouseFragment extends Fragment {

    protected boolean isVisible = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
//            presenter.doPost(HttpUtil.URL_PATH, aHeaderDescription, bodyDescription);
            isVisible = true;
        } else {
            //相当于Fragment的onPause
            isVisible = false;
        }
    }


    protected static class ItemAdapter extends BaseAdapter implements View.OnClickListener {

        private Context context;
        private OnItemClickListener mOnItemClickListener;
        private int mPosition;
        private List<Properties> dataList;
        private boolean showGreenTabView;

        public ItemAdapter(Context context, List<Properties> data) {
            //todo 存在一個問題 爲何使用EventBus會導致listData指向不一致
            this.context = context;
            dataList = data;
        }

        @Override
        public int getCount() {
            if (dataList.size() != 0) return dataList.size();
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
                view = LayoutInflater.from(context).inflate(R.layout.item_houselistitem, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.iconView = view.findViewById(R.id.item_resultitem_heart_img);
                viewHolder.iconView.setOnClickListener(this);
                viewHolder.iconView.setTag(position);
                viewHolder.greenTabView = view.findViewById(R.id.item_view_green_tab);
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
                viewHolder.greenPriceTxt = view.findViewById(R.id.item_txt_green);

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
            if (!dataList.isEmpty()) {

                clearViewState(viewHolder);

                Properties properties = dataList.get(position);
                L.d("properties", properties.toString());
                viewHolder.codeTxt.setText(properties.getPropertyNo());
                String floor = properties.getFloor() == null || properties.getFloor().equals("") ? "" : properties.getFloor();
                viewHolder.buildTxt.setText(properties.getEstateName() + " " + properties.getBuildingName() + " " + floor + " " + (properties.getHouseNo() == null ? "" : properties.getHouseNo() + "室"));
                viewHolder.tipsTxt.setText(properties.getPrompt());
                viewHolder.rentTxt.setText("$" + properties.getRentPrice());
                viewHolder.priceTxt.setText("$" + properties.getSalePrice());
                viewHolder.dateTxt.setText(properties.getLastFollowDate());
                viewHolder.placeTxt.setText(properties.getPropertyInterval());
                viewHolder.reallyTxt.setText(properties.getSquareFoot());
                viewHolder.useRveSaleTxt.setText(properties.getActualSalePriceUnit());

                viewHolder.useRveRentTxt.setText(properties.getActualRentPriceUnit());
                viewHolder.directionTxt.setText(properties.getHouseDirection());
                viewHolder.reallyRveSaleTxt.setText(properties.getSalePriceUnit());
                viewHolder.reallyRveRentTxt.setText(properties.getRentPriceUnit());
                String salePricePremiumUnpaid = properties.getSalePricePremiumUnpaid();
                viewHolder.greenTabView.setVisibility(showGreenTabView && salePricePremiumUnpaid != null && !salePricePremiumUnpaid.equals("") ? View.VISIBLE : View.INVISIBLE);

                viewHolder.greenPriceTxt.setText("$" + properties.getSalePricePremiumUnpaid());

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

                viewHolder.iconHot.setSelected(properties.getHotList() == null || properties.getHotList().equals("") ? false : true);
                viewHolder.iconL.setSelected(!properties.getIsConfirmed());
                viewHolder.iconD.setSelected(properties.getDevelopmentEndCredits());
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

        private void clearViewState(ViewHolder viewHolder) {

            viewHolder.codeTxt.setText("");
            viewHolder.buildTxt.setText("");
            viewHolder.tipsTxt.setText("");
            viewHolder.rentTxt.setText("");
            viewHolder.priceTxt.setText("");
            viewHolder.dateTxt.setText("");
            viewHolder.placeTxt.setText("");
            viewHolder.reallyTxt.setText("");
            viewHolder.useRveSaleTxt.setText("");

            viewHolder.useRveRentTxt.setText("");
            viewHolder.directionTxt.setText("");
            viewHolder.reallyRveSaleTxt.setText("");
            viewHolder.reallyRveRentTxt.setText("");

            viewHolder.useTxt.setText("");

            viewHolder.iconSingle.setSelected(false);
            viewHolder.iconFavo.setSelected(false);
            viewHolder.iconView.setSelected(false);
            viewHolder.iconO.setSelected(false);
            viewHolder.iconKey.setImageLevel(1);

            viewHolder.iconHot.setSelected(false);
            viewHolder.iconL.setSelected(false);
            viewHolder.iconD.setSelected(false);

            viewHolder.ssdTxt.setVisibility(View.GONE);
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
            View greenTabView;
            TextView buildTxt, placeTxt, codeTxt, priceTxt, rentTxt, tipsTxt, dateTxt, ssdTxt;
            TextView directionTxt, useTxt, useRveSaleTxt, useRveRentTxt, reallyTxt, reallyRveSaleTxt, reallyRveRentTxt, greenPriceTxt;
            ImageView iconHot, iconKey, iconO, iconL, iconD, iconSingle, iconFavo;
        }

        public void setShowGreenTabView(boolean showGreenTabView) {
            this.showGreenTabView = showGreenTabView;
        }

        public void updateView(int position, ListView listView, boolean b) {
            int visibleFirstPosi = listView.getFirstVisiblePosition();
            int visibleLastPosi = listView.getLastVisiblePosition();
            if (position >= visibleFirstPosi && position <= visibleLastPosi) {
                View view = listView.getChildAt(position - visibleFirstPosi);
                ViewHolder holder = (ViewHolder) view.getTag();
                holder.iconView.setSelected(b);
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
