package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button falseButton;
    private Button trueButton;
    private Button nextButton;
    private TextView questionTextView;

    private int currentIndex = 0;
    private int correctAnswers = 0;

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
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                setNextQuestion();
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
        int reesultMessageId = 0;
        if(userAnswer == correctAnswer){
            reesultMessageId = R.string.correct_answer;
            correctAnswers++;
        }else{
            reesultMessageId = R.string.incorrect_answer;
        }
        Toast.makeText(this, reesultMessageId,Toast.LENGTH_SHORT).show();

        if(currentIndex == questions.length -1){
            showQuizResult();
        }
    }

    public void showQuizResult(){
        String resultMessage = getString(R.string.quiz_result,correctAnswers%questions.length, questions.length);
        Toast.makeText(this, resultMessage, Toast.LENGTH_SHORT).show();
    }

}