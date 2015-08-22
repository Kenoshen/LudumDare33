package ludum.dare.trait;

import ludum.dare.hitbox.HitboxGroup;
import ludum.dare.hitbox.HitboxSequence;
import ludum.dare.trait.AnimatorTrait;
import ludum.dare.trait.GameObject;
import ludum.dare.trait.Trait;
import ludum.dare.utils.NamedAnimation;

import java.util.Map;

/**
 * Created by Admin on 8/21/2015.
 */
public class TimedHitboxTrait extends Trait {
    private static Class[] REQUIRES = new Class[]{AnimatorTrait.class};
    private Map<String, HitboxSequence> hitboxes;
    private AnimatorTrait animations;

    public TimedHitboxTrait(GameObject obj, Map<String, HitboxSequence> hitboxes) {
        super(obj);
        this.hitboxes = hitboxes;
    }

    @Override
    public void initialize() {
        super.initialize();
        animations = self.getTrait(AnimatorTrait.class);
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
            if (sequence.frames.length <= animation.getLastCalledFrame()) {
                return sequence.frames[animation.getLastCalledFrame()];
            }
        }
        return null;
    }
}
