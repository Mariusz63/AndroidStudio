package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PromptActivity extends AppCompatActivity {

    public static  final String KEY_EXTRA_ANSWER_SHOW = "quiz.answerShown";
    private boolean correctAnswer;
    private Button showCorrectAnswerButton;
    private Button backButton;
    private TextView answerTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prompt);

        showCorrectAnswerButton = findViewById(R.id.see_question_button);
        answerTextView = findViewById(R.id.answer_text_view);
        backButton = findViewById(R.id.back_button);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Finish the PromptActivity
            }
        });

        showCorrectAnswerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int answer = correctAnswer?R.string.button_true:R.string.button_false;
                answerTextView.setText(answer);
                setAnswerShownResult(true);
            }

            private void setAnswerShownResult(boolean answerWasShown) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra(KEY_EXTRA_ANSWER_SHOW,answerWasShown);
                setResult(RESULT_OK,resultIntent);
            }

        });

        correctAnswer = getIntent().getBooleanExtra(MainActivity.KEY_EXTRA_ANSWER,true);
    }

}