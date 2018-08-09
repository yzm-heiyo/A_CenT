package com.centanet.hk.aplus.Views.basic;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.centanet.hk.aplus.Utils.L;


/**
 * Created by yangzm4 on 2018/6/29.
 */

public class BaseFragment extends Fragment {

    private static final String STATE_SAVE_OR_HIDDEN = "STATE_SAVE_OR_HIDDEN";
    protected OnFragmentInteractionListener onFragmentInteractionListener;
    private String thiz = getClass().getSimpleName();

    protected boolean isVisible = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
//            presenter.doPost(HttpUtil.URL_PATH, aHeaderDescription, bodyDescription);
            isVisible = true;
            L.d(thiz, "setUserVisibleHint");
            isShowFront();
        } else {
            //相当于Fragment的onPause
            isVisible = false;
        }
    }

    protected void isShowFront() {}

    protected boolean isFragmentVisible() {
        return isVisible;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            onFragmentInteractionListener = (OnFragmentInteractionListener) context;
        } else
            throw new IllegalArgumentException("Activity  Must Implements BaseFragment.OnFragmentInteractionListener");
    }

    private void init(Bundle savedInstanceState) {

        //处理内存重启导致的多个Fragment重叠问题
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_OR_HIDDEN);

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(STATE_SAVE_OR_HIDDEN, isHidden());
        super.onSaveInstanceState(outState);
    }

    public interface OnFragmentInteractionListener {
        void onInteraction(String fragmentType, Object viewData);
    }
}
