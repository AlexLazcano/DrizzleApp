package com.example.Drizzle;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class HomeFragment extends Fragment {

    private View view;
    private int numOfClicks = 0; // for the button.
    String selectedSize;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);

        Button searchBtn = view.findViewById(R.id.findGroupBtn);
        TextView statusText = view.findViewById(R.id.searchingStatus);
        TextView userName = view.findViewById(R.id.userName);
        RadioGroup group = view.findViewById(R.id.groupSizeRadioGroup); // for the radio buttons.

        // TODO: userName.setText("Welcome, " + (the user's name?') + "!"); ... need to figure out as to how this information will be obtained.

        // TODO: createGroupSizeRadioButtons();... would be nicer as a function.

        String[] groupSizes = getResources().getStringArray(R.array.group_size);

        for (int i = 0; i < groupSizes.length; i++) {
            String groupSize = groupSizes[i];

            RadioButton sizeRadioBtn = new RadioButton(getContext()); // getContext(): correct? total guess.
            sizeRadioBtn.setText(groupSize);                          // seems to work...

            sizeRadioBtn.setOnClickListener(new View.OnClickListener() { // what to do when a RadioButton is pressed. TODO: FIX. DOES NOT WORK.
                @Override
                public void onClick(View v) {
                    selectedSize = groupSize;
                    Log.i("Drizzle", selectedSize);
                }
            });

            group.addView(sizeRadioBtn);
        }

        // TODO: Create some form of widget for your preferred study subject, for the search. Remember, we need the subject and group size!

        searchBtn.setOnClickListener(new View.OnClickListener() { // what to do when searchBtn is pressed.
            @Override
            public void onClick(View v) {
                if (numOfClicks % 2 == 0) {
                    statusText.setText(R.string.searching_button_line);
                    /* TODO:
                     algorithm that finds the group for the user should start here.
                     it should then also notify the user that a group has been found.
                     i need the user ID, study topic and preferred group size for this.
                    */
                }
                else {
                    statusText.setText(R.string.not_searching_button_line);
                }
                numOfClicks++;
            }
        });

        return view;
    }
}