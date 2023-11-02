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
        setContentView(R.layout.activity_main2);
    }

    public void onBackPressed() {
        finish(); // Finish the activity when the back button is pressed
    }

    /*
    creates a new game activity if selected
     */
    public void RunGame(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /*
    exits the game if selected
     */
    public void Exit(View view) {
        System.exit(0);
        finish();
    }
}