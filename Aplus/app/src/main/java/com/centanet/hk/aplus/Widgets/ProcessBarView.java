package com.centanet.hk.aplus.Widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.L;


/**
 * Created by Administrator on 2017/10/30.
 */

public class ProcessBarView extends View {

    private static final int STATUS_UNSELECT = 0;
    private static final int STATUS_LEFT = 1;
    private static final int STATUS_RIGHT = 2;

    private Paint paint;

    private int color_line_normal;
    private int color_line_select;

    private float pressX;

    private int radius = 25;

    private int height;
    private int width;

    private float leftOldX = radius;
    private float rightOldX = 0;

    private float slideX;

    private boolean isSetLeftProcess = false;
    private boolean isSetRightProcess = false;

    private float stroke_width_normal;
    private float stroke_width_select;

    private int max = 100;

    private int touchStatus = 0;

    private float leftX = radius;
    private float rightX = 0;

    private float leftProcess;
    private float rightProcess;

    private boolean isFirstLoad = true;

    private OnProgressChangeListener onProgressChangeListener;


    public ProcessBarView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public ProcessBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public ProcessBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public void setOnProgressChangeListener(OnProgressChangeListener onProgressChangeListener) {
        this.onProgressChangeListener = onProgressChangeListener;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMax() {
        return max;
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.ProcessBarView, defStyleAttr, 0);
        color_line_normal = a.getColor(R.styleable.ProcessBarView_color_line_normal, Color.parseColor("#dedede"));
        color_line_select = a.getColor(R.styleable.ProcessBarView_color_line_select, Color.parseColor("#BB2E2D"));
        stroke_width_normal = a.getDimension(R.styleable.ProcessBarView_stroke_width_normal, 2f);
        stroke_width_select = a.getDimension(R.styleable.ProcessBarView_stroke_width_select, 4f);

        int text_color = a.getColor(R.styleable.ProcessBarView_text_color, color_line_select);
        a.recycle();
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(text_color);
        paint.setAntiAlias(true);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);


        if(isFirstLoad) {
            rightOldX = getWidth() - radius;
            rightX = rightOldX;
            isFirstLoad = false;
        }

        if (isSetLeftProcess) {
            isSetLeftProcess = false;
            setLeftProcess(leftProcess);
            if (isSetRightProcess) {
                isSetRightProcess = false;
                setRightProcess(rightProcess);
            }
        } else if (isSetRightProcess) {
            setRightProcess(rightProcess);
            isSetRightProcess = false;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        L.d("onDraw", "leftX: " + leftX + " rightX: " + rightX);
        super.onDraw(canvas);

        // 获取对应高度
        height = getHeight();
        // 获取对应宽度
        width = getWidth();
        //先画全部进度条
        paint.setColor(color_line_normal);
        paint.setStrokeWidth(stroke_width_normal);
        canvas.drawLine(0, height / 2, width, height / 2, paint);


        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);


