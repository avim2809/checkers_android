package com.example.san4o.checkers;

import android.widget.GridLayout;

import com.example.san4o.checkers.activity.GameActivity;

public class DataManager {


    private static DataManager Instance;

    private  DataManager(){
    }

    public static DataManager getInstance(){
        if(Instance == null){
            Instance = new DataManager();
        }
        return Instance;
    }
}
