package com.example.income_app.Fragments;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.income_app.R;
import com.example.income_app.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    FragmentSettingsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(getLayoutInflater());
        requireActivity().setTitle(requireActivity().getResources().getString(R.string.settings));
        ((AppCompatActivity)requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        final String[] options = new String[] {
                "Про додаток"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, options);

        binding.listView.setAdapter(adapter);

        binding.listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Log.d("mLog",((TextView)view.findViewById(android.R.id.text1)).getText().toString()+i);
        });

        return binding.getRoot();
    }

}