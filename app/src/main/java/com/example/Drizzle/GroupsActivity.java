package com.example.Drizzle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class GroupsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    String s1[], s2[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        recyclerView = findViewById(R.id.recycleView);
        s1 = getResources().getStringArray(R.array.group_titles);
        s2 = getResources().getStringArray(R.array.group_desc);

        MyAdapter myAdapter = new MyAdapter(this, s1, s2);

        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}