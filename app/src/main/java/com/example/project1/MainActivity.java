package com.example.project1;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {

    /**
     * Instance of the viewmodel
     */
    private WhackAMoleViewModel whackAMoleViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        /**
         * Get references to UI elements
         */
        ImageView mole1 = findViewById(R.id.mole1);
        ImageView mole2 = findViewById(R.id.mole2);
        ImageView mole3 = findViewById(R.id.mole3);
        ImageView mole4 = findViewById(R.id.mole4);
        ImageView mole5 = findViewById(R.id.mole5);
        ImageView mole6 = findViewById(R.id.mole6);
        ImageView mole7 = findViewById(R.id.mole7);
        ImageView mole8 = findViewById(R.id.mole8);
        ImageView mole9 = findViewById(R.id.mole9);
        ImageView mole10 = findViewById(R.id.mole10);
        ImageView mole11 = findViewById(R.id.mole11);
        ImageView mole12 = findViewById(R.id.mole12);

        // Create an array of all the mole ImageViews for easy access
        ImageView[] moleViews = {mole1, mole2, mole3, mole4, mole5, mole6, mole7, mole8, mole9, mole10, mole11, mole12};

        /**
         * At the beginning of the game, show no moles on the screen.
         */
        InitializeMoles(moleViews);

        /**
         * Initialize the WhatAMoleViewModel instance.
         */
        whackAMoleViewModel = new ViewModelProvider(this).get(WhackAMoleViewModel.class);

        /**
         * Update the text in the UI that displays the user's current score.
         */
        whackAMoleViewModel.getCurrentScore().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer score) {
                // update textview that contains the current score
            }
        });

        /**
         * Update the text in the UI that displays the user's highest score.
         */
        whackAMoleViewModel.getHighScore().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer highScore) {
                // update the textview that contains the high score
            }
        });

        /**
         * Update the text in the UI that displays the user's number of lives.
         */
        whackAMoleViewModel.getNumLives().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer lives) {
                // update the textview containing the amount of lives the user has
            }
        });

        /**
         * Update the text in the UI to show game over text.
         */
        whackAMoleViewModel.isGameOver().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isGameOver) {
                if (isGameOver) {
                    // show some sort of game over screen
                }
            }
        });

        /**
         * Update the mole picked to be visible in the UI
         */
        whackAMoleViewModel.getVisibleMole().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer visibleMoleId) {
                // Hide all moles
                for (ImageView moleView : moleViews) {
                    moleView.setVisibility(ImageView.INVISIBLE);
                }

                // Show the mole corresponding to the visibleMoleId
                if (visibleMoleId >= 1) {
                    moleViews[visibleMoleId - 1].setVisibility(ImageView.VISIBLE);
                }
            }
        });
    }

    /**
     * Hide all moles.
     * @param moleViews an array containing all of the moles (ImageViews) to be hidden.
     */
    public void InitializeMoles(ImageView[] moleViews) {
        for (ImageView moleView : moleViews) {
            moleView.setVisibility(ImageView.INVISIBLE);
        }
    }
}