        //左侧按钮
        canvas.drawCircle(leftX, height / 2, radius, paint);
        //右侧按钮
        canvas.drawCircle(rightX, height / 2, radius, paint);

        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);


        //按鈕邊界綫
        canvas.drawCircle(leftX, height / 2, radius, paint);
        canvas.drawCircle(rightX - 0, height / 2, radius, paint);

        //两个按钮之间的进度线
        paint.setColor(color_line_select);
        paint.setStrokeWidth(stroke_width_select);
        canvas.drawLine(leftX + radius, height / 2, rightX - radius, height / 2, paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //down的时候记录下触摸点X位置以及触摸点是否落在有效范围的状态
                touchStatus = checkTouchStatus(event.getX(), event.getY());
                pressX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                //触摸点落在有效范围内则跟随手指移动
                float changeProgress;
                changeProgress = event.getX() - pressX;
                if (touchStatus == 1) {
                    setLeft(changeProgress);
                } else if (touchStatus == 2) {
                    setRight(changeProgress);
                }
                break;
            case MotionEvent.ACTION_UP:

                setOldPointX(event.getX(), touchStatus);
                slideX = 0;
                touchStatus = STATUS_UNSELECT;

                break;
            case MotionEvent.ACTION_CANCEL:
                touchStatus = STATUS_UNSELECT;
                break;
        }
        return true;
    }

    public void setOldPointX(float x, int status) {

        switch (status) {
            case STATUS_LEFT:
                leftOldX = leftX;
                break;
            case STATUS_RIGHT:
                rightOldX = rightX;
                break;
        }

    }

    public void setLeftValue(int value) {
        if (value > max) value = max;
        float process = (float) value / max;
        setLeftProcess(process);
    }

    public void setRightValue(int value) {
        float process = (float) value / max;
        setRightProcess(process);
    }


    public void setLeftProcess(float leftProcess) {

        if (getWidth() == 0) {
            this.leftProcess = leftProcess;
            isSetLeftProcess = true;
            return;
        }

        float x = (getWidth() - radius * 4) * leftProcess + radius;
        leftX = x;
        invalidate();
        leftOldX = leftX;

        if (onProgressChangeListener != null)
            onProgressChangeListener.onLeftProgressChange(leftProcess, (int) (max * leftProcess));
    }

    public void setRightProcess(float rightProcess) {

        if (getWidth() == 0) {
            this.rightProcess = rightProcess;
            isSetRightProcess = true;
            return;
        }

        float x = (getWidth() - radius * 4) * (rightProcess) + radius * 3;
        rightX = x;
        invalidate();
        rightOldX = rightX;
        L.d("PriceDialog", "Max: " + max);

        if (onProgressChangeListener != null)
            onProgressChangeListener.onRightProgressChange(rightProcess, (int) (max * rightProcess));
    }


    private void setRight(float changeSlide) {

        changeSlide = -changeSlide;

        //上下限判定
        if (changeSlide < 0) {
            slideX = rightOldX - changeSlide >= width - radius ? radius + rightOldX - width : changeSlide;
        }

        if (changeSlide > 0) {
            slideX = rightOldX - changeSlide >= leftOldX + radius * 2 ? changeSlide : rightOldX - leftOldX - radius * 2;
        }

        leftX = leftOldX;
        rightX = rightOldX - slideX;

        invalidate();

        if (onProgressChangeListener != null) {
            float process = 1 - (rightX - radius * 3) / (width - radius * 4);
            onProgressChangeListener.onRightProgressChange(process, (int) (max * (1 - process)));
        }
    }

    private void setLeft(float changeSlide) {

        //上下限判定
        if (changeSlide < 0)
            slideX = leftOldX + changeSlide >= radius ? changeSlide : -leftOldX + radius;

        if (changeSlide > 0) {
            slideX = leftOldX + changeSlide <= rightOldX - radius * 2 ? changeSlide : rightOldX - radius * 2 - leftOldX;
        }

        leftX = leftOldX + slideX;
        rightX = rightOldX;
        invalidate();

        if (onProgressChangeListener != null) {
            float process = (leftX - radius) / (width - radius * 4);
            onProgressChangeListener.onLeftProgressChange(process, (int) (max * process));
        }
    }

    /**
     * @param pointX
     * @param pointY
     * @return 0表示触摸点不在两头按钮范围内，1表示触摸点落在左侧按钮范围内，2表示触摸点落在右侧按钮范围内
     */
    private int checkTouchStatus(float pointX, float pointY) {
        //注释掉的是Y轴上触摸点是否落在按钮的高度范围之内，之所以注释是为了触摸的范围大一点
//        if (pointY >= (getHeight() - leftProgressIcon.getHeight()) / 2.0f && pointY <= getHeight() - (getHeight() - leftProgressIcon.getHeight()) / 2.0f) {
        if (pointX >= leftOldX - radius && pointX <= leftOldX + radius) {
            return STATUS_LEFT;
        }
        if (pointX >= rightOldX - radius && pointX <= rightOldX + radius) {
            return STATUS_RIGHT;
        }
//        }
        return STATUS_UNSELECT;
    }

    public interface OnProgressChangeListener {
        void onLeftProgressChange(float progress, int value);

        void onRightProgressChange(float progress, int value);
    }

}
