package com.centanet.hk.aplus.Widgets.PropertyDetail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Space;
import android.widget.TextView;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.DensityUtil;
import com.centanet.hk.aplus.Views.HouseDetailView.view.IDataManager;
import com.centanet.hk.aplus.Widgets.SmallItemView;
import com.centanet.hk.aplus.bean.detail.DetailTrustor;
import com.centanet.hk.aplus.bean.detail.Trustor;
import com.centanet.hk.aplus.bean.detail.TrustorDetail;
import com.centanet.hk.aplus.common.CommandField;

import java.util.List;

/**
 * Created by yangzm4 on 2018/7/4.
 */

public class PropertyTrustorInfoView extends LinearLayout implements IDataManager<DetailTrustor> {

    private Context context;
    private LinearLayout contentView;
    private boolean isOdish;

    public PropertyTrustorInfoView(Context context) {
        this(context, null);
    }

    public PropertyTrustorInfoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PropertyTrustorInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    void initView() {
        contentView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.item_detail_clientinfo, null, false);
        this.addView(contentView);
    }

    @Override
    public void setData(DetailTrustor data) {
        addClientInfo(data);
    }


    public void setOdish(boolean odish) {
        isOdish = odish;
    }

    private void addClientInfo(DetailTrustor data) {
        if (data.getTrustors() != null && !data.getTrustors().isEmpty()) {
            List<Trustor> trustors = data.getTrustors();
            for (int i = 0; i < trustors.size(); i++) {

                post(() -> {
                    Space space = new Space(context);
                    space.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(context, 12)));
                    contentView.addView(space);
                });

                LinearLayout infoView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.item_detail_owner, null, false);

                View infoTitleView = LayoutInflater.from(context).inflate(R.layout.item_info_title, null, false);
                ((TextView) infoTitleView.findViewById(R.id.phone_txt_trustertype)).setText(trustors.get(i).getTrustorTypeName());
                infoView.addView(infoTitleView);

                View ownerView = LayoutInflater.from(context).inflate(R.layout.item_info_owner, null, false);
                ((TextView) ownerView.findViewById(R.id.phone_txt_owner)).setText(trustors.get(i).getTrustorName());
                ((TextView) ownerView.findViewById(R.id.phone_txt_ownerremark)).setText(trustors.get(i).getTrustortRemark());
                infoView.addView(ownerView);


                if (trustors.get(i).getTrustorDetails() != null) {
                    infoView.addView(getLineView());
                    addPhoneView(infoView, trustors.get(i).getTrustorDetails());
                }

                post(() -> contentView.addView(infoView));
            }
        }
    }

    private void addPhoneView(LinearLayout infoView, List<TrustorDetail> trustorDetails) {
        int size = trustorDetails.size();

        for (int i = 0; i < size; i++) {

            Space space = new Space(context);
            space.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(context, 10)));
            infoView.addView(space);

            TrustorDetail trustorDetail = trustorDetails.get(i);
//            for (int i = 0; i < 3; i++) {
            View phoneTitleView = LayoutInflater.from(context).inflate(R.layout.item_info_title, null, false);
            ((TextView) phoneTitleView.findViewById(R.id.phone_txt_trustertype)).setText(trustorDetail.getContactTypeName());
            infoView.addView(phoneTitleView);

            View tleView = LayoutInflater.from(context).inflate(R.layout.item_info_validphone, null, false);

            //如果直限类型为不允许，显示不可以
            if (trustorDetail.getDirectSellTyp() == CommandField.APPropertyTrustorDirectSell.NOTALLOW)
                tleView.findViewById(R.id.phone_ly_tips).setVisibility(VISIBLE);

            ((TextView) tleView.findViewById(R.id.phone_txt_remark)).setText(trustorDetail.getMobileRemark());
            TextView tleTxt = tleView.findViewById(R.id.phone_txt_number);
            tleTxt.setText(trustorDetail.getContactValue());
            infoView.addView(tleView);
//            }
            if (i != size - 1) infoView.addView(getLineView());
        }
    }

    private View getLineView() {
        View view = new View(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
        view.setLayoutParams(params);
        view.setBackgroundColor(Color.parseColor("#E7E7E7"));
        return view;
    }

}
