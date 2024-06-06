// HighScoresActivity.java
package com.example.quizgameapril;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class HighScoresActivity extends AppCompatActivity {

    private TextView textViewHighScores;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        textViewHighScores = findViewById(R.id.textViewHighScores);

        databaseHelper = new DatabaseHelper(this);
        List<HighScore> highScores = databaseHelper.getAllHighScores();
        displayHighScores(highScores);
    }

    private void displayHighScores(List<HighScore> highScores) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < highScores.size(); i++) {
            HighScore highScore = highScores.get(i);
            sb.append(i + 1).append(". ")
                    .append(highScore.getPlayerName()).append(": ")
                    .append(highScore.getScore()).append("\n");
        }
        textViewHighScores.setText(sb.toString());
    }

    public void onBackButtonClick(View view) {
        // Navigate back to MainActivity
        onBackPressed();
    }
}
