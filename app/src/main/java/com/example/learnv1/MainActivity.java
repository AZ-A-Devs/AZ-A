package com.example.learnv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MainActivity extends AppCompatActivity {

    int counter = 0;
    A a = new A("a");
    Z z = new Z("z");
    A a2 = new A("a");
    Result result = new Result();
    private EditText edtA;
    private EditText edtZ;
    private Button submitValues;
    TextView textCountDown;
    private static final long time_per_question = 60000;
    private CountDownTimer countDownTimer;
    private long timeLeft = time_per_question;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        submitValues = findViewById(R.id.BtnSubmitAns);
        textCountDown = findViewById(R.id.TextCountDown);
        startTimer();

        TextView txtResult = findViewById(R.id.TextResult);
        TextView txtEquation = findViewById(R.id.TextEquation);
        edtA = findViewById(R.id.edtA);
        edtZ = findViewById(R.id.edtZ);

        a.setValues();
        z.setValues();
        a2.setValues( a.getValue() );

        result.setValues(a.getTotal() + z.getTotal() + a2.getTotal(),
                a.getRep() + z.getRep() + a2.getRep());

        txtResult.setText(result.getValue()+"");
        txtEquation.setText(result.getRep());

        edtA.addTextChangedListener(valuesTextWatcher);
        edtZ.addTextChangedListener(valuesTextWatcher);
    }

    public void onBackPressed() {
        stopTimer();
        finish(); // Finish the activity when the back button is pressed
    }

    private TextWatcher valuesTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String aField = edtA.getText().toString().trim();
            String zField = edtZ.getText().toString().trim();

            submitValues.setEnabled( containsNumber(aField) && containsNumber(zField) );
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public void Generate(View view)
    {
        textCountDown = findViewById(R.id.TextCountDown);
        resetTimer();

        TextView txtResult = findViewById(R.id.TextResult);
        TextView txtEquation = findViewById(R.id.TextEquation);

        a.setValues();
        z.setValues();
        a2.setValues( a.getValue() );

        result.setValues(a.getTotal() + z.getTotal() + a2.getTotal(),
                a.getRep() + z.getRep() + a2.getRep());

        txtResult.setText(result.getValue()+"");
        txtEquation.setText(result.getRep());

        edtA.addTextChangedListener(valuesTextWatcher);
        edtZ.addTextChangedListener(valuesTextWatcher);
    }

    public void Submit(View view) {
        double aGuessed = 0;
        double zGuessed = 0;

        TextView txtConclusion = findViewById(R.id.TextConclusion);
        edtA = findViewById(R.id.edtA);
        edtZ = findViewById(R.id.edtZ);

        try{
            aGuessed = Double.parseDouble(edtA.getText().toString());
            zGuessed = Double.parseDouble(edtZ.getText().toString());

        }catch(NumberFormatException e){
            Toast.makeText(this, "Only numbers pls", Toast.LENGTH_SHORT).show();
            Generate(view);
            return;
        }

        if( result.getValue() == a.getCoeficient() * aGuessed + z.getCoeficient() * zGuessed + a2.getCoeficient() * aGuessed){
            counter++;
            txtConclusion.setText("Correct: " + counter);
            Generate(view);
        }else{
            lost("Incorrect!");
        }
    }

    public void lost(String wayOfLosing){
        stopTimer();
        Intent intent = new Intent(this, PopUp.class);
        intent.putExtra("score", counter+"");
        intent.putExtra("lost", wayOfLosing);
        startActivity(intent);
        finish();
    }

    private void startTimer(){
        stopTimer();

        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                updateTextCountDown();
            }

            @Override
            public void onFinish() {
                lost("Out of time!");
            }
        }.start();
    }

    private void resetTimer() {
        stopTimer();
        timeLeft = time_per_question;
        updateTextCountDown();
        startTimer();
    }

    private void stopTimer(){
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Stop the current timer if it's running.
        }
    }

    private void updateTextCountDown() {
        int seconds = (int) timeLeft / 1000;
        String timeLeftFormatted = "Time Left: "+ seconds;
        textCountDown.setText(timeLeftFormatted);
    }

    private boolean containsNumber(String text) {
        Pattern pattern = Pattern.compile(".*\\d+.*");
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }
}