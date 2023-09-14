package com.test.masterpokiescasino.fragments;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.test.masterpokiescasino.R;
import com.test.masterpokiescasino.databinding.DialogFragmentWinBinding;
import com.test.masterpokiescasino.databinding.FragmentMainBinding;
import com.test.masterpokiescasino.utils.AnimationEndCallback;
import com.test.masterpokiescasino.utils.Utils;

import java.util.Random;

public class WinDialogFragment extends DialogFragment {

    DialogFragmentWinBinding binding;
    private boolean gold = false;

    public WinDialogFragment(boolean gold) {
        this.gold = gold;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogFragmentWinBinding.inflate(inflater, container, false);

        binding.txtWin.setText(String.valueOf(getRand(500, 10000)));
        setCancelable(false);
        binding.bttnMore.setOnClickListener(v->
        {
            Utils.pressAnim(v, 200, 1.1f, new AnimationEndCallback() {
                @Override
                public void onAnimationEnd() {
                    dismiss();
                }});
        });
        if(gold)
            binding.gold.setImageDrawable(requireActivity().getDrawable(R.drawable.gold));

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getDialog().getWindow().setLayout((int) (width * 0.9), (int) (height * 0.7));
        super.onResume();
    }

    private Integer getRand(int start, int end)
    {
        Random rand = new Random();
        return rand.nextInt(end) + start;
    }
}
