package com.centanet.hk.aplus.Views.Trans;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.centanet.hk.aplus.BackHandlerHelper.BackHandlerHelper;
import com.centanet.hk.aplus.BackHandlerHelper.FragmentBackHandler;
import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Views.HomePageView.view.HomePagerFragment;
import com.centanet.hk.aplus.Views.HousetListView.view.HouseFragment;
import com.centanet.hk.aplus.Views.SearchView.view.SearchFragment;
import com.centanet.hk.aplus.Views.basic.BaseFragment;
import com.centanet.hk.aplus.bean.http.HouseDescription;
import com.centanet.hk.aplus.eventbus.BUS_MESSAGE;
import com.centanet.hk.aplus.eventbus.MessageEventBus;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by yangzm4 on 2018/7/27.
 */

public class TransContentFragment extends BaseFragment implements HomePagerFragment.OnHomePagerRequestLisenter, FragmentBackHandler {

    private Fragment oldFragment;
    private List<Fragment> fragments;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homecontent, null, false);
        turnToFragment(new HomePagerFragment(), HomePagerFragment.FRAGMENT_TAG_HOMEPAGER);
        EventBus.getDefault().register(this);
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEventBus messageEvent) {
        switch (messageEvent.getMsg()) {
            case BUS_MESSAGE.HomeFastSearch.FAST_SEARCH:
                HouseFragment houseFragment = new HouseFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("HOUSE_REQUEST", (HouseDescription) messageEvent.getObject());
                houseFragment.setArguments(bundle);
                turnToFragment(houseFragment, HouseFragment.FRAGMENT_TAG_HOUSELIST);
                L.d("BUS_FAST_SEARCH", "");
                break;
        }
    }

    private void turnToFragment(Fragment fragment, String tag) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//        transaction.addToBackStack(null);
        if (oldFragment != null) {
            L.d("Hide OldFragment", "");
            transaction.hide(oldFragment);
        }
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.add(R.id.fragment_content, fragment, tag).commit();
        oldFragment = fragment;
    }

    @Override
    public void onHomePagerRequest(int type, String... data) {
        if (type == HomePagerFragment.HOME_SEARCH)
            turnToFragment(new SearchFragment(), SearchFragment.FRAGMENT_TAG_SEARCH);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onBackPressed() {
//        int backStackEntryCount = getChildFragmentManager().getBackStackEntryCount();
//        L.d("BackStackSize", backStackEntryCount + "");
//        // 回退栈中至少有多个fragment,栈底部是首页
//        if (backStackEntryCount > 1) {
//            while (getChildFragmentManager().getBackStackEntryCount() > 1) {
//                getChildFragmentManager().popBackStackImmediate();
//            }
//            return true;
//        } else return false;
        return BackHandlerHelper.handleBackPress(getChildFragmentManager());
    }
}
