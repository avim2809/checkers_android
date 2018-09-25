package com.example.san4o.checkers.exceptions;

import com.example.san4o.checkers.Location;
import com.example.san4o.checkers.enums.StoneColor;

public class NotYourTurnException extends RuntimeException {
    private StoneColor turn;
    private Location attempted_move;

    public NotYourTurnException(StoneColor turn) {
        this.turn = turn;
    }

    @Override
    public String toString() {
        String message = "NotYourTurnException:{" +turn.toString() + " Attempted to make a move " +
                "while it wasn't its turn!}";
        return message;
    }

}
