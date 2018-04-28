package com.centanet.hk.aplus.Views.MainActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.centanet.hk.aplus.Adapter.PagerAdapter;
import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Views.Dialog.SimpleTipsDialog;
import com.centanet.hk.aplus.Views.FavoListView.view.FavoriteFragment;
import com.centanet.hk.aplus.Views.HousetListView.view.HouseListFragment;
import com.centanet.hk.aplus.Views.MineView.view.MineFragment;
import com.centanet.hk.aplus.Views.basic.BasicActivty;
import com.centanet.hk.aplus.manager.ApplicationManager;
import com.githang.statusbar.StatusBarCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mzh1608258 on 2018/1/2.
 */

public class MainActivity extends BasicActivty {


    private ViewPager pager;
    private RadioGroup rg;
    private List<Fragment> fragments;
    private FragmentPagerAdapter adapter;
    private List<RadioButton> rbs;
    private String thiz = getClass().getSimpleName();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        initViews();
        init();

        TabLayout tabLayout;
        StatusBarCompat.setStatusBarColor(this, Color.parseColor("#BB2E2D"), false);
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
            } else if (rg.getChildCount() != fragments.size()) {
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
     * 主页面
     *
     * @return
     */

    protected List<Fragment> setFragments() {

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new HouseListFragment());
        FavoriteFragment favoriteFragment = new FavoriteFragment();
        fragments.add(favoriteFragment);
        fragments.add(new MineFragment());

        return fragments;
    }

    @Override
    protected void onResume() {
        super.onResume();
        int updateType = ApplicationManager.getApplication().getUpdateType();
        String updateUrl = ApplicationManager.getApplication().getUpdateUrl();
        if (updateType != 0) {
            SimpleTipsDialog simpleTipsDialog = new SimpleTipsDialog();
            if (ApplicationManager.getApplication().getClientVer() < 110) return;
            if (updateType == 2) {
                simpleTipsDialog.setDialogCancelOnTouchOutside(false);
                simpleTipsDialog.setContentString("軟件需要更新才能使用");
                simpleTipsDialog.setLeftBtnVisibility(false);
                simpleTipsDialog.ableToKeyBack(false);
            } else if (updateType == 1) {
                simpleTipsDialog.setContentString("是否更新软件");
            }

            simpleTipsDialog.setOnItemclickListener(new SimpleTipsDialog.OnItemClickListener() {
                @Override
                public void onClick(DialogFragment dialog, int type) {
                    dialog.dismiss();
                    if (type == SimpleTipsDialog.DIALOG_YES) {
                        Uri uri = Uri.parse(updateUrl);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                }
            });
            simpleTipsDialog.show(getSupportFragmentManager(), "");
        }
    }

    private void setAdapter() {
        adapter = new PagerAdapter(getSupportFragmentManager(), fragments);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(fragments.size());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
