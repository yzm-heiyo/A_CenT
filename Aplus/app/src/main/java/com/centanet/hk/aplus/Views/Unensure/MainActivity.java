package com.centanet.hk.aplus.Views.Unensure;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.centanet.hk.aplus.Views.abst.MainActivityAbst;
import com.centanet.hk.aplus.Views.HousetListView.view.HouseListFragment;

/**
 * Created by mzh1608258 on 2018/1/2.
 */

public class MainActivity extends MainActivityAbst {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TabLayout tabLayout;

    }

    /**
     * 主页面
     * @return
     */
    @Override
    protected Fragment[] setFragments() {

        Fragment[] fragments = new Fragment[3];
        fragments[0] = new HouseListFragment();
        fragments[1] = new HouseListFragment();
        fragments[2] = new MineFragment();

        return fragments;
    }
}
