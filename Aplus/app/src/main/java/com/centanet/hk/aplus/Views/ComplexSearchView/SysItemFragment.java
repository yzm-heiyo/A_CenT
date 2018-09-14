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
import com.centanet.hk.aplus.bean.params.SystemParamItems;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangzm4 on 2018/7/24.
 */

public class SysItemFragment extends BaseFragment {

    public static String ARGUMENT = "SysItemFragment";
    public static String SELECTLIST = "SelectList";
    private RadioGroup group;
    private RecyclerView recyclerView;
    private SysItemAdapter adapter;
    private List<SystemParamItems> datas;
    private List<String> selects;
    private int paramsType;

//    public static SysItemFragment newInstance(List<SystemParamItems> argument, List<String> selectList) {
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(ARGUMENT, (Serializable) argument);
//        bundle.putStringArrayList(SELECTLIST, (ArrayList<String>) selectList);
//        SysItemFragment contentFragment = new SysItemFragment();
//        contentFragment.setArguments(bundle);
//        return contentFragment;
//    }

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
            L.d("AdapterOnClick", "");
            CheckBox checkBox = v.findViewById(R.id.sysitem_checkbox);
            checkBox.setChecked(!checkBox.isChecked());
            oniTtemClick(checkBox.isChecked(), position, datas, selects);
        });
    }

    private void initView(View view) {

        recyclerView = view.findViewById(R.id.sys_recycle);

        datas = (List<SystemParamItems>) getArguments().get(ARGUMENT);
        L.d("House_PropertyTypes",datas.toString());
        selects = (List<String>) getArguments().get(SELECTLIST);
//        datas = AppSystemParamsManager.getSystemParams(paramsType).getSystemParamItems();
        if (selects == null) selects = new ArrayList<>();
        L.d("SysFragment", selects.size() + "");

        adapter = new SysItemAdapter(datas, selects);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    protected void oniTtemClick(boolean checked, int position, List<SystemParamItems> datas, List<String> selects) {
    }


    class SysItemAdapter extends BaseRecycleAdapter<SystemParamItems> {

        private OnItemClickLisenter lisenter;
        private List<String> selects;

        public SysItemAdapter(List<SystemParamItems> datas) {
            super(datas);
        }

        public SysItemAdapter(List<SystemParamItems> datas, List<String> selects) {
            super(datas);
            this.selects = selects;
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
            ((CheckBox) holder.getView(R.id.sysitem_checkbox)).setChecked(false);
            if (selects != null) {
                if (selects.contains(datas.get(position).getItemValue()))
                    ((CheckBox) holder.getView(R.id.sysitem_checkbox)).setChecked(true);
            }
            setItemText(holder.getView(R.id.sysitem_txt_title), datas.get(position).getItemText());
        }

        public void setOnItemClickLisenter(OnItemClickLisenter lisenter) {
            this.lisenter = lisenter;
        }
    }

    public interface OnItemClickLisenter {
        void onClick(View view, int position);
    }

}
