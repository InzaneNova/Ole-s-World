package no.Strohm.game2D.items.itemList;

import no.Strohm.game2D.entity.mob.Mob;
import no.Strohm.game2D.items.Item;
import no.Strohm.game2D.items.interfaces.Usable;
import no.Strohm.game2D.world.World;

/**
 * Created by Ole on 10/05/2014.
 */
public class ItemFist extends Item implements Usable {

    public ItemFist(int ID) {
        super(ID, "fist", 3, 0, 0);
    }

    @Override
    public void use(Mob source, World world) {

    }

}
