package game;

import javafx.animation.AnimationTimer;
import java.util.concurrent.TimeUnit;

import static game.GameSettings.GAME_REFRESH_DELAY;

public class GameAnimationTimer extends AnimationTimer {

    private long prevCallTime = 0;
    private final GameEngine gameEngine;

    public GameAnimationTimer(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    // Java FX calls it every frame
    @Override
    public void handle(long nowTime) {
        if (GameOfLife.alive == 0) this.stop();
        if ((nowTime - prevCallTime) >= TimeUnit.MILLISECONDS.toNanos(GAME_REFRESH_DELAY)) {
            gameEngine.nextStep();
            prevCallTime = nowTime;
        }
    }

}
