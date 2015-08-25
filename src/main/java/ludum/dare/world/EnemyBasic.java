package ludum.dare.world;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import ludum.dare.collision.CollisionGroup;
import ludum.dare.collision.CollisionSequence;
import ludum.dare.screen.GameScreen;
import ludum.dare.utils.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.winger.physics.CBody;
import com.winger.physics.body.BoxBody;
import ludum.dare.collision.AnimationBundle;
import ludum.dare.trait.*;

/**
 * Created by jake on 8/21/2015.
 */
public class EnemyBasic extends GameObject implements AnimationCallback {
    private PhysicalTrait physical;
    private AnimatorTrait animator;
    public Vector2 target;
    public boolean hitFromRight;

    public boolean rightFacing = false;



    private CollisionCallback collisionFunc = new CollisionCallback() {
        @Override
        public void collide(GameObject obj) {
            if (obj.getTrait(InputHandlerTrait.class) == null) {
                return;
            }
            HealthTrait health = obj.getTrait(HealthTrait.class);
            if (health != null) {
                health.damage(10, EnemyBasic.this);
            }
            SoundLibrary.GetSound("Punch_Hit").play();
        }
    };
    private HealthCallback healthCallback = new HealthCallback() {
        @Override
        public void damageReceived(int amount, GameObject from) {
            collidedWith(from);

        }

        @Override
        public void healthRegained(int amount, GameObject from) {

        }

        @Override
        public void died() {
            if (MathUtils.randomBoolean(.6f)) {
                GameScreen.addObject(new Fourty(getTrait(PositionTrait.class).x, getTrait(PositionTrait.class).y - 5.5f , 0, 2));
            }
        }
    };

