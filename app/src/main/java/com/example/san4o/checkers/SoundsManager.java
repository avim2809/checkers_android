package com.example.san4o.checkers;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

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
        if (Build.VERSION.SDK_INT <21)
            soundPoool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        else
        {
            AudioAttributes attributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build();
            soundPoool = new SoundPool.Builder().setMaxStreams(2).setAudioAttributes(attributes).build();

        }

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
        if(Globals.gameVolume) {
            soundPoool.play(eatSound, 1.0f, 1.0f, 1, 0, 1.0f);
        }
    }

    //__________________________________________________________
    public void playEatenSound() {
        if(Globals.gameVolume) {
            soundPoool.play(eatenSound, 1.0f, 1.0f, 1, 0, 1.0f);
        }
    }

    //__________________________________________________________
    public void playWinSound() {
        if(Globals.gameVolume) {
            soundPoool.play(winSound, 1.0f, 1.0f, 1, 0, 1.0f);
        }
    }

    //__________________________________________________________
    public void playMoveSound() {
        if(Globals.gameVolume) {
            soundPoool.play(moveSound, 1.0f, 1.0f, 1, 0, 1.0f);
        }
    }

    //__________________________________________________________
    public void playKingSound() {
        if(Globals.gameVolume) {
            soundPoool.play(kingSound, 1.0f, 1.0f, 1, 0, 1.0f);
        }
    }

    //__________________________________________________________
    public void playWrongMoveSound() {
        if(Globals.gameVolume) {
            soundPoool.play(wrongMove, 1.0f, 1.0f, 1, 0, 1.0f);
        }
    }

    //__________________________________________________________
    public void playLossSound() {
        if(Globals.gameVolume) {
            soundPoool.play(lossSound, 1.0f, 1.0f, 1, 0, 1.0f);
        }
    }
    //__________________________________________________________
}
