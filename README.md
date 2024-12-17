Project 1
==================
Repository for Iowa State University's CprE 3880 project 1, in collaboration with Lex Somers and Noah Thompson.


# Project Overview:
Whack-A-Mole is an Android application designed for a mobile version of the traditional Whack-A-Mole arcade game. Players will try to quickly tap on moles that appear on the screen to earn points and beat their highest score. If a player doesn't tap on a mole quick enough, then they will lose a life. Players only get three lives at the start of each game.

# My Role:
I focused on the backend integration, where I:
 * Created the functionality for moles to appear next to holes put into the main layout view
 * Created the score earning, high score capture, and lives lost functionality within the app
 * Created the spawn rate of moles, where the spawn rate increases as the game progresses

# Learnings:
During this project, I learned how to use Runnable and Handler objects to trigger moles spawning into the game, and also increase the spawn rate of moles. I also learned how to use MutableLiveData objects to update the different score (high score and current score) and amount of lives text while the game is being played.

# Resources Used: 
 - Java
 - Android Studio & API
 - Git
 - XML

 # Directory Navigation: 
 To view the Java classes, navigate to `app/src/main/java/com/example/project1` (https://github.com/cadenrotis/Whack-A-Mole/tree/master/app/src/main/java/com/example/project1).

 To view the xml files and other files used for the views, navigate to `app/src/main/res/layout` (https://github.com/cadenrotis/Whack-A-Mole/tree/master/app/src/main/res/layout).
