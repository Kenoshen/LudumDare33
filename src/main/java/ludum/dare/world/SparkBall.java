package ludum.dare.world;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.winger.physics.CBody;
import com.winger.physics.body.BoxBody;
import com.winger.physics.body.ChainBody;
import com.winger.physics.body.CircleBody;
import ludum.dare.collision.AnimationBundle;
import ludum.dare.collision.CollisionGroup;
import ludum.dare.collision.CollisionSequence;
import ludum.dare.trait.*;
import ludum.dare.utils.AtlasManager;
import ludum.dare.utils.CollisionCallback;
import ludum.dare.utils.NamedAnimation;

import java.awt.*;

/**
 * Created by jake on 8/23/2015.
 */
public class SparkBall extends GameObject {
    private PhysicalTrait physical;
    private AnimatorTrait animator;

    private CollisionCallback collisionFunc = new CollisionCallback() {
        @Override
        public void collide(GameObject obj) {
            if (obj.getTrait(InputHandlerTrait.class) == null) {
                return;
            }
            HealthTrait health = obj.getTrait(HealthTrait.class);
            if (health != null) {
                health.damage(10, SparkBall.this);
            }
            markForDeletion();
        }
    };

    public SparkBall(float x, float y, float z, Vector2 direction, float speed) {
        int size = 4;
        float sparkOffset = 6f;
        /* this is a hack because the physics stuff doesn't set the position correctly until it steps once */
        traits.add(new PositionTrait(this, x, y+sparkOffset, z));
        traits.add(new DrawableTrait(this));
        traits.add(new CollidableTrait(this, collisionFunc));

        AnimationBundle bundle = new AnimationBundle();

        final NamedAnimation animation = new NamedAnimation("spark", .1f, AtlasManager.instance.getAtlas("spark").findRegions("spark/spark"), AtlasManager.instance.getAtlas("spark").findRegions("spark/spark"), new Vector2(0,0), new Vector2(size, size));
        bundle.addNamedAnimation(animation);

        CollisionSequence hitSequence = new CollisionSequence();
        hitSequence.name = "spark";

        CollisionGroup cGroup = new CollisionGroup();
        cGroup.circles = new Circle[]{ new Circle(0, 0, 1.5f)};

        hitSequence.frames = new CollisionGroup[4];
        for(int i = 0; i < hitSequence.frames.length; i++) {
            hitSequence.frames[i] = cGroup;
        }
        bundle.addHitboxSequence(hitSequence);

        animator = new AnimatorTrait(this, bundle.getAnimations());
        traits.add(animator);

        traits.add(new TimedCollisionTrait(this, bundle));
        traits.add(new MoveDirectionTrait(this, direction, speed));

        FixtureDef fd = new FixtureDef();
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.position.x = x;
        bd.position.y = y;
        bd.fixedRotation = true;
        CBody body = new BoxBody(2, 1).init(fd, bd);
        physical = new PhysicalTrait(this, body);
        physical.setSensor(true);
        physical.setOffset(0, sparkOffset);
        traits.add(physical);

        traits.add(new DebugTrait(this));

        initializeTraits();

        body.setFriction(0);
    }
}
