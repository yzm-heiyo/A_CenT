package com.centanet.hk.aplus.Views.MainActivity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.centanet.hk.aplus.Views.FavoriteView.view.FavoriteFragment;
import com.centanet.hk.aplus.Views.abst.MainActivityAbst;
import com.centanet.hk.aplus.Views.HousetListView.view.HouseListFragment;
import com.centanet.hk.aplus.Views.MineView.view.MineFragment;

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

        FavoriteFragment favoriteFragment = new FavoriteFragment();

        Bundle b = new Bundle();
        boolean isFavorite = true;
        b.putBoolean("isFavorite", isFavorite);
        favoriteFragment.setArguments(b);
        fragments[1] = favoriteFragment;
        fragments[2] = new MineFragment();

        return fragments;
    }
}
