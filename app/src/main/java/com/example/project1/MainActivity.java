package com.example.project1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

/**
 * The main activity for the app, acting as the controller in our MVC pattern
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Instance of the ViewModel and TextViews
     */
    private WhackAMoleViewModel whackAMoleViewModel;
    private TextView currentScore;
    private TextView highScore;
    private TextView numOfLives;
    private TextView gameOver;
    private Button startBtn;

    /**
     * Overridden onCreate() method to initialize all UI elements and call functions from the ViewModel
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        /**
         * Get references to all of the moles in the UI
         */
        ImageButton mole1 = findViewById(R.id.mole1);
        ImageButton mole2 = findViewById(R.id.mole2);
        ImageButton mole3 = findViewById(R.id.mole3);
        ImageButton mole4 = findViewById(R.id.mole4);
        ImageButton mole5 = findViewById(R.id.mole5);
        ImageButton mole6 = findViewById(R.id.mole6);
        ImageButton mole7 = findViewById(R.id.mole7);
        ImageButton mole8 = findViewById(R.id.mole8);
        ImageButton mole9 = findViewById(R.id.mole9);
        ImageButton mole10 = findViewById(R.id.mole10);
        ImageButton mole11 = findViewById(R.id.mole11);
        ImageButton mole12 = findViewById(R.id.mole12);

        /**
         * Get references to the all of the TextViews in the UI
         */
        currentScore = findViewById(R.id.currentScore);
        highScore = findViewById(R.id.highScore);
        numOfLives = findViewById(R.id.amountLives);
        gameOver = findViewById(R.id.gameOver);

        /**
         * Get a reference to the start button
         */
        startBtn = findViewById(R.id.startButton);

        gameOver.setVisibility(TextView.INVISIBLE); // game over text should be invisible by default

        /**
         * Create an array of all the mole ImageViews for easy access
         */
        ImageView[] moleButtons = {mole1, mole2, mole3, mole4, mole5, mole6, mole7, mole8, mole9, mole10, mole11, mole12};

        /**
         * At the beginning of the game, show no moles on the screen.
         */
        HideMoles(moleButtons);

        /**
         * Initialize the WhatAMoleViewModel instance.
         */
        whackAMoleViewModel = new ViewModelProvider(this).get(WhackAMoleViewModel.class);

        /**
         * Update the mole picked to be visible in the UI
         */
        whackAMoleViewModel.getMoleLocation().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer visibleMoleId) {
                // Hide all moles
                HideMoles(moleButtons);

                // Show the mole corresponding to the visibleMoleId
                if (visibleMoleId >= 1) {
                    moleButtons[visibleMoleId - 1].setAlpha(1f);

                }
            }
        });

        /**
         * Update the text in the UI that displays the user's current score.
         */
        whackAMoleViewModel.getCurrentScore().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer currentScoreNum) {
                // update textview that contains the current score
                currentScore.setText(String.format("Score: %d", currentScoreNum));
            }
        });

        /**
         * Update the text in the UI that displays the user's highest score.
         */
        whackAMoleViewModel.getHighScore().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer highScoreNum) {
                // update the textview that contains the high score
                highScore.setText(String.format("High Score: %d", highScoreNum));
            }
        });

        /**
         * Update the text in the UI that displays the user's number of lives.
         */
        whackAMoleViewModel.getNumLives().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer lives) {
                // update the textview containing the amount of lives the user has
                numOfLives.setText(String.format("Lives: %d", lives));
            }
        });

        /**
         * Update the text in the UI to show game over text.
         */
        whackAMoleViewModel.isGameOver().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isGameOver) {
                if (isGameOver) {
                    // show some sort of game over screen and stop the game
                    gameOver.setVisibility(TextView.VISIBLE);
                    whackAMoleViewModel.stopGame();
                    startBtn.setVisibility(View.VISIBLE);
                    startBtn.setClickable(true);
                }
            }
        });

        /**
         * Starts spawning the moles
         */
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameOver.setVisibility(TextView.INVISIBLE);
                whackAMoleViewModel.startGame();
                startBtn.setVisibility(View.INVISIBLE);
                startBtn.setClickable(false);
            }
        });

        /**
         * Register that mole 1 was clicked
         */
        mole1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whackAMoleViewModel.moleHillClicked(1);
            }
        });

        /**
         * Register that mole 2 was clicked
         */
        mole2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whackAMoleViewModel.moleHillClicked(2);
            }
        });

        /**
         * Register that mole 3 was clicked
         */
        mole3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whackAMoleViewModel.moleHillClicked(3);
            }
        });

        /**
         * Register that mole 4 was clicked
         */
        mole4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whackAMoleViewModel.moleHillClicked(4);
            }
        });

        /**
         * Register that mole 5 was clicked
         */
        mole5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whackAMoleViewModel.moleHillClicked(5);
            }
        });

        /**
         * Register that mole 6 was clicked
         */
        mole6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whackAMoleViewModel.moleHillClicked(6);
            }
        });

        /**
         * Register that mole 7 was clicked
         */
        mole7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whackAMoleViewModel.moleHillClicked(7);
            }
        });

        /**
         * Register that mole 8 was clicked
         */
        mole8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whackAMoleViewModel.moleHillClicked(8);
            }
        });

        /**
         * Register that mole 9 was clicked
         */
        mole9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whackAMoleViewModel.moleHillClicked(9);
            }
        });

        /**
         * Register that mole 10 was clicked
         */
        mole10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whackAMoleViewModel.moleHillClicked(10);
            }
        });

        /**
         * Register that mole 11 was clicked
         */
        mole11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whackAMoleViewModel.moleHillClicked(11);
            }
        });

        /**
         * Register that mole 12 was clicked
         */
        mole12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whackAMoleViewModel.moleHillClicked(12);
            }
        });
    }

    /**
     * Hide all moles.
     * @param moleButtons an array containing all of the moles (ImageViews) to be hidden.
     */
    public void HideMoles(ImageView[] moleButtons) {
        for (ImageView moleButton : moleButtons) {
            moleButton.setAlpha(0f);

        }
    }
}