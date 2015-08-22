package ludum.dare.world;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.winger.input.raw.CGamePad;
import com.winger.input.raw.CKeyboard;
import com.winger.input.raw.CMouse;
import com.winger.physics.CBody;
import com.winger.physics.body.PlayerBody;
import ludum.dare.hitbox.AnimationBundle;
import ludum.dare.trait.*;
import ludum.dare.utils.NamedAnimation;

import java.util.Map;

/**
 * Created by mwingfield on 8/3/15.
 */
public class Player extends GameObject {
    private PhysicalTrait physical;
    private AnimatorTrait animator;
    private TimedHitboxTrait hitboxes;

    public Player(float x, float y, float z, float width, float height, AnimationBundle bundle, CMouse mouse, CKeyboard keyboard, CGamePad gamepad){
        traits.add(new PositionTrait(this, x, y, z));
        traits.add(new SizeTrait(this, width, height));
        traits.add(new DrawableTrait(this, new Sprite()));
        animator = new AnimatorTrait(this, bundle.getAnimations());
        animator.setState("basic");
        traits.add(animator);

        hitboxes = new TimedHitboxTrait(this, bundle.getHitboxes());
        traits.add(hitboxes);

        traits.add(new ControlTrait(this, mouse, keyboard, gamepad));
        CBody body = new PlayerBody(width, height).init(new Vector2(x, y));
        physical = new PhysicalTrait(this, body);
        traits.add(physical);

        traits.add(new DebugTrait(this));

        traits.add(new UpdatableTrait(this) {
            @Override
            public void update(float delta) {
                ((Player)self).update(delta);
            }
        });

        initializeTraits();

        body.setFriction(1);
    }


    public void update(float delta){
        Vector2 vel = physical.body.getLinearVelocity();
        if (vel.y > 2){
            animator.changeStateIfUnique("jump");
        } else if (vel.y < 2){
            animator.changeStateIfUnique("fall");
        } else if (vel.x > 2){
            animator.changeStateIfUnique("run", true);
        } else if (vel.x < 2){
            animator.changeStateIfUnique("run", true);
        }
    }
}
