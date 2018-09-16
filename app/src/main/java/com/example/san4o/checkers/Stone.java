package com.example.san4o.checkers;


import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.san4o.checkers.activity.GameActivity;
import com.example.san4o.checkers.enums.Color;

public class Stone{

    private int row;
    private int col;
    private Color color;
    private int stone_res;
    private int gridIndex;
    private ImageView image;
    private int id;

    public Stone(Color newColor, ImageView imageRes,int gridIndex){
        this.color = newColor;
        this.gridIndex = gridIndex;

        if(this.color == Color.WHITE){
            stone_res = R.drawable.stone_white;
        }else if(this.color == Color.BLACK){
            stone_res = R.drawable.stone_black;
        }
        image = imageRes;
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
    public ImageView getImageView(){
        return image;
    }
    //_____________________________________________
    public Color getColor(){
        return this.color;
    }
    //_____________________________________________
}
