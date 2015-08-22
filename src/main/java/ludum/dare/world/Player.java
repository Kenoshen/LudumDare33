package ludum.dare.world;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.winger.input.raw.CGamePad;
import com.winger.input.raw.CKeyboard;
import com.winger.input.raw.CMouse;
import com.winger.physics.CBody;
import com.winger.physics.body.BoxBody;
import com.winger.physics.body.PlayerBody;
import ludum.dare.hitbox.AnimationBundle;
import ludum.dare.Conf;
import ludum.dare.hitbox.HitboxGroup;
import ludum.dare.hitbox.HitboxSequence;
import ludum.dare.trait.*;
import ludum.dare.utils.AtlasManager;
import ludum.dare.utils.NamedAnimation;

import java.util.Map;

/**
 * Created by mwingfield on 8/3/15.
 */
public class Player extends GameObject {
    private static final float PLAYER_ANIMATION_VEL_CHANGE = 0.1f;

    private PhysicalTrait physical;
    private AnimatorTrait animator;
    private TimedHitboxTrait hitboxes;

    public Player(float x, float y, float z, float width, float height, CMouse mouse, CKeyboard keyboard, CGamePad gamepad){
        traits.add(new PositionTrait(this, x, y, z));
        traits.add(new SizeTrait(this, width, height));
        final Sprite sprite = new Sprite();
        traits.add(new DrawableTrait(this, sprite));


        AnimationBundle bundle = new AnimationBundle();

        final NamedAnimation animation = new NamedAnimation("stand", 0.2f, AtlasManager.instance.getAtlas("game").findRegions("bumStand"));
        bundle.addNamedAnimation(animation);
        bundle.addNamedAnimation(new NamedAnimation("walk", 0.2f, AtlasManager.instance.getAtlas("game").findRegions("bumWalk")));

        HitboxSequence sequence = new HitboxSequence();
        sequence.title = "basic";

        HitboxGroup group1 = new HitboxGroup();
        group1.circles = new Circle[] {new Circle(1,1,1)};

        HitboxGroup group2 = new HitboxGroup();
        group2.circles = new Circle[] {new Circle(1,1.5f,1)};

        HitboxGroup group3 = new HitboxGroup();
        group3.circles = new Circle[] {new Circle(1,2,1)};

        sequence.frames = new HitboxGroup[5];
        sequence.frames[2] = group1;
        sequence.frames[3] = group2;
        sequence.frames[4] = group3;
        bundle.addHitboxSequence(sequence);

        animator = new AnimatorTrait(this, bundle.getAnimations());
        traits.add(animator);

        hitboxes = new TimedHitboxTrait(this, bundle.getHitboxes());
        traits.add(hitboxes);

        traits.add(new ControlTrait(this, mouse, keyboard, gamepad));
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
        CBody body = new BoxBody(width, height).init(fD, bD);
        physical = new PhysicalTrait(this, body);
        traits.add(physical);

        traits.add(new DebugTrait(this));

        final Player self = this;
        traits.add(new UpdatableTrait(this, new Runnable(){
            @Override
            public void run() {
                Vector2 vel = physical.body.getLinearVelocity();
                if (vel.len() > PLAYER_ANIMATION_VEL_CHANGE){
                    animator.changeStateIfUnique("walk", true);
                } else {
                    animator.changeStateIfUnique("stand", true);
                }
                DrawableTrait drawable = self.getTrait(DrawableTrait.class);
                if (vel.x > 0){
                    animator.flipped = false;
                } else if(vel.x < 0) {
                    animator.flipped = true;
                }
            }
        }));

        initializeTraits();

        body.setFriction(1);
    }
}
