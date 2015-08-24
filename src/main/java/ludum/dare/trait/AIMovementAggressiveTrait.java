package ludum.dare.trait;

import com.badlogic.gdx.math.Vector2;
import javafx.animation.Animation;
import ludum.dare.utils.AnimationCallback;

/**
 * Created by jake on 8/22/2015.
 */
public class AIMovementAggressiveTrait extends Trait implements AnimationCallback {
    private float speed, minDist;
    private boolean attacking = false;
    private AnimatorTrait animator;

    public AIMovementAggressiveTrait(GameObject obj, float s, float md) {
        super(obj);
        speed = s;
        minDist = md;
    }

    @Override
    public void initialize() {
        super.initialize();
        animator = self.getTrait(AnimatorTrait.class);
        animator.registerAnimationCallback(this);
    }

    @Override
    public Class[] requires() {
        return new Class[] {AnimatorTrait.class};
    }

    public void updateMovement(Vector2 target){
        if (attacking) {
            return;
        }

        Vector2 vel = new Vector2(0, 0);
        if(target.x < self.getTrait(PositionTrait.class).x){
            self.getTrait(AnimatorTrait.class).flipped = false;
            if (self.getTrait(PositionTrait.class).x - target.x >minDist){
                    vel.x -= speed;
                    self.getTrait(AnimatorTrait.class).changeStateIfUnique("walk", true);
//            log.debug("Enemy: Want to move left");
            }
        }
        if(target.x >= self.getTrait(PositionTrait.class).x){
            self.getTrait(AnimatorTrait.class).flipped = true;
            if (target.x - self.getTrait(PositionTrait.class).x>minDist){
            vel.x += speed;
            self.getTrait(AnimatorTrait.class).changeStateIfUnique("walk", true);
//            log.debug("Enemy: Want to move right");
            }
        }
        if((target.y < self.getTrait(PositionTrait.class).y)
                && (self.getTrait(PositionTrait.class).y - target.y)>minDist/10){
            vel.y -= speed;
            self.getTrait(AnimatorTrait.class).changeStateIfUnique("walk", true);
//            log.debug("Enemy: Want to move down");
        }
        if((target.y >= self.getTrait(PositionTrait.class).y)
                && (target.y - self.getTrait(PositionTrait.class).y)>minDist/10){
            vel.y += speed;
            self.getTrait(AnimatorTrait.class).changeStateIfUnique("walk", true);
//            log.debug("Enemy: Want to move up");
        }
        if(vel.x == 0 && vel.y == 0) {
            self.getTrait(AnimatorTrait.class).setState("hit", false);
            attacking = true;
        }
        vel = vel.nor().scl(speed);
        self.getTrait(PhysicalTrait.class).body.body.setLinearVelocity(vel);
    }

    @Override
    public void animationStarted(String name) {

    }

    @Override
    public void animationEnded(String name) {
        if (name.equals("hit")) {
            attacking = false;
        }
    }
}
