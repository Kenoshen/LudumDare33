package ludum.dare.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import ludum.dare.collision.AnimationBundle;
import ludum.dare.trait.*;
import ludum.dare.utils.AnimationCallback;
import ludum.dare.utils.AtlasManager;
import ludum.dare.utils.NamedAnimation;

/**
 * Created by Admin on 8/24/2015.
 */
public class Explosion extends GameObject implements AnimationCallback{
    public Explosion(float x, float y, float size) {
        traits.add(new PositionTrait(this, x,y,0));
        traits.add(new DrawableTrait(this));
        traits.add(new LightTrait(this, Color.ORANGE));

        AnimationBundle bundle = new AnimationBundle();
        bundle.addNamedAnimation(new NamedAnimation("boom", .1f, AtlasManager.instance.findRegions("boom/boom"), AtlasManager.instance.findRegions("boom/boom"), new Vector2(), new Vector2(size*1.5f, size)));
        traits.add(new AnimatorTrait(this, bundle.getAnimations()));

        initializeTraits();
        getTrait(AnimatorTrait.class).setState("boom", false);
        getTrait(AnimatorTrait.class).registerAnimationCallback(this);
    }

    @Override
    public void animationStarted(String name) {

    }

    @Override
    public void animationEnded(String name) {
        if (name.equals("boom")) {
            markForDeletion();
        }
    }
}
