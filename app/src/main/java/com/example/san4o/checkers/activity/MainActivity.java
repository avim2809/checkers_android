package com.example.san4o.checkers.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;

import com.example.san4o.checkers.DataManager;
import com.example.san4o.checkers.Globals;
import com.example.san4o.checkers.HighScore;
import com.example.san4o.checkers.R;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener {

    private EditText nameEditText;

    private Button rules;
    private Button highScore;
    private Button about;
    private Button start;
    private String name;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Globals.mainActivity = this;
        DataManager.getInstance().initSharedPreferences(this.getApplicationContext());


        nameEditText = findViewById(R.id.name);

        initData();
        makeEntrance();
        start.setOnClickListener(this);
        rules.setOnClickListener(this);
        highScore.setOnClickListener(this);
        about.setOnClickListener(this);


        new StyleableToast
                .Builder(Globals.mainActivity)
                .text("Welcome " + nameEditText.getText().toString() + "!")
                .textColor(android.graphics.Color.WHITE)
                .backgroundColor(android.graphics.Color.BLUE)
                .show();
    }

    private void initData(){
        name = DataManager.getInstance().loadPlayerName();
        if(!name.equals("")){
            nameEditText.setText(name.toString());
        }

        ArrayList<HighScore> highScoreArrayList = DataManager.getInstance().loadHighScores();
        if(highScoreArrayList != null){
            Globals.highScoreTable = highScoreArrayList;
        }
    }
    //_______________________________________________
    private void makeEntrance() {
        int[] location = new int[2];
        rules = findViewById(R.id.rules);
        rules.getLocationInWindow(location);
        Log.i("rules x loc", String.valueOf(location[0]));
        highScore = findViewById(R.id.highScores);
        about = findViewById(R.id.about);
        start = findViewById(R.id.start);


        start.getLocationInWindow(location);
        startAnim(start, location.clone(), 100);

        highScore.getLocationInWindow(location);
        startAnim(highScore, location.clone(), 300);


        startAnim(rules, location.clone(), 500);

        about.getLocationInWindow(location);
        startAnim(about, location.clone(), 700);
    }
    //_______________________________________________
    private void startAnim(View view, int[] location, int delay) {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setVisibility(View.VISIBLE);
                TranslateAnimation anim = new TranslateAnimation(-700, location[0], location[1], location[1]);
                anim.setRepeatCount(0);
                anim.setDuration(2000);
                anim.setInterpolator(new BounceInterpolator());
                view.startAnimation(anim);
            }
        }, delay);
    }
    //_______________________________________________
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.rules) {
            clickOnRules();
        } else if (view.getId() == R.id.highScores) {
            clickOnHighScore();
        } else if (view.getId() == R.id.about) {
            clickOnAbout();
        } else if (view.getId() == R.id.start) {
            clickOnStart();
        }
    }
    //_______________________________________________

    private void clickOnRules() {
        Intent rulesIntent = new Intent(this, RulesActivity.class);
        startActivity(rulesIntent);
    }
    //_______________________________________________
    private void clickOnHighScore(){
        Intent highScoreIntent = new Intent(this,HighScoreActivity.class);
        startActivity(highScoreIntent);
    }
    //_______________________________________________
    private void clickOnAbout() {
        Intent aboutIntent = new Intent(this, AboutAvtivity.class);
        startActivity(aboutIntent);
    }
    //_______________________________________________
    private void clickOnStart() {
        String userName = nameEditText.getText().toString();
        if (!userName.equals("")) {
            DataManager.getInstance().saveName(nameEditText.getText().toString());
            Intent gameIntent = new Intent(this, CheckersActivity.class);
            gameIntent.putExtra("name", userName);
            startActivity(gameIntent);
        }
    }
    //_______________________________________________
}
