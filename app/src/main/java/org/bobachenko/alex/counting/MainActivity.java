package org.bobachenko.alex.counting;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView txtExpression;
    TextView txtCaption;
    TextView txtAnswerValue;
    TextView txtScore;
    Button btnStart;
    TableLayout keyboard;
    ProgressBar progressBar;
    ImageView imageScore;

    Random random = new Random(System.currentTimeMillis());

    final int COUNT_OF_QUESTIONS = 9;
    int currentQuestion = 0;
    int currentResult;
    int rightAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtExpression = (TextView) findViewById(R.id.txtExpression);
        txtCaption = (TextView) findViewById(R.id.txtCaption);
        txtAnswerValue = (TextView) findViewById(R.id.txtAnswerValue);
        txtScore = (TextView) findViewById(R.id.txtScore);
        btnStart = (Button) findViewById(R.id.btnStart);
        keyboard = (TableLayout) findViewById(R.id.keyboard);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        imageScore = (ImageView) findViewById(R.id.imageScore);
    }

    public void onButtonStartClick (View view) {
        txtExpression.setVisibility(View.VISIBLE);
        txtCaption.setVisibility(View.VISIBLE);
        btnStart.setVisibility(View.INVISIBLE);
        keyboard.setVisibility(View.VISIBLE);
        txtScore.setVisibility(View.INVISIBLE);
        imageScore.setVisibility(View.INVISIBLE);

        rightAnswers = 0;
        currentQuestion=0;
        progressBar.setProgress(0);
        newQuestion();
    }

    void newQuestion() {
        if(currentQuestion>COUNT_OF_QUESTIONS){
            showResults();
            return;
        }

        boolean isPlus = random.nextInt(10) % 2 == 0;

        int x = random.nextInt(11);
        int y = random.nextInt(11);

        String operation = "";

        if(isPlus) {
            currentResult = x + y;
            operation = "+";
        }
        else {
            operation = "-";
            if(x < y) {
                int temp = x;
                x = y;
                y = temp;
            }

            currentResult = x - y;
        }

        txtExpression.setText(x + operation + y);
    }

    public void onButtonDigitClick (View view) {
        Button btnDigit = (Button)view;
        if(txtAnswerValue.getText().toString().length()<4)
            txtAnswerValue.setText(txtAnswerValue.getText().toString()+btnDigit.getText().toString());
    }

    public void onButtonClearClick (View view) {
        txtAnswerValue.setText("");
    }

    public void onButtonAnswerClick (View view) {
        if(txtAnswerValue.getText().toString().isEmpty()) {
            return;
        }

        int answer = Integer.parseInt(txtAnswerValue.getText().toString());

        if(answer==currentResult) {
            rightAnswers++;
        }

        txtAnswerValue.setText("");
        currentQuestion++;
        progressBar.setProgress(currentQuestion);

        newQuestion();
    }

    void showResults() {
        txtExpression.setVisibility(View.INVISIBLE);
        txtCaption.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.VISIBLE);
        keyboard.setVisibility(View.INVISIBLE);
        txtScore.setVisibility(View.VISIBLE);

        txtScore.setText("Результат - " + rightAnswers + " правильных ответов.");

        imageScore.setVisibility(View.VISIBLE);

        if(rightAnswers==10)
            imageScore.setImageResource(R.drawable.img5);

        if(rightAnswers<10 && rightAnswers>7)
            imageScore.setImageResource(R.drawable.img4);

        if(rightAnswers<8 && rightAnswers>4)
            imageScore.setImageResource(R.drawable.img3);

        if(rightAnswers<5)
            imageScore.setImageResource(R.drawable.img2);
    }
}