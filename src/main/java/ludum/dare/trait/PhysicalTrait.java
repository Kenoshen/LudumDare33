package ludum.dare.trait;

import com.badlogic.gdx.math.Vector2;
import com.winger.physics.CBody;
import com.winger.physics.CWorld;

/**
 * Created by mwingfield on 8/2/15.
 */
public class PhysicalTrait extends Trait {
    private static Class[] REQUIRES = new Class[]{ PositionTrait.class, SizeTrait.class };

    public CBody<?> body;
    private PositionTrait pos;

    public PhysicalTrait(GameObject obj, CBody<?> body) {
        super(obj);
        this.body = body;
    }

    @Override
    public void initialize(){
        super.initialize();

        pos = self.getTrait(PositionTrait.class);
        body.addToWorld(CWorld.world._world);
    }

    @Override
    public Class[] requires() {
        return REQUIRES;
    }

    public void step(){
        Vector2 p = body.getPosition();
        pos.x = p.x;
        pos.y = p.y;
    }

    public void delete(){
        body.removeFromWorld();
    }

}
