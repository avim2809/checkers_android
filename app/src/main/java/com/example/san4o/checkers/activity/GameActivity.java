package com.example.san4o.checkers.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.example.san4o.checkers.GameManager;
import com.example.san4o.checkers.R;

public class GameActivity extends Activity implements View.OnClickListener {
    private String userName ;
    private GameManager gameManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        userName= getIntent().getExtras().get("name").toString();
        initGameSettings();
    }
    //___________________________________________
    private void initGameSettings(){
        GridLayout gridLayout = findViewById(R.id.game_board);
        gameManager = new GameManager(gridLayout,this);
        gameManager.initGame();
    }
    //___________________________________________
    @Override
    public void onClick(View view) {
        if(view instanceof ImageView){
            gameManager.clickOnStone(view);
        }
    }
    //___________________________________________
}
