package ludum.dare.trait;

/**
 * Created by jake on 8/22/2015.
 */
public class EnemyThrowerBehaviorTrait extends Trait {
    public EnemyThrowerBehaviorTrait(GameObject obj) {
        super(obj);
    }

    @Override
    public Class[] requires() {
        return new Class[0];
    }
}
