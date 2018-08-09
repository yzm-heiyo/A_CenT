package com.centanet.hk.aplus.Views.basic;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangzm4 on 2018/7/23.
 */

/**
 * 封装ViewHolder ,子类可以直接使用
 */
public class BaseViewHolder extends RecyclerView.ViewHolder{

    private Map<Integer, View> mViewMap;

    public BaseViewHolder(View itemView) {
        super(itemView);
        mViewMap = new HashMap<>();
    }

    /**
     * 获取设置的view
     * @param id
     * @return
     */
    public View getView(int id) {
        View view = mViewMap.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            mViewMap.put(id, view);
        }
        return view;
    }
}
