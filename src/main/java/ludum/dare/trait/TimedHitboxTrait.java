package ludum.dare.trait;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import ludum.dare.hitbox.HitboxGroup;
import ludum.dare.hitbox.HitboxSequence;
import ludum.dare.utils.NamedAnimation;

import java.util.Map;

/**
 * Created by Admin on 8/21/2015.
 */
public class TimedHitboxTrait extends Trait {
    private static Class[] REQUIRES = new Class[]{AnimatorTrait.class, PositionTrait.class};
    private Map<String, HitboxSequence> hitboxes;
    private AnimatorTrait animations;
    private PositionTrait position;

    public TimedHitboxTrait(GameObject obj, Map<String, HitboxSequence> hitboxes) {
        super(obj);
        this.hitboxes = hitboxes;
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

    public HitboxGroup getCurrentHitboxes() {
        NamedAnimation animation = animations.getCurrentAnimation();
        String name = animation.getName();
        if (hitboxes.containsKey(name)) {
            HitboxSequence sequence = hitboxes.get(name);
            if (sequence.frames.length >= animation.getLastCalledFrame()) {
                return sequence.frames[animation.getLastCalledFrame()];
            }
        }
        return null;
    }

    public void draw(ShapeRenderer shaper) {
        HitboxGroup hitboxes = getCurrentHitboxes();
        if (hitboxes != null) {
//            System.out.println("hitboxes to render");
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
