package com.example.lib_firestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.lib_firestore.Student.AccountFragment;
import com.example.lib_firestore.Student.HistoryFragment;
import com.example.lib_firestore.Student.QRFragment;
import com.example.lib_firestore.Student.SearchFragment;
import com.example.lib_firestore.Student.StudentHomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class StudentbaseActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;


    StudentHomeFragment studentHomeFragment = new StudentHomeFragment();
    SearchFragment searchFragment = new SearchFragment();
    QRFragment qrFragment = new QRFragment();
    HistoryFragment historyFragment = new HistoryFragment();
    AccountFragment accountFragment = new AccountFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentbase);
        getSupportActionBar().hide();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, studentHomeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, studentHomeFragment).commit();
                        return true;
                    case R.id.search:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, searchFragment).commit();
                        return true;
                    case R.id.QR:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, qrFragment).commit();
                        return true;
                    case R.id.History:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, historyFragment).commit();
                        return true;
                    case R.id.Account:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new InfoActivity()).commit();
                        return true;
                }
                return false;
            }
        });
    }
}