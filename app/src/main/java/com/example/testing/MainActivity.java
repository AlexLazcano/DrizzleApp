package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Alex  Branch */
//        View t = findViewById(R.id.nav);
//        NavController navController = findNavController(R.id.fragment);

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav);
        NavController navController = Navigation.findNavController(this, R.id.navFrag);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);



        /*BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this,  R.id.fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);*/


    }




}