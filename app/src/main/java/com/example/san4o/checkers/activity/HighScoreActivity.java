package com.example.san4o.checkers.activity;

import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.san4o.checkers.DataManager;
import com.example.san4o.checkers.Globals;
import com.example.san4o.checkers.HighScore;
import com.example.san4o.checkers.R;

import java.util.ArrayList;

public class HighScoreActivity extends AppCompatActivity implements View.OnClickListener{

    private TableLayout highScoresTableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayShowHomeEnabled(true);
        ab.setIcon(R.drawable.icon_mini);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        Globals.highScoreActivity = this;

        highScoresTableLayout = findViewById(R.id.high_scores_table);
        Button backBtn = findViewById(R.id.back);
        backBtn.setOnClickListener(this);
        initHighScoresTable();
    }
    //_________________________________________________
    private void initHighScoresTable(){
        ArrayList<HighScore> highScores = DataManager.getInstance().loadHighScores();

        TableRow firstTableRow = new TableRow(Globals.highScoreActivity);
        TextView firstName = new TextView(Globals.highScoreActivity);
        firstName.setText("Name");
        TextView firstScore = new TextView(Globals.highScoreActivity);
        firstScore.setText("Score");
        firstTableRow.addView(firstName);
        firstTableRow.addView(firstScore);
        highScoresTableLayout.addView(firstTableRow);

        TableRow currentTableRow;
        TextView currentName;
        TextView currentScore;
        for(int i=0;i<highScores.size();i++){
            currentTableRow = new TableRow(Globals.highScoreActivity);
            currentName = new TextView(Globals.highScoreActivity);
            currentName.setText(highScores.get(i).getName());
            currentScore = new TextView(Globals.highScoreActivity);
            currentScore.setText(String.valueOf(highScores.get(i).getScore()));
            currentTableRow.addView(currentName);
            currentTableRow.addView(currentScore);
            highScoresTableLayout.addView(currentTableRow);
        }
    }
    //_________________________________________________
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.back){
            finish();
        }
    }
    //_________________________________________________
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game_action_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    //_________________________________________________
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.new_game){
            Toast.makeText(Globals.highScoreActivity, "click on new game", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.share){
            Toast.makeText(Globals.highScoreActivity, "click on share", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
    //_________________________________________________
}
