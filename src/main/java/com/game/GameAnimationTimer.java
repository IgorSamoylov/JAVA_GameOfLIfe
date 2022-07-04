package com.game;

import javafx.animation.AnimationTimer;

import java.util.concurrent.TimeUnit;

public class GameAnimationTimer extends Thread {
    private final GameEngine gameEngine;
    public final AnimationEngine animationEngine;
    public GameAnimationTimer(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        animationEngine = new AnimationEngine();
    }

    public class AnimationEngine extends AnimationTimer {
        private long prevCallTime = 0;
        // Java FX simple calls it every frame and sends current system time in nanos
        @Override
        public void handle(long nowTime) {
            if (GameStats.alive == 0 ||
                    GameStats.repeatCounter == GameSettings.MAX_REPEAT) {
                this.stop();
                GameStats.repeatCounter = 0;
            }
            if (nowTime >= prevCallTime
                    + TimeUnit.MILLISECONDS.toNanos(GameSettings.GAME_REFRESH_DELAY)) {
                gameEngine.nextStep();
                prevCallTime = nowTime;
            }
        }
    }
}


