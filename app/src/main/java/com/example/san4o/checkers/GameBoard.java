package com.example.san4o.checkers;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    * 1.ImageView(Cell BackGround)
    * 2.ImageView(Glow Background)
    *
    * */
    public static int BOARD_SIZE=8;
    private Stone[][] stones;
    private HashMap<Location,RelativeLayout>locationViewHashMap=null;
    private HashMap<RelativeLayout,Location>View2locationHashMap=null;
    private ArrayList<Stone> activeStones=null;
    private GridLayout gameBoardGrid;
    public final String BACKGROUND_TAG = "image_bg";
    public final String GLOW_TAG = "image_glow";
    public final String STONE_TAG = "stone";
    private int whiteDrawableCell = R.drawable.white_cell_small;
    private int blackDrawableCell = R.drawable.black_cell_small;
    private int whiteCellGlow = R.drawable.white_cell_glow;
    private int blackCellGlow = R.drawable.black_cell_glow;
    private CheckersActivity context;
    private ArrayList<ImageView> glowingCells;
    private Animation glowAnimation;
    private GameManager gameManager;

    public GameBoard(GameManager gm){
        gameBoardGrid = Globals.gameBoardGrid;
        context = Globals.checkersActivity;
        activeStones = new ArrayList<>();
        initGameProps();
        gameManager = gm;
        locationViewHashMap = new HashMap<>();
        initBoard();
    }
    //______________________________________
    private void initGameProps(){
        glowingCells = new ArrayList<>();
        glowAnimation = AnimationUtils.loadAnimation(Globals.checkersActivity,R.anim.glow_anim);
    }

    //______________________________________
    public void initComputerStones(StoneColor stoneColor){
        int playerStoneDrawable = getDrawableStoneByColor(stoneColor);
        Stone currStoneToAdd;

        for(int i=0;i<3;i++){
            for(int j=0;j<BOARD_SIZE;j++){
                if((i+j)%2==1){
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
                    currStoneToAdd = new Stone(StoneColor.BLACK,currCellIndex,location);
                    currStoneToAdd.setLocation(location);
                    currStoneToAdd.setRelativeLayout(frame);
                    addStone(currStoneToAdd);
                    //listener.addStone(currStoneToAdd);
                    frame.addView(stoneImage);
                }
            }
        }
    }
    //______________________________________
    public void initUserStones(StoneColor stoneColor){
        int playerStoneDrawable = getDrawableStoneByColor(stoneColor);
        Stone currStoneToAdd;

        for(int i=BOARD_SIZE-1;i>BOARD_SIZE-4;i--){
            for(int j=BOARD_SIZE-1;j>=0;j--){
                if((i+j)%2==1){
                    int currCellIndex = (gameBoardGrid.getColumnCount() * i) + j;
                    RelativeLayout frame = (RelativeLayout) gameBoardGrid.getChildAt(currCellIndex);
                    Location location = new Location(j,i);
                    ImageView stoneImage =  new ImageView(context);

                    stoneImage.setTag(STONE_TAG+":"+location.toString());
                    stoneImage.setBackgroundResource(playerStoneDrawable);
                    stoneImage.setOnClickListener(gameManager);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(2, 2, 2, 2);
                    params.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE);
                    stoneImage.setLayoutParams(params);
                    currStoneToAdd = new Stone(StoneColor.WHITE,currCellIndex,location);
                    currStoneToAdd.setLocation(location);
                    currStoneToAdd.setRelativeLayout(frame);
                    addStone(currStoneToAdd);
                    //listener.addStone(currStoneToAdd);
                    frame.addView(stoneImage);
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

    public int getDrawableCellGlowCellByColor(StoneColor stoneColor)
    {
        if (stoneColor == StoneColor.BLACK)
            return blackCellGlow;
        return whiteCellGlow;
    }

    public int getDrwableCellByColor(StoneColor stoneColor)
    {
        if (stoneColor == StoneColor.BLACK)
            return blackDrawableCell;
        return whiteDrawableCell;
    }
    //______________________________________
    private void initBoard(){
        stones = new Stone[BOARD_SIZE][BOARD_SIZE];
        int currColor;
        int currGlow;
        for(int i=0;i<BOARD_SIZE;i++){
            if(i%2 ==0) {
                currColor = whiteDrawableCell;
                currGlow = whiteCellGlow;
            }else {
                currColor = blackDrawableCell;
                currGlow = blackCellGlow;
            }
            for(int j=0; j<BOARD_SIZE; j++){
                int cellIndex = (gameBoardGrid.getColumnCount() * i) + j;
                RelativeLayout frame =  new RelativeLayout(context);
                Location location = new Location(j,i);
                ImageView imageGlow = new ImageView(context);
                imageGlow.setImageResource(currGlow);
                imageGlow.setTag(GLOW_TAG+":"+location);
                ImageView imageBG = new ImageView(context);
                ImageView imageBG1 = new ImageView(context);
                imageBG.setImageResource(currColor);
                imageBG1.setBackground(imageBG.getDrawable());
                imageBG.setTag(BACKGROUND_TAG);
                imageBG1.setTag(BACKGROUND_TAG+":"+location);
                imageBG.setOnClickListener(gameManager);
                imageBG1.setOnClickListener(gameManager);
                imageGlow.setOnClickListener(gameManager);
                //frame.setBackgroundResource(currColor);
                imageGlow.setVisibility(View.INVISIBLE);
                frame.addView(imageBG1);
                //frame.addView(imageGlow);
                if(currColor == whiteDrawableCell){
                    currColor = blackDrawableCell;
                    currGlow = blackCellGlow;
                }else{
                    currColor = whiteDrawableCell;
                    currGlow = whiteCellGlow;
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
        int row = stoneToAdd.getLocation().getX();
        int col = stoneToAdd.getLocation().getY();
        stones[row][col] = stoneToAdd;
    }
    //______________________________________
    public void changeStoneLocationOnBoard(Location old,Location newLocation){
        Stone stone = stones[old.getX()][old.getY()];
        stones[old.getX()][old.getY()] = null;
        stones[newLocation.getY()][newLocation.getY()] = stone;
    }
    //______________________________________
    public Stone[][] getBoard(){
        return stones;
    }

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

    public ArrayList<Stone> getActiveStones() {
        return activeStones;
    }

    public void setActiveStones(ArrayList<Stone> activeStones) {
        this.activeStones = activeStones;
    }

    public HashMap<Location, RelativeLayout> getLocationViewHashMap() {
        return locationViewHashMap;
    }

    public RelativeLayout getLayoutatLocation(Location location)
    {
        return locationViewHashMap.get(location);

    }

    public Location getLocationFromLayout(RelativeLayout layout)
    {
        return Location.getLocationfromString(layout.getTag().toString());
    }
    public ImageView getStoneImageViewAtLocation(Location location)
    {
        ImageView ret= null;
        RelativeLayout layout = getLayoutatLocation(location);
        assert (layout!=null);
        ret = layout.findViewWithTag(STONE_TAG+":"+location.toString());
        return ret;
    }
    public GameManager getGameManager() {
        return gameManager;
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void onBecomingKing(Stone stone)
    {
        //TODO: add animation for becoming king
    }

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
        stones[location.getX()][location.getY()] = null;
    }

    //______________________________________
    public void glowCells(ImageView cellToGlow){
        cellToGlow.startAnimation(glowAnimation);
        glowingCells.add(cellToGlow);
    }
    //______________________________________
    public void glowCells(ArrayList<ImageView> cellToGlowArray){
        for(int i=0;i<cellToGlowArray.size();i++){
            cellToGlowArray.get(i).startAnimation(glowAnimation);
            glowingCells.add(cellToGlowArray.get(i));
        }
    }
    //______________________________________
    public void removeGlow(){
        for(int i=0;i<glowingCells.size();i++){
            glowingCells.get(i).clearAnimation();
        }
    }
    //______________________________________
}
