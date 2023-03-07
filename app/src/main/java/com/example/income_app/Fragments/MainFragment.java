package com.example.income_app.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.income_app.R;
import com.example.income_app.databinding.FragmentMainBinding;

public class MainFragment extends Fragment {

    FragmentMainBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(getLayoutInflater());

        replace_fragment(new IncomeFragment());
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.income:
                    replace_fragment(new IncomeFragment());
                    break;
                case R.id.settings:
                    replace_fragment(new SettingsFragment());
                    break;
            }

            return true;
        });

        return binding.getRoot();
    }

    private void replace_fragment(Fragment fragment){
        FragmentManager fragment_manager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragment_transition = fragment_manager.beginTransaction();
        fragment_transition.replace(R.id.frame_layout,fragment);
        fragment_transition.commit();
    }

}