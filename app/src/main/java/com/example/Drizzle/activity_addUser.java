/**
 * activity_addUser will add the single user to the firebase
 * @author Haoyuan Zhao
 * @version 1
 * @since 2020-11-22
 */
package com.example.Drizzle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.LinkedList;
import java.util.List;

// Use the application default credentials

public class activity_addUser extends AppCompatActivity {

    public static final String TAG = "addUser";
    private String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    public UserPool newPool = new UserPool();
    private final DocumentReference UserIdGetter = FirebaseFirestore.getInstance().document("UserList/CurrentUserId");
    private Button backBtn;

//    @Override
//    protected void onStart() {
//        super.onStart();
//        UserIdGetter.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//            @Override
//            //Get the newest User Id
//            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException error) {
//                if (documentSnapshot.exists()){
//                    userId = documentSnapshot.getLong("userId").intValue();
//                }
//                //Create a new user ID if no exist (start from 100)
//                else{
//                    userId = 100;
//                    updateUserId();
//                }
//            }
//        });
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);


        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });






        DocumentReference userPath = FirebaseFirestore.getInstance().document("UserList/"+ userId);
        userPath.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException error) {
                if (documentSnapshot.exists()){
                    final EditText nameET = (EditText)findViewById(R.id.plainText_user_name);
                    final Spinner genderET = (Spinner)findViewById(R.id.spinner_gender);
                    final Spinner schoolET = (Spinner)findViewById(R.id.spinner_school);
                    final Spinner majorET = (Spinner)findViewById(R.id.spinner_major);
                    final Spinner minorET = (Spinner)findViewById(R.id.spinner_minor);
                    final EditText postalCodeET = (EditText)findViewById(R.id.plainText_postal_code);
                    final Spinner personalityET = (Spinner)findViewById(R.id.spinner_personality);
                    final EditText courseTaken_1ET = (EditText)findViewById(R.id.editTextText_courseTaken_1);
                    final EditText courseTaken_2ET = (EditText)findViewById(R.id.editTextText_courseTaken_2);
                    final EditText courseTaken_3ET = (EditText)findViewById(R.id.editTextText_courseTaken_3);
                    final EditText courseTaken_4ET = (EditText)findViewById(R.id.editTextText_courseTaken_4);
                    final EditText favCourse_1ET = (EditText)findViewById(R.id.editTextText_favCourse_1);
                    final EditText favCourse_2ET = (EditText)findViewById(R.id.editTextText_favCourse_2);
                    final EditText favCourse_3ET = (EditText)findViewById(R.id.editTextText_favCourse_3);
                    final EditText biographyET = (EditText)findViewById(R.id.plainText_biography);
                    User tarUser = documentSnapshot.toObject(User.class);
                    nameET.setText(tarUser.getName());
                    genderET.setSelection(getIndex(genderET,tarUser.getGender()));
                    majorET.setSelection(getIndex(majorET,tarUser.getMajor()));
                    minorET.setSelection(getIndex(minorET,tarUser.getMinor()));
                    schoolET.setSelection(getIndex(schoolET,tarUser.getSchool()));
                    postalCodeET.setText(tarUser.getPostalCode());
                    if (tarUser.getPersonality() == 1){
                        personalityET.setSelection(getIndex(personalityET,"Outgoing"));
                    }
                    else if (tarUser.getPersonality() == 0){
                        personalityET.setSelection(getIndex(personalityET,"Reserved"));
                    }
                    else{
                        personalityET.setSelection(getIndex(personalityET,"Neutral"));
                    }
                    courseTaken_1ET.setText(tarUser.getCurrentClassed().get(0));courseTaken_2ET.setText(tarUser.getCurrentClassed().get(1));
                    courseTaken_3ET.setText(tarUser.getCurrentClassed().get(2));courseTaken_4ET.setText(tarUser.getCurrentClassed().get(3));
                    favCourse_1ET.setText((tarUser.getCurrentClassed().get(0)));favCourse_2ET.setText((tarUser.getCurrentClassed().get(1)));
                    favCourse_3ET.setText((tarUser.getCurrentClassed().get(2)));
                    biographyET.setText((tarUser.getBiography()));

                }
                else{
                    Toast.makeText(activity_addUser.this,"This is a new User",Toast.LENGTH_SHORT ).show();
                }
            }
        });
    }

    public void addUserToCloud(View view){
        Button btn = findViewById(R.id.button_submit_addUser);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User newUser = new User();
                //Connect to the button and spinner
                final EditText nameET = (EditText)findViewById(R.id.plainText_user_name);
                final Spinner genderET = (Spinner)findViewById(R.id.spinner_gender);
                final Spinner schoolET = (Spinner)findViewById(R.id.spinner_school);
                final Spinner majorET = (Spinner)findViewById(R.id.spinner_major);
                final Spinner minorET = (Spinner)findViewById(R.id.spinner_minor);
                final EditText postalCodeET = (EditText)findViewById(R.id.plainText_postal_code);
                final Spinner personalityET = (Spinner)findViewById(R.id.spinner_personality);
                final EditText courseTaken_1ET = (EditText)findViewById(R.id.editTextText_courseTaken_1);
                final EditText courseTaken_2ET = (EditText)findViewById(R.id.editTextText_courseTaken_2);
                final EditText courseTaken_3ET = (EditText)findViewById(R.id.editTextText_courseTaken_3);
                final EditText courseTaken_4ET = (EditText)findViewById(R.id.editTextText_courseTaken_4);
                final EditText favCourse_1ET = (EditText)findViewById(R.id.editTextText_favCourse_1);
                final EditText favCourse_2ET = (EditText)findViewById(R.id.editTextText_favCourse_2);
                final EditText favCourse_3ET = (EditText)findViewById(R.id.editTextText_favCourse_3);
                final EditText biographyET = (EditText)findViewById(R.id.plainText_biography);

                //Set the path for this user using user ID
                DocumentReference userPath = FirebaseFirestore.getInstance().document("UserList/"+userId);

                //Get information from editText and spinner
                newUser.setUserId(userId);
                newUser.setName(nameET.getText().toString());
                newUser.setGender(genderET.getSelectedItem().toString());
                newUser.setSchool(schoolET.getSelectedItem().toString());
                newUser.setMajor(majorET.getSelectedItem().toString());
                newUser.setMinor(minorET.getSelectedItem().toString());
                newUser.setPostalCode(postalCodeET.getText().toString());
                if (personalityET.getSelectedItem().toString().equals("Outgoing")){
                    newUser.setPersonality(1);
                }
                else if (personalityET.getSelectedItem().toString().equals("Reserved")){
                    newUser.setPersonality(-1);
                }
                else{
                    newUser.setPersonality(0);
                }
                List<String> courseTaken = new LinkedList<>();
                courseTaken.add(courseTaken_1ET.getText().toString());courseTaken.add(courseTaken_2ET.getText().toString());
                courseTaken.add(courseTaken_3ET.getText().toString());courseTaken.add(courseTaken_4ET.getText().toString());

                List<String> favCourse = new LinkedList<>();
                favCourse.add(favCourse_1ET.getText().toString());
                favCourse.add(favCourse_2ET.getText().toString());
                favCourse.add(favCourse_3ET.getText().toString());

                newUser.setCurrentClassed(courseTaken);
                newUser.setFavPastClass(favCourse);
                newUser.setBiography(biographyET.getText().toString());

                //Push it to the firebase
                userPath.set(newUser).addOnCompleteListener(new OnCompleteListener<Void>(){
                    @Override
                    public void onComplete(@NonNull Task<Void> task){
                        if ( task.isSuccessful()){
                            Log.d(TAG, "Document was saved!");
                        }
                        else{
                            Log.w(TAG,"Document save failed!",task.getException());
                        }
                    }
                });
//                //Update user ID number
//                userId++;
//                updateUserId();
            }
        });
    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }

//    private void updateUserId(){
//        Map<String, Object> userIdToSave = new HashMap<String, Object>();
//        userIdToSave.put("userId",userId);
//        UserIdGetter.set(userIdToSave).addOnCompleteListener(new OnCompleteListener<Void>(){
//            @Override
//            public void onComplete(@NonNull Task<Void> task){
//                if ( task.isSuccessful()){
//                    Log.d(TAG, "User Id updated!");
//                }
//                else{
//                    Log.w(TAG,"User Id update failed!",task.getException());
//                }
//            }
//        });
//    }
}