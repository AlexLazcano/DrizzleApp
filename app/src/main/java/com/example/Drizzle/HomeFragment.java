package com.example.Drizzle;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class HomeFragment extends Fragment {

    private View view;
    private int numOfClicks = 0; // for the button.

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);

        Button searchBtn = view.findViewById(R.id.findGroupBtn);
        TextView statusText = view.findViewById(R.id.searchingStatus);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numOfClicks % 2 == 0) {
                    statusText.setText("Searching for your perfect study group...");
                    // algorithm that finds the group for the user to be placed here.
                    // it should then also notify the user that a group has been found.
                    // i need the user ID for this.
                }
                else {
                    statusText.setText("Not currently searching.");
                }
                numOfClicks++;
            }
        });

        return view;
    }
}