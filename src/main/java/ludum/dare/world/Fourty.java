package ludum.dare.world;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.winger.physics.CBody;
import com.winger.physics.body.BoxBody;
import ludum.dare.collision.AnimationBundle;
import ludum.dare.collision.CollisionGroup;
import ludum.dare.collision.CollisionSequence;
import ludum.dare.trait.*;
import ludum.dare.utils.AtlasManager;
import ludum.dare.utils.CollisionCallback;
import ludum.dare.utils.HealthCallback;
import ludum.dare.utils.NamedAnimation;

/**
 * Created by jake on 8/21/2015.
 */
public class Fourty extends GameObject{
    private AnimatorTrait animator;

    private CollisionCallback collisionFunc = new CollisionCallback() {
        @Override
        public void collide(GameObject obj) {
            SoundLibrary.GetSound("Collect").play();
            markForDeletion();
        }
    };


    public Fourty(float x, float y, float z, float size){
        traits.add(new PositionTrait(this, x, y, z));
        traits.add(new DrawableTrait(this));
        traits.add(new CollidableTrait(this, collisionFunc));


        AnimationBundle bundle = new AnimationBundle();

        bundle.addNamedAnimation(new NamedAnimation("NOANIMATION", .1f, AtlasManager.instance.findRegions("collectables/40brown"),
                AtlasManager.instance.findRegions("collectables/40brown_n"), new Vector2(0, 0), new Vector2(size, size*2)));

        CollisionSequence hitSequence = new CollisionSequence();
        hitSequence.name = "NOANIMATION";

        CollisionGroup group1 = new CollisionGroup();
        group1.boxes = new Rectangle[]{ new Rectangle(-(size/2),-((size*2)/2),size,size*2)};

        hitSequence.frames = new CollisionGroup[1];
        hitSequence.frames[0] = group1;
        bundle.addHitboxSequence(hitSequence);

        animator = new AnimatorTrait(this, bundle.getAnimations());
        traits.add(animator);
        traits.add(new TimedCollisionTrait(this, bundle));

        FixtureDef fd = new FixtureDef();
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.position.x = x;
        bd.position.y = y;
        bd.fixedRotation = true;
        CBody body = new BoxBody(1, 1).init(fd, bd);
        PhysicalTrait physical = new PhysicalTrait(this, body);
        physical.setOffset(0, size-.5f);
        traits.add(physical);

        traits.add(new DebugTrait(this));

        initializeTraits();
    }

}

