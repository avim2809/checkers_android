package com.example.san4o.checkers;

import com.example.san4o.checkers.enums.Color;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class GameManager {
    private ArrayList highScoreArray;
    private GameBoard gameBoard;
    private PlayerUser playerUser;
    private PlayerComputer playerComputer;

    public GameManager(){
        gameBoard = new GameBoard();
        highScoreArray = new ArrayList<HighScore>();
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
    public void saveData(){
        DataManager.getInstance().saveData(playerUser.getName(),"player_name");
        DataManager.getInstance().saveData(gameBoard.getBoard(),"game_board_data");
        DataManager.getInstance().saveData(GameBoard.BOARD_SIZE,"game_board_size");
    }
    //__________________________________________
    public void addHighScore(String name,int score){
        if(highScoreArray.size()>=10){
            Collections.sort(highScoreArray,Collections.reverseOrder());
            highScoreArray.remove(highScoreArray.size()-1);
        }
        highScoreArray.add(new HighScore(name,score));
        DataManager.getInstance().saveData(highScoreArray,"high_scores");
    }
    //__________________________________________
}
