package com.example.adam.diceroller;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private int currImage = 0;
    private static int playerScore = 0;
    private static int Turn = 0;
    private static int computerScore = 0;
    private static int roll = 0;
    boolean userTurn = true;

    private Integer images[] = {R.drawable.dice1,
                                R.drawable.dice2,
                                R.drawable.dice3,
                                R.drawable.dice4,
                                R.drawable.dice5,
                                R.drawable.dice6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateScore();
        setInitialImage();
    }

    private void setInitialImage() {
        setCurrentImage();
    }

    private void setCurrentImage(){
        final ImageView imageView = (ImageView) findViewById(R.id.imageDisplay);
        imageView.setImageResource(images[currImage]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void diceRoll(View view) {
        randomRoll();
        currImage = roll - 1;
        setCurrentImage();
        if (roll == 1) {
            userTurn = false;
            Turn = 0;
            compTurn();
        } else {
            Turn += roll;
        }
        updateScore();
    }
    public void holdScore(View view) {
        playerScore += Turn;
        userTurn = false;
        Log.d("UserScore", "" + playerScore);
        /*if(userTurn){
            playerScore += Turn;
            userTurn = false;
            Log.d("UserScore", ""+playerScore);
        } else {
            computerScore += Turn;
            userTurn = true;
        }*/
        Turn = 0;
        updateScore();
        compTurn();
    }

    public void reset(View view){
        playerScore = computerScore = Turn = 0;
        Log.d("UserScore", ""+ playerScore);
        Log.d("UserTurn", ""+ Turn);
        Log.d("ComputerScore", ""+ computerScore);
        Log.d("ComputerTurn", ""+ Turn);
        userTurn = true;
        updateScore();
    }

    public void randomRoll(){
        Random rand = new Random();
        roll = rand.nextInt(6) + 1;
        currImage = roll;
    }

    public void updateScore() {
        Resources res = getResources();
        String text = String.format(res.getString(R.string.scores), playerScore, computerScore, Turn);
        ((TextView)findViewById(R.id.score)).setText(text);
    }
    public void compTurn() {
        Button rollBtn = (Button) findViewById(R.id.button);
        rollBtn.setClickable(false);
        Button holdBtn = (Button) findViewById(R.id.button);
        holdBtn.setClickable(false);
        randomRoll();
        while(roll != 1 && Turn < 20){
                Turn += roll;
                updateScore();
                randomRoll();
            Log.d("Comp Roll", "" + roll);
            Log.d("Comp Total Turn Score", ""+Turn);
            }
        if(roll == 1) {
            Turn = 0;
            Log.d("Computer End", "got a 1");
        } else {
            computerScore += Turn;
            Turn = 0;
            Log.d("Computer End, no 1", ""+computerScore);
        }
        userTurn = true;
        updateScore();
        rollBtn.setClickable(true);
        holdBtn.setClickable(true);
    }
}