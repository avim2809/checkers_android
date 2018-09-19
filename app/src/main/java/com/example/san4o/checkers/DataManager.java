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
        sharedPrefEditor.putBoolean("first_run", false);}
        //sharedPrefEditor.putString("name", name);

        sharedPrefEditor.commit();
    }
    //________________________________________________________
    public Stone[][] loadData(){
        if(sharedPreferences.contains("first_run")){
            String gameBoardString = sharedPreferences.getString("game_board_data", "");
            int gameBoardSize = Integer.parseInt(sharedPreferences.getString("game_board_size",""));
            String playerName = sharedPreferences.getString("player_name","");
            Type type = new TypeToken<Stone[][]>(){}.getType();
            Stone[][] gameBoard = gson.fromJson(gameBoardString, type);
            if(gameBoard != null){
                return gameBoard;
            }
        }
        return null;
    }
    //________________________________________________________
    public void initSharedPreferences(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPrefEditor = sharedPreferences.edit();
    }
    //________________________________________________________
}
