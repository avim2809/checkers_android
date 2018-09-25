package com.example.san4o.checkers;

public class HighScore {

    private String name;

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
    }
}
