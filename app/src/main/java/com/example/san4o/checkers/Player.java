package com.example.san4o.checkers;
import com.example.san4o.checkers.enums.PlayerRole;
import com.example.san4o.checkers.enums.StoneColor;

public class Player {
    private String name;
    private int stonesNum=0;
    private StoneColor playerStoneColor;
    protected GameBoard gameBoard;
    protected Stone[][] updatedBoardStones;
    private boolean turn = false;

    public PlayerRole getRole() {
        return role;
    }

    public void setRole(PlayerRole role) {
        this.role = role;
    }

    private PlayerRole role;

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StoneColor getPlayerStoneColor() {
        return playerStoneColor;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public Stone[][] getUpdatedBoardStones() {
        return updatedBoardStones;
    }

    public void setUpdatedBoardStones(Stone[][] updatedBoardStones) {
        this.updatedBoardStones = updatedBoardStones;
    }

    public void setPlayerStoneColor(StoneColor playerStoneColor) {
        this.playerStoneColor = playerStoneColor;
    }
    public StoneColor getColor(){
        return  this.playerStoneColor;
    }

    public int getStonesNum() {
        return stonesNum;
    }

    public void setStonesNum(int stonesNum) {
        this.stonesNum = stonesNum;
    }
    //_____________________________________________
}
