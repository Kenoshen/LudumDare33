package ludum.dare.world;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.winger.physics.CBody;
import com.winger.physics.body.BoxBody;
import ludum.dare.collision.AnimationBundle;
import ludum.dare.collision.CollisionGroup;
import ludum.dare.collision.CollisionSequence;
import ludum.dare.screen.GameScreen;
import ludum.dare.trait.*;
import ludum.dare.utils.AtlasManager;
import ludum.dare.utils.NamedAnimation;

/**
 * Created by jake on 8/22/2015.
 */
public class SpawnerDoor extends GameObject{
    private PhysicalTrait physical;
    private Vector2 target;
    private AnimatorTrait animator;


    public SpawnerDoor(float x, float y, float z){
        float size = 8;
        float width = size;
        float height = size*1.5f;
        traits.add(new PositionTrait(this, x, y, z));
        traits.add(new DrawableTrait(this));

        traits.add(new HealthTrait(this, 80, null));

        AnimationBundle bundle = new AnimationBundle();

        NamedAnimation open = new NamedAnimation("open", .1f,AtlasManager.instance.findRegions("door/open/open"), AtlasManager.instance.findRegions("door/open/open"), new Vector2(0, 0), new Vector2(width, height));
        bundle.addNamedAnimation(open);
        NamedAnimation close = new NamedAnimation("close", .1f,AtlasManager.instance.findRegions("door/close/close"), AtlasManager.instance.findRegions("door/close/close"), new Vector2(0, 0), new Vector2(width, height));
        bundle.addNamedAnimation(close);

        animator = new AnimatorTrait(this, bundle.getAnimations());
        traits.add(animator);

        FixtureDef fd = new FixtureDef();
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.position.x = x;
        bd.position.y = y;
        bd.fixedRotation = true;
        CBody body = new BoxBody(size/2, 1).init(fd, bd);
        physical = new PhysicalTrait(this, body);
        physical.setOffset(0, size/2 - .5f);
        traits.add(physical);

        traits.add(new DebugTrait(this));

        initializeTraits();
        animator.setState("close", false, true);
    }

    public void spawnSomething() {
        PositionTrait position = getTrait(PositionTrait.class);
        GameScreen.addObject(new SparkBall(position.x, position.y - 8, 0, new Vector2(1, -1), 20));
    }
}
