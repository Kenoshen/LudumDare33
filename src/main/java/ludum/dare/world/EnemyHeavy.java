package ludum.dare.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import ludum.dare.utils.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.winger.physics.CBody;
import com.winger.physics.body.BoxBody;
import ludum.dare.collision.AnimationBundle;
import ludum.dare.trait.*;
import ludum.dare.utils.AtlasManager;
import ludum.dare.utils.NamedAnimation;

import java.util.Map;

/**
 * Created by jake on 8/22/2015.
 */
public class EnemyHeavy extends GameObject{
    private PhysicalTrait physical;
    public Vector2 target;
    private AnimatorTrait animator;

    public boolean rightFacing = false;


    public EnemyHeavy(float x, float y, float z){
        float width = 12;
        float height =12;
        traits.add(new PositionTrait(this, x, y, z));
        traits.add(new DrawableTrait(this));

        traits.add(new HealthTrait(this, 50, null));

        AnimationBundle bundle = new AnimationBundle();

        final NamedAnimation animation = new NamedAnimation("stand", .1f,AtlasManager.instance.getAtlas("heavybot").findRegions("stand/heavyStand"), AtlasManager.instance.getAtlas("heavybot").findRegions("stand/heavyStand"), new Vector2(0, 0), new Vector2(width*1.5f, height));
        bundle.addNamedAnimation(animation);
        bundle.addNamedAnimation(new NamedAnimation("walk", .1f,AtlasManager.instance.getAtlas("heavybot").findRegions("walk/heavyWalk"), AtlasManager.instance.getAtlas("heavybot").findRegions("walk/heavyWalk"), new Vector2(0,0), new Vector2(width*1.5f, height)));
        bundle.addNamedAnimation(new NamedAnimation("hit", .1f,AtlasManager.instance.getAtlas("heavybot").findRegions("slam/heavySlam"), AtlasManager.instance.getAtlas("heavybot").findRegions("slam/heavySlams"), new Vector2(-3,3), new Vector2(width*1.5f, height*1.5f)));

        animator = new AnimatorTrait(this, bundle.getAnimations());
        traits.add(animator);

        traits.add(new AITrait(this));
        traits.add(new AIMovementAggressiveTrait(this));
        traits.add(new ControlTraitEnemy(this, 3, 12));

        FixtureDef fd = new FixtureDef();
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.position.x = x;
        bd.position.y = y;
        bd.fixedRotation = true;
        CBody body = new BoxBody(width/2, 1).init(fd, bd);
        physical = new PhysicalTrait(this, body);
        physical.setOffset(0, height/2 -.5f);
        traits.add(physical);

        traits.add(new DebugTrait(this));

        initializeTraits();
    }
}
