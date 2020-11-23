package com.example.Drizzle;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FriendsFragment extends Fragment {
    RecyclerView recyclerView;
    String s1[], s2[];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_friends, container, false);

        recyclerView = root.findViewById(R.id.recycleViewFriends);
        s1 = getResources().getStringArray(R.array.firstNamesFriend);
        s2 = getResources().getStringArray(R.array.secondNamesFriend);

        MyAdapter myAdapter = new MyAdapter(getContext(),s1, s2);

        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        return root;


    }
}