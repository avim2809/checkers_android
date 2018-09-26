package com.example.san4o.checkers;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.transition.ChangeTransform;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.san4o.checkers.activity.HighScoreActivity;
import com.example.san4o.checkers.enums.PlayerRole;
import com.example.san4o.checkers.enums.StoneColor;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import static android.support.v4.content.ContextCompat.startActivity;

public class GameManager implements View.OnClickListener {
    public final int INIT_STONES_NUM = 12;
    private ArrayList highScoreArray;
    private GameBoard gameBoard;
    private Player playerUser;
    private Player playerComputer;
    private StoneColor turn;
    private Stone last_clicked_stone;
    private LinkedHashSet<Location> lastvalidMoves;
    private Location lastClickedLocation;
    protected ArrayList<Stone> lostStones = null;
    private boolean waiting_for_movement = false;
    private ArrayList<AnimationDrawable> animationDrawableArrayList;
    private ArrayList<ImageView> imageViewArrayList;
    private ImageView backgroundImg, backgroundGlowImg;

    public StoneColor getTurn() {
        return turn;
    }

    private Animation glowAnimation;
    private ArrayList<ImageView> glowingCells;

    public Player getPlayerUser() {
        return playerUser;
    }

    public Player getPlayerComputer() {
        return playerComputer;
    }

    public void setTurn(StoneColor turn) {
        this.turn = turn;
    }

    //_____________________________________________
    public GameManager() {
        highScoreArray = new ArrayList<HighScore>();
        initGlowAnim();
    }

    //_____________________________________________
    public Player getPlayerwithTurn() {
        if (playerUser.getColor() == turn)
            return playerUser;
        return playerComputer;
    }

    //_____________________________________________
    private void initGlowAnim() {
        glowingCells = new ArrayList<>();
        glowAnimation = AnimationUtils.loadAnimation(Globals.checkersActivity, R.anim.glow_anim);
    }

    //_____________________________________________
    public void initGame() {
        animationDrawableArrayList = new ArrayList<>();
        imageViewArrayList = new ArrayList<>();
        gameBoard = new GameBoard(this);
        turn = StoneColor.WHITE;
        playerUser = new Player();
        playerComputer = new Player();
        generateColor();
        gameBoard.initComputerStones(playerComputer.getColor());
        gameBoard.initUserStones(playerUser.getColor());
        playerComputer.setRole(PlayerRole.COMPUTER);
        playerComputer.setStonesNum(INIT_STONES_NUM);
        playerUser.setRole(PlayerRole.USER);
        playerUser.setStonesNum(INIT_STONES_NUM);
        backgroundImg = new ImageView(Globals.checkersActivity);
        backgroundImg.setImageResource(gameBoard.getDrwableCellByColor(StoneColor.BLACK));
        backgroundGlowImg = new ImageView(Globals.checkersActivity);
        backgroundGlowImg.setImageResource(gameBoard.getDrawableCellGlowCellByColor(StoneColor.BLACK));
    }

    //___________________________________________-
    private void generateColor() {
//        Random rand = new Random();
//        int colorNum = rand.nextInt(2)+1;
//
//        if(colorNum == 1){
//            playerUser.setPlayerStoneColor(StoneColor.WHITE);
//            playerComputer.setPlayerStoneColor(StoneColor.BLACK);
//        } else if (colorNum == 2){
//            playerUser.setPlayerStoneColor(StoneColor.BLACK);
//            playerComputer.setPlayerStoneColor(StoneColor.WHITE);
//        }

        //avoid random for now till we adapt ALGO to check what is current player color
        playerUser.setPlayerStoneColor(StoneColor.WHITE);
        playerComputer.setPlayerStoneColor(StoneColor.BLACK);
    }

    //_____________________________________________________________
    public void saveData() {
        DataManager.getInstance().saveData(playerUser.getName(), "player_name");
        DataManager.getInstance().saveData(gameBoard.getBoard(), "game_board_data");
        DataManager.getInstance().saveData(GameBoard.BOARD_SIZE, "game_board_size");
    }

