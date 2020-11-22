/**
 * activity_addUser will add the single user to the firebase
 * @author Haoyuan Zhao
 * @version 1
 * @since 2020-11-22
 */
package com.example.Drizzle;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

// Use the application default credentials

public class activity_addUser extends AppCompatActivity {

    public static final String TAG = "addUser";
    private int userId;
    public UserPool newPool = new UserPool();
    private final DocumentReference UserIdGetter = FirebaseFirestore.getInstance().document("UserList/CurrentUserId");

    @Override
    protected void onStart() {
        super.onStart();
        UserIdGetter.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            //Get the newest User Id
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException error) {
                if (documentSnapshot.exists()){
                    userId = documentSnapshot.getLong("userId").intValue();
                }
                //Create a new user ID if no exist (start from 100)
                else{
                    userId = 100;
                    updateUserId();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
    }

    public void addUserToCloud(View view){
        Button btn = findViewById(R.id.button_submit_addUser);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User newUser = new User(newPool);
                //Connect to the button and spinner
                final EditText nameET = (EditText)findViewById(R.id.plainText_user_name);
                final Spinner genderET = (Spinner)findViewById(R.id.spinner_gender);
                final Spinner schoolET = (Spinner)findViewById(R.id.spinner_school);
                final Spinner majorET = (Spinner)findViewById(R.id.spinner_major);
                final Spinner minorET = (Spinner)findViewById(R.id.spinner_minor);
                final EditText postalCodeET = (EditText)findViewById(R.id.plainText_postal_code);

                //Set the path for this user using user ID
                DocumentReference userPath = FirebaseFirestore.getInstance().document("UserList/"+Integer.toString(userId));

                //Get information from editText and spinner
                newUser.setUserId(userId);
                newUser.setName(nameET.getText().toString());
                newUser.setGender(genderET.getSelectedItem().toString());
                newUser.setSchool(schoolET.getSelectedItem().toString());
                newUser.setMajor(majorET.getSelectedItem().toString());
                newUser.setMinor(minorET.getSelectedItem().toString());
                newUser.setPostalCode(postalCodeET.getText().toString());

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

                //Update user ID number
                userId++;
                updateUserId();
            }
        });
    }

    private void updateUserId(){
        Map<String, Object> userIdToSave = new HashMap<String, Object>();
        userIdToSave.put("userId",userId);
        UserIdGetter.set(userIdToSave).addOnCompleteListener(new OnCompleteListener<Void>(){
            @Override
            public void onComplete(@NonNull Task<Void> task){
                if ( task.isSuccessful()){
                    Log.d(TAG, "User Id updated!");
                }
                else{
                    Log.w(TAG,"User Id update failed!",task.getException());
                }
            }
        });
    }
}