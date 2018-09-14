package com.centanet.hk.aplus.Views.TransHomePagerView.TransSearchView.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.centanet.hk.aplus.BackHandlerHelper.FragmentBackHandler;
import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.DensityUtil;
import com.centanet.hk.aplus.Utils.DialogUtil;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.TextUtil;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.Views.Dialog.AreaDialog;
import com.centanet.hk.aplus.Views.Dialog.FlootUnitDialog;
import com.centanet.hk.aplus.Views.Dialog.voice.VoiceInputPanel;
import com.centanet.hk.aplus.Views.HomePageView.view.HomePagerFragment;
import com.centanet.hk.aplus.Views.HousetListView.view.HouseFragment;
import com.centanet.hk.aplus.Views.TransHomePagerView.TransPagerView.view.TransHomePagerFragment;
import com.centanet.hk.aplus.Views.TransHomePagerView.TransSearchView.present.ITransSearchPresent;
import com.centanet.hk.aplus.Views.TransHomePagerView.TransSearchView.present.TransSearchPreesent;
import com.centanet.hk.aplus.Views.TransListFragment.view.TransListFragment;
import com.centanet.hk.aplus.Views.basic.BaseFragment;
import com.centanet.hk.aplus.Widgets.LineBreakLayout;
import com.centanet.hk.aplus.bean.auto_estate.PropertyParamHints;
import com.centanet.hk.aplus.bean.district.DistrictItem;
import com.centanet.hk.aplus.bean.http.AHeaderDescription;
import com.centanet.hk.aplus.bean.http.AutoSearchDescription;
import com.centanet.hk.aplus.bean.http.HouseDescription;
import com.centanet.hk.aplus.bean.save_option.PropertyRequestSaveParams;
import com.centanet.hk.aplus.bean.save_option.TransRequestSaveParams;
import com.centanet.hk.aplus.bean.translist.TransListRequest;
import com.centanet.hk.aplus.eventbus.MessageEventBus;
import com.centanet.hk.aplus.manager.ApplicationManager;
import com.centanet.hk.aplus.manager.PropertyRequestParamsManager;
import com.centanet.hk.aplus.manager.TransRequestParamsManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.HouseNavigation.HIDDEN;
import static com.centanet.hk.aplus.eventbus.BUS_MESSAGE.HouseNavigation.SHOW;


/**
 * Created by mzh1608258 on 2018/1/9.
 */

public class TransSearchFragment extends BaseFragment implements ITransSearchView, View.OnClickListener, VoiceInputPanel.EventListener, FragmentBackHandler {

    private static final int VIEW_HISRORY = 0;
    private static final int VIEW_SEARCH = 1;
    public static final String FRAGMENT_TAG_SEARCH = "SEARCH";
    private static final int VIEW_SELECT_MAX = 20;
    public static final String VIEW_SEARCH_HISTORY_SAVE = "SAVEHISTORY";
    public static final String VIEW_SEARCH_KEY_TYPE = "CONDITION";
    public static final String VIEW_SEARCH_LABEL = "LABEL";
    public static final String VIEW_SEARCH_FLOOT = "FLOOT";
    public static final String VIEW_SEARCH_UNIT = "UNIT";
    private ListView lv_history, lv_search;
    private static String keyword = "";
    private List<String> searchLabelList; //显示的简写
    private List<PropertyParamHints> oldHistoryList;
    private List<PropertyParamHints> newHistoryList;
    private AHeaderDescription headerDescription;

    private List<PropertyParamHints> dataList;
    private List<String> selectList;

    private TextView btn, clearSelectTxt, clearHistoryTxt;

    private View finish, selectLabelView, searchView, historyView, searchHistoryView;
    private LineBreakLayout searchLabelGroup;
    private String thiz = getClass().getSimpleName();
    private EditText searchEdit;
    private ITransSearchPresent present;
    private DataAdapter searchAdapter;
    private View mic;

    private TextView areaTxt, areaTipTxt, selectCountTxt;
    private View flootView;

    private TextView flootTxt, unitTxt;

    private int viewType = VIEW_HISRORY;
    private String flootStr = "", unitsStr = "";

    AutoSearchDescription autoSearchDescription;
    private DataAdapter historyAdapter;

    private View back;

