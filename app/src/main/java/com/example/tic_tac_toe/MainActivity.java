package com.example.tic_tac_toe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];

    private boolean bool = true; //As X starts First

    private int count;

    private int points_X;
    private int points_O;

    private TextView textViewX;
    private TextView textViewY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewX = findViewById(R.id.player_x);
        textViewY = findViewById(R.id.player_o);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "b_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(v -> resetGame());

    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (bool) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }

        count++;

        if (checkForWin()) {
            if (bool) {  //checkForWin has returned true and X has won
                player1Wins();
            } else {    //checkForWin has returned true and O has won
                player2Wins();
            }
        } else if (count == 9) {  //checkForWin has returned False, therefore check if all spaces are filled
            draw();
        } else {
            bool = !bool;   //If draw also hasn't occurred, then switch the turn
        }

    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();  //Get the fields
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;   //If a Column is filled by X or O
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;   //If a Row is filled by X or O
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;   //First Diagonal is filled by X or O
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;   //Second Diagonal is filled by X or O
        }

        return false;
    }

    private void player1Wins() {
        points_X++;
        Toast.makeText(this, "Player X has Won!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void player2Wins() {
        points_O++;
        Toast.makeText(this, "Player O has Won!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointsText() {
        textViewX.setText("Player-X \n" + points_X);
        textViewY.setText("Player-O \n" + points_O);
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");  //Reset All the Spaces
            }
        }
        count = 0;  //Counter set to Zero
        bool = true;  //X will start the game as convention
    }

    private void resetGame() {
        points_X = 0;
        points_O = 0;
        updatePointsText();
        resetBoard();
    }
}