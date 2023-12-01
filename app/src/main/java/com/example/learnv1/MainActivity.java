/*issues:
-Not solved: time should be handled as long, but i had to cast it to int and handle it that way
    cuz i had problems when sending the longs, so i casted them to ints, here and in PopUp
 */
//goooooooool
package com.example.learnv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import android.content.Context;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.text.InputFilter;
import android.content.pm.ActivityInfo;

//main game
public class MainActivity extends AppCompatActivity {

    int counter = 0; //keeps count of the score
    int test = 0;

    //values depending on the difficulty
    private int bound, lower, upper;
    private final int maxLength = 4;

    //objects for the equations
    A a = new A("a");
    Z z = new Z("z");
    A a2 = new A("a");
    Result result = new Result();
    private EditText edtA;//input for a
    private EditText edtZ;//input for z
    private TextView txtResult;//value to find
    private TextView txtEquation;//equation
    private TextView txtConclusion;//score
    private TextView txtSolveFor;
    private TextView txtFindA;
    private TextView txtFindZ;
    private Button submitValues;//buttonto submit answer

    //timer
    TextView textCountDown;
    private static int time_per_question;
    private CountDownTimer countDownTimer;
    private int timeLeft;

    //to preserve counter
    private TextView textHighScore;
    public static final String SHARED_PREFS = "shared preferences";
    private int h_score;

    //sound
    MediaPlayer correctSound;
    MediaPlayer incorrectSound;

/*
This method is executed when the activity is created
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        correctSound = MediaPlayer.create(this, R.raw.correct);
        incorrectSound = MediaPlayer.create(this, R.raw.incorrect);

        txtSolveFor = findViewById(R.id.TextResultHint);
        txtSolveFor.setText(getString(R.string.solve_for));
        txtFindA = findViewById(R.id.edt2A);
        txtFindZ = findViewById(R.id.edt2Z);
        txtFindA.setText(getString(R.string.find_a));
        txtFindZ.setText(getString(R.string.find_z));

        //locks screen rotation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //setting the values depending on the difficulty
        bound = getIntent().getIntExtra("bound", 10);
        lower = getIntent().getIntExtra("lower", 10);
        upper = getIntent().getIntExtra("upper", 10);
        time_per_question = getIntent().getIntExtra("time", 60000);
        timeLeft = time_per_question;


        /*
        Creating all the objects that refer to the objects that the
        user sees
         */
        submitValues = findViewById(R.id.BtnSubmitAns);
        submitValues.setText(getString(R.string.submit));
        textCountDown = findViewById(R.id.TextCountDown);
        edtA = findViewById(R.id.edtA);
        edtZ = findViewById(R.id.edtZ);
        txtResult = findViewById(R.id.TextResult);
        txtEquation = findViewById(R.id.TextEquation);
        txtConclusion = findViewById(R.id.TextConclusion);
        textHighScore = findViewById(R.id.TextHighScore);

