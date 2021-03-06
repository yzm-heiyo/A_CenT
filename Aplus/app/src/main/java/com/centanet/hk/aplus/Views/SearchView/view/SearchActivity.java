package com.centanet.hk.aplus.Views.SearchView.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

import com.centanet.hk.aplus.MyApplication;
import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.DensityUtil;
import com.centanet.hk.aplus.Utils.DialogUtil;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.Utils.net.HttpUtil;
import com.centanet.hk.aplus.Utils.TextUtil;
import com.centanet.hk.aplus.Views.Dialog.AreaDialog;
import com.centanet.hk.aplus.Views.Dialog.FlootUnitDialog;
import com.centanet.hk.aplus.Views.Dialog.voice.VoiceInputPanel;
import com.centanet.hk.aplus.Views.SearchView.present.ISearchPresent;
import com.centanet.hk.aplus.Views.SearchView.present.SearchPreesent;
import com.centanet.hk.aplus.Views.basic.BasicActivty;
import com.centanet.hk.aplus.Widgets.LineBreakLayout;
import com.centanet.hk.aplus.bean.auto_estate.PropertyParamHints;
import com.centanet.hk.aplus.bean.district.DistrictItem;
import com.centanet.hk.aplus.bean.http.AutoSearchDescription;
import com.centanet.hk.aplus.bean.http.AHeaderDescription;
import com.centanet.hk.aplus.manager.ApplicationManager;
import com.centanet.hk.aplus.manager.FavoRequestParamsManager;
import com.centanet.hk.aplus.manager.PropertyRequestParamsManager;
import com.githang.statusbar.StatusBarCompat;

import java.util.ArrayList;
import java.util.List;

import static com.centanet.hk.aplus.MyApplication.getContext;


/**
 * Created by mzh1608258 on 2018/1/9.
 */

public class SearchActivity extends BasicActivty implements ISearchView, View.OnClickListener, VoiceInputPanel.EventListener {

    private static final int VIEW_HISRORY = 0;
    private static final int VIEW_SEARCH = 1;
    private static final int VIEW_SELECT_MAX = 20;
    public static final String VIEW_SEARCH_HISTORY_SAVE = "SAVEHISTORY";
    public static final String VIEW_SEARCH_KEY_TYPE = "CONDITION";
    public static final String VIEW_SEARCH_LABEL = "LABEL";
    public static final String VIEW_SEARCH_AREA = "AREA";
    public static final String VIEW_SEARCH_FLOOT = "FLOOT";
    public static final String VIEW_SEARCH_UNIT = "UNIT";
    private ListView lv_history, lv_search;
    private static String keyword = "";
    private List<String> searchLabelList;
    private List<PropertyParamHints> oldHistoryList;
    private List<PropertyParamHints> newHistoryList;
    private AHeaderDescription headerDescription;

    private List<PropertyParamHints> dataList;
    private List<String> selectList;

    private TextView btn, clearSelectTxt, clearHistoryTxt, selectCountTxt;

    private View finish, selectLabelView, searchView, historyView, searchHistoryView;
    private LineBreakLayout searchLabelGroup;
    private String thiz = getClass().getSimpleName();
    private EditText searchEdit;
    private ISearchPresent present;
    private DataAdapter searchAdapter;
    private View mic;

    private TextView areaTxt;
    private View flootView;

    private View back;

    private int viewType = VIEW_HISRORY;
    private String flootStr = "", unitsStr = "";

