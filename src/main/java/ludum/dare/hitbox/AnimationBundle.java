package ludum.dare.hitbox;

import com.fasterxml.jackson.databind.util.Named;
import ludum.dare.utils.NamedAnimation;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 8/21/2015.
 */
public class AnimationBundle {
    private Map<String, NamedAnimation> animations = new HashMap<String, NamedAnimation>();
    private Map<String, HitboxSequence> hitboxes = new HashMap<String, HitboxSequence>();

    public void addNamedAnimation(NamedAnimation animation) {
        animations.put(animation.getName(), animation);
    }

    public void addHitboxSequence(HitboxSequence sequence) {
        hitboxes.put(sequence.title, sequence);
    }

    public Map<String, NamedAnimation> getAnimations() {
        return animations;
    }

    public Map<String, HitboxSequence> getHitboxes() {
        return hitboxes;
    }
}
