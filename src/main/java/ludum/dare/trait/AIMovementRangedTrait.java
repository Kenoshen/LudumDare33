package ludum.dare.trait;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import ludum.dare.screen.GameScreen;
import ludum.dare.utils.AnimationCallback;
import ludum.dare.world.SoundLibrary;
import ludum.dare.world.SparkBall;

/**
 * Created by jake on 8/22/2015.
 */
public class AIMovementRangedTrait extends Trait implements AnimationCallback{
    private float speed, minDist;
    private AnimatorTrait animator;
    private Vector2 aim = new Vector2();
    private PositionTrait position;
    private boolean shooting;

    public AIMovementRangedTrait(GameObject obj, float s, float md) {
        super(obj);
        speed = s;
        minDist = md;
    }

    @Override
    public Class[] requires() {
        return new Class[] {AnimatorTrait.class};
    }


    @Override
    public void initialize() {
        super.initialize();
        animator = self.getTrait(AnimatorTrait.class);
        animator.registerAnimationCallback(this);
        position = self.getTrait(PositionTrait.class);
        shooting = false;
    }

    public void updateMovement(Vector2 target){
        aim = target.cpy().sub(position.x, position.y).sub(0, 7);
        if (shooting) {
            return;
        }
        Vector2 vel = new Vector2(0, 0);
        if(target.x < position.x){
            self.getTrait(AnimatorTrait.class).flipped = false;
            if (position.x - target.x >minDist){
                vel.x -= speed;
                self.getTrait(AnimatorTrait.class).changeStateIfUnique("walk", true);
//            log.debug("Enemy: Want to move left");
            }
        }
        if(target.x >= position.x){
            self.getTrait(AnimatorTrait.class).flipped = true;
            if (target.x - position.x>minDist){
                vel.x += speed;
                self.getTrait(AnimatorTrait.class).changeStateIfUnique("walk", true);
//            log.debug("Enemy: Want to move right");
            }
        }
        if((target.y < position.y)
                && (position.y - target.y)>minDist){
            vel.y -= speed;
            self.getTrait(AnimatorTrait.class).changeStateIfUnique("walk", true);
//            log.debug("Enemy: Want to move down");
        }
        if((target.y >= position.y)
                && (target.y - position.y)>minDist){
            vel.y += speed;
            self.getTrait(AnimatorTrait.class).changeStateIfUnique("walk", true);
//            log.debug("Enemy: Want to move up");
        }
        if(vel.x == 0 && vel.y == 0) {

            if(!shooting){
                System.out.println("will shoot");
                shooting = true;
                self.getTrait(AnimatorTrait.class).setState("shoot", false);
                SoundLibrary.GetSound("Electric_Charge").play();
            }
        }
        vel = vel.nor().scl(speed);
        self.getTrait(PhysicalTrait.class).body.body.setLinearVelocity(vel);
    }

    @Override
    public void animationStarted(String name) {
        if (name.equals("walk")) {
            shooting = false;
        }
    }

    @Override
    public void animationEnded(String name) {
        if (name.equals("shoot")) {
            GameScreen.addObject(new SparkBall(position.x, position.y+1, 0, aim.cpy().nor(), 10));
            animator.setState("walk");
            shooting = false;
            System.out.println("done shoot");
        }
    }
}
