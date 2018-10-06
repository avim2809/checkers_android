package com.example.san4o.checkers;

import android.os.SystemClock;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class AnimationManager {

    public  Animation glowAnimation;
    public  Animation disappearAnimation;
    public  Animation appearAnimation ;
    public  Animation eatAnimation ;
    public Animation jumpAnimation;

    public AnimationManager(){

        glowAnimation=AnimationUtils.loadAnimation(Globals.checkersActivity,R.anim.glow_anim);
        disappearAnimation=  AnimationUtils.loadAnimation(Globals.checkersActivity,R.anim.disappear_anim);
        appearAnimation= AnimationUtils.loadAnimation(Globals.checkersActivity,R.anim.appear_anim);
        eatAnimation = AnimationUtils.loadAnimation(Globals.checkersActivity,R.anim.eat_anim);
        jumpAnimation = AnimationUtils.loadAnimation(Globals.checkersActivity,R.anim.jump_anim);
    }
    //___________________________________________________
}
