package ludum.dare.collision;

import ludum.dare.utils.NamedAnimation;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 8/21/2015.
 */
public class AnimationBundle {
    private Map<String, NamedAnimation> animations = new HashMap<String, NamedAnimation>();
    private Map<String, CollisionSequence> hitboxes = new HashMap<String, CollisionSequence>();
    private Map<String, CollisionSequence> hurtboxes = new HashMap<>();

    public void addNamedAnimation(NamedAnimation animation) {
        animations.put(animation.getName(), animation);
    }

    public void addHitboxSequence(CollisionSequence sequence) {
        hitboxes.put(sequence.name, sequence);
    }

    public void addHurtboxSequence(CollisionSequence sequence) {
        hurtboxes.put(sequence.name, sequence);
    }

    public Map<String, NamedAnimation> getAnimations() {
        return animations;
    }

    public Map<String, CollisionSequence> getHitboxes() {
        return hitboxes;
    }

    public Map<String, CollisionSequence> getHurtboxes() { return hurtboxes; }
}
