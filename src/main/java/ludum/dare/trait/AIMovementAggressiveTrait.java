package ludum.dare.trait;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by jake on 8/22/2015.
 */
public class AIMovementAggressiveTrait extends Trait {
    private float speed, minDist;

    public AIMovementAggressiveTrait(GameObject obj, Vector2 target, float s, float md) {
        super(obj);
        speed = s;
        minDist = md;
    }

    @Override
    public Class[] requires() {
        return new Class[0];
    }

    public void updateMovement(Vector2 target){
        Vector2 vel = new Vector2(0, 0);
        if((target.x < self.getTrait(PositionTrait.class).x)
                && (self.getTrait(PositionTrait.class).x - target.x)>minDist){
            vel.x -= speed;
            log.debug("Enemy: Want to move left");
        }
        if((target.x >= self.getTrait(PositionTrait.class).x)
                && (target.x - self.getTrait(PositionTrait.class).x)>minDist){
            vel.x += speed;
            log.debug("Enemy: Want to move right");
        }
        if((target.y < self.getTrait(PositionTrait.class).y)
                && (self.getTrait(PositionTrait.class).y - target.y)>minDist){
            vel.y -= speed;
            log.debug("Enemy: Want to move down");
        }
        if((target.y >= self.getTrait(PositionTrait.class).y)
                && (target.y - self.getTrait(PositionTrait.class).y)>minDist){
            vel.y += speed;
            log.debug("Enemy: Want to move up");
        }
        self.getTrait(PhysicalTrait.class).body.body.setLinearVelocity(vel);
    }
}
