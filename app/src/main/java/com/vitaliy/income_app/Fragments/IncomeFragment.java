package com.vitaliy.income_app.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.vitaliy.income_app.MainActivity;
import com.vitaliy.income_app.R;
import com.vitaliy.income_app.c_fragment_manager;
import com.vitaliy.income_app.databinding.FragmentIncomeBinding;
import com.vitaliy.income_app.c_group;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Objects;

public class IncomeFragment extends Fragment {

    FragmentIncomeBinding binding;
    ArrayList<c_group> groups = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentIncomeBinding.inflate(getLayoutInflater());
        MainActivity.change_title(requireActivity().getResources().getString(R.string.income),false);

        Display display = requireActivity().getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float density = requireActivity().getResources().getDisplayMetrics().density;
        float dpWidth = outMetrics.widthPixels / density;
        int columns = Math.round(dpWidth/125);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),columns));
        binding. recyclerView.setAdapter(new c_recycler_adapter(getActivity()));

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference groupRef = database.getReference("groups");
        groupRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                groups.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    c_group group = snapshot.getValue(c_group.class);
                    if(group!=null)
                        groups.add(new c_group(group.title,group.subgroups,group.html,group.icon));
                }
                Objects.requireNonNull(binding.recyclerView.getAdapter()).notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        return binding.getRoot();
    }

    class c_recycler_adapter extends RecyclerView.Adapter<c_recycler_adapter.view_holder>{

        Context context;

        public c_recycler_adapter(Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new view_holder(LayoutInflater.from(context).inflate(R.layout.income_item,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull view_holder holder, int position) {
            holder.income_title.setText(groups.get(position).title);
            Picasso.get().load(groups.get(position).icon).into(holder.icon);
            holder.card_view.setOnClickListener(view -> c_fragment_manager.replace_fragment(R.id.fragment_container_view,GroupFragment.newInstance(groups.get(position)),getActivity(),true));
        }

        @Override
        public int getItemCount() {
            return groups.size();
        }

        class view_holder extends RecyclerView.ViewHolder{
            TextView income_title;
            CardView card_view;
            ImageView icon;
            public view_holder(@NonNull View itemView) {
                super(itemView);
                income_title = itemView.findViewById(R.id.income_title);
                card_view = itemView.findViewById(R.id.card_view);
                icon = itemView.findViewById(R.id.icon);
            }
        }
    }

}