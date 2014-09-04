package no.Strohm.game2D.entity;

import no.Strohm.game2D.graphics.Screen;
import no.Strohm.game2D.world.World;

/**
 * Created by Ole on 25/12/13.
 */
public class Spawner extends Entity {

    private int spawn;

    public Spawner(World world) {
        super(0, 0, world, null, "spawner");
        spawn = 300;
    }

    public void tick() {
        spawn--;
        if(spawn <= 0) {
            //spawning mobs N' stuff.
        } else spawn = 300;
    }

    public void render(Screen screen) {
    }

    public void spawnEntity(Entity e) {
        world.addEntity(e);
    }
}
