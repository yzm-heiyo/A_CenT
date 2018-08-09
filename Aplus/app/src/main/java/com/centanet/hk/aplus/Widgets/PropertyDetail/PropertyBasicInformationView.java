package com.centanet.hk.aplus.Widgets.PropertyDetail;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Views.HouseDetailView.view.IDataManager;
import com.centanet.hk.aplus.Widgets.SmallItemView;
import com.centanet.hk.aplus.bean.detail.DetailDataInformation;

/**
 * Created by yangzm4 on 2018/7/4.
 */

//todo 无限View
public class PropertyBasicInformationView extends LinearLayout implements IDataManager<DetailDataInformation>{

    private Context context;
    private SmallItemView houseTxt;
    private TextView datbookTxt;

    public PropertyBasicInformationView(Context context) {
        this(context, null);
    }

    public PropertyBasicInformationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PropertyBasicInformationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.item_detail_record_date_information, null, false);

        this.addView(view);
    }

    @Override
    public void setData(DetailDataInformation data) {

    }
}
