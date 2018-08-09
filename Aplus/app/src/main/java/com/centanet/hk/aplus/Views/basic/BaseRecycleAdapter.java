package com.centanet.hk.aplus.Views.basic;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yangzm4 on 2018/7/23.
 */

public abstract class BaseRecycleAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    // 数据源
    protected List<T> datas;

    // 构造方法，传入
    public BaseRecycleAdapter(List<T> datas) {
        this.datas = datas;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(), parent, false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        bindData(holder, position);
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    /**
     * 设置文本属性
     *
     * @param view
     * @param text
     */
    public void setItemText(View view, String text) {
        if (view instanceof TextView) {
            ((TextView) view).setText(text);
        }
    }

    /**
     * 获取布局layout
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 绑定数据
     *
     * @param holder   具体的viewHolder
     * @param position 对应的索引
     */
    protected abstract void bindData(BaseViewHolder holder, int position);


    /**
     * 刷新数据
     *
     * @param datas
     */
    public void refresh(List<T> datas) {
        this.datas.clear();
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }


    /**
     * 添加数据
     *
     * @param datas
     */
    public void addData(List<T> datas) {
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }


    public interface OnItemClickLisenter {
        void onClick(View v, int position);
    }

}
