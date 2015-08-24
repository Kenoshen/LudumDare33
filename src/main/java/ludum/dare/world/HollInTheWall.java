package ludum.dare.world;

import com.badlogic.gdx.math.Vector2;
import ludum.dare.collision.AnimationBundle;
import ludum.dare.trait.AnimatorTrait;
import ludum.dare.trait.DrawableTrait;
import ludum.dare.trait.GameObject;
import ludum.dare.trait.PositionTrait;
import ludum.dare.utils.AtlasManager;
import ludum.dare.utils.NamedAnimation;

/**
 * Created by Admin on 8/24/2015.
 */
public class HollInTheWall extends GameObject {
    public HollInTheWall(int x, int y, float size) {
        traits.add(new PositionTrait(this, x,y,0));
        traits.add(new DrawableTrait(this));

        AnimationBundle bundle = new AnimationBundle();
        bundle.addNamedAnimation(new NamedAnimation("hole", .1f, AtlasManager.instance.findRegions("hole/hole"), AtlasManager.instance.findRegions("hole/hole"), new Vector2(), new Vector2(size, size*2)));
        traits.add(new AnimatorTrait(this, bundle.getAnimations()));

        initializeTraits();
        getTrait(AnimatorTrait.class).setState("hole", false);
    }
}
