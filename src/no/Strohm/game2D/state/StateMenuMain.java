package no.Strohm.game2D.state;

import no.Strohm.game2D.Game;
import no.Strohm.game2D.InputHandler;
import no.Strohm.game2D.graphics.Screen;

/**
 * Created by Ole on 15/12/13.
 */
public class StateMenuMain extends StateMenu {

    private int textPosX = 50;
    private int textPosY = 100;

    public StateMenuMain(InputHandler input) {
        super(5, startId, input);
    }

    protected void press() {
        switch (selected) {
            case 0:
                setState(gameId);
                break;
            case 1:
                break;
            case 2:
                setState(instructionsId);
                break;
            case 3:
                setState(aboutId);
                break;
            case 4:
                setState(exitId);
                break;
        }
    }

    public void render(Screen screen) {
        screen.renderArea(0x00A9FF, 0, screen.w, 0, screen.h, false);
        screen.renderText(Game.TITLE, (screen.w - 9 * 8) / 2, 50, 0xFF0000, false);
        screen.renderText("Play Game", textPosX, textPosY, getColor(0), false);
        screen.renderText("Start server", textPosX, textPosY + 10, getColor(1), false);
        screen.renderText("Instructions", textPosX, textPosY + 20, getColor(2), false);
        screen.renderText("About", textPosX, textPosY + 30, getColor(3), false);
        screen.renderText("Exit", textPosX, textPosY + 40, getColor(4), false);
    }
}
