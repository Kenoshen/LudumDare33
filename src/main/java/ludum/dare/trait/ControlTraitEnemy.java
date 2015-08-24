package ludum.dare.trait;

import com.badlogic.gdx.math.Vector2;
import com.winger.physics.body.BoxBody;
import ludum.dare.Conf;
import ludum.dare.utils.AnimationCallback;
import ludum.dare.world.Player;
import ludum.dare.world.SoundLibrary;

/**
 * Created by Admin on 8/22/2015.
 */
public class ControlTraitEnemy extends Trait implements AnimationCallback {
    private static Class[] REQUIRES = new Class[]{PhysicalTrait.class, AnimatorTrait.class, HealthTrait.class};

    private PhysicalTrait physical;
    private AnimatorTrait animator;
    private HealthTrait health;
    private boolean rightFacing;

    public ControlTraitEnemy(GameObject obj) {
        super(obj);
    }

    @Override
    public void initialize(){
        super.initialize();
        physical = self.getTrait(PhysicalTrait.class);
        animator = self.getTrait(AnimatorTrait.class);
        animator.registerAnimationCallback(this);
        health = self.getTrait(HealthTrait.class);

        if (!(physical.body instanceof BoxBody)){
            throw new RuntimeException("InputHandlerTrait requires PhysicalTrait, but it also requires a BoxBody for the physicalTrait.body");
        }
    }


    @Override
    public Class[] requires() {
        return REQUIRES;
    }

    public void update() {
            // wipe yo'self off. You dead.
            zeroOutVelocity();
        if (health.health <= 0) {
            System.out.println("I'M FUCKING DEAD");
            animator.changeStateIfUnique("death", false);
            physical.setActive(false);
            return;
        }
        Vector2 movement = new Vector2(0, 0);
        // character can either attack or move. not both.
                animator.setState("punch", false);

        if (movement.len() < 1){
            zeroOutVelocity();
        } else {
            physical.body.body.setLinearVelocity(movement);
        }

        Vector2 vel = physical.body.getLinearVelocity();

        if (vel.x > 0){
            animator.flipped = false;
        } else if(vel.x < 0) {
            animator.flipped = true;
        }
    }

    private void zeroOutVelocity() {
        Vector2 vel = physical.body.body.getLinearVelocity();
        vel.x *= -1;
        vel.y *= -1;
        physical.body.body.applyLinearImpulse(vel, new Vector2(0, 0), true);
    }

    @Override
    public void animationStarted(String name) {
    }

    @Override
    public void animationEnded(String name) {
        if (name.equals("punch")) {

        } else if(name.equals("pain") || name.equals("backpain")){
        }
    }
}
