package ludum.dare.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import ludum.dare.screen.GameScreen;
import ludum.dare.utils.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.winger.physics.CBody;
import com.winger.physics.body.BoxBody;
import ludum.dare.collision.AnimationBundle;
import ludum.dare.collision.CollisionGroup;
import ludum.dare.collision.CollisionSequence;
import ludum.dare.trait.*;

import java.util.Map;

/**
 * Created by jake on 8/22/2015.
 */
public class EnemyHeavy extends GameObject implements AnimationCallback {
    private PhysicalTrait physical;
    public Vector2 target;
    private AnimatorTrait animator;

    public boolean rightFacing = false;

    private CollisionCallback collisionFunc = new CollisionCallback() {
        @Override
        public void collide(GameObject obj) {
            if (obj.getTrait(InputHandlerTrait.class) == null) {
                return;
            }
            HealthTrait health = obj.getTrait(HealthTrait.class);
            if (health != null) {
                health.damage(20, EnemyHeavy.this);
            }
        }
    };


    public EnemyHeavy(float x, float y, float z){
        float width = 12;
        float height =12;
        traits.add(new PositionTrait(this, x, y, z));
        traits.add(new DrawableTrait(this));
        traits.add(new CollidableTrait(this, collisionFunc));

        traits.add(new HealthTrait(this, 80, null));

        //This is to cache the sound
        SoundLibrary.GetSound("Ground_Pound");

        AnimationBundle bundle = new AnimationBundle();

        bundle.addNamedAnimation(new NamedAnimation("stand", .1f, AtlasManager.instance.findRegions("heavybot/stand/heavyStand"), AtlasManager.instance.findRegions("heavybot/stand/heavyStand"), new Vector2(0, 0), new Vector2(width * 1.5f, height)));
        CollisionSequence standSequence = new CollisionSequence();
        standSequence.name = "stand";

        CollisionGroup standGroup = new CollisionGroup();
        standGroup.boxes = new Rectangle[] {new Rectangle(-2,-5.5f,4,10)};

        standSequence.frames = new CollisionGroup[] {standGroup};
        bundle.addHurtboxSequence(standSequence);

        bundle.addNamedAnimation(new NamedAnimation("walk", .1f, AtlasManager.instance.findRegions("heavybot/walk/heavyWalk"), AtlasManager.instance.findRegions("heavybot/walk/heavyWalk"), new Vector2(0, 0), new Vector2(width * 1.5f, height)));
        CollisionSequence walkSequence = new CollisionSequence();
        walkSequence.name = "walk";

        CollisionGroup walkGroup = new CollisionGroup();
        walkGroup.boxes = new Rectangle[] {new Rectangle(-2,-5.5f,4,10)};

        walkSequence.frames = new CollisionGroup[4];
        for(int i = 0; i < walkSequence.frames.length; i++){
            walkSequence.frames[i] = walkGroup;
        }
        bundle.addHurtboxSequence(walkSequence);


        bundle.addNamedAnimation(new NamedAnimation("hit", .1f, AtlasManager.instance.findRegions("heavybot/slam/heavySlam"), AtlasManager.instance.findRegions("heavybot/slam/heavySlam"), new Vector2(-3, 3), new Vector2(width * 1.5f, height * 1.5f)));
        CollisionSequence hitSequence = new CollisionSequence();
        hitSequence.name = "hit";

        CollisionGroup hitGroup1 = new CollisionGroup();
        hitGroup1.boxes = new Rectangle[] {new Rectangle(-2,-5.5f,4,10)};

        CollisionGroup hitGroup2 = new CollisionGroup();
        hitGroup2.boxes = new Rectangle[] {new Rectangle(-2,-5.5f,4,10)};

        CollisionGroup hitGroup3 = new CollisionGroup();
        hitGroup3.circles = new Circle[] {new Circle(0, -3, 2.5f), new Circle(-2.5f, -.5f, 2.5f), new Circle(-5, 2, 2.5f)};

        CollisionGroup hitGroup4 = new CollisionGroup();
        hitGroup4.circles = new Circle[] {new Circle(0, -3, 2.5f), new Circle(-3.5f, -2.75f, 2.5f), new Circle(-6.5f, -2.5f, 2.5f)};

        hitSequence.frames = new CollisionGroup[] {hitGroup1, hitGroup2, hitGroup2, hitGroup2, hitGroup2, hitGroup3, hitGroup4, hitGroup4, hitGroup3};
        bundle.addHurtboxSequence(hitSequence);

        CollisionSequence hitBoxSeq = new CollisionSequence();
        hitBoxSeq.name = "hit";

        CollisionGroup hitBoxGroup1 = new CollisionGroup();
        hitBoxGroup1.circles = new Circle[] {new Circle(-9f, 0, 2.5f)};

        CollisionGroup hitBoxGroup2 = new CollisionGroup();
        hitBoxGroup2.circles = new Circle[] {new Circle(-7.5f, -4.5f, 2.5f)};

        hitBoxSeq.frames = new CollisionGroup[9];

        hitBoxSeq.frames[5] = hitBoxGroup1;
        hitBoxSeq.frames[6] = hitBoxGroup2;


        bundle.addHitboxSequence(hitBoxSeq);

        bundle.addNamedAnimation(new NamedAnimation("die", .1f, AtlasManager.instance.findRegions("bot/die/botDie"),
                AtlasManager.instance.findRegions("bot/die/botDie"), new Vector2(4, 0), new Vector2(width * 1.5f, height)));


        animator = new AnimatorTrait(this, bundle.getAnimations());
        animator.registerAnimationCallback(this);
        traits.add(animator);

        traits.add(new TimedCollisionTrait(this, bundle));

        traits.add(new AITrait(this));
        traits.add(new AIMovementAggressiveTrait(this));
        traits.add(new ControlTraitEnemy(this, 3, 12, 3));

        FixtureDef fd = new FixtureDef();
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.position.x = x;
        bd.position.y = y;
        bd.fixedRotation = true;
        CBody body = new BoxBody(width/2, 1).init(fd, bd);
        physical = new PhysicalTrait(this, body);
        physical.setOffset(0, height / 2 - .5f);
        traits.add(physical);

        traits.add(new DebugTrait(this));
    }

    @Override
    public void animationStarted(String name) {
        if (name.equals("die")) {
            GameScreen.addObject(new Explosion(getTrait(PositionTrait.class).x, getTrait(PositionTrait.class).y, 12));
        }
    }

    @Override
    public void animationEnded(String name) {

    }
}
