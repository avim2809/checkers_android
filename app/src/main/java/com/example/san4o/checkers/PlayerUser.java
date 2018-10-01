//package com.example.san4o.checkers;
//
//import java.util.ArrayList;
//import java.util.Set;
//
//public class PlayerUser extends PlayerRole {
//    private Stone last_clicked_stone =null;
//    private boolean waiting_for_movement = false;
//    private Set<Location> lastvalidMoves = null;
//    public PlayerUser(GameBoard gameBoard){
//        activeStones = new ArrayList<>();
//        lostStones = new ArrayList<>();
//        this.gameBoard = gameBoard;
//    }
//    //_____________________________________________
//
////    @Override
////    public void onClick(View view) {
////        if(view instanceof ImageView){
////            Stone clickedStone = findClickedStone((ImageView) view);
////            if(clickedStone != null){
////                Log.i("onClick: PlayerUser:", ""+clickedStone.toString());
////                Set<Location> validMoves = GameRules.getValidMoves(clickedStone,gameBoard);
////                Log.i("onClick: PlayerUser:", "valid moves:"+validMoves.toString());
////                if (!validMoves.isEmpty())
////                {
////                    if (last_clicked_stone != null)
////                    {
////                        // user clicked on same stone or different stone before actually moving
////                        if (lastvalidMoves!= null&& !clickedStone.equals(last_clicked_stone))
////                        {
////                            //user clicked on different stone: clear previous marked cells mark new cells
////                            markPossibleMovesOnBoard(lastvalidMoves,true);
////                            markPossibleMovesOnBoard(validMoves,false);
////
////                        }
////
////
////                    }
////                    else
////                    {
////                        markPossibleMovesOnBoard(validMoves,false);
////                        lastvalidMoves = validMoves;
////
////                    }
////
////                    if (!waiting_for_movement)
////                    {
////                        waiting_for_movement = true;
////
////                    }
////
////                }
////                else {
////                    //toast invalid move
////                    new StyleableToast
////                            .Builder(Globals.checkersActivity)
////                            .text(Globals.checkersActivity.getResources().getString(R.string.invalid_move))
////                            .textColor(android.graphics.Color.WHITE)
////                            .backgroundColor(android.graphics.Color.BLUE)
////                            .show();
////
////                }
////
////            }
////            else
////            {
////                if (waiting_for_movement)
////                {
////                    //player clicked on empty frame - wants to move somewhere
////                    last_clicked_stone = null;
////
////                }
////
////            }
////
////
////
////        }
////    }
//
////    //mark all possible moves on board
////    private void markPossibleMovesOnBoard(Set<Location> locations,boolean clear)
////    {
////        for (Location location:locations)
////        {
////            RelativeLayout grid_frame = gameBoard.getLayoutAtLocation(location);
////            if (grid_frame!= null)
////            {
////                ImageView frameImage = grid_frame.findViewWithTag(gameBoard.BACKGROUND_TAG);
////                if (!clear) {frameImage.setVisibility(View.INVISIBLE);}
////                else {frameImage.setVisibility(View.VISIBLE);}
////
////            }
////        }
////    }
//
//
//
//    //_____________________________________________
//    //____________________________________________
//    public String getName() {
//        return name;
//    }
//    //____________________________________________
//    public void setName(String name) {
//        this.name = name;
//    }
//    //____________________________________________
//}
