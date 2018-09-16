package com.example.san4o.checkers;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.provider.Settings;
import android.support.annotation.DrawableRes;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.san4o.checkers.activity.GameActivity;
import com.example.san4o.checkers.enums.Color;

import java.util.ArrayList;

public class GameBoard{
    public static int BOARD_SIZE=8;

    private Stone[][] stones;
    private ArrayList<Stone> activeStones=null;
    private GridLayout gameBoardGrid;
    private int whiteDrawableCell = R.drawable.white_cell_small;
    private int blackDrawableCell = R.drawable.black_cell_small;
    private int whiteCellGlow = R.drawable.white_cell_glow;
    private int blackCellGlow = R.drawable.black_cell_glow;
    private GameActivity context;

    public GameBoard(){
        gameBoardGrid = Globals.gameBoardGrid;
        context = Globals.gameActivity;
        activeStones = new ArrayList<>();
        initBoard();
    }
    //______________________________________
    public void initComputerStones(Color color,Player listener){
        int playerStoneDrawable = getDrawableByColor(color);
        Stone currStoneToAdd;

        for(int i=0;i<3;i++){
            for(int j=0;j<BOARD_SIZE;j++){
                if((i+j)%2==1){
                    int currCellIndex = (gameBoardGrid.getColumnCount() * i) + j;
                    FrameLayout canvas = (FrameLayout) gameBoardGrid.getChildAt(currCellIndex);

                    ImageView stoneImage =  new ImageView(context);
                    stoneImage.setTag("row"+String.valueOf(i)+"col"+String.valueOf(j));
                    stoneImage.setBackgroundResource(playerStoneDrawable);
                    //stoneImage.setOnClickListener(listener);
                    stoneImage.setScaleType(ImageView.ScaleType.CENTER);

                    currStoneToAdd = new Stone(Color.BLACK,stoneImage,currCellIndex);
                    currStoneToAdd.setRow(i);
                    currStoneToAdd.setCol(j);

                    addStone(currStoneToAdd);
                    listener.addStone(currStoneToAdd);
                    canvas.addView(stoneImage);
                }
            }
        }
    }
    //______________________________________
    public void initUserStones(Color color,Player listener){
        int playerStoneDrawable = getDrawableByColor(color);
        Stone currStoneToAdd;

        for(int i=BOARD_SIZE-1;i>BOARD_SIZE-4;i--){
            for(int j=BOARD_SIZE-1;j>=0;j--){
                if((i+j)%2==1){
                    int currCellIndex = (gameBoardGrid.getColumnCount() * i) + j;
                    FrameLayout canvas = (FrameLayout) gameBoardGrid.getChildAt(currCellIndex);

                    ImageView stoneImage =  new ImageView(context);
                    stoneImage.setTag("row"+String.valueOf(i)+"col"+String.valueOf(j));
                    stoneImage.setBackgroundResource(playerStoneDrawable);
                    stoneImage.setOnClickListener(listener);
                    stoneImage.setScaleType(ImageView.ScaleType.CENTER);

                    currStoneToAdd = new Stone(Color.WHITE,stoneImage,currCellIndex);
                    currStoneToAdd.setRow(i);
                    currStoneToAdd.setCol(j);
                    addStone(currStoneToAdd);
                    listener.addStone(currStoneToAdd);

                    canvas.addView(stoneImage);
                }
            }
        }
    }
    //______________________________________
    private int getDrawableByColor(Color color){
        int drawableToRet = 0;
        if(color == Color.BLACK){
            drawableToRet = R.drawable.stone_black_small;
        }else if (color == Color.WHITE){
            drawableToRet = R.drawable.stone_white_small;
        }

        return drawableToRet;
    }
    //______________________________________
    private void initBoard(){
        stones = new Stone[BOARD_SIZE][BOARD_SIZE];
        int currColor;
        int currGlow;
        for(int i=0;i<BOARD_SIZE;i++){
            if(i%2 ==0) {
                currColor = whiteDrawableCell;
                currGlow = whiteCellGlow;
            }else {
                currColor = blackDrawableCell;
                currGlow = blackCellGlow;
            }
            for(int j=0; j<BOARD_SIZE; j++){
                int cellIndex = (gameBoardGrid.getColumnCount() * i) + j;
                FrameLayout canvas =  new FrameLayout(context);
                ImageView imageGlow = new ImageView(context);
                imageGlow.setImageResource(currGlow);
                imageGlow.setTag("image_glow");


                ImageView imageBG = new ImageView(context);
                imageBG.setImageResource(currColor);
                imageBG.setTag("image_bg");
                //canvas.setBackgroundResource(currColor);
                canvas.addView(imageGlow);
                canvas.addView(imageBG);
                if(currColor == whiteDrawableCell){
                    currColor = blackDrawableCell;
                    currGlow = blackCellGlow;
                }else{
                    currColor = whiteDrawableCell;
                    currGlow = whiteCellGlow;
                }
                gameBoardGrid.addView(canvas,cellIndex);
            }
        }
    }
    //______________________________________
    private GridLayout getGrid(){
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
    private void changeStoneLocation(int row,int col){
        int cellIndex = (gameBoardGrid.getColumnCount() * row) + col;
        ImageView currCellImage = (ImageView) gameBoardGrid.getChildAt(cellIndex);
        currCellImage.setBackgroundResource(0);
    }
    //______________________________________
    public Stone[][] getBoard(){
        return stones;
    }
    //______________________________________
}
