package com.example.lib_firestore.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.lib_firestore.R;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        getSupportActionBar().hide();
    }
}