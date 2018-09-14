package com.centanet.hk.aplus.Widgets;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.DensityUtil;
import com.centanet.hk.aplus.Utils.L;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by yangzm4 on 2018/6/12.
 */

public class HorizontalView extends ViewGroup {

    private Context context;

    public static final int STATE_SLIDE_LEFT = 0;
    public static final int STATE_SLIDE_RIGHT = 1;
    private static final int SLIDE_MIN = 20;
    private float move;
    private int itemMargin = 10;
    private ValueAnimator animator;
    private boolean isAnimation;
    private int count;
    private Paint bottomLinePaint;
    private ValueAnimator bgAnimator;


    @Retention(RetentionPolicy.SOURCE)
    //这里指定int的取值只能是以下范围
    @IntDef({STATE_SLIDE_LEFT, STATE_SLIDE_RIGHT})
    @interface SlideStateTypeDef {
    }

    private TextPaint textPaint;
    private TextPaint selectedPaint;

    private int seeSize = 4;//可见个数
    private float selectedTextSize;
    private int selectedColor;
    private float textSize;
    private int textColor;

    private float oldX;

    private ViewGroup content;

    private int position;
    private float x;
    private boolean isFirstVisible = true;

    private int screenWidth;

    private int wiewWidth;

    private List<Integer> speedList;

    private int velocitySpeed;

    private List<String> dataList;

    private OnItemClickLisenter onItemClickLisenter;

    private OnItemScrollLisenter onItemScrollLisenter;

    private float currentSelect = 0;
    private float oldSelect = 0;
    private int index = 0;


    //    private LinearLayout.LayoutParams layoutParams;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public HorizontalView(Context context) {
        this(context, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public HorizontalView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public HorizontalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;
        setWillNotDraw(false);
        setClickable(true);
        initAttrs(attrs);//初始化属性
        initPaint();//初始化画笔
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void init() {

        dataList = new ArrayList<>();

        speedList = new ArrayList<>();

        content = this;

        bottomLinePaint = new Paint();
        bottomLinePaint.setColor(getResources().getColor(R.color.colortheme));

        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        float density = dm.density;
        screenWidth = dm.widthPixels;
        int height = dm.heightPixels;

//        content = new LinearLayout(context);
//        this.addView(content);

//        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        layoutParams.setLayoutDirection(LinearLayout.HORIZONTAL);
//        this.setLayoutParams(layoutParams);
    }

    @SuppressLint("RestrictedApi")
    private void initAttrs(AttributeSet attrs) {

    }

    public void setOnItemClickLisenter(OnItemClickLisenter onItemClickLisenter) {
        this.onItemClickLisenter = onItemClickLisenter;
    }

    public void setOnItemScrollLisenter(OnItemScrollLisenter onItemScrollLisenter) {
        this.onItemScrollLisenter = onItemScrollLisenter;
    }

    private void initPaint() {
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(textSize);
        textPaint.setColor(textColor);
        selectedPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        selectedPaint.setColor(selectedColor);
        selectedPaint.setTextSize(selectedTextSize);
    }

    public void addItems(final List<String> titles) {

        dataList.addAll(titles);

        count = titles.size();

        isFirstVisible = true;

//        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                if (isFirstVisible) {

        for (int i = 0; i < dataList.size(); i++) {
            final View view = LayoutInflater.from(context).inflate(R.layout.item_txt, null, false);
            TextView textView = view.findViewById(R.id.item_txt_title);
            textView.setText(dataList.get(i));
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(screenWidth / seeSize, LayoutParams.WRAP_CONTENT);
//            view.setWidth(screenWidth / seeSize);

//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(screenWidth / seeSize, LayoutParams.WRAP_CONTENT);
//            layoutParams.setMargins(0,15,0,0);

            view.setLayoutParams(params);
            view.setTag(i);
            this.addView(view);

            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: ");
                    if (Math.abs(move) < SLIDE_MIN) {
                        selectItem((Integer) v.getTag());
                        if (onItemClickLisenter != null)
                            onItemClickLisenter.onClick(v, (Integer) v.getTag());
                    }
                }
            });

//                        Space space = new Space(context);
//                        space.setLayoutParams(new ViewGroup.LayoutParams(DensityUtil.px2dip(context, 10), LayoutParams.WRAP_CONTENT));
//                        content.addView(space);
            //获得视图的宽度
            wiewWidth = wiewWidth + screenWidth / seeSize;
//            wiewWidth = wiewWidth + textView.getWidth();
//                        wiewWidth = wiewWidth + (getWidth() - 30) / 4 + 10;
        }

