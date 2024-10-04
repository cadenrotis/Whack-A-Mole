package com.example.project1;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.SystemClock;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Objects;
import java.util.Random;

/**
 * A java class that contains the ViewModel to handle the logic for the Whack-a-mole game app
 */
public class WhackAMoleViewModel extends AndroidViewModel {
    /**
     * Strings used to capture the high score number in a file for shared preferences
     */
    private static final String PREFS_NAME = "whack_a_mole_prefs";
    private static final String HIGH_SCORE_KEY = "high_score";

    /**
     * SharedPreferences instance used to persistently save the high score
     */
    private SharedPreferences sharedPreferences;

    /**
     * LiveData containers to update scores, live count, game over text, and the id of the visible mole
     */
    private final MutableLiveData<Integer> currentScore = new MutableLiveData<>();
    private final MutableLiveData<Integer> highScore = new MutableLiveData<>();
    private final MutableLiveData<Integer> amountLives = new MutableLiveData<>();
    private final MutableLiveData<Boolean> gameOver = new MutableLiveData<>(false);

    /**
     * Array of MutableLiveData<MoleHill> objects to track where moles are located
     */
    private final MutableLiveData<Integer> currentMoleLocation = new MutableLiveData<>(-1);

    /**
     * Runnable used to schedule the hiding of the mole either after time runs out or it is clicked.
     */
    private Runnable hideMoleRunnable;

    /**
     * Integer variables used for keeping track of scores, lives, spawn time, and visible time of moles
     */
    private int currentScoreNum = 0;
    private int numLives = 3;
    private int spawnTimeInMs = 4000; // starting spawn time for moles is 4 seconds
    private int moleVisibleTimeInMs = 2000; // mole is visible is 2 seconds

    /**
     * Handler to post the Runnables that show and hide the moles
     */
    private Handler handler = new Handler();

    /**
     * Boolean variable to keep track of when game is running
     */
    private boolean gameRunning = false;

    /**
     * Constructor for the ViewModel.
     * @param application the application object that has the high score.
     */
    public WhackAMoleViewModel(Application application) {
        super(application);
        sharedPreferences = application.getSharedPreferences(PREFS_NAME, 0);
        highScore.setValue(sharedPreferences.getInt(HIGH_SCORE_KEY, 0)); // get the value associated with the high score key
        currentScore.setValue(currentScoreNum);
        amountLives.setValue(numLives);
    }

    /**
     * Starts the game by allowing mole spawning by default and starts the posting of the Runnable.
     */
    public void startGame() {
        System.out.println("In startGame()");
        gameRunning = true;
        handler.post(updateMoleSpawnTime);

        // reset user's current score to 0
        currentScoreNum = 0;
        currentScore.setValue(currentScoreNum);

        // reset number of lives to 3
        numLives = 3;
        amountLives.setValue(numLives);

        // reset spawn time and visible time for the moles
        spawnTimeInMs = 4000;
        moleVisibleTimeInMs = 2000;
    }

    /**
     * Runnable to handle mole spawning at various rates
     */
    private Runnable updateMoleSpawnTime = new Runnable() {
        @Override
        public void run() {
            System.out.println("In updateMoleSpawnTime run()");
            if (gameRunning) {
                // Trigger mole appearance
                showMole();

                // Schedule next spawn
                handler.postAtTime(this, SystemClock.uptimeMillis() + spawnTimeInMs);
            }
        }
    };

    /**
     * Determines which mole in MainActivity to show/make visible.
     */
    private void showMole() {
        System.out.println("In showMole()");

        // If a mole is already showing, hide it.
        if (currentMoleLocation.getValue() >= 0) {
            System.out.println("currentMoleLocation not set");
            hideMole();
            loseALife();
        }

        // Pick a random number between 1 and 12 for the 12 possible MoleHills
        currentMoleLocation.setValue(new Random().nextInt(12) + 1);

        // Schedule the mole to disappear after a set time (moleVisibleTimeInMs)
        hideMoleRunnable = new Runnable() {
            @Override
            public void run() {
                hideMole();
                loseALife();
            }
        };
        handler.postDelayed(hideMoleRunnable, moleVisibleTimeInMs);
    }

