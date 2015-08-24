package ludum.dare.trait;

import com.badlogic.gdx.math.Vector2;
import com.winger.physics.body.BoxBody;
import ludum.dare.Conf;
import ludum.dare.utils.AnimationCallback;
import ludum.dare.world.EnemyBasic;
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
    private ImmobilizedTrait imob;

    private boolean attacking = false;

    private float speed = 7;
    private float minDist = 9;


    public boolean dead = false;

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
        imob = self.getTrait(ImmobilizedTrait.class);
        rightFacing = ((EnemyBasic)self).rightFacing;

        if (!(physical.body instanceof BoxBody)){
            throw new RuntimeException("InputHandlerTrait requires PhysicalTrait, but it also requires a BoxBody for the physicalTrait.body");
        }
    }


    @Override
    public Class[] requires() {
        return REQUIRES;
    }

    public void update() {
        Vector2 target = ((EnemyBasic) self).target;
        Vector2 myPos = new Vector2(self.getTrait(PositionTrait.class).x, self.getTrait(PositionTrait.class).y) ;
        if(dead) {
            // wipe yo'self off. You dead.
            zeroOutVelocity();
        }
        if (health.health <= 0) {
            //TODO: add enemy death animation, enemy dieing will crash game until this added
            System.out.println("I'M FUCKING DEAD");
            animator.changeStateIfUnique("death", false);
            dead = true;
            physical.setActive(false);
            return;
        }
        // character can either attack or move. not both.
        if (attacking) {
            return;
        }

        Vector2 vel = new Vector2(0, 0);
        if(target.x < myPos.x){
            if (myPos.x - target.x >minDist){
                vel.x -= speed;
                self.getTrait(AnimatorTrait.class).changeStateIfUnique("walk", true);
//            log.debug("Enemy: Want to move left");
            }
        }
        if(target.x >= myPos.x){
            if (target.x - myPos.x>minDist){
                vel.x += speed;
                self.getTrait(AnimatorTrait.class).changeStateIfUnique("walk", true);
//            log.debug("Enemy: Want to move right");
            }
        }
        if((target.y < myPos.y)
                && (myPos.y - target.y)>minDist/10){
            vel.y -= speed;
            self.getTrait(AnimatorTrait.class).changeStateIfUnique("walk", true);
//            log.debug("Enemy: Want to move down");
        }
        if((target.y >= myPos.y)
                && (target.y - myPos.y)>minDist/10){
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

        if (vel.len() < 1){
            zeroOutVelocity();
        } else {
            physical.body.body.setLinearVelocity(vel);
        }

        vel = physical.body.getLinearVelocity();
        if (vel.x > 0){
            animator.flipped = true;
        } else if(vel.x < 0) {
            animator.flipped = false;
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
