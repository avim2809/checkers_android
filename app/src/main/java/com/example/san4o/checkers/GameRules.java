package com.example.san4o.checkers;

import com.example.san4o.checkers.enums.StoneColor;
import com.example.san4o.checkers.exceptions.InvalidMoveException;
import com.example.san4o.checkers.exceptions.NotYourTurnException;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

//This class contains the Rules Algo for the game
public final class GameRules {

    /**
     * This method returns all the valid moves a Stone can make on the provided Board.
     * If stone has eat moves then only they are considered valid
     * @param stone Stone to check valid moves for
     * @param board the Board on which the stone resides
     * @return the Set of valid Locations where the Stone can move
     */
    public static LinkedHashSet<Location> getValidMoves(final Stone stone, final GameBoard board) {
        final LinkedHashSet<Location> validMoves = new LinkedHashSet<>();
        boolean hasEatmoves = false;
        int moveY = 0;
        int jumpY = 0;

        //check for single moves
        if (stone.getStoneColor() == StoneColor.BLACK) {
            moveY = 1;
            jumpY = 2;
        } else if (stone.getStoneColor() == StoneColor.WHITE) {
            moveY = -1;
            jumpY = -2;
        }

        final int startX = stone.getLocation().getX();
        final int startY = stone.getLocation().getY();

        final Location moveNW = new Location(startX - 1, startY + moveY);
        final Location moveNE = new Location(startX + 1, startY + moveY);
        final Location moveSW = new Location(startX - 1, startY - moveY);
        final Location moveSE = new Location(startX + 1, startY - moveY);

        final Location jumpNW = new Location(startX - 2, startY + jumpY);
        final Location jumpNE = new Location(startX + 2, startY + jumpY);
        final Location jumpSW = new Location(startX - 2, startY - jumpY);
        final Location jumpSE = new Location(startX + 2, startY - jumpY);

        if (!hasEatmoves)
        {
            if (isLocationOnBoard(moveNW) && board.getStoneAtLocation(moveNW) == null) {
                validMoves.add(moveNW);
            }
            if (isLocationOnBoard(moveNE) && board.getStoneAtLocation(moveNE) == null) {
                validMoves.add(moveNE);
            }
            if (isLocationOnBoard(moveSW) && board.getStoneAtLocation(moveSW) == null && stone.isKing()) {
                //only king can move backwards
                validMoves.add(moveSW);
            }
            if (isLocationOnBoard(moveSE) && board.getStoneAtLocation(moveSE) == null && stone.isKing()) {
                //only king can move backwards
                validMoves.add(moveSE);
            }

        }

        //Eat other stones locations
        if (isLocationOnBoard(jumpNW) && board.getStoneAtLocation(jumpNW) == null && board.getStoneAtLocation(moveNW) != null
                && board.getStoneAtLocation(moveNW).getStoneColor() != stone.getStoneColor()) {
            moveNW.setEatenLocation(true);
            jumpNW.setEatsLocation(moveNW);
            if (!hasEatmoves)
            {
                validMoves.clear();
                hasEatmoves = true;
            }
            validMoves.add(jumpNW);


        }
        if (isLocationOnBoard(jumpNE) && board.getStoneAtLocation(jumpNE) == null && board.getStoneAtLocation(moveNE) != null
                && board.getStoneAtLocation(moveNE).getStoneColor() != stone.getStoneColor()) {
            if (!hasEatmoves)
            {
                validMoves.clear();
                hasEatmoves = true;
            }
            moveNE.setEatenLocation(true);
            jumpNE.setEatsLocation(moveNE);
            validMoves.add(jumpNE);

        }
        if (isLocationOnBoard(jumpSW) && board.getStoneAtLocation(jumpSW) == null && board.getStoneAtLocation(moveSW) != null && stone.isKing()
                && board.getStoneAtLocation(moveSW).getStoneColor() != stone.getStoneColor()) {
            moveSW.setEatenLocation(true);
            jumpSW.setEatsLocation(moveSW);
            if (!hasEatmoves)
            {
                validMoves.clear();
                hasEatmoves = true;
            }

            validMoves.add(jumpSW);

        }
        if (isLocationOnBoard(jumpSE) && board.getStoneAtLocation(jumpSE) == null
                && board.getStoneAtLocation(moveSE) != null && stone.isKing()
                &&board.getStoneAtLocation(moveSE).getStoneColor()!= stone.getStoneColor()) {
            moveSE.setEatenLocation(true);
            jumpSE.setEatsLocation(moveSE);
            if (!hasEatmoves)
            {
                validMoves.clear();
                hasEatmoves = true;
            }

            validMoves.add(jumpSE);

        }

        return validMoves;
    }

