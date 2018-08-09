package com.centanet.hk.aplus.Widgets.PropertyDetail;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Views.HouseDetailView.view.IDataManager;
import com.centanet.hk.aplus.bean.detail.PriceTrust;
import com.centanet.hk.aplus.bean.detail.PropertyFollow;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangzm4 on 2018/7/4.
 */

public class PropertyFollowView extends LinearLayout implements IDataManager<List<PropertyFollow>> {

    public static final int PROPERTY_FOLLOW_ADD = 0;
    public static final int PROPERTY_FOLLOW_ALL = 0;

    private Context context;
    private TextView followAddTxt, followAllTxt;
    private ListView followListView;
    private OnItemClickLisenter onItemClickLisenter;
    private List<PropertyFollow> propertyFollows;
    private FollowAdapter adapter;

    public PropertyFollowView(Context context) {
        this(context, null);
    }

    public PropertyFollowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PropertyFollowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
        initListener();
        init();
    }

    private void init() {
        propertyFollows = new ArrayList<>();
        adapter = new FollowAdapter(context, propertyFollows);
        followListView.setAdapter(adapter);
    }


    void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.item_detail_follow_top, null, false);
        followAddTxt = view.findViewById(R.id.detail_follow_txt_add);
        followAllTxt = view.findViewById(R.id.detail_follow_txt_all);
//        followListView = view.findViewById(R.id.detail_follow_list);
        this.addView(view);
    }

    @Override
    public void setData(List<PropertyFollow> data) {
        if (propertyFollows != null) propertyFollows.clear();
        propertyFollows.addAll(data);
        post(() -> adapter.notifyDataSetChanged());
    }


    private void initListener() {
    }

    public void setOnItemClickLisenter(OnItemClickLisenter onItemClickLisenter) {
        this.onItemClickLisenter = onItemClickLisenter;
    }

    class FollowAdapter extends BaseAdapter {

        private List<PropertyFollow> follows;
        private Context context;

        public FollowAdapter(Context context, List<PropertyFollow> priceTrusts) {
            this.follows = priceTrusts;
            this.context = context;
        }

        @Override
        public int getCount() {
            return follows.size();
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
                view = LayoutInflater.from(context).inflate(R.layout.item_follow_item, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.timeTxt = view.findViewById(R.id.follow_txt_time);
                viewHolder.personTxt = view.findViewById(R.id.follow_txt_follower);
                viewHolder.contentTxt = view.findViewById(R.id.follow_txt_content);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }

            if (follows != null && !follows.isEmpty()) {
                viewHolder.timeTxt.setText(follows.get(position).getFollowTime());
                viewHolder.contentTxt.setText(follows.get(position).getFollowContent());
                viewHolder.personTxt.setText(follows.get(position).getFollower());
            }

            return view;
        }

        private class ViewHolder {
            TextView timeTxt, personTxt, contentTxt;
        }
    }

    interface OnItemClickLisenter {
        void onClick(View v, int type);
    }
}
