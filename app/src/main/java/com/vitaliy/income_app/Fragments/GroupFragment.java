package com.vitaliy.income_app.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.webkit.WebSettingsCompat;
import androidx.webkit.WebViewFeature;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.vitaliy.income_app.MainActivity;
import com.vitaliy.income_app.R;
import com.vitaliy.income_app.c_ads_manager;
import com.vitaliy.income_app.c_fragment_manager;
import com.vitaliy.income_app.c_group;
import com.vitaliy.income_app.databinding.FragmentGroupBinding;
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
            group = getArguments().getParcelable("group");
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGroupBinding.inflate(getLayoutInflater());
        MainActivity.change_title(group.title,true);

        if(group.subgroups !=null){
            binding.listView.setVisibility(View.VISIBLE);
            binding.webView.setVisibility(View.GONE);
            String[] options = new String[group.subgroups.size()];
            for (int x = 0; x<group.subgroups.size(); x++){
                options[x] = group.subgroups.get(x).title;
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_list_item_1, options);

            binding.listView.setAdapter(adapter);

            binding.listView.setOnItemClickListener((adapterView, view, i, l) -> c_fragment_manager.replace_fragment(R.id.fragment_container_view,GroupFragment.newInstance(new c_group(group.subgroups.get(i).title,group.subgroups.get(i).subgroups,group.subgroups.get(i).html,group.subgroups.get(i).icon)),getActivity(),true));
        } else if(group.html!=null) {
            binding.listView.setVisibility(View.GONE);
            binding.webView.setVisibility(View.VISIBLE);
            if(WebViewFeature.isFeatureSupported(WebViewFeature.ALGORITHMIC_DARKENING))
                WebSettingsCompat.setAlgorithmicDarkeningAllowed(binding.webView.getSettings(), true);
            binding.webView.getSettings().setJavaScriptEnabled(true);
            binding.webView.loadDataWithBaseURL(null, group.html, "text/html", "utf-8", null);

            c_ads_manager.show_ad(getActivity());
        }

        return binding.getRoot();
    }
}