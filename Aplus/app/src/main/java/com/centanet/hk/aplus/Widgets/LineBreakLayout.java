package com.centanet.hk.aplus.Widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangzm4 on 2018/1/20.
 * 自定义label容器，能够设置左右间距，行间距和自动换行
 * 可以完善的地方：是否要添加默認試圖
 */

public class LineBreakLayout extends ViewGroup implements View.OnClickListener {
    //TAG
    private String thiz = getClass().getSimpleName();

    //暫時不用，看看後期程序拓展需要不
    private List<String> labelList;

    private onItemOnclickListener mOnClickListener;

    private int contentLayoutID;

    //自定义属性
    private int LEFT_RIGHT_SPACE; //左右距离
    private int ROW_SPACE;//行间距

    public LineBreakLayout(Context context) {
        this(context, null);
    }

    public LineBreakLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineBreakLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LineBreakLayout);
        LEFT_RIGHT_SPACE = ta.getDimensionPixelSize(R.styleable.LineBreakLayout_leftAndRightSpace1, 10);//默认10
        ROW_SPACE = ta.getDimensionPixelSize(R.styleable.LineBreakLayout_rowSpace1, 10);
        //設置LineBreakLayout的默認試圖
        contentLayoutID = android.R.layout.simple_expandable_list_item_1;
        ta.recycle(); //回收
        labelList = new ArrayList<>();
        // ROW_SPACE=20   LEFT_RIGHT_SPACE=40
    }

//    public View[] getViews() {
//
//        return views;
//    }

    public void removeAllContentViews() {
        this.removeAllViews();
    }

    public void setItemOnclickListener(LineBreakLayout.onItemOnclickListener itemOnclickListener) {
        mOnClickListener = itemOnclickListener;
    }

    /**
     * 設置行間据
     *
     * @param rowSpace
     */
    public void setRowSpace(int rowSpace) {
        ROW_SPACE = rowSpace;
    }

    /**
     * 設置左右距離
     *
     * @param leftRightSpace
     */
    public void setLeftRightSpace(int leftRightSpace) {
        LEFT_RIGHT_SPACE = leftRightSpace;
    }


    /**
     * 設置label的數據
     *
     * @param labelList
     */
    public void setLabelData(List<String> labelList) {
        this.labelList = labelList;
    }


    /**
     * 設置label的視圖
     *
     * @param layoutID
     */
    public void setItemContentLayoutID(int layoutID) {
        contentLayoutID = layoutID;
    }


    /**
     * 添加labelView的item
     *
     * @param itemText
     */
    public void addItem(String itemText) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        if (!TextUtils.isEmpty(itemText)) {
            View view = inflater.inflate(contentLayoutID, null);
            view.setOnClickListener(this);
            TextView tv = view.findViewById(R.id.item_label_text);
            tv.setText(itemText);

            tv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                private boolean isFirst = true;

                @Override
                public void onGlobalLayout() {
                    if (isFirst) {
                        if (tv.getWidth() > getWidth() ) {
                            ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(getWidth(), tv.getHeight());
                            tv.setLayoutParams(layoutParams);
                        }
                        isFirst = false;
                    }
                }
            });

//            view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                @Override
//                public void onGlobalLayout() {
//                    L.d("search",view.getWidth()+"   "+getWidth());
//                    if (view.getWidth() > getWidth() - 20) {
//                        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(getWidth()-100, tv.getHeight());
//                        view.setLayoutParams(layoutParams);
//                        L.d("viewWidth",view.getWidth()+"");
//                    }
//                }
//            });

            this.addView(view);
        }
    }

    /**
     * 添加LabelView的item
     *
     * @param itemList
     */
    public void addItem(List<String> itemList) {
        if (!itemList.isEmpty()) {
            for (int i = 0; i < itemList.size(); i++) {
                addItem(itemList.get(i));
            }
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //为所有的标签childView计算宽和高
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        //获取高的模式
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //建议的高度
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //布局的宽度采用建议宽度（match_parent或者size），如果设置wrap_content也是match_parent的效果
        int width = MeasureSpec.getSize(widthMeasureSpec);

        int height;
        if (heightMode == MeasureSpec.EXACTLY) {
            //如果高度模式为EXACTLY（match_perent或者size），则使用建议高度
            height = heightSize;
        } else {
            //其他情况下（AT_MOST、UNSPECIFIED）需要计算计算高度
            int childCount = getChildCount();
            if (childCount <= 0) {
                height = 0;   //没有标签时，高度为0
            } else {
                int row = 1;  // 标签行数
                int widthSpace = width;// 当前行右侧剩余的宽度
                for (int i = 0; i < childCount; i++) {
                    View view = getChildAt(i);
                    //获取标签宽度
                    int childW = view.getMeasuredWidth();

                    if (widthSpace >= childW) {
                        //如果剩余的宽度大于此标签的宽度，那就将此标签放到本行
                        widthSpace -= childW;
                    } else {
                        row++;    //增加一行						//如果剩余的宽度不能摆放此标签，那就将此标签放入一行
                        widthSpace = width - childW;
                    }
                    //减去标签左右间距
                    widthSpace -= LEFT_RIGHT_SPACE;
                }
                //由于每个标签的高度是相同的，所以直接获取第一个标签的高度即可
                int childH = getChildAt(0).getMeasuredHeight();
                //最终布局的高度=标签高度*行数+行距*(行数-1)
                height = (childH * row) + ROW_SPACE * (row - 1);

            }
        }

        //设置测量宽度和测量高度
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int row = 0;
        int right = 0;   // 标签相对于布局的右侧位置
        int botom;       // 标签相对于布局的底部位置

        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            int childW = childView.getMeasuredWidth();
            int childH = childView.getMeasuredHeight();
            //右侧位置=本行已经占有的位置+当前标签的宽度
            right += childW;
            //底部位置=本行已经占有的位置+当前标签的宽度
            botom = row * (childH + ROW_SPACE) + childH;
            // 如果右侧位置已经超出布局右边缘，跳到下一行
            // if it can't drawing on a same line , skip to next line
            if (right > (r - LEFT_RIGHT_SPACE)) {
                row++;
                right = childW;
                botom = row * (childH + ROW_SPACE) + childH;
            }

            childView.layout(right - childW, botom - childH, right, botom);

            right += LEFT_RIGHT_SPACE;
        }
    }

    @Override
    public void onClick(View v) {
        int position = this.indexOfChild(v);
        L.d(thiz, position + "");
        if (mOnClickListener != null) {
            mOnClickListener.onItemClick(v, this, position);
        }
    }

    public interface onItemOnclickListener {
        void onItemClick(View view, ViewGroup contentView, int position);
    }


}
