package ludum.dare.world;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.winger.input.raw.CGamePad;
import com.winger.input.raw.CKeyboard;
import com.winger.input.raw.CMouse;
import com.winger.physics.CBody;
import com.winger.physics.body.BoxBody;
import ludum.dare.collision.AnimationBundle;
import ludum.dare.Conf;
import ludum.dare.collision.CollisionGroup;
import ludum.dare.collision.CollisionSequence;
import ludum.dare.trait.*;
import ludum.dare.utils.AtlasManager;
import ludum.dare.utils.NamedAnimation;


/**
 * Created by mwingfield on 8/3/15.
 */
public class Player extends GameObject {
    private static final float PLAYER_ANIMATION_VEL_CHANGE = 0.1f;

    private PhysicalTrait physical;
    private AnimatorTrait animator;
    private TimedCollisionTrait hitboxes;

    public Player(float x, float y, float z, float width, float height, CMouse mouse, CKeyboard keyboard, CGamePad gamepad){
        traits.add(new PositionTrait(this, x, y, z));
        traits.add(new SizeTrait(this, width, height));
        traits.add(new DrawableTrait(this));

        ID = "Player";


        AnimationBundle bundle = new AnimationBundle();

        bundle.addNamedAnimation(new NamedAnimation("stand", 0.1f, AtlasManager.instance.getAtlas("game").findRegions("bumStand"), new Vector2(0,-.3f), new Vector2(6,6)));
        bundle.addNamedAnimation(new NamedAnimation("walk", 0.1f, AtlasManager.instance.getAtlas("game").findRegions("bumWalk"), new Vector2(0,-.3f), new Vector2(6,6)));
        bundle.addNamedAnimation(new NamedAnimation("punch", 0.1f, AtlasManager.instance.getAtlas("game").findRegions("bumJab"), new Vector2(1.7f,-.3f), new Vector2(8,6)));

        CollisionSequence sequence = new CollisionSequence();
        sequence.name = "punch";

        CollisionGroup group1 = new CollisionGroup();
        group1.circles = new Circle[] {new Circle(7.2f,.5f,1.5f)};

        sequence.frames = new CollisionGroup[3];
        sequence.frames[0] = group1;
        bundle.addHitboxSequence(sequence);

        CollisionSequence collisionSequence = new CollisionSequence();
        collisionSequence.name = "stand";
        collisionSequence.frames = new CollisionGroup[9];

        // Generating the hurtbox
        CollisionGroup collisionGroup = new CollisionGroup();
        collisionGroup.boxes = new Rectangle[]{new Rectangle(-4,-4,8,8)};

        for(int i = 0; i < collisionSequence.frames.length; i++){
            collisionSequence.frames[i] = collisionGroup;
        }

        bundle.addHurtboxSequence(collisionSequence);

        animator = new AnimatorTrait(this, bundle.getAnimations());
        traits.add(animator);

        hitboxes = new TimedCollisionTrait(this, bundle);
        traits.add(hitboxes);

        traits.add(new InputHandlerTrait(this, mouse, keyboard, gamepad));
        traits.add(new ControlTrait(this));
        //
        // this part sets the collision filter for the boundary object
        // all boundary objects collide with Actor objects
        FixtureDef fD = new FixtureDef();
        fD.filter.categoryBits = Conf.instance.physicsBitFilterActor();
        fD.filter.maskBits = Conf.instance.physicsBitFilterBoundary();
        //
        BodyDef bD = new BodyDef();
        bD.type = BodyDef.BodyType.DynamicBody;
        bD.position.x = x;
        bD.position.y = y;
        bD.fixedRotation = true;
        CBody body = new BoxBody(width, height).init(fD, bD);
        physical = new PhysicalTrait(this, body);
        traits.add(physical);

        traits.add(new DebugTrait(this));

        initializeTraits();

        body.setFriction(1);
    }
}