package com.example.san4o.checkers;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.san4o.checkers.activity.CheckersActivity;
import com.example.san4o.checkers.activity.HighScoreActivity;
import com.example.san4o.checkers.enums.Color;

import java.lang.reflect.Array;
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
    private CheckersActivity context;
    private ArrayList<ImageView> glowingCells;
    private Animation glowAnimation;

    public GameBoard(){
        gameBoardGrid = Globals.gameBoardGrid;
        context = Globals.checkersActivity;
        activeStones = new ArrayList<>();
        initGameProps();
        initBoard();
    }
    //______________________________________
    private void initGameProps(){
        glowingCells = new ArrayList<>();
        glowAnimation = AnimationUtils.loadAnimation(Globals.checkersActivity,R.anim.glow_anim);
    }
    //______________________________________
    private void initWhiteCells(){
        for(int i=0;i<BOARD_SIZE;i++){
            for(int j=0;j<BOARD_SIZE;j++){
                if((i+j)%2==0){

                }
            }
        }
    }
    //______________________________________
    public void initComputerStones(Color color,Player listener){
        int playerStoneDrawable = getDrawableByColor(color);
        Stone currStoneToAdd;

        for(int i=0;i<3;i++){
            for(int j=0;j<BOARD_SIZE;j++){
                if((i+j)%2==1){
                    int currCellIndex = (gameBoardGrid.getColumnCount() * i) + j;
                    RelativeLayout frame = (RelativeLayout) gameBoardGrid.getChildAt(currCellIndex);

                    ImageView stoneImage =  new ImageView(context);
                    stoneImage.setTag("row"+String.valueOf(i)+"col"+String.valueOf(j));
                    stoneImage.setBackgroundResource(playerStoneDrawable);

                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(2, 2, 2, 2);
                    params.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE);
                    stoneImage.setLayoutParams(params);

                    currStoneToAdd = new Stone(Color.BLACK,stoneImage,currCellIndex);
                    currStoneToAdd.setRow(i);
                    currStoneToAdd.setCol(j);
                    currStoneToAdd.setRelativeLayout(frame);
                    addStone(currStoneToAdd);
                    listener.addStone(currStoneToAdd);
                    frame.addView(stoneImage);
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
                    RelativeLayout frame = (RelativeLayout) gameBoardGrid.getChildAt(currCellIndex);

                    ImageView stoneImage =  new ImageView(context);
                    stoneImage.setTag("row"+String.valueOf(i)+"col"+String.valueOf(j));
                    stoneImage.setBackgroundResource(playerStoneDrawable);
                    stoneImage.setOnClickListener(listener);


                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(2, 2, 2, 2);
                    params.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE);
                    stoneImage.setLayoutParams(params);

                    currStoneToAdd = new Stone(Color.WHITE,stoneImage,currCellIndex);
                    currStoneToAdd.setRow(i);
                    currStoneToAdd.setCol(j);
                    currStoneToAdd.setRelativeLayout(frame);
                    addStone(currStoneToAdd);
                    listener.addStone(currStoneToAdd);

                    frame.addView(stoneImage);
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
                RelativeLayout frame =  new RelativeLayout(context);

                ImageView imageGlow = new ImageView(context);
                imageGlow.setImageResource(currGlow);
                imageGlow.setTag("image_glow");

                ImageView imageBG = new ImageView(context);
                imageBG.setImageResource(currColor);
                imageBG.setTag("image_bg");

                frame.addView(imageGlow);
                frame.addView(imageBG);
                if(currColor == whiteDrawableCell){
                    currColor = blackDrawableCell;
                    currGlow = blackCellGlow;
                }else{
                    currColor = whiteDrawableCell;
                    currGlow = whiteCellGlow;
                }
                gameBoardGrid.addView(frame,cellIndex);
            }
        }
        initWhiteCells();
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
    public void glowCells(ImageView cellToGlow){
        cellToGlow.startAnimation(glowAnimation);
        glowingCells.add(cellToGlow);
    }
    //______________________________________
    public void glowCells(ArrayList<ImageView> cellToGlowArray){
        for(int i=0;i<cellToGlowArray.size();i++){
            cellToGlowArray.get(i).startAnimation(glowAnimation);
            glowingCells.add(cellToGlowArray.get(i));
        }
    }
    //______________________________________
    public void removeGlow(){
        for(int i=0;i<glowingCells.size();i++){
            glowingCells.get(i).clearAnimation();
        }
    }
    //______________________________________
}
