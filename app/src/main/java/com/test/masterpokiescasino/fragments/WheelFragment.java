package com.test.masterpokiescasino.fragments;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.test.masterpokiescasino.R;
import com.test.masterpokiescasino.databinding.FragmentSlotsBinding;
import com.test.masterpokiescasino.databinding.FragmentWheelBinding;
import com.test.masterpokiescasino.utils.AnimationEndCallback;
import com.test.masterpokiescasino.utils.Utils;

import java.util.Random;

public class WheelFragment extends Fragment {

    FragmentWheelBinding binding;
    private boolean animation = false;
    private int count_rand_1 = 0, count_rand_2 = 0, count_rand_3 = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentWheelBinding.inflate(inflater, container, false);

        binding.bttnSpinWheel.setOnClickListener(v ->
        {

            if (!animation) {
                animation = true;
                Utils.pressAnim(v, 200, 1.1f, new AnimationEndCallback() {
                    @Override
                    public void onAnimationEnd() {
                        int randomDegrees = (int) ((Math.random() * 2) * 3600) + 360;
                        RotateAnimation rotateAnimation = new RotateAnimation(0, randomDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        rotateAnimation.setDuration(getRand(5000, 7000));
                        rotateAnimation.setFillAfter(true);
                        rotateAnimation.setInterpolator(new DecelerateInterpolator());
                        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                WheelFragment.this.animation = false;
                                WinDialogFragment winDialogFragment = new WinDialogFragment(true);
                                winDialogFragment.show(getChildFragmentManager(), "win");
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });

                        binding.wheel.startAnimation(rotateAnimation);
                    }
                });

            }
        });



        return binding.getRoot();
    }

    private Integer getRand(int start, int end)
    {
        Random rand = new Random();
        return rand.nextInt(end) + start;
    }


}
