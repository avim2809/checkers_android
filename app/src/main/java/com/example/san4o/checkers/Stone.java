package com.example.san4o.checkers;


import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.example.san4o.checkers.enums.Color;

public class Stone{

    private int row;
    private int col;
    private Color color;
    private int gridIndex;
    private ImageView image;
    private RelativeLayout relativeLayout;

    public Stone(Color newColor, ImageView imageRes,int gridIndex){
        this.color = newColor;
        this.gridIndex = gridIndex;
        image = imageRes;
        isKing = false;
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

    public boolean isKing() {
        return isKing;
    }

    public void setKing(boolean king) {
        isKing = king;
    }

    private boolean isKing;

    public RelativeLayout getRelativeLayout() {
        return relativeLayout;
    }

    public void setRelativeLayout(RelativeLayout relativeLayout) {
        this.relativeLayout = relativeLayout;
    }
}
