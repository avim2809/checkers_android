package com.example.san4o.checkers;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class PlayerUser extends Player {


    private String name;

    public PlayerUser(GameBoard gameBoard){
        activeStones = new ArrayList<>();
        lostStones = new ArrayList<>();
        this.gameBoard = gameBoard;
        score = 0;
    }
    //_____________________________________________
    @Override
    public void makeMove(Stone stoneToMove) {

    }
    //_____________________________________________
    private boolean checkIfEatRight(int i,int j){
        if(updatedBoardStones[i-1][j+1].getColor() != playerColor){
            return true;
        }
        return false;
    }
    //_____________________________________________
    private boolean checkIfEatLeft(int i,int j){
        if(updatedBoardStones[i-1][j-1].getColor() != playerColor){
            return true;
        }
        return false;
    }
    //____________________________________________
    @Override
    public void onClick(View view) {
        if(view instanceof ImageView){
            Stone clickedStone = findClickedStone((ImageView) view);
            if(clickedStone != null){
                Log.i("  "+String.valueOf(clickedStone.getRow())+"|"+String.valueOf(clickedStone.getCol()), "onClick: PlayerUser");
                moveStoneAction(clickedStone);
            }
        }
    }
    //_____________________________________________
    private void moveStoneAction(Stone stoneToMove){

    }
    //____________________________________________
    public String getName() {
        return name;
    }
    //____________________________________________
    public void setName(String name) {
        this.name = name;
    }
    //____________________________________________
}
