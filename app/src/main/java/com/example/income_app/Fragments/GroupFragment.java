package com.example.income_app.Fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.example.income_app.R;
import com.example.income_app.c_ads_manager;
import com.example.income_app.c_group;
import com.example.income_app.databinding.FragmentGroupBinding;
import java.util.List;

public class GroupFragment extends Fragment {

    FragmentGroupBinding binding;

    c_group_serializable group;

    public static class c_group_serializable implements Parcelable {
        public String title;
        public List<c_group> subgroups;
        public String html;
        public String icon;

        public c_group_serializable(String title, List<c_group> subgroups, String html, String icon) {
            this.title = title;
            this.subgroups = subgroups;
            this.html = html;
            this.icon = icon;
        }

        protected c_group_serializable(Parcel in) {
            title = in.readString();
            html = in.readString();
        }

        public static final Creator<c_group_serializable> CREATOR = new Creator<c_group_serializable>() {
            @Override
            public c_group_serializable createFromParcel(Parcel in) {
                return new c_group_serializable(in);
            }

            @Override
            public c_group_serializable[] newArray(int size) {
                return new c_group_serializable[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(@NonNull Parcel parcel, int i) {
            parcel.writeString(title);
            parcel.writeString(html);
        }
    }

    public static GroupFragment newInstance(c_group group) {
        Bundle args = new Bundle();
        args.putParcelable("group", new c_group_serializable(group.title,group.subgroups,group.html,group.icon));
        GroupFragment fragment = new GroupFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            group = (c_group_serializable) getArguments().getParcelable("group");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGroupBinding.inflate(getLayoutInflater());
        requireActivity().setTitle(group.title);
        ((AppCompatActivity)requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(group.subgroups !=null){
            String[] options = new String[group.subgroups.size()];
            for (int x = 0; x<group.subgroups.size(); x++){
                options[x] = group.subgroups.get(x).title;
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_list_item_1, options);

            binding.listView.setAdapter(adapter);

            binding.listView.setOnItemClickListener((adapterView, view, i, l) -> {
                FragmentManager fragment_manager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragment_transition = fragment_manager.beginTransaction();
                fragment_transition.addToBackStack(null);
                fragment_transition.replace(R.id.fragment_container_view,GroupFragment.newInstance(new c_group(group.subgroups.get(i).title,group.subgroups.get(i).subgroups,group.subgroups.get(i).html,group.subgroups.get(i).icon)));
                fragment_transition.commit();
            });
        } else if(group.html!=null) {
//            c_ads_manager.loadAd(getActivity());
            binding.listView.setVisibility(View.GONE);
            binding.webView.getSettings().setJavaScriptEnabled(true);
            binding.webView.loadData(group.html, "text/html; charset=utf-8", "UTF-8");
        }

        return binding.getRoot();
    }
}