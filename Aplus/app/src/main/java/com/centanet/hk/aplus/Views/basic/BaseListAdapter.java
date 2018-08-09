package com.centanet.hk.aplus.Views.basic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * Created by yangzm4 on 2018/7/9.
 */

public abstract class BaseListAdapter<T> extends BaseAdapter {

    protected Context context;
    protected List<T> datas;
    protected int layoutId;
    protected OnItemClickLisenter onItemClickLisenter;

    public BaseListAdapter(Context context, List<T> datas, int layoutId) {
        this.context = context;
        this.datas = datas;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null)
            view = LayoutInflater.from(context).inflate(layoutId, null, false);
        else view = convertView;
        return getItemView(view, convertView, position, datas);
    }

    public void setOnItemClickLisenter(OnItemClickLisenter onItemClickLisenter) {
        this.onItemClickLisenter = onItemClickLisenter;
    }

    protected abstract View getItemView(View view, View convertView, int position, List<T> datas);

    public interface OnItemClickLisenter<K> {
        void onClick(View v, int type, K data);
    }

}