    /*
    this method returns all valid eat moves from start location recursively
    * */
    public static Set<Location> getEatMoves(final Stone stone,final GameBoard board)
    {
        Set<Location> eatMoves = new HashSet<>();
        int moveY = 0;
        int jumpY = 0;
        //check for single moves
        if (stone.getStoneColor() == StoneColor.BLACK) {
            moveY = 1;
            jumpY = 2;
        } else if (stone.getStoneColor() == StoneColor.WHITE) {
            moveY = -1;
            jumpY = -2;
        }

        final int startX = stone.getLocation().getX();
        final int startY = stone.getLocation().getY();
        final Location moveNW = new Location(startX - 1, startY + moveY);
        final Location moveNE = new Location(startX + 1, startY + moveY);
        final Location moveSW = new Location(startX - 1, startY - moveY);
        final Location moveSE = new Location(startX + 1, startY - moveY);
        final Location jumpNW = new Location(startX - 2, startY + jumpY);
        final Location jumpNE = new Location(startX + 2, startY + jumpY);
        final Location jumpSW = new Location(startX - 2, startY - jumpY);
        final Location jumpSE = new Location(startX + 2, startY - jumpY);


        if (isLocationOnBoard(jumpNW) && board.getStoneAtLocation(jumpNW) == null && board.getStoneAtLocation(moveNW) != null
                && board.getStoneAtLocation(moveNW).getStoneColor() != stone.getStoneColor()) {
            moveNW.setEatenLocation(true);
            jumpNW.setEatsLocation(moveNW);
            eatMoves.add(jumpNW);
            /* recursively keep scanning */
            Stone temp = new Stone(stone.getStoneColor(),jumpNW);
            eatMoves.addAll(getEatMoves(temp,board));
        }
        if (isLocationOnBoard(jumpNE) && board.getStoneAtLocation(jumpNE) == null && board.getStoneAtLocation(moveNE) != null
                && board.getStoneAtLocation(moveNE).getStoneColor() != stone.getStoneColor()) {
            moveNE.setEatenLocation(true);
            jumpNE.setEatsLocation(moveNE);
            eatMoves.add(jumpNE);
            Stone temp = new Stone(stone.getStoneColor(),jumpNE);
            eatMoves.addAll(getEatMoves(temp,board));

        }
        if (isLocationOnBoard(jumpSW) && board.getStoneAtLocation(jumpSW) == null && board.getStoneAtLocation(moveSW) != null && stone.isKing()
                && board.getStoneAtLocation(moveSW).getStoneColor() != stone.getStoneColor()) {
            moveSW.setEatenLocation(true);
            jumpSW.setEatsLocation(moveSW);
            eatMoves.add(jumpSW);
            Stone temp = new Stone(stone.getStoneColor(),jumpSW);
            eatMoves.addAll(getEatMoves(temp,board));

        }
        if (isLocationOnBoard(jumpSE) && board.getStoneAtLocation(jumpSE) == null
                && board.getStoneAtLocation(moveSE) != null && stone.isKing()
                &&board.getStoneAtLocation(moveSE).getStoneColor()!= stone.getStoneColor()) {
            moveSE.setEatenLocation(true);
            jumpSE.setEatsLocation(moveSE);
            eatMoves.add(jumpSE);
            Stone temp = new Stone(stone.getStoneColor(),jumpSE);
            eatMoves.addAll(getEatMoves(temp,board));

        }

        return eatMoves;

    }

    /**
     * Determine if the Location is on the board.
     * @param location the Location to check
     * @return true if and only if the Location is on the Board
     */
    private static boolean isLocationOnBoard(final Location location) {
        return location.getX() >= 0 && location.getX() < GameBoard.BOARD_SIZE
                && location.getY() >= 0 && location.getY() < GameBoard.BOARD_SIZE;
    }

    /**
     * Determine if the proposed new move is valid.
     * @param stone the Stone making the moves
     * @param previousMoves the Set of previous moves the stone has made
     * @param move the new move to determine if valid
     * @param board the GameBoard on which the moves are taking place
     * @return true if the new move is valid
     */
    public static boolean isValidMove(final Stone stone, final List<Location> previousMoves, final Location move, final GameBoard board) {
        final Stone theoreticalPiece;

        if (previousMoves.size() > 0) {
            final Location previousLocation = previousMoves.get(previousMoves.size() - 1);
            final Location prevPrevLocation = previousMoves.size() > 1 ? previousMoves.get(previousMoves.size() - 2) : stone.getLocation();
            if (!isJump(previousLocation, move) || !isJump(prevPrevLocation, previousLocation)) {
                return false;
            }
            theoreticalPiece = new Stone(stone.getStoneColor(), previousLocation);
        } else {
            theoreticalPiece = stone;
        }

        return getValidMoves(theoreticalPiece, board).contains(move);
    }

    /**
     * Determines if a move is a Jump (eat move).
     * @param location the Location of the start
     * @param move the Location being moved to
     * @return true if the move is a jump
     */
    private static boolean isJump(final Location location, final Location move) {
        return Math.abs(move.getX() - location.getX()) > 1;
    }
    /**
     * Move a stone in a Game
     * @param game the Game in which to move the stone
     * @param stone the Stone to move in the game
     * @param moves the Set of moves the stone will make
     */
    public static void move(final GameManager game, final Stone stone, final List<Location> moves)  throws NotYourTurnException,InvalidMoveException {
        if (stone.getStoneColor() != game.getTurn()) {
            throw new NotYourTurnException(game.getTurn());
        }

        for (Location location : moves) {
            if (!GameRules.getValidMoves(stone, game.getGameBoard()).contains(location)) {
                throw new InvalidMoveException(stone.getStoneColor(), location);
            }

            final Iterator<Stone> iterator = game.getGameBoard().getActiveStones().iterator();
            while (iterator.hasNext()) {
                final Stone jumped = iterator.next();
                if (jumped.getLocation().isBetween(stone.getLocation(), location)) {
                    iterator.remove();
                }
            }

            stone.setLocation(location);

            if (stone.getStoneColor() == StoneColor.WHITE) {
                if (stone.getLocation().getY() == GameBoard.BOARD_SIZE - 1) {
                    stone.makeKing();
                    game.getGameBoard().onBecomingKing(stone);

                }
            } else if (stone.getStoneColor() == StoneColor.BLACK) {
                if (stone.getLocation().getY() == 0) {
                    stone.makeKing();
                    game.getGameBoard().onBecomingKing(stone);
                }
            }
        }
    }


    /**
     * A private constructor to prevent instantiation of this utility class.
     */
    private GameRules() { }


}
