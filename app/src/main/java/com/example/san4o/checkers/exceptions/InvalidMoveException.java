package com.example.san4o.checkers.exceptions;

import com.example.san4o.checkers.Location;
import com.example.san4o.checkers.enums.StoneColor;

public class InvalidMoveException extends RuntimeException {
    private Location move;
    private StoneColor stone;

    public InvalidMoveException(StoneColor stone, Location move) {
        this.move = move;
        this.stone = stone;
    }

    @Override
    public String toString() {
        return "InvalidMoveException{" +
                "move=" + move +
                ", stone=" + stone +
                '}';
    }
}
