package com.centanet.hk.aplus.Widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.DensityUtil;

/**
 * 自定义圆点提示
 * Created by yangzm4 on 2018/3/29.
 */

public class CircleTipsView extends View {

    private Paint paint;
    private String tip;
    private int RADIUS = 10;
    private int textSize = 12;

    public CircleTipsView(Context context) {
        super(context);
    }

    public CircleTipsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleTipsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint = new Paint();
        paint.setAntiAlias(true);

        paint.setColor(getResources().getColor(R.color.color_white));

        int radius = DensityUtil.dip2px(null, RADIUS);
        canvas.drawCircle(radius, radius, radius, paint);
        canvas.save();

        if (tip != null) {
            int count = Integer.parseInt(tip);
            paint.setColor(getResources().getColor(R.color.colortheme));
            paint.setTextSize(DensityUtil.dip2px(null, textSize));
            if (count < 10) {
                canvas.drawText(tip, radius - DensityUtil.dip2px(null, textSize / 2 - 3), radius + DensityUtil.dip2px(null, textSize / 2 - 2), paint);
            } else {
                canvas.drawText(tip, DensityUtil.dip2px(null, 3), radius + DensityUtil.dip2px(null, textSize / 2 - 2), paint);
            }
        }
    }

    public void setText(String tips) {
        tip = tips;
        invalidate();
    }

    public void setText(int tips) {
        tip = tips + "";
        invalidate();
    }
}
