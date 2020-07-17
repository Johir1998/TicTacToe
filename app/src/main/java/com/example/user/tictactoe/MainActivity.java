package com.example.user.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[50][50];

    private boolean player1_Turn = true;

    private int roundCount = 0;

    private int player1_Points = 0;
    private int player2_Points = 0;

    private TextView textView_Player1;
    private TextView textView_Player2;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView_Player1 = findViewById(R.id.TextView_Player1_Id);

        textView_Player2 = findViewById(R.id.TextView_Player2_Id);

        for (int i = 1; i <= 3; i++) {

            for (int j = 1; j <= 3; j++) {

                String buttonID = "Button_" + i + j + "_Id";

                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());

                buttons[i][j] = findViewById(resID);

                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.resetButton_Id);

        buttonReset.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                resetGame();
            }
        });
    }

    @Override

    public void onClick(View v) {

        if (!((Button) v).getText().toString().equals("")) {

            return;
        }

        if (player1_Turn) {

            ((Button) v).setText("X");
        }

        else {

            ((Button) v).setText("O");
        }

        roundCount++;

        if (checkForWin()==true) {

            if (player1_Turn) {

                player1Wins();
            }

            else {

                player2Wins();
            }
        }

        else if (roundCount == 9) {

            draw();
        }

        else {

            player1_Turn = !player1_Turn;
        }
    }

    private boolean checkForWin() {

        String[][] field = new String[50][50];

        for (int i = 1; i <= 3; i++) {

            for (int j = 1; j <= 3; j++) {

                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 1; i <= 3; i++) {

            if (field[i][1].equals(field[i][2]) && field[i][1].equals(field[i][3]) && !field[i][1].equals("")) {

                return true;
            }
        }

        for (int i = 1; i <= 3; i++) {

            if (field[1][i].equals(field[2][i]) && field[1][i].equals(field[3][i]) && !field[1][i].equals("")) {

                return true;
            }
        }

        if (field[1][1].equals(field[2][2]) && field[1][1].equals(field[3][3]) && !field[1][1].equals("")) {

            return true;
        }

        if (field[1][3].equals(field[2][2]) && field[1][3].equals(field[3][1]) && !field[1][3].equals("")) {

            return true;
        }

        return false;
    }

    private void player1Wins() {

        player1_Points++;

        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();

        updatePointsText();

        resetBoard();
    }

    private void player2Wins() {

        player2_Points++;

        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();

        updatePointsText();

        resetBoard();
    }

    private void draw() {

        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();

        resetBoard();
    }

    private void updatePointsText() {

        textView_Player1.setText("Player 1 : " + player1_Points);

        textView_Player2.setText("Player 2 : " + player2_Points);
    }

    private void resetBoard() {

        for (int i = 1; i <= 3; i++) {

            for (int j = 1; j <= 3; j++) {

                buttons[i][j].setText("");
            }
        }

        roundCount = 0;

        player1_Turn = true;
    }

    private void resetGame() {

        player1_Points = 0;

        player2_Points = 0;

        updatePointsText();

        resetBoard();
    }

    @Override

    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);

        outState.putInt("player1Points", player1_Points);

        outState.putInt("player2Points", player2_Points);

        outState.putBoolean("player1Turn", player1_Turn);
    }

    @Override

    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");

        player1_Points = savedInstanceState.getInt("player1Points");

        player2_Points = savedInstanceState.getInt("player2Points");

        player1_Turn = savedInstanceState.getBoolean("player1Turn");
    }
}
