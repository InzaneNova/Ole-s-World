package no.Strohm.game2D.Multiplayer;

import no.Strohm.game2D.graphics.Screen;
import no.Strohm.game2D.graphics.SpriteSheet;

public class OnlinePlayers {

	public static boolean onlineOn = false;
	public int xPos = 20, yPos = 20;
    public String gameTag = "";

	public OnlinePlayers(String gameTag) {
        this.gameTag = gameTag;
        System.out.println("CLIENT: Online player set");
	}

	public void render(Screen screen) { // WIP
		int xx = xPos - 8;
		int yy = yPos - 8;
		screen.render(SpriteSheet.mobs, 0, 0, 8, xx, yy, true, 0);
		screen.render(SpriteSheet.mobs, 1, 0, 8, xx + 8, yy, true, 0);
		screen.render(SpriteSheet.mobs, 0, 1, 8, xx, yy + 8, true, 0);
		screen.render(SpriteSheet.mobs, 1, 1, 8, xx + 8, yy + 8, true, 0);
	}

}
