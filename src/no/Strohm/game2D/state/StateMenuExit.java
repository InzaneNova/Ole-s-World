package no.Strohm.game2D.state;

import no.Strohm.game2D.Game;
import no.Strohm.game2D.InputHandler;
import no.Strohm.game2D.graphics.Screen;

/**
 * Created by Ole on 22/12/13.
 */
public class StateMenuExit extends StateMenu {


    public StateMenuExit(InputHandler input) {
        super(2, exitId, input);
    }

    protected void press() {
        switch (selected) {
            case 0:
                Game.stop();
                break;
            case 1:
                setState(lastState);
                break;
        }
    }

    public void render(Screen screen) {
        screen.renderArea(0x00A9FF, 0, screen.w, 0, screen.h, false);
        screen.renderText("Are you sure you want to quit?", (screen.w - "Are you sure you want to quit?".length() * 8) / 2, 50, col1 + col2, false);
        screen.renderText("yes", 50, screen.h / 2, getColor(0), false);
        screen.renderText("no", 50, screen.h / 2 + 16, getColor(1), false);
    }
}
