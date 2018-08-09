package com.centanet.hk.aplus.Views.ComplexSearchView;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Views.basic.BaseFragment;
import com.centanet.hk.aplus.Views.basic.BaseRecycleAdapter;
import com.centanet.hk.aplus.Views.basic.BaseViewHolder;
import com.centanet.hk.aplus.bean.build_tag.TagCategory;
import com.centanet.hk.aplus.bean.build_tag.TagInfo;
import com.centanet.hk.aplus.manager.AppSystemParamsManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by yangzm4 on 2018/7/24.
 */

public class FacilityFragment extends BaseFragment implements View.OnClickListener {

    public static String ARGUMENT = "SysItemFragment";
    private View ssd, ssd10;
    private LinearLayout content;
    private RecyclerView recyclerView;
    private SysItemAdapter adapter;
    private List<String> selectList;
    private List<TagCategory> tagCategories;
    private List<TagInfo> tagInfos;
    private List<String> facList;
    private LinearLayout titleContent;
    private int currentPostion;
    private OnFacilityChangeLisenter onFacilityChangeLisenter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFacilityChangeLisenter) {
            onFacilityChangeLisenter = (OnFacilityChangeLisenter) context;
        } else
            throw new IllegalArgumentException("Activity  Must Implements StatuFragment.OnStatuChangeLisenter");
    }

    public static FacilityFragment newInstance(List<String> argument) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(ARGUMENT, (ArrayList<String>) argument);
        FacilityFragment contentFragment = new FacilityFragment();
        contentFragment.setArguments(bundle);
        return contentFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_factility, null, false);
        init();
        initView(view);
        initLisenter();
        return view;
    }

    private void init() {
        selectList = getArguments().getStringArrayList(ARGUMENT);
        if (selectList == null) selectList = new ArrayList<>();

        tagCategories = AppSystemParamsManager.getTagCategorys();
        tagInfos = new ArrayList<>();
        tagInfos.addAll(tagCategories.get(0).getTagInfos());

        facList = new ArrayList<>();
        for (TagCategory tagCategory : tagCategories) {
            facList.add(tagCategory.getChineseName());
        }
    }


    private void initLisenter() {

        adapter.setOnItemClickLisenter((v, position) -> {
            L.d("AdapterOnClick", "");
            CheckBox checkBox = v.findViewById(R.id.sysitem_checkbox);
            checkBox.setChecked(!checkBox.isChecked());
            String key = tagInfos.get(position).getTagKeyId();

            if (checkBox.isChecked()) {
                if (!selectList.contains(key))
                    selectList.add(key);
            } else selectList.remove(key);

//            List<String> keys = new ArrayList<>();
//            for (TagInfo info : tagInfos) {
//                keys.add(info.getTagKeyId());
//            }
//            keys.addAll(selectList);
//            int count = keys.size();
//            HashSet<String> hs = new HashSet<String>(keys);
//            L.d("Count", "AllCount: " + count + " SameCount: " + (count - hs.size()));
            if (onFacilityChangeLisenter != null)
                onFacilityChangeLisenter.onFacilityChange(selectList);
            setCount(currentPostion, getCount(tagInfos, selectList));
        });
    }


    private int getCount(List<TagInfo> tagInfos, List<String> selectList) {
        List<String> keys = new ArrayList<>();
        for (TagInfo info : tagInfos) {
            keys.add(info.getTagKeyId());
        }
        keys.addAll(selectList);
        int count = keys.size();
        HashSet<String> hs = new HashSet<String>(keys);
        return count - hs.size();
    }

    private void setCount(int position, int count) {
        View view = titleContent.getChildAt(position);
        TextView countTxt = view.findViewById(R.id.facility_txt_count);
        if (count == 0) countTxt.setVisibility(View.GONE);
        else {
            countTxt.setVisibility(View.VISIBLE);
            countTxt.setText(count + "");
        }
    }

    private void initView(View view) {

        recyclerView = view.findViewById(R.id.facility_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new SysItemAdapter(tagInfos, selectList);
        recyclerView.setAdapter(adapter);

        titleContent = view.findViewById(R.id.facility_ll_content);
        addItems(facList);
    }

    private void addItems(List<String> facList) {
        for (int i = 0; i < facList.size(); i++) {
            String str = facList.get(i);
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_facility, null, false);
            if (i == 0) view.setSelected(true);
            TextView textView = view.findViewById(R.id.facility_txt_title);
            textView.setText(str);
            view.setTag(i);
            view.setOnClickListener(v -> {
                resetView();
                L.d("addItems", "Factility Item Click" + v.getTag());
                v.setSelected(!v.isSelected());
                if (tagInfos != null) {
                    tagInfos.clear();
                    tagInfos.addAll(tagCategories.get((Integer) v.getTag()).getTagInfos());
                    currentPostion = (int) v.getTag();
                    adapter.notifyDataSetChanged();
                }
            });
            int count = getCount(tagCategories.get(i).getTagInfos(), selectList);
            TextView countTxt = view.findViewById(R.id.facility_txt_count);
            if (count == 0) countTxt.setVisibility(View.GONE);
            else {
                countTxt.setVisibility(View.VISIBLE);
                countTxt.setText(count + "");
            }

            titleContent.addView(view);

//            setCount(i, getCount(tagCategories.get(i).getTagInfos(), selectList));
            L.d("Count", getCount(tagCategories.get(i).getTagInfos(), selectList) + "");
        }
    }

    private void resetView() {
        int count = titleContent.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = titleContent.getChildAt(i);
            if (view instanceof RelativeLayout)
                view.setSelected(false);
        }
    }

    @Override
    public void onClick(View v) {
    }


    class SysItemAdapter extends BaseRecycleAdapter<TagInfo> {

        private OnItemClickLisenter lisenter;
        private List<String> selects;

        public SysItemAdapter(List<TagInfo> datas) {
            super(datas);
        }

        public SysItemAdapter(List<TagInfo> datas, List<String> selects) {
            super(datas);
            this.selects = selects;
        }

        @Override
        protected int getLayoutId() {
            return R.layout.item_list_factility;
        }

        @Override
        protected void bindData(BaseViewHolder holder, int position) {
            holder.getView(R.id.sysitem_ll_content).setTag(position);
            holder.getView(R.id.sysitem_ll_content).setOnClickListener(v -> {
                if (lisenter != null) lisenter.onClick(v, (int) v.getTag());
            });
            ((CheckBox) holder.getView(R.id.sysitem_checkbox)).setChecked(false);
            if (selects != null) {
                if (selects.contains(datas.get(position).getTagKeyId()))
                    ((CheckBox) holder.getView(R.id.sysitem_checkbox)).setChecked(true);
            }
            setItemText(holder.getView(R.id.sysitem_txt_title), datas.get(position).getTagChineseName());
        }

        public void setOnItemClickLisenter(OnItemClickLisenter lisenter) {
            this.lisenter = lisenter;
        }
    }

    public interface OnItemClickLisenter {
        void onClick(View view, int position);
    }

    public interface OnFacilityChangeLisenter {
        void onFacilityChange(List<String> tags);
    }

}
