package com.centanet.hk.aplus.Views.MainActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Views.Dialog.SimpleTipsDialog;
import com.centanet.hk.aplus.Views.FavoListView.view.FavoriteFragment;
import com.centanet.hk.aplus.Views.abst.MainActivityAbst;
import com.centanet.hk.aplus.Views.HousetListView.view.HouseListFragment;
import com.centanet.hk.aplus.Views.MineView.view.MineFragment;
import com.centanet.hk.aplus.manager.ApplicationManager;
import com.githang.statusbar.StatusBarCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mzh1608258 on 2018/1/2.
 */

public class MainActivity extends MainActivityAbst {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TabLayout tabLayout;
        StatusBarCompat.setStatusBarColor(this, Color.parseColor("#BB2E2D"), false);
    }


    /**
     * 主页面
     *
     * @return
     */
    @Override
    protected List<Fragment> setFragments() {

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new HouseListFragment());

        FavoriteFragment favoriteFragment = new FavoriteFragment();

//        Bundle b = new Bundle();
//        boolean isFavorite = true;
//        b.putBoolean("isFavorite", isFavorite);
//        favoriteFragment.setArguments(b);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
