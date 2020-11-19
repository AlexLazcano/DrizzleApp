package com.example.Drizzle;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UserPool newPool = new UserPool();
        User newUser = new User(newPool);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity.context = getApplicationContext();

        final EditText nameET = (EditText)findViewById(R.id.textInput_name);
        final Spinner genderET = (Spinner)findViewById(R.id.spinner_gender);
        final Spinner schoolET = (Spinner)findViewById(R.id.spinner_school);
        final Spinner majorET = (Spinner)findViewById(R.id.spinner_major);
        final Spinner minorET = (Spinner)findViewById(R.id.spinner_minor);
        final EditText postalCodeET = (EditText)findViewById(R.id.textInput_postalCode);
        Button btn = findViewById(R.id.button_submit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newUser.setName(nameET.getText().toString());
                newUser.setGender(genderET.getSelectedItem().toString());
                newUser.setSchool(schoolET.getSelectedItem().toString());
                newUser.setMajor(majorET.getSelectedItem().toString());
                newUser.setMinor(minorET.getSelectedItem().toString());
                newUser.setPostalCode(postalCodeET.getText().toString());

//                Log.i("storeTest", newUser.getName() + "\n" +
//                                            newUser.getGender() + "\n" +
//                                            newUser.getSchool() + "\n" +
//                                            newUser.getMajor() + "\n" +
//                                            newUser.getMinor() + "\n" +
//                                            newUser.getPostalCode() + "\n");

                newUser.writeToLocal(newUser.getName());
                String info = readFromLocal(newUser.getName()+".txt");
                Log.i("From local",info);
            }
        });
    }

    public static Context getAppContext(){
        return MainActivity.context;
    }

    private String readFromLocal(String fileName) {

        String ret = "";

        try {
            InputStream inputStream = getAppContext().openFileInput("User.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret;
    }

}