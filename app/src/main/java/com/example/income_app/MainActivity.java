package com.example.income_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.income_app.Fragments.MainFragment;
import com.example.income_app.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setElevation(0);

        FragmentManager fragment_manager = getSupportFragmentManager();
        FragmentTransaction fragment_transition = fragment_manager.beginTransaction();
        fragment_transition.replace(R.id.fragment_container_view,new MainFragment());
        fragment_transition.commit();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}