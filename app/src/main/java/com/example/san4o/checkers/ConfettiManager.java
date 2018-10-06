package com.example.san4o.checkers;

import android.view.View;

import com.plattysoft.leonids.ParticleSystem;

public class ConfettiManager {
    public ConfettiManager(){

    }

    public void startConfetti(){
        new ParticleSystem(Globals.highScoreActivity, 1000, R.drawable.confetti1, 3000)
                .setSpeedRange(0.2f, 0.5f)
                .setFadeOut(1000)
                .oneShot(Globals.highScoreActivity.findViewById(R.id.emiter_top_left), 200);

        new ParticleSystem(Globals.highScoreActivity, 1000, R.drawable.confetti2, 4000)
                .setSpeedRange(0.2f, 0.5f)
                .setRotationSpeed(120)
                .setFadeOut(1000)
                .oneShot(Globals.highScoreActivity.findViewById(R.id.emiter_top_left), 200);
    }
}
