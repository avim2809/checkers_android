package com.example.san4o.checkers;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.san4o.checkers.enums.Color;

import java.util.ArrayList;

public class PlayerComputer extends Player implements View.OnClickListener{

    public PlayerComputer(GameBoard gameBoard){
        activeStones = new ArrayList<>();
        lostStones = new ArrayList<>();
        this.gameBoard = gameBoard;
        score=0;
    }
    //_____________________________________________
    @Override
    public void makeMove(Stone stoneToMove) {
        updatedBoardStones = this.gameBoard.getBoard();

        // computer walks down i++ : j++/j--
        for(int i=0;i<GameBoard.BOARD_SIZE;i++){
            for(int j=0;j<gameBoard.BOARD_SIZE;j++){
                if(updatedBoardStones[i][j].equals(stoneToMove)){
                    if(checkIfEatRight(i,j) == true){
                        updatedBoardStones[i+1][j+1]=null;
                        updatedBoardStones[i+2][j+2]=updatedBoardStones[i][j];
                        updatedBoardStones[i][j]=null;
                    }
                }
            }
        }
    }
    //_____________________________________________
    private boolean checkIfEatRight(int i,int j){
        if(updatedBoardStones[i+1][j+1].getColor() != playerColor){
            return true;
        }
        return false;
    }
    //_____________________________________________
    private boolean checkIfEatLeft(int i,int j){
        if(updatedBoardStones[i+1][j-1].getColor() != playerColor){
            return true;
        }
        return false;
    }
    //_____________________________________________
    @Override
    public void onClick(View view) {
        //if(view instanceof ImageView){
           // Stone clickedStone = findClickedStone((ImageView) view);
           // if(clickedStone != null){
             //   Log.i("  "+String.valueOf(clickedStone.getRow())+"|"+String.valueOf(clickedStone.getCol()), "onClick: PlayerComputer");
           // }
       // }
    }
    //_____________________________________________
}