        Generate();//updating for the first time
    }

    protected void onDestroy() {
        super.onDestroy();
        if (correctSound != null) {
            correctSound.release();
        }
        if (incorrectSound != null) {
            incorrectSound.release();
        }
    }

    /*
    Updates the values in case of a correct answer (switch question)
     */
    public void Generate()
    {
        loadData();//loads the high score
        resetTimer();//start and reset the timer

        /*
        setting the random values
         */
        a.setValues( bound, lower, upper );
        z.setValues( bound, lower, upper );
        a2.setValues2( a.getValue(), lower, upper );
        result.setValues(a.getTotal() + z.getTotal() + a2.getTotal(),
                a.getRep() + z.getRep() + a2.getRep());

        txtResult.setText(result.getValue() + "");//showing the user the value
        txtEquation.setText(result.getRep());//showing the user the equation

        /*
        this allows the submit button to be activated only
        when theres numbers in each input
         */
        edtA.addTextChangedListener(valuesTextWatcher);
        edtZ.addTextChangedListener(valuesTextWatcher);

        setEditorActionListener(edtA, edtZ); // editText1 to editText2 auto
        setEditorActionListener(edtZ, null); // editText2
    }

    /*
    This is the method executed when the submit button is pressed
     */
    public void Submit(View view) {
        double aGuessed = 0;
        double zGuessed = 0;


        //this is just in case, there shouldnt be any exceptions of that sort tbh
        try{
            aGuessed = Double.parseDouble(edtA.getText().toString());
            zGuessed = Double.parseDouble(edtZ.getText().toString());
            edtA.setText("");
            edtZ.setText("");

        }catch(NumberFormatException e){
            Toast.makeText(this, "Only numbers pls", Toast.LENGTH_SHORT).show();
            Generate();
            return;
        }

        //this validates that the answer is right
        if( result.getValue() == a.getCoeficient() * aGuessed + z.getCoeficient() * zGuessed + a2.getCoeficient() * aGuessed){
            //if the answer is right

            // play correct sound
            correctSound.start();

            counter++;
            txtConclusion.setText(getString(R.string.current_score) + counter);

            //update high score
            if(counter > h_score){
                saveData();
            }
            Generate();
        }else{

            lost(getString(R.string.incorrect));
        }
    }

    //this makes the change from edittext to edittext automatic
    private void setEditorActionListener(final EditText editText, final EditText nextEditText) {
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    if (nextEditText != null) {
                        nextEditText.requestFocus();
                    } else {
                        // Hide the keyboard if there is no next EditText
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    //method called when the android back nbutton is pressed
    public void onBackPressed() {
        stopTimer();
        finish(); // Finish the activity when the back button is pressed
    }

    //this method is used as validator of the input, it is called everytime an edit text
    // is updated
    private TextWatcher valuesTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        //checks that theres a num to activate the submit button
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String aField = edtA.getText().toString().trim();
            String zField = edtZ.getText().toString().trim();

            submitValues.setEnabled( containsNumber(aField) && containsNumber(zField) );
        }

        //gets only the number part in case there are letter or other chars in between
        // and it alsa limits the input
        @Override
        public void afterTextChanged(Editable s) {
            String text = s.toString();
            if (!text.isEmpty()) {
                int enteredLength = text.length();
                if (enteredLength > maxLength) {
                    s.delete(enteredLength - 1, enteredLength);
                }
            }
            if (!text.isEmpty() && !text.matches("-?\\d*")) {
                // If the text is not a valid number, remove non-numeric characters
                s.replace(0, s.length(), text.replaceAll("[^\\d-]", ""));
            }
        }
    };

    public void lost(String wayOfLosing){
        //called when user looses, calls new activity and stops the current one
        stopTimer();
        Intent intent = new Intent(this, PopUp.class);
        intent.putExtra("score", counter+"");
        intent.putExtra("lost", wayOfLosing);
        intent.putExtra("time", time_per_question);
        intent.putExtra("bound", bound);
        intent.putExtra("lower", lower);
        intent.putExtra("upper", upper);
        intent.putExtra("answer", result.getRep()+"\na = "+a.getValue()+"   z = "+z.getValue());
        incorrectSound.start(); // if the answer is incorrect
        startActivity(intent);
        finish();//finishes current activity
    }

    private void startTimer(){
        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = (int) millisUntilFinished;
                updateTextCountDown();//upadtes the timer for the user to see the time
            }

            @Override
            public void onFinish() {
                lost(getString(R.string.out_of_time));//calls lost method if time ends
            }
        }.start();
    }

    private void resetTimer() {
        stopTimer();//stops the time in case its running
        timeLeft = time_per_question; //set the value to the orgininal
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
        String timeLeftFormatted = getString(R.string.time_left)+ seconds;
        textCountDown.setText(timeLeftFormatted);
    }

    /*
    used to preserve data, high score in this case
     */
    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(bound == 6){
            editor.putInt(getString(R.string.easy), counter);
        }else if(bound == 11){
            editor.putInt(getString(R.string.medium), counter);
        }else{
            editor.putInt(getString(R.string.hard), counter);
        }
        editor.apply();//must do
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        if(bound == 6){
            h_score = sharedPreferences.getInt(getString(R.string.easy), 0);
        }else if(bound == 11){
            h_score = sharedPreferences.getInt(getString(R.string.medium), 0);
        }else{
            h_score = sharedPreferences.getInt(getString(R.string.hard), 0);
        }
        updateHighScore();
    }

    public void updateHighScore(){
        textHighScore.setText(getString(R.string.high_score) + h_score);
    }

    public void notUpdateHighScore() {
        System.out.println("hey how you doinn");
    }

    private boolean containsNumber(String text) {
        Pattern pattern = Pattern.compile(".*\\d+.*");//regex
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }
}