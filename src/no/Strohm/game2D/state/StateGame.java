package no.Strohm.game2D.state;

import no.Strohm.game2D.InputHandler;
import no.Strohm.game2D.graphics.Screen;
import no.Strohm.game2D.util.FPS;
import no.Strohm.game2D.world.RandomWorld;
import no.Strohm.game2D.world.World;

import java.util.Random;

/**
 * Created by Ole on 15/12/13.
 */
public class StateGame extends State {
    private Random r = new Random();
    private World gameWorld;

    public StateGame(InputHandler input) {
        super(gameId, input);
        gameWorld = new RandomWorld(256, 256, input);
    }

    public void tick() {
        gameWorld.tick();
    }

    public void render(Screen screen) {
        gameWorld.render(screen);
        if (FPS.renderFps) screen.renderText(FPS.frames + " fps, " + FPS.ticks + " ticks", 5, 5, 0, false);
    }
}
