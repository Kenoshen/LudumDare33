package ludum.dare.trait;

import com.badlogic.gdx.math.Vector2;
import com.winger.physics.CBody;
import com.winger.physics.CWorld;

/**
 * Created by mwingfield on 8/2/15.
 */
public class PhysicalTrait extends Trait {
    private static Class[] REQUIRES = new Class[]{ };

    public CBody<?> body;
    private PositionTrait pos;
    private Vector2 offset;

    private boolean active = true;
    private boolean sensor;

    public PhysicalTrait(GameObject obj, CBody<?> body) {
        super(obj);
        this.body = body;
        offset = new Vector2(0,0);
    }

    @Override
    public void initialize(){
        super.initialize();

        pos = self.getTrait(PositionTrait.class);
        body.addToWorld(CWorld.world._world);
        if(sensor) {
            body.setIsSensor(sensor);
        }
    }

    @Override
    public Class[] requires() {
        return REQUIRES;
    }

    public void step(){
        if (pos != null) {
            Vector2 p = body.getPosition();
            pos.x = p.x + offset.x;
            pos.y = p.y + offset.y;
            pos.rotation = (float)Math.toDegrees(body.getAngle());
        }
    }

    public void delete(){
        CWorld.world._world.destroyBody(body.body);
        body.body = null;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setOffset(int x, float y) {
        offset.x = x;
        offset.y = y;
    }

    public void setSensor(boolean b) {
        sensor = b;
    }
}
