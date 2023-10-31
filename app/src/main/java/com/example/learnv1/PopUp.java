package com.example.learnv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PopUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);
        String score = getIntent().getStringExtra("score");
        String lost = getIntent().getStringExtra("lost");

        TextView  txtScore = findViewById(R.id.TextScore);
        txtScore.setText(lost + "\nYour score was: " + score);
    }

    public void onBackPressed() {
        finish(); // Finish the activity when the back button is pressed
    }

    public void PlayAgain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void Quit(View view) {
        finish();
    }
}