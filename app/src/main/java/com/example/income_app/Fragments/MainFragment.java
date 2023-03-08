package com.example.income_app.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.income_app.R;
import com.example.income_app.c_fragment_manager;
import com.example.income_app.databinding.FragmentMainBinding;

public class MainFragment extends Fragment {

    private int selected_item_id;
    public FragmentMainBinding binding;

    @SuppressLint("NonConstantResourceId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(getLayoutInflater());

        c_fragment_manager.replace_fragment(R.id.frame_layout,(selected_item_id==R.id.settings)?new SettingsFragment():new IncomeFragment(),requireActivity());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.income:
                    selected_item_id = R.id.income;
                    c_fragment_manager.replace_fragment(R.id.frame_layout,new IncomeFragment(),requireActivity());
                    break;
                case R.id.settings:
                    selected_item_id = R.id.settings;
                    c_fragment_manager.replace_fragment(R.id.frame_layout,new SettingsFragment(),requireActivity());
                    break;
            }
            return true;
        });

        return binding.getRoot();
    }

}