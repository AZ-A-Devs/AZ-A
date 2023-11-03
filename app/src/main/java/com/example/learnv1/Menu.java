package com.example.learnv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
/*
first activity, its the menu
 */
public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void onBackPressed() {
        finish(); // Finish the activity when the back button is pressed
    }

    /*
    creates a new game activity if selected
     */
    public void RunGame(int bound, int lower, int upper, int time) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("bound", bound);
        intent.putExtra("lower", lower);
        intent.putExtra("upper", upper);
        intent.putExtra("time", time);
        startActivity(intent);
    }

    /*
    exits the game if selected
     */
    public void Exit(View view) {
        System.exit(0);
        finish();
    }

    /*
    This helps set the values depending on the difficulty :)
     */
    public void Easy(View view) {
        RunGame(6, 5, 5, 30000);
    }

    public void Medium(View view) {
        RunGame(11, 10, 10, 60000);
    }

    public void Hard(View view) {
        RunGame(16, 20, 20, 120000);
    }
}