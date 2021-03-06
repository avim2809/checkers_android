package com.example.san4o.checkers;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.san4o.checkers.activity.HighScoreActivity;
import com.example.san4o.checkers.enums.PlayerRole;
import com.example.san4o.checkers.enums.StoneColor;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;


public class GameManager implements View.OnClickListener {
    public final int INIT_STONES_NUM = 12;
    private final Handler handler;
    private ArrayList<HighScore> highScoreArray;
    private GameBoard gameBoard;
    private Player playerUser;
    private Player playerComputer;
    private StoneColor turn;
    private Stone last_clicked_stone = null;
    private LinkedHashSet<Location> lastValidMoves;
    private Location lastClickedLocation;
    protected ArrayList<Stone> lostStones = null;
    private boolean waiting_for_movement = false;
    private AnimationManager animationManager;
    private SoundsManager soundsManager;
    private ArrayList<ImageView> glowingCells;
    private ArrayList<ImageView> invisibleCells;
    private boolean eat_turn = false;

    public GameManager() {
        glowingCells = new ArrayList<>();
        invisibleCells = new ArrayList<>();
        animationManager = new AnimationManager();


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundsManager = new SoundsManager();
        }
        handler = new Handler();

        if (Globals.highScoreTable == null) {
            highScoreArray = new ArrayList<HighScore>();
        } else {
            highScoreArray = Globals.highScoreTable;
        }
    }

    //_____________________________________________
    public StoneColor getTurn() {
        return turn;
    }

    //_____________________________________________
    public Player getPlayerUser() {
        return playerUser;
    }

    //_____________________________________________
    public Player getPlayerComputer() {
        return playerComputer;
    }

    //_____________________________________________
    public Player getPlayerWithTurn() {
        if (playerUser.getColor() == turn)
            return playerUser;
        return playerComputer;
    }

    //_____________________________________________
    public void initGame() {
        gameBoard = new GameBoard(this);
        turn = StoneColor.WHITE;
        playerUser = new Player();
        playerComputer = new Player();
        generateColor();
        gameBoard.initComputerStones(playerComputer.getColor());
        gameBoard.initUserStones(playerUser.getColor());
        playerComputer.setRole(PlayerRole.COMPUTER);
        playerUser.setRole(PlayerRole.USER);
        Globals.checkersActivity.initScores();
    }

    //___________________________________________-
    private void generateColor() {
        playerUser.setPlayerStoneColor(StoneColor.WHITE);
        playerComputer.setPlayerStoneColor(StoneColor.BLACK);
    }

    //_____________________________________________________________
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    //_____________________________________________________________
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        if (turn == playerUser.getColor()) {
            if (view instanceof ImageView) {
                Stone clickedStone = findClickedStone((ImageView) view);
                Location clickedLocation = Location.getLocationfromString(view.getTag().toString());
                lastClickedLocation = clickedLocation;

                if (clickedStone != null && clickedStone.getStoneColor() == playerUser.getColor()) {
                    //clicked on some user stone
                    Log.i("onClick: GameManager:", "" + clickedStone.toString());
                    LinkedHashSet<Location> validMoves = GameRules.getValidMoves(clickedStone, gameBoard);
                    Log.i("onClick: GameManager:", "valid moves:" + validMoves.toString());
                    if (!validMoves.isEmpty()) {
                        if (last_clicked_stone != null) {
                            // user clicked on same stone or different stone before actually moving
                            if (!clickedStone.equals(last_clicked_stone)) {
                                //user clicked on different stone: clear previous marked and cells mark new cells
                                if (lastValidMoves != null) {
                                    unmarkPossibleMovesOnBoard();
                                }
                                markPossibleMovesOnBoard(validMoves);
                                lastValidMoves = validMoves;
                            }
                        } else {
                            //first time click
                            markPossibleMovesOnBoard(validMoves);
                            lastValidMoves = validMoves;
                        }
                        if (!waiting_for_movement) {
                            waiting_for_movement = true;
                        }
                    } else {
                        //has no valid moves
                        if (last_clicked_stone != null && lastValidMoves != null) {
                            unmarkPossibleMovesOnBoard();
                            lastValidMoves = null;
                        }
                        if (waiting_for_movement) {
                            waiting_for_movement = false;
                        }
                        new StyleableToast
                                .Builder(Globals.checkersActivity)
                                .text(Globals.checkersActivity.getResources().getString(R.string.no_moves))
                                .textColor(android.graphics.Color.WHITE)
                                .textBold()
                                .backgroundColor(Color.rgb(216, 180, 141))
                                .length(Toast.LENGTH_SHORT)
                                .show();

                    }
                    last_clicked_stone = clickedStone;
                } else {
                    if (waiting_for_movement) {
                        //player clicked on empty frame  and wants to move somewhere
                        if (lastValidMoves != null && last_clicked_stone != null) {
                            if (!lastValidMoves.contains(clickedLocation)) {
                                //invalid move attempt
                                new StyleableToast
                                        .Builder(Globals.checkersActivity)
                                        .text(Globals.checkersActivity.getResources().getString(R.string.invalid_move))
                                        .textColor(android.graphics.Color.WHITE)
                                        .backgroundColor(Color.rgb(216, 180, 141))
                                        .length(Toast.LENGTH_SHORT)
                                        .show();
                                soundsManager.playWrongMoveSound();
                                return;
                            }
                            clickedLocation = findClickedLocationInValidMoves(clickedLocation);
                            if (clickedLocation.getEatsLocation() != null) {
                                moveAndEatStone(last_clicked_stone, clickedLocation.getEatsLocation(), clickedLocation);
                            } else moveStone(last_clicked_stone, clickedLocation);
                            waiting_for_movement = false;
                        } else
                            last_clicked_stone = null;
                    }
                }
            }
        }
    }

    //_____________________________________________________________
    private Location findClickedLocationInValidMoves(Location locationToFind) {
        Location locToRet = null;
        Iterator<Location> it = lastValidMoves.iterator();
        while (it.hasNext()) {
            Location loc = it.next();
            if (loc.equals(locationToFind)) {
                locToRet = loc;
                break;
            }
        }
        return locToRet;
    }

    //_____________________________________________________________
    private void markPossibleMovesOnBoard(Set<Location> locations) {
        unmarkPossibleMovesOnBoard();
        for (Location location : locations) {
            RelativeLayout grid_frame = gameBoard.getLayoutAtLocation(location);
            if (grid_frame != null) {
                ImageView imageBG = grid_frame.findViewWithTag(gameBoard.BACKGROUND_TAG + ":" + location);
                ImageView imageGlow = grid_frame.findViewWithTag(gameBoard.GLOW_TAG + ":" + location);
                if (location.getEatsLocation() != null) {
                    //imageGlow.startAnimation(animationManager.glowAnimation);
                    imageGlow.setVisibility(View.INVISIBLE);
                } else {
                    //imageGlow.clearAnimation();
                    imageGlow.setVisibility(View.VISIBLE);
                }

                imageBG.startAnimation(animationManager.glowAnimation);
                glowingCells.add(imageBG);
            }
        }
    }

    //_________________________________________________________
    private void unmarkPossibleMovesOnBoard() {
        for (int i = 0; i < glowingCells.size(); i++) {
            glowingCells.get(i).clearAnimation();
        }

        for (int i = 0; i < invisibleCells.size(); i++) {
            invisibleCells.get(i).setVisibility(View.VISIBLE);
        }
    }

    //_________________________________________________________
    private Stone findClickedStone(ImageView image) {
        Stone stoneToRet = null;
        for (int i = 0; i < gameBoard.getActiveStones().size(); i++) {
            Stone currStone = gameBoard.getActiveStones().get(i);
            if (currStone.getLocation().equals(Location.getLocationfromString(image.getTag().toString()))) {
                stoneToRet = gameBoard.getActiveStones().get(i);
                break;
            }
        }
        return stoneToRet;
    }

    //_________________________________________________________
    /*`
    Moves stone on the grid with animation possibly eating stones on the way or becoming king
    update the Grid view and player score
    * */
    private void moveStone(Stone stone, Location destination) {
        addMove();
        lastClickedLocation = destination;
        last_clicked_stone = stone;
        ImageView currImageView = getGameBoard().getStoneImageViewAtLocation(last_clicked_stone.getLocation());

        if ((currImageView == null)) throw new AssertionError();
        RelativeLayout currentParent = gameBoard.getLayoutAtLocation(last_clicked_stone.getLocation());
        RelativeLayout newParent = gameBoard.getLayoutAtLocation(lastClickedLocation);

        if (newParent == null) throw new AssertionError();


        soundsManager.playMoveSound();       //ImageView imageToMove = currentParent.findViewWithTag(gameBoard.STONE_TAG + ":" + last_clicked_stone.getLocation().toString());
        currentParent.removeView(currImageView);

        newParent.addView(currImageView);
        newParent.requestLayout();
        unmarkPossibleMovesOnBoard();
        currImageView.setTag(gameBoard.STONE_TAG + ":" + lastClickedLocation.toString());
        gameBoard.changeStoneLocationOnBoard(last_clicked_stone.getLocation(), lastClickedLocation);
        gameBoard.getStoneAtLocation(last_clicked_stone.getLocation()).setLocation(lastClickedLocation);
        if (GameRules.checkIfKingPositionAndSetKingState(stone)) {
            makeKing(stone);
        }
        if (!eat_turn) {
            switch (getPlayerWithTurn().getRole()) {
                case USER:
                    turn = playerComputer.getColor();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            playAsComputer();
                        }
                    }, 1000);

                    break;
                case COMPUTER:
                    turn = playerUser.getColor();
                    break;
            }
        }
    }

    //_________________________________________________________
    private void addMove() {
        if (this.getPlayerWithTurn().getRole() == PlayerRole.USER) {
            playerUser.addMove();
        }
    }

    //_________________________________________________________
    private void moveAndEatStone(Stone movedStone, Location eatenLocation, Location moveLocation) {
        eat_turn = true;
        //first move the stone normally then eat
        moveStone(movedStone, moveLocation);
        lastClickedLocation = moveLocation;
        last_clicked_stone = movedStone;

        //Stone eatenStone = gameBoard.getStoneAtLocation(eatenLocation);
        ImageView eatenImageViewStone = gameBoard.getStoneImageViewAtLocation(eatenLocation);
        RelativeLayout eatenLayout = gameBoard.getLayoutAtLocation(eatenLocation);


        eatenLayout.requestLayout();
        gameBoard.removeStoneAtLocation(eatenLocation);

        if (getPlayerWithTurn().getRole() == PlayerRole.COMPUTER) {
            soundsManager.playEatenSound();
        } else {
            soundsManager.playEatSound();
        }

        eatenImageViewStone.startAnimation(animationManager.eatAnimation);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                eatenImageViewStone.clearAnimation();
                eatenLayout.removeView(eatenImageViewStone);
            }
        }, 1000);

        eat_turn = false;
        //switch Turn and update score
        switch (getPlayerWithTurn().getRole()) {
            case USER:
                turn = playerComputer.getColor();
                playerComputer.setStonesNum(playerComputer.getStonesNum() - 1);
                Globals.checkersActivity.BlackScore.setText(playerComputer.getStonesNum() + "");
                Globals.checkersActivity.BlackImg.startAnimation(animationManager.jumpAnimation);
                //Globals.checkersActivity.BlackImg.startAnimation(animationManager.jumpAnimation);
                if (playerComputer.getStonesNum() == 0) {
                    OnVictory(PlayerRole.USER);
                    soundsManager.playWinSound();
                    return;
                }

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playAsComputer();
                    }
                }, 1000);

                break;
            case COMPUTER:
                playerUser.setStonesNum(playerUser.getStonesNum() - 1);
                Globals.checkersActivity.WhiteScore.setText(playerUser.getStonesNum() + "");
                Globals.checkersActivity.WhiteImg.startAnimation(animationManager.jumpAnimation);
                if (playerUser.getStonesNum() == 0) {
                    OnVictory(PlayerRole.COMPUTER);
                    soundsManager.playLossSound();
                    return;
                }

                turn = playerUser.getColor();
                break;
        }
    }

    //_________________________________________________________
    private void playAsComputer() {
        if (turn == playerComputer.getColor()) {
            Stone selected = null;
            boolean has_eat_moves = false;
            boolean has_moves = false;
            LinkedHashSet<Location> validMoves = new LinkedHashSet<>();
            HashMap<Stone, LinkedHashSet<Location>> allMoves = new HashMap<>();

            for (Stone stone : gameBoard.getActiveStones()) {
                if (stone.getStoneColor() == playerComputer.getColor()) {
                    Log.i("onClick: GameManager:", "" + stone.toString());

                    validMoves = GameRules.getValidMoves(stone, gameBoard);
                    Log.i("onClick: GameManager:", "valid moves:" + validMoves.toString());
                    if (!validMoves.isEmpty()) {
                        selected = stone;
                        has_moves = true;
                        allMoves.put(stone, validMoves);
                    }
                }
            }

            if (!has_moves) {
                throw new RuntimeException("playAsComputer: didn't find any stone with valid moves");
            }

            for (Stone current : allMoves.keySet()) {
                validMoves = allMoves.get(current);
                if (validMoves.iterator().next().getEatsLocation() != null) {
                    //found a stone with eating moves
                    has_eat_moves = true;
                    selected = current;
                    break;
                }
            }

            Location chosen_loc = null;

            if (has_eat_moves) {
                validMoves = GameRules.getValidMoves(selected, gameBoard);
                chosen_loc = Objects.requireNonNull(randomLocationFromSet(validMoves));
                moveAndEatStone(selected, chosen_loc.getEatsLocation(), chosen_loc);
                return;
            } else {
                selected = getRandomStone();
                validMoves = GameRules.getValidMoves(selected, gameBoard);
                chosen_loc = Objects.requireNonNull(randomLocationFromSet(validMoves));
                moveStone(selected, chosen_loc);
                return;
            }
        }
    }

    //__________________________________________
    private Stone getRandomStone() {
        Random rand = new Random();
        int index;
        Stone stoneToRet = null;
        while (stoneToRet == null) {
            index = rand.nextInt(gameBoard.getActiveStones().size());
            if ((gameBoard.getActiveStones().get(index)).getStoneColor() == playerComputer.getColor()) {
                LinkedHashSet<Location> validMoves = GameRules.getValidMoves(gameBoard.getActiveStones().get(index), gameBoard);
                if (validMoves.size() > 0) {
                    stoneToRet = gameBoard.getActiveStones().get(index);
                    break;
                }
            }
        }

        return stoneToRet;
    }

    //__________________________________________
    public void addHighScore(String name, int score) {
        if (highScoreArray.size() >= 10) {
            Collections.sort(highScoreArray, new Comparator<HighScore>() {

                @Override
                public int compare(HighScore high1, HighScore high2) {
                    return (high1.getScore() - high2.getScore());
                }
            });

            HighScore lastHighScore = highScoreArray.get(highScoreArray.size() - 1);
            Log.i("new high score", String.valueOf(score));
            if (lastHighScore.getScore() >= score) {
                highScoreArray.remove(highScoreArray.size() - 1);
                HighScore currHighScore = new HighScore(name, score);
                Globals.currHighScore = currHighScore;
                highScoreArray.add(currHighScore);
                DataManager.getInstance().saveData(highScoreArray, "high_scores");

            }
        } else {
            HighScore currHighScore = new HighScore(name, score);
            Globals.currHighScore = currHighScore;
            highScoreArray.add(currHighScore);
            DataManager.getInstance().saveData(highScoreArray, "high_scores");
        }
    }

    //__________________________________________
    private Location randomLocationFromSet(LinkedHashSet<Location> locations) {
        int size = locations.size();
        if (size > 0) {
            Random rand = new Random();
            int item = rand.nextInt(size); // In real life, the Random object should be rather more shared than this
            int i = 0;
            for (Location loc : locations) {
                if (i == item)
                    return loc;
                i++;
            }
            return null;
        }
        return null;
    }

    //__________________________________________
    private void OnVictory(PlayerRole winner) {
        //TODO: handle victor and end game
        switch (winner) {
            case USER:
                addHighScore(playerUser.getName(), playerUser.getMovesCount());
                Globals.highScoreTable = highScoreArray;
                DataManager.getInstance().saveData(highScoreArray, "high_scores");

                break;
            case COMPUTER:
                break;
        }

        Intent highScoresActivity = new Intent(Globals.checkersActivity, HighScoreActivity.class);
        Globals.checkersActivity.startActivity(highScoresActivity);
        Globals.checkersActivity.finish();
    }
    //__________________________________________

    private void makeKing(Stone stone) {
        int kingDrawable = 0;
        if (stone.getStoneColor() == StoneColor.BLACK) {
            kingDrawable = R.drawable.black_stone_king;
        } else {
            kingDrawable = R.drawable.white_stone_king;
        }

        RelativeLayout currentParent = gameBoard.getLayoutAtLocation(stone.getLocation());
        ImageView imageToMove = currentParent.findViewWithTag(gameBoard.STONE_TAG + ":" + stone.getLocation().toString());
        imageToMove.setBackgroundResource(kingDrawable);
        soundsManager.playKingSound();
    }
    //__________________________________________
}
