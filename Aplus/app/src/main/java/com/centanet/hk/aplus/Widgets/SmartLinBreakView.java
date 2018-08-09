package com.centanet.hk.aplus.Widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.centanet.hk.aplus.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by yangzm4 on 2018/8/2.
 */

public class SmartLinBreakView extends LinearLayout {

    private Context context;
    private RelativeLayout contentView;
    private TextView title;
    private LineBreakLayout breakLayout;
    private OnItemChangeLisenter onItemChangeLisenter;

    public SmartLinBreakView(Context context) {
        this(context, null);
    }

    public SmartLinBreakView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmartLinBreakView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(context);
        initLisenter();
        setAttributes(context, attrs, defStyleAttr);
    }

    private void initLisenter() {
        breakLayout.setItemOnclickListener((view, contentView1, position) -> {
            contentView1.removeView(view);
            if (contentView1.getChildCount() == 0) this.setVisibility(GONE);
            if (onItemChangeLisenter != null)
                onItemChangeLisenter.onChangeLisenter(view, contentView1, position);
        });
    }

    private void setAttributes(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SmartLinBreakView, defStyleAttr, 0);
        for (int i = 0; i < a.getIndexCount(); i++) {
            switch (a.getIndex(i)) {
                case R.styleable.SmartLinBreakView_title:
                    title.setText(a.getString(a.getIndex(i)));
                    break;
            }
        }
        a.recycle();
    }

    private void init(Context context) {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setLayoutDirection(LinearLayout.VERTICAL);
        setLayoutParams(params);

        contentView = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.item_smartlinbreak, null, false);
        title = contentView.findViewById(R.id.title);
        breakLayout = contentView.findViewById(R.id.lb);
        breakLayout.setItemContentLayoutID(R.layout.item_dialog_option);
        this.addView(contentView);
    }

    public void setOnItemChangeLisenter(OnItemChangeLisenter onItemChangeLisenter) {
        this.onItemChangeLisenter = onItemChangeLisenter;
    }

    public void addItem(String item) {
        breakLayout.addItem(item);
    }

    public void addItem(List<String> items) {
        breakLayout.addItem(items);
    }

    public interface OnItemChangeLisenter {
        void onChangeLisenter(View view, ViewGroup contentView, int position);
    }
}
