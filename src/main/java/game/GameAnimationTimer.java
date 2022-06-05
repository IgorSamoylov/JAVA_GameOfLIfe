package game;

import javafx.animation.AnimationTimer;

import static game.GameSettings.GAME_REFRESH_DELAY;

public class GameAnimationTimer extends AnimationTimer {

    private long prevCallTime = 0;
    private final GameEngine gameEngine;

    public GameAnimationTimer(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    // Java FX calls it every frame and sends current system time in nanos
    @Override
    public void handle(long nowTime) {
        if (GameStats.alive == 0) this.stop();
        if (nowTime  >= prevCallTime + GAME_REFRESH_DELAY) {
            gameEngine.nextStep();
            prevCallTime = nowTime;
        }
    }

}
