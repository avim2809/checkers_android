package com.example.san4o.checkers;

import android.widget.GridLayout;
import android.widget.ImageView;

import com.example.san4o.checkers.activity.GameActivity;
import com.example.san4o.checkers.enums.Color;

import java.util.ArrayList;

public class GameBoard {
    public static int BOARD_SIZE=8;

    private Stone[][] stones;
    private ArrayList<Stone> activeStones=null;
    private GridLayout gameBoardGrid;
    private GameActivity context;

    public GameBoard(GridLayout grid,GameActivity activity){
        gameBoardGrid = grid;
        context = activity;
        activeStones = new ArrayList<>();
        stones= new Stone[BOARD_SIZE][BOARD_SIZE];
    }
    //______________________________________
    public void initStones(){
        Stone currStone;
        for(int i=0;i<BOARD_SIZE;i++){
            for(int j=0;j<BOARD_SIZE;j++){
                int cellIndex = (gameBoardGrid.getColumnCount() * i) + j;
                ImageView image =  (ImageView) gameBoardGrid.getChildAt(cellIndex);
                Color color = getColor(image);
                if(color != null){
                    currStone = new Stone(color,image);
                    currStone.setRow(i);
                    currStone.setCol(j);
                    addStone(currStone);
                }
            }
        }
    }
    //______________________________________
    private void initBoard(){
        stones = new Stone[BOARD_SIZE][BOARD_SIZE];
    }
    //______________________________________
    public GridLayout getGrid(){
        return this.gameBoardGrid;
    }
    //______________________________________
    private void addStone(Stone stoneToAdd){
        activeStones.add(stoneToAdd);
        int row = stoneToAdd.getRow();
        int col = stoneToAdd.getCol();
        stones[row][col] = stoneToAdd;
    }
    //______________________________________
    private Color getColor(ImageView image){
        String imageTag = image.getTag().toString();
        switch(imageTag)
        {
            case "empty":
                return null;
            case "stone_black":
                return Color.BLACK;
            case "stone_white":
                return Color.WHITE;
        }
        return null;
    }
    //______________________________________
    private void changeStoneLocation(int row,int col){
        int cellIndex = (gameBoardGrid.getColumnCount() * row) + col;
        ImageView currCellImage = (ImageView) gameBoardGrid.getChildAt(cellIndex);
        currCellImage.setBackgroundResource(0);
    }
    //______________________________________
}
