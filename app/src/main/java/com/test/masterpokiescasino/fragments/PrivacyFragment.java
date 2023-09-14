package com.test.masterpokiescasino.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.RotateAnimation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.test.masterpokiescasino.databinding.FragmentPolicyBinding;
import com.test.masterpokiescasino.databinding.FragmentWheelBinding;
import com.test.masterpokiescasino.utils.AnimationEndCallback;
import com.test.masterpokiescasino.utils.Utils;

import java.util.Random;

public class PrivacyFragment extends Fragment {

    FragmentPolicyBinding binding;

    private int count_rand_1 = 0, count_rand_2 = 0, count_rand_3 = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPolicyBinding.inflate(inflater, container, false);

        binding.bttnBack.setOnClickListener(v ->
        {
            Utils.pressAnim(v, 200, 1.1f, new AnimationEndCallback() {
                @Override
                public void onAnimationEnd() {
                    getActivity().onBackPressed();
                }
            });

        });



        return binding.getRoot();
    }




}
