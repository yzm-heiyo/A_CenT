package com.centanet.hk.aplus.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mzh1608258 on 2018/1/3.
 */

public class PagerAdapter extends FragmentPagerAdapter implements Serializable {


    private List<Fragment> fragments;


    public PagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        // TODO Auto-generated method stub
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return fragments.size();
    }

    public List<Fragment> getFragments() {
        return fragments;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
