package com.centanet.hk.aplus.Views.ComplexSearchView.adapter;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.centanet.hk.aplus.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangzm4 on 2018/7/30.
 */

public class ArticleListFragment extends ListFragment {
    private ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        //定义一个数组
        List<String> data = new ArrayList<String>();
        for (int i = 0; i < 30; i++) {
            data.add("smyh" + i);
        }
        //将数组加到ArrayAdapter当中
        adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.item_list_sysitem, data);
        //绑定适配器时，必须通过ListFragment.setListAdapter()接口，而不是ListView.setAdapter()或其它方法
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }
}
