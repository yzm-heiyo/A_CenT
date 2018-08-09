package com.centanet.hk.aplus.Widgets.PropertyDetail;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Views.HouseDetailView.view.IDataManager;
import com.centanet.hk.aplus.Widgets.SmallItemView;
import com.centanet.hk.aplus.bean.detail.DetailDataInformation;

import static android.content.ContentValues.TAG;

/**
 * Created by yangzm4 on 2018/7/4.
 */

public class PropertyDataInformationView extends LinearLayout implements IDataManager<DetailDataInformation> {

    private Context context;
    private TextView markTxt, centaPercentTxt, phonePercentTxt;
    private TextView bargainTxt, priceTxt, ownerTxt;


    public PropertyDataInformationView(Context context) {
        this(context, null);
    }

    public PropertyDataInformationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PropertyDataInformationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.item_detail_record_date_information, null, false);
        ownerTxt = view.findViewById(R.id.datainfo_txt_owner);
        priceTxt = view.findViewById(R.id.datainfo_txt_transprice);
        bargainTxt = view.findViewById(R.id.datainfo_txt_bargain);
        phonePercentTxt = view.findViewById(R.id.datainfo_txt_phonepercent);
        centaPercentTxt = view.findViewById(R.id.datainfo_txt_centapercent);
        markTxt = view.findViewById(R.id.datainfo_txt_marktrans);
        this.addView(view);
    }

    @Override
    public void setData(DetailDataInformation data) {

        String estate = data.getEstate();
        if (estate != null && estate.length() > 0) {
            markTxt.setText(estate.substring(estate.indexOf("共") + 1, estate.indexOf("共") + 2));
            centaPercentTxt.setText(estate.substring(estate.indexOf("佔") + 1, estate.indexOf("佔") + 2));
            phonePercentTxt.setText(estate.substring(estate.indexOf("話") + 1, estate.indexOf("話") + 2));
        }

        String dayb = data.getDaybook();
        L.d("Datainfo", dayb);

        if (dayb != null && dayb.length() > 0) {
            bargainTxt.setText(dayb.substring(0, dayb.indexOf(" ")));
            priceTxt.setText(dayb.substring(dayb.indexOf(" ") + 1, dayb.lastIndexOf(";")));
            ownerTxt.setText(dayb.substring(dayb.lastIndexOf(";") + 1));
        }
    }
}
