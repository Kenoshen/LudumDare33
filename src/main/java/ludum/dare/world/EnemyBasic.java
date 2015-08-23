package ludum.dare.world;

import com.badlogic.gdx.math.Circle;
import ludum.dare.collision.CollisionGroup;
import ludum.dare.collision.CollisionSequence;
import ludum.dare.utils.Sprite;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.winger.physics.CBody;
import com.winger.physics.body.BoxBody;
import com.winger.physics.body.PlayerBody;
import ludum.dare.collision.AnimationBundle;
import ludum.dare.trait.*;
import ludum.dare.utils.AtlasManager;
import ludum.dare.utils.NamedAnimation;

import java.util.Map;

/**
 * Created by jake on 8/21/2015.
 */
public class EnemyBasic extends GameObject{
    private PhysicalTrait physical;
    private Vector2 target;
    private AnimatorTrait animator;

    public EnemyBasic(float x, float y, float z, float width, float height){
        traits.add(new PositionTrait(this, x, y, z));
        traits.add(new DrawableTrait(this));

        AnimationBundle bundle = new AnimationBundle();

        final NamedAnimation animation = new NamedAnimation("stand", .1f,AtlasManager.instance.getAtlas("bot").findRegions("stand/botStand"),
                AtlasManager.instance.getAtlas("bot").findRegions("stand/botStand"), new Vector2(0, -.7f), new Vector2(width, height));
        bundle.addNamedAnimation(animation);
        bundle.addNamedAnimation(new NamedAnimation("walk", .1f,AtlasManager.instance.getAtlas("bot").findRegions("walk/botWalk"),
                AtlasManager.instance.getAtlas("bot").findRegions("walk/botWalk"), new Vector2(0, -.7f), new Vector2(width, height)));
        bundle.addNamedAnimation(new NamedAnimation("hit", .1f,AtlasManager.instance.getAtlas("bot").findRegions("hit/botHit"),
                AtlasManager.instance.getAtlas("bot").findRegions("hit/botHit"), new Vector2(0, -.7f), new Vector2(width * 1.5f, height)));

        CollisionSequence hitSequence = new CollisionSequence();
        hitSequence.name = "hit";

        CollisionGroup group1 = new CollisionGroup();
        group1.circles = new Circle[]{ new Circle(-6.0f,1.5f,2.5f)};

        hitSequence.frames = new CollisionGroup[5];
        hitSequence.frames[3] = group1;
        bundle.addHitboxSequence(hitSequence);



        animator = new AnimatorTrait(this, bundle.getAnimations());
        traits.add(animator);
        traits.add(new TimedCollisionTrait(this, bundle));

        traits.add(new AITrait(this));
        traits.add(new AIMovementAggressiveTrait(this, 7.0f, 9.0f));
        traits.add(new HealthTrait(this, 100));
        traits.add(new AIMeleeAttackTrait(this));

        FixtureDef fd = new FixtureDef();
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.position.x = x;
        bd.position.y = y;
        bd.fixedRotation = true;
        CBody body = new BoxBody(width/2, 1).init(fd, bd);
        physical = new PhysicalTrait(this, body);
        physical.setOffset(0, height/2 - .5f);
        traits.add(physical);

        traits.add(new DebugTrait(this));


        initializeTraits();
    }

}

