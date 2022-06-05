package game;

import javafx.animation.AnimationTimer;

import java.util.concurrent.TimeUnit;
import static game.GameSettings.*;

public class GameAnimationTimer extends AnimationTimer {
    private long prevCallTime = 0;
    private final GameEngine gameEngine;

    public GameAnimationTimer(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    @Override
    public void handle(long now) {
        if ((now - prevCallTime) >= TimeUnit.MILLISECONDS.toNanos(GAME_REFRESH_DELAY)) {
            gameEngine.nextStep();
            prevCallTime = now;
        }
    }
}
