package com.example.san4o.checkers;

import android.util.Log;

import java.util.Date;
import java.util.UUID;

public class HighScore {

    private String name;

    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    private int score;

    public HighScore(String name,int score){
        this.name = name;
        this.score = score;
        this.id = generateID();
    }

    private String generateID(){
        String uniqueID = UUID.randomUUID().toString();
        Log.i("uniqueID: ", uniqueID);
        //String id = new Date().getTime()+
        return uniqueID;
    }

    //private long generateLong(){

    //}

    public String getID(){
        return this.id;
    }
}