    AutoSearchDescription autoSearchDescription;
    private DataAdapter historyAdapter;
    private TextView areatip;
    private TextView areaTipTxt;
    private List<String> districtItems;
    private String[] flootUnit;
    private TextView flootTxt, unitTxt;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        StatusBarCompat.setStatusBarColor(this, Color.parseColor("#BB2E2D"), false);
        init();
    }

    protected void init() {

//        if(FavoRequestParamsManager.getParams().)

//        districtItems = new ArrayList<>();

        flootUnit = new String[2];

        present = new SearchPreesent(this);
        dataList = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        headerDescription = ((MyApplication) getApplicationContext()).getHeaderDescription();

        searchLabelList = new ArrayList<>();
        oldHistoryList = new ArrayList<>();
        newHistoryList = new ArrayList<>();
        autoSearchDescription = new AutoSearchDescription();
        autoSearchDescription.setMobileRequest(true);
        autoSearchDescription.setVoice(false);
        searchView = findViewById(R.id.search_simple_view);
        historyView = findViewById(R.id.search_history_view);
        searchHistoryView = findViewById(R.id.search_history_title);
        selectCountTxt = findViewById(R.id.activity_search_txt_count);
        areaTxt = findViewById(R.id.search_txt_area);
        areaTipTxt = findViewById(R.id.search_txt_areatip);
        areaTxt.setOnClickListener(this);

        flootView = this.findViewById(R.id.search_ll_floot);
        flootView.setOnClickListener(this);

        mic = this.findViewById(R.id.mic);
        mic.setOnClickListener(this);

        areatip = this.findViewById(R.id.search_txt_areatip);

        back = this.findViewById(R.id.back);
        back.setOnClickListener(v -> {
            finish();
        });


        lv_search = this.findViewById(R.id.activity_search_listview);
        btn = this.findViewById(R.id.activity_search_confirm_btn);
        finish = this.findViewById(R.id.activity_search_back);
        selectLabelView = findViewById(R.id.search_select_layout);
        searchEdit = findViewById(R.id.search_edit);

        clearHistoryTxt = findViewById(R.id.search_history_txt_clear);
        clearHistoryTxt.setOnClickListener(this);
        clearSelectTxt = findViewById(R.id.select_txt_clear);
        clearSelectTxt.setOnClickListener(this);


        flootTxt = findViewById(R.id.search_txt_floot);
        unitTxt = findViewById(R.id.search_txt_unit);


        back.setOnClickListener(v -> finish());

        lv_history = findViewById(R.id.search_history_list);

        searchLabelGroup = this.findViewById(R.id.search_labelgroup);
        searchLabelGroup.setItemContentLayoutID(R.layout.item_label_search);
        searchLabelGroup.setItemOnclickListener(mOnItemOnclickListener);
        searchLabelGroup.setRowSpace(30);
        searchLabelGroup.setLeftRightSpace(20);

        if (bundle != null) {
            districtItems = (List<String>) bundle.get(VIEW_SEARCH_AREA);
            if (!TextUtil.isEmply(districtItems)) {
                areatip.setText(districtItems.size() + "");
                areatip.setVisibility(View.VISIBLE);
            }

            flootUnit[0] = bundle.getString(VIEW_SEARCH_FLOOT);
            flootUnit[1] = bundle.getString(VIEW_SEARCH_UNIT);
            if (!TextUtil.isEmply(flootUnit[0]))
                flootTxt.setText(flootUnit[0]);
            if (!TextUtil.isEmply(flootUnit[1]))
                unitTxt.setText(flootUnit[1]);

            selectList = (List<String>) bundle.get(VIEW_SEARCH_HISTORY_SAVE);
            if (selectList != null)
                present.recoverLabelHistiry(selectList);
            else selectList = new ArrayList<>();
        } else {
            selectList = new ArrayList<>();
            districtItems = new ArrayList<>();
        }

        btn.setOnClickListener(this);
        finish.setOnClickListener(this);
        searchEdit.addTextChangedListener(new EditChangedListener());

        searchEdit.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                historyView.setVisibility(View.GONE);
                searchView.setVisibility(View.VISIBLE);
                selectCountTxt.setVisibility(View.VISIBLE);
                selectCountTxt.setText(getString(R.string.selected) + ": " + newHistoryList.size());
                viewType = VIEW_SEARCH;
            }
            return false;
        });

        View footView = LayoutInflater.from(this).inflate(R.layout.list_footview, null, false);
        lv_search.addFooterView(footView, null, false);
        lv_history.addFooterView(footView, null, false);

        searchAdapter = new DataAdapter(this, dataList, selectList);
        lv_search.setAdapter(searchAdapter);
        historyAdapter = new DataAdapter(this, oldHistoryList);
        lv_history.setAdapter(historyAdapter);

        lv_search.setOnItemClickListener((parent, view, position, id) -> {
            DataAdapter.ViewHolder holder = (DataAdapter.ViewHolder) view.getTag();
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
                }
                selectCountTxt.setText(getString(R.string.selected) + ": " + newHistoryList.size());
            } else
                DialogUtil.getSimpleDialog(getString(R.string.select_max_20)).show(getSupportFragmentManager(), "");
        });

        lv_history.setOnItemClickListener((parent, view, position, id) -> {
            PropertyParamHints data = oldHistoryList.get(position);
            addSearchLabelData(data, searchLabelList);
            if (!newHistoryList.contains(data))
                newHistoryList.add(data);
            if (!selectList.contains(data.getKeyId()))
                selectList.add(data.getKeyId());
            showHistoryLableView();
        });

        present.getSearchHistory();

        boolean isMicToThis = getIntent().getBooleanExtra("mic", false);
        if (isMicToThis) showVoiceInputPanel();
        else {
            searchEdit.setFocusable(true);
            searchEdit.setFocusableInTouchMode(true);
            searchEdit.requestFocus();
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public LineBreakLayout.onItemOnclickListener mOnItemOnclickListener = (view, contentView, position) -> {
        contentView.removeView(view);
        if (contentView.getChildCount() == 0) selectLabelView.setVisibility(View.GONE);
        if (!searchLabelList.isEmpty()) searchLabelList.remove(position);
        if (!selectList.isEmpty()) selectList.remove(position);
        if (!newHistoryList.isEmpty()) newHistoryList.remove(position);
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if(keyCode == KeyEvent.)
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            SearchActivity.this.setResult(0, getHouseSimple());
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

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
                        SearchActivity.this.setResult(1, getSearchOption());
                        FavoRequestParamsManager.getParams().setAddress(newHistoryList);
                        L.d("VIEW_HISRORY", newHistoryList.toString());
                        FavoRequestParamsManager.getParams().setArea(getSelectDistrict(ApplicationManager.getDistrictItems(), districtItems));
                        finish();
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
                SearchActivity.this.setResult(0, getHouseSimple());
                finish();
                break;
            case R.id.mic:
                showVoiceInputPanel();
//                VoiceInputPanel.show(SearchActivity.this, false, SearchActivity.this);
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
                areaDialog.setItem(ApplicationManager.getDistrictItems(), districtItems);
                areaDialog.setOnItemClickLisenter((dialog, v1, list) -> {
                    if (list != null && !list.isEmpty()) {
                        areaTipTxt.setVisibility(View.VISIBLE);
                    } else areaTipTxt.setVisibility(View.GONE);
//                    houseDescription.setDistrictListIds(list);
                    districtItems.addAll(list);
                    areaTipTxt.setText(list.size() + "");
                });
                areaDialog.show(getSupportFragmentManager(), "");
                break;
            case R.id.search_ll_floot:
                FlootUnitDialog dialog = new FlootUnitDialog();
                dialog.setData(flootUnit);
                dialog.setOnItemClickLisenter((dialog1, v1, list) -> {
                    flootUnit = list;
                });
                dialog.show(getSupportFragmentManager(), "");

                break;
            default:
                break;
        }
    }

    private Intent getSearchOption() {
        if (flootStr.equals("") && unitsStr.equals("") && newHistoryList.isEmpty()) {
            SearchActivity.this.setResult(1);
            finish();
            return null;
        }

        return getHouseSimple();
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
            bundle.putStringArrayList(VIEW_SEARCH_AREA, (ArrayList<String>) districtItems);

        }

        bundle.putString(VIEW_SEARCH_FLOOT, flootUnit[0]);
        bundle.putString(VIEW_SEARCH_UNIT, flootUnit[1]);
        intent.putExtras(bundle);
        return intent;
    }

    public void showVoiceInputPanel() {
        VoiceInputPanel.show(this, false, this);
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(SearchActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
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
        if (searchLabelList.size() < VIEW_SELECT_MAX) {
            if (!searchLabelList.contains(labelString)) {
                searchLabelList.add(labelString);
            }
        } else {
            if (!searchLabelList.contains(labelString))
                DialogUtil.getSimpleDialog(getString(R.string.select_max_20)).show(getSupportFragmentManager(), "");
        }
    }

    @Override
    public void addListData(final List<PropertyParamHints> data) {
        dataList.clear();
        dataList.addAll(data);
        runOnUiThread(new Runnable() {
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
        runOnUiThread(new Runnable() {
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
        runOnUiThread(() -> {
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

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM );
                btn.setLayoutParams(params);
                btn.setBackgroundColor(getResources().getColor(R.color.colortheme));

                btn.setText(getString(R.string.search));
                selectCountTxt.setVisibility(View.GONE);
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
            autoSearchDescription.setDistrictKeyIds(districtItems);
            present.doPost(HttpUtil.URL_AUTOSEARCH, headerDescription, autoSearchDescription);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        keyword = "";
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 給所有組建分發事件
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
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
