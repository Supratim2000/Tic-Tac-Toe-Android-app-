package com.example.tictactoe;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    //This variable keeps track if the game is active.
    boolean gameActive = true;

    /* nowPlayer keeps track of if we use O or X in each tap.
     *  nowPlayer -> manages player state.
     *  (nowPlayer == 0) -> X
     *  (nowPlayer == 1) -> O
     * */
    private int nowPlaying = 0;

    /*
     *  gameState array manages the state of each grid tagged 0 - 8.
     *  (element in gameState == 0) -> X
     *  (element in gameState == 1) -> O
     *  (element in gameState == 2) -> NULL
     * */
    private int[] gridState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    //These are all of the winning positions of our game.
    private int[][] winPos = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar;
        actionBar = getSupportActionBar();

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#000000"));

        assert actionBar != null;
        actionBar.setBackgroundDrawable(colorDrawable);
    }

    //This function runs if any of the grid is pressed.
    public void gridTap(View view) {

        //Checks if the game is active.
        if (!gameActive) {
            //If game is not active then gameReset() function resets the state of the game.
            gameReset();
        } else {
            TextView status = (TextView) findViewById(R.id.playerStatus);
            ImageView img = (ImageView) view;

            //Gets the tag in which grid clicked and converts the tag into integer.
            int tappedImageTag = Integer.parseInt(img.getTag().toString());

            //Checks if the grid is blank.
            //Cause when a grid is filled with X or O then we can't change the grid.
            if (gridState[tappedImageTag] == 2) {
                gridState[tappedImageTag] = nowPlaying;
                img.setTranslationY(-500f);

                //Checks which player's turn it is.
                if (nowPlaying == 0) {
                    img.setImageResource(R.drawable.x);
                    status.setText("Player two turn - Tap grid to play.");
                } else {
                    img.setImageResource(R.drawable.o);
                    status.setText("Player one turn - Tap grid to play.");
                }

                //Changes the player turn.
                nowPlaying = (nowPlaying + 1) % 2;

                img.animate().translationYBy(500f).setDuration(500);
            }

            //Check if anyone of the players won.
            for (int[] winState : winPos) {

                //Condition if someone has won.
                if ((gridState[winState[0]] == gridState[winState[1]]) && (gridState[winState[1]] == gridState[winState[2]]) && gridState[winState[0]] != 2) {
                    gameActive = false;

                    //Check who won.
                    String winStatus;
                    if (gridState[winState[0]] == 0) {
                        winStatus = "Player One won the game!";
                    } else {
                        winStatus = "Player Two won the game!";
                    }

                    status.setText(winStatus);
                }
            }

            //Shows on UI if it is a draw.
            if (isDraw() && gameActive) {
                status.setText("Draw! Press reset");
            }
        }
    }

    //Resets the game after winning of any player.
    private void gameReset() {
        gameActive = true;
        nowPlaying = 0;
        Arrays.fill(gridState, 2);

        ((ImageView) findViewById(R.id.image0)).setImageResource(0);
        ((ImageView) findViewById(R.id.image1)).setImageResource(0);
        ((ImageView) findViewById(R.id.image2)).setImageResource(0);
        ((ImageView) findViewById(R.id.image3)).setImageResource(0);
        ((ImageView) findViewById(R.id.image4)).setImageResource(0);
        ((ImageView) findViewById(R.id.image5)).setImageResource(0);
        ((ImageView) findViewById(R.id.image6)).setImageResource(0);
        ((ImageView) findViewById(R.id.image7)).setImageResource(0);
        ((ImageView) findViewById(R.id.image8)).setImageResource(0);

        TextView status = (TextView) findViewById(R.id.playerStatus);
        status.setText("Player one turn - Tap grid to play.");
    }

    //Resets the game manually using RESET button.
    public void reset(View view) {
        gameActive = true;
        nowPlaying = 0;
        Arrays.fill(gridState, 2);

        ((ImageView) findViewById(R.id.image0)).setImageResource(0);
        ((ImageView) findViewById(R.id.image1)).setImageResource(0);
        ((ImageView) findViewById(R.id.image2)).setImageResource(0);
        ((ImageView) findViewById(R.id.image3)).setImageResource(0);
        ((ImageView) findViewById(R.id.image4)).setImageResource(0);
        ((ImageView) findViewById(R.id.image5)).setImageResource(0);
        ((ImageView) findViewById(R.id.image6)).setImageResource(0);
        ((ImageView) findViewById(R.id.image7)).setImageResource(0);
        ((ImageView) findViewById(R.id.image8)).setImageResource(0);
        TextView status = (TextView) findViewById(R.id.playerStatus);

        status.setText("Player one turn - Tap grid to play.");

        Toast.makeText(this, "resetting...", Toast.LENGTH_SHORT).show();
    }

    //Checks if the game is draw.
    private boolean isDraw() {
        int count = 0;
        for (int i = 0; i < gridState.length; i++)
            if (gridState[i] != 2)
                count++;
        return count == 9 ? true : false;
    }
}