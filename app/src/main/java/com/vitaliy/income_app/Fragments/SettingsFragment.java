package com.vitaliy.income_app.Fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.vitaliy.income_app.MainActivity;
import com.vitaliy.income_app.R;
import com.vitaliy.income_app.c_database_helper;
import com.vitaliy.income_app.c_fragment_manager;
import com.vitaliy.income_app.c_group;
import com.vitaliy.income_app.databinding.FragmentSettingsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsFragment extends Fragment {

    FragmentSettingsBinding binding;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference groupRef = database.getReference("about");

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(getLayoutInflater());
        MainActivity.change_title(requireActivity().getResources().getString(R.string.settings),false);

        c_database_helper dbHelper = new c_database_helper(getActivity());
        binding.darkThemeLayout.setOnClickListener(view -> binding.darkThemeSwitchCompat.performClick());
        binding.darkThemeSwitchCompat.setChecked(dbHelper.getData().equals("")?(getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)==Configuration.UI_MODE_NIGHT_YES:Boolean.parseBoolean(dbHelper.getData()));
        binding.darkThemeSwitchCompat.setOnCheckedChangeListener((compoundButton, b) -> {
            if(b) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            requireActivity().finishAffinity();
            requireActivity().startActivity(new Intent(getActivity(),MainActivity.class));
            dbHelper.addData(String.valueOf(b));
        });

        groupRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String html = dataSnapshot.child("html").getValue(String.class);
                binding.aboutAppBtn.setOnClickListener(view -> c_fragment_manager.replace_fragment(R.id.fragment_container_view,GroupFragment.newInstance(new c_group(requireActivity().getResources().getString(R.string.about_app),null,html,null)),getActivity(),true));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        return binding.getRoot();
    }

}