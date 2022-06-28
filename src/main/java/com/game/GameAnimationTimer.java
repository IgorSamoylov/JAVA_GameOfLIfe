package com.game;

import javafx.animation.AnimationTimer;

import java.util.concurrent.TimeUnit;

public class GameAnimationTimer extends AnimationTimer {

    private final GameEngine gameEngine;
    private long prevCallTime = 0;

    public GameAnimationTimer(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    // Java FX simple calls it every frame and sends current system time in nanos
    @Override
    public void handle(long nowTime) {
        if (GameStats.alive == 0) this.stop();
        if (nowTime >= prevCallTime
                + TimeUnit.MILLISECONDS.toNanos(GameSettings.GAME_REFRESH_DELAY)) {
            gameEngine.nextStep();
            prevCallTime = nowTime;
        }
    }

}
