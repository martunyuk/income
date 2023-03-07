package com.example.income_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.income_app.Fragments.MainFragment;
import com.example.income_app.databinding.ActivityMainBinding;

import java.net.InetAddress;

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

        if(!isNetworkAvailable(this)){
            new AlertDialog.Builder(this)
                    .setTitle(R.string.no_internet)
                    .setMessage(R.string.no_internet_desc)
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> finish())
                    .setIcon(R.drawable.wifi_off)
                    .setCancelable(false)
                    .show();
        }

    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}