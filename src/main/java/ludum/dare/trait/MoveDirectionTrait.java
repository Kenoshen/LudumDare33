package ludum.dare.trait;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Admin on 8/23/2015.
 */
public class MoveDirectionTrait extends Trait {
    private Vector2 direction;
    private PhysicalTrait physics;
    private float speed;

    public MoveDirectionTrait(GameObject obj, Vector2 direction, float speed) {
        super(obj);
        this.direction = direction;
        this.speed = speed;
    }

    @Override
    public Class[] requires() {
        return new Class[] {PhysicalTrait.class};
    }

    @Override
    public void initialize() {
        super.initialize();
        physics = self.getTrait(PhysicalTrait.class);
        direction = direction.nor();
    }

    boolean set = false;
    public void travel(float delta) {
        if (!set) {
            set = true;
            physics.body.body.setLinearVelocity(direction.scl(speed));

        }
//        Vector2 lastPos = physics.body.body.getTransform().getPosition();
//        lastPos.add(direction.scl(speed).scl(delta));
//        physics.body.body.setTransform(lastPos, 0);
    }
}
