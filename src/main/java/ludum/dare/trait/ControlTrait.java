package ludum.dare.trait;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.winger.physics.body.BoxBody;
import ludum.dare.Conf;
import ludum.dare.utils.AnimationCallback;
import ludum.dare.world.Player;
import ludum.dare.world.SoundLibrary;

import java.util.HashMap;

/**
 * Created by Admin on 8/22/2015.
 */
public class ControlTrait extends Trait implements AnimationCallback {
    private static final float PLAYER_ANIMATION_VEL_CHANGE = 0.1f;
    private static Class[] REQUIRES = new Class[]{PhysicalTrait.class, AnimatorTrait.class, HealthTrait.class};

    public enum ControlAction {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        ATTACK,
        JUMP,
        NONE
    }

    private PhysicalTrait physical;
    private AnimatorTrait animator;
    private ImmobilizedTrait imob;
    private HealthTrait health;
    private BoxBody player;
    private boolean rightFacing;

    public boolean attacking = false;
    public boolean queuedAttack = false;

    public boolean jumping = false;
    public boolean landing = false;

    public boolean dead = false;

    private ControlAction leftRightRequest = ControlAction.NONE;
    private ControlAction upDownRequest = ControlAction.NONE;
    private boolean attackRequest = false;
    private boolean jumpRequest = false;

    public ControlTrait(GameObject obj) {
        super(obj);
    }

    @Override
    public void initialize(){
        super.initialize();
        physical = self.getTrait(PhysicalTrait.class);
        animator = self.getTrait(AnimatorTrait.class);
        animator.registerAnimationCallback(this);
        imob = self.getTrait(ImmobilizedTrait.class);
        health = self.getTrait(HealthTrait.class);
        rightFacing = ((Player)self).rightFacing;

        if (!(physical.body instanceof BoxBody)){
            throw new RuntimeException("InputHandlerTrait requires PhysicalTrait, but it also requires a BoxBody for the physicalTrait.body");
        }

        player = (BoxBody)physical.body;
    }


    @Override
    public Class[] requires() {
        return REQUIRES;
    }

    public void requestMove(ControlAction action) {
        switch (action) {
             case UP:
            case DOWN:
                upDownRequest = action;
                break;
            case LEFT:
            case RIGHT:
                leftRightRequest = action;
                break;
            case ATTACK:
                attackRequest = true;
                break;
            case JUMP:
                jumpRequest = true;
                break;
        }
    }

    public void update() {
        if (dead) {
            // wipe yo'self off. You dead.
            zeroOutVelocity();
            return;
        }
        if (health.health <= 0) {
            System.out.println("I'M FUCKING DEAD");
            animator.changeStateIfUnique("death", false);
            SoundLibrary.GetSound("Player_Death").play();
            dead = true;
            physical.setActive(false);
            return;
        }
        Vector2 movement = new Vector2(0, 0);
        // character can either attack or move. not both.
        if (landing || imob.imob) {
            // can't do shit when you recovering from them dank hops.
        } else if (attackRequest) {
            if (jumping && !attacking) {
                attacking = true;
                animator.setState("jumpKick", false, true);
            } else if (attacking) {
                queuedAttack = true;
            } else {
                attacking = true;
                animator.setState("punch", false);
                SoundLibrary.GetSound("Punch_Miss").play();
            }
        } else if (!attacking || jumping) {
            if (jumpRequest && !jumping) {
                jumping = true;
                SoundLibrary.GetSound("Jump_Player").play();
            }
            if (upDownRequest.equals(ControlAction.UP)) {
                movement.y += Conf.instance.playerWalkSpeed();
            } else if (upDownRequest.equals(ControlAction.DOWN)) {
                movement.y -= Conf.instance.playerWalkSpeed();
            }

            if (leftRightRequest.equals(ControlAction.LEFT)) {
                rightFacing = false;
                movement.x -= Conf.instance.playerWalkSpeed();
            } else if (leftRightRequest.equals(ControlAction.RIGHT)) {
                rightFacing = true;
                movement.x += Conf.instance.playerWalkSpeed();
            }
        }

        movement = movement.nor().scl(Conf.instance.playerWalkSpeed());

        if (movement.len() < 1){
            zeroOutVelocity();
        } else {
            physical.body.body.setLinearVelocity(movement);
        }

        Vector2 vel = physical.body.getLinearVelocity();
        if (jumping) {
            if (!landing && !attacking) {
                animator.changeStateIfUnique("jump", false);
            }
        } else if (vel.len() > PLAYER_ANIMATION_VEL_CHANGE) {
            animator.changeStateIfUnique("walk", true);
        } else if (imob.imob && imob.type.equals(ImmobilizedTrait.Type.ZAP)) {
            animator.changeStateIfUnique("zap", false);
        } else if(imob.imob && rightFacing && ((Player)self).hitFromRight){
            animator.changeStateIfUnique("pain", false);
        } else if(imob.imob && rightFacing && !((Player)self).hitFromRight){
            animator.changeStateIfUnique("backpain", false);
        } else if(imob.imob && !rightFacing && ((Player)self).hitFromRight){
            animator.changeStateIfUnique("backpain", false);
        } else if(imob.imob && !rightFacing && !((Player)self).hitFromRight){
            animator.changeStateIfUnique("pain", false);
        }
        else if (!attacking) {
            animator.changeStateIfUnique("stand", true);
        }

        if (vel.x > 0){
            animator.flipped = false;
        } else if(vel.x < 0) {
            animator.flipped = true;
        }

        leftRightRequest = ControlAction.NONE;
        upDownRequest = ControlAction.NONE;
        attackRequest = false;
        jumpRequest = false;
    }

    private void zeroOutVelocity() {
        Vector2 vel = physical.body.body.getLinearVelocity();
        vel.x *= -1;
        vel.y *= -1;
        physical.body.body.applyLinearImpulse(vel, new Vector2(0, 0), true);
    }

    @Override
    public void animationStarted(String name) {
        System.out.println(name + " started");
    }

    @Override
    public void animationEnded(String name) {
        System.out.println(name + " ended");
        if (name.equals("punch")) {
            if (queuedAttack) {
                animator.setState("punch2", false);
                queuedAttack = false;
            } else {
                attacking = false;
            }
        } else if (name.equals("punch2")) {
            queuedAttack = false;
            attacking = false;
        } else if (name.equals("jump") || name.equals("jumpKick")) {
            animator.setState("land", false);
            landing = true;
        } else if (name.equals("land")) {
            landing = false;
            jumping = false;
            attacking = false;
        } else if(name.equals("pain") || name.equals("backpain") || name.equals("zap")){
            imob.imob = false;
        }
        else {
            attacking = false;
        }
    }
}
