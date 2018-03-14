package com.centanet.hk.aplus.Views.abst;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.centanet.hk.aplus.Adapter.PagerAdapter;
import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Views.basic.BasicActivty;

/**
 * Created by mzh1608258 on 2018/1/3.
 */

public abstract class DetailActivityAbst extends BasicActivty {


    private ViewPager vp;
    private TabLayout tab;
    private FragmentPagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_result_detail);
        initViews();

    }

    private void initViews() {
        vp = this.findViewById(R.id.detail_activity_viewpager);
        tab = this.findViewById(R.id.detail_activity_tablayout);
        adapter = new PagerAdapter(getSupportFragmentManager(), setFragments());
        vp.setAdapter(adapter);
        tab.setupWithViewPager(vp);

        initFragmentTitles();

    }

    private void initFragmentTitles() {
        tab.removeAllTabs();
        for (int i = 0; i < setFragemtsTitle().length; i++) {
            tab.addTab(tab.newTab().setText(setFragemtsTitle()[i]));
        }
    }

    protected abstract Fragment[] setFragments();

    protected abstract String[] setFragemtsTitle();

}