    public EnemyBasic(float x, float y, float z){
        float width = 12;
        float height = 12;
        traits.add(new PositionTrait(this, x, y, z));
        traits.add(new DrawableTrait(this));
        traits.add(new CollidableTrait(this, collisionFunc));
        traits.add(new ControlTraitEnemy(this, 7, 9, 1.5f));
        traits.add(new ImmobilizedTrait(this));

        traits.add(new HealthTrait(this, 50, healthCallback));

        AnimationBundle bundle = new AnimationBundle();

        bundle.addNamedAnimation(new NamedAnimation("stand", .1f, AtlasManager.instance.findRegions("bot/stand/botStand"),
                AtlasManager.instance.findRegions("bot/stand/botStand_n"), new Vector2(0, -.7f), new Vector2(width, height)));
        CollisionSequence standSequence = new CollisionSequence();
        standSequence.name = "stand";

        CollisionGroup standGroup = new CollisionGroup();
        standGroup.boxes = new Rectangle[] {new Rectangle(-2,-6,4,10)};

        standSequence.frames = new CollisionGroup[] {standGroup};
        bundle.addHurtboxSequence(standSequence);

        bundle.addNamedAnimation(new NamedAnimation("walk", .1f, AtlasManager.instance.findRegions("bot/walk/botWalk"),
                AtlasManager.instance.findRegions("bot/walk/botWalk_n"), new Vector2(0, -.7f), new Vector2(width, height)));
        CollisionSequence walkSequence = new CollisionSequence();
        walkSequence.name = "walk";

        CollisionGroup walkGroup = new CollisionGroup();
        walkGroup.boxes = new Rectangle[] {new Rectangle(-2,-6,4,10)};

        walkSequence.frames = new CollisionGroup[] {walkGroup, walkGroup, walkGroup, walkGroup};
        bundle.addHurtboxSequence(walkSequence);

        bundle.addNamedAnimation(new NamedAnimation("hit", .1f, AtlasManager.instance.findRegions("bot/hit/botHit"),
                AtlasManager.instance.findRegions("bot/hit/botHit_n"), new Vector2(0, -.7f), new Vector2(width * 1.5f, height)));
        CollisionSequence hitSequence = new CollisionSequence();
        hitSequence.name = "hit";

        CollisionGroup group1 = new CollisionGroup();
        group1.circles = new Circle[]{ new Circle(-6.0f,1.5f,2.5f)};

        hitSequence.frames = new CollisionGroup[8];
        hitSequence.frames[6] = group1;
        bundle.addHitboxSequence(hitSequence);

        CollisionSequence hurtSequence = new CollisionSequence();
        hurtSequence.name = "hit";

        CollisionGroup hurtGroup = new CollisionGroup();
        hurtGroup.boxes = new Rectangle[] {new Rectangle(-2,-6,4,10)};

        hurtSequence.frames = new CollisionGroup[] {hurtGroup, hurtGroup, hurtGroup, hurtGroup, hurtGroup, hurtGroup, hurtGroup, hurtGroup, hurtGroup};
        bundle.addHurtboxSequence(hurtSequence);

        bundle.addNamedAnimation(new NamedAnimation("lightPain", .1f, AtlasManager.instance.findRegions("bot/lightPain/botPain"),
                AtlasManager.instance.findRegions("bot/lightPain/botPain_n"), new Vector2(0, 0), new Vector2(width, height)));
        bundle.addNamedAnimation(new NamedAnimation("heavyPain", .1f, AtlasManager.instance.findRegions("bot/heavyPain/botPain"),
                AtlasManager.instance.findRegions("bot/heavyPain/botPain_n"), new Vector2(0, 0), new Vector2(width, height)));
        bundle.addNamedAnimation(new NamedAnimation("die", .1f, AtlasManager.instance.findRegions("bot/die/botDie"),
                AtlasManager.instance.findRegions("bot/die/botDie_n"), new Vector2(0, 0), new Vector2(width*1.5f, height)));


        animator = new AnimatorTrait(this, bundle.getAnimations());
        animator.registerAnimationCallback(this);
        traits.add(animator);
        traits.add(new TimedCollisionTrait(this, bundle));

        traits.add(new AITrait(this));
        traits.add(new AIMovementAggressiveTrait(this));
        traits.add(new AIMeleeAttackTrait(this));

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
    public void collidedWith(GameObject p){
        Vector2 v = new Vector2(0, 0);
        ControlTraitEnemy myControl = getTrait(ControlTraitEnemy.class);
        PositionTrait ePos = getTrait(PositionTrait.class);
        PositionTrait pPos = p.getTrait(PositionTrait.class);
        String pAnimName = p.getTrait(AnimatorTrait.class).getCurrentAnimation().getName();

        if(pAnimName == "punch"){
            if(ePos.x < pPos.x && ePos.y < pPos.y){
                hitFromRight = true;
                v.x -= 3;
                v.y -= 1;
            }
            if(ePos.x >= pPos.x && ePos.y < pPos.y){
                hitFromRight = false;
                v.x += 3;
                v.y -= 1;
            }
            if(ePos.x >= pPos.x && ePos.y >= pPos.y){
                hitFromRight = false;
                v.x += 3;
                v.y += 1;
            }
            if(ePos.x < pPos.x && ePos.y >= pPos.y){
                hitFromRight = true;
                v.x -= 3;
                v.y += 1;
            }
        } else if(pAnimName == "punch2" || pAnimName == "jumpKick"){
            if(ePos.x < pPos.x && ePos.y < pPos.y){
                hitFromRight = true;
                v.x -= 10;
                v.y -= 5;
            }
            if(ePos.x >= pPos.x && ePos.y < pPos.y){
                hitFromRight = false;
                v.x += 10;
                v.y -= 5;
            }
            if(ePos.x >= pPos.x && ePos.y >= pPos.y){
                hitFromRight = false;
                v.x += 10;
                v.y += 5;
            }
            if(ePos.x < pPos.x && ePos.y >= pPos.y){
                hitFromRight = true;
                v.x -= 10;
                v.y += 5;
            }
        }
        getTrait(PhysicalTrait.class).body.body.setLinearVelocity(v);
    }

    public void updateTarget(Vector2 t){
        target = t;
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