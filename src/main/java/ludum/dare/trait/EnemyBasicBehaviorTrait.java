package ludum.dare.trait;

import ludum.dare.world.Player;

/**
 * Created by jake on 8/22/2015.
 */
public class EnemyBasicBehaviorTrait extends Trait {


    public EnemyBasicBehaviorTrait(GameObject obj) {
        super(obj);
    }

    @Override
    public Class[] requires() {
        return new Class[0];
    }


}
