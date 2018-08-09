package com.centanet.hk.aplus.Views.ComplexSearchView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioGroup;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Views.basic.BaseFragment;
import com.centanet.hk.aplus.Views.basic.BaseRecycleAdapter;
import com.centanet.hk.aplus.Views.basic.BaseViewHolder;
import com.centanet.hk.aplus.bean.build_tag.TagInfo;
import com.centanet.hk.aplus.bean.params.SystemParamItems;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yangzm4 on 2018/7/25.
 */

public class TagItemFragment extends BaseFragment{

    public static String ARGUMENT = "SysItemFragment";
    private RadioGroup group;
    private RecyclerView recyclerView;
    private SysItemAdapter adapter;
    private List<TagInfo> datas;
    private int paramsType;

    public static SysItemFragment newInstance(List<TagInfo> argument) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARGUMENT, (Serializable) argument);
        SysItemFragment contentFragment = new SysItemFragment();
        contentFragment.setArguments(bundle);
        return contentFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sysitem, null, false);
        initView(view);
        initLisenter();
        return view;
    }

    private void initLisenter() {

        adapter.setOnItemClickLisenter((v, position) -> {
            L.d("AdapterOnClick","");
            CheckBox checkBox = v.findViewById(R.id.sysitem_checkbox);
            checkBox.setChecked(!checkBox.isChecked());
        });
    }

    private void initView(View view) {

        recyclerView = view.findViewById(R.id.sys_recycle);

        datas = (List<TagInfo>) getArguments().get(ARGUMENT);
//        datas = AppSystemParamsManager.getSystemParams(paramsType).getSystemParamItems();
        L.d("SysFragment", datas.size() + "");

        adapter = new SysItemAdapter(datas);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    class SysItemAdapter extends BaseRecycleAdapter<TagInfo> {

        private OnItemClickLisenter lisenter;

        public SysItemAdapter(List<TagInfo> datas) {
            super(datas);
        }

        @Override
        protected int getLayoutId() {
            return R.layout.item_list_sysitem;
        }

        @Override
        protected void bindData(BaseViewHolder holder, int position) {
            holder.getView(R.id.sysitem_ll_content).setTag(position);
            holder.getView(R.id.sysitem_ll_content).setOnClickListener(v -> {
                if (lisenter != null) lisenter.onClick(v, (int) v.getTag());
            });
            setItemText(holder.getView(R.id.sysitem_txt_title), datas.get(position).getTagChineseName());
        }

        public void setOnItemClickLisenter(OnItemClickLisenter lisenter) {
            this.lisenter = lisenter;
        }
    }

    public interface OnItemClickLisenter {
        void onClick(View view, int position);
    }



}
