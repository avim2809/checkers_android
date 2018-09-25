package com.example.san4o.checkers.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.san4o.checkers.DataManager;
import com.example.san4o.checkers.Globals;
import com.example.san4o.checkers.R;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

public class MainActivity extends Activity implements View.OnClickListener {

    private EditText nameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Globals.mainActivity = this;
        DataManager.getInstance().initSharedPreferences(this.getApplicationContext());



        nameEditText = findViewById(R.id.name);
        Button rules = findViewById(R.id.rules);
        Button highScore = findViewById(R.id.highScores);
        Button about = findViewById(R.id.about);
        Button start = findViewById(R.id.start);

        start.setOnClickListener(this);
        rules.setOnClickListener(this);
        highScore.setOnClickListener(this);
        about.setOnClickListener(this);


        new StyleableToast
                .Builder(Globals.mainActivity)
                .text("Welcome!")
                .textColor(android.graphics.Color.WHITE)
                .backgroundColor(android.graphics.Color.BLUE)
                .show();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.rules){
            clickOnRules();
        } else if (view.getId() == R.id.highScores){
            clickOnHighScore();
        }else if (view.getId() == R.id.about){
            clickOnAbout();
        }else if (view.getId() == R.id.start){
            clickOnStart();
        }
    }

    private void clickOnHighScore() {
    }

    private void clickOnRules(){
        Intent rulesIntent = new Intent(this,RulesActivity.class);
        startActivity(rulesIntent);
    }

//    private void clickOnHighScore(){
//        Intent highScoreIntent = new Intent(this,HighScoreActivity.class);
//        startActivity(highScoreIntent);
//    }

    private void clickOnAbout(){
        Intent aboutIntent = new Intent(this,AboutAvtivity.class);
        startActivity(aboutIntent);
    }

    private void clickOnStart(){
        String userName = nameEditText.getText().toString();
        if(!userName.equals("")){
            Intent gameIntent = new Intent(this,CheckersActivity.class);
            gameIntent.putExtra("name",userName);
            startActivity(gameIntent);
        }
    }
}
