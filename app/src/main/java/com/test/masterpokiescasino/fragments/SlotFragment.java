package com.test.masterpokiescasino.fragments;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.test.masterpokiescasino.R;
import com.test.masterpokiescasino.databinding.FragmentMainBinding;
import com.test.masterpokiescasino.databinding.FragmentSlotsBinding;
import com.test.masterpokiescasino.utils.AnimationEndCallback;
import com.test.masterpokiescasino.utils.Utils;

import java.util.Random;

public class SlotFragment extends Fragment {

    FragmentSlotsBinding binding;
    private boolean animation = false;
    private int count_rand_1 = 0, count_rand_2 = 0, count_rand_3 = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSlotsBinding.inflate(inflater, container, false);

        binding.bttnSpinSlot.setOnClickListener(v ->
        {
            if (!animation) {
                animation = true;
                Utils.pressAnim(v, 200, 1.1f, new AnimationEndCallback() {
                    @Override
                    public void onAnimationEnd() {
                        animation = false;
                        Animation.AnimationListener animationListener = new Animation.AnimationListener() {
                            int count_1 = 0, count_2 = 0, count_3 = 0;
                            private boolean[] stop = {false, false, false};
                            @Override
                            public void onAnimationStart(Animation animation) {
                                int rand_1 = getRand(1, 3);
                                int rand_2 = getRand(1, 3);
                                int rand_3 = getRand(1, 3);

                                if (count_rand_1 != count_1) {
                                    binding.item1.setImageDrawable(getRandDrawable(rand_1));
                                    count_1++;
                                }
                                if (count_rand_2 != count_2) {

                                    binding.item2.setImageDrawable(getRandDrawable(rand_2));
                                    count_2++;
                                }
                                if (count_rand_3 != count_3) {
                                    binding.item3.setImageDrawable(getRandDrawable(rand_3));
                                    count_3++;
                                }
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                if(count_rand_1 != count_1)
                                    binding.item1.startAnimation(animation);
                                else
                                    stop[0] = true;
                                if(count_rand_2 != count_2)
                                    binding.item2.startAnimation(animation);
                                else
                                    stop[1] = true;
                                if(count_rand_3 != count_3)
                                    binding.item3.startAnimation(animation);
                                else
                                    stop[2] = true;

                                if(stop[0] && stop[1] && stop[2])
                                {
                                    WinDialogFragment winDialogFragment = new WinDialogFragment(false);
                                    winDialogFragment.show(getChildFragmentManager(), "win");
                                }
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        };


                        count_rand_1 = getRand(15, 30);
                        count_rand_2 = getRand(15, 30);
                        count_rand_3 = getRand(15, 30);
                        TranslateAnimation animation1 = new TranslateAnimation(0, 0, -100, 100);
                        animation1.setDuration(100);
                        animation1.setAnimationListener(animationListener);
                        binding.item1.startAnimation(animation1);
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

    @SuppressLint("UseCompatLoadingForDrawables")
    private Drawable getRandDrawable(int pos)
    {
        switch (pos)
        {
            case 1: return requireActivity().getDrawable(R.drawable.slot_item_1);
            case 2: return requireActivity().getDrawable(R.drawable.slot_item_2);
            case 3: return requireActivity().getDrawable(R.drawable.slot_item_3);
        }
        return null;
    }

}
