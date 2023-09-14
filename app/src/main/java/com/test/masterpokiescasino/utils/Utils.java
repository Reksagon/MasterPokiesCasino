package com.test.masterpokiescasino.utils;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;

public class Utils {

    public static void pressAnim(View view, long duration, float scale, AnimationEndCallback callback) {
        ValueAnimator anim = ValueAnimator.ofInt(view.getHeight(), view.getHeight());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = (int) valueAnimator.getAnimatedValue();
                view.setLayoutParams(layoutParams);
            }
        });

        ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                view,
                PropertyValuesHolder.ofFloat("scaleX", scale),
                PropertyValuesHolder.ofFloat("scaleY", scale)
        );
        scaleDown.setDuration(duration);
        scaleDown.setRepeatCount(1);
        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);
        scaleDown.start();

        scaleDown.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {}

            @Override
            public void onAnimationEnd(Animator animator) {
                callback.onAnimationEnd();
            }

            @Override
            public void onAnimationCancel(Animator animator) {}

            @Override
            public void onAnimationRepeat(Animator animator) {}
        });
    }

}
