package com.example.san4o.checkers.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import com.example.san4o.checkers.DataManager;
import com.example.san4o.checkers.GameManager;
import com.example.san4o.checkers.Globals;
import com.example.san4o.checkers.R;

public class CheckersActivity extends Activity implements DialogInterface.OnClickListener,View.OnClickListener{

    private String userName ;
    private GameManager gameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkers);
        Globals.checkersActivity = this;
        userName = getIntent().getExtras().get("name").toString();
        initGameSettings();
    }

    //___________________________________________
    private void initGameSettings(){
        GridLayout gridLayout = findViewById(R.id.game_board);
        Globals.gameBoardGrid = gridLayout;
        Globals.checkersActivity = this;
        gameManager = new GameManager();
        gameManager.initGame();
    }
    //___________________________________________
    @Override
    public void onClick(View view) {
        //if(view instanceof ImageView){
        //gameManager.clickOnStone(view);
        //}
    }
    //___________________________________________
    @Override
    protected void onPause() {
        super.onPause();
        DataManager.getInstance().saveData(userName,"name");

        //gameManager.saveData();
    }
    //___________________________________________


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AlertDialog.Builder backButtonDialog = new AlertDialog.Builder(this);
        backButtonDialog.setTitle("Confirm Exit").setMessage("Would you like to save the gam?").setPositiveButton("Yes", this).setNegativeButton("No", this).setNeutralButton("Stay in game",this).show();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if(i == dialogInterface.BUTTON_POSITIVE){
            //DataManager.getInstance().saveData();
            Toast.makeText(this, "yes to save", Toast.LENGTH_SHORT).show();
            finish();

        }else if (i == dialogInterface.BUTTON_NEGATIVE){
            Toast.makeText(this, "not to save", Toast.LENGTH_SHORT).show();
            finish();
        }else if (i == dialogInterface.BUTTON_NEUTRAL){
            Toast.makeText(this, "stay in game ", Toast.LENGTH_SHORT).show();
        }
    }
}
