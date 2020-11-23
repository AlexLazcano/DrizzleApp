package com.example.Drizzle;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class GroupFragment extends Fragment {
    RecyclerView recyclerView;
    String s1[], s2[];
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_group, container, false);

        recyclerView = root.findViewById(R.id.recycleViewGroup);
        s1 = getResources().getStringArray(R.array.group_titles);
        s2 = getResources().getStringArray(R.array.group_desc);

        MyAdapter myAdapter = new MyAdapter(getContext(), s1, s2);

        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        return root;
    }
}