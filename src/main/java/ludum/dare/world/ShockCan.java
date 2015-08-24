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
public class ShockCan extends GameObject{
    private PhysicalTrait physical;
    public Vector2 target;
    private AnimatorTrait animator;

    private boolean isRed = true;


    public ShockCan(float x, float y, float z){
        float size = 8;
        traits.add(new PositionTrait(this, x, y, z));
        traits.add(new DrawableTrait(this));

        traits.add(new HealthTrait(this, 80, null));
        traits.add(new AITrait(this));

        AnimationBundle bundle = new AnimationBundle();

        NamedAnimation red = new NamedAnimation("red", .1f,AtlasManager.instance.findRegions("box/boxRed"), AtlasManager.instance.findRegions("box/boxRed"), new Vector2(0, 0), new Vector2(size, size*2));
        bundle.addNamedAnimation(red);
        NamedAnimation redDent = new NamedAnimation("redDent", .1f,AtlasManager.instance.findRegions("box/boxRedDent"), AtlasManager.instance.findRegions("box/boxRedDent"), new Vector2(0, 0), new Vector2(size, size*2));
        bundle.addNamedAnimation(redDent);
        NamedAnimation green = new NamedAnimation("green", .1f,AtlasManager.instance.findRegions("box/boxGreen"), AtlasManager.instance.findRegions("box/boxGreen"), new Vector2(0, 0), new Vector2(size, size*2));
        bundle.addNamedAnimation(green);
        NamedAnimation greenDent = new NamedAnimation("greenDent", .1f,AtlasManager.instance.findRegions("box/boxGreenDent"), AtlasManager.instance.findRegions("box/boxGreenDent"), new Vector2(0, 0), new Vector2(size, size*2));
        bundle.addNamedAnimation(greenDent);
        NamedAnimation broken = new NamedAnimation("broken", .1f,AtlasManager.instance.findRegions("box/boxBroken"), AtlasManager.instance.findRegions("box/boxBroken"), new Vector2(0, 0), new Vector2(size, size*2));
        bundle.addNamedAnimation(broken);
        NamedAnimation dead = new NamedAnimation("dead", .1f,AtlasManager.instance.findRegions("box/boxDead"), AtlasManager.instance.findRegions("box/boxDead"), new Vector2(0, 0), new Vector2(size, size*2));
        bundle.addNamedAnimation(dead);


        CollisionGroup hurtGroup = new CollisionGroup();
        hurtGroup.boxes = new Rectangle[] {new Rectangle(-3, -8, 6, 12) };

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
        animator.setState("red");
        traits.add(animator);
        traits.add(new TimedCollisionTrait(this, bundle));

        FixtureDef fd = new FixtureDef();
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.position.x = x;
        bd.position.y = y;
        bd.fixedRotation = true;
        CBody body = new BoxBody(size/2, 1).init(fd, bd);
        physical = new PhysicalTrait(this, body);
        physical.setOffset(0, size - 1f);
        traits.add(physical);

        traits.add(new DebugTrait(this));

        initializeTraits();
    }

    public void shoot() {
        PositionTrait position = getTrait(PositionTrait.class);
        Vector2 aim = target.cpy().sub(position.x, position.y).sub(0, 5);
        GameScreen.addObject(new SparkBall(position.x, position.y - 1, 0, aim.cpy().nor(), 10));
    }

    public void goGreen() {
        isRed = false;
        if (getTrait(HealthTrait.class).health > 60) {
            getTrait(AnimatorTrait.class).setState("green");
        } else if (getTrait(HealthTrait.class).health > 20) {
            getTrait(AnimatorTrait.class).setState("greenDent");
        } else if (getTrait(HealthTrait.class).health > 0){
            getTrait(AnimatorTrait.class).setState("broken");
        } else {
            getTrait(AnimatorTrait.class).setState("dead");
        }
    }

    public void goRed() {
        isRed = true;
        if (getTrait(HealthTrait.class).health > 60) {
            getTrait(AnimatorTrait.class).setState("red");
        } else if (getTrait(HealthTrait.class).health > 20) {
            getTrait(AnimatorTrait.class).setState("redDent");
        } else if (getTrait(HealthTrait.class).health > 0){
            getTrait(AnimatorTrait.class).setState("broken");
        } else {
            getTrait(AnimatorTrait.class).setState("dead");
        }
    }

    public void updateAnimation() {
        if(isRed) {
            goRed();
        } else {
            goGreen();
        }
    }
}
