package com.centanet.hk.aplus.Widgets;

import android.content.Context;
import android.content.res.TypedArray;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.DensityUtil;

import static org.xmlpull.v1.XmlPullParser.TEXT;

/**
 * Created by mzh1608258 on 2018/1/3.
 */

public class SmallItemView extends LinearLayout {

    private TextView title, telephone, content;
    private LinearLayout titleLayout, contentLayout, teleLayout;
    private int styleType = 0;

    public enum TitleType {
        text(0), TELEPHONE(1);

        private int value;

        TitleType(int value) {
            this.value = value;
        }

        int getValue() {
            return value;
        }
    }


    public void setTitleType(TitleType type) {
        this.styleType = type.getValue();
    }


    public SmallItemView(Context context) {
        this(context, null);
    }

    public SmallItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmallItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setInit(context);
        setAttributes(context, attrs, defStyleAttr);

    }


    private void setInit(Context context) {
        View.inflate(context, R.layout.smallitem, this);
        title = this.findViewById(R.id.smallitem_title);
        content = this.findViewById(R.id.smallitem_content);
        telephone = this.findViewById(R.id.smallitem_tele);
        titleLayout = this.findViewById(R.id.smallitem_title_layout);
        contentLayout = this.findViewById(R.id.smallitem_content_layout);
        teleLayout = this.findViewById(R.id.smallitem_tele_layout);
    }

    //設置抬頭內容
    public boolean setTitleName(String title) {
        if (TextUtils.isEmpty(title)) {
            return false;
        }

        switch (styleType) {
            case 0:
                this.title.setText(title);
                this.title.setVisibility(VISIBLE);
                this.teleLayout.setVisibility(GONE);
                break;

            case 1:
                this.telephone.setText(title);
                this.teleLayout.setVisibility(VISIBLE);
                this.title.setVisibility(GONE);
                break;

            default:
                return false;

        }

        return true;
    }

    //設置內容內容
    public boolean setContentName(String content) {
        if (TextUtils.isEmpty(content)) {
            return false;
        }

        this.content.setText(content);
        return true;
    }

    public boolean setContentName(Spanned content) {
        if (TextUtils.isEmpty(content)) {
            return false;
        }

        this.content.setText(content);
        return true;
    }

    //設置比重
    public void setWeight(float titleWeight, float contentWeight) {

        setTitleWeight(titleWeight);
        setContentWeight(contentWeight);

    }

    private void setTitleWeight(float weight) {
        LayoutParams params = (LayoutParams) titleLayout.getLayoutParams();
        params.weight = weight;
    }

    private void setContentWeight(float weight) {
        LayoutParams params = (LayoutParams) contentLayout.getLayoutParams();
        params.weight = weight;

    }

    //設置字體顏色
    public void setColor(int titleColor, int contentColor) {
        setTitleColor(titleColor);
        setContentColor(contentColor);
    }

    private void setTitleColor(int color) {
        switch (styleType) {
            case 0:
                title.setTextColor(color);
                break;

            case 1:
                telephone.setTextColor(color);
                break;

            default:
                break;
        }

    }


    private void setContentColor(int color) {
        content.setTextColor(color);
    }

    //設置字體大小
    private void setSize(int titleSize, int ContentSize) {
        setTitleSize(titleSize);
        setContentSize(ContentSize);
    }

    private void setTitleSize(int size) {
        switch (styleType) {
            case 0:
                title.setTextSize(size);
                break;

            case 1:
                telephone.setTextSize(size);
                break;

            default:
                break;
        }

    }

    private void setContentSize(int size) {
        content.setTextSize(size);
    }

    private void setAttributes(Context context, AttributeSet attrs, int defStyleAttr) {


        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SmallItemView, defStyleAttr, 0);

        styleType = a.getInt(R.styleable.SmallItemView_SmallItemTitleStyle, 0);

        switch (styleType) {
            case 0:
                title.setVisibility(VISIBLE);
                teleLayout.setVisibility(GONE);
                break;

            case 1:
                title.setVisibility(GONE);
                teleLayout.setVisibility(VISIBLE);

                break;

            default:
                break;
        }
        Log.i("SmallItemView","--------------------  START  --------------------");
        for (int i = 0; i < a.getIndexCount(); i++) {

            Log.i("SmallItemView","IndexCount"+a.getIndexCount()+"");
            switch (a.getIndex(i)) {
                case R.styleable.SmallItemView_SmallItemTitle:

                    setTitleName(a.getString(a.getIndex(i)));
                    Log.i("SmallItemView","SmallItemView_SmallItemTitle: "+a.getString(a.getIndex(i)));
                    break;

                case R.styleable.SmallItemView_SmallItemContent:
                    setContentName(a.getString(a.getIndex(i)));
                    Log.i("SmallItemView","SmallItemView_SmallItemContent: "+a.getString(a.getIndex(i)));
                    break;

                case R.styleable.SmallItemView_SmallItemContentWeight:
                    setContentWeight(a.getFloat(a.getIndex(i), 2));
                    Log.i("SmallItemView","SmallItemView_SmallItemContentWeight: "+a.getString(a.getIndex(i)));
                    break;

                case R.styleable.SmallItemView_SmallItemTitleWeight:
                    setTitleWeight(a.getFloat(a.getIndex(i), 1));
                    Log.i("SmallItemView","SmallItemView_SmallItemTitleWeight: "+a.getString(a.getIndex(i)));
                    break;

                case R.styleable.SmallItemView_SmallItemTitleColor:
                    setTitleColor(a.getColor(a.getIndex(i), getResources().getColor(R.color.color_itemt_result_text)));
//                    Log.i("SmallItemView","SmallItemView_SmallItemTitleColor");
                    break;

                case R.styleable.SmallItemView_SmallItemContentColor:
                    setContentColor(a.getColor(a.getIndex(i), getResources().getColor(R.color.color_itemt_result_text)));
//                    Log.i("SmallItemView","SmallItemView_SmallItemContentColor");
                    break;

                case R.styleable.SmallItemView_SmallItemContentSize:

                    setTitleSize(DensityUtil.px2dip(getContext(), a.getDimensionPixelOffset(a.getIndex(i), -1)));
                    Log.e("SmallItemView",a.getDimensionPixelOffset(a.getIndex(i),-1)+"");
                    break;

                case R.styleable.SmallItemView_SmallItemTitleSize:

                    setContentSize(DensityUtil.px2dip(getContext(), a.getDimensionPixelOffset(a.getIndex(i), -1)));
                    Log.e("SmallItemView",a.getDimensionPixelOffset(a.getIndex(i),-1)+"");
                    /**
                     * 获取dimension的方法有几种，区别不大
                     * 共同点是都会将dp，sp的单位转为px，px单位的保持不变
                     * 所以需要使用DensityUtil.px2dip将其转换成dp
                     * getDimension() 返回float，
                     * getDimensionPixelSize 返回int 小数部分四舍五入
                     * getDimensionPixelOffset 返回int，但是会抹去小数部分
                     */
                    break;

                default:
                    break;
            }
        }
        a.recycle();
    }


}
