package no.Strohm.game2D.containers;

import no.Strohm.game2D.Game;
import no.Strohm.game2D.entity.mob.Player;
import no.Strohm.game2D.util.Vector2i;

/**
 * Created by Ole on 25/01/14.
 */
public class Inventory extends ItemContainer {

    public Inventory(Player player) {
        super("inventory");
        setPlayer(player);
        setFocus(true);
        dimensions = new Vector2i(9, 9);
        pos = new Vector2i(Game.WIDTH / 4, (Game.HEIGHT - (19 + dimensions.getY() * 16)) / 2);
    }
}
