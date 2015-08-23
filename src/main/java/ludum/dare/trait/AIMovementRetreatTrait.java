package ludum.dare.trait;

/**
 * Created by jake on 8/22/2015.
 */
public class AIMovementRetreatTrait extends Trait {
    public AIMovementRetreatTrait(GameObject obj) {
        super(obj);
    }

    @Override
    public Class[] requires() {
        return new Class[0];
    }
}
