package no.Strohm.game2D.world.tiles;

import no.Strohm.game2D.entity.Entity;
import no.Strohm.game2D.entity.particles.ParticleText;
import no.Strohm.game2D.graphics.Screen;
import no.Strohm.game2D.graphics.SpriteSheet;
import no.Strohm.game2D.util.Vector2i;
import no.Strohm.game2D.world.World;

import java.util.Random;

/**
 * Created by Ole on 15/12/13.
 */
public abstract class Tile {

    public static int grassId = 0;
    public static int waterId = 1;
    public static int treeId = 2;
    public static int stoneId = 3;
    public static int sandId = 4;
    public static int dirtId = 5;
    public static int holeId = 6;
    public static int voidId = 9000;

    public final String tag;
    protected final Vector2i pos;
    protected final SpriteSheet sheet = SpriteSheet.tiles;
    protected static final Random random = new Random();
    public int id;
    public Entity lastHurtByEntity = null;
    protected World world;
    protected int health;

    public Tile(int id, int health, int x, int y, World world, String tag) {
        this.id = id;
        this.health = health;
        pos = new Vector2i(x, y);
        this.world = world;
        this.tag = tag + ".tile";
    }

    public static Tile createTile(int id, int x, int y, World world) {
        int xx = x;
        int yy = y;
        if (id == grassId) return new TileGrass(xx, yy, world);
        if (id == waterId) return new TileWater(xx, yy, world);
        if (id == treeId) return new TileTree(xx, yy, world);
        if (id == stoneId) return new TileStone(xx, yy, world);
        if (id == sandId) return new TileSand(xx, yy, world);
        if (id == dirtId) return new TileDirt(xx, yy, world);
        if (id == holeId) return new TileHole(xx, yy, world);
        else return new TileVoid(xx, yy, world);
    }

    public boolean solid() {
        return false;
    }

    public boolean breakAble() {
        return true;
    }

    public boolean slows() {
        return false;
    }

    public abstract void tick();

    public abstract void render(Screen screen);

    public void getHurt(Entity source, int dmg) {
        if (!breakAble()) return;
        lastHurtByEntity = source;
        health -= dmg;
        world.addEntity(new ParticleText(pos.getX(), pos.getY(), world, Integer.toString(dmg), 0xff0000));
        if (health <= 0) die();
    }

    public void die() {
        System.out.println(tag + " at " + pos.getX() + ", " + pos.getY() + " killed by " + lastHurtByEntity.tag);
        onDeath();
    }

    public void onDeath() {}
}