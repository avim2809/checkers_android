package com.example.san4o.checkers;

import android.provider.ContactsContract;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.san4o.checkers.activity.CheckersActivity;
import com.example.san4o.checkers.enums.StoneColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class GameBoard{

    /*GUI Layout HIERARCHY
    * GridLayout -->contains-->RelativeLayout-->contains-->
    * 1.ImageView(RED Glow Background
    * 2.ImageView(GREEN Glow Background
    * 2.ImageView(color background)
    *
    * */
    public static int BOARD_SIZE=8;
    private Stone[][] stones;
    private HashMap<Location,RelativeLayout>locationViewHashMap=null;
    private ArrayList<Stone> activeStones=null;
    private GridLayout gameBoardGrid;
    public final String BACKGROUND_TAG = "image_bg";
    public final String GLOW_TAG = "image_glow";
    public final String STONE_TAG = "stone";
    public final String EAT_GLOW_TAG = "eat_glow";
    private int whiteDrawableCell = R.drawable.white_cell_small;
    private int blackDrawableCell = R.drawable.black_cell_small;
    private int blackCellGlow = R.drawable.black_cell_glow;
    private int eatCellGlow = R.drawable.black_cell_small_eat_glow;
    private CheckersActivity context;
    private ArrayList<ImageView> glowingCells;

    private GameManager gameManager;

    public GameBoard(GameManager gm){
        gameBoardGrid = Globals.gameBoardGrid;
        context = Globals.checkersActivity;
        activeStones = new ArrayList<>();
        gameManager = gm;
        locationViewHashMap = new HashMap<>();
        initBoard();

    }
    //______________________________________
    public void initStonesFromData(Stone[][] stones){
        this.stones = stones;

        for(int i=0;i<BOARD_SIZE;i++){
            for(int j=0;j<BOARD_SIZE;j++){
                if(stones[i][j] != null){
                    createAndAddStone(i,j,stones[i][j].getStoneColor());
                }
            }
        }
    }
    //______________________________________
    private void createAndAddStone(int i,int j,StoneColor color){
        Stone currStoneToAdd;
        int playerStoneDrawable = getDrawableStoneByColor(color);
        int currCellIndex = (gameBoardGrid.getColumnCount() * i) + j;
        RelativeLayout frame = (RelativeLayout) gameBoardGrid.getChildAt(currCellIndex);
        Location location = new Location(j,i);
        ImageView stoneImage =  new ImageView(context);
        stoneImage.setTag(STONE_TAG+":"+location.toString());
        stoneImage.setBackgroundResource(playerStoneDrawable);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(2, 2, 2, 2);
        params.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE);
        stoneImage.setLayoutParams(params);
        currStoneToAdd = new Stone(color,currCellIndex,location);
        currStoneToAdd.setLocation(location);
        //currStoneToAdd.setRelativeLayout(frame);
        addStone(currStoneToAdd);
        frame.addView(stoneImage);
    }
    //______________________________________
    public void initComputerStones(StoneColor stoneColor) {
        //int playerStoneDrawable = getDrawableStoneByColor(stoneColor);
        //Stone currStoneToAdd;
        Stone[][] stonesFromData = DataManager.getInstance().loadGameBoard();
        StoneColor compColor = gameManager.getPlayerComputer().getColor();
        if (stonesFromData != null) {
            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    if (stonesFromData[i][j] != null) {
                        if (stonesFromData[i][j].getStoneColor() == compColor) {
                            createAndAddStone(i, j, compColor);
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    if ((i + j) % 2 == 1) {
                        createAndAddStone(i, j, StoneColor.BLACK);
                    }
                }
            }
        }
    }
    //______________________________________
    public void initUserStones(StoneColor stoneColor) {
        //int playerStoneDrawable = getDrawableStoneByColor(stoneColor);

        Stone[][] stonesFromData = DataManager.getInstance().loadGameBoard();
        StoneColor userColor = gameManager.getPlayerUser().getColor();
        if (stonesFromData != null) {
            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    if(stonesFromData[i][j] != null) {
                        if (stonesFromData[i][j].getStoneColor() == userColor) {
                            createAndAddStone(i, j, userColor);
                        }
                    }
                }
            }
        } else {

            for (int i = BOARD_SIZE - 1; i > BOARD_SIZE - 4; i--) {
                for (int j = BOARD_SIZE - 1; j >= 0; j--) {
                    if ((i + j) % 2 == 1) {
                        createAndAddStone(i, j, StoneColor.WHITE);
                    }
                }
            }
        }
    }
    //______________________________________
    public int getDrawableStoneByColor(StoneColor stoneColor){
        int drawableToRet = 0;
        if(stoneColor == StoneColor.BLACK){
            drawableToRet = R.drawable.stone_black_small;
        }else if (stoneColor == StoneColor.WHITE){
            drawableToRet = R.drawable.stone_white_small;
        }

        return drawableToRet;
    }

    //______________________________________
    private void initBoard(){
        stones = new Stone[BOARD_SIZE][BOARD_SIZE];
        int currColor;
        int currGlow = blackCellGlow;
        for(int i=0;i<BOARD_SIZE;i++){
            if(i%2 ==0) {
                currColor = whiteDrawableCell;
                //currGlow = whiteCellGlow;
            }else {
                currColor = blackDrawableCell;
                //currGlow = blackCellGlow;
            }
            for(int j=0; j<BOARD_SIZE; j++){
                int cellIndex = 0;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    cellIndex = (gameBoardGrid.getColumnCount() * i) + j;
                }

                RelativeLayout frame =  new RelativeLayout(context);
                Location location = new Location(j,i);

                ImageView imageEatGlow = new ImageView(context);
                imageEatGlow.setImageResource(eatCellGlow);
                imageEatGlow.setTag(EAT_GLOW_TAG+":"+location);
                imageEatGlow.setOnClickListener(gameManager);

                ImageView imageGlow = new ImageView(context);
                imageGlow.setImageResource(currGlow);
                imageGlow.setTag(GLOW_TAG+":"+location);
                imageGlow.setOnClickListener(gameManager);

                ImageView imageBG = new ImageView(context);
                imageBG.setImageResource(currColor);
                imageBG.setTag(BACKGROUND_TAG+":"+location);
                imageBG.setOnClickListener(gameManager);

                frame.addView(imageEatGlow);
                frame.addView(imageGlow);
                frame.addView(imageBG);

                if(currColor == whiteDrawableCell){
                    currColor = blackDrawableCell;
                    currGlow = blackCellGlow;
                }else{
                    currColor = whiteDrawableCell;
                    //currGlow = whiteCellGlow;
                }
                frame.setTag(location.toString());
                gameBoardGrid.addView(frame,cellIndex);
                locationViewHashMap.put(location,frame);
            }
        }
    }
    //______________________________________
    private GridLayout getGrid(){
        return this.gameBoardGrid;
    }
    //______________________________________
    private void addStone(Stone stoneToAdd){
        activeStones.add(stoneToAdd);
        int row = stoneToAdd.getLocation().getRow();
        int col = stoneToAdd.getLocation().getCol();
        stones[row][col] = stoneToAdd;
    }
    //______________________________________
    public void changeStoneLocationOnBoard(Location old,Location newLocation){
        Stone stone = stones[old.getRow()][old.getCol()];
        stones[old.getRow()][old.getCol()] = null;
        stones[newLocation.getRow()][newLocation.getCol()] = stone;
    }
    //______________________________________
    public Stone[][] getBoard(){
        return stones;
    }
    //______________________________________
    public Stone getStoneAtLocation(Location location)
    {
        for (Stone stone :activeStones) {
            if (stone.getLocation().equals(location))
            {
                return stone;
            }
        }
        return null;
    }
    //______________________________________
    public ArrayList<Stone> getActiveStones() {
        return activeStones;
    }
    //______________________________________
    public void setActiveStones(ArrayList<Stone> activeStones) {
        this.activeStones = activeStones;
    }
    //______________________________________
    public HashMap<Location, RelativeLayout> getLocationViewHashMap() {
        return locationViewHashMap;
    }
    //______________________________________
    public RelativeLayout getLayoutAtLocation(Location location)
    {
        return locationViewHashMap.get(location);
    }
    //______________________________________
    public Location getLocationFromLayout(RelativeLayout layout) {
        return Location.getLocationfromString(layout.getTag().toString());
    }
    //______________________________________
    public ImageView getStoneImageViewAtLocation(Location location)
    {
        ImageView ret;
        RelativeLayout layout = getLayoutAtLocation(location);
        assert (layout!=null);
        ret = layout.findViewWithTag(STONE_TAG+":"+location.toString());
        return ret;
    }
    //______________________________________
    public GameManager getGameManager() {
        return gameManager;
    }
    //______________________________________
    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    //______________________________________
    public void removeStoneAtLocation(Location location)
    {
        Iterator<Stone> it = activeStones.iterator();
        while (it.hasNext())
        {
            Stone stone = it.next();
            if (stone.getLocation().equals(location))
            {
                it.remove();
                break;
            }

        }
        stones[location.getRow()][location.getCol()] = null;
    }
    //______________________________________
}
