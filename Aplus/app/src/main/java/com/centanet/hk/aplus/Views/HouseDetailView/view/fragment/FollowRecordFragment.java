package com.centanet.hk.aplus.Views.HouseDetailView.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Views.HouseDetailView.view.IDataManager;
import com.centanet.hk.aplus.bean.detail.PropertyFollow;
import com.centanet.hk.aplus.manager.PermissionManager;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangzm4 on 2018/7/13.
 */

public class FollowRecordFragment extends Fragment implements IDataManager<List<PropertyFollow>> {

    private Context context;
    private TextView followAddTxt, followAllTxt;
    private OnItemClickLisenter onItemClickLisenter;
    private List<PropertyFollow> propertyFollows;
    private LinearLayout contentView;
    private LayoutInflater inflater;
    private TextView nodataTxt;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        View view = inflater.inflate(R.layout.item_detail_follow_top, null, false);
        contentView = view.findViewById(R.id.record_ly_follows);
        progressBar = view.findViewById(R.id.progressBar);
        initView(view);
        initListener();
        init();
        return view;
    }

    private void init() {
        context = getContext();
        propertyFollows = new ArrayList<>();
    }

    private void initListener() {

    }


    void initView(View contentView) {

        followAddTxt = contentView.findViewById(R.id.detail_follow_txt_add);
        followAllTxt = contentView.findViewById(R.id.detail_follow_txt_all);
        if (PermissionManager.isSearchAllPermission() && PermissionManager.followAddPermission)
            followAddTxt.setVisibility(View.VISIBLE);
        else followAddTxt.setVisibility(View.GONE);

        nodataTxt = contentView.findViewById(R.id.record_txt_nodata);

    }

    public void resetFragment() {
        progressBar.setVisibility(View.VISIBLE);
        contentView.removeAllViews();
        nodataTxt.setVisibility(View.GONE);
        followAddTxt.setVisibility(View.GONE);
        followAllTxt.setVisibility(View.GONE);
    }

    @Override
    public void setData(List<PropertyFollow> data) {
        progressBar.setVisibility(View.GONE);
        contentView.removeAllViews();

        followAllTxt.setVisibility(View.VISIBLE);

        if (!PermissionManager.isSearchAllPermission()) {//是否有查看跟进权限
            nodataTxt.setVisibility(View.VISIBLE);
            nodataTxt.setText("沒有查看跟進權限");
            followAllTxt.setVisibility(View.GONE);
            return;
        }

        if (PermissionManager.isFollowAddPermission()) {//是否有添加跟进权限
            followAddTxt.setVisibility(View.VISIBLE);
        }

        if (data == null || data.isEmpty()) {
            nodataTxt.setVisibility(View.VISIBLE);
            followAllTxt.setVisibility(View.GONE);
            return;
        }

        nodataTxt.setVisibility(View.GONE);

        if (propertyFollows != null) propertyFollows.clear();
        propertyFollows.addAll(data);
        addItem(data);
    }

    private void addItem(List<PropertyFollow> data) {
        int size = data.size() > 3 ? 3 : data.size();
        for (int i = 0; i < size; i++) {
            PropertyFollow follow = data.get(i);
            View view = inflater.inflate(R.layout.item_follow_item, null, false);
//            ((TextView) view.findViewById(R.id.follow_txt_time)).setText(follow.getFollowTime());
            ((TextView) view.findViewById(R.id.follow_txt_time)).setText(follow.getFollowTime().replace("T", " "));
            ((TextView) view.findViewById(R.id.follow_txt_follower)).setText(follow.getFollower());
            ((TextView) view.findViewById(R.id.follow_txt_content)).setText(follow.getFollowContent());
            contentView.addView(view);
        }
    }

    public void setOnItemClickLisenter(OnItemClickLisenter onItemClickLisenter) {
        this.onItemClickLisenter = onItemClickLisenter;
    }

    interface OnItemClickLisenter {
        void onClick(View v, int type);
    }

}
