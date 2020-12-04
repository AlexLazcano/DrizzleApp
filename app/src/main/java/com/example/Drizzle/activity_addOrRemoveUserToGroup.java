package com.example.Drizzle;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class activity_addOrRemoveUserToGroup extends AppCompatActivity {


    //This is an application of activity_pullUser,
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_to_group);
    }

    public void addUserToGroup(View view){
        Button btn = findViewById(R.id.activity_addUserToGroup_button_add);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final EditText pullUserId = (EditText)findViewById(R.id.editTextText_activity_addUserToGroup_UserId);
                DocumentReference userPath = FirebaseFirestore.getInstance().document("UserList/"+pullUserId.getText().toString());
                userPath.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    //Get the newest User Id
                    public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException error) {
                        if (documentSnapshot.exists()){
                            User tarUser = documentSnapshot.toObject(User.class);

                            //!!!Only the following part was changed from activity_pullUser!!!
                            final EditText tarGroupId = (EditText)findViewById(R.id.editTextText_activity_addUserToGroup_GroupId);
                            //This step is just convert the String to Int
                            tarUser.joinGroup(Integer.parseInt(tarGroupId.getText().toString()));
                            Toast.makeText(activity_addOrRemoveUserToGroup.this,"Done",Toast.LENGTH_SHORT).show();
                            //!!!Only the above part was changed from activity_pullUser!!!

                        }
                        else{
                            Toast.makeText(activity_addOrRemoveUserToGroup.this,"User Id not found",Toast.LENGTH_SHORT ).show();
                        }
                    }
                });
            }
        });
    }

    public void removeUserFromGroup(View view){
        Button btn = findViewById(R.id.activity_addUserToGroup_button_remove);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final EditText pullUserId = (EditText)findViewById(R.id.editTextText_activity_addUserToGroup_UserId);
                DocumentReference userPath = FirebaseFirestore.getInstance().document("UserList/"+pullUserId.getText().toString());
                userPath.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    //Get the newest User Id
                    public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException error) {
                        if (documentSnapshot.exists()){
                            User tarUser = documentSnapshot.toObject(User.class);

                            //!!!Only the following part was changed from activity_pullUser!!!
                            final EditText tarGroupId = (EditText)findViewById(R.id.editTextText_activity_addUserToGroup_GroupId);
                            //This step is just convert the String to Int
                            tarUser.leaveGroup(Integer.parseInt(tarGroupId.getText().toString()));
                            Toast.makeText(activity_addOrRemoveUserToGroup.this,"Done",Toast.LENGTH_SHORT).show();
                            //!!!Only the above part was changed from activity_pullUser!!!
                        }
                        else{
                            Toast.makeText(activity_addOrRemoveUserToGroup.this,"User Id not found",Toast.LENGTH_SHORT ).show();
                        }
                    }
                });
            }
        });
    }
}