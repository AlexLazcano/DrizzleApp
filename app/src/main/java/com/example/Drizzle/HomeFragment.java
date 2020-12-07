/**
 * Home fragment class in which a user can start searching for a new group.
 * @author Markus Mattila & Alexis Lazcano
 * @version 2
 * @since 2020-11-22
 */

package com.example.Drizzle;

import android.content.Intent;
import android.content.res.Configuration;
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

import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class HomeFragment extends Fragment {

    private View view;
    private int numOfClicks = 0; // for the button.
    String selectedSize; // selected size on home radiobutton
    String selectedSubject; // selected subject on home radiobutton.

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);





        Button searchBtn = view.findViewById(R.id.findGroupBtn); // the main button for initiating search algorithm.
        Button logOutBtn = view.findViewById(R.id.logOutBtn); //the log out button aft bottom right

        TextView statusText = view.findViewById(R.id.searchingStatus); // the text with the searching algorithm's status.
        TextView userName = view.findViewById(R.id.userName); // for the name with which one is greeted.

        RadioGroup group1 = view.findViewById(R.id.groupSizeRadioGroup); // for the size radio buttons.
        RadioGroup group2 = view.findViewById(R.id.groupSubjectRadioGroup); // for the subject radio buttons.

        // TODO: userName.setText("Welcome, " + (the user's name?') + "!"); ... need to figure out as to how this information will be obtained.

        // TODO: "createGroupSizeRadioButtons(). createGroupSubjectRadioButtons()"... would be nicer as a function.

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

            group1.addView(sizeRadioBtn);
        }

        String[] studySubjects = getResources().getStringArray(R.array.study_subject);

        for (int i = 0; i < studySubjects.length; i++) {
            String studySubject = studySubjects[i];

            RadioButton subjectRadioButton = new RadioButton(getContext()); // getContext(): correct? total guess.
            subjectRadioButton.setText(studySubject);                          // seems to work...

            subjectRadioButton.setOnClickListener(new View.OnClickListener() { // what to do when a RadioButton is pressed. TODO: FIX. DOES NOT WORK.
                @Override
                public void onClick(View v) {
                    selectedSubject = studySubject;
                    Log.i("Drizzle", selectedSubject);
                }
            });

            group2.addView(subjectRadioButton);
        }

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

        logOutBtn.setOnClickListener(new View.OnClickListener() { //define logout process
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Log.d("getinstance", FirebaseAuth.getInstance().toString());
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//makesure user cant go back
                startActivity(intent);
            }
        });

        return view;
    }
}