    /**
     * Hides the currently visible mole.
     */
    private void hideMole() {
        System.out.println("In hideMole()");

        // Cancel any pending hideMole runnables if the mole is clicked or hidden early
        if (hideMoleRunnable != null) {
            handler.removeCallbacks(hideMoleRunnable);
        }

        if (currentMoleLocation.getValue() >= 0) {
            System.out.println("hid mole at moleHills[" + currentMoleLocation.getValue() + "]");
            currentMoleLocation.setValue(-1);
        } else {
            System.out.println("No moles to hide.");
        }
    }

    /**
     * Called when the mole is clicked. Increases the score and updates the LiveData.
     */
    public void moleHillClicked(int moleHill) {
        System.out.println("In moleHillClicked()");
        if (moleHill == currentMoleLocation.getValue()) {
            currentScoreNum += 10;
            currentScore.setValue(currentScoreNum);

            // Hide the mole and cancel pending hideMole runnables
            hideMole();

            // Adjust spawn time for increased difficulty
            increaseDifficulty();
        }
    }

    /**
     * Increases difficulty by reducing spawn time and mole visible time.
     */
    private void increaseDifficulty() {
        System.out.println("In increaseDifficulty()");
        if (spawnTimeInMs > 600) {
            spawnTimeInMs *= 0.9; // Decrease spawn time by 10%
        }
        if (moleVisibleTimeInMs > 500) {
            moleVisibleTimeInMs *= 0.95; // Decrease mole visible time by 10%
        }
    }

    /**
     * Handles losing a life. If no lives are left, the game ends.
     */
    public void loseALife() {
        System.out.println("In loseALife()");
        // no lives are left, so stop the game
        if (numLives > 0) {
            numLives--;
            amountLives.setValue(numLives);
        }
        if(numLives == 0) {
            stopGame();
            gameOver.setValue(true);
            updateHighScore();
        }
    }

    /**
     * Stops the game and removes any scheduled moles from appearing.
     */
    public void stopGame() {
        System.out.println("In stopGame()");
        gameRunning = false;
        handler.removeCallbacks(updateMoleSpawnTime);
    }

    /**
     * Updates the high score (the variable and in shared preferences) if the
     * current score exceeds the stored high score.
     */
    private void updateHighScore() {
        System.out.println("In updateHighScore()");
        int storedHighScore = sharedPreferences.getInt(HIGH_SCORE_KEY, 0);
        if (currentScoreNum > storedHighScore) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(HIGH_SCORE_KEY, currentScoreNum); // edit the value associated with the high score key
            editor.apply(); // apply the change to the key-value pair
            highScore.setValue(currentScoreNum);
        }
    }

    /**
     * A getter method to know if the game is running.
     * @return a boolean variable that says whether a mole can be spawned or not.
     */
    public boolean isGameRunning() {
        return gameRunning;
    }

    /**
     * Will return a LiveData object to update the current score text in the UI in MainActivity.java
     * @return currentScore
     */
    public LiveData<Integer> getCurrentScore() {
        return currentScore;
    }

    /**
     * Will return a LiveData object to update the high score text in the UI in MainActivity.java
     * @return highScore
     */
    public LiveData<Integer> getHighScore() {
        return highScore;
    }

    /**
     * Will return a LiveData object to update number of lives text in the UI in MainActivity.java
     * @return amountLives
     */
    public LiveData<Integer> getNumLives() {
        return amountLives;
    }

    /**
     * Will return a LiveData object to show game over text in the UI in MainActivity.java
     * @return gameOver
     */
    public LiveData<Boolean> isGameOver() {
        return gameOver;
    }

    /**
     * Will return a LiveData object to show which mole should be visible in the UI in MainActivity.java
     * @return visibleMole
     */
    public LiveData<Integer> getMoleLocation() {
        return currentMoleLocation;
    }
}
