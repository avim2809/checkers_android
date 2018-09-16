package com.example.san4o.checkers;

import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.san4o.checkers.enums.Color;

import java.util.ArrayList;

public abstract class Player implements View.OnClickListener{
    protected int score=0;
    protected ArrayList<Stone> activeStones = null;
    protected ArrayList<Stone> lostStones = null;
    protected Color playerColor ;
    protected GameBoard gameBoard;
    protected Stone[][] updatedBoardStones;

    public abstract void makeMove(Stone stoneToMove);
    //_____________________________________________
    public void setPlayerColor(Color playerColor) {
        this.playerColor = playerColor;
    }
    //_____________________________________________
    public Color getColor(){
        return  this.playerColor;
    }
    //_____________________________________________
    public int score(){
        return this.score;
    }
    //_____________________________________________
    public void setScore(int newScore){
        this.score = newScore;
    }
    //_____________________________________________
    public void addStone(Stone stoneToAdd){
        this.activeStones.add(stoneToAdd);
    }
    //_____________________________________________
    public Stone findClickedStone(ImageView image){
        Stone stoneToRet=null;
        for(int i=0;i<activeStones.size();i++){
            ImageView currImageView = activeStones.get(i).getImageView();

                if(currImageView.getTag().toString().equals(image.getTag().toString())){
                    stoneToRet = activeStones.get(i);
                    break;
                }

        }
        return stoneToRet;
    }
    //_____________________________________________
    public void updateLostStones(Stone lostStone){
        lostStones.remove(lostStone);
    }
    //_____________________________________________
}
