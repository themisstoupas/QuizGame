// QuizCompletionActivity.java
package com.example.quizgameapril;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class QuizCompletionActivity extends AppCompatActivity {

    private EditText editTextName;
    private int totalScore;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_completion);

        editTextName = findViewById(R.id.editTextName);
        totalScore = getIntent().getIntExtra("totalScore", 0);

        databaseHelper = new DatabaseHelper(this);
    }

    public void onSaveButtonClick(View view) {
        String playerName = editTextName.getText().toString().trim();
        if (!playerName.isEmpty()) {
            databaseHelper.addHighScore(playerName, totalScore);
            finish();
        } else {
            editTextName.setError("Please enter your name");
        }
    }
}
