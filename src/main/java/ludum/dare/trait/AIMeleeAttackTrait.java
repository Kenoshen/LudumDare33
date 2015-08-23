package ludum.dare.trait;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by jake on 8/22/2015.
 */
public class AIMeleeAttackTrait extends Trait {

    public AIMeleeAttackTrait(GameObject obj) {
        super(obj);
    }

    @Override
    public Class[] requires() {
        return new Class[0];
    }

    public void attack(Vector2 target){
    }
}
