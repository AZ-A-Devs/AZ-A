package com.example.learnv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PopUp extends AppCompatActivity {
    private int bound, lower, upper;
    String answer;
    private int time;
    private TextView txtLost;
    private Button btnPlayAgain;
    private Button btnQuit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);

        txtLost = findViewById(R.id.TextLost);
        txtLost.setText(getString(R.string.you_lost));
        btnPlayAgain = findViewById(R.id.BtnAgain);
        btnPlayAgain.setText(getString(R.string.play_again));
        btnQuit = findViewById(R.id.BtnQuit);
        btnQuit.setText(getString(R.string.quit));



        //locks screen rotation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //collecting all the values to show and/or restart
        String score = getIntent().getStringExtra("score");
        String lost = getIntent().getStringExtra("lost");
        time = getIntent().getIntExtra("time", 70000);
        bound = getIntent().getIntExtra("bound", 11);
        lower = getIntent().getIntExtra("lower", 10);
        upper = getIntent().getIntExtra("upper", 10);
        answer = getIntent().getStringExtra("answer");

        TextView  txtScore = findViewById(R.id.TextScore);
        TextView txtAns = findViewById(R.id.TextAns);
        txtScore.setText(lost + getString(R.string.your_score_was) + score);
        txtAns.setText(getString(R.string.answer_was)+answer);
    }

    public void onBackPressed() {
        finish(); // Finish the activity when the back button is pressed
    }

    //play again with the same difficulty so we gotta preserve the info
    public void PlayAgain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("bound", bound);
        intent.putExtra("lower", lower);
        intent.putExtra("upper", upper);
        intent.putExtra("time", time);
        startActivity(intent);

        finish();
    }

    public void Quit(View view) {
        finish();
    }
}