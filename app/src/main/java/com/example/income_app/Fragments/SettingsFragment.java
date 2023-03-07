package com.example.income_app.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.income_app.R;
import com.example.income_app.c_fragment_manager;
import com.example.income_app.c_group;
import com.example.income_app.databinding.FragmentSettingsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsFragment extends Fragment {

    FragmentSettingsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(getLayoutInflater());
        requireActivity().setTitle(requireActivity().getResources().getString(R.string.settings));
        ((AppCompatActivity)requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference groupRef = database.getReference("about");
        groupRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String html = snapshot.getValue(String.class);
                    binding.listView.setOnItemClickListener((adapterView, view, i, l) -> {
                        String title = ((TextView) view).getText().toString();
                        if(i==0) c_fragment_manager.replace_fragment(R.id.fragment_container_view,GroupFragment.newInstance(new c_group(title,null,html,null)),getActivity(),true);
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        final String[] options = new String[] {
                "Про додаток"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, options);

        binding.listView.setAdapter(adapter);

        return binding.getRoot();
    }

}