package ludum.dare.trait;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import ludum.dare.collision.AnimationBundle;
import ludum.dare.collision.CollisionGroup;
import ludum.dare.collision.CollisionSequence;
import ludum.dare.utils.NamedAnimation;

import java.util.Map;

/**
 * Created by Admin on 8/21/2015.
 */
public class TimedCollisionTrait extends Trait {
    private static Class[] REQUIRES = new Class[]{AnimatorTrait.class, PositionTrait.class};
    private Map<String, CollisionSequence> hitboxes;
    private Map<String, CollisionSequence> hurtboxes;
    private AnimatorTrait animations;
    private PositionTrait position;

    public TimedCollisionTrait(GameObject obj, AnimationBundle bundle) {
        super(obj);
        this.hitboxes = bundle.getHitboxes();
        this.hurtboxes = bundle.getHurtboxes();
    }

    @Override
    public void initialize() {
        super.initialize();
        animations = self.getTrait(AnimatorTrait.class);
        position = self.getTrait(PositionTrait.class);
    }

    @Override
    public Class[] requires() {
        return REQUIRES;
    }

    public CollisionGroup getCurrentHitboxes() {
        return getCollisions(hitboxes);
    }

    public CollisionGroup getCurrentHurtboxes() {
        return getCollisions(hurtboxes);
    }

    private CollisionGroup getCollisions(Map<String, CollisionSequence> data) {
        NamedAnimation animation = animations.getCurrentAnimation();
        String name = animation.getName();
        if (data.containsKey(name)) {
            CollisionSequence sequence = data.get(name);
            if (animation.getLastCalledFrame() < sequence.frames.length) {
                return sequence.frames[animation.getLastCalledFrame()];
            }
        }
        return null;
    }

    public void draw(ShapeRenderer shaper) {
        CollisionGroup hurtboxes = getCurrentHurtboxes();
        if (hurtboxes != null) {
            shaper.setColor(Color.YELLOW);
            if (hurtboxes.circles != null) {
                for (Circle circle : hurtboxes.circles) {
                    shaper.circle(position.x + circle.x, position.y + circle.y, circle.radius);
                }
            }

            if (hurtboxes.boxes != null) {
                for (Rectangle rec : hurtboxes.boxes) {
                    shaper.rect(position.x + rec.x, position.y + rec.y, rec.width, rec.height);
                }
            }
        }
        CollisionGroup hitboxes = getCurrentHitboxes();
        if (hitboxes != null) {
            shaper.setColor(Color.RED);
            if (hitboxes.circles != null) {
                for (Circle circle : hitboxes.circles) {
                    shaper.circle(position.x + circle.x, position.y + circle.y, circle.radius);
                }
            }

            if (hitboxes.boxes != null) {
                for (Rectangle rec : hitboxes.boxes) {
                    shaper.rect(position.x + rec.x, position.y + rec.y, rec.width, rec.height);
                }
            }
        }
    }
}
