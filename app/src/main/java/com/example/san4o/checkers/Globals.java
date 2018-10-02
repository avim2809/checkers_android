package com.example.san4o.checkers;

import android.widget.GridLayout;

import com.example.san4o.checkers.activity.CheckersActivity;
//import com.example.san4o.checkers.activity.HighScoreActivity;
import com.example.san4o.checkers.activity.HighScoreActivity;
import com.example.san4o.checkers.activity.MainActivity;
import com.example.san4o.checkers.enums.StoneColor;

import java.util.ArrayList;

public final class Globals {
    public static GridLayout gameBoardGrid;
    public static HighScoreActivity highScoreActivity;
    public static CheckersActivity checkersActivity;
    public static MainActivity mainActivity;
    public static StoneColor userStoneColor;
    public static StoneColor computerStoneColor;
    public static ArrayList<HighScore> highScoreTable;
    public static boolean gameVolume = true;
}