    //_____________________________________________________________
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    //_____________________________________________________________
    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    //_____________________________________________________________
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
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
                            if (lastvalidMoves != null) {
                                unmarkPossibleMovesOnBoard();
                            }
                            markPossibleMovesOnBoard(validMoves);
                            lastvalidMoves = validMoves;
                        }
                    } else {
                        //first time click
                        markPossibleMovesOnBoard(validMoves);
                        lastvalidMoves = validMoves;
                    }
                    if (!waiting_for_movement) {
                        waiting_for_movement = true;
                    }
                } else {
                    //has no valid moves
                    if (last_clicked_stone != null && lastvalidMoves != null) {
                        //clear previous marked cell
//                        markPossibleMovesOnBoard(lastvalidMoves,true);
                        unmarkPossibleMovesOnBoard();
                        lastvalidMoves = null;
                    }
                    if (waiting_for_movement) {
                        waiting_for_movement = false;
                    }
                    new StyleableToast
                            .Builder(Globals.checkersActivity)
                            .text(Globals.checkersActivity.getResources().getString(R.string.no_moves))
                            .textColor(android.graphics.Color.WHITE)
                            .backgroundColor(Color.MAGENTA)
                            .length(Toast.LENGTH_SHORT)
                            .show();

                }
                last_clicked_stone = clickedStone;
            } //end clickedStone != null
            else {
                if (waiting_for_movement) {
                    //player clicked on empty frame  and wants to move somewhere
                    if (lastvalidMoves != null && last_clicked_stone != null) {
                        if (!lastvalidMoves.contains(clickedLocation)) {
                            //invalid move attempt
                            new StyleableToast
                                    .Builder(Globals.checkersActivity)
                                    .text(Globals.checkersActivity.getResources().getString(R.string.invalid_move))
                                    .textColor(android.graphics.Color.WHITE)
                                    .backgroundColor(android.graphics.Color.MAGENTA)
                                    .length(Toast.LENGTH_SHORT)
                                    .show();
                            return;


                        }
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

    //_____________________________________________________________
    //mark all possible moves on board
    private void markPossibleMovesOnBoard(Set<Location> locations) {
        unmarkPossibleMovesOnBoard();
        //create new animation for new cells
        for (Location location : locations) {

            RelativeLayout grid_frame = gameBoard.getLayoutatLocation(location);
            if (grid_frame != null) {
                ImageView imageBG = grid_frame.findViewWithTag(gameBoard.BACKGROUND_TAG + ":" + location);
                imageBG.startAnimation(glowAnimation);
                glowingCells.add(imageBG);
                //AnimationDrawable lastAnimationDrable = new AnimationDrawable();
                //ImageView origImage = grid_frame.findViewWithTag(gameBoard.BACKGROUND_TAG+":"+location);
                //imageViewArrayList.add(origImage);
//                ImageView backgroundImg = new ImageView(checkersActivity);
//                backgroundImg.setImageResource(gameBoard.getDrwableCellByColor(StoneColor.BLACK));
//                ImageView backgroundGlowImg = new ImageView(checkersActivity);
//                backgroundGlowImg.setImageResource(gameBoard.getDrawableCellGlowCellByColor(StoneColor.BLACK));
                //lastAnimationDrable.addFrame(backgroundImg.getDrawable(),500);
                //lastAnimationDrable.addFrame(backgroundGlowImg.getDrawable(),500);
                //lastAnimationDrable.setOneShot(false);
                //origImage.setBackground(lastAnimationDrable);
//                backgroundImg.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        lastAnimationDrable.start();
//                    }
//                });
                //lastAnimationDrable.start();
                //animationDrawableArrayList.add(lastAnimationDrable);
            }
        }
    }

    //_________________________________________________________
    private void unmarkPossibleMovesOnBoard() {
        //for (AnimationDrawable animationDrawable:animationDrawableArrayList) {
        //animationDrawable.stop();
        // }
        //for (ImageView imageView:imageViewArrayList)
        //imageView.setBackground(backgroundImg.getDrawable());


        //animationDrawableArrayList.clear();
        //imageViewArrayList.clear();
        for (int i = 0; i < glowingCells.size(); i++) {
            glowingCells.get(i).clearAnimation();
        }
    }

    //_________________________________________________________
    private Stone findClickedStone(ImageView image) {
        Stone stoneToRet = null;
        for (int i = 0; i < gameBoard.getActiveStones().size(); i++) {
            Stone stone = gameBoard.getActiveStones().get(i);
            if (stone.getLocation().equals(Location.getLocationfromString(image.getTag().toString()))) {
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
        //move the stone over the path using animation
        lastClickedLocation = destination;
        last_clicked_stone = stone;
        //get Layout (parent) of square
        ImageView iv = getGameBoard().getStoneImageViewAtLocation(last_clicked_stone.getLocation());
        if ((iv == null)) throw new AssertionError();
        RelativeLayout currentParent = gameBoard.getLayoutatLocation(last_clicked_stone.getLocation());
        RelativeLayout newParent = gameBoard.getLayoutatLocation(lastClickedLocation);
        if (newParent == null) throw new AssertionError();
        Transition move = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            move = new ChangeTransform().addTarget(iv).setDuration(500);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition(newParent, move);
        }
        //handle post animation actions ( if doing that on TransitionEnd, it doesn't work properly)
        currentParent.removeView(iv);
        newParent.addView(iv);
        newParent.requestLayout();
        unmarkPossibleMovesOnBoard();
        iv.setTag(gameBoard.STONE_TAG + ":" + lastClickedLocation.toString());
        gameBoard.changeStoneLocationOnBoard(last_clicked_stone.getLocation(), lastClickedLocation);
        gameBoard.getStoneAtLocation(last_clicked_stone.getLocation()).setLocation(lastClickedLocation);


        switch (getPlayerwithTurn().getRole()) {
            case USER:
                turn = playerComputer.getColor();
                playAsComputer();
                break;
            case COMPUTER:
                turn = playerUser.getColor();
                break;
        }
    }

    //_________________________________________________________
    private void moveAndEatStone(Stone movedStone, Location eatenLocation, Location moveLocation) {
        //first move the stone normally then eat
        moveStone(movedStone, moveLocation);
        lastClickedLocation = moveLocation;
        last_clicked_stone = movedStone;
        //get Layout (parent) of square
        Stone eatenStone = gameBoard.getStoneAtLocation(eatenLocation);
        ImageView eaten_iv = gameBoard.getStoneImageViewAtLocation(eatenLocation);
        RelativeLayout eaten_layout = gameBoard.getLayoutatLocation(eatenLocation);

        //handle post move animation actions ( if doing that on TransitionEnd, it doesn't work properly)
        //Post eat Animation actions

        //eaten_iv.setVisibility(View.INVISIBLE);
//        eaten_layout.removeView(eaten_iv); This Line causes exception
        eaten_layout.endViewTransition(eaten_iv);
        eaten_layout.requestLayout();
        gameBoard.removeStoneAtLocation(eatenLocation);


        //Eat Animation Goes Here

        //TODO: LILYA this is the eat animation part, call your implementation instead
//        ImageView eaten_iv_blown = new ImageView(checkersActivity);
//        eaten_iv_blown.setImageResource(R.drawable.stone_black_big);
//        if (turn == StoneColor.WHITE)
//            eaten_iv_blown.setImageResource(R.drawable.stone_white_big);
//        Transition eatStone_blow_size = new ChangeBounds().addTarget(eaten_iv_blown).setDuration(500);
//        Transition eatStone_fade_out = new Fade(Fade.OUT).addTarget(eaten_iv_blown).setDuration(500);
//        TransitionSet moveAndEat = new TransitionSet();
//        moveAndEat.addTransition(eatStone_blow_size).addTransition(eatStone_fade_out);
//        moveAndEat.setOrdering(TransitionSet.ORDERING_TOGETHER);
//        TransitionManager.beginDelayedTransition(eaten_layout,moveAndEat);


        //switch Turn and update score
        switch (getPlayerwithTurn().getRole()) {
            case USER:
                turn = playerComputer.getColor();
                playerComputer.setStonesNum(playerComputer.getStonesNum() - 1);
                Globals.checkersActivity.BlackScore.setText(playerComputer.getStonesNum() + "");
                if (playerComputer.getStonesNum() == 0) {
                    //User won
                    OnVictory(PlayerRole.USER);
                    return;
                }

                playAsComputer();
                break;
            case COMPUTER:
                playerUser.setStonesNum(playerUser.getStonesNum() - 1);
                Globals.checkersActivity.WhiteScore.setText(playerUser.getStonesNum() + "");
                if (playerUser.getStonesNum() == 0) {
                    //Computer won
                    OnVictory(PlayerRole.COMPUTER);
                    return;
                }

                turn = playerUser.getColor();
                break;
        }
    }

    //_________________________________________________________
    private void playAsComputer() {
        //find the first computer stone that has valid moves
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Stone selected = null;
        boolean has_eat_moves = false;
        boolean has_moves = false;
        LinkedHashSet<Location> validMoves = new LinkedHashSet<>();
        HashMap<Stone, LinkedHashSet<Location>> allmoves = new HashMap<>();

        for (Stone stone : gameBoard.getActiveStones()) {
            if (stone.getStoneColor() == playerComputer.getColor()) {
                validMoves = GameRules.getValidMoves(stone, gameBoard);
                if (!validMoves.isEmpty()) {
                    selected = stone;
                    has_moves = true;
                    allmoves.put(stone, validMoves);
                }
            }
        }

        if (!has_moves) {
            throw new RuntimeException("playAsComputer: didn't find any stone with valid moves");
        }

        //now scan again to see if there are any stone with eating moves, if so than it would be the selected stone
        for (Stone current : allmoves.keySet()) {
            validMoves = allmoves.get(current);
            if (validMoves.iterator().next().getEatsLocation() != null) {
                //found a stone with eating moves
                has_eat_moves = true;
                selected = current;
                break;
            }
        }

        Location chosen_loc = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            chosen_loc = Objects.requireNonNull(randomLocationFromSet(validMoves));
        }
        if (has_eat_moves) {
            moveAndEatStone(selected, chosen_loc.getEatsLocation(), chosen_loc);
        } else moveStone(selected, chosen_loc);
    }

    //__________________________________________
    public void addHighScore(String name, int score) {
        if (highScoreArray.size() >= 10) {
            Collections.sort(highScoreArray, Collections.reverseOrder());
            highScoreArray.remove(highScoreArray.size() - 1);
        }
        highScoreArray.add(new HighScore(name, score));
        DataManager.getInstance().saveData(highScoreArray, "high_scores");
    }
    //__________________________________________
    private Location randomLocationFromSet(LinkedHashSet<Location> locations) {
        int size = locations.size();
        int item = new Random().nextInt(size); // In real life, the Random object should be rather more shared than this
        int i = 0;
        for (Location loc : locations) {
            if (i == item)
                return loc;
            i++;
        }
        return null;
    }

    //__________________________________________
    private void OnVictory(PlayerRole winner) {
        //TODO: handle victor and end game
        switch (winner) {
            case USER:
                // deside what is the score of a winner
                addHighScore(winner.name(), 0);
                Intent highScoresActivity = new Intent(Globals.checkersActivity, HighScoreActivity.class);
                Globals.checkersActivity.startActivity(highScoresActivity);
                break;
            case COMPUTER:
                break;
        }
    }
    //__________________________________________
}
