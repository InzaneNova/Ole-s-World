package no.Strohm.game2D.world.tiles;

import no.Strohm.game2D.graphics.Screen;
import no.Strohm.game2D.world.World;

/**
 * Created by Ole on 15/12/13.
 */
public class TileGrass extends Tile {

    public TileGrass(int x, int y, World world) {
        super(grassId, 10, x, y, world, "grass");
    }

    public void tick() {

    }

    public void render(Screen screen) {
        int xx = pos.getX() << 4;
        int yy = pos.getY() << 4;
        screen.render(sheet, 0, 0, 16, xx, yy, true, 0);
    }

    public void die() {
        super.die();
        world.setTile(dirtId, pos.getX(), pos.getY());
    }
}
