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

    public AnimationManager(){

        glowAnimation=AnimationUtils.loadAnimation(Globals.checkersActivity,R.anim.glow_anim);
        disappearAnimation=  AnimationUtils.loadAnimation(Globals.checkersActivity,R.anim.disappear_anim);
        appearAnimation= AnimationUtils.loadAnimation(Globals.checkersActivity,R.anim.appear_anim);
        eatAnimation = AnimationUtils.loadAnimation(Globals.checkersActivity,R.anim.eat_anim);

//        glowAnimation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) { }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                animation.cancel();
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) { }
//        });

//        disappearAnimation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) { }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                animation.cancel();
//                SystemClock.sleep(1000);
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) { }
//        });
//
//        appearAnimation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) { }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                animation.cancel();
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) { }
//        });
//
//        eatAnimation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) { }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                animation.cancel();
//                SystemClock.sleep(1000);
//                Log.i("eat anim", "onAnimationEnd: ");
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) { }
//        });
    }
    //___________________________________________________
}
