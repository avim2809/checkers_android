package com.example.san4o.checkers.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.san4o.checkers.ConfettiManager;
import com.example.san4o.checkers.Globals;
import com.example.san4o.checkers.HighScore;
import com.example.san4o.checkers.R;
import com.example.san4o.checkers.StyledTextView;

import org.sk.PrettyTable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HighScoreActivity extends AppCompatActivity implements View.OnClickListener {
    private ConfettiManager confetti;
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

        confetti = new ConfettiManager();

        if (Globals.currHighScore != null) {
            confetti.startConfetti();
        }

        initHighScoresTable();
    }

    //_________________________________________________
    @SuppressLint("ResourceAsColor")
    private void initHighScoresTable() {
        ArrayList<HighScore> highScores = Globals.highScoreTable;

        if (highScores != null) {
            Collections.sort(highScores, new Comparator<HighScore>() {
                @Override
                public int compare(HighScore high1, HighScore high2) {
                    return (high1.getScore() - high2.getScore());
                }
            });

            TableRow firstTableRow = new TableRow(this);
            firstTableRow.setPadding(5, 5, 5, 5);
            firstTableRow.setBackgroundColor(R.color.colorAccent);

            Resources res = getResources();

            TextView firstName = createTextView(res.getString(R.string.name));
            firstName.setTextSize(20);

            TextView firstScore = createTextView(res.getString(R.string.Moves));
            firstScore.setTextSize(20);

            firstTableRow.addView(firstName);
            firstTableRow.addView(firstScore);
            highScoresTableLayout.addView(firstTableRow);

            TableRow currentTableRow;
            TextView currentName = null;
            TextView currentScore = null;
            HighScore currHighScore;
            for (int i = 0; i < highScores.size(); i++) {
                currentTableRow = new TableRow(this);
                currHighScore = highScores.get(i);

                if (currHighScore.getID() != null) {
                    if (Globals.currHighScore != null) {
                        if (currHighScore.getID().equals(Globals.currHighScore.getID())) {
                            currentName = createStyledTextView(currHighScore.getName());
                            currentScore = createStyledTextView(String.valueOf(currHighScore.getScore()));
                        } else {
                            currentName = createTextView(currHighScore.getName());
                            currentScore = createTextView(String.valueOf(currHighScore.getScore()));
                        }
                    } else {
                        currentName = createTextView(currHighScore.getName());
                        currentScore = createTextView(String.valueOf(currHighScore.getScore()));
                    }
                } else {
                    currentName = createTextView(currHighScore.getName());
                    currentScore = createTextView(String.valueOf(currHighScore.getScore()));
                }
                if (currentName != null && currentScore != null) {
                    currentName.setTextColor(R.color.colorPrimaryDark);
                    currentScore.setTextColor(R.color.colorPrimaryDark);
                }

                currentTableRow.addView(currentName);
                currentTableRow.addView(currentScore);
                highScoresTableLayout.addView(currentTableRow);
            }
        }
    }

    //_________________________________________________
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    //_________________________________________________
    private TextView createTextView(String text) {
        TextView textToRet = new TextView(this);
        textToRet.setGravity(Gravity.CENTER);
        textToRet.setTypeface(textToRet.getTypeface(), Typeface.BOLD);
        textToRet.setPadding
                (2, 2, 2, 2);
        textToRet.setTextSize(16);
        textToRet.setText(text);

        return textToRet;
    }

    private StyledTextView createStyledTextView(String text) {
        StyledTextView textToRet = new StyledTextView(this, null);
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
            Intent mainIntent = new Intent(this, MainActivity.class);
            startActivity(mainIntent);
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
        String message = "Check out the high score table: \n";
        PrettyTable table = new PrettyTable("Name","Score");
        ArrayList<HighScore> highScoreArrayList = Globals.highScoreTable;
        if (highScoreArrayList != null && highScoreArrayList.size() > 0) {
            for (int i = 0; i < highScoreArrayList.size(); i++) {
                table.addRow(highScoreArrayList.get(i).getName(),highScoreArrayList.get(i).getScore()+"");
            }
            message+= table.toString();
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, message);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
    }
    //_________________________________________________

    private static class ConfettiSample {
        final int nameResId;
        final Class<? extends Activity> targetActivityClass;

        private ConfettiSample(int nameResId, Class<? extends Activity> targetActivityClass) {
            this.nameResId = nameResId;
            this.targetActivityClass = targetActivityClass;
        }
    }
}
