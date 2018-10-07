package com.example.san4o.checkers.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.san4o.checkers.DataManager;
import com.example.san4o.checkers.GameManager;
import com.example.san4o.checkers.Globals;
import com.example.san4o.checkers.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class CheckersActivity extends AppCompatActivity implements DialogInterface.OnClickListener,View.OnClickListener {

    private String userName;
    private GameManager gameManager;
    public TextView BlackScore, WhiteScore;
    public ImageView BlackImg,WhiteImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        ActionBar ab = getSupportActionBar();
        ab.hide();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_checkers);
        Globals.checkersActivity = this;

        Button popUpMenuBtn = findViewById(R.id.pop_up_menu);
        popUpMenuBtn.setOnClickListener(this);
        BlackScore = findViewById(R.id.black_score);
        WhiteScore = findViewById(R.id.white_score);
        BlackImg   = findViewById(R.id.black_img);
        WhiteImg = findViewById(R.id.white_img);
        if (getIntent().hasExtra("name")) {
            userName = getIntent().getExtras().get("name").toString();
        }
        initGameSettings();
    }

    //___________________________________________
    private void initGameSettings() {
        Globals.gameBoardGrid = findViewById(R.id.game_board);
        gameManager = new GameManager();
        gameManager.initGame();
        gameManager.getPlayerUser().setName(userName);
        Globals.userStoneColor = gameManager.getPlayerUser().getColor();
        Globals.computerStoneColor = gameManager.getPlayerComputer().getColor();

    }

    public void initScores(){
        BlackScore.setText(String.valueOf(gameManager.getPlayerComputer().getStonesNum()) + "");
        WhiteScore.setText(String.valueOf(gameManager.getPlayerUser().getStonesNum()) + "");
    }


    //___________________________________________
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        AlertDialog.Builder backButtonDialog = new AlertDialog.Builder(this);
        backButtonDialog.setTitle(R.string.confirm).setMessage(R.string.save);
        backButtonDialog.setPositiveButton(R.string.yes, this);
        backButtonDialog.setNegativeButton(R.string.no, this);
        backButtonDialog.setNeutralButton(R.string.stay_in, this);
        backButtonDialog.show();
    }

    //___________________________________________
    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if (i == dialogInterface.BUTTON_POSITIVE) {
            DataManager.getInstance().saveGameBoard(gameManager.getGameBoard().getBoard());
            DataManager.getInstance().saveMovesCount(gameManager.getPlayerUser().getMovesCount());
            finish();
        } else if (i == dialogInterface.BUTTON_NEGATIVE) {
            DataManager.getInstance().saveGameBoard(null);
            DataManager.getInstance().saveMovesCount(0);
            finish();
        } else if (i == dialogInterface.BUTTON_NEUTRAL) {
            return;
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(View view) {
        createPopUpMenu(view);
    }
    //___________________________________________

    private void createPopUpMenu(View view){
        Context wrapper = new ContextThemeWrapper(this, R.style.MyPopupMenu);

         PopupMenu popUpMenu = new PopupMenu(wrapper,view);
        try {
            Field[] fields = popUpMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popUpMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(Globals.gameVolume == true){
            getMenuInflater().inflate(R.menu.pop_up_menu_mute,popUpMenu.getMenu());
        }else{
            getMenuInflater().inflate(R.menu.pop_up_menu_volume,popUpMenu.getMenu());
        }

        popUpMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.new_game:
                        Intent refresh = new Intent(CheckersActivity.this, CheckersActivity.class);
                        DataManager.getInstance().saveData(null,"game_board_data");
                        startActivity(refresh);
                        finish();
                        break;
                    case R.id.volume:
                        if(Globals.gameVolume == true){
                            Globals.gameVolume = false;
                        } else {
                            Globals.gameVolume = true;
                        }
                        DataManager.getInstance().saveVolume(Globals.gameVolume);
                        break;
                }
                return false;
            }
        });


        popUpMenu.show();
    }
}
