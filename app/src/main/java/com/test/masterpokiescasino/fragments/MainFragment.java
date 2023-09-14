package com.test.masterpokiescasino.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.test.masterpokiescasino.R;
import com.test.masterpokiescasino.databinding.FragmentMainBinding;
import com.test.masterpokiescasino.utils.AnimationEndCallback;
import com.test.masterpokiescasino.utils.Utils;

public class MainFragment extends Fragment {

    FragmentMainBinding binding;
    private boolean animation = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);

        binding.bttnSlot.setOnClickListener(v->
        {
            if(!animation) {
                animation = true;
                Utils.pressAnim(v, 200, 1.1f, new AnimationEndCallback() {
                    @Override
                    public void onAnimationEnd() {
                        animation = false;
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container_main, new SlotFragment());
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                });
            }
        });

        binding.bttnWheel.setOnClickListener(v->
        {
            if(!animation) {
                animation = true;
                Utils.pressAnim(v, 200, 1.1f, new AnimationEndCallback() {
                    @Override
                    public void onAnimationEnd() {
                        animation = false;
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container_main, new WheelFragment());
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                });
            }
        });

        binding.bttnPrivacy.setOnClickListener(v->
        {
            if(!animation) {
                animation = true;
                Utils.pressAnim(v, 200, 1.1f, new AnimationEndCallback() {
                    @Override
                    public void onAnimationEnd() {
                        animation = false;
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container_main, new PrivacyFragment());
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                });
            }
        });

        return binding.getRoot();
    }


}
