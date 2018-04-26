package com.centanet.hk.aplus.Utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by yangzm4 on 2018/3/30.
 */

public class AnimationUtil {

    private static final int ANIMATION_DURATION = 500;
    private static boolean isFirst = true;


    public static AnimatorSet buildListRemoveAnimator(final View view, final List list,
                                                      final BaseAdapter adapter, final int index) {
        Animator.AnimatorListener al = new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // TODO Auto-generated method stub
                if(isFirst) {
                    list.remove(index);
//                RecyclerView.ViewHolder vh = (RecyclerView.ViewHolder) view.getTag();
//                vh.needInflate = true;
                    adapter.notifyDataSetChanged();
                    isFirst = false;
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub

            }
        };

        AnimatorSet animatorSet = new AnimatorSet();
        Animator anim = ObjectAnimator.ofFloat(view, "rotationX", 0, 90);
        Animator animb = ObjectAnimator.ofFloat(view, "alpha", 1, 0);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        final int height = view.getMeasuredHeight();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // TODO Auto-generated method stub
                if (animation.getAnimatedFraction() >= 1) {
                    view.setVisibility(View.GONE);
                }
                else {
                    view.getLayoutParams().height = height
                            - (int) (height * animation.getAnimatedFraction());
                    view.requestLayout();
                }
            }
        });

        anim.setDuration(ANIMATION_DURATION);
        animb.setDuration(ANIMATION_DURATION);
        valueAnimator.setDuration(ANIMATION_DURATION + ANIMATION_DURATION + 100);
        animatorSet.playTogether(anim, animb, valueAnimator);
        animatorSet.addListener(al);
        return animatorSet;
    }


    private void deletePattern(final View view, final int position) {

        Animation.AnimationListener al = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                listFavo.remove(position);
//                adapter.notifyDataSetChanged();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
        collapse(view, al);

    }

    private void collapse(final View view, Animation.AnimationListener al) {
        final int originHeight = view.getMeasuredHeight();

        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1.0f) {
                    view.setVisibility(View.GONE);
                } else {
                    view.getLayoutParams().height = originHeight - (int) (originHeight * interpolatedTime);
                    view.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        if (al != null) {
            animation.setAnimationListener(al);
        }
        animation.setDuration(300);
        view.startAnimation(animation);
    }
}
