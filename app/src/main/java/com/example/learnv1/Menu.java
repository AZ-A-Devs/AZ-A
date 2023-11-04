package com.example.learnv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/*
first activity, its the menu
 */
public class Menu extends AppCompatActivity {
    private Spinner spinner1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Create an Array
        spinner1 = (Spinner)findViewById(R.id.spinner);
        String [] options = {"Easy", "Medium", "Hard"};

        ArrayAdapter <String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        spinner1.setAdapter(adapter);
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

    public void Play(View view){
        String selection = spinner1.getSelectedItem().toString();
        if(selection.equals("Easy")){
            Easy();
        } else if (selection.equals("Medium")) {
            Medium();
        } else if (selection.equals("Hard")) {
            Hard();
        }
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
    public void Easy() {
        RunGame(6, 5, 5, 30000);
    }

    public void Medium () {
        RunGame(11, 10, 10, 60000);
    }

    public void Hard() {
        RunGame(16, 20, 20, 120000);
    }
}