    private TransListRequest request;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_search, container, false);

        init(view);
        MessageEventBus msg = new MessageEventBus();
        msg.setMsg(HIDDEN);
        EventBus.getDefault().post(msg);
        return view;
    }

    protected void init(View view) {

        present = new TransSearchPreesent(this);
        dataList = new ArrayList<>();
//        Bundle bundle = getIntent().getExtras();
        Bundle bundle = null;
        headerDescription = ApplicationManager.getApplication().getHeaderDescription();

        //todo 要改掉

        if(getArguments()!=null){
            Bundle bundle1 = getArguments();
            TransListRequest description = (TransListRequest) bundle1.get("HOUSE_REQUEST");
            request = description;
        }else   request = new TransListRequest();


        searchLabelList = new ArrayList<>();
        oldHistoryList = new ArrayList<>();
        newHistoryList = new ArrayList<>();
        autoSearchDescription = new AutoSearchDescription();
        autoSearchDescription.setMobileRequest(true);
        autoSearchDescription.setVoice(false);
        searchView = view.findViewById(R.id.search_simple_view);
        historyView = view.findViewById(R.id.search_history_view);
        searchHistoryView = view.findViewById(R.id.search_history_title);
        selectCountTxt = view.findViewById(R.id.activity_search_txt_count);

        areaTipTxt = view.findViewById(R.id.search_txt_areatip);
        areaTxt = view.findViewById(R.id.search_txt_area);
        areaTxt.setOnClickListener(this);

        flootView = view.findViewById(R.id.search_ll_floot);
        flootView.setOnClickListener(this);

        back = view.findViewById(R.id.back);
        back.setOnClickListener(v -> {
            searchEdit.clearFocus();
            searchEdit.setCursorVisible(false);
            getActivity().onBackPressed();
        });

        mic = view.findViewById(R.id.mic);
        mic.setOnClickListener(this);

        lv_search = view.findViewById(R.id.activity_search_listview);
        btn = view.findViewById(R.id.activity_search_confirm_btn);
        finish = view.findViewById(R.id.activity_search_back);
        selectLabelView = view.findViewById(R.id.search_select_layout);
        searchEdit = view.findViewById(R.id.search_edit);

        clearHistoryTxt = view.findViewById(R.id.search_history_txt_clear);
        clearHistoryTxt.setOnClickListener(this);
        clearSelectTxt = view.findViewById(R.id.select_txt_clear);
        clearSelectTxt.setOnClickListener(this);

        lv_history = view.findViewById(R.id.search_history_list);

        flootTxt = view.findViewById(R.id.search_txt_floot);
        unitTxt = view.findViewById(R.id.search_txt_unit);

        if(!TextUtil.isEmply(request.getFloors())){
            flootTxt.setText(request.getFloors());
        }

        if(!TextUtil.isEmply(request.getUnits())){
            unitTxt.setText(request.getUnits());
        }

        if(!TextUtil.isEmply(request.getDistrictListIds())){
            areaTipTxt.setText(request.getDistrictListIds().size()+"");
            areaTipTxt.setVisibility(View.VISIBLE);
        }

        searchLabelGroup = view.findViewById(R.id.search_labelgroup);
        searchLabelGroup.setItemContentLayoutID(R.layout.item_label_search);
        searchLabelGroup.setItemOnclickListener(mOnItemOnclickListener);
        searchLabelGroup.setRowSpace(30);
        searchLabelGroup.setLeftRightSpace(20);

        if (bundle != null) {
            selectList = (List<String>) bundle.get(VIEW_SEARCH_HISTORY_SAVE);
            if (selectList != null)
                present.recoverLabelHistiry(selectList);
            else selectList = new ArrayList<>();
        } else {
            selectList = new ArrayList<>();
        }

        btn.setOnClickListener(this);
        finish.setOnClickListener(this);
        searchEdit.addTextChangedListener(new EditChangedListener());

        searchEdit.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
                historyView.setVisibility(View.GONE);
                searchView.setVisibility(View.VISIBLE);
                selectCountTxt.setVisibility(View.VISIBLE);
                selectCountTxt.setText(getString(R.string.selected) + ": " + newHistoryList.size());
                viewType = VIEW_SEARCH;
            }
            return false;
        });

        View footView = LayoutInflater.from(getContext()).inflate(R.layout.list_footview, null, false);
        lv_search.addFooterView(footView, null, false);
        lv_history.addFooterView(footView, null, false);

        searchAdapter = new DataAdapter(getContext(), dataList, selectList);
        lv_search.setAdapter(searchAdapter);
        historyAdapter = new DataAdapter(getContext(), oldHistoryList);
        lv_history.setAdapter(historyAdapter);

        lv_search.setOnItemClickListener((parent, view1, position, id) -> {
            DataAdapter.ViewHolder holder = (DataAdapter.ViewHolder) view1.getTag();
            boolean check = holder.box.isChecked();
            PropertyParamHints data = dataList.get(position);
            if (check || selectList.size() < VIEW_SELECT_MAX) {
                holder.box.setChecked(!check);
                if (check) {
                    selectList.remove(data.getKeyId());
                    newHistoryList.remove(data);
                    searchLabelList = deleteSearchLableData(data, searchLabelList);
                } else if (!check) {
                    selectList.add(data.getKeyId());
                    newHistoryList.add(data);
                    addSearchLabelData(data, searchLabelList);
                    selectCountTxt.setText(getString(R.string.selected) + ": " + newHistoryList.size());
                } else
                    DialogUtil.getSimpleDialog(getString(R.string.select_max_20)).show(getFragmentManager(), "");
            }
        });

        lv_history.setOnItemClickListener((parent, view1, position, id) -> {
            PropertyParamHints data = oldHistoryList.get(position);
            addSearchLabelData(data, searchLabelList);
            if (!newHistoryList.contains(data))
                newHistoryList.add(data);
            if (!selectList.contains(data.getKeyId()))
                selectList.add(data.getKeyId());
            showHistoryLableView();
        });

        present.getSearchHistory();

        if (getArguments() != null) {
            boolean isMicToThis = getArguments().getBoolean("mic", false);
            if (isMicToThis) showVoiceInputPanel();
            else {
                searchEdit.setFocusable(true);
                searchEdit.setFocusableInTouchMode(true);
                searchEdit.requestFocus();
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
//        MessageEventBus msg = new MessageEventBus();
//        if (isVisible() && isResumed()) {
//            msg.setMsg(HIDDEN);
//        } else {
//            msg.setMsg(SHOW);
//        }
//        EventBus.getDefault().post(msg);
        MessageEventBus msg = new MessageEventBus();
        if (isVisible() && isResumed()) {
            msg.setMsg(HIDDEN);
            if (getArguments() != null) {
                boolean isMicToThis = getArguments().getBoolean("mic", false);
                if (isMicToThis) {
                    showVoiceInputPanel();
                    Bundle bundle = getArguments();
                    bundle.putBoolean("mic",false);
                    setArguments(bundle);
                }
            }

            if(getArguments()!=null){
                Bundle bundle1 = getArguments();
                TransListRequest description = (TransListRequest) bundle1.get("HOUSE_REQUEST");
                request = description;
            }

            if(!TextUtil.isEmply(request.getFloors())){
                flootTxt.setText(request.getFloors());
            }

            if(!TextUtil.isEmply(request.getUnits())){
                unitTxt.setText(request.getUnits());
            }

            if(!TextUtil.isEmply(request.getDistrictListIds())){
                areaTipTxt.setText(request.getDistrictListIds().size()+"");
                areaTipTxt.setVisibility(View.VISIBLE);
            }

        } else {
            msg.setMsg(SHOW);
        }
        EventBus.getDefault().post(msg);
    }

    public LineBreakLayout.onItemOnclickListener mOnItemOnclickListener = (view, contentView, position) -> {
        contentView.removeView(view);
        if (contentView.getChildCount() == 0) selectLabelView.setVisibility(View.GONE);
        if (!searchLabelList.isEmpty()) searchLabelList.remove(position);
        if (!selectList.isEmpty()) selectList.remove(position);
        if (!newHistoryList.isEmpty()) newHistoryList.remove(position);
    };

    private List<DistrictItem> getSelectDistrict(List<DistrictItem> items, List<String> keys) {
        List<DistrictItem> districtItems = new ArrayList<>();
        if (keys == null) return null;
        for (DistrictItem item : items) {
            if (keys.indexOf(item.getDistrictKeyId()) != -1) {
                districtItems.add(item);
            }
        }
        return districtItems;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_search_confirm_btn:
                switch (viewType) {
                    case VIEW_HISRORY:
                        present.saveSearchHistory(newHistoryList);
                        TransRequestParamsManager.getParams().setAddress(newHistoryList);
                        TransRequestParamsManager.getParams().setArea(getSelectDistrict(ApplicationManager.getDistrictItems(), request.getDistrictListIds()));
                        request.setSearcherAddress(getKeys(newHistoryList));
//                        getAddSimString();
                        FragmentTransaction ft = getParentFragment().getChildFragmentManager().beginTransaction();
                        Fragment house = getParentFragment().getChildFragmentManager().findFragmentByTag(TransListFragment.FRAGMENT_TAG_TRANSLIST);
                        if (house != null) {
                            ft.remove(house);
                        }
//                        } else {
                        Bundle bundle = new Bundle();
                        bundle.putString(VIEW_SEARCH_LABEL, getAddSimString());
                        bundle.putSerializable("HOUSE_REQUEST", request);
                        house = new TransListFragment();
                        house.setArguments(bundle);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        ft.add(R.id.fragment_content, house, TransListFragment.FRAGMENT_TAG_TRANSLIST);
//                        }
                        ft.hide(this).commit();

                        break;
                    case VIEW_SEARCH:
                        showSelectCondition();
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM );
                        btn.setLayoutParams(params);
                        btn.setBackgroundColor(getResources().getColor(R.color.colortheme));
                        break;
                }
                break;
            case R.id.activity_search_back:

                break;
            case R.id.mic:
                showVoiceInputPanel();
                break;
            case R.id.search_history_txt_clear:
                oldHistoryList.clear();
                historyAdapter.notifyDataSetChanged();
                present.deleteHistory();
                searchHistoryView.setVisibility(View.GONE);
                break;
            case R.id.select_txt_clear:
                searchLabelList.clear();
                selectList.clear();
                newHistoryList.clear();
                selectLabelView.setVisibility(View.GONE);
                searchLabelGroup.removeAllContentViews();
                break;
            case R.id.search_txt_area:
                AreaDialog areaDialog = new AreaDialog();
                areaDialog.setItem(ApplicationManager.getDistrictItems(), request.getDistrictListIds());
                areaDialog.setOnItemClickLisenter((dialog, v1, list) -> {

                    if (list != null && !list.isEmpty()) {
                        areaTipTxt.setVisibility(View.VISIBLE);
                    } else areaTipTxt.setVisibility(View.GONE);
                    request.setDistrictListIds(list);
                    areaTipTxt.setText(list.size() + "");
                });
                areaDialog.show(getFragmentManager(), "");
                break;
            case R.id.search_ll_floot:
                String[] strs = new String[2];
                strs[0] = request.getFloors();
                strs[1] = request.getUnits();
                FlootUnitDialog dialog = new FlootUnitDialog();
                dialog.setData(strs);
                dialog.setOnItemClickLisenter((dialog1, v1, list) -> {
                    if (list[0] == null || list[0].equals("")) {
                        flootTxt.setText(R.string.search_floot);
                    } else flootTxt.setText(list[0]);

                    if (list[1] == null || list[1].equals("")) {
                        unitTxt.setText(R.string.search_units);
                    } else unitTxt.setText(list[1]);

                    request.setFloors(list[0]);
                    request.setUnits(list[1]);

                });
                dialog.show(getFragmentManager(), "");

                break;
            default:
                break;
        }
    }

    private List<String> getKeys(List<PropertyParamHints> newHistoryList) {
        List<String> keys = new ArrayList<>();

        for (PropertyParamHints data : newHistoryList) {
            keys.add(data.getKeyIdType() + ":" + data.getKeyId());
        }

        return keys;
    }

    private String getAddSimString() {
        L.d("AddressLabel", searchLabelList.toString());
        StringBuilder builder = new StringBuilder();
        for (String label : searchLabelList) {
            builder.append(label + ";");
        }
        return builder.toString();
    }

    private Intent getHouseSimple() {
        Intent intent = new Intent();
        String condition;
        Bundle bundle = new Bundle();
        if (!newHistoryList.isEmpty()) {
            present.saveSearchHistory(newHistoryList);

            StringBuilder builder = new StringBuilder();
            for (PropertyParamHints data : newHistoryList) {
                builder.append(data.getKeyIdType() + ":" + data.getKeyId() + "/");
            }

            condition = builder.toString();
            bundle.putString(VIEW_SEARCH_KEY_TYPE, condition.substring(0, condition.length() - 1));
            builder.delete(0, condition.length());
            for (String label : searchLabelList) {
                builder.append(label + ";");
            }
            String labelString = builder.toString();
            bundle.putString(VIEW_SEARCH_LABEL, labelString.substring(0, labelString.length() - 1));
        }

        bundle.putString(VIEW_SEARCH_FLOOT, flootStr);
        bundle.putString(VIEW_SEARCH_UNIT, unitsStr);
        intent.putExtras(bundle);
        return intent;
    }

    public void showVoiceInputPanel() {
        VoiceInputPanel.show(getActivity(), false, this);
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO},
                        1);
            }
        }
    }

    private void showSelectCondition() {
        viewType = VIEW_HISRORY;
        showHistoryLableView();
        searchView.setVisibility(View.GONE);
        searchView.setAnimation(moveToViewBottom());
        btn.setText(getString(R.string.search));
        selectCountTxt.setVisibility(View.GONE);
    }

    private void showHistoryLableView() {
        if (searchLabelList != null && searchLabelList.isEmpty())
            selectLabelView.setVisibility(View.GONE);
        if (searchLabelList != null && !searchLabelList.isEmpty())
            selectLabelView.setVisibility(View.VISIBLE);
        historyView.setVisibility(View.VISIBLE);
        searchLabelGroup.removeAllViews();
        searchLabelGroup.addItem(searchLabelList);
    }

    public TranslateAnimation moveToViewBottom() {
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
        mHiddenAction.setDuration(200);
        return mHiddenAction;
    }


    private List<String> deleteSearchLableData(PropertyParamHints data, List<String> searchLabelList) {
        String labelString = present.changeToLabelData(data);
        searchLabelList.remove(labelString);
        return searchLabelList;
    }

    private void addSearchLabelData(PropertyParamHints data, List<String> searchLabelList) {
//        String labelString = present.changeToLabelData(data);
//        if (searchLabelList.size() < VIEW_SELECT_MAX)
//            if (!searchLabelList.contains(labelString)) {
//                searchLabelList.add(labelString);
//            }

        String labelString = present.changeToLabelData(data);
        if (searchLabelList.size() < VIEW_SELECT_MAX){
            if (!searchLabelList.contains(labelString)) {
                searchLabelList.add(labelString);
            }
        } else{
            if (!searchLabelList.contains(labelString))
                DialogUtil.getSimpleDialog(getString(R.string.select_max_20)).show(getFragmentManager(), "");
        }

    }

    @Override
    public void addListData(final List<PropertyParamHints> data) {
        dataList.clear();
        dataList.addAll(data);
        if (getActivity() == null) return;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                searchAdapter.notifyDataSetChanged();
            }
        });
        autoSearchDescription.setVoice(false);
    }

    @Override
    public void recoverHistoryView(List<PropertyParamHints> history) {
        if (history != null && !history.isEmpty())
            searchHistoryView.setVisibility(View.VISIBLE);
        oldHistoryList.clear();
        oldHistoryList.addAll(history);
        if (getActivity() == null) return;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                historyAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void recoverHistoryLabelView(List<String> history) {
        searchLabelGroup.addItem(history);
        searchLabelList.addAll(history);
        selectLabelView.setVisibility(View.VISIBLE);
    }

    @Override
    public void recoverNewHistory(List<PropertyParamHints> history) {
        newHistoryList = history;
    }

    @Override
    public void refreshHistoryView() {
        if (getActivity() == null) return;
        getActivity().runOnUiThread(() -> {
            if (viewType == VIEW_SEARCH)
                searchAdapter.notifyDataSetChanged();
            if (viewType == VIEW_HISRORY) {
                historyAdapter.notifyDataSetChanged();
                setListViewHeightBasedOnChildren(lv_history);
            }
        });
    }

    @Override
    public void onConfirmClick(String msg) {
        if (msg != null && !msg.equals("")) {
            autoSearchDescription.setVoice(true);
            searchEdit.setText(msg);
            searchEdit.setSelection(msg.length());
        }
    }

    @Override
    public void onCancel() {

    }

    @Override
    public boolean onBackPressed() {
        FragmentTransaction ft = getParentFragment().getChildFragmentManager().beginTransaction();
//        ft.addToBackStack(null);
        Fragment fragment = getParentFragment().getChildFragmentManager().findFragmentByTag(TransListFragment.FRAGMENT_TAG_TRANSLIST);
        if (fragment != null) {
            ft.show(fragment);
            ft.hide(this).commit();
        } else {
            TransRequestParamsManager.setParams(new TransRequestSaveParams());
            ft.show(getParentFragment().getChildFragmentManager().findFragmentByTag(TransHomePagerFragment.FRAGMENT_TAG_HOMEPAGER));
            ft.remove(this).commit();
        }
        // 获取当前回退栈中的Fragment个数
//        getParentFragment().getChildFragmentManager().beginTransaction().replace(R.id.fragment_content, new HomePagerFragment()).commit();
        return true;
    }

    private static class DataAdapter extends BaseAdapter {

        private Context context;

        private List<PropertyParamHints> data;
        private List<String> slectList;

        public DataAdapter(Context context, List<PropertyParamHints> data) {
            this.data = data;
            this.context = context;
        }

        public DataAdapter(Context context, List<PropertyParamHints> data, List<String> slectList) {
            this.data = data;
            this.context = context;
            this.slectList = slectList;
        }

        @Override
        public int getCount() {
            return this.data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(context, R.layout.item_search, null);
                holder.box = convertView.findViewById(R.id.item_search_checkbox);
                holder.simpleNameTxt = convertView.findViewById(R.id.item_search_acronym_txt);
                holder.areaTxt = convertView.findViewById(R.id.item_search_area);
                holder.chAddressTxt = convertView.findViewById(R.id.item_search_ch_address);
                holder.enAddressTxt = convertView.findViewById(R.id.item_search_eng_address);
                holder.view = convertView.findViewById(R.id.item_search_checkbox_view);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (!data.isEmpty())
                setData(holder, data, position);
            return convertView;
        }

        private void setData(ViewHolder holder, List<PropertyParamHints> data, int position) {
            PropertyParamHints itemData = data.get(position);
            String color = "#BB2E2D";
            holder.simpleNameTxt.setText(TextUtil.changeKeyWordColor(itemData.getNameSpell() + "", keyword, color));
            holder.simpleNameTxt.setVisibility(itemData.getNameSpell().length() <= 0 ? View.GONE : View.VISIBLE);
            holder.enAddressTxt.setText(TextUtil.changeKeyWordColor(itemData.getEnAddressName() + "", keyword, color));
            holder.enAddressTxt.setVisibility(itemData.getEnAddressName().length() <= 0 ? View.GONE : View.VISIBLE);
            holder.chAddressTxt.setText(TextUtil.changeKeyWordColor(itemData.getAddressName() + "", keyword, color));
            holder.chAddressTxt.setVisibility(itemData.getAddressName().length() <= 0 ? View.GONE : View.VISIBLE);
            holder.areaTxt.setText(TextUtil.changeKeyWordColor(itemData.getDistrictName() + " > " + itemData.getAreaName(), keyword, color));
            holder.view.setVisibility(slectList == null ? View.GONE : View.VISIBLE);
            holder.box.setChecked(false);
            if (slectList != null && slectList.contains(itemData.getKeyId())) {
                holder.box.setChecked(true);
            }
        }

        private class ViewHolder {
            TextView simpleNameTxt, areaTxt, chAddressTxt, enAddressTxt;
            CheckBox box;
            View view;
        }
    }

    class EditChangedListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (searchEdit.getText().toString().length() == 0) {
                viewType = VIEW_HISRORY;
                historyView.setVisibility(View.VISIBLE);
                searchView.setVisibility(View.GONE);
                selectCountTxt.setVisibility(View.GONE);

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM );
                btn.setLayoutParams(params);
                btn.setBackgroundColor(getResources().getColor(R.color.colortheme));

                btn.setText(getString(R.string.search));
                return;
            }
            selectCountTxt.setVisibility(View.VISIBLE);
            selectCountTxt.setText(getString(R.string.selected) + ": " + newHistoryList.size());
            viewType = VIEW_SEARCH;
//            btn.setText(getString(R.string.dialog_hint_confirm));

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(DensityUtil.dip2px(getContext(),15),0,DensityUtil.dip2px(getContext(),15),DensityUtil.dip2px(getContext(),15));
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM );
            btn.setLayoutParams(params);
            btn.setText(getString(R.string.dialog_hint_confirm));
            btn.setBackground(getResources().getDrawable(R.drawable.shape_square_circle_3_red));

            searchView.setVisibility(View.VISIBLE);
            historyView.setVisibility(View.GONE);
        }

        @Override
        public void afterTextChanged(Editable s) {
            String params = s.toString();
            autoSearchDescription.setName(params);
            keyword = params;
            autoSearchDescription.setDistrictKeyIds(request.getDistrictListIds());
            present.doPost(HttpUtil.URL_AUTOSEARCH, headerDescription, autoSearchDescription);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        keyword = "";
    }


    /**
     * 动态设置ListView的高度
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if (listView == null) return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
