package ludum.dare.world;

import ludum.dare.trait.BossTrait;
import ludum.dare.trait.GameObject;

/**
 * Created by Admin on 8/24/2015.
 */
public class Boss extends GameObject {
    public ShockCan can1;
    public ShockCan can2;
    public SpawnerDoor door;

    public Boss(ShockCan can1, ShockCan can2, SpawnerDoor door) {
        this.can1 = can1;
        this.can2 = can2;
        this.door = door;

        traits.add(new BossTrait(this));

        initializeTraits();
    }
}
