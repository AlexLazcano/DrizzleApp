package com.example.Drizzle;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class activity_pullAllData extends AppCompatActivity {

    private final String TAG = "activity_pullAllData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_all_data);
    }

    public void pullAllUserDataFromSever(View view){
        Button btw = findViewById(R.id.button_pullAllUserData);
        btw.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View w){
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("UserList").get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()) {
                                    UserPool newPool = new UserPool();
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        User tarUser = document.toObject(User.class);
                                        newPool.addUser(tarUser);
                                    }
                                    final TextView dataOutput = findViewById(R.id.textView_AllDataOutput);
                                    dataOutput.setMovementMethod(ScrollingMovementMethod.getInstance());
                                    dataOutput.setText(newPool.outPutAllInfo());
                                    //Or do other operations here...
                                }
                                else{
                                    Log.d(TAG,"Error getting User: ", task.getException());
                                }
                            }
                        });
            }
        });
    }

    public void pullAllGroupDataFromSever(View view){
        Button btw = findViewById(R.id.button_pullAllGroupData);
        btw.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View w){
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("GroupList").get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()) {
                                    UserPool newPool = new UserPool();
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Group tarGroup = document.toObject(Group.class);
                                        newPool.addGroup(tarGroup);
                                    }
                                    final TextView dataOutput = findViewById(R.id.textView_AllDataOutput);
                                    dataOutput.setMovementMethod(ScrollingMovementMethod.getInstance());
                                    dataOutput.setText(newPool.outPutAllInfo());
                                    //Or do other operations here...
                                }
                                else{
                                    Log.d(TAG,"Error getting User: ", task.getException());
                                }
                            }
                        });
            }
        });
    }
}