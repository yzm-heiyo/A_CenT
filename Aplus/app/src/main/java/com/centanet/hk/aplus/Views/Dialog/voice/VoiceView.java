package com.centanet.hk.aplus.Views.Dialog.voice;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.centanet.hk.aplus.R;


/**
 * Created by oreo on 17/11/2017.
 */

public class VoiceView extends View {

    private static final String TAG = VoiceView.class.getName();

    private static final int STATE_NORMAL = 0;
    private static final int STATE_PRESSED = 1;
    private static final int STATE_RECORDING = 2;

    private Bitmap mNormalBitmap;
    private Bitmap mPressedBitmap;
    private Bitmap mRecordingBitmap;
    private Paint mPaint;
    private AnimatorSet mAnimatorSet = new AnimatorSet();
    private OnRecordListener mOnRecordListener;

    private int mState = STATE_NORMAL;
    private boolean mIsRecording = false;
    private float mMinRadius;
    private float mMaxRadius;
    private float mCurrentRadius;

    public VoiceView(Context context) {
        super(context);
        init();
    }

    public VoiceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
//        mNormalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.btn_speech_to_text);
//        mPressedBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.btn_speech_to_text);
//        mRecordingBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.btn_speech_to_text);
        mNormalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ico_mic_recording);
        mPressedBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ico_mic_recording);
        mRecordingBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ico_mic_recording);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
//        mPaint.setColor(0xfffdedf4);
        mPaint.setColor(0xffAFEEEE);
//        mPaint.setColor(0xff00B2BA);

        mMinRadius = toPx(68) / 2;
        mCurrentRadius = mMinRadius;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mMaxRadius = Math.min(w, h) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        if (mCurrentRadius > mMinRadius) {
            canvas.drawCircle(width / 2, height / 2, mCurrentRadius, mPaint);
        }

        switch (mState) {
            case STATE_NORMAL:
                canvas.drawBitmap(mNormalBitmap, getWidth() / 2 - mNormalBitmap.getWidth() / 2, getHeight() / 2 - mNormalBitmap.getHeight() / 2, mPaint);
                break;
            case STATE_PRESSED:
                canvas.drawBitmap(mPressedBitmap, getWidth() / 2 - mPressedBitmap.getWidth() / 2, getHeight() / 2 - mPressedBitmap.getHeight() / 2, mPaint);
                break;
            case STATE_RECORDING:
                canvas.drawBitmap(mRecordingBitmap, getWidth() / 2 - mRecordingBitmap.getWidth() / 2, getHeight() / 2 - mRecordingBitmap.getHeight() / 2, mPaint);
                break;
        }
    }

    public void animateRadius(float radius) {
        if (radius <= mCurrentRadius) {
            return;
        }
        if (radius > mMaxRadius) {
            radius = mMaxRadius;
        } else if (radius < mMinRadius) {
            radius = mMinRadius;
        }
        if (radius == mCurrentRadius) {
            return;
        }
        if (mAnimatorSet.isRunning()) {
            mAnimatorSet.cancel();
        }
        mAnimatorSet.playSequentially(
                ObjectAnimator.ofFloat(this, "CurrentRadius", getCurrentRadius(), radius).setDuration(50),
                ObjectAnimator.ofFloat(this, "CurrentRadius", radius, mMinRadius).setDuration(600)
        );
        mAnimatorSet.start();
    }

    public float getCurrentRadius() {
        return mCurrentRadius;
    }

    public void setCurrentRadius(float currentRadius) {
        mCurrentRadius = currentRadius;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "ACTION_DOWN");
                mState = STATE_PRESSED;
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "ACTION_UP");
                if (mIsRecording) {
                    mState = STATE_NORMAL;
                    if (mOnRecordListener != null) {
                        mOnRecordListener.onRecordFinish();
                    }
                } else {
                    mState = STATE_RECORDING;
                    if (mOnRecordListener != null) {
                        mOnRecordListener.onRecordStart();
                    }
                }
                mIsRecording = !mIsRecording;
                invalidate();
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

    public void setOnRecordListener(OnRecordListener onRecordListener) {
        mOnRecordListener = onRecordListener;
    }

    public static interface OnRecordListener {
        void onRecordStart();

        void onRecordFinish();
    }

    public int toPx(float dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

}