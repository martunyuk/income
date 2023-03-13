package com.vitaliy.income_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import com.vitaliy.income_app.Fragments.MainFragment;
import com.vitaliy.income_app.databinding.ActivityMainBinding;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    public static ActivityMainBinding binding;
    final private MainFragment main_fragment = new MainFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backBtn.setOnClickListener(view -> onBackPressed());

        c_fragment_manager.replace_fragment(R.id.fragment_container_view,main_fragment,this);

        if(!isNetworkAvailable(this)){
            new AlertDialog.Builder(this)
                    .setTitle(R.string.no_internet)
                    .setMessage(R.string.no_internet_desc)
                    .setPositiveButton(R.string.ok, (dialog, which) -> finish())
                    .setIcon(R.drawable.wifi_off)
                    .setCancelable(false)
                    .show();
        } else MobileAds.initialize(this, initializationStatus -> {});

        c_database_helper dbHelper = new c_database_helper(this);
        if(Boolean.parseBoolean(dbHelper.getData())) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    public static void change_title(String title, boolean show_back_btn){
        binding.title.setText(title);
        binding.backBtn.setVisibility(show_back_btn?View.VISIBLE:View.INVISIBLE);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onBackPressed() {
        if(main_fragment.isVisible()) {
            switch (main_fragment.binding.bottomNavigationView.getSelectedItemId()){
                case R.id.income:
                    new AlertDialog.Builder(this)
                            .setTitle(R.string.exiting_the_app)
                            .setMessage(R.string.are_you_sure_you_want_to_exit)
                            .setPositiveButton(R.string.yes, (dialog, whichButton) -> {
                                finish();
                                dialog.dismiss();
                            }).setNegativeButton(R.string.no, (dialog, whichButton) -> dialog.dismiss()).show();
                    break;
                case R.id.settings:
                    main_fragment.binding.bottomNavigationView.setSelectedItemId(R.id.income);
                    break;
            }
        } else super.onBackPressed();
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}