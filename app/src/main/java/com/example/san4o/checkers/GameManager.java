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

    public GameManager(){
        gameBoard = new GameBoard();
        //gameActivity = activity;
    }
    //___________________________________________-
    public void initGame(){
        playerUser = new PlayerUser(gameBoard);
        playerComputer = new PlayerComputer(gameBoard);
        generateColor();
        gameBoard.initComputerStones(playerComputer.getColor(),playerComputer);
        gameBoard.initUserStones(playerUser.getColor(),playerUser);
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
    //__________________________________________
}
