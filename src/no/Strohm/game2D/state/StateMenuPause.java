package no.Strohm.game2D.state;

import no.Strohm.game2D.InputHandler;
import no.Strohm.game2D.graphics.Screen;

/**
 * Created by Ole on 22/12/13.
 */
public class StateMenuPause extends StateMenu {


    public StateMenuPause(InputHandler input) {
        super(4, pauseId, input);
    }

    protected void press() {
        switch (selected){
            case 0:
                setState(gameId);
                break;
            case 1:
                setState(optionsId);
                break;
            case 2:
                setState(instructionsId);
                break;
            case 3:
                setState(startId);
                break;
        }
    }

    public void render(Screen screen) {
        screen.renderArea(0x00A9FF, 0, screen.w, 0, screen.h, false);
        screen.renderText("Paused", (screen.w - 6 * 8) / 2, 60, col1+col2, false);
        screen.renderText("Resume", (screen.w - 6 * 8) / 2, screen.h / 2 - 15, getColor(0), false);
        screen.renderText("options", (screen.w - 7 * 8) / 2, screen.h / 2 - 5, getColor(1), false);
        screen.renderText("Instructions", (screen.w - 12 * 8) / 2, screen.h / 2 + 5, getColor(2), false);
        screen.renderText("Main Menu", (screen.w - 9 * 8) / 2, screen.h / 2 + 15, getColor(3), false);
    }
}
