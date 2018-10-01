package com.example.san4o.checkers.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.san4o.checkers.DataManager;
import com.example.san4o.checkers.Globals;
import com.example.san4o.checkers.HighScore;
import com.example.san4o.checkers.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HighScoreActivity extends AppCompatActivity implements View.OnClickListener {

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
    @SuppressLint("ResourceAsColor")
    private void initHighScoresTable() {
        //ArrayList<HighScore> highScores = DataManager.getInstance().loadHighScores();
        ArrayList<HighScore> highScores = Globals.highScoreTable;

        if (highScores != null) {

            Collections.sort(highScores,new Comparator<HighScore>() {

                @Override
                public int compare(HighScore high1, HighScore high2) {
                    return (high2.getScore()-high1.getScore());
                }
            });

            TableRow firstTableRow = new TableRow(this);
            firstTableRow.setPadding(5, 5, 5, 5);
            firstTableRow.setBackgroundColor(R.color.colorAccent);

            Resources res = getResources();

            TextView firstName = createTextView(res.getString(R.string.name));
            firstName.setTextSize(20);

            TextView firstScore = createTextView(res.getString(R.string.score));
            firstScore.setTextSize(20);

            firstTableRow.addView(firstName);
            firstTableRow.addView(firstScore);
            highScoresTableLayout.addView(firstTableRow);

            TableRow currentTableRow;
            TextView currentName;
            TextView currentScore;
            for (int i = 0; i < highScores.size(); i++) {
                currentTableRow = new TableRow(this);

                currentName = createTextView(highScores.get(i).getName());
                currentName.setTextColor(R.color.colorPrimaryDark);

                currentScore = createTextView(String.valueOf(highScores.get(i).getScore()));
                currentScore.setTextColor(R.color.colorPrimaryDark);

                currentTableRow.addView(currentName);
                currentTableRow.addView(currentScore);
                highScoresTableLayout.addView(currentTableRow);
            }
        }
    }

    //_________________________________________________
    private TextView createTextView(String text) {
        TextView textToRet = new TextView(this);
        textToRet.setGravity(Gravity.CENTER);
        textToRet.setTypeface(textToRet.getTypeface(), Typeface.BOLD);
        textToRet.setPadding(2, 2, 2, 2);
        textToRet.setTextSize(16);
        textToRet.setText(text);

        return textToRet;
    }

    //_________________________________________________
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back) {
            finish();
        }
    }

    //_________________________________________________
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game_action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //_________________________________________________
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.new_game) {
            newGameOption();
        } else if (item.getItemId() == R.id.share) {
            shareHighScores();
            Toast.makeText(Globals.highScoreActivity, "click on share", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
    //_________________________________________________

    private void newGameOption() {
        Intent newGame = new Intent(this, CheckersActivity.class);
        startActivity(newGame);
    }

    //_________________________________________________
    private void shareHighScores() {
        String message = "Check out the high score: \n";
        ArrayList<HighScore> highScoreArrayList = Globals.highScoreTable;
        if (highScoreArrayList != null && highScoreArrayList.size() > 0) {
            for (int i = 0; i < highScoreArrayList.size(); i++) {
                message.concat(highScoreArrayList.get(i).getName() + " " + highScoreArrayList.get(i).getScore() + "\n");
            }
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, message);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
    }
    //_________________________________________________
}