//                    isFirstVisible = false;
//                }
//            }
//        });
    }

    public int getItemCount() {
        return count;
    }

    /**
     * 选择指定Item
     *
     * @param index
     */
    public void selectItem(int index) {


        if (isAnimation) bgAnimator.cancel();

        if (index >= dataList.size()) throw new IllegalArgumentException("index out of size");
        if (index < 0) throw new IllegalArgumentException("index out of size");

        resetAllItemState();
        View v = getItemAtIndex(index);
        v.setSelected(true);
        slidToItem(v);


        this.index = index;
//        Paint paint = new Paint();
//        paint.setColor(getResources().getColor(R.color.colortheme));
        doAnimation(oldSelect, index, 500, 0);
//        Rect rect = new Rect(currentSelect * screenWidth / seeSize, 0, (currentSelect + 1) * screenWidth / seeSize, 15);
//        canvas.drawRect(rect, paint);

        L.d("selectItem", "" + index);
        //14:59
//        currentSelect = index;
//        postInvalidate();

//        Canvas canvas = new Canvas();
//        if(currentSelect != index)
//        postInvalidate();
    }

    public int getCurrent() {
        return (int) oldSelect;
    }


    /**
     * 获得指定位置的Item
     *
     * @param index
     * @return
     */
    public View getItemAtIndex(int index) {
        View v = null;
        int position = 0;
        for (int i = 0; i < this.getChildCount(); i++) {
            if (this.getChildAt(i) instanceof RelativeLayout) {
                if (position == index) {
                    v = this.getChildAt(i);
                    break;
                }
                position++;
            }
        }
        return v;
    }


    public void setIndexItemTip(int index, String tip) {
        View view = getItemAtIndex(index);
        TextView textView = view.findViewById(R.id.item_txt_other);
        textView.setVisibility(VISIBLE);
        if (tip == null || tip.equals("")) {
            textView.setVisibility(GONE);
            return;
        }

        if (tip.equals("7")) {
            textView.setBackground(getResources().getDrawable(R.drawable.shape_circle_blue));
            textView.setText(" " + tip + " ");
        }

        if (tip.equals("14")) {
            textView.setBackground(getResources().getDrawable(R.drawable.shape_circle_orange));
            textView.setText(tip);
        }

        if (tip.equals("30")) {
            textView.setBackground(getResources().getDrawable(R.drawable.shape_square_circle_red));
            textView.setText(tip);
        }

    }

    /**
     * 滑动到指定的视图
     *
     * @param v
     */
    private void slidToItem(View v) {
        ViewGroup.MarginLayoutParams params = (MarginLayoutParams) this.getLayoutParams();

        int screenCenterPoint = screenWidth / 2;

        if (v.getX() < screenCenterPoint) {
            if (params.leftMargin >= 0) return;
            if (params.leftMargin < 0) {
                doAnimation(300, params.leftMargin, 0);
                return;
            }
        }

        if (wiewWidth - (v.getX() + v.getWidth() / 2) < screenCenterPoint) {
            doAnimation(300, params.leftMargin, -(wiewWidth - screenWidth));
            return;
        }

        int slideLength = (int) (v.getX() + params.leftMargin + v.getWidth() / 2 - screenCenterPoint);

        doAnimation(100, params.leftMargin, params.leftMargin - slideLength);
    }


    /**
     * 清除所有Item的状态
     */
    private void resetAllItemState() {
        for (int i = 0; i < this.getChildCount(); i++) {
            if (this.getChildAt(i) instanceof RelativeLayout)
                this.getChildAt(i).setSelected(false);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        this.onTouchEvent(ev);//在这里先处理下你的手势左右滑动事件
        return super.dispatchTouchEvent(ev);
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

        int height = 0;
        if (heightMode == MeasureSpec.EXACTLY) {
            //如果高度模式为EXACTLY（match_perent或者size），则使用建议高度
            height = heightSize;
        } else {
            //其他情况下（AT_MOST、UNSPECIFIED）需要计算计算高度
            int childCount = getChildCount();
            if (childCount <= 0) {
                height = 0;   //没有标签时，高度为0
            } else {
                int ViewWidth = 0;
                for (int i = 0; i < childCount; i++) {
                    View view = getChildAt(i);
                    //获取标签宽度
                    ViewWidth = ViewWidth + view.getMeasuredWidth();
                    height = view.getHeight();
                }
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
            botom = row * (childH) + childH;
            childView.layout(right - childW, botom - childH, right, botom);

        }
    }


    private void requesDoAnimation(int move, int duration, int start, int end) {

        //根据正负来判断滑动的方向
        if (end == 0) {
            if (move > 0) end = -(wiewWidth - screenWidth);
            if (move < 0) end = 0;
        } else {
            if (move > 0)
                end = start - end;
            if (move < 0)
                end = start + end;
        }

        doAnimation(duration, start, end);

    }

    private void doAnimation(int duration, int start, int end) {
        if (animator != null) animator.cancel();
        animator = ValueAnimator.ofInt(start, end);
        animator.setDuration(duration);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ViewGroup.MarginLayoutParams params = (MarginLayoutParams) HorizontalView.this.getLayoutParams();
                params.leftMargin = (int) animation.getAnimatedValue();
                if (params.leftMargin > 0) {
                    params.leftMargin = 0;
                }
                if (params.leftMargin + wiewWidth - screenWidth <= 0) {
                    params.leftMargin = -(wiewWidth - screenWidth);
                }
                setLayoutParams(params);

                if (onItemScrollLisenter != null && (int) animation.getAnimatedValue() != 0)
                    onItemScrollLisenter.onScroll(false);

            }
        });
