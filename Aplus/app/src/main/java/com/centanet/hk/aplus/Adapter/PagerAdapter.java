package com.centanet.hk.aplus.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by mzh1608258 on 2018/1/3.
 */

public class PagerAdapter extends FragmentPagerAdapter {


    private Fragment[] fragments;

    public PagerAdapter(FragmentManager fm, Fragment[] fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        // TODO Auto-generated method stub
        return fragments[i];
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return fragments.length;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


}
