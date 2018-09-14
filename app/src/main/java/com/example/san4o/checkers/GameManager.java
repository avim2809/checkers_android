package com.example.san4o.checkers;

import android.widget.GridLayout;
import android.widget.Toast;
import android.view.View;

import com.example.san4o.checkers.activity.GameActivity;
import com.example.san4o.checkers.enums.Color;

import java.util.Random;

public class GameManager {

    private GameBoard gameBoard;
    private Player playerUser;
    private Player playerComputer;
    private GameActivity gameActivity;

    public GameManager(GridLayout grid,GameActivity activity){
        gameBoard = new GameBoard(grid,activity);
        gameActivity = activity;
    }
    //___________________________________________-
    public void initGame(){
        playerUser = new PlayerUser();
        playerComputer = new PlayerComputer();
        generateColor();
        gameBoard.initStones();
    }
    //___________________________________________-
    private void generateColor(){
        Random rand = new Random();
        int colorNum = rand.nextInt(2)+1;

        if(colorNum == 1){
            playerUser.setPlayerColor(Color.WHITE);
            playerComputer.setPlayerColor(Color.BLACK);
        } else if (colorNum == 2){
            playerUser.setPlayerColor(Color.BLACK);
            playerComputer.setPlayerColor(Color.WHITE);
        }
    }
    //___________________________________________-

    public void clickOnStone(View clickedStone){
        String id = String.valueOf(clickedStone.getId());
        Toast.makeText(gameActivity, id, Toast.LENGTH_SHORT).show();
    }
    //___________________________________________-
}
