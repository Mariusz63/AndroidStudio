package com.example.quiz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_CURRENT_INDEX = "currentIndex";
    public static final String KEY_EXTRA_ANSWER ="quiz.correctAnswer";

    private static final int REQUEST_CODE_PROMPT = 0;


    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURRENT_INDEX,currentIndex);
    }
    private Button falseButton;
    private Button trueButton;
    private Button nextButton;
    private Button promptButton;
    private TextView questionTextView;

    private int currentIndex = 0;
    private int correctAnswers = 0;
    private boolean answerWasShown;


    private Question[] questions = new Question[]{
            new Question(R.string.q_iOS,false),
            new Question(R.string.q_android,true),
            new Question(R.string.q_kotlin,true),
            new Question(R.string.q_google,true),
            new Question(R.string.q_java,true)
    };

    private void setNextQuestion(){
        trueButton.setEnabled(true);
        falseButton.setEnabled(true);
        currentIndex = (currentIndex +1)%questions.length;
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if(requestCode == REQUEST_CODE_PROMPT){
            if(data == null){return; }
            answerWasShown = data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOW,false);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.d("onCreate","Wywolana zostala metoca cyklu zycia: onCreate");
        setContentView(R.layout.activity_main);

        if(savedInstanceState!=null){
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }

        promptButton = findViewById(R.id.answer_hint_button);
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        questionTextView = findViewById(R.id.questio_text_view);
        setNextQuestion();

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                trueButton.setEnabled(false);
                falseButton.setEnabled(false);
                checkAnswerCorrectness(true);
            }

        });



        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                trueButton.setEnabled(false);
                falseButton.setEnabled(false);
                checkAnswerCorrectness(false);
            }
        });



        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                currentIndex = (currentIndex +1)%questions.length;
                answerWasShown = false;
                setNextQuestion();
            }
        });

        promptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,PromptActivity.class);
                boolean correctAnswer = questions[currentIndex].isTrueAnswer();
                intent.putExtra(KEY_EXTRA_ANSWER,correctAnswer);
                startActivityForResult(intent,REQUEST_CODE_PROMPT);
            }
        });

    }

    public class Question {
        private int questionId;
        private boolean trueAnswer;

        public Question(int questionId, boolean trueAnswer) {
            this.questionId = questionId;
            this.trueAnswer = trueAnswer;
        }

        public int getQuestionId() {
            return questionId;
        }

        public boolean isTrueAnswer() {
            return trueAnswer;
        }

    }

    private void checkAnswerCorrectness(boolean userAnswer){
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        int resultMessageId = 0;
        if(answerWasShown){
            resultMessageId = R.string.answer_was_shown;
        }else {
            if (userAnswer == correctAnswer) {
                resultMessageId = R.string.correct_answer;
                correctAnswers++;
            } else {
                resultMessageId = R.string.incorrect_answer;
            }
        }
        Toast.makeText(this, resultMessageId,Toast.LENGTH_SHORT).show();

        if(currentIndex == questions.length -1){
            showQuizResult();
        }
    }

    public void showQuizResult(){
        String resultMessage = getString(R.string.quiz_result,correctAnswers%questions.length, questions.length);
        Toast.makeText(this, resultMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("onDestroy","Wywolano metode onDestroy");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("onStop","Wywolano metode onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("onPause","Wywolano metode onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("onResume","Wywolano metode onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("onStart", "Wywolano metode onStart");
    }

}