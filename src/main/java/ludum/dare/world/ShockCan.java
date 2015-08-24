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
import ludum.dare.trait.*;
import ludum.dare.utils.AtlasManager;
import ludum.dare.utils.NamedAnimation;

/**
 * Created by jake on 8/22/2015.
 */
public class ShockCan extends GameObject{
    private PhysicalTrait physical;
    private Vector2 target;
    private AnimatorTrait animator;


    public ShockCan(float x, float y, float z){
        float width = 12;
        float height = 12;
        traits.add(new PositionTrait(this, x, y, z));
        traits.add(new DrawableTrait(this));

        traits.add(new HealthTrait(this, 80, null));

        AnimationBundle bundle = new AnimationBundle();

        NamedAnimation red = new NamedAnimation("red", .1f,AtlasManager.instance.findRegions("box/boxRed"), AtlasManager.instance.findRegions("box/boxRed"), new Vector2(0, 0), new Vector2(width*1.5f, height));
        bundle.addNamedAnimation(red);
        NamedAnimation redDent = new NamedAnimation("redDent", .1f,AtlasManager.instance.findRegions("box/boxRedDent"), AtlasManager.instance.findRegions("box/boxRedDent"), new Vector2(0, 0), new Vector2(width*1.5f, height));
        bundle.addNamedAnimation(redDent);
        NamedAnimation green = new NamedAnimation("green", .1f,AtlasManager.instance.findRegions("box/boxGreen"), AtlasManager.instance.findRegions("box/boxGreen"), new Vector2(0, 0), new Vector2(width*1.5f, height));
        bundle.addNamedAnimation(green);
        NamedAnimation greenDent = new NamedAnimation("greenDent", .1f,AtlasManager.instance.findRegions("box/boxGreenDent"), AtlasManager.instance.findRegions("box/boxGreenDent"), new Vector2(0, 0), new Vector2(width*1.5f, height));
        bundle.addNamedAnimation(greenDent);
        NamedAnimation broken = new NamedAnimation("broken", .1f,AtlasManager.instance.findRegions("box/boxBroken"), AtlasManager.instance.findRegions("box/boxBroken"), new Vector2(0, 0), new Vector2(width*1.5f, height));
        bundle.addNamedAnimation(broken);
        NamedAnimation dead = new NamedAnimation("dead", .1f,AtlasManager.instance.findRegions("box/boxDead"), AtlasManager.instance.findRegions("box/boxDead"), new Vector2(0, 0), new Vector2(width*1.5f, height));
        bundle.addNamedAnimation(dead);


        CollisionGroup hurtGroup = new CollisionGroup();
        hurtGroup.boxes = new Rectangle[] {new Rectangle(-6, -6, 12, 12) };

        CollisionSequence redSeq = new CollisionSequence();
        redSeq.name = "red";
        redSeq.frames = new CollisionGroup[] { hurtGroup };
        bundle.addHurtboxSequence(redSeq);

        CollisionSequence redDentSeq = new CollisionSequence();
        redDentSeq.name = "redDent";
        redDentSeq.frames = new CollisionGroup[] { hurtGroup };
        bundle.addHurtboxSequence(redDentSeq);

        CollisionSequence greenSeq = new CollisionSequence();
        greenSeq.name = "green";
        greenSeq.frames = new CollisionGroup[] { hurtGroup };
        bundle.addHurtboxSequence(greenSeq);

        CollisionSequence greenDentSeq = new CollisionSequence();
        greenDentSeq.name = "greenDent";
        greenDentSeq.frames = new CollisionGroup[] { hurtGroup };
        bundle.addHurtboxSequence(greenDentSeq);

        CollisionSequence brokenSeq = new CollisionSequence();
        brokenSeq.name = "broken";
        brokenSeq.frames = new CollisionGroup[] { hurtGroup };
        bundle.addHurtboxSequence(brokenSeq);

        animator = new AnimatorTrait(this, bundle.getAnimations());
        traits.add(animator);
        traits.add(new TimedCollisionTrait(this, bundle));

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
