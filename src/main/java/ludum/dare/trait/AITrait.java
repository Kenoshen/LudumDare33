package ludum.dare.trait;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by jake on 8/22/2015.
 */
public class AITrait extends Trait {

    public AITrait(GameObject obj) {
        super(obj);
    }
    @Override
    public Class[] requires() {
        return new Class[0];
    }
}