//        animator.addListener(new Animator.AnimatorListener() {
//
//            @Override
//            public void onAnimationStart(Animator animation) {
//                isAnimation = true;
//            }
//
//            @TargetApi(Build.VERSION_CODES.KITKAT)
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                if (onItemScrollLisenter != null) {
//                    onItemScrollLisenter.onScroll(true);
//                }
//                Log.d(TAG, "onAnimationEnd: " + ((MarginLayoutParams) HorizontalView.this.getLayoutParams()).leftMargin);
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        });
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        VelocityTracker velocityTracker = VelocityTracker.obtain();
        velocityTracker.addMovement(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = event.getRawX();
                Log.e(TAG, "onTouchEvent: " + "Action_Down" + " X: " + x);
                oldX = ((ViewGroup.MarginLayoutParams) this.getLayoutParams()).leftMargin;
                move = 0;
                if (animator != null) animator.cancel();
                break;
            case MotionEvent.ACTION_MOVE:

                velocityTracker.computeCurrentVelocity(1000);

                Log.d(TAG, "onTouchEvent: " + " velocityTracker " + velocityTracker.getXVelocity());
                move = x - event.getRawX();

                speedList.add((int) Math.abs(velocityTracker.getXVelocity()));

                if (Math.abs(move) > SLIDE_MIN) {
                    setMaigin(move, oldX);
                    if (onItemScrollLisenter != null) onItemScrollLisenter.onScroll(true);
                }

                break;
            case MotionEvent.ACTION_UP:


                if (speedList.size() >= 2)
                    velocitySpeed = speedList.get(speedList.size() - 2);

                speedList.clear();

                int leftMaigin = ((ViewGroup.MarginLayoutParams) this.getLayoutParams()).leftMargin;

                if (velocitySpeed > 5000) {
                    requesDoAnimation((int) move, 300, leftMaigin, 0);
                    break;
                }

                if (velocitySpeed > 3000) {
                    requesDoAnimation((int) move, 500, leftMaigin, 400);
                    break;
                }

                if (velocitySpeed > 500) {
                    requesDoAnimation((int) move, 300, leftMaigin, 200);
                    break;
                }

                Log.e(TAG, "onTouchEvent: " + "ACTION_UP");
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) this.getLayoutParams();
                if (params.leftMargin > 0) {
                    params.leftMargin = 0;
                }

                if (params.leftMargin + wiewWidth - screenWidth <= 0) {
                    params.leftMargin = -(wiewWidth - screenWidth);
                }
                setLayoutParams(params);

                if (onItemScrollLisenter != null) onItemScrollLisenter.onScroll(true);

                if (velocityTracker != null)
                    velocityTracker.recycle();
                break;
        }
        return super.onTouchEvent(event);
    }

    private void setMaigin(float move, float lodX) {

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) this.getLayoutParams();
        params.leftMargin = (int) (lodX - move);

        //到达边界的时候为两天添加阻力
        if (move < 0 && params.leftMargin >= 0) {
            params.leftMargin = (int) ((lodX - move) / 4);
        }

        if (move > 0 && (params.leftMargin + wiewWidth - screenWidth <= 0)) {
            params.leftMargin = screenWidth - wiewWidth + (params.leftMargin + wiewWidth - screenWidth) / 4;
        }

        Log.d(TAG, "setMaiginLef: " + "margin " + params.leftMargin);
        setLayoutParams(params);

    }

    public void setItemSeeSize(int seeSize) {
        this.seeSize = seeSize;
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        L.d("onDraw_ing", currentSelect + "");
        Rect rect = new Rect((int) (currentSelect * (screenWidth / seeSize)), getHeight() - DensityUtil.dip2px(context,5), (int) ((currentSelect + 1) * (screenWidth / seeSize)), getHeight());
        canvas.drawRect(rect, bottomLinePaint);

    }

    private void doAnimation(float start, float end, int duration, int t) {

        bgAnimator = ValueAnimator.ofFloat(start, end);
        bgAnimator.setInterpolator(new DecelerateInterpolator());
        bgAnimator.setDuration(duration);
        bgAnimator.addUpdateListener(valueAnimator1 -> {
            currentSelect = (float) valueAnimator1.getAnimatedValue();
            oldSelect = (float) valueAnimator1.getAnimatedValue();

            postInvalidate();
//            int i  = (int) valueAnimator1.getAnimatedValue();
//            Rect rect = new Rect(i * screenWidth / seeSize, 0, (i + 1) * screenWidth / seeSize, 15);
//            canvas.drawRect(rect, paint);
        });
        bgAnimator.start();

        bgAnimator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                isAnimation = true;
            }

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onAnimationEnd(Animator animation) {
                if (onItemScrollLisenter != null) {
                    onItemScrollLisenter.onScroll(true);
                }
                isAnimation = false;
                oldSelect = index;
                Log.d(TAG, "onAnimationEnd: " + ((MarginLayoutParams) HorizontalView.this.getLayoutParams()).leftMargin);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    public interface OnItemClickLisenter {
        void onClick(View view, int position);
    }

    public interface OnItemScrollLisenter {
        void onScroll(boolean isEnd);
    }

}
