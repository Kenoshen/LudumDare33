package ludum.dare.trait;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by jake on 8/22/2015.
 */
public class AIMovementRetreatTrait extends Trait {
    private float speed, minDist, maxDist;

    public AIMovementRetreatTrait(GameObject obj, float s, float mnD, float mxD) {
        super(obj);
        speed = s;
        minDist = mnD;
        maxDist = mxD;
    }

    @Override
    public Class[] requires() {
        return new Class[0];
    }

    public void updateMovement(Vector2 target){
        Vector2 vel = new Vector2(0,0);
        if(target.x < self.getTrait(PositionTrait.class).x){
            self.getTrait(AnimatorTrait.class).flipped = false;
            if (self.getTrait(PositionTrait.class).x - target.x <maxDist
                    && self.getTrait(PositionTrait.class).x - target.x >minDist){
                vel.x += speed;
                self.getTrait(AnimatorTrait.class).changeStateIfUnique("walk", true);
//            log.debug("Enemy: Want to move left");
            }
        }
        if(target.x >= self.getTrait(PositionTrait.class).x){
            self.getTrait(AnimatorTrait.class).flipped = true;
            if (target.x - self.getTrait(PositionTrait.class).x<maxDist
                    && target.x - self.getTrait(PositionTrait.class).x>minDist){
                vel.x -= speed;
                self.getTrait(AnimatorTrait.class).changeStateIfUnique("walk", true);
//            log.debug("Enemy: Want to move right");
            }
        }
        if((target.y < self.getTrait(PositionTrait.class).y)
                && (self.getTrait(PositionTrait.class).y - target.y)<maxDist
                && (self.getTrait(PositionTrait.class).y - target.y)>minDist){
            vel.y += speed;
            self.getTrait(AnimatorTrait.class).changeStateIfUnique("walk", true);
//            log.debug("Enemy: Want to move down");
        }
        if((target.y >= self.getTrait(PositionTrait.class).y)
                && (target.y - self.getTrait(PositionTrait.class).y)<maxDist
                && (target.y - self.getTrait(PositionTrait.class).y)>minDist){
            vel.y -= speed;
            self.getTrait(AnimatorTrait.class).changeStateIfUnique("walk", true);
//            log.debug("Enemy: Want to move up");
        }
        if(vel.x == 0 && vel.y == 0) {
            self.getTrait(AnimatorTrait.class).changeStateIfUnique("shoot", true);
        }
        vel = vel.nor().scl(speed);
        self.getTrait(PhysicalTrait.class).body.body.setLinearVelocity(vel);
    }
}
