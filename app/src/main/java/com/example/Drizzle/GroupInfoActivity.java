package com.example.Drizzle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class GroupInfoActivity extends AppCompatActivity {


    TextView title, desc;
    String data1, data2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);
        title = findViewById(R.id.GroupInfoTitle);
        desc = findViewById(R.id.GroupInfoDesc);

        getData();
        setData();

    }


    private void getData(){
        if(getIntent().hasExtra("data1") && getIntent().hasExtra("data2")){
            data1 = getIntent().getStringExtra("data1");
            data2 = getIntent().getStringExtra("data2");


        }else{
            Toast.makeText(this, "no data", Toast.LENGTH_SHORT).show();
        }

    }
    private void setData(){
        title.setText(data1);
        desc.setText(data2);


    }

}