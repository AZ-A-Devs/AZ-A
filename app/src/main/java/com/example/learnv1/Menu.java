package com.example.learnv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

/*
first activity, its the menu
 */
public class Menu extends AppCompatActivity {
    private Spinner spinner1;
    private TextView txtHighScore;//temporal

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //locks screen rotation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        //Creates an array to select the difficulty with the spinner
        spinner1 = (Spinner)findViewById(R.id.spinner);
        String [] options = {"Easy", "Medium", "Hard"};

        txtHighScore = findViewById(R.id.TextHighScore);

        ArrayAdapter <String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        spinner1.setAdapter(adapter);

        /*
        This shows the highest Score for the difficulty selected in the Menu
         */
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selection = spinner1.getSelectedItem().toString();

                if(selection.equals("Easy")){
                    SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                    int h_score = sharedPreferences.getInt("Easy", 0);
                    txtHighScore.setText("High Score: "+h_score);
                } else if (selection.equals("Medium")) {
                    SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                    int h_score = sharedPreferences.getInt("Medium", 0);
                    txtHighScore.setText("High Score: "+h_score);
                } else if (selection.equals("Hard")) {
                    SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
                    int h_score = sharedPreferences.getInt("Hard", 0);
                    txtHighScore.setText("High Score: "+h_score);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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