package com.example.san4o.checkers;

import com.example.san4o.checkers.enums.Color;

import java.util.ArrayList;

public abstract class Player {
    protected int score=0;
    protected ArrayList<Stone> stones = null;
    protected ArrayList<Stone> lostStones = null;
    protected Color playerColor ;

    public abstract void makeMove();

    public void setPlayerColor(Color playerColor) {
        this.playerColor = playerColor;
    }
}
