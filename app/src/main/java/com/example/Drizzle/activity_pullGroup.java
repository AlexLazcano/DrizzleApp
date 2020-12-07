/**
 * Example for pull group from firebase
 * @author Haoyuan Zhao
 * @version 1
 * @since 2020-11-23
 */
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

public class activity_pullGroup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_group);
    }

    public void pullGroupFromServer(View view){
        Button btn = findViewById(R.id.button_pullGroup);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final EditText pullGroupId = (EditText)findViewById(R.id.editText_pullGroup);
                DocumentReference groupPath = FirebaseFirestore.getInstance().document("GroupList/"+pullGroupId.getText().toString());
                groupPath.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    //Get the newest User Id
                    public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException error) {
                        if (documentSnapshot.exists()){
                            Group tarGroup = documentSnapshot.toObject(Group.class);
                            Toast.makeText(activity_pullGroup.this, "Group name: "+tarGroup.getGroupName()+
                                    "\nGroup Id: " + tarGroup.getGroupId()+
                                    "\nSize: " + tarGroup.getGroupSize(), Toast.LENGTH_LONG).show(); //...
                            //THIS IS JUST A EXAMPLE OF PULL USER
                            //You can do other operations here...
                        }
                        else{
                            Toast.makeText(activity_pullGroup.this,"Group Id not found",Toast.LENGTH_SHORT ).show();
                        }
                    }
                });
            }
        });
    }
}