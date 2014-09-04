package no.Strohm.game2D.items.itemList;

import no.Strohm.game2D.entity.mob.Mob;
import no.Strohm.game2D.world.World;

/**
 * Created by Ole on 10/05/2014.
 */
public class ItemSwordIron extends ItemSword {

    public ItemSwordIron(int ID) {
        super(ID, "iron", 2, 0);
    }

    @Override
    public void use(Mob source, World world) {

    }

}
