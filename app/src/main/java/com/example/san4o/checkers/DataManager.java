package com.example.san4o.checkers;

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
