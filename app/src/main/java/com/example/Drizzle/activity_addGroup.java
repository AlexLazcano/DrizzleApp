/**
 * activity_addGroup will add the single group to the firebase
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

public class activity_addGroup extends AppCompatActivity {

    public static final String TAG = "addGroup";
    private int groupId;
    public UserPool newPool = new UserPool();
    private final DocumentReference GroupIdGetter = FirebaseFirestore.getInstance().document("GroupList/CurrentGroupId");

    @Override
    protected void onStart() {
        super.onStart();
        GroupIdGetter.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            //Get the newest Group Id
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException error) {
                if (documentSnapshot.exists()){
                    groupId = documentSnapshot.getLong("groupId").intValue();
                }
                //Create a new Group ID if no exist (start from 100)
                else{
                    groupId = 100;
                    updateGroupId();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
    }

    public void addGroupToCloud(View view){
        Button btn = findViewById(R.id.button_submit_addGroup);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Connect to the button and spinner
                final EditText groupName = (EditText)findViewById(R.id.plainText_group_name);
                final Spinner groupSize = (Spinner)findViewById(R.id.spinner_groupSize);
                final EditText studyTopic = (EditText)findViewById(R.id.plainText_study_topic);

                //Set the path for this group using group ID
                DocumentReference groupPath = FirebaseFirestore.getInstance().document("GroupList/"+Integer.toString(groupId));

                //Get information from editText and spinner
                Group newGroup = new Group(
                        groupName.getText().toString(),
                        groupId,
                        groupSize.getSelectedItem().toString(),
                        studyTopic.getText().toString());

                //Push it to the firebase
                groupPath.set(newGroup).addOnCompleteListener(new OnCompleteListener<Void>(){
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

                //Update group ID number
                groupId++;
                updateGroupId();
            }
        });
    }

    private void updateGroupId(){
        Map<String, Object> groupIdToSave = new HashMap<String, Object>();
        groupIdToSave.put("groupId", groupId);
        GroupIdGetter.set(groupIdToSave).addOnCompleteListener(new OnCompleteListener<Void>(){
            @Override
            public void onComplete(@NonNull Task<Void> task){
                if ( task.isSuccessful()){
                    Log.d(TAG, "Group Id updated!");
                }
                else{
                    Log.w(TAG,"Group Id update failed!",task.getException());
                }
            }
        });
    }
}