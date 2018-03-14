package com.centanet.hk.aplus.Views.abst;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.centanet.hk.aplus.Adapter.PagerAdapter;
import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Views.basic.BasicActivty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mzh1608258 on 2018/1/2.
 */

public abstract class MainActivityAbst extends BasicActivty {

    private ViewPager pager;
    private RadioGroup rg;
    private Fragment[] fragments;
    private FragmentPagerAdapter adapter;
    private List<RadioButton> rbs;
    private String thiz = getClass().getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        init();

    }

    private void initViews() {
        rg = this.findViewById(R.id.main_navigationbar);
        pager = this.findViewById(R.id.main_viewpager);

        rbs = new ArrayList<>();
        rbs.add((RadioButton) rg.findViewById(R.id.activity_main_manager));
        rbs.add((RadioButton) rg.findViewById(R.id.activity_main_collect));
        rbs.add((RadioButton) rg.findViewById(R.id.activity_main_user));
    }

    private void init() {

        fragments = this.setFragments();

        try {
            //不可為空,判定個數是否一致
            if (fragments == null) {
                throw new Exception("fragments are null!pls check");
            } else if (rg.getChildCount() != fragments.length) {
                throw new Exception("fragments and RadioGroup size are not match!pls check");
            }
            setAdapter();
        } catch (Exception e) {
            e.printStackTrace();
        }
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                for (int i = 0; i < rbs.size(); i++) {
                    if (checkedId == rbs.get(i).getId()) {
                        pager.setCurrentItem(i);
                        L.d(getClass().getSimpleName(), rbs.get(i).isChecked() + "");
                        return;
                    }
                }

            }
        });
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position <= rbs.size()) {
                    rbs.get(position).setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    /**
     * 設置Fragments個數
     *
     * @return
     */
    protected abstract Fragment[] setFragments();


    private void setAdapter() {
        adapter = new PagerAdapter(getSupportFragmentManager(), fragments);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(fragments.length);
    }

}
