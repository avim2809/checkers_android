package com.example.san4o.checkers;


import android.widget.ImageView;

import com.example.san4o.checkers.enums.Color;

public class Stone {

    private int row;
    private int col;
    private Color color;
    private int stone_res;
    private ImageView image;
    private int id;

    public Stone(Color newColor,ImageView imageRes){
        this.color = newColor;
        if(this.color == Color.WHITE){
            stone_res = R.drawable.stone_white;
        }else if(this.color == Color.BLACK){
            stone_res = R.drawable.stone_black;
        }
        //image.setBackgroundResource(stone_res);
        //id = imageRes.getId();
    }
    //_____________________________________________
    public void setRow(int newRow){
        this.row=newRow;
    }
    //_____________________________________________
    public void setCol(int newCol){
        this.col = newCol;
    }
    //_____________________________________________
    public int getRow(){
        return row;
    }
    //_____________________________________________
    public int getCol(){
        return col;
    }
    //_____________________________________________
}
