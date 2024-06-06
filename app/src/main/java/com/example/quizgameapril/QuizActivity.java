// QuizActivity.java
package com.example.quizgameapril;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView textViewQuestion;
    private TextView[] answerTextViews; // Array to hold answer TextViews
    private Button buttonSubmit;
    private Button buttonExitQuiz;
    private TextView textViewError;
    private TextView textViewTotalScore;
    private View scoreRectangle;

    private List<Question> questionList;
    private int currentQuestionIndex = 0;
    private int totalScore = 0;
    private String selectedAnswerText = ""; // Initialize selected answer text

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        textViewQuestion = findViewById(R.id.textViewQuestion);
        answerTextViews = new TextView[]{
                findViewById(R.id.textViewAnswer1),
                findViewById(R.id.textViewAnswer2),
                findViewById(R.id.textViewAnswer3),
                findViewById(R.id.textViewAnswer4)
        };
        buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonExitQuiz = findViewById(R.id.buttonExitQuiz);
        textViewError = findViewById(R.id.textViewError);
        textViewTotalScore = findViewById(R.id.textViewTotalScore);
        scoreRectangle = findViewById(R.id.scoreRectangle);

        handler = new Handler();

        questionList = new ArrayList<>();
        questionList.add(new Question("What does DRS stand for in Formula 1?", "Drag Reduction System", "Downforce Retention System", "Dynamic Racing Suspension", "Drive Recovery System", "Drag Reduction System"));
        questionList.add(new Question("Which Grand Prix circuit features the famous Eau Rouge corner?", "Circuit de Spa-Francorchamps", "Monza", "Suzuka", "Circuit of the Americas", "Circuit de Spa-Francorchamps"));
        questionList.add(new Question("In Formula 1, what does the term \"blue flags\" indicate to drivers?", "Faster cars are approaching to lap them", "Slow-moving vehicles ahead", "Rain is approaching", "Mechanical failure in the car", "Faster cars are approaching to lap them"));
        questionList.add(new Question("What is the minimum weight requirement (including the driver) for Formula 1 cars?", "650 kg", "600 kg", "700 kg", "750 kg", "650 kg"));
        questionList.add(new Question("Which Formula 1 team holds the record for the most Constructors' Championships?", "Ferrari", "Mercedes", "McLaren", "Red Bull Racing", "Ferrari"));

        Collections.shuffle(questionList);

        showQuestion();

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedAnswerText.isEmpty()) {
                    textViewError.setVisibility(View.VISIBLE);
                    return;
                }

                buttonSubmit.setEnabled(false);
                textViewError.setVisibility(View.GONE);

                checkAnswer(selectedAnswerText);
            }
        });

        buttonExitQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void showQuestion() {
        if (currentQuestionIndex < questionList.size()) {
            Question currentQuestion = questionList.get(currentQuestionIndex);
            textViewQuestion.setText(currentQuestion.getQuestion());

            String[] options = currentQuestion.getOptions();
            shuffleArray(options); // Shuffle options

            for (int i = 0; i < options.length; i++) {
                answerTextViews[i].setText((char)('a' + i) + ") " + options[i]);
                final String answerText = options[i];
                answerTextViews[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedAnswerText = answerText;
                        for (TextView textView : answerTextViews) {
                            textView.setTextColor(Color.BLACK);
                        }
                        ((TextView) v).setTextColor(Color.BLUE); // Color when clicked
                    }
                });
            }
        }
    }

    private void checkAnswer(String selectedOptionText) {
        Question currentQuestion = questionList.get(currentQuestionIndex);
        String[] options = currentQuestion.getOptions();

        for (int i = 0; i < options.length; i++) {
            if (options[i].equals(selectedOptionText)) {
                if (options[i].equals(currentQuestion.getCorrectAnswer())) {
                    answerTextViews[i].setTextColor(Color.GREEN);
                    totalScore += 10;
                    showMessage("Correct answer!");
                } else {
                    answerTextViews[i].setTextColor(Color.RED);
                    totalScore -= 2;
                    showMessage("Wrong answer. The correct answer is " + currentQuestion.getCorrectAnswer());
                }
                break;
            }
        }

        textViewTotalScore.setText("Total Score: " + totalScore);
        scoreRectangle.setBackgroundColor(Color.TRANSPARENT);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                selectedAnswerText = ""; // Reset selected answer text
                for (TextView textView : answerTextViews) {
                    textView.setTextColor(Color.BLACK);
                }
                buttonSubmit.setEnabled(true);
                currentQuestionIndex++;
                if (currentQuestionIndex < questionList.size()) {
                    showQuestion();
                } else {
                    Intent intent = new Intent(QuizActivity.this, QuizCompletionActivity.class);
                    intent.putExtra("totalScore", totalScore);
                    startActivity(intent);
                    finish();
                }
            }
        }, 1000);
    }

    private void showMessage(String message) {
        System.out.println(message);
    }

    // Method to shuffle an array
    private void shuffleArray(String[] array) {
        List<String> list = new ArrayList<>();
        for (String element : array) {
            list.add(element);
        }
        Collections.shuffle(list);
        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }
    }
}
