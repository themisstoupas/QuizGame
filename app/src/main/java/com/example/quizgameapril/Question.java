// Question.java
package com.example.quizgameapril;

public class Question {
    private String question;
    private String[] options;
    private String correctAnswer;

    public Question(String question, String optionA, String optionB, String optionC, String optionD, String correctAnswer) {
        this.question = question;
        this.options = new String[]{optionA, optionB, optionC, optionD};
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
