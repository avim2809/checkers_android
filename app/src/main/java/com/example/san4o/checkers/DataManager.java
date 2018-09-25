package com.example.san4o.checkers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;

public class DataManager implements Serializable {

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor sharedPrefEditor;
    private static DataManager Instance;
    private static Gson gson ;

    private  DataManager(){
    }
    //________________________________________________________
    public static DataManager getInstance(){
        if(Instance == null){
            Instance = new DataManager();
            gson = new Gson();

        }
        return Instance;
    }
    //________________________________________________________
    public void saveData(Object objectToSave,String dataName){
        String stringObjectToSave = gson.toJson(objectToSave);
        sharedPrefEditor.putString(dataName, stringObjectToSave);
        if(!sharedPreferences.contains(("first_run"))){
            sharedPrefEditor.putBoolean("first_run", false);
        }
        sharedPrefEditor.commit();
    }
    //________________________________________________________
    public void saveName(String name){
        sharedPrefEditor.putString("player_name", name);
        if(!sharedPreferences.contains(("first_run"))){
            sharedPrefEditor.putBoolean("first_run", false);
        }
        sharedPrefEditor.commit();
    }
    //________________________________________________________
    public Stone[][] loadGameBoard(){
        if(sharedPreferences.contains("first_run")){
            String gameBoardString = sharedPreferences.getString("game_board_data", "");
            Type stonesType = new TypeToken<Stone[][]>(){}.getType();
            Stone[][] gameBoard = gson.fromJson(gameBoardString, stonesType);
            if(gameBoard != null){
                return gameBoard;
            }
        }
        return null;
    }
    //_______________________________________________________
    public int loadGameBoardSize(){
        int gameBoardSize = Integer.parseInt(sharedPreferences.getString("game_board_size",""));
        return gameBoardSize;
    }
    //________________________________________________________
    public String loadPlayerName(){
        String playerName = sharedPreferences.getString("player_name","");
        return playerName;
    }
    //________________________________________________________
    public void initSharedPreferences(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPrefEditor = sharedPreferences.edit();
    }
    //________________________________________________________
//    public ArrayList<HighScore> loadHighScores(){
//        if(sharedPreferences.contains("first_run")){
//            String highScoresString = sharedPreferences.getString("high_scores","");
//            Type highScoresType = new TypeToken<ArrayList<HighScore>>(){}.getType();
//            ArrayList<HighScore> highScores = gson.fromJson(highScoresString, highScoresType);
//            return highScores;
//        }
//        return null;
//    }
    //________________________________________________________
}
