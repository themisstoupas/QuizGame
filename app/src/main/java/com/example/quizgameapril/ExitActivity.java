package com.example.quizgameapril;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ExitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        finishAffinity(); // Close the app
    }
}
