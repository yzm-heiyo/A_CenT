package com.centanet.hk.aplus.Widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;
import com.centanet.hk.aplus.common.DataManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangzm4 on 2018/1/19.
 */

public class SmartCheckBoxLayout extends LinearLayout implements CheckBox.OnClickListener {

    private final String thiz = getClass().getSimpleName();

    private List<CheckBox> viewChechBoxes;

    private static boolean isSelectAll = false;

    private OnItemClick onItemClick;

    private List<Integer> seletList;

    public SmartCheckBoxLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public SmartCheckBoxLayout(Context context, List<Integer> seletList) {
        super(context, null);
    }

    public SmartCheckBoxLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmartCheckBoxLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init() {

        viewChechBoxes = new ArrayList<>();
        if (seletList == null)
            seletList = new ArrayList<>();

        int count = this.getChildCount();

        /**
         * 加載所有的checkBox
         */
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            if (view instanceof CheckBox) {
                viewChechBoxes.add((CheckBox) view);
                ((CheckBox) view).setOnClickListener(this);
            }
        }
    }

    public void setSeletList(List<Integer> seletList) {
        if (seletList == null && !seletList.isEmpty()) return;
        this.seletList = seletList;
        setSaveStatus();
    }

    /**
     * 是否全部選中
     *
     * @param selectAll
     */
    public void selectAllItem(boolean selectAll) {
        L.d(thiz, "method: selectAllItem");
        int count = this.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            if (view instanceof CheckBox) {
                ((CheckBox) view).setChecked(selectAll);
            }
        }
    }


    /**
     * 檢查是否已選中所有選項
     */
    public void isSelectAll() {
        L.d(thiz, "method: isSelectAll");
        int count = this.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            if (view instanceof CheckBox) {
                if (!((CheckBox) view).isChecked() && i != 0) {
                    viewChechBoxes.get(0).setChecked(false);
                    isSelectAll = false;
                    break;
                }
            }
            isSelectAll = true;
            if (i == count - 1) selectAllItem(isSelectAll);
        }
    }

    /**
     * 恢復之前選中狀態
     *
     * @return
     */
    public void setSaveStatus() {
        for (int id : seletList) {
            ((CheckBox) findViewById(id)).setChecked(true);
        }
    }


    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }


    public String[] getCheckBoxContent() {

        if (viewChechBoxes.size() > 0) {
            List<String> results = new ArrayList<>();

            for (int i = 0; i < viewChechBoxes.size(); i++) {
                if (viewChechBoxes.get(i).isChecked()) {
                    results.add(viewChechBoxes.get(i).getText().toString());
                }
            }

            if (!results.isEmpty()) {
                L.d(thiz, "Method: ------> getCheckBoxContent");
                return results.toArray(new String[results.size()]);
            }
        }

        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_status_all:
                isSelectAll = !isSelectAll;
                selectAllItem(isSelectAll);
                getCheck(view);
                L.d(thiz, "status:all onclick   " + isSelectAll);
                break;
            case R.id.dialog_status_N:
            case R.id.dialog_status_TP:
            case R.id.dialog_status_P:
            case R.id.dialog_status_WT:
            case R.id.dialog_status_search:
            case R.id.dialog_status_G:
                if (((CheckBox) view).isChecked())
                    isSelectAll();
                getCheck(view);
                break;
            default:
                break;
        }
    }

    public void getCheck(View view) {
        if (!seletList.isEmpty())
            seletList.clear();

        for (CheckBox b : viewChechBoxes) {
            if (b.isChecked()) seletList.add(b.getId());
        }

        if (onItemClick != null) onItemClick.onClick(view, view.getId(), seletList, -1);
    }

    public interface OnItemClick {
        void onClick(View v, int viewId, List<Integer> selectList, int position);
    }
}
