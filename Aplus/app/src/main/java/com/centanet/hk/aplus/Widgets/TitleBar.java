package com.centanet.hk.aplus.Widgets;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.centanet.hk.aplus.R;
import com.centanet.hk.aplus.Utils.DensityUtil;


/**
 * Created by mzh1608258 on 2017/7/13.
 */

public class TitleBar extends LinearLayout implements View.OnClickListener {

    private ImageView backicon;
    private TextView titleContent;
    private View backPlace;
    private LinearLayout ll, content, right;
    private int statusBarHeight;
    private OnItemClickListener mOnItemClickListener;
    public static final int TYPE_BACK = 0, TYPE_PUT = 1;
    private Context context;


    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //  statusBarHeight = getStatusBarHeight(context);
        init(context);
        setAttributes(context, attrs, defStyleAttr);

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }


    private void init(final Context context) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.titlebar, this);
        backicon = this.findViewById(R.id.titlebar_backicon);
        titleContent = this.findViewById(R.id.titlebar_text);
        backPlace = this.findViewById(R.id.titlebar_backplace);
        backPlace.setOnClickListener(this);
        ll = this.findViewById(R.id.titlebar_background);
        content = this.findViewById(R.id.title_content);
        right = this.findViewById(R.id.titlebar_right_layout);
        right.setOnClickListener(this);

        LayoutParams llParams = (LayoutParams) ll.getLayoutParams();
        llParams.height = llParams.height + statusBarHeight;

        LayoutParams contentParams = (LayoutParams) content.getLayoutParams();
        contentParams.setMargins(0, statusBarHeight, 0, 0);

    }

    public View getBackPlace() {
        return this.backPlace;
    }

    private void setAttributes(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TitleBar, defStyleAttr, 0);
        for (int i = 0; i < a.getIndexCount(); i++) {
            switch (a.getIndex(i)) {
                case R.styleable.TitleBar_titlebarText:

                    titleContent.setText(a.getString(a.getIndex(i)));
                    break;
                case R.styleable.TitleBar_titlebarTextColor:

                    titleContent.setTextColor(a.getColor(a.getIndex(i), Color.BLACK));
                    break;
                case R.styleable.TitleBar_titlebarTextSize:
                    titleContent.setTextSize(DensityUtil.px2dip(context, a.getDimensionPixelSize(a.getIndex(i), (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()))));

                    break;

                case R.styleable.TitleBar_titleBarTextStyle:
                    titleContent.setTypeface(Typeface.defaultFromStyle(a.getInt(a.getIndex(i), 0)));
                    break;

                case R.styleable.TitleBar_titlebarShowImage_left:
                    Boolean show = a.getBoolean(a.getIndex(i), true);
                    backicon.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
                    backPlace.setOnClickListener(null);
                    break;

                case R.styleable.TitleBar_titlebarRighttext:
                    TextView tv = new TextView(getContext());
                    LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    params.gravity = Gravity.CENTER;
                    tv.setText(a.getString(a.getIndex(i)));
                    tv.setTextSize(DensityUtil.px2dip(getContext(), getResources().getDimensionPixelSize(R.dimen.themeSizeplus)));
                    tv.setGravity(Gravity.CENTER);
                    tv.setTextColor(getResources().getColor(R.color.colorBackground));
                    this.right.addView(tv, params);
                    break;

            }
        }
        a.recycle();
    }

    public ImageView getImageView() {
        return this.backicon;
    }

    public void setTitleContent(String titleName) {
        if (titleName != null) {
            this.titleContent.setText(titleName);
        }

    }

    public View getRightLayout() {
        return right;
    }

    private int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.titlebar_right_layout:
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onClick(v, TYPE_PUT);
                break;

            case R.id.titlebar_backplace:
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onClick(v, TYPE_BACK);
                    break;
                }
                ((Activity) context).finish();

            default:
                break;
        }
    }

    public interface OnItemClickListener {
        void onClick(View v, int type);

    }

}
