package com.example.san4o.checkers;

import android.media.AudioManager;
import android.media.SoundPool;

public class SoundsManager {
    private static SoundPool soundPoool;
    private static int eatSound;
    private static int eatenSound;
    private static int winSound;
    private static int moveSound;
    private static int kingSound;
    private static int wrongMove;
    private static int lossSound;

    public SoundsManager() {
        soundPoool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);

        eatSound = soundPoool.load(Globals.checkersActivity, R.raw.eat_sound, 1);
        eatenSound = soundPoool.load(Globals.checkersActivity, R.raw.eaten_sound, 1);
        winSound = soundPoool.load(Globals.checkersActivity, R.raw.win, 1);
        moveSound = soundPoool.load(Globals.checkersActivity, R.raw.move, 1);
        kingSound = soundPoool.load(Globals.checkersActivity, R.raw.king, 1);
        wrongMove = soundPoool.load(Globals.checkersActivity, R.raw.wrong_move, 1);
        lossSound = soundPoool.load(Globals.checkersActivity, R.raw.loss, 1);
    }

    //__________________________________________________________
    public void playEatSound() {
        soundPoool.play(eatSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    //__________________________________________________________
    public void playEatenSound() {
        soundPoool.play(eatenSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    //__________________________________________________________
    public void playWinSound() {
        soundPoool.play(winSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    //__________________________________________________________
    public void playMoveSound() {
        soundPoool.play(moveSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    //__________________________________________________________
    public void playKingSound() {
        soundPoool.play(kingSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    //__________________________________________________________
    public void playWrongMoveSound() {
        soundPoool.play(wrongMove, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    //__________________________________________________________
    public void playLossSound() {
        soundPoool.play(lossSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }
    //__________________________________________________________
}
