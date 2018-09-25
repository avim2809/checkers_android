package com.example.san4o.checkers;


import android.widget.RelativeLayout;

import com.example.san4o.checkers.enums.StoneColor;

public class Stone{

    private StoneColor stoneColor;
    private int gridIndex;
    private RelativeLayout relativeLayout;
    private boolean isKing;
    private Location location;

    public Stone(StoneColor stoneColor, final Location location) {
        this.stoneColor = stoneColor;
        this.location = location;
    }

    public void setStoneColor(StoneColor stoneColor) {
        this.stoneColor = stoneColor;
    }

    public int getGridIndex() {
        return gridIndex;
    }

    public void setGridIndex(int gridIndex) {
        this.gridIndex = gridIndex;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Stone(StoneColor newStoneColor, int gridIndex, Location location){
        this.stoneColor = newStoneColor;
        this.gridIndex = gridIndex;
        isKing = false;
        this.location = location;
    }

    public StoneColor getStoneColor(){
        return this.stoneColor;
    }


    public boolean isKing() {
        return isKing;
    }

    public void makeKing()
    {
        this.isKing = true;
    }

    public RelativeLayout getRelativeLayout() {
        return relativeLayout;
    }

    public void setRelativeLayout(RelativeLayout relativeLayout) {
        this.relativeLayout = relativeLayout;
    }

    @Override
    public String toString() {
        return "Stone{" +
                "stoneColor=" + stoneColor +
                ", location=" + location.toString() +
                '}';
    }
}
