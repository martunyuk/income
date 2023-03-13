package com.vitaliy.income_app;

import android.app.Activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class c_fragment_manager {
    public static void replace_fragment(int replacement_id, Fragment fragment, Activity activity){
        replace_fragment(replacement_id,fragment,activity,false);
    }
    public static void replace_fragment(int replacement_id, Fragment fragment, Activity activity, boolean addToBackStack){
        FragmentManager fragment_manager = ((AppCompatActivity)activity).getSupportFragmentManager();
        FragmentTransaction fragment_transition = fragment_manager.beginTransaction();
        if(addToBackStack) fragment_transition.addToBackStack(null);
        fragment_transition.replace(replacement_id,fragment);
        fragment_transition.commit();
    }